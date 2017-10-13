<!doctype html>
<html lang="en">
<head>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>部门职员-系统设置-芸豆会计</title>
	
<script src="${pageContext.request.contextPath}/static/jquery/jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/jquery/jquery-jtemplates.js" type="text/javascript"></script>
<script src="${ctxStatic}/js/workAccount.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/jquery-validation/1.11.1/jquery.validate.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/jquery-validation/1.11.1/localization/messages_zh.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/jquery/jquery-ui.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/jquery/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/jquery/jquery.ztree.core-3.5.js" type="text/javascript"></script>

<link href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/bootstrap/css/page.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/zTreeStyle/zTreeStyle.css" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/master_2.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/css/dialog.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctxStatic}/js/message.js"></script>
<script type="text/javascript">
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
        //beforeExpand: beforeExpand,
		onClick: onClick
    }
};


var zNodes =[
           <c:forEach items="${sysOfficeList}" var="office">
			{ id:"${office.id}", pId:"${office.parentId}", name:"${office.name}",open:true, iconSkin:"pIcon01"},
</c:forEach>
			{ id:"已移除的职员", pId:"0", name:"已移除的职员", iconSkin:"pIcon01"}
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
var cid="";
function onClick(e,treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	//singlePath(treeNode);
	//zTree.expandNode(treeNode);
	cid=treeNode.id;
	switch(cid)
	{
	case "已移除的职员":
		$("#yichu").addClass("displayCss");
		$("#sysuser #delFlag").val("1");
		$("#sysuser input[title=officeId]").val("");
		$("#sysuser input[title=officeName]").val("");
	  break;
	default:
		$("#yichu").removeClass("displayCss");
		$("#sysuser #delFlag").val("0");
		$("#sysuser input[title=officeId]").val(treeNode.id);
		$("#sysuser input[title=officeName]").val(treeNode.name);
	}
	var  sss=$("#sysuser input[title=company]").val();
	 if(cid==sss){
		 $("#sysuser #delFlag").val("0");
		 $("#sysuser input[title=officeId]").val("");
			$("#sysuser input[title=officeName]").val("");
	 }
	$("#searchForm").ajaxSubmit({
        type: 'post',
        url: "${ctx}/inspection/setup/sysUser/userlist" ,
        dataType : 'json',
        data: $("#searchForm").serialize(),
        success: function(data){
        	if(cid!="已移除的职员")
        		{
        		 $("#userList").setTemplateElement("template").processTemplate(data);
        		}else{
        			 $("#userList").setTemplateElement("templateDel").processTemplate(data);
        		}
        	/*  $(".pagination").html(data.html);
          $("#pageNo").val(data.pageNo);
          $("#pageSize").val(data.pageSize); */
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
        }                
    });
	return;
	$("#searchForm").submit();
	
}
function page(n,s){
	$("#pageNo").val(n);
	$("#pageSize").val(s);
	$("#searchForm").ajaxSubmit({
        type: 'post',
        url: "${ctx}/inspection/setup/sysUser/userlist" ,
        dataType : 'json',
        data: $("#searchForm").serialize(),
        success: function(data){
        	if(cid!="已移除的职员")
    		{
    		 $("#userList").setTemplateElement("template").processTemplate(data);
    		}else{
    			 $("#userList").setTemplateElement("templateDel").processTemplate(data);
    		}
        	 /*  $(".pagination").html(data.html);
	          $("#pageNo").val(data.pageNo);
	          $("#pageSize").val(data.pageSize); */
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
        }                
    });
	
	return;
}
</script>

<style>
	.help-inline {
		color: red;
	}
	.table_line1 tr td:nth-of-type(1) {width:5%;}
	.table_line1 tr td:nth-of-type(2) {width:10%;}
	.table_line1 tr td:nth-of-type(3) {width:17%;}
	.table_line1 tr td:nth-of-type(4) {width:26%;}
	.table_line1 tr td:nth-of-type(5) {width:10%;}
	.table_line1 tr td:nth-of-type(6) {width:19%;}
</style>
</head>
<style>
table.table_line1>tbody>tr>td{font-size:12px;}
span label{font-weight:normal;}
.form-group label.control-label{padding-top:0;}
.role_css span{margin-right:10px;}
.form-horizontal .radio, .form-horizontal .checkbox, .form-horizontal .radio-inline, .form-horizontal .checkbox-inline{padding-top:0;}
</style>
<body >
	<div class="message-pop">
<span>
保存成功！
</span>
</div>
<div class="main-row">
	
	<div class="main_index3">
		<div class="pane_main5">
			<div class="t_pane_a1 radius_5">
				<div class="user_pane_r2">
					<div class="hr20"></div>
					<ul class="nav nav-tabs" role="tablist">
						<li class="active"><a href="${ctx}/inspection/setup/sysUser/list">部门职员</a></li>
						<li><a href="${ctx}/inspection/setup/sysCustomer/">客户管理</a></li>
						<li><a href="${ctx}/inspection/setup/sysTel/">通讯录</a></li>
						<li><a href="${ctx}/inspection/setup/sysMenu/list">角色权限</a></li>
						<li><a href="${ctx}/inspection/setup/sysMessage/">系统消息</a></li>
					</ul>
					<div class="tab-content main5_con">
						<div class="tab-pane active" id="tab_e2">
							<div class="hr10"></div>
							
							<div class="sub_left4 right">
								<div class="left4_main">
								<p id="date"></p>
								<form:form  style="display:none" id="searchForm" modelAttribute="user" action="${ctx}/inspection/setup/sysUser/list" method="post" class="breadcrumb form-search ">
									<%-- <input id="pageNo" name="pageNo" type="text" value="${page.pageNo}"/>
									<input id="pageSize" name="pageSize" type="text" value="${page.pageSize}"/> --%>
									<ul id="sysuser" class="ul-form">
										<li><label>归属公司：</label><form:input title="company" path="company.id" htmlEscape="false" maxlength="50" class="input-medium"/></li>
										<li><label>登录名：</label><form:input path="loginName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
										<li class="clearfix"></li>
										<li><label>归属部门：</label><form:input title="officeId" path="office.id" htmlEscape="false" maxlength="50" class="input-medium"/></li>
										
										<li><label>部门姓名：</label><form:input title="officeName" path="office.name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
										<li><label>姓名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
										<li><label>delFlag：</label><form:input path="delFlag" htmlEscape="false" maxlength="50" class="input-medium"/></li>
										
										<li class="clearfix"></li>
									</ul>
								</form:form>
								<span class="btn btn-default btn_w_a btn_bg_2 dialog_add3">新增</span>
								<span id="yichu" class="btn btn-default btn_w_a btn_bg_4 dialog_del2">移除</span>
								<div class="hr10"></div>
						
						
						
								<div class="table_head">
									<table class="table border_n1 table_line1 table_first" width="100%">
										<tr>
											<td class="t_top1"></td>
											<td class="t_top1">姓名</td>
											<td class="t_top1">登陆账号</td>
											<td class="t_top1">邮箱</td>
											<td class="t_top1">部门</td>
											<td class="t_top1">角色</td><td class="t_top1">操作</td>
										</tr>
									</table>
								</div>		
								<div class="table_containerbox">
									<table class="table border_n1 table_line1">
										<tbody id="userList">
										
										<!--  
										<c:forEach items="${page.list}" var="user">
													<tr>
													<td><lable><input type="checkbox" value="${user.id}" /></lable></td>
														<td>${user.name}</td>
														<td>${user.loginName}</td>
														<td>${user.email}</td>
														<td>${user.office.name}</td>
														<td>${user.roleNamess}</td>
														<td>
										    				<a id="${user.id}" href="javascript:void(0);" onclick="ShowDetails('${user.id}')"  class="btn btn-default dialog_add4 btn_n4 bg_p6 btn_i2">修改</a>
																</td>
													</tr>
												</c:forEach>-->
										
											</tbody>
										</table>
									</div>
										<%-- <div class="page_cc">
											<div class="pagination">${page}</div>
										</div> --%>
							</div>
						</div>
						</div>

							<div class="sub_left3 left">
								<div class="tit_sc1">部门管理</div>
								<div class="right pad_t_b5">
									<a class="btn btn-default btn_n4 bg_p6 btn_i3 dialog_add2">新增</a><a class="btn btn-default btn_n4 bg_p6 btn_i2 dialog_edit">编辑</a><a class="btn btn-default btn_n4 bg_p6 btn_i4 dialog_del">删除</a>
								</div>
								<div class="clearfix"></div>
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

		<!--  	<div class="footer_s3 text-center">版权所属 北京芸智慧财务有限公司    技术支持 麟腾传媒</div>-->


		</div>
	</div>
</div>
<!--新增部门-->
<div id="dialog_add2" class="displayCss" title="新增部门">
	<div class="min-h200">
		<div class="hr40"></div>
		
		<form:form id="inputForm" modelAttribute="office" action="${ctx}/inspection/setup/sysUser/saveOffice" method="post">
			<div class="form-group text-center">
				<label class="control-label mar-rig10" style="text-align:right;">部门名称</label>				
				 <form:hidden path="id"/>	
				 <form:input path="name" htmlEscape="false" minlength="3" maxlength="12"  required="true" class="form-control ipt_w5 w150 "/>
				<span class="help-inline"></span>			
			</div>
			<div class="clearfix"></div>
			<div class="hr40"></div>
			<div class="text-center">
				<input id="btnSubmit" type="submit" value="确定" class="btn btn-default btn_w_a btn_bg_2 mar-rig10"/>
				<!-- <button class="btn  btn_w_a btn_bg_4 dialog_close2">取消</button> -->
			</div>
			
		</form:form>
		
	</div>
</div>
<!--编辑部门-->
<div id="dialog_edit" class="displayCss" title="编辑">
	<div class="min-h200">
		<div class="hr40"></div>
		
		<form:form id="inputForm" modelAttribute="office" action="${ctx}/inspection/setup/sysUser/saveOffice" method="post">
			<div class="form-group text-center">
				<label class="control-label mar-rig10" style="text-align:right;">部门名称</label>				
				 <form:hidden path="id"/>	
				 <form:input path="name" htmlEscape="false" minlength="3" maxlength="12"  required="true" class="form-control ipt_w5 w150 "/>
				<span class="help-inline"></span>			
			</div>
			<div class="clearfix"></div>
			<div class="hr40"></div>
			<div class="text-center">
<input id="btnSubmit" type="submit" value="确定" class="btn btn-default btn_w_a btn_bg_2 mar-rig10"/>
				<!-- <button class="btn  btn_w_a btn_bg_4 dialog_close3">取消</button> -->
			</div>
			
		</form:form>
	</div>
</div>
<!--移除部门-->
<div id="dialog_del" class="displayCss" title="系统提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center">您确定要删除“<span></span>”部门吗？</div>
		<div class="hr40"></div>
		<form>
		<div class="text-center">
			<button type="submit" class="btn delOffice btn-default btn_w_a btn_bg_2 mar-rig10">确认</button>
			<button class="btn dialog_close4 btn_w_a btn_bg_4">取消</button>
		</div>
		</form>
	</div>
</div>
<!--选择部门-->
<div id="dialog_selectOffice" class="displayCss" title="系统提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center">请选择需要操作的部门</div>
		<div class="hr40"></div>
		<form>
		<div class="text-center">
			<button class="btn dialog_close7 btn-default btn_w_a btn_bg_4">确认</button>
		</div>
		</form>
	</div>
</div>
<!-- 不可删除 -->
<div id="notDelectUser" class="displayCss" title="系统提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center"></div>
		<div class="hr40"></div>
		<form>
		<div class="text-center">
			<button class="btn dialog_close10 btn-default btn_w_a btn_bg_4">确认</button>
		</div>
		</form>
	</div>
</div>
<!--选择职员-->
<div id="dialog_selectUser" class="displayCss" title="系统提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center">请至少选择一位要删除的职员！</div>
		<div class="hr40"></div>
		<form>
		<div class="text-center">
			<button class="btn dialog_close9 btn-default btn_w_a btn_bg_4">确认</button>
		</div>
		</form>
	</div>
</div>
<!--新增职员-->
<div id="dialog_add3" class="displayCss" title="新增职员（初始密码为“666666”）">
	<div class="client_inner2">
		<div class="client_form_a1">
			<form:form id="inputFormUserAdd" modelAttribute="user" action="${ctx}/inspection/setup/sysUser/save" method="post" class="form-horizontal">
	
					<div class="form-group">
							<label class="col-sm-2 control-label">姓名：</label>
							<div class="col-sm-4">
								<form:input path="name" htmlEscape="false" class="form-control ipt_w4 required"/>
								<span class="help-inline"></span>
							</div>
							<label class="col-sm-2 control-label">登陆账户：</label>
			<div class="col-sm-4">
				<form:input path="loginName" htmlEscape="false" maxlength="100" class="form-control ipt_w4 required"/>
				<span class="help-inline"></span>
			</div>
						</div>
		
<div class="form-group">
			<label class="col-sm-2 control-label">性别：</label>
			<div class="col-sm-4">
				<label class="radio-inline">
				 <form:radiobutton checked="true"  path="sex" value="1" htmlEscape="false" maxlength="64" />男  
              </label>	<label class="radio-inline">
               <form:radiobutton  path="sex" value="0" htmlEscape="false" maxlength="64"/>女  
			</label>
			</div>
				<label class="col-sm-2 control-label">出生日期：</label>
			<div class="col-sm-4">
				<form:input path="birth"  type="text"  maxlength="20" class="form-control ipt_w4"
					id="datepicker1"/>
			</div>
			</div>
		
	
	<div class="form-group">
			<label class="col-sm-2 control-label">E-mail：</label>
			<div class="col-sm-4">
				<form:input path="email" htmlEscape="false" maxlength="200" class="form-control ipt_w4"/>
				<span class="help-inline"></span>
			</div>
			<label class="col-sm-2 control-label">手机：</label>
			<div class="col-sm-4">
				<form:input path="mobile" htmlEscape="false" maxlength="200" class="form-control ipt_w4"/>
			</div>
		</div>
			
<div class="form-group">
			<label class="col-sm-2 control-label">QQ号码：</label>
			<div class="col-sm-4">
				<form:input path="qq" htmlEscape="false" maxlength="200" class="form-control ipt_w4"/>
			</div>
			<label class="col-sm-2 control-label">紧急联系人：</label>
			<div class="col-sm-4">
				<form:input path="urgentMan" htmlEscape="false" maxlength="100" class="form-control ipt_w4"/>
			</div>
		</div>
		
 <div class="form-group">
			<label class="col-sm-2 control-label">归属部门：</label>
			<div class="col-sm-4">
				<form:select path="officeid" class="form-control ipt_w4">
					<form:options items="${sysOfficeList}" itemLabel="name" itemValue="id" htmlEscape="false" class="input-xlarge "/>
				</form:select>
				<span class="help-inline"></span>
			</div>
			<label class="col-sm-2 control-label">备注：</label>
			<div class="col-sm-4">
				<form:input path="remark" htmlEscape="false" maxlength="200" class="form-control ipt_w4"/>
			</div>
		</div>
 
<div class="form-group">
			<label class="col-sm-2 control-label">联系地址：</label>
			<div class="col-sm-10">
				<form:input path="address" htmlEscape="false" maxlength="100" class="form-control ipt_w5" style="width:610px;"/>
			</div>
			
		</div>

	<div class="form-group">
			<label class="col-sm-2 control-label">角色:</label>
			<div class="col-sm-10 role_css">
				<form:checkboxes path="roleIdArray" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" />
					<span class="help-inline"></span>
			</div>
			
		</div>
		

<div class="form-group">
<div class="col-sm-offset-2 col-sm-10">
<span class="right">

			<input id="btnSubmit" class="btn btn-default btn_w_a btn_bg_2 mar-rig10" type="submit" value="确定"/>
	
			<!-- <input id="btnCancel" class="btn dialog_close5 btn_w_a btn_bg_4" type="button" value="取消"/> -->
			</span>
						<div class="right"></div>
		</div>
		</div>
	
			</form:form>
		</div>
	</div>
</div>
<!--修改职员-->
<div id="dialog_add4" class="displayCss" title="修改职员（初始密码为“666666”）">
	<div class="client_inner2">
		<div class="client_form_a1">
			<form:form id="inputFormUserupdate" modelAttribute="user" action="${ctx}/inspection/setup/sysUser/save" method="post" class="form-horizontal">
	<form:hidden path="id"/>
					<div class="form-group">
							<label class="col-sm-2 control-label">姓名：</label>
							<div class="col-sm-4">
								<form:input path="name" htmlEscape="false" maxlength="100" class="form-control ipt_w4 required"/>
								<span class="help-inline"></span>
							</div>
							<label class="col-sm-2 control-label">登陆账户：</label>
			<div class="col-sm-4">
				<form:input path="loginName" htmlEscape="false" maxlength="100" class="form-control ipt_w4 required"/>
				<span class="help-inline"></span>
			</div>
						</div>
			
<div class="form-group">
			<label class="col-sm-2 control-label">性别：</label>
			<div class="col-sm-4">
				<label class="radio-inline">
				 <form:radiobutton checked="true"  path="sex" value="1" htmlEscape="false" maxlength="64" />男  
              </label>	<label class="radio-inline">
               <form:radiobutton  path="sex" value="0" htmlEscape="false" maxlength="64"/>女  
			</label>
			</div>
				<label class="col-sm-2 control-label">出生日期：</label>
			<div class="col-sm-4">
				<form:input path="birth" readonly="readonly" type="text"  maxlength="20" class="form-control ipt_w4"
					id="datepicker2"/>
			</div>
			</div>
		
	<div class="form-group">
			<label class="col-sm-2 control-label">E-mail：</label>
			<div class="col-sm-4">
				<form:input path="email" htmlEscape="false" maxlength="200" class="form-control ipt_w4"/>
				<span class="help-inline"></span>
			</div>
			<label class="col-sm-2 control-label">手机：</label>
			<div class="col-sm-4">
				<form:input path="mobile" htmlEscape="false" maxlength="200" class="form-control ipt_w4"/>
			</div>
		</div>
		
<div class="form-group">
			<label class="col-sm-2 control-label">QQ号码：</label>
			<div class="col-sm-4">
				<form:input path="qq" htmlEscape="false" maxlength="200" class="form-control ipt_w4"/>
			</div>
			<label class="col-sm-2 control-label">紧急联系人：</label>
			<div class="col-sm-4">
				<form:input path="urgentMan" htmlEscape="false" maxlength="100" class="form-control ipt_w4"/>
			</div>
		</div>
			
 <div class="form-group">
			<label class="col-sm-2 control-label">归属部门：</label>
			<div class="col-sm-4">
				<form:select path="officeid" class="form-control ipt_w4">
					<form:options  items="${sysOfficeList}" itemLabel="name" itemValue="id" htmlEscape="false" class="input-xlarge "/>
				</form:select>
				<span class="help-inline"></span>
			</div>
			<label class="col-sm-2 control-label">备注：</label>
			<div class="col-sm-4">
				<form:input path="remark" htmlEscape="false" maxlength="200" class="form-control ipt_w4"/>
			</div>
		</div>
 
<div class="form-group">
			<label class="col-sm-2 control-label">联系地址：</label>
			<div class="col-sm-10">
				<form:input path="address" htmlEscape="false" maxlength="100" class="form-control ipt_w5" style="width:610px;"/>
			</div>
			
		</div>
			
	<div class="form-group">
			<label class="col-sm-2 control-label">角色:</label>
			<div class="col-sm-10 role_css">
				<form:checkboxes  path="roleIdArray" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" />
				<span class="help-inline"> </span>
			</div>
		</div>
<div class="form-group">
<div class="col-sm-offset-2 col-sm-10">
<span class="right">
	
			<input id="btnSubmit" class="btn btn-default btn_w_a btn_bg_2 mar-rig10" type="submit" value="确定"/>
	
			<!-- <input id="btnCancel" class="btn dialog_close8 btn_w_a btn_bg_4" type="button" value="取消"/> -->
			</span>
						<div class="right"></div>
		</div>
		</div>
			</form:form>
		</div>
	</div>
</div>
<!--删除职员-->
<div id="dialog_del2" class="displayCss" title="系统提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center">您确定要“删除”该员工吗？</div>
		<div class="hr40"></div>
		<form>
		<div class="text-center">
			<input class="btn delectUser btn-default btn_w_a btn_bg_2 mar-rig10" type="button" value="确认" />
			<input class="btn dialog_close6 btn_w_a btn_bg_4" type="button" value="取消" />
			
		</div>
		
		</form>
	</div>
</div>
<!--重置-->
<div id="dialog_win3" class="displayCss" title="系统提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center">确定要重置此用户的密码为初始密码吗？</div>
		<div class="hr40"></div>
		<form>
		<div class="text-center">
			<input type="button" class="btn resetPassWordYes btn-default btn_w_a btn_bg_2 mar-rig10" value="确认" />
			<input type="button"  class="btn dialog_close6 btn_w_a btn_bg_4" value="取消" />
			
		</div>
		
		</form>
	</div>
</div>
 <!-- Templates -->
<p style="display:none"><textarea id="template" ><!--

{#foreach $T as user}
<tr>
			<td><lable><input type="checkbox" value="{$T.user.id}" /></lable></td>
				
				<td>{$T.user.name}</td>
				<td>{$T.user.loginName}</td>
				<td>{$T.user.email}</td>
				<td>{$T.user.officeName}</td>
				<td>{$T.user.roleNamess}</td>
				<td>
    				<a id="{$T.user.id}" title="修改" href="javascript:void(0);" onclick="ShowDetails('{$T.user.id}')"  class="btn btn-default dialog_add4 btn_n4 bg_p6 btn_i2"></a>
					<a id="{$T.user.id}" title="重置密码" href="javascript:void(0);" onclick="resetPassWord('{$T.user.id}')" class="btn_reset2"><span class="glyphicon glyphicon-cog" aria-hidden="true"></span></a>
				</td>
			</tr>
		   {#/for}
-->
</textarea></p>
 <!-- Output elements -->	
  <!-- Templates -->
<p style="display:none"><textarea id="templateDel" ><!--

{#foreach $T as user}
<tr>
			<td><lable><input type="checkbox" value="{$T.user.id}" /></lable></td>
				
				<td>{$T.user.name}</td>
				<td>{$T.user.loginName}</td>
				<td>{$T.user.email}</td>
				<td>{$T.user.officeName}</td>
				<td>{$T.user.roleNamess}</td>
				<td>
    				<a id="{$T.user.id}" href="javascript:void(0);" onclick="updateDel('{$T.user.id}')"  class="btn btn-default btn_n4 bg_p6 btn_i2">恢复</a>
						</td>
			</tr>
		   {#/for}
-->
</textarea></p>
 <!-- Output elements -->	
 
 	
<script>
$.fn.formEdit = function(data){
    return this.each(function(){
        var input, name;
        if(data == null){this.reset(); return; }
        for(var i = 0; i < this.length; i++){  
            input = this.elements[i];
            //checkbox的name可能是name[]数组形式
            name = (input.type == "checkbox")? input.name.replace(/(.+)\[\]$/, "$1") : input.name;
            if(data[name] == undefined) continue;
            switch(input.type){
                case "checkbox":
                    if(data[name] == ""){
                        input.checked = false;
                    }else{
                        //数组查找元素
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

$(":checkbox").click(function(){ 
	$(this).parent().siblings().find(":checkbox").attr("checked",false); 
	$(this).attr("checked",true); 
});


var resetPassWordId=null;
function resetPassWord(id){ 
	resetPassWordId=id;
	$( "#dialog_win3" ).dialog( "open" );
	event.preventDefault();
};

$(".resetPassWordYes").click(function(event){
	
	$.ajax({  
		type:"POST",
		url:"${ctx}/inspection/setup/sysUser/resetPassWord?id="+resetPassWordId,
	    success:function(json){  
	    	messagePop(json);
	    	$( "#dialog_win3" ).dialog( "close" );
	    	event.preventDefault();
	   },
	    error:function(){}
	 });  
});

function ShowDetails(userid){
	$.ajax({  
		type:"POST",
			url:"${ctx}/inspection/setup/sysUser/getUserById",  
			dataType : 'json',
		   data: "id="+userid,
	       success:function(json){  
	    	   $json = json;
	    	   $('#inputFormUserupdate').formEdit($json);
	       },
	       error:function(){}
	 });  
	$( "#dialog_add4" ).dialog( "open" );
};
function updateDel(userid,event){
	
	var e = event || window.event;
	var thisTag=$(e.srcElement).parent().parent();
	$.ajax({  
		type:"POST",
			url:"${ctx}/inspection/setup/sysUser/updateDel",  
		   data: {id:userid,delFlag:"0"},
	       success:function(json){  
	    	   thisTag.remove();
	       },
	       error:function(){}
	 });  
};
$(document).ready(function(){
	$("#searchForm").ajaxSubmit({
        type: 'post',
        url: "${ctx}/inspection/setup/sysUser/userlist" ,
        dataType : 'json',
        data: $("#searchForm").serialize(),
        success: function(data){
          $("#userList").setTemplateElement("template").processTemplate(data);
          fixTableHead($("#userList").height(), 204, 205);
        /*   $(".pagination").html(data.html);
          $("#pageNo").val(data.pageNo);
          $("#pageSize").val(data.pageSize); */
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
            
        }                
    });
	
	//目录导航
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	
	
	$( "#dialog_win3" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});
	$( ".dialog_close6" ).click(function( event ) {
		$( "#dialog_del2,#dialog_win3" ).dialog( "close" );
		event.preventDefault();
	});
	//新增部门
	$( "#dialog_add2" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});	
	$( ".dialog_add2" ).click(function( event ) {
		
		$( "#dialog_add2" ).dialog( "open" );
		event.preventDefault();
	});
	$( ".dialog_close2" ).click(function( event ) {
		$( "#dialog_add2" ).dialog( "close" );
		event.preventDefault();
	});
	//选择部门
	$( "#dialog_selectOffice" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});
	$( "#notDelectUser" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});
	
	
	$( ".dialog_close7" ).click(function( event ) {
		$( "#dialog_selectOffice" ).dialog( "close" );
		event.preventDefault();
	});
	//编辑部门
	$( "#dialog_edit" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});	
	$( ".dialog_edit" ).click(function( event ) {

		$("#dialog_edit #id").val($("#sysuser input[title=officeId]").val());
		$("#dialog_edit #name").val($("#sysuser input[title=officeName]").val());
		var OfficeId=$("#dialog_edit #id").val();
		if(OfficeId==null||OfficeId==""){
			$( "#dialog_selectOffice" ).dialog( "open" );
			event.preventDefault();
			return;
		}
		$( "#dialog_edit" ).dialog( "open" );
		event.preventDefault();
	});
	$( ".dialog_close3" ).click(function( event ) {
		$( "#dialog_edit" ).dialog( "close" );
		event.preventDefault();
	});
	//删除部门
	$( "#dialog_del" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});	
	$( ".dialog_del" ).click(function( event ) {
		var OfficeId=$("#sysuser input[title=officeId]").val();
		if(OfficeId==null||OfficeId==""){
			$( "#dialog_selectOffice" ).dialog( "open" );
			event.preventDefault();
			return;
		}
		$("#dialog_del span").text($("#sysuser input[title=officeName]").val());
		$( "#dialog_del" ).dialog( "open" );
		event.preventDefault();
	});
	$( ".delOffice" ).click(function( event ) {
		$( "#dialog_del" ).dialog( "close" );
		window.location.href="${ctx}/inspection/setup/sysUser/deleteOffice?id="+$("#sysuser input[title=officeId]").val();
		event.preventDefault();
	});
	$( ".dialog_close4" ).click(function( event ) {
		$( "#dialog_del" ).dialog( "close" );
		event.preventDefault();
	});
	//新增职员
	$( "#dialog_add3" ).dialog({
		autoOpen: false,
		width: 840,
		modal: true
	});	
	$( ".dialog_add3" ).click(function( event ) {
		$( "#dialog_add3" ).dialog( "open" );
		event.preventDefault();
	});
	$( ".dialog_close5" ).click(function( event ) {
		$( "#dialog_add3" ).dialog( "close" );
		event.preventDefault();
	});
	
	//编辑职员
	$( "#dialog_add4" ).dialog({
		autoOpen: false,
		width: 840,
		modal: true
	});	
	
	
	$( ".dialog_close8" ).click(function( event ) {
		$( "#dialog_add4" ).dialog( "close" );
		event.preventDefault();
	});
	
	$( ".dialog_close10" ).click(function( event ) {
		$( "#notDelectUser" ).dialog( "close" );
		event.preventDefault();
	});
	
	//选择职员
	$( "#dialog_selectUser" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});
	$( ".dialog_close9" ).click(function( event ) {
		$( "#dialog_selectUser" ).dialog( "close" );
		event.preventDefault();
	});
	//删除职员
	$( "#dialog_del2" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});	
	$( ".dialog_del2" ).click(function( event ) {
		 var checkedNum = $(".left4_main table input:checked").length;
		    if(checkedNum < 1) {
		    	$( "#dialog_selectUser" ).dialog( "open" );
				event.preventDefault();
		    return;
		    }
		    var id=new Array();
			
		    $(".left4_main table input:checked").each(function() {
				 id.push($(this).val());
			  });
			 $.post("${ctx}/inspection/setup/sysUser/countCustmerbySupervisor?ids="+id.toString(),function(result){
				 
					if(result!="0"){
						$( "#notDelectUser .font-14" ).html(result);
						$( "#notDelectUser" ).dialog( "open" );
						event.preventDefault();
					}else{
						$( "#dialog_del2" ).dialog( "open" );
						event.preventDefault();
					}
				});
			 
			 
		
	});
	$( ".delectUser" ).click(function( event ) {
		 var id=new Array();
		 $(".left4_main table input:checked").each(function() {
			 id.push($(this).val());
		  });
		
		 
		 $.post("${ctx}/inspection/setup/sysUser/deletes?ids="+id.toString(),function(result){
			 $( "#dialog_del2" ).dialog( "close" );
				event.preventDefault();
				$(".left4_main table input:checked").each(function() {
		      	  $(this).parent().parent().parent().remove();
		        });
			});
		 
	});
	$( ".dialog_close6" ).click(function( event ) {
		$( "#dialog_del2" ).dialog( "close" );
		event.preventDefault();
	});
	/*日期*/
	$( "#datepicker1" ).datepicker({
		dateFormat:'yy-mm-dd'
	}).attr("readonly",true);
	$( "#datepicker2" ).datepicker({
		dateFormat:'yy-mm-dd'
	}).attr("readonly",true);
	
});

$().ready(function() {  
	$("#inputFormUserAdd").validate({
		 errorPlacement: function(error, element) {
			  if ( element.is(":radio") )
			    error.appendTo( element.parent().next().next() );
			  else if ( element.is(":checkbox") )
				  error.appendTo( element.parent().next().next().next() );
			  else
			    error.appendTo( element.next() );
			},
	        rules: {  
	        	name: {     
	                required:true,     
	                minlength:2,
	                maxlength:8
	        	},
	        	loginName: {     
	        		isRightfulString:true, 
	                required:true,     
	                minlength:4,   
	                maxlength:15,
	                remote: {     
	                         url: "${ctx}/inspection/setup/sysUser/validatorLoginName",           
	                         type: "post",                            
	                         dataType: "json",                      
	                         data: {                                         
	                        	 loginName: function() { return $("#inputFormUserAdd #loginName").val();}       
	                				}     
	        				}
	        				},  
	        	roleIdArray : "required", 
			    email: {   
			    email: true  
			    } 
	 
	 			 }
	    				});  
	   
		$("#inputFormUserupdate").validate({
			 errorPlacement: function(error, element) {
				  if ( element.is(":radio") )
				    error.appendTo( element.parent().next().next() );
				  else if ( element.is(":checkbox") )
					  error.appendTo( element.parent().next().next().next() );
				  else
				    error.appendTo( element.next() );
				},
		        rules: {  
		        	name: {     
		                required:true,     
		                minlength:2,
		                maxlength:8
		        	},
		        	loginName: {     
		        		isRightfulString:true, 
		                required:true,     
		                minlength:4,   
		                maxlength:15,
		                remote: {     
		                         url: "${ctx}/inspection/setup/sysUser/validatorLoginName",         
		                         type: "post",                               
		                         dataType: "json",                           
		                         data: {                                        
		                        	 id: function() { return $("#inputFormUserupdate #id").val();},
		                        	 loginName: function() { return $("#inputFormUserupdate #loginName").val();}     
		                				}     
		        				}
		        				},  
		        	roleIdArray : "required", 
				    email: {   
				    email: true  
				    } 
		 
		 			 }
		    	});    
	 
		
	});  

jQuery.validator.addMethod("isRightfulString", function(value, element) {	  
  return this.optional(element) || /^[A-Za-z0-9_-]+$/.test(value);	  
}, "请输入合法字符串！");   
</script>
</body>
</html>