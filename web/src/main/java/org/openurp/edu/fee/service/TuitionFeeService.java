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
package org.openurp.edu.fee.service;

import java.util.List;

import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Student;

/**
 * @author zhouqi
 */
public interface TuitionFeeService {

  /**
   * 判断是否交费完成
   *
   * @param stdId
   * @param semesterId
   * @return
   */
  public boolean isCompleted(Long stdId, Integer semesterId);

  /**
   * 交费
   *
   * @param stds学生
   * @param completed
   *            是否完成
   * @param semester
   *            学期
   */
  public void pay(List<Student> stds, boolean completed, Semester semester);
}
