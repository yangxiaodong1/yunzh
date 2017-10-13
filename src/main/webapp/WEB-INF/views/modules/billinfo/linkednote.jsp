<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>联查票据-在线会计-芸豆会计</title>
<link href="${pageContext.request.contextPath}/static/css/reset.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/sangetanceng.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/xuanzemoban.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/newSubjectTwo.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/autoAmortization.css" rel="stylesheet" type="text/css">
<style type="text/css">
	html {background: #fff;}
</style>
</head>
<body>
	<div class="c-right clearfix">
			<div class="edit">
				<input type="hidden" id="voucherID" value="${voucher.id}">
				<h2 class="h2-tit">联查票据</h2>
				<div class="th clearfix">
					<div class="th-left">
						<span class="scrollleft"></span>
						<span class="scrollright"></span>
					</div>
					<div class="th-right clearfix">
						<span  onclick="window.history.go(-1)">返回</span>
					</div>
				</div>
				<div class="voucher-tab">
					<div class="tb voucher-current add-voucher-account">
						<div class="voucher-top clearfix">
					      <div class="vou-left">
					      	记字第<input class="ui-spinbox" type="text"  value="${title}" readonly="readonly">号
					      </div>
					      <div class="date-wrap">
			            	日期 <input type="text" class="ui-datepicker-input ui-input ui-datepicker-input Wdate" id="vch-date" value="${date}" onFocus="WdatePicker({minDate:'%y-{%M-1}-01',maxDate:'%y-{%M-1}-%ld'})" readonly="readonly">
					 	  </div>
					      <div class="vou-right">
					      	附单据<input type="text"  value="${billinfosize}" readonly="readonly">张
					      </div>
					    </div>
						<div class="body">
							<table class="tab-voucher"  cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<th class="col-operate"></th>
										<th class="col-summary">摘要</th>
										<th class="col-subject">会计科目</th>
										<th class="col-money col-debite"> 
											<strong class="tit">借方金额</strong>
								            <div class="money-unit"> 
								            	<span>亿</span> 
								            	<span>千</span> 
								            	<span>百</span> 
								            	<span>十</span> 
								            	<span>万</span> 
								            	<span>千</span> 
								            	<span>百</span> 
								            	<span>十</span> 
								            	<span>元</span> 
								            	<span>角</span> 
								            	<span class="last">分</span> 
								            </div>
		          						</th>
										<th class="col-money col-credit">
											<strong class="tit">贷方金额</strong>
								            <div class="money-unit"> 
								            	<span>亿</span> 
								            	<span>千</span> 
								            	<span>百</span> 
								            	<span>十</span> 
								            	<span>万</span> 
								            	<span>千</span> 
								            	<span>百</span> 
								            	<span>十</span> 
								            	<span>元</span> 
								            	<span>角</span> 
								            	<span class="last">分</span> 
								            </div>
										</th>
										<th class="col-delete"></th>
									</tr>
								</thead>
								<tbody class="tbody-voucher">
									<c:forEach var="billinfo" items="${voucherExp}">	
										 <tr class="entry-item billinfo-voucher-tr-">
											<td class="col-operate">
												<div class="add-delete">
													<a class="a-add" href="javascript:;"></a>
												</div> 
											</td>
			          						<td class="col-summary" data-edit="summary">
			          							<div class="cell-val summary-val">${billinfo.exp}</div>
			          						</td>
			          						<td class="col-subject" data-edit="subject" data-amount="${billinfo.debit} ${billinfo.credit}">
			          							<div class="cell-val subject-val">
			          								<p data-amountid="${billinfo.accountId}" data-amountdc="${billinfo.dc}">${billinfo.accountName}</p>
			          								<p>
			          									<c:if test="${!empty billinfo.balance}">
			          										余额：<%-- <c:if test="${billinfo.dc == 1}">-</c:if> --%>
			          										${billinfo.balance.endbalance}
			          									</c:if>
			          								</p>
			          							</div>
			          						</td>
			          						<td class="col-debite col-dc" data-edit="money">
			          							<c:choose>
			          								<c:when test="${billinfo.debit <= 0}"><div class="cell-val debit-val no-edit"></div></c:when>
			          								<c:otherwise><div class="cell-val debit-val">${fn:replace(billinfo.showdebit, '.', '')}</div></c:otherwise>
			          							</c:choose>
			          						</td>
			          						<td class="col-credit col-dc" data-edit="money">
			          							<c:choose>
			          								<c:when test="${billinfo.credit <= 0}"><div class="cell-val credit-val no-edit"></div></c:when>
			          								<c:otherwise><div class="cell-val credit-val">${fn:replace(billinfo.showcredit, '.', '')}</div></c:otherwise>
			          							</c:choose>
			          						</td>
			          						<td class="col-delete">
												<div class="add-delete">
													<a class="a-delete" href="javascript:;"></a>
												</div> 
											</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
							      	<td class="col-operate"></td>
							        <td colspan="2" class="col-total">合计：<span id="capAmount"></span></td>
							        <td class="col-debite col-debite1">
							        	<div class="cell-val debit-total" id="debit-total"></div>
							        </td>
							        <td class="col-credit col-credit1">
							        	<div class="cell-val credit-total" id="credit-total"></div>
							        </td>
							        <td class="col-delete"></td>
							    </tfoot>
							</table>
							<p class="make-person">制单人：<em class="make-person-name">${user}</em></p>
						</div>
					</div>
				</div>
			</div>
		</div>
</body>
</html>