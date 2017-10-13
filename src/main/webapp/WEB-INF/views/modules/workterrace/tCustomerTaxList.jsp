<!doctype html>
<html lang="en">
<head>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<head>
	<title>报税表管理</title>
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
		<li class="active"><a href="${ctx}/workterrace/tCustomerTax/">报税表列表</a></li>
		<shiro:hasPermission name="workterrace:tCustomerTax:edit"><li><a href="${ctx}/workterrace/tCustomerTax/form">报税表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tCustomerTax" action="${ctx}/workterrace/tCustomerTax/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>客户id：</label>
				<form:input path="customerId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>客户id</th>
				<th>序号</th>
				<th>税种科目id</th>
				<th>税种科目名称</th>
				<th>计税基数</th>
				<th>税率</th>
				<th>应纳税额</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="workterrace:tCustomerTax:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tCustomerTax">
			<tr>
				<td><a href="${ctx}/workterrace/tCustomerTax/form?id=${tCustomerTax.id}">
					${tCustomerTax.id}
				</a></td>
				<td>
					${tCustomerTax.customerId}
				</td>
				<td>
					${tCustomerTax.orderNumber}
				</td>
				<td>
					${tCustomerTax.accountId}
				</td>
				<td>
					${tCustomerTax.accountName}
				</td>
				<td>
					${tCustomerTax.taxBase}
				</td>
				<td>
					${tCustomerTax.taxRate}
				</td>
				<td>
					${tCustomerTax.taxMoney}
				</td>
				<td>
					<fmt:formatDate value="${tCustomerTax.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tCustomerTax.remarks}
				</td>
				<shiro:hasPermission name="workterrace:tCustomerTax:edit"><td>
    				<a href="${ctx}/workterrace/tCustomerTax/form?id=${tCustomerTax.id}">修改</a>
					<a href="${ctx}/workterrace/tCustomerTax/delete?id=${tCustomerTax.id}" onclick="return confirmx('确认要删除该报税表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>