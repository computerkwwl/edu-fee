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
package org.openurp.code;

import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;
import org.beangle.struts2.helper.Params;

public class SqlGenerator {

  public static void main(String[] args) throws Exception {
    //    File f1 = new File("target/ddl");
    //    if (!f1.exists()) {
    //      f1.mkdirs();
    //    }
    //    System.out.println(f1);
    //    DdlGenerator.main(new String[] { PostgresPlusDialect.class.getName(), f1.toString(), "zh_CN", null });
    String attrValue = "20.00F";
    DecimalFormat df = new DecimalFormat("0.00");
    System.out.println(StringUtils.equals(attrValue, df.format(df.parse(attrValue))));
    System.out.println(StringUtils.equals(attrValue,
        Params.converter.convert(String.valueOf(attrValue), Float.class).toString()));
  }
}
