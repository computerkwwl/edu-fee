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

import org.beangle.commons.entity.metadata.Model;
import org.openurp.edu.base.code.model.FeeType;
import org.openurp.edu.base.model.Student;

public class FeeTypeStat {

  private Student std;

  private FeeType type;

  private Number shouldPayed;

  private Number payed;

  public FeeTypeStat(Long stdId, Integer typeId, Number shouldPayed, Number payed) {
    super();
    this.std = (Student) Model.newInstance(Student.class, stdId);
    this.type = new FeeType(typeId);
    this.shouldPayed = shouldPayed;
    this.payed = payed;
  }

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public FeeType getType() {
    return type;
  }

  public void setType(FeeType type) {
    this.type = type;
  }

  public Number getShouldPayed() {
    return shouldPayed;
  }

  public void setShouldPayed(Number shouldPayed) {
    this.shouldPayed = shouldPayed;
  }

  public Number getPayed() {
    return payed;
  }

  public void setPayed(Number payed) {
    this.payed = payed;
  }
}
