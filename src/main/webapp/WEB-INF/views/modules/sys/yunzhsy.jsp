<%@ page contentType="text/html;charset=UTF-8" %><%@ include file="/WEB-INF/views/include/taglib.jsp"%><%@page import="com.thinkgem.jeesite.modules.sys.entity.User"%><%@page import="com.thinkgem.jeesite.modules.sys.utils.UserUtils"%> 
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>工作平台</title>
<script src="${ctxStatic}/jquery/jquery-1.11.2.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_2.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/bootstrap/js/bootstrap-hover-dropdown.js" type="text/javascript"></script>
</head>
<body style="overflow-y:hidden;">
<div class="main-row">
	<div class="top_gzt">
		<div class="logo_c left"></div>
		<div class="top_action right">
			<div class="top_nav left">
				<ul class="remove-style">
				  <li>  </li> 
					<li class="n_1"><a href="javascript:void(0)" attrurl="${pageContext.request.contextPath}/a/yunzhmain"><span class="btn_icos"></span>首 页</a></li>
					<li class="n_2"><a href="javascript:void(0)" attrurl="${pageContext.request.contextPath}/a/workterrace/chargeToAccount"><span class="btn_icos"></span>工作台</a></li>
					
					<%
					User currentUser = UserUtils.getUser();
					//if (currentUser.getRoleNames().contains("记账公司管理员")){
						if (currentUser.getRoleNames().contains("记账公司管理员")){
					 %>
					<li class="n_3"><a href="javascript:void(0)" attrurl="${ctx}/inspection/workstatistics/tWorkGather"><span class="btn_icos"></span>工作统计</a></li>
					
					<li class="n_4"><a href="javascript:void(0)" attrurl="${ctx}/inspection/setup/sysUser"><span class="btn_icos"></span>系统设置</a></li>
					<%
					}
					 %>
					 <!-- 
					<li class="n_5"><span class="btn_icos"></span><a href="${ctx}/sys/user/voucherIndexx" >在线会计</a></li>
				 -->
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="top_tool left">
				<ul class="remove-style">
					<li class="btn_t01"><a href="javascript:void(0)"><span class="btn_icos"></span>工具下载</a></li>
					<li class="btn_t02"><a href="javascript:void(0)" attrurl="${ctx}/feedback/list"><span class="btn_icos"></span>意见反馈</a>&nbsp;&nbsp;|&nbsp;&nbsp;</li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="drop_user left">
				<div class="face" id='facename'>
				<c:if test="${currentUser.photo!=null&&currentUser.photo!=''}">
			  <!--  <img src="${ctx}/sys/user/imageGet?fileName=${currentUser.photo}" width="88" height="88" class="radius_imgs" />
				 -->
				 <img src="${ctx}/sys/user/imageGet?fileName=${currentUser.photo}" width="33" height="33" class="radius_imgs" />
				</c:if>
				<c:if test="${currentUser.photo==null||currentUser.photo==''}">
				<img src="${ctxStatic}/images/img-1.png" width="33" height="33" /> 
				</c:if>
				</div>
				<!--  
				<div class="dropdown"> <span id="dropdownMenu2" data-toggle="dropdown">${currentUser.name}</span>
					<ul class="dropdown-menu pull-right" role="menu" aria-labelledby="dropdownMenu2">
						<li role="presentation"><a role="menuitem" tabindex="-1" href="#" target="mainFrame" attrurl="${ctx}/sys/user/info"> 个人设置</a></li>
						<li role="presentation"><a role="menuitem" tabindex="-1" href="${pageContext.request.contextPath}/a/logout">退出</a></li>
					</ul>
				</div>
				-->
				
				<div class="dropdown"> <span id="dropdownMenu2" data-toggle="dropdown" data-hover="dropdown" data-delay="100" data-close-others="false" class="upname">
				<c:choose >
   							 <c:when test="${fn:length(currentUser.name)>3}">
							       ${fn:substring(currentUser.name, 0, 3)}...
							    </c:when>
								 <c:otherwise>
								 ${currentUser.name}
							    </c:otherwise>
							</c:choose>
				</span>
					<ul class="dropdown-menu pull-right">
					<li role="presentation"><a role="menuitem" tabindex="-1" href="#"  attrurl="${ctx}/sys/user/info"> 个人设置</a></li>
					<!--	<li role="presentation"><a role="menuitem" tabindex="-1" href="#">个人设置</a></li>
					 	<li role="presentation"><a role="menuitem" tabindex="-1" href="#">数据导入</a></li>
						<li role="presentation"><a role="menuitem" tabindex="-1" href="#">数据迁移</a></li>	-->
						<li role="presentation"><a role="menuitem" tabindex="-1" href="${pageContext.request.contextPath}/a/logout">退出</a></li>
						
					</ul>
				</div>	
			</div>

		</div>
		<div class="clearfix"></div>
	</div>
	<div class="frame_panel">
	<c:if test="${i=='1'}">
		<iframe id="id_iframe" src="${pageContext.request.contextPath}/a/workterrace/chargeToAccount" frameborder='0' scrolling='yes' width='100%' height=''></iframe>
		</c:if>
		<c:if test="${i!='1'}">
		<iframe id="id_iframe" src="${pageContext.request.contextPath}/a/yunzhmain" frameborder='0' scrolling='yes' width='100%' height=''></iframe>
		</c:if>
		<div class="hr20"></div>

	</div>
</div>
<script type="text/javascript">
//子窗口调用此方法
function enterCustomer(idd){
	var url="${ctx}/customer/tCustomer/setSession";
	window.location.href=url+"?sessionCustomerId="+idd;
}
function updateusername(username){
	//alert(username.length);
	var usernamenew='';
	if(username.length>3){
		usernamenew=username.substring(0,3)+"...";
		//alert(usernamenew);
	}else{
		usernamenew=username;
	}
	
	$(".upname").text(usernamenew);
}
function updatefilename(filename){
	var filename=filename;
	var img="<img src='${ctx}/sys/user/imageGet?fileName="+filename+"' width='33' height='33' class='radius_imgs' />";
    //$("#facename").html(<img src="${ctx}/sys/user/imageGet?fileName="+filename width="88" height="88" class="radius_imgs" />);
   $("#facename").html(img);
	
}
$(function(){
	var winH=$(window).height();
	//var frameH=winH-130;
	var frameH=winH-60;
	var frame=$('.frame_panel iframe');
	frame.height(frameH);
	$('[attrurl]').on('click',function(event){
		var frameurl=$(this).attr('attrurl');
		frame.attr('src',frameurl);
	})
})

//鼠标离开下拉菜单隐藏
	$('.dropdown-toggle').dropdownHover();
</script>

<script type="text/javascript">
$(function(){
    //iframe加载完成后执行
    $("#id_iframe").load(function(){
        var $frm = $(this);
        var $contents = $frm.contents();
       var title= $contents.find("title").text();
        $("title").text(title);
    });
});

</script>
</body>
</html>