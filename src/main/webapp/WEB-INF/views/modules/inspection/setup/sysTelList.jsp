<!doctype html>
<html lang="en">
<head>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>通讯录-系统设置-芸豆会计</title>
<script src="${pageContext.request.contextPath}/static/jquery/jquery.js"	type="text/javascript"></script>
<script	src="${pageContext.request.contextPath}/static/jquery/jquery.form.js"	type="text/javascript"></script>
<script	src="${pageContext.request.contextPath}/static/jquery/jquery-jtemplates.js"	type="text/javascript"></script>
<script	src="${pageContext.request.contextPath}/static/jquery-validation/1.11.1/jquery.validate.js"	type="text/javascript"></script>
<script	src="${pageContext.request.contextPath}/static/jquery/jquery-ui.min.js"	type="text/javascript"></script>
<link	href="${pageContext.request.contextPath}/static/jquery/jquery-ui.css"	rel="stylesheet" type="text/css" />
<script	src="${pageContext.request.contextPath}/static/jquery/jquery.ui.datepicker-zh-CN.js"	type="text/javascript"></script>
<script	src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"	type="text/javascript"></script>
<link	href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.css"	type="text/css" rel="stylesheet" />
<link	href="${pageContext.request.contextPath}/static/bootstrap/css/page.css"	type="text/css" rel="stylesheet" />
<script type="text/javascript"	src="${pageContext.request.contextPath}/static/jquery/jquery.ztree.core-3.5.js"></script>
<script src="${ctxStatic}/js/workAccount.js" type="text/javascript"></script>
<link rel="stylesheet"	href="${pageContext.request.contextPath}/static/zTreeStyle/zTreeStyle.css"	type="text/css">
<link href="${pageContext.request.contextPath}/static/css/master_2.css"	type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/css/dialog.css"	type="text/css" rel="stylesheet" />
<style>
	.table_line1 tr td:nth-of-type(1) {width:12%;}
	.table_line1 tr td:nth-of-type(2) {width:16%;}
	.table_line1 tr td:nth-of-type(3) {width:36%;}
	.table_line1 tr td:nth-of-type(4) {width:23%;}
</style>
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
       // beforeExpand: beforeExpand,
		onClick: onClick
    }
};
var zNodes =[
            <c:forEach items="${sysOfficeList}" var="office">
 			 { id:"${office.id}", pId:"${office.parentId}", name:"${office.name}",open:true, iconSkin:"pIcon01"},
            </c:forEach> 

              {id:"已移除的职员", pId: 0 ,name:"已移除的职员",open:true, iconSkin:"pIcon01"}
  			
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
	//singlePath(treeNode);
	//zTree.expandNode(treeNode);
	
	cid=treeNode.id;
	switch(cid)
	{
	case "已移除的职员":
		$("#delFlag").val("1");
		$("input[title=officeId]").val("");
		$("input[title=officeName]").val("");
	  break;
	default:
		$("#delFlag").val("0");
		$("input[title=officeId]").val(treeNode.id);
		$("input[title=officeName]").val(treeNode.name);
	}
	var  sss=$("input[title=company]").val();
	 if(cid==sss){
		 $("#delFlag").val("0");
		 $("input[title=officeId]").val("");
		 $("input[title=officeName]").val("");
	 }
	 $("#name").val("");
	$("#search").ajaxSubmit({
        type: 'post',
        url: "${ctx}/inspection/setup/sysTel/search" ,
        dataType : 'json',
        data: $("#searchForm").serialize(),
        success: function(data){
        		 $("#userList").setTemplateElement("template").processTemplate(data);
        	
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
        }                
    });
	
	
	
}
$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
});

</script>
</head>
<body>
<!-- Templates -->
<p style="display:none;">
<textarea id="template"  >
	{#foreach $T as user}
	    <tr>
	    <td>{$T.user.name}</td>
				<td>{$T.user.mobile}</td>
				<td>{$T.user.email}</td>
				<td>{$T.user.qq}</td>
				<td>{$T.user.officeName}</td>
	    </tr>
	 {#/for}
</textarea>
</p>
	<div class="main-row"  >
		<div class="main_index3">
			<div class="pane_main5">
				<div class="t_pane_a1 radius_5">
					<div class="user_pane_r2" >
						<div class="hr20"></div>
						<ul class="nav nav-tabs" role="tablist">
							<li><a href="${ctx}/inspection/setup/sysUser/list">部门职员</a></li>
						<li><a href="${ctx}/inspection/setup/sysCustomer/">客户管理</a></li>
						<li  class="active"><a href="${ctx}/inspection/setup/sysTel/">通讯录</a></li>
						<li><a href="${ctx}/inspection/setup/sysMenu/list">角色权限</a></li>
						<li><a href="${ctx}/inspection/setup/sysMessage/">系统消息</a></li>
						</ul>
						<div class="tab-content main5_con" >
							<div class="tab-pane active" id="tab_e4" >
								<div class="hr10"></div>

								<div class="sub_left4 right">
									<div class="left4_main">
										<div class="left">
										<!-- 	<form id= "search"  >
												<input id="writer" type="text" class="ipt_s03 left" name="search"/>
												<input id="submit" type="button" class="ipt_04" value="搜索" />
											</form> -->
											
												<form:form id="search" modelAttribute="user" action="${ctx}/inspection/setup/sysUser/list" method="post" >
		<!--  <input id="pageNo" name="pageNo" type="text" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="text" value="${page.pageSize}"/>-->
			<form:hidden title="company" path="company.id" htmlEscape="false" maxlength="50" class="input-medium"/>
			<form:hidden path="loginName" htmlEscape="false" maxlength="50" class="input-medium"/>
			<form:hidden title="officeId" path="office.id" htmlEscape="false" maxlength="50" class="input-medium"/>
			<form:hidden title="officeName" path="office.name" htmlEscape="false" maxlength="50" class="input-medium"/>
			<form:hidden path="delFlag" htmlEscape="false" maxlength="50" class="input-medium"/>
			<form:input path="name" htmlEscape="false" maxlength="50" class="ipt_s03 left"/>
			<input id="submit" type="button" class="ipt_04" value="搜索" />
		
	</form:form>
										</div>
										<div class="clearfix"></div>
										<div class="hr10"></div>
										<p>客户列表 》公司</p>
										<div class="table_head">
											<table class="table border_n1 table_line1 table_first"
												width="100%" >
												<tr>
													<!-- <td class="t_top1"></td> -->
													<td class="t_top1">姓名</td>
													<td class="t_top1">手机号</td>
													<td class="t_top1">邮箱</td>
													<td class="t_top1">QQ</td>
													<td class="t_top1">部门</td>
												</tr>
											</table>
										</div>
										<div class="table_containerbox">
											<table class="table border_n1 table_line1">
												<tr>
														<tbody id="userList"   ></tbody>
												</tr>
											</table>
										</div>
						<%-- <div class="pagination">${page}</div> 
 --%>								</div>
							</div>

							<div class="sub_left3 left">
								<div class="tit_sc1">职员列表</div>
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
<!-- 			<div class="footer_s3 text-center">版权所属 北京芸智慧财务有限公司    技术支持 麟腾传媒</div> -->

		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		
		$("#search").ajaxSubmit({
	        type: 'post',
	        url: "${ctx}/inspection/setup/sysTel/userlist" ,
	        dataType : 'json',
	        data: $("#searchForm").serialize(),
	        success: function(data){
	          $("#userList").setTemplateElement("template").processTemplate(data);
	          fixTableHead($("#userList").height(), 222, 174);
	        },
	        error: function(XmlHttpRequest, textStatus, errorThrown){
	             //alert( "error");
	        }                
	    }); 
		 $("#submit").click(function() {
		    	$("#search").ajaxSubmit({
			        type: 'post',
			        url: "${ctx}/inspection/setup/sysTel/search" ,
			        dataType : 'json',
			        data:  $("#searchForm").serialize(),
			        success: function(date){
			          $("#userList").setTemplateElement("template").processTemplate(date);
			        },
			        error: function(XmlHttpRequest, textStatus, errorThrown){
			             //alert( "error");
			        }                
			    });  
		   }); 
	});
	
</script>
</body>
</html>