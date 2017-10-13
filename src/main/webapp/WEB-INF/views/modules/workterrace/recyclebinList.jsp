<!DOCTYPE html>
<html lang="en">
<head>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>回收站-工作台-芸豆会计</title>
<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.11.2.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-ui.min.js"></script>
<link href="${pageContext.request.contextPath}/static/jquery/jquery-ui.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/css/master_2.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/remove_sys.css">
<link href="${pageContext.request.contextPath}/static/css/dialog.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/bootstrap/css/page.css" type="text/css" rel="stylesheet" />
<style>
.pane_main5{width:1110px;margin:0 auto;}
.tips_nodata{text-align:center;}
</style>

</head>
<body style="overflow:hidden;">
	<div class="main_index3">
		<div class="pane_main5">
			<div class="t_pane_a1 radius_5 tb">
				<div class="hr10"></div>
				<div class="tit_channel_1">回收站
					<button onclick="back()" class="btn btn-default2"><i>返回工作台</i></button>
				</div>			
				<div class="tips_nodata">
					<c:if test="${page.count<1}">
					<img src="${pageContext.request.contextPath}/static/imgs/nodata.jpg" >
					</c:if>
				</div>
		<c:if test="${page.count>0}">
				<div class="remove_list">
					<form:form id="searchForm" modelAttribute="tCustomer" action="${ctx}/workterrace/recyclebin/list" method="post" >
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		</form:form>
					<ul class="remove-style">
						<c:forEach items="${page.list}" var="tCustomer" varStatus="status">
						<li>
							<div class="inner">
								<div class="img"></div>
								<div class="txt">
									<div class="tit_attr1">${tCustomer.customerName}</div>
									<div class="tit_attr2">建账月份：${tCustomer.initperiod}</div>
									<div class="tit_attr3">增<span>值</span>税：
									<c:choose>
									<c:when test="${tCustomer.valueAddedTax==1}">小型规模纳税人 </c:when>
									<c:when test="${tCustomer.valueAddedTax==0}">一般纳税人 </c:when>
									</c:choose>
									</div>
								</div>
								<div class="clearfix"></div>
								<input type="hidden" value="${tCustomer.id}"/>
								<button  class="restore btn btn-default btn_w_a btn_bg_4 right">还原</button>
							</div>
						</li>
						</c:forEach>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="pagination">${page}</div>
				</c:if>
			</div>
		</div>
	</div>
<div id="dialog_del2" class="displayCss" title="系统提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center">您确定要还原该客户吗？</div>
		<div class="hr40"></div>
		<form>
		<div class="text-center">
			<input type="button" class="btn restores  btn-default btn_w_a btn_bg_2 mar-rig10" value="确认" />
			<input type="button"  class="btn dialog_close6 btn_w_a btn_bg_4" value="取消" />
			
		</div>
		</form>
	</div>
</div>
<script>
$(".tb").height(function(){
	return parseInt($(window).height()-30);	
})
$(".remove_list").css({
	"min-height":parseInt($(window).height()-150)
})
$(function(){

	$( "#dialog_del2" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});	
	$( ".dialog_close6" ).click(function( event ) {
		$( "#dialog_del2" ).dialog( "close" );
		event.preventDefault();
	});
	
	var cid="";
	var $tag=null;
$(".restores").click(function( event ) {
		
		if(cid!="")
		{
			$.post("${ctx}/workterrace/recyclebin/delete",{id:cid},function(result){
				$tag.parent().parent().remove();
				$( "#delectCustomer" ).dialog( "close" );
				event.preventDefault();
				
			});
		}
		
		$( "#dialog_del2" ).dialog( "close" );
		event.preventDefault();
	});
$(".restore").click(function( event ) {
	$tag=$(this);
	cid=$(this).prev().val();	
	$("#dialog_del2" ).dialog( "open" );
	event.preventDefault();
});
var tbHeight=$(".tb").height();
var dataImg=$(".tips_nodata img").height();
$(".tips_nodata img").css({
	"marginTop":parseInt((tbHeight-dataImg)/2-20)
})

})
function back(){
	 window.location.href="${ctx}/workterrace/chargeToAccount/";
}
</script>
</body>
</html>