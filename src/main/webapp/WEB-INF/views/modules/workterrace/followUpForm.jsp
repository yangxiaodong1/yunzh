<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>跟进表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/workterrace/followUp/">跟进表列表</a></li>
		<li class="active"><a href="${ctx}/workterrace/followUp/form?id=${followUp.id}">跟进表${not empty followUp.id?'修改':'添加'}查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="followUp" action="${ctx}/workterrace/followUp/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">客户id：</label>
			<div class="controls">
				<form:input path="customerId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">跟进内容：</label>
			<div class="controls">
				    <form:radiobutton path="upContent" htmlEscape="false" value="信息公示已完成"/>信息公示已完成
               <form:radiobutton path="upContent" htmlEscape="false" value="企业所得税汇算缴纳"/>企业所得税汇算缴纳   
                <form:radiobutton path="upContent" htmlEscape="false" value="残疾人保证金缴纳"/>残疾人保证金缴纳   

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">跟进时间：</label>
			<div class="controls">
				<input name="upTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${followUp.upTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人：</label>
			<div class="controls">
				<form:input path="linkman" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系号码：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remarks1" htmlEscape="false" maxlength="300" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>