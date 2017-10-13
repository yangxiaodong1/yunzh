<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
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
		<li class="active"><a href="${ctx}/customer/tCustomer/">用户列表</a></li>
		</ul>
	<form:form id="searchForm" modelAttribute="tCustomer" action="${ctx}/customer/tCustomer/" method="post" class="breadcrumb form-search">
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
				<th>公司名称</th>
				<th>公司简称</th>
				<th>行业</th>
				<th>法人代表</th>
				<th>身份证号</th>
				<th>营业执照号</th>
				<th>组织机构代码</th>
				<th>成立日期</th>
				<th>更新时间</th>
				<shiro:hasPermission name="customer:tCustomer:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tCustomer">
			<tr>
				<td>
				<!--  
				<a href="${ctx}/customer/tCustomer/form?id=${tCustomer.id}">
					${tCustomer.customerName}
				</a>
				-->
				<a href="${ctx}/voucherexp/tVoucherExp/periodShow?fdbid=${tCustomer.id}">${tCustomer.customerName}</a>
				</td>
				<td>
					${tCustomer.abbrName}
				</td>
				<td>
					${tCustomer.industry}
				</td>
				<td>
					${tCustomer.legalRepresentative}
				</td>
				<td>
					${tCustomer.idNumber}
				</td>
				<td>
					${tCustomer.businessLicenseNumber}
				</td>
				<td>
					${tCustomer.organizationCode}
				</td>
				<td>
					<fmt:formatDate value="${tCustomer.establishmentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${tCustomer.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="customer:tCustomer:edit"><td>
    				<a href="${ctx}/customer/tCustomer/form?id=${tCustomer.id}">修改</a>
					<a href="${ctx}/customer/tCustomer/delete?id=${tCustomer.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>