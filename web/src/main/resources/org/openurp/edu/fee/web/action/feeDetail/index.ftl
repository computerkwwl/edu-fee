[#ftl]
[@b.head/]
  [@b.toolbar title="收费信息维护"/]
  <table class="indexpanel">
    <tr>
      <td class="index_view">
        [@b.form title="ui.searchForm" name="feeDefailSearchForm" action="!search" target="feeDetails" theme="search"]
          <tr>
            <td class="search-item">
              [@eams.semesterCalendar theme="html" style="width:100px" id="f_semester" label="学年学期" value=curSemester name="feeDetail.semester.id" items=semesters/]
            <td>
          </tr>
          [@b.select label="学历层次" items=levels?sort_by("code") empty="..."  name="feeDetail.std.level.id"/]
          [@b.textfields names="feeDetail.std.user.code;学号,feeDetail.std.user.name;姓名,feeDetail.invoiceCode;发票"/]
          [@b.select label="收费类型" items=feeTypes?sort_by("code") empty="..."  name="feeDetail.type.id"/]
        [/@]
      </td>
      <td class="index_content">[@b.div id="feeDetails"/]</td>
    </tr>
  </table>
  <script>
    $(function() {
      $(document).ready(function() {
        bg.form.submit(document.feeDefailSearchForm, "${b.url("!search")}", "feeDetails");
      });
    });
  </script>
[@b.foot/]
