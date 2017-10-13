<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta charset="utf-8" />
<title>凭证摘要管理</title>
<meta name="keywords" content="关键字,关键字,关键字" />
<meta name="description" content="描述。……" />
<meta name="Author" content="author_bj designer_bj">
<link href="${ctxStatic}/css/reset.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/checkCredentials.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="checkVoucher">
		<h2 class="h2-tit">2015年11月凭证</h2>
		<div class="th clearfix">
			<div class="th-left">
				<form:form id="searchForm" modelAttribute="tVoucher" action="${ctx}/voucherexp/tVoucherExp/periodShow" method="post" class="breadcrumb form-search">
					<form:hidden path="fdbid"/>
					<form:select path="accountPeriod" htmlEscape="false" modelAttribute="periodList" id="Period" class="seltime"> 
				 		<form:options items="${periodList}" itemLabel="accountPeriod" itemValue="accountPeriod" htmlEscape="false"/> 
	             	</form:select>&nbsp;&nbsp;
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				</form:form>					
			</div>
			<div class="th-right clearfix">
				<span>结账</span>
				<span>打印全部</span>
			</div>
		</div>
		<div class="tb">
			<table class="tabhead" width="100%" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th width="25%">摘要</th>
						<th width="25%">科目</th>
						<th width="25%">借方金额</th>
						<th width="25%">贷方金额</th>
					</tr>
				</thead>
			</table>
			<c:forEach items="${tvoucherList}" var="tvoucher">
				<c:if test="${tvoucher.count>0}">
					<table id="contentTable" class="table" width="100%" cellpadding="0" cellspacing="0">
						<thead>
						<tr>
						<td class="list-head" colspan="4">
						<div class="clearfix">
							<span>日期:<fmt:formatDate value="${tvoucher.accountDate}" pattern="yyyy-MM-dd"/></span>
							<span>凭证字号：${tvoucher.voucherTitleName}_${tvoucher.voucherTitleNumber}</span>
							<span>记账人：${tvoucher.userName}s</span>
							<div class="hoverCont"> 
									<span class="s-icon"><i></i>影像</span>
									<span class="s-icon"><i></i>修改</span>
									<span class="s-icon"><i></i><a href="${ctx}/voucherexp/tVoucherExp/deletebytvid?tVId=${tvoucher.id}" onclick="return confirmx('确认要删除该凭证摘要吗？', this.href)">删除</a></span>
									<span class="s-icon"><i></i>插入</span>
									<span class="s-icon"><i></i>打印</span>
									<span class="s-pizhu s-icon"><i></i>批注
										<div class="pizhu"><em></em><em>0/100</em><textarea placeholder="请输入标注内容" name="" id="" cols="30" rows="10"></textarea></div>
									</span>
							</div>
						</div>
						</td>
						</tr>
						</thead>
						<tbody class="list-body">
							<c:forEach items="${tvoucherexpList}" var="tvoucherexp">
								<c:if test="${tvoucherexp.TVId==tvoucher.id}">
									<tr>
										<td  width="25%">${tvoucherexp.exp}</td>
										<td  width="25%">${tvoucherexp.accountName}</td>
										<td  width="25%">${tvoucherexp.debit}</td>
										<td  width="25%">${tvoucherexp.credit}</td>
									</tr>
								</c:if>
							</c:forEach>
							<tfoot>
								<tr>
									<td class="list-foot" colspan="4">合计：${tvoucher.money}</td>
								</tr>
							</tfoot>
						</tbody>
					</table>
				</c:if>
			</c:forEach>
		</div>
	</div>
<!--页面脚本区S-->
<!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->
<script>
</script>
<!--页面脚本区E-->
</body>
</html>