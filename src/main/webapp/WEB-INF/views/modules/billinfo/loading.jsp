<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>处理的票据-在线会计-芸豆会计</title>

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
<meta name="Author" content="author_bj designer_bj">

<link rel="stylesheet" href="${pageContext.request.contextPath}/static/zoomImage/css/zyImage.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/zoomImage/css/styles.css">

<script type="text/javascript">
 //	项目路径
 var ctx = "${ctx}";
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
</style>
<script type="text/javascript">
var ctxStatic = "/static";
</script>
</head>
<body>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/rotator.js"></script>
<script>
var total = ${total};
var uploadPeriod = '${uploadPeriod}';
var lastNum = 0;
 $(window).load(function () {
	 var timeid = window.setInterval(function(){
		 $.ajax({
		     type: 'POST',
		     url: ctx+'/billinfo/tDealBillInfo/getIntelligentAccountSchedule',
		     cache:false,  
		     dataType:'json',
		     data: {
		    		"uploadPeriod":uploadPeriod
		     } ,
		     success: function(data){
		    	 var dealNum = data;
		    	 var end = dealNum / total;
		    	 end = end * 100;
		    	 end = parseInt(end);
		    	 $("#rotator").rotator({
		    			"color":"#3498db",
		    			"starting":lastNum,
		    			"ending": end,
		    			"timer":100,
		    			callback: function () {
		    				
		    	        }
		    	    });
		    	 lastNum = end;
		    	 if(dealNum == total){
		    		 window.clearInterval(timeid);
		    		 //后台处理完毕
		    		 window.location.href= ctx +"/billinfo/tDealBillInfo/intelligentAccountResult?uploadPeriod="+uploadPeriod;
		    	 }
		     }
		});
	 },1000);
	 
	 
});
 </script>
	<div class="checkVoucher">
		<div class="message-pop">
			<span></span>
		</div>
		<h2 class="h2-tit">
			智能做账
		</h2>
		<div class="th clearfix">
			<div class="th-left text-left"><div class="left" style="position: relative;top:12px;">${fn:substring(uploadPeriod, 0, 4)}年${fn:substring(uploadPeriod, 4, 6)}期</div>								
			</div>
			<div class="th-right clearfix">				
				<!-- <span onclick="window.open('处理的_智能做账_保存后.html','_self')">保存</span> -->
				<span onclick="window.history.go(-1)">返回票据</span>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<div style="width:960px;background:#fff;margin:0 auto;">
		<table cellpadding="0" cellspacing="0" style="width:80%;margin:0 auto;">
			<tr>
				<td valign="top">
					<div id="rotator" style="width:300px;height:300px;">
					</div>
				</td>
				<td width="30"></td>
				<td valign="middle" align="left">
					我们正拼命为您生成凭证中，请稍等。。。<br /><br />
					点击返回票据会终止智能凭证
				</td>
			</tr>
		</table>
	</div>
</body>
</html>