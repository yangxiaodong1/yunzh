<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>个人设置</title>
<script src="${ctxStatic}/jquery/jquery-1.11.2.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/CusUser.js"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_2.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="${ctxStatic}/webuploader/webuploader.html5only.js"></script>
<link href="${ctxStatic}/webuploader/webuploader.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctxStatic}/webuploader/headUp.js"></script>

<script type="text/javascript" src="${ctxStatic}/jquery/jquery.validate.js"></script>

<style>
.thumbnail{border-radius:0;padding:0;background:transparent;border:none;}
.thumbnail .info{display:none;}
.thumbnail .error{text-align:center;}
.thumbnail img{border-radius:50%;border:1px #dcdcdc solid;padding:5px;}
.webuploader-pick{color: #666; background-color: #fff; border: 1px #ccc solid;padding:5px 12px;border-radius: 5px;margin:0 auto;}
#uploader-demo{position:relative;width:100px;height:150px;padding:120px 0 0 0; }
.oldfaces{border-radius:50%;width:100px;height:100px;border:1px #dcdcdc solid;padding:5px;position:absolute;top:0;left:0;z-index:1;}
#fileList{position:absolute;z-index:2;top:0;left:0;}
.webuploader-container{text-align:center;}

label.error{color:#ff0000;font-weight: normal;margin:3px 0 0 5px;}
.col-sm-8 input.form-control{width:270px;float:left;}
div.form-group{clear:both;}

</style>
<script>
	var ctx = "${ctx}";
	$(function(){
		/**密码修改**/
		$("#btnSubmitPwd").on("click",function(){
			$("#inputFormPwd").ajaxSubmit({
	            type: 'post',
	            url: "${ctx}/sys/user/modifyPwd" ,
	            data: $("#inputFormPwd").serialize(),
	            success: function(data){
	            	if(data=="1")
	                	alert("保存成功！");
	            	if(data=="2")
	                	alert("原密码输入错误！");
	                $( "#inputFormPwd").resetForm();
	            },
	            error: function(XmlHttpRequest, textStatus, errorThrown){
	                 //alert( "error");
	            }                
	        })
		})
		/**转凭证**/
	/*	$("#btnCusCommmm").on("click",function(){
			//alert("ooo");
			
			$("#inputFormCusCom").ajaxSubmit({
	            type: 'post',
	            url: "${ctx}/cususer/tCusUser/save" ,
	            data: $("#inputFormCusCom").serialize(),
	            success: function(data){
	            	if(data=="1")
	                	alert("保存成功！");
	            	
	            },
	            error: function(XmlHttpRequest, textStatus, errorThrown){
	                 alert( "error");
	            }                
	        })
		})
	})*/
	
	
	function test(){
		alert("已经接受，不能再次接受");
	}
	
	
		function messagePop(str,top){
			console.log(arguments.length)
				$(".message-pop").fadeIn();
				$(".message-pop").children("span").html(str);
				arguments.length == "2" ? $(".message-pop").css({"top":top}):$(".message-pop").css({"top":"80px"});
				setTimeout(function(){
					$(".message-pop").fadeOut();
				},2000)
		}
	
</script>
</head>
<body>
<div class="message-pop"><span>保存成功！</span></div>
<div class="main-row">
	<div class="main_cons">
		<div class="font-24 font_cc5 line_h65">个人设置	</div>
		<div class="bg_col_1 radius_5">
			<div class="hr75"></div>
			<div class="w_main">
				<div class="w_face left">
					<form id="upload" enctype="multipart/form-data" method="post">
						<!-- <div class="border_i2 radius_imgs left pad_5">
							 <img src="${ctxStatic}/images/gzpt/face1.jpg" width="88" height="88" class="radius_imgs" />
						</div>
						-->
						<div class="clearfix"></div>
						<div class="hr10"></div>
						<div class="img_w102">
							<!--  <div class="btn_upfile radius_5 mar_auto"><span>修改头像</span>
								<input type="file" class="btn-default" value="修改头像" />
							</div>
							-->
							<div id="uploader-demo">							    
							    <div id="fileList" class="uploader-list"></div>
							    <div id="filePicker">选择图片</div>
							    <div class='oldfaces'><img src="${ctx}/sys/user/imageGet?fileName=${currentUser.photo}" width="88" height="88" class="radius_imgs" /></div>
							</div>
							
							<!--  <div class="uploader-list" id="fileList">
							
							</div>
							<div class="ipt_pos_a">											
								<div class='ipt_pos_1' id="filePicker">修改头像</div>
							</div>-->
						</div>
					</form>
				</div>
				<div class="w_700 right">
					<div class="user_form3">
						<ul class="nav nav-tabs" role="tablist">
							<li role="presentation" class="active"><a href="#user_p6" aria-controls="home" role="tab" data-toggle="tab">个人资料</a></li>
							<li role="presentation"><a href="#user_p7" aria-controls="profile" role="tab" data-toggle="tab">修改密码</a></li>
							<li role="presentation"><a href="#user_p8" aria-controls="messages" role="tab" data-toggle="tab">交接账簿</a></li>
						</ul>
						<div class="tab-content">
							<div role="tabpanel" class="tab-pane active" id="user_p6">
								<div class="hr30"></div>
								<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/info" method="post" class="form-horizontal">
								  <div class="form-group">
								    <label class="col-sm-2 control-label font-14">账号</label>
								    <div class="col-sm-8">
								      <form:input path="loginName" htmlEscape="false" maxlength="50" class="form-control" readonly="true"/>
								    </div>
								  </div>
								  <div class="form-group">
								    <label class="col-sm-2 control-label font-14">姓名</label>
								    <div class="col-sm-8">
								      <form:input path="name" htmlEscape="false" maxlength="50" class="form-control upname"/>
								    </div>
								  </div>
								  <div class="form-group">
								    <label class="col-sm-2 control-label font-14">性别</label>
								    <div class="col-sm-8">
										<label class="radio-inline">
										  <form:radiobutton path="sex" value="1"/>男
										</label>
										<label class="radio-inline">
										  <form:radiobutton path="sex" value="0" />女
										</label>
								    </div>
								  </div>
								  <div class="form-group">
								    <label class="col-sm-2 control-label font-14">手机号</label>
								    <div class="col-sm-8">
								      <form:input path="mobile" htmlEscape="false" maxlength="50" class="form-control"/>
								    </div>
								  </div>
								  <div class="form-group">
								    <label class="col-sm-2 control-label font-14">QQ</label>
								    <div class="col-sm-8">
								      <form:input path="qq" htmlEscape="false" maxlength="50" class="form-control"/>
								    </div>
								  </div>
								  <div class="form-group">
								    <label class="col-sm-2 control-label font-14">详细地址</label>
								    <div class="col-sm-8">
								      <form:input path="address" htmlEscape="false"  maxlength="200" class="form-control"/>
								    </div>
								  </div>
								  <div class="hr40"></div>
								  <div class="form-group">
								    <div class="col-sm-offset-2 col-sm-12">
								      <input id="btnSubmitForm" class="btn btn-default btn_submit2 mar-lft150 addusername" type="submit" value="保 存"/>
								    </div>
								  </div>
								</form:form>
							</div>
							
							<div role="tabpanel" class="tab-pane" id="user_p7">
								<div class="hr30"></div>
								<form:form id="inputFormPwd" modelAttribute="user" method="post" class="form-horizontal">
								  <div class="form-group">
								    <label class="col-sm-2 control-label font-14">原密码</label>
								    <div class="col-sm-8">
								      <input id="oldPassword" name="oldPassword" type="password" value="" maxlength="50" minlength="3" class="form-control"/>
										<span class="help-inline" id="old" style="color:red"><font color="red">*</font> </span>
										 
								    </div>
								  </div>
								  <div class="form-group">
								    <label class="col-sm-2 control-label font-14">新密码</label>
								    <div class="col-sm-8">
								      <input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="form-control"/>
									  <span class="help-inline" style=""><font color="red">*</font> </span>
								    </div>
								  </div>
								  <div class="form-group">
								    <label class="col-sm-2 control-label font-14">确认密码</label>
								    <div class="col-sm-8">
								      <input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" class="form-control" equalTo="#newPassword"/>
										<span class="help-inline"><font color="red">*</font> </span>
								    </div>
								  </div>
								  <div class="hr40"></div>
								  <div class="form-group">
								    <div class="col-sm-offset-2 col-sm-12">
								    	<input id="btnSubmitPwd" class="btn btn-default btn_submit2 mar-lft150" type="submit" value="保 存"/>
								    </div>
								  </div>
								</form:form>
							</div>
							
							<div role="tabpanel" class="tab-pane" id="user_p8">
								<div class="hr30"></div>
								<div class="user_zb2">
									<div class="tit_z1 font-14 font_cc2">
										<span>转交账簿</span>
									</div>
									<div class="hr40"></div>
									<div class="w_340 mar_auto">
									<form:form id="inputFormCusCom" modelAttribute="tCusUser"  method="post" class="form-horizontal">
										  <div class="form-group">
										    <label class="col-sm-3 control-label font-14">选择账簿</label>
										    <div class="col-sm-7">
									              <form:select path="customerid" htmlEscape="false" class="form-control"> 
			 											<c:forEach items="${listcustomer}" var="cusomer">
			 												<form:option value="${cusomer.id}">${cusomer.customerName}</form:option>
			 											</c:forEach>     
            									 </form:select>
										    </div>
										  </div>
									
										  <div class="form-group">
										    <label class="col-sm-3 control-label font-14">转交给</label>
										    <div class="col-sm-7">
										    	<form:select path="userid" htmlEscape="false" class="form-control"> 
												 	<c:forEach items="${listusernew}" var="user">
												 		<form:option value="${user.id}">${user.name}</form:option>
												 	</c:forEach>     
									             </form:select>
										    </div>
										  </div>
									
										  <div class="hr40"></div>
										  <div class="form-group">
										    <div class="col-sm-offset-3 col-sm-7">
										      <input id="btnCusCom" class="btn btn-default btn_skin2 btn_bg_1 right" type="button" value="提 交"/>
										      <div class="clearfix"></div>
										    </div>
										  </div>
										</form:form>
									</div>
								</div>

								<div class="hr30"></div>
								<div class="user_zb2">
									<div class="tit_z1 font-14 font_cc2">
										<span>接收账簿</span>
									</div>
									<div class="hr30"></div>
									<ul class="list_zb4">
										<c:forEach items="${tcususerList}" var="tcususerList">
											<li>
												<span class="font-14">${tcususerList.sysuerNameBe}转交了${tcususerList.count}家公司账簿</span>
												<c:if test="${tcususerList.acceptState=='0'}">
													<span id="span${tcususerList.userbeid}" class="btn btn-default right btn_w4 btn_bg_2 accept">接收</span>
												</c:if>
											<div class="clearfix"></div>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
						<div class="hr30"></div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="hr75"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(".addusername").on("click",function(){
	 var username=$(".upname").val();
	// alert(username);
	 $(window.parent.updateusername(username));
});
/*
$(fuunction(){
	$(".addusername").on("click",function(){
		alert(1);
	});
});*/
</script>

<script type="text/javascript">
$(document).ready(function(){
  //用于验证老密码的
  $("#oldPassword").blur(function(){
   var oldpassword= $("#oldPassword").val();
   $("#old").empty();

  $.ajax({  
		type:"POST",
		url:"${ctx}/sys/user/comparepwd?pwd="+oldpassword,
	    success:function(json){  
	   	
	    	if(json=="1"){//1是没有成功的
	    		$("#old").text("密码不正确,请重新输入");
	    	}
	    	event.preventDefault();
	    	
	   },
	    error:function(){}
	 });  

  });
});
</script>
<script type="text/javascript"> 
	$().ready(function() {
		 $("#inputFormPwd").validate({
			    rules: {
			      oldPassword: {
			        required: true,
			        minlength: 5
			      },
			      newPassword: {
			        required: true,
			        minlength: 5
			      },
			      confirmNewPassword: {
			        required: true,
			        minlength: 5,
			        equalTo: "#newPassword"
			      }
			      
			      
			    },
			    messages: {
			      oldPassword: {
			        required: "",
			        minlength: ""
			      },
			      newPassword: {
			        required: "请输入密码",
			        minlength: "密码长度不能小于 5 个字符"
			      },
			      confirmNewPassword: {
			        required: "请输入密码",
			        minlength: "密码长度不能小于 5 个字符",
			        equalTo: "两次密码输入不一致"
			      }
			      
			      
			    }
			});
		});
	
	</script>
	<script type="text/javascript">
	//start
	$("#btnCusCom").on("click",function(){
			
			$("#inputFormCusCom").ajaxSubmit({
	            type: 'post',
	            url: "${ctx}/cususer/tCusUser/save" ,
	            data: $("#inputFormCusCom").serialize(),
	            success: function(data){
	            	if(data=="1")
	            		alert("保存成功！");
	            	
	            },
	            error: function(XmlHttpRequest, textStatus, errorThrown){
	            	alert("error");
	            }                
	        });
		
		
		
	})
	//end
	
	</script>
</body>
</html>