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
<title>加盟申请-客户管理</title>
<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery.validate.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_3.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
</head>

<body>
<div class="pad_15_10">
	<div class="panel panel-default mar_b0 radius0 border_none">
		<div class="panel-heading bg_col_2 panel-head-pub"><span class="font_cc8 pad_l_15">加盟申请列表</span>
		<!-- 
		<span class="btn btn-default btn_w_a2 btn_bg_6 left mar-rig10"><a href="${pageContext.request.contextPath}/a/newcharge/tjoinappl/addym" style="color:white;">添加加盟申请客户</a></span>
			
			<span class="right"><a href="#" class="font_cc8">查看详细数据</a></span>
			 -->
			<div class="clearfix"></div>
		</div>
		<table class="table table-center table_hover2">
			<thead>
				<tr class="block_c4 font_white">
					<th class="weight_normal" width="120">时间</th>
					<th class="weight_normal" width="100">所在城市</th>
					<th class="weight_normal">公司名称</th>
					<th class="weight_normal" width="100">联系人</th>
					<th class="weight_normal" width="130">联系电话</th>
					<th class="weight_normal" width="230">反馈</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${tList}" var="cl">
				<tr>
					<td>${cl.createDateString}</td>
					<td>${cl.city}</td>
					<td class="text-left">${cl.compname}</td>
					<td>${cl.contectperson}</td>
					<td>${cl.contectphone}</td>
					<td>
					<c:if test="${cl.message==null||cl.message==''}">
					<a href="#" class="font_cc4 dialog_add3" id="${cl.id}">添加反馈</a>
					</c:if>
					
					<c:if test="${cl.message!=null&&cl.message!=''}">
					<span class="font_cc9">
					
					${cl.message}
					
					</span> 
					
					
					<a class="mar-lft10 font_cc4 dialog_add3_edit" href="#" id="${cl.id}">编辑</a>
					</c:if>
					
					</td>
				
				</tr>
				</c:forEach>
				
			</tbody>
		</table>
	</div>
	<div class="hr15"></div>	
</div>
<!--添加反馈-->
<div id="dialog_add3" title="反馈信息" style="display:none;">
	<div class="font-14 w400 mar_auto">
		<form action="" method="post">
		<div class="radio">
		  <label>
			<input type="radio" name="message" id="blankRadio1" value="信息有误（号码不对或空号）" aria-label="...">信息有误（号码不对或空号）
		  </label>
		</div>
		<div class="radio">
		  <label>
			<input type="radio" name="message" id="blankRadio2" value="电话不接（未接电话）" aria-label="...">电话不接（未接电话）
		  </label>
		</div>
		<div class="radio">
		  <label>
			<input type="radio" name="message" id="blankRadio3" value="有试用意向，想继续跟进" aria-label="...">有试用意向，想继续跟进
		  </label>
		</div>
		<div class="radio">
		  <label>
			<input type="radio" name="message" id="blankRadio4" value="不感兴趣，放弃试用（提收集一些反馈意见）" aria-label="...">不感兴趣，放弃试用（提收集一些反馈意见）
		  </label>
		</div>
		<div class="radio">
		  <label>
			<input type="radio" name="message" id="blankRadio5" value="option5" aria-label="...">其他 <input type="text" class="form-control ipt_w4" value="" style="width:339px;" name="text">
		  </label>
		</div>
		<div class="right pad_l_r8">
			<button type="submit" class="btn btn-default btn_w_a2 btn_bg_6 ipt_w90 left">保存</button>
		</div>
		</form>
		<div class="hr15">
	</div></div>
</div>
<!--编辑反馈-->
<div id="dialog_add3_edit" title="编辑反馈信息" style="display:none;">
	<div class="font-14 w400 mar_auto">
		<form action="" method="post" id="signupFormNew">
		<div class="radio">
		  <label>
			<input type="radio" name="message" id="blankRadio1" value="信息有误（号码不对或空号）" aria-label="...">信息有误（号码不对或空号）
		  </label>
		</div>
		<div class="radio">
		  <label>
			<input type="radio" name="message" id="blankRadio2" value="电话不接（未接电话）" aria-label="...">电话不接（未接电话）
		  </label>
		</div>
		<div class="radio">
		  <label>
			<input type="radio" name="message" id="blankRadio3" value="有试用意向，想继续跟进" aria-label="...">有试用意向，想继续跟进
		  </label>
		</div>
		<div class="radio">
		  <label>
			<input type="radio" name="message" id="blankRadio4" value="不感兴趣，放弃试用（提收集一些反馈意见）" aria-label="...">不感兴趣，放弃试用（提收集一些反馈意见）
		  </label>
		</div>
		<div class="radio">
		  <label>
			<input type="radio" name="message" id="blankRadio5" value="option5" aria-label="...">其他 <input type="text" class="form-control ipt_w4" value="" style="width:339px;" name="text">
		  </label>
		</div>
		<div class="right pad_l_r8">
			<button type="submit" class="btn btn-default btn_w_a2 btn_bg_6 ipt_w90 left">保存</button>
		</div>
		</form>
		<div class="hr15">
	</div></div>
</div>
<script type="text/javascript">
$(function(){
	//反馈信息
	$( "#dialog_add3" ).dialog({
		autoOpen: false,
		width: 474,
		modal: true
	});	
	$( ".dialog_add3" ).click(function( event ) {
		var id=$(this).attr("id");
		//alert(id);
		var urls='${pageContext.request.contextPath}/a/newcharge/tjoinappl/savefeedback?id='+id;
		$( "#dialog_add3 form" ).attr("action",urls);
		$( "#dialog_add3" ).dialog( "open" );
		event.preventDefault();
	});
	
	//编辑反馈信息
	$( "#dialog_add3_edit" ).dialog({
		autoOpen: false,
		width: 474,
		modal: true
	});	
	$( ".dialog_add3_edit" ).click(function( event ) {
		var id=$(this).attr("id");
		//alert(id);
		var urls='${pageContext.request.contextPath}/a/newcharge/tjoinappl/savefeedback?id='+id;
		$( "#dialog_add3_edit form" ).attr("action",urls);
		insertPutt(id);
		$( "#dialog_add3_edit" ).dialog( "open" );
		event.preventDefault();
	});
	
	function insertPutt(id){
		$.ajax({  
			type:"POST",
			
			url:"${pageContext.request.contextPath}/a/newcharge/tjoinappl/forms",
			
				dataType : "json",
				data: "id="+id,
		       success:function(json){  
		    	  //alert(666);
		    	   $json = json;
		    	  $('#signupFormNew').formEdit($json);
		       },
		      error:function(){showd("dialog_error"); }
		      //error:function(){alert(8888);},
		 });
		 };
		//插件函数
		 $.fn.formEdit = function(data){
		     return this.each(function(){
		         var input, name;
		         if(data == null){this.reset(); return; }
		         for(var i = 0; i < this.length; i++){  
		             input = this.elements[i];
		             name = (input.type == "checkbox")? input.name.replace(/(.+)\[\]$/, "$1") : input.name;
		             if(data[name] == undefined) continue;
		             switch(input.type){
		                 case "checkbox":
		                     if(data[name] == ""){
		                         input.checked = false;
		                     }else{

		                         if(data[name].indexOf(input.value) > -1){
		                             input.checked = true;
		                         }else{
		                             input.checked = false;
		                         }
		                     }
		                 break;
		                 case "radio":
		                     if(data[name] == ""){
		                         input.checked = false;
		                     }else if(input.value == data[name]){
		                         input.checked = true;
		                     }
		                 break;
		                 case "button": break;
		                 default: input.value = data[name];
		             }
		         }
		     });
		 };
		 
		 

})
</script>
</body>
</html>
