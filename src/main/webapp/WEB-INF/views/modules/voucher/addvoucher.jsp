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
 var accountArr;
</script>
<style type="text/css">
	html {background: #fff;}
</style>
</head>
<body>
	<div class="c-right clearfix">
			<div class="edit">
				<c:if test="${hasBillInfo}">
					<div class="pics">
						<a class="a-left" id="LeftArr" href="javascript:;"></a>
						<div class="pic-pj" id="ISL_Cont_1">
							<ul class="ul-pj clearfix">
								<c:forEach var="billinfo" items="${billinfos}" varStatus="status">	
									<c:if test="${!empty billinfo.id}">
										<c:choose>
											<c:when test="${status.index < 4 }">
												<li class="billinfo-voucher-li-${billinfo.id}   billinfo-show">
													<img src="${billinfo.imageUrl}"  data-id="${billinfo.id}"     width="226" height="156" alt="">
													<em class="billinfo-delete"  data-id="${billinfo.id}"  ></em>
													<p>${billinfo.abstractMsg}</p>
													<p class="p-price">价格合计：${billinfo.totalPrice}</p>
												</li>
											</c:when>
											<c:otherwise>
												<li class="billinfo-voucher-li-${billinfo.id}  billinfo-hide-right">
													<img src="${billinfo.imageUrl}"  data-id="${billinfo.id}"     width="226" height="156" alt="">
													<em class="billinfo-delete"  data-id="${billinfo.id}"  ></em>
													<p>${billinfo.abstractMsg}</p>
													<p class="p-price">价格合计：${billinfo.totalPrice}</p>
												</li>
											</c:otherwise>
										</c:choose>
										</c:if>
								</c:forEach>
							</ul>
						</div>
						<a class="a-right" id="RightArr" href="javascript:;"></a>
					</div>
				</c:if>
				
				<h2 class="h2-tit">新增凭证</h2>
				<div class="th clearfix">
					<div class="th-left">
						<span class="scrollleft"></span>
						<span class="scrollright"></span>
						<span class="span-tip" style="display: none;">作废反馈成功</span>
					</div>
					<div class="th-right clearfix">
						<span class="add-voucher" style="display: none;">新增模板</span>
						<span class="a-save">保存并新增</span>
						<span class="sp-import">模板导入</span>
						<span id="save-voucher-template" class="save-voucher">保存模板</span>
					</div>
				</div>
				<div class="voucher-tab">
					<div class="tb voucher-current <c:if test='${hasBillInfo}'>hasBillInfo</c:if>  add-voucher-account">
						<div class="voucher-top clearfix">
					      <div class="vou-left">
					      	记字第<input class="ui-spinbox" type="text"  value="${title}" readonly="readonly">号
					      </div>
					      <div class="date-wrap">
			            	日期<input type="text" class="ui-datepicker-input ui-input ui-datepicker-input Wdate" id="vch-date" value="${date}"  readonly="readonly">
					 	  </div>
					      <div class="vou-right">
					      	附单据<input type="text"  value="${billinfosize}">张
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
									<c:forEach var="billinfo" items="${billinfos}">	
										 <tr class="entry-item billinfo-voucher-tr-${billinfo.id}" data-id="${billinfo.id}">
											<td class="col-operate">
												<div class="add-delete">
													<a class="a-add" href="javascript:;"></a>
												</div> 
											</td>
			          						<td class="col-summary" data-edit="summary">
			          							<div class="cell-val summary-val">${billinfo.abstractMsg}</div>
			          						</td>
			          						<td class="col-subject" data-edit="subject" data-amount="${billinfo.amount}">
			          							<div class="cell-val subject-val">
			          								<p data-amountid="${billinfo.debitAccount.id}" data-amountdc="${billinfo.debitAccount.accountName}">${billinfo.debitAccount.accuntId}&nbsp;${billinfo.debitAccount.accountName}</p>
			          								<p></p>
			          							</div>
			          						</td>
			          						<td class="col-debite col-dc" data-edit="money">
			          							<c:choose>
			          								<c:when test="${billinfo.amount > 0 }"><div class="cell-val debit-val">${billinfo.amount}</div></c:when>
			          								<c:otherwise><div class="cell-val debit-val"></div></c:otherwise>
			          							</c:choose>
			          							<%-- <div class="cell-val debit-val">${billinfo.amount}</div> --%>
			          						</td>
			          						<td class="col-credit col-dc" data-edit="money">
			          							<c:choose>
			          								<c:when test="${!empty billinfo.debitAccount}"><div class="cell-val credit-val no-edit"></div></c:when>
			          								<c:otherwise><div class="cell-val credit-val"></div></c:otherwise>
			          							</c:choose>
			          						</td>
			          						<td class="col-delete">
												<div class="add-delete">
													<a class="a-delete" href="javascript:;"></a>
												</div> 
											</td>
										</tr>
										 <tr class="entry-item billinfo-voucher-tr-${billinfo.id}" data-id="${billinfo.id}">
											<td class="col-operate">
												<div class="add-delete">
													<a class="a-add" href="javascript:;"></a>
												</div> 
											</td>
			          						<td class="col-summary" data-edit="summary">
			          							<div class="cell-val summary-val">${billinfo.abstractMsg}</div>
			          						</td>
			          						<td class="col-subject" data-edit="subject"  data-amount="${billinfo.amount}">
			          							<div class="cell-val subject-val">
			          								<p data-amountid="${billinfo.creditAccount.accuntId}" data-amountdc="${billinfo.creditAccount.accountName}">${billinfo.creditAccount.accuntId}&nbsp;${billinfo.creditAccount.accountName}</p>
			          								<p></p>
			          							</div>
			          						</td>
			          						<td class="col-debite col-dc" data-edit="money">
			          							<c:choose>
			          								<c:when test="${!empty billinfo.creditAccount}"><div class="cell-val debit-val no-edit"></div></c:when>
			          								<c:otherwise><div class="cell-val debit-val"></div></c:otherwise>
			          							</c:choose>
			          						</td>
			          						<td class="col-credit col-dc" data-edit="money">
			          							<c:choose>
			          								<c:when test="${billinfo.amount > 0 }"><div class="cell-val credit-val">${billinfo.amount}</div></c:when>
			          								<c:otherwise><div class="cell-val credit-val"></div></c:otherwise>
			          							</c:choose>
			          		<%-- 					<div class="cell-val credit-val">${billinfo.amount}</div> --%>
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
				
				<!-- 科目-->
				<!-- <ul class="ul-select" style="display:none">
				
				</ul> -->
			</div>
		</div>
		
		
		
		<div id="template-voucher" class="dialog" style="display:none;" >
			<div class="tb" >
						<div class="voucher-top clearfix">
					      <div class="vou-left">
					      	记字第<input class="ui-spinbox" type="text" readonly="readonly">号
					      </div>
					      <div class="date-wrap">
			            	日期 <input type="text" class="ui-datepicker-input ui-input ui-datepicker-input Wdate" id="vch-date" value="${date}"  readonly="readonly">
					 	  </div>
					      <div class="vou-right">
					      	附单据<input type="text" value="0" readonly="readonly">张
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
		
		
		
		<!-- 选择模板 -->
<div class="SelectTemplate dialog" id="tab" style="display: none;">
	<input type="hidden" id="operate-template-id">
	<h6 class="h6-kr">选择模板<i  class="bill-info-close-dialog"></i></h6>
	<div class="content">
		<div class="switch clearfix">
			<a href="javascript:;"><i></i>日常凭证模板</a><a href="javascript:;"><i></i>计提转结模板</a>
		</div>
		<div class="tabCont">
			<div class="cont conta clearfix">
				<div class="contH">
				
				</div>
			</div>
			<div class="cont contb clearfix">
				<div class="contH">
					
				</div>
			</div>
		</div>
	</div>
</div>

	<div id="save-template" class="layer template dialog" style="display:none;">
		<h5 class="h6-kr">保存模板<i class="bill-info-close-dialog"></i></h5>
		<ul class="ul-template clearfix">
			<li class="clearfix"><label for="">模板名称</label><input id="save-template-input" type="text"></li>
			<li class="clearfix"><label for="">保存金额</label><input id="save-template-checkbox" type="checkbox"></li>
		</ul>
		<div class="buttonW clearfix">
			<button class="cancel">取消</button><button class="save save-template-save">保存</button>
		</div>
	</div> 	
		
		
		<div class="tbvoucher dialog  voucher-template-dialog" style="display:none;">
		<input type="hidden" id="voucher-template-id">
		<h5 class="h6-kr">新建模板<i class="bill-info-close-dialog"></i></h5>
				<div class="body">
					<div class="voucher-top clearfix">
				      <div class="vou-left">
				      	<label for="" class="lab-vou">模板名称</label><input type="text" id="new-save-template-input">
				      </div>
			    	</div> 
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
					 <p class="make-person">&nbsp;<!--制单人：<em>${user.name}</em> --></p>
					<div class="savevoucher">保存模板</div>
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
		
		
	<!-- 是否设置设置摊销 -->
	<div id="newPop" class="layer newPop" style="display:none">
		<h5 class="h6-kr">摊销科目<i class="i-close"></i></h5>
		<p class="prompt">你是否确定不设置摊销或折旧方法</p>
		<div class="button buttonW clearfix">
			<button id="newPop-save" class="cancel">确定</button><button id="newPop-cancel" class="save">取消</button>
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


	var $container = $('.contH');
    $container.imagesLoaded( function () {
        $container.masonry({
            columnWidth: '.contlist',
            itemSelector: '.contlist'
        });
    });
})



var $mainH2 = 395;

jQuery.extend({
    sw: function(id, event, tabActiveClass) {
        var _this = $("#" + id),
            _tabs = _this.find(".switch a"),
            _contents = _this.find(".cont"),
            showOne = function(i){
            	$(".tabCont").height(function(){
		return $mainH2;
	})

            	var $container = $('.contH');
			    $container.imagesLoaded( function () {
			        $container.masonry({
			            columnWidth: '.contlist',
			            itemSelector: '.contlist'
			        });
			    });

                _tabs.removeClass(tabActiveClass);
                _tabs.eq(i).addClass(tabActiveClass);
                _contents.hide();
                _contents.eq(i).show();

            };
        showOne(0);
        _tabs.bind(event, function(e){
            e.preventDefault();
            var _curr_tab = $(this),
                _idx = _curr_tab.index();
            showOne(_idx);
        })
    }
});

$(function($){
    $.sw("tab", "mouseover", "on");
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