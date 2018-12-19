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

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.query.builder.Condition;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.struts2.helper.Params;
import org.beangle.struts2.helper.QueryHelper;
import org.openurp.code.edu.model.EducationLevel;
import org.openurp.edu.base.code.model.FeeType;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.fee.model.FeeDetail;
import org.openurp.edu.fee.service.FeeDetailService;
import org.openurp.edu.web.action.SemesterSupportAction;

/**
 * 收费查询
 *
 * @author chaostone
 */
public class FeeSearchAction extends SemesterSupportAction {

  protected FeeDetailService feeDetailService;

  protected void indexSetting() {
    put("levels", getLevels());
    put("feeTypes", codeService.getCodes(FeeType.class));
  }

  /**
   * 查询
   *
   * @return
   */
  public String search() {
    if (getDeparts().isEmpty() || getStdTypes().isEmpty()) {
      return forwardError("对不起，您没有权限！");
    }
    List<FeeDetail> fees = entityDao.search(buildQuery());
    put("fees", fees);

    paramsInSearch(fees);

    return forward();
  }

  protected void paramsInSearch(List<FeeDetail> fees) {
    ;
  }

  protected OqlBuilder<FeeDetail> buildQuery() {
    OqlBuilder<FeeDetail> builder = OqlBuilder.from(FeeDetail.class, "feeDetail");
    populateConditions(builder, "feeDetail.std.stdType.id,feeDetail.semester.studentType.id");
    builder.where("feeDetail.std.project in (:project)", getProject());
    builder.where("feeDetail.std.state.department in (:departs)", getDeparts());
    List<EducationLevel> levels = getLevels();
    if (CollectUtils.isNotEmpty(levels)) {
      builder.where("feeDetail.std.level in (:levels)", levels);
    }
    List<Condition> conditions = QueryHelper.extractConditions(Student.class, "std", null);
    String className = get("className");
    if (CollectionUtils.isNotEmpty(conditions)) {
      builder.join(builder.getAlias() + ".std", "std");
      builder.where(conditions);
    }
    if (StringUtils.isNotBlank(className)) {
      builder.where("std.state.squad.name = :squadName", className);
    }
    builder.limit(getPageLimit());
    String orderBy = get("orderBy");
    if (StringUtils.isBlank(orderBy)) {
      orderBy = "feeDetail.id";
    } else {
      orderBy += ",feeDetail.id";
    }
    builder.orderBy(Order.parse(orderBy));
    return builder;
  }

  public String info() {
    put("feeDetail", entityDao.get(FeeDetail.class, getIntId("feeDetail")));
    return forward();
  }

  protected Collection<?> getExportDatas() {
    Integer[] ids = Params.getAll("ids", Integer.class);
    if (ArrayUtils.isEmpty(ids)) {
      return entityDao.search(buildQuery().limit(null));
    } else {
      return entityDao.get(FeeDetail.class, ids);
    }
  }

  public void setFeeDetailService(FeeDetailService feeDetailService) {
    this.feeDetailService = feeDetailService;
  }
}
