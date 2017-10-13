<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<title>现金流量附加表-在线会计-芸豆会计</title>
<meta name="decorator" content="default" />
<meta name="keywords" content="关键字,关键字,关键字" />
<meta name="description" content="描述。……" />
<meta name="Author" content="author_bj designer_bj">
<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery-formatMoney/jQuery.formatMoney.js" type="text/javascript"></script>
<script src="${ctxStatic}/fixedheadertable/jquery.fixedheadertable.js"></script>
<script src="${ctxStatic}/js/rpt/cfadditionalJs.js"></script>
<script src="${ctxStatic}/js/table_height.js"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="${ctxStatic}/css/reset.css" rel="stylesheet" type="text/css">
<!-- <link href="css/accountCom.css" rel="stylesheet" type="text/css"> -->
<link href="${ctxStatic}/css/cashFlowvAndProfit.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/fixedheadertable/defaultTheme.css" rel="stylesheet" media="screen" />
<script type="text/javascript">
	//	项目路径
	var ctx = "${ctx}";
	var ctxStatic = "${ctxStatic}";
</script>
<style>
.table-account-wrapper tbody tr td {
	text-align: left;
	text-indent: 28px;
}

.table-profit tbody tr td:nth-child(4) {
	text-align: left;
	padding-right: 0;
	padding-left: 8px;
}

.table-account-wrapper tbody tr td {
	text-align: center;
	text-indent: 0;
}

input[type="text"] {
	margin-bottom: 0;
	vertical-align: baseline;
}

.account-wrapper .tb {
	margin-top: 16px;
}

input[readonly] {
	background: #fff;
}

.input-xlarge {
	width: 200px;
}

.table-profit tbody tr td:nth-child(7) {
	text-align: center;
	padding-right: 0;
}
</style>

</head>
<body>
	<input type="hidden" id="periodBeginHidden" value="${accountPeriod}" />
	<input type="hidden" id="periodEndHidden" value="${periodEnd}" />
	<input type="hidden" id="periodTypeHidden" value="${periodtype}" />
	<input type="hidden" id="tableInfoCountHidden" value="" />
	<div class="account-wrapper profit">
		<h2 class="h2-tit" id="cfadditionalTitle"></h2>
		<div class="th clearfix">
			<div class="th clearfix">
				<div class="th-right clearfix">
					<a href="javascript:void(0)" onclick="savaECashFlow()"><span>保存</span></a> 
					<a href="javascript:void(0)" onclick="emptyRecal()"><span>清空并重算</span></a> 
					<a href="javascript:void(0)" onclick="nextECashFlow()"><span>下一步</span></a>
					<a href="javascript:void(0)" onclick="javascript:history.go(-1)"><span>返回</span></a>
				</div>
			</div>
		</div>
		<div class="tb">
			<table id="myTable02" class="table-profit table-account-wrapper" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th width="40%">项目</th>
						<th width="10%">行次</th>
						<th width="25%">本月金额</th>
						<th width="25%">本年累计金额</th>
					</tr>
				</thead>
				<tbody id="tableInfo">

				</tbody>
			</table>
		</div>
	</div>
	<%-- <div id="dialog" title="编辑公式">
		<div class='hr10'></div>
		<form:form id="inputFormFormual" modelAttribute="tAcctreportitem"
			action="" method="post"
			class="form-horizontal">
			<sys:message content="${message}" />
			 <form:hidden path="id" />
			<form:hidden path="reportitem" />
			<form:hidden path="accountPeriod" value="${accountPeriod }" />
			<form:hidden path="periodtype" value="${periodtype }"/>

			<div class="control-group">
				<label class="control-label">科目编号：</label>
				<div class="controls">
					<form:select path="accountid" htmlEscape="false" maxlength="6"
						class="input-xlarge ">
						<form:option value=""></form:option>
						<c:forEach items="${ listAcc}" var="acc">
							<form:option value="${acc.accuntId }">
							${acc.accuntId }${acc.accountName }
						</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">取数规则：</label>
				<div class="controls">
					<form:select path="ftype" htmlEscape="false" maxlength="4"
						class="input-xlarge ">
						<form:option value="DF">借方发生额</form:option>
						<form:option value="CF">贷方发生额</form:option>
						<form:option value="SL">损益发生额</form:option>
					</form:select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">加减运算：</label>
				<div class="controls">
					<form:select path="op" htmlEscape="false" maxlength="4"
						class="input-xlarge ">
						<form:option value="+">+</form:option>
						<form:option value="-">-</form:option>
					</form:select>
				</div>
			</div>
			<div class="form-actions">
				<input id="btnSubmit" class="btn btn-primary" type="button"
					value="保 存" onclick="saveCfadditional()"/> <input id="btnCancel" class="btn" type="button"
					value="返 回" onclick="history.go(-1)" />
			</div>
		</form:form>
		<div class='step_panel'>
			<div class='step_con step_a1'>
				<table class="table table-bordered table_vmiddle table_pad_no"
					style='font-size: 12px;'>
					<thead>
						<tr>
							<th><span class='pad_l_r5'>科目</span></th>
							<th><span class='pad_l_r5'>运算符号</span></th>
							<th><span class='pad_l_r5'>取数规则</span></th>
							<th><span class='pad_l_r5'>期末数</span></th>
							<th><span class='pad_l_r5'>年初数</span></th>
							<th><span class='pad_l_r5'>操作</span></th>
						</tr>
					</thead>
					<tbody id="listEFormula">
					</tbody>
				</table>
			</div>
		</div>
	</div> --%>
	<!-- Templates -->
	<p style="display: none">
		<textarea id="template">
			<!--

{#foreach $T as eformula}
<tr>
	<tr>
		<td>
			{$T.eformula.accuntid }{$T.eformula.accountName }
		</td>
		<td>
			{$T.eformula.op }	
		</td>
		<td>
			{$T.eformula.ftype }
		</td>
		<td>
			{$T.eformula.periodEnd }
		</td>
		<td>
			{$T.eformula.yearStart }
		</td>
		<td>		
			<a id="{$T.eformula.acctReportItemId }" href="javascript:void(0)" onclick="deleteCfadditional({$T.eformula.acctReportItemId })">删除</a>
		</td>
</tr>
{#/for}
-->
</textarea>
	</p>
</body>
</html>