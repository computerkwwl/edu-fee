[@b.head/]
<body>
 <table id="stdUserBar"></table>
 <table class="frameTable_title">
    <tr>
    <td>&nbsp;</td>
    <form name="semesterForm" method="post" target="contentFrame" action="courseAndFee.action?method=index">
    <#include "/template/time/semester.ftl"/>
    </form>
    </tr>
 </table>
   <table class="frameTable">
   <tr>
    <td width="20%" class="frameTable_view"><#include "searchForm.ftl"/></td>
    <td valign="top">
    <iframe src="#" id="contentFrame" name="contentFrame"
      marginwidth="0" marginheight="0"
      scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
    </td>
   </tr>
  </table>
  <script>
   var bar = new ToolBar('stdUserBar','&nbsp;选课与缴费',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@text name="action.help"/>");

    function searchStd(){
       document.stdUserSearchForm.submit();
    }
    searchStd();
 </script>
</body>
<#include "/template/foot.ftl"/>
