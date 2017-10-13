<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>录入凭证-在线会计-芸豆会计</title>
<link href="${pageContext.request.contextPath}/static/css/voucher.css" type="text/css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/js/voucher.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/handlebar/handlebars-1.0.0.beta.6.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/js/scrollpic.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

<script type="text/javascript">
 //	项目路径
 var ctx = "${ctx}";
</script>
</head>
<body>
<div class="c-right clearfix">
		<div class="edit">
			<div class="edit-show">
				新增票据
			</div>
			<c:if test="${!empty billinfos}">
			<div class="pics">
					<a class="a-left" href="javascript:;"></a>
					<div class="pic-pj">
						<ul class="ul-pj clearfix">
							<c:forEach var="billinfo" items="${billinfos}" varStatus="status">	
								<c:choose>
									<c:when test="${status.index < 3 }">
										<li class="billinfo-show">
											<img src="${billinfo.imageUrl}"  data-id="${billinfo.id}"  width="194" height="114" alt="">
											<em class="em-del" data-id="${billinfo.id}"></em>
										</li>
									</c:when>
									<c:otherwise>
										<li class="billinfo-hide-right">
											<img src="${billinfo.imageUrl}"  data-id="${billinfo.id}"  width="194" height="114" alt="">
											<em class="em-del" data-id="${billinfo.id}"></em>
										</li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</ul>
					</div>
					<a class="a-right" href="javascript:;"></a>
				</div>
			</c:if>
			<div class="th clearfix">
				<div class="th-left">
					<span class="scrollleft"></span>
					<span class="scrollright"></span>
				</div>
				<div class="th-right clearfix">
					<span class="a-save">保存</span>
					<span>模板导入</span>
					<span>保存模板</span>
				</div>
			</div>
			<div class="voucher-tab">
				<div class="tb voucher-current">
					<div class="voucher-top clearfix">
				      <div class="vou-left">
				      	记字第<input class="ui-spinbox" type="text" value="${title}">号
				      </div>
				      <div class="date-wrap">
			            	日期 <input type="text" class="ui-datepicker-input ui-input ui-datepicker-input Wdate" id="vch-date" value="" onFocus="WdatePicker({startDate:'1980-05-01'})" readonly="readonly">
					  </div>
				      <div class="vou-right">
				      	附单据<input type="text">张
				      </div>
				    </div>
					<div class="body">
						<table class="tab-voucher"  cellpadding="0" cellspacing="0">
							<thead>
								<tr>
									<th class="col-operate"></th>
									<th class="col-summary" colspan="2">摘要</th>
									<th class="col-subject" colspan="2">会计科目</th>
									<th class="col-money"> 
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
								</tr>
							</thead>
							<tbody class="tbody-voucher">
								<c:choose>
									<c:when test="${!empty billinfos}">
										<c:forEach var="billinfo" items="${billinfos}">	
											<tr class="entry-item billinfo-voucher-tr-${billinfo.id}" >
												<td class="col-operate">
													<div class="add-delete">
														<a class="a-add" href="javascript:;"></a>
														<a class="a-delete" href="javascript:;"></a>
													</div> 
												</td>
				          						<td class="col-summary" data-edit="summary">
				          							<div class="cell-val summary-val">${billinfo.abstractMsg}</div>
				          						</td>
				          						<td class="col-option" width="30">
				          							<div class="option">
				          								<a class="selSummary">摘要</a>
				          							</div>
				          						</td>
				          						<td class="col-subject" data-edit="subject">
				          							<div class="cell-val subject-val"></div>
				          						</td>
				          						<td class="col-option">
				          							<div class="option">
				          								<a class="selSub">科目</a>
				          							</div>
				          						</td>
				          						<td class="col-debite col-dc" data-edit="money">
				          							<div class="cell-val debit-val">${billinfo.amount}</div>
				          						</td>
				          						<td class="col-credit col-dc" data-edit="money">
				          							<div class="cell-val credit-val"></div>
				          						</td>
											</tr>			
											<tr class="entry-item billinfo-voucher-tr-${billinfo.id}">
												<td class="col-operate">
													<div class="add-delete">
														<a class="a-add" href="javascript:;"></a>
														<a class="a-delete" href="javascript:;"></a>
													</div> 
												</td>
				          						<td class="col-summary" data-edit="summary">
				          							<div class="cell-val summary-val">${billinfo.abstractMsg}</div>
				          						</td>
				          						<td class="col-option">
				          							<div class="option">
				          								<a class="selSummary">摘要</a>
				          							</div>
				          						</td>
				          						<td class="col-subject" data-edit="subject">
				          							<div class="cell-val subject-val"></div>
				          						</td>
				          						<td class="col-option">
				          							<div class="option">
				          								<a class="selSub">科目</a>
				
				          							</div>
				          						</td>
				          						<td class="col-debite col-dc" data-edit="money">
				          							<div class="cell-val debit-val"></div>
				          						</td>
				          						<td class="col-credit col-dc" data-edit="money">
				          							<div class="cell-val credit-val">${billinfo.amount}</div>
				          						</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="entry-item">
											<td class="col-operate">
												<div class="add-delete">
													<a class="a-add" href="javascript:;"></a>
													<a class="a-delete" href="javascript:;"></a>
												</div> 
											</td>
			          						<td class="col-summary" data-edit="summary">
			          							<div class="cell-val summary-val"></div>
			          						</td>
			          						<td class="col-option" width="30">
			          							<div class="option">
			          								<a class="selSummary">摘要</a>
			          							</div>
			          						</td>
			          						<td class="col-subject" data-edit="subject">
			          							<div class="cell-val subject-val"></div>
			          						</td>
			          						<td class="col-option">
			          							<div class="option">
			          								<a class="selSub">科目</a>
			          								
			          							</div>
			          						</td>
			          						<td class="col-debite col-dc" data-edit="money">
			          							<div class="cell-val debit-val"></div>
			          						</td>
			          						<td class="col-credit col-dc" data-edit="money">
			          							<div class="cell-val credit-val"></div>
			          						</td>
										</tr>			
										<tr class="entry-item">
											<td class="col-operate">
												<div class="add-delete">
													<a class="a-add" href="javascript:;"></a>
													<a class="a-delete" href="javascript:;"></a>
												</div> 
											</td>
			          						<td class="col-summary" data-edit="summary">
			          							<div class="cell-val summary-val"></div>
			          						</td>
			          						<td class="col-option">
			          							<div class="option">
			          								<a class="selSummary">摘要</a>
			          							</div>
			          						</td>
			          						<td class="col-subject" data-edit="subject">
			          							<div class="cell-val subject-val"></div>
			          						</td>
			          						<td class="col-option">
			          							<div class="option">
			          								<a class="selSub">科目</a>
			
			          							</div>
			          						</td>
			          						<td class="col-debite col-dc" data-edit="money">
			          							<div class="cell-val debit-val"></div>
			          						</td>
			          						<td class="col-credit col-dc" data-edit="money">
			          							<div class="cell-val credit-val"></div>
			          						</td>
										</tr>
										<tr class="entry-item">
											<td class="col-operate">
												<div class="add-delete">
													<a class="a-add" href="javascript:;"></a>
													<a class="a-delete" href="javascript:;"></a>
												</div> 
											</td>
			          						<td class="col-summary" data-edit="summary">
			          							<div class="cell-val summary-val"></div>
			          						</td>
			          						<td class="col-option">
			          							<div class="option">
			          								<a class="selSummary">摘要</a>
			          							</div>
			          						</td>
			          						<td class="col-subject" data-edit="subject">
			          							<div class="cell-val subject-val"></div>
			          						</td>
			          						<td class="col-option">
			          							<div class="option">
			          								<a class="selSub">科目</a>
			          							</div>
			          						</td>
			          						<td class="col-debite col-dc" data-edit="money">
			          							<div class="cell-val debit-val"></div>
			          						</td>
			          						<td class="col-credit col-dc" data-edit="money">
			          							<div class="cell-val credit-val"></div>
			          						</td>
										</tr>
									</c:otherwise>
								</c:choose>
								
							</tbody>
							<tfoot>
						      	<td class="col-operate"></td>
						        <td colspan="4" class="col-total">合计：<span id="capAmount"></span></td>
						        <td class="col-debite col-debite1">
						        	<div class="cell-val debit-total" id="debit-total"></div>
						        </td>
						        <td class="col-credit col-credit1">
						        	<div class="cell-val credit-total" id="credit-total"></div>
						        </td>
						    </tfoot>
						</table>
						<p class="make-person">制单人：<em class="make-person-name">XXX</em></p>
					</div>
				</div>
			</div>
		</div>
		<ul class="ul-select" style="display:none">
						
		</ul>
	</div>
</body>
<script id="accountTemplate" type="text/x-handlebars-template">	
{{#each this}}
	<li data-id="{{id}}" data-dc="{{dc}}">{{accuntId}}&nbsp;{{accountName}}</li>
{{/each}}
</script>	
<script id="templateBillInfoToVoucherImport" type="text/x-handlebars-template">	
{{#each this}}
				<div class="tb voucher-current">
					<div class="voucher-top clearfix">
				      <div class="vou-left">
				      	记字第<input class="ui-spinbox" type="text">号
				      </div>
				      <div class="date-wrap">
			            	日期 <input type="text" class="ui-datepicker-input ui-input ui-datepicker-input Wdate" id="vch-date" value="" onFocus="WdatePicker({startDate:'1980-05-01'})" readonly="readonly">
					  </div>
				      <div class="vou-right">
				      	附单据<input type="text">张
				      </div>
				    </div>
					<div class="body">
						<table class="tab-voucher"  cellpadding="0" cellspacing="0">
							<thead>
								<tr>
									<th class="col-operate"></th>
									<th class="col-summary" colspan="2">摘要</th>
									<th class="col-subject" colspan="2">会计科目</th>
									<th class="col-money"> 
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
								</tr>
							</thead>
							<tbody class="tbody-voucher">
										<tr class="entry-item">
											<td class="col-operate">
												<div class="add-delete">
													<a class="a-add" href="javascript:;"></a>
													<a class="a-delete" href="javascript:;"></a>
												</div> 
											</td>
			          						<td class="col-summary" data-edit="summary">
			          							<div class="cell-val summary-val"></div>
			          						</td>
			          						<td class="col-option" width="30">
			          							<div class="option">
			          								<a class="selSummary">摘要</a>
			          							</div>
			          						</td>
			          						<td class="col-subject" data-edit="subject">
			          							<div class="cell-val subject-val"></div>
			          						</td>
			          						<td class="col-option">
			          							<div class="option">
			          								<a class="selSub">科目</a>
			          								
			          							</div>
			          						</td>
			          						<td class="col-debite col-dc" data-edit="money">
			          							<div class="cell-val debit-val"></div>
			          						</td>
			          						<td class="col-credit col-dc" data-edit="money">
			          							<div class="cell-val credit-val"></div>
			          						</td>
										</tr>			
										<tr class="entry-item">
											<td class="col-operate">
												<div class="add-delete">
													<a class="a-add" href="javascript:;"></a>
													<a class="a-delete" href="javascript:;"></a>
												</div> 
											</td>
			          						<td class="col-summary" data-edit="summary">
			          							<div class="cell-val summary-val"></div>
			          						</td>
			          						<td class="col-option">
			          							<div class="option">
			          								<a class="selSummary">摘要</a>
			          							</div>
			          						</td>
			          						<td class="col-subject" data-edit="subject">
			          							<div class="cell-val subject-val"></div>
			          						</td>
			          						<td class="col-option">
			          							<div class="option">
			          								<a class="selSub">科目</a>
			
			          							</div>
			          						</td>
			          						<td class="col-debite col-dc" data-edit="money">
			          							<div class="cell-val debit-val"></div>
			          						</td>
			          						<td class="col-credit col-dc" data-edit="money">
			          							<div class="cell-val credit-val"></div>
			          						</td>
										</tr>
										<tr class="entry-item">
											<td class="col-operate">
												<div class="add-delete">
													<a class="a-add" href="javascript:;"></a>
													<a class="a-delete" href="javascript:;"></a>
												</div> 
											</td>
			          						<td class="col-summary" data-edit="summary">
			          							<div class="cell-val summary-val"></div>
			          						</td>
			          						<td class="col-option">
			          							<div class="option">
			          								<a class="selSummary">摘要</a>
			          							</div>
			          						</td>
			          						<td class="col-subject" data-edit="subject">
			          							<div class="cell-val subject-val"></div>
			          						</td>
			          						<td class="col-option">
			          							<div class="option">
			          								<a class="selSub">科目</a>
			          							</div>
			          						</td>
			          						<td class="col-debite col-dc" data-edit="money">
			          							<div class="cell-val debit-val"></div>
			          						</td>
			          						<td class="col-credit col-dc" data-edit="money">
			          							<div class="cell-val credit-val"></div>
			          						</td>
										</tr>
							</tbody>
							<tfoot>
						      	<td class="col-operate"></td>
						        <td colspan="4" class="col-total">合计：<span id="capAmount"></span></td>
						        <td class="col-debite col-debite1">
						        	<div class="cell-val debit-total" id="debit-total"></div>
						        </td>
						        <td class="col-credit col-credit1">
						        	<div class="cell-val credit-total" id="credit-total"></div>
						        </td>
						    </tfoot>
						</table>
						<p class="make-person">制单人：<em class="make-person-name">XXX</em></p>
					</div>
				  </div>
{{/each}}
</script>
</html>