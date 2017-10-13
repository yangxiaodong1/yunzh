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
<title>常见问题-后台管理</title>
<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery.validate.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_3.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
</head>

<body>
<div class="pad_15_10">
	<div class="panel panel-default mar_b0 radius0 border_none">
		<div class="panel-heading bg_col_2 panel-head-pub">
			<div class=" pad_t_3">
				<span class="glyphicon glyphicon_a10 ico_pub bg_pub mar-top2" aria-hidden="true"></span><span class="font_cc8 mar-rig10 font-14">常见问题</span>
				<span class="btn btn-default btn_w_a2 btn_bg_6 mar-rig10 addd" >添加</span>
			</div>
			<div class="clearfix"></div>
		</div>
		<sys:message content="${message}"/>
		<table class="table table-center table_hover2 font_cc9">
			<thead>
				<tr class="block_c4 font_white">
					<th class="weight_normal">标题</th>					
					<th class="weight_normal" width="120">创建时间</th>
					<th class="weight_normal" width="120">更新时间</th>
					<th class="weight_normal" width="120">修改人</th>
					<th class="weight_normal" width="150">操作</th>
				</tr>
			</thead>
			<tbody>
				
					<c:forEach items="${tcList}" var="tl">
					<tr>
					<td class="text-left">${tl.title}</td>
					<td>${tl.createDateString}</td>
					<td>${tl.updateDateString}</td>
					<td>${tl.updateByStrinig}</td>
					<td class="font_cc4"><a href="${pageContext.request.contextPath}/a/comquestion/tcomquest/editcomQuest?id=${tl.id}" class="mar-lr5">编辑</a><a href="#" class="mar-lr5 dialog_del" id="${tl.id}">删除</a></td>
				    </tr>
					</c:forEach>		
				
			</tbody>
		</table>
	</div>
	<div class="hr15"></div>	
</div>
<!--删除-->
<div id="dialog_del" style="display:none;" title="温馨提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center">您确认要删除此内容吗？</div>
		<div class="hr40"></div>
		<form action="" method="post">
		<div class="text-center">
			<button type="submit" class="btn btn-default btn_w_a btn_bg_2 mar-rig10 ">确认</button>
		</div>
		</form>
		<div class="hr40"></div>
	</div>
</div>
<script type="text/javascript">
$(function(){
	$("#h_rig").height(function(){
		return $("#h_lft").height();
	});
	
	$(".addd").on("click",function(){
		urls='${ctx}/comquestion/tcomquest/oftenMeetaddym';
		window.location.href=urls;
	});
	
	$( "#dialog_del" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});	
	$( ".dialog_del" ).click(function(){
		var dialogDel=$(this);
		var id=dialogDel.attr("id");
		//alert(id);
		var url='${pageContext.request.contextPath}/a/comquestion/tcomquest/deletecomquest?id='+id;
		$( "#dialog_del form" ).attr("action",url);
		$( "#dialog_del" ).dialog( "open" );
		event.preventDefault();
     });
	
})
</script>
</body>
</html>
