<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>加盟申请管理</title>
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
		<li class="active"><a href="${ctx}/newcharge/tjoinappl/">加盟申请列表</a></li>
		<shiro:hasPermission name="newcharge:tjoinappl:edit"><li><a href="${ctx}/newcharge/tjoinappl/form">加盟申请添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tjoinappl" action="${ctx}/newcharge/tjoinappl/" method="post" class="breadcrumb form-search">
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
				<th>申请公司名字</th>
				<th>所在城市</th>
				<th>对接人</th>
				<th>联系电话</th>
				<th>反馈信息</th>
				<th>update_date</th>
				<th>remarks</th>
				<shiro:hasPermission name="newcharge:tjoinappl:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tjoinappl">
			<tr>
				<td><a href="${ctx}/newcharge/tjoinappl/form?id=${tjoinappl.id}">
					${tjoinappl.compname}
				</a></td>
				<td>
					${tjoinappl.city}
				</td>
				<td>
					${tjoinappl.contectperson}
				</td>
				<td>
					${tjoinappl.contectphone}
				</td>
				<td>
					${tjoinappl.message}
				</td>
				<td>
					<fmt:formatDate value="${tjoinappl.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tjoinappl.remarks}
				</td>
				<shiro:hasPermission name="newcharge:tjoinappl:edit"><td>
    				<a href="${ctx}/newcharge/tjoinappl/form?id=${tjoinappl.id}">修改</a>
					<a href="${ctx}/newcharge/tjoinappl/delete?id=${tjoinappl.id}" onclick="return confirmx('确认要删除该加盟申请吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>