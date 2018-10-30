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

import org.openurp.base.model.Department;
import org.openurp.edu.base.code.model.EduSpan;
import org.openurp.edu.base.code.model.FeeType;
import org.openurp.edu.base.model.Major;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.fee.model.FeeDefault;

public interface FeeDefaultService {

  /**
   * 首先按照学生的类别和部门进行查找对应的费用<br>
   * 如果没有找到，则放宽条件，只按照学生类别查找<br>
   *
   * @param std
   * @param type
   * @return
   */
  public FeeDefault getFeeDefault(Student std, FeeType type);

  /**
   * 查找收费默认值
   *
   * @param feeType
   * @param eduSpans
   * @param department
   * @param major
   *            可选条件,如果有值则考虑查询范围为(major,null)<br>
   *            否则不考虑该条件
   * @return
   */
  public List<FeeDefault> getFeeDefaults(FeeType feeType, List<EduSpan> eduSpans, Department department,
      Major major);
}
