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
<title>数据概况-后台管理</title>
<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
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
				<span class="glyphicon glyphicon_a13 ico_pub bg_pub mar-top2" aria-hidden="true"></span><span class="font_cc8 mar-rig10 font-14">数据概况</span>
			</span>
			<span class="left">
			<button class="btn btn_b5 mar-rig10" onclick="location='${pageContext.request.contextPath}/a/newcharge/tChargecompany/dataGeneralSituation?type=7'">最近7天</button><button class="btn btn_b5 mar-rig10" onclick="location='${pageContext.request.contextPath}/a/newcharge/tChargecompany/dataGeneralSituation?type=30'">最近30天</button>
			</span>
			<form:form id="dataForm" action="${pageContext.request.contextPath}/a/newcharge/tChargecompany/dataGeneralSituation" method="post" >
			<span class="left ipt_date">
				<input type="text" class="form-control ipt_w5 w80 left text-center" value="${begin }" id="begin" name="begin"><span class="mar-lr5 line_h24 font_cc8">——</span>
			</span>
			<span class="left ipt_date">
				<input type="text" class="form-control ipt_w5 w80 left text-center" value="${end }" id="end" name="end">
			</span>
			<span>&nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;</span>
			</form:form>
			<div class="clearfix"></div>
		</div>
		<table class="table table-center table_hover2 font_cc9 table_vmiddle">
			<thead>
				<tr class="block_c4 font_white">
					<th width="40"></th>
					<th class="weight_normal" width="100"></th>	
					<th class="weight_normal">新增代账公司</th>					
					<th class="weight_normal">新增企业总数</th>
					<th class="weight_normal">新增会计人数</th>									
				</tr>
			</thead>
			<tbody>
			<c:forEach var="company" items="${ttList}"> 
  			  	<tr>
					<td></td>
					<td>
					<c:if test="${company.dateStringDate==''||company.dateStringDate==null}">0</c:if>
					${company.dateStringDate}</td>	
					<td>
					<c:if test="${company.company==''||company.company==null}">0</c:if>
					${company.company }</td>					
					<td>
					<c:if test="${company.cucount ==''||company.cucount ==null}">0</c:if>
					${company.cucount }</td>				
					<td>
					<c:if test="${company.acount==''||company.acount==null}">0</c:if>
					${company.acount }</td>					
				</tr>
			</c:forEach> 
				
				
			</tbody>
		</table>
	</div>
	<div class="hr15"></div>	
</div>
<script>
$(function() {
	$( "#begin" ).datepicker({
		showOn: "button",
	 	onSelect:function(dateText,inst){
	       $("#end").datepicker("option","minDate",dateText);
	    }
	});
	$( "#end" ).datepicker({
		showOn: "button",
		onSelect:function(dateText,inst){
	        $("#begin").datepicker("option","maxDate",dateText);
	    }
	});
});
</script>
</body>
</html>
