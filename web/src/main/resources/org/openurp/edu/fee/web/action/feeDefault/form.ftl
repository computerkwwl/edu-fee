[#ftl]
[@b.head/]
  [@b.toolbar title="收费明细配置"]
    bar.addBack();
  [/@]
  [@b.form name="feeDefaultForm" action="!save" target="feeDefaults" theme="list"]
    [#assign elementSTYLE = "width: 200px"/]
    [#assign s = "feeDefault_"/]
    <input id="${s}project" type="hidden" name="project.id" value="${project.id}"/>
    [@b.select id=s + "span" label="学历层次" name="feeDefault.eduSpan.id" items=[] empty="..." required="true" style=elementSTYLE/]
    [@b.select id=s + "department" label="院系所" name="feeDefault.department.id" items=[] empty="..." required="true" style=elementSTYLE/]
    [@b.select id=s + "major" label="专业" name="feeDefault.major.id" items=[] empty="..." style=elementSTYLE/]
    [@b.select label="交费类型" name="feeDefault.type.id" items=feeTypes?sort_by(["name"]) value=(feeDefault.type.id)! empty="..." required="true" style=elementSTYLE/]
    [@b.textfield label="默认金额" name="feeDefault.value" value=(feeDefault.value)!0 required="true" maxlength="64" check="match('number')" style=elementSTYLE/]
    [@b.textarea label="备注" name="feeDefault.remark" value=(feeDefault.remark?html)! maxlength="200" rows="3" style=elementSTYLE/]
    [@b.formfoot]
      <input type="hidden" name="feeDefault.id" value="${(feeDefault.id)!}"/>
      [#--[@b.redirectParams/]--]
      [@b.submit value="提交" target="feeDefaults"/]
    [/@]
  [/@]
  <script>
    $.struts2_jquery.require("/scripts/dwr/util.js", null, "${base}/static");
    $.struts2_jquery.require("/engine.js", null, "${base}/dwr");
  </script>
  <script src="${base}/dwr/interface/projectMajorDwr.js"></script>
  <script src="${base}/static/scripts/common/majorSelect.js"></script>
  <script>
    $(function() {
      var sds = null;
      function init(form) {
        sds = new Major3Select("${s}project", "${s}span", null, "${s}department", "${s}major", null, true, true, true, true);
        sds.init([ { "id": "${project.id}", "name": "" }, "${request.getServletPath()}" ]);
        console.log(sds);

        form["feeDefault.eduSpan.id"].value = "${(feeDefault.eduSpan.id)!}";
        form["feeDefault.department.id"].value = "${(feeDefault.department.id)!}";
        form["feeDefault.major.id"].value = "${(feeDefault.major.id)!}";
      }

      $(document).ready(function() {
        init(document.feeDefaultForm);
      });
    });
  </script>
[@b.foot/]
