[#ftl]
[@b.head/]
  [@b.grid items=feeDefaults var="feeDefault"]
    [@b.gridbar]
      bar.addItem("${b.text("action.add")}", action.add());
      bar.addItem("${b.text("action.modify")}", action.edit());
      bar.addItem("${b.text("action.delete")}", action.remove());
      bar.addItem("打印预览", function() {
        bg.form.submit(action.getForm(), "${b.url("!printReview")}", "_blank");
      }, "print.png");
    [/@]
    [@b.row]
      [@b.boxcol/]
      [@b.col title="学历层次" property="level.name" width="100px"/]
      [@b.col title="院系所" property="department.name" width="100px"/]
      [@b.col title="专业" property="major.name"]${feeDefault.major.name}[/@]
      [@b.col title="收费类别" property="type.name" width="100px"/]
      [@b.col title="默认金额" property="value" width="100px"/]
      [@b.col title="备注" property="remark" width="100px"/]
    [/@]
  [/@]
[@b.foot/]
