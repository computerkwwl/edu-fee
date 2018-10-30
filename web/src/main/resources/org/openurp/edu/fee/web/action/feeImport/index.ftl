[@b.head/]
<p>正在载入页面...</p>
<#assign importAction="feeDetail.action"/>
<form method="post" action="" name="actionForm" onsubmit="return false;"/>
<script>
  importData();
  function importData() {
      var form = document.actionForm;
    form.action = "feeImport.action?method=importForm&templateDocumentId=11";
       addInput(form,"importTitle","收费信息导入");
      form.submit();
  }
</script>
<#include "/template/foot.ftl"/>
