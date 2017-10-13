<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转交管理</title>
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
			
			$("#btnSubmit1").on("click",function(){
				$("#inputForm1").submit();
			})
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<!--  
		<li><a href="${ctx}/cususer/tCusUser/">转交列表</a></li>
		-->
		<li><a href="${ctx}/sys/user/info">个人信息</a></li>
		<li><a href="${ctx}/sys/user/modifyPwd">修改密码</a></li>
		<li class="active"><a href="${ctx}/cususer/tCusUser/form?id=${tCusUser.id}">转交<shiro:hasPermission name="cususer:tCusUser:edit">${not empty tCusUser.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cususer:tCusUser:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tCusUser" action="${ctx}/cususer/tCusUser/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">客户名称：</label>
			 <form:select path="customerid" htmlEscape="false"> 
			 	<c:forEach items="${listcustomer}" var="cusomer">
			 		<form:option value="${cusomer.id}">${cusomer.customerName}</form:option>
			 	</c:forEach>     
             </form:select>
		</div>
		<div class="control-group">
			<label class="control-label">转交用户：</label>
			<form:select path="userid" htmlEscape="false"> 
			 	<c:forEach items="${listuser}" var="user">
			 		<form:option value="${user.id}">${user.name}</form:option>
			 	</c:forEach>     
             </form:select>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交"/>&nbsp;
			<!-- <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
		</div>
	</form:form> 
	
	<c:forEach items="${tcususerList}" var="tcususerList">
		<form:form id="inputForm1" modelAttribute="tCusUser" action="${ctx}/cususer/tCusUser/updateAcceptState?id=${tcususerList.id}" method="post" class="form-horizontal">
			${tcususerList.sysuerNameBe}转交了------${tcususerList.customerName}------公司账簿
			<c:if test="${tcususerList.acceptState=='0'}">
				<input id="btnSubmit1" class="btn btn-primary" type="submit" value="接 受"/>
			</c:if>
		</form:form>
	</c:forEach>
	  
</body>
</html>