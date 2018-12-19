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
package org.openurp.edu.fee.web.action;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.model.Department;
import org.openurp.edu.base.code.model.FeeType;
import org.openurp.edu.base.model.Major;
import org.openurp.edu.fee.model.FeeDefault;
import org.openurp.edu.web.action.SemesterSupportAction;

/**
 * 交费缺省值
 *
 * @author chenweixiong,chaostone
 */
public class FeeDefaultAction extends SemesterSupportAction {

  /**
   * 转向配置收费默认值页面
   *
   * @return
   */
  public String search() {
    OqlBuilder<FeeDefault> builder = OqlBuilder.from(FeeDefault.class, "feeDefault");
    populateConditions(builder);
    builder.limit(getPageLimit());
    String orderBy = get("orderBy");
    if (StringUtils.isBlank(orderBy)) {
      orderBy = "feeDefault.id";
    } else {
      orderBy += ",feeDefault.id";
    }
    builder.orderBy(Order.parse(orderBy));
    put("feeDefaults", entityDao.search(builder));
    return forward();
  }

  /**
   * 修改和添加
   *
   * @return
   */
  public String edit() {
    Integer feeDefaultId = getIntId("feeDefault");
    if (null != feeDefaultId) {
      put("feeDefault", entityDao.get(FeeDefault.class, feeDefaultId));
    }
    put("feeTypes", codeService.getCodes(FeeType.class));
    put("levels", getLevels());
    Date nowAt = new Date();
    put("departments",
        entityDao.search(OqlBuilder.from(Department.class, "department")
            .where("department.beginOn <= :nowAt", nowAt)
            .where("department.endOn is null or department.endOn >= :nowAt")));
    put("majors",
        entityDao.search(OqlBuilder.from(Major.class, "major").where("major.beginOn <= :nowAt", nowAt)
            .where("major.endOn is null or major.endOn >= :nowAt")));
    return forward();
  }

  /**
   * 保存
   *
   * @return
   */
  public String save() {
    entityDao.saveOrUpdate(populateEntity(FeeDefault.class, "feeDefault"));
    return redirect("search", "info.save.success");
  }

  /**
   * 删除
   *
   * @return
   */
  public String remove() {
    try {
      entityDao.remove(entityDao.get(FeeDefault.class, getIntIds("feeDefault")));
    } catch (Exception e) {
      return forward("deleteErrors");
    }
    return redirect("search", "field.evaluate.deleteSuccess");
  }

  /**
   * 打印
   *
   * @return
   */
  public String printReview() {
    put("feeDefaults",
        entityDao.search(OqlBuilder.from(FeeDefault.class, "feeDefault").orderBy(
            Order.parse("feeDefault.level.code, feeDefault.department.name, feeDefault.type.name"))));
    return forward();
  }
}
