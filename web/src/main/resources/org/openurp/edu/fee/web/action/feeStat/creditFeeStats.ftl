[@b.head/]
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/itemSelect.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/course/CourseTake.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
    <table id="bar"></table>
    <@table.table width="100%" align="center" sortable="true" id="creditFeeStat">
        <@table.thead>
          <@table.sortTd name="attr.student.code" id="creditFeeStat.student.code"/>
          <@table.sortTd name="attr.studentName" id="creditFeeStat.student.name"/>
          <@table.sortTd name="entity.studentType" id="creditFeeStat.student.stdType"/>
            <@table.sortTd name="filed.sogType.schoolYear" id="creditFeeStat.year"/>
            <@table.sortTd name="filed.sogType.term" id="creditFeeStat.term"/>
            <@table.sortTd name="attr.credit" id="creditFeeStat.credits"/>
            <@table.sortTd name="field.feeDetail.shouldPaidFee(RMB)" id="creditFeeStat.creditFee"/>
            <@table.sortTd name="field.feeDetail.money" id="creditFeeStat.payed"/>
        </@>
        <@table.tbody datas=creditFeeStats;creditFeeStat>
            <td><a href="#" onclick="javascript:location='studentSearch!info.action?studentId=${creditFeeStat.student.id}'">${(creditFeeStat.student.code)?default('')}</a></td>
            <td>${(creditFeeStat.student.name)?default('')}</td>
            <td><@i18nName creditFeeStat.student.stdType?if_exists/></td>
            <td>${(creditFeeStat.year)?default('')}</td>
            <td>${(creditFeeStat.term)?default('')}</td>
            <td>${(creditFeeStat.credits)?default('')}</td>
            <td>${(creditFeeStat.creditFee)?default('')}</td>
            <td>${(creditFeeStat.payed)?default('')}</td>
        </@>
    </@>
    <br><br><br><br>
    <form method="post" action="" onsubmit="return false;" name="actionForm"></form>
    <script>
        var bar = new ToolBar("bar", "学费－学分 缴费不符统计", null, true, true);
        bar.addItem("<@text name="action.export"/>","exportData('accountsTable',true)",'excel.png','导出学费－学分不符记录表');
        var form = document.actionForm;
        function exportData(){

           addInput(form, "semester.id", <#if Parameters['semester.id']?exists && Parameters['semester.id'] != "">${Parameters['semester.id']}<#else>${semester.id?default("1")}</#if>);
           addInput(form,"keys","std.code,std.name,std.stdType.name,type.name,shouldPayed,payed","hidden");
           addInput(form,"titles","学号,姓名,学生类别,收费类型,应缴,实缴金额","hidden");
           form.action="feeStat.action?method=export";
           form.submit();
        }
    </script>
</BODY>
<#include "/template/foot.ftl"/>
