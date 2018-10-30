[@b.head/]
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/itemSelect.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/course/CourseTake.js"></script>
<body LEFTMARGIN="0" TOPMARGIN="0">
   <table id="bar"></table>
   <table>
     <tr>
       <td>
         <form name="actionForm" method="post" action="" onsubmit="return false;">
          </form>
       </td>
     </tr>
   </table>
   <@table.table width="100%" align="center" sortable="true" id="listTable">
     <@table.thead>
       <@table.sortTd text="学号" id="feeDetail.std.id"/>
       <@table.td text="姓名"/>
       <@table.td text="学生类别"/>
       <@table.sortTd text="收费类型" id="feeDetail.type.id"/>
       <@table.td text="应缴"/>
       <@table.td text="实缴金额"/>
     </@>
     <@table.tbody datas=accounts;account>
       <td><a href="#" onclick="javascript:location='studentSearch!info.action?studentId=${account.std.id}'">${account.std.code}</a></td>
       <td>${account.std.name}</td>
       <td>${account.std.stdType.name}</td>
       <td>${account.type.name}</td>
       <td>${account.shouldPayed}</td>
       <td>${account.payed}</td>
     </@>
   </@>
  <script>
      var bar=new ToolBar("bar", "应-实 缴费不符统计", null, true, true);
      var form = document.actionForm;
      bar.addItem("<@text name="action.export"/>","exportData('accountsTable',true)",'excel.png','导出项包括课程安排');
      function exportData(){
         addInput(form, "semester.id", "<#if Parameters['semester.id']?exists && Parameters['semester.id'] != "">${Parameters['semester.id']}<#else>${semester.id?default("1")}</#if>");
         addInput(form,"keys","std.code,std.name,std.stdType.name,type.name,shouldPayed,payed","hidden");
         addInput(form,"titles","学号,姓名,学生类别,收费类型,应缴,实缴金额","hidden");
         form.action="feeStat.action?method=export";
         form.submit();
      }
  </script>
</body>
<#include "/template/foot.ftl"/>
