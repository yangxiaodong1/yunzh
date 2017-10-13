<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统消息管理</title>
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
		<li class="active"><a href="${ctx}/sysnews/tsysnews/">系统消息列表</a></li>
		<shiro:hasPermission name="sysnews:tsysnews:edit"><li><a href="${ctx}/sysnews/tsysnews/form">系统消息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tsysnews" action="${ctx}/sysnews/tsysnews/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>消息标题：</label>
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
				<th>消息标题</th>
				<th>消息内容</th>
				<th>消息的发送状态,</th>
				<th>是否定时发送状态,0代表定时1代表不定时</th>
				<th>create_by</th>
				<th>update_date</th>
				<th>remarks</th>
				<shiro:hasPermission name="sysnews:tsysnews:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tsysnews">
			<tr>
				<td><a href="${ctx}/sysnews/tsysnews/form?id=${tsysnews.id}">
					${tsysnews.title}
				</a></td>
				<td>
					${tsysnews.content}
				</td>
				<td>
					${fns:getDictLabel(tsysnews.sendstatus, '', '')}
				</td>
				<td>
					${fns:getDictLabel(tsysnews.settimestatus, '', '')}
				</td>
				<td>
					${tsysnews.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${tsysnews.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tsysnews.remarks}
				</td>
				<shiro:hasPermission name="sysnews:tsysnews:edit"><td>
    				<a href="${ctx}/sysnews/tsysnews/form?id=${tsysnews.id}">修改</a>
					<a href="${ctx}/sysnews/tsysnews/delete?id=${tsysnews.id}" onclick="return confirmx('确认要删除该系统消息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>