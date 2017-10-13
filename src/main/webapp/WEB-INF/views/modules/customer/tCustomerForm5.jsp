<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
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
		<li><a href="${ctx}/customer/tCustomer/">用户列表</a></li>
		<li class="active"><a href="${ctx}/customer/tCustomer/form?id=${tCustomer.id}">用户<shiro:hasPermission name="customer:tCustomer:edit">${not empty tCustomer.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customer:tCustomer:edit">查看</shiro:lacksPermission></a></li>
		<li><a href="${ctx}/customer/tCustomer/form1?id=${tCustomer.id}">联系方式<shiro:hasPermission name="customer:tCustomer:edit">${not empty tCustomer.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customer:tCustomer:edit">查看</shiro:lacksPermission></a></li>
		<li><a href="${ctx}/customer/tCustomer/form2?id=${tCustomer.id}">税务资料<shiro:hasPermission name="customer:tCustomer:edit">${not empty tCustomer.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customer:tCustomer:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tCustomer" action="${ctx}/customer/tCustomer/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">公司名称：</label>
			<div class="controls">
				<form:input path="customerName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">营业执照号：</label>
			<div class="controls">
				<form:input path="businessLicenseNumber" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始记账日期：</label>
			<div class="controls">
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tCustomer.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组织机构代码：</label>
			<div class="controls">
				<form:input path="organizationCode" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会计制度：</label>
			<div class="controls">
				<form:select path="system" htmlEscape="false"> 
			 		<form:option value="1">2013小企业会计准则</form:option>
			 		<form:option value="2">2007企业会计准则</form:option>
             	</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">记账人：</label>
			<div class="controls">
				<form:input path="supervisor" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>