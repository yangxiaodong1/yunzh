<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>科目分组管理</title>
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
		<li class="active"><a href="${ctx}/account/taccountGroup/">科目分组列表</a></li>
		<shiro:hasPermission name="account:taccountGroup:edit"><li><a href="${ctx}/account/taccountGroup/form">科目分组添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="taccountGroup" action="${ctx}/account/taccountGroup/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>name：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>id</th>
				<th>fdbid</th>
				<th>group_id</th>
				<th>class_id</th>
				<th>name</th>
				<th>fdc</th>
				<th>cash</th>
				<th>update_date</th>
				<th>remarks</th>
				<shiro:hasPermission name="account:taccountGroup:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="taccountGroup">
			<tr>
				<td><a href="${ctx}/account/taccountGroup/form?id=${taccountGroup.id}">
					${taccountGroup.id}
				</a></td>
				<td>
					${taccountGroup.fdbid}
				</td>
				<td>
					${taccountGroup.groupId}
				</td>
				<td>
					${taccountGroup.classId}
				</td>
				<td>
					${taccountGroup.name}
				</td>
				<td>
					${taccountGroup.fdc}
				</td>
				<td>
					${taccountGroup.cash}
				</td>
				<td>
					<fmt:formatDate value="${taccountGroup.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${taccountGroup.remarks}
				</td>
				<shiro:hasPermission name="account:taccountGroup:edit"><td>
    				<a href="${ctx}/account/taccountGroup/form?id=${taccountGroup.id}">修改</a>
					<a href="${ctx}/account/taccountGroup/delete?id=${taccountGroup.id}" onclick="return confirmx('确认要删除该科目分组吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>