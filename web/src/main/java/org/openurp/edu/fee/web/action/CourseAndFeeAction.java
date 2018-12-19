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

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.course.model.CourseTaker;
import org.openurp.edu.fee.model.FeeDetail;
import org.openurp.edu.web.action.SemesterSupportAction;

/**
 * 学生选课和收费信息
 *
 * @author chaostone
 */
public class CourseAndFeeAction extends SemesterSupportAction {

  /**
   * 查询学生
   *
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return @
   */
  public String search() {
    OqlBuilder<Student> builder = OqlBuilder.from(Student.class, "student");
    populateConditions(builder);
    builder.where("student.project=:project", getProject());
    builder.where("student.state.department in (:departments)", getDeparts());
    if (CollectUtils.isNotEmpty(getStdTypes())) {
      builder.where("student.stdType in (:stdTypes)", getStdTypes());
    }
    if (CollectUtils.isNotEmpty(getLevels())) {
      builder.where("student.level in (:levels)", getLevels());
    }
    builder.limit(getPageLimit());
    builder.orderBy(Order.parse(get("orderBy")));
    put("stdUsers", entityDao.search(builder));
    return forward();
  }

  /**
   * 查询学生
   *
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return @
   */
  public String info() {
    Integer semesterId = getIntId("semester");
    Long stdId = getLongId("std");
    OqlBuilder<?> builder = OqlBuilder.from(CourseTaker.class, "take");
    builder.where("take.std.id=:stdId", stdId);
    builder.where("take.clazz.semester.id=:semesterId", semesterId);
    put("courseTakes", entityDao.search(builder));

    builder = OqlBuilder.from(FeeDetail.class, "fee");
    builder.where("fee.std.id=:stdId", stdId);
    builder.where("fee.semester.id=:semesterId", semesterId);
    put("fees", entityDao.search(builder));
    put("std", entityDao.get(Student.class, stdId));
    return forward();
  }
}
