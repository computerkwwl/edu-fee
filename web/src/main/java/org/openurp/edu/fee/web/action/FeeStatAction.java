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
package org.openurp.edu.fee.web.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.openurp.edu.base.code.model.FeeType;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.fee.data.FeeTypeStat;
import org.openurp.edu.fee.model.FeeDetail;
import org.openurp.edu.fee.model.stat.CreditFeeStat;
import org.openurp.edu.fee.utils.EntityUtils;
import org.openurp.edu.web.action.SemesterSupportAction;

/**
 * 收费统计
 *
 * @author chaostone
 *
 */
public class FeeStatAction extends SemesterSupportAction {

  /**
   * @return
   */
  public String statFeeType() {
    List<?> accounts = entityDao.search(buildQuery());
    fillStudentAndFeeType((List<FeeTypeStat>) accounts);
    put("accounts", accounts);
    return forward();
  }

  /**
   * 统计学费与学分不符的记录
   *
   * @return
   */
  public String creditFeeStats() {
    put("creditFeeStats", entityDao.search(buildQuery()));
    return forward();
  }

  /**
   * 获得“应-实 缴费不符统计”的查询条件
   *
   * @return
   */
  protected OqlBuilder<FeeDetail> buildStatFeeTypeQuery() {
    OqlBuilder<FeeDetail> builder = OqlBuilder.from(FeeDetail.class, "feeDetail");
    populateConditions(builder);
    if (CollectUtils.isNotEmpty(getStdTypes())) {
      builder.where("feeDetail.std.stdType in (:stdTypes)", getStdTypes());
    }
    builder.where("feeDetail.semester.id = :semesterId", getIntId("semester"));
    builder.groupBy("feeDetail.std.id, feeDetail.type.id");
    builder.having("sum(feeDetail.shouldPay) <> sum(feeDetail.payed)");
    builder.select("new " + FeeTypeStat.class.getName()
        + "(feeDetail.std.id, feeDetail.type.id, sum(feeDetail.shouldPay), sum(feeDetail.payed))");
    builder.limit(getPageLimit());
    builder.orderBy(Order.parse(get("orderBy")));
    return builder;
  }

  /**
   * 获得“学费－学分 不符统计”的查询条件
   *
   * @return
   */
  protected OqlBuilder<CreditFeeStat> buildCreditFeeQuery() {
    OqlBuilder<CreditFeeStat> builder = OqlBuilder.from(CreditFeeStat.class, "creditFeeStat");
    populateConditions(builder);
    builder.where("feeDetail.std.project=:project", getProject());
    builder.where("feeDetail.std.state.department in (:departments)", getDeparts());
    if (CollectUtils.isNotEmpty(getStdTypes())) {
      builder.where("feeDetail.std.stdType in (:stdTypes)", getStdTypes());
    }
    if (CollectUtils.isNotEmpty(getSpans())) {
      builder.where("feeDetail.std.span in (:spans)", getSpans());
    }
    builder.limit(getPageLimit());
    builder.orderBy(Order.parse(get("orderBy")));
    return builder;
  }

  /**
   * 根据indexPage选择查询条件收集的方法
   *
   * @return
   */
  protected <T extends Entity<?>> OqlBuilder<T> buildQuery() {
    if (StringUtils.isEmpty(get("indexPage")) || get("indexPage").equals("statFeeType")) {
      return (OqlBuilder<T>) buildStatFeeTypeQuery();
    } else {
      return (OqlBuilder<T>) buildCreditFeeQuery();
    }
  }

  /**
   * 组建FeeTypeStat对象
   *
   * @param accounts
   */
  private void fillStudentAndFeeType(List<FeeTypeStat> accounts) {
    List<Student> stds = CollectUtils.newArrayList();
    List<FeeType> feeTypes = CollectUtils.newArrayList();
    for (FeeTypeStat stat : accounts) {
      stds.add(stat.getStd());
      feeTypes.add(stat.getType());
    }
    Map<Long, Student> stdMap = EntityUtils.instance(entityDao).queryToMap(Student.class, stds);
    Map<Integer, FeeType> feeTypeMap = EntityUtils.instance(entityDao).queryToMap(FeeType.class, feeTypes);
    for (FeeTypeStat stat : accounts) {
      stat.setStd(stdMap.get(stat.getStd().getId()));
      stat.setType(feeTypeMap.get(stat.getType().getId()));
    }
  }
}
