[@b.head/]
<body >
  <table id="taskBar"></table>
<@table.table width="100%" sortable="true" id="listTable" headIndex="1">
   <form name="stdForm" action="" method="post" onsubmit="return false;">
     <tr>
        <td>学  期:</td>
         <td>
         <select name="tuitionFee.semester.id" style="width:200px">
         <#list semesters as semester>
         <option value="${semester.id}">${semester.name}</option>
         </#list>
         <option value="">...</option>
         </select>
       </td>
      </tr>
      <tr>
       <td class="infoTitle"><@text name="common.college" />:</td>
       <td>
           <select id="tuitionFee.std.department.code" name="tuitionFee.std.department.code"  style="width:200px;" >
              <option value="">....</option>
               <#list departments as department>
               <option value="${department.code}">${department.name}</option>
               </#list>
           </select>
         </td>
       <tr><td class="infoTitle">专业:</td>
       <td>
           <select id="major" name="tuitionFee.std.major.code"  style="width:200px;" >
              <option value="">....</option>
               <#list majors as major>
               <option value="${major.code}">${major.name}</option>
               </#list>
           </select>
         </td>
        </tr>
      <tr>
        <td>年级:</td><td><input name="tuitionFee.std.grade" value="" style="width:100px" maxLength="15"></td>
      </tr>
      <tr>
        <td>是否缴费:</td><td><select name ="tuitionFee.completed">
                     <option value="1" selected>是</option>
                     <option value="0" selected>否</option>
                    </select>
                  </td>
      </tr>
      <tr>
        <td align="right"><button name="next" onclick ="nextstep()"  style="width:60px;">下一步</button><button name="cancel" onclick="cancel()"  style="width:60px;" >取消</button>
      <td><button name="finish" onclick="finish()" style="width:60px;" >完成</button>
      </tr>
   </form>
  </@>
  <table>
    <script language="javascript">
     var bar=new ToolBar('taskBar','从学生表中添加缴费信息',null,true,false);
     var form = document.stdForm;
     bar.addBack("<@text name="action.back"/>");
     function addUser(){
      var ids = getSelectIds("userId");
      if(ids==''){
      alert("请选择一项或多项");
      return ;
      }
       self.location="";
     }
    function nextstep(){
     form['tuitionFee.semester.id'].readonly=true;
     }
</script>
  </body>
<#include "/template/foot.ftl"/>
