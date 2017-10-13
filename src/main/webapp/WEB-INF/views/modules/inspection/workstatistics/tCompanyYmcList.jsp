<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户总数记录管理</title>
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
		<li class="active"><a href="${ctx}/inspection/workstatistics/tCompanyYmc/">客户总数记录列表</a></li>
		<shiro:hasPermission name="inspection:workstatistics:tCompanyYmc:edit"><li><a href="${ctx}/inspection/workstatistics/tCompanyYmc/form">客户总数记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tCompanyYmc" action="${ctx}/inspection/workstatistics/tCompanyYmc/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>记账公司id：</label>
				<form:input path="companyId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>当前年月：</label>
				<form:input path="ymonth" htmlEscape="false" maxlength="6" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>记账公司id</th>
				<th>当前年月</th>
				<th>当前年月正常服务客户数量</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="inspection:workstatistics:tCompanyYmc:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tCompanyYmc">
			<tr>
				<td><a href="${ctx}/inspection/workstatistics/tCompanyYmc/form?id=${tCompanyYmc.id}">
					${tCompanyYmc.companyId}
				</a></td>
				<td>
					${tCompanyYmc.ymonth}
				</td>
				<td>
					${tCompanyYmc.ymonthCount}
				</td>
				<td>
					<fmt:formatDate value="${tCompanyYmc.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tCompanyYmc.remarks}
				</td>
				<shiro:hasPermission name="inspection:workstatistics:tCompanyYmc:edit"><td>
    				<a href="${ctx}/inspection/workstatistics/tCompanyYmc/form?id=${tCompanyYmc.id}">修改</a>
					<a href="${ctx}/inspection/workstatistics/tCompanyYmc/delete?id=${tCompanyYmc.id}" onclick="return confirmx('确认要删除该客户总数记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>