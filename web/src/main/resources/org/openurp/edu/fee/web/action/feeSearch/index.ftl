[@b.head/]
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="bar"></table>
  <table class="frameTable" width="100%">
    <tr>
    <td valign="top" style="width:168px" class="frameTable_view">
      <table id="searchTable" width="100%" class="searchTable">
      <form name="feeDetailForm" method="post" target="contentFrame" action="feeSearch!search.action" onsubmit="return false;">
        <tr>
          <td width="40%"><@text name="entity.studentType"/></td>
          <td><@htm.i18nSelect datas=stdTypeList selected=(feeDetail.std.stdType.id)?default("") name="feeDetail.std.stdType.id" style="width:100px">
              <option value=""><@text name="common.all"/></option>
            </@>
          </td>
        </tr>
        <tr>
          <td>学年学期</td>
          <td>
            <select id="year" name="feeDetail.semester.id" style="width:100%;">
              <option value="">全部</option>
               <#list semesters as semester>
                   <option value="${semester.id}" <#if semester.id?string=(nowSemester.id)?default('')?string>selected</#if> title="${semester.schoolYear}年${semester.name}学期">${semester.schoolYear}年${semester.name}学期</option>
               </#list>
            </select>
          </td>
        </tr>
        <tr>
          <td><@text name="attr.stdNo"/></td>
          <td>
              <input type="text" name="feeDetail.std.code" maxlength="32" style="width:100%;" tabindex="1">
          </td>
        </tr>
        <tr>
          <td><@text name="field.feeDetail.studentName"/></td>
          <td><input type="text" name="feeDetail.std.name" maxlength="20" style="width:100%;"></td>
        </tr>
        <tr>
          <td>发票</td>
          <td><input type="text" name="feeDetail.invoiceCode" maxlength="32" style="width:100%;"></td>
        </tr>
        <tr>
          <td>收费类型</td>
          <td>
            <select name="feeDetail.type.id" style="width:100%">
              <option value="">全部</option>
              <#list feeTypes?if_exists as feeType>
              <option value="${feeType.id}">${feeType.name}</option>
              </#list>
            </select>
          </td>
        </tr>
        <tr>
          <td align="center" colspan="2">
             <button onclick="search();">查询</button>
          </td>
        </tr>
      </form>
      </table>
    </td>
    <td valign="top">
      <iframe  src="#" id="contentFrame" name="contentFrame" scrolling="no" marginwidth="0" marginheight="0"      frameborder="0"  height="100%" width="100%" style="min-height:500px;">
      </iframe>
    </td>
  </tr>
  </table>
 <script>
    var bar = new ToolBar('bar','收费信息查询',null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addHelp("<@text name="action.help"/>");
    var form = document.feeDetailForm
    function search(){
      form.action="feeSearch!search.action";
      form.submit();
    }
   search();
</script>
</body>
 <#include "/template/foot.ftl"/>
