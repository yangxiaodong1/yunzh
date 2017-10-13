<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户-角色管理</title>
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
		<li class="active"><a href="${ctx}/inspection/setup/sysUserRole/">用户-角色列表</a></li>
		<li><a href="${ctx}/inspection/setup/sysUserRole/form">用户-角色添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="sysUserRole" action="${ctx}/inspection/setup/sysUserRole/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户编号：</label>
				<form:input path="userId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>角色编号：</label>
				<form:input path="roleId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户编号</th>
				<th>角色编号</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysUserRole">
			<tr>
				<td><a href="${ctx}/inspection/setup/sysUserRole/form?id=${sysUserRole.id}">
					${sysUserRole.userId}
				</a></td>
				<td>
					${sysUserRole.roleId}
				</td>
				<td>
    				<a href="${ctx}/inspection/setup/sysUserRole/form?id=${sysUserRole.id}">修改</a>
					<a href="${ctx}/inspection/setup/sysUserRole/delete?id=${sysUserRole.id}" onclick="return confirmx('确认要删除该用户-角色吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>