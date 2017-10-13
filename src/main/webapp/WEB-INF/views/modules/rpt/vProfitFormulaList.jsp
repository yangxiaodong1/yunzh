<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>利润公式表-在线会计-芸豆会计</title>
	<meta name="decorator" content="default"/>
	
</head>
<body>
	<form:form id="inputForm" modelAttribute="tAcctreportitem" action="${ctx}/rpt/formula/saveProfit" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<form:hidden path="id"/>
		<form:hidden path="reportitem"/>
		<form:hidden path="accountPeriod"/>
		<div class="control-group">
			<label class="control-label">科目编号：</label>
			<div class="controls">
				<form:select path="accountid"  htmlEscape="false"  maxlength="6" class="input-xlarge ">
					<form:option value=""></form:option>
					<c:forEach items="${ listAcc}" var="acc">
						<form:option value="${acc.accuntId }">
							${acc.accuntId }${acc.accountName }
						</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>科目</th>
				<th>运算符号</th>
				<th>本年累计金额</th>
				<th>本月金额</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${listEFormula}" var="eformula">
			<tr>
				<td>
					${eformula.accuntid }${eformula.accountName }
				</td>
				<td>
					${eformula.op }	
				</td>
				<td>
					${eformula.yearStart }
				</td>
				<td>
					${eformula.periodEnd }
				</td>
				<td>
					<a href="${ctx}/rpt/formula/deleteProfit?id=${eformula.acctReportItemId }&reportitemId=${ eformula.reportitemid}&accountPeriod=${eformula.accountperiod}" onclick="return confirmx('确认要删除该报表公式科目吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>