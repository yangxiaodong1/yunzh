<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构表管理</title>
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
		<li class="active"><a href="${ctx}/inspection/setup/sysOffice/">机构表列表</a></li>
		<li><a href="${ctx}/inspection/setup/sysOffice/form">机构表添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="office" action="${ctx}/inspection/setup/sysOffice/list/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>编号：</label>
				<form:input path="id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>父级编号：</label>
				<form:input path="parentId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>所有父级编号：</label>
				<form:input path="parentIds" htmlEscape="false" maxlength="2000" class="input-medium"/>
			</li>
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>机构类型：</label>
				<form:input path="type" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>机构等级：</label>
				<form:input path="grade" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	
	
	
		<c:forEach items="${listuser}" var="user">
		<p>
			loginName:${user.loginName}<br/>
	password:${user.password}
		</p>
			
		</c:forEach>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>父级编号</th>
				<th>所有父级编号</th>
				<th>名称</th>
				<th>机构类型</th>
				<th>机构等级</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		
		
		<c:forEach items="${page.list}" var="office">
			<tr>
				<td><a href="${ctx}/inspection/setup/sysOffice/form?id=${office.id}">
					${office.id}
				</a></td>
				<td>
					${office.parentId}
				</td>
				<td>
					${office.parentIds}
				</td>
				<td>
					${office.name}
				</td>
				<td>
					${office.type}
				</td>
				<td>
					${office.grade}
				</td>
				<td>
					<fmt:formatDate value="${office.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${office.remarks}
				</td>
				<td>
				<a href="${ctx}/inspection/setup/sysOffice/findCompanyAdmin?id=${office.id}">管理员账号</a>
    				<a href="${ctx}/inspection/setup/sysOffice/form?id=${office.id}">修改</a>
					<a href="${ctx}/inspection/setup/sysOffice/delete?id=${office.id}" onclick="return confirmx('确认要删除该机构表吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>