<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>系统设置-客户管理</title>
<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery.form.js"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctxStatic}/jquery/jquery.ztree.core-3.5.js"></script>
<link rel="stylesheet" href="${ctxStatic}/zTreeStyle/zTreeStyle.css" type="text/css">
<link href="${ctxStatic}/css/master_2.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
<style type="text/css">  
    .ui-datepicker-calendar {  
        display: none;// 不显示日期面板  
    }  
</style>
<script type="text/javascript">
<!--
var setting = {
    view: {
        dblClickExpand: true,
        showLine: false
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeExpand: beforeExpand,
		onClick: onClick
    }
};


var zNodes =[
	{ id:11, pId:0, name:"公司", open:true},
	{ id:111, pId:11, name:"代购一部", iconSkin:"pIcon01"},
	{ id:112, pId:11, name:"代购一部", iconSkin:"pIcon01"},
	{ id:113, pId:11, name:"代购一部", iconSkin:"pIcon01"},
	{ id:114, pId:11, name:"代购一部", iconSkin:"pIcon01"},
	{ id:12, pId:0, name:"停止服务客户", open:false},
	{ id:121, pId:12, name:"叶子节点 1-2-1", iconSkin:"pIcon01"},
	{ id:122, pId:12, name:"叶子节点 1-2-2", iconSkin:"pIcon01"},
	{ id:123, pId:12, name:"叶子节点 1-2-3", iconSkin:"pIcon01"},
	{ id:124, pId:12, name:"叶子节点 1-2-4", iconSkin:"pIcon01"},

	{ id:13, pId:0, name:"停止服务客户", open:false},
	{ id:131, pId:13, name:"叶子节点 2-2-1", iconSkin:"pIcon01"},
	{ id:132, pId:13, name:"叶子节点 2-2-2", iconSkin:"pIcon01"},
	{ id:133, pId:13, name:"叶子节点 2-2-3", iconSkin:"pIcon01"},
	{ id:134, pId:13, name:"叶子节点 2-2-4", iconSkin:"pIcon01"}
];

function beforeExpand(treeId, treeNode) {
    singlePath(treeNode);
}
function singlePath(currNode) {
    var cLevel = currNode.level;
    //id是唯一的
    var cId = currNode.id;     
    //此对象可以保存起来，没有必要每次查找
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    //展开的所有节点，这是从父节点开始查找（也可以全文查找）
    var expandedNodes = treeObj.getNodesByParam("open", true, currNode.getParentNode());
 
    for(var i = expandedNodes.length - 1; i >= 0; i--){
        var node = expandedNodes[i];
        var level = node.level;
        var id = node.id;
        if (cId != id && level == cLevel) {
            treeObj.expandNode(node, false);
        }
    }
}
function onClick(e,treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	singlePath(treeNode);
	zTree.expandNode(treeNode);
}
//-->
</script>
</head>
<body>
	<div class="main-row">
	<div class="main_index3">
		<div class="pane_main5">
			<div class="t_pane_a1 radius_5">
				<div class="user_pane_r2">
					<div class="hr20"></div>
					<ul class="nav nav-tabs" role="tablist">
						<li><a href="#tab_e1">基础设置</a></li>
						<li><a href="#tab_e2">部门职员</a></li>
						<li class="active"><a href="#tab_e3">客户管理</a></li>

						<li><a href="#tab_e4">通讯录</a></li>
						<li><a href="#tab_e5">角色权限</a></li>
						<li><a href="#tab_e6">系统消息</a></li>
					</ul>
					<div class="tab-content main5_con">
						<div class="tab-pane active" id="tab_e3">
							<div class="hr10"></div>
							<div class="sub_left4 right">
								<div class="left4_main">
								<span class="btn btn-default btn_w_a btn_bg_2 dialog_add" >新增客户</span>
								<span class="btn btn-default btn_w_a btn_bg_4 dialog_remove">停止服务</span>
								<div class="hr10"></div>
								<div class="left">
									<form>
									<input type="text" class="ipt_s03 left" /><input type="submit" class="ipt_04" value="搜索" />
									</form>
								</div>
								<div class="right">
								每页显示 <span class="font_cc4">10</span> <span class="font_cc4">20</span> <span class="font_cc4">30</span> <span class="font_cc4">40</span> 共 <span class="font_cc4">7</span> 条记录 <button class="btn_p7"></button><span class="pad_l_r5">1</span><button class="btn_p8"></button>
								</div>
								<div class="clearfix"></div>
								<div class="hr10"></div>
								<p>客户列表 》公司</p>
								<table class="table mar-bot10 border_n1 table_line1" width="100%">
									<tr>
										<td class="t_top1"></td>
										<td class="t_top1">编号</td>

										<td class="t_top1">客户名称</td>

										<td class="t_top1">联系人</td>

										<td class="t_top1">联系电话</td>

										<td class="t_top1">到期时间</td>

										<td class="t_top1">记账人</td>
									</tr>
									<tr>
										<td><lable><input type="checkbox" /></lable>
										</td>

										<td>000001
										</td>

										<td>北京三味书屋文化有限责任公司
										</td>

										<td>无梦生
										</td>

										<td>0531-123456789
										</td>

										<td>2015-01-01
										</td>
										<td>
											<select name=""><option>东方晴雨</option><option>玄同</option></select><a href="#" class="font_cc4 underline mar-lft10">保存</a>
										</td>
									</tr>
									<tr>
										<td><lable><input type="checkbox" /></lable>
										</td>

										<td>000002
										</td>

										<td>北京百世经委文化传媒有限公司
										</td>

										<td>无梦生
										</td>

										<td>130-0100-0100
										</td>

										<td>2015-01-01
										</td>
										<td>
											玄同<a href="#" class="font_cc4 underline mar-lft10">编辑</a>
										</td>
									</tr>

								</table>
									
									<div class="page_cc">共2条记录</div>
								</div>
							</div>

							<div class="sub_left3 left">
								<div class="tit_sc1">组织架构</div>
								<div class="sidebar_user">
									<ul id="treeDemo" class="ztree"></ul>
								</div>
								<div class="hr10"></div>
							</div>
							<div class="clearfix"></div>
							<div class="hr15"></div>
						</div>
					</div>
					<div class="hr20"></div>
				</div>
			</div>
			<div class="hr20"></div>
			<div class="footer_s3 text-center">版权所属 北京芸智慧财务有限公司    技术支持 麟腾传媒</div>
		</div>
	</div>
</div>
<!--新增客户-->
<div id="dialog_add" title="客户信息（“*”为必填）">
	<div class="dialog_client">
		<div class="t_pane_a1 radius_5">
			<ul class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active"><a href="#tab_g1" aria-controls="tab_g1" role="tab" data-toggle="tab">基本资料</a></li>
				<li role="presentation"><a href="#tab_g2" aria-controls="tab_g2" role="tab" data-toggle="tab">联系方式</a></li>
				<li role="presentation"><a href="#tab_g3" aria-controls="tab_g3" role="tab" data-toggle="tab">税务资料</a></li>
				<li role="presentation"><a href="#tab_g4" aria-controls="tab_g4" role="tab" data-toggle="tab">附件</a></li>
				<li role="presentation"><a href="#tab_g5" aria-controls="tab_g5" role="tab" data-toggle="tab">客户查账</a></li>
			</ul>
			<form:form id="inputForm" modelAttribute="tCustomer" method="post" class="form-horizontal">
			<div class="tab-content border_n3">
				<div role="tabpanel" class="tab-pane active" id="tab_g1">
					<!--基本资料-->
					<div class="client_inner2">
						<div class="client_form1">
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
									<label class="col-sm-2 control-label">开始记账时间</label>
									<div class="col-sm-4">
									<input name="initperiod" type="text" readonly="readonly" class="form-conrotl datepicker text-center" value="<fmt:formatDate value="${tServiceCharge.beginSignDate}" pattern="yyyy-MM"/>"/>
									<!--  
									<input name="initperiod" type="text" class="form-control ipt_w4" id="datepicker1" />
									-->
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
								 		<form:option value="1">2013小企业会计准则2015</form:option>
								 		<form:option value="2">2007企业会计准则</form:option>
					             	</form:select><em>*</em>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label">记账人</label>
									<div class="col-sm-10">
										<form:input id="supervisor" path="supervisor" htmlEscape="false" maxlength="100" class="form-control ipt_w4"/>
										<span class="help-inline"><font color="red">*</font> </span>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<span class="right">
										<button id="creat_per" type="button" class="btn btn-default btn_w_a btn_bg_2 mar-rig10">确定</button>
										<input id="btnCancel" class="btn btn-default btn_w_a btn_bg_4" type="button" value="取 消"/>
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
										<button id="creat_per1" type="button" class="btn btn-default btn_w_a btn_bg_2 mar-rig10">确定</button>&nbsp;
										<input id="btnCancel1" class="btn btn-default btn_w_a btn_bg_4" type="button" value="取 消"/>
										
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
											<form:radiobutton path="valueAddedTax" value="0" />一般纳税人
										</label>
									 	<label class="radio-inline">
									 		<form:radiobutton path="valueAddedTax" value="1"/>小型规模纳税人
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
											<button id="creat_per2" type="button" class="btn btn-default btn_w_a btn_bg_2 mar-rig10">确定</button>&nbsp;
											<input id="btnCancel2" class="btn btn-default btn_w_a btn_bg_4" type="button" value="取 消"/>
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
					<div class="tit_sc3 font-14 border_top1"><b>收费列表</b></div>
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
								<form class="form-horizontal">
								  <div class="form-group">
								    <label class="col-sm-3 control-label text-left">用户名</label>
								    <div class="col-sm-5">
								      <p class="form-control-static">cuihuohan</p>
								    </div>
								  </div>
								  <div class="form-group">
								    <label class="col-sm-3 control-label text-left">密　码</label>
								    <div class="col-sm-5">
								      <input type="text" class="form-control ipt_w5" placeholder="" />
								    </div>
								    <div class="col-sm-3">
								    	<button type="submit" class="btn btn-default btn_w_a btn_bg_2">重置密码</button>
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
</div>

<!--移除客户-->
<div id="dialog_remove" title="系统提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center">您确定要“停止”该客户的服务吗？</div>
		<div class="hr40"></div>
		<form>
		<div class="text-center">
			<button type="submit" class="btn btn-default btn_w_a btn_bg_2 mar-rig10">确认</button>
			<button class="btn btn-default btn_w_a btn_bg_4">取消</button>
		</div>
		</form>
	</div>
</div>
<script>
$(function(){
	//目录导航
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
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
	
	$("#creat_per,#creat_per2,#creat_per1").click(function() {
		if($("#customerName").val()!="" && $("#supervisor").val()!=""){
		$("#inputForm").ajaxSubmit({
            type: 'post',
            url: "${ctx}/customer/tCustomer/save" ,
            data: $("#inputForm").serialize(),
            success: function(data){
            	if(data=="1")
                	alert("保存成功！");
                $( "#inputForm").resetForm();
            },
            error: function(XmlHttpRequest, textStatus, errorThrown){
                 //alert( "error");
            }                
        })
        }else{
        	alert("请填写必须要输入的信息！");
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
})
</script>
<script>
$(function(){
	$("#uploadbutton").on("click",function(){
			if($("#appendix").val()=="" && $("#filetype").val()==""){
				alert("请输入必填信息");
			}else{
			$("#upload").ajaxSubmit({
				type:'post',
				url:"${ctx}/customer/tCustomer/fileUpload2",
				success:function(data){
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
				},
				error:function(XmlHttpRequest, textStatus, errorThrown){
					alert("error");
				}
			});
			}
		});
		
	})
/**当ID值是的时候可以调用***/
function buttondelete(id){
	$.ajax({
        type: "POST",//使用post方法访问后台
        url: "${ctx}/customer/tCustomer/deleteById",//要访问的后台地址  
        data: "id="+id,
        success: function(data){
        	alert("删除成功");
        	$('#'+id).parent().parent().remove();
        }
})
}	
</script>
</body>
</html>