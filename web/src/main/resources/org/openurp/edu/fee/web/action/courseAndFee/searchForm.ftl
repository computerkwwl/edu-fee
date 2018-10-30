  <table width="100%">
      <form name="stdUserSearchForm" action="courseAndFee.action?method=search" target="contentFrame" method="post" onsubmit="return false;">
      <input type="hidden" name="pageNo" value="1" />
      <input type="hidden" name="semester.id" value=${semester.id}>
      <tr>
        <td width="40%"><@text name="attr.student.code"/>:</td>
      <td><input type="text" name="student.code" value="" style="width:100px;" maxlength="32"/></td>
    </tr>
      <tr>
        <td><@text name="attr.personName"/>:</td>
        <td><input type="text" name="student.name" value="" style="width:100px;" maxlength="20"/></td>
      </tr>
      <tr>
        <td><@text name="std.grade"/>:</td>
        <td><input type="text" name="student.grade" value="" style="width:100px;" maxlength="7"/></td>
      </tr>
      <tr>
        <td><@text name="entity.studentType"/>:</td>
        <td><@htm.i18nSelect datas=stdTypeList selected=(student.stdType.id)?default("") name="student.stdType.id" style="width:100px"><option value=""><@text name="common.all"/></option></@></td>
      </tr>
    <tr>
      <td><@text name="common.college"/>:</td>
      <td><@htm.i18nSelect datas=departmentList selected=(student.department.id)?default("") name="student.department.id" style="width:100px"><option value=""><@text name="common.all"/></option></@></td>
    </tr>
    <tr>
      <td>是否在籍:</td>
      <td><@htm.select2 name="student.active" hasAll=true selected="1" style="width:100px;"/></td>
    </tr>
      <tr height="50px">
        <td align="center" colspan="2">
          <input type="button" onclick="searchStd(1);" value="<@text name="action.query"/>" class="buttonStyle"/>
        </td>
      </tr>
    </form>
  </table>
