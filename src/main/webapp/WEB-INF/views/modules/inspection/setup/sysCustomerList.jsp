<!doctype html>
<html lang="en">
<head>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>客户管理-系统设置-芸豆会计</title>
<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.11.2.js" type="text/javascript"></script>
<script	src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"	type="text/javascript"></script>
<script	src="${pageContext.request.contextPath}/static/jquery/jquery.form.js"	type="text/javascript"></script>
<script	src="${pageContext.request.contextPath}/static/jquery-validation/1.11.1/jquery.validate.js"	type="text/javascript"></script>
<script	src="${pageContext.request.contextPath}/static/jquery/jquery-ui.min.js"	type="text/javascript"></script>
<link	href="${pageContext.request.contextPath}/static/jquery/jquery-ui.css"	rel="stylesheet" type="text/css" />
<script	src="${pageContext.request.contextPath}/static/jquery/jquery.ui.datepicker-zh-CN.js"	type="text/javascript"></script>
<link	href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.css"	type="text/css" rel="stylesheet" />
<link	href="${pageContext.request.contextPath}/static/bootstrap/css/page.css"	type="text/css" rel="stylesheet" />
<script type="text/javascript"	src="${pageContext.request.contextPath}/static/jquery/jquery.ztree.core-3.5.js"></script>
<script src="${ctxStatic}/js/workAccount.js" type="text/javascript"></script>
<link rel="stylesheet"	href="${pageContext.request.contextPath}/static/zTreeStyle/zTreeStyle.css"	type="text/css">
<link href="${pageContext.request.contextPath}/static/css/master_2.css"	type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/css/dialog.css"	type="text/css" rel="stylesheet" />
<style>
	.table_line1 tr td:nth-of-type(7) {width:8%;}
	.table_line1 tr td:nth-of-type(1) {width:5%;}
	.table_line1 tr td:nth-of-type(2) {width:30%;}
	.table_line1 tr td:nth-of-type(3) {width:15%;}
	.table_line1 tr td:nth-of-type(4) {width:15%;}
	.table_line1 tr td:nth-of-type(5) {width:10%;}
	#dialog_add1 {display: none;}
	#dialog_add1 .client_inner2 {padding: 20px;overflow: hidden;}
	#dialog_add1 input[type="checkbox"] {vertical-align: -2px;cursor: pointer;}
	#dialog_add1 label {margin: 0;cursor: pointer;}
	#dialog_add1 .chose_companies {clear: both;margin: 15px;padding:10px;border: 1px #dcdcdc solid;color: #666;height: 380px;overflow-y: auto; }
	#dialog_add1 .chose_companies div {line-height: 24px;margin: 4px 0;cursor: pointer;}
	#dialog_add1 .btn_top {margin-bottom: 10px;}
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
        //beforeExpand: beforeExpand,
		onClick: onClick
    }
};


var zNodes =[


{ id:"office${company.id}", pId:"${company.parent.id}", name:"${company.name}", iconSkin:"pIcon01",open:true },   
 <c:forEach items="${sysOfficeList}" var="office">
{ id:"${office.id}", pId:"${office.parent.id}", name:"${office.name}", iconSkin:"pIcon01"},
</c:forEach> 
             
 <c:forEach items="${userList1}" var="user">
	{ id:"${user.id}", pId:"${user.officeid}", name:"${user.name}", iconSkin:"pIcon02"},
</c:forEach>  
/* { id:"1", pId:"0", name:"总部", iconSkin:"pIcon01",open:true},
{ id:"2", pId:"1", name:"分部1", iconSkin:"pIcon01",open:true},
{ id:"3", pId:"2", name:"人1", iconSkin:"pIcon02",open:true},
{ id:"4", pId:"1", name:"分部2", iconSkin:"pIcon01",open:true},
{ id:"5", pId:"2", name:"人2", iconSkin:"pIcon02",open:true},  */

{ id:"", name:"-------------------------------------"},
	{ id:"加急客户", name:"加急客户"},
	{ id:"停止服务客户", name:"停止服务客户"},
	{ id:"服务到期客户", name:"服务到期客户"}
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
var sysCustomerId="";
var urgentSum=0;
var rapauditNum=0;
function onClick(e,treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	//singlePath(treeNode);
	//zTree.expandNode(treeNode);
	sysCustomerId=treeNode.id;
	switch(sysCustomerId)
	{
	case "加急客户":
		$.post("${ctx}/inspection/setup/sysCustomer/getbyOfficeId" ,function(result){
			rapauditNum=result;
			$("#dialog_selectOffice #rapauditNum").text(rapauditNum);
		});
		$("#titlebutton").html("<span onclick=\"dialogadd1()\" class=\"btn btn-default btn_w_a btn_bg_2 dialog_add1\">新增加急客户</span>&nbsp;<span onclick=\"dialogadd2()\" class=\"btn btn-default btn_w_a btn_bg_4\">停止加急服务</span>");
		
		$("#systCustomerForm #serviceexpirationdate").val("");
		$("#systCustomerForm #supervisor").val("");
		$("#systCustomerForm #urgent").val("1");
		$("#systCustomerForm #outOfService").val("");
		$("#systCustomerForm #officeId").val("");
		if($("#toptable tr td").length==7){
			$("#toptable tr td:last").remove();
		}
	  break;
	case "停止服务客户":
		$("#titlebutton").html("<span onclick=\"dialogadd()\" class=\"btn btn-default btn_w_a btn_bg_2\">新增客户</span>&nbsp;");
		
		$("#systCustomerForm #serviceexpirationdate").val("");
		$("#systCustomerForm #supervisor").val("");
		$("#systCustomerForm #urgent").val("");
		$("#systCustomerForm #outOfService").val("1");
		$("#systCustomerForm #officeId").val("");
		if($("#toptable tr td").length==6){
			$("#toptable tr ").append("<td>操作</td>");
		}
		
		
	  break;
	case "服务到期客户":
		var mydate = new Date();
		var year = "" + mydate.getFullYear();
		var month = (mydate.getMonth()+1);
		if(month<10){
			month="0"+month;
		}
		$("#titlebutton").html("<span onclick=\"dialogadd()\" class=\"btn btn-default btn_w_a btn_bg_2\">新增客户</span>");
		$("#systCustomerForm #serviceexpirationdate").val(year+month);
		$("#systCustomerForm #supervisor").val("");
		$("#systCustomerForm #urgent").val("");
		$("#systCustomerForm #outOfService").val("0");
		$("#systCustomerForm #officeId").val("");
		if($("#toptable tr td").length==6){
			$("#toptable tr ").append("<td>操作</td>");
		}
	  break;
	default:
		if(null==treeNode.pId){
			sysCustomerId="";
		}
		$("#titlebutton").html("<span onclick=\"dialogadd()\" class=\"btn btn-default btn_w_a btn_bg_2\">新增客户</span>&nbsp;<span onclick=\"dialogremove();\" class=\"btn btn-default btn_w_a btn_bg_4\">停止服务</span>");
		$("#systCustomerForm #urgent").val("");
		$("#systCustomerForm #outOfService").val("0");
		$("#systCustomerForm #serviceexpirationdate").val("");
		
		if(treeNode.iconSkin=="pIcon01"){
			sysCustomerId=sysCustomerId.replace("office", "");
			$("#systCustomerForm #officeId").val(sysCustomerId);
			$("#systCustomerForm #supervisor").val("");
		}else if(treeNode.iconSkin=="pIcon02"){
			sysCustomerId=sysCustomerId.replace("user", "");
			$("#systCustomerForm #supervisor").val(sysCustomerId);
			$("#systCustomerForm #officeId").val("");
		}
		
		if($("#toptable tr td").length==7){
			$("#toptable tr td:last").remove();
		}
		
	}
	$("#systCustomerForm #customerName").val("");
	pages();
}
</script>
</head>
<body>
<div class="message-pop"><span>保存成功！</span></div>
<p style="display:none;">

</p> 
<form:form style="display:none" id="updataForm"	modelAttribute="tCustomer" >
	<ul id="sysuser" class="ul-form">
		<form:input title="id" path="id" />
		<form:input title="supervisor" path="supervisor" />
	</ul>
</form:form>
<div class="main-row">
	<div class="main_index3">
		<div class="pane_main5">
			<div class="t_pane_a1 radius_5">
				<div class="user_pane_r2">
					<div class="hr20"></div>
					<ul class="nav nav-tabs" role="tablist">
						<li><a href="${ctx}/inspection/setup/sysUser/list">部门职员</a></li>
						<li class="active"><a href="${ctx}/inspection/setup/sysCustomer/">客户管理</a></li>
						<li><a href="${ctx}/inspection/setup/sysTel/">通讯录</a></li>
						<li><a href="${ctx}/inspection/setup/sysMenu/list">角色权限</a></li>
						<li><a href="${ctx}/inspection/setup/sysMessage/">系统消息</a></li>
					</ul>
					<div class="tab-content main5_con">
						<div class="tab-pane active" id="tab_e3">
							<div class="hr10"></div>
							
							<div class="sub_left4 right">
								<div  class="left4_main">
								<div  id="titlebutton"><span class="btn btn-default btn_w_a btn_bg_2 dialog_add_new">新增客户</span>
								<span onclick="dialogremove();" class="btn btn-default btn_w_a btn_bg_4">停止服务</span>
								</div>
								<div class="hr10"></div>
								<div class="left">
								<!-- 	<form id= "search"  >
									<input id="writer" type="text" class="ipt_s03 left" name="search"/>
									<input id="submit" type="button" class="ipt_04" value="搜索" />
									</form>-->
									
									<form:form id="systCustomerForm" modelAttribute="tCustomer" action="${ctx}/inspection/setup/sysCustomer/map" method="post" >
											<%-- <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
											<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/> --%>
											<form:hidden path="serviceexpirationdate"/>
											<form:hidden path="urgent"/>
											<form:hidden path="outOfService"/>
											<form:hidden path="supervisor"/>
											<form:hidden path="officeId"/>
											<form:input path="customerName" class="ipt_s03 left"/>
											<input id="searchSelect" type="button" class="ipt_04" value="搜索" />
	                               </form:form>
								</div>
								<div id="jiajisum" class="right">
									</div>
								
								<div class="clearfix"></div>
								<div class="hr10"></div>
								<p>客户列表 》公司</p>
									
								<div class="table_head">
									<table id="toptable" class="table_first table border_n1 table_line1" >
										<tr>
											<td class="t_top1"></td>
											<td class="t_top1">客户名称</td>
											<td class="t_top1">联系人</td>
											<td class="t_top1">联系电话</td>
											<td class="t_top1">到期时间</td>
											<td class="t_top1">记账人</td>
											
										</tr>
									</table>
								</div>
								<div class="table_containerbox">
									<table id="systCustomerTable" class="table border_n1 table_line1">
										<tbody id="tCustomerList" ></tbody>
									</table>
								</div>
									
									<%-- <div name="systCustomerForm" class="pagination">${page}</div> --%>
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
		</div>
	</div>
</div>
<div id="" class="displayCss" title="系统提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center">请至少选择一项需要操作的客户！</div>
		<div class="hr40"></div>
		<form>
		<div class="text-center">
			<button class="btn dialog_close9 btn-default btn_w_a btn_bg_4">确认</button>
		</div>
		</form>
	</div>
</div>
<div id="dialog_selectOffice" class="displayCss" title="系统提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center">加急客户指标一共不能超过“<span id="rapauditNum" class="font_cc6"></span>”个</div>
		<div class="hr40"></div>
		<form>
		<div class="text-center">
			<button class="btn dialog_close7 btn-default btn_w_a btn_bg_4">确认</button>
		</div>
		</form>
	</div>
</div>
<!--移除客户-->
<div id="dialog_remove"  class="displayCss" title="系统提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center">您确定要“停止”选中的客户的服务吗？</div>
		<div class="hr40"></div>
		<form>
		<div class="text-center">
		<input type="button" class="outOfService btn btn-default btn_w_a btn_bg_2 mar-rig10" value="确认" />
		<input type="button" class="btn outOfServicec btn-default btn_w_a btn_bg_4" value="取消" />
		</div>
		</form>
	</div>
</div>
<div id="ssss" style="display: none;">	
<form:form modelAttribute="tCustomer"  method="post">
<form:hidden path="id"/>
<form:select path="supervisor" class="form-control ipt_w4">
<form:options items="${userList}" itemLabel="name" itemValue="id"/>
</form:select>
<a href="javascript:void(0);" onclick="saves()" class="font_cc4 underline mar-lft10">保存</a>
</form:form>
	
	</div>
	
<!-- 新增加急客户 -->
<div id="dialog_add1"  class="displayCss" title="新增加急客户">
	<div class="client_inner2">
		<div class="client_form_a1">
			<div class="col-sm-offset-2 col-sm-10 btn_top">
			<!--  <span class="right">
				<button class="btn btn-default btn_w_a btn_bg_2">加急</button>
				</span>-->	
			</div>
			<div id="jiajiList" class="chose_companies">
			</div>
			<div class="col-sm-offset-2 col-sm-10">
				<span class="right">
				<input type="button" class="urgentGo btn btn-default btn_w_a btn_bg_2" value="确认加急"/>
				<!-- <input type="button" class="btn btn-default btn_w_a btn_bg_4 mar-rig10 cancel" value="取消"/> -->
				</span>
				<div class="right"></div>
			
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/customer.jsp"%>
<%@ include file="../../workterrace/tServiceChargeList.jsp"%>
<script>
function dialogadd1(){
	
	
	$("#systCustomerForm").ajaxSubmit({
        type: 'post',
        url: "${ctx}/inspection/setup/sysCustomer/ListUrgent" ,
        dataType : 'json',
        data: $("#systCustomerForm").serialize(),
        success: function(data){
        	item="";
        	var j=1;
        	$.each(data,function(i,result){   
	    		item += "<div><input type=\"checkbox\" value="+result['id']+">&nbsp;<label>"+j+"&nbsp;&nbsp;"+result['customerName']+"</label></div>"; 
	   	   	j++;   
	       }); 
	       $("#jiajiList").html(item);
	       $("#dialog_add1 .chose_companies div").each(function() {
				$(this).on("click",function() {
					var arg = arguments;
					!arguments.callee.open ? $("input",this).prop("checked",true) :$("input:checked",this).prop("checked",false);
					arg.callee.open = !arg.callee.open;
				})
			});
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
             //alert( "error");
        } 
	 });
	$( "#dialog_add1" ).dialog( "open" );
	return;
}
function dialogadd2(){
	var selectlenght=$("#systCustomerTable input[type='checkbox']:checked").length;
	if(selectlenght<1){
		$( "#dialog_selectUser" ).dialog( "open" );
		return;
	}
	var ids=new Array();
	$("#systCustomerTable input[type='checkbox']:checked").each(function(){
		ids.push($(this).val());
	});
		$.ajax({
			type:"post",
			url:" ${ctx}/inspection/setup/sysCustomer/urgentupdates?ids="+ids.toString()+"&u=0",
			success:function(result){
				$("#systCustomerTable input[type='checkbox']:checked").each(function() {
			        $(this).parent().parent().parent().remove();
			        });
				urgentSum-=selectlenght;
				
				$("#urgentSum").text(rapauditNum-urgentSum);
			}
		});
}
//urgentSum
/*
$("#dialog_add1 input[type='checkbox']").on("change",function() {
	var selectlenght=$("#dialog_add1 input[type='checkbox']:checked").length;
	alert(selectlenght);
});*/
	$(function() {
	
		//新增加急客户
		$( "#dialog_add1" ).dialog({
			autoOpen: false,
			width: 500,
			modal: true
		});	
		$( "#dialog_selectUser" ).dialog({
			autoOpen: false,
			width: 470,
			modal: true
		});
		$( ".dialog_close9" ).click(function( event ) {
			$( "#dialog_selectUser" ).dialog( "close" );
			event.preventDefault();
		});
		
		$( "#dialog_selectOffice" ).dialog({
			autoOpen: false,
			width: 470,
			modal: true
		});
		$( ".dialog_close7" ).click(function( event ) {
			$( "#dialog_selectOffice" ).dialog( "close" );
			event.preventDefault();
		});
		/*$( ".dialog_add1" ).click(function( event ) {
			$( "#dialog_add1" ).dialog( "open" );
			event.preventDefault();
		});*/
		$("#dialog_add1 .cancel").on("click",function( event) {
			$( "#dialog_add1" ).dialog( "close" );
			event.preventDefault();
		});
		$("#dialog_add1 .urgentGo").on("click",function(event) {
			var selectlenght=$("#dialog_add1 input[type='checkbox']:checked").length;

			if(selectlenght<1){
				$( "#dialog_selectUser" ).dialog( "open" );
				event.preventDefault();
				return;
			}
			if(urgentSum+selectlenght>rapauditNum){
				$( "#dialog_selectOffice" ).dialog( "open" );
				event.preventDefault();
				return;
			}
			var ids=new Array();
			$("#dialog_add1 input[type='checkbox']:checked").each(function(){
				ids.push($(this).val());
			});
				$.ajax({
					type:"post",
					url:" ${ctx}/inspection/setup/sysCustomer/urgentupdates?ids="+ids.toString()+"&u=1",
					success:function(result){
						$("#dialog_add1 input[type='checkbox']:checked").each(function() {
					        $(this).parent().remove();
					        });
						$("#urgentSum").text(rapauditNum-urgentSum);
						pages();
					}
				});
			});
		
	})


function edits(event){
	
	var e = event || window.event;
	var thistag=$(e.srcElement).parent();
	var id=thistag.attr("name");
	thistag.html($("#ssss").html());
	
	$.ajax({  
			type:"POST",
			url:"forms", 
		    data: "id="+id,
	        success:function(json){  
	    	   $json = json;
	    	   thistag.find("form").formEdit($json);
	       }
	})
}
function saves(event){
	
	var e = event || window.event;
	var thistag=$(e.srcElement).parent().parent();
	 var supervisorText=thistag.find("select option:selected").text();
	thistag.find("form").ajaxSubmit({
	        type: 'post',
	        url: "${ctx}/inspection/setup/sysCustomer/update" ,
	        data: thistag.find("form").serialize(),
	        success: function(data){
	        	thistag.html(supervisorText+"<a href='javascript:void(0);' onclick='edits();' class='font_cc4 underline mar-lft10'>编辑</a>")
	        }  
		 });
	//var ss=$(e.srcElement).parent().html("<a href='javascript:void(0);' onclick='edits();'  class='font_cc4 underline mar-lft10'>编辑</a>");
	
}

function pages(){
	
	$("#systCustomerForm").ajaxSubmit({
        type: 'post',
        url: "${ctx}/inspection/setup/sysCustomer/map" ,
        dataType : 'json',
        data: $("#systCustomerForm").serialize(),
        success: function(data){
        	var item=""; 
        	
	        	switch(sysCustomerId)
	        	{
	        	case "加急客户":
	        		urgentSum=data.length;
	        		$("#jiajisum").html("<span style='line-height:26px;' class=\"font-24 font_cc4\">剩余<span id=\"urgentSum\">"+(rapauditNum-urgentSum)+"</span>名额 </span>&nbsp;&nbsp;总计有“<span class=\"font_cc6\">"+rapauditNum+"</span>”个加急指标");
	        		
	        		$.each(data,function(i,result){   
	      	    		item += "<tr><td><lable><input type='checkbox' value="+result['id']+" /></lable></td><td>"+result['customerName']+"</td> <td>"+result['contactPerson']+"</td> <td>"+result['officePhone']+"</td><td>"+result['serviceexpirationdate']+"</td><td name="+result['id']+" >"+result['supervisorName']+"</td></tr>"; 
	      	    		}); 
	        		
	        	$("#systCustomerTable  #editTd").html("记账人")
	        	  break;
	        	case "停止服务客户":
	        		$("#jiajisum").html("");
	        		$.each(data,function(i,result){   
	      	    		item += "<tr><td><lable><input type='checkbox' value="+result['id']+" /></lable></td><td>"+result['customerName']+"</td> <td>"+result['contactPerson']+"</td> <td>"+result['officePhone']+"</td><td>"+result['serviceexpirationdate']+"</td><td>"+result['supervisorName']+"</td><td><input type=\"button\" onclick='huifu("+result['id']+");' class=\"btn btn_f4\" value=\"恢复服务\"/></td></tr>"; 
	      	  	    	}); 
	            	$("#systCustomerTable  #editTd").html("操作")
	            	
	        		
	        	  break;
	        	case "服务到期客户":
	        		$("#jiajisum").html("");
	        		$.each(data,function(i,result){   
	      	    		item += "<tr><td><lable><input type='checkbox' value="+result['id']+" /></lable></td><td>"+result['customerName']+"</td> <td>"+result['contactPerson']+"</td> <td>"+result['officePhone']+"</td><td>"+result['serviceexpirationdate']+"</td><td>"+result['supervisorName']+"</td><td><input type=\"button\" onclick='dialogmonss("+result['id']+",\""+result['customerName']+"\");' class=\"btn btn_f5\"/></td></tr>"; 
	      	  	    	}); 
	            	$("#systCustomerTable  #editTd").html("操作")
	            	
	        	  break;
	        	default:
	        		$("#jiajisum").html("");
	        		$.each(data,function(i,result){   
	      	    		item += "<tr><td><lable><input type='checkbox' value="+result['id']+" /></lable></td><td>"+result['customerName']+"</td> <td>"+result['contactPerson']+"</td> <td>"+result['officePhone']+"</td><td>"+result['serviceexpirationdate']+"</td><td name="+result['id']+" >"+result['supervisorName']+"<a href='javascript:void(0);' onclick='edits();'  class='font_cc4 underline mar-lft10'>编辑</a></td></tr>"; 
	      	    		}); 
	        	$("#systCustomerTable  #editTd").html("记账人")
	        	}
        	
        
        	
        	//停止服务
  	    	 /* $(".pagination").html(data.html);
	         $("#pageNo").val(data.pageNo);
	         $("#pageSize").val(data.pageSize); */
  	    	 $("#tCustomerList").html(item);
  	    	fixTableHead($("#tCustomerList").height(), 260, 176);
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
             //alert( "error");
        } 
	 });
	
	
	
}

function dialogadd() {
	$( "#dialog_add_new" ).dialog( "open" );
	return;
}
function dialogremove() {
	var selectlenght=$("#systCustomerTable input:checked").length;
	alert(selectlenght);
	if(selectlenght<1){
		$( "#dialog_selectUser" ).dialog( "open" );
		event.preventDefault();
		return;
	}
	$( "#dialog_remove" ).dialog( "open" );
	return;
}

function huifu(id,event){
	
	var e = event || window.event;
	var thistag=$(e.srcElement).parent();
	$.ajax({
		type:"post",
		url:" ${ctx}/inspection/setup/sysCustomer/outOfServiceupdate?ids="+id+"&u=1",
		success:function(result){
			thistag.parent().remove();
		}
	});
	return;
}

$(function(){
	
	pages();
	$( ".outOfService" ).click(function( event ) {
		var ids=new Array();
		$("#systCustomerTable input[type='checkbox']:checked").each(function(){
			ids.push($(this).val());
		});
			$.ajax({
				type:"post",
				url:" ${ctx}/inspection/setup/sysCustomer/outOfServiceupdate?ids="+ids.toString()+"&u=0",
				success:function(result){
					$("#systCustomerTable input[type='checkbox']:checked").each(function() {
				        $(this).parent().parent().parent().remove();
				        $( "#dialog_remove" ).dialog( "close" );
						event.preventDefault();
				        });
				}
			});
	});
	
	$( ".outOfServicec" ).click(function( event ) {
			$( "#dialog_remove" ).dialog( "close" );
			event.preventDefault();
	});
	//目录导航
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	/******新增客户****/
	var dialog, form,
	dialog = $( "#dialog_add_new" ).dialog({
		autoOpen: false,
		width: 840,
		modal: true
	});	
	$( "#searchSelect" ).on('click',function( ) {
		pages();
	});	

	//移除客户
	$( "#dialog_remove" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});	
	
	

})


var ctx = "${ctx}";
var contextPath="${pageContext.request.contextPath}";
</script>

</body>
</html> 