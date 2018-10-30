[#ftl]
<html>
  <head>
    ${b.script("jquery","jquery.min.js")}
    ${b.script("bui","js/jquery-form.js")}
    ${b.script("bui","js/jquery-history.js")}
    ${b.script("bui","js/beangle.js")}
    ${b.script("bui","js/beangle-ui.js")}
    ${b.script("my97","WdatePicker.js")}
    ${b.script("bui","js/jquery-colorbox.js")}
    ${b.script("bui","js/jquery-chosen.js")}
    <script type="text/javascript" src="${base}/static/scripts/jquery/jquery.ui.core.js?bg=3.5.0&compress=no"></script>
    <script type="text/javascript" src="${base}/static/js/plugins/jquery.subscribe,/js/struts2/jquery.struts2,jquery.ui.struts2.js?bg=3.5.0&compress=no"></script>
    ${b.script("bootstrap","js/bootstrap.min.js")}
    <script type="text/javascript">
    var App = {contextPath:"${base}"};
    beangle.base='${b.static_base()}/bui/0.0.6';
    beangle.renderAs("struts");

    jQuery(document).ready(function () {
    jQuery.struts2_jquery.version="3.6.1";
    jQuery.scriptPath = App.contextPath+"/static/";
    jQuery.struts2_jquerySuffix = "";
    jQuery.ajaxSettings.traditional = true;
    jQuery.ajaxSetup ({cache: false});});
    </script>
    ${b.css("bui","css/beangle-ui.css")}
    <style>
      body * {
        font-size: 10pt;
      }
      .listTable {
        border-collapse: collapse;
        border:solid;
        border-width:1px;
        border-color:#006CB2;
        vertical-align: middle;
        font-family: 宋体;
        font-style: normal;
        width: 100%;
        text-align: center;
      }
      table.listTable td{
        border:solid;
        border-width:1px;
        border-right-width:1;
        border-bottom-width:1;
        border-color:#006CB2;
      }
      .darkColumn{
        color: #000000;
        text-decoration: none;
        letter-spacing:0;
        background-color: #c7dbff;
      }
    </style>
  </head>
  <body LEFTMARGIN="0" TOPMARGIN="0">
    [@b.toolbar title="打印收费配置"]
      bar.addPrint();
      bar.addClose();
    [/@]
    <table class="listTable">
      <tr class="darkColumn" style="text-align:center;">
        <td>序号</td>
        <td>课程类别</td>
        <td>学历层次</td>
        <td>学费（分/元）</td>
      </tr>
      [#list creditFeeDefaults as cfd]
      <tr>
        <td style="text-align: right">${cfd_index + 1}</td>
        <td>${cfd.courseType.name}</td>
        <td>${cfd.eduSpan.name}</td>
        <td>${(cfd.value?string("0.00"))!"0.00"}</td>
      </tr>
      [/#list]
    </table>
  </body>
</html>
