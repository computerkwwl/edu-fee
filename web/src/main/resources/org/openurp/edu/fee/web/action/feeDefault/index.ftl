[#ftl]
[@b.head/]
  [#include "../feeDefaultConfig/nav.ftl"/]
  [#include "/template/major3Select.ftl"/]
  <table class="indexpanel">
    <tr>
      <td class="index_view">
        [@b.form title="ui.searchForm" name="feeDefaultearchForm" action="!search" target="feeDefaults" theme="search"]
          [@majorSelect id="s1" levelId="feeDefault.level.id" departId="feeDefault.department.id" majorId="feeDefault.major.id"/]
        [/@]
      </td>
      <td class="index_content">[@b.div id="feeDefaults"/]</td>
    </tr>
  </table>
  <script>
    $(function() {
      $(document).ready(function() {
        bg.form.submit(document.feeDefaultearchForm, "${b.url("!search")}", "feeDefaults");
      });
    });
  </script>
[@b.foot/]
