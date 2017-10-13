<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>金额平衡表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/balance/tBalance/">金额平衡表列表</a></li>
		<shiro:hasPermission name="balance:tBalance:edit"><li><a href="${ctx}/balance/tBalance/form">金额平衡表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tBalance" action="${ctx}/balance/tBalance/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>外键，引用公司客户表：</label>
				<form:input path="fdbid" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>外键，引用科目表的主键：</label>
				<form:input path="accountId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>账期：</label>
				<form:input path="accountPeriod" htmlEscape="false" maxlength="6" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<shiro:hasPermission name="balance:tBalance:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tBalance">
			<tr>
				<shiro:hasPermission name="balance:tBalance:edit"><td>
    				<a href="${ctx}/balance/tBalance/form?id=${tBalance.id}">修改</a>
					<a href="${ctx}/balance/tBalance/delete?id=${tBalance.id}" onclick="return confirmx('确认要删除该金额平衡表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>