[#ftl]
[@b.head/]
  [@b.toolbar title="班级费用统计"]
    [#--
    bar.addItem("导出", function() {
      addInput(form,"fileName","班级费用统计");
      addInput(form,"keys","code,name,educationType.name,stdCount,realCount");
      addInput(form,"titles","代码,名称,培养类型,需缴费人数,已缴费人数");
      form.action="adminClassAndFee.action?method=export";
      form.submit();
    });
    --]
  [/@]
  [@eams.semesterBar name="project.id" semesterEmpty=false semesterName="semester.id" semesterValue=semester/]
  <table class="indexpanel">
    <tr valign="top">
      <td class="index_view" width="20%">
        [#include "searchForm.ftl"/]
      </td>
      <td valign="top">
        [@b.div id="squadAndFeeDiv"/]
      </td>
    </tr>
  </table>
  <script>
    $(function() {
      $(document).ready(function() {
        bg.form.sumbit("searchForm", "${b.url("!search")}", "squadAndFeeDiv");
      });
    });
  </script>
[@b.foot/]
