<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="description" content="芸豆会计为专为财务公司打造的一款智能化财务云平台" />
    <meta name="keywords" content="芸豆会计、智能财务系统、嘉芸汇" />
    <meta name="renderer" content="webkit">
    <link href="${ctxStatic}/login/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <meta name="viewport" content="width=device-width, initial-scale=1">    
    <meta name="renderer" content="webkit">
  <!--  // <meta name="decorator" content="blank"/> -->
    <title>芸豆会计</title>
    <link href="${ctxStatic}/login/yddlxt.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery-1.11.2.js" type="text/javascript"></script>  
<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<style type="text/css">
      label.error{background:none;font-weight:normal;}
    </style>
</head>
<body>
  
    <div class="box-bg">
        <div class="hr_150"></div>
        <div class="container">
            <div class="row">
                <div class="col-xs-12">
                    <div class="cloum-700">
                        <div class="login-box">
                            <div class="logon-logo"><img src="${ctxStatic}/login/ydkjdl-03.png" /></div>

                            <div class="hr_40"></div>
                            
                            <form role="tabpanel" action="${ctx}/login" method="post" class="tab-pane fade in active" id="login" >
                                <!-- <div class="tab-info" id="login-info" style="display: block;">用户名与密码不匹配，请重新输入。</div> -->
                                <div id="messageBox" style="display: block;" class="tab-info ${empty message ? 'hide' : ''}">
									${message}
								</div>
                                <div class="hr_10"></div>
                                <label for="login-user" class="sr-only">请输入用户名</label>
                                <input type="text" id="login-user" name="username" class="form-control" placeholder="请输入用户名"  autofocus="">
                                <div class="hr_10"></div>
                                <label for="login-password" class="sr-only">密码</label>
                                <input type="password" id="login-password" name="password" class="form-control" placeholder="密码" >
                                <div class="hr_10"></div>
                                
                              <!--   <div class="col-xs-12 col-md-6 col-A">
                                    <label for="yz-password" class="sr-only">请输入验证码</label>
                                    <input type="text" id="yz-password" class="form-control" placeholder="请输入验证码" required="">
                                </div> -->
                                
                                <c:if test="${isValidateCodeLogin}">
	                                <div  class="validateCode">
										<sys:validateCode name="validateCode"/>
									</div>
								</c:if>
                                
                                
                                   <%--  <div class="col-xs-12 col-md-6">
                                        <div class="col-xs-12 col-md-9 "><img src="${ctxStatic}/login/ydkjdl-04.jpg" /></div>
                                        <div class="col-xs-12 col-md-3"><img src="${ctxStatic}/login/ydkjdl-05.jpg" style="margin-top:25px;" /></div>
                                   </div> --%>
                                
                                <div class="hr_10"></div>
                                <div class="checkbox">
                                    <label class="f-white" for="rememberMe" title="下次不需要再登录">
                                        <input type="checkbox" id="rememberMe" name="rememberMe" /> 记住我
                                    </label>
                                    
                                   <!--  <a class="pull-right forgot" href="http://micourse.net/recover">忘记密码</a> -->
                                </div>

                                <input class="btn btn-lg btn-primary btn-block login" type="submit" value="登录"/>
                            </form>
                        </div>                        
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
    
    $(function(){
    	$("#login").validate({
			rules: {
				validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
			},
			messages: {
				
				validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
			},
			
			errorPlacement: function(error, element) {
				$("#messageBox").html(error);
			} 
		});
    	
    	$("#validateCode").addClass("form-control").attr("placeholder","请填写验证码.").css({
    		"width":"150px",
    		"font-weight":"normal"
    	});
    	//$('.validateCodeRefresh').html("<img src=\"${ctxStatic}/login/ydkjdl-05.jpg\" style=\"width:25px; height:25px\" />");
    	$("body").height($(window).height());
    })
    

	
	
</script>
    
</body>
</html>