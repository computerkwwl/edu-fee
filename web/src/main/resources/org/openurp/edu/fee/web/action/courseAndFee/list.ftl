[@b.head/]
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
  <table id="stdUserBar"></table>
     <@table.table width="100%" align="left" id="sortTable" sortable="true">
     <@table.thead>
       <@table.selectAllTd  id="stdId"/>
       <@table.sortTd id="student.code" name="attr.stdNo" width="10%"/>
       <@table.sortTd width="8%" id="student.name" name="attr.personName"/>
       <@table.sortTd  width="5%" id="student.gender.id" name="entity.gender"/>
       <@table.sortTd  width="8%" id="student.grade" name="std.grade"/>
       <@table.sortTd  width="15%"id="student.department.name" name="department"/>
       <@table.sortTd  width="15%" id="student.major.name" name="entity.major"/>
          <@table.td width="5%" name="entity.acount"/>
     </@>
     <@table.tbody datas=stdUsers;stdUser>
       <@table.selectTd type="checkbox" id="stdId" value=stdUser.id/>
      <td><A href="studentSearch!info.action?studentId=${stdUser.id}" target="blank">${stdUser.code}</A></td>
        <td><A onclick="info('${stdUser.id}');" href="#" alt="点击查看缴费和选课信息"><@i18nName stdUser/></a></td>
        <td><@i18nName (stdUser.gender)?if_exists/></td>
        <td>${stdUser.grade}</td>
        <td><@i18nName stdUser.department/></td>
        <td><@i18nName stdUser.major?if_exists/></td>
        <td></td>
     </@>
    </@>
  <form name="stdUserListForm" method="post" onsubmit="return false;"></form>
  <script>
    var bar = new ToolBar('stdUserBar','&nbsp;选课与缴费',null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("查看选课和收费","info()",'info.gif');

    function getStdIds(){
       return(getCheckBoxValue(document.getElementsByName("stdId")));
    }
    function info(id){
        var stdIds =id;
      var form = document.stdUserListForm;
        if(null==stdIds||''==stdIds){

          stdIds=getStdIds();
          if(isMultiId(stdIds)){
               window.alert('请仅仅选择一个学生!');
               return;
          }
          if(stdIds==""){
               window.alert('请选择一个学生!');
               return;
          }
        }
        form.action="courseAndFee.action?method=info&semester.id=${Parameters['semester.id']}&std.id="+stdIds;
        form.submit();
    }
 </script>
 </body>
<#include "/template/foot.ftl"/>
