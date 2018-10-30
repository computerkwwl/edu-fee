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
package org.openurp.edu.fee.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.NumberIdObject;
import org.openurp.edu.base.code.model.CourseType;
import org.openurp.edu.base.code.model.EduSpan;

/**
 * 学分收费标准
 *
 * @author chaostone
 *
 */
@Entity(name = "org.openurp.edu.fee.model.CreditFeeDefault")
public class CreditFeeDefault extends NumberIdObject<Integer> {

  private static final long serialVersionUID = -8094625916977015539L;

  /** 学历层次（原：学生类别） */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private EduSpan eduSpan;

  /** 课程类别 */
  @ManyToOne(fetch = FetchType.LAZY)
  private CourseType courseType;

  /** 收费金额 */
  private Float value;

  public CourseType getCourseType() {
    return courseType;
  }

  public void setCourseType(CourseType courseType) {
    this.courseType = courseType;
  }

  public EduSpan getEduSpan() {
    return eduSpan;
  }

  public void setEduSpan(EduSpan eduSpan) {
    this.eduSpan = eduSpan;
  }

  public Float getValue() {
    return value;
  }

  public void setValue(Float value) {
    this.value = value;
  }

}
