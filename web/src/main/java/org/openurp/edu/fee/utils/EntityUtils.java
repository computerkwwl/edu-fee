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
/**
 *
 */
package org.openurp.edu.fee.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.Entity;

/**
 * @author zhouqi 2016年11月30日
 */
public class EntityUtils {

  private EntityDao entityDao;

  /**
   * @param entityDao
   */
  public EntityUtils(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  public static EntityUtils instance(EntityDao entityDao) {
    return new EntityUtils(entityDao);
  }

  public <ID extends Serializable, T extends Entity<ID>> Map<ID, T> queryToMap(Class<T> entityClass,
      List<?> sources) {
    List<T> entities = entityDao.search(OqlBuilder.from(entityClass, "entity").where("entity in (:entities)",
        sources));
    Map<ID, T> entityMap = CollectUtils.newHashMap();
    for (T entity : entities) {
      entityMap.put(entity.getId(), entity);
    }
    return entityMap;
  }
}
