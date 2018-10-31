[#ftl]
[@b.head/]
  [@b.toolbar title="收费明细配置"]
    bar.addBack();
  [/@]
  [@b.form name="feeDetailForm" action="!save" target="feeDetails" theme="list"]
    [#assign elementSTYLE = "width: 200px"/]
    [#if (feeDetail.id)?exists || (feeDetail.std.id)?exists]
      [@b.field label="学号"]${feeDetail.std.user.code}[/@]
    [#else]
      [@b.textfield label="学号" name="userCode" value=(feeDetail.std.user.code)! required="true" maxlength="32" style=elementSTYLE comment="在左边输入学号后，点击页面空白处，即可获取该学生信息"/]
      <input type="hidden" name="feeDetail.std.id" value=""/>
    [/#if]
    [@b.field label="姓名"]<span id="fd_stdName" style="display: inline-block;">${(feeDetail.std.user.name)!}</span>[/@]
    [@b.field label="专业"]<span id="fd_major" style="display: inline-block;">${(feeDetail.std.state.major.name)!}</span>[/@]
    [@b.field label="班级"]<span id="fd_squad" style="display: inline-block;">${(feeDetail.std.state.squad.name)!}</span>[/@]
    [@b.field label="学历层次"]<span id="fd_span" style="display: inline-block;">${(feeDetail.std.span.name)!}</span>[/@]
    [@b.field label="院系"]<span id="fd_department" style="display: inline-block;">${(feeDetail.std.state.department.name)!}</span>[/@]
    [#if (feeDetail.id)?exists]
      [@b.field label="学年学期"]<span style="display: inline-block;">${feeDetail.semester.schoolYear}${feeDetail.semester.name}</span>[/@]
    [#else]
      [@eams.semesterCalendar label="学年学期" name="feeDetail.semester.id" required="true" value=(feeDetail.semester)?default(semester) style=elementSTYLE/]
    [/#if]
    [@b.select label="币种" name="feeDetail.currencyCategory.id" items=currencyTypes?sort_by(["name"]) value=(feeDetail.currencyCategory.id)! empty="..." required="true" style=elementSTYLE/]
    [@b.select label="收费部门" name="feeDetail.depart.id" items=departments?sort_by(["name"]) value=(feeDetail.depart.id)! empty="..." required="true" style=elementSTYLE/]
    [@b.textfield label="汇率" name="feeDetail.rate" value=(feeDetail.rate?string("0.00#"))! maxlength="64" check="match('number')" style=elementSTYLE/]
    [@b.select label="收费方式" name="feeDetail.mode.id" items=feeModes?sort_by(["name"]) value=(feeDetail.mode.id)! empty="..." required="true" style=elementSTYLE/]
    [@b.select label="交费类型" name="feeDetail.type.id" items=feeTypes?sort_by(["name"]) value=(feeDetail.type.id)! empty="..." required="true" style=elementSTYLE/]
    [@b.textfield label="应缴费用" name="feeDetail.shouldPay" value=(feeDetail.shouldPay?string("0.00#"))! maxlength="64" check="match('number')" style=elementSTYLE/]
    [@b.textfield label="本次缴费" name="feeDetail.payed" value=(feeDetail.payed?string("0.00#"))! maxlength="64" check="match('number')" style=elementSTYLE/]
    [@b.textfield label="发票号" name="feeDetail.invoiceCode" value=(feeDetail.invoiceCode)! maxlength="32" style=elementSTYLE/]
    [@b.datepicker label="缴费日期" name="feeDetail.updatedAt" value=(feeDetail.updatedAt)! style=elementSTYLE/]
    [@b.textarea label="备注" name="feeDetail.remark" value=(feeDetail.remark?html)! maxlength="200" rows="3" style=elementSTYLE/]
    [@b.formfoot]
      <input type="hidden" name="feeDetail.id" value="${(feeDetail.id)!}"/>
      [#--[@b.redirectParams/]--]
      [@b.submit value="提交"/]
    [/@]
  [/@]
  <script>
    $(function() {
      function init(form) {
        var formObj = $(form);
        var stdNameObj = formObj.find("#fd_stdName");
        var majorObj = formObj.find("#fd_major");
        var squadObj = formObj.find("#fd_squad");
        var spanObj = formObj.find("#fd_span");
        var departmentObj = formObj.find("#fd_department");

        formObj.find("[name=userCode]").blur(function() {
          stdNameObj.empty();
          majorObj.empty();
          squadObj.empty();
          spanObj.empty();
          departmentObj.empty();
          form["feeDetail.std.id"].value = "";

          var thisObj = $(this);
          var code = thisObj.val().trim();
          if (code.length == 0) {
            alert("请输入一个有效的学号，谢谢！");
          } else {
            $.ajax({
              "type": "POST",
              "url": "${b.url("!loadStdAjax")}",
              "async": false,
              "dataType": "json",
              "data": {
                "code": code
              },
              "success": function(data) {
                if (data.id) {
                  stdNameObj.text(data.user.name);
                  majorObj.text(data.state.major.name);
                  squadObj.text(data.state.squad.name);
                  spanObj.text(data.span.name);
                  departmentObj.text(data.state.department.name);
                  form["feeDetail.std.id"].value = data.id;
                } else {
                  alert("请输入一个有效的学号，谢谢！");
                  thisObj.val("");
                }
              }
            });
          }
        });
      }

      $(document).ready(function() {
        init(document.feeDetailForm);
      });
    });
  </script>
[@b.foot/]
