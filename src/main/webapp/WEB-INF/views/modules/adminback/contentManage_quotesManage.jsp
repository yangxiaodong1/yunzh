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
<title>语录管理-后台管理</title>
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
				<span class="glyphicon glyphicon_a12 ico_pub bg_pub mar-top2" aria-hidden="true"></span><span class="font_cc8 mar-rig10 font-14">语录管理</span>
				<a href="${pageContext.request.contextPath}/a/quotation/tquotation/quotation_add" class="btn btn-default btn_w_a2 btn_bg_6 mar-rig10">添加</a>
			</div>
			<div class="clearfix"></div>
		</div>
		<table class="table table-center table_hover2 font_cc9 table_vmiddle">
			<thead>
				<tr class="block_c4 font_white">
					<th width="60"></th>
					<th class="weight_normal">标题</th>					
					<th class="weight_normal" width="100">创建时间</th>
					<th class="weight_normal" width="100">更新时间</th>
					<th class="weight_normal" width="100">修改人</th>					
					<th class="weight_normal" width="120">操作</th>
				</tr>
			</thead>
			<tbody>
				
				<c:forEach items="${tqlist}" var="tl">
				<tr>
					<td>
					<c:if test="${tl.startstatus=='0'||tl.startstatus==''||tl.startstatus==null}">
					<span class="btn btn_b5" style="visibility:hidden;">启用</span>
					</c:if>
					<c:if test="${tl.startstatus=='1'}">
					<span class="btn btn_b5" >启用</span>
					</c:if>
					</td>
					<td class="text-left">${tl.content}</td>
					<td>${tl.createDateString}</td>	
					<td>${tl.updateDateString}</td>				
					<td>${tl.updateByStrinig}</td>					
					<td class="text-left font_cc4"><a href="${pageContext.request.contextPath}/a/quotation/tquotation/forms?id=${tl.id}" class="mar-lr5">编辑</a><a href="#" class="mar-lr5 deletee dialog_del" id="${tl.id}">删除</a>
					
					<a href="#" class="mar-lr5">
					
					<c:if test="${tl.startstatus=='0'||tl.startstatus==''||tl.startstatus==null}">
					<span class=" start" id="${tl.id}">启用</span>
					</c:if>
					
					</a>
					
					</td>
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
   $(".deleteee").on("click",function(){
	   var id=$(this).attr("id");
	   //alert(id);
	   urls='${pageContext.request.contextPath}/a/quotation/tquotation/quotation_delete?';
		window.location.href=urls+'id='+id;//用于向后台传递参数
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
		var url='${pageContext.request.contextPath}/a/quotation/tquotation/quotation_delete?id='+id;
		$( "#dialog_del form" ).attr("action",url);
		$( "#dialog_del" ).dialog( "open" );
		event.preventDefault();
    });
   
   
   
   
   
   
   
   
   
   $(".start").on("click",function(){
	   //alert(888);
	   var a=$(this).html();
	   var id=$(this).attr("id");
	   if(a=="启用"){
		   var status=1;
		   urls='${pageContext.request.contextPath}/a/quotation/tquotation/quotation_update?';
		   window.location.href=urls+'id='+id+'&status='+status;
	   }
	   
   });
   
   
   
})
</script>
</body>
</html>
