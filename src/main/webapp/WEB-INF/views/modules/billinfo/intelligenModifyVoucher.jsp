<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增凭证-在线会计-芸豆会计</title>
<link href="${pageContext.request.contextPath}/static/css/reset.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/sangetanceng.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/xuanzemoban.css" rel="stylesheet" type="text/css">

<link href="${pageContext.request.contextPath}/static/css/newSubjectTwo.css" rel="stylesheet" type="text/css">

<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/js/addvoucher.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/handlebar/handlebars-1.0.0.beta.6.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src='${pageContext.request.contextPath}/static/js/imagesLoaded.js'></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/masonry.pkgd.min.js"></script>

<link href="${pageContext.request.contextPath}/static/css/autoAmortization.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
 //	项目路径
 var ctx = "${ctx}";
 var balance;
</script>
<style type="text/css">
	html {background: #fff;}
</style>
</head>
<body>
	<div class="c-right clearfix">
			<div class="edit">
				<input type="hidden" id="voucherID" value="${info.billInfo.id}">
				<h2 class="h2-tit">修改凭证</h2>
				<div class="th clearfix">
					<div class="th-left">
						<span class="scrollleft"></span>
						<span class="scrollright"></span>
						<span class="span-tip" style="display: none;">作废反馈成功</span>
					</div>
					<div class="th-right clearfix">
						<span id="modify" class="a-modify">修改</span>
					</div>
				</div>
				<div class="voucher-tab">
					<div class="tb voucher-current add-voucher-account">
						<div class="voucher-top clearfix">
					      <div class="vou-left">
					      	记字第<input class="ui-spinbox" type="text"  value="${info.title}" readonly="readonly">号
					      </div>
					      <div class="date-wrap">
			            	日期 <input type="text" class="ui-datepicker-input ui-input ui-datepicker-input Wdate" id="vch-date" value="${info.accountDate}" onFocus="WdatePicker({minDate:'%y-{%M-1}-01',maxDate:'%y-{%M-1}-%ld'})" readonly="readonly">
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
									<c:forEach var="billinfo" items="${info.intelligentAccountContentResults}">	
										 <tr class="entry-item billinfo-voucher-tr-">
											<td class="col-operate">
												<div class="add-delete">
													<a class="a-add" href="javascript:;"></a>
												</div> 
											</td>
			          						<td class="col-summary" data-edit="summary">
			          							<div class="cell-val summary-val">${billinfo.summary}</div>
			          						</td>
			          						<td class="col-subject" data-edit="subject" data-amount="${billinfo.debit} ${billinfo.credit}">
			          							<div class="cell-val subject-val">
			          								<p data-amountid="${billinfo.subject}" data-amountdc="${billinfo.dc}">${billinfo.account.accountName}</p>
			          								<p>
			          									<c:if test="${!empty billinfo.balance}">
			          										余额：
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
							<p class="make-person">制单人：<em class="make-person-name">${user.name}</em></p>
						</div>
					</div>
				</div>
			</div>
		</div>

		
		
		<!-- 温馨提示 -->
<div class="autoAmortization dialog" style="display:none;">
	<input type="hidden" id="autoAmortization-amortizeAccountId">
	<h6 class="h6-kr">设置自动摊销<i id="autoAmortization-close" class="bill-info-close-dialog"></i></h6>
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
		<button id="autoAmortization-cancel" class="cancel">取消</button><button class="save autoAmortization-save">保存</button>
	</div>
</div>

<!-- 科目-->
<ul class="ul-select-amortize" style="display:none">
				
</ul>

<div class="pull-down" style="display:none">
	<ul class="ul-select">
						
	</ul>
	<!-- <span class="sp-add">新增科目</span> -->
</div>
		
<div class="mask"></div>

	<!-- 温馨提示-->
	<div class="layer reminder dialog" style="display:none;">
		<input id="delete-template-id"  type="hidden">
		<h5 class="h6-kr">温馨提示<i class="bill-info-close-dialog"></i></h5>
		<ul class="ul-kindlyReminder clearfix">
			<li class="clearfix">是否删除凭证模板？</li>
		<!-- 	<li class="reminder-msg clearfix">图片编号"250031"</li> -->
			<li class="txt-col clearfix">删除后将无法恢复!</li>
		</ul>
		<div class="buttonW clearfix">
			<button class="cancel">取消</button><button class="save delete-template-save">保存</button>
		</div>
	</div>	

<div class="message-pop">
	<i class="i-mess"></i>
	<h4>信息提示</h4>
	<p>保存成功！！！</p>
</div>
		
<!-- 票据跨期弹层 -->
	<div id="subject-select-add" class="layer newSubjectTwo" style="display:none">
		<h5 class="h6-kr">新增科目<i id="subject-select-add-close" class="i-close"></i></h5>
		<ul class="ul-newSubjectTwo clearfix">
			<li class="clearfix"><label for="">科目名称</label><input type="text" id="subject-select-add-name" class="inp-length" value=""></li>
			<li class="clearfix"><label for="">上级科目</label>
				<select id="subject-select-add-select" type="text" class="inp-name" value="招商银行">
					
				</select>
			</li>
		</ul>
		<div class="button buttonW clearfix">
			<button id="subject-select-add-cancel" class="cancel">取消</button><button id="subject-select-add-save" class="save">保存</button>
		</div>
	</div>
		
	
<!--页面脚本区S-->
<!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->
<script>
$(function(){
	var _contr = $(".ul-each li"),
		_focs = $(".switch .lay"),
		_bills = $(".ul-bill li"),
		_dels = $(".ul-bill li .a-del");
	_contr.bind("click",function(){
		var i = $(this).index();
		_focs.hide();
		$(_focs[i]).show();
	})
	// 图表删除按钮
	_bills.hover(function(){
		$(this).find(".a-del").show();
		$(this).find(".a-wrong").show();

	},function(){
		$(this).find(".a-del").hide();
		$(this).find(".a-wrong").hide();
	})


	$("body").on("click",".ul-bill li .a-del",function(){
		$(".popUp").show();
		$(".mask").show();
	})

	$("body").on("click",".butcancel",function(){
		$(".popUp").hide();
		$(".mask").hide();
	})

	$("body").on("click",".i-close",function(){
		$(".popUp").hide();
		$(".mask").hide();
	})
	//查看大图-关闭
	$("body").on("click",".bill-info-close-dialog,.cancel",function(){
		$(".dialog").hide();
		$(".mask").hide();
		if($(".to-new-voucher-template-current").length > 0){
			$(".to-new-voucher-template-current").addClass("voucher-current").removeClass("to-new-voucher-template-current");
		}
	})

// 会计科目 余额的显示隐藏
$("body").on("input propertychange",".inp-sub",function(){
	var divstr = $(this).prev().text();
	var inpstr = $(this).val();
	if(divstr == inpstr){
		$(this).parent().next().find(".option").append('<a class="balance" data-id="762192203369742" data-number="1001" data-cur="[&quot;RMB&quot;]">余额</a>');
	}else{
		$(".balance").remove();
	}
})
	
	//修改凭证
	$("body").on("click","#modify",function(){
		 //判断当前的表单是否已提交 提交过的不允许再提交
		 if($(".voucher-current").hasClass("voucher_has_save")){
			 //messagePop("当前凭证已保存");
			 $(".span-tip").text("当前凭证已保存").show();
			 msgFadeOut();
			 return false;
		 }
		//合计金额
		 var totalAmount = $(".voucher-current").find(".debit-total").text();
		 totalAmount =  toMoneyNum(totalAmount);//小数点处理
		 var creditTotal =  $(".voucher-current").find(".credit-total").text();
		 creditTotal =  toMoneyNum(creditTotal);//小数点处理
		 if(totalAmount != creditTotal){
			 //messagePop("录入借贷不平");
			 $(".span-tip").text("录入借贷不平").show();
			 msgFadeOut();
			 return false;
		 }
		 if(totalAmount <= 0){
			 //messagePop("请录入借贷方金额");
			 $(".span-tip").text("请录入借贷方金额").show();
			 msgFadeOut();
			 return false;
		 }

		//凭证字号
		var voucherTitleNumber = $(".voucher-current").find(".ui-spinbox").val();
		
		//记账日期 TODO后台赋值
		var accountDate = $(".voucher-current").find(".ui-datepicker-input").val();
		
		var length = $(".voucher-current").find(".vou-right").children("input").val();
		
		if(isNaN(length)){
			length = 0;
		}	
		
		var recieptNumber = length;
		//制单人
		var userName = $(".voucher-current").find(".make-person-name").text();
		
		var voucherExpArr = new Array()
		//凭证摘要
		var flag = false;
		var flagAccount = false;
		var noinputaccountName;
		var validTr = 0;
		$(".voucher-current").find(".entry-item").each(function(i){
			//摘要
			var exp = $(this).find(".summary-val").text();
			if(exp.length > 0){
				var voucherExp = {};
				voucherExp.exp = exp;
				noinputaccountName = $(this).find(".subject-val").find("p").eq(0);
				if($(noinputaccountName).text().length < 1 && !flagAccount){
					flagAccount = true;
				}
				voucherExp.accountName =  $(noinputaccountName).text();
				voucherExp.accountId =  $(this).find(".subject-val").find("p").eq(0).data("amountid");
				
				var debit = parseFloat($(this).find(".debit-val").text());
				
				if(debit > 0 && !isNaN(debit)){
					debit =  toMoneyNum(debit+"");//小数点处理
				}else{
					debit = 0;
				}	
				voucherExp.debit =  debit;
				var credit = parseFloat($(this).find(".credit-val").text());
				if(credit > 0 && !isNaN(credit)){
					credit =  toMoneyNum(credit+"");//小数点处理
				}else{
					credit = 0;
				}
				voucherExp.credit =  credit;
				if($(this).find(".amortize").hasClass("hasSave")){
					voucherExp.amortize =  true;
				}
				voucherExpArr.push(voucherExp);
				validTr++;
			}else{
				if(0 == i ){
					flag = true;
					return false;
				}
			}
		})
		
		if(flag){
			messagePop("第1条分录摘要不能为空！");
			return false;
		}
		
		if(flagAccount){ //摘要未填 未填处于点击状态
			$(noinputaccountName).click();
			return false;
		}
		
		if(validTr < 2){
			messagePop("请录入至少二条有效分录！");
			return false;
		}
		
		var billInfoStr='';

		$(".billinfo-delete").each(function(){ 
			billInfoStr = billInfoStr+ $(this).data("id")+","
		});
		//保存前信息验证 TODO
		var  expName;		// 名字
		var  enterAccountDate;		// 入账日期
		var  originalValue;		// 原值
		var  scrapValueRate;		// 残值率
		var  scrapValue;		// 残值
		var  amortizeAgeLimit;		// 摊销年限
		var debitAccountId;//借-科目id
		var creditAccountId;//贷-科目id
		var amortizeAccountId;//摊销科目id
		//设置自动摊销
		if($(".voucher-current").find(".amortize").hasClass("hasSave")){
			expName = $(".autoAmortization .ipt-summary").val();
			originalValue = toMoneyNum($(".voucher-current .amortize").data("amount")+"");
			scrapValueRate = $(".autoAmortization .ipt-value").val();
			scrapValue = $(".autoAmortization #scrap-value").text();
			amortizeAgeLimit = $(".autoAmortization .ipt-monthnum").val();
			debitAccountId = $(".autoAmortization .ipt-debit").data("accountid");
			creditAccountId = $(".autoAmortization .ipt-credit").data("accountid");
			amortizeAccountId = $("#autoAmortization-amortizeAccountId").val();
		}
		$.ajax({
		     type: 'POST',
		     url: ctx+'/billinfo/tDealBillInfo/modify',
		     cache:false,  
		     dataType:'json',
		     data: {
		    	 'id':$("#voucherID").val(),
		    	 'voucherTitleNumber':voucherTitleNumber,
		    	 'accountDate' :accountDate,
		    	 'recieptNumber':recieptNumber,
		    	 'totalAmount':totalAmount,
		    	 'userName':userName,
		    	'voucherExpStr':JSON.stringify(voucherExpArr),
		    	'billinfoid':billInfoStr,
		    	'expName':expName,	
		 	    'enterAccountDate':	accountDate	,
		 	    'originalValue':originalValue,		
		 	    'scrapValueRate':scrapValueRate	,
		 	    'scrapValue':scrapValue,
		 	    'amortizeAgeLimit':	amortizeAgeLimit,
		 	    'debitAccountId':debitAccountId,
				'creditAccountId' :creditAccountId,
				'amortizeAccountId':amortizeAccountId
		     } ,
		     success: function(data){
		    	if(data.suc){
		    		//给当前凭证保存标记 和 凭证id
		    		$(".voucher-current").addClass("voucher_has_save").data("id",data.id);
		    		//保存成功后 保存按钮隐藏 新增按钮显示
		    		$(".a-save").hide();
		    		$(".add-voucher").show();
		    		$(".span-tip").text("修改凭证成功").show();
		    		
		    		 window.location.href= ctx +"/billinfo/tDealBillInfo/intelligentAccountResult";
		    		
		    	}else{
		    		//保存失败提示
		    		$(".span-tip").text("修改凭证失败").show();
		    	}
		    	msgFadeOut();
		     }

		});
	})

})

</script>
<!--页面脚本区E-->
<script id="accountTemplate" type="text/x-handlebars-template">	
{{#each this}}
	<li data-id="{{id}}" data-dc="{{dc}}" data-ifamortize="{{ifAmortize}}" data-accuntid="{{accuntId}}">{{accuntId}}&nbsp;{{parentName}}{{accountName}}</li>
{{/each}}
</script>	
</body>
</html>