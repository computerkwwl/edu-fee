[#ftl]
[@b.head/]
  [@b.grid items=fees var="feeDetail"]
    [@b.gridbar]
      bar.addItem("${b.text("action.add")}", function() {
        bg.form.submit(action.getForm(), "${b.url("!edit")}", "feeDetails");
      }, "new.png");
      bar.addItem("${b.text("action.modify")}", action.edit());
      bar.addItem("${b.text("action.info")}", action.info());
      bar.addItem("${b.text("action.delete")}", action.remove("确认要删除吗？"));
      function importParam() {
        action.addParam("file", "/template/excel/feeDetail.xls");
        action.addParam("display", "收费信息导入模板");
        action.addParam("importTitle", "收费信息导入");
      }
      bar.addItem("导入", function() {
        importParam();
        bg.form.submit(action.getForm(), "${b.url("!importForm")}", "_blank");
      });
      bar.addItem("下载导入模板", function() {
        importParam();
        bg.form.submit(action.getForm(), "${b.url("!downloadTemplate")}", "_self");
      }, "${base}/static/images/action/download.gif");
    [/@]
    [@b.row]
      [@b.boxcol/]
      [@b.col title="缴费日期" property="createdAt"]${(feeDetail.createdAt?string("yyyy-MM-dd"))!}[/@]
      [@b.col title="学号" property="std.code"/]
      [@b.col title="姓名" property="std.name"/]
      [@b.col title="年级" property="std.state.grade"/]
      [@b.col title="学年学期" property="semester.beginOn"]${feeDetail.semester.schoolYear} ${feeDetail.semester.name}[/@]
      [@b.col title="收费类型" property="type.name"/]
      [@b.col title="应缴" property="shouldPay"]<span[#if feeDetail.shouldPay?default(0) lt 0]color: red[/#if]>${(feeDetail.shouldPay?string("0.00#"))!}</span>[/@]
      [@b.col title="实缴" property="payed"]<span[#if feeDetail.payed?default(0) lt 0]color: red[/#if]>${(feeDetail.payed?string("0.00#"))!}</span>[/@]
      [@b.col title="发票号" property="invoiceCode"/]
    [/@]
  [/@]
[@b.foot/]
