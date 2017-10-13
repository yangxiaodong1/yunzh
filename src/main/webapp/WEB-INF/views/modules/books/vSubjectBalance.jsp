<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>科目余额表-在线会计-芸豆会计</title>
<meta name="decorator" content="default" />
<meta name="keywords" content="关键字,关键字,关键字" />
<meta name="description" content="描述。……" />
<meta name="Author" content="author_bj designer_bj">
<script src="${ctxStatic}/js/jquery-1.11.2.min.js"></script>

<script src="${ctxStatic}/js/books/subjectBalanceJs.js"></script>
<script src="${ctxStatic}/fixedheadertable/jquery.fixedheadertable.js"></script> 
<script src="${ctxStatic}/js/table_height.js"></script>
<link href="${ctxStatic}/css/checkCredentials.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/reset.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/balance.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/accountCom.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/fixedheadertable/defaultTheme.css" rel="stylesheet" media="screen" />
<link href="${ctxStatic}/css/downlist.css" rel="stylesheet" type="text/css">
<style>
.fht-table th {
	vertical-align: middle;
}

.th-left select {
	width: 110px;
	display: inline-block;
}

.table-balance tbody tr td:nth-child(1) {
	text-indent: 4px;
}

.table-account-wrapper tbody tr td {
	height: auto;
}

.table-balance tbody tr td:nth-child(2) {
	line-height: 28px;
	padding: 5px 0;
}
</style>
<script type="text/javascript">
	//下载
	function downloadSubjectBalance() {
		var beginPeriod = $("#beginPeriodHidden").val();
		var endPeriod = $("#endPeriodHidden").val();
		window.open("${ctx}/books/subjectBalance/downloadSubjectBalance?accountPeriod=" + beginPeriod + "&periodEnd=" + endPeriod, "", "");
	}
	//打印
	function stampSubjectBalance() {
		var beginPeriod = $("#beginPeriodHidden").val();
		var endPeriod = $("#endPeriodHidden").val();
		window.open("${ctx}/books/subjectBalance/stampSubjectBalance?accountPeriod=" + beginPeriod + "&periodEnd=" + endPeriod, "", "");
	}
</script>
<script type="text/javascript">
	//	项目路径
	var ctx = "${ctx}";
	var ctxStatic = "${ctxStatic}";
	var accountPeriod = "${accountPeriod}";
	var periodEnd = "${periodEnd}";
</script>
</head>
<body>
	<div class="account-wrapper balance">
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
				<input id="beginPeriodHidden" type="hidden" value="" /> 
				<input id="endPeriodHidden" type="hidden" value="" />
			</div>
			<div class="th-right clearfix">
				<a href="javascript:void(0)" onclick="downloadSubjectBalance()"><span>下载</span></a> <a href="javascript:void(0)" onclick="stampSubjectBalance()"><span>打印</span></a>
			</div>
		</div>
		<div class="tb">
			<table id="myTable02" class="table-balance table-account-wrapper" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th width="9%" rowspan="2">科目编号</th>
						<th width="11%" rowspan="2">科目名称</th>
						<th class="borb" width="10%" colspan="2">期初余额</th>
						<th class="borb" width="10%" colspan="2">本期发生额</th>
						<th class="borb" width="10%" colspan="2">本年累计发生额</th>
						<th class="borb" width="10%" colspan="2">期末余额</th>
					</tr>
					<tr>
						<th width="10%">借方</th>
						<th width="10%">贷方</th>
						<th width="10%">借方</th>
						<th width="10%">贷方</th>
						<th width="10%">借方</th>
						<th width="10%">贷方</th>
						<th width="10%">借方</th>
						<th width="10%">贷方</th>
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