
$(function(){
	var messagePop = function(str){
		$(".message-pop").fadeIn();
		$(".message-pop").children("span").html(str);
		setTimeout(function(){
			$(".message-pop").fadeOut();
		},2000)
	}
	
	$( "#signDate" ).datepicker({
		dateFormat:'yy-mm-dd',
		showOn: "button",
		buttonImage: contextPath+"/static/imgs/04.jpg",
		buttonImageOnly: true,
		buttonText: "选择日期"

	});
	//是否删除
	$( "#dialog_del2" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});	
	//是否删除
	$( "#dialog_del3" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});	
	
	//收费
	$( "#dialog_mon" ).dialog({
		autoOpen: false,
		width: 840,
		modal: true
	});	
	//修改客户
	$( "#dialog_add" ).dialog({
		autoOpen: false,
		width: 840,
		modal: true
	});
	
	$( "#delectCustomer" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});	
	$( ".delectCustomerClose" ).click(function( event ) {
		$( "#delectCustomer" ).dialog( "close" );
		event.preventDefault();
	});
	
})
var initTime=$("#initPeriod").val();
//点击客户时候触发
$("table tbody").delegate(".dialog_add","click",function(){
	//var id=$(this).siblings("input:last").val();
	ycid=$(this).siblings("input:last").val();
	
	if("undefined" != typeof ycid){
		//alert("id有定义");
		//alert(thcurrentPeriod);
		//console.log(ycid);
		insertPut(ycid);
	}else{
		$( "#inputForm").resetForm();
     	$( "#upload").resetForm();
     	$(".wxusername").hide();
     	$(".wxpassword").hide();
     	//alert(thcurrentPeriod);
     	newDate();
     	//thcurrentPeriod='';
     	//date();
	}
		
	
	//var id=$(".hiddenId").attr("id");
	//alert(id);
	//alert("1");

	/* 以上是点击获取时间的*/
	 
	 //var initPeriod=$("#initPeriod").val();//获取当前账期
	//	alert(initPeriod+"得到了幺")
	 //date();
	$("#dialog_add .nav-tabs li:first").addClass("active").siblings().removeClass("active");
	$("#dialog_add .tab-content .tab-pane:first").addClass("active").siblings().removeClass("active");
	$( "#dialog_add" ).dialog( "open" );
	event.preventDefault();
});
$(".dialog_add").on("click",function(){
	//var id=$(this).siblings("input:last").val();
	ycid=$(this).siblings("input:last").val();
	
	if("undefined" != typeof ycid){
		//alert("id有定义");
		//alert(thcurrentPeriod);
		//console.log(ycid);
		insertPut(ycid);
	}else{
		$( "#inputForm").resetForm();
		
     	$( "#upload").resetForm();
     	$(".wxusername").hide();
     	$(".wxpassword").hide();
     	//alert(thcurrentPeriod);
     	newDate();
     	//thcurrentPeriod='';
     	//date();
	}
		
	
	//var id=$(".hiddenId").attr("id");
	//alert(id);
	//alert("1");

	/* 以上是点击获取时间的*/
	 
	 //var initPeriod=$("#initPeriod").val();//获取当前账期
	//	alert(initPeriod+"得到了幺")
	 //date();
	$("#dialog_add .nav-tabs li:first").addClass("active").siblings().removeClass("active");
	$("#dialog_add .tab-content .tab-pane:first").addClass("active").siblings().removeClass("active");
	$( "#dialog_add" ).dialog( "open" );
	event.preventDefault();
	
});
function date(){
	var dateYear = new Date();
	var myers = $("#year_span");
	myers.empty();
	var nowyear=dateYear.getFullYear();
	var oldyear=nowyear-5;
	var newyears=nowyear+5;
	var currentPeriod=$("#currentPeriod").val();//获取当前账期
	//thcurrentPeriod=currentPeriod;
	//alert(currentPeriod+"得到了幺")
	var initPeriod=$("#initPeriod").val();//获取起始账期
	//alert(initPeriod+'dddd');
	var yearpreiod=initPeriod.substring(0,4);
	var monthperiod=initPeriod.substring(4,6);
	//alert(11);
	
	if(currentPeriod==''){
		//------
		if($("#systemdiv select").length == 0){
			$("#systemdiv span").remove();
			var selectHtmlsys="<select id='system' name='system' htmlEscape='false' class='form-control ipt_w4'> \
	 		<option value='1'>2013小企业会计准则</option>\
	 		<option value='2'>2007企业会计准则</option>\
     	      </select><em>*</em>";
			$("#systemdiv").append(selectHtmlsys);
			
		}
		
		//------
		
		if($("#datediv select").length == 0) {
			$("#datediv span").remove();
			var first=$("#datediv input[name='initperiod']");
			if(first.length==0){
				var firstinput="<input type='hidden' name='initperiod'/>";
				$("#datediv").append(firstinput);
			}
			//var inputfirst="<input type='hidden' name='initperiod'/>";
			var selectHtml = "<select class='form-control ipt_wauto' style='margin-right:8px;' id='year_span'> \
								</select> \
								<select class='form-control ipt_wauto' id='month_span'> \
								</select>";
			$("#datediv").append(selectHtml);
		}
		var myers = $("#year_span");
		for(var i=oldyear;i<newyears;i++) {
			if(i==yearpreiod){
				var option = $("<option selected>").text(i).val(i)
			}else{
				var option = $("<option>").text(i).val(i)
			}
			myers.append(option);
		}
		var nowmonth=dateYear.getMonth()+1;
		var months = $("#month_span");
		months.empty();
		for(var j=1;j<13;j++) {	
			//alert(typeof j);
			if(j==monthperiod){
				if(j<10){
					var option = $("<option selected>").text('0'+j).val('0'+j);
				}else{
					var option = $("<option selected>").text(j).val(j);
				}
				
			}else{
				if(j<10){
					var option = $("<option>").text('0'+j).val('0'+j);
				}else{
					var option = $("<option>").text(j).val(j);
				}
				
			}
			months.append(option);
	}
	}else{
		$("#datediv :not('input')").remove();
		$("#systemdiv :not('input')").remove();
		//$("#datediv input:first").remove();
		$("#datediv input[name='initperiod']").remove();
		//$("#qszq").remove();
		$("#datediv").append("<span style='line-height:24px;'>"+initPeriod+"</span>");
		/*$("#datediv").html(initPeriod);*/
		//---------企业准则
		//$("#systemdiv").text($("#systemid").val());
		$("#systemdiv").append("<span style='line-height:24px;'>"+$("#systemid").val()+"</span>");
		
		//--------
	}
		
	$("[name=initperiod]").val(function(){
		var years=$('#year_span').val();
		var months=$('#month_span').val();
		return years+months;
	});
	$('#year_span').change(function(){
		var years=$(this).val();
		var months=$('#month_span').val()
		$("[name=initperiod]").val(function(){
			console.info(years+months);
			return years+months;
		});
	})
	$('#month_span').change(function(){
		var years=$('#year_span').val();
		var months=$(this).val();	
		$("[name=initperiod]").val(function(){
			console.info(years+months);
			return years+months;
		});
	})
}
    //给输入框中赋值
	function insertPut(id){
		$.ajax({  
			type:"POST",
				url:ctx+"/customer/tCustomer/forms", 
			  data: "id="+id,
		       success:function(json){  
		    	   //alert(5455555);
		    	   $json = json;
		    	   $('#inputForm').formEdit($json);
		    	   //alert(json.initperiod+'哈哈哈');
		    	   //console.log($json.system);
		    	   //---------
		    	   var systemint=$json.system;
		    	   var systemintstring="";
		    	   if(systemint==1){
		    		   systemintstring="2013小企业会计准则";
		    	   }else if(systemint==2){
		    		   systemintstring="2007企业会计准则";
		    	   }
		    	   $("#systemid").val(systemintstring);
		    	   //-----------
		    	   $("#currentPeriod").val(json.currentperiod);
		    	   $("#initPeriod").val(json.initperiod);
		    	  
		    	   date();
		       },
		       error:function(){
 }
		      
		 });
	};  
		
	$("table tbody").delegate(".dialog_mon","click",function(event){ 
	
	
		
		$("#inputForminsert #customerId").val($(this).siblings("input:last").val());
		$("#searchFormtServiceCharge #customerId1").val($(this).siblings("input:last").val());
		$("#dialog_mon #customerName1").text($(this).siblings("input:first").val());
		$("#searchFormtServiceCharge").ajaxSubmit({
		        type: 'post',
		        url: ctx+"/inspection/workstatistics/tServiceCharge/listpage" ,
		        dataType : 'json',
		        data: $("#searchFormtServiceCharge").serialize(),
		        success: function(data){
		           $("#tServiceChargeList").setTemplateElement("template").processTemplate(data);
		          $("#dialog_mon .pagination").html(data.html);
		          $("#dialog_mon #pageNo").val(data.pageNo);
		          $("#dialog_mon #pageSize").val(data.pageSize);
		        },
		        error: function(XmlHttpRequest, textStatus, errorThrown){
		        	
		        }                
			 });
		$( "#dialog_mon" ).dialog( "open" );
		event.preventDefault();
		
	});

	$( ".delecttServiceCharge" ).click(function( event ) {
		
		var id= $("#dialog_del2 #delectids").val();
		$.ajax({  
			type:"POST",
				url:ctx+"/inspection/workstatistics/tServiceCharge/delects",  
				dataType : 'json',
			   data: "id="+id,
		       success:function(data){  
		    	   
		    	   messagePop("删除成功！");
		    	   $("#tServiceChargeList").setTemplateElement("template").processTemplate(data);
			          $("#dialog_mon .pagination").html(data.html);
			          $("#dialog_mon #pageNo").val(data.pageNo);
			          $("#dialog_mon #pageSize").val(data.pageSize);
			          $( "#dialog_del2" ).dialog( "close" );
			  		event.preventDefault();
		       },
		       error:function(){
}
		 });  
		
	});
	
	$( ".dialog_close6" ).click(function( event ) {
		$( "#dialog_del2" ).dialog( "close" );
		event.preventDefault();
	});



			
function savetServiceCharge(){
	if(!validateForm()){
		return;
	}
	 $("#inputForminsert").ajaxSubmit({
	        type: 'post',
	        url: ctx+"/inspection/workstatistics/tServiceCharge/saves" ,
	        dataType : 'json',
	        data: $("#inputForminsert").serialize(),
	        success: function(data){
	        	messagePop("保存成功！");
	        	 $("#tServiceChargeList").setTemplateElement("template").processTemplate(data);
		          $("#dialog_mon .pagination").html(data.html);
		          $("#dialog_mon #pageNo").val(data.pageNo);
		          $("#dialog_mon #pageSize").val(data.pageSize);
	        },
	        error: function(XmlHttpRequest, textStatus, errorThrown){
	        	
	        }   
	        
		 });
	 $("#inputForminsert").resetForm();  
	 return false;
}


	 function ShowDetails(id){
		 
			$.ajax({  
				type:"POST",
					url:ctx+"/inspection/workstatistics/tServiceCharge/forms",  
					dataType : 'json',
				   data: "id="+id,
			       success:function(json){  
			    	   
			    	   $json = json;
			    	   $('#inputForminsert').formEdit($json);
			       },
			       error:function(){
}
			 });  
		};
		 function delDetails(id){
			 $("#dialog_del2 #delectids").val(id);
			 $( "#dialog_del2" ).dialog( "open" );
				event.preventDefault();
			};
 /*function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		 $("#searchFormtServiceCharge").ajaxSubmit({
		        type: 'post',
		        url: ctx+"/inspection/workstatistics/tServiceCharge/listpage" ,
		        dataType : 'json',
		        data: $("#searchForm").serialize(),
		        success: function(data){
		        	 $("#tServiceChargeList").setTemplateElement("template").processTemplate(data);
			          $("#dialog_mon .pagination").html(data.html);
			          $("#dialog_mon #pageNo").val(data.pageNo);
			          $("#dialog_mon #pageSize").val(data.pageSize);
		        },
		        error: function(XmlHttpRequest, textStatus, errorThrown){
		            alert( "error");
		        }                
			 });
		return false;
	}*/
function page(n,s,event){
	
	var e = event || window.event;
	var ss=$(e.srcElement).parent().parent().parent().attr("name");
	  switch(ss){ 
	  
	    case "searchForm":    
	    	$("#searchForm #pageNo").val(n);
	    	$("#searchForm #pageSize").val(s);
	    	$("#searchForm").submit();
	    	return false;
	      break; 
	      
	    case "searchFormtServiceCharge": 
	    	$("#searchFormtServiceCharge #pageNo").val(n);
			$("#searchFormtServiceCharge #pageSize").val(s);
			 $("#searchFormtServiceCharge").ajaxSubmit({
			        type: 'post',
			        url: ctx+"/inspection/workstatistics/tServiceCharge/listpage" ,
			        dataType : 'json',
			        data: $("#searchForm").serialize(),
			        success: function(data){
			        	 $("#tServiceChargeList").setTemplateElement("template").processTemplate(data);
				          $("#dialog_mon .pagination").html(data.html);
				          $("#dialog_mon #pageNo").val(data.pageNo);
				          $("#dialog_mon #pageSize").val(data.pageSize);
			        },
			        error: function(XmlHttpRequest, textStatus, errorThrown){
			            alert( "error");
			        }                
				 });
			return false;

	      break; 
	    case "systCustomerForm": 
	    	$("#systCustomerForm #pageNo").val(n);
	    	$("#systCustomerForm #pageSize").val(s);
	    	pages();
	      break; 
	    case "searchFormfollowUp": 
	    	$("#searchFormfollowUp #pageNo").val(n);
			$("#searchFormfollowUp #pageSize").val(s);
			$("#searchFormfollowUp").ajaxSubmit({
		        type: 'post',
		        url: ctx+"/workterrace/followUp/followUplist" ,
		        dataType : 'json',
		        data: $("#searchFormfollowUp").serialize(),
		        success: function(data){
		           $("#followUpList").setTemplateElement("templateFollowUpForm").processTemplate(data);
		          $("#dialog_edit2 .pagination").html(data.html);
		          $("#dialog_edit2 #pageNo").val(data.pageNo);
		          $("#dialog_edit2 #pageSize").val(data.pageSize);
		          $( "#dialog_edit2" ).dialog( "open" );
		  		event.preventDefault();
		        },
		        error: function(XmlHttpRequest, textStatus, errorThrown){
		        	
		        }                
			 });

	      break; 
	    } 
	  return false;
	
};



var cid="";
var $tag;
function delectCustomer(id){
	$( "#delectCustomer" ).dialog( "open" );
	event.preventDefault();
	cid=id;
    e = event || window.event;
	$tag= $(e.srcElement).parent().parent().parent();
	
}
function delectCustomers(){
	if(cid!="")
		{
			$.post(ctx+"/customer/tCustomer/delectCustomer",{id:cid},function(result){
				$tag.remove();
				$( "#delectCustomer" ).dialog( "close" );
				event.preventDefault();
				
			});
		}
}



 $.fn.formEdit = function(data){
     return this.each(function(){
         var input, name;
         if(data == null){this.reset(); return; }
         for(var i = 0; i < this.length; i++){  
             input = this.elements[i];
             name = (input.type == "checkbox")? input.name.replace(/(.+)\[\]$/, "$1") : input.name;
             if(data[name] == undefined) continue;
             switch(input.type){
                 case "checkbox":
                     if(data[name] == ""){
                         input.checked = false;
                     }else{

                         if(data[name].indexOf(input.value) > -1){
                             input.checked = true;
                         }else{
                             input.checked = false;
                         }
                     }
                 break;
                 case "radio":
                     if(data[name] == ""){
                         input.checked = false;
                     }else if(input.value == data[name]){
                         input.checked = true;
                     }
                 break;
                 case "button": break;
                 default: input.value = data[name];
             }
         }
     });
 };
 

		 function dialogmonss(customerId,customerName){
		//alert(customerId+"   "+customerName);
		$("#inputForminsert #customerId").val(customerId);
		$("#searchFormtServiceCharge #customerId1").val(customerId);
		$("#dialog_mon #customerName1").text(customerName);
		$("#searchFormtServiceCharge").ajaxSubmit({
		        type: 'post',
		        url: ctx+"/inspection/workstatistics/tServiceCharge/listpage" ,
		        dataType : 'json',
		        data: $("#searchFormtServiceCharge").serialize(),
		        success: function(data){
		           $("#tServiceChargeList").setTemplateElement("template").processTemplate(data);
		          $("#dialog_mon .pagination").html(data.html);
		          $("#dialog_mon #pageNo").val(data.pageNo);
		          $("#dialog_mon #pageSize").val(data.pageSize);
		        },
		        error: function(XmlHttpRequest, textStatus, errorThrown){
		        	
		        }                
			 });
		$( "#dialog_mon" ).dialog( "open" );
		event.preventDefault();
		
	};
	
	function newDate(){
		var datediv =$("#datediv");
		datediv.empty();
		var selectdiv="<input type='hidden' name='initperiod'/><input type='hidden' value='' id='currentPeriod'/><input type='hidden' value='' id='initPeriod'/><select class='form-control ipt_wauto' style='margin-right:8px;' id='year_span'></select><select class='form-control ipt_wauto' id='month_span'></select>";
		datediv.append(selectdiv);
		var dateYear = new Date();
		var myers = $("#year_span");
		myers.empty();
		var nowyear=dateYear.getFullYear();
		var oldyear=nowyear-5;
		var newyears=nowyear+5;
		for(var i=oldyear;i<newyears;i++) {	
			if(i==nowyear){
				var option = $("<option selected>").text(i).val(i)
			}else{
				var option = $("<option>").text(i).val(i)
			}
			myers.append(option);
		}

		var nowmonth=dateYear.getMonth()+1;
		var months = $("#month_span");
		months.empty();
		for(var j=1;j<13;j++) {	
			if(j==nowmonth){
				if(j<10){
					var option = $("<option selected>").text('0'+j).val('0'+j);
				}else{
					var option = $("<option selected>").text(j).val(j);
				}
				
			}else{
				if(j<10){
					var option = $("<option>").text('0'+j).val('0'+j);
				}else{
					var option = $("<option>").text(j).val(j);
				}
				
			}
			months.append(option);
		}
		//alert(nowmonth);
		$("[name=initperiod]").val(function(){
			var years=$('#year_span').val();
			var months=$('#month_span').val();
			return years+months;
		});
		$('#year_span').change(function(){
			var years=$(this).val();
			var months=$('#month_span').val()
			$("[name=initperiod]").val(function(){
				console.info(years+months);
				return years+months;
			});
		})
		$('#month_span').change(function(){
			var years=$('#year_span').val();
			var months=$(this).val();	
			$("[name=initperiod]").val(function(){
				console.info(years+months);
				return years+months;
			});
		})
	}
	function stampShoujv(id){
		window.open(ctx+"/inspection/workstatistics/tServiceCharge/stampShoujv?id="+id) ;
	}
	function validateForm() {  
		return $("#inputForminsert").validate({		
		 errorPlacement: function(error, element) {
			  if ( element.is(":radio") )
			    error.appendTo( element.parent().next().next() );
			  else if ( element.is(":checkbox") )
			    error.appendTo ( element.parent().parent().next() );
			  else
				  if(element.attr("name")=="signDate")
				  {
					  error.appendTo( element.next().next() );
				  }else{
					  error.appendTo( element.next() );
				  }
			},
	        rules: {  
	        	signDate: {  required: true},  
	        	loanMoney: {  required: true,number:true,min:0},  
	        	accountbookMoney: {  required: true,number:true,min:0},  
	        	shouldMoney: {  required: true,number:true,min:0},  
	        	realityMoney: {  required: true,number:true,min:0}
	  } 
	  }).form();     
	}