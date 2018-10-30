[@b.head/]
<script language="JavaScript" type="text/JavaScript" src="staict/scripts/itemSelect.js"></script>
<script src='${base}/static/scripts/common/stdTypeDepart3Select.js'></script>
<script src='${base}/static/scripts/common/educationTypeDepart3Select.js'></script>
<body>
   <table id="bar"></table>
   <table class="frameTable_title">
     <form method="post" action="" name="feeStatForm" target="displayFrame" onsubmit="return false;">
     <tr>
       <td style="font-size: 10pt;" align="left">统计项目</td>
         <#include "/template/time/semester.ftl"/>
         <#--
         <input type="hidden" name="semester.schoolYear" value="${semester.schoolYear}"/>
         <input type="hidden" name="semester.name" value="${semester.name}"/>
         -->
     </tr>
   </table>
   <#include "/components/initAspectSelectData.ftl"/>
   <table class="frameTable">
     <tr valign="top" height="90">
       <td class="frameTable_view" width="24%">
         <#include "menu.ftl"/>
       </td>
    </form>
       <td rowspan="3">
         <iframe name="displayFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
       </td>
     </tr>
   </table>
  <script>
    <#--页面工具栏定义-->
      var bar=new ToolBar("bar", "缴费统计", null, true, true);
      <#--定义一个动态“搜索”查询功能点-->
        bar.addItem("<@text name="action.help"/>", "javascript:alert('正在建设中...')", "help.png");

    <#--当第一次进入时默认选择第一项菜单，以后就选择上次选择的菜单项-->
    var selectDo = "${Parameters['indexPage']?default("statFeeType")}";

    <#--三联动-->
    var stdTypeArray = new Array();
      <#list stdTypeList as stdType>
          stdTypeArray[${stdType_index}]={'id':'${stdType.id?if_exists}','name':'<@i18nName stdType/>'};
      </#list>
      var sdsFee = new EducationTypeDepart3Select("fee_std_stdTypeOfMajor", "fee_std_department", "fee_std_major", "fee_std_majorField", true, true, true, true);
      sdsFee.init(stdTypeArray,departArray);
      sdsFee.major=1;
      var sdsCredit = new StdTypeDepart3Select("credit_std_stdTypeOfMajor", "credit_std_department", "credit_std_major", "credit_std_majorField", true, true, true, true);
      sdsCredit.init(stdTypeArray,departArray);
      sdsCredit.major=1;
      function changeMajorType(event){
         var select = getEventTarget(event);
         sdsFee.major=select.value;
         sdsCredit.major=select.value;
         fireChange($("fee_std_department"));
         fireChange($("credit_std_department"));
      }

    <#--动态选择被执行的查询路径-->
       function selectOnclick()
      {
        if (selectDo == "statFeeType") {
          document.getElementById('statFeeType').onclick();
        } else {
        document.getElementById('creditFeeStats').onclick();
        }
         }

      <#--声明form-->
      var form = document.feeStatForm;

      <#--当用户点击查询选择项(菜单项)的时候触发的动作-->
      function selectFrame(td, indexPage) {
      <#--查询区域随选择的菜单项改变(name)-->
        if (selectDo != indexPage) {
          if (selectDo == "statFeeType") {
            $("viewFee").style.display = "none";
            $("viewCredit").style.display = "block";
          } else {
            $("viewCredit").style.display = "none";
            $("viewFee").style.display = "block";
          }
          <#--当选择的菜单项发生改变时，记住所选的查询路径(菜单项)，以备切换学期时使用-->
          selectDo = indexPage;
        }

        clearSelected(menuTable, td);
        setSelectedRow(menuTable, td);

        form.action = "feeStat.action?method=" + indexPage;
        addInput(form, "indexPage", indexPage, "hidden");
        form.target = "displayFrame";
        form.submit();
      }

      selectOnclick();
  </script>
</body>
<#include "/template/foot.ftl"/>
