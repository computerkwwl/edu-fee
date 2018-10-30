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
package org.openurp.edu.fee.web.action;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Squad;
import org.openurp.edu.eams.web.helper.BaseInfoSearchHelper;
import org.openurp.edu.fee.model.TuitionFee;
import org.openurp.edu.fee.utils.ConditionUtils;
import org.openurp.edu.web.action.SemesterSupportAction;

public class SquadAndFeeAction extends SemesterSupportAction {

  private BaseInfoSearchHelper baseInfoSearchHelper;

  @Override
  public String index() {
    getSemester();
    return super.index();
  }

  @Override
  public String search() {
    put("squades", entityDao.getAll(Squad.class));
    Integer semesterId = getIntId("semester");
    Semester semester = entityDao.get(Semester.class, semesterId);
    List<Squad> squads = CollectUtils.newArrayList(baseInfoSearchHelper.searchSquad(getProject(), semester));
    OqlBuilder<Object[]> builder = OqlBuilder.from(TuitionFee.class.getName() + " fee");
    builder.where(ConditionUtils.splitCollection("fee.std.state.squad", ":squad", 1000, squads));
    builder.where("fee.completed = true");
    builder.where("fee.semester.id=:feeSemesterId", semesterId);
    builder.groupBy("fee.std.state.squad.id");
    builder.select("fee.std.state.squad.id,count(*)");
    List<Object[]> results = entityDao.search(builder);
    Map<Object, Object> realCounts = CollectUtils.newHashMap();
    for (Object[] data : results) {
      realCounts.put(data[0].toString(), data[1]);
    }
    put("realCounts", realCounts);
    put("squads", squads);
    return forward();
  }

  public void setBaseInfoSearchHelper(BaseInfoSearchHelper baseInfoSearchHelper) {
    this.baseInfoSearchHelper = baseInfoSearchHelper;
  }
}
