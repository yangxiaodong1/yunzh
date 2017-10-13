<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>芸智慧财务系统</title>
<script src="${ctxStatic}/jquery/jquery-1.11.2.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_1.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/css/firstP.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/reset.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="main-row">
	<div class="hr10"></div>
	<div class="logo_c left"></div>
	<div class="top_action right">
		
		<div class="drop_company">
			<div class="tit_c2 left"><span class="ico_b">当前公司</span><em>北京极光剑一文化传媒有限公司</em></div>
			<div class="dropdown left">
				<a href="#" id="dropdownMenu1" data-toggle="dropdown">点此更换</a>
				<ul class="dropdown-menu pull-right" role="menu" aria-labelledby="dropdownMenu1">
					<li role="presentation"><a role="menuitem" tabindex="-1" href="#">北京极光剑一文化传媒有限公司</a></li>
					<li role="presentation"><a role="menuitem" tabindex="-1" href="#">北京奇虎科技有限公司</a></li>
					<li role="presentation"><a role="menuitem" tabindex="-1" href="#">北京万一商盟有限公司</a></li>
					<li role="presentation"><a role="menuitem" tabindex="-1" href="#">北京极光剑一文化传媒有限公司</a></li>
				</ul>
			</div>
		</div>
		<div class="link_home"> <a href="#" class="btn btn-default">工作台</a> </div>
		<div class="link-top left"><a href="#" class="btn btn_edit">账簿编辑</a><a href="#" class="btn btn_follow">跟进记录</a>|</div>
		<div class="drop_user left">
			<div class="face"><img src="static/images/img-1.png" width="33" height="33" /></div>
			<div class="dropdown"> <span id="dropdownMenu2" data-toggle="dropdown">殇璎珞</span>
				<ul class="dropdown-menu pull-right" role="menu" aria-labelledby="dropdownMenu2">
					<li role="presentation"><a role="menuitem" tabindex="-1" href="#">个人设置</a></li>
					<li role="presentation"><a role="menuitem" tabindex="-1" href="#">数据导入</a></li>
					<li role="presentation"><a role="menuitem" tabindex="-1" href="#">数据迁移</a></li>
					<li role="presentation"><a role="menuitem" tabindex="-1" href="#">退出</a></li>
				</ul>
			</div>
		</div>
		<div class="hr12"></div>
	</div>
	<div class="clearfix"></div>
	<div class="line_t1"></div>
	<div class="main-frame">
		<div class="main-side">
			<div class="sidebar">
				<div class="hr40"></div>
				<ul class="remove-style">
					<li class="a1 this"><a href="${pageContext.request.contextPath}/a/voucher/tVoucher/enterVoucher">新增票据</a></li>
					<li class="a2"><a href="${pageContext.request.contextPath}/a/billinfo/tBillInfo/billInfolist">票　据</a></li>
					<li class="a3"><a href="${pageContext.request.contextPath}/a/customer/tCustomer/list">查凭证</a></li>
					<li class="a4"><a href="${pageContext.request.contextPath}/a/books/Subsidiary/list">账　簿</a></li>
					<li class="a5"><a href="${pageContext.request.contextPath}/a/rpt/balance/list">报　表</a></li>
					<li class="a6"><a href="${pageContext.request.contextPath}/a/account/tAccount">设　置</a></li>
				</ul>
			</div>
		</div>
		<div class="main-con left">
			<div class="body-con">
				<div class="span-col-1">
					<div class="span_1 left">
						<div class="span_01"> <a href="${pageContext.request.contextPath}/a/voucher/tVoucher/enterVoucher"><span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span></a>
							<div class="cons">
								<div class="tit_d1">欢迎来到这里，体验我们的产品！</div>
								<div class="tit_h1"><a href="${pageContext.request.contextPath}/a/voucher/tVoucher/enterVoucher">新增凭证</a></div>
								<div class="tit_d1">点击这里新增凭证</div>
							</div>
							<div class="clearfix"></div>
						</div>
						
						<div class="span_02"> <span class="btn_warning mar-rig10">11月份凭证未完成</span><a href="${pageContext.request.contextPath}/a/billinfo/tBillInfo/billInfolist" class="btn btn-default btn_d1">点击检查</a> </div>
					  
					</div>
					<div class="span_2 right">
						<div class="tit_p1">票据数据</div>
					
						<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
						<em class="em-le"></em>
							<ol class="carousel-indicators">
								<li data-target="#carousel-example-generic" data-slide-to="0" class="active"><i class="year">2015</i>年<i class="month">11</i>月</li>
								<li data-target="#carousel-example-generic" data-slide-to="1"><i class="year">2015</i>年<i class="month">10</i>月</li>
							</ol>
							<em class="em-ri"></em>
							<div class="carousel-inner" role="listbox">
								<div class="item active">
									<div class="list_r1">
									<a href="${pageContext.request.contextPath}/a/billinfo/tBillInfo/billInfolist">
										<div class="cons">
											<ul class="remove-style">
												<li>
													<div class="tit_s1">上传票据</div>
													<div class="txt-n1 s_default"><span class="sp-upload cor1"><em>${uploadNotes}</em><i>/张</i></span> </div>
												</li>
												<li>
													<div class="tit_s1">已处理</div>
													<div class="txt-n1 s_success"><span class="sp-process cor2"><em>${processed}</em><i>/张</i></span> </div>
												</li>
												<li>
													<div class="tit_s1">已记账</div>
													<div class="txt-n1 s_primary"><span class="sp-accou cor3"><em>${accounted}</em><i>/张</i></span> </div>
												</li>
												<li>
													<div class="tit_s1">未记账</div>
													<div class="txt-n1 s_warning"><span class="sp-notAcc cor4"><em>${notAccount}</em><i>/张</i></span> </div>
												</li>
											</ul>
											<div class="clearfix"></div>
										</div>
										</a>
									</div>
								</div>
								<div class="item">
									<div class="list_r1">
										<div class="cons">
											<ul class="remove-style">
												<li>
													<div class="tit_s1">上传票据</div>
													<div class="txt-n1 s_default"><em>${uploadNotes}</em><span class="">/张</span> </div>
												</li>
												<li>
													<div class="tit_s1">已处理</div>
													<div class="txt-n1 s_success"><em>${processed}</em><span class="">/张</span> </div>
												</li>
												<li>
													<div class="tit_s1">已记账</div>
													<div class="txt-n1 s_primary"><em>${accounted}</em><span class="">/张</span> </div>
												</li>
												<li>
													<div class="tit_s1">未记账</div>
													<div class="txt-n1 s_warning"><em>${notAccount}</em><span class="">/张</span> </div>
												</li>
											</ul>
											<div class="clearfix"></div>
										</div>
									</div>
								</div>
							</div>
							<!-- Controls -->
							<div class="btn_months"> <a class="left carousel-control btn_p1" href="#carousel-example-generic" role="button" data-slide="prev">上一个</a> <a class="right carousel-control btn_p2" href="#carousel-example-generic" role="button" data-slide="next">下一个</a> </div>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
		<div class="clearfix"></div>
	</div>
</div>
<script type="text/javascript">

$(function(){
	/* var winH = $(window).height();
	$('.main-content').css("padding-bottom",winH-522-20); */
	var monthNum = $(".month").text();
	var monthBef;
	var monthAft;
	var dataStr = {};
	
	var year;
	
	$(".em-le").bind("click",function(){
		monthNum = parseInt($(".month").text());
		monthBef = monthNum-1;
		if(monthBef == 0){
			monthBef = 12;
			year =  parseInt($(".year").text());
			year = year - 1;
			$(".year").text(year)
		}
		if(monthBef > 0 && monthBef < 10){
			monthBef = "0"+monthBef;
		}
		$(".month").text(monthBef);
		var url ="voucherIndexJson";
		$.ajax({
			url:url,
			type:"POST",
			async:false,
			data:{time:$(".year").text()+monthBef},
			success:function(d){
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
	$(".em-ri").bind("click",function(){
		monthNum = parseInt($(".month").text());
		monthAft = monthNum+1;
		if(monthAft == 13){
			monthAft = 1;
			year =  parseInt($(".year").text());
			year = year + 1;
			$(".year").text(year)
		}
		if(monthAft > 0 && monthAft < 10){
			monthAft = "0"+monthAft;
		}
		$(".month").text(monthAft);
		var url ="voucherIndexJson";
		$.ajax({
			url:url,
			type:"POST",
			async:false,
			data:{time:$(".year").text()+monthAft},
			success:function(d){
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
	$(".aclick").click(function(e){
		  e.stopPropagation();
	})
	
})


$winH=$(window).height();
$mainH=$winH-80;
$marTop=($mainH-426)/2;
$(".main-frame .main-con .body-con").height(function(){
	return $mainH;
})
$(".main-frame .main-con .body-con").css({
	"padding-top":$marTop
})
</script>
</body>
</html>
