[#ftl]
[@b.head/]
  [@b.toolbar title="学分标准收费管理"/]
  [@b.div id="configs"/]
  [@b.form name="configForm" action="" target="configs"/]
  <script>
    $(function() {
      $(document).ready(function() {
        bg.form.submit(document.configForm, "${b.url("feeDefault")}", "configs");
      });
    });
  </script>
[@b.foot/]
