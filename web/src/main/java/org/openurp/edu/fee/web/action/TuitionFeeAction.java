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

import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.transfer.TransferListener;
import org.beangle.commons.transfer.importer.EntityImporter;
import org.beangle.commons.transfer.importer.listener.ImporterForeignerListener;
import org.openurp.base.model.Department;
import org.openurp.edu.base.model.Major;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.base.service.SemesterService;
import org.openurp.edu.fee.model.TuitionFee;
import org.openurp.edu.fee.service.TuitionFeeService;
import org.openurp.edu.web.action.SemesterSupportAction;

public class TuitionFeeAction extends SemesterSupportAction {

  protected SemesterService semesterService;

  protected TuitionFeeService tuitionFeeService;

  protected List<TransferListener> importerListeners;

  protected void indexSetting() {
    put("semesters", entityDao.get(Semester.class, "calender", semesterService.getCalendar(getProject())));

    put("departments",
        entityDao.search(OqlBuilder.from(Department.class, "department").where("department.perant is null")
            .where("department.teaching = true")));// FIXME 2018-10-19 zhouqi 原 collect
    put("majors",
        entityDao.search(OqlBuilder.from(Major.class, "major").where(
            "between major.beginOn and major.engOn or major.endOn is null")));
  }

  public String search() {
    OqlBuilder<TuitionFee> builder = OqlBuilder.from(TuitionFee.class, "tuitionFee");
    populateConditions(builder);
    builder.limit(getPageLimit());
    builder.orderBy(Order.parse(get("orderBy")));
    put("tuitionFees", search(builder));
    return forward();
  }

  /**
   * 无缴费数据学生
   *
   * @return
   */
  public String unTuitionFeeList() {
    OqlBuilder<Student> builder = OqlBuilder.from(Student.class, "std");
    populateConditions(builder);
    builder.where("not exists( from " + TuitionFee.class.getName()
        + " tuitionFee where tuitionFee.std = std and tuitionFee.semester.id = :semesterId)",
        getInt("tuitionFee.semester.id"));
    builder.limit(getPageLimit());
    builder.orderBy(Order.parse(get("orderBy")));
    put("students", entityDao.search(builder));
    return forward();
  }

  /**
   * 缴费
   *
   * @return
   */
  public String addFee() {
    Semester semester = semesterService.getSemester(getInt("tuitionFee.semester.id"));
    List<Student> stds = entityDao.get(Student.class, getLongIds("std"));
    tuitionFeeService.pay(stds, getBool("completed"), semester);
    return redirect("unTuitionFeeList", "info.action.success");
  }

  protected void configImporter(EntityImporter importer) {
    importer.addListener(new ImporterForeignerListener(entityDao));

    for (TransferListener listener : importerListeners) {
      importer.addListener(listener);
    }
  }

  public void setSemesterService(SemesterService semesterService) {
    this.semesterService = semesterService;
  }

  public void setTuitionFeeService(TuitionFeeService tuitionFeeService) {
    this.tuitionFeeService = tuitionFeeService;
  }

  public void setImporterListeners(List<TransferListener> importerListeners) {
    this.importerListeners = importerListeners;
  }
}
