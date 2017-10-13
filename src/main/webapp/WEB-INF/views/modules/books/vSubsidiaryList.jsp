<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>明细账-在线会计-芸豆会计</title>
<meta name="decorator" content="default" />
<meta name="keywords" content="关键字,关键字,关键字" />
<meta name="description" content="描述。……" />
<meta name="Author" content="author_bj designer_bj">
<script src="${ctxStatic}/js/jquery-1.11.2.min.js"></script>

<script src="${ctxStatic}/js/books/subsidiaryJs.js"></script>
<script src="${ctxStatic}/fixedheadertable/jquery.fixedheadertable.js"></script> 
<script src="${ctxStatic}/js/table_height.js"></script>

<link href="${ctxStatic}/css/reset.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/detailaccount.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/accountCom.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/fixedheadertable/defaultTheme.css" rel="stylesheet" media="screen" />
<link href="${ctxStatic}/css/downlist.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	//下载
	function downloadSubsidiary() {
		var beginPeriod = $("#beginPeriodHidden").val();
		var endPeriod = $("#endPeriodHidden").val();
		var accountName = $("#accNameHidden").val();
		window.open("${ctx}/books/Subsidiary/downloadSubsidiary?accountPeriod=" + beginPeriod + "&periodEnd=" + endPeriod + "&accountName=" + accountName, "", "");
	}
	//打印
	function stampSubsidiary() {
		var beginPeriod = $("#beginPeriodHidden").val();
		var endPeriod = $("#endPeriodHidden").val();
		var accountName = $("#accNameHidden").val();
		window.open("${ctx}/books/Subsidiary/stampSubsidiary?accountPeriod=" + beginPeriod + "&periodEnd=" + endPeriod + "&accountName=" + accountName, "", "");
	}
	//下载年度账
	function stampYearSubsidiary() {
		var beginPeriod = $("#beginPeriodHidden").val();
		var endPeriod = $("#endPeriodHidden").val();
		var accountName = $("#accNameHidden").val();
		window.open("${ctx}/books/Subsidiary/stampYearSubsidiary?accountPeriod=" + beginPeriod + "&periodEnd=" + endPeriod, "", "");
	}
</script>
<script type="text/javascript">
	//调到凭证页面
	function goVoucherexp(id) {

	}
</script>
<script type="text/javascript">
	//	项目路径
	var ctx = "${ctx}";
	var ctxStatic = "${ctxStatic}";
	var accountPeriod = "${accountPeriod}";
	var periodEnd = "${periodEnd}";
	var accId = "${accountInfo.id}";
	var accName = "${accountInfo.accountName}";
	var accountId = "${accountInfo.accuntId}";
</script>
</head>
<body>
	<div class="account-wrapper detailAccount">
		<h2 class="h2-tit" id="accountName"></h2>
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
				<div class="list">
					<input class="detail_subject account" type="text" value="" readonly>
					<div class="triangle"></div>
					<ul id="acc">
					</ul>
				</div>
				<input id="beginPeriodHidden" type="hidden" value="" /> 
				<input id="endPeriodHidden" type="hidden" value="" /> 
				<input id="accIdHidden" type="hidden" value="" /> 
				<input id="accNameHidden" type="hidden" value="" />
			</div>
			<div class="th-right clearfix">
				<a href="javascript:void(0)" onclick="downloadSubsidiary()"><span>下载</span></a> <a href="javascript:void(0)" onclick="stampSubsidiary()"><span>打印</span></a> <a href="javascript:void(0)" onclick="stampYearSubsidiary()"><span>下载年度帐</span></a>
			</div>
		</div>

		<div class="tb">
			<table id="myTable02" class="table-detailAccount table-account-wrapper" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th width="13%">日期</th>
						<th width="13%">凭证号</th>
						<th width="30%">摘要</th>
						<th width="13%">借方</th>
						<th width="13%">贷方</th>
						<th width="5%">方向</th>
						<th width="13%">余额</th>
					</tr>
				</thead>
				<tbody id="tableInfo">

				</tbody>
			</table>
		</div>
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
				var beginPeriod = $(this).attr("period");
				$("#beginPeriodHidden").attr("value", beginPeriod);
				var endPeriod = $("#endPeriodHidden").val();
				var accId = $("#accIdHidden").val();
			});
			$(".list:eq(1)").find("ul li").on("click", function() {
				var endPeriod = $(this).attr("period");
				$("#endPeriodHidden").attr("value", endPeriod);
				var beginPeriod = $("#beginPeriodHidden").val();
				var accId = $("#accIdHidden").val();
				tableInfo(accId, beginPeriod, endPeriod);
			});
			$(".list:eq(2)").find("ul li").on("click", function() {
				var accId = $(this).attr("accId");
				var parentName = $(this).attr("parentName");
				var accountName = $(this).attr("accountName");
				var accNames = '';
				if (typeof (parentName) == "undefined" || parentName == 0) {
					accNames = accountName;
				} else {
					accNames = parentName + '-' + accountName;
				}
				$("#accNameHidden").attr("value", accNames);
				$("#accIdHidden").attr("value", accId);
				var beginPeriod = $("#beginPeriodHidden").val();
				var endPeriod = $("#endPeriodHidden").val();
				tableInfo(accId, beginPeriod, endPeriod);
			});
			$(".list ul li").on("click", function() {
				$(this).parents(".list").find(".detail_subject").val($(this).text());
				var beginPeriod = $("#beginPeriodHidden").val();
				var endPeriod = $("#endPeriodHidden").val();
				var accountName = $("#accNameHidden").val();
				titleInfoFun(beginPeriod, endPeriod, accountName);
			});
			var beginPeriod = $("#beginPeriodHidden").val();
			var endPeriod = $("#endPeriodHidden").val();
			var accountName = $("#accNameHidden").val();
			titleInfoFun(beginPeriod, endPeriod, accountName);
		});
	</script>
</body>
</html>