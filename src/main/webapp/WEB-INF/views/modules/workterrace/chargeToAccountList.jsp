<!doctype html>
<html lang="en">
<head>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>记账-工作台-芸豆会计</title>
<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
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
<script src="${pageContext.request.contextPath}/static/accountAndbalance/js/jquery.fixedheadertable.js"></script>
<script src="${pageContext.request.contextPath}/static/jquery/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>

<style>
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
.table_jz2 > thead > tr > th, .table_jz2 > tbody > tr > th, .table_jz2 > tfoot > tr > th, .table_jz2 > thead > tr > td, .table_jz2 > tbody > tr > td, .table_jz2 > tfoot > tr > td {  padding: 15px 0;}
.cursorPointer {cursor:pointer;}
</style>
	
</head>
<body>
<div class="message-pop"><span>保存成功！</span></div>
<div class="main-row">
	<div class="main_index3">
		<div class="pane_main5">
			<div class="t_pane_a1 radius_5">
				<div class="user_pane_r2">
					<div class="hr20"></div>
					<ul class="nav nav-tabs" role="tablist">
						<li class="active"><a href="${ctx}/workterrace/chargeToAccount/">记账</a></li>
						<li><a href="${ctx}/workterrace/reportTax/">报税</a></li>
						<li><a href="${ctx}/workterrace/wages/">工资</a></li>
						<li><a href="${ctx}//workterrace/daily/">日常</a></li>
						<li><a href="${ctx}/workterrace/tPersonalReminder/">个人提醒</a></li>
					</ul>
						<input id="newclients" type="button" class="btn btn-default btn_w_a btn_bg_2 radius0 dialog_add xin" value="新增客户"/>
					<div class="tab-content main5_con radius_5">
						<div class="tab-pane active" id="tab_f1">
							<div class="hr20"></div>
							
							<div class="pane_gzt">
								<div class="search_form4">
									
										
										<span class="span_year left">
											<form:form id="searchForm" modelAttribute="tCustomer" action="${ctx}/workterrace/chargeToAccount/list" method="post" >
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
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
									<table class="table_jz2" id='myTable01' collspacing='0' cellpadding='0' border='1' width='100%'>
									      <thead>
									        <tr>
									         	<th width='30' class='tmynum'></th>
									         	<th class='row_04'>客户名称</th>
									       <th title="01" width="55"><span class="">01月</span></th>
								          <th title="02" width="55"><span class="">02月</span></th>
								          <th title="03" width="55"><span class="">03月</span></th>
								          <th title="04"  width="55"><span class="">04月</span></th>
								          <th title="05"  width="55"><span class="">05月</span></th>
								          <th title="06"  width="55"><span class="">06月</span></th>
								          <th title="07"  width="55"><span class="">07月</span></th>
								          <th title="08"  width="55"><span class="">08月</span></th>
								          <th title="09"  width="55"><span class="">09月</span></th>
								          <th title="10"  width="55"><span class="">10月</span></th>
								          <th title="11"  width="55"><span class="">11月</span></th>
								          <th title="12" width="55"><span class="">12月</span></th>

								        </tr>
								      </thead>
								      <tbody>
								<c:forEach items="${customerList}" var="tCustomer" varStatus="status">
								        <tr>
										  <td width='30' class='mynum'><span>${status.count}</span></td>
										 <td title="${tCustomer.customerName}" class="row_01">
										 <span id="${tCustomer.id}">
							<c:choose >
   							 <c:when test="${fn:length(tCustomer.customerName)>13}">
							       ${fn:substring(tCustomer.customerName, 0, 13)}...
							    </c:when>
								 <c:otherwise>
								 ${tCustomer.customerName}
							    </c:otherwise>
							</c:choose>
											</span>
								          	<span class="btn_h4 right">
								          	<input id="customerName" type="hidden" value="${tCustomer.customerName}"/>
								          		<a title="编辑" href="javascript:void(0);" class="dialog_add btn btn-default btn_n4 bg_p6 btn_i2 pad_10"></a>
								          		<a title="移除" href="javascript:void(0);" onclick="delectCustomer(${tCustomer.id})" class="btn btn-default btn_n4 bg_p6 btn_i4 pad_10"></a>
								          		<a title="收费" href="javascript:void(0);" class="dialog_mon btn btn-default btn_n4 bg_p6 btn_i7 pad_10"></a>
								          		<input id="tCustomerid" type="hidden" value="${tCustomer.id}"/>
								          	</span>
								          </td>
								           
								          <td class="cursorPointer"><span class="  ${tCustomer.wagesVo.montharray1}"></span></td>
								        <td class="cursorPointer"><span class="  ${tCustomer.wagesVo.montharray2}"></span></td>
								        <td class="cursorPointer"><span class="  ${tCustomer.wagesVo.montharray3}"></span></td>
								        <td class="cursorPointer"><span class="  ${tCustomer.wagesVo.montharray4}"></span></td>
								        <td class="cursorPointer"><span class="  ${tCustomer.wagesVo.montharray5}"></span></td>
								        <td class="cursorPointer"><span class="  ${tCustomer.wagesVo.montharray6}"></span></td>
								        <td class="cursorPointer"><span class="  ${tCustomer.wagesVo.montharray7}"></span></td>
								        <td class="cursorPointer"><span class="  ${tCustomer.wagesVo.montharray8}"></span></td>
								        <td class="cursorPointer"><span class="  ${tCustomer.wagesVo.montharray9}"></span></td>
								        <td class="cursorPointer"><span class="  ${tCustomer.wagesVo.montharray10}"></span></td>
								        <td class="cursorPointer"><span class="  ${tCustomer.wagesVo.montharray11}"></span></td>
								        <td class="cursorPointer"><span class="  ${tCustomer.wagesVo.montharray12}"></span></td>
								        </tr>
								        </c:forEach>
								      </tbody>
								</table>
								
								</div>
								<div name="searchForm" class="pagination" style="display:none;">${page}</div>
							</div>	
							<div class="clearfix"></div>
							<div class="hr15"></div>
							<div class="bg_p11 line_h30 font_cc7 font-14 radius_bl5 radius_br5">
								<div class="pane_gzt">
									<span class="left"><span id="bymonth"></span>月完成&nbsp;<span id="doneCount">${tc.doneCount }</span>&nbsp;&nbsp;&nbsp;进行中&nbsp;<span id="doIngCount">${tc.doIngCount }</span>&nbsp;&nbsp;&nbsp;未开始&nbsp;<span id="doWillCount">${tc.doWillCount }</span></span>
									<span class="right">
										<a href="${ctx}/workterrace/chargeToAccount/list" class="left mar-rig20"><span class="btn btn-default btn_n4 bg_p6 btn_i8 pad_10"></span>重新统计</a>
									    <a href="${ctx}/workterrace/recyclebin/list" class="left"><span class="btn btn-default btn_n4 bg_p6 btn_i9 pad_10"></span>回收站</a>
									</span>
									<div class="clearfix"></div>
								
								</div>

							</div>
						</div>
					</div>
					<div class="hr10"></div>
					<div class="bg_p6 tips_jz10">
					</div>
					<div class="hr10"></div>
				</div>
			</div>

			<div class="hr20"></div>


		</div>
	</div>
</div>
</div>
<%@include file="tServiceChargeList.jsp"%>

<%@include file="updateCustomerInfo.jsp"%>
<script src="${pageContext.request.contextPath}/static/js/gztjs.js"></script>
<script type="text/javascript">
 $(function(){
	 $(".xin").on("click",function(){
		 
		 $("#id").val("");
	 });
	 
 });
</script>
<script>
$(function(){
	
	$(".table_jz2 tr td .bg_sta4").removeClass("bg_sta4").parent().text("服务到期").addClass("bg_sta4");
	
	$(".table_jz2 tr td .bg_sta4").addClass("curr_span").removeClass("curr_span").parent().text("服务到期");

	$(".table_jz2 tr td .going").parent().text("进行中");

	$(".table_jz2 tr").hover(function(){
		$(this).addClass("thover");
		$(this).prev("tr").addClass("top_lines");
	},function(){
		$(this).removeClass("thover");
		$(this).prev("tr").removeClass("top_lines");
	})

	$(".table_jz2 tbody tr:eq(0)").hover(function(){
		$(this).addClass("thover");
		$(".table_jz2 thead tr").addClass("top_lines");
	},function(){
		$(this).removeClass("thover");
		$(".table_jz2 thead tr").removeClass("top_lines");
	});
	$(".fixed_table thead th").on("click",function() {
		$(".fixed_table th span").removeClass("curr_span");
		$("span",this).addClass("curr_span");
		var month=$(this).attr("title");
		var yearmonth= $("#byYear").val()+$(this).attr("title");
		$.post("${ctx}/workterrace/chargeToAccount/getCount",{byYearMonth:yearmonth},function(result){
			$("#bymonth").text(month);
			$("#doneCount").text(result.doneCount);
			$("#doIngCount").text(result.doIngCount);
			$("#doWillCount").text(result.doWillCount);
		});
	})
	
	var mydate = new Date();
	var year = "" + mydate.getFullYear();
	var month = mydate.getMonth()+1;
	   
	$("#bymonth").text(month+"");
	 $(".fixed_table thead th").eq(month+1).find("span").addClass("curr_span");
	 
	 $("table tbody").delegate(".cursorPointer","click",function(){
			var sessionCustomerId=$(this).parent().find("input:last").val();
			//window.location.href="${ctx}/customer/tCustomer/setSession?sessionCustomerId="+sessionCustomerId;
			$(window.parent.enterCustomer(sessionCustomerId));
	 })
})



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
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/gotoAccountBook.js"></script>
<script type="text/javascript">

</script>
</body>
</html>