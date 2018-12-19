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
import org.hibernate.annotations.NaturalId;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Student;

/**
 * @author zhouqi
 */
@Entity(name = "org.openurp.edu.fee.model.TuitionFee")
public class TuitionFee extends NumberIdObject<Integer> {

  private static final long serialVersionUID = -7229057559557518435L;

  /** 学生 */
  @NaturalId
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  /** 教学日历 */
  @NaturalId
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 学费 */
  private Float fee;

  /** 是否交费完成 */
  private boolean completed;

  /** 备注 */
  @Size(max = 500)
  private String remark;

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public Float getFee() {
    return fee;
  }

  public void setFee(Float fee) {
    this.fee = fee;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }
}
