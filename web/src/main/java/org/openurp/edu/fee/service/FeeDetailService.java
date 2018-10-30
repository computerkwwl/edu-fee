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

import java.util.List;

import org.beangle.commons.collection.page.Page;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.edu.base.code.model.FeeType;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.course.model.CourseTaker;
import org.openurp.edu.fee.model.FeeDetail;

public interface FeeDetailService {

  public List<FeeDetail> getFeeDetails(FeeDetail feeDetail);

  /**
   * 根据查询条件 以及部门ids得到一个分页的对象
   *
   * @param studentId
   * @param studentName
   * @param semester
   * @return
   */
  public Page<FeeDetail> getFeeDetails(FeeDetail feeDetail, String departIdSeq, String orderByName,
      int pageNo, int pageSize);

  /**
   * 根据查询条件得到的收费信息的信息.
   *
   * @param feeDetail
   * @param departIdSeq
   * @param orderByName
   *
   * @return
   */
  public List<FeeDetail> getFeeDetails(FeeDetail feeDetail, String departIdSeq, String orderByName);

  /**
   * 得到学生的选课情况,条件是:部门Ids和教学日历对象
   *
   * @param departmentIds
   * @param teachCalenar
   * @return
   */
  public OqlBuilder<CourseTaker> getStdSelectCourses(String departmentIds, Student std, Semester semester);

  /**
   * 为学生定制消息
   *
   * @param messagekeyId
   * @param title
   * @param body
   * @param studentIds
   */
  public void saveMessageBystudentNos(String title, String body, String studentIds);

  /**
   * 根据问题类型列表，收费的查询条件，以及排序条件得到相应的排序名称得到分页结果
   *
   * @param feeTypeList
   * @param feeDetailInfo
   * @param pageNo
   * @param pageSize
   * @return
   */
  public List<Object[]> getFeeDetailsOfTypes(FeeDetail feeDetail, List<FeeType> feeTypeList,
      String orderbyName, int pageNo, int pageSize);

  /**
   * 查找某个学生在指定学期，指定收费项目的全部收费纪录.<br>
   * 如果不指定教学日历，则查找全部学期的收费项目.<br>
   * 如果不指定收费项目，则查找全部的收费项目.<br>
   *
   * @param std
   * @param semester
   * @param type
   * @return
   */
  public List<FeeDetail> getFeeDetails(Student std, Semester semester, FeeType type);

  /**
   * 查询学生在指定学期应缴的费用<br>
   * Float[0]按照默认值应缴交付金额<br>
   * Float[1]按照既往缴费的应缴之和<br>
   * Float[1]按照既往缴费的实缴之和<br>
   *
   * @param std
   * @param semester
   * @param type
   * @return
   */
  public Float[] statFeeFor(Student std, Semester semester, FeeType type);
}
