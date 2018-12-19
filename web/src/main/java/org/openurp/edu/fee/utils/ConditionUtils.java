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
package org.openurp.edu.fee.utils;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.builder.Condition;
import org.beangle.commons.entity.Entity;

/**
 * @author zhouqi 2018年10月18日
 *
 */
public class ConditionUtils {

  private final static int DEFAULT_PER_LEN = 1000;

  /**
   * 将按指定的字段、参加名称、对应的数据和指定的长度（默认为1000）拆分成多个查询条件
   *
   * @param fieldStr
   * @param inStr
   * @param perLen 默认为 1000
   * @param data
   * @return
   */
  public static <ID extends Serializable, T extends Entity<ID>> Condition splitCollection(
      String fieldStr, String inStr, int perLen, List<T> data) {
    int splitLen = 0 == perLen ? DEFAULT_PER_LEN : perLen;
    int n = data.size() / splitLen + (data.size() % splitLen == 0 ? 0 : 1);

    StringBuilder hql = new StringBuilder();
    List<List<T>> params = CollectUtils.newArrayList();
    for (int i = 0; i < n; i++) {
      if (hql.length() > 0) {
        hql.append(" or ");
      }
      hql.append(fieldStr + " in (:" + StringUtils.remove(inStr, ":") + i + ")");
      if (n - 1 == i) {
        if (0 == i) {
          params.add(data);
        } else {
          params.add(data.subList(i * splitLen, data.size()));
        }
      } else {
        params.add(data.subList(i * splitLen, (i + 1) * splitLen));
      }
    }
    Condition condition = new Condition(hql.toString());
    condition.params(params);
    return condition;
  }
}
