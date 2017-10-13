<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>公式表管理</title>
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
		<li class="active"><a href="${ctx}/acctreportitem/tAcctreportitem/">公式表列表</a></li>
		<shiro:hasPermission name="acctreportitem:tAcctreportitem:edit"><li><a href="${ctx}/acctreportitem/tAcctreportitem/form">公式表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tAcctreportitem" action="${ctx}/acctreportitem/tAcctreportitem/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>公司客户外键：</label>
				<form:input path="fdbid" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>报表分组的外键：</label>
				<form:input path="reportId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>报表编码：</label>
				<form:input path="reportitem" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>科目编号：</label>
				<form:input path="accountid" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>公司客户外键</th>
				<th>报表分组的外键</th>
				<th>报表编码</th>
				<th>科目编号</th>
				<th>取数规则</th>
				<th>加减运算</th>
				<shiro:hasPermission name="acctreportitem:tAcctreportitem:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tAcctreportitem">
			<tr>
				<td><a href="${ctx}/acctreportitem/tAcctreportitem/form?id=${tAcctreportitem.id}">
					${tAcctreportitem.fdbid}
				</a></td>
				<td>
					${tAcctreportitem.reportId}
				</td>
				<td>
					${tAcctreportitem.reportitem}
				</td>
				<td>
					${tAcctreportitem.accountid}
				</td>
				<td>
					${tAcctreportitem.ftype}
				</td>
				<td>
					${tAcctreportitem.op}
				</td>
				<shiro:hasPermission name="acctreportitem:tAcctreportitem:edit"><td>
    				<a href="${ctx}/acctreportitem/tAcctreportitem/form?id=${tAcctreportitem.id}">修改</a>
					<a href="${ctx}/acctreportitem/tAcctreportitem/delete?id=${tAcctreportitem.id}" onclick="return confirmx('确认要删除该公式表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>