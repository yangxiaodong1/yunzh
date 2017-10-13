<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>利润表-在线会计-芸豆会计</title>
	<meta name="decorator" content="default"/>
	<meta name="keywords" content="关键字,关键字,关键字" />
	<meta name="description" content="描述。……" />
	<meta name="Author" content="author_bj designer_bj">

	<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-jtemplates.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery.form.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery.lightbox.js" type="text/javascript"></script>
	<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>	
	<script src="${ctxStatic}/fixedheadertable/jquery.fixedheadertable.js"></script>


	<script src="${ctxStatic}/js/rpt/profitListJs.js"></script>
	<script src="${ctxStatic}/js/table_height.js"></script>	
	<%-- <script src="${ctxStatic}/js/loading.js"></script> --%>
	<%-- <link href="${ctxStatic}/bootstrap/css/bootstrap.min.css"	type="text/css" rel="stylesheet" /> --%>
	<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet"	type="text/css" />
	<link href="${ctxStatic}/css/reset.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/css/cashFlowvAndProfit.css" rel="stylesheet" type="text/css">
	<%-- <link href="${ctxStatic}/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" /> --%>
	<link href="${ctxStatic}/css/public_v1.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/fixedheadertable/defaultTheme.css" rel="stylesheet" media="screen" />
	<link href="${ctxStatic}/css/downlist.css" rel="stylesheet" type="text/css">
	<script type="text/javascript">
		//下载
		function downloadProfit(){
			var accountPeriod = $("#beginPeriodHidden").val();
			var endPeriod = $("#endPeriodHidden").val();
			//window.location.href="${ctx}/rpt/profit/downloadProfit?accountPeriod="+accountPeriod+"&periodEnd="+periodEnd; 
			window.open("${ctx}/rpt/profit/downloadProfit?accountPeriod="+accountPeriod+"&periodEnd="+endPeriod,"","") ;
		}
		//打印
		function stampProfit(){
			var accountPeriod = $("#beginPeriodHidden").val();
			var endPeriod = $("#endPeriodHidden").val();
			//window.location.href="${ctx}/rpt/profit/stampProfit?accountPeriod="+accountPeriod+"&periodEnd="+periodEnd; 
			window.open("${ctx}/rpt/profit/stampProfit?accountPeriod="+accountPeriod+"&periodEnd="+endPeriod,"","") ;
		}
	</script>
	<style type="text/css">
	.left {
		float:left;
	}
	.input-medium {width: 120px;}
	</style>
<script type="text/javascript">
	//	项目路径
	var ctx = "${ctx}";
	var ctxStatic = "${ctxStatic}";
	var accountPeriod = "${accountPeriod}";
	var periodEnd = "${periodEnd}";
</script>
</head>
<body>
	<div class="account-wrapper profit">
		<h2 class="h2-tit" id="ProfitTitle"></h2>
		<div class="th clearfix">
				<div class="th-left">
					<div class="list">
						<input class="detail_subject beginPeriod" type="text" value="" readonly style="width: 100px">
						<div class="triangle"></div>
						<ul class="period" style="width: 110px">
						</ul>
					</div>
					<em style="float: none;">至</em>
					<div class="list">
						<input class="detail_subject endPeriod" type="text" value="" readonly style="width: 100px">
						<div class="triangle"></div>
						<ul class="period" style="width: 110px">
						</ul>
					</div>
					<input id="beginPeriodHidden" type="hidden" value="" /> 
					<input id="endPeriodHidden" type="hidden" value="" />
					<p class="ptxt" id="companyName"></p>
				</div>
			<div class="th-right clearfix">
			<!-- <a href="javascript:void(0)" onclick="submitfrom()"><span>查询</span></a>	 -->
			<a href="javascript:void(0)" onclick="downloadProfit()"><span>下载</span></a>
			<a href="javascript:void(0)" onclick="stampProfit()"><span>打印</span></a>
			</div>
		</div>
		<div class="tb">
			<table id="myTable02" class="table-profit table-account-wrapper" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th width="50%">项目</th>
						<th width="10%">行次</th>
						<th width="20%">本年累计金额</th>
						<th width="20%">本月金额</th>
					</tr>
				</thead>
				<tbody id="tableInfo">
					
				</tbody>
			</table>
		</div>
	</div>
<script>
	$(function() {
		$("html").on("click", function() {
			$(".list ul").hide();
		})
		$(".detail_subject,.triangle").each(function() {
			$(this).on("click", function(e) {
				var e = window.event || e;
				e.stopPropagation();
				$(this).parent().find("ul").show();
				$(this).parent().siblings().find("ul").hide();
			})
		})
		$(".list:eq(0)").find("ul li").on("click", function() {
			var beginPeriod = $(this).attr("period");
			$("#beginPeriodHidden").attr("value", beginPeriod);
			var endPeriod = $("#endPeriodHidden").val();
		});
		$(".list:eq(1)").find("ul li").on("click", function() {
			var endPeriod = $(this).attr("period");
			$("#endPeriodHidden").attr("value", endPeriod);
			var beginPeriod = $("#beginPeriodHidden").val();
			tableInfo(beginPeriod, endPeriod);
		});
		
		$(".list ul li").on("click", function() {
			$(this).parents(".list").find(".detail_subject").val($(this).text());
			var beginPeriod = $("#beginPeriodHidden").val();
			var endPeriod = $("#endPeriodHidden").val();
			titleInfoFun(beginPeriod, endPeriod);
		});
		var beginPeriod = $("#beginPeriodHidden").val();
		var endPeriod = $("#endPeriodHidden").val();
		titleInfoFun(beginPeriod, endPeriod);
	});
</script>
<%-- <div id="dialog" title="编辑公式">		
		<div class='hr10'></div>
		<form:form id="inputForm" modelAttribute="tAcctreportitem" action="" method="post">
		
		<form:hidden path="id"/>
		<form:hidden path="reportitem"/>
		
		<div class="control-group">
			<label class="control-label">科目编号：</label>
			<div class="controls">
				  <form:select path="accountid"  htmlEscape="false"  maxlength="6" class="input-xlarge ">
					<form:option value=""></form:option>
					<c:forEach items="${ listAcc}" var="acc">
						<form:option value="${acc.accuntId }">
							${acc.accuntId }${acc.accountName }
						</form:option>
					</c:forEach>
				</form:select> 
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick = "savaProfit()"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
		<div class='step_panel'>
			<div class='step_con step_a1'>
				<table class="table table-bordered table_vmiddle table_pad_no" style='font-size:12px;'>
				<thead>
					<tr>
						<th><span class='pad_l_r5'>科目</span></th>
						<th><span class='pad_l_r5'>运算符号</span></th>
						<th><span class='pad_l_r5'>本年累计金额</span></th>
						<th><span class='pad_l_r5'>本月金额</span></th>
						<th><span class='pad_l_r5'>操作</span></th>
					</tr>
				</thead>
				<tbody id = "listEFormula">
				</tbody>
				</table>
			</div>
		</div>
</div> --%>
	<!-- Templates -->
<p style="display:none"><textarea id="template" ><!--

{#foreach $T as eformula}
<tr>
	<tr>
		<td>
			{$T.eformula.accuntid }{$T.eformula.accountName }
		</td>
		<td>
			{$T.eformula.op }	
		</td>
		<td>
			{$T.eformula.yearStart }
		</td>
		<td>
			{$T.eformula.periodEnd }
		</td>
		<td>
			<a id="{$T.eformula.acctReportItemId }" href="javascript:void(0)" onclick="deleteProfit({$T.eformula.acctReportItemId })">删除</a>
		</td>
	</tr>
</tr>
{#/for}
-->
</textarea></p>
<!--页面脚本区S-->
<!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->

<!--页面脚本区E-->

</body>
<script type="text/javascript">
	$(function() {
		console.log($(window).height())
		$(".fht-tbody").css({
			"height" : $(window).height() - 220
		});
	})
</script>
</html>