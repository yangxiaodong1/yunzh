<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>意见反馈-后台管理</title>
<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery.validate.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_3.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="${ctxStatic}/css/lightbox.css" type="text/css" media="screen" />
<script src="${ctxStatic}/jquery/jquery.lightbox.js"></script>
<style>
.con_reply{padding-left:55px;padding-top:10px;clear:both;}
.btn_publish{padding-bottom:5px;padding-top:5px;}
</style>
</head>

<body>
<div class="pad_15_10">
	<div class="panel panel-default mar_b0 radius0 border_none">		
		<div class="hr15"></div>
		<h3 class="h3 text-center pad_15">意见反馈</h3>
		<div class="hr15"></div>
		<div class="mar_b8">
			<div class="list_comm">
			<c:forEach var="f" items="${list}"> 
				<div class="items">
					<div class="imgs w45 left">
					<!-- 	<img src="${f.createBy.photo }" width="45" height="45" class="radius_imgs" alt="" /> -->
						<img src="${ctx}/sys/user/imageGet?fileName=${f.createBy.photo}" width="45" height="45" class="radius_imgs" alt="" />
					</div>
					<div class="cons right">
						<div class="inner">
							<div class="line_h24"><span class="font_cc4 mar-rig20">${f.createBy.name }</span>
							<span><fmt:formatDate value="${f.createDate }" pattern="yyyy-MM-dd"/></span></div>							
							<div class="font_cc5">${f.feed }</div>
							<c:if test="${!empty f.images}">
							<div class="hr5"></div>
							<c:forEach var="a" items="${f.images}"> 
							<span class="left border_i2 mar-rig10">
							<a href="${ctx}/feedback/getFile?fileName=${a }" rel="lightbox">
								<img src="${ctx}/feedback/getFile?fileName=${a }" width="123" height="100" />
							</a>
							</span>
							</c:forEach>
							</c:if>
							<div class="hr5"></div>
							<c:if test="${f.isBack == '1'}">
							<div class="arrows">
								<span class="caret2"></span>
							</div>
							<div class="border_i2 bg_col_3 pad_15">
								<div><span class="font_cc4">${f.updateBy.name }</span>&nbsp;&nbsp;回复&nbsp;&nbsp;
								<span class="font_cc4">${f.createBy.name }</span>&nbsp;&nbsp;
								<fmt:formatDate value="${f.updateDate }" pattern="yyyy-MM-dd"/>
								</div>
								${f.back }
							</div>
							</c:if>
						</div>
						<div class="hr10"></div>
						<div class="pane_reply">
						<c:if test="${f.isBack != '1'}">
							<div class="btn_reply btn_back"><button class="btn btn-default btn_w_a btn_bg_2 right" attreply="${f.id }">回复</button></div>		
						</c:if>
						<c:if test="${f.isBack == '1'}">
							<div class="btn_reply btn_edit"><button class="btn btn-default btn_w_a btn_bg_2 right" attrtext="${f.back }" attreply="${f.id }">编辑</button></div>		
						</c:if>
						</div>
						<div class="hr15"></div>
					</div>
					<div class="clearfix"></div>
				</div>
				</c:forEach> 
			</div>
		</div>
		<div class="hr15"></div>
	</div>
	<div class="hr15"></div>	
</div>
<img src="" width="400" id="mybigpic" style="position:absolute;display:none;border:1px #ccc solid;">
<script type="text/javascript">
$(function(){
	$('[rel=lightbox]').lightbox({
		fitToScreen: true,
		imageClickClose: false
	});
	$('.btn_back').each(function(){
		var i=0;
		$(this).click(function(){
			var t = $(this).find("button").text();
			if(t == "回复"){
				$(this).find("button").text("取消");
				var nid=$(this).find('button').attr("attreply");
				var html="";
				html+="<div class='con_reply'>";
				html+="<form action='${ctx}/feedback/update' method=\"post\">";
				html+="<input type='hidden' name='id' value=";
				html+=nid;
				html+=">";
				html+="<input type='hidden' name='isBack' value='1' />";
				html+="<textarea name=\"back\" class=\"bg_col_3 border_i2 width_100\" style=\"resize:none\"></textarea>";
				html+="<div class='btn_publish'><button type='submit' class=\"btn btn-default btn_w_a btn_bg_2\" attrpublist=\"1\">发布</button></div>";
				html+="</form>";
				html+="</div>";

				$(this).parent().append(function(){
					return html;
				});
			}else{
				$(this).parent().find(".con_reply").remove();
				$(this).find("button").text("回复");
			}
		})
	})
	$('.btn_edit').each(function(){
		var i=0;
		$(this).click(function(){	
			var t = $(this).find("button").text();
			if(t == "编辑"){
				$(this).find("button").text("取消");
				$(this).parent().parent().find(".border_i2").css("display","none");
				$(this).parent().parent().find(".arrows").css("display","none");
				var nid=$(this).find('button').attr("attreply");
				var text=$(this).find('button').attr("attrtext");
				var html="";
				html+="<div class='con_reply'>";
				html+="<form action='${ctx}/feedback/update' method=\"post\">";
				html+="<input type='hidden' name='id' value=";
				html+=nid;
				html+=">";
				html+="<input type='hidden' name='isBack' value='1' />";
				html+="<textarea name=\"back\" class=\"bg_col_3 border_i2 width_100\" style=\"resize:none\">"+text+"</textarea>";
				html+="<div class='btn_publish'><button type='submit' class=\"btn btn-default btn_w_a btn_bg_2\" attrpublist=\"1\">发布</button></div>";
				html+="</form>";
				html+="</div>";

				$(this).parent().append(function(){
					return html;
				});
			}else{
				$(this).parent().find(".con_reply").remove();
				$(this).parent().parent().find(".border_i2").css("display","block");
				$(this).parent().parent().find(".arrows").css("display","block");
				$(this).find("button").text("编辑");
			}
			
		})
	})
})
</script>
</body>
</html>
