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

<script type="text/javascript" src="${ctxStatic}/jquery/jquery.Jcrop.js"/></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_2.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="<c:url value="/resources/uploadPlugin/css/jquery.Jcrop.css"/>" type="text/css"></link>

<script type="text/javascript" src="${ctxStatic}/webuploader/webuploader.html5only.js"></script>
<link href="${ctxStatic}/webuploader/webuploader.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctxStatic}/webuploader/headUp.js"></script>
<script>
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
	                $( "#inputFormPwd").resetForm();
	            },
	            error: function(XmlHttpRequest, textStatus, errorThrown){
	                 //alert( "error");
	            }                
	        })
		})
		/**转凭证**/
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
	                 //alert( "error");
	            }                
	        })
		})
	})
	function updateSate(userbeid){
		 $.ajax({
	         type:"POST",
	         url:"${ctx}/cususer/tCusUser/updateAcceptState",
	         data:"userbeid="+userbeid,
	         success:function(data){
	        	 if(data=="1"){
	        		 $("#span"+userbeid).text("已接受");
	        		 $("#span"+userbeid).replaceWith("<span class='btn btn-default right btn_w4 btn_bg_3'>已接收</span>");
	        	 }    
	         }
	   });
	}
	function test(){
		alert("已经接受，不能再次接受");
	}
</script>
<script>
//定义一个全局api，这样操作起来比较灵活
var api = null;
function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.readAsDataURL(input.files[0]);
        reader.onload = function (e) {
            $('#cutimg').removeAttr('src');
            $('#cutimg').attr('src', e.target.result);
            api = $.Jcrop('#cutimg', {
                setSelect: [ 20, 20, 200, 200 ],
                aspectRatio: 1,
                onSelect: updateCoords
            });
        };
        if (api != undefined) {
            api.destroy();
        }
    }
    function updateCoords(obj) {
        $("#x").val(obj.x);
        $("#y").val(obj.y);
        $("#w").val(obj.w);
        $("#h").val(obj.h);
    };
}
</script>
</head>
<body>
<div class="main-row">
	<div class="main_cons">
		<div class="font-24 font_cc5 line_h65">个人设置	</div>
		<div class="bg_col_1 radius_5">
			<div class="hr75"></div>
			<div class="w_main">
				<div class="w_face left">
					
						<div class="border_i2 radius_imgs left pad_5">
							<img src="${ctxStatic}/images/gzpt/face1.jpg" width="88" height="88" class="radius_imgs" />
						</div>
						<div class="clearfix"></div>
						<div class="hr10"></div>
						<div class="img_w102">
							<!--  <div class="btn_upfile radius_5 mar_auto"><span>修改头像</span>
								<input type="file" class="btn-default" value="修改头像" />
									</div>
							-->
							<div class="uploader-list" id="fileList"></div>
							<div class="ipt_pos_a">											
								<div class='ipt_pos_1' id="filePicker">选择图片</div>
								<button type="button" class="btn btn-default btn_w_a2 btn_bg_6 ipt_w90 ipt_pos_2">添加</button>
							</div>
							<!-- 自己添加的方法的 -->
							<form name="form" action="" class="form-horizontal" method="post" enctype="multipart/form-data">
							    <div class="modal-body text-center">
							        <div class="zxx_main_con">
							            <div class="zxx_test_list">
							                <input class="photo-file" type="file" name="imgFile" id="fcupload" onchange="readURL(this);"/>
							                <img alt="" src="" id="cutimg"/>
							                <input type="hidden" id="x" name="x"/>
							                <input type="hidden" id="y" name="y"/>
							                <input type="hidden" id="w" name="w"/>
							                <input type="hidden" id="h" name="h"/>
							            </div>
							        </div>
							    </div> 
							    <div class="modal-footer">
							        <button id="submit" onclick="">上传</button>
							    </div>
							</form>
							
						</div>

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
								      <form:input path="name" htmlEscape="false" maxlength="50" class="form-control"/>
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
								      <input id="btnSubmitForm" class="btn btn-default btn_submit2 mar-lft150" type="submit" value="保 存"/>
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
										<span class="help-inline"><font color="red">*</font> </span>
								    </div>
								  </div>
								  <div class="form-group">
								    <label class="col-sm-2 control-label font-14">新密码</label>
								    <div class="col-sm-8">
								      <input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="form-control"/>
									  <span class="help-inline"><font color="red">*</font> </span>
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
								    	<input id="btnSubmitPwd" class="btn btn-default btn_submit2 mar-lft150" type="button" value="保 存"/>
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
												 	<c:forEach items="${listuser}" var="user">
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
													<span id="span${tcususerList.userbeid}" class="btn btn-default right btn_w4 btn_bg_2" onclick="updateSate(${tcususerList.userbeid})">接收</span>
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
</body>
</html>