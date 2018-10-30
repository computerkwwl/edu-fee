[#ftl]
[@b.head/]
  [#include "../feeDefaultConfig/nav.ftl"/]
  <table class="indexpanel">
    <tr>
      <td class="index_view">
        [@b.form title="ui.searchForm" name="creditFeeDetaultSearchForm" action="!search" target="creditFeeDefaults" theme="search"]
          [@b.select label="学历层次" items=eduSpans?sort_by("code") empty="..."  name="cfd.eduSpan.id"/]
        [/@]
      </td>
      <td class="index_content">[@b.div id="creditFeeDefaults"/]</td>
    </tr>
  </table>
  <script>
    $(function() {
      $(document).ready(function() {
        bg.form.submit(document.creditFeeDetaultSearchForm, "${b.url("!search")}", "creditFeeDefaults");
      });
    });
  </script>
[@b.foot/]
