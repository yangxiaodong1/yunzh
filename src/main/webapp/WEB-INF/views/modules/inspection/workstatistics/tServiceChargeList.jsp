<!doctype html>
<html lang="en">
<head>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
	<title>收费审核-工作统计-芸豆会计</title>
	<script src="${pageContext.request.contextPath}/static/jquery/jquery.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-ui.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/jquery/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
	<script src="${ctxStatic}/fixedheadertable/jquery.fixedheadertable.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery.ztree.core-3.5.js"></script>
	<link href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/static/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/zTreeStyle/zTreeStyle.css" type="text/css">
	<link href="${pageContext.request.contextPath}/static/css/master_2.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
	<link href="${pageContext.request.contextPath}/static/bootstrap/css/page.css" type="text/css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/static/css/dialog.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/fixedheadertable/defaultTheme.css" rel="stylesheet" media="screen" />
	
	<script src="${pageContext.request.contextPath}/static/jquery-validation/1.11.1/jquery.validate.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/static/jquery-validation/1.11.1/localization/messages_zh.js" type="text/javascript"></script>
<style type="text/css">  
  	table.ui-datepicker-calendar {  
        display: none;// 不显示日期面板  
    }  
    .help-inline {color: red;}
    .ipt_txt_s1 {width: 280px;}
    label.note {line-height:24px;}
    .checkbtn {vertical-align:-2px;}
</style>  
</head>
<body>
	<div class="main_index3">
		<div class="pane_main5">
			<div class="t_pane_a1 radius_5">
				<div class="user_pane_r2">
					<div class="hr20"></div>
					<ul class="nav nav-tabs" role="tablist">
						<li><a href="${ctx}/inspection/workstatistics/tWorkGather/">工作量汇总表</a></li>
		<li><a href="${ctx}/inspection/workstatistics/tWorkInfo/">工作量明细表</a></li>
	<li><a href="${ctx}/inspection/workstatistics/tServeInfo/">服务收费报表</a></li>
	<li class="active"><a href="${ctx}/inspection/workstatistics/tServiceCharge/">收费审核</a></li>
	</ul>
					<div class="tab-content main5_con radius_5">
						<div class="tab-pane active" id="tab_f1">
							<div class="hr20"></div>
							
							<div class="pane_gzt">
								<div class="search_form4">
								
								<form:form id="searchForm" class="datachose" modelAttribute="tServiceCharge" action="${ctx}/inspection/workstatistics/tServiceCharge/" method="post">
									<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
									<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
									<form:input path="customerName" htmlEscape="false" class="form-control ipt_w5 ipt_txt_s1 mar-rig3 left"/>
									<input id="btnSubmit" class="btn btn-default btn_w_a btn_bg_2 radius0 left" type="submit" value="查询"/>
									<span class="right">
										<div class="border_01 select left text-center calender_width">
											<input name="beginSignDate" type="text" readonly="readonly" class="form-conrotl datepicker text-center" value="<fmt:formatDate value="${tServiceCharge.beginSignDate}" pattern="yyyy-MM"/>"/> 
											<div class="cal_bg right pad_l_r8"></div>
										</div>
										<span class="left con">—</span> 
										<div class="border_01 select left mar-rig10 text-center calender_width">
											<input name="endSignDate" type="text" readonly="readonly" class="form-conrotl datepicker  text-center" value="<fmt:formatDate value="${tServiceCharge.endSignDate}" pattern="yyyy-MM"/>"/>
											<div class="cal_bg right pad_l_r8"></div>
										</div>
										<form:checkbox  path="states" class="lab checkbtn" htmlEscape="false"/>
										<label class="note">包含已审核收费</label>
									</span>
									<div class="clearfix"></div>
								</form:form>
								
								</div>
								<div class="hr15"></div>
								<div class="table_container">
									<table class="table table_jz2" id="myTable02">
								      <thead>
								        <tr>
								          <th>客户名称</th>
								          <th>服务期间</th>
								          <th>应收款</th>
								          <th>实收款</th>
								          <th>收款人</th>	
								          <th>状态</th>			
								          <th>操作</th>	        
								        </tr>
								      </thead>
								      <tbody>
								      <c:forEach items="${tServiceChargeList}" var="tServiceCharge" varStatus="status">
												<tr>
												
													
													 <th scope="row" class="row_01">
																	          	<label>${status.count}</label>${tServiceCharge.customerName}
																	       </th>
													<td>
														${tServiceCharge.serviceDate}
													</td>
													<td>
														${tServiceCharge.shouldMoney}
													</td>
													<td>
														${tServiceCharge.realityMoney}
													</td>
													<td>
														${tServiceCharge.loginName}
													</td>
													<td>
														
												<td><input  type="hidden" value="${tServiceCharge.id}"/>
												<c:if test="${tServiceCharge.state=='0'}">
												<a title="确认审核" href="javascript:void(0);" class=" dialog_del btn btn_yes pad_10" ></a>
								    				<a title="打印收据" href="javascript:void(0);" class="stampShoujv btn bg_p6 btn_i3 pad_10"></a>
								    				<a title="编辑" href="javascript:void(0);" class="form btn bg_p6 btn_i2 btn_edit pad_10"></a>	
								    				</c:if>
								    				<c:if test="${tServiceCharge.state=='1'}">
								    				<a title="打印收据" href="javascript:void(0);" class="stampShoujv btn bg_p6 btn_i3 pad_10" href="#"></a>
								    				</c:if>
								    				</td>
											</tr>
										</c:forEach>
									   
	
									      </tbody>
									</table>
								</div>
								<%-- <div class="pagination">${page}</div> --%>
								<div style="height:22px;"></div>
							</div>
						</div>
					</div>		
				</div>
			</div>
		</div>
	</div>
	
		
<script type="text/javascript">
function page(n,s){
	$("#pageNo").val(n);
	$("#pageSize").val(s);
	$("#searchForm").submit();
	return false;
}

$( ".stampShoujv" ).click(function() {
	var id=$(this).parent().find("input:first").val();
	window.open("${ctx}/inspection/workstatistics/tServiceCharge/stampShoujv?id="+id) ;
});
$( ".form" ).click(function(event) {
	$.ajax({  
		type:"POST",
			url:"forms",  
			dataType : 'json',
		   data: "id="+$(this).parent().find("input:first").val(),
	       success:function(json){  
	    	   
	    	   $json = json;
	    	   $("#customerName1").text($json.customerName);
	    	   $("#inputForminsert").formEdit($json);
	    	   $( "#dialog_mon" ).dialog( "open" );
		   		event.preventDefault();
	       },
	       error:function(){
}
	 });  
});
function fixTableHeader(baseHeight, containerheight) {
	$(".table_container").css({
		"height":containerheight
	})
	if($("#myTable02 tbody").height() >= baseHeight) {
    	$('#myTable02').fixedHeaderTable({ footer: true, altClass: 'odd' });
    	$(".fht-table-wrapper .fht-tbody").css({
    		"height": baseHeight,
    		"overflow-y":"auto",
    		"overflow-x":"inherit"
    	});
    }
}
function fixTableHead(height, box, siderbar) {
$(".table_containerbox").css({
	"height":$(window).height() - box
})
$(".sidebar_user").css({
	"height":$(window).height() - siderbar
})
if(height >= $(window).height() - 215) {
   	var tableWidth = $(".table_containerbox").width();
    var scale = (1 - 17/tableWidth)*100 + "%";
    $(".table_first").css({"width":scale});
}else {
 	$(".table_first").css({"width":"100%"});
 	if(height == 0) {
 		$(".table_containerbox").css("border","none");
 	}
}
}
	$(function(){
		fixTableHeader($(window).height() - 212, $(window).height() - 173)
		$( "#signDate" ).datepicker({
			dateFormat:'yy-mm-dd',
			showOn: "button",
			buttonImage: "${pageContext.request.contextPath}/static/imgs/04.jpg",
			buttonImageOnly: true,
			buttonText: "选择日期"
		});
		$( ".ui-datepicker-trigger" ).on("click",function() {
			console.log(1)
	 		$("table.ui-datepicker-calendar").css("display","table"); 
		});
		$( ".dialog_monClose" ).click(function( event ) {
			$( "#dialog_mon" ).dialog( "close" );
			event.preventDefault();
		});
		$( "#dialog_del" ).dialog({
			autoOpen: false,
			width: 470,
			modal: true
		});
		var $thisparent;
		$( ".dialog_del" ).click(function( event ) {
			$thisparent=$(this).parent();
			$( "#dialog_del" ).dialog( "open" );
			event.preventDefault();
		});
		
		$( ".auditing" ).click(function(event) {
			var ids=$thisparent.find("input:first").val();
			$.post("${ctx}/inspection/workstatistics/tServiceCharge/auditing",{id:ids},function(result){
				$thisparent.prev().html("已审核");
				$thisparent.find("a:even").remove();
				$( "#dialog_del" ).dialog( "close" );
				event.preventDefault();
			});
		});
		
		$( ".dialog_close4" ).click(function( event ) {
			$( "#dialog_del" ).dialog( "close" );
			event.preventDefault();
		});
		$( "#signDate" ).datepicker({
			dateFormat:'yy-mm-dd'
		});
		$( "#dialog_mon" ).dialog({
			autoOpen: false,
			width: 840,
			modal: true
		});	
	/*日期*/
	$( ".datepicker" ).datepicker({
		  monthNamesShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],  // 区域化月名为中文  
		  prevText: '上月',         // 前选按钮提示  
		  nextText: '下月',         // 后选按钮提示  
		           changeYear: true,          // 年下拉菜单  
		           changeMonth: true,             // 月下拉菜单  
		           showButtonPanel: true,         // 显示按钮面板  
		           showMonthAfterYear: true,  // 月份显示在年后面  
		           currentText: "本月",         // 当前日期按钮提示文字  
		           closeText: "确认",           // 关闭按钮提示文字  
		           dateFormat: "yy-mm",       // 日期格式  
		  onClose: function(dateText, inst) {// 关闭事件  
		      var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();  
		      var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();  
		      $(this).datepicker('setDate', new Date(year, month, 1));  
		  }  
	});

	$(".table_jz2 tbody tr").hover(function(){
		$(this).addClass("thover2 font_cc4");
		$(".btn_i3",this).addClass("btn_doc_hover");
		$(".btn_i2",this).addClass("btn_edit_hover");
	},function(){
		$(this).removeClass("thover2 font_cc4");
		$(".btn_i3",this).removeClass("btn_doc_hover");
		$(".btn_i2",this).removeClass("btn_edit_hover");
	})
})
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
 function savetServiceCharge(){
		
	 $("#inputForminsert").ajaxSubmit({
	        type: 'post',
	        url: ctx+"/inspection/workstatistics/tServiceCharge/saves" ,
	        dataType : 'json',
	        data: $("#inputForminsert").serialize(),
	        success: function(data){
	        	 $("#tServiceChargeList").setTemplateElement("template").processTemplate(data);
	        	
		          $("#dialog_mon .pagination").html(data.html);
		          $("#dialog_mon #pageNo").val(data.pageNo);
		          $("#dialog_mon #pageSize").val(data.pageSize);
	        },
	        error: function(XmlHttpRequest, textStatus, errorThrown){
	        
	        }   
	        
		 });
	
	 return false;
}
</script>
<div class="displayCss" id="dialog_mon" title="收费" style="width:840px;">
	<div class="client_inner2">
		<div class="client_form_f2">
		<form  class="form-horizontal">
		
				</form>
		<form:form id="inputForminsert" modelAttribute="tServeInfoinsert" action="${ctx}/inspection/workstatistics/tServiceCharge/save" method="post" >
	<span style="display: none">
	<form:input id="id" path="id" />
				</span>	
					
<form:hidden class="customerId" path="customerId"/>

	<div class="form-group">
						
					<label class="labelname5 left control-label">公司名称：</label>
					<div class="labelcon6 left">
						<span id="customerName1" class='com_name7'></span>
					</div>
					<label class="labelname7 control-label">签约日期：</label>
					<div class="right line_h26 w170">

					<input name="signDate" id="signDate" type="text" readonly="readonly" class="form-control ipt_w5" style="width:145px;float:left;" value="<fmt:formatDate value="${tServiceCharge.signDate}" pattern="yyyy-MM-dd"/>"/> 
						<div class="clearfix"></div>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="form-group">
				
				
					

					<label class="labelname5 left control-label">服务期限：</label>
					<div class="labelcon6 left">

						<form:select path="serviceDate1"  class="form-control ipt_w5 ipt_w60">
							
							<option value="2016">2016</option>
							<option value="2017">2017</option>
							<option value="2018">2018</option>
							<option value="2019">2019</option>
							<option value="2020">2020</option>
						</form:select>
						<span>年</span>
						<form:select path="serviceDate2" class="form-control ipt_w5 ipt_w40">
							<option value="01">01</option>
							<option value="02">02</option>
							<option value="03">03</option>
							<option value="04">04</option>
							<option value="05">05</option>
							<option value="06">06</option>
							<option value="07">07</option>
							<option value="08">08</option>
							<option value="09">09</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
						</form:select>
						<span>月</span>
						<span>~</span>
						<form:select path="serviceDate3" class="form-control ipt_w5 ipt_w60">
								
							<option value="2016">2016</option>
							<option value="2017">2017</option>
							<option value="2018">2018</option>
							<option value="2019">2019</option>
							<option value="2020">2020</option>
						</form:select>
						<span>年</span>
						<form:select path="serviceDate4" class="form-control ipt_w5 ipt_w40">
							<option value="01">01</option>
							<option value="02">02</option>
							<option value="03">03</option>
							<option value="04">04</option>
							<option value="05">05</option>
							<option value="06">06</option>
							<option value="07">07</option>
							<option value="08">08</option>
							<option value="09">09</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
						</form:select>
						<span>月</span>
					</div>
					<label class="labelname7 control-label ">付款方式：</label>
					<div class="right line_h26 w170">


					<form:select path="modePayment" class="form-control ipt_w5">
						<option value="0">按月收费</option>
						<option value="1">按季收费</option>
						<option value="2">按年收费</option>
					</form:select>
						</div>
					<div class='clearfix'></div>
				</div>

				<div class="form-group">
					<label class="labelname5 left control-label">代账收费（月）：</label>
					<div class="labelcon6 left">
						<div class="line_h26 w170">

					<form:input path="loanMoney" htmlEscape="false" class="form-control ipt_w5 ipt_b80 left mar-rig5"/>元<span class="help-inline"></span>
					</div></div>

					<label class="labelname7 control-label">账本收费（年）：</label>
					<div class="right line_h26 w170">
						<div class="line_h26 w170 mar-rig10">

					<form:input path="accountbookMoney" htmlEscape="false" class="form-control ipt_w5 ipt_b80 leftmar-rig5"/>元<span class="help-inline"></span>
					</div></div>
<div class='clearfix'></div>
					

				</div>
				<div class='clearfix'></div>
				<div class="form-group">
					<label class="labelname5 left control-label">应收款：</label>
					<div class="labelcon6 left">
						<div class="line_h26 w170">
						<form:input path="shouldMoney" htmlEscape="false" class="form-control ipt_w5 ipt_b80 "/>&nbsp;元<span class="help-inline"></span>
					</div></div>
					<label class="labelname7 control-label">实际收款：</label>
					<div class="right line_h26 w170">
						<div class="line_h26 w170">
						<form:input path="realityMoney" htmlEscape="false" class="form-control ipt_w5 ipt_b80 "/>&nbsp;元<span class="help-inline"></span>
					</div></div><div class='clearfix'></div>
				</div>

				<div class="form-group">
					<label class="labelname5 left control-label">特殊事项说明：</label>
					<div class="labelcon8 left">
					<form:textarea path="remark" htmlEscape="false" maxlength="300" class="form-control textarea_n3" rows="3" style="width:100%;"/>
					</div>
					<div class='clearfix'></div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<span class="right">
	  					<input type="submit" value="确定"  class="btn btn-default btn_w_a btn_bg_2 mar-rig10"/>
						<!-- <input type="button"  class="dialog_monClose btn btn-default btn_w_a btn_bg_4" value="取消"/> -->
						<br/>
						</span>
						<div class="right"></div>
						
	
					</div>
				</div>
			</form:form>
		</div>
	</div>
	
</div>
<!--移除部门-->
<div id="dialog_del" class="displayCss" title="系统提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center">您确定要通过这条收费审核吗？</div>
		<div class="hr40"></div>
		<form>
		<div class="text-center">
			<input type="button" value="确定" class="btn auditing btn-default btn_w_a btn_bg_2 mar-rig10"/>
			<input type="button"  class="btn dialog_close4 btn_w_a btn_bg_4" value="取消"/>
		</div>
		</form>
	</div>
</div>
<script>
$().ready(function() {  
	$("#inputForminsert").validate({
		 errorPlacement: function(error, element) {
			  if ( element.is(":radio") )
			    error.appendTo( element.parent().next().next() );
			  else if ( element.is(":checkbox") )
			    error.appendTo ( element.parent().parent().next() );
			  else
			    error.appendTo( element.next() );
			},
	        rules: {  
	        	loanMoney: {  required: true,number:true,min:0},  
	        	accountbookMoney: {  required: true,number:true,min:0},  
	        	shouldMoney: {  required: true,number:true,min:0},  
	        	realityMoney: {  required: true,number:true,min:0}
	  }  
	        /*messages: {  
	        	loanMoney: "*",
	        	accountbookMoney: "*",
	        	shouldMoney: "*",
	        	realityMoney: "*"
	    
	 
	  }*/  
	  });    });  
	  /*
$(".NumDecText").keyup(function(){    
    $(this).val($(this).val().replace(/[^0-9.]/g,''));    
}).bind("paste",function(){  //CTR+V事件处理    
    $(this).val($(this).val().replace(/[^0-9.]/g,''));     
}).css("ime-mode", "disabled"); //CSS设置输入法不可用    */
	</script>
</body>
</html>