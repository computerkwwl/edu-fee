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

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.transfer.TransferListener;
import org.beangle.commons.transfer.importer.EntityImporter;
import org.beangle.commons.transfer.importer.listener.ImporterForeignerListener;
import org.openurp.edu.base.code.model.CurrencyCategory;
import org.openurp.edu.base.code.model.FeeMode;
import org.openurp.edu.base.code.model.FeeType;
import org.openurp.edu.base.model.Semester;
import org.openurp.edu.base.model.Student;
import org.openurp.edu.base.service.StudentService;
import org.openurp.edu.eams.web.util.DownloadHelper;
import org.openurp.edu.fee.model.FeeDetail;

public class FeeDetailAction extends FeeSearchAction {

  protected StudentService studentService;

  protected List<TransferListener> importerListeners;

  public String downloadTemplate() {
    DownloadHelper.download(getRequest(), getResponse(),
        getClass().getClassLoader().getResource(get("file")), get("display"));
    return null;
  }

  /**
   * 修改收费信息
   *
   * @return
   */
  public String edit() {
    Integer feeId = getIntId("feeDetail");
    if (null != feeId) {
      put("feeDetail", entityDao.get(FeeDetail.class, feeId));
    }
    put("semester", getSemester());
    prepare();
    return forward();
  }

  public String loadStdAjax() {
    put("std", studentService.getStudent(getProject().getId(), get("code")));
    return forward();
  }

  /**
   * 保存收费信息
   *
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return @
   */
  public String save() {
    FeeDetail feeDetail = populateEntity(FeeDetail.class, "feeDetail");
    // 设置其他信息
    feeDetail.setUpdatedAt(new Date());
    feeDetail.setWhoModified(getUsername());
    // 计算汇率
    if (null == feeDetail.getRate()) {
      CurrencyCategory category = (CurrencyCategory) entityDao.get(CurrencyCategory.class, feeDetail
          .getCurrencyCategory().getId());
      feeDetail.setRate(category.getRateToRmb());
    }
    // 如果应缴为零,则置为null
    if (null != feeDetail.getShouldPay() && feeDetail.getShouldPay().floatValue() == 0) {
      feeDetail.setShouldPay(null);
    }
    if (null != feeDetail.getRate() && null != feeDetail.getPayed()) {
      feeDetail.setToRMB(new Float(feeDetail.getRate().floatValue() * feeDetail.getPayed().floatValue()));
    }

    entityDao.saveOrUpdate(feeDetail);

    return redirect("search", "info.save.success");
  }

  /**
   * 删除收费信息
   *
   * @return
   */
  public String remove() {
    Integer[] feeDetailIds = getIntIds("feeDetail");
    if (ArrayUtils.isEmpty(feeDetailIds)) {
      return forwardError("error.parameters.needed");
    }
    List<FeeDetail> fees = entityDao.get(FeeDetail.class, feeDetailIds);
    String departId = "," + getDepartIdSeq() + ",";
    List<FeeDetail> tobeRemoved = CollectUtils.newArrayList();
    for (FeeDetail fee : fees) {
      if (StringUtils.contains(departId, "," + fee.getDepart().getId() + ","))
        tobeRemoved.add(fee);
    }
    entityDao.remove(tobeRemoved);
    return redirect("search", "info.delete.success");
  }

  private void prepare() {
    put("spans", getStdTypes());
    put("departments", getDeparts());
    put("feeTypes", codeService.getCodes(FeeType.class));
    put("feeModes", codeService.getCodes(FeeMode.class));
    put("currencyTypes", codeService.getCodes(CurrencyCategory.class));
  }

  public Float[] statFeeFor(String stdCode, String year, String term, Integer feeTypeId) {
    List<Student> stds = entityDao.get(Student.class, "code", stdCode);
    if (CollectionUtils.isEmpty(stds)) {
      return new Float[] { new Float(0), new Float(0) };
    }
    Student std = stds.get(0);

    Semester semester = semesterService.getSemester(std.getProject(), year, term);
    if (null == semester) {
      return new Float[] { new Float(0), new Float(0) };
    }
    FeeType type = entityDao.get(FeeType.class, feeTypeId);
    if (null == type) {
      return new Float[] { new Float(0), new Float(0) };
    }
    return feeDetailService.statFeeFor(std, semester, type);
  }

  public String importForm() {
    return forward();
  }

//  protected EntityImporter buildEntityImporter(String upload, Class<?> clazz) {
//    try {
//      UploadedFile[] importFiles = (UploadedFile[]) ActionContext.getContext().getParameters()
//          .get("importFile").getObject();
//      if (null == importFiles) {
//        logger.error("cannot get upload file {}.", upload);
//        return null;
//      }
//      File file = (File) importFiles[0].getContent();
//      String fileName = get(upload + "FileName");
//      InputStream is = new FileInputStream(file);
//      String formatName = Strings.capitalize(Strings.substringAfterLast(fileName, "."));
//      Option<TransferFormat> format = Enums.get(TransferFormat.class, formatName);
//      return (format.isDefined()) ? ImporterFactory.getEntityImporter(format.get(), is, clazz, null) : null;
//    } catch (Exception e) {
//      logger.error("error", e);
//      return null;
//    }
//  }

  /**
   * 导入收费信息
   *
   * @return
   */
  protected void configImporter(EntityImporter importer) {
    importer.addListener(new ImporterForeignerListener(entityDao));

    for (TransferListener listener : importerListeners) {
      importer.addListener(listener);
    }
  }

  public void setStudentService(StudentService studentService) {
    this.studentService = studentService;
  }

  public void setImporterListeners(List<TransferListener> importerListeners) {
    this.importerListeners = importerListeners;
  }

  @Override
  protected String getEntityName() {
    return FeeDetail.class.getName();
  }
}
