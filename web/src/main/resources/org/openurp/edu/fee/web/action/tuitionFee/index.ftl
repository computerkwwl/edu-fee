[@b.head/]
<script language="JavaScript" type="text/JavaScript" src="static/scripts/Selector.js"></script>
<script language="JavaScript" src="<@text name="menu.js.url"/>"></script>
 <body >
 <table id="gradeBar"></table>
    <table class="frameTable_title">
       <tr>
        <td>
          缴费情况选择
          <select name="" onchange="changeView(this.value)">
          <option value="0">有缴费数据</option>
          <option value="1">无缴费数据</option>
          </select>
       </td>
       <td class="separator">|</td>
     <form name="stdSearch" target="contentFrame" method="post" entity="tuitionFee" action="tuitionFee.action?method=index" onsubmit="return false;">
      <#--<#assign semesterHolder="tuitionFee"/>
      <#include "../semester.ftl"/> <br>-->
      <input type="hidden" name="tuitionFee.semester.id" value="${semester.id}"></input>
      <#include "/template/time/semester.ftl"/>
     </tr>
   </table>
  <table width="100%" border="0" height="100%" class="frameTable">
  <tr>
   <td style="width:20%" valign="top" class="frameTable_view">
    <#include "searchForm.ftl"/>
    <#include "/template/stdTypeDepart2Select.ftl"/>
    </form>
     </td>
     <td valign="top">
       <iframe src="#" id="contentFrame" name="contentFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%">
       </iframe>
     </td>
    </tr>
  <table>
<script>
    var action="tuitionFee.action";
    var form = document.stdSearch;
    function search(pageNo,pageSize,orderBy){
       form['std.code'].value=form['tuitionFee.std.code'].value;
       form['std.name'].value=form['tuitionFee.std.name'].value;
       form['std.grade'].value=form['tuitionFee.std.grade'].value;
       form['std.major.id'].value=form['tuitionFee.std.major.id'].value;
       form['std.department.id'].value=form['tuitionFee.std.department.id'].value;
       form['std.inSchool'].value=form['tuitionFee.std.inSchool'].value;
       form['std.active'].value=form['tuitionFee.std.active'].value;
     if($('isFinishedFee').style.display=='none'){
           form.action = action+"?method=unTuitionFeeList";
       }else{
           form.action = action+"?method=search";
       }
     form.target="contentFrame";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    var bar=new ToolBar("gradeBar","缴费信息管理",null,true,true);
    bar.addItem("导入交费名单","importData()");
    bar.addItem("下载数据模板","downloadTemplate()",'download.gif');
    bar.setMessage('<@getMessage/>');
    bar.addHelp("<@text name="action.help"/>");

    search();

   function changeView(tuition){
       if(tuition=="1"){
         $("isFinishedFeeTitle").style.display="none";
         $("isFinishedFee").style.display="none";
       }else{
         $("isFinishedFeeTitle").style.display="";
         $("isFinishedFee").style.display="";
    }
    search(1);
   }
   function importData(){
       var form =document.stdSearch;
       form.action="tuitionFee.action?method=importForm&templateDocumentId=6";
       addInput(form,"importTitle","学生缴费信息上传")
       form.submit();
    }

     function downloadTemplate(){
      self.location="dataTemplate.action?method=download&document.id=6";
    }

   function beforeSearch(){
     search(1);
   }

</script>
<script language="JavaScript" type="text/JavaScript" src="scripts/viewSelect.js"></script>
 </body>
<#include "/template/foot.ftl"/>
