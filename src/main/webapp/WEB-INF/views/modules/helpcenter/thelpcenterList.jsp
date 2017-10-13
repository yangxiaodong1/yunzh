<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>帮助中心管理</title>
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
		<li class="active"><a href="${ctx}/helpcenter/thelpcenter/">帮助中心列表</a></li>
		<shiro:hasPermission name="helpcenter:thelpcenter:edit"><li><a href="${ctx}/helpcenter/thelpcenter/form">帮助中心添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="thelpcenter" action="${ctx}/helpcenter/thelpcenter/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>标题</th>
				<th>update_date</th>
				<th>remarks</th>
				<shiro:hasPermission name="helpcenter:thelpcenter:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="thelpcenter">
			<tr>
				<td><a href="${ctx}/helpcenter/thelpcenter/form?id=${thelpcenter.id}">
					${thelpcenter.title}
				</a></td>
				<td>
					<fmt:formatDate value="${thelpcenter.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${thelpcenter.remarks}
				</td>
				<shiro:hasPermission name="helpcenter:thelpcenter:edit"><td>
    				<a href="${ctx}/helpcenter/thelpcenter/form?id=${thelpcenter.id}">修改</a>
					<a href="${ctx}/helpcenter/thelpcenter/delete?id=${thelpcenter.id}" onclick="return confirmx('确认要删除该帮助中心吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>