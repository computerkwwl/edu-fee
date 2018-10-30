   var form =document.feeDetailForm;
   
   //当学年度、学期以及收费项目变化时，统计应交费用和已缴费用
   function  changeFeeValue(){
    var form =document.feeDetailForm;
    var stdCode=form['feeDetail.std.code'].value;
    if(""!=stdCode){
		var stdTypeId = form['semester.calendar.id'].value;
		var year = form['semester.schoolYear'].value;
		var term = form['semester.name'].value;	
		var feeTypeId = form['feeDetail.type.id'].value;
		feeDetailDwr.statFeeFor(stdCode,stdTypeId,year,term,feeTypeId,statFeeFor);
    }
  }
  /**
   * 回调统计指定日历、收费项目、学生的应交费用和已缴费用(RMB)。
   * data[0]为应缴费用
   * data[1]为已缴费用
   */
  function statFeeFor(data){
    //alert(data)
    var form =document.feeDetailForm;
    var rate = getRate(form['feeDetail.currencyCategory.id'].value);
    if(data[1]==0){
       data[1]=data[0];
    }
    // 如果还没有交够
    if(data[1]!=data[2]){
       $('shouldPay').innerHTML="<label id='shouldPay'>"+data[0]+"</label>";
	   // 如果从来已经交过,从此记录他的应交费用.否则应交费用存为null
	   if(data[2]==0){
	      $('shouldPay').innerHTML+= "<input type='hidden' name='feeDetail.shouldPay' value='"+data[0]+"'>";
	   }
    }
    else{
       $('shouldPay').innerHTML="<input id='shouldPay' name='feeDetail.shouldPay' onchange='isNumber(this);setRemainder();' style='width:100px;' value='"+data[0]+"'>";
    }
	$('hasPayed').innerHTML=data[2];
	$('remainder').innerHTML=data[1]-data[2];
	form['feeDetail.payed'].value=transfer((data[1]-data[2]),rate);
    
    if(data[0]!=data[1]&&data[0]!=0){
	      $("message").innerHTML="应缴:"+data[0]+" 与该学期的历史收费中的应缴:"+data[1]+"不符";
	}else{
	   if(!msg_outof_shool==$("message").innerHTML){
	        $("message").innerHTML="";
	   }
	}
  }
  
  function isNumber(elem){
    if(!(/^[\+\-]?\d+\.?\d*$/.test(elem.value))){
       elem.value="";
       alert("格式不正确");        
    }
  }
  /**
   * 设置应缴的数量
   * @see input name='feeDetail.shouldPay' onchange='isNumber(this);setRemainder();'
   */
  function setRemainder(){
      var form =document.feeDetailForm;
      var shouldPay=form['feeDetail.shouldPay'].value;
      var rate = getRate(form['feeDetail.currencyCategory.id'].value);
      $('remainder').innerHTML=shouldPay;
      form['feeDetail.payed'].value=transfer(shouldPay,rate);
  }
  //查询币种对应的汇率
  function getRate(value){
    for(var i=0;i<currencyCategory.length;i++){
        if(value==currencyCategory[i].id){
           return currencyCategory[i].rateToRMB;
        }
     }
     return 1;
  }
  // 改变币种时，设置应缴的数量
  function changeRate(event){
     var ele= getEventTarget(portableEvent(event));
     var rate =getRate(ele.value);
     $('rate').value=rate;
     form=document.feeDetailForm;
     if($('remainder').innerHTML!=""){
        form['feeDetail.payed'].value=transfer(new Number($('remainder').innerHTML),rate);
     }	 
  }

  // 查找学生
  function getStd(){     
     var form =document.feeDetailForm;
     var stdCode=form['feeDetail.std.code'].value;
     if(stdCode==""){ alert("请输入学号");clear(); }
     else{
    	 studentService.getStudentMap(stdCode,setStdInfo);
     }
  }
  // 清除设定的信息
  function clear(){
       $('queryDetailButton').disabled=true;
       $('name').innerHTML="";
       $('major').innerHTML="";
       $('adminClass').innerHTML="";
       $('feeDetailStdType').innerHTML="";
       $('department').innerHTML="";
       $('message').innerHTML="";
       $('stdId').value="";
       $('feeOrigin').innerHTML="";
  }
  
  var msg_outof_shool="该同学已经离开学校了.";
  var no_std="没有该学号对应的学生";
  var dd = new SemesterSelect("stdType","year","term",false,false,false);
  
  function setStdInfo(data){
	  dd = new SemesterSelect("stdType","year","term",false,false,false);
     if(null==data){
       clear();       
       $('message').innerHTML=no_std;
     }else{
        $('name').innerHTML=data.name;
        $('stdId').value=data.id;
        $('stdType').value=data['stdType'];
        $('educationType').value=data['educationType'];
        $('feeDetailStdType').innerHTML=data['feeDetailStdType'];
        $('department').innerHTML=data['department'];
        $('major').innerHTML=data['major'];
        $('adminClass').innerHTML=data['adminClass'];
        $('feeOrigin').innerHTML="";
        if(!data.state||!data.inSchool)$("message").innerHTML=msg_outof_shool;
        else{
           $("message").innerHTML='';
        }
        $('queryDetailButton').disabled=false;
        dd.initYearSelect(); 
        setTimeout("changeFeeValue()",500);
     }
  }
  function queryStdDetail(){
      var stdId=document.feeDetailForm['feeDetail.std.id'].value;
      if(""!=stdId)
        window.open("studentDetailByManager.do?method=detail&stdId="+stdId, '', '');
  }
  
  function transfer(value,rate){
      return (Math.floor(value/rate*100)/100);
  }

  
  