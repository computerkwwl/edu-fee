/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright (c) 2005, The OpenURP Software.
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
package org.openurp.edu.fee.service.impl;

import java.util.Collections;
import java.util.List;

import org.beangle.commons.collection.Order;
import org.beangle.commons.collection.page.Page;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.struts2.helper.Params;
import org.openurp.edu.base.code.model.FeeType;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.course.model.CourseTaker;
import org.openurp.edu.fee.model.FeeDefault;
import org.openurp.edu.fee.model.FeeDetail;
import org.openurp.edu.fee.service.FeeDefaultService;
import org.openurp.edu.fee.service.FeeDetailService;

/**
 * @author Administrator
 *
 */
public class FeeDetailServiceImpl extends BaseServiceImpl implements FeeDetailService {

  private FeeDefaultService feeDefaultService;

  public List<FeeDetail> getFeeDetails(FeeDetail feeDetail) {
    if (null == feeDetail) {
      return Collections.EMPTY_LIST;
    } else {
      return entityDao.search(OqlBuilder.from(FeeDetail.class, "feeDetail").orderBy(
          Order.parse("semester.beginOn desc")));
    }
  }

  public List<FeeDetail> getFeeDetails(Student std, Semester semester, FeeType type) {
    FeeDetail feeDetail = new FeeDetail();
    feeDetail.setStd(std);
    feeDetail.setSemester(semester);
    feeDetail.setType(type);
    return getFeeDetails(feeDetail);
  }

  public Float[] statFeeFor(Student std, Semester semester, FeeType type) {
    Float[] values = new Float[3];
    List<FeeDetail> fees = getFeeDetails(std, semester, type);
    FeeDefault defaultValue = feeDefaultService.getFeeDefault(std, type);
    values[0] = (null == defaultValue) ? new Float(0) : new Float(defaultValue.getValue().intValue());

    float shouldPay = 0;
    float payed = 0;
    for (FeeDetail feeDetail : fees) {
      shouldPay += (null == feeDetail.getShouldPay()) ? 0 : feeDetail.getShouldPay().floatValue();
      payed += (null == feeDetail.getToRMB()) ? 0 : feeDetail.getToRMB().floatValue();
    }
    values[1] = new Float(shouldPay);
    values[2] = new Float(payed);
    return values;

  }

  /*
   * (non-Javadoc)
   * @see org.openurp.edu.fee.service.FeeDetailService#getStdSelectCourses(java.lang.String,
   * org.openurp.edu.base.model.Student, org.openurp.edu.base.model.Semester)
   */
  public OqlBuilder<CourseTaker> getStdSelectCourses(String departmentIds, Student std, Semester semester) {
    OqlBuilder<CourseTaker> builder = OqlBuilder.from(CourseTaker.class, "taker");
    builder.where("taker.clazz.teachDepart.id in (:teachDepartIds)",
        Params.converter.convert(departmentIds, Integer.class));
    builder.where("taker.std in (:std)", std);
    builder.where("taker.clazz.semester in (:semester)", semester);
    builder.orderBy("taker.std.id");
    return builder;
  }

  /**
   * 发送消息
   *
   * @see org.openurp.edu.service.fee.FeeDetailService#saveMessageBystudentNos(java.lang.String, java.lang.String, java.lang.String)
   */
  public void saveMessageBystudentNos(String title, String body, String studentIds) {
    //    Long[] studentId = SeqStringUtil.transformToLong(studentIds);
    //    String[] string = new String[10];
    // messageService.saveStudentMessage(title, body, string);
  }

  public List<Object[]> getFeeDetailsOfTypes(FeeDetail feeDetail, List<FeeType> feeTypeList,
      String orderByName, int pageNo, int pageSize) {
    // FIXME 2018-10-22 zhouqi 暂时先屏蔽报错，等使用到时再开启修复
    //    OqlBuilder<Object[]> builder = OqlBuilder.from(FeeDetail.class.getName() + " fee");
    //    BeanMap map = new BeanMap(feeDetail);
    //    for (Object key : map.keySet()) {
    //      ;
    //    }
    //    builder.where("fee");

    //    Criteria criteria = getSession().createCriteria(FeeDetail.class);
    //    List criterions = CriterionUtils.getEntityCriterions(feeDetail);
    //    for (Iterator iter = criterions.iterator(); iter.hasNext();)
    //      criteria.add((Criterion) iter.next());
    //
    //    String sqlstr = "";
    //    String[] alainColumn = new String[feeTypeList.size()];
    //    Type[] types = new Type[feeTypeList.size()];
    //    String groupString = "XH";
    //    for (int i = 0; i < feeTypeList.size(); i++) {
    //      FeeType feeType = (FeeType) feeTypeList.get(i);
    //      sqlstr += "sum(case when SFLXID = " + feeType.getId() + " then RMB else 0 end) as type" + i + "_,";
    //      alainColumn[i] = "type" + i + "_";
    //      types[i] = Hibernate.FLOAT;
    //    }
    //    if (sqlstr.length() > 0) {
    //      sqlstr = sqlstr.substring(0, sqlstr.length() - 1);
    //    }
    //    if (StringUtils.isNotEmpty(orderByName)) {
    //      groupString = groupString + "," + orderByName;
    //    }
    //    ProjectionList projectionList = Projections.projectionList();
    //    if (sqlstr.length() > 0) {
    //      projectionList.add(Projections.property("student"));
    //      projectionList.add(Projections.sqlGroupProjection(sqlstr, groupString, alainColumn, types));
    //    } else {
    //      projectionList.add(Projections.groupProperty("student"));
    //    }
    //    projectionList.add(Projections.groupProperty("schoolYear")).add(Projections.groupProperty("name"));
    //    criteria.setProjection(projectionList);
    //    if (StringUtils.isNotEmpty(orderByName)) {
    //      String[] orderName = orderByName.split(",");
    //      for (int i = 0; i < orderName.length; i++) {
    //        criteria.addOrder(Order.asc(orderName[i]));
    //      }
    //    }
    //    criteria.addOrder(Order.desc("schoolYear")).addOrder(Order.asc("name"));
    return null;
  }

  public Page<FeeDetail> getFeeDetails(FeeDetail feeDetail, String departIdSeq, String orderbyName,
      int pageNo, int pageSize) {
    // FIXME 2018-10-22 zhouqi 暂时先屏蔽报错，等使用到时再开启修复
    //    return dynaSearch(getFeeDetailCriteria(feeDetail, departIdSeq, orderbyName), pageNo, pageSize);
    return null;
  }

  public List<FeeDetail> getFeeDetails(FeeDetail feeDetail, String departIdSeq, String orderByName) {
    // FIXME 2018-10-22 zhouqi 暂时先屏蔽报错，等使用到时再开启修复
    //    Criteria criteria = getSession().createCriteria(FeeDetail.class);
    //    // 添加学生条件
    //    List stdCriterions = null;
    //    if (null != feeDetail.getStd()) {
    //      stdCriterions = CriterionUtils.getEntityCriterions("std.", feeDetail.getStd());
    //      if (!stdCriterions.isEmpty()) {
    //        Criteria stdCriteria = criteria.createCriteria("std", "std");
    //        for (Iterator iter = stdCriterions.iterator(); iter.hasNext();) {
    //          Criterion criterion = (Criterion) iter.next();
    //          stdCriteria.add(criterion);
    //        }
    //      }
    //    }
    //    List criterions = CriterionUtils.getEntityCriterions(feeDetail, new String[] { "std" });
    //    for (Iterator iter = criterions.iterator(); iter.hasNext();)
    //      criteria.add((Criterion) iter.next());
    //
    //    Long[] departmentId = SeqStringUtil.transformToLong(departIdSeq);
    //    criteria.add(Restrictions.in("depart.id", departmentId));
    //    if (StringUtils.isNotEmpty(orderByName)) {
    //      String[] orderName = StringUtils.split(orderByName, ",");
    //      Set createdAlias = new HashSet();
    //      if (null != stdCriterions) {
    //        createdAlias.add("std");
    //      }
    //      for (int i = 0; i < orderName.length; i++) {
    //        if (orderName[i].indexOf(".") > 0) {
    //          String alias = orderName[i].substring(0, orderName[i].indexOf("."));
    //          if (!createdAlias.contains(alias)) {
    //            createdAlias.add(alias);
    //            criteria.createAlias(alias, alias);
    //          }
    //        }
    //        if (orderName[i].indexOf(" ") != -1) {
    //          if (orderName[i].substring(orderName[i].indexOf(" ") + 1).equals("desc"))
    //            criteria.addOrder(Order.desc(orderName[i].substring(0, orderName[i].indexOf(" "))));
    //          else criteria.addOrder(Order.asc(orderName[i].substring(0, orderName[i].indexOf(" "))));
    //        } else {
    //          criteria.addOrder(Order.asc(orderName[i]));
    //        }
    //      }
    //    }
    //    return criteria;
    return null;
  }

  /**
   * @param feeDefaultService
   *            The feeDefaultService to set.
   */
  public void setFeeDefaultService(FeeDefaultService stdAndFeeTypeDefaultService) {
    this.feeDefaultService = stdAndFeeTypeDefaultService;
  }
}
