[@b.head/]

 <body >
 <table id="bar"></table>
 <#include "/components/stdList1stTable.ftl"/>

 <@htm.actionForm name="actionForm" action="" entity="std">
   <input type="hidden" name="tuitionFee.semester.id" value="${Parameters['tuitionFee.semester.id']}"/>
   <#assign params=""/>
   <#list Parameters?keys as key>
     <#assign params=params + "&" + key + "=" + Parameters[key]/>
  </#list>
   <input type="hidden" name ="params" value="${params}">
 </@>
 <script>
   var bar = new ToolBar("bar","没有缴费学生列表",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("缴费","addFee(1)");
   bar.addItem("未缴费","addFee(0)");
   bar.addItem("<@text name="action.export"/>","exportData()");
   bar.addPrint("<@text name="action.print"/>");
   function exportData(){
     var form = document.actionForm;
     if(${totalSize}>10000){alert("数据量超过一万不能导出");return;}
     addInput(form,"keys","code,name,gender.name,grade,department.name,major.name");
     addInput(form,"titles","学号,姓名,性别,入学时间,院系,专业");
     addInput(form,"fileName","没有注册学生列表");
     exportList();
   }
   function addFee(completed){
   var ids = getSelectIds('stdId');
       if(''==ids){
       alert("至少选择一项");
       return;
       }
     form.action = "tuitionFee.action?method=addFee&completed="+completed+"&stdIds="+ids;
     form.submit();
   }
 </script>
 </body>
<#include "/template/foot.ftl"/>
