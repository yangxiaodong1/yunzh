<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>现金流量表-在线会计-芸豆会计</title>
	<meta name="decorator" content="default"/>
	<meta name="keywords" content="关键字,关键字,关键字" />
	<meta name="description" content="描述。……" />
	<meta name="Author" content="author_bj designer_bj">
	<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-ui.min.js"	type="text/javascript"></script>
	<script  src="${ctxStatic}/js/jquery-1.11.2.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/fixedheadertable/jquery.fixedheadertable.js"></script>
	
	<script src="${ctxStatic}/js/rpt/cashFlowJs.js"></script>
	<script src="${ctxStatic}/js/table_height.js"></script>
	<%-- <script src="${ctxStatic}/js/loading.js"></script> --%>
	<link href="${ctxStatic}/css/reset.css" rel="stylesheet" type="text/css">
	<!-- <link href="css/accountCom.css" rel="stylesheet" type="text/css"> -->
	<link href="${ctxStatic}/css/cashFlowvAndProfit.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/fixedheadertable/defaultTheme.css" rel="stylesheet" media="screen" />
	<link href="${ctxStatic}/css/downlist.css" rel="stylesheet" type="text/css">
	<script type="text/javascript">
	var ctx = "${ctx}";
	</script>
	<script type="text/javascript">
		//下载
		function downloadCashFlow(){
			var beginPeriod = $("#beginPeriodHidden").val();
			var periodtype = $("#periodtypeHidden").val();
			var companySystem = $("#companySystemHidden").val();
			window.open("${ctx}/rpt/cashFlow/downloadCashFlow?accountPeriod="+beginPeriod+"&periodtype="+periodtype+"&companySystem="+companySystem,"","") ;
		}
		//打印
		function stampCashFlow(){
			var beginPeriod = $("#beginPeriodHidden").val();
			var periodtype = $("#periodtypeHidden").val();
			var companySystem = $("#companySystemHidden").val();
			window.open("${ctx}/rpt/cashFlow/stampCashFlow?accountPeriod="+beginPeriod+"&periodtype="+periodtype+"&companySystem="+companySystem,"","") ;
		}
	</script>
	<style type="text/css">
		.input-medium {width: 120px;
	</style>
</head>
<body>
	<div class="account-wrapper profit">
		<h2 class="h2-tit" id = "cashFlowTitle"></h2>
		<div class="th clearfix">
			<div class="th-left">
				<div class="list">
					<input class="detail_subject beginPeriod" type="text" value="" readonly style="width: 100px">
					<div class="triangle"></div>
					<ul class="period" style="width: 110px">
					</ul>
				</div>
				<input id="beginPeriodHidden" type="hidden" value="" />
				<input id="periodtypeHidden" type="hidden" value="1" />
				<input id="companySystemHidden" type="hidden" value="" />
				
				<p class="ptxt" id="companyName"></p>
			</div>
			<div class="th-right clearfix">
				<a href="javascript:void(0)" onclick="cfadditionalPage()"><span>调整</span></a>
				<a href="javascript:void(0)" onclick="downloadCashFlow()"><span>下载</span></a>
				<a href="javascript:void(0)" onclick="stampCashFlow()"><span>打印</span></a>
			</div>
		</div>
		<div class="tb">
			<table id="myTable02" class="table-profit table-account-wrapper" cellpadding="0" cellspacing="0">
				
			</table>
		</div>
	</div>
<!--页面脚本区S-->
<!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->
<script>
		//日期选择
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
				var periodtype = $("#periodtypeHidden").val();
				$("#beginPeriodHidden").attr("value", beginPeriod);
				tableInfo(beginPeriod,periodtype);
			});

			$(".list ul li").on("click", function() {
				$(this).parents(".list").find(".detail_subject").val($(this).text());
				var beginPeriod = $("#beginPeriodHidden").val();
				var periodtype = $("#periodtypeHidden").val();
				titleInfoFun(beginPeriod,periodtype);
			});
			var beginPeriod = $("#beginPeriodHidden").val();
			var periodtype = $("#periodtypeHidden").val();
			titleInfoFun(beginPeriod,periodtype);
		});
	</script>
<!--页面脚本区E-->
</body>
</html>