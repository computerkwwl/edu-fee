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
package org.openurp.edu.fee.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.fee.model.TuitionFee;
import org.openurp.edu.fee.service.TuitionFeeService;

/**
 * @author zhouqi
 */
public class TuitionFeeServiceImpl extends BaseServiceImpl implements TuitionFeeService {

  /**
   * 判断当前学生的交费完成情况
   */
  public boolean isCompleted(Long stdId, Integer semesterId) {
    if (stdId == null) {
      return false;
    }
    Semester semester = entityDao.get(Semester.class, semesterId);
    OqlBuilder<TuitionFee> query = OqlBuilder.from(TuitionFee.class, "tuition");
    query.where("tuition.std.id = (:stdId)", stdId);
    query.where("tuition.semester.schoolYear = (:schoolYear)", semester.getSchoolYear());
    query.where("tuition.completed = true");
    return CollectionUtils.isNotEmpty(entityDao.search(query));
  }

  /**
   * 交费
   */
  public void pay(List<Student> stds, boolean isCompleted, Semester semester) {
    List<TuitionFee> tuitionFees = CollectUtils.newArrayList();
    for (Student std : stds) {
      TuitionFee tuitionFee = new TuitionFee();
      tuitionFee.setSemester(semester);
      tuitionFee.setCompleted(isCompleted);
      tuitionFee.setStd(std);
    }
    entityDao.saveOrUpdate(tuitionFees);
  }
}
