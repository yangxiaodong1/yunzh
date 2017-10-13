<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>客户列表-添加</title>
<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery.validate.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_3.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctxStatic}/jquery/jquery.form.js"></script>
</head>

<body>
<div class="pad_15_10">
	<div class="panel panel-default mar_b0 radius0 border_none pad_10 ">
		<div class="panel panel-default mar_b0 radius0 border_i4 ">
			<div class="pad_lr_35">
				<div class="hr15"></div>
				
				<form:form id="inputForm" modelAttribute="tChargecompany" action="${pageContext.request.contextPath}/a/newcharge/tChargecompany/update" method="post" class="form-horizontal form-horizontal2" enctype="multipart/form-data">
		             <form:hidden path="id"/>
				<div class="panel-heading bg_col_2 panel-head-pub">
					<div class="left pad_t_3">
						<span class="glyphicon glyphicon_a5 ico_pub bg_pub" aria-hidden="true"></span><span class="font_cc8 font-14">客户基本信息</span>
					</div>
					<div class="right">
						<button type="button" class="btn btn-default btn_w_a2 btn_bg_6 ipt_w90 left mar-rig10 dialog_infos">设置帐号</button>
						<button type="button" class=" add btn btn-default btn_w_a2 btn_bg_6 ipt_w90 left ">保存</button>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="font_cc10">
					
								<div class="form-group">
									<label class="col-sm-3 control-label">客户名称</label>
									<div class="col-sm-9">
								
									<form:input path="chargecomname" htmlEscape="false" maxlength="64" class="input-xlarge form-control ipt_w4" />
									 	<!-- <input type="text" class="form-control ipt_w4" value="" name="depName"> --> 
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">所在城市</label>
									
									<form:hidden path="city" htmlEscape="false" maxlength="200" class="input-xlarge form-control ipt_w4"/>
								<!-- 	<form:select path="city1"  class="form-control ipt_wauto input-xlarge">
										<option value="湖北" >湖北</option>
										<option value="湖南" >湖南</option>
						            </form:select>
						            <form:select path="city2"  class="form-control ipt_wauto input-xlarge">
										<option value="襄阳">襄阳</option>
										<option value="武汉" selected="selected">武汉</option>
										<option value="宜昌">宜昌</option>
						            </form:select>
						            <form:select path="city3"  class="form-control ipt_wauto input-xlarge">
										<option value="襄阳1">襄阳1</option>
										<option value="武汉2" selected="selected">武汉2</option>
										<option value="宜昌3">宜昌3</option>
						            </form:select>
						             -->
						             <sys:treeselect id="area"  name="area.id" value="${office.area.id}" labelName="area.name" labelValue="${office.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="required" />
					 
					
								</div>							
								<div class="form-group">
									<label class="col-sm-3 control-label">联系人</label>
									<div class="col-sm-9">
									<form:input path="contectperson" htmlEscape="false" maxlength="64" class="input-xlarge form-control ipt_w4"/>
									 
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">手机号码</label>
									<div class="col-sm-9">
									<form:input path="mobilenumber" htmlEscape="false" maxlength="64" class="input-xlarge form-control ipt_w4"/>
									 	
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">企业人数</label>
									<div class="col-sm-9">
										<form:input path="companynumber" htmlEscape="false" maxlength="200" class="input-xlarge form-control ipt_w4"/>
									 	
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">企业经营范围</label>
									<div class="col-sm-9">
									<form:input path="companyrunrange" htmlEscape="false" maxlength="64" class="input-xlarge form-control ipt_w4" placeholder="可以以逗号隔开"/>
									 	
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label">使用状态</label>
									<div class="col-sm-9">
									 <label class="radio-inline">
									 		<form:radiobutton path="usestatus" name="RadioOptions2" id="inlineRadio1" value="0"/>试用客户
										</label>
										<label class="radio-inline">
											<form:radiobutton path="usestatus" name="RadioOptions2" id="inlineRadio2" value="1" />正式客户
											
										</label>				
									</div>
								</div>
							
				</div>
				<div class="hr15"></div>
				<div class="border_top_1">
					<div class="hr10"></div>
					<div class="panel-heading bg_col_2 panel-head-pub">
						<div class="pad_t_3">
							<span class="glyphicon glyphicon_a6 ico_pub bg_pub" aria-hidden="true"></span><span class="font_cc8 font-14">客户详细信息</span>
						</div>
						<div class="clearfix"></div>
					</div>
					
					
					<div class="font_cc10">
						<div class="left w200"><img src="${ctxStatic}/images/manager/img_4.jpg"></div>
						<div class="left w600">

								<div class="form-group">
									<label class="col-sm-4 control-label" style="width:110px;">法人姓名</label>
									<div class="col-sm-8">
									<form:input path="legalname" htmlEscape="false" maxlength="64" class="input-xlarge form-control ipt_w4"/>
										
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label" style="width:110px;">联系电话</label>
									<div class="col-sm-8">
									<form:input path="contectphone" htmlEscape="false" maxlength="64" class="input-xlarge form-control ipt_w4"/>
										
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label" style="width:110px;">身份证号</label>
									<div class="col-sm-8">
									<form:input path="cardnumber" htmlEscape="false" maxlength="100" class="input-xlarge form-control ipt_w4"/>
									
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label" style="width:110px;">身份证附件</label>
									<div class="col-sm-8">
										 <input type="file" placeholder="" name="filetype1" id="filetype">
										<!--<form:input path="appendixtype" htmlEscape="false" maxlength="100" class="input-xlarge " type="file" id="filetype"/>
									 -->
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label" style="width:110px;"></label>
									<div class="col-sm-8">
										<button type="button" class="add btn btn-default btn_w_a2 btn_bg_6 ipt_w90 left mar-rig10 ">添加</button>
									</div>
								</div>
														
						</div>
						<div class="clearfix"></div>
						<div class="hr20"></div>
					</div>
					<!--设置账号-->
<div id="dialog_infos" title="设置账号">
	<div class="font-14 w400 mar_auto">
		<div class="hr15"></div>
		   
			<div class="form-group">
				<label class="control-label w100 left mar-rig10" style="text-align:right;">用户登录名</label>				
				 <input type="text" class="form-control ipt_w4 depNameed" value="" name="depNameed" id="depNameed">				
			</div>
			<div class="form-group">
				<label class="control-label w100 left mar-rig10" style="text-align:right;">密码</label>				
				 <input type="password" class="form-control ipt_w4" value="" name="pwd" id="pwd">				
			</div>
			<div class="form-group">
				<label class="control-label w100 left mar-rig10" style="text-align:right;">是否审验发票</label>	
				<div class="ipt_check">			
					<input type="checkbox" value="option1"  />
				</div>							
			</div>
			<div class="form-group">
				<label class="control-label w100 left mar-rig10" style="text-align:right;">加急审核数量</label>
				<select class="form-control ipt_wauto" name=""><option>10</option><option>9</option></select>			
			</div>
			<div class="clearfix"></div>
			<div class="hr15"></div>
			<div class="text-center">
			 
				<button type="button" class=" btn btn-default btn_w_a2 btn_bg_6 ipt_w90 right save">保存</button>
			</div>
			<div class="hr15"></div>
	
		
	</div>
</div><!-- 设置账号到这里 -->
					
					</form:form>
				</div>
				<div class="hr15"></div>
			</div>		
		</div>
	</div>
</div>

<script type="text/javascript">
$(function(){
	//设置账号
	$( "#dialog_infos" ).dialog({
		autoOpen: false,
		width: 474,
		modal: true
	});	
	$( ".dialog_infos" ).click(function( event ) {
		$("#dialog_infos").find("input").val("");
		$( "#dialog_infos" ).dialog( "open" );
		event.preventDefault();
	});
	$( ".save" ).click(function( event ) {
		$( "#dialog_infos" ).dialog( "close" );
		event.preventDefault();
	});
})
</script>
<script type="text/javascript">
 
 $(function(){
	
	 $(".add").on("click",function(){
		 var c=$("#depNameed").val();
		 var pwd=$("#pwd").val();
		 var t=$("#filetype").val();
		 alert(t);
		 $("#inputForm").ajaxSubmit({
	            type: 'post',
	            url: " ${pageContext.request.contextPath}/a/newcharge/tChargecompany/update1?depNameed="+c+"&pwd="+pwd+"&typename="+t ,
	            data: $("#inputForm").serialize(),
	            success: function(data){
	            	if(data=="1"){
	                	alert("保存成功！");
	                	//window.location="${pageContext.request.contextPath}/a/clientManage_menue";newcharge/tChargecompany
	                	window.location="${pageContext.request.contextPath}/a/newcharge/tChargecompany/menue";
	            	}
	                $( "#inputForm").resetForm();
	            },
	            error: function(XmlHttpRequest, textStatus, errorThrown){
	                 //alert( "error");
	            }                
	        })
		 
		 
	 });
 })
</script>
<script type="text/javascript">
 
 $(function(){
	 $(".fff").on("click",function(){
		var c=$(".depNameed").val();
		alert(c);
	 });
 })
	
</script>
</body>
</html>
