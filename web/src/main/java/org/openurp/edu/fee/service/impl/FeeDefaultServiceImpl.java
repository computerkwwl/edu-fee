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
package org.openurp.edu.fee.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.model.Department;
import org.openurp.edu.base.code.model.EduSpan;
import org.openurp.edu.base.code.model.FeeType;
import org.openurp.edu.base.model.Major;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.fee.model.FeeDefault;
import org.openurp.edu.fee.service.FeeDefaultService;

public class FeeDefaultServiceImpl extends BaseServiceImpl implements FeeDefaultService {

  private FeeDefaultService feeDefaultService;

  public FeeDefault getFeeDefault(Student std, FeeType type) {
    List<FeeDefault> feeDefaults = feeDefaultService.getFeeDefaults(type, Arrays.asList(std.getSpan()),
        std.getDepartment(), std.getMajor());
    if (CollectionUtils.isEmpty(feeDefaults)) {
      return null;
    }
    Collections.sort(feeDefaults, new Comparator<FeeDefault>() {

      public int compare(FeeDefault fd1, FeeDefault fd2) {
        int rs = fd1.getEduSpan().getId().compareTo(fd2.getEduSpan().getId());
        if (rs == 0) {
          if (null == fd1.getMajor() && null != fd2.getMajor()) {
            return 1;
          } else if (null != fd1.getMajor() && null == fd2.getMajor()) {
            return -1;
          }
        }
        return rs;
      }
    });
    return feeDefaults.get(0);
  }

  @Override
  public List<FeeDefault> getFeeDefaults(FeeType feeType, List<EduSpan> eduSpans, Department department,
      Major major) {
    OqlBuilder<FeeDefault> builder = OqlBuilder.from(FeeDefault.class, "feeDefault");
    builder.where("feeDefault.type = :feeType", feeType);
    builder.where("feeDefault.department = :department", department);
    builder.where("feeDefault.eduSpan in (:eduSpans)", eduSpans);
    if (null != major) {
      builder.where("feeDefault.major is null or feeDefault.major = :major", major);
    }
    return entityDao.search(builder);
  }
}
