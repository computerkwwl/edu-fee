[#ftl]
[@b.head/]
  [@b.toolbar title="收费明细配置"]
    bar.addBack();
  [/@]
  [@b.form name="creditFeeDefaultForm" action="!save" target="creditFeeDefaults" theme="list"]
    [#assign elementSTYLE = "width: 200px"/]
    [#assign s = "creditFeeDefault_"/]
    [@b.select id=s + "span" label="学历层次" name="creditFeeDefault.eduSpan.id" items=eduSpans empty="..." required="true" style=elementSTYLE/]
    [@b.select label="课程类别" name="creditFeeDefault.courseType.id" items=courseTypes?sort_by(["name"]) value=(creditFeeDefault.courseType.id)! empty="..." style=elementSTYLE/]
    [@b.validity]
      $("[name='creditFeeDefault.eduSpan.id']", document.creditFeeDefaultForm).require().assert(function() {
        var isOk = false;

        $.ajax({
          "type": "POST",
          "url": "${b.url("!checkAjax")}",
          "async": false,
          "dataType": "json",
          "data": {
            "id": document.creditFeeDefaultForm["creditFeeDefault.id"].value,
            "spanId": document.creditFeeDefaultForm["creditFeeDefault.eduSpan.id"].value,
            "typeId": document.creditFeeDefaultForm["creditFeeDefault.courseType.id"].value
          },
          "success": function(data) {
            isOk = data.isOk;
          }
        });

        return isOk;
      }, "该记录已存在！！！");

    [/@]
    [@b.textfield label="学费" name="creditFeeDefault.value" value=(creditFeeDefault.value)!0 required="true" maxlength="64" check="match('number')" style=elementSTYLE comment="（元/分）"/]
    [@b.formfoot]
      <input type="hidden" name="creditFeeDefault.id" value="${(creditFeeDefault.id)!}"/>
      [#--[@b.redirectParams/]--]
      [@b.submit value="提交" target="creditFeeDefaults"/]
    [/@]
  [/@]
[@b.foot/]
