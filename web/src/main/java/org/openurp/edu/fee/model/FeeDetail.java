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

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.openurp.base.model.Department;
import org.openurp.base.model.NumberIdTimeObject;
import org.openurp.edu.base.code.model.CurrencyCategory;
import org.openurp.edu.base.code.model.FeeMode;
import org.openurp.edu.base.code.model.FeeType;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Student;

/**
 * 收费明细信息
 *
 * @author chaostone
 *
 */
@Entity(name = "org.openurp.edu.fee.model.FeeDetail")
public class FeeDetail extends NumberIdTimeObject<Integer> {

  private static final long serialVersionUID = 5868193073466043875L;

  /** 学生 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  /** hSK成绩 */
  @Size(max = 50)
  private String HSKGrade;

  /** 收费部门 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Department depart;

  /** 发票号 */
  @Size(max = 32)
  private String invoiceCode;

  /** 交费类型 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private FeeType type;

  /** 收费方式 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private FeeMode mode;

  /** 应缴费用（单位：人民币） */
  private Float shouldPay;

  /** 货币类别 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private CurrencyCategory currencyCategory;

  /** 汇率 */
  private Float rate;

  /** 实收金额(单位是货币类别) */
  private Float payed;

  /** 折合成人民币 */
  private Float toRMB;

  /** 学年度学期 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  /** 收费人 */
  @Size(max = 50)
  private String whoAdded;

  /** 修改人 */
  @Size(max = 50)
  private String whoModified;

  /** 备注 */
  @Size(max = 500)
  private String remark;

  /** 创建时间 */
  private Date createdAt;

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public CurrencyCategory getCurrencyCategory() {
    return currencyCategory;
  }

  public void setCurrencyCategory(CurrencyCategory currencyCategory) {
    this.currencyCategory = currencyCategory;
  }

  public Department getDepart() {
    return depart;
  }

  public void setDepart(Department depart) {
    this.depart = depart;
  }

  public String getHSKGrade() {
    return HSKGrade;
  }

  public void setHSKGrade(String HSKGrade) {
    this.HSKGrade = HSKGrade;
  }

  public String getInvoiceCode() {
    return invoiceCode;
  }

  public void setInvoiceCode(String invoiceCode) {
    this.invoiceCode = invoiceCode;
  }

  public Float getPayed() {
    return payed;
  }

  public void setPayed(Float payed) {
    this.payed = payed;
  }

  public Float getRate() {
    return rate;
  }

  public void setRate(Float rate) {
    this.rate = rate;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Float getShouldPay() {
    return shouldPay;
  }

  public void setShouldPay(Float shouldPay) {
    this.shouldPay = shouldPay;
  }

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public Float getToRMB() {
    return toRMB;
  }

  public void setToRMB(Float toRMB) {
    this.toRMB = toRMB;
  }

  public FeeType getType() {
    return type;
  }

  public void setType(FeeType type) {
    this.type = type;
  }

  public String getWhoAdded() {
    return whoAdded;
  }

  public void setWhoAdded(String whoCharge) {
    this.whoAdded = whoCharge;
  }

  public FeeMode getMode() {
    return mode;
  }

  public void setMode(FeeMode mode) {
    this.mode = mode;
  }

  public String getWhoModified() {
    return whoModified;
  }

  public void setWhoModified(String whoModified) {
    this.whoModified = whoModified;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

}
