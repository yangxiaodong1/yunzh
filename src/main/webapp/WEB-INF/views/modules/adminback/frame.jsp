<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@page import="com.thinkgem.jeesite.modules.sys.entity.User"%> 
<%@page import="com.thinkgem.jeesite.modules.sys.utils.UserUtils"%> 
<%@page import="com.thinkgem.jeesite.modules.power.service.TYdSysMenuService"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>芸豆会计－管理后台</title>
<script src="${ctxStatic}/jquery/jquery-1.11.2.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery/jquery-ui.min.js"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_3.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/bootstrap/js/bootstrap-hover-dropdown.js" type="text/javascript"></script>
<script>
function f_url(urls){
	var urls
	$("#pane-subcon iframe").attr("src",urls);
}
</script>
</head>

<body>
<div class="main-row">
	<div class="iframe_top right">
		<div class="top_action">
			<div class="right" style="display:inline-block;">
				<div class="drop_user">
					<div class="face" id='facename'>
					 <c:if test="${currentUser.photo!=null&&currentUser.photo!=''}">
				 <img src="${ctx}/sys/user/imageGet?fileName=${currentUser.photo}" width="33" height="33" class="radius_imgs" />
				</c:if>
				<c:if test="${currentUser.photo==null||currentUser.photo==''}">
				<img src="${ctxStatic}/images/img-1.png" width="33" height="33" /> 
				</c:if>
					</div>
					<!-- <div class="dropdown"> <span id="dropdownMenu2" data-toggle="dropdown">殇璎珞</span>
						<ul class="dropdown-menu pull-right" role="menu" aria-labelledby="dropdownMenu2">
							<li role="presentation"><a role="menuitem" tabindex="-1" href="#">个人设置</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1" href="#">数据导入</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1" href="#">数据迁移</a></li>
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
					<li role="presentation"><a role="menuitem" tabindex="-1" href="#"  famesrc="${ctx}/sys/user/info"> 个人设置</a></li>
					<!--	<li role="presentation"><a role="menuitem" tabindex="-1" href="#">个人设置</a></li>
					 	<li role="presentation"><a role="menuitem" tabindex="-1" href="#">数据导入</a></li>
						<li role="presentation"><a role="menuitem" tabindex="-1" href="#">数据迁移</a></li>	-->
						<li role="presentation"><a role="menuitem" tabindex="-1" href="${pageContext.request.contextPath}/a/logout">退出</a></li>
						
					</ul>
				</div>	
					 
					 
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
	<div class="bg_pub logo_c left"></div>
	<div class="clearfix"></div>
	<div class="main-frame">
		<div class="main-side"> 
			<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
				<div class="panel panel-default">
				
					<div class="n_1 item" role="tab" id="heading1">
						<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse1" aria-expanded="false" aria-controls="collapse1" onclick="f_url('${pageContext.request.contextPath}/a/newcharge/tChargecompany/index');"> 首页 </a> 
					</div>
				
					<div id="collapse1" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading1"></div>
				</div>
				<div class="panel panel-default">
				<c:if test="${fn:contains(powerNames, '客户管理')}">
					<div class="n_2 item" role="tab" id="headingOne">
						<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="false" aria-controls="collapseOne" onclick="console.log(12);"> 客户管理 <span class="caret3"></span></a>
						
					</div>
					 </c:if>
					<div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
						<ul class="a_no">
							<li><a href="#" famesrc="${pageContext.request.contextPath}/a/newcharge/tjoinappl/menu">加盟申请</a></li>
							<li><a href="#" famesrc="${pageContext.request.contextPath}/a/newcharge/tChargecompany/menue">客户列表</a></li>
						</ul>
					</div>
				</div>
				<div class="panel panel-default">
				<c:if test="${fn:contains(powerNames, '内容管理')}">
					<div class="n_3 item" role="tab" id="headingTwo">
						<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo" onclick="console.log(456);"> 内容管理 <span class="caret3"></span></a>
					</div>
					 </c:if>
					<div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
						<ul class="a_no">
							<li><a href="#" famesrc="${pageContext.request.contextPath}/a/helpcenter/thelpcenter/helpMenue">帮助中心</a></li>
							<li><a href="#" famesrc="${pageContext.request.contextPath}/a/comquestion/tcomquest/oftenMeetQue">常见问题</a></li>
							<li><a href="#" famesrc="${pageContext.request.contextPath}/a/sysnews/tsysnews/newsMenue">系统消息</a></li>
							<li><a href="#" famesrc="${pageContext.request.contextPath}/a/quotation/tquotation/QuotationMenue">语录管理</a></li>
						</ul>
					</div>
				</div>
				<div class="panel panel-default">
				<c:if test="${fn:contains(powerNames, '数据统计')}">
					<div class="n_4 item" role="tab" id="headingThree">
						<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree" onclick="console.log(789);"> 数据统计 <span class="caret3"></span></a>
					</div>
					 </c:if>
					<div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
						<ul class="a_no">
							<li><a href="#" famesrc="${pageContext.request.contextPath}/a/newcharge/tChargecompany/dataGeneralSituation">数据概况</a></li>
							<li><a href="#" famesrc="${pageContext.request.contextPath}/a/newcharge/tChargecompany/cityGeneralSituation">城市区域</a></li>
						</ul>
					</div>
				</div>
				<div class="panel panel-default">
				<c:if test="${fn:contains(powerNames, '意见反馈')}">
					<div class="n_5 item" role="tab" id="heading5">
						<a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapse5" aria-expanded="false" aria-controls="collapse5" onclick="f_url('${pageContext.request.contextPath}/a/feedback/back');"> 意见反馈 </a> 
					</div>
					</c:if>
					<div id="collapse5" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading5"></div>
				</div>
				<div class="panel panel-default">
		<c:if test="${fn:contains(powerNames, '用户管理')}">
					<div class="n_6 item" role="tab" id="heading6">
						<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse6" aria-expanded="false" aria-controls="collapse6" onclick="f_url('${pageContext.request.contextPath}/a/sys/user/usermanage');"> 用户管理 </a> 
					</div>
			</c:if>
					<div id="collapse6" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading6"></div>
				</div>
				<div class="panel panel-default">
				<c:if test="${fn:contains(powerNames, '操作日志')}">
					<div class="n_7 item" role="tab" id="heading7">
						<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse7" aria-expanded="false" aria-controls="collapse7" onclick="f_url('${pageContext.request.contextPath}/a/sys/log/handleLog');"> 操作日志 </a> 
					</div>
					</c:if>
					<div id="collapse7" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading7"></div>
				</div>
			</div>
		</div>
		<div class="main-con">
			<div class="body-con" id="pane-subcon">
				<iframe id="id_iframe" width="100%" height="100%" frameborder=0 scrolling=auto src="${pageContext.request.contextPath}/a/newcharge/tChargecompany/index"></iframe>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
function updatefilename(filename){
	var filename=filename;
	var img="<img src='${ctx}/sys/user/imageGet?fileName="+filename+"' width='33' height='33' class='radius_imgs' />";
    //$("#facename").html(<img src="${ctx}/sys/user/imageGet?fileName="+filename width="88" height="88" class="radius_imgs" />);
   $("#facename").html(img);
	
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

var winH=$(window).height();
var mainH=winH-60;
$(".main-frame .main-con .body-con").css({
	"height":mainH
})
$(function(){
	//折叠菜单

	//框架
	$("[famesrc]").click(function(event){
		var famesrc=$(this).attr("famesrc");
		event.preventDefault();
		$("#pane-subcon iframe").attr("src",famesrc);
		$(".side-subcon .submenu").hide();
	})
	
	$('#tableframe').height(function(){
		return $(window).height();
	})
})
//鼠标离开下拉菜单隐藏
	$('.dropdown-toggle').dropdownHover();
	//用于个人设置的
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
