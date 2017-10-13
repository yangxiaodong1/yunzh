<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>

<head>
<meta charset="utf-8" />
<title>科目-在线会计-芸豆会计</title>
<meta name="keywords" content="关键字,关键字,关键字" />
<meta name="description" content="描述。……" />
<meta name="Author" content="author_bj designer_bj">
<link href="${ctxStatic}/accountAndbalance/css/reset.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/accountAndbalance/css/subjectset.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/accountAndbalance/css/setCom.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/accountAndbalance/css/dialog.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctxStatic}/accountAndbalance/js/jquery-1.11.2.min.js"></script>
<link href="${pageContext.request.contextPath}/static/accountAndbalance/css/defaultTheme.css" rel="stylesheet" type="text/css">
<script src='${pageContext.request.contextPath}/static/accountAndbalance/js/jquery.fixedheadertable.js'></script>
<style>
 .table-account-wrapper tbody tr td {
	text-align:left;
	text-indent:1em;
}
/*
.message-pop{width: 250px;height: 100px;opacity: 0;padding-top: 20px;position: fixed;bottom: -130px;right: -300px;border: 1px solid #aaa;font-family: "Microsoft Yahei";background: #fff;z-index:99;}
.message-pop i.i-mess{width: 18px;height: 18px;cursor: pointer;background: url(${ctxStatic}/images/close.png) top center no-repeat;position: absolute;top: 0;right: 2px;}
.message-pop h4{text-align: center;}
.message-pop p{padding-top: 30px;text-align: center;}
*/
.table-account-wrapper tbody tr.addNew {background: #ccf3fc;}

/**20160118**/
body{background:#eff1f3;}
.tabpane-main{width:960px;margin:0 auto;position:relative;}
.account-wrapper{position:absolute;min-width:960px;width:960px;font-size:12px;padding:0;}
.account-wrapper *{font-size:12px;}
.table-account-wrapper tbody tr:nth-child(2n){background:#f3faff;}
.table-account-wrapper tbody tr:hover{background:#cff6fb;}
.table-subjectset .addData td{background-color:transparent;}
.fht-table-wrapper .fht-fixed-body .fht-tbody, .fht-table-wrapper .fht-tbody{overflow-x:hidden;}
.account-wrapper .tb{min-width:960px;width:960px;box-sizing:border-box;}
.mask{z-index:100;}
.layerS{z-index:102;}
.confirm{padding:0 10px;}
td #fname{margin-top:5px;}
.account-wrapper .tb{margin-top:10px;}
/*修改*/
.deleteSubject {display: none;height: 200px;}
.confirm {font-size: 14px;line-height: 30px;margin: 30px 0;}
.sure {background: #fba22c;float: right;}
.layer button.fsave{background: #408ae1;float: left;}

</style>
</head>
<body style="overflow:hidden;">
<div class="mask"></div>
<div class="message-pop">
<span>
保存成功！
</span>
</div>
<div class='tabpane-main'>
	<div class="account-wrapper subjectset">
		<h2 class="h2-tit">科目设置</h2>
		<div class="th clearfix">
			<div class="th-left">
				<span>组词说明：1001 01 01 01</span>
			</div>
			<div class="th-right clearfix">
				<span class="sp-lead">引入</span>
			</div>
		</div>
		
		<div class="tb">			
			<table class="table-subjectset table-account-wrapper" id='myTable01' cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th width="30%">科目编号</th>
						<th width="38%">科目名称</th>
						<th width="32%">余额方向</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="tAccount" varStatus="status">
						<tr class="addData tr${tAccount.parentId.id }" id="tr${tAccount.accuntId}">
							<td Style="text-indent:${tAccount.level}em">
								${tAccount.accuntId}
								<c:if test="${tAccount.level!=4}">
									<i onclick="ddsjxx('${tAccount.accuntId}','${tAccount.id}','${tAccount.detail}','${tAccount.accountName}')"></i>
								</c:if>									
							</td>
							<td Style="text-indent:${tAccount.level}em" <c:if test="${tAccount.isEnable==0}">ondblclick="changeText($(this),'${tAccount.id}')"</c:if>>${tAccount.accountName}</td>
							<td Style="text-align:center">
								<c:if test="${tAccount.dc==1}">借</c:if>
								<c:if test="${tAccount.dc==-1}">贷</c:if>
								<c:if test="${tAccount.isEnable==0}"><i onclick="deleteinfo('${tAccount.id}','${tAccount.accountName}',$(this))"></i></c:if>
							</td>
						</tr>
					</c:forEach> 
				</tbody>
			</table>
		</div>
		
		<div class="layer layerS IntroduceSubject">
			<h5 class="h6-kr">引入科目<i class="i-close"></i></h5>
			<ul class="ul-IntroduceSubject clearfix">
				<li>提示：请先确认当前帐套是否有自定义科目，
如有自定义科目会导致引入不成功</li>
				<li class="clearfix"><label for="">引入帐套</label>
					<select class="selector">
						<c:forEach items="${customerInfo}" var="customer">
						<c:if test="${customer.id!=sessionCustomer.id}">
							<option value="${customer.id }">${customer.customerName}</option>
						</c:if>							
						</c:forEach>						
					</select>
				</li>
			</ul>
			<div class="buttonW clearfix">
				<button style="margin-right:20px;" class="cancel">取消</button><button class="fsave" onclick="daoru()">导入</button>
			</div>
		</div>
		
		<!-- 修改 -->
		<div class="layer layerS deleteSubject">
			<h5 class="h6-kr">系统提示<i class="i-close"></i></h5>
			<div class="confirm"></div>
			<div class="buttonW clearfix">				
				<button class="fsave cancel_delete">取消</button>
				<button class="sure">确定</button>
			</div>
		</div>
		
	</div>
</div>
<!--页面脚本区S-->
<!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->

<script>

$winH=$(window).height();
$mainH2=$winH;
$(".tb").height(function(){
	return parseInt($(window).height()-120);	
})
var h_1=$(".tb").height();
var winh1=$("#myTable01").height();
if(winh1>h_1){
	$('#myTable01').fixedHeaderTable({ 
		altClass: 'odd',
		autoShow: false
	});
}
$('.fht-default').show();



$(".sp-lead").bind("click",function(){
	$(".IntroduceSubject").show();
	$(".mask").show();
})



//删除科目
var did;
var dobj;
var sid;
function deleteinfo(id,name,obj){
	did="";
	sid="";
	did=id;
	dobj=$(obj);
	$(".deleteSubject").show();
	$(".mask").show();
	$(".confirm").text("你确定要删除'' "+name+" ''科目吗？");
}
	
//删除科目
$(".sure").on("click",function(event){	
	if(did!=null && did!="" && did!=undefined){
		sid="";
		$.ajax({  
	        type : "GET",  
	        url : "${ctx}/account/tAccount/delete",  
	        data : "id="+did,  
	        dataType: "text",  
	        success : function(msg) {         
	        	if(msg==1){
	        		messagePop("有子级不能删除");
	        	}
	        	if(msg==2){
	        		messagePop("初始余额表正在使用不能删除");
	        	}
	        	if(msg==0){
	        		messagePop("删除科目信息成功");
	        		$(dobj).parents(".addData").remove();
	        	}
	        	
	        },error : function() { 	        	
	        	messagePop("失败");
	        } 
	    });
		did="";
	}
		
	
	if(sid!=null && sid!="" && sid!=undefined){
			did="";
			$.ajax({  
   	        type : "GET",  
   	        url : "${ctx}/account/tAccount/parentInfo",  
   	        data : "id="+sid,  
   	        dataType: "json",  
   	        success : function(msg) { 
   	        	if(msg!=null){
   	        		var fdc="借";        		
   	        		if(msg.dc=='-1'){
   	        			fdc="贷";
   	        		}       
   	        		var levelem=msg.level*1+1;        
   	        		if($("tr[id^=tr"+msg.accuntId+"]:last").val()!=undefined){
   	        			$("tr[id^=tr"+msg.accuntId+"]:last").after("<tr class='addNew'  id='remove'><td Style='text-indent:"+levelem+"em'><input type='hidden' id='addid' value='"+msg.id+"'/><input type='hidden' id='accuntIdfor' value='"+msg.accuntIdfor+"'/>"+msg.accuntIdfor+"</td><td><input type='text' id='addname' placeholder='按回车键可继续添加同级科目'></td><td Style='text-align:center'><input type='hidden' id='fdc' value='"+msg.dc+"'/>"+fdc+"</td></tr>");
   	        		}        		
   	        		$("#addname").focus();
   	        		if(i==0){
   	        			$("tr[id!='remove']").click(function(){
  		        				$("#remove").remove();
  		        			})
  		        		}
   	        	}
   	        },error : function() {
   	        	messagePop("失败");
   	        } 
   	    });
		sid="";
	}
	$(".deleteSubject").hide();
	$(".mask").hide();
});



$(".i-close").bind("click",function(){
	$(".deleteSubject").hide();
	$(".IntroduceSubject").hide();
	$(".mask").hide();
})
$(".cancel").bind("click",function(){
	$(".IntroduceSubject").hide();
	$(".mask").hide();
})

//修改
$(".cancel_delete").bind("click",function(){
	did="";
	sid="";
	$(".deleteSubject").hide();
	$(".mask").hide();
})

//删除
/* function deleteinfo(id,name){
	if(confirm("你确定要删除'' "+name+" ''科目吗？")){
		var timer = setTimeout(function() {
			var nTop = $(window).scrollTop();
			var nTop1=$('.tb').scrollTop();
			window.location="${ctx}/account/tAccount/delete?id="+id+"&nTop="+nTop+"&nTop1="+nTop1;
		},0);
	} 
	
}*/
var i=0;//判断是否按下回车
var upd;
var uval;
function changeText(obj,id) {	
	if($("#fname").val()==undefined){
		var text = obj.text();		
		obj.html("<input id='fname' type='text' value='"+text+"'/> <input type='hidden' id='fid' value='"+id+"'/>");
		$("#fname").focus();
		$("#fname").on("blur",function() {
			if(i==0){
				obj.html(text);
				/* window.location="/yunzh/a/account/tAccount/list"; */
			}		
		})
		uval="";
		upd=obj;
		uval=text;
	}	
}

document.onkeydown = function(e) {
	var e = e || window.event;
	if(e.keyCode == "13") {		
		var timer = setTimeout(function() {
			var nTop = $(window).scrollTop();
			var nTop1=$('.fht-tbody').scrollTop();
			var id=$("#fid").val();
	    	var accountName=$("#fname").val();
	    	var addid=$("#addid").val();
	    	var accuntId=$("#accuntIdfor").val();
	    	var addname=$("#addname").val();
	    	if(id!=undefined){
	    		accountName=accountName.trim();
	    		if(accountName.length>64){
	    			messagePop("科目名称太长,只能输入64个!");
	    			i=0;
	    			return;
	    		}
	    		
	    		if(accountName=="" || accountName=="undefined"){
	    			messagePop("科目名称不可为空!");
	    			i=0;
	    			return;
	    		}
	    		/* window.location="${ctx}/account/tAccount/save?id="+id+"&accountName="+encodeURI(encodeURI(accountName))+"&nTop="+nTop+"&nTop1="+nTop1; */
	    		$.ajax({  
	       	        type : "GET",  
	       	        url : "${ctx}/account/tAccount/update",  
	       	        data :"id="+id+"&accountName="+encodeURI(encodeURI(accountName)),  
	       	        dataType: "json",  
	       	        success : function(msg) { 
	       	        	if(msg=="1"){
	       	        		messagePop("修改成功!");
	       	        		upd.html(accountName);
	       	        	}else if(msg=="1"){
	       	        		messagePop("科目名称不可为空!");
	       	        	}
	       	        	i=0;
	       	        },error : function() {
	       	        	messagePop("修改失败");
	       	        	upd.html(uval);
	       	        	i=0;
	       	        } 
	       	    });
	    	}else if(addname!=undefined){
	    		addname=addname.trim();
	    		if(addname.length>64){
	    			messagePop("科目名称太长,只能输入64个!");
	    			return;
	    		}
	    		window.location="${ctx}/account/tAccount/save?id="+addid+"&accountName="+encodeURI(encodeURI(addname))+"&faccuntId="+accuntId+"&nTop="+nTop+"&nTop1="+nTop1;	
	    	}   	
		},0);		 	
   	 	i=1;
	}
}
function ddsjxx(accuntId,id,detail,accountName){	
	var arg = arguments;
	did="";
	sid="";
	if($("#remove")!="undefined"){
		$("#remove").remove();
	}
	if(!arg.callee.open) {
		arg.callee.open = true;
		if(detail==1){
			$.ajax({  
		        type : "GET",  
		        url : "${ctx}/balance/tBalance/parentdetail",  
		        data : "id="+id,  
		        dataType: "text",  
		        success : function(msg) {	
		        	if(msg>0){
		        		 $(".deleteSubject").show();
		        		$(".mask").show();
		        		$(".confirm").text("您正在为科目 '' "+accountName+" '' 增加第一个下级科目， 系统将把该科目的数据全部转移到新增的下级科目中， 该流程不可逆，您是否要继续？？"); 
		        		sid=id;
		        		arg.callee.open = false;		        		
		        	}else{
		        		$.ajax({  
	 	        	        type : "GET",  
	 	        	        url : "${ctx}/account/tAccount/parentInfo",  
	 	        	        data : "id="+id,  
	 	        	        dataType: "json",  
	 	        	        success : function(msg) { 
	 	        	        	if(msg!=null){
	 	        	        		var fdc="借";        		
	 	        	        		if(msg.dc=='-1'){
	 	        	        			fdc="贷";
	 	        	        		}       
	 	        	        		var levelem=msg.level*1+1;        	
	 	        	        		if($("tr[id^=tr"+accuntId+"]:last").val()!=undefined){
	 	          	        			$("tr[id^=tr"+accuntId+"]:last").after("<tr class='addNew'  id='remove'><td Style='text-indent:"+levelem+"em'><input type='hidden' id='addid' value='"+msg.id+"'/><input type='hidden' id='accuntIdfor' value='"+msg.accuntIdfor+"'/>"+msg.accuntIdfor+"</td><td><input type='text' id='addname' placeholder='按回车键可继续添加同级科目'></td><td Style='text-align:center'><input type='hidden' id='fdc' value='"+msg.dc+"'/>"+fdc+"</td></tr>");
	 	          	        		}       		
	 	        	        		$("#addname").focus();
	 	        	        		if(i==0){
	 	        	        			$("tr[id!='remove']").click(function(){
		 	   		        				$("#remove").remove();
		 	   		        				arg.callee.open = false;
		 	   		        			})
		 	   		        		}
	 	        	        	}
	 	        	        },error : function() {
	 	        	        	arg.callee.open = false;
	 	        	        	messagePop("失败");
	 	        	        } 
	 	        	    }); 
		        	}
		        },error : function() {
		        	arg.callee.open = false;
		        	messagePop("失败");
		        } 
		    }); 
		}else{
			$.ajax({  
		        type : "GET",  
		        url : "${ctx}/account/tAccount/parentInfo",  
		        data : "id="+id,  
		        dataType: "json",  
		        success : function(msg) { 
		        	if(msg!=null){
		        		var fdc="借";        		
		        		if(msg.dc=='-1'){
		        			fdc="贷";
		        		}       
		        		var levelem=msg.level*1+1;        	
		        		if($("tr[id^=tr"+accuntId+"]:last").val()!=undefined){
	   	        			$("tr[id^=tr"+accuntId+"]:last").after("<tr class='addNew'  id='remove'><td Style='text-indent:"+levelem+"em'><input type='hidden' id='addid' value='"+msg.id+"'/><input type='hidden' id='accuntIdfor' value='"+msg.accuntIdfor+"'/>"+msg.accuntIdfor+"</td><td><input type='text' id='addname' placeholder='按回车键可继续添加同级科目'></td><td Style='text-align:center'><input type='hidden' id='fdc' value='"+msg.dc+"'/>"+fdc+"</td></tr>");
	   	        		}        		
		        		$("#addname").focus();
		        		if(i==0){
		        			$("tr[id!='remove']").click(function(){
		        				$("#remove").remove();
		        				arg.callee.open = false;
		        			})
		        		}
		        	}
		        },error : function() {
		        	arg.callee.open = false;
		        	messagePop("失败");
		        } 
		    }); 
		}		
	}	
}

if(''!='${getparent.id}'){
	var fdc="借";
	if('${getparent.dc}'=='-1'){
		fdc="贷";
	} 
	$("tr[id^=tr${getparent.accuntId}]:last").after("<tr class='addNew' id='remove'><td Style='text-indent:${getparent.level+1}em'><input type='hidden' id='addid' value='${getparent.id}'/><input type='hidden' id='accuntIdfor' value='${getparent.accuntIdfor}'/>${getparent.accuntIdfor}</td><td><input type='text' id='addname' placeholder='按回车键可继续添加同级科目'></td><td Style='text-align:center'><input type='hidden' id='fdc' value='${getparent.dc}'/>"+fdc+"</td></tr>");
	$("#addname").focus();
	
	if(i==0){
		$("tr[id!='remove']").click(function(){
			$("#remove").remove();
		})
	}
	
} 
	
function daoru(){
	var cid=$(".selector").val();
	if(cid!="" && cid!=null){
		window.location="/yunzh/a/account/tAccount/leadAccount?cid="+cid;
	}else{
		messagePop("亲！对不起目前没有可导入的数据");
	}
	
}

$(function() {	
	var timer = setTimeout(function() {
		//var nTop = $(window).scrollTop();
		$(window).scrollTop(${nTop});
		$(".fht-tbody").scrollTop(${nTop1});
		
		if('${message}'!=''){
			messagePop('${message}');
		}
		if('${kemu}'!=''){
			messagePop('${kemu}');
		}
	},0)   
})
var messagePop = function(str){
	$(".message-pop").fadeIn();
	$(".message-pop").children("span").html(str);
	setTimeout(function(){
		$(".message-pop").fadeOut();
	},2000)
}
</script>
<!--页面脚本区E-->
</body>
</html>