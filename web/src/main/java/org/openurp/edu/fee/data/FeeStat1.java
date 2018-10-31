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
package org.openurp.edu.fee.data;

import org.openurp.base.model.Department;

/**
 * @author zhouqi 2018年10月31日
 */
public class FeeStat1 {

  private Department department;

  private int payedCount;

  private float payedValue;

  private int payCount;

  private FeeStat1() {
    super();
  }

  public FeeStat1(Department department, int payedCount, float payedValue, int payCount) {
    this();
    this.department = department;
    this.payedCount = payedCount;
    this.payedValue = payedValue;
    this.payCount = payCount;
  }

  public FeeStat1(Department department, Number payedCount, Number payedValue, Number payCount) {
    this(department, payedCount.intValue(), payedValue.floatValue(), payCount.intValue());
  }

  public Department getDepartment() {
    return department;
  }

  public int getPayedCount() {
    return payedCount;
  }

  public float getPayedValue() {
    return payedValue;
  }

  public int getPayCount() {
    return payCount;
  }

  public int getUnpayCount() {
    return payCount - payedCount;
  }

  public float getPayedRate() {
    return payedCount / (float) payCount * 100;
  }
}
