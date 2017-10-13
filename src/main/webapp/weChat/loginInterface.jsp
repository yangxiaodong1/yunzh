<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>登陆</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" href="${ctxStatic}/css/ydkj20151204.css">
<script src="${ctxStatic}/js/jquery-2.1.4.js"></script>
<script src="${ctxStatic}/js/tap.js"></script>
<script src="${ctxStatic}/js/weChatGoPage.js"></script>
<script type="text/javascript">
 //	项目路径
 var ctx = "${ctx}";
</script>
</head>
	<body class="log">
	<div class="container">
		<div class="title">芸豆会计</div>
		<form  method="post" id="login-Form"  action="<%=request.getContextPath() %>/weChat/WTableInfo/indexWeChat">
		<div class="login active">
			账号<input class="login" id="userName" name="userName"  value="${userName}" type="text" placeholder="请输入账号">
		</div>
		<div class="login">
			密码<input class="login" id="password" name="password" type="password" name="" id="" placeholder ="请输入密码">
		</div>
		<button	type="submit" class="btn btn-default btn_m btn_e1 mar-auto ">登录</button>
		</form>
	</div>
	<script>
		document.getElementsByTagName("html")[0].style.fontSize = document.documentElement.clientWidth/10 + "px";
		$(".login input").on("focus",function() {
			$(this).attr("placeholder","");
			$(this).parent().addClass("active").siblings(".login").removeClass("active");
		})
		$(".login input[type=text]").on("blur",function() {
			if($(this).attr("placeholder") == "") {
				$(this).attr("placeholder","填写账号");
			}
			$(this).parent().removeClass("active");
		})
		$(".login input[type=password]").on("blur",function() {
			if($(this).attr("placeholder") == "") {
				$(this).attr("placeholder","填写密码");
			}
			$(this).parent().removeClass("active");
		})
	</script>
	<script type="text/javascript">
		function gotoRptIndex(){
			window.loaction.href="${ctx}/"
		}
	
	</script>
</body>
</html>