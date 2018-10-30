[@b.head/]
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="bar"></table>
<@table.table id="id" sortable="true" width="100%">
 <@table.thead>
  <@table.selectAllTd id="tuitionFeeId"/>
  <@table.sortTd id="tuitionFee.std.code" text="学号"/>
  <@table.sortTd id="tuitionFee.std.name" text="姓名"/>
  <@table.sortTd id="tuitionFee.completed" text="是否缴费"/>
  <@table.sortTd id="tuitionFee.std.department" text="院系"/>
  <@table.sortTd id="tuitionFee.std.major" text="专业"/>
  <@table.sortTd id="tuitionFee.std.grade" text="年级"/>
 </@>
 <@table.tbody datas=tuitionFees;tuitionFee>
  <@table.selectTd id="tuitionFeeId" value=tuitionFee.id/>
  <td>${(tuitionFee.std.code)?default("")}</td>
  <td>${tuitionFee.std.name?default("")}</td>
  <td>${(tuitionFee.completed?string("是","否"))?default("")}</td>
  <td>${(tuitionFee.std.department.name)?default("")}</td>
  <td>${(tuitionFee.std.major.name)?default("")}</td>
  <td>${tuitionFee.std.grade?default("")}</td>
 </@>
</@>
<@htm.actionForm name="actionForm" method="post" entity="tuitionFee" action="tuitionFee.action"/>
<script language="javascript">
   var bar=new ToolBar('bar','学生缴费信息',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("删除","remove()","delete.gif");
   bar.addPrint("<@text name="action.print"/>");
 </script>
</body>
<#include "/template/foot.ftl"/>
