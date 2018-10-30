    <td>学  期:</td>
         <td>
         <select name="<#if semesterHolder?exists>${semesterHolder}.</#if>semester.id" id="semesterSelect" style="width:250px" onChange="changeSemester(this)" >
         <#list (semesters?sort_by("code")?reverse)?if_exists as semester>
         <option value="${semester.id}">${semester.schoolYear}&nbsp;${semester.name}</option>
         </#list>
         <option value="">...</option>
         </select>
    </td>
   <script>
    var semesterAction=document.getElementById('semesterSelect').form.action;
    function changeSemester(select){
        var form =this.form;
      form.action=semesterAction;
      form.submit();
    }
   </script>
