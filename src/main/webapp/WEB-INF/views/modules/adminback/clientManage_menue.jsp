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
<title>客户列表-管理后台</title>
<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery.validate.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_3.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
<style type="text/css">
input:-webkit-autofill {
 -webkit-box-shadow: 0 0 0px 1000px white inset;
 -webkit-text-fill-color: #333;
}
</style>
</head>

<body>
<div class="pad_15_10">
	<div class="panel panel-default mar_b0 radius0 border_none">
		<div class="panel-heading bg_col_2 panel-head-pub">
			<div class="left pad_t_3">
				<span class="glyphicon glyphicon_a4 ico_pub bg_pub" aria-hidden="true"></span><span class="font_cc8 ">客户列表</span>
			</div>
			<div class="right"><span class="btn btn-default btn_w_a2 btn_bg_6 left mar-rig10"><a href="${pageContext.request.contextPath}/a/newcharge/tChargecompany/addym" style="color:white;">添加客户</a></span>
				<div class="left mar-rig10">
					<form action="${pageContext.request.contextPath}/a/newcharge/tChargecompany/search" method="post">
					<input type="text" class="ipt_s03 left" name="search" placeholder="请输入你所要选择的城市"><input type="submit" class="ipt_04" value="搜索">
					</form>
				</div>
				<span class="left mar-rig10">
					<!-- 
					<select class="form-control ipt_wauto" name="">
					<option>全部城市</option><option>北京</option>
					</select>
					 -->
					<select id="allCity" name="allCity" class="form-control ipt_w4 optionCity">
					     <option >全部城市</option>
					     <!-- 
						<c:forEach items="${tcList}" var="tcl">
							<option value="${tcl.city }">${tcl.city }</option>
						</c:forEach>	
						 -->	
						 <c:forEach items="${citySetlist}" var="tcl">
							<option value="${tcl}">${tcl}</option>
						</c:forEach>									
					</select>
					
				</span>
				<span class="left mar-rig10">
					<select id="useStatus" name="useStatus" class="form-control ipt_wauto option">
				          <option >状态筛选</option>
						<option value="0" >试用</option>
						<option value="1" >正式</option>		
				   </select>
				</span>
				<a href="${pageContext.request.contextPath}/a/newcharge/tChargecompany/stopservemenue" class="font_cc4 underline line_h26">显示已停用客户</a>
			</div>
			<div class="clearfix"></div>
		</div>
		<table class="table table-center table_hover2 font_cc9">
			<thead>
				<tr class="block_c4 font_white">
					<th class="weight_normal" width="200">所在城市</th>
					<th class="weight_normal">公司名称</th>
					<th class="weight_normal" width="100">联系人</th>
					<th class="weight_normal" width="130">对接人</th>
					<th class="weight_normal" width="80">子账号数量</th>
					<th class="weight_normal" width="80">企业数</th>
					<th class="weight_normal" width="100">状态</th>
					<th class="weight_normal" width="230">操作</th>
				</tr>
			</thead>
			<tbody>
				
				<c:forEach items="${tcList}" var="cl">
				<tr>
					<td>
					<c:choose >
   							 <c:when test="${cl.province=='北京市'||cl.province=='天津市'||cl.province=='重庆市'||cl.province=='上海市'||cl.province=='台湾'||cl.province=='香港特别行政区'||cl.province=='澳门特别行政区'}">
							      ${cl.province}
							    </c:when>
								 <c:otherwise>
								 ${cl.municipality}
							    </c:otherwise>
							</c:choose>
					</td>
					<td class="text-left">${cl.chargecomname.chargecomname}</td>
					<td>${cl.contectperson.contectperson}</td>
					<td>${cl.abutment.abutment}</td>
					<td>${cl.sonCount}</td>
					<td>${cl.countCompany}</td>
					<c:if test="${cl.usestatus.usestatus=='0'}">
					<td>试用</td>
					</c:if>
					<c:if test="${cl.usestatus.usestatus=='1'}">
					<td>正式</td>
					</c:if>
					<c:if test="${cl.usestatus.usestatus==null||cl.usestatus.usestatus==''}">
					<td>没有填写状态</td>
					</c:if>
					<td><a href="${pageContext.request.contextPath}/a/newcharge/tChargecompany/check?id=${cl.id}"  class="mar-lr5">查看</a><a href="#" class="mar-lr5 dialog_infos" id="${cl.id}">设置</a><a href="${pageContext.request.contextPath}/a/newcharge/tChargecompany/edit?id=${cl.id}" class="mar-lr5">编辑</a><a href="#" class="mar-lr5 stopServe" id="${cl.id}">停止服务</a></td>
				</tr>
				</c:forEach>
				
			</tbody>
		</table>
	</div>
	<div class="hr15"></div>	
</div>
	<!--设置账号-->
<div id="dialog_infos" title="设置账号" style="display:none;">
	<div class="font-14 w400 mar_auto">
	 <form action="" method="post">
		<div class="hr15"></div>
		  
			<div class="form-group">
				<label class="control-label w100 left mar-rig10" style="text-align:right;">用户登录名</label>
				<label id="loginnamenew"></label>
				<!-- 				
				 <input type="text" class="form-control ipt_w4 depNameed" value="" name="depNameed" id="depNameed" readonly="true"/>				
			 -->
			</div>
			<div class="form-group">
				<label class="control-label w100 left mar-rig10" style="text-align:right;">密码</label>				
				 <input type="password" class="form-control ipt_w4" value="" name="pwd" id="pwd" autocomplete="off"/>				
			</div>
			
			<div class="clearfix"></div>
			<div class="hr15"></div>
			<div class="text-center">
			 
				<button type="submit" class=" btn btn-default btn_w_a2 btn_bg_6 ipt_w90 right ">保存</button>
			</div>
			<div class="hr15"></div>
	
		</form>
	</div>
</div><!-- 设置账号到这里 -->
<script type="text/javascript">
$(function(){
	
	//清除input中的默认值
	
	$("#h_rig").height(function(){
		return $("#h_lft").height();
	});
	
	$(".stopServe").on("click",function(){
		var id=$(this).attr("id");
		//alert(id);
		urls="${pageContext.request.contextPath}/a/newcharge/tChargecompany/serviceStatus?";
		
		//urls='${ctx}/amortize/tAmortize/amortize?';
	
		window.location.href=urls+'id='+id+'&serviceStatus=0'+'&delog=0';	
	});
	//根据状态查找
	$(".option").on("change",function(){
		var userStatus=$('#useStatus').val();
		urls="${pageContext.request.contextPath}/a/newcharge/tChargecompany/selByStatus?";
		window.location.href=urls+'userStatus='+userStatus;	
		//alert($('#useStatus').val());
		
	});
	//根据点击城市查询查找
	$(".optionCity").on("change",function(){
		var allCity=$('#allCity').val();
		//alert(1);
		//alert(allCity);
		urls="${pageContext.request.contextPath}/a/newcharge/tChargecompany/allCity?";
		window.location.href=urls+'allCity='+allCity;	
		//alert($('#useStatus').val());
		
	});
	//关于设置的js
	$( "#dialog_infos" ).dialog({
		autoOpen: false,
		width: 474,
		modal: true
	});	
	$( ".dialog_infos" ).click(function( event ) {
		$("#dialog_infos").find("input").val("");
		var dialcompId=$(this);
		var id=dialcompId.attr("id");
		//alert(id);
		$("#loginnamenew").empty();
		
		$.ajax({
	        type: "POST",//使用post方法访问后台
	        url: "${pageContext.request.contextPath}/a/newcharge/tChargecompany/fillusername",//要访问的后台地址  
	        data: "id="+id,
	        success: function(result){
	        	var username=result;
	        	//console.log(username+"获得用户名");
	        	//$("#depNameed").val(username);
	        	$("#loginnamenew").text(username);
	        }
	});
		var urls='${pageContext.request.contextPath}/a/newcharge/tChargecompany/saveSetUser?id='+id;
		$( "#dialog_infos form" ).attr("action",urls);
		$( "#dialog_infos" ).dialog( "open" );
		event.preventDefault();
	});
})
</script>
</body>
</html>
