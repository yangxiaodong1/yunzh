<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>结账-在线会计-芸豆会计</title>
<link href="${pageContext.request.contextPath}/static/css/reset.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/accountSettle.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/checkCredentials.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/sangetanceng.css" rel="stylesheet" type="text/css">

<script src="${pageContext.request.contextPath}/static/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/taxbase/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript">
 //	项目路径
 var ctx = "${ctx}";
</script>
<script src="${pageContext.request.contextPath}/static/js/settleAccount.js" type="text/javascript"></script>
<style>
.message-pop{display:none;position:fixed;top:40px;width:100%;text-align:center;z-index:1000;}
.message-pop span{background:#4f9de8;padding:5px 75px;color:#fff;}
#cancel {margin-right:20px;}
.tbvoucher .body {overflow-y:auto; margin: 10px auto;}
</style> 
</head>
<body>
<!-- 结账 -->
<div class="settle-wrapper" style="display:none;">
		<div class="message-pop">
			<span></span>
		</div>
		<div class="tb">

			<div class="cont">
				<div class="txtbanner">
					<p>凭证都已经自动生成，请确认无误后再结账！</p>
					<button id="sure-settle-account">确认结账</button>
					<button id="cancel" onclick="returnFun()">取消</button>
				</div>
				<div class="model">
					<ul id="settle-account-ul" class="clearfix">

					</ul>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 查看凭证 -->
	<div class="bg" style="background:#000;opacity:0.5;width:100%;height:100%;position:absolute;top:0;left:0;display:none;"></div>
	<div class="tbvoucher dialog voucher-current" style="display:none;z-index:999;">

				<h5 class="h6-kr">录入凭证<i class="bill-info-close-dialog"></i></h5>
				<div class="body">
					<div class="voucher-top clearfix">
				       <div class="vou-left">
					      	记字第<input class="ui-spinbox" type="text"  value="" readonly="readonly">号
					   </div>
					   <div class="date-wrap">
			            	日期 <input type="text" class="ui-datepicker-input ui-input ui-datepicker-input Wdate" id="vch-date" value="${date}" onFocus="WdatePicker({minDate:'%y-{%M-1}-01',maxDate:'%y-{%M-1}-%ld'})" readonly="readonly">
					   </div>
					   <!-- 
					   <div class="vou-right">
					      	附单据<input type="text"  value="0" readonly="readonly">张
					   </div>
					    -->
			    	</div> 
					<table class="tab-voucher min-height-s" cellpadding="0" cellspacing="0">
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
							<tr>
								<td class="col-operate"></td>
						        <td colspan="2" class="col-total">合计：<span id="capAmount"></span></td>
						        <td class="col-debite col-debite1">
						        	<div class="cell-val debit-total" id="debit-total"></div>
						        </td>
						        <td class="col-credit col-credit1">
						        	<div class="cell-val credit-total" id="credit-total"></div>
						        </td>
						        <td class="col-delete"></td>
							</ttr>
					      	
					    </tfoot>
					</table>
					<p class="make-person">制单人：<em>${user.name}</em></p>
					<!-- <div class="savevoucher-list">保存凭证</div> -->
				</div>
			</div>	
<script type="text/javascript">

var messagePop = function(str){
	$(".message-pop").fadeIn();
	$(".message-pop").children("span").html(str);
	setTimeout(function(){
		$(".message-pop").fadeOut();
	},2000)
}
//返回上一页
function returnFun(){
	history.go(-1);
}
</script>
</body>
</html>