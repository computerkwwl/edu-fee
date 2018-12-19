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

import java.math.BigDecimal;

import freemarker.template.utility.NullArgumentException;

/**
 * @author zhouqi 2018年12月14日
 */
public final class BigDecimalUtils {

  public static <X extends Number, Y extends Number, Z extends Number> Z add(X x, Y y, Class<Z> returnType) {
    if (null == x || null == y || null == returnType) {
      throw new NullArgumentException("x, y or returnType of parameters is invalid or null!!!");
    }
    BigDecimal num1 = BigDecimal.valueOf(x.doubleValue());
    BigDecimal num2 = BigDecimal.valueOf(y.doubleValue());
    try {
      return returnType.getConstructor(Double.TYPE).newInstance(num1.add(num2).doubleValue());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <X extends Number, Y extends Number, Z extends Number> Z subtract(X x, Y y,
      Class<Z> returnType) {
    if (null == x || null == y || null == returnType) {
      throw new NullArgumentException("x, y or returnType of parameters is invalid or null!!!");
    }
    BigDecimal num1 = BigDecimal.valueOf(x.doubleValue());
    BigDecimal num2 = BigDecimal.valueOf(y.doubleValue());
    try {
      return returnType.getConstructor(Double.TYPE).newInstance(num1.subtract(num2).doubleValue());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <X extends Number, Y extends Number, Z extends Number> Z multiply(X x, Y y,
      Class<Z> returnType) {
    if (null == x || null == y || null == returnType) {
      throw new NullArgumentException("x, y or returnType of parameters is invalid or null!!!");
    }
    BigDecimal num1 = BigDecimal.valueOf(x.doubleValue());
    BigDecimal num2 = BigDecimal.valueOf(y.doubleValue());
    try {
      return returnType.getConstructor(Double.TYPE).newInstance(num1.multiply(num2).doubleValue());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <X extends Number, Y extends Number, Z extends Number> Z divide(X x, Y y, Class<Z> returnType)
      throws ArithmeticException {
    if (null == x || null == y || null == returnType) {
      throw new NullArgumentException("x, y or returnType of parameters is invalid or null!!!");
    }
    BigDecimal num1 = BigDecimal.valueOf(x.doubleValue());
    BigDecimal num2 = BigDecimal.valueOf(y.doubleValue());
    try {
      return returnType.getConstructor(Double.TYPE).newInstance(
          num1.divide(num2, 10, BigDecimal.ROUND_HALF_DOWN).doubleValue());
    } catch (ArithmeticException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
