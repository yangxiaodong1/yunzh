<%@ page contentType="text/html;charset=UTF-8" %><%@ include file="/WEB-INF/views/include/taglib.jsp"%><%@page import="com.thinkgem.jeesite.modules.sys.entity.User"%><%@page import="com.thinkgem.jeesite.modules.sys.utils.UserUtils"%> 
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>首页-芸豆会计</title>
<script src="${ctxStatic}/jquery/jquery-1.11.2.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_2.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery.form.js"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctxStatic}/jquery/jquery.ztree.core-3.5.js"></script>
<link rel="stylesheet" href="${ctxStatic}/zTreeStyle/zTreeStyle.css" type="text/css">
<link href="${ctxStatic}/css/master_2.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/static/jquery/jquery-jtemplates.js" type="text/javascript"></script>
<script type="text/javascript">

</script>
</head>
<body>
  <div class="message-pop"><span>保存成功！</span></div> 
	<div class="main_index3">
		<div class="pane_main3">
			<div class="user_info3">
				<div class="face_imgs left">
<div class="border_i2 radius_imgs left pad_5"><!--
<img src="${ctx}/sys/user/imageGet?fileName=${currentUser.photo}" width="88" height="88" class="radius_imgs" />-->
<!-- 	 <img src="${ctxStatic}/images/gzpt/face1.jpg" width="70" height="70" class="radius_imgs"/> 
 -->
 
               <c:if test="${currentUser.photo!=null&&currentUser.photo!=''}">
				 <img src="${ctx}/sys/user/imageGet?fileName=${currentUser.photo}" width="88" height="88" class="radius_imgs" />
				</c:if>
				<c:if test="${currentUser.photo==null||currentUser.photo==''}">
				<img src="${ctxStatic}/images/img-1.png" width="88" height="88" /> 
				</c:if>

</div>
<div class="clearfix"></div>
				</div>
				<div class="info_txt3">
					<h1>${currentUser.name}，${message}</h1>
					<!-- <p>今日语录“${dayRead}”</p> -->
					<p>今日语录“${title}”</p>
				</div>
				<div class="btn btn_item3 dialog_add" >新增客户</div>
				<div class="clearfix"></div>
			</div>
			<div class="hr20"></div>
			<div class="tit_gz2 ">汇总</div>
			<table class="table table_h2">
			<tbody>
				<tr>
					<td class="p_td3" width="167">
					<td>记账</td>
					<td>报税</td>
					<td>跟进</td>
					<td>客户数</td>
				</tr>

				<tr>
					<td class="p_td3">
<p>今日</p>

					</td>
					<td><span class="font-24 font_cc1">${vo.montharray1}</span></td>
					<td><span class="font-24 font_cc2">${vo.montharray4}</span></td>
					<td><span class="font-24 font_cc3">${vo.montharray7}</span></td>
					<td style="vertical-align:middle;text-align:center;" rowspan=3><span class="font-36 font_cc4">${vo.montharray9}</span></td>
				</tr>

				<tr>
					<td class="p_td3">

<p>昨日</p>

					</td>
					<td><span class="font-24 font_cc1">${vo.montharray2}</span></td>
					<td><span class="font-24 font_cc2">${vo.montharray5}</span></td>
					<td><span class="font-24 font_cc3">${vo.montharray8}</span></td>

				</tr>

				<tr>
					<td class="p_td3">

<p>未处理</p>
					</td>
					<td><span class="font-24 font_cc1">${vo.montharray3}</span></td>
					<td><span class="font-24 font_cc2">${vo.montharray6}</span></td>
					<td><span class="font-24 font_cc3"></span></td>

				</tr>
			</tbody>
			</table>

			<div class="pane_s3 pane_bor3 left radius_5">
				<div class="tit_w5">个人提醒
					<span class="btn_more2"><a href="${ctx}/workterrace/tPersonalReminder/">更多</a></span>
					<div class="clearfix"></div>
				</div>
				<table class="table table_h3">
					<tbody id="tPersonalReminderList">
						<c:forEach items="${pageTPersonalReminder.list}" var="tPersonalReminder"><tr>
						<td width="125"><fmt:formatDate value="${tPersonalReminder.warnTime}" pattern="yyyy-MM-dd HH:mm"/></td>
							<td width="50">
							<c:choose>
		 						 <c:when test="${tPersonalReminder.degreeImportance=='0'}">
							      低
							    </c:when>
							     <c:when test="${tPersonalReminder.degreeImportance=='1'}">
							      中
							    </c:when>
							     <c:when test="${tPersonalReminder.degreeImportance=='2'}">
							     高
							    </c:when>
								 <c:otherwise>
							    </c:otherwise>
							</c:choose>
							</td>
							<c:if test="${fn:length(tPersonalReminder.matters)>24}"><td style='text-align:left'>${fn:substring(tPersonalReminder.matters, 0, 24)}...</td></c:if>
 							<c:if test="${fn:length(tPersonalReminder.matters)<=24}"><td style='text-align:left'>${tPersonalReminder.matters}</td></c:if>
							<td width="60"><div id="${tPersonalReminder.id}" onclick="updatastate(this)" style="cursor:pointer;" title="确认完成" class="btn_d3"></div></td>
						</tr></c:forEach>
					</tbody>
				</table>
			</div>

			<div class="pane_s3 pane_bor3 right radius_5">
				<div class="tit_w5">消息（${pageTsysnews.count}）
					<span class="btn_more2"><a href="${ctx}/inspection/setup/sysMessage/findListByUserId">更多</a></span>
					<div class="clearfix"></div>
				</div>
				<div class="body_s4">
					<!--  <table border="0" width="100%">-->
					<table border="0" width="100%" class="table table_h3">
					
						<c:if test="${pageTsysnews.count>0}">
						<c:forEach items="${pageTsysnews.list}" var="Tsysnews"><tr>
						<td width="125"><fmt:formatDate value="${Tsysnews.sendtime}" pattern="yyyy-MM-dd HH:mm"/></td>
							
							<c:if test="${fn:length(Tsysnews.title)>32}"><td style='text-align:left'>${fn:substring(Tsysnews.title, 0, 32)}...</td></c:if>
 							<c:if test="${fn:length(Tsysnews.title)<=32}"><td style='text-align:left'>${Tsysnews.title}</td></c:if>
					
						</tr>
						</c:forEach>
						</c:if>
						<c:if test="${pageTsysnews.count<1}">
						<tr>
						<td height="120"><br><div class="btn btn_j2">您还没有收到信息哦！</div></td>
						</tr>
						</c:if>
						
					</table>
				</div>
			</div>

			<div class="clearfix"></div>

			
		</div>
		<div class="hr20"></div>
			<script src='${ctxStatic}/js/pub_foot.js'></script>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<!-- 新增客户 -->
<div id="dialog_add" title="客户信息">
	<div class="dialog_client">
		<div class="t_pane_a1 radius_5">
			<ul class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active"><a href="#tab_g1" aria-controls="tab_g1" role="tab" data-toggle="tab">基本资料</a></li>
				<li role="presentation"><a href="#tab_g2" aria-controls="tab_g2" role="tab" data-toggle="tab">联系方式</a></li>
				<li role="presentation"><a href="#tab_g3" aria-controls="tab_g3" role="tab" data-toggle="tab">税务资料</a></li>
				<li role="presentation"><a href="#tab_g4" aria-controls="tab_g4" role="tab" data-toggle="tab">附件</a></li>
				<li role="presentation"><a href="#tab_g5" aria-controls="tab_g5" role="tab" data-toggle="tab" class="khcz">客户查账</a></li>
			</ul>
			<form:form id="inputForm" modelAttribute="tCustomer" method="post" class="form-horizontal">
			<form:hidden id="id" path="id" class="yc"/>
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
									<div class="col-sm-4">
										<input type='hidden' name='initperiod'>
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
									<div class="col-sm-10">
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
										
										<!--  <input id="btnCancel" class="btn btn-default btn_w_a btn_bg_4 mar-rig10" type="button" value="取 消"/>-->
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
										
										<!-- <input id="btnCancel1" class="btn btn-default btn_w_a btn_bg_4 mar-rig10" type="button" value="取 消"/> -->
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
									 		<form:radiobutton path="valueAddedTax" value="0" checked="true"/>小型规模纳税人
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
									 <input name="certificatesmatureNew" type="text" class="form-control ipt_w4" id="datepicker2" /> 
									 <!-- <input name="certificatesmature" type="text" class="form-control ipt_w4" id="datepicker2" /> -->	
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-3 col-sm-9">
										<span class="right">											
											<!--  <input id="btnCancel2" class="btn btn-default btn_w_a btn_bg_4 mar-rig10" type="button" value="取 消"/>-->
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
										<div class="right">
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
					<div class="tit_sc3 font-14 border_top1"><b>附件列表</b></div>
					<div class="min-h40">
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
					<div class="col-sm-offset-2 col-sm-10" style="margin-top:10px;">
						<span class="right">
						
						<!--  <input id="btnCancel" class="btn btn-default btn_w_a btn_bg_4 mar-rig10" type="button" value="取 消"/>-->
						<button id="creat_per3" type="button" class="btn btn-default btn_w_a btn_bg_2 ">确定</button>
						</span>
						<div class="hr20"></div>
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
								<span id="khspan">
								<form class="form-horizontal" action="${ctx}/workterrace/chargeToAccount/updatepwd" method="post">
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
								      <input type="text" class="form-control ipt_w5" placeholder="" id="pwd" name="password"/>
								    </div>
								    <div class="col-sm-3">
								    	<div class='act_buttons'>
								    		<button type="button" class="btn btn-default btn_w_a btn_bg_2 subbutton btn_opt1">重置密码</button>
								    		<button type="button" class="btn btn-default btn_w_a btn_bg_2 subbutton btn_opt2" style='display:none;'>保存</button>
								    	</div>
								    </div>
								  </div>
								</form>	
								</span>							
							</div>
							<div class="clearfix"></div>
							<div class="hr20"></div>
						</div>
					</div>
					<!-- 新添加的确定 -->
					<div class="form-group" style="margin:0 20px 20px;">
						<div class="col-sm-offset-3 col-sm-9">
							<span class="right">
							<!--  <input id="btnCancel2" class="btn btn-default btn_w_a btn_bg_4 mar-rig10" type="button" value="取 消"/>-->
								<button id="creat_per4" type="button" class="btn btn-default btn_w_a btn_bg_2 ">确定</button>&nbsp;
								
							</span>
							<div class="right"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
</div>
<!-- 新增客户结束 -->
<script>
$(".act_buttons .btn_opt1").on('click',function(){
	$(this).hide();
	$(".act_buttons .btn_opt2").show();
	$("#pwd").attr("style","border:1px #999999 solid")

})
var dateYear = new Date();
var myers = $("#year_span");
myers.empty();
var nowyear=dateYear.getFullYear();
var oldyear=nowyear-5;
var newyears=nowyear+5;
for(var i=oldyear;i<newyears;i++) {	
	if(i==nowyear){
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
	if(j==nowmonth){
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
//alert(nowmonth);
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
/**20160130**/
$(function(){
	$.ajax({
        type: "POST",//使用post方法访问后台
        url: "${ctx}/customer/tAppendix/findByState",//要访问的后台地址  
        success: function(data){
        	var list=eval(data);
			$.each(list,function(n,val){
				$("table#uptable>tbody").append("<tr>"+
				"<td>"+val.appendixName+"</td>"+
				"<td>"+val.upPeople+"</td>"+"<td>2015-12-07</td>"+
				"<td>"+
				"<a class='btn btn-default btn_n4 bg_p6 btn_i6 pad_10' href='${ctx}/customer/tCustomer/downFile?fileName="+val.appendixTypeName+"'></a>"+
				"<a id='"+val.id+"' class='btn btn-default btn_n4 bg_p6 btn_i4 pad_10' onclick='buttondelete("+val.id+")'></a>"+
				"</td>"+
				"</tr>"
				)
				
			});	
			
			
			
        }
	})
	//目录导航
	//$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	/******新增客户****/
	var dialog, form;
	dialog = $( "#dialog_add" ).dialog({
		autoOpen: false,
		width: 840,
		modal: true
	});	
	$( ".dialog_add" ).on('click',function( event ) {
		dialog.dialog( "open" );
		event.preventDefault();
	});	
	$( "#btnCancel,#btnCancel1,#btnCancel2" ).on('click',function( event ) {
		dialog.dialog( "close" );
		//event.preventDefault();
	});
	//----点击附件如果有的话就添加
	
	//----
	$("#creat_per,#creat_per2,#creat_per1,#creat_per3,#creat_per4").click(function() {
		if($("#customerName").val()!="" && $("#supervisor").val()!=""){
		$("#inputForm").ajaxSubmit({
            type: 'post',
            url: "${ctx}/customer/tCustomer/save",
            data: $("#inputForm").serialize(),
            success: function(data){
            	if(data.result=="1"){
            		messagePop("保存成功！");	
                	window.location="${pageContext.request.contextPath}/a/workterrace/chargeToAccount";
            	}
                $( "#inputForm").resetForm();
            	$( "#upload").resetForm();
            },
            error: function(XmlHttpRequest, textStatus, errorThrown){
            	messagePop( "error");
            }                
        })
        
        $( "#inputForm").resetForm();
        }else{
        	messagePop("请填写必须要输入的信息！");
        }
	});
	
	$( ".datepicker" ).datepicker({
		  monthNamesShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],  // 区域化月名为中文  
		  prevText: '上月',         // 前选按钮提示  
		  nextText: '下月',         // 后选按钮提示  
		           changeYear: true,          // 年下拉菜单  
		           changeMonth: true,             // 月下拉菜单  
		           showButtonPanel: true,         // 显示按钮面板  
		           showMonthAfterYear: true,  // 月份显示在年后面  
		           currentText: "本月",         // 当前日期按钮提示文字  
		           closeText: "确认",           // 关闭按钮提示文字  
		           dateFormat: "yy-mm",       // 日期格式  
		  onClose: function(dateText, inst) {// 关闭事件  
		      var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();  
		      var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();  
		      $(this).datepicker('setDate', new Date(year, month, 1));  
		  }  
	});
	
	form = dialog.find( "form" ).on( "submit", function( event ) {
      //event.preventDefault();
      //addUser();
    });
	/*日期*/
	$( "#datepicker1" ).datepicker({
		dateFormat:'yy-mm-dd'
	});
	$( "#datepicker2" ).datepicker({
		dateFormat:'yy-mm-dd'
	});
	//移除客户
	$( "#dialog_remove" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});	
	$( ".dialog_remove" ).click(function( event ) {
		$( "#dialog_remove" ).dialog( "open" );
		event.preventDefault();
	});
	$("#uploadbutton").on("click",function(){
		//var appendixname=$("#appendix").val();
		if($("#appendix").val()=="" || $("#filetype").val()==""){
			messagePop("请输入必填上传信息或选中文件");
		}else{
		$("#upload").ajaxSubmit({
			type:'post',
			url:"${ctx}/customer/tCustomer/fileUpload2",
			//data:"appendixName="+appendixname,
			success:function(data){
				var arrays=$("#array").val();
				if(arrays==''){
					$("#array").val(data.id);
				}else{
					$("#array").val($("#array").val()+","+data.id);
				}
				
				$("table#uptable>tbody").append("<tr>"+
						"<td>"+data.appendixName+"</td>"+
						"<td>"+data.upPeople+"</td>"+"<td>"+data.createDate+"</td>"+
						"<td>"+
						"<a class='btn btn-default btn_n4 bg_p6 btn_i6 pad_10' href='${ctx}/customer/tCustomer/downFile?fileName="+data.appendixTypeName+"'></a>"+
						"<a id='"+data.id+"' class='btn btn-default btn_n4 bg_p6 btn_i4 pad_10' onclick='buttondelete("+data.id+")'></a>"+
						"</td>"+
						"</tr>"
				)
			},
			error:function(XmlHttpRequest, textStatus, errorThrown){
				messagePop("error");
			}
		});
		}
		 $("#upload ").resetForm();
	});
})
function buttondelete(id){
	$.ajax({
        type: "POST",//使用post方法访问后台
        url: "${ctx}/customer/tCustomer/deleteById",//要访问的后台地址  
        data: "id="+id,
        success: function(data){
        	messagePop("删除成功");
        	$('#'+id).parent().parent().remove();
        }
})


$(".btn_p7").on('click',function(){
	alert('123');
	$('.pad_l_r5').text(Number($('.pad_l_r5').text())-1);
})
}
function show(){
	$.ajax({
        type: "POST",//使用post方法访问后台
        url: "${ctx}/customer/tAppendix/findByState",//要访问的后台地址  
        success: function(data){
        	var list=eval(data);
        	$("table#uptable>tbody").empty();
			$.each(list,function(n,val){
				$("table#uptable>tbody").append("<tr>"+
				"<td>"+val.appendixName+"</td>"+
				"<td>"+val.upPeople+"</td>"+"<td>2015-12-07</td>"+
				"<td>"+
				"<a class='btn btn-default btn_n4 bg_p6 btn_i6 pad_10' href='${ctx}/customer/tCustomer/downFile?fileName="+val.appendixTypeName+"'></a>"+
				"<a id='"+val.id+"' class='btn btn-default btn_n4 bg_p6 btn_i4 pad_10' onclick='buttondelete("+val.id+")'></a>"+
				"</td>"+
				"</tr>"
				)
				
			});	
        }
	})
}
</script>
<script>
$(function(){
	$(".pane_bor3").height(function(){
		return parseInt($(window).height()-478);
	})
	$(".khcz").on("click",function(){//点击的时候填充微信企业账号
		var id=$(".yc").val();
		//alert(id);
		if(id.length==0){
			$("#khspan").html("这是新加客户，请到工作台设置企业查账账号");
		}else{
			$.ajax({
		        type: "POST",//使用post方法访问后台
		        url: "${ctx}/workterrace/chargeToAccount/fillcount",//要访问的后台地址  
		        data: "id="+id,
		        success: function(wechet){
		        	var username=wechet.userName;var password=wechet.plainTextPassword;
		        	//alert(username+"###"+password);
		        	$("#idi").val(id);
		        	$("#username").val(username);//给隐藏的用户名赋值
		        	$("#usernamew").html(username);//给用户名
		        	$("#pwd").val(password);
		        }
		})}
		});
		
		
})

function messagePop(str){//用于显示提示信息
		$(".message-pop").fadeIn();
		$(".message-pop").children("span").html(str);
		setTimeout(function(){
			$(".message-pop").fadeOut();
		},2000)
}
function updatastate(obj,event){
	
	var e = event || window.event;
	id=obj.id
	$.ajax({  
		type:"POST",
			url:"${ctx}/workterrace/tPersonalReminder/updatastates" ,
		   data: "id="+id,
	       success:function(data){ 
	    	   $("#tPersonalReminderList").setTemplateElement("template").processTemplate(data);
	       },
	       error:function(){
	    	   
	       }
	 }); 
}


</script>
<!-- Templates -->
<p style="display:none"><textarea id="template" >
<!--
{#foreach $T.list as tPersonalReminder}
			<tr>
    					<td width="125">{$T.tPersonalReminder.warnTime}</td>
							<td width="50">{$T.tPersonalReminder.degreeImportance}</td>
						<td style='text-align:left'>{$T.tPersonalReminder.matter}</td>
 							<td  width="60">
							<div id="{$T.tPersonalReminder.id}" onclick="updatastate(this)" style="cursor:pointer;" title="确认完成" class="btn_d3"></div></td>
	
			</tr>
		   {#/for}
-->
</textarea></p>
 <!-- Output elements -->
</body>
</html>