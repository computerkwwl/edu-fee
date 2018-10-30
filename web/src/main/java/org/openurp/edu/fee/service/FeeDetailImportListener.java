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
package org.openurp.edu.fee.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.transfer.TransferResult;
import org.beangle.commons.transfer.importer.listener.ItemImporterListener;
import org.openurp.base.model.Department;
import org.openurp.edu.base.code.model.CurrencyCategory;
import org.openurp.edu.base.code.model.FeeMode;
import org.openurp.edu.base.code.model.FeeType;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.base.service.SemesterService;
import org.openurp.edu.fee.model.FeeDetail;
import org.openurp.edu.web.util.ImporterListenerUtils;

/**
 * 一次性导入excel中的所有收费信息
 *
 * @author chaostone
 *
 */
public class FeeDetailImportListener extends ItemImporterListener {

  /** 发票号由数字组成 */
  private final int NUMBERCHAIN = 1;

  /** 发票号由字母和数字组成 */
  private final int LETTERNUMBERCHAIN = 2;

  /** 发票号由字母和数字组成，但号码的第一位必须为字母 */
  private final int FIRSTLETTERCHAIN = 3;

  private EntityDao entityDao;

  private SemesterService semesterService;

  private List<FeeDetail> feeDetails;

  private Map<String, Semester> semesterMap;

  private Map<String, Student> studentMap;

  private Map<String, Department> departmentMap;

  private Map<String, FeeType> feeTypeMap;

  private Map<String, FeeMode> feeModeMap;

  private Map<String, CurrencyCategory> currencyCategoryMap;

  public FeeDetailImportListener() {
    super();
    init();
  }

  private void init() {
    feeDetails = CollectUtils.newArrayList();
    semesterMap = CollectUtils.newHashMap();
    studentMap = CollectUtils.newHashMap();
    departmentMap = CollectUtils.newHashMap();
    feeTypeMap = CollectUtils.newHashMap();
    feeModeMap = CollectUtils.newHashMap();
    currencyCategoryMap = CollectUtils.newHashMap();
  }

  public void onStart(TransferResult tr) {
    init();
  }

  // FIXME 暂时不做了 ---- 先别删除下面注释掉的语句
  public void onItemStart(TransferResult tr) {
    // 检验缴费日期的合法性
    ImporterListenerUtils.checkIsEmpty(tr, importer, Date.class, "createdAt", true, true, "yyyy-M-d", null);

    // 学生
    Student student = ImporterListenerUtils.checkAndConvertObject(entityDao, tr, importer, Student.class,
        "std.user.code", false, studentMap);

    // 收费部门
    ImporterListenerUtils.checkAndConvertObject(entityDao, tr, importer, Department.class, "depart.code",
        false, departmentMap);

    // 交费类型
    ImporterListenerUtils.checkAndConvertObject(entityDao, tr, importer, FeeType.class, "type.code", false,
        feeTypeMap);

    // 交费方式
    ImporterListenerUtils.checkAndConvertObject(entityDao, tr, importer, FeeMode.class, "mode.code", false,
        feeModeMap);

    // 应缴费用
    ImporterListenerUtils.checkIsEmpty(tr, importer, Float.class, "shouldPay", true, true, "0.00", null);

    // 实缴金额
    ImporterListenerUtils.checkIsEmpty(tr, importer, Float.class, "payed", true, true, "0.00", null);

    // 学年学期
    if (null != student) {
      String schoolYear = ImporterListenerUtils.checkIsEmpty(tr, importer, String.class,
          "semester.schoolYear", false, false, null, null);
      String term = ImporterListenerUtils.checkIsEmpty(tr, importer, String.class, "semester.name", false,
          false, null, null);
      String key = student.getProject().getCode() + "_" + schoolYear + "_" + term;
      Semester semester = semesterMap.get(key);
      if (null == semester) {
        semester = semesterService.getSemester(student.getProject(), schoolYear, term);
        if (null == semester) {
          tr.addFailure("No found semester!",
              "<b>semester.schoolYear</b>=" + StringUtils.defaultString(schoolYear)
                  + ", <b>semester.name</b>=" + StringUtils.defaultString(term));
        } else {
          semesterMap.put(key, semester);
          importer.getCurData().put("semester", semester);
        }
      }
    }

    // 货币类型
    ImporterListenerUtils.checkAndConvertObject(entityDao, tr, importer, CurrencyCategory.class,
        "currencyCategory.code", false, currencyCategoryMap);

    // 折合人民币
    ImporterListenerUtils.checkIsEmpty(tr, importer, Float.class, "toRMB", true, true, "0.00", null);

    // Object obj1 = map.get("invoiceCode");
    // if (obj1 == null) {
    // return;
    // }
    // String invoiceCode = obj1.toString();
    // FIXME here 先别删除这些注释掉的语句
    // if (isValidInvoiceCode(tr, invoiceCode, LETTERNUMBERCHAIN)) {
    // Object obj2 = entityDao.loadByKey(FeeDetail.class, "invoiceCode",
    // invoiceCode);
    // if (obj2 == null) {
    super.onItemStart(tr);
    // } else {
    // this.importer.setCurrent(obj2);
    // }
    // } else {
    // tr.addFailure("invoice code is invalid.", invoiceCode);
    // }
  }

  public void onFinish(TransferResult tr) {
    // 若是无错，则保存或更新
    if (!tr.hasErrors()) {
      entityDao.saveOrUpdate(feeDetails);
    }
  }

  public void onItemFinish(TransferResult tr) {
    if (!tr.hasErrors()) {
      feeDetails.add((FeeDetail) importer.getCurrent());
    }
  }

  /**
   * 验证发票号的合法性
   *
   * @param tr
   * @param invoiceCode
   * @param flag
   * @return
   */
  private boolean isValidInvoiceCode(TransferResult tr, String invoiceCode, int flag) {
    // FIXME: 调用处是在上面FIXME处
    if (null == ImporterListenerUtils.checkIsEmpty(tr, importer, Float.class, "invoiceCode", true, false,
        null, null)) {
      return false;
    }
    boolean isNumber = true;
    boolean isLetter = true;
    for (int i = 0; i < invoiceCode.length(); i++) {
      switch (flag) {
        case NUMBERCHAIN:
          if (invoiceCode.charAt(i) >= '0' && invoiceCode.charAt(i) <= '9') {
            isNumber = true;
          } else {
            isNumber = false;
          }
          if (isNumber == false) {
            return false;
          }
          break;
        case FIRSTLETTERCHAIN:
          if (i == 0 && (invoiceCode.charAt(i) >= 'A' && invoiceCode.charAt(i) <= 'Z') == false) {
            return false;
          }
        case LETTERNUMBERCHAIN:
          if (invoiceCode.charAt(i) >= 'A' && invoiceCode.charAt(i) <= 'Z') {
            isLetter = true;
          } else {
            isLetter = false;
          }
          if (invoiceCode.charAt(i) >= '0' && invoiceCode.charAt(i) <= '9') {
            isNumber = true;
          } else {
            isNumber = false;
          }
          if (isLetter == false && isNumber == false) {
            return false;
          }
          break;

        default:
          return false;
      }
    }
    return true;
  }

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  public void setSemesterService(SemesterService semesterService) {
    this.semesterService = semesterService;
  }
}
