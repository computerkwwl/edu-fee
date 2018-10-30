  <table width="100%">
      <tr>
        <td class="infoTitle" align="left" valign="bottom">
         <img src="static/images/action/info.gif" align="top"/>
            <B><@text name="baseinfo.searchStudent"/></B>
        </td>
      </tr>
      <tr>
        <td colspan="8" style="font-size:0px">
            <img src="static/images/action/keyline.gif" height="2" width="100%" align="top">
        </td>
     </tr>
  </table>
  <table width='100%' class="searchTable" onkeypress="dwr.util.onReturn(event, search)">
  <input type="hidden" name="std.code">
  <input type="hidden" name="std.name">
  <input type="hidden" name="std.grade">
  <input type="hidden" name="std.department.id">
  <input type="hidden" name="std.major.id">
  <input type="hidden" name="std.active">
  <input type="hidden" name="std.inSchool">
    <input type="hidden" name="pageNo" value="1" />
      <tr>
       <td class="infoTitle" width="35%"><@text name="attr.stdNo" />:</td>
       <td>
        <input type="text" name="tuitionFee.std.code" size="10" maxlength="32" value="${Parameters['std.code']?if_exists}"/>
       </td>
    </tr>
      <tr>
       <td   class="infoTitle"><@text name="attr.personName"/>:</td>
       <td>
        <input type="text" name="tuitionFee.std.name" size="10" maxlength="20" value="${Parameters['std.name']?if_exists}"/>
       </td>
    </tr>
     <tr>
       <td class="infoTitle"><@text name="std.grade" />:</td>
       <td><input type="text" name="tuitionFee.std.grade" maxlength="7" style="width:100px;"></td>
     </tr>
      <tr>
       <td class="infoTitle"><@text name="common.college" />:</td>
       <td>
           <select id="department" name="tuitionFee.std.department.id"  style="width:100px;" >
              <option value="">....</option>
               <#--<#list departmentList as department>
               <option value="${(department.code)?default('')}" title="${(department.name)?default('')}">${(department.name)?default('')}</option>
               </#list>-->
           </select>
         </td>
     </tr>
     <tr>
       <td class="infoTitle">专业:</td>
       <td>
           <select id="major" name="tuitionFee.std.major.id"  style="width:100px;" >
            <option value="">....</option>
           <#-- <#list majors as major>
           <option value="${(major.code)?default('')}" title="${(major.name)?default('')}">${(major.name)?default('')}</option>
           </#list>-->
           </select>
         </td>
        </tr>

        <tr>
       <td class="infoTitle">是否在籍:</td>
       <td>
           <select  name="tuitionFee.std.active"  style="width:100px;" >
              <option value="1">是</option>
         <option value="0">否</option>
           </select>
         </td>
        </tr>
         <tr>
       <td class="infoTitle">是否在校:</td>
       <td>
           <select name="tuitionFee.std.inSchool"  style="width:100px;" >
              <option value="1">是</option>
         <option value="0">否</option>
           </select>
         </td>
        </tr>
      <tr>
       <td class="infoTitle"><div id="isFinishedFeeTitle">缴费完成:</div></td>
       <td><div id="isFinishedFee"><@htm.select2 selected="false" name="tuitionFee.completed" style="width:100px" hasAll=false/> </div></td>
        </tr>
      <tr>
      <tr align="center">
       <td colspan="2">
          <button style="width:60px" onClick="search(1)"><@text name="action.query"/></button>
       </td>
      </tr>
  </table>
