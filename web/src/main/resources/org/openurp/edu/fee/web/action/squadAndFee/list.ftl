[@b.head/]
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/baseinfo/BaseInfo.js?ver=1"></script>
  <@getMessage/>
  <@table.table  width="100%" sortable="true" id="listTable" >
    <@table.thead>
      <@table.selectAllTd id="adminClassId"/>
      <@table.sortTd name="attr.code" id="adminClass.code"/>
      <@table.sortTd name="common.name" id="adminClass.name"/>
      <@table.sortTd name="educationType" id="adminClass.educationType.name" />
      <@table.td text="需缴费人数"/>
      <@table.td text="已缴费人数"/>
    </@>
    <@table.tbody datas=adminClasses;adminClass>
        <@table.selectTd id="adminClassId" value=adminClass.id/>
        <td>${adminClass.code}</td>
        <td><a href="adminClass.action?method=info&adminClass.id=${adminClass.id}">${adminClass.name}</a></td>
        <td><@i18nName adminClass.educationType/></td>
        <td>${adminClass.stdCount}</td>
        <td>${(realCounts[adminClass.id?string])?default("")}</td>
    </@>
  </@>
  </body>
<#include "/template/foot.ftl"/>
