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
package org.openurp.edu.fee.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.transfer.TransferResult;
import org.beangle.commons.transfer.importer.listener.ItemImporterListener;
import org.openurp.base.model.Department;
import org.openurp.edu.base.code.model.FeeType;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.base.model.StudentState;
import org.openurp.edu.base.service.SemesterService;
import org.openurp.edu.fee.model.FeeDetail;
import org.openurp.edu.fee.utils.StudentUtils;
import org.openurp.edu.web.util.ImporterListenerUtils;

/**
 * 一次性导入excel中的所有收费信息
 *
 * @author chaostone
 */
public class FeeDetailImportListener extends ItemImporterListener {

  private EntityDao entityDao;

  private SemesterService semesterService;

  private FeeDetailService feeDetailService;

  private List<FeeDetail> feeDetails;

  private Map<String, Semester> semesterMap;

  private Map<String, Student> studentMap;

  private Map<String, Department> departmentMap;

  private Map<String, FeeType> feeTypeMap;

  private Map<Student, StudentState> studentStateMap;

  private Date nowAt;

  public FeeDetailImportListener() {
    super();
    init();
  }

  private void init() {
    feeDetails = CollectUtils.newArrayList();
    semesterMap = CollectUtils.newHashMap();
    studentMap = CollectUtils.newHashMap();
    departmentMap = CollectUtils.newHashMap();
    feeTypeMap = CollectUtils.newHashMap();
    studentStateMap = CollectUtils.newHashMap();
    nowAt = new Date();
  }

  public void onStart(TransferResult tr) {
    init();
  }

  // FIXME 暂时不做了 ---- 先别删除下面注释掉的语句
  public void onItemStart(TransferResult tr) {
    // 学生
    Student student = ImporterListenerUtils.checkAndConvertObject(entityDao, tr, importer, Student.class,
        "std.user.code", false, studentMap);

    // 收费部门（默认为学生所在院系，因此允许为空）
    ImporterListenerUtils.checkAndConvertObject(entityDao, tr, importer, Department.class, "depart.code",
        true, departmentMap);

    // 学年学期
    if (null != student) {
      String schoolYear = ImporterListenerUtils.checkIsEmpty(tr, importer, String.class,
          "semester.schoolYear", false, false, null, null);
      String term = ImporterListenerUtils.checkIsEmpty(tr, importer, String.class, "semester.name", false,
          false, null, null);
      String key = student.getProject().getCode() + "_" + schoolYear + "_" + term;
      Semester semester = semesterMap.get(key);
      if (null == semester) {
        semester = semesterService.getSemester(student.getProject(), schoolYear, term);
        if (null == semester) {
          tr.addFailure("No found semester!",
              "<b>semester.schoolYear</b>=" + StringUtils.defaultString(schoolYear)
                  + ", <b>semester.name</b>=" + StringUtils.defaultString(term));
        } else {
          semesterMap.put(key, semester);
          importer.getCurData().put("semester", semester);
        }
      }
      if (null != semester) {
        StudentState studentState = studentStateMap.get(student);
        if (null == studentState) {
          List<StudentState> studentStates = StudentUtils.getStudentStates(student, semester);
          if (CollectionUtils.isEmpty(studentStates) || studentStates.size() != 1) {
            tr.addFailure("本学生在指定学年学期没有找到所在的学籍状态信息。", "<b>std.user.code</b>=" + student.getCode()
                + "<b>semester.schoolYear</b>=" + StringUtils.defaultString(schoolYear)
                + ", <b>semester.name</b>=" + StringUtils.defaultString(term));
          } else {
            FeeDetail feeDetail = feeDetailService.getFeeDetail(student, semester);
            if (null != feeDetail) {
              importer.getCurData().put("feeDetail", feeDetail);
            }
            studentStateMap.put(student, studentStates.get(0));
          }
        }
      }
    }

    // 应缴费用
    ImporterListenerUtils.checkIsEmpty(tr, importer, Float.class, "shouldPay", true, true, "0.00", null);

    // 交费类型
    ImporterListenerUtils.checkAndConvertObject(entityDao, tr, importer, FeeType.class, "type.code", false,
        feeTypeMap);

    // 实缴金额
    Float payed = ImporterListenerUtils.checkIsEmpty(tr, importer, Float.class, "payed", true, true, "0.00",
        null);

    // 终缴日期：如果实缴金额不为空，则本字段为必填，不然忽略
    if (null != payed) {
      ImporterListenerUtils.checkIsEmpty(tr, importer, Float.class, "payedAt", false, true, "0.00", null);
    }

    super.onItemStart(tr);
  }

  public void onFinish(TransferResult tr) {
    // 若是无错，则保存或更新
    if (!tr.hasErrors()) {
      entityDao.saveOrUpdate(feeDetails);
    }
  }

  public void onItemFinish(TransferResult tr) {
    if (!tr.hasErrors()) {
      FeeDetail feeDetail = ImporterListenerUtils.getValue(importer, "feeDetail");
      if (null == feeDetail) {
        feeDetail = (FeeDetail) importer.getCurrent();
      }
      if (null == feeDetail.getDepart()) {
        feeDetail.setDepart(studentStateMap.get(feeDetail.getStd()).getDepartment());
      }
      feeDetail.setType(ImporterListenerUtils.getValue(importer, "type"));
      // 如果实缴为空，则下面这些字段忽略
      if (null == feeDetail.getPayed()) {
        feeDetail.setPayedAt(null);
        feeDetail.setWhoAdded(null);
      }
      feeDetail.setUpdatedAt(nowAt);
      feeDetails.add(feeDetail);
    }
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  public void setSemesterService(SemesterService semesterService) {
    this.semesterService = semesterService;
  }

  public void setFeeDetailService(FeeDetailService feeDetailService) {
    this.feeDetailService = feeDetailService;
  }
}
