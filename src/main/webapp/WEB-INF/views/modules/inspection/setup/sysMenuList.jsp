<!doctype html>
<html lang="en">
<head>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>角色权限-系统设置-芸豆会计</title>
<script src="${pageContext.request.contextPath}/static/jquery/jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/fixedheadertable/jquery.fixedheadertable.js"></script>
<script src="${ctxStatic}/js/workAccount.js"></script>
<link href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery.ztree.core-3.5.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/zTreeStyle/zTreeStyle.css" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/master_2.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/fixedheadertable/defaultTheme.css" rel="stylesheet" media="screen" />
<script type="text/javascript">
$(document).ready(function(){
	fixTableHeader($(window).height() - 197, $(window).height() - 161);
	$(".sidebar_user").css({
		"height":$(window).height() - 173
	})
/*$("#searchForm").ajaxSubmit({
		
        type: 'post',
        url: "${ctx}/inspection/setup/sysMenu/listMenu" ,
        dataType : 'json',
        data: $("#searchForm").serialize(),
        success: function(data){
          //  设置模板 加载数据
           $("#menuList").setTemplateElement("template").processTemplate(data);
      
          
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
             //alert( "error");
        }                
    });*/
    $("input:checkbox").prop("disabled",true).prop("checked", true);
	
 });
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
{ id:0, pId:0, name:"系统角色", open:true},
{ id:1, pId:0, name:"记账公司管理员", iconSkin:"pIcon01"},
{ id:2, pId:0, name:"记账员", iconSkin:"pIcon01"}
/*<c:forEach items="${rolelist}" var="role">
	{ id:"${role.id}", pId:0, name:"${role.name}", iconSkin:"pIcon01"},
	</c:forEach>*/
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
	var cid=treeNode.id;
	
	switch(cid)
	{
	case 1:
		$("input:checkbox").prop("checked", true);
		
	  break;
	case 2:
		
		$("input:checkbox").slice(61,85).prop("checked", false);
	  break;
	default:
	}
	
	/*$(".main_index3 #roleId").val(treeNode.id);
	$("#searchForm").ajaxSubmit({
        type: 'post',
        url: "${ctx}/inspection/setup/sysMenu/listMenu" ,
        dataType : 'json',
        data: $("#searchForm").serialize(),
        success: function(data){
           $("#menuList").setTemplateElement("template").processTemplate(data);
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
             //alert( "error");
        }                
    });*/
}

$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
});
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
						<li><a href="${ctx}/inspection/setup/sysUser/list">部门职员</a></li>
						<li><a href="${ctx}/inspection/setup/sysCustomer/">客户管理</a></li>
						<li><a href="${ctx}/inspection/setup/sysTel/">通讯录</a></li>
						<li class="active"><a href="${ctx}/inspection/setup/sysMenu/list">角色权限</a></li>
						<li><a href="${ctx}/inspection/setup/sysMessage/">系统消息</a></li>
					</ul>
					<div class="tab-content main5_con">
						<div class="tab-pane active" id="tab_e5">
							<div class="hr10"></div>
							
							<div class="sub_left4 right">
								<div class="left4_main">
								
								<div class="tit_sc2">权限项</div>
								
								
								<form:form style="display:none" id="searchForm" modelAttribute="menu" action="${ctx}/inspection/setup/sysMenu/listMenu" method="post" class="breadcrumb form-search">

		<ul class="ul-form">
			<li><label>父级编号：</label>
			</li>
				<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>是否在菜单中显示：</label>
				<form:input path="isShow" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>角色Id：</label>
				<form:input path="roleId" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>

	<!--  <table id="contentTable" class="table mar-bot10 table-bordered" width="100%">
		<thead class="thead_a1">
			<tr><th>编号</th><th>父级编号</th><th>名称</th><th>是否在菜单中显示</th>
				<th><lable><input type="checkbox" /></lable>授权</th></tr>
		</thead>
		<tbody id="menuList">
	
			</tbody>
	</table>-->
							
								<div class="table_container">
									<table class="table mar-bot10 table-bordered" id="myTable02" width="100%">
										<thead class="thead_a1">
											<th>模块</th>
											<th>功能列表</th>
											<th>操作</th>
											<th>授权</th>
											
										</thead>
										    <tr>
										      <td>首页</td>
										      <td>客户</td>
										      <td>新增</td>
										      <td><lable><input type="checkbox" /></lable></td>
										    </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>工作汇总查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td>个人提醒</td>
										      <td>预览</td>
										  <td><lable><input type="checkbox" /></lable></td>
										       </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>快捷入口</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td>系统消息</td>
										      <td>预览</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>快捷入口</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td>工作台</td>
										      <td>记账</td>
										      <td>客户列表查看</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>客户修改</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>客户删除</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>在线会计入口</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>记账月统计</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>收费查看</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>收费新增</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>收费修改</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>收费删除</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>回收站入口</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td>报税</td>
										      <td>客户列表查看</td>
										  <td><lable><input type="checkbox" /></lable></td>
										       </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>客户修改</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>客户删除</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>在线会计入口</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>保税月统计</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>收费查看</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>收费新增</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>收费修改</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>收费删除</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>回收站入口</td>
										  <td><lable><input type="checkbox" /></lable></td>
										       </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>报税新增</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>报税查看</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td>工资</td>
										      <td>客户列表查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>客户修改</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>客户删除</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>在线会计入口</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>保税月统计</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>收费查看</td>
										  <td><lable><input type="checkbox" /></lable></td>
										       </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>收费新增</td>
										  <td><lable><input type="checkbox" /></lable></td>
										       </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>收费修改</td>
										  <td><lable><input type="checkbox" /></lable></td>
										       </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>收费删除</td>
										  <td><lable><input type="checkbox" /></lable></td>
										       </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>回收站入口</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>工资新增</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>工资修改</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>工资查看</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td>日常</td>
										      <td>客户列表查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>客户修改</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>客户删除</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>在线会计入口</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>保税月统计</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>收费查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>收费新增</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>收费修改</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>收费删除</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>回收站入口</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>跟进查看</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>跟进新增</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>跟进修改</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>跟进删除</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td>个人提醒</td>
										      <td>个人提醒查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>个人提醒新增</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>个人提醒修改</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>个人提醒删除</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td>回收站</td>
										      <td>客户查看</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>客户还原</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td>工作统计</td>
										      <td>工作量汇总表</td>
										      <td>查看</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>详细数据列表入口</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td>工作量明细表</td>
										      <td>查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td>服务收费列表</td>
										      <td>查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td>收费审核</td>
										      <td>查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>修改</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>打印</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td>详细数据列表</td>
										      <td>查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td>系统设置</td>
										      <td>部门职员</td>
										      <td>部门查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>部门新增</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>部门修改</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>部门删除</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>职员查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>职员新增</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>职员修改</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>职员删除</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td>客户管理</td>
										      <td>客户查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>客户新增</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>客户修改</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>收费查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td>通讯录</td>
										      <td>部门查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>职员查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td>角色权限</td>
										      <td>权限查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td>系统信息</td>
										      <td>系统消息查看</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td>在线会计</td>
										      <td>首页</td>
										      <td>票据统计查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>个人设置</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>数据导入</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>数据迁移</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td>凭证</td>
										      <td>凭证查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>凭证新增</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>凭证修改</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>凭证删除</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>科目查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>科目新增</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>自动摊销修改</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>模板查看</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>模板新增</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>模板修改</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>模板删除</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>票据查看</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>票据新增</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>票据修改</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>票据删除</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td>账簿</td>
										      <td>查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td>报表</td>
										      <td>查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td>设置</td>
										      <td>财务初始余额查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>财务初始余额新增</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>财务初始余额修改</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td>数据</td>
										      <td>数据查看</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td>科目设置</td>
										      <td>科目查看</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>科目新增</td>
										   <td><lable><input type="checkbox" /></lable></td>
										      </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>科目修改</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										    <tr>
										      <td></td>
										      <td></td>
										      <td>科目删除</td>
										    <td><lable><input type="checkbox" /></lable></td>
										     </tr>
										  </table>
								</div>
								

									<div class="clearfix"></div>
								</div>
							</div>

							<div class="sub_left3 left">
								<div class="tit_sc1">角色定义</div>
							<!--  	<div class="right pad_t_b5">
									<a class="btn btn-default btn_n4 bg_p6 btn_i3">新增</a>
									<a class="btn btn-default btn_n4 bg_p6 btn_i2">编辑</a>
									<a class="btn btn-default btn_n4 bg_p6 btn_i4">删除</a>
								</div>-->
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
				</div>
				<div class="hr20"></div>
			</div>

			

		</div>
	</div>
</div>
 <!-- Templates -->
<p style="display:none"><textarea id="template" ><!--

{#foreach $T as menu}

<tr><td>{$T.menu.id}</td><td>{$T.menu.parentId}</td><td>{$T.menu.name}</td><td>{$T.menu.isShow}</td><td><lable><input type="checkbox" /></lable></td>	
			</tr>

		   {#/for}
-->
</textarea></p>
 <!-- Output elements -->		
</body>
</html>