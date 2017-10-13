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
<title>加盟申请-客户管理-后台管理</title>
<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery.validate.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_3.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctxStatic}/jquery/jquery.form.js"></script>
<script src="${ctxStatic}/jquery/jquery.cxselect.min.js"></script>
</head>

<body>
<div class="pad_15_10">
	<div class="panel panel-default mar_b0 radius0 border_none pad_10 ">
		<div class="panel panel-default mar_b0 radius0 border_i4 ">
			<div class="pad_lr_35">
				<div class="hr15"></div>
				
				<form:form id="inputForm" modelAttribute="tjoinappl" action="${ctx}/newcharge/tjoinappl/saveApp" method="post" class="form-horizontal">
		             <form:hidden path="id"/>
				<div class="panel-heading bg_col_2 panel-head-pub">
					<div class="left pad_t_3">
						<span class="glyphicon glyphicon_a5 ico_pub bg_pub" aria-hidden="true"></span><span class="font_cc8 font-14">客户基本信息</span>
					</div>
					<div class="right">
						
						<input type="submit" class="btn btn-default btn_w_a2 btn_bg_6 ipt_w90 left " value="保存"/>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="font_cc10">
					
								<div class="form-group">
									<label class="col-sm-3 control-label">申请公司名称</label>
									<div class="col-sm-9">
									 	<form:input path="compname" htmlEscape="false" maxlength="64" class="input-xlarge form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">所在城市</label>
									
									<form:hidden path="city" htmlEscape="false" maxlength="200" class="input-xlarge form-control ipt_w4"/>
								<!-- 
						             <sys:treeselect id="area"  name="area.id" value="${office.area.id}" labelName="area.name" labelValue="${office.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="required" />
					 -->
					                <div class="col-sm-9" id="city_china_val">
										<form:select class="form-control ipt_wauto province select" data-first-title="选择省" path="city1" readonly="true">
										<option selected>浙江省</option>
										</form:select>
										<form:select class="form-control ipt_wauto city select" data-first-title="选择市" path="city2" readonly="true">
										<option>杭州市</option>
										</form:select>
										<form:select class="form-control ipt_wauto area select" data-value="西湖区" data-first-title="选择地区" path="city3" readonly="true">
										<option>西湖区</option>
										</form:select>
									</div>
					
								</div>							
								<div class="form-group">
									<label class="col-sm-3 control-label">联系人</label>
									<div class="col-sm-9">
									 <form:input path="contectperson" htmlEscape="false" maxlength="64" class="input-xlarge  form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">联系电话</label>
									<div class="col-sm-9">
									 		<form:input path="contectphone" htmlEscape="false" maxlength="64" class="input-xlarge form-control ipt_w4"/>
									</div>
								</div>
				</div>
					</form:form>
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
//城市
$.cxSelect.defaults.url = '${ctxStatic}/cityData.min.json';
$('#city_china_val').cxSelect({
  selects: ['province', 'city', 'area'],
  nodata: 'none'
});
 $(function(){
	 $(".fff").on("click",function(){
		var c=$(".depNameed").val();
		alert(c);
	 });
 })
	
</script>
</body>
</html>
