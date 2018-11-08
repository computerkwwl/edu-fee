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

import org.apache.commons.collections.CollectionUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;
import org.openurp.code.edu.model.EducationLevel;
import org.openurp.edu.base.code.model.CourseType;
import org.openurp.edu.fee.model.CreditFeeDefault;
import org.openurp.edu.web.action.RestrictionSupportAction;

public class CreditFeeDefaultAction extends RestrictionSupportAction {

  protected void indexSetting() {
    put("levels", getLevels());
  }

  @Override
  protected String getEntityName() {
    return CreditFeeDefault.class.getName();
  }

  /**
   * 组建查询条件
   *
   * @return
   */
  protected OqlBuilder<CreditFeeDefault> buildQuery() {
    OqlBuilder<CreditFeeDefault> builder = OqlBuilder.from(CreditFeeDefault.class, "cfd");
    List<EducationLevel> spans = getLevels();
    if (CollectUtils.isNotEmpty(spans)) {
      builder.where("cfd.level in (:levels)", spans);
    }
    builder.limit(getPageLimit());
    builder.orderBy(Order.parse(get("orderBy")));
    return builder;
  }

  public String search() {
    put("creditFeeDefaults", entityDao.search(buildQuery()));
    return forward();
  }

  protected void editSetting(Entity<?> entity) {
    indexSetting();
    put("courseTypes", codeService.getCodes(CourseType.class));
  }

  public String checkAjax() {
    Integer id = getInt("id");
    Integer spanId = getIntId("span");
    Integer typeId = getIntId("type");
    OqlBuilder<CreditFeeDefault> builder = OqlBuilder.from(CreditFeeDefault.class, "cfd");
    if (null != id) {
      builder.where("cfd.id != :id", id);
    }
    builder.where("cfd.level.id = :spanId", spanId);
    if (null != typeId) {
      builder.where("cfd.courseType.id is null or cfd.courseType.id = :typeId", typeId);
    }
    put("isOk", CollectionUtils.isEmpty(entityDao.search(builder)));
    return forward();
  }

  /*
   * (non-Javadoc)
   * @see
   * org.beangle.struts2.action.EntityDrivenAction#saveAndForward(org.beangle.commons.entity.Entity)
   */
  protected String saveAndForward(Entity<?> entity) {
    CreditFeeDefault creditFeeDefault = (CreditFeeDefault) entity;
    OqlBuilder<CreditFeeDefault> builder = OqlBuilder.from(CreditFeeDefault.class, "creditFeeDefault");
    if (creditFeeDefault.getCourseType() == null || creditFeeDefault.getCourseType().getId() == null
        || creditFeeDefault.getCourseType().getId().longValue() == 0) {
      creditFeeDefault.setCourseType(null);
      builder.where("creditFeeDefault.level = :level", creditFeeDefault.getLevel());
      builder.where("creditFeeDefault.courseType is null");
    } else {
      builder.where("creditFeeDefault.level = :level", creditFeeDefault.getLevel());
      builder.where("creditFeeDefault.courseType is :courseType", creditFeeDefault.getCourseType());
    }
    List<CreditFeeDefault> defaults = entityDao.search(builder);
    if (null == entity.getId() && CollectionUtils.isEmpty(defaults) || CollectionUtils.isNotEmpty(defaults)
        && defaults.get(0).equals(entity)) {
      return super.saveAndForward(entity);
    } else {
      return forward("edit", "error.code.existed");
    }
  }

  /**
   * 删除
   *
   * @return
   */
  public String remove() {
    try {
      entityDao.remove(entityDao.get(CreditFeeDefault.class, getIntIds("cfd")));
    } catch (Exception e) {
      return forward("deleteErrors");
    }
    return redirect("search", "field.evaluate.deleteSuccess");
  }

  /**
   * 打印预览
   *
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   */
  public String print() {
    put("creditFeeDefaults", entityDao.getAll(CreditFeeDefault.class));
    return forward();
  }
}
