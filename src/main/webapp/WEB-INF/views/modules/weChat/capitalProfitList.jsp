<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>资金利润</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" href="${ctxStatic}/css/ydkj20151204.css">
<script src="${ctxStatic}/js/jquery-2.1.4.js"></script>
<script src="${ctxStatic}/js/tap.js"></script>
<script src="${ctxStatic}/js/weChatGoPage.js"></script>
<script type="text/javascript">
$(function (){
	listRpt();
	<!-- 选择日期时间 查询-->
	$('#accountPeriod').change(function() {
		var accountPeriod= $('#accountPeriod option:selected').html().replace(/[^0-9]+/g, '');
		$('#searchForm').submit();
	});
});
</script>
<script type="text/javascript">
 //	项目路径
 var ctx = "<%=request.getContextPath() %>";
 var ctxStatic = "${ctxStatic}";
 var defaultPeriod = "${defaultPeriod}";
 var initperiod="${initperiod}";
</script>
</head>
<body>
	<div class="container">
		
		<div class="date_chose">
			<img class="date_btn left" src="${ctxStatic}/images/date_left.png" alt="">
			<div class="date"><span class="year">${fn:substring(defaultPeriod, 0, 4)}</span>年<span class="mouth">${fn:substring(defaultPeriod, 4, 6)}</span>月</div>
			<img class="date_btn right" src="${ctxStatic}/images/date_right.png" alt="">
		</div>
		<div class="space"></div>
		<div class="space"></div>
		<div id="tableInfo" class="table">
	
		</div>
	</div>
	 <div class="page_bottom">
		<a id="aRept" href="javascript:void(0)" onclick="listRpt('')">资金利润</a>
		<a id="aTaxation" href="javascript:void(0)" onclick="listTaxation('')">缴税</a>
		<a id="aWage"href="javascript:void(0)" onclick="listWageSocialSecurity('')">公司社保</a>
	</div>
	<script src="${ctxStatic}/js/date_change.js"></script>
</body>
</html>