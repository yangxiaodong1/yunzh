<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>记账管理</title>
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
		<li><a href="${ctx}/workterrace/chargeToAccount/">记账列表</a></li>
		<li class="active"><a href="${ctx}/workterrace/chargeToAccount/form?id=${chargeToAccount.id}">记账<shiro:hasPermission name="workterrace:chargeToAccount:edit">${not empty chargeToAccount.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="workterrace:chargeToAccount:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="chargeToAccount" action="${ctx}/workterrace/chargeToAccount/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">customer_name：</label>
			<div class="controls">
				<form:input path="customerName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">code：</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">dt_time：</label>
			<div class="controls">
				<input name="dtTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${chargeToAccount.dtTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">upload_sum：</label>
			<div class="controls">
				<form:input path="uploadSum" htmlEscape="false" maxlength="12" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">abbr_name：</label>
			<div class="controls">
				<form:input path="abbrName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">industry：</label>
			<div class="controls">
				<form:input path="industry" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">legal_representative：</label>
			<div class="controls">
				<form:input path="legalRepresentative" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">id_number：</label>
			<div class="controls">
				<form:input path="idNumber" htmlEscape="false" maxlength="15" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">business_license_number：</label>
			<div class="controls">
				<form:input path="businessLicenseNumber" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">organization_code：</label>
			<div class="controls">
				<form:input path="organizationCode" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">establishment_date：</label>
			<div class="controls">
				<input name="establishmentDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${chargeToAccount.establishmentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">start_date：</label>
			<div class="controls">
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${chargeToAccount.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">init_period：</label>
			<div class="controls">
				<form:input path="initPeriod" htmlEscape="false" maxlength="6" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">current_period：</label>
			<div class="controls">
				<form:input path="currentPeriod" htmlEscape="false" maxlength="6" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">service_expiration_date：</label>
			<div class="controls">
				<form:input path="serviceExpirationDate" htmlEscape="false" maxlength="6" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">supervisor：</label>
			<div class="controls">
				<form:input path="supervisor" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">audit_person：</label>
			<div class="controls">
				<form:input path="auditPerson" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">remarks：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">contact_person：</label>
			<div class="controls">
				<form:input path="contactPerson" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">office_phone：</label>
			<div class="controls">
				<form:input path="officePhone" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">phone_number：</label>
			<div class="controls">
				<form:input path="phoneNumber" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">mobile_phone：</label>
			<div class="controls">
				<form:input path="mobilePhone" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">fax_number：</label>
			<div class="controls">
				<form:input path="faxNumber" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">qq：</label>
			<div class="controls">
				<form:input path="qq" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">e_mail：</label>
			<div class="controls">
				<form:input path="eMail" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">other：</label>
			<div class="controls">
				<form:input path="other" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">detailed_address：</label>
			<div class="controls">
				<form:input path="detailedAddress" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">value_added_tax：</label>
			<div class="controls">
				<form:input path="valueAddedTax" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">tax_rate：</label>
			<div class="controls">
				<form:input path="taxRate" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">state_tax_registration_number：</label>
			<div class="controls">
				<form:input path="stateTaxRegistrationNumber" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">computer_code1：</label>
			<div class="controls">
				<form:input path="computerCode1" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">state_tax_bureau：</label>
			<div class="controls">
				<form:input path="stateTaxBureau" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">remarks1：</label>
			<div class="controls">
				<form:input path="remarks1" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">local_tax_registration_number：</label>
			<div class="controls">
				<form:input path="localTaxRegistrationNumber" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">computer_code2：</label>
			<div class="controls">
				<form:input path="computerCode2" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">local_tax_bureau：</label>
			<div class="controls">
				<form:input path="localTaxBureau" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">remarks2：</label>
			<div class="controls">
				<form:input path="remarks2" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">remarks3：</label>
			<div class="controls">
				<form:input path="remarks3" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会计制度：</label>
			<div class="controls">
				<form:input path="system" htmlEscape="false" maxlength="11" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">国家税务登记专管员：</label>
			<div class="controls">
				<form:input path="stateTaxRegistrationAdmin" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证书到期时间：</label>
			<div class="controls">
				<input name="certificatesMature" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${chargeToAccount.certificatesMature}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地税税务登记管理员：</label>
			<div class="controls">
				<form:input path="localTaxRegistrationAdmin" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="workterrace:chargeToAccount:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>