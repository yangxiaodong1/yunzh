<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>科目信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {			
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
		/* function paretntInfo(){
			if(${count}==1){
				var number=$("#accuntNumber").val();
				var urlinfo="${ctx}/account/tAccount/form?count=2&groundid=${typeid}";
				$("#inputForm").attr("action",urlinfo);
				$("#inputForm").submit();
			}			
		} */
		function paretntInfo(){
				var accunt=$("#accuntId").val();
				if(${count}==1){
                $.ajax({  
                    type : "GET",  
                    url : "parentInfo",  
                    data : "accuntId="+accunt+"&groupId="+${typeid},  
                    dataType: "json",  
                    success : function(msg) { 
                    	if(msg!=null){
                    		$("#parentinfo").attr("value",msg.accuntId+"  "+msg.accountName);
                    		$("#level").attr("value",msg.level);
                    		$("#parentid").attr("value",msg.id);
                    	}else{                    		
                        	$("#parentinfo").attr("value","无上级目录");
                    		$("#level").attr("value",null);
                    		$("#parentid").attr("value",null);
                    	}                    
                    },error : function() {
                    	alert("失败");
                    } 
                }); 
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/account/tAccount/">科目信息列表</a></li>
		<li class="active"><a href="${ctx}/account/tAccount/form">科目信息<shiro:hasPermission name="account:tAccount:edit">${not empty tAccount.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="account:tAccount:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tAccount" action="${ctx}/account/tAccount/save?typeid=${typeid }" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${infoUpdate.id }">
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">科目编号：</label>
			<div class="controls">
				<input id="accuntId" type="text" name=accuntId value="${InfoById.accuntIdfor }${infoUpdate.accuntId }"  onblur="paretntInfo()"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">科目名称：</label>
			<div class="controls">
				<input name="accountName" type="text" value="${infoUpdate.accountName }"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上级科目：</label>
			<div class="controls">
				<input type="hidden" id="level" name="level" value="${InfoById.level }"/>
				<input type="hidden" id="parentid" name="idFor" value="${InfoById.id }"/>
				<c:choose>
					<c:when test="${InfoById.id==null}">
						<input id="parentinfo" type="text" value="无上级目录" readonly="readonly"/>
					</c:when>
					<c:otherwise>					
						<input type="text" value="${InfoById.accuntId }${InfoById.accountName}${infoUpdate.parentId.accuntId }${infoUpdate.parentId.accountName}" readonly="readonly"/>
					</c:otherwise>
				</c:choose>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">科目类别：</label>
			<div class="controls">
				<select name="groupId" class="selector" style="width:200px" >
					<c:forEach items="${ground }" var="ground">
						<option value="${ground.groupId }">${ground.name }</option>
					</c:forEach>					
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">余额方向：</label>
			<div class="controls">
				<input type="radio" name="dc" value="借" checked="checked"/>借
				<input type="radio" name="dc" value="贷" />贷
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="account:tAccount:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>