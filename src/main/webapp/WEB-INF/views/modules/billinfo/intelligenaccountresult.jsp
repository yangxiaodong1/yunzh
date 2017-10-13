<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>智能做账-在线会计-芸豆会计</title>

<script src="${pageContext.request.contextPath}/static/jquery/jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/taxbase/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/static/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/static/jquery/jquery-ui.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/jquery/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>

<link href="${pageContext.request.contextPath}/static/css/master_2.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/css/dialog.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/css/reset.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/accountSettle.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/checkCredentials.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/print_dialog.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/certificate_table.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/sangetanceng.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/static/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery.form.js"></script>
<link href="${pageContext.request.contextPath}/static/css/wenxintishi.css" rel="stylesheet" type="text/css">

<link rel="stylesheet" href="${pageContext.request.contextPath}/static/zoomImage/css/zyImage.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/zoomImage/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/iviewer/jquery.iviewer.css" />
<script src="${pageContext.request.contextPath}/static/iviewer/jquery.mousewheel.min.js" type="text/javascript" ></script>
<script src="${pageContext.request.contextPath}/static/iviewer/jquery.iviewer.js" type="text/javascript" ></script>
<script src="${pageContext.request.contextPath}/static/iviewer/zyImage.js" type="text/javascript" ></script>
<script src="${pageContext.request.contextPath}/static/jquery/toggle.js" type="text/javascript" ></script>
<link href="${pageContext.request.contextPath}/static/css/autoAmortization.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/static/js/addvoucher.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/handlebar/handlebars-1.0.0.beta.6.js" type="text/javascript"></script>
<script type="text/javascript">
 //	项目路径
 var ctx = "${ctx}";
 var bills = new Array();
 var bill;
</script>
<style>
*{font-size:12px;}
.btn_txt{padding:0 5px;float:right;margin-right:60px;visibility:hidden;}
.checkVoucher .btn_txt {float:right;visibility:hidden;width: 100px;margin-right: 35px;}
.checkVoucher .btn_txt span {width: 46px;text-indent: 20px;}
.ui-dialog .ui-dialog-title {
	width: auto;
	}
.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {
	background: url(static/images/ico/21.jpg) no-repeat center;
}
#dialog_remove {
	display:none;
}
.message-pop{display:none;position:fixed;top:40px;width:100%;text-align:center;z-index:1000;}
.message-pop span{background:#4f9de8;padding:5px 75px;color:#fff;}
.hoverCont .shade {position: absolute;top: 0;left: 75px;height: 30px;width: 226px;background: #e7f3ff;opacity: 0.6;filteer:alpha(opacity=60);z-index: 2;display:none;}
.total_text {text-align:right;padding-right: 8px;}
#contentTable .hoverCont span.pzupload {text-indent:20px;}
.rightSlide{top:3px;}
#dialog_media .min-h200{position: relative;width:840px;height:499px;overflow: hidden;}
.viewer{position: relative;width:600px;height:330px;}
</style>
<script type="text/javascript">
var ctxStatic = "/static";
</script>
</head>
<body>
	<div class="checkVoucher">
		<div class="message-pop">
			<span></span>
		</div>
		<h2 class="h2-tit">
			智能做账
		</h2>
		<div class="th clearfix">
			<div class="th-left text-left"><div class="left" style="position: relative;top:12px;">
			${fn:substring(uploadPeriod, 0, 4)}年${fn:substring(uploadPeriod, 4, 6)}期
			</div>								
			</div>
			<div class="th-right clearfix">				
				<span id="intelligen-save">保存</span>
				<span onclick="window.history.go(-2)">返回票据</span>
			</div>
			<div class="clear"></div>
		</div>
		<div class="tb">
			<div class="thead_container">
				<table class="tabhead" width="100%" cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<th width="25%">摘要</th>
							<th width="35%">科目</th>
							<th width="20%">借方金额</th>
							<th width="20%">贷方金额</th>
						</tr>
					</thead>
				</table>
			</div>
			<div class="tb_scroll">			
					<c:forEach var="re" items="${result}">
					<div class="contentTable showTable voucher-${re.billInfo.id}">
						<table id="contentTable" class="table" width="100%" cellpadding="0" cellspacing="0">
							<thead>
							<tr>
							<td class="list-head" colspan="4">
							<div class="clearfix">
								<span>日期:<fmt:formatDate value="${re.accountDate}" pattern="yyyy-MM-dd" /> 
								</span>
								<span>凭证字号：记-${re.title}</span>
								<span>记账人：${user.name}</span>
								<div class="hoverCont"> 
										<span class="s-icon image dialog_image" data-id="${re.billInfo.id}"><i></i>影像</span>
										<span class="s-icon updatebill" data-id="${re.billInfo.id}"><i></i>修改</span>
										<span id="${re.billInfo.id}" class="s-icon dialog_remove" onclick=""><i></i>删除</span>
								</div>								
							</div>
							</td>
							</tr>
							</thead>
							<tbody class="list-body">		
								<c:forEach var="co" items="${re.intelligentAccountContentResults}">
									<tr>
										<td  width="25%">${co.summary}</td>
										<td  width="35%">${co.account.accuntId}&nbsp;${co.account.parentName}${co.account.accountName} 
											<c:if test="${co.amortizeFlag}">
											<span class="right pad_5_5 font_cc4 ipt_sz" attrsz="0" data-id="${re.billInfo.id}">
											设置自动摊销</span>
											</c:if>
										</td>
										<td  width="20%">
											<c:if test="${co.debit>0}">
												${co.debit}
											</c:if>
										</td>
										<td  width="20%">
											<c:if test="${co.credit>0}">
												${co.credit}
											</c:if>
										</td>
									</tr>
								</c:forEach>							
							</tbody>
							<tfoot>
								<tr>
									<td class="list-foot" colspan="2">合计：${re.totalAmountZN}</td>
									<td class="total_text">${re.totalAmount}</td>
									<td class="total_text">${re.totalAmount}</td>
								</tr>
							</tfoot>
						</table>
					</div>
					</c:forEach>				

			</div>
		</div>
	</div>
	
	
<!-- 影像 -->
<div id="dialog_media" title="影像">
	<div class="min-h200">
	<div class="imgbill">
		<!--img-->
<div class="parent_container">
	<div id="panImage" class="pan_image"></div>
</div>
		<!--/img-->
	</div>
	<div class="rightSlide">
		<span class="spanbut spDeta sp-detailedInformation"><i>详细信息</i></span>
		<div class="cont">
			<div class="contdetail cont-IntelligentAccount">
			
			</div>
			<div class="contdetail cont-detailedInformation">
				<table class="tabdetail" cellspacing = "0" cellpadding = "0">
					<tr><td width="20%"><label for="">发票类型</label></td><td width="80%"><div class="ipt_bj"><input id="bill-info-type" type="text" class="sta_ipta" readonly/><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span></div></td></tr>
					<tr><td width="20%"><label for="">发票号码</label></td><td width="80%"><div class="ipt_bj"><input id="bill-info-invoiceCode" type="text" class="sta_ipta" readonly/><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span></div></td></tr>
					<tr><td width="20%"><label for="">发票代码</label></td><td width="80%"><div class="ipt_bj"><input id="bill-info-invoiceNumber" type="text" class="sta_ipta" readonly/><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span></div></td></tr>
					<tr><td width="20%"><label for="">开票日期</label></td><td width="80%"><div class="ipt_bj"><input id="bill-info-signDate" type="text" class="sta_ipta" readonly/><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span></div></td></tr>
					<tr><td width="20%"><label for="">付款方</label></td><td width="80%"><div class="ipt_bj"><input id="bill-info-payName" type="text"  class="sta_ipta" readonly/><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span></div></td></tr>
					<tr><td width="20%"><label for="">摘要</label></td><td width="80%"><div class="ipt_bj"><input id="bill-info-abstractMsg" type="text" class="sta_ipta" readonly/><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span></div></td></tr>		
					<tr><td width="20%"><label for="">金额</label></td><td width="80%"><div class="ipt_bj"><input id="bill-info-amount"  type="text" class="sta_ipta" readonly/><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span></div></td></tr>
					<tr><td width="20%"><label for="">税额</label></td><td width="80%"><div class="ipt_bj"><input id="bill-info-tax" type="text"  class="sta_ipta" readonly/><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span></div></td></tr>
					<tr><td width="20%"><label for="">价格合计</label></td><td width="80%"><div class="ipt_bj"><input id="bill-info-totalPrice" type="text"class="sta_ipta" readonly/><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span></div></td></tr>
					<tr><td width="20%"><label for="">收款单位</label></td><td width="80%"><div class="ipt_bj"><input id="bill-info-recieveName" type="text" class="sta_ipta" readonly/><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span></div></td></tr>
					<tr><td width="20%"><label for="">收款方纳税人识别码</label></td><td width="80%"><div class="ipt_bj"><input id="bill-info-recieveTaxIdentificationNumber" type="text" class="sta_ipta" readonly/><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span></div></td></tr>
					<tr><td width="20%"><label for=""></label>票据顺序号码</td><td width="80%"><div class="ipt_bj"><input id="bill-info-dValue" type="text" class="sta_ipta" readonly/><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span></div></td></tr>
					<tr><td width="20%"><label for=""></label></td><td width="80%"></td></tr>
					<tr><td width="20%"><label for=""></label></td><td width="80%"></td></tr>
				</table>
			</div>
		</div>
	</div>
	</div>
</div>

<!-- 科目-->
<ul class="ul-select-amortize" style="display:none">
				
</ul>

<!-- <div class="pull-down" style="display:none">
	<ul class="ul-select">
						
	</ul>
</div> -->

<!--删除提示-->
<div id="dialog_remove" title="系统提示">
	<input id="dialog_remove_bill_id" type="hidden">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center message">是否删除凭证“记-001”？<br />
删除后凭证关联的票据会自动放入票据列表中
		</div>
		<div class="hr40"></div>
		
		<div class="text-center">
			<button class="btn btn-default btn_w_a btn_bg_4 cancel mar-rig10">取消</button>
			<button class="btn btn-default btn_w_a btn_bg_2 suredelete">确认</button>
		</div>

	</div>
</div>		
	
<!-- 设置自动摊销 -->
<div id="dialog_tx" title="设置自动摊销">
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
	<div class="hr20"></div>
	<div class="buttonW clearfix">
		<button type="button" class="btn btn-default btn_w_a btn_bg_4 radius_3" id="ipt_btnt1">取消</button>&nbsp;&nbsp;&nbsp;&nbsp;<iput type="submit" class="btn btn-default btn_w_a btn_bg_2 radius_3" id="ipt_btnt2">确定</button>
	</div>
</div>
<!-- 有未设置摊销项目 -->
<div id="dialog_sznone" title="温馨提示">
	<ul class="ul-kindlyReminder clearfix">
		<li class="clearfix">有未设置摊销项目</li>
	</ul>
</div>
<!--页面脚本区S-->
<!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->
<script>
$(function(){
	
	//摊销
	$( "#dialog_tx" ).dialog({
		autoOpen: false,
		modal: true,
		width:760,
		height:300
	})
	
	//保存按钮20160405
	$( "#dialog_sznone" ).dialog({
		autoOpen: false,
		modal: true,
		width:474,
		height:200
	})	
	
	
	var  arraysz= new Array();
	$(".ipt_sz").each(function(){
		var attrsz=$(this).attr('attrsz');
		var nowDataid=$(this).attr('data-id');
		var iptsz=$(this);
		var indexsz=iptsz.index();		
		arraysz[indexsz]=attrsz;
		if(attrsz=='0'){
			//有未设置摊销项目
			iptsz.css('visibility','visible');
			iptsz.on('click',function(){
				$("#dialog_tx").dialog("open");
				//取消
				$("#ipt_btnt1").on('click',function(){
					$("#dialog_tx").dialog("close");
				})
				//提交
				$("#ipt_btnt2").on('click',function(){
					$("#dialog_tx").dialog("close");
				})
				console.info(nowDataid);
			})
		}else if(attrsz=='1'){
			iptsz.css('visibility','hidden');
		}		
	})	
	
/* 	var valsz=isCon(arraysz, 0);
	if(valsz!=true){
		//无未设置摊销项目，值为1
		$("#save_pz").click(function(){
			window.location.href="处理的_智能做账_保存后.html";
		})
		console.info("无");
	}else{
		//有未设置摊销项目，值为0
		$("#save_pz").click(function(){
			$( "#dialog_sznone" ).dialog("open");
		})
		console.info("有");
	} */
	
	
	//保存
	$("#intelligen-save").on("click",function(e){
		window.location.href = ctx +"/billinfo/tDealBillInfo/saveIntelligent";
	})
	
	//修改凭证
	$(".updatebill").on("click",function(e){
		var id = $(this).data("id");
		window.location.href =  ctx +"/billinfo/tDealBillInfo/modifyVoucher?id="+id;	
	})
	
	//删除凭证 TODO 存在问题  没有删除中对应数据 后台方法未调用
	$("body").on("click",".suredelete",function(){
		var id = $("#dialog_remove_bill_id").val();
		//删除session对应数据
		$.ajax({
		     type: 'POST',
		     url: ctx+'/billinfo/tDealBillInfo/intelligentAccountBill',
		     cache:false,  
		     async: false,
		     dataType:'json',
		     data:{
		    	 "id":id
		     },
		     success: function(data){
		    	 if(data){
		    			for(var i in bills){
		    				bill = bills[i];
		    				if(id == bill.id){
		    					bills.splice(i,1);
		    				}
		    			}
		    			$(".voucher-"+id).remove();
		    			$(".cancel").click();
		    			alert("删除成功");
		    	 }else{
		    		 alert("删除失败");
		    	 }
		     }
		});		
	})
	
})
	$(".print_all").on("click",function() {
		$(".bg").show();
		$(".print_dialog").show();
	})
	
	$(".bill-info-close-dialog").on("click",function() {
		$(".bg").hide();
		$(".print_dialog").hide();
	})

	$(".template_chose").each(function() {
		$(this).hover(function() {
			$(".checked",this).show();
		},function() {
			$(".checked",this).hide();
		})
	})
	$( "#dialog_remove").dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});	
	$( "#dialog_media" ).dialog({
		autoOpen: false,
		width: 840,
		height:532,
		modal: true
	});	
/**影像**/
$("body").on("click",".dialog_image",function( event ) {
	$( "#dialog_media" ).dialog( "open" );
	var mytid=$(this).attr("data-id");
	$("#panImage").empty();
	var imgList = new Array();

	//第一次时 取处理票据信息
	if(bills.length < 1){
		//加载科目信息
		$.ajax({
		     type: 'POST',
		     url: ctx+'/billinfo/tDealBillInfo/intelligentAccountBill',
		     cache:false,  
		     async: false,
		     dataType:'json',
		     success: function(data){
		    	 bills = data;
		     }
		});
	}
	for(var i in bills){
		billC = bills[i];
		if(mytid == billC.id){
			bill = billC;
			var id = bill.id;
			var imageUrl = bill.imageUrl;
			var abstractMsg = bill.abstractMsg;
			/* imageUrl = 'http://localhost:8080/yunzh/static/zoomImage/1.jpg'; */ //todo
			var billinfoedit = {
					"content":abstractMsg,
					"src":imageUrl,
					"attrimgid":id
			};
			imgList.push(billinfoedit);
		}
	}
	
	$("#panImage").zyImage({
		imgList : imgList, // 数据列表
		mainImageWidth  : 600,
		mainImageHeight : 330,
		thumImageWidth  : 110,
		thumImageHeight : 110,
		changeCallback : function(index, image){  // 切换回调事件

		},
		deleteCallback : function(image){  // 删除回调事件
	
		}
	});
	
});

/**详细信息*/
$(".sp-detailedInformation").toggle(function(){
	var data = bill;
	
	$("#bill-info-type").val(data.tBId).data("field","t_b_id");
	$("#bill-info-invoiceCode").val(data.invoiceCode).data("field","invoice_code");
	$("#bill-info-invoiceNumber").val(data.invoiceNumber).data("field","invoice_number");
	$("#bill-info-signDate").val(data.signDate).data("field","sign_date");
	$("#bill-info-payName").val(data.payAccountName).data("field","pay_account_name");
	$("#bill-info-abstractMsg").val(data.abstractMsg).data("field","abstract_msg");
	$("#bill-info-amount").val(data.amount).data("field","amount");
	$("#bill-info-tax").val(data.tax).data("field","tax");
	$("#bill-info-totalPrice").val(data.totalPrice).data("field","total_price");
	$("#bill-info-recieveName").val(data.recieveName).data("field","recieve_name");
	$("#bill-info-recieveTaxIdentificationNumber").val(data.recieveTaxIdentificationNumber).data("field","recieve_tax_identification_number");
	//票据顺序号码 todo
	$("#bill-info-dValue").val(data.dValue).data("field","d_value");
	
	$(this).show();
	$(".rightSlide").animate({right:"0"}).removeClass("bor27bc9e");
	$(this).removeClass("spDeta").addClass("spDetaOn").css("border-right","1px solid #fff");
	$(".sp-IntelligentAccount").removeClass("spInteOn").addClass("spInte").css("border-right","1px solid #3d8ae2");
	$(".cont-IntelligentAccount").hide();
	$(".cont-detailedInformation").show();
	
},function(){
	$(this).show();
	$(".rightSlide").animate({right:"-392"});
	$(this).removeClass("spDetaOn").addClass("spDeta").css("border-right","1px solid #fff");
	$(".sp-IntelligentAccount").removeClass("spInteOn").addClass("spInte").css("border-right","1px solid #fff");
})
	//删除方法 
	//点击事件删除确定事件
	$(".dialog_remove").click(function( event ) {
	  	id=$(this).attr('id');
	  	$("#dialog_remove_bill_id").val(id);
	  	var title = $(this).parent().parent().find(".title").text();
	  	$("#dialog_remove").find(".message").html("是否删除凭证“记-"+title+"”?<br/>删除后凭证关联的票据会自动放入票据列表中");
		$( "#dialog_remove").dialog( "open" );
		$(".cancel").on("click",function() {
			$( "#dialog_remove" ).dialog( "close" );
		})
	});
	//提示语
	var messagePop = function(str){
		$(".message-pop").fadeIn();
		$(".message-pop").children("span").html(str);
		setTimeout(function(){
			$(".message-pop").fadeOut();
		},2000)
	}
	   
</script>
<script id="accountTemplate" type="text/x-handlebars-template">	
{{#each this}}
	<li data-id="{{id}}" data-dc="{{dc}}" data-ifamortize="{{ifAmortize}}" data-accuntid="{{accuntId}}">{{accuntId}}&nbsp;{{parentName}}{{accountName}}</li>
{{/each}}
</script>	
</body>
</html>