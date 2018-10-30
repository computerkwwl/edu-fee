     <tr valign="top">
       <td>
         <fieldSet align=left>
           <legend style="font-weight:bold;font-size:12px">${strFieldSetName}</legend>
            <table class="searchTable" width="100%">
               <tr>
                 <td class="infoTitle">年级:</td>
                 <td><input type="text" name="${clazzStdName}.grade" style="width:100px;" maxlength="7"></td>
               </tr>
                 <tr>
                 <td class="infoTitle"><@text name="entity.studentType" />:</td>
                 <td>
                      <select id="std_stdTypeOfMajor" name="${clazzStdName}.type.id" style="width:100px;">
                        <option value=""><@text name="filed.choose" /></option>
                      </select>
                   </td>
              </tr>
                <tr>
                 <td class="infoTitle"><@text name="common.college" />:</td>
                 <td>
                     <select id="std_department" name="${clazzStdName}.department.id" style="width:100px;">
                       <option value=""><@text name="filed.choose" />...</option>
                     </select>
                   </td>
                  </tr>
               <tr>
                 <td class="infoTitle"><@text name="entity.major" />:</td>
                 <td>
                     <select id="std_major" name="${clazzStdName}.major.id" style="width:100px;">
                       <option value=""><@text name="filed.choose" />...</option>
                     </select>
                   </td>
                  </tr>

               <tr>
                 <td class="infoTitle"><@text name="entity.majorField" />:</td>
                 <td>
                     <select id="std_majorField" name="${clazzStdName}.majorField.id" style="width:100px;">
                       <option value=""><@text name="filed.choose" />...</option>
                     </select>
                   </td>
                  </tr>
                  ${needClass?if_exists}
                  ${needFeeType?if_exists}
          </table>
        </fieldSet>
      </td>
    </tr>
    ${extraHTML?if_exists}
