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
<title>城市区域-后台管理</title>
<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery.cxselect.min.js"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_3.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
</head>

<body>
<div class="pad_15_10">
	<div class="panel panel-default mar_b0 radius0 border_none">
		<div class="panel-heading bg_col_2 panel-head-pub">

			<span class="left mar-top3">
				<span class="glyphicon glyphicon_a14 ico_pub bg_pub mar-top2" aria-hidden="true"></span><span class="font_cc8 mar-rig10 font-14">城市区域</span>
			</span>
			<span class="left line_h24 mar-top2 font_cc11 mar-rig10">
			数据截止于 ${curdate }
			</span>
			<form:form id="dataForm" action="${pageContext.request.contextPath}/a/newcharge/tChargecompany/cityGeneralSituation" method="post" >
			<span class="left ipt_date" id="city_china_val">
				<select class="form-control ipt_wauto province select" data-first-title="选择省" name="province">
					<option>${province }</option>
				</select>
				<select class="form-control ipt_wauto city select" data-first-title="选择市" name="city">
					<option>${city }</option>
				</select>
				<select class="form-control ipt_wauto area select" data-value="选择区" data-first-title="选择地区" name="county">
					<option>${county }</option>
				</select>
			</span>
			<span>&nbsp;&nbsp;&nbsp;<input class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;</span>
			</form:form>
			<div class="clearfix"></div>
		</div>
		<table class="table table-center table_hover2 font_cc9 table_vmiddle">
			<thead>
				<tr class="block_c4 font_white">
					<th width="40"></th>
					<th class="weight_normal">位置区域</th>	
					<th class="weight_normal">代账公司总数</th>					
					<th class="weight_normal">企业总数</th>
					<th class="weight_normal">会计总人数</th>									
				</tr>
			</thead>
			<tbody>
			<c:forEach var="company" items="${list}"> 
  			  	<tr>
					<td></td>
					<td>${company.city}</td>	
					<td>${company.companyNumber }</td>					
					<td>${company.customer }</td>				
					<td>${company.companyUsers }</td>					
				</tr>
			</c:forEach> 
			</tbody>
		</table>
	</div>
	<div class="hr15"></div>	
</div>
<script>

$(function() {
	//城市
	$.cxSelect.defaults.url = '${ctxStatic}/cityData.min.json';
	$('#city_china_val').cxSelect({
	  selects: ['province', 'city', 'area'],
	  nodata: 'none'
	});
});
</script>
</body>
</html>
