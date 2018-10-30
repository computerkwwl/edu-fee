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
package org.openurp.edu.web.util;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.pojo.NumberIdObject;
import org.beangle.commons.transfer.TransferResult;
import org.beangle.commons.transfer.importer.AbstractItemImporter;
import org.beangle.struts2.helper.Params;
import org.springframework.util.CollectionUtils;

/**
 * 导入验证工具辅助类
 *
 * @author zhouqi
 *
 */
public class ImporterListenerUtils {

  /**
   * 检查并转换获得对象
   *
   * @param tr
   * @param clazz
   * @param attr 格式为：x1.x2.x3，如果只有 x1 就使用 x1 作查询条件，否则 x2.x3 。
   * @param objectMap
   *            TODO
   *
   * @param <T>
   */
  public static <T extends Entity<?>> T checkAndConvertObject(EntityDao entityDao, TransferResult tr,
      AbstractItemImporter importer, Class<T> clazz, String attr, boolean allowNull, Map<String, T> objectMap) {
    // 根据指定的字段(attr)从模板上读取数值(objectKeyValue)
    String objectKeyValue = (String) importer.getCurData().get(attr);
    if (allowNull && StringUtils.isBlank(objectKeyValue) || null == tr || null == clazz
        || StringUtils.isBlank(attr)) {
      return null;
    }
    T t = null;
    // 验证数据基础正确性
    if (null == checkIsEmpty(tr, importer, String.class, attr, allowNull, false, null, null)) {
      return null;
    } else {
      // 得到对象的属性
      String[] attrArray = StringUtils.split(attr, ".");
      String prefix = attrArray[0];
      String property = "";
      // 如果是简单属性
      if (1 == attrArray.length) {
        property = prefix;
      } else {
        // 如果是多层属性
        for (int i = 1; i < attrArray.length; i++) {
          property += attrArray[i];
          if (i + 1 < attrArray.length) {
            property += ".";
          }
        }
      }
      // 从缓存中取出之前已使用的对象
      if (null != objectMap) {
        t = objectMap.get(objectKeyValue);
      }
      // 如果不使用缓存，或者缓存没有这个对象时，验证objectKeyValue并从数据库中有效对象
      if (null == objectMap || null != objectMap && null == t) {
        Collection<T> entities = entityDao.get(clazz, property, new Object[] { objectKeyValue });
        // 如果不对得到对象，就停止下面的过程
        if (CollectionUtils.isEmpty(entities)) {
          tr.addFailure("No found " + prefix + "!", "<b>" + attr + "</b>=" + objectKeyValue);
        } else if (entities.size() > 1) {
          tr.addFailure("No unique found " + prefix + "!", "<b>" + attr + "</b>=" + objectKeyValue);
        } else {
          // 得到有效的对象
          t = entities.iterator().next();
        }
        // 将有效的对象缓存起来
        if (null != objectMap) {
          objectMap.put(objectKeyValue, t);
        }
      }
      // 对缓存起来的对象进行除Hibernate的缓存
      if (null != t && t instanceof NumberIdObject) {
        entityDao.refresh(t);
      }
      // 将对象存入导入器中
      importer.getCurData().put(attrArray[0], t);
    }
    return t;
  }

  /**
   * 基础类型对象的验证<br>
   *
   * @param <T>
   * @param tr
   * @param importer
   * @param clazz
   * @param attr
   * @param allowNull
   * @param numForward
   *            数字取向：-1（负数）、0（零和正数）和1（正数 ）
   * @param isConvert
   * @param defaultValues
   *            TODO
   */
  public static <T extends Entity<?>> T checkAndConvertLang(TransferResult tr, AbstractItemImporter importer,
      Class<T> clazz, String attr, boolean allowNull, Integer numForward, boolean isConvert,
      String[] defaultValues) {
    // 根据指定的字段(attr)从模板上读取数值(attrValue)
    Object attrValue = importer.getCurData().get(attr);
    if (allowNull && StringUtils.isBlank(String.valueOf(attrValue)) || null == tr || null == clazz
        || StringUtils.isBlank(attr) || null != numForward && (-2 > numForward || 2 < numForward)) {
      return null;
    }
    // 验证数据基础正确性，其中defaultValues是指导入的数值是否在defaultValues中规定的内容
    if (null == checkIsEmpty(tr, importer, clazz, attr, allowNull, isConvert, null, defaultValues)) {
      return null;
    }
    // 转换为类型
    T o = Params.converter.convert(String.valueOf(attrValue), clazz);
    try {
      // 如果是数字类型，根据验证要求来验证有效性
      if (null != numForward) {
        double numValue = Double.parseDouble(String.valueOf(attrValue));
        if (-1 == numForward && numValue > -1 || 0 == numForward && numValue < 0 || 1 == numForward
            && numValue < 1) {
          tr.addFailure("error.parameters.illega", "<b style=\"color:red\">" + attrValue
              + "</b> is invalid in <b>" + attr + "</b> value!");
          return null;
        }
      }
    } catch (NumberFormatException e) {
      // 验证结果该数值根本不是数字类型
      e.printStackTrace();
      tr.addFailure("error.parameters.illegal", "<b style=\"color:red\">" + attrValue
          + "</b> is invalid in <b>" + attr + "</b> value!");
      return null;
    }
    // 对一些基本类型的数值，比如布尔型、日期型等
    if (isConvert) {
      importer.changeCurValue(attr, o);
    }
    return o;
  }

  /**
   * 检查导入字段的值是否为空，或错误转换后得到空值
   *
   * @param <T>
   * @param tr
   * @param importer
   * @param clazz
   * @param attr
   * @param allowNull
   * @param isConvert
   * @param format 需要验证的格式
   * @param defaultValues 若此参数不为null，则忽略format参数
   * @return
   */
  public static <T extends Object> T checkIsEmpty(TransferResult tr, AbstractItemImporter importer,
      Class<T> clazz, String attr, boolean allowNull, boolean isConvert, String format, String[] defaultValues) {
    // 根据指定的字段(attr)从模板上读取数值(attrValue)
    String attrValue = (String) importer.getCurData().get(attr);
    if (allowNull && StringUtils.isBlank(String.valueOf(attrValue)) || null == tr || null == clazz
        || StringUtils.isBlank(attr)) {
      return null;
    }

    // 验证是否必填
    if (!allowNull && StringUtils.isBlank(attrValue)) {
      tr.addFailure("error.parameters.illegal", "<b>" + attr + "</b> of value is null or empty!");
      return null;
    }

    T o = null;
    // 按defaultValues中规定的内容验证
    if (null == defaultValues) {
      // 验证输入了内容，能不能转换成对应类型的数据
      if (StringUtils.isNotBlank(attrValue)) {
        if (StringUtils.isNoneBlank(format)) {
          if (clazz.isAssignableFrom(Date.class)) {
            try {
              SimpleDateFormat sdf = new SimpleDateFormat(format);
              Date value = new Date(sdf.parse(attrValue).getTime());
              if (StringUtils.equals(sdf.format(value), attrValue)) {
                o = (T) value;
              } else {
                throw new IllegalArgumentException(attrValue + " of value is invalid in " + attr + "!");
              }
            } catch (Exception e) {
              tr.addFailure("error.parameters.illegal", "<b style=\"color:red\">" + attrValue
                  + "</b> of value is invalid in <b>" + attr + "</b>!");
              return null;
            }
          } else if (Number.class.isAssignableFrom(clazz)) {
            try {
              DecimalFormat df = new DecimalFormat(format);
              Number value = df.parse(attrValue);
              if (StringUtils.equals(df.format(value), attrValue)) {
                o = (T) value;
              } else {
                throw new IllegalArgumentException(attrValue + " of value is invalid in " + attr + "!");
              }
            } catch (Exception e) {
              tr.addFailure("error.parameters.illegal", "<b style=\"color:red\">" + attrValue
                  + "</b> of value is invalid in <b>" + attr + "</b>!");
              return null;
            }
          }
        } else {
          // 转换获得对应类型的数据
          o = Params.converter.convert(String.valueOf(attrValue), clazz);
          if (null == o || !StringUtils.equals(attrValue, o.toString())) {
            tr.addFailure("error.parameters.illegal", "<b style=\"color:red\">" + attrValue
                + "</b> of value is invalid in <b>" + attr + "</b>!");
            return null;
          }
        }
      }
    } else {
      boolean isExists = false;
      for (int i = 0; i < defaultValues.length; i++) {
        if (StringUtils.equals(defaultValues[i], attrValue)) {
          isExists = true;
          break;
        }
      }
      if (!isExists) {
        tr.addFailure("error.parameters.illegal", "<b style=\"color:red\">" + attrValue
            + "</b> of value is invalid in <b>" + attr + "</b>!");
        return null;
      }
    }

    // 对一些基本类型的数值，比如布尔型、日期型等
    if (isConvert) {
      importer.changeCurValue(attr, o);
    }
    return o;
  }
}
