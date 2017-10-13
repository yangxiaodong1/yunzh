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
<title>编辑客户-客户管理</title>
<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery.validate.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_3.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery/jquery.cxselect.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="${ctxStatic}/webuploader/webuploader.html5only.js"></script>
<script type="text/javascript" src="${ctxStatic}/webuploader/up.js"></script>
<link href="${ctxStatic}/webuploader/webuploader.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctxStatic}/jquery/jquery.form.js"></script>
<style>
.ipt_pos_a{position:relative;height:25px;width:90px;overflow:hidden;}
.ipt_pos_a .ipt_pos_1{opacity:0;width:90px;position:absolute;top:0;left:0;z-index:2;}
.ipt_pos_a .ipt_pos_2{position:absolute;top:0;left:0;z-index:1;}
.uploader-list{width:100px;position: absolute;  z-index: 2;  top: 0;  left: 0;}
.thumbnail{border-radius:0;border-color:#999999;padding:5px;width:195px;margin-bottom:10px;}


.uploader-demo {
  position: relative;
  width: 195px;
  height: 150px;
  padding: 120px 0 0 0;
}
.oldfaces {
  width: 195px;
  height: 112px;
  border: 1px #999999 solid;
  padding: 5px;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 1;
}
.thumbnail > img, .thumbnail a > img {
  width:182px;
  height:100px;
}
.thumbnail .info{display:none;}
label.error{color:#ff0000;font-weight:normal;margin-left:3px;}
</style>
</head>

<body>
<div class="message-pop"><span>保存成功！</span></div>
<div class="pad_15_10">
	<div class="panel panel-default mar_b0 radius0 border_none pad_10 ">
		<div class="panel panel-default mar_b0 radius0 border_i4 ">
			<div class="pad_lr_35">
				<div class="hr15"></div>
				
				<form:form id="inputForm" modelAttribute="tChargecompany" action="" method="post" class="form-horizontal form-horizontal2" enctype="multipart/form-data">
		             <form:hidden path="id"/>
		              <form:input path="appendixtype" htmlEscape="false" maxlength="100" class="input-xlarge " type="hidden"  id="appendixtype" value=""/>
		             <form:input path="runappendix" htmlEscape="false" maxlength="100" class="input-xlarge " type="hidden" id="runappendix" value="" />
		             	<form:input path="taxappendx" htmlEscape="false" maxlength="100" class="input-xlarge " type="hidden" id="taxappendx" value="" />
		             	<form:input path="codeappendx" htmlEscape="false" maxlength="100" class="input-xlarge " type="hidden" id="codeappendx" value="" />
				<div class="panel-heading bg_col_2 panel-head-pub">
					<div class="left pad_t_3">
						<span class="glyphicon glyphicon_a5 ico_pub bg_pub" aria-hidden="true"></span><span class="font_cc8 font-14">客户基本信息编辑</span>
					</div>
					<div class="right">
					
						<button type="button" class="btn btn-default btn_w_a2 btn_bg_6 ipt_w90 left mar-rig10 dialog_infos">设置帐号</button>
				
						<button type="submit" class=" add btn btn-default btn_w_a2 btn_bg_6 ipt_w90 left ">保存</button>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="font_cc10">
					
								<div class="form-group">
									<label class="col-sm-3 control-label">客户名称</label>
									<div class="col-sm-9">
								
									<form:input path="chargecomname" htmlEscape="false" maxlength="64" class="input-xlarge form-control ipt_w4" />
									 	<!-- <input type="text" class="form-control ipt_w4" value="" name="depName"> --> 
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">所在城市</label>
									
									<form:hidden path="city" htmlEscape="false" maxlength="200" class="input-xlarge form-control ipt_w4"/>
								
					 <div class="col-sm-9" id="city_china_val">
										<form:select class="form-control ipt_wauto province select" data-first-title="选择省" path="city1" id="shen">
										<option selected>${city1}</option>
										</form:select>
										<form:select class="form-control ipt_wauto city select" data-first-title="选择市" path="city2" id="shi">
										<option>${city2}</option>
										</form:select>
										<form:select class="form-control ipt_wauto area select" data-value="西湖区" data-first-title="选择地区" path="city3">
										<option>${city3}</option>
										</form:select>
									</div>
					
								</div>							
								<div class="form-group">
									<label class="col-sm-3 control-label">联系人</label>
									<div class="col-sm-9">
									<form:input path="contectperson" htmlEscape="false" maxlength="64" class="input-xlarge form-control ipt_w4"/>
									 
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">手机号码</label>
									<div class="col-sm-9">
									<form:input path="mobilenumber" htmlEscape="false" maxlength="64" class="input-xlarge form-control ipt_w4"/>
									 	
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">企业人数</label>
									<div class="col-sm-9">
										<form:input path="companynumber" htmlEscape="false" maxlength="200" class="input-xlarge form-control ipt_w4"/>
									 	
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">企业经营范围</label>
									<div class="col-sm-9">
									<form:input path="companyrunrange" htmlEscape="false" maxlength="64" class="input-xlarge form-control ipt_w4" placeholder="可以以逗号隔开"/>
									 	
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-3 control-label">使用状态</label>
									<div class="col-sm-9">
									 <label class="radio-inline">
									 		<form:radiobutton path="usestatus" name="RadioOptions2" id="inlineRadio1" value="0"/>试用客户
										</label>
										<label class="radio-inline">
											<form:radiobutton path="usestatus" name="RadioOptions2" id="inlineRadio2" value="1" />正式客户
											
										</label>				
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">是否审验发票</label>	
									<div class="col-sm-9">
									<form:checkbox path="auditbill" htmlEscape="false" maxlength="1" class="input-xlarge " value="1"/>										
																		
										<label class="control-label" style="margin-left:29px;margin-right:20px;">加急审核数量</label>
									 <form:select path="rapauditnum" class="form-control ipt_wauto">   
                                                <!--   <option>请选择</option>  --> 
                                                    <c:forEach items="${numList}" var="num">
											<c:if test="${num==rnum}">
												<option value="${num}" selected="true">${num}</option>
											</c:if>
											<c:if test="${num!=rnum}">
											<option value="${num}" >${num}</option>
											</c:if>
											</c:forEach> 
                                      </form:select>  
									
									</div>									
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">对接人</label>
									<div class="col-sm-9">
									       <form:select path="abutment" class="form-control ipt_wauto" style="width:195px;">   
                                                   <!--<option>请选择</option>   -->
                                                 <!--  <form:options items="${userList}" itemLabel="name" itemValue="name"/>  --> 
                                                   <c:forEach items="${userList}" var="user">
											<c:if test="${user.name==username}">
												<option value="${user.name }" selected="true">${user.name }</option>
											</c:if>
											<c:if test="${user.name!=username}">
											<option value="${user.name }" >${user.name }</option>
											</c:if>
											</c:forEach>  
                                          </form:select>  
									</div>
								</div>
								
							
				</div>
				<div class="hr15"></div>
				<div class="border_top_1">
					<div class="hr10"></div>
					<div class="panel-heading bg_col_2 panel-head-pub">
						<div class="pad_t_3">
							<span class="glyphicon glyphicon_a6 ico_pub bg_pub" aria-hidden="true"></span><span class="font_cc8 font-14">客户详细信息</span>
						</div>
						<div class="clearfix"></div>
					</div>
					
					
					<div class="font_cc10">
						<%-- <div class="left w200"><img src="${ctxStatic}/images/manager/img_4.jpg"></div> --%>
						<div class="left w600" style="margin-left:115px">

								<div class="form-group">
									<label class="col-sm-5 control-label" style="width:160px;">法人姓名</label>
									<div class="col-sm-7">
										
										<form:input path="legalname" htmlEscape="false" maxlength="64" class="input-xlarge form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-5 control-label" style="width:160px;">联系电话</label>
									<div class="col-sm-7">
									<form:input path="contectphone" htmlEscape="false" maxlength="64" class="input-xlarge form-control ipt_w4"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-5 control-label" style="width:160px;">身份证号</label>
									<div class="col-sm-7">
										<form:input path="cardnumber" htmlEscape="false" maxlength="100" class="input-xlarge form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-5 control-label" style="width:160px;">身份证附件</label>
									<div class="col-sm-7">
										<div class="uploader-demo">
											<div class="uploader-list" id="fileList"></div>
											<div class="ipt_pos_a">											
												<div class='ipt_pos_1' id="filePicker">选择图片</div>
												<button type="button" class="btn btn-default btn_w_a2 btn_bg_6 ipt_w90 ipt_pos_2">添加</button>
											
											</div>
											<div class="oldfaces">
											<c:if test="${appnameString!=''&&appnameString!=null}">
											<img src="${ctx}/newcharge/tChargecompany/getFile?fileName=${appnameString}" width="182" height="100">
											</c:if>
											</div>
										</div>
    									
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-5 control-label" style="width:160px;">营业执照号码</label>
									<div class="col-sm-7">
										<form:input path="runnumber" htmlEscape="false" maxlength="100" class="input-xlarge form-control ipt_w4"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-5 control-label" style="width:160px;">营业执照附件</label>
									<div class="col-sm-7">
									<div class="uploader-demo">
										<div class="uploader-list" id="fileList2"></div>
										<div class="ipt_pos_a">											
											<div class='ipt_pos_1' id="filePicker2">选择图片</div>
											<button type="button" class="btn btn-default btn_w_a2 btn_bg_6 ipt_w90 ipt_pos_2">添加</button>
										</div>
										<div class="oldfaces">
										<c:if test="${runnameString!=''&&runnameString!=null}">
										<img src="${ctx}/newcharge/tChargecompany/getFile?fileName=${runnameString}" width="182" height="100">
										</c:if>
										</div>
										</div>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-5 control-label" style="width:160px;">税务登记证号码</label>
									<div class="col-sm-7">
										<form:input path="taxrenum" htmlEscape="false" maxlength="100" class="input-xlarge form-control ipt_w4"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-5 control-label" style="width:160px;">税务登记证附件</label>
									<div class="col-sm-7">
									<div class="uploader-demo">
										<div class="uploader-list" id="fileList3"></div>
										<div class="ipt_pos_a">											
											<div class='ipt_pos_1' id="filePicker3">选择图片</div>
											<button type="button" class="btn btn-default btn_w_a2 btn_bg_6 ipt_w90 ipt_pos_2">添加</button>
										</div>
										<div class="oldfaces">
										<c:if test="${runnameString!=''&&runnameString!=null}">
										<img src="${ctx}/newcharge/tChargecompany/getFile?fileName=${taxnameString}" width="182" height="100">
										</c:if>
										</div>
										</div>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-5 control-label" style="width:160px;">组织机构代码</label>
									<div class="col-sm-7">
										<form:input path="organcode" htmlEscape="false" maxlength="100" class="input-xlarge form-control ipt_w4"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-5 control-label" style="width:160px;">组织机构代码证附件</label>
									<div class="col-sm-7">
										<div class="uploader-demo">
											<div class="uploader-list" id="fileList4"></div>
											<div class="ipt_pos_a">											
												<div class='ipt_pos_1' id="filePicker4">选择图片</div>
												<button type="button" class="btn btn-default btn_w_a2 btn_bg_6 ipt_w90 ipt_pos_2">添加</button>
											</div>
											
											<div class="oldfaces">
											<c:if test="${codnameString!=''&&codnameString!=null}">
											<img src="${ctx}/newcharge/tChargecompany/getFile?fileName=${codnameString}" width="182" height="100">
											</c:if>
											</div>
										
										</div>
									</div>
								</div>				
						</div>
						<div class="clearfix"></div>
						<div class="hr20"></div>
					</div>
					<!--设置账号-->
<div id="dialog_infos" title="设置账号">
	<div class="font-14 w400 mar_auto">
		<div class="hr15"></div>
		   
			<div class="form-group">
				<label class="control-label w100 left mar-rig10" style="text-align:right;">用户登录名</label>				
				<!--  <input type="text" class="form-control ipt_w4 depNameed" value="${loginname}" name="depNameed" id="depNameed" readonly="true">
				 				 -->
				 				<label >${loginname}</label>		
			</div>
			<div class="form-group">
				<label class="control-label w100 left mar-rig10" style="text-align:right;">密码</label>				
				 <input type="password" class="form-control ipt_w4" value="" name="pwd" id="pwd">				
			</div>
			
			<div class="clearfix"></div>
			<div class="hr15"></div>
			<div class="text-center">
			 
				<button type="button" class=" btn btn-default btn_w_a2 btn_bg_6 ipt_w90 right save">保存</button>
			</div>
			<div class="hr15"></div>
	
		
	</div>
</div><!-- 设置账号到这里 -->
					
					</form:form>
				</div>
				<div class="hr15"></div>
			</div>		
		</div>
	</div>
</div>

<script type="text/javascript">
$(function(){
	//设置账号
	$( "#dialog_infos" ).dialog({
		autoOpen: false,
		width: 474,
		modal: true
	});	
	$( ".dialog_infos" ).click(function( event ) {
		//$("#dialog_infos").find("input").val("");
		$( "#dialog_infos" ).dialog( "open" );
		event.preventDefault();
	});
	$( ".save" ).click(function( event ) {
		$( "#dialog_infos" ).dialog( "close" );
		event.preventDefault();
	});
})
//城市
$.cxSelect.defaults.url = '${ctxStatic}/cityData.min.json';
$('#city_china_val').cxSelect({
  selects: ['province', 'city', 'area'],
  nodata: 'none'
});
</script>
<script type="text/javascript">
var ctx = "${ctx}";
 $(function(){
	
	/*  $(".add").on("click",function(){
		 var c=$("#depNameed").val();
		 var pwd=$("#pwd").val();
		 var t=$("#filetype").val();

		 $("#inputForm").ajaxSubmit({
	            type: 'post',
	            url: " ${pageContext.request.contextPath}/a/newcharge/tChargecompany/update2?depNameed="+c+"&pwd="+pwd+"&typename="+t ,
	            data: $("#inputForm").serialize(),
	            success: function(data){
	            	if(data=="1"){
	            		messagePop("保存成功！");
	                	//window.location="${pageContext.request.contextPath}/a/clientManage_menue";newcharge/tChargecompany
	                	window.location="${pageContext.request.contextPath}/a/newcharge/tChargecompany/menue";
	            	}
	                $( "#inputForm").resetForm();
	            },
	            error: function(XmlHttpRequest, textStatus, errorThrown){
	            	messagePop( "error");
	            }                
	        })
		 
		 
	 }); */
	 
	 $(".adddd").on("click",function(){
			// validateForm();
			 var c=$("#depNameed").val();
			 var pwd=$("#pwd").val();
			 var t=$("#filetype").val();
			 var shen=$("#shen").val();
			 var shi=$("#shi").val();
			 var inlineRadio1=$("#inlineRadio1:checked");
			 var inlineRadio2=$("#inlineRadio2:checked");
			// var name = $("[name=RadioOptions2]:checked");
			// console.log(name.length);
			// alert(inlineRadio1)
			 //alert(shen+'aaa');
			// alert(c);
			 if(shi!=''&&shen!=''){
				 if(inlineRadio1.length==1||inlineRadio2.length==1){
				 
				 //if(c!=''&&pwd!=''){
			 $("#inputForm").ajaxSubmit({
		            type: 'post',
		            url: " ${pageContext.request.contextPath}/a/newcharge/tChargecompany/update2?depNameed="+c+"&pwd="+pwd+"&typename="+t ,
		            data: $("#inputForm").serialize(),
		            success: function(data){
		            	if(data=="1"){
		            		messagePop("保存成功！");
		                	//window.location="${pageContext.request.contextPath}/a/clientManage_menue";newcharge/tChargecompany
		                	window.location="${pageContext.request.contextPath}/a/newcharge/tChargecompany/menue";
		            	}
		                $( "#inputForm").resetForm();
		            },
		            error: function(XmlHttpRequest, textStatus, errorThrown){
		            	messagePop( "error");
		            }                
		        })
				 //}else{
					// messagePop("请完设置账号用户名密码");
				 //}
				 
				 }else{ messagePop("请选择使用状态");}
				 
			 }else{messagePop("请完整选择省市");}
			 
		 });
 })
</script>
<script type="text/javascript">
 
 $(function(){
	 $(".fff").on("click",function(){
		var c=$(".depNameed").val();
		/* alert(c); */
	 });
 })
	function messagePop(str){//用于显示提示信息
		$(".message-pop").fadeIn();
		$(".message-pop").children("span").html(str);
		setTimeout(function(){
			$(".message-pop").fadeOut();
		},2000)
}
</script>
<script type="text/javascript">

$().ready(function() {
	// 在键盘按下并释放及提交后验证提交表单
	  $("#inputForm").validate({
	    rules: {
	    	chargecomname: "required",
	    	contectperson: "required",
	    	mobilenumber: {
	    		required: true,
				number: true,
				minlength : 11,
				isMobile : true
			   },
	    	companynumber: "required",
	    	companyrunrange: "required"
	    		
	    		
	    },
	    messages: {
	    	chargecomname: "请输入客户名称",
	    	contectperson: "请输入联系人",
	    	mobilenumber: {
				required: "请输入手机号",
				number:"请输入手机号",
				minlength: "手机号不能小于11个字符",
				isMobile : "请正确填您的手机号码"
			},
	    	companynumber: "请输入企业人数",
	    	companyrunrange: "请输入企业经营范围"
	    	
	    },
	    submitHandler:function(form){
	    	var c=$("#depNameed").val();
			 var pwd=$("#pwd").val();
			 var t=$("#filetype").val();
			 var shen=$("#shen").val();
			 var shi=$("#shi").val();
			 var inlineRadio1=$("#inlineRadio1:checked");
			 var inlineRadio2=$("#inlineRadio2:checked");
			
			 if(shi!=''&&shen!=''){
				 if(inlineRadio1.length==1||inlineRadio2.length==1){
			 $("#inputForm").ajaxSubmit({
		            type: 'post',
		            url: " ${pageContext.request.contextPath}/a/newcharge/tChargecompany/update2?depNameed="+c+"&pwd="+pwd+"&typename="+t ,
		            data: $("#inputForm").serialize(),
		            success: function(data){
		            	if(data=="1"){
		            		messagePop("保存成功！");
		                	//window.location="${pageContext.request.contextPath}/a/clientManage_menue";newcharge/tChargecompany
		                	
		                	setTimeout(function(){
		                		window.location="${pageContext.request.contextPath}/a/newcharge/tChargecompany/menue";
		                	},500);
		                	messagePop("保存成功！");
		                	//window.location="${pageContext.request.contextPath}/a/newcharge/tChargecompany/menue";
		            	}
		               // $( "#inputForm").resetForm();
		            },
		            error: function(XmlHttpRequest, textStatus, errorThrown){
		            	messagePop( "error");
		            }                
		        })
		        $( "#inputForm").resetForm();
				 }else{ messagePop("请选择使用状态");}
				 
			 }else{messagePop("请完整选择省市");}
        }    
	    
	    
	    
		});
	});
jQuery.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "请正确填写您的手机号码");
jQuery.validator.addMethod("iscard", function(value, element) {
    var card =/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
    return this.optional(element) || card.test(value);
}, "请正确填写您的身份证号码");
	  
</script>
</body>
</html>
