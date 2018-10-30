   <table>
     <tr valign="top">
       <td width="24%">
         <fieldSet align=left>
           <legend style="font-weight:bold;font-size:12px">缴费统计栏目</legend>
           <table id="menuTable" width="100%" style="font-size:10pt">
             <tr></tr>
                      <tr height="25">
                          <td class="padding" id="statFeeType" onclick="selectFrame(this, 'statFeeType')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
                              &nbsp;&nbsp;<image src="${base}/static/images/action/list.gif" align="absmiddle"/>应-实 缴费不符统计
                          </td>
                      </tr>
                      <tr height="25">
                          <td class="padding" id="creditFeeStats" onclick="selectFrame(this, 'creditFeeStats')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
                              &nbsp;&nbsp;<image src="${base}/static/images/action/list.gif" align="absmiddle"/>学费－学分 不符统计
                          </td>
                      </tr>
           </table>
         </fieldSet>
       </td>
     </tr>
     <tr valign="top">
       <td>
         <#--应-实 缴费不符统计-->
         <div id="viewFee" style="display:block">
         <fieldSet align=left>
           <legend style="font-weight:bold;font-size:12px">应-实 缴费不符统计范围</legend>
            <table class="searchTable" width="100%">
                 <tr>
                   <td class="infoTitle">年级:</td>
                   <td><input type="text" name="feeDetail.std.grade" style="width:100px;" maxlength="7"></td>
                 </tr>
                   <tr>
                   <td class="infoTitle"><@text name="entity.studentType"/>:</td>
                   <td>
                        <select id="fee_std_stdTypeOfMajor" name="feeDetail.std.stdType.id" style="width:100px;">
                          <option value=""><@text name="filed.choose"/></option>
                        </select>
                     </td>
              </tr>
                <tr>
                   <td class="infoTitle"><@text name="common.college"/>:</td>
                   <td>
                         <select id="fee_std_department" name="feeDetail.std.department.id" style="width:100px;">
                           <option value=""><@text name="filed.choose"/>...</option>
                         </select>
                    </td>
                  </tr>
                 <tr>
                   <td class="infoTitle"><@text name="entity.major"/>:</td>
                   <td>
                         <select id="fee_std_major" name="feeDetail.std.major.id" style="width:100px;">
                           <option value=""><@text name="filed.choose"/>...</option>
                         </select>
                     </td>
                  </tr>
                 <tr>
                   <td class="infoTitle"><@text name="entity.majorField"/>:</td>
                   <td>
                         <select id="fee_std_majorField" name="feeDetail.std.majorField.id" style="width:100px;">
                           <option value=""><@text name="filed.choose"/>...</option>
                         </select>
                     </td>
                  </tr>
                  <tr>
                    <td><@text name="common.adminClass"/>：</td>
                    <td><input type="text" name="feeDetail_className" maxlength="20" style="width:100px;"/></td>
                  </tr>
                  <tr>
                    <td colspan="2" align="center"><button onclick="document.getElementById('statFeeType').onclick()">查询当前统计</button></td>
                  </tr>
            </table>
        </div>
         <#--学费-学分 不符统计-->
         <div id="viewCredit" style="display:none">
         <fieldSet align=left>
           <legend style="font-weight:bold;font-size:12px">学分-学费 不符统计范围</legend>
            <table class="searchTable" width="100%">
                 <tr>
                   <td class="infoTitle">年级:</td>
                   <td><input type="text" name="creditFeeStat.student.grade" style="width:100px;" maxlength="7"></td>
                 </tr>
                   <tr>
                   <td class="infoTitle"><@text name="entity.studentType"/>:</td>
                   <td>
                        <select id="credit_std_stdTypeOfMajor" name="creditFeeStat.student.stdType.id" style="width:100px;">
                          <option value=""><@text name="filed.choose"/></option>
                        </select>
                     </td>
              </tr>
                <tr>
                   <td class="infoTitle"><@text name="common.college"/>:</td>
                   <td>
                         <select id="credit_std_department" name="creditFeeStat.student.department.id" style="width:100px;">
                           <option value=""><@text name="filed.choose"/>...</option>
                         </select>
                    </td>
                  </tr>
                 <tr>
                   <td class="infoTitle"><@text name="entity.major"/>:</td>
                   <td>
                         <select id="credit_std_major" name="creditFeeStat.student.major.id" style="width:100px;">
                           <option value=""><@text name="filed.choose"/>...</option>
                         </select>
                     </td>
                  </tr>
                 <tr>
                   <td class="infoTitle"><@text name="entity.majorField"/>:</td>
                   <td>
                         <select id="credit_std_majorField" name="creditFeeStat.student.majorField.id" style="width:100px;">
                           <option value=""><@text name="filed.choose"/>...</option>
                         </select>
                     </td>
                  </tr>
                  <tr>
                    <td><@text name="common.adminClass"/>：</td>
                    <td><input type="text" name="creditFeeStat_className" maxlength="20" style="width:100px;"/></td>
                  </tr>
                  <tr>
                    <td colspan="2" align="center"><button onclick="document.getElementById('creditFeeStats').onclick()">查询当前统计</button></td>
                  </tr>
            </table>
          </div>
        </fieldSet>
      </td>
    </tr>
     <tr>
       <td><br><br></td>
     </tr>
   </table>
