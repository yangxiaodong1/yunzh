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
<title>停止服务列表-客户管理</title>
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
			<div class="left pad_t_3">
				<span class="glyphicon glyphicon_a7 ico_pub bg_pub" aria-hidden="true"></span><span class="font_cc8 ">停止服务列表</span>
			</div>
			<div class="right"><span class="btn btn-default btn_w_a2 btn_bg_6 left mar-rig10"><a href="${pageContext.request.contextPath}/a/newcharge/tChargecompany/addym" style="color:white;">添加客户</a></span>
				<div class="left mar-rig10">
					<form action="${pageContext.request.contextPath}/a/newcharge/tChargecompany/searchByStopService" method="post">
					<input type="text" class="ipt_s03 left" name="search" placeholder="请输入你所要选择的城市"><input type="submit" class="ipt_04" value="搜索">
					</form>
				</div>
				<a href="${pageContext.request.contextPath}/a/newcharge/tChargecompany/menue" class="font_cc4 underline line_h26">显示客户列表</a>
			</div>
			<div class="clearfix"></div>
		</div>
		<table class="table table-center table_hover2 font_cc9">
			<thead>
				<tr class="block_c4 font_white">
					<th class="weight_normal" width="100">所在城市</th>
					<th class="weight_normal">公司名称</th>
					<th class="weight_normal" width="100">联系人</th>
					<th class="weight_normal" width="130">对接人</th>
					<th class="weight_normal" width="100">子账号数量</th>
					<th class="weight_normal" width="100">企业数量</th>
					<th class="weight_normal" width="100">状态</th>
					<th class="weight_normal" width="230">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${tcList}" var="cl">
				<tr>
					<td><c:choose >
   							 <c:when test="${cl.province=='北京市'||cl.province=='天津市'||cl.province=='重庆市'||cl.province=='上海市'||cl.province=='台湾'||cl.province=='香港特别行政区'||cl.province=='澳门特别行政区'}">
							      ${cl.province}
							    </c:when>
								 <c:otherwise>
								 ${cl.municipality}
							    </c:otherwise>
							</c:choose></td>
					<td class="text-left">${cl.chargecomname.chargecomname}</td>
					<td>${cl.contectperson.contectperson}</td>
					<td>${cl.abutment.abutment}</td>
					<td>${cl.sonCount}</td>
					<td>${cl.countCompany}</td>
					<c:if test="${cl.usestatus.usestatus=='0'}">
					<td>试用</td>
					</c:if>
					<c:if test="${cl.usestatus.usestatus=='1'}">
					<td>正式</td>
					</c:if>
					<c:if test="${cl.usestatus.usestatus==null||cl.usestatus.usestatus==''}">
					<td>没有填写状态</td>
					</c:if>
					<td><a href="#" class="mar-lr5 startServe" id="${cl.id}">恢复服务</a></td>
				</tr>
				</c:forEach>
				
				
				
			</tbody>
		</table>
	</div>
	<div class="hr15"></div>	
</div>
<script type="text/javascript">
$(function(){
	$("#h_rig").height(function(){
		return $("#h_lft").height();
	});
	$(".startServe").on("click",function(){
		var id=$(this).attr("id");
		//alert(id);
		urls="${pageContext.request.contextPath}/a/newcharge/tChargecompany/serviceStatusT?";
		
		//urls='${ctx}/amortize/tAmortize/amortize?';
	
		window.location.href=urls+'id='+id+'&serviceStatus=1'+'&delog=1';	
	});
})
</script>
</body>
</html>
