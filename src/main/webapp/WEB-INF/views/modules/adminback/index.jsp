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
<title>芸豆会计－管理后台</title>
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
		<div class="panel-heading bg_col_2 panel-head-pub"><span class="glyphicon glyphicon_a1 ico_pub bg_pub" aria-hidden="true"></span><span class="font_cc8">数据概况(${dNow})</span>
			<span class="right"><a href="${pageContext.request.contextPath}/a/newcharge/tChargecompany/dataGeneralSituation" class="font_cc8">查看详细数据</a></span>
			<div class="clearfix"></div>
		</div>
		<table class="table table-center table_hover">
			<thead>
				<tr class="block_c4 font_white">
					<th width="40"></th>
					<th></th>
					<th class="weight_normal">新增带记账公司数</th>
					<th class="weight_normal">新增企业数</th>
					<th class="weight_normal">新增会计人数</th>
				</tr>
			</thead>
			<tbody>
			
			<tr>
  			  	<th scope="row"></th>
					<td>今日</td>
					<td><c:if test="${tcon.companyNumber==''||tcon.companyNumber==null}">0</c:if>
					${tcon.companyNumber}
					</td>
					<td>
					<c:if test="${tcon.customer==''||tcon.customer==null}">0</c:if>
					${tcon.customer}</td>
					<td>
					<c:if test="${tcon.companyUsers==''||tcon.companyUsers==null}">0</c:if>
					${tcon.companyUsers}</td>
				</tr>
				<tr>
  			  	<th scope="row"></th>
					<td>昨日</td>
					<td>${tconold.companyNumber}
					<c:if test="${tconold.companyNumber==''||tconold.companyNumber==null}">0</c:if>
					</td>
					<td>
					<c:if test="${tconold.customer==''||tconold.customer==null}">0</c:if>
					${tconold.customer}</td>
					<td>
					<c:if test="${tconold.companyUsers==''||tconold.companyUsers==null}">0</c:if>
					${tconold.companyUsers}</td>
				</tr>
			
			<tr>
					<th scope="row"></th>
					<td>累计</td>
					<td>${accountChargeString}</td>
					<td>${conutcustomer}</td>
					<td>${conutaccount}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="hr15"></div>
	<div class="row_15">
		<div class="col-sm-6">
			<div class="panel-heading panel-head-pub"><span class="glyphicon glyphicon_a2 ico_pub bg_pub" aria-hidden="true"></span><span class="font_cc8">待记账公司城市分布</span> 
				<div class="clearfix"></div>
			</div>
			<div class="panel panel-default mar_b0 radius0  w_all">				
				<table class="table table_hover">
					<tbody>
						<c:forEach var="comp" items="${listcomp}">
						<tr>
							<th scope="row" width="20"></th>
							<td width="40">TOP${comp.top}</td>
							<td>${comp.city}</td>
							<td width="95">${comp.companyNumber }</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="panel-heading panel-head-pub"><span class="glyphicon glyphicon_a2 ico_pub bg_pub" aria-hidden="true"></span><span class="font_cc8">服务企业城市分布</span>
				<div class="clearfix"></div>
			</div>
			<div class="panel panel-default mar_b0 radius0 w_all">
				
				<table class="table table_hover">
					<tbody>
						<c:forEach var="cust" items="${listcustomer}">
						<tr>
							<th scope="row" width="20"></th>
							<td width="40">TOP${cust.top}</td>
							<td>${cust.city}</td>
							<td width="95">${cust.customer}</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="hr15"></div>
		<div class="col-sm-6">
			<div class="panel-heading panel-head-pub"><span class="glyphicon glyphicon_a2 ico_pub bg_pub" aria-hidden="true"></span><span class="font_cc8">财务人员城市分布</span> 
				<div class="clearfix"></div>
			</div>
			<div class="panel panel-default mar_b0 radius0 w_all" id="h_lft">
				
				<table class="table table_hover">
					<tbody>
						<c:forEach var="users" items="${listusers}">
						<tr>
							<th scope="row" width="20"></th>
							<td width="40">TOP${users.top}</td>
							<td>${users.city}</td>
							<td width="95">${users.companyUsers}</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="panel-heading panel-head-pub"><span class="glyphicon glyphicon_a3 ico_pub bg_pub" aria-hidden="true"></span>
				<div class="clearfix"></div>
			</div>
			<div class="panel panel-default mar_b0 radius0 w_all min_h150" id="h_rig">				
				<div class="hr20"></div>
				<div class="pad_10_15 border_i3 width_85 mar_auto">
					<span class="font-18">加盟申请</span>
					<span class="font-18 right">${joinCount}</span>
				</div>
				<div class="hr20"></div>
				<div class="pad_10_15 border_i3 width_85 mar_auto">
					<span class="font-18">意见反馈数量</span>
					<span class="font-18 right">${tfCount}</span>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function(){
	$("#h_rig").height(function(){
		return $("#h_lft").height();
	});
})
</script>
</body>
</html>
