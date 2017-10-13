<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<head>
<title>系统消息-系统设置-芸豆会计</title>
<script src="${pageContext.request.contextPath}/static/jquery/jquery.js"	type="text/javascript"></script>
<script	src="${pageContext.request.contextPath}/static/jquery/jquery.form.js"	type="text/javascript"></script>
<script	src="${pageContext.request.contextPath}/static/jquery/jquery-jtemplates.js"	type="text/javascript"></script>
<script	src="${pageContext.request.contextPath}/static/jquery-validation/1.11.1/jquery.validate.js"	type="text/javascript"></script>
<script	src="${pageContext.request.contextPath}/static/jquery/jquery-ui.min.js"	type="text/javascript"></script>
<link	href="${pageContext.request.contextPath}/static/jquery/jquery-ui.css"	rel="stylesheet" type="text/css" />
<script	src="${pageContext.request.contextPath}/static/jquery/jquery.ui.datepicker-zh-CN.js"	type="text/javascript"></script>
<script	src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"	type="text/javascript"></script>
<link	href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.css"	type="text/css" rel="stylesheet" />
<link	href="${pageContext.request.contextPath}/static/bootstrap/css/page.css"	type="text/css" rel="stylesheet" />
<script type="text/javascript"	src="${pageContext.request.contextPath}/static/jquery/jquery.ztree.core-3.5.js"></script>
<link rel="stylesheet"	href="${pageContext.request.contextPath}/static/zTreeStyle/zTreeStyle.css"	type="text/css">
<link href="${pageContext.request.contextPath}/static/css/master_2.css"	type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/css/dialog.css"	type="text/css" rel="stylesheet" />

<script type="text/javascript">
$(document).ready(function(){
	$(".list_toggle_2 li").click(function(){
		$(this).find(".tit_t7").addClass("tit_t7_act");
		$(this).find(".con_t2").show();
		$(this).siblings().find(".con_t2").hide();
		$(this).siblings().find(".tit_t7").removeClass("tit_t7_act");
	})
	$("#tab_e6").css({
		"height":$(window).height() - 110,
		"overflow-y":"auto"
	});
});
</script>
</head>
<body>

	<div class="main-row"  >
		<div class="main_index3">
			<div class="pane_main5">
				<div class="t_pane_a1 radius_5">
					<div class="user_pane_r2" >
						<div class="hr20"></div>
						<ul class="nav nav-tabs" role="tablist">
							<li><a href="${ctx}/inspection/setup/sysUser/">部门职员</a></li>
							<li><a href="${ctx}/inspection/setup/sysCustomer/">客户管理</a></li>
						<li ><a href="${ctx}/inspection/setup/sysTel/">通讯录</a></li>
							<li ><a href="${ctx}/inspection/setup/sysMenu/">角色权限</a></li>
							<li class="active"><a href="${ctx}/inspection/setup/sysMessage/">系统消息</a></li>
						</ul>
						<div class="tab-content main5_con">
						<div class="tab-pane active" id="tab_e6">
							<div class="hr10"></div>
							<div class="no_data">
							<c:choose>
								<c:when test="${tsysNewsList.size()<1}">
								<div class="no_data">
									<img src="${pageContext.request.contextPath}/static/imgs/nodata.jpg">
								</div>
							</c:when>
							<c:otherwise>
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
							
							</c:otherwise>
							</c:choose>
							
							
							
							<div class="clearfix"></div>
							<div class="hr15"></div>
						</div>
					</div>
					<div class="hr20"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
	});
	
</script>
</body>
</html>