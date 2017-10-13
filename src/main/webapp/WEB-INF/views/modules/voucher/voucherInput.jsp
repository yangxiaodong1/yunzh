<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>凭证录入-在线会计-芸豆会计</title>
<link href="${pageContext.request.contextPath}/static/css/billInfo.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/handlebar/handlebars-1.0.0.beta.6.js" type="text/javascript"></script>
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div class="content clearfix">
	<div class="c-right">
		<div class="tb">
			<div class="parta">
				<div class="head">
					<em class="refresh"></em>
					<span class="s-sort">排序：<em>
						<select id="list_order_by" style="width:100%;border:none">
							<option value="signDate">日期</option>
							<option value="totalPrice">金额</option>
						</select>
					</em></span>
					<span class="checkbox-show checkbox">显示已作废<input type="checkbox" id="checkboxshowInput">
						<label id="checkboxshowInput-label" for="checkboxshowInput"></label>
					</span>
					<span class="checkbox-history checkbox">查看历史票据<input type="checkbox"  id="checkboxhisInput">
						<label id="checkboxhisInput-label" for="checkboxhisInput"></label>
					</span>
					<ul class="ul-each">
						<li id="billinfo-showtype-list" class="li-list billinfo-show-type" data-showtype="2">列表</li>
						<li id="billinfo-showtype-img" class="li-img billinfo-show-type ctron select" data-showtype="1">头像</li>
					</ul>
				</div>
				<div class="switch">
						<c:if test="${showType eq '1'}">
							<div class="lay bills" style="display:block;">
								<ul class="ul-bill clearfix">
									<c:forEach  var="billinfo" items="${result}">
										<li id="billinfoi-bean-${billinfo.id}">
											<div class="img-bill">
												<img src="${billinfo.imageUrl}" width="214" height="143" alt="">
											</div>
											<div class="bill-txt">
												<p class="pget">
													<input class="voucher_checkbox" type="checkbox" id="checkboxprinput${billinfo.id}"
													 data-amount="${billinfo.amount}" 
													 data-tax="${billinfo.tax}" 
													 data-totalprice="${billinfo.totalPrice}"
													 data-abstractmsg="${billinfo.abstractMsg}"
													 data-id="${billinfo.id}"
													 >
													<label for="checkboxprinput${billinfo.id}"></label>${billinfo.abstractMsg}
												</p>
												<p class="p-price">
													票价合计：<em>${billinfo.totalPrice}</em> 
													<time><fmt:formatDate value="${billinfo.signDate}" pattern="yyyy-MM-dd" /> </time>
												</p>
													<c:if test="${billinfo.billStatus != '4'}">
														<a class="a-wrong voucher_sign_error"  href="javascript:;" data-id="${billinfo.id}">标错</a>
													</c:if>
													<c:if test="${billinfo.billStatus != '5'}">
														<a class="a-del voucher_scrap" href="javascript:;" data-id="${billinfo.id}">废弃</a>	
													</c:if>
											</div>
										</li>
									</c:forEach>
								</ul>
							</div>
						</c:if>
						<c:if test="${showType eq '2'}">
							<div class="lay images clearfix" style="display:block;">
								<table width="100%" class="imgs-table" cellpadding="0"
									cellspacing="0">
									<thead>
										<tr>
											<th width="5%">选择</th>
											<th width="4%">影像</th>
											<th width="8%">类型</th>
											<th width="11%">摘要</th>
											<th width="16%">开票单位</th>
											<th width="10%">开票日期</th>
											<th width="9%">金额</th>
											<th width="9%">税额</th>
											<th width="9%">价格合计</th>
											<th width="14%">操作</th>
											<th width="4%">状态</th>
										</tr>
									</thead>
									<tbody id="table_show">
										<c:forEach var="billinfoTotal" items="${result}"  varStatus="index" >
											<tr>
												<td class="none-line">
													<div class="choose-fir">
														<input id="ipParent${billinfoTotal.index}" type="checkbox"  class="list_checkbox_parent list_checkbox_parent${billinfoTotal.index}"
														data-index="${billinfoTotal.index}" >
														<label for="ipParent${billinfoTotal.index}"></label>
													</div>
												</td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
												<td>${billinfoTotal.amountCount }</td>
												<td>${billinfoTotal.taxCount }</td>
												<td>${billinfoTotal.totalPriceCount }</td>
												<td></td>
												<td></td>
											</tr>
											<c:forEach var="billinfo" items="${billinfoTotal.billInfoList}">
												<tr id="billinfoi-bean-${billinfo.id}">
													<td class="none-line">
														<div class="choose">
															<em>
															<input id="ip${billinfo.id}" type="checkbox" class="list_checkbox_children list_checkbox_children${index.index} voucher_checkbox"
																 data-parentcheckbox="${index.index}" 
																 data-amount="${billinfo.amount}" 
																 data-tax="${billinfo.tax}" 
																 data-totalprice="${billinfo.totalPrice}"
																 data-abstractmsg="${billinfo.abstractMsg}"
																 data-id="${billinfo.id}"
															 >
															<label for="ip${billinfo.id}" id="label${billinfo.id}"></label>
															</em>
														</div>
													</td>
													<td class="tobig" data-url="${billinfo.imageUrl}">
														<%-- <img src="${billinfo.imageUrl}" alt=""> --%>
														<img src="/yunzh/static/billinfo/camera.png" alt="">
													</td>
													<td>${billinfo.tBIdName}</td>
													<td>${billinfo.abstractMsg}</td>
													<td>${billinfo.payName}</td>
													<td><fmt:formatDate value="${billinfo.signDate}"
															pattern="yyyy-MM-dd" /></td>
													<td>${billinfo.amount}</td>
													<td>${billinfo.tax}</td>
													<td>${billinfo.totalPrice}</td>
													<td>
														<div class="sp-but clearfix">
																<c:if test="${billinfo.billStatus ne '4'}">
																	<span class="biaoc voucher_sign_error" data-id="${billinfo.id}" >标错</span>
																</c:if>
																<c:if test="${billinfo.billStatus ne '5'}">
																		<span class="feiq voucher_scrap" data-id="${billinfo.id}">废弃</span>
																</c:if>
														</div>
													</td>
													<td>
														<c:choose>
															<c:when test="${billinfo.billStatus eq 0}">待审验</c:when>
															<c:when test="${billinfo.billStatus eq 1}">退回</c:when>
															<c:when test="${billinfo.billStatus eq 2}">已审验</c:when>
															<c:when test="${billinfo.billStatus eq 3}">已做账</c:when>
															<c:when test="${billinfo.billStatus eq 4}">作废</c:when>
															<c:when test="${billinfo.billStatus eq 5}">错误</c:when>
														</c:choose>
													</td>
												</tr>
											</c:forEach>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:if>
				</div>
				
				<div class="popUp wrong error-dialog">
					<h4>请填写标错原因<i class="i-close"></i></h4>
					<textarea id="sign-error-reason" rows="3" cols="20">
					</textarea>
					<div class="button clearfix">
						<button class="butsure voucher-sign-error-sure">确定</button>
						<button id="sign-error-reson-cancel" class="butcancel">取消</button>
					</div>
				</div>
				
				<!-- 放大图片 -->
				<div id="tobig">
					<i class="tobig-i"></i>
					<img src="" alt="">
				</div>
				
				<div class="popUp wrong scrap-dialog">
					<h4>请填写废弃原因<i class="i-close"></i></h4>
					<textarea id="scrap-reason" rows="3" cols="20">
					</textarea>
					<div class="button clearfix">
						<button class="butsure voucher-scrap-sure">确定</button>
						<button id="scrap-cancel" class="butcancel">取消</button>
					</div>
				</div>
				
			</div>
		</div>
		
		<div class="mask"></div>
		<div class="edit" style="min-height:410px;">
			<div class="edit-show">
				<p>选中票据：金额：<em id="amount_total">0.00</em>税额：<em id="tax_total">0.00</em>价格合计：<em id="total_price_total">0.00</em></p>
				<a id="voucher-edit-show-or-open"  class="a-edit" href="javascript:;">隐藏编辑</a>
			</div>
			<div class="th clearfix">
				<span class="sp-each clearfix"><i class="i-left"></i><i class="i-right"></i></span>
				<a id="new-voucher-option"  href="javascript:;">新增凭证</a>
				<a href="javascript:;">联查票据</a>
				<a id="new-voucher-template" href="javascript:;">生成模板</a>
				<a id="voucher-template-import" href="javascript:;">模板导入</a>
				<a id="delete-voucher" href="javascript:;">删除凭证</a>
				<a class="a-save" href="javascript:;">保存</a>
			</div>
			<div id="voucher-ul">
				
			</div>
				<ul class="ul-select" style="display:none">

				</ul>
			<ul class="ul-word" style="display:none">
				<c:forEach  var="vt"  items="${voucherTitles}">
					<li>${vt.voucherTitleName}</li>
				</c:forEach> 
			</ul>
			<div id="voucher-template-create-dialog" class="poptemp creat">
				<h4>生成模板<i class="creat-close"></i></h4>
				<div class="sel"><label for="">模板类型：</label>
				<select name="" id="newVoucherTemplate-type">
					<c:forEach  var="vt"  items="${tVoucherTemplateTypes}">
						<option value="${vt.id}">${vt.templateTypeName}</option>
					</c:forEach> 
				</select></div>
				<div class="sel"><label for="">模板名称：</label><input type="text" id="newVoucherTemplate-name"></div>
				<div class="button clearfix">
					<button id="newVoucherTemplate-save" class="butsure">确定</button>
					<button class="butcancel">取消</button>
				</div>
			</div>
			<div  id="voucher-template-import-dialog"  class="poptemp choose">
				<h4>请选择模板<i class="creat-close"></i></h4>
				<div class="sel"><label for="">模板类型：</label>
					<select name="" id="voucher-template-import-type">
						<option value="">全部</option>
						<c:forEach  var="vt"  items="${tVoucherTemplateTypes}">
							<option value="${vt.id}">${vt.templateTypeName}</option>
						</c:forEach> 
					</select>
				</div>
				<div class="sel"><label for="">名字：</label><select name="" id="voucher-template-import-select"></select></div>
				<div class="button clearfix">
					<button id="voucher-template-import-save"  class="butsure">确定</button>
					<button class="butcancel">取消</button>
				</div>
			</div>
			<div class="mask-edit"></div>
		</div>
		<div class="message-pop">
			<i class="i-mess"></i>
			<h4>信息提示</h4>
			<p>保存成功！！！</p>
		</div>
	</div>
</div>
</body>
<script id="templateImg" type="text/x-handlebars-template">	
<div class="lay bills" style="display:block;">
	<ul class="ul-bill clearfix">
	{{#result}}
	<li id="billinfoi-bean-{{id}}">
		<div class="img-bill">
			<img src="{{{imageUrl}}}" width="214" height="143" alt="">
		</div>
		<div class="bill-txt">
			<p class="pget">
				<input class="voucher_checkbox" type="checkbox" id="checkboxprinput{{id}}" 
				data-amount="{{amount}}" 
				data-tax="{{tax}}" 
				data-totalprice="{{totalPrice}}"
				data-abstractmsg="{{abstractMsg}}"
 				data-id="{{id}}"
				>
				<label for="checkboxprinput{{id}}"></label> {{abstractMsg}}
			</p>
			<p class="p-price">票价合计：<em>{{totalPrice}}</em> <time>{{signDate}}</p>
			{{#if error}}
				<a class="a-wrong voucher_sign_error" href="javascript:;" data-id="{{id}}">标错</a>	
			{{/if}}
			{{#if scrap}}
				<a class="a-del voucher_scrap" href="javascript:;" data-id="{{id}}">废弃</a>	
			{{/if}}
		</div>
	</li>
	{{/result}}
	</ul>
</div>
</script>
<script id="templateList" type="text/x-handlebars-template">
<div class="lay images clearfix" style="display:block;">
	<table width="100%" class="imgs-table" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th width="5%">选择</th>
				<th width="4%">影像</th>
				<th width="8%">类型</th>
				<th width="11%">摘要</th>
				<th width="16%">开票单位</th>
				<th width="10%">开票日期</th>
				<th width="9%">金额</th>
				<th width="9%">税额</th>
				<th width="9%">价格合计</th>
				<th width="14%">操作</th>
				<th width="4%">状态</th>
			</tr>
		</thead>
		<tbody>
{{#result}}
<tr>
	<td class="none-line">
		<div class="choose-fir">
			<input id="ipParent{{index}}" type="checkbox" data-index="{{index}}"  class="list_checkbox_parent list_checkbox_parent{{index}}"><label for="ipParent{{index}}"></label>
		</div>
	</td>
	<td></td>
	<td></td>
	<td></td>
	<td></td>
	<td></td>
	<td>{{amountCount}}</td>
	<td>{{taxCount}}</td>
	<td>{{totalPriceCount}}</td>
	<td></td>
	<td></td>
</tr>
{{#billInfoList}}
	<tr id="billinfoi-bean-{{id}}">
		<td class="none-line">
			<div class="choose">
				<em>
				<input id="ip{{id}}" type="checkbox" class="list_checkbox_children list_checkbox_children{{../index}} voucher_checkbox" 
				data-parentcheckbox="{{../index}}"  
				data-amount="{{amount}}" 
				data-tax="{{tax}}" 
				data-totalprice="{{totalPrice}}"
				data-abstractmsg="{{abstractMsg}}"
				data-id="{{id}}"
				>
				<label for="ip{{id}}"></label>
				</em>
			</div>
		</td>
		<td class="tobig" data-url="{{billinfo.imageUrl}}">
			<img src="/yunzh/static/billinfo/camera.png" alt="">
		</td>
		<td>{{tBIdName}}</td>
		<td>{{abstractMsg}}</td>
		<td>{{payName}}</td>
		<td>{{signDate}}</td>
		<td>{{amount}}</td>
		<td>{{tax}}</td>
		<td>{{totalPrice}}</td>
		<td>
			<div class="sp-but clearfix">
					{{#if error}}
						<span class="biaoc voucher_sign_error" data-id="{{id}}">标错</span>
					{{/if}}
					{{#if scrap}}
						<span class="feiq voucher_scrap" data-id="{{id}}">废弃</span>
					{{/if}}
			</div>
		</td>
		<td>{{statusName}}</td>
	</tr>
{{/billInfoList}}
{{/result}}
</tbody>
</table>
</div>
</script>


<script id="templateBillInfoToVoucher" type="text/x-handlebars-template">	
{{#each this}}
<div class="tb voucher-li voucher-current">
					<div class="voucher-top clearfix">
				      <div class="mark-wrap">
				      	<span class="txt">凭证字</span>
				        <span class="ui-combo-wrap" id="vch-mark">
				        	<input type="text" name="" class="input-txt" autocomplete="off" value="{{defaultVoucherTitle}}" readonly="readonly">
				        	<i class="trigger"></i>
				    	</span>
				        <span class="ui-spinbox-wrap">
				        	<input type="text" class="ui-spinbox" id="vch-num" autocomplete="off" value="{{voucherTitleNumber}}">
				        	<span class="btn-wrap clearfix">
				        		<a class="btn-up"></a>
				        		<a class="btn-down"></a>
				        	</span>
				        </span>
				        	<span class="txt">号</span>
				        <span class="date-wrap">
				            <span class="txt">日期</span>
				            <input type="text" class="ui-input ui-datepicker-input" id="vch-date" value="{{CurrentLastYYYYMMDD}}" onFocus="WdatePicker({startDate:'1980-05-01'})" readonly="readonly">
				        </span>
				      </div>
				      <div class="tit-wrap">
				        <h1 class="voucher-tit">记账凭证</h1>
				        <span id="vch-year">{{currentYear}}</span>年第<span id="vch-period">{{currentMonth}}</span>期 
				      </div>
				      <span class="attach-wrap">
				        	单据
				        <input type="text" class="ui-input" id="vch-attach" value="{{voucherLiNum}}">
				        	张
				      </span>
				    </div>
					<div class="body">
						<table class="tab-voucher"  cellpadding="0" cellspacing="0">
							<thead>
								<tr>
									<th class="col-operate"></th>
									<th class="col-summary" colspan="2">摘要</th>
									<th class="col-subject" colspan="2">会计科目</th>
									<th class="col-money" width="218"> 
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
									<th class="col-money col-credit" width="218">
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
							<tbody>
							{{#billinfo}}
								<tr class="entry-item">
									<td class="col-operate">
										<div class="add-delete">
											<a class="a-add" href="javascript:;"></a>
											<a class="a-delete" href="javascript:;"></a>
										</div> 
									</td>
	          						<td class="col-summary" data-edit="summary">
	          							<div class="cell-val summary-val">{{abstractmsg}}</div>
	          						</td>
	          						<td class="col-option" width="30">
	          							<div class="option">
	          								<a class="selSummary">摘要</a>
	          							</div>
	          						</td>
	          						<td class="col-subject" data-edit="subject">
	          							<div class="cell-val subject-val" data-amount="{{amount}}"  data-amountid=""></div>
	          						</td>
	          						<td class="col-option">
	          							<div class="option">
	          								<a class="selSub">科目</a>
	          							</div>
	          						</td>
	          						<td class="col-debite col-dc" data-edit="money">
	          							<div class="cell-val  debit-val"></div>
	          						</td>
	          						<td class="col-credit col-dc" data-edit="money">
	          							<div class="cell-val  credit-val"></div>
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
	          							<div class="cell-val summary-val">{{abstractmsg}}</div>
	          						</td>
	          						<td class="col-option" width="30">
	          							<div class="option">
	          								<a class="selSummary">摘要</a>
	          							</div>
	          						</td>
	          						<td class="col-subject" data-edit="subject">
	          							<div class="cell-val subject-val" data-amount="{{amount}}" data-amountid=""></div>
	          						</td>
	          						<td class="col-option">
	          							<div class="option">
	          								<a class="selSub">科目</a>
	          							</div>
	          						</td>
	          						<td class="col-debite col-dc" data-edit="money">
	          							<div class="cell-val  debit-val"></div>
	          						</td>
	          						<td class="col-credit col-dc" data-edit="money">
	          							<div class="cell-val  credit-val"></div>
	          						</td>
								</tr>
							{{/billinfo}}				
							</tbody>
							<tfoot>
						      	<td class="col-operate"></td>
						        <td colspan="4" class="col-total">合计：<span id="capAmount"></span></td>
						        <td class="col-debite col-debite1">
						        	<div class="cell-val debit-total debit-total" id="debit-total"></div>
						        </td>
						        <td class="col-credit col-credit1">
						        	<div class="cell-val credit-total credit-total" id="credit-total"></div>
						        </td>
						    </tfoot>
						</table>
						<p class="make-person">制单人：<span class="make-person-name">XXX</span></p>
					</div>
				</div> 
{{/each}}
</script>

<script id="newVoucherTemplate" type="text/x-handlebars-template">	
	<div style="width:300px;height:200px;position:absolute;top:100px;left:300px;z-index:1000px;background-color: red;">
		<select id="newVoucherTemplate-type" >
		{{#each this}}
				<option value="{{id}}">{{templateTypeName}}</option>
		{{/each}}
		</select>	
		<br>
		<input type="text" id="newVoucherTemplate-name"  >
		<input type="button" id="newVoucherTemplate-save" value="提交">	
</div>
</script>

<script id="accountTemplate" type="text/x-handlebars-template">	
{{#each this}}
	<li data-id="{{id}}" data-dc="{{dc}}">{{accuntId}}&nbsp;{{accountName}}</li>
{{/each}}
</script>	


<script id="templateBillInfoToVoucherImport" type="text/x-handlebars-template">	
{{#each this}}
<div class="tb voucher-li voucher-current">
					<div class="voucher-top clearfix">
				      <div class="mark-wrap">
				      	<span class="txt">凭证字</span>
				        <span class="ui-combo-wrap" id="vch-mark">
				        	<input type="text" name="" class="input-txt" autocomplete="off" value="{{defaultVoucherTitle}}" readonly="readonly">
				        	<i class="trigger"></i>
				    	</span>
				        <span class="ui-spinbox-wrap">
				        	<input type="text" class="ui-spinbox" id="vch-num" autocomplete="off" value="{{voucherTitleNumber}}">
				        	<span class="btn-wrap clearfix">
				        		<a class="btn-up"></a>
				        		<a class="btn-down"></a>
				        	</span>
				        </span>
				        	<span class="txt">号</span>
				        <span class="date-wrap">
				            <span class="txt">日期</span>
				            <input type="text" class="ui-input ui-datepicker-input" id="vch-date" value="{{CurrentLastYYYYMMDD}}" onFocus="WdatePicker({startDate:'1980-05-01'})" readonly="readonly">
				        </span>
				      </div>
				      <div class="tit-wrap">
				        <h1 class="voucher-tit">记账凭证</h1>
				        <span id="vch-year">{{currentYear}}</span>年第<span id="vch-period">{{currentMonth}}</span>期 
				      </div>
				      <span class="attach-wrap">
				        	单据
				        <input type="text" class="ui-input" id="vch-attach" value="{{voucherLiNum}}">
				        	张
				      </span>
				    </div>
					<div class="body">
						<table class="tab-voucher"  cellpadding="0" cellspacing="0">
							<thead>
								<tr>
									<th class="col-operate"></th>
									<th class="col-summary" colspan="2">摘要</th>
									<th class="col-subject" colspan="2">会计科目</th>
									<th class="col-money" width="218"> 
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
									<th class="col-money col-credit" width="218">
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
							<tbody>
							{{#billinfo}}
								<tr class="entry-item">
									<td class="col-operate">
										<div class="add-delete">
											<a class="a-add" href="javascript:;"></a>
											<a class="a-delete" href="javascript:;"></a>
										</div> 
									</td>
	          						<td class="col-summary" data-edit="summary">
	          							<div class="cell-val summary-val">{{exp}}</div>
	          						</td>
	          						<td class="col-option" width="30">
	          							<div class="option">
	          								<a class="selSummary">摘要</a>
	          							</div>
	          						</td>
	          						<td class="col-subject" data-edit="subject">
	          							<div class="cell-val subject-val" data-amount="{{debit}}{{credit}}"  data-amountid="{{accountId}}">{{accountName}}</div>
	          						</td>
	          						<td class="col-option">
	          							<div class="option">
	          								<a class="selSub">科目</a>
	          							</div>
	          						</td>
	          						<td class="col-debite col-dc" data-edit="money">
	          							<div class="cell-val  debit-val">{{debit}}</div>
	          						</td>
	          						<td class="col-credit col-dc" data-edit="money">
	          							<div class="cell-val  credit-val">{{credit}}</div>
	          						</td>
								</tr>
							{{/billinfo}}				
							</tbody>
							<tfoot>
						      	<td class="col-operate"></td>
						        <td colspan="4" class="col-total">合计：<span id="capAmount"></span></td>
						        <td class="col-debite col-debite1">
						        	<div class="cell-val debit-total debit-total" id="debit-total"></div>
						        </td>
						        <td class="col-credit col-credit1">
						        	<div class="cell-val credit-total credit-total" id="credit-total"></div>
						        </td>
						    </tfoot>
						</table>
						<p class="make-person">制单人：<span class="make-person-name">XXX</span></p>
					</div>
				</div> 
{{/each}}
</script>


<script type="text/javascript">
 var ctx = "${ctx}";
</script>
<%--引入操作js --%>
<script src="${ctxStatic}/js/billinfo.js" type="text/javascript"></script>
</html>