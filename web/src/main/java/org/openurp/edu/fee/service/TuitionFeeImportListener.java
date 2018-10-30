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
package org.openurp.edu.fee.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.transfer.TransferResult;
import org.beangle.commons.transfer.importer.listener.ItemImporterListener;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.service.SemesterService;
import org.openurp.edu.fee.model.TuitionFee;

public class TuitionFeeImportListener extends ItemImporterListener {

  private EntityDao entityDao;

  private SemesterService semesterService;

  public TuitionFeeImportListener() {
    super();
  }

  public void onItemFinish(TransferResult tr) {
    Map<String, Object> dataMap = importer.getCurData();
    TuitionFee tuitionFee = (TuitionFee) importer.getCurrent();
    int errors = tr.errors();
    if (tuitionFee.getStd() == null) {
      tr.addFailure("std.error.notExist", dataMap.get("std.code"));
    }

    if (tuitionFee.getSemester() == null || tuitionFee.getSemester().getId() == null) {
      Semester semester = semesterService.getSemester(tuitionFee.getStd().getProject(),
          (String) dataMap.get("semester.schoolYear"), (String) dataMap.get("semester.name"));
      if (null != semester) {
        tuitionFee.setSemester(semester);
      } else {
        tr.addFailure("error.calendar", dataMap.get("semester.code"));
      }
    }

    if (tr.errors() < errors) {
      String completed = (String) dataMap.get("completed");
      if (!Arrays.asList("是", "否").contains(completed)) {
        tuitionFee.setCompleted(StringUtils.equals("是", completed));
      } else {
        tr.addFailure("是否缴费错误", completed);
      }
      entityDao.saveOrUpdate(tuitionFee);
    }
  }

  public void onStartItem(TransferResult tr) {
    // 判断是否要导入的对象已经存在
    Map<String, Object> dataMap = importer.getCurData();
    String stdCode = (String) dataMap.get("std.code");
    String semesterCode = (String) dataMap.get("semester.code");
    if (StringUtils.isNotBlank(stdCode) && StringUtils.isNotBlank(semesterCode)) {
      OqlBuilder<TuitionFee> builder = OqlBuilder.from(TuitionFee.class, "fee");
      builder.where("fee.std.code = :stdCode", stdCode);
      builder.where("fee.semester.code = :semesterCode", semesterCode);
      List<TuitionFee> fees = entityDao.search(builder);
      if (CollectionUtils.isNotEmpty(fees)) {
        importer.setCurrent(fees.get(0));
      }
    }
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  public void setSemesterService(SemesterService semesterService) {
    this.semesterService = semesterService;
  }
}
