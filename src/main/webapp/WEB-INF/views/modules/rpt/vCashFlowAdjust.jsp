<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>调整现金流量表-在线会计-芸豆会计</title>
<meta name="decorator" content="default" />
<meta name="keywords" content="关键字,关键字,关键字" />
<meta name="description" content="描述。……" />
<meta name="Author" content="author_bj designer_bj">
<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/js/jquery-1.11.2.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-formatMoney/jQuery.formatMoney.js" type="text/javascript"></script>
<script src="${ctxStatic}/fixedheadertable/jquery.fixedheadertable.js"></script>
<script src="${ctxStatic}/js/rpt/cashFlowAdjustJs.js"></script>

<script src="${ctxStatic}/js/table_height.js"></script>
<link href="${ctxStatic}/css/reset.css" rel="stylesheet" type="text/css">
<!-- <link href="css/accountCom.css" rel="stylesheet" type="text/css"> -->
<link href="${ctxStatic}/css/cashFlowvAndProfit.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/fixedheadertable/defaultTheme.css" rel="stylesheet" media="screen" />


<style>
.table-account-wrapper tbody tr td {
	text-align: left;
	text-indent: 28px;
}

.table-profit tbody tr td:nth-child(4) {
	text-align: left;
}

input[type="text"] {
	margin-bottom: 0;
	vertical-align: baseline;
}

input[readonly] {
	background: #fff;
}

.input-xlarge {
	width: 200px;
}

.account-wrapper .tb {
	margin-top: 16px;
}

.table-account-wrapper tbody tr td {
	text-align: center;
	padding-left: 0;
	text-indent: 0;
}

.table-profit tbody tr td:nth-child(7) {
	text-align: center;
	padding-right: 0;
}
</style>
<script type="text/javascript">
	//	项目路径
	var ctx = "${ctx}";
	var ctxStatic = "${ctxStatic}";
</script>
</head>
<body>
	<input type="hidden" id="periodBeginHidden" value="${accountPeriod}" />
	<input type="hidden" id="periodEndHidden" value="${periodEnd}" />
	<input type="hidden" id="periodTypeHidden" value="${periodtype}" />
	<input type="hidden" id="tableInfoCountHidden" value="" />
	<div class="account-wrapper profit">
		<h2 class="h2-tit" id="cashFlowAjustTitle"></h2>
		<div class="th clearfix">
			<div class="th-right clearfix">
				<a href="javascript:void(0)" onclick="savaECashFlow()"><span>保存</span></a>
				<a href="javascript:void(0)" onclick="javascript:history.go(-1)"><span>上一步</span></a>
			</div>
		</div>
		<div class="tb">
			<table id="myTable02" class="table-profit table-account-wrapper" cellpadding="0" cellspacing="0">

			</table>
		</div>
	</div>
</body>
</html>