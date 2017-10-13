<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>现金流量表管理</title>
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
		<li class="active"><a href="${ctx}/cashflow/tCashflow/">现金流量表列表</a></li>
		<shiro:hasPermission name="cashflow:tCashflow:edit"><li><a href="${ctx}/cashflow/tCashflow/form">现金流量表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tCashflow" action="${ctx}/cashflow/tCashflow/" method="post" class="breadcrumb form-search">
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
				<th>fdbid</th>
				<th>reportitem</th>
				<th>groupid</th>
				<th>yearperiod</th>
				<th>fcur</th>
				<th>ytdendamount</th>
				<th>ytdbeginamount</th>
				<th>lytdendamount</th>
				<th>lytdbeginamount</th>
				<th>flowtype</th>
				<th>fadddate</th>
				<th>periodtype</th>
				<shiro:hasPermission name="cashflow:tCashflow:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tCashflow">
			<tr>
				<td><a href="${ctx}/cashflow/tCashflow/form?id=${tCashflow.id}">
					${tCashflow.fdbid}
				</a></td>
				<td>
					${tCashflow.reportitem}
				</td>
				<td>
					${tCashflow.groupid}
				</td>
				<td>
					${tCashflow.yearperiod}
				</td>
				<td>
					${tCashflow.fcur}
				</td>
				<td>
					${tCashflow.ytdendamount}
				</td>
				<td>
					${tCashflow.ytdbeginamount}
				</td>
				<td>
					${tCashflow.lytdendamount}
				</td>
				<td>
					${tCashflow.lytdbeginamount}
				</td>
				<td>
					${tCashflow.flowtype}
				</td>
				<td>
					<fmt:formatDate value="${tCashflow.fadddate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tCashflow.periodtype}
				</td>
				<shiro:hasPermission name="cashflow:tCashflow:edit"><td>
    				<a href="${ctx}/cashflow/tCashflow/form?id=${tCashflow.id}">修改</a>
					<a href="${ctx}/cashflow/tCashflow/delete?id=${tCashflow.id}" onclick="return confirmx('确认要删除该现金流量表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>