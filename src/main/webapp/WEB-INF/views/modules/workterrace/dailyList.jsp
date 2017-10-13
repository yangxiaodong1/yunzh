<!doctype html>
<html lang="en">
<head>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>日常-工作台-芸豆会计</title>
<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.11.2.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery.ztree.core-3.5.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-ui.min.js"></script>
<link href="${pageContext.request.contextPath}/static/jquery/jquery-ui.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/accountAndbalance/css/defaultTheme.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/master_2.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/bootstrap/css/page.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/css/dialog.css" type="text/css" rel="stylesheet" />
<script	src="${pageContext.request.contextPath}/static/jquery/jquery.form.js"	type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery.ui.datepicker-zh-CN.js.js" charset="gb2312"></script>
<script src="${pageContext.request.contextPath}/static/accountAndbalance/js/jquery.fixedheadertable.js"></script>

	<style>
.user_pane_r2{position:relative;}
#newclients{position:absolute;right:0;top:20px;}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery.ui.datepicker-zh-CN.js.js" charset="gb2312"></script>
		<script type="text/javascript">
		//进度条状态
		$(function(){
		$(".nums_width").each(function(){
			var attrstart=$(this).attr('attrstart');
			var attrend=$(this).attr('attrend');
			var widthLine=attrend-attrstart+1;
			var marginLeft=attrstart-1;
			$(this).css({
				'width':widthLine*55,
				'margin-left':marginLeft*55
			})
		})
		})
	</script>
<style>
.nums_width{width:0px;line-height:0;height:14px;font-size:12px;overflow:hidden;padding-top:0;}
body{overflow:hidden;}
.user_pane_r2{position:relative;}
#newclients{position:absolute;right:0;top:20px;}
.fixed_table{width:960px;margin:0 auto;}
.pane_gzt{padding:0;width:960px;margin:0 auto;}
.pane_main5 {padding:0;width:1065px;margin:0 auto;}
.fht-thead .fht-table th{border:1px #ddd solid;}
.table_jz2 .btn_h4 .btn_i2 {margin-top: -1px;}
.btn_h4 .btn_i4 {background-position: 0 -193px;}
.btn_h4 .btn_i4:hover {background-position: 0 -157px;}
.btn_i7 {background-position: -312px -3px;margin-top:-1px;}
.btn_i7:hover {background-position: -312px -38px;}
.table_jz2 > thead > tr > th, .table_jz2 > tbody > tr > th, .table_jz2 > tfoot > tr > th, .table_jz2 > tfoot > tr > td {  padding: 15px 0;}
.nums_width .pad_3{margin:4px 0;}
.nums_width .sta_c01{color:#ff883b;}
.nums_width .sta_c02{color:#50d4bf;}
.nums_width .sta_c03{color:#a5cefc;}
.nums_width .sta_c01,.nums_width .sta_c02,.nums_width .sta_c03{margin-right:22px;}
</style>	
</head>
<body>
<div class="message-pop" style="top:160px;"><span>保存成功！</span></div>
<div class="main-row">

	<div class="main_index3">
		<div class="pane_main5">
			<div class="t_pane_a1 radius_5">
				<div class="user_pane_r2">
					<div class="hr20"></div>
					<ul class="nav nav-tabs" role="tablist">
						<li><a href="${ctx}/workterrace/chargeToAccount/">记账</a></li>
						<li><a href="${ctx}/workterrace/reportTax/">报税</a></li>
						<li><a href="${ctx}/workterrace/wages/">工资</a></li>
						<li class="active"><a href="${ctx}//workterrace/daily/">日常</a></li>
						<li><a href="${ctx}/workterrace/tPersonalReminder/">个人提醒</a></li>
					</ul>
					<input id="newclients" type="button" class="btn btn-default btn_w_a btn_bg_2 radius0 dialog_add" value="新增客户"/>
					<div class="tab-content main5_con radius_5">
						<div class="tab-pane active" id="tab_f1">
							<div class="hr20"></div>
							
							<div class="pane_gzt">
								<div class="search_form4">
									
										
										<span class="span_year left">
											<form:form id="searchForm" modelAttribute="tCustomer" action="${ctx}/workterrace/daily/list" method="post" >
		<!-- <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/> -->
		<form:hidden path="byYear"/>
		 <input type="button" class="bg_p6 btn_sea1 left" onclick="up()"  value="左"/>
					<form:input class="form-control-static ipt_w5 left"  readonly="true" path="byYearName" htmlEscape="false"/>
					<input type="button" class="bg_p6 btn_sea2 left mar-rig3" onclick="down()" value="右"/>
					
	                                      </form:form>
											
										</span>
									<form id="formsetSession" name="testForm" action="${ctx}/customer/tCustomer/setSession" method="post">
										
		
										<input id="keyword_set" type="text" class="form-control ipt_w5 ipt_txt_s1 mar-rig3 left" value="请输入客户名称进入账簿">
											<input id="sessionCustomerId" name="sessionCustomerId" type="hidden" />
										<button id="enter_customer" type="submit" class="btn btn-default btn_w_a btn_bg_2 radius0 left en_customer">进入账簿</button>
										
										<div class="clearfix"></div>
									</form>
									
									
								</div>
							<div class="hr15"></div>
								<div class="fixed_table">
									<table class="table_jz2" id='myTable01'  collspacing='0' cellpadding='0' border='1' width='960'>
									      <thead>
									        <tr>
									        	<th class='tmynum' width='30'></th>
									         <th class='row_04' width="269">客户名称</th>
									          <th width='55'  title="01" class='month_td'><span class="span_month">01月<input type='hidden' value='1' /></span></th>
									          <th width='55' title="02" class='month_td'><span class="span_month">02月<input type='hidden' value='2' /></span></th>
									          <th width='55'  title="03" class='month_td'><span class="span_month">03月<input type='hidden' value='3' /></span></th>
									          <th width='55'  title="04" class='month_td'><span class="span_month">04月<input type='hidden' value='4' /></span></th>
									          <th width='55'  title="05" class='month_td'><span class="span_month">05月<input type='hidden' value='5' /></span></th>
									          <th width='55'  title="06" class='month_td'><span class="span_month">06月<input type='hidden' value='6' /></span></th>
									          <th width='55'  title="07" class='month_td'><span class="span_month">07月<input type='hidden' value='7' /></span></th>
									          <th width='55'  title="08" class='month_td'><span class="span_month">08月<input type='hidden' value='8' /></span></th>
									          <th width='55'  title="09" class='month_td'><span class="span_month">09月<input type='hidden' value='9' /></span></th>
									          <th width='55'  title="10" class='month_td'><span class="span_month">10月<input type='hidden' value='10' /></span></th>
									          <th width='55'  title="11" class='month_td'><span class="span_month">11月<input type='hidden' value='11' /></span></th>
									          <th  title="12" class='month_td'><span class="span_month">12月<input type='hidden' value='12' /></span></th>
												
									        </tr>
									      </thead>
									      <tbody>


									        <tr class='mytbodytr' style='display:none;'>
									        	<td class='mynum'  width='30'></td>
									         <td class="row_01" width="269">客户名称</td>
									          <td width='55'  title="01" class='month_td'><span class="span_month">01月<input type='hidden' value='1' /></span></td>
									          <td width='55' title="02" class='month_td'><span class="span_month">02月<input type='hidden' value='2' /></span></td>
									          <td width='55'  title="03" class='month_td'><span class="span_month">03月<input type='hidden' value='3' /></span></td>
									          <td width='55'  title="04" class='month_td'><span class="span_month">04月<input type='hidden' value='4' /></span></td>
									          <td width='55'  title="05" class='month_td'><span class="span_month">05月<input type='hidden' value='5' /></span></td>
									          <td width='55'  title="06" class='month_td'><span class="span_month">06月<input type='hidden' value='6' /></span></td>
									          <td width='55'  title="07" class='month_td'><span class="span_month">07月<input type='hidden' value='7' /></span></td>
									          <td width='55'  title="08" class='month_td'><span class="span_month">08月<input type='hidden' value='8' /></span></td>
									          <td width='55'  title="09" class='month_td'><span class="span_month">09月<input type='hidden' value='9' /></span></td>
									          <td width='55'  title="10" class='month_td'><span class="span_month">10月<input type='hidden' value='10' /></span></td>
									          <td width='55'  title="11" class='month_td'><span class="span_month">11月<input type='hidden' value='11' /></span></td>
									          <td title="12" class='month_td'><span class="span_month">12月<input type='hidden' value='12' /></span></td>
												
									        </tr>
									        
								<c:forEach items="${listDailyBySupervisor}" var="tCustomer" varStatus="status">
								              <tr>
										  <td width='30' class='mynum'><span>${status.count}</span></td>
										 <td title="${tCustomer.customerName}" class="row_01" width="269">
							<span id="${tCustomer.id}">
							<c:choose>
   							 <c:when test="${fn:length(tCustomer.customerName)>13}">
							       ${fn:substring(tCustomer.customerName, 0, 13)}...
							    </c:when>
								 <c:otherwise>
								 ${tCustomer.customerName}
							    </c:otherwise>
							</c:choose></span>
								          	<span class="btn_h4 right">
								          	<input id="customerName" type="hidden" value="${tCustomer.customerName}"/>
								          		<a title="编辑" href="javascript:void(0);" class="dialog_add btn btn-default btn_n4 bg_p6 btn_i2 pad_10" title="编辑"></a>
								          		<a title="移除" href="javascript:void(0);" onclick="delectCustomer(${tCustomer.id})" class="btn btn-default btn_n4 bg_p6 btn_i4 pad_10" title="删除"></a>
								          		<a title="收费" href="javascript:void(0);" class="dialog_mon btn btn-default btn_n4 bg_p6 btn_i7 pad_10" title="收费"></a>
								          		<input id="tCustomerid" type="hidden" value="${tCustomer.id}"/>
								          	</span>
								          </td>
								          
								          <td colspan="12" class='td_bg3'>
								          	<div class="line_table4">
									          	<div class="nums_width" attrstart='01' attrend='${tCustomer.montharray[0]}'>
										          	<div class="lines_t5 pad_3 block_c1 radius_5 dialog_edit2 ${tCustomer.montharray[1]}"></div>
										          	<!-- <span class="label block_c1 ${tCustomer.montharray[2]}">已完成</span> -->
										          	<span class="glyphicon glyphicon-ok-circle sta_c01 ${tCustomer.montharray[2]}"></span>
									          	</div>
									          	<div class="nums_width" attrstart='07' attrend='${tCustomer.montharray[3]}'>
									          		<div class="lines_t5 pad_3 block_c2 radius_5 dialog_edit2 ${tCustomer.montharray[4]}" ></div>
									          		<!-- <span class="label block_c2 ${tCustomer.montharray[5]}">已完成</span>-->
									          		<span class="glyphicon glyphicon-ok-circle sta_c02 ${tCustomer.montharray[5]}"></span>
									          	</div>
									          	<div class="nums_width" attrstart='01' attrend='${tCustomer.montharray[6]}'>
									          		<div class="lines_t5 pad_3 block_c3 radius_5 dialog_edit2 ${tCustomer.montharray[7]}" ></div>
									          		<!-- <span class="label block_c3 ${tCustomer.montharray[8]}">已完成</span>-->
									          		<span class="glyphicon glyphicon-ok-circle sta_c03 ${tCustomer.montharray[8]}"></span>
									          	</div>
								          	</div>
								          </td>

								        </tr>
								        </c:forEach>
								      </tbody>
								</table>
								</div>
								<!-- <div name="searchForm" class="pagination">${page}</div> -->
							</div>	
							<div class="clearfix"></div>
							<div class="hr15"></div>
							<div class=" bg_p11 line_h30 font_cc7 font-14 radius_bl5 radius_br5">
								<div class="pane_gzt">
									<span class="left font-12">
										<span class="inline-block pad_5 mar-rig5 block_c1"></span>信息公示&nbsp;&nbsp;已完成&nbsp;<span id="montharray1">${wv.montharray1}</span>&nbsp;&nbsp;未完成&nbsp;<span id="montharray2">${wv.montharray7-wv.montharray1}</span>&nbsp;&nbsp;
										<span class="inline-block pad_5 mar-rig5 block_c2"></span>残疾人保证金缴纳&nbsp;&nbsp;已完成&nbsp;<span id="montharray3">${wv.montharray3}</span>&nbsp;&nbsp;未完成&nbsp;<span id="montharray4">${wv.montharray7-wv.montharray3}</span>&nbsp;&nbsp;
										<span class="inline-block pad_5 mar-rig5 block_c3"></span>企业所得税汇算缴纳&nbsp;&nbsp;已完成&nbsp;<span id="montharray5">${wv.montharray5}</span>&nbsp;&nbsp;未完成&nbsp;<span id="montharray6">${wv.montharray7-wv.montharray5}</span>
									</span>
	<span class="right">
										<a href="${ctx}/workterrace/daily/list" class="left mar-rig20"><span class="btn btn-default btn_n4 bg_p6 btn_i8 pad_10"></span>重新统计</a>
									    <a href="${ctx}/workterrace/recyclebin/list" class="left"><span class="btn btn-default btn_n4 bg_p6 btn_i9 pad_10"></span>回收站</a>
									</span>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="hr10"></div>
					<div class="bg_p6 tips_jz2">
					</div>
					<div class="hr10"></div>
				</div>
			</div>

			<div class="hr20"></div>

		</div>
	</div>
</div>
<%@include file="tServiceChargeList.jsp"%>
<%@include file="updateCustomerInfo.jsp"%>

<script>
var h_1=$(window).height()-245;
$('.fixed_table').height(h_1);
var winh1=$("#myTable01").height();
if(winh1>h_1){
	$('#myTable01').fixedHeaderTable({ 
		autoShow: false
	});
	$('.fixed_table').height(h_1+1);
}
$('.fixed_table .fht-default').show();
$('.fixed_table tbody tr:last').hover(function(){
	$(this).attr("style","border-bottom:1px #3d8ae2 solid");
},function(){
	$(this).attr("style","border-bottom:1px #dddddd solid");
})
$("#myTable01 tbody tr:eq(1)").hover(function(){
	$(".fht-thead th").css({"border-bottom":"1px #3d8ae2 solid"});
},function(){
	$(".fht-thead th").css({"border-bottom":"1px #dddddd solid"});
})
/***20160131***/

$(function(){
	
	
	$(".table_jz2 tr").hover(function(){
		$(this).addClass("thover");
		$(this).prev("tr").addClass("top_lines");
	},function(){
		$(this).removeClass("thover");
		$(this).prev("tr").removeClass("top_lines");
	})

	$("#InsertFollowUp #upTime").on("change",function() {
		$('#InsertFollowUp').find(":checkbox").attr("checked",false); 
		 $('#InsertFollowUp').find(":checkbox").removeAttr("disabled"); 
			var byYears=$("#InsertFollowUp #upTime").val();
			if(byYears.length<7){
				return;
			}
			bymonth=byYears.substring(5, 7);
			if(bymonth>6){
				$('#InsertFollowUp #upContent1').attr("disabled",true);
			}
			if(bymonth<7||bymonth>9){
				$('#InsertFollowUp #upContent2').attr("disabled",true);
			}
			if(bymonth>5){
				$('#InsertFollowUp #upContent3').attr("disabled",true);
			}
			byYears=byYears.substring(0, 4);
			var customerIds=$("#InsertFollowUp #customerId3").val();
			$.ajax({  
				type:"GET",
					url:"${ctx}/workterrace/followUp/findUpContent" ,
				   data: {byYear:byYears,customerId:customerIds},
			       success:function(json){ 
			    	 // alert(json);
			    	  var strs= new Array(); //定义一数组 
			    	  strs=json.split(","); //字符分割 
			    	  for (i=0;i<strs.length ;i++ ) 
			    	  { 
			    		  switch (strs[i])
			    		  {
			    		  case "信息公示已完成":
			    			  $('#InsertFollowUp #upContent1').attr("disabled",true);
			    		    continue;
			    		    break;
			    		  case "残疾人保证金缴纳":
			    			  $('#InsertFollowUp #upContent2').attr("disabled",true);
			    		    continue;
			    		    break;
			    		  case "企业所得税汇算缴纳":
			    			  $('#InsertFollowUp #upContent3').attr("disabled",true);
			    			continue;
			    		 	break;
			    		  } 
			    	  } 
			       },
			       error:function(){
			    	   
			       }
			 });  
			
	})
	$(".fixed_table thead th").on("click",function() {
		$(".fixed_table th span").removeClass("curr_span");
		$("span",this).addClass("curr_span");
		var month=$(this).attr("title");
		var yearmonth= $("#byYear").val()+$(this).attr("title");
		
		
		$.post("${ctx}/workterrace/daily/getCount",{byYearMonth:yearmonth},function(result){
			
			$(".pane_gzt #montharray1").text(result.montharray1);
			$(".pane_gzt #montharray2").text(result.montharray7-result.montharray1);
			$(".pane_gzt #montharray3").text(result.montharray3);
			$(".pane_gzt #montharray4").text(result.montharray7-result.montharray3);
			$(".pane_gzt #montharray5").text(result.montharray5);
			$(".pane_gzt #montharray6").text(result.montharray7-result.montharray5);
		});
	})
	
	var mydate = new Date();
	var year = "" + mydate.getFullYear();
	var month = mydate.getMonth()+1;
	   
	$("#bymonth").text(month+"");
	 $(".fixed_table thead th").eq(month+1).find("span").addClass("curr_span");
	
	 
		 $("#tab_f1 table tbody ").delegate("div","click",function(){
		var customerId=$(this).parent().parent().find("input:last").val();
		var customername=$(this).parent().parent().find("input:first").val();
		var byYear=$("#byYear").val();
		//alert(customerId +"    "+customername);
		$("#searchFormfollowUp #customerId2").val(customerId);
		$("#InsertFollowUp #customerId3").val(customerId);
		$("#searchFormfollowUp #byYear").val(byYear);
		$("#InsertFollowUp #customerName2").text(customername);
		
		findUpContent();
		$("#searchFormfollowUp").ajaxSubmit({
		        type: 'post',
		        url: "${ctx}/workterrace/followUp/followUplist" ,
		        dataType : 'json',
		        data: $("#searchFormfollowUp").serialize(),
		        success: function(data){
		           $("#followUpList").setTemplateElement("templateFollowUpForm").processTemplate(data);
		          $("#dialog_edit2 .pagination").html(data.html);
		          $("#dialog_edit2 #pageNo").val(data.pageNo);
		          $("#dialog_edit2 #pageSize").val(data.pageSize);
		          
		          findUpContent();
		          var byYears=$("#InsertFollowUp #upTime").val();
		         
					if(byYears.length<7){
						return;
					}
					bymonth=byYears.substring(5, 7);
					
					if(bymonth>6){
						$('#InsertFollowUp #upContent1').attr("disabled",true);
					}
					if(bymonth<7||bymonth>9){
						$('#InsertFollowUp #upContent2').attr("disabled",true);
					}
					if(bymonth>5){
						$('#InsertFollowUp #upContent3').attr("disabled",true);
					}
		          $( "#dialog_edit2" ).dialog( "open" );
		  		event.preventDefault();
		        },
		        error: function(XmlHttpRequest, textStatus, errorThrown){
		        	
		        }                
			 });
		
		
		
		
		
		
		$( "#dialog_edit2" ).dialog( "open" );
		event.preventDefault();
	})
	  
	//新增跟进记录
	$( "#dialog_edit2" ).dialog({
		autoOpen: false,
		width: 840,
		modal: true
	});	
	/*$( ".dialog_edit2" ).click(function( event ) {
		
	});*/
	// 确定弹窗
	$( ".dialog_sure" ).click(function( event ) {
		var arg = arguments;
		var sure = $("#dialog_sure");
		if(!arg.callee.open) {
			arg.callee.open = true;
			sure.css("display","block");
			sure.animate({
				top:"10px",
			},500,function() {
				setTimeout(function() {
					sure.css({
						top:0,
						display:"none"
					});
					arg.callee.open = false;
				},1000);
			});
		}
		event.preventDefault();
	});
	/*日期*/
	$( "#datepicker1" ).datepicker({
		dateFormat:'yy-mm-dd'
	}).attr('readonly',true);
	$( "#datepicker2" ).datepicker({
		dateFormat:'yy-mm-dd'
	}).attr('readonly',true);
	
	$( "#upTime" ).datepicker({
		dateFormat:'yy-mm-dd',
		showOn: "button",
		buttonImage: "${pageContext.request.contextPath}/static/imgs/04.jpg",
		buttonImageOnly: true,
		buttonText: "选择日期"
		
	}).attr('readonly',true);

	$( ".delFollowUp" ).click(function( event ) {
		var id= $("#dialog_del3 #delectids").val();
		
		$.ajax({  
			type:"POST",
			 url: "${ctx}/workterrace/followUp/delect" ,
				dataType : 'json',
			   data: "id="+id,
		       success:function(data){  
		    	  
		    	   messagePop("删除成功！");
		    	   $("#followUpList").setTemplateElement("templateFollowUpForm").processTemplate(data);
			          $("#dialog_edit2 .pagination").html(data.html);
			          $("#dialog_edit2 #pageNo").val(data.pageNo);
			          $("#dialog_edit2 #pageSize").val(data.pageSize);
			          
			          findUpContent();
			          
			      	$( "#dialog_del3" ).dialog( "close" );
					event.preventDefault();
		       },
		       error:function(){
}
		 });  
	
		return;
	});
	$( ".dialog_closefollowUp" ).click(function( event ) {
		$( "#dialog_del3" ).dialog( "close" );
		event.preventDefault();
	});
	//复选变单选
	$(":checkbox").click(function(){ 
	
			$(this).parent().siblings().find(":checkbox").attr("checked",false); 
			$(this).attr("checked",true); 
			$("#remarks1").val($(this).val());
			
	}); 
	
	
})
function InsertFollowUp(){
	if(!validateInsertFollowUpForm()){
		return;
	}
	$("#InsertFollowUp").ajaxSubmit({
        type: 'post',
        url: "${ctx}/workterrace/followUp/save" ,
        dataType : 'json',
        data: $("#InsertFollowUp").serialize(),
        success: function(data){
    		
    		messagePop("保存成功！");
        	
    		$("#followUpList").setTemplateElement("templateFollowUpForm").processTemplate(data);
	          $("#dialog_edit2 .pagination").html(data.html);
	          $("#dialog_edit2 #pageNo").val(data.pageNo);
	          $("#dialog_edit2 #pageSize").val(data.pageSize);
	          resutf();
	          findUpContent();
	          
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
        	
        }   
        
	 });
	
	 findUpContent();
 return false;
}
function formfollowUp(id){
	
	$.ajax({  
		type:"POST",
			url:"${ctx}/workterrace/followUp/form" ,
			dataType : 'json',
		   data: "id="+id,
	       success:function(json){ 
	    	   $json = json;
	    	   $('#InsertFollowUp').formEdit($json);
	       },
	       error:function(){
}
	 });  
	 
}
function delfollowUp(id){
	 $("#dialog_del3 #delectids").val(id);
	 $( "#dialog_del3" ).dialog( "open" );
		event.preventDefault();
}


function findUpContent(){
	$('#InsertFollowUp').find(":checkbox").attr("checked",false); 
	 $('#InsertFollowUp').find(":checkbox").removeAttr("disabled"); 
	var byYears=$("#InsertFollowUp #upTime").val();
	
	if(byYears.length<7){
		return;
	}
	bymonth=byYears.substring(5, 7);
	if(bymonth>6){
		$('#InsertFollowUp #upContent1').attr("disabled",true);
	}
	if(bymonth<7||bymonth>9){
		$('#InsertFollowUp #upContent2').attr("disabled",true);
	}
	if(bymonth>5){
		$('#InsertFollowUp #upContent3').attr("disabled",true);
	}
	if(byYears.length<4){
		return;
	}
	byYears=byYears.substring(0, 4);
	var customerIds=$("#InsertFollowUp #customerId3").val();
	$.ajax({  
		type:"GET",
			url:"${ctx}/workterrace/followUp/findUpContent" ,
		   data: {byYear:byYears,customerId:customerIds},
	       success:function(json){ 
	    	 // alert(json);
	    	  var strs= new Array(); //定义一数组 
	    	  strs=json.split(","); //字符分割 
	    	  
	    	  $("#InsertFollowUp .checkbox-inline .label").remove();
	    	  for (i=0;i<strs.length ;i++ ) 
	    	  { 
	    		  switch (strs[i])
	    		  {
	    		  case "信息公示已完成":
	    			  $('#InsertFollowUp #upContent1').attr("disabled",true);
	    			  if($('#InsertFollowUp #upContent1').parent().find("span").length<2){
	    			 		 $('#InsertFollowUp #upContent1').parent().append("<span class=\"label block_c1\">已完成</span>");
	    			 		$(".checkbox-inline .label").show();
	    			  }
	    		    continue;
	    		    break;
	    		  case "残疾人保证金缴纳":
	    			  $('#InsertFollowUp #upContent2').attr("disabled",true);
	    			  if($('#InsertFollowUp #upContent2').parent().find("span").length<2){
	    			 		 $('#InsertFollowUp #upContent2').parent().append("<span class=\"label block_c2\">已完成</span>");
	    			 		$(".checkbox-inline .label").show();
	    			  }
	    		    continue;
	    		    break;
	    		  case "企业所得税汇算缴纳":
	    			  $('#InsertFollowUp #upContent3').attr("disabled",true);
	    			  if($('#InsertFollowUp #upContent3').parent().find("span").length<2){
	    			 		 $('#InsertFollowUp #upContent3').parent().append("<span class=\"label block_c3\">已完成</span>");
	    			 		$(".checkbox-inline .label").show();
	    			  }
	    			continue;
	    		 	break;
	    		  } 
	    	  } 
	       },
	       error:function(){
}
	 });  
	 
	
}

function resutf(){
	$('#InsertFollowUp #id').val("");
	$('#InsertFollowUp #phone').val("");
	 $('#InsertFollowUp #linkman').val("");
	 $('#InsertFollowUp #phone').val("");
	 $('#InsertFollowUp #remarks1').val("");
	 $('#InsertFollowUp').find(":checkbox").attr("checked",false); 
    }
  function up(){
    	var aa=$("#byYear").val();
    	var bb=Number(aa)-1;
		$("#byYear").val(bb);
		$("#searchForm").submit();
    	return false;
    };
    function down(){
    	var aa=$("#byYear").val();
    	var bb=Number(aa)+1;
		$("#byYear").val(bb);
		$("#searchForm").submit();
    	return false;
    };
 var ctx = "${ctx}";
 var contextPath="${pageContext.request.contextPath}";
 
 function validateInsertFollowUpForm() {  
		return $("#InsertFollowUp").validate({		
		 errorPlacement: function(error, element) {
			  if ( element.is(":radio") )
			    error.appendTo( element.parent().next().next() );
			  else if ( element.is(":checkbox") )
			    error.appendTo ( element.parent().parent().next() );
			  else
				  if(element.attr("name")=="upTime")
				  {
					  error.appendTo( element.next().next() );
				  }else{
					  error.appendTo( element.next() );
				  }
			},
	        rules: {  
	        	upTime: {  required: true},  
	        	linkman: {  required: true},  
	        	phone: {  required: true},  
	        	remarks1: {  required: true}
	  } 
	  }).form();     
	}
</script>

<!--删除跟进-->
<div id="dialog_del3" title="系统提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center">您确定要“删除”该条跟进信息吗？</div>
		<div class="hr40"></div>

		<div class="text-center">
		<input id="delectids" type="hidden"/>
			<button  class="btn delFollowUp btn-default btn_w_a btn_bg_2 mar-rig10">确认</button>
			<button class="btn dialog_closefollowUp btn_w_a btn_bg_4">取消</button>
			
		</div>
		
		
	</div>
</div>
<!--新增跟进记录-->
<div id="dialog_edit2" title="新增跟进记录">
	<div class="client_inner2">
		<div class="client_form_a1">
		
			<form:form id="InsertFollowUp" modelAttribute="followUp" action="${ctx}/workterrace/followUp/save" method="post" >
		
		<span style="display: none">
	<form:input id="id" path="id" />
				</span>	
	
	<form:hidden id="customerId3" path="customerId"/>
				<div class="form-group">
					<label class="labelname5 left control-label">公司名称：</label>
					
					<div class="labelcon9 left">
						<span id="customerName2" class="com_name7"></span>
					</div>
					<label class="labelname5 control-label left">时间：</label>
					<div class="left line_h26 w170">
						<input style="width:145px;float:left;" id="upTime" name="upTime" type="text" readonly="readonly" class="form-control ipt_w4" 
					value="<fmt:formatDate value="${followUp.upTime}" pattern="yyyy-MM-dd"/>"/>
					<span class="help-inline"></span>
					 	<div class="clearfix"></div>
					</div>
					<div class="clearfix"></div>
				</div>				
				<div class="form-group">

					<label class="labelname5 left control-label">联系人：</label>
					<div class="labelcon9 left">
					 	<form:input path="linkman" htmlEscape="false" class="form-control ipt_w4"/>
					 	<span class="help-inline"></span>
					</div>

					<label class="labelname5 control-label left">联系号码：</label>
					<div class="left line_h26 w170">
						<form:input path="phone" htmlEscape="false" class="form-control ipt_w5"/><span  class="help-inline"></span>
					</div>
					<div class="clearfix"></div>
				</div>

				<div class="form-group">
					<label class="labelname5 left control-label">跟进内容：</label>
					<div class="labelcon8 left">
					 	<form:textarea path="remarks1" htmlEscape="false" maxlength="300" class="form-control textarea_n3" rows="3"/><span class="help-inline"></span>
					 	<div class='hr10'></div>
					 	<label class="checkbox-inline">
						   <form:checkbox path="upContent" htmlEscape="false" value="信息公示已完成" /><span>信息公示</span>
						</label>
						<label class="checkbox-inline">
						 <form:checkbox path="upContent" htmlEscape="false" value="残疾人保证金缴纳" /><span>残疾人保证金缴纳</span>
						</label>
						<label class="checkbox-inline">
						    <form:checkbox path="upContent" htmlEscape="false" value="企业所得税汇算缴纳" /><span>企业所得税汇算缴纳  </span>
						</label>
					</div>
					<div class="clearfix"></div>
				</div>
				
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<span class="right">
						<input type="button" value="确定" onclick="InsertFollowUp()" class="btn btn-default btn_w_a btn_bg_2 mar-rig10"/>
						<input type="button" onclick="resutf()" class="btn btn-default btn_w_a btn_bg_4" value="重置"/>
						
						</span>
						<div class="right"></div>
					</div>
					<div class="clearfix"></div>
				</div>
			</form:form>
		</div>
	</div>
	
	<div class="pad_l_r15">
	
		<div class="tit_sc3 font-14 border_top1"><b>跟进列表</b></div>
		
			<form:form id="searchFormfollowUp" modelAttribute="followUp" action="${ctx}/inspection/workterrace/followUp/list" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<form:hidden id="customerId2" path="customerId"/>
					</form:form>
		<table class="table table-bordered table_list4">
			<thead>
				<tr>
					<th>跟进时间</th>
					<th>跟进内容</th>
					<th>联系人</th>
					<th>跟进人</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="followUpList">
			</tbody>
		</table><div name="searchFormfollowUp" class="pagination">${page}</div>
		<!--  <div class="pad_10_3">
			<div class="page_cc">共2条记录
				<button class="btn_p7"></button>
				<span class="pad_l_r5">1</span>
				<button class="btn_p8"></button>
			</div>
		</div>-->
	</div>
	
	<div id="dialog_sure1" class="dialog_sure">
		<div class="dialog">保存成功!</div>
	</div>
	<div id="dialog_delect1" class="dialog_sure">
		<div class="dialog">删除成功!</div>
	</div>
	<div id="dialog_error1" class="dialog_sure">
		<div class="dialog">页面君有点累，刷新页面试试</div>
	</div>
</div>
<!-- Templates -->
<p style="display:none"><textarea id="templateFollowUpForm" ><!--

{#foreach $T.list as followUp}
<tr>
				<td>{$T.followUp.upTime}</td>
				<td>{$T.followUp.remarks1}</td>
				<td>{$T.followUp.linkman}</td>
				<td>{$T.followUp.userName}</td>
				<td>
					<a title="编辑" onclick="formfollowUp('{$T.followUp.id}')" href="javascript:void(0);" class="btn btn-default btn_n4 bg_p6 btn_i2 pad_10 mar-top1 " href="#"></a>
					<a title="删除" onclick="delfollowUp('{$T.followUp.id}')" href="javascript:void(0);" class="btn btn-default btn_n4 bg_p6 btn_i4 pad_10" href="#"></a>
			</tr>
		   {#/for}
-->
</textarea></p>
 <!-- Output elements -->	
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/gotoAccountBook.js"></script>
</body>
</html>