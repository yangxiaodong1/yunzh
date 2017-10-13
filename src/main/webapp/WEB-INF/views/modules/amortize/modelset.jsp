<%@ page contentType="text/html;charset=UTF-8"%>
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
<title>芸智慧财务系统-凭证模板设置</title>
<script src="${ctxStatic}/jquery/jquery-1.11.2.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/js/modelset.js" type="text/javascript"></script>
<link href="${ctxStatic}/css/master_1.css" type="text/css" rel="stylesheet" />


<script type="text/javascript">
//	项目路径
var ctx = "${ctx}";

$(function(){
	listmodelset(1);
});
</script>
<style>
.item{height:254px;overflow:hidden;}
.t_pane_a1 .item .captions .list_t5{height:227px;overflow:hidden;}
.tit_channel_1{margin-top:10px;}
.t_pane_a1 .item .captions .list_t5 ul{min-height:40px;}
</style>
</head>
<body>


	<div class="main-con">
		<div class="body-con">
			<div class="tit_channel_1">凭证模板设置</div>
			<div class="t_pane_1">
				<div class="tit_d3">
					<button class="btn btn-default2" onclick="addVoucher()">新建模板</button>
				</div>
				<div class="clearfix"></div>
				<div class="hr10"></div>
			</div>
			<div class="t_pane_a1 border_n1">
				<div class="hr10"></div>
				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="active"><a href="#tab_c1"
						aria-controls="home" role="tab" data-toggle="tab" onclick="listmodelset(1)">日常凭证模板</a></li>
					<li role="presentation"><a href="#tab_c2"
						aria-controls="profile" role="tab" data-toggle="tab" onclick="listmodelset(2)">计提转结模板</a></li>
					<li role="presentation"><a href="#tab_c3"
						aria-controls="messages" role="tab" data-toggle="tab" onclick="listmodelset(3)">智能模板</a></li>
				</ul>
				<div class="hr10"></div>
				<div class="tab-content list_pane_h">
					<div role="tabpanel" class="tab-pane active" id="tab_c1">
						<div id="modelsetInfo1" class="masonry-container">
							

						</div>
						<div class="clearfix"></div>
						<div class="hr10"></div>
					</div>
					<div role="tabpanel" class="tab-pane" id="tab_c2">
						<div id="modelsetInfo2" class="masonry-container">
							

						</div>
						<div class="clearfix"></div>
						<div class="hr10"></div>
					</div>
					<div role="tabpanel" class="tab-pane" id="tab_c3">
						<div id="modelsetInfo3" class="masonry-container">
							

						</div>
						<div class="clearfix"></div>
						<div class="hr10"></div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="hr10"></div>

		</div>
		<div class="clearfix"></div>
	</div>
	<div class="clearfix"></div>

<script type="text/javascript">
$(function(){	

	$(".item").hover(function(){
		$(this).addClass("curr").siblings().removeClass("curr");
	})  
})
</script>
</body>
</html>