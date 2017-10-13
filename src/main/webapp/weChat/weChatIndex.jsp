<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>首页</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" href="${ctxStatic}/css/ydkj20151204.css">
<script src="${ctxStatic}/js/jquery-2.1.4.js"></script>
<script src="${ctxStatic}/js/tap.js"></script>
<script src="${ctxStatic}/js/weChatGoPage.js"></script>
<script type="text/javascript">
 //	项目路径
 var ctx = "<%=request.getContextPath() %>";
</script>
</head>
<body >
	<div class="container_01">
		<div class="company">${tCustomer.customerName }</div>
		<div class="chargeup">
			<div class="agent_chargeup">
				<div class="agent">代理记账</div>
				<div class="note">建账月份：${fn:substring(tCustomer.initperiod, 0, 4)}年
				${fn:substring(tCustomer.initperiod, 4, 6)}月</div>
				<div class="note"><span>增值税</span>：<c:choose><c:when test="${tCustomer.valueAddedTax == '0' }">小型规模纳税人</c:when><c:otherwise>一般纳税人</c:otherwise></c:choose></div>
				<div class="note"><span>服务商</span>：${tCustomer.officeCompanyName }</div>
			</div>
			<div class="logo">
				<div>芸豆</div>
				<div class="text_logo">LOGO</div>
			</div>
		</div>
		<div class="space"></div>
		<button class="enter" onclick="gotoRpt()">进入账簿</button>
		<div class="space"></div>
	</div>
	<script>
		document.getElementsByTagName("html")[0].style.fontSize = document.documentElement.clientWidth/10 + "px";
	</script>
	<script type="text/javascript">
	//跳到账簿首页
	function gotoRpt(){
		window.location.href='<%=request.getContextPath() %>/weChat/WTableInfo/indexWeChatInfo';
	}
	</script>
</body>
</html>