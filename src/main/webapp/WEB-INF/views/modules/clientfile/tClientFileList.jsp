<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>添加图片管理</title>
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
		<li class="active"><a href="${ctx}/clientfile/tClientFile/">添加图片列表</a></li>
		<shiro:hasPermission name="clientfile:tClientFile:edit"><li><a href="${ctx}/clientfile/tClientFile/form">添加图片添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tClientFile" action="${ctx}/clientfile/tClientFile/" method="post" class="breadcrumb form-search">
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
				<th>文件原名</th>
				<th>上传文件路径</th>
				<th>create_by</th>
				<th>create_date</th>
				<th>update_by</th>
				<th>update_date</th>
				<th>remarks</th>
				<shiro:hasPermission name="clientfile:tClientFile:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tClientFile">
			<tr>
				<td><a href="${ctx}/clientfile/tClientFile/form?id=${tClientFile.id}">
					${tClientFile.oldName}
				</a></td>
				<td>
					${tClientFile.fileName}
				</td>
				<td>
					${tClientFile.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${tClientFile.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tClientFile.updateBy.id}
				</td>
				<td>
					<fmt:formatDate value="${tClientFile.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tClientFile.remarks}
				</td>
				<shiro:hasPermission name="clientfile:tClientFile:edit"><td>
    				<a href="${ctx}/clientfile/tClientFile/form?id=${tClientFile.id}">修改</a>
					<a href="${ctx}/clientfile/tClientFile/delete?id=${tClientFile.id}" onclick="return confirmx('确认要删除该添加图片吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>