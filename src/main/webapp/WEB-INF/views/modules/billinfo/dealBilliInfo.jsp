<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>处理的票据-在线会计-芸豆会计</title>

<link href="${pageContext.request.contextPath}/static/css/reset.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/list2.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/wenxintishi.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/sangetanceng.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/autoAmortization.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/bootstrap/css/font.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/zoomImage/css/zyImage.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/static/zoomImage/css/styles.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/iviewer/jquery.iviewer.css" rel="stylesheet" type="text/css" />

<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/js/dealbillinfo.js" ></script>
<script src="${pageContext.request.contextPath}/static/jquery/toggle.js"></script>
<script src="${pageContext.request.contextPath}/static/jquery/jquery-ui.min.js" type="text/javascript" ></script>
<script src="${pageContext.request.contextPath}/static/iviewer/jquery.mousewheel.min.js" type="text/javascript" ></script>
<script src="${pageContext.request.contextPath}/static/iviewer/jquery.iviewer.js"  type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/iviewer/zyImage.js" type="text/javascript" ></script>
<!-- 日期选择插件 -->
<script src="${pageContext.request.contextPath}/static/jquery/jquery.ui.datepicker-zh-CN.js"></script>
<link href="${pageContext.request.contextPath}/static/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/static/css/dialog.css" rel="stylesheet" type="text/css"  />
<style>
	.list-wrapper .th-left{width:500px;}
	.viewer{position: relative;width:600px;height:330px;}
	.ui-widget-overlay{background-color: #000;opacity: 0.5;}
</style>
<script type="text/javascript">
 //	项目路径
 var ctx = "${ctx}";
 //静态资源路径
 var ctxStatic = "${ctxStatic}";
 //图片路径
 var image_path = "${image_path}";
 // 查询发票列表返回的数据结果
 var billinfosData;
 // 出租车类 票据信息
 var taxiMap ;
 //打开编辑页面时发票id-- 处于大图展示状态
 var editBillId;
 //打开编辑页面时发票信息-- 处于大图展示状态
 var editBillBean;
</script>
</head>
<body>
<div class="list-wrapper clearfix">
		<h2 class="h2-tit">处理的票据</h2>
			<div class="th clearfix">
				<div class="th-left text-left" style="left:0;bottom:0;">
					<span>
						<select id="sel_yn" class="sel_yn">		
						<c:forEach var="per" items="${periodList }">
						<option value="${per}">${fn:substring(per, 0, 4)}年
									第${fn:substring(per, 4, 6)}期</option>	
						</c:forEach>		 		
	             		</select>
					</span>
					<span class="span_sta sta1"><input name="bill-status" class="bill-status" value="!= '3'" type="radio" checked />显示未做账票据</span>
					<span class="span_sta sta2"><input name="bill-status" class="bill-status" value="= '3'" type="radio" />显示已做账票据</span>
					<span class="span_sta sta3"><input name="bill-status" class="bill-status" value="= '4'" type="radio" />显示作废票据</span>
			    	<span class="span-tip" style="display: none;"><em class="ico-em"></em>标错反馈成功</span>
				</div>
				<div class="th-right clearfix">
					<span id="linked-notes">联查票据</span>
					<span id="add-voucher">新增凭证</span>
					<span id="intelligentAccount">智能做账</span>
					<div style="display:hidden;">
						<form id="to-add-voucher-form-submit" method="get" action="${ctx}/voucher/tVoucher/enterVoucher">
							<input type="hidden" name="billInfos" id="to-add-voucher-form-billInfos" >
						</form>
					</div>
				</div>
			</div>
		<div class="lay imgs" style="display:block;"><!--none-->
			<div id="error_billinfo_div" class="img_lista">
				<div class="tit_biga pad_t_b5 mar-bot10">
					<div class="left">
						<h2 class="text-left">问题票据<span>3</span></h2>
					</div>
					<div class="right">
						<span class="glyphicon glyphicon-chevron-up" aria-hidden="true"></span>
					</div>
					<div class="clear"></div>
				</div>
				<ul id="error_billinfo" class="ul-pj clearfix">

				</ul>
			</div>
			<div id="deal_billinfo_div" class="img_lista">
				<div class="tit_biga pad_t_b5 mar-bot10">
					<div class="left">
						<h2 class="text-left">票据<span>3</span></h2>
					</div>
					<div class="right">
						<span class="glyphicon glyphicon-chevron-up" aria-hidden="true"></span>
					</div>
					<div class="clear"></div>
				</div>
				<ul id="deal_billinfo" class="ul-pj clearfix">
				
				</ul>
			</div>
		</div>
	</div>
<!-- 编辑 -->
<div class="kindlyReminder dialog" style="display:none;">
	<h6 class="h6-kr">编辑<i class="bill-info-close-dialog"></i></h6>
	<div class="mydialog">
		<div class="a"><a href="javascript:;" id="dialog-big-del">票据作废</a><a href="javascript:;" id="dialog-big-period">票据跨期</a><a href="javascript:;" id="dialog-big-add">新增凭证</a></div>
	</div>
	<div class="imgbill">

<!--img-->
<div class="parent_container">
	<div id="panImage" class="pan_image"></div>
</div>
	</div>
	<div class="rightSlide">
		<span class="spanbut spDeta sp-detailedInformation"><i>详细信息</i></span>
		<div class="cont">
			<div class="contdetail cont-IntelligentAccount">
			
			</div>
			<div class="contdetail cont-detailedInformation">
				<input id="dillog-big-bill-info-id" type="hidden">
				<input id="dillog-big-bill-info-number" type="hidden">
				<input id="dillog-big-bill-info-hasSave" type="hidden">
				<table class="tabdetail" cellspacing = "0" cellpadding = "0">
					<tr><td width="20%"><label for="">发票类型</label></td>
					<!-- <td width="80%"><div class="ipt_bj"><input id="bill-info-type" type="text" class="sta_ipta" readonly/><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span></div></td>
					 -->
					 	<td id="bill-info-tBIdName" width="80%">
							<div class="ipt_bj">
								<select id="bill-info-type">
									<c:forEach var="type" items="${typeList}">
										<option value="${type.id}">${type.billTypeName}</option>
									</c:forEach>
								</select>
								<span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span>								
							</div>
						</td>
					</tr>
					<tr><td width="20%"><label for="">发票号码</label></td><td width="80%"><div class="ipt_bj"><input id="bill-info-invoiceCode" type="text" class="sta_ipta" readonly/><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span></div></td></tr>
					<tr><td width="20%"><label for="">发票代码</label></td><td width="80%"><div class="ipt_bj"><input id="bill-info-invoiceNumber" type="text" class="sta_ipta" readonly/><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span></div></td></tr>
					<tr><td width="20%"><label for="">开票日期</label></td>
						<!-- <td width="80%"><div class="ipt_bj"><input id="bill-info-signDate" type="text" class="sta_ipta" readonly/><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span></div></td>
						 -->
						 <td width="80%">
								<div class="ipt_bj">
									<input id="bill-info-signDate" type="text" value="" class="sta_ipta" readonly/>
									<span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span>
								</div>
						</td>
					</tr>
					
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
			<li class="clearfix text-left">提示：跨期票据跨期成功后不可恢复</li>
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
			<li class="clearfix">是否作废此票据？</li>
			<li class="txt-col clearfix">作废后不可恢复</li>
		</ul>
		<div class="buttonW clearfix">
			<button class="cancel">取消</button><button class="save reminder-save">保存</button>
		</div>
	</div>	

	<!-- 票据信息反馈-->
	<div class="layer feedback dialog" style="display:none;">
		<input id="feedback-id"  type="hidden">
		<h5 class="h6-kr">票据信息反馈<i class="bill-info-close-dialog"></i></h5>
		<ul class="ul-kindlyReminder clearfix">
			<li id="feedback-error" class="clearfix"><span id="feedback-msg">发票号码</span>信息错误反馈</li>
		</ul>
		<div class="buttonW clearfix">
			<button id="feedback-save" class="save feedback-save" style="float:none;">提交</button>
		</div>
	</div>	

<div class="mask"></div>

<script>
$winH=$(window).height();
$mainH2=$winH-145;
$(".imgs").height(function(){
	return $mainH2;
})

/**复选**/
var staNow=$(".sta1 input");
staNow.on('click',function(){
	if(staNow.is(':checked')){
		console.info(1);
	}else{
		console.info(2);
	}
})
var staNow2=$(".sta2 input");
staNow2.on('click',function(){
	if(staNow2.is(':checked')){
		console.info(3);
	}else{
		console.info(4);
	}
})
var staNow3=$(".sta3 input");
staNow3.on('click',function(){
	if(staNow3.is(':checked')){
		console.info(5);
	}else{
		console.info(6);
	}
})

//折叠
$(".img_lista").each(function(){
	var parentDiv=$(this);
		parentDiv.find(".glyphicon").toggle(function(){
		parentDiv.find(".glyphicon").removeClass("glyphicon-chevron-up").addClass("glyphicon-chevron-down");
		parentDiv.find(".ul-pj").hide();
	},function(){		
		parentDiv.find(".glyphicon").removeClass("glyphicon-chevron-down").addClass("glyphicon-chevron-up");
		parentDiv.find(".ul-pj").show();
	})
})


//instrument
$(".dialog-link").on("click",function(){
	$(".instrument").show();
	$(".mask").show();
})

/**详细信息**/
$(".sp-detailedInformation").toggle(function(){
	var data = editBillBean;
	$("#dillog-big-bill-info-id").val(data.id);
	$("#dillog-big-bill-info-number").val(data.invoiceNumber);
	
	
	$("#bill-info-type").val(data.tBId).data("field","t_b_id");
	$("#bill-info-invoiceCode").val(data.invoiceCode).data("field","invoice_code");
	$("#bill-info-invoiceNumber").val(data.invoiceNumber).data("field","invoice_number");
	$("#bill-info-signDate").val(data.signDate).data("field","sign_date");
	$("#bill-info-payName").val(data.payName).data("field","payName");
	$("#bill-info-abstractMsg").val(data.abstractMsg).data("field","abstract_msg");
	$("#bill-info-amount").val(data.amount).data("field","amount");
	$("#bill-info-tax").val(data.tax).data("field","tax");
	$("#bill-info-totalPrice").val(data.totalPrice).data("field","total_price");
	$("#bill-info-recieveName").val(data.recieveName).data("field","recieve_name");
	$("#bill-info-recieveTaxIdentificationNumber").val(data.recieveTaxIdentificationNumber).data("field","recieve_tax_identification_number");
	//票据顺序号码 todo
	$("#bill-info-dValue").val(data.dValue).data("field","d_value");
	
	$(".rightSlide").animate({right:"0"}).removeClass("bor27bc9e");
	$(this).removeClass("spDeta").addClass("spDetaOn").css("border-right","1px solid #fff");
	$(".sp-IntelligentAccount").removeClass("spInteOn").addClass("spInte").css("border-right","1px solid #3d8ae2");
	$(".cont-IntelligentAccount").hide();
	$(".cont-detailedInformation").show();
},function(){
	$(".rightSlide").animate({right:"-392"});
	$(this).removeClass("spDetaOn").addClass("spDeta").css("border-right","1px solid #fff");
	$(".sp-IntelligentAccount").removeClass("spInteOn").addClass("spInte").css("border-right","1px solid #fff");
})

var editFlag = false;
/**Tags**/
$(".ipt_bj").each(function(){
	var parentC=$(this).parent();
	$(this).hover(function(){
		parentC.find(".glyphicon").css("visibility","visible");
		//edit
		parentC.find(".glyphicon-pencil").on("click",function(){
			parentC.find(".sta_ipta").addClass("ipta1").removeAttr("readonly");
			parentC.find(".glyphicon").css("visibility","hidden");
			editFlag = true;
		})
		//feedback
		parentC.find(".glyphicon-paperclip").on("click",function(){
			$("#feedback-msg").text($(parentC).prev().text());
			$(".kindlyReminder").css("opacity","0.7");
			$(".feedback").show();
		})
	},function(){
		if(editFlag){
			editFlag = false;
			$.ajax({
			     type: 'POST',
			     url: ctx+'/billinfo/tDealBillInfo/uploadBill',
			     cache:false,  
			     dataType:'json',
			     data: {
			    		"id":$("#dillog-big-bill-info-id").val(),
			    		"field":parentC.find(".sta_ipta").data("field"),
			    		"value":parentC.find(".sta_ipta").val()
			     } ,
			     success: function(data){
			    	if(data.suc){
			    		editBillBean = data.bean;
			    		alert("修改成功");
			    	}else{
			    		alert("修改失败");
			    	}
			     }
			});
		}
		parentC.find(".glyphicon").css("visibility","hidden");
		parentC.find(".sta_ipta").removeClass("ipta1").attr("readonly",true);
	})
})


//close feedback
$(".feedback .bill-info-close-dialog").on("click",function(){
	$(".kindlyReminder").css("opacity","1");
})

//UI date20160405
$("#bill-info-signDate").datepicker({
	onSelect:function(dateText,inst){
		$.ajax({
		     type: 'POST',
		     url: ctx+'/billinfo/tDealBillInfo/uploadBill',
		     cache:false,  
		     dataType:'json',
		     data: {
		    		"id":$("#dillog-big-bill-info-id").val(),
		    		"field":$("#bill-info-signDate").data("field"),
		    		"value":$("#bill-info-signDate").val()
		     } ,
		     success: function(data){
		    	if(data.suc){
		    		editBillBean = data.bean;
		    		alert("修改成功");
		    	}else{
		    		alert("修改失败");
		    	}
		     }
		});
		
		
		
	}
});
</script>
</body>
</html>