<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统消息-后台管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery.validate.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_3.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
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
	<style>
	.table thead th.block_c4{background:#3d8ae2;}
	</style>
</head>
<body>
	<!-- 
	<form:form id="searchForm" modelAttribute="tsysnews" action="${ctx}/sysnews/tsysnews/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form> -->
	<div class="pad_15_10">
	<div class="panel panel-default mar_b0 radius0 border_none">
		<div class="panel-heading bg_col_2 panel-head-pub">
			<div class=" pad_t_3">
				<span class="glyphicon glyphicon_a11 ico_pub bg_pub mar-top2" aria-hidden="true"></span><span class="font_cc8 mar-rig10 font-14">系统消息</span>
				<a href="${pageContext.request.contextPath}/a/sysnews/tsysnews/addsysNews" class="btn btn-default btn_w_a2 btn_bg_6 mar-rig10">添加</a>
			</div>
			<div class="clearfix"></div>
		</div>
		<table class="table table-center table_hover2 font_cc9">
			<thead>
				<tr class="">
					<th class="weight_normal block_c4 font_white">消息标题</th>					
					<th class="weight_normal block_c4 font_white" width="180">发送时间</th>
					<th class="weight_normal block_c4 font_white" width="120">操作人</th>
					<th class="weight_normal block_c4 font_white" width="120">状态</th>
					<th class="weight_normal block_c4 font_white" width="150">操作</th>
				</tr>
			</thead>
			<tbody>
				
				<c:forEach items="${page.list}" var="tl">
				<tr>
					<td class="text-left">${tl.title}</td>
					<td><fmt:formatDate value="${tl.sendtime}" type="date"/></td>					
					<td>${tl.updateBy.name}</td>
					<td>
					<c:if test="${tl.sendstatus!='1'}">
					<c:if test="${tl.settimestatus==null||tl.settimestatus==''}">已经保存</c:if>
					</c:if>
					<c:if test="${tl.sendstatus!='1'}">
					<c:if test="${tl.settimestatus=='0'}">定时发送</c:if>
					</c:if>
					<c:if test="${tl.sendstatus=='1'}">已经发送</c:if>
					</td>
					<td class="font_cc4">
					<c:if test="${tl.sendstatus==''||tl.sendstatus==null}">
					<a href="${pageContext.request.contextPath}/a/sysnews/tsysnews/forms?id=${tl.id}" class="mar-lr5">编辑</a><a href="${pageContext.request.contextPath}/a/sysnews/tsysnews/sendstatus?id=${tl.id}" class="mar-lr5 sendd">发送</a>
					</c:if>
					<c:if test="${tl.sendstatus=='1'}"><a href="${pageContext.request.contextPath}/a/sysnews/tsysnews/check?id=${tl.id}" class="mar-lr5">查看</a></c:if>
					</td>
				
				</tr>
				</c:forEach>	
				
			</tbody>
		</table>
	</div>
	<div class="hr15"></div>	
</div>
	<div class="pagination">${page}</div>
</body>
</html>