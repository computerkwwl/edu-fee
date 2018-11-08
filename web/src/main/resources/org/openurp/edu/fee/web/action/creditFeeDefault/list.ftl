[#ftl]
[@b.head/]
  [@b.grid items=creditFeeDefaults var="cfd"]
    [@b.gridbar]
      bar.addItem("${b.text("action.add")}", action.add());
      bar.addItem("${b.text("action.modify")}", action.edit());
      bar.addItem("${b.text("action.delete")}", action.remove());
      bar.addItem("打印预览", function() {
        bg.form.submit(action.getForm(), "${b.url("!print")}", "_blank");
      }, "print.png");
    [/@]
    [@b.row]
      [@b.boxcol/]
      [@b.col title="学历层次" property="level.name" width="220px"/]
      [@b.col title="课程类别" property="courseType.name" width="270px"/]
      [@b.col title="学费（分/元）" property="value"]${(cfd.value?string("0.00"))!"0.00"}[/@]
    [/@]
  [/@]
[@b.foot/]
