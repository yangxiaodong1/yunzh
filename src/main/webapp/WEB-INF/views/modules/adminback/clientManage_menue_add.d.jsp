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
<title>客户列表-编辑</title>
<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery.validate.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_3.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctxStatic}/webuploader/webuploader.html5only.js"></script>
<script type="text/javascript" src="${ctxStatic}/webuploader/up.js"></script>
<link href="${ctxStatic}/webuploader/webuploader.css" type="text/css" rel="stylesheet" />
<style>
.ipt_pos_a{position:relative;height:25px;width:90px;overflow:hidden;}
.ipt_pos_a .ipt_pos_1{opacity:0;width:90px;position:absolute;top:0;left:0;z-index:2;}
.ipt_pos_a .ipt_pos_2{position:absolute;top:0;left:0;z-index:1;}
.uploader-list{width:195px;}
.thumbnail{border-radius:0;border-color:#999999;}
</style>
</head>

<body>
<div class="pad_15_10">
	<div class="panel panel-default mar_b0 radius0 border_none pad_10 ">
		<div class="panel panel-default mar_b0 radius0 border_i4 ">
			<div class="pad_lr_35">
				<div class="hr15"></div>
				<form:form id="inputForm" modelAttribute="tChargecompany" action="${pageContext.request.contextPath}/a/newcharge/tChargecompany/ds" method="post" class="form-horizontal form-horizontal2" enctype="multipart/form-data">
		             <form:hidden path="id"/>
				<div class="panel-heading bg_col_2 panel-head-pub">
					<div class="left pad_t_3">
						<span class="glyphicon glyphicon_a5 ico_pub bg_pub" aria-hidden="true"></span><span class="font_cc8 font-14">客户基本信息</span>
					</div>
					<div class="right">
						<button type="button" class="btn btn-default btn_w_a2 btn_bg_6 ipt_w90 left mar-rig10 dialog_infos">设置帐号</button>
					<!-- <button type="button" class=" add btn btn-default btn_w_a2 btn_bg_6 ipt_w90 left ">保存</button> -->	
					<input type="submit" class="  btn btn-default btn_w_a2 btn_bg_6 ipt_w90 left "/>保存
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="font_cc10">
					
								<div class="form-group">
									<label class="col-sm-3 control-label">记账公司名称</label>
									<div class="col-sm-9">
									<form:input path="chargecomname" htmlEscape="false" maxlength="64" class="input-xlarge form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">所在城市</label>
									<div class="col-sm-9">
										<!-- <select class="form-control ipt_wauto">
										<option>浙江省</option>
										</select>
										<select class="form-control ipt_wauto">
										<option>杭州市</option>
										</select>
										<select class="form-control ipt_wauto">
										<option>余杭县</option>
										</select> -->
										<form:hidden path="city" htmlEscape="false" maxlength="200" class="input-xlarge form-control ipt_w4"/>
							 <sys:treeselect id="area"  name="area.id" value="${office.area.id}" labelName="area.name" labelValue="${office.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="required" />
									</div>
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
									 	
									 	<form:input path="companyrunrange" htmlEscape="false" maxlength="64" class="input-xlarge form-control ipt_w4"  placeholder="可以以逗号隔开"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label">使用状态</label>
									<div class="col-sm-9">
									 	<label class="radio-inline">
										 <form:radiobutton path="usestatus" name="RadioOptions2" id="inlineRadio1" value="0"/> 试用客户
										</label>
										<label class="radio-inline">
										 <form:radiobutton path="usestatus" name="RadioOptions2" id="inlineRadio2" value="1" />正式客户
										</label>										
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label">是否审验发票</label>	
									<div class="col-sm-9">
									<form:checkbox path="auditbill" htmlEscape="false" maxlength="1" class="input-xlarge " value="1"/>										
																		
										<label class="control-label" style="margin-left:29px;margin-right:20px;">加急审核数量</label>
									 <form:select path="rapauditnum" class="form-control ipt_wauto">   
                                                   <option>请选择</option>   
                                                   <form:options items="${ballMap}"/>   
                                      </form:select>  
									
									</div>									
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">对接人</label>
									<div class="col-sm-9">
									       <form:select path="rapauditnum" class="form-control ipt_wauto" style="width:195px;">   
                                                   <option>请选择</option>   
                                                   <form:options items="${ballMap}"/>   
                                          </form:select>  
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
									<label class="col-sm-5 control-label" style="width:160px;">法人姓名</label>
									<div class="col-sm-7">
										
										<form:input path="legalname" htmlEscape="false" maxlength="64" class="input-xlarge form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-5 control-label" style="width:160px;">联系电话</label>
									<div class="col-sm-7">
									<form:input path="contectphone" htmlEscape="false" maxlength="64" class="input-xlarge "/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-5 control-label" style="width:160px;">身份证号</label>
									<div class="col-sm-7">
										<form:input path="cardnumber" htmlEscape="false" maxlength="100" class="input-xlarge "/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-5 control-label" style="width:160px;">身份证附件</label>
									<div class="col-sm-7">
										
										<div class="uploader-list" id="fileList"></div>
										<div class="ipt_pos_a">											
											<div class='ipt_pos_1' id="filePicker">选择图片</div>
											<button type="button" class="btn btn-default btn_w_a2 btn_bg_6 ipt_w90 ipt_pos_2">添加</button>
											<button type="button" class="btn btn-default btn_w_a2 btn_bg_6 ipt_w90 ipt_pos_2">添加w</button>
										</div>
    									
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-5 control-label" style="width:160px;">营业执照号码</label>
									<div class="col-sm-7">
										<form:input path="runnumber" htmlEscape="false" maxlength="100" class="input-xlarge "/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-5 control-label" style="width:160px;">营业执照附件</label>
									<div class="col-sm-7">
										<div class="uploader-list" id="fileList2"></div>
										<div class="ipt_pos_a">											
											<div class='ipt_pos_1' id="filePicker2">选择图片</div>
											<button type="button" class="btn btn-default btn_w_a2 btn_bg_6 ipt_w90 ipt_pos_2">添加</button>
										</div>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-5 control-label" style="width:160px;">税务登记证号码</label>
									<div class="col-sm-7">
										<form:input path="taxrenum" htmlEscape="false" maxlength="100" class="input-xlarge "/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-5 control-label" style="width:160px;">税务登记证附件</label>
									<div class="col-sm-7">
										<div class="uploader-list" id="fileList3"></div>
										<div class="ipt_pos_a">											
											<div class='ipt_pos_1' id="filePicker3">选择图片</div>
											<button type="button" class="btn btn-default btn_w_a2 btn_bg_6 ipt_w90 ipt_pos_2">添加</button>
										</div>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-5 control-label" style="width:160px;">组织机构代码</label>
									<div class="col-sm-7">
										<form:input path="organcode" htmlEscape="false" maxlength="100" class="input-xlarge "/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-5 control-label" style="width:160px;">组织机构代码证附件</label>
									<div class="col-sm-7">
										<div class="uploader-list" id="fileList4"></div>
										<div class="ipt_pos_a">											
											<div class='ipt_pos_1' id="filePicker4">选择图片</div>
											<button type="button" class="btn btn-default btn_w_a2 btn_bg_6 ipt_w90 ipt_pos_2">添加</button>
										</div>
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
				 <input type="text" class="form-control ipt_w4" value="" name="depNameed" id="depNameed"/>				
			</div>
			<div class="form-group">
				<label class="control-label w100 left mar-rig10" style="text-align:right;">密码</label>				
				 <input type="text" class="form-control ipt_w4" value="" name="pwd" id="pwd"/>				
			</div>
			
			<div class="clearfix"></div>
			<div class="hr15"></div>
			<div class="text-center">
				<button type="button" class="btn btn-default btn_w_a2 btn_bg_6 ipt_w90 right ff ">保存</button>
			</div>
			<div class="hr15"></div>
		
	</div>
</div>
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
	$( ".ff" ).click(function( event ) {
		$( "#dialog_infos" ).dialog( "close" );
		event.preventDefault();
	});
	$(".addimg").click(function(){	
	
		$(".thumb_imgs").attr("src",function(){		
			return $("[attrupload=attrupload]").val();
		})	
	});
	
	
})
</script>
<script type="text/javascript">
 
 $(function(){
	
	 $(".add").on("click",function(){
		 var c=$("#depNameed").val();
		 var pwd=$("#pwd").val();
		// var t=$("#filetype").val();
		alert(1);
		 $("#inputForm").ajaxSubmit({
	            type: 'post',
	            url:'${pageContext.request.contextPath}/a/newcharge/tChargecompany/update2',
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
</body>
</html>
