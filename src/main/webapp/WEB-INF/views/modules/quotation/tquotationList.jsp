<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>语录表管理</title>
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
		<li class="active"><a href="${ctx}/quotation/tquotation/">语录表列表</a></li>
		<shiro:hasPermission name="quotation:tquotation:edit"><li><a href="${ctx}/quotation/tquotation/form">语录表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tquotation" action="${ctx}/quotation/tquotation/" method="post" class="breadcrumb form-search">
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
				<th>内容</th>
				<th>0，null代表不启用,1代表启用</th>
				<th>update_date</th>
				<th>remarks</th>
				<shiro:hasPermission name="quotation:tquotation:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tquotation">
			<tr>
				<td><a href="${ctx}/quotation/tquotation/form?id=${tquotation.id}">
					${tquotation.title}
				</a></td>
				<td>
					${tquotation.content}
				</td>
				<td>
					${tquotation.startstatus}
				</td>
				<td>
					<fmt:formatDate value="${tquotation.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tquotation.remarks}
				</td>
				<shiro:hasPermission name="quotation:tquotation:edit"><td>
    				<a href="${ctx}/quotation/tquotation/form?id=${tquotation.id}">修改</a>
					<a href="${ctx}/quotation/tquotation/delete?id=${tquotation.id}" onclick="return confirmx('确认要删除该语录表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>