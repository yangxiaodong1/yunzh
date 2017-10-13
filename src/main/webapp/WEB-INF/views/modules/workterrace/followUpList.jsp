<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>跟进表管理</title>
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
<div class="message-pop"><span>保存成功！</span></div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/workterrace/followUp/">跟进表列表</a></li>
		<li><a href="${ctx}/workterrace/followUp/form">跟进表添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="followUp" action="${ctx}/workterrace/followUp/" method="post" class="breadcrumb form-search">
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
				<th>id</th>
				<th>客户id</th>
				<th>跟进内容</th>
				<th>跟进时间</th>
				<th>联系人</th>
				<th>联系号码</th>
				<th>备注</th>
				<th>跟进人</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="followUp">
			<tr>
				<td><a href="${ctx}/workterrace/followUp/form?id=${followUp.id}">
					${followUp.id}
				</a></td>
				<td>
					${followUp.customerId}
				</td>
				<td>
				${followUp.upContent}
				</td>
				<td>
					<fmt:formatDate value="${followUp.upTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${followUp.linkman}
				</td>
				<td>
					${followUp.phone}
				</td>
				<td>
					${followUp.remarks1}
				</td>
				<td>
					${followUp.upMan}
				</td>
				<td>
    				<a href="${ctx}/workterrace/followUp/form?id=${followUp.id}">修改</a>
					<a href="${ctx}/workterrace/followUp/delete?id=${followUp.id}" onclick="return confirmx('确认要删除该跟进表吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>