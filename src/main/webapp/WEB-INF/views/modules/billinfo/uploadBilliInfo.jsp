<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传的票据-在线会计-芸豆会计</title>
<link href="${pageContext.request.contextPath}/static/css/reset.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/list2.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/wenxintishi.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/sangetanceng.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/autoAmortization.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/handlebar/handlebars-1.0.0.beta.6.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/js/uploadbillinfo.js" type="text/javascript"></script>
<style>
.list-wrapper .th-left {
	width: 500px;
}
</style>
<script type="text/javascript">
	//	项目路径
	var ctx = "${ctx}";
	//图片路径
	var image_path = "${image_path}";
</script>
</head>
<body>
	<div class="list-wrapper clearfix">
		<h2 class="h2-tit">上传的票据</h2>
		<div class="th clearfix">
			<div class="th-left text-left" style="left: 0; bottom: 0;">
				<span> <select id="sel_yn" class="sel_yn">
						<c:forEach var="per" items="${periodList}">
						<option value="${per}">${fn:substring(per, 0, 4)}年
									第${fn:substring(per, 4, 6)}期</option>	
						</c:forEach>	
				</select>
				</span> <span class="span_sta sta1"><input id="cancelFlag" type="checkbox" />显示作废票据</span>
				 <span class="span-tip" style="display: none;"><em class="ico-em"></em>标错反馈成功</span>
			</div>
			<div id="result_num" class="th-right2"><%-- 上传共${upload_num}张 --%></div>
		</div>
		
		<div class="lay imgs" style="display: block;">
			<ul class="ul-pj clearfix">
				
			</ul>
		</div>
	</div>

	<!-- 温馨提示 -->
	<div class="kindlyReminder dialog" style="display: none;">
		<input id="kindlyReminder-id" type="hidden">
		<h6 class="h6-kr">
			编辑<i class="bill-info-close-dialog"></i>
		</h6>
		<div class="mydialog">
			<div class="a">
				<a href="javascript:;" id="dialog-big-del">票据作废</a>
				<a href="javascript:;" id="dialog-big-period">票据跨期</a>
				<a href="javascript:;" id="dialog-big-add">新增凭证</a>
				<div style="display:hidden;">
					<form id="to-add-voucher-form-submit" method="get" action="${ctx}/voucher/tVoucher/enterVoucher">
							<input type="hidden" name="billInfos" id="to-add-voucher-form-billInfos" >
					</form>
				</div>
			</div>
		</div>
		<div class="imgbill">
			<img id="bill-info-img" src="static/images/temp_n4.jpg" width="794"
				height="422" alt="">
		</div>
	</div>
	
	<!-- 标错反馈弹层 -->
	<div class="layer wrong dialog" style="display: none;">
		<input id="wrong-id" type="hidden">
		<h5 class="h6-kr">
			标错反馈<i class="bill-info-close-dialog"></i>
		</h5>
		<ul class="ul-wrongreason clearfix">
			<li><input class="inp-check" type="radio" name="wrong">价格标错</li>
			<li><input class="inp-check" type="radio" name="wrong">付款方标错</li>
			<li><input class="inp-check" type="radio" name="wrong">摘要标错</li>
			<li><input class="inp-check" type="radio" name="wrong">发票类型标错</li>
			<li><input class="inp-check wrong-input" type="radio"
				name="wrong">其他<input class="inp-txt" type="text"></li>
		</ul>
		<div class="buttonW clearfix">
			<button class="cancel">取消</button>
			<button class="save wrong-save">保存</button>
		</div>
	</div>

	<!-- 票据跨期弹层 -->
	<div class="layer instrument dialog" style="display: none;">
		<input id="instrument-id" type="hidden">
		<h5 class="h6-kr">
			票据跨期<i class="bill-info-close-dialog"></i>
		</h5>
		<ul class="ul-NegoInstrument clearfix">
			<li class="clearfix"><label for="">票据编号</label><input
				type="text" class="inp-tx"></li>
			<li class="clearfix"><label for="">票据期数</label><input
				type="text" class="inp-val time-from" readonly="readonly"></li>
			<li class="clearfix"><label for="">跨期至</label><span
				class="sp-time"></span><input type="text"
				class="inp-val inp-col select-time"
				onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM'})"></li>
			<li class="clearfix text-left">提示：跨期票据跨期成功后不可恢复</li>
		</ul>
		<div class="buttonW clearfix">
			<button class="cancel">取消</button>
			<button class="save instrument-save">保存</button>
		</div>
	</div>

	<!-- 温馨提示-->
	<div class="layer reminder dialog" style="display: none;">
		<input id="reminder-id" type="hidden">
		<h5 class="h6-kr">
			温馨提示<i class="bill-info-close-dialog"></i>
		</h5>
		<ul class="ul-kindlyReminder clearfix">
			<li class="clearfix">是否作废此票据？</li>
			<li class="txt-col clearfix">作废后不可恢复</li>
		</ul>
		<div class="buttonW clearfix">
			<button class="cancel">取消</button>
			<button class="save reminder-save">保存</button>
		</div>
	</div>

	<div class="mask"></div>

	<div class="message-pop">
		<i class="i-mess"></i>
		<h4>信息提示</h4>
		<p>保存成功！！！</p>
	</div>
</body>
</html>