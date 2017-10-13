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
<title>用户管理-后台管理</title>
<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery.validate.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_3.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
<style>
.col-sm-8 label{display:block;color:#ff0000;font-weight:bold;}
</style>
</head>

<body>
<div class="pad_15_10">
	<div class="panel panel-default mar_b0 radius0 border_none">
		<div class="panel-heading bg_col_2 panel-head-pub">
			<div class=" pad_t_3">
				<span class="glyphicon glyphicon_a15 ico_pub bg_pub mar-top2" aria-hidden="true"></span><span class="font_cc8 mar-rig10 font-14">用户管理</span>
				<a href="#" class="btn btn-default btn_w_a2 btn_bg_6 mar-rig10 dialog_admin">添加</a>
				<span class="btn btn_b6" id="actip" style="visibility:hidden;">“<b></b>”账号已<span></span></span>
				<!-- <span class="btn btn_b6">“绮罗生”账号已停用</span> -->
			</div>
			<div class="clearfix"></div>
		</div>
		<table class="table table-center table_hover2 font_cc9 table_vmiddle">
			<thead>
				<tr class="block_c4 font_white">
					<th width="60"></th>
					<th class="weight_normal">姓名</th>					
					<th class="weight_normal">用户名</th>
					<th class="weight_normal">创建时间</th>
					<th class="weight_normal">创建人</th>					
					<th class="weight_normal" width="150">操作</th>
				</tr>
			</thead>
			<tbody>
				
				<c:forEach items="${userList}" var="ul">
				<tr id="${ul.id}" idd="rows">
				
					<td>
					<c:if test="${ul.useStatus=='0'||ul.useStatus==''||ul.useStatus==null}">
					<span class="btn btn_b7" style="visibility:hidden;">停用</span>
					</c:if>
					<c:if test="${ul.useStatus=='1'}">
					<span class="btn btn_b7" style="visibility:visible;">停用</span>
					</c:if>
					</td>
					<td>${ul.name}</td>
					<td>${ul.loginName}</td>	
					<td>${ul.createDateString}</td>				
					<td>${ul.createByString}</td>					
					<td class="text-left font_cc4"><a href="#" class="mar-lr5 dialog_admin_edit" id="${ul.id}" >编辑</a><a href="#" class="mar-lr5 btn_act1" attrperson="${ul.name}" attrpid="${ul.id}">
					
					<c:if test="${ul.useStatus=='0'||ul.useStatus==''||ul.useStatus==null}"><b>停用</b></c:if>
					  <c:if test="${ul.useStatus=='1'}"> <b>恢复</b></c:if>
					
					
					</a><a href="#" class="mar-lr5 dialog_edit5" id="${ul.id}" username="${ul.name}">设置权限</a></td>
				</tr>
				</c:forEach>
							
			</tbody>
		</table>
	</div>
	<div class="hr15"></div>	
</div>
<!--添加管理员-->
<div id="dialog_admin" title="添加管理员" style='display:none;'>
	<div class="min-h200">
		<div class="pad_15">
		<form class="form-horizontal form-horizontal2" action="${pageContext.request.contextPath}/a/sys/user/saveUserBykh" method="post" id="signupForm">
			<dl class="errors"></dl>
			<div class="form-group">
				<label class="col-sm-4 control-label" for="companytname">姓名</label>
				<div class="col-sm-8">
					<input type="text" class="form-control ipt_w4 required" id="companytname" name="companytname" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label" for="username">用户名/登录名</label>
				<div class="col-sm-8">
					<input type="text" class="form-control ipt_w4 required" id="username" name="username" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label" for="mobile">手机号</label>
				<div class="col-sm-8">
					<input type="text" class="form-control ipt_w4 required" id="mobile" name="mobile" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label" for="password">密码</label>
				<div class="col-sm-8">
					<input id="password" name="password" type="password"  class="form-control ipt_w4 " />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label" for="confirm_password">二次密码输入</label>
				<div class="col-sm-8">
					<input id="confirm_password" name="confirm_password" type="password"  class="form-control ipt_w4 " />
				</div>
			</div>

			<span class="right">
			<button type="submit" class="  btn btn-default btn_w_a btn_bg_2 mar-rig10 submit_admin">保存</button>
			</span>
		</form>
		</div>
		<div class="hr20"></div>
	</div>
</div>
<!--编辑管理员-->
<div id="dialog_admin_edit" title="编辑管理员" style='display:none;'>
	<div class="min-h200">
		<div class="pad_15">
		<form class="form-horizontal form-horizontal2" action="${pageContext.request.contextPath}/a/sys/user/editUser" method="post" id="signupFormNew">
			<dl class="errors"></dl>
			<input type="hidden" class="form-control ipt_w4 required" id="id" name="id" />
			<div class="form-group">
				<label class="col-sm-4 control-label" for="companytname">客户名称</label>
				<div class="col-sm-8">
					<input type="text" class="form-control ipt_w4 required" id="name" name="name" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label" for="username">用户名/登录名</label>
				<div class="col-sm-8">
					<input type="text" class="form-control ipt_w4 required" id="loginName" name="loginName" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label" for="mobile">手机号</label>
				<div class="col-sm-8">
					<input type="text" class="form-control ipt_w4 required" id="mobile" name="mobile" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label" for="password">密码</label>
				<div class="col-sm-8">
					<input id="password" name="password" type="password"  class="form-control ipt_w4 " /><br/>若不修改密码，请留空。
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label" for="confirm_password">二次密码输入</label>
				<div class="col-sm-8">
					<input id="confirm_password" name="confirm_password" type="password"  class="form-control ipt_w4 " />
				</div>
			</div>

			<span class="right">
			<button type="submit" class="  btn btn-default btn_w_a btn_bg_2 mar-rig10 submit_admin">保存</button>
			</span>
		</form>
		</div>
		<div class="hr20"></div>
	</div>
</div>
<!--设置权限-->
<div id="dialog_edit5" title="设置权限" style='display:none;'>
	<div class="min-h200">
		<form action="" method="post">
		<div class="font-14 pad_15">
			<span class="inline-block w80 mar-rig10 text-right">姓名</span>
			
			<input type="text" class="ipt_w5 w100 form-control" value="mary" readonly id="nameval">
			<div class="hr10"></div>
			<span class="inline-block w80 mar-rig10 text-right">权限选择</span>
			<div class="right w320">
				<span class="inline-block w150 powerset"><label><input type="checkbox" name="powerIdList" value="1"  id="checkbox0"></label>客户管理</span>
				<span class="inline-block w150 powerset"><label><input type="checkbox" name="powerIdList" value="4"  id="checkbox3"></label>意见反馈</span>
				<span class="inline-block w150 powerset"><label><input type="checkbox" name="powerIdList" value="2"  id="checkbox1"></label>内容管理</span>
				<span class="inline-block w150 powerset"><label><input type="checkbox" name="powerIdList" value="5"  id="checkbox4"></label>用户管理</span>
				<span class="inline-block w150 powerset"><label><input type="checkbox" name="powerIdList" value="3"  id="checkbox2"></label>数据统计</span>
				<span class="inline-block w150 powerset"><label><input type="checkbox" name="powerIdList" value="6"  id="checkbox5"></label>操作日志</span>
			</div>
			<div class="hr10"></div>
		</div>
		<span class="right">
			<button type="submit" class="btn btn-default btn_w_a btn_bg_2 mar-rig10">保存</button>
		</span>
		</form>
		<div class="hr15"></div>
	</div>
</div>
<script type="text/javascript">

$(function(){
	/**停用与恢复**/
	$(".btn_act1").click(function(){
		persons=$(this).attr("attrperson");
		$("#actip b").html(persons);
		//var a=$(".btn_act1 b").html();
		var a=$(this).find("b").html();
		$("#actip span").html(a);
		$("#actip").css('visibility','visible');	
		setTimeout("$('#actip').animate('visibility','hidden')",800);
		
		//进入到页面中刷新页面
		var status;
		if(a=="停用"){
			status=1;
		}else if(a=="恢复"){
			status=0;
		}
		
		urls='${ctx}/sys/user/updateStatus?';
		var aid=$(this).attr("attrpid");
		//alert(aid);
		//setTimeout(window.location.href=urls+'id='+aid+'&status='+status,8000);
		window.location.href=urls+'id='+aid+'&status='+status;//用于向后台传递参数
		console.info(persons,aid);
	})
	
	$(".btn_act2").click(function(){
		persons=$(this).attr("attrperson");
		$("#actip b").html(persons);
		$("#actip span").html("已恢复")
		$("#actip").css('visibility','visible');	
		setTimeout("$('#actip').css('visibility','hidden')",8000);
		console.info(persons);
	})
	//添加管理员
	$( "#dialog_admin" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});	
	$( ".dialog_admin" ).click(function( event ) {
		$( "#dialog_admin" ).dialog( "open" );
		event.preventDefault();
	});
	//编辑管理员
	$( "#dialog_admin_edit" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});	
	$( ".dialog_admin_edit" ).click(function( event ) {
		
		var id=$(this).attr("id");
		//alert(id);
		insertPutt(id);
		$( "#dialog_admin_edit" ).dialog( "open" );
		event.preventDefault();
		
	});
	function insertPutt(id){
		$.ajax({  
			type:"POST",
			
			url:"${pageContext.request.contextPath}/a/sys/user/forms",
			
				dataType : "json",
				data: "id="+id,
		       success:function(json){  
		    	  // alert(666);
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
		 
		 
	
	
	//表单序列化专递值进行添加管理员
	$(".add").click(function() {
		//alert("yxd");
		if($("#username").val()!="" && $("#password").val()!=""){
		$("#inputForm").ajaxSubmit({
            type: 'post',
           // url: "${pageContext.request.contextPath}/a/sys/user/saveUserBykh",${ctx}/sys/user/voucherIndexx
            url: "${ctx}/sys/user/saveUserBykh",
           data: $("#signupForm").serialize(),
            success: function(data){
            	if(data=="1"){
                	alert("保存成功！");	
                	window.location="${pageContext.request.contextPath}/a/workterrace/chargeToAccount";
            	}
                $( "#signupForm").resetForm();
            	$( "#upload").resetForm();
            },
            error: function(XmlHttpRequest, textStatus, errorThrown){
                 //alert( "error");
            }                
        })
        }else{
        	alert("请填写必须要输入的信息！");
        }
	});
	
	
	//设置权限
	$( "#dialog_edit5" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});	
	$( ".dialog_edit5" ).click(function( event ) {
		//用于提交权限的
		var id=$(this).attr("id");
		//alert(id);
		var namevalue=$(this).attr("username");
		//alert(namevalue);
		$("#nameval").val(namevalue);
		var url='${pageContext.request.contextPath}/a/sys/user/savePower?id='+id;
		$( "#dialog_edit5 form" ).attr("action",url);
		//alert(1)powerset
		 $(".powerset").each(function() {
			 //alert("111");
			 $(":checked",this).prop("checked",false);
		 });
		
		//给对应的复选框赋值，ajax
		$.ajax({
	        type: "POST",//使用post方法访问后台
	        url: "${ctx}/sys/user/fillpower",//要访问的后台地址  
	        data: "id="+id,
	        success: function(data){
	        	//var tyList=eval(data);//获取到返回来的list集合
	        	//$.each(tyList,function(n.val){
	        	//	alert(val.name);
	        	//});
	        	//var values=$(".valuename");
	        	//for(var i;i<values.length;i++){
	        	//	alert("ddd");
	        	//};
	        	var tyList=eval(data);
	        	
	            $.each(tyList, function (i, item) { 
	            	//var ii=i.toString();
	            	//var idcheckbox=$("#checkbox"+ii).val();
	            	var numbid=item.id;
	            	$(".powerset").each(function() {
		       			 var num=$(this).find("input").val();
		       			 if(num==numbid){
		       				$(this).find("input").prop("checked",true);
		       			 }
		       			
		       		 });
	            	
	                
	            });
	        }
	    });
		
		$( "#dialog_edit5" ).dialog( "open" );
		event.preventDefault();
	});
	//验证
	var container = $('#signupForm .errors ');
	$("#signupForm").validate({
		rules: {
		   companytname: {
			required: true
		   },
		   username: {
			required: true
		   },
		   mobile: {
			number: true,
			minlength : 11,
			isMobile : true
		   },
		   password: {
			required: true,
			minlength: 5
		   },
		   confirm_password: {
				required: true,
				minlength: 5,
				equalTo: "#password"
		   }
		},
		messages: {
			companytname: "请输入姓名",
			username: "请输入用户名登录名",
			mobile: {
				required: "请输入手机号",
				number:"请输入手机号",
				minlength: "手机号不能小于11个字符",
				isMobile : "请正确填您的手机号码"
			},
			password: {
				required: "请输入密码",
				minlength: "密码不能小于5个字符"
			},
			confirm_password: {
				required: "请输入确认密码",
				minlength: "确认密码不能小于5个字符",
				equalTo: "两次输入密码不一致不一致"
			}
		}

	});

})
//手机验证规则  
// 手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "请正确填写您的手机号码");
</script>
<script type="text/javascript">
$(function(){
	
	
	
	
})
</script>
</body>
</html>
