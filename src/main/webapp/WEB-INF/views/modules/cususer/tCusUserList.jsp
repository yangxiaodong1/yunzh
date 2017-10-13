.<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转交管理</title>
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
		<li class="active"><a href="${ctx}/cususer/tCusUser/">转交列表</a></li>
		<shiro:hasPermission name="cususer:tCusUser:edit"><li><a href="${ctx}/cususer/tCusUser/form">转交添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tCusUser" action="${ctx}/cususer/tCusUser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>客户名称</th>
				<th>被转交用户</th>
				<th>转交用户</th>
				<th>接受状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="cususer:tCusUser:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tCusUser">
			<tr>
				<td><a href="${ctx}/cususer/tCusUser/form?id=${tCusUser.id}">
					${tCusUser.customerName}
				</a></td>
				<td>
					${tCusUser.sysuerNameBe}
				</td>
				<td>
					${tCusUser.sysuerName}
				</td>
				<td>
					${tCusUser.acceptState}
				</td>
				<td>
					<fmt:formatDate value="${tCusUser.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tCusUser.remarks}
				</td>
				<shiro:hasPermission name="cususer:tCusUser:edit"><td>
    				<a href="${ctx}/cususer/tCusUser/form?id=${tCusUser.id}">修改</a>
					<a href="${ctx}/cususer/tCusUser/delete?id=${tCusUser.id}" onclick="return confirmx('确认要删除该转交吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>