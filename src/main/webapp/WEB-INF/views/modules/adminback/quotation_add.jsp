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
<title>添加语录-后台管理</title>
<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery.validate.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_3.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
</head>

<body>
<div class="pad_15_10">
	<div class="panel panel-default mar_b0 radius0 border_none">
		<form:form id="inputForm" modelAttribute="tquotation"  action="${pageContext.request.contextPath}/a/quotation/tquotation/saveQuotation" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		
		<div class="panel-heading bg_col_2 panel-head-pub">
			<div class="left pad_t_3">
				<span class="glyphicon glyphicon_a9 ico_pub bg_pub mar-top2" aria-hidden="true"></span><span class="font_cc8 mar-rig10 font-14">添加新的语录</span>
				
			</div>
			<div class="right">
				<button type="submit" class="  btn btn-default btn_w_a2 btn_bg_6 ipt_w90 left mar-rig10 dialog_infos ">保存</button>
				
			</div>
			<div class="clearfix"></div>
		</div>
		<div class="panel-body">
		<!-- 	<div class="form-group">
				<label class="control-label w80 mar-rig10 left" style="text-align:right;">标题</label>	
				<form:input path="title" htmlEscape="false" maxlength="64" class="input-xlarge form-control ipt_w5 w200" id="title"/>			
			</div>
			
			
			-->
			<div class="form-group">
			    <label class="control-label w80 mar-rig10 left" style="text-align:right;">语录添加</label>	
				<form:textarea path="content" htmlEscape="false" rows="8" id="content" class="input-xxlarge form-control " style="display:inline-block;width:600px;border-radius:0;border-color:#999999;resize:none;"/>
			</div> 
			
		</div>
		
			</form:form>
	</div>
	<div class="hr15"></div>	
</div>
<script type="text/javascript">
$(function(){
	$("#h_rig").height(function(){
		return $("#h_lft").height();
	});
	
	
	
	//添加语录
	$(".adda").click(function() {
		alert(1);
		if($("#title").val()!="" && $("#content").val()!=""){
		$("#inputForm").ajaxSubmit({
            type: 'post',
            url: "${pageContext.request.contextPath}/a/quotation/tquotation/saveQuotation",
            data: $("#inputForm").serialize(),
            success: function(data){
            	if(data=="1"){
                	alert("保存成功！");	
                	window.location="${pageContext.request.contextPath}/a/quotation/tquotation/QuotationMenue";
            	}
                $( "#inputForm").resetForm();
            },
            error: function(XmlHttpRequest, textStatus, errorThrown){
                 //alert( "error");
            }                
        })
        }else{
        	alert("请填写必须要输入的信息！");
        }
	});
	 $(".add").on("click",function(){
		 alert(234);
		 $("#inputForm").ajaxSubmit({
	            type: 'post',
	            url: "${pageContext.request.contextPath}/a/quotation/tquotation/saveQuotation" ,
	            data: $("#inputForm").serialize(),
	            success: function(data){
	            	if(data=="1"){
	                	alert("保存成功！");
	                	//window.location="${pageContext.request.contextPath}/a/clientManage_menue";newcharge/tChargecompany
	                	window.location="${pageContext.request.contextPath}/a/quotation/tquotation/QuotationMenue";
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
