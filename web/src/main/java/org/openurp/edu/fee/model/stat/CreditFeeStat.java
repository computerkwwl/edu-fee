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
package org.openurp.edu.fee.model.stat;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.commons.entity.pojo.NumberIdObject;
import org.openurp.edu.base.model.Student;

@Entity(name = "org.openurp.edu.fee.model.stat.CreditFeeStat")
public class CreditFeeStat extends NumberIdObject<Integer> {

  private static final long serialVersionUID = 2848121169595997825L;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student student;

  @NotNull
  private String year;

  @NotNull
  private String term;

  @NotNull
  private Integer credits;

  @NotNull
  private Float payed;

  @NotNull
  private Float creditFee;

  /**
   * @return the student
   */
  public Student getStudent() {
    return student;
  }

  /**
   * @param student
   *            the student to set
   */
  public void setStudent(Student student) {
    this.student = student;
  }

  /**
   * @return the year
   */
  public String getYear() {
    return year;
  }

  /**
   * @param year
   *            the year to set
   */
  public void setYear(String year) {
    this.year = year;
  }

  /**
   * @return the term
   */
  public String getTerm() {
    return term;
  }

  /**
   * @param term
   *            the term to set
   */
  public void setTerm(String term) {
    this.term = term;
  }

  /**
   * @return the credits
   */
  public Integer getCredits() {
    return credits;
  }

  /**
   * @param credits
   *            the credits to set
   */
  public void setCredits(Integer credits) {
    this.credits = credits;
  }

  /**
   * @return the payed
   */
  public Float getPayed() {
    return payed;
  }

  /**
   * @param payed
   *            the payed to set
   */
  public void setPayed(Float payed) {
    this.payed = payed;
  }

  /**
   * @return the perTermFee
   */
  public Float getCreditFee() {
    return creditFee;
  }

  /**
   * @param perTermFee
   *            the perTermFee to set
   */
  public void setCreditFee(Float perTermFee) {
    this.creditFee = perTermFee;
  }
}
