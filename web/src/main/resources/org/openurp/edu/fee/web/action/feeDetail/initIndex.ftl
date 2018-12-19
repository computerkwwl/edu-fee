[#ftl]
[@b.head/]
  [@b.toolbar title = "<span style=\"color: blue\">" + semester.schoolYear + "学年" + semester.name + "</span>将初始化收费学生列表"]
    bar.addItem("返回列表", function() {
      bg.Go("${b.url("feeDetail")}", "main");
    }, "backward.png");
  [/@]
  <div style="color: blue">温馨提示：如果没有出现在列表的学生，有以下几种情况：<br>1.&nbsp;这些学生的学籍不在当前指定的学年学期中；<br>2.&nbsp;这些学生没有找到对应的收费默认配置；<br>3.&nbsp;这些学生学籍不完整，比如没有院系、专业等。</div>
  <table class="indexpanel">
    <tr>
      <td class="index_view">
        [@b.form title="ui.searchForm" name="feeDefailInitSearchForm" action="!toInitStudentList" target="feeDefailInitStudents" theme="search"]
          <input type="hidden" name="feeDetail.semester.id" value="${Parameters["feeDetail.semester.id"]}"/>
          [@b.textfields names="student.user.code;学号,student.user.name;姓名,student.state.grade;年级"/]
          [@b.select label="common.college" name="student.state.department.id" items=departments?sort_by("code") empty="..."/]
          [@b.select label="有无标准" name="hasFeeDefault" items={ "1": "有", "0": "否" } empty="..."/]
        [/@]
      </td>
      <td class="index_content">[@b.div id="feeDefailInitStudents"/]</td>
    </tr>
  </table>
  <script>
    $(function() {
      $(document).ready(function() {
        bg.form.submit(document.feeDefailInitSearchForm, "${b.url("!toInitStudentList")}", "feeDefailInitStudents");
      });
    });
  </script>
[@b.foot/]