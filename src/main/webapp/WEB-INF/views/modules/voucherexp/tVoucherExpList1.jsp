<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>凭证摘要管理</title>
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
		<li class="active"><a href="${ctx}/voucherexp/tVoucherExp/">凭证摘要列表</a></li>
		<shiro:hasPermission name="voucherexp:tVoucherExp:edit"><li><a href="${ctx}/voucherexp/tVoucherExp/form">凭证摘要添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tVoucherExp" action="${ctx}/voucherexp/tVoucherExp/" method="post" class="breadcrumb form-search">
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
				<th>摘要</th>
				<th>科目名称</th>
				<th>借方金额</th>
				<th>贷方金额</th>
				<shiro:hasPermission name="voucherexp:tVoucherExp:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tVoucherExp">
			<tr>
				<td><a href="${ctx}/voucherexp/tVoucherExp/form?id=${tVoucherExp.id}">
					${tVoucherExp.exp}
				</a></td>
				<td>
					${tVoucherExp.accountName}
				</td>
				<td>
					${tVoucherExp.debit}
				</td>
				<td>
					${tVoucherExp.credit}
				</td>
				<shiro:hasPermission name="voucherexp:tVoucherExp:edit"><td>
    				<a href="${ctx}/voucherexp/tVoucherExp/form?id=${tVoucherExp.id}">修改</a>
					<a href="${ctx}/voucherexp/tVoucherExp/delete?id=${tVoucherExp.id}" onclick="return confirmx('确认要删除该凭证摘要吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>