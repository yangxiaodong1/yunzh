<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>总账-在线会计-芸豆会计</title>
<meta name="decorator" content="default" />
<meta name="keywords" content="关键字,关键字,关键字" />
<meta name="description" content="描述。……" />
<meta name="Author" content="author_bj designer_bj">

<script type="text/javascript" src="${ctxStatic}/js/jquery-1.11.2.min.js"></script>
<script src="${ctxStatic}/fixedheadertable/jquery.fixedheadertable.js"></script>
<script src="${ctxStatic}/js/books/generalLedgerJs.js"></script>
<script src="${ctxStatic}/js/table_height.js"></script>

<link href="${ctxStatic}/css/reset.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/balance.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/accountCom.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/fixedheadertable/defaultTheme.css" rel="stylesheet" media="screen" />
<link href="${ctxStatic}/css/downlist.css" rel="stylesheet" type="text/css">
<style type="text/css">
.table-account-wrapper tbody tr td:nth-child(1),.table-account-wrapper tbody tr td:nth-child(2) {
	text-align: left;
	text-indent: 8px;
}

.table-account-wrapper tbody tr td:nth-child(5) {
	text-align: center;
	text-indent: 0;
	padding-right: 0;
}

table.tb_scroll tr td:nth-child(1),table.tb_scroll tr td:nth-child(2) {
	width: 22%;
}

table.tb_scroll tr td:nth-child(3) {
	width: 14%;
}

.nav,.breadcrumb {
	margin-bottom: 0;
}
</style>

<script type="text/javascript">
		//下载
		function downloadGeneral(){
			var period = $("#periodHidden").val();
			var accountName = $("#accNameHidden").val();
			window.open("${ctx}/books/generalLedger/downloadGeneral?accountPeriod="+period+"&accountName="+accountName,"","") ;
		}
		//打印
		function stampGeneral(){
			var period = $("#periodHidden").val();
			var accountName = $("#accNameHidden").val();
			window.open("${ctx}/books/generalLedger/stampGeneral?accountPeriod="+period+"&accountName="+accountName,"","") ;
		}
		//打印
		function stampYearGeneral(){
			var period = $("#periodHidden").val();
			window.open("${ctx}/books/generalLedger/stampYearGeneral?accountPeriod="+period,"","") ;
		}
	</script>
<script type="text/javascript">
		
	</script>
<style>
.th-left select {
	width: inherit;
	display: inline-block;
	margin-right: 15px;
}

.table-account-wrapper tbody tr td.text-left {
	text-align: left;
	padding: 0;
}

.table-account-wrapper tbody tr td.text-left span {
	padding: 0 10px;
}
</style>
</head>
<body>
	<input id="periodHidden" type="hidden" value="" />
	<input id="accIdHidden" type="hidden" value="" />
	<input id="accNameHidden" type="hidden" value="" />
	<div class="account-wrapper general">
		<h2 class="h2-tit" id="accountName"></h2>
		<div class="th clearfix">
			<div class="th-left">
				年份：
				<div class="list">
					<input class="detail_subject period" type="text" value="" readonly style="width: 100px">
					<div class="triangle"></div>
					<ul class="period" style="width: 110px">
					</ul>
				</div>
				科目：
				<div class="list">
					<input class="detail_subject account" type="text" value="" readonly>
					<div class="triangle"></div>
					<ul id="acc">
					</ul>
				</div>
			</div>
			<div class="th-right clearfix">
				<a href="javascript:void(0)" onclick="downloadGeneral()"><span>下载</span></a> <a href="javascript:void(0)" onclick="stampGeneral()"><span>打印</span></a> <a href="javascript:void(0)" onclick="jumpPage()"><span>联查明细账</span></a> <a href="javascript:void(0)" onclick="stampYearGeneral()"><span>下载年度帐</span></a>
			</div>
		</div>
		<div class="tb">
			<table id="myTable02" class="table-general table-account-wrapper" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th width="16%">期间</th>
						<th width="30%">摘要</th>
						<th width="16%">借方</th>
						<th width="16%">贷方</th>
						<th width="6%">方向</th>
						<th width="16%">余额</th>
					</tr>
				</thead>
				<tbody id="tableInfo">

				</tbody>
			</table>
		</div>
		<script>
		$(function() {
			//$(".detail_subject:last").val($(".list:last ul li:first").text());
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
				var period = $(this).attr("period");
				$("#periodHidden").attr("value", period);
				var period = $("#periodHidden").val();
				var accId = $("#accIdHidden").val();
				tableInfo(accId, period);
			});
			$(".list:eq(1)").find("ul li").on("click", function() {
				var accId = $(this).attr("accId");
				var accountName = $(this).attr("accountName");
				
				$("#accNameHidden").attr("value", accountName);
				$("#accIdHidden").attr("value", accId);
				
				var period = $("#periodHidden").val();
				tableInfo(accId, period);
			});
			$(".list ul li").on("click", function() {
				$(this).parents(".list").find(".detail_subject").val($(this).text());
				var period = $("#periodHidden").val();
				var accountName = $("#accNameHidden").val();
				titleInfoFun(period, accountName);
			});
			var period = $("#periodHidden").val();
			var accountName = $("#accNameHidden").val();
			titleInfoFun(period, accountName);
		});
	</script>
	</div>
</body>
</html>