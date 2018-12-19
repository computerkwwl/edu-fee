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

import java.util.List;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.functor.NotZeroNumberPredicate;
import org.openurp.base.model.User;
import org.openurp.edu.base.code.model.FeeType;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.base.service.StudentService;
import org.openurp.edu.eams.security.EamsUserCategory;
import org.openurp.edu.fee.model.FeeDetail;
import org.openurp.edu.fee.service.FeeDetailService;
import org.openurp.edu.web.action.RestrictionSupportAction;

/**
 * 学生查询自己费用
 *
 * @author chaostone
 *
 */
public class FeeOfStdAction extends RestrictionSupportAction {

  private FeeDetailService feeDetailService;

  private StudentService studentService;

  public String index() {
    User user = getLoginUser();
    if (null == user) {
      return forwardError("");
    } else {
      if (!user.isCategory(EamsUserCategory.STD_USER))
        return forwardError("");
    }
    Student std = studentService.getStudent(getProject().getId(), user.getName());
    if (null == std) {
      return forwardError("");
    }
    Integer feeTypeId = getIntId("feeType");
    FeeType type = null;
    Integer semesterId = getIntId("semester");
    Semester semester = null;
    if (NotZeroNumberPredicate.getInstance().apply(feeTypeId)) {
      type = entityDao.get(FeeType.class, feeTypeId);
      put("conditionFeeType", type);
    }
    if (NotZeroNumberPredicate.getInstance().apply(semesterId)) {
      semester = entityDao.get(Semester.class, semesterId);
      put("conditionSemester", semester);
    }
    // FIXME 2018-12-19 zhouqi 下面代码需要调整
//    List<FeeDetail> fees = feeDetailService.getFeeDetails(std, semester, type);
//    Set<FeeType> feeTypes = CollectUtils.newHashSet();
//    Set<Semester> semesters = CollectUtils.newHashSet();
//    for (FeeDetail feeDetail : fees) {
//      feeTypes.add(feeDetail.getType());
//      semesters.add(feeDetail.getSemester());
//    }
//    put("fees", fees);
//    put("feeTypes", feeTypes);
//    put("semesters", semesters);
    return forward();
  }

  /**
   * @param studentService
   *            The studentService to set.
   */
  public void setStudentService(StudentService studentService) {
    this.studentService = studentService;
  }

  public void setFeeDetailService(FeeDetailService feeDetailService) {
    this.feeDetailService = feeDetailService;
  }
}
