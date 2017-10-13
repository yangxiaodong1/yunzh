<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/static/css/firstP.css"
	rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/reset.css"
	rel="stylesheet" type="text/css">
<script
	src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js"
	type="text/javascript"></script>
<title>首页-在线会计-芸豆会计</title>
</head>
<body>
	<div class="fir-wrapper">
		<div class="main-content clearfix">
			<div class="layab">
				<a href="${pageContext.request.contextPath}/a/voucher/tVoucher/enterVoucher" id="add1">
					<div class="d-top"></div> <span class="sp-jia"></span>
					<p class="p-txt p-wel">欢迎来到这里，体验我们的产品！</p>
					<p class="p-txt p-new">新增凭证</p>
					<p class="p-txt p-click">点击这里新增凭证</p>
				</a>
				<c:choose>
					<c:when test="${firstLoad}">
					<a href="#" id="check">
						<div class="bott">
							<i></i><span>${fn:substring(user_period, 4, 6)}月份凭证未完成!</span><a class="aclick"
								href="${pageContext.request.contextPath}/a/voucherexp/tVoucherExp/periodShow">点击检查</a>
						</div>
					</a>
					</c:when>
					<c:otherwise>
					<a href="${pageContext.request.contextPath}/a/account/tAccount/balan" id="inbalancee">
						<div class="bott">
							<i></i><span>请先设置初始余额!</span>
						</div>
					</a>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="layc">
				<h4 class="tit">票据数据</h4>
				<div class="time">
					<em class="em-le"></em><span><i class="year">${fn:substring(user_period, 0, 4)}</i>年<i
						class="month">${fn:substring(user_period, 4, 6)}</i>月</span><em class="em-ri"></em>
				</div>
				<a href="${pageContext.request.contextPath}/a/billinfo/tBillInfo/billInfolist" id="blistt">
					<div class="txt">
						<span>上传发票</span> <span>已处理</span>
					</div>
					<div class="cont">
						<span class="sp-upload cor1"><em>${uploadNotes}</em><i>/张</i></span>
						<span class="sp-process cor2"><em>${processed}</em><i>/张</i></span>
					</div>
					<div class="txt">
						<span>已记账</span> <span>未记账</span>
					</div>
					<div class="cont">
						<span class="sp-accou cor3"><em>${accounted}</em><i>/张</i></span>
						<span class="sp-notAcc cor4"><em>${notAccount}</em><i>/张</i></span>
					</div>
				</a>
			</div>
		</div>
	</div>
	<!--页面脚本区S-->
	<!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->
	<script>
		$(function() {
			/* var winH = $(window).height();
			$('.main-content').css("padding-bottom",winH-522-20); */
			var monthNum = $(".month").text();
			var monthBef;
			var monthAft;
			var dataStr = {};

			var year;

			$(".em-le").bind("click", function() {
				monthNum = parseInt($(".month").text());
				monthBef = monthNum - 1;
				if (monthBef == 0) {
					monthBef = 12;
					year = parseInt($(".year").text());
					year = year - 1;
					$(".year").text(year)
				}
				if (monthBef > 0 && monthBef < 10) {
					monthBef = "0" + monthBef;
				}
				$(".month").text(monthBef);
				var url = "voucherIndexJson";
				$.ajax({
					url : url,
					type : "POST",
					async : false,
					data : {
						time : $(".year").text() + monthBef
					},
					success : function(d) {
						dataStr.uploadNotes = d.uploadNotes;
						dataStr.processed = d.processed;
						dataStr.accounted = d.accounted;
						dataStr.notAccount = d.notAccount;
					}
				})
				$(".sp-upload em").text(dataStr.uploadNotes);
				$(".sp-process em").text(dataStr.processed);
				$(".sp-accou em").text(dataStr.accounted);
				$(".sp-notAcc em").text(dataStr.notAccount);
			})
			$(".em-ri").bind("click", function() {
				monthNum = parseInt($(".month").text());
				monthAft = monthNum + 1;
				if (monthAft == 13) {
					monthAft = 1;
					year = parseInt($(".year").text());
					year = year + 1;
					$(".year").text(year)
				}
				if (monthAft > 0 && monthAft < 10) {
					monthAft = "0" + monthAft;
				}
				$(".month").text(monthAft);
				var url = "voucherIndexJson";
				$.ajax({
					url : url,
					type : "POST",
					async : false,
					data : {
						time : $(".year").text() + monthAft
					},
					success : function(d) {
						dataStr.uploadNotes = d.uploadNotes;
						dataStr.processed = d.processed;
						dataStr.accounted = d.accounted;
						dataStr.notAccount = d.notAccount;
					}
				})
				$(".sp-upload em").text(dataStr.uploadNotes);
				$(".sp-process em").text(dataStr.processed);
				$(".sp-accou em").text(dataStr.accounted);
				$(".sp-notAcc em").text(dataStr.notAccount);
			})

			//防止点击事件冒泡事件
			$(".aclick").click(function(e) {
				e.stopPropagation();
			})

		})
	</script>
	<script type="text/javascript">
	   $(function(){
		   //点击新增票据调用父类窗口的函数
		  $("#add").on("click",function(){
			   var rel="link_20";
			   var text="新增票据";
			   var famsrc="${pageContext.request.contextPath}/a/voucher/tVoucher/enterVoucher";
			  
			   $(window.parent.addTicket(rel,text,famsrc));
			   window.parent.$("iframe").last().show();
		   });
		  
		   //点击发票信息列表调用父类中的窗口，生成一个iframe
		  $("#blist").on("click",function(){
			   var rel="link_02";
			   var text="票据";
			   var famsrc="${pageContext.request.contextPath}/a/billinfo/tBillInfo/billInfolist";
			   
			   $(window.parent.addTicket(rel,text,famsrc));
			   window.parent.$("iframe").last().show();
		   });
		   //点击检查调用父类窗口进入票据信息列表中
		  $("#check").on("click",function(){
			   var rel="link_02";
			   var text="票据";
			   var famsrc="${pageContext.request.contextPath}/a/billinfo/tBillInfo/billInfolist";
			   
			   $(window.parent.addTicket(rel,text,famsrc));
			   window.parent.$("iframe").last().show();
		   });
		   //点击初始化余额进入到父类初始余额
		  $("#inbalance").on("click",function(){
			   var rel="link_13";
			   var text="财务初始余额";
			   var famsrc="${pageContext.request.contextPath}/a/account/tAccount/balan";
			   
			   $(window.parent.addTicket(rel,text,famsrc));
			   window.parent.$("iframe").last().show();
		   });
		   
		   
	   })
	
	</script>
	<!--页面脚本区E-->
</body>
</html>