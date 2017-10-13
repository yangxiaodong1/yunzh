<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>常见问题表管理</title>
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
		<li class="active"><a href="${ctx}/comquestion/tcomquest/">常见问题表列表</a></li>
		<shiro:hasPermission name="comquestion:tcomquest:edit"><li><a href="${ctx}/comquestion/tcomquest/form">常见问题表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tcomquest" action="${ctx}/comquestion/tcomquest/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>内容：</label>
				<form:input path="content" htmlEscape="false" class="input-medium"/>
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
				<th>内容</th>
				<th>update_date</th>
				<th>remarks</th>
				<shiro:hasPermission name="comquestion:tcomquest:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tcomquest">
			<tr>
				<td><a href="${ctx}/comquestion/tcomquest/form?id=${tcomquest.id}">
					${tcomquest.title}
				</a></td>
				<td>
					${tcomquest.content}
				</td>
				<td>
					<fmt:formatDate value="${tcomquest.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tcomquest.remarks}
				</td>
				<shiro:hasPermission name="comquestion:tcomquest:edit"><td>
    				<a href="${ctx}/comquestion/tcomquest/form?id=${tcomquest.id}">修改</a>
					<a href="${ctx}/comquestion/tcomquest/delete?id=${tcomquest.id}" onclick="return confirmx('确认要删除该常见问题表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>