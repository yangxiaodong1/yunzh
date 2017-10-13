<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<head>
<title>系统消息</title>
<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.11.2.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-ui.min.js"></script>
<link href="${pageContext.request.contextPath}/static/jquery/jquery-ui.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/css/master_2.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/remove_sys.css">
<link href="${pageContext.request.contextPath}/static/css/dialog.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/bootstrap/css/page.css" type="text/css" rel="stylesheet" />

<script type="text/javascript">
$(document).ready(function(){
	$(".list_toggle_2 li").click(function(){
		$(this).find(".tit_t7").addClass("tit_t7_act");
		$(this).find(".con_t2").show();
		$(this).siblings().find(".con_t2").hide();
		$(this).siblings().find(".tit_t7").removeClass("tit_t7_act");
	})
});
</script>
<style>
body{background:#d8e1e8;}
.pane_main5{width:1110px;margin:0 auto;}
.tips_nodata{text-align:center;}
.main5_con{border-top:1px #dcdcdc solid;}
.tit_channel_1 .btn-default2{right:38px;}
</style>

</head>
<body>

	<div class="main-row"  >
		<div class="main_index3">
			<div class="pane_main5">			

				<div class="t_pane_a1 radius_5"  >
					<div class="hr10"></div>
					<div class="tit_channel_1">历史所有消息
						<!-- <button onclick="back()" class="btn btn-default2"><i>查看历史所有消息</i></button> -->
					</div>
					<c:if test="${tsysNewsList.size()<1}">
					<div class="tips_nodata" >
						<img src="${pageContext.request.contextPath}/static/imgs/nodata.jpg" >
					</div></c:if><c:if test="${tsysNewsList.size()>0}">
					<div class="user_pane_r2" >
						<div class="hr20"></div>
						
						<div class="tab-content main5_con">
						<div class="tab-pane active" id="tab_e6">
							<div class="hr10"></div>
							<div class="list_toggle_2">
								<ul class="remove-style">
								<c:forEach items="${tsysNewsList}" var="tsysnews">
									<li>
										<div class="tit_t7"><span class="icos"></span>${tsysnews.title}</div>
										<div class="con_t2">
											<div class="inner">${tsysnews.content}
											</div>
										</div>
									</li>
								</c:forEach>
								</ul>
							</div>
							<div class="clearfix"></div>
							<div class="hr15"></div>
						</div>
					</div>
					<div class="hr20"></div>
				</div></c:if>
			</div>
			<div class="hr20"></div>

		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$(".tips_nodata").height(function(){
		return parseInt($(window).height()-80);	
	})
	var tbHeight=$(".tips_nodata").height();
	var dataImg=$(".tips_nodata img").height();
	$(".tips_nodata img").css({
		"marginTop":parseInt((tbHeight-dataImg)/2-40)
	})
});

	function back(){
		 window.location.href="${ctx}/inspection/setup/sysMessage/findListAll";
	}
</script>
</body>
</html>