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
package org.openurp.edu.fee.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.NumberIdObject;
import org.openurp.base.model.Department;
import org.openurp.code.edu.model.EducationLevel;
import org.openurp.edu.base.code.model.FeeType;
import org.openurp.edu.base.model.Major;

/**
 * 收费缺省值
 *
 * @author chenweixiong,chaostone
 */
@Entity(name = "org.openurp.edu.fee.model.FeeDefault")
public class FeeDefault extends NumberIdObject<Integer> {

  private static final long serialVersionUID = -3198565526508585146L;

  /** 起始年级 */
  @NotNull
  @Size(max = 50)
  private String fromGrade;

  /** 截止年级 */
  @NotNull
  @Size(max = 50)
  private String toGrade;

  /** 学历层次（原：学生类别） */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private EducationLevel level;

  /** 系 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Department department;

  /** 所属的专业 */
  @ManyToOne(fetch = FetchType.LAZY)
  private Major major;

  /** 收费类型 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private FeeType type;

  /** 对应的值 */
  @NotNull
  private Integer value;

  /** remark */
  @Size(max = 500)
  private String remark;

  public String getFromGrade() {
    return fromGrade;
  }

  public void setFromGrade(String fromGrade) {
    this.fromGrade = fromGrade;
  }

  public String getToGrade() {
    return toGrade;
  }

  public void setToGrade(String toGrade) {
    this.toGrade = toGrade;
  }

  /**
   * @return Returns the value.
   */
  public Integer getValue() {
    return value;
  }

  /**
   * @param value
   *          The value to set.
   */
  public void setValue(Integer defaultValue) {
    this.value = defaultValue;
  }

  /**
   * @return Returns the type.
   */
  public FeeType getType() {
    return type;
  }

  /**
   * @param type
   *          The type to set.
   */
  public void setType(FeeType feeType) {
    this.type = feeType;
  }

  /**
   * @return Returns the remark.
   */
  public String getRemark() {
    return remark;
  }

  /**
   * @param remark
   *          The remark to set.
   */
  public void setRemark(String remark) {
    this.remark = remark;
  }

  /**
   * @return Returns the level.
   */
  public EducationLevel getLevel() {
    return level;
  }

  /**
   * @param stdType
   *          The level to set.
   */
  public void setLevel(EducationLevel level) {
    this.level = level;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public Major getMajor() {
    return major;
  }

  public void setMajor(Major major) {
    this.major = major;
  }
}
