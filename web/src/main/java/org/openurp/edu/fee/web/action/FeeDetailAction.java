/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.edu.fee.web.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.query.builder.Condition;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.transfer.TransferListener;
import org.beangle.commons.transfer.importer.EntityImporter;
import org.beangle.commons.transfer.importer.listener.ImporterForeignerListener;
import org.openurp.edu.base.code.model.CurrencyCategory;
import org.openurp.edu.base.code.model.FeeMode;
import org.openurp.edu.base.code.model.FeeType;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.base.model.StudentState;
import org.openurp.edu.base.service.StudentService;
import org.openurp.edu.eams.web.util.DownloadHelper;
import org.openurp.edu.fee.data.FeeDetailInitStudent;
import org.openurp.edu.fee.model.FeeDefault;
import org.openurp.edu.fee.model.FeeDetail;
import org.openurp.edu.fee.utils.BigDecimalUtils;
import org.openurp.edu.fee.utils.ConditionUtils;

public class FeeDetailAction extends FeeSearchAction {

  protected StudentService studentService;

  protected List<TransferListener> importerListeners;

  private final Map<String, FeeDetailInitStudent> sessionInitStudentMap;

  public FeeDetailAction() {
    sessionInitStudentMap = CollectUtils.newHashMap();
  }

  public String initIndex() {
    put("semester", entityDao.get(Semester.class, getIntId("feeDetail.semester")));
    put("departments", getDeparts());
    return forward();
  }

  public String toInitStudentList() {
    String sessionId = getRequest().getSession().getId();
    sessionInitStudentMap.remove(sessionId);

    Semester semester = entityDao.get(Semester.class, getIntId("feeDetail.semester"));

    // 获取在指定学年学期期间在校的学生
    OqlBuilder<Student> builder1 = OqlBuilder.from(Student.class, "student");
    populateConditions(builder1);
    StringBuilder hql = new StringBuilder();
    hql.append("exists (");
    hql.append("  from student.states studentState");
    hql.append(" where studentState.std = student");
    hql.append("   and studentState.inschool = true");
    hql.append("   and studentState.beginOn <= :endOn");
    hql.append("   and studentState.endOn >= :beginOn");
    hql.append(")");
    builder1.where(hql.toString(), semester.getEndOn(), semester.getBeginOn());
    List<Student> students = entityDao.search(builder1);

    // 获得上面所获学生所有历史缴费记录
    OqlBuilder<?> builder2 = OqlBuilder.from(FeeDetail.class.getName() + " feeDetail");
    Condition studentCondition = ConditionUtils.splitCollection("feeDetail.std", "stds", 900, students);
    builder2.where(studentCondition);
    builder2.groupBy("feeDetail.std.id");
    builder2.select("feeDetail.std.id, sum(feeDetail.payed)");
    List<Object[]> stats = (List<Object[]>) entityDao.search(builder2);
    Map<Long, Number> sumFeeMap = CollectUtils.newHashMap();
    for (Object[] data : stats) {
      sumFeeMap.put((Long) data[0], (Number) data[1]);
    }

    OqlBuilder<FeeDetail> builder3 = OqlBuilder.from(FeeDetail.class, "feeDetail");
    builder3.where(studentCondition);
    builder3.where("feeDetail.semester = :semester", semester);
    List<FeeDetail> feesBySemester = entityDao.search(builder3);
    Map<Student, FeeDetail> feeBySemesterMap = CollectUtils.newHashMap();
    for (FeeDetail feeDetail : feesBySemester) {
      feeBySemesterMap.put(feeDetail.getStd(), feeDetail);
    }

    // 根据上面找出的学生学籍状态，找到对应匹配到的默认收费标准
    OqlBuilder<FeeDefault> builder4 = OqlBuilder.from(FeeDefault.class, "feeDefault");
    // FIXME 2018-12-05 zhouqi 下面两个条件是暂时的，等到数据补缮后才去除
    builder4.where("feeDefault.fromGrade is not null");
    builder4.where("feeDefault.toGrade is not null");
    hql = new StringBuilder();
    hql.append("exists (");
    hql.append("  from ").append(StudentState.class.getName()).append(" studentState");
    hql.append(" where studentState.std.level = feeDefault.level");
    hql.append("   and studentState.inschool = true");
    hql.append("   and studentState.beginOn <= :endOn");
    hql.append("   and studentState.endOn >= :beginOn");
    hql.append("   and studentState.grade between feeDefault.fromGrade and feeDefault.toGrade");
    hql.append("   and (feeDefault.department is null or studentState.department = feeDefault.department)");
    hql.append("   and (feeDefault.major is null or studentState.major = feeDefault.major)");
    hql.append(")");
    builder4.where(hql.toString(), semester.getEndOn(), semester.getBeginOn());
    List<FeeDefault> feeDefaults = entityDao.search(builder4);
    Map<String, FeeDefault> feeDefaultMap = CollectUtils.newHashMap();
    for (FeeDefault feeDefault : feeDefaults) {
      String key = feeDefault.getFromGrade() + "_" + feeDefault.getToGrade() + "_"
          + feeDefault.getLevel().getId() + "_"
          + (null == feeDefault.getDepartment() ? "?" : feeDefault.getDepartment().getId()) + "_"
          + (null == feeDefault.getMajor() ? "?" : feeDefault.getMajor().getId());
      // 如果不存在，或后者的 id 比已存在的要大
      if (!feeDefaultMap.containsKey(key) || feeDefault.getId().compareTo(feeDefaultMap.get(key).getId()) > 0) {
        feeDefaultMap.put(key, feeDefault);
      }
    }

    Map<Student, StudentState> currentStateMap = CollectUtils.newHashMap();
    Map<StudentState, FeeDefault> stateFeeDefaultMap = CollectUtils.newHashMap();
    for (Student student : students) {
      // 过滤指定学期已生成缴费记录的学生
      if (feeBySemesterMap.containsKey(student)) {
        continue;
      }
      // 萃取指定学年学期期间的学生学籍状态
      StudentState currentState = null;
      for (StudentState state : student.getStates()) {
        if (state.getBeginOn().before(semester.getEndOn()) && state.getEndOn().after(semester.getBeginOn())) {
          currentState = state;
          break;
        }
      }
      // 寻找可以被匹配到的收费标准配置
      String defaultKey = null;
      for (String key : feeDefaultMap.keySet()) {
        String[] dataInKey = StringUtils.split(key, "_");
        if (currentState.getGrade().compareTo(dataInKey[0]) > 0
            && currentState.getGrade().compareTo(dataInKey[1]) < 0) {
          if (student.getLevel().getId().toString().equals(dataInKey[2])
              && (dataInKey[3].equals("?") || currentState.getDepartment().getId().toString()
                  .equals(dataInKey[3]))
              && (dataInKey[4].equals("?") || currentState.getMajor().getId().toString().equals(dataInKey[4]))) {
            defaultKey = key;
            break;
          }
        }
      }
      // 如果没有找到，就将过滤该学生
      if (StringUtils.isBlank(defaultKey)) {
        continue;
      }
      FeeDefault feeDefault = feeDefaultMap.get(defaultKey);
      Float defaultValue = new Float(feeDefault.getValue());
      Float sum = new Float(sumFeeMap.containsKey(student.getId()) ? (Double) sumFeeMap.get(student.getId())
          : 0);
      if (sum.compareTo(defaultValue) >= 0) {
        continue;
      }
      currentStateMap.put(student, currentState);
      stateFeeDefaultMap.put(currentState, feeDefault);
    }

    if (CollectionUtils.isNotEmpty(students)) {
      Boolean hasFeeDefault = getBoolean("hasFeeDefault");
      if (null == hasFeeDefault) {
        builder1.where(ConditionUtils.splitCollection("student", "students", 900, students));
      } else {
        if (hasFeeDefault) {
          if (currentStateMap.isEmpty()) {
            builder1.where("student is null");
          } else {
            builder1.where(ConditionUtils.splitCollection("student", "students", 900,
                CollectUtils.newArrayList(currentStateMap.keySet())));
          }
        } else {
          builder1.where(ConditionUtils.splitCollection("student", "students", 900,
              CollectUtils.newArrayList(CollectionUtils.subtract(students, currentStateMap.keySet()))));
        }
      }
      builder1.limit(getPageLimit());
      String orderBy = get("orderBy");
      if (StringUtils.isBlank(orderBy)) {
        orderBy = "student.id";
      } else {
        orderBy += ",student.id";
      }
      builder1.orderBy(Order.parse(orderBy));
      students = entityDao.search(builder1);
    }
    sessionInitStudentMap.put(sessionId, new FeeDetailInitStudent(semester, students, stateFeeDefaultMap,
        currentStateMap));
    put("students", students);
    put("feeDefaultMap", feeDefaultMap);
    put("currentStateMap", currentStateMap);
    put("stateFeeDefaultMap", stateFeeDefaultMap);
    return forward();
  }

  public String beforeInitCheckAjax() {
    FeeDetailInitStudent sessionData = sessionInitStudentMap.get(getRequest().getSession().getId());
    if (null == sessionData) {
      put("isOk", false);
    } else {
      Long[] studentIds = getLongIds("student");
      put("isOk",
          ArrayUtils.isEmpty(studentIds) || MapUtils.isNotEmpty(sessionData.getCurrentStateMap(studentIds)));
    }
    return forward();
  }

  public String feeDetailInit() {
    String sessionId = getRequest().getSession().getId();
    FeeDetailInitStudent sessionData = sessionInitStudentMap.get(sessionId);
    if (null == sessionData) {
      return forwardError(new String[] { "在初始化缴费中的学生数据已过期，可能已经在其它界面处理过了！",
          "温馨提示：请不要在多个浏览器、窗口或浏览器的标签页中打开同一个界面或模块，如果出现了请关闭所有浏览器后，重新进入系统。" });
    }
    List<Student> students = sessionData.getStudents(getLongIds("student"));
    Date nowAt = new Date();
    List<FeeDetail> toSaveFeeDetails = CollectUtils.newArrayList();
    for (Student student : students) {
      StudentState currentState = sessionData.getCurrentState(student);
      FeeDefault feeDefault = sessionData.getFeeDefault(currentState);
      FeeDetail feeDetail = new FeeDetail();
      feeDetail.setStd(student);
      feeDetail.setSemester(sessionData.getSemester());
      feeDetail.setDepart(currentState.getDepartment());
      feeDetail.setType(feeDefault.getType());
      // 总额／学制
      feeDetail
          .setShouldPay(BigDecimalUtils.divide(feeDefault.getValue(), Math.ceil(student.getDuration()), Float.class));
      feeDetail.setWhoAdded(getUsername() + "(初始化)");
      feeDetail.setUpdatedAt(nowAt);
      toSaveFeeDetails.add(feeDetail);
    }
    entityDao.saveOrUpdate(toSaveFeeDetails);
    sessionInitStudentMap.remove(sessionId);
    return redirect("index", "初始化成功");
  }

  public String downloadTemplate() {
    DownloadHelper.download(getRequest(), getResponse(), getClass().getResource(get("file")), get("display"));
    return null;
  }

  /**
   * 修改收费信息
   */
  public String edit() {
    Integer feeId = getIntId("feeDetail");
    if (null != feeId) {
      put("feeDetail", entityDao.get(FeeDetail.class, feeId));
    }
    put("semester", getSemester());
    prepare();
    return forward();
  }

  public String loadStdAjax() {
    put("std", studentService.getStudent(getProject().getId(), get("code")));
    return forward();
  }

  /**
   * 保存收费信息
   */
  public String save() {
    FeeDetail feeDetail = populateEntity(FeeDetail.class, "feeDetail");
    feeDetail.setWhoModified(getUsername());
    feeDetail.setUpdatedAt(new java.sql.Date(System.currentTimeMillis()));
    entityDao.saveOrUpdate(feeDetail);
    return redirect("search", "info.save.success");
  }

  /**
   * 删除收费信息
   */
  public String remove() {
    Integer[] feeDetailIds = getIntIds("feeDetail");
    if (ArrayUtils.isEmpty(feeDetailIds)) {
      return forwardError("error.parameters.needed");
    }
    List<FeeDetail> fees = entityDao.get(FeeDetail.class, feeDetailIds);
    String departId = "," + getDepartIdSeq() + ",";
    List<FeeDetail> tobeRemoved = CollectUtils.newArrayList();
    for (FeeDetail fee : fees) {
      if (StringUtils.contains(departId, "," + fee.getDepart().getId() + ","))
        tobeRemoved.add(fee);
    }
    entityDao.remove(tobeRemoved);
    return redirect("search", "info.delete.success");
  }

  private void prepare() {
    put("spans", getStdTypes());
    put("departments", getDeparts());
    put("feeTypes", codeService.getCodes(FeeType.class));
    put("feeModes", codeService.getCodes(FeeMode.class));
    put("currencyTypes", codeService.getCodes(CurrencyCategory.class));
  }

  public String importForm() {
    return forward();
  }

  /**
   * 导入收费信息
   *
   * @return
   */
  protected void configImporter(EntityImporter importer) {
    importer.addListener(new ImporterForeignerListener(entityDao));

    for (TransferListener listener : importerListeners) {
      importer.addListener(listener);
    }
  }

  public void setStudentService(StudentService studentService) {
    this.studentService = studentService;
  }

  public void setImporterListeners(List<TransferListener> importerListeners) {
    this.importerListeners = importerListeners;
  }

  @Override
  protected String getEntityName() {
    return FeeDetail.class.getName();
  }
}
