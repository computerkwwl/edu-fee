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
package org.openurp.edu.fee.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.fee.model.FeeDetail;
import org.openurp.edu.fee.service.FeeDetailService;

/**
 * @author Administrator
 */
public class FeeDetailServiceImpl extends BaseServiceImpl implements FeeDetailService {

  @Override
  public FeeDetail getFeeDetail(Student student, Semester semester) {
    List<FeeDetail> feeDetails = entityDao.get(FeeDetail.class, new String[] { "std", "semester" }, student,
        semester);
    return CollectionUtils.isEmpty(feeDetails) ? null : feeDetails.get(0);
  }
}
