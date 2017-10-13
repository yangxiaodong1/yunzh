<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>系统消息</title>
<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery.validate.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_3.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
</head>

<body>
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
				<tr class="block_c4 font_white">
					<th class="weight_normal">消息标题</th>					
					<th class="weight_normal" width="120">发送时间</th>
					<th class="weight_normal" width="120">操作人</th>
					<th class="weight_normal" width="120">状态</th>
					<th class="weight_normal" width="150">操作</th>
				</tr>
			</thead>
			<tbody>
				
				<c:forEach items="${tslist}" var="tl">
				<tr>
					<td class="text-left">${tl.title}</td>
					<td>${tl.sendtimeString}</td>					
					<td>${tl.updateByStrinig}</td>
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
<script type="text/javascript">
$(function(){
   $(".send").on("click",function(){
	   alert(1)
   })
})
</script>
</body>
</html>
