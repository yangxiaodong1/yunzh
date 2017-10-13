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
<title>意见反馈-芸豆会计</title>
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
</head>

<body>
<div class="pad_15_10">
	<div class="panel panel-default mar_b0 radius0 border_none">		
		<div class="hr15"></div>
		<h3 class="h3 text-center pad_15">意见反馈</h3>
		<div class="hr15"></div>
		<div class="mar_b8">
			<div class="list_comm">
				<div class="items">	
					<div class="imgs w45 left text-center">
					<!-- 	<img src="${user.photo }" width="45" height="45" class="radius_imgs" alt="" /> -->
						 <img src="${ctx}/sys/user/imageGet?fileName=${currentuser.photo}" width="45" height="45" class="radius_imgs" alt="" />
						
						<div class="font_cc4">${user.name }</div>
					</div>
					<div class="cons right">
						<div class="inner">
							<form action="${ctx}/feedback/save" method="post" >
							<input id="feedFiles" type="hidden" name="feedFiles" value="" />
							<textarea name="feed" class="bg_col_3 border_i2 width_100" style="resize:none"></textarea>
							<div class="hr10"></div>
							<input class="btn btn-default btn_w_a btn_bg_2 right" type="submit" value="发布">
							<span class="left inline-block border_i2 ipt_ufile bg_col_3">
								<input type="file" class="opacity_0 imgs" name="file" data-url="" multiple />
							</span>
							<div class="clearfix"></div>
							</form>
						</div>
						<div class="hr15"></div>
					</div>
					<div class="clearfix"></div>
				</div>
				<c:forEach var="f" items="${list}"> 
				<div class="items">
					<div class="imgs w45 left">
					<!-- 	<img src="${f.createBy.photo }" width="45" height="45" class="radius_imgs" alt="" /> -->
						<img src="${ctx}/sys/user/imageGet?fileName=${f.createBy.photo}" width="45" height="45" class="radius_imgs" alt="" />
					</div>
					<div class="cons right">
						<div class="inner">
							<div class="line_h24">
							<span class="font_cc4 mar-rig20">${f.createBy.name }</span>
							<span><fmt:formatDate value="${f.createDate }" pattern="yyyy-MM-dd"/> </span>
							</div>							
							<div class="font_cc5">
							${f.feed }
							</div>
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
								<div class="font_cc4">${f.updateBy.name }</div>
								${f.back }
							</div>
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
<script src="${ctxStatic}/jquery/jquery.fileupload.js"></script>
<script src="${ctxStatic}/jquery/jquery.fileupload-process.js"></script>
<script src="${ctxStatic}/jquery/jquery.fileupload-validate.js"></script>
<img src="" width="400" id="mybigpic" style="position:absolute;display:none;border:1px #ccc solid;">
<script type="text/javascript">
$(function(){
	$('[rel=lightbox]').lightbox({
		fitToScreen: true,
		imageClickClose: false
	});
	
	$(".ipt_ufile input").attr('data-url', '${ctx}/feedback/fileUpload').fileupload({
        dataType: 'json',
        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
        maxFileSize: 1048576*2,//限制上传文件的大小2M
        dropZone: ""
    }).bind('fileuploadsubmit', function (e, data) {
        var twitter = $('#twitter');
        data.formData = { twitter: twitter.val() };
    }).on('fileuploadprocessalways', function (e, data) {
        if (data.files.error) {
            if (data.files[0].error == 'File type not allowed') {
            	alert("上传格式错误，请上传：jpg、gif、jpeg、png格式文件");
            }
            if (data.files[0].error == 'File is too large') {
            	alert("上传文件太大了，不能超过2M");
            }
        }
    }).on('fileuploaddone', function (e, data) {
    	if(data.result.state == 200){
    		if(data.result.data.length>0){
    			if($("#feedFiles").val() != "")
    				$("#feedFiles").val($("#feedFiles").val()+","+data.result.data[0].id);
    			else
    				$("#feedFiles").val(data.result.data[0].id);
    			$(".ipt_ufile").before('<span class="left border_i2 mar-rig10"><img src="${ctx}/feedback/getFile?fileName='+data.result.data[0].name+'" width="123" height="100" /></span>');
    		}
    	}else if(data.result.state == 250){
    		alert(data.result.msg);
    	}
    });
})
</script>
</body>
</html>
