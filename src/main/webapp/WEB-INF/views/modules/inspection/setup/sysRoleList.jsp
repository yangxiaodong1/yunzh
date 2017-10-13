<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
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
		<li class="active"><a href="${ctx}/inspection/setup/sysRole/">角色列表</a></li>
		<li><a href="${ctx}/inspection/setup/sysRole/form">角色添加</a></li>
	</ul>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>角色名称</th>
				<th>英文名称</th>
				<th>角色类型</th>
				<th>是否可用</th>
			<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="role">
			<tr>
				<td>
					${role.id}
				</td>
				<td>
					${role.name}
				</td>
				<td>
					${role.enname}
				</td>
				<td>
					${role.roleType}
				</td>
				<td>
					${role.useable}
				</td>
				<td>
    				<a href="${ctx}/inspection/setup/sysRole/form?id=${role.id}">修改</a>
					<a href="${ctx}/inspection/setup/sysRole/delete?id=${role.id}" onclick="return confirmx('确认要删除该角色吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>