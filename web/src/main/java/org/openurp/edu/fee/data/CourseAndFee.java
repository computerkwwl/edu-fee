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
package org.openurp.edu.fee.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class CourseAndFee {

  /** 学号 */
  private String stdCode;

  /** 选课门数 */
  private int number; // 选课 门数

  /** 收费list */
  private List feeList = new ArrayList();

  /**
   * @return Returns the feeList.
   */
  public List getFeeList() {
    return feeList;
  }

  /**
   * @param feeList
   *            The feeList to set.
   */
  public void setFeeList(List feeList) {
    this.feeList = feeList;
  }

  /**
   * @return Returns the number.
   */
  public int getNumber() {
    return number;
  }

  /**
   * @param number
   *            The number to set.
   */
  public void setNumber(int number) {
    this.number = number;
  }

  /**
   * @param stdCode
   *            The stdCode to set.
   */
  public void setStdCode(String stdCode) {
    this.stdCode = stdCode;
  }

  /**
   * @see java.lang.Object#equals(Object)
   */
  public boolean equals(Object object) {
    if (!(object instanceof CourseAndFee)) {
      return false;
    }
    CourseAndFee rhs = (CourseAndFee) object;
    return new EqualsBuilder().append(this.stdCode, rhs.stdCode).isEquals();
  }
}
