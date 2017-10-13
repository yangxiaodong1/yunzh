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
<title>操作日志-后台管理</title>
<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_3.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
<style>
.pagination  a {
    outline: 0;
    color: #2fa4e7;
    text-decoration: none;
}
.pagination {
    margin: 8px 0;
}
.pagination ul{
    display: inline-block;
    margin-bottom: 0;
    margin-left: 0;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    -webkit-box-shadow: 0 1px 2px rgba(0,0,0,0.05);
    -moz-box-shadow: 0 1px 2px rgba(0,0,0,0.05);
    box-shadow: 0 1px 2px rgba(0,0,0,0.05);
}
.pagination ul>li {
    display: inline;
}
li {
    line-height: 20px;
}
.pagination ul>li:first-child>a, .pagination ul>li:first-child>span {
    border-left-width: 1px;
    -webkit-border-bottom-left-radius: 4px;
    border-bottom-left-radius: 4px;
    -webkit-border-top-left-radius: 4px;
    border-top-left-radius: 4px;
    -moz-border-radius-bottomleft: 4px;
    -moz-border-radius-topleft: 4px;
}
.pagination ul>li>a, .pagination ul>li>span {
    float: left;
    padding: 4px 12px;
    line-height: 20px;
    text-decoration: none;
    background-color: #fff;
    border: 1px solid #ddd;
    border-left-width: 0;
}
.pagination ul>.disabled>span, .pagination ul>.disabled>a, .pagination ul>.disabled>a:hover, .pagination ul>.disabled>a:focus {
    color: #999;
    cursor: default;
    background-color: transparent;
}

.pagination ul>.active>a, .pagination ul>.active>span {
    color: #999;
    cursor: default;
}
.pagination ul>li>a:hover, .pagination ul>li>a:focus, .pagination ul>.active>a, .pagination ul>.active>span {
    background-color: #f5f5f5;
}
.pagination ul>li:last-child>a, .pagination ul>li:last-child>span {
    -webkit-border-top-right-radius: 4px;
    border-top-right-radius: 4px;
    -webkit-border-bottom-right-radius: 4px;
    border-bottom-right-radius: 4px;
    -moz-border-radius-topright: 4px;
    -moz-border-radius-bottomright: 4px;
}
.pagination .controls a {
    border: 0;
}
.pagination .controls input {
    border: 0;
    color: #999;
    width: 30px;
    padding: 0;
    margin: -3px 0 0 0;
    text-align: center;
    background-color: #fff;
    border: 1px solid #ccc;
    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
    -moz-box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
    box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
    -webkit-transition: border linear .2s,box-shadow linear .2s;
    -moz-transition: border linear .2s,box-shadow linear .2s;
    -o-transition: border linear .2s,box-shadow linear .2s;
    transition: border linear .2s,box-shadow linear .2s;
}
</style>
<script type="text/javascript">
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
    	return false;
    }
</script>
</head>

<body>
<div class="pad_15_10">
	<div class="panel panel-default mar_b0 radius0 border_none">
		<div class="panel-heading bg_col_2 panel-head-pub">
		<form:form id="searchForm" action="${ctx}/sys/log/handleLog" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<div class="">
				<span class="left line_h24 mar-rig5 mar-lft10">操作菜单</span>
				<input id="title" name="title" type="text" maxlength="50" class="form-control ipt_w5 w104 left mar-rig30 " value="${log.title}">
				
				<span class="left line_h24 mar-rig5 mar-lft10">用户ID</span>
				<input id="createBy.id" name="createBy.id" type="text" class="form-control ipt_w5 w150 left mar-rig30" value="${log.createBy.id}">
				
				<span class="left line_h24 mar-rig5 mar-lft10">URL</span>
				<input id="requestUri" name="requestUri" type="text" class="form-control ipt_w5 w200 left " value="${log.requestUri}">
			</div>
			<div class="hr10"></div>			
			<div class="">
				<span class="left line_h24 mar-rig5 mar-lft10">日期范围</span>
				<span class="left ipt_date">					
					<input type="text" class="form-control ipt_w5 w80 left text-center" id="beginDate" name="beginDate" value="<fmt:formatDate value="${log.beginDate}" pattern="yyyy-MM-dd"/>">
					<span class="mar-lr5 line_h24 font_cc8">——</span>
				</span>
				<span class="left ipt_date mar-rig30">
					<input type="text" class="form-control ipt_w5 w80 left text-center" id="endDate" name="endDate" value="<fmt:formatDate value="${log.endDate}" pattern="yyyy-MM-dd"/>">
				</span>
				<span class="line_h24 pad_t_3">
					<input type="checkbox"${log.exception eq '1'?' checked':''} value="1" id="exception" name="exception"/>只查异常信息
				</span>
				<span class="line_h24 pad_t_3">
				&nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
				</span>
				<div class="clearfix"></div>
			</div>		
		</form:form>	
		</div>
		<table id="contentTable" class="table table-center table_hover2 font_cc9 table_vmiddle">
			<thead>
				<tr class="block_c4 font_white">					
					<th class="weight_normal">操作菜单</th>					
					<th class="weight_normal" width="100">操作用户</th>
					<th class="weight_normal" width="100">所在公司</th>
					<th class="weight_normal" width="100">所在部门</th>	
					<th class="weight_normal">URL</th>		
					<th class="weight_normal" width="80">提交方式</th>
					<th class="weight_normal">操作者IP </th>					
					<th class="weight_normal" width="150">操作时间</th>
				</tr>
			</thead>
			<tbody>
			<%request.setAttribute("strEnter", "\n");request.setAttribute("strTab", "\t");%>
			<c:forEach items="${page.list}" var="log">
				<tr>
					<td>${log.title}</td>
					<td>${log.createBy.name}</td>
					<td>${log.createBy.company.name}</td>
					<td>${log.createBy.office.name}</td>
					<td><strong>${log.requestUri}</strong></td>
					<td>${log.method}</td>
					<td>${log.remoteAddr}</td>
					<td><fmt:formatDate value="${log.createDate}" type="both"/></td>
				</tr>
				<c:if test="${not empty log.exception}"><tr>
					<td colspan="8" style="word-wrap:break-word;word-break:break-all;">
	<%-- 					用户代理: ${log.userAgent}<br/> --%>
	<%-- 					提交参数: ${fns:escapeHtml(log.params)} <br/> --%>
						异常信息: <br/>
						${fn:replace(fn:replace(fns:escapeHtml(log.exception), strEnter, '<br/>'), strTab, '&nbsp; &nbsp; ')}</td>
				</tr></c:if>
			</c:forEach>
			</tbody>
		</table>
		<div class="pagination">${page}</div>
	</div>
	<div class="hr15"></div>	
</div>
<script>
$(function() {
	$( "#beginDate" ).datepicker({
		showOn: "button",
		onSelect:function(dateText,inst){
	       $("#endDate").datepicker("option","minDate",dateText);
	    }
	});
	$( "#endDate" ).datepicker({
		showOn: "button",
		onSelect:function(dateText,inst){
	       $("#beginDate").datepicker("option","maxDate",dateText);
	    }
	});
});
</script>
</body>
</html>
