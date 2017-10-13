
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<div id="dialog_add" title="客户信息" style='display:none;'>
	<div class="dialog_client">
		<div class="t_pane_a1 radius_5">
			<ul class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active"><a href="#tab_g1" aria-controls="tab_g1" role="tab" data-toggle="tab">基本资料</a></li>
				<li role="presentation"><a href="#tab_g2" aria-controls="tab_g2" role="tab" data-toggle="tab">联系方式</a></li>
				<li role="presentation"><a href="#tab_g3" aria-controls="tab_g3" role="tab" data-toggle="tab">税务资料</a></li>
				<li role="presentation"><a href="#tab_g4" aria-controls="tab_g4" role="tab" data-toggle="tab" class="fj">附件</a></li>
				<li role="presentation"><a href="#tab_g5" aria-controls="tab_g5" role="tab" data-toggle="tab" class="khcz" >客户查账</a></li>
			</ul>
			<form:form id="inputFormNew" modelAttribute="tCustomer" method="post" class="form-horizontal">
			<form:hidden id="id" path="id" class="ycc"/>
			<div class="tab-content border_n3">
				<div role="tabpanel" class="tab-pane active" id="tab_g1">
					<!--基本资料-->
					<div class="client_inner2">
						<div class="client_form1">
						<input type="hidden" id="array" name="array">
								<div class="form-group">
									<label class="col-sm-2 control-label">公司名称</label>
									
									<div class="col-sm-4">
									<form:input id="customerName" path="customerName" htmlEscape="false" maxlength="100" class="form-control ipt_w4"/>
									<span class="help-inline"><font color="red">*</font> </span>
									</div>

									<label class="col-sm-2 control-label">营业执照号</label>
									<div class="col-sm-4">
										<form:input path="businessLicenseNumber" htmlEscape="false" maxlength="20" class="form-control ipt_w4"/>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label">启用期间</label>
									<div class="col-sm-4" id="datediv">
										<input type='hidden' name='initperiod' />
									    <input type='hidden' value='' id="currentPeriod"/>
										<input type='hidden' value='' id="initPeriod"/>
										<select class='form-control ipt_wauto' style='margin-right:8px;' id='year_span'>
										</select>
										<select class='form-control ipt_wauto' id='month_span'>
										</select>
										<span class="help-inline"><font color="red">*</font> </span>
									</div>

									<label class="col-sm-2 control-label">组织机构代码号</label>
									<div class="col-sm-4">
										<form:input path="organizationCode" htmlEscape="false" maxlength="20" class="form-control ipt_w4"/>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label">备注</label>
									<div class="col-sm-10">
										<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="form-control textarea_n3"/>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label">会计制度</label>
									<div class="col-sm-10" id="systemdiv">
									<input type="hidden" value='' id="systemid">
									<form:select path="system" htmlEscape="false" class="form-control ipt_w4"> 
								 		<form:option value="1">2013小企业会计准则</form:option>
								 		<form:option value="2">2007企业会计准则</form:option>
					             	</form:select><em>*</em>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label">记账人</label>
									<div class="col-sm-10">
										<%-- <form:input id="supervisor" path="supervisor" htmlEscape="false" maxlength="100" class="form-control ipt_w4"/>
										<span class="help-inline"><font color="red">*</font> </span> --%>
										<select id="supervisor" name="supervisor" class="form-control ipt_w4">
											<c:forEach items="${userlist}" var="user">
											<c:if test="${user.name==username}">
												<option value="${user.id }" selected="true">${user.name }</option>
											</c:if>
											<c:if test="${user.name!=username}">
											<option value="${user.id }" >${user.name }</option>
											</c:if>
											</c:forEach>											
										</select>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<span class="right">
										<!-- 
										<input id="btnCancel" class="btn btn-default btn_w_a btn_bg_4 mar-rig10" type="button" value="取 消"/>
										 -->
										<button id="creat_per" type="button" class="btn btn-default btn_w_a btn_bg_2 ">确定</button>
										</span>
										<div class="right"></div>
									</div>
								</div>
						
						</div>
					</div>
				</div>
				<!--联系方式-->
				<div role="tabpanel" class="tab-pane" id="tab_g2">
					<div class="client_inner2">
						<div class="client_form2">
						
								<div class="form-group">
									<label class="col-sm-3 control-label">联系人</label>
									<div class="col-sm-9">
										<form:input path="contactPerson" htmlEscape="false" maxlength="20" class="form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">手机号码</label>
									<div class="col-sm-9">
									<form:input path="mobilePhone" htmlEscape="false" maxlength="20" class="form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">E-mail</label>
									<div class="col-sm-9">
										<form:input path="eMail" htmlEscape="false" maxlength="50" class="form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">QQ号</label>
									<div class="col-sm-9">
										<form:input path="qq" htmlEscape="false" maxlength="20" class="form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">详细地址</label>
									<div class="col-sm-9">
										<form:input path="detailedAddress" htmlEscape="false" maxlength="100" class="form-control ipt_w5"/>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-3 col-sm-9">
										<span class="right">
										<!--  
										<input id="btnCancel1" class="btn btn-default btn_w_a btn_bg_4 mar-rig10" type="button" value="取 消"/>&nbsp;
										 -->
										<button id="creat_per1" type="button" class="btn btn-default btn_w_a btn_bg_2 ">确定</button>
										</span>
										<div class="right"></div>
									</div>
								</div>
					
						</div>
					</div>
				</div>
				<div role="tabpanel" class="tab-pane" id="tab_g3">
					<!--税务资料-->
					<div class="client_inner2">
						<div class="client_form3">
						
								<div class="form-group">
									<label class="col-sm-3 control-label">增值税性质</label>
									<div class="col-sm-9">
									 	<label class="radio-inline">
									 		<form:radiobutton path="valueAddedTax" value="0"/>小型规模纳税人
										</label>
										<label class="radio-inline">
											<form:radiobutton path="valueAddedTax" value="1" />一般纳税人
										</label>										
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">国家税务登记证号</label>
									<div class="col-sm-9">
										<form:input path="stateTaxRegistrationNumber" htmlEscape="false" maxlength="20" class="form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">专管员</label>
									<div class="col-sm-9">
										<form:input path="statetaxregistrationadmin" htmlEscape="false" maxlength="20" class="form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">地税税务登记证号</label>
									<div class="col-sm-9">
										<form:input path="localTaxRegistrationNumber" htmlEscape="false" maxlength="20" class="form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">专管员</label>
									<div class="col-sm-9">
										<form:input path="localtaxregistrationadmin" htmlEscape="false" maxlength="20" class="form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">证书到期时间</label>
									<div class="col-sm-9">
									 	<input name="certificatesmature" type="text" class="form-control ipt_w4" id="datepicker2" />
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-3 col-sm-9">
										<span class="right">
										<!-- 
											<input id="btnCancel2" class="btn btn-default btn_w_a btn_bg_4 mar-rig10" type="button" value="取 消"/>
											 -->
											&nbsp;
											<button id="creat_per2" type="button" class="btn btn-default btn_w_a btn_bg_2 ">确定</button>		
										</span>
										<div class="right"></div>
									</div>
								</div>
						</div>
					</div>
				</div>
				</form:form>
				<div role="tabpanel" class="tab-pane" id="tab_g4">
					<!--附件-->
					<div class="client_inner2">
						<div class="client_form4">
							<form id="upload" enctype="multipart/form-data" method="post">
								<div class="form-group">
									<label class="col-sm-3 control-label">附件名称</label>
									<div class="col-sm-9">
									 	<input id="appendix" name="appendix" type="text" class="form-control ipt_w6" placeholder="" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">附件</label>
									<div class="col-sm-9">
									 	<input id="filetype" type="file" name="files" class="mar-top5" placeholder="" />
									 	<!--  
									 	<input type="file" name="files1" class="mar-top5" placeholder="" />
									 	-->
									 	<div class="mar-top5">支持txt xls doc gif pdf等文件格式，大小不超过500K</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-3 col-sm-9">
										<span class="right">
										<button id="uploadbutton" type="button" class="btn btn_w_a btn_bg_2 btn_up4 mar-rig10">上传</button>
										<button type="reset" class="btn btn_w_a btn_bg_4">重置</button>
										</span>
										<div class="right"></div>
									</div>
								</div>
							</form>
						</div>
					</div>
					<div class="tit_sc3 font-14 border_top1"><b>附件列表</b></div>
					<div class='min-h40'>
					<table id="uptable" class="table table-bordered table_list4">
						<thead>
							<tr>
							<th>名称</th>
							<th>上传人</th>
							<th>上传时间</th>
							<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<!-- 动态添加的标签 -->
						</tbody>
					</table>
					</div>
					<div class="pad_10_3">
						<div class="page_cc">共2条记录
							<button class="btn_p7"></button>
							<span class="pad_l_r5">1</span>
							<button class="btn_p8"></button>
						</div>
					</div>
				</div>
				<div role="tabpanel" class="tab-pane" id="tab_g5">
					<!--客户查账-->
					<div class="client_inner2">
						<div class="client_form5 mar_auto">
							<div class="left w250"><img src="${ctxStatic}/images/gzpt/img_31.jpg" /></div>
							<div class="right w320">
								<div class="font-24">企业查账账号</div>
								<div class="hr20"></div>
								<div class="">微信扫描左侧二维码，关注即可实时查账</div>
								<div class="hr20"></div>
								<form class="form-horizontal" action="${ctx}/workterrace/chargeToAccount/updatepwd" method="post" id="wechatform">
								<input type="hidden" id="idi" name="fdbid"/>
								  <div class="form-group">
								    <label class="col-sm-3 control-label text-left">用户名</label>
								    <div class="col-sm-5">
								    <input type="hidden" id="username" name="username"/>
								      <p class="form-control-static" id="usernamew">cuihuohan</p>
								    </div>
								  </div>
								  <div class="form-group">
								    <label class="col-sm-3 control-label text-left">密　码</label>
								    <div class="col-sm-5">
								      <input type="text" class="form-control ipt_w5" value="" id="pwd" name="password"/>
								    </div>
								    <div class="col-sm-3">
								    	<div class='act_buttons'>
								    		<button type="button" class="btn btn-default btn_w_a btn_bg_2  btn_opt1">重置密码</button>
								    		<button type="button" class="btn btn-default btn_w_a btn_bg_2 subbutton btn_opt2" style='display:none;'>保存</button>
								    	</div>
								    </div>
								  </div>
								</form>							
							</div>
							<div class="clearfix"></div>
							<div class="hr20"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
</div>

<script>
$(".act_buttons .btn_opt1").on('click',function(){
	$(this).hide();
	$(".act_buttons .btn_opt2").show();
	$("#pwd").attr("style","border:1px #999999 solid")

})
/*
function date(){
var dateYear = new Date();
var myers = $("#year_span");
myers.empty();
var nowyear=dateYear.getFullYear();
var oldyear=nowyear-5;
var newyears=nowyear+5;
var currentPeriod=$("#currentPeriod").val();//获取当前账期
var initPeriod=$("#initPeriod").val();//获取起始账期

var yearpreiod=initPeriod.substring(0,4);
var monthperiod=initPeriod.substring(4,6);
for(var i=oldyear;i<newyears;i++) {
	if(i==yearpreiod){
		var option = $("<option selected>").text(i).val(i)
	}else{
		var option = $("<option>").text(i).val(i)
	}
	myers.append(option);
}

var nowmonth=dateYear.getMonth()+1;
var months = $("#month_span");
months.empty();
for(var j=1;j<13;j++) {	
	//alert(typeof j);
	if(j==monthperiod){
		if(j<10){
			var option = $("<option selected>").text('0'+j).val('0'+j);
		}else{
			var option = $("<option selected>").text(j).val(j);
		}
		
	}else{
		if(j<10){
			var option = $("<option>").text('0'+j).val('0'+j);
		}else{
			var option = $("<option>").text(j).val(j);
		}
		
	}
	months.append(option);
}

$("[name=initperiod]").val(function(){
	var years=$('#year_span').val();
	var months=$('#month_span').val();
	return years+months;
});
$('#year_span').change(function(){
	var years=$(this).val();
	var months=$('#month_span').val()
	$("[name=initperiod]").val(function(){
		console.info(years+months);
		return years+months;
	});
})
$('#month_span').change(function(){
	var years=$('#year_span').val();
	var months=$(this).val();	
	$("[name=initperiod]").val(function(){
		console.info(years+months);
		return years+months;
	});
})
}*/

function date(){
	var dateYear = new Date();
	var myers = $("#year_span");
	myers.empty();
	var nowyear=dateYear.getFullYear();
	var oldyear=nowyear-5;
	var newyears=nowyear+5;
	var currentPeriod=$("#currentPeriod").val();//获取当前账期
	//thcurrentPeriod=currentPeriod;
	//alert(currentPeriod+"得到了幺")
	var initPeriod=$("#initPeriod").val();//获取起始账期
	//alert(initPeriod+'dddd');
	var yearpreiod=initPeriod.substring(0,4);
	var monthperiod=initPeriod.substring(4,6);
	//alert(11);
	if(currentPeriod==''){
		
		if($("#systemdiv select").length == 0){
			$("#systemdiv span").remove();
			var selectHtmlsys="<select id='system' name='system' htmlEscape='false' class='form-control ipt_w4'> \
	 		<option value='1'>2013小企业会计准则</option>\
	 		<option value='2'>2007企业会计准则</option>\
     	      </select><em>*</em>";
			$("#systemdiv").append(selectHtmlsys);
			
		}
		
		
		
		if($("#datediv select").length == 0) {
			$("#datediv span").remove();
			var first=$("#datediv input[name='initperiod']");
			if(first.length==0){
				var firstinput="<input type='hidden' name='initperiod'/>";
				$("#datediv").append(firstinput);
			}
			var selectHtml = "<select class='form-control ipt_wauto' style='margin-right:8px;' id='year_span'> \
								</select> \
								<select class='form-control ipt_wauto' id='month_span'> \
								</select>";
			$("#datediv").append(selectHtml);
		}
		var myers = $("#year_span");
	for(var i=oldyear;i<newyears;i++) {
		if(i==yearpreiod){
			var option = $("<option selected>").text(i).val(i)
		}else{
			var option = $("<option>").text(i).val(i)
		}
		myers.append(option);
	}
	var nowmonth=dateYear.getMonth()+1;
	var months = $("#month_span");
	months.empty();
	for(var j=1;j<13;j++) {	
		//alert(typeof j);
		if(j==monthperiod){
			if(j<10){
				var option = $("<option selected>").text('0'+j).val('0'+j);
			}else{
				var option = $("<option selected>").text(j).val(j);
			}
			
		}else{
			if(j<10){
				var option = $("<option>").text('0'+j).val('0'+j);
			}else{
				var option = $("<option>").text(j).val(j);
			}
			
		}
		months.append(option);
	}
	}else{
		$("#datediv :not('input')").remove();
		$("#datediv input[name='initperiod']").remove();
		$("#datediv").append("<span style='line-height:24px;'>"+initPeriod+"</span>");
		/*$("#datediv").html(initPeriod);*/
		
		$("#systemdiv :not('input')").remove();
		//---------企业准则
		//$("#systemdiv").text($("#systemid").val());
		$("#systemdiv").append("<span style='line-height:24px;'>"+$("#systemid").val()+"</span>");
	}
		
	$("[name=initperiod]").val(function(){
		var years=$('#year_span').val();
		var months=$('#month_span').val();
		return years+months;
	});
	$('#year_span').change(function(){
		var years=$(this).val();
		var months=$('#month_span').val()
		$("[name=initperiod]").val(function(){
			console.info(years+months);
			return years+months;
		});
	})
	$('#month_span').change(function(){
		var years=$('#year_span').val();
		var months=$(this).val();	
		$("[name=initperiod]").val(function(){
			console.info(years+months);
			return years+months;
		});
	})
}
</script>
<script>







$(function(){
	//alert($("#dj").length);
	$(".khcz").on("click",function(){//点击的时候填充微信企业账号
		var id=$(".ycc").val();
		//alert("11");
		if(id.length==0){
			$("#khspan").html("这是新加客户，请到工作台设置企业查找账号");
		}else{
		$.ajax({
	        type: "POST",//使用post方法访问后台
	        url: "${ctx}/workterrace/chargeToAccount/fillcount",//要访问的后台地址  
	        data: "id="+id,
	        success: function(wechet){
	        	var username=wechet.userName;var password=wechet.plainTextPassword;
	        	//alert(password);
	        	//alert(username+"###"+password);
	        	$("#idi").val(id);
	        	$("#username").val(username);//给隐藏的用户名赋值
	        	$("#usernamew").html(username);//给用户名
	        	$("#pwd").val(password);
	        }
	})}
	});
	//})
	
	//点击附件列表附件
	$(".fj").on("click",function(){
		var idthis=$(".ycc").val().toString();
		
		$("table#uptable>tbody").empty();
		//ajax
		$.ajax({
        type: "POST",
        url: "${ctx}/customer/tAppendix/findBytcuid",
        data:"id="+idthis,
        success: function(data){
        	var list=eval(data);
			$.each(list,function(n,val){
				$("table#uptable>tbody").append("<tr>"+
				"<td>"+val.appendixName+"</td>"+
				"<td>"+val.upPeople+"</td>"+"<td>"+val.createDate+"</td>"+
				"<td>"+
				"<a class='btn btn-default btn_n4 bg_p6 btn_i6 pad_10' href='${ctx}/customer/tCustomer/downFile?fileName="+val.appendixTypeName+"'></a>"+
				"<a id='"+val.id+"' class='btn btn-default btn_n4 bg_p6 btn_i4 pad_10' onclick='buttondelete("+val.id+")'></a>"+
				"</td>"+
				"</tr>"
				)
			}) }
        });
	})
		//ajax结束
	
	
	$(".subbutton").click(function() {
		//alert(1)
		$("#wechatform").ajaxSubmit({
	       type: 'post',
	       url: "${ctx}/workterrace/chargeToAccount/updatepwd" ,
	       data: $("#wechatform").serialize(),
	       success: function(data){
	       	if(data=="1"){
	       		messagePop("重置密码成功！");
	       		$(".act_buttons .btn_opt2").hide();
	       		$(".act_buttons .btn_opt1").show();
	       		$("#pwd").attr("style","border:0");
	           //	window.location="${pageContext.request.contextPath}/a/workterrace/chargeToAccount";
	       	}
	          // $( "#inputForm").resetForm();
	       },
	       error: function(XmlHttpRequest, textStatus, errorThrown){
	    	   messagePop( "error");
	       }                
	   })
	   
	});
})

</script>