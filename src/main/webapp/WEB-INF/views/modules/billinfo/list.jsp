<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发票信息列表-在线会计-芸豆会计</title>
<link href="${pageContext.request.contextPath}/static/css/reset.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/list.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/wenxintishi.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/sangetanceng.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/js/billinfolist.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/js/addvoucher.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/handlebar/handlebars-1.0.0.beta.6.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/static/css/autoAmortization.css" rel="stylesheet" type="text/css">



<script type="text/javascript">
 //	项目路径
 var ctx = "${ctx}";
</script>
</head>
<body>
<div class="list-wrapper clearfix">
		<h2 class="h2-tit">票据</h2>
			<div class="th clearfix">
				<div class="th-left">
					<span><i>${year}</i>年</span>
					<span>第<i>${month}</i>期</span>
					<span class="spab1">票据筛选</span>
					<span class="spab2 ui-combo-wrap" id="vch-mark">
			        	<input id="billstatus" type="text" name="" disabled="disabled" class="input-txt" autocomplete="off" value="全部" data-billstatus="">
			        	<i class="trigger"></i>
			    	</span>
			    	<span class="span-tip" style="display: none;"><em class="ico-em"></em>标错反馈成功</span>
				</div>
				<div class="th-right clearfix">
					<span>结账</span>
					<span class="sp-import">联查凭证</span>
					<span id="add-voucher">新增凭证</span>
					<div style="display:hidden;">
						<form id="to-add-voucher-form-submit" method="get" action="${ctx}/voucher/tVoucher/enterVoucher">
							<input type="hidden" name="billInfos" id="to-add-voucher-form-billInfos" >
						</form>
					</div>
					<!-- <span>智能做账</span> -->
				</div>
			</div>
		<div class="lay bills clearfix" <c:if test="${showType == 1}"> style="display:none;" </c:if> >
			<table width="100%" class="bill-table"  cellpadding="0" cellspacing="0">
				<thead>
				    <tr>
				      <th width="53">选择</th>
				      <th width="8%">类型</th>
				      <th width="12%">摘要</th>
				      <th width="19%">开票单位</th>
				      <th width="10%">开票日期</th>
				      <th width="9%">金额</th>
				      <th width="9%">税额</th>
				      <th width="9%">价格合计</th>
				       <th width="5%">状态</th>
				      <th width="15%">操作</th>
				    </tr>
				 </thead>
				 <tbody id="bills-tbody">
				 	
				 </tbody>
			</table>
		</div>
		<div class="lay imgs" <c:if test="${showType == 2}"> style="display:none;" </c:if>>
			<ul class="ul-pj clearfix">
				
			</ul>
		</div>
		<ul class="ul-word">
			<li data-billstatus="" value="">全部</li>
			<li data-billstatus="2" value="2">已审验 </li>
			<li data-billstatus="3" value="3">已做账 </li>
		</ul>
		<div id="showType" class="button" data-showtype="1">切换为列表模式</div>
	</div>
	
	<!-- 温馨提示 -->
<div class="kindlyReminder dialog" style="display:none;">
	<input id="dillog-big-bill-info-id" type="hidden">
	<input id="dillog-big-bill-info-number" type="hidden">
	<input id="dillog-big-bill-info-hasSave" type="hidden">
	<input id="dillog-big-bill-info-type" type="hidden">
	<h6 class="h6-kr">查看大图<i class="bill-info-close-dialog"></i></h6>
	<div class="imgbill"><img id="bill-info-img" src="http://placehold.it/794x422" width="794" height="422" alt=""></div>
	<div class="rightSlide">
		<span class="spanbut spInte sp-IntelligentAccount"><i>智能做账</i></span>
		<span class="spanbut spDeta sp-detailedInformation"><i>详细信息</i></span>
		<div class="cont">
			<div class="contdetail cont-IntelligentAccount">
			
			</div>
			<div class="contdetail cont-detailedInformation">
				<table class="tabdetail" cellspacing = "0" cellpadding = "0">
					<tr><td width="20%"><label for="">摘要</label></td><td id="bill-info-abstractMsg"  width="80%"></td></tr>
					<tr><td width="20%"><label for="">票据种类</label></td><td id="bill-info-tBIdName" width="80%"></td></tr>
					<tr><td width="20%"><label for="">开票单位</label></td><td id="bill-info-recieveName" width="80%"></td></tr>
					<tr><td width="20%"><label for="">票据状态</label></td><td id="bill-info-statusName" width="80%"></td></tr>
					<tr><td width="20%"><label for="">付款单位</label></td><td id="bill-info-payName" width="80%"></td></tr>
					<tr><td width="20%"><label for="">金额</label></td><td id="bill-info-amount" width="80%"></td></tr>
					<tr><td width="20%"><label for="">税额</label></td><td id="bill-info-tax" width="80%"></td></tr>
					<tr><td width="20%"><label for="">价格合计</label></td><td id="bill-info-totalPrice" width="80%"></td></tr>
					<tr><td width="20%"><label for=""></label></td><td width="80%"></td></tr>
					<tr><td width="20%"><label for=""></label></td><td width="80%"></td></tr>
					<tr><td width="20%"><label for=""></label></td><td width="80%"></td></tr>
					<tr><td width="20%"><label for=""></label></td><td width="80%"></td></tr>
				</table>
			</div>
		</div>
	</div>
	<div class="bottom">
		<div class="bg"></div>
		<div class="a"><a href="javascript:;" id="dialog-big-del">删除</a><a href="javascript:;" id="dialog-big-wrong">标错</a><a href="javascript:;" id="dialog-big-period">跨期</a></div>
	</div>
</div>
	
	<!-- 标错反馈弹层 -->
	 <div class="layer wrong dialog" style="display:none;">
	 	<input id="wrong-id"  type="hidden">
		<h5  class="h6-kr">标错反馈<i class="bill-info-close-dialog"></i></h5>
		<ul class="ul-wrongreason clearfix">
			<li><input class="inp-check" type="radio" name="wrong">价格标错</li>
			<li><input class="inp-check" type="radio" name="wrong">付款方标错</li>
			<li><input class="inp-check" type="radio" name="wrong">摘要标错</li>
			<li><input class="inp-check" type="radio" name="wrong">发票类型标错</li>
			<li><input class="inp-check wrong-input" type="radio" name="wrong">其他<input class="inp-txt" type="text"></li>
		</ul>
		<div class="buttonW clearfix">
			<button class="cancel">取消</button><button class="save wrong-save">保存</button>
		</div>
	</div> 
	
	<!-- 票据跨期弹层 -->
	<div class="layer instrument dialog" style="display:none;">
		<input id="instrument-id"  type="hidden">
		<h5 class="h6-kr">票据跨期<i class="bill-info-close-dialog"></i></h5>
		<ul class="ul-NegoInstrument clearfix">
			<li class="clearfix"><label for="">票据编号</label><input  type="text" class="inp-tx" value="1234567890"></li>
			<li class="clearfix"><label for="">票据期数</label><input type="text" class="inp-val time-from" value="2015年11月" readonly="readonly"></li>
			<li class="clearfix"><label for="">跨期至</label><span class="sp-time"></span><input type="text" class="inp-val inp-col select-time"onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM'})" ></li>
		</ul>
		<div class="buttonW clearfix">
			<button class="cancel">取消</button><button class="save instrument-save">保存</button>
		</div>
	</div>
	
	<!-- 温馨提示-->
	<div class="layer reminder dialog" style="display:none;">
		<input id="reminder-id"  type="hidden">
		<h5 class="h6-kr">温馨提示<i class="bill-info-close-dialog"></i></h5>
		<ul class="ul-kindlyReminder clearfix">
			<li class="clearfix">是否删除图片数据？</li>
			<li class="reminder-msg clearfix">图片编号"250031"</li>
			<li class="txt-col clearfix">删除后将无法恢复!</li>
		</ul>
		<div class="buttonW clearfix">
			<button class="cancel">取消</button><button class="save reminder-save">保存</button>
		</div>
	</div>	
	
	
	<div class="tbvoucher dialog voucher-current" style="display:none;">
		<h5 class="h6-kr">录入凭证<i class="bill-info-close-dialog"></i></h5>
				<div class="body">
					<div class="voucher-top clearfix">
				       <div class="vou-left">
					      	记字第<input class="ui-spinbox" type="text"  value="" readonly="readonly">号
					   </div>
					   <div class="date-wrap">
			            	日期 <input type="text" class="ui-datepicker-input ui-input ui-datepicker-input Wdate" id="vch-date" value="${date}"  readonly="readonly">
					   </div>
					   <div class="vou-right">
					      	附单据<input type="text"  value="0" readonly="readonly">张
					   </div>
			    	</div> 
					<table class="tab-voucher min-height-s"  cellpadding="0" cellspacing="0">
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
					<p class="make-person">制单人：<em>${user.name}</em></p>
					<div class="savevoucher-list">保存凭证</div>
				</div>
			</div>	

<div class="mask"></div>

	
<!-- 科目-->
<ul class="ul-select" style="display:none">
				
</ul>	
	
	<!-- 温馨提示 -->
<div class="autoAmortization dialog" style="display:none;">
	<h6 class="h6-kr">设置自动摊销<i class="bill-info-close-dialog"></i></h6>
	<table class="tab-autoAmortization" cellspacing="0" cellpadding="0">
		<tbody>
			<tr>
				<td width="173">摘要</td>
				<td width="249">会计科目</td>
				<td width="93">分摊月数</td>
				<td width="87">残值率</td>
				<td width="120">残值</td>
			</tr>
			<tr>
				<td><input class="ipt-summary" type="text" readonly="readonly"></td>
				<td>借<input class="ipt-subjext ipt-debit" type="text"></td>
				<td><input class="ipt-monthnum" type="text">月</td>
				<td><input class="ipt-value" type="text"></td>
				<td id="scrap-value">0</td>
			</tr>
			<tr>
				<td><input class="ipt-summary" type="text" readonly="readonly"></td>
				<td>贷<input class="ipt-subjext ipt-credit" type="text"></td>
				<td></td>
				<td style="visibility: hidden;"></td>
				<td style="visibility: hidden;"></td>
			</tr>
		</tbody>
	</table>
	<div class="buttonW clearfix">
		<button class="cancel">取消</button><button class="save autoAmortization-save">保存</button>
	</div>
</div>

<!-- 科目-->
<ul class="ul-select-amortize" style="display:none">
				
</ul>		
	
<div class="message-pop">
	<i class="i-mess"></i>
	<h4>信息提示</h4>
	<p>保存成功！！！</p>
</div>
		
<!--页面脚本区S-->
<!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->
<!-- <script src="js/jquery-1.11.2.min.js"></script> -->
<!--页面脚本区E-->

<!--TODO 智能做账（跳转到新增发票页面后台处理数据 c:foreach 处理展示）  票据联查  图片模式的操作 -->

<script id="accountTemplate" type="text/x-handlebars-template">	
{{#each this}}
	<li data-id="{{id}}" data-dc="{{dc}}" data-ifamortize="{{ifAmortize}}" data-accuntid="{{accuntId}}">{{accuntId}}&nbsp;{{parentName}}{{accountName}}</li>
{{/each}}
</script>	
</body>
</html>