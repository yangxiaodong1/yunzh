<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>云智慧 登录</title>
<style type="text/css">
   html,body,table{background-color:#5e90c8;width:100%;text-align:center;color:#888888;font-family:Microsoft Yahei;}
a,a:hover{color:#888;text-decoration:none;}
.form-signin-heading{font-family:Helvetica, Georgia, Arial, sans-serif, 榛戜綋;font-size:36px;margin-bottom:20px;color:#0663a2;text-align:center;}
.form-signin{position:relative;text-align:left;width:550px;padding:48px 96px;margin:0 auto 20px;background-color:#fff;border:1px solid #e5e5e5;
-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;-webkit-box-shadow:0 1px 2px rgba(0,0,0,.05);-moz-box-shadow:0 1px 2px rgba(0,0,0,.05);box-shadow:0 1px 2px rgba(0,0,0,.05);
}
.form-signin .checkbox{margin-bottom:10px;color:#0663a2;} 
.form-signin .input-label{font-size:14px;color:#888;display:block;}
label{font-weight:normal;}
.form-signin .form-control{height:47px;margin-bottom:20px;}
.form-signin .btn.btn-large{font-size:18px;background-color:#589af5;border:none;width:100%;border-radius:18px;padding:0;line-height:48px;margin-top:15px;} 
.form-signin .btn.btn-large:hover{background-color:#1784e0;}
.form-signin div.validateCode {padding-bottom:15px;}
.mid{vertical-align:middle;}
.header{height:80px;padding-top:20px;}
.alert{position:relative;width:300px;margin:0 auto;*padding-bottom:0px;}
label.error{background:none;width:270px;font-weight:normal;color:inherit;margin:0;}
.act-btn{font-size:14px;line-height:30px;color:#888;}
.logo-img{border-bottom:1px #e4e4e4 solid;padding-bottom:15px;margin-bottom:15px;}
.login-form{padding:0 10px;}
@media(max-width:768px){
	.form-signin{width:100%;padding:20px;}
}

    </style>
<script src="static/jquery/jquery-1.8.3.min.js" type="text/javascript">


</script>
<link href="static/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="static/css/login.css" type="text/css" rel="stylesheet" />
</head>
<body>


<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
<div class="header">
	<div id="messageBox" class="alert alert-error hide"><button data-dismiss="alert" class="close">×</button>
		<label id="loginError" class="error"></label>
	</div>
</div>
<div class="form-signin">
<div class="logo-img"><img src="static/images/login-1.jpg"></div>
<div class="login-form">
<form id="loginForm"  action="/yunzh/a/login" method="post">
	<label class="input-label" for="username">手机jiji</label>
	<input type="text" id="username" name="username" class="form-control required" >
    <div class="clearfix"></div>
	<label class="input-label" for="password">密码</label>
	<input type="password" id="password" name="password" class="form-control required" placeholder="******">	
    <div class="clearfix"></div>	
    <div class="act-btn">
	<label for="rememberMe" title="下次不需要再登录"><input type="checkbox" id="rememberMe" name="rememberMe" /> 记住密码</label>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#">忘记密码</a>
    </div>
    <div class="clearfix"></div>
    <input class="btn btn-large btn-primary" type="submit" value="登 录"/>&nbsp;&nbsp;
	
</form>
</div>
</div>

</body>
</html>
