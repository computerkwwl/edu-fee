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
package org.openurp.edu.base.code.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.beangle.commons.entity.pojo.Code;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.code.school;

/**
 * 课程类别代码 对应数据库表BZHB_BZ_T
 *
 * @author HJ 2005-9-7
 */
@Entity(name = "org.openurp.edu.base.code.model.CurrencyCategory")
@Cacheable
@Cache(region = "eams.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@school
public class CurrencyCategory extends Code<Integer> {

  private static final long serialVersionUID = 1L;

  public static Integer RMB = 1;

  /**
   * 相对于人民币的汇率
   */
  private Float rateToRmb;

  public CurrencyCategory() {
  }

  public CurrencyCategory(String code) {
    super(Integer.valueOf(code));
    setCode(code);
  }

  public CurrencyCategory(Integer id) {
    super(id);
  }

  public Float getRateToRmb() {
    return rateToRmb;
  }

  public void setRateToRmb(Float rateToRmb) {
    this.rateToRmb = rateToRmb;
  }
}
