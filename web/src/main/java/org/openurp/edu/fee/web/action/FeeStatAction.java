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

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.model.Department;
import org.openurp.code.edu.model.EducationLevel;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.fee.data.FeeStat1;
import org.openurp.edu.fee.model.FeeDetail;
import org.openurp.edu.web.action.SemesterSupportAction;

/**
 * 收费统计
 *
 * @author chaostone
 */
public class FeeStatAction extends SemesterSupportAction {

  protected void indexSetting() {
    List<Semester> semesters = entityDao.get(Semester.class, "calendar", getProject().getCalendars());

    Map<String, List<Semester>> semesterMap = CollectUtils.newHashMap();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    for (Semester semester : semesters) {
      String year = sdf.format(semester.getBeginOn());
      if (!semesterMap.containsKey(year)) {
        semesterMap.put(year, CollectUtils.newArrayList());
      }
      semesterMap.get(year).add(semester);
    }
    put("semesterMap", semesterMap);
  }

  /**
   * “学费收缴情况说明”统计
   *
   * @return
   */
  public String stat1() {
    List<Department> departments = getDeparts();

    Map<Number, Department> departmentMap = CollectUtils.newHashMap();

    for (Department department : departments) {
      departmentMap.put(department.getId(), department);
    }

    OqlBuilder<Object[]> builder = OqlBuilder.from(FeeDetail.class.getName() + " feeDetail");
    builder.where("feeDetail.semester.id in (:semesterId)", getIntIds("semester"));
    builder.groupBy("feeDetail.std.state.department.id");
    builder
        .select("feeDetail.std.state.department.id,sum(case when feeDetail.payed is not null and feeDetail.payed > 0 then 1 else 0 end),sum(case when feeDetail.payed is not null or feeDetail.payed > 0 then feeDetail.payed else 0 end),count(*)");
    List<Object[]> stats = entityDao.search(builder);
    List<FeeStat1> results = CollectUtils.newArrayList();
    for (Object[] data : stats) {
      results.add(new FeeStat1(departmentMap.get(data[0]), (Number) data[1], (Number) data[2],
          (Number) data[3]));
    }
    put("results", results);
    return forward();
  }

  public String detail1() {
    OqlBuilder<FeeDetail> builder = OqlBuilder.from(FeeDetail.class, "feeDetail");
    builder.where("feeDetail.std.project in (:project)", getProject());
    builder.where("feeDetail.std.state.department in (:departs)", getDeparts());
    List<EducationLevel> spans = getLevels();
    if (CollectUtils.isNotEmpty(spans)) {
      builder.where("feeDetail.std.span in (:spans)", spans);
    }
    builder.where("feeDetail.std.state.department.id = (:departmentId)", getIntId("department"));
    builder.where("feeDetail.semester.id in (:semesterIds)", getIntIds("semester"));
    builder.where("feeDetail.payed is null or feeDetail.payed = 0");
    String orderBy = get("orderBy");
    if (StringUtils.isBlank(orderBy)) {
      orderBy = "feeDetail.id";
    } else {
      orderBy += ",feeDetail.id";
    }
    builder.orderBy(Order.parse(orderBy));
    put("feeDetails", entityDao.search(builder));
    return forward();
  }
}
