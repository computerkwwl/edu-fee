[#ftl]
[@b.form name="searchForm" action="!search" target="squadAndFeeDiv"]
<input type="hidden" name="semester.id" value="${semester.id}"/>
[#include "/components/adminClassSearchTable.ftl"/]
[/@]
