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
<meta name="decorator" content="default"/>
<title>添加系统消息-后台管理</title>
<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>

<script src="${ctxStatic}/jquery/jquery.validate.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_3.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<style>
.ui-datepicker .ui-datepicker-title select{border-radius:0;padding:0;line-height:20px;height:20px;font-weight:normal;}
input[type="radio"], input[type="checkbox"]{margin-top:0;}
</style>
</head>

<body>
<div class="pad_15_10">
	<div class="panel panel-default mar_b0 radius0 border_none">
		<form:form id="inputForm" modelAttribute="tsysnews" action="${ctx}/sysnews/tsysnews/savesysNews" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<div class="panel-heading bg_col_2 panel-head-pub">
			<div class="left pad_t_3">
				<span class="glyphicon glyphicon_a9 ico_pub bg_pub mar-top2" aria-hidden="true"></span><span class="font_cc8 mar-rig10 font-14">添加新的系统消息</span>
				
			</div>
			<div class="right">
				<button type="submit" class="btn btn-default btn_w_a2 btn_bg_6 ipt_w90 left mar-rig10 dialog_infos">保存</button>
				
			</div>
			<div class="clearfix"></div>
		</div>
		<div class="panel-body">
			<div class="form-group">
				<label class="control-label w80 mar-rig10" style="text-align:right;">标题</label>				
					
				<form:input path="title" htmlEscape="false" maxlength="64" class="input-xlarge form-control ipt_w5 w200" style="width:300px"/>			
			</div>
			<div class="form-group">
				<label class="control-label w80 mar-rig10" style="text-align:right;">发送时间</label>	
			<!-- 	<form:input path="sendtimeString" htmlEscape="false" class="input-xlarge form-control ipt_w5 w200 mar-rig10" id="datepicker1"/>
			
				 -->
				 <form:input path="sendtimeString" htmlEscape="false" class="input-xlarge form-control ipt_w5 w200 mar-rig10 datepickerr" id="datepicker1" style="width:300px"/>
			
				<span class="line_h24">
			
				 <form:radiobutton path="settimestatus" value="0" />定时发送	
				</span>			
			</div>
			<div class="form-group">
			 	<label class="control-label w80 mar-rig10 left" style="text-align:right;" >添加内容</label>	
				<div class="left" style="width:700px">
				<form:textarea path="content" htmlEscape="true" rows="8" class="input-xxlarge " style="display:inline-block;width:100%;border-radius:0;border-color:#999999;resize:none;"/>
				<sys:ckeditor replace="content" uploadPath="/cms/site" />
				</div>
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
	/*日期*/
	$( "#datepicker1" ).datepicker({
		dateFormat:'yy-mm-dd'
	});
	
	$( ".datepicker" ).datepicker({
		  monthNamesShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],  // 区域化月名为中文  
		  prevText: '上月',         // 前选按钮提示  
		  nextText: '下月',         // 后选按钮提示  
		           changeYear: true,          // 年下拉菜单  
		           changeMonth: true,             // 月下拉菜单  
		           showButtonPanel: true,         // 显示按钮面板  
		           showMonthAfterYear: true,  // 月份显示在年后面  
		           currentText: "本月",         // 当前日期按钮提示文字  
		           closeText: "确认",           // 关闭按钮提示文字  
		           dateFormat: "yy-mm-dd",       // 日期格式  
		  onClose: function(dateText, inst) {// 关闭事件  
		      var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();  
		      var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();  
		      var day = $("#ui-datepicker-div .ui-datepicker-day :selected").val();  
		      $(this).datepicker('setDate', new Date(year, month, day));  
		  }  
	});
})
</script>
</body>
</html>
