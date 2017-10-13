<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>

<head>
<meta charset="utf-8" />
<title>税种设置-在线会计-芸豆会计</title>
<meta name="keywords" content="关键字,关键字,关键字" />
<meta name="description" content="描述。……" />
<meta name="Author" content="author_bj designer_bj">
<script src="${ctxStatic}/taxbase/jquery/jquery-1.11.2.js" type="text/javascript"></script>
<script src="${ctxStatic}/taxbase/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/handlebar/handlebars-1.0.0.beta.6.js" type="text/javascript"></script>
<link href="${ctxStatic}/taxbase/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/taxbase/css/master_1.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="${ctxStatic}/taxbase/css/taxes_setting.css">
<link href="${pageContext.request.contextPath}/static/css/autoAmortization.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/accountAndbalance/css/dialog.css" rel="stylesheet" type="text/css">
<style type="text/css">
.ul-select-amortize{position: absolute;background: #fff;z-index: 11;width: 204px;height: 200px;overflow-y:auto;}
.ul-select-amortize li{width: 100%;height: 28px;line-height: 28px;text-align: left;float: left;text-indent: 2px;cursor: pointer;}
.ul-select-amortize li:hover{background: #ddd}
ol,ul{list-style:none;}
/* reset ������ʽ */
body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,textarea,p,blockquote,th,td,hr,button,article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section {margin:0;padding:0;}
ol,ul{list-style:none;}
/*
.message-pop{width: 250px;height: 100px;opacity: 0;padding-top: 20px;position: fixed;bottom: -130px;right: -300px;border: 1px solid #aaa;font-family: "Microsoft Yahei";background: #fff;z-index:99;}
.message-pop i.i-mess{width: 18px;height: 18px;cursor: pointer;background: url(${ctxStatic}/images/close.png) top center no-repeat;position: absolute;top: 0;right: 2px;}
.message-pop h4{text-align: center;}
.message-pop p{padding-top: 30px;text-align: center;}
*/

/**20160118**/
.account-wrapper{padding:10px 13px;position:absolute;width:100%;box-sizing:border-box;}
.account-wrapper .tb *{font-size:12px;}
.account-wrapper .tb {overflow-y:scroll;}
.items{margin-bottom:15px;}
.ul-select-amortize li{height:auto;line-height:18px;padding:5px 0;}
.message-pop{display:none;position:fixed;top:40px;width:100%;text-align:center;z-index:1000;}
.message-pop span{background:#4f9de8;padding:5px 75px;color:#fff;}

.unpaied {text-align:right;}
.unpaied button {margin-right:8px;margin-bottom:8px;}

.t_pane_1 .tit_d3 {padding:0;}
.mask{z-index:1;}
.layerS {width:auto;height:auto;top:0;left:50%;margin-top:100px;margin-left:-466px;z-index:999;}
.formula_01 {width:900px;}
.item_unpaied {margin:15px;}
.tit_d3 button {display:none;}
.ul-select-amortize {z-index:1000;}
</style>

</head>
<body style="overflow:hidden;">

<div class="message-pop">
<span>
保存成功！
</span>
</div>
<div class="account-wrapper">
<div class="tit_channel_1">营业税金及附加设置</div>
<div class="unpaied">
	<button class="btn btn-default2 unpaied">未交增值税</button>
</div>
<div class="tb">
	<div class="main-con">
		<div class="body-con">			
			<div class="t_pane_1">
				<div class="clearfix"></div>
				<div class="hr10"></div>
			</div>
			<div class="t_pane_a1 border_n1">
				<div class="hr10"></div>
				<div class="hr10"></div>
				<c:forEach var="account" items="${account }" varStatus="statu">
				<!-- <form action="" method=""> -->
					<!-- 隐藏值 -->
					<c:if test="${statu.index!=0 }">
					<input id="infotext" type="hidden"/>
					<div class="border_n1 items ratestate">
						<div class="border_n1">
							<span class="mar-lft10">${account.accountName }</span>
							
							<span class="right mar-rig10" >
								<input class="fcheckbox use" type="checkbox" <c:if test="${account.israteenable==1 }"> checked</c:if>>
								<span>启用</span>
							</span>							
							<div class="right">
								<span class="mar-rig10">税率</span>
								<c:if test="${account.rate==0.00}">
									<input class="rate" type="text" value="0" onblur="moveOut($(this))">
								</c:if>
								<c:if test="${account.rate!=0.00}">
									<input class="rate" type="text" value="${account.rate }" onblur="moveOut($(this))">
								</c:if>
								
								<span class="mar-rig10">%</span>
							</div>
							
						</div>
						<div class="formula">
							<div class="hr10"></div>
							<div class="main">
								<span class="mar-lft10 mar-rig10">交税基数公式</span>
								<c:forEach var="taxbase" items="${taxbase }">
								<c:if test="${taxbase.taxcategory==account.accuntId  }">									
									
									<div class="add">
										<select class='option mar-rig10 optionselect'>
											<option selected value='+' <c:if test="${taxbase.op=='+'  }">selected="selected"</c:if>>+</option>
											<option value='-' <c:if test="${taxbase.op=='-'  }">selected="selected"</c:if>>-</option>									
										</select>
										<input class="cardinal mar-rig10 ipt-subjex" type="text" value="${taxbase.accuntId }&nbsp;${taxbase.accountName }">									
									<input type="hidden" class="faccountID" value="${taxbase.accuntId }">
									</div>
								</c:if>
								</c:forEach>
								<a class="add" href="javascript:;">添加基数</a>
								<a class="delete" href="javascript:;">删除基数</a>
								<div class="t_pane_1">
									<div class="tit_d3">
										<input class="parentaccountid" type="hidden" value="${account.accuntId}"/>
										<input class="fid" type="hidden" value="${account.id}"/>
										<button class="btn btn-default2 save_mes">保存</button>
									</div>
								</div>
							</div>
							<div class="hr10"></div>
						</div>
					</div>
				</c:if>
				<!-- </form> -->
				</c:forEach>
				<div class="clearfix"></div>
			</div>
			<div class="hr10"></div>
		</div>
		<div class="clearfix"></div>
	</div>
	<div class="clearfix"></div>
	<!-- 科目-->
<ul class="ul-select-amortize" style="display:none">
				
</ul>
<div class="mask"></div>
<div class="layer layerS newSubject" style="display:none;">
			<h5 class="h6-kr">未交增值税<i class="i-close"></i></h5>
			<div class="border_n1 items ratestate item_unpaied">
						<div class="border_n1">
							<span class="mar-lft10">${account[0].accountName }</span>
							
							<span class="right mar-rig10" Style="display:none">
								<input class="fcheckbox use" type="checkbox" <c:if test="${account[0].israteenable==1 }"> checked</c:if>>
								<span>启用</span>
							</span>							
							<div class="right">
								<span class="mar-rig10">税率</span>
								<input class="rate" type="text" value="${account[0].rate }" onblur="moveOut($(this))">
								<span class="mar-rig10">%</span>
							</div>
							
						</div>
						<div class="formula formula_01">
							<div class="hr10"></div>
							<div class="main">
								<span class="mar-lft10 mar-rig10">交税基数公式</span>
								<c:forEach var="taxbase" items="${taxbase }">
								<c:if test="${taxbase.taxcategory==account[0].accuntId  }">
									<div class="add">
										<select class='option mar-rig10 optionselect'>
											<option selected value='+' <c:if test="${taxbase.op=='+'  }">selected="selected"</c:if>>+</option>
											<option value='-' <c:if test="${taxbase.op=='-'  }">selected="selected"</c:if>>-</option>								
										</select>
										<input class="cardinal mar-rig10 ipt-subjex" type="text" value="${taxbase.accuntId }&nbsp;${taxbase.accountName }">									
										<input type="hidden" class="faccountID" value="${taxbase.accuntId }">
									</div>
									
								</c:if>
								</c:forEach>
								<a class="add" href="javascript:;">添加基数</a>
								<a class="delete" href="javascript:;">删除基数</a>
								<div class="t_pane_1">
									<div class="tit_d3">
										<input class="parentaccountid" type="hidden" value="${account[0].accuntId}"/>
										<input class="fid" type="hidden" value="${account[0].id}"/>
										<button class="btn btn-default2 save_mes">保存</button>
									</div>
								</div>
							</div>
							<div class="hr10"></div>
						</div>
					</div>
		<!-- 	<div class="button buttonW clearfix">
				<button class="cancel">取消</button>
				<button class="fsave">保存</button>
			</div> -->
		</div>
</div>
</div>
<script type="text/javascript">
$(".unpaied").on("click",function() {
	$(".layerS,.mask").show();
})
$(".i-close").on("click",function() {
	$(".layerS,.mask").hide();
})

$(".tb").height(function(){
	return parseInt($(window).height()-60);	
})

function moveOut(obj){	
	var objVal =obj.val();
	var reg = /^(([0-9]|([+-]?[0-9][0-9]{0,9}))((\.[0-9]*)?))$/;
	if(!reg.test(objVal)){
		obj.prop("value","0");
	}else{
		if(objVal!="0"){
			var num = new Number(objVal);
			obj.prop("value",num.toFixed(2));
		}		
	}	
}
//科目信息
var accountArr;
$(function() {	
	$(".layerS").css("margin-top",($(window).height() - $(".layerS").height())/2);
	
	//加载科目信息
	$.ajax({
	     type: 'POST',
	     url: '${ctx}/account/tAccount/getAccountByCodeOrName',
	     cache:false,  
	     async: false,
	     dataType:'json',
	     data: {
	    	 'param' :''
	     } ,
	     success: function(data){
	    	 accountArr = data;
	     }
	}); 
	
	var clickTd;
	var addHtml = "<div class='add'>\
	<select class='option mar-rig10 optionselect'>\
		<option selected value='+'>+</option>\
		<option value='-'>-</option>\
	</select>\
	<input class='cardinal mar-rig10 ipt-subjex' type='text' value=''>\
	<input type='hidden' class='faccountID'></div>";
	$("a.add").on("click",function() {
		var parent = $(this).parent();
		$(addHtml).insertBefore($(this));
		if($(this).parents(".newSubject").length == 1) {
			if(parent.find("div.add").length > 2) {
				$(this).hide();
			}
		}else {
			if(parent.find("div.add").length > 4) {
				$(this).hide();
			}
		}
		if(parent.find("div.add").length == 1) {
			parent.find("select:eq(0)").hide();
		}
		parent.find(".delete").show();
		parent.find("button").show();
	})
	$(".layerS .main").each(function() {
		if($("div.add",this).length > 2) {
			$("a.add",this).hide();
		}
	})
	$(".main").each(function() {
		$("select:eq(0)",this).hide();
		if($("div.add",this).length > 0) {
			$(".delete",this).show()
		}
		if($("div.add",this).length > 4) {
			$("a.add",this).hide();
		}
	})	
	$(".delete").on("click",function() {
		var parent = $(this).parent();
		parent.find("div.add:last").remove();
		if(parent.find("div.add").length < 5) {
			parent.find("a.add").show();
		}
		if(parent.find("div.add").length == 0) {
			$(this).hide();
		}
		parent.find("button").show();
	})
	$("select.option").on("change",function() {
		$(this).parents(".main").find("button").show();
	})
	
	$(".cardinal,.rate").on("focus",function(e) {
		var val = $(this).val();
		$(this).on("keyup",function() {
			if($(this).val() != val) {
				$(this).parents(".ratestate").find("button").show();
			}
		})
	})
	$(".use").on("change",function() {
		$(this).parents(".ratestate").find("button").show();
	})
	//科目-自动摊销-选择科目
	
	$("body").on("click",".ipt-subjex",function(event){	
		clickTd= $(this);
		event.stopPropagation();
		$(this).find("input").focus();
		$(this).addClass("cellOn-amortize");
		var clickTop = $(this).offset().top;
		var clickLeft = $(this).offset().left;
		var addTop = clickTop+34;	
		var addLeft = clickLeft;
		var addcss = {
					top: addTop+"px",
					left: addLeft+"px"
					};
		$(".ul-select-amortize").css(addcss);		
		/* $.ajax({
	 	     type: 'POST',
	 	     url: '${ctx}/account/tAccount/getAccountByCodeOrName',
	 	     cache:false,  
	 	     async: false,
	 	     dataType:'json',
	 	     data: {
	 	    	 'param' :''
	 	     } ,
	 	     success: function(data){
	 	    	 
	 	    	 var result = '';	 	    	
	 	    	 var source = $("#accountTemplate").html();	 	    	
	 	    	 var template = Handlebars.compile(source);	 	    	
	 	    	 result= template(data);	 	    	
	 	    	$(".ul-select-amortize").html(result);
	 	     }
		});  */
		var result = '';
    	var source = $("#accountTemplate").html();
    	var template = Handlebars.compile(source);
    	result = template(accountArr);
    	$(".ul-select-amortize").html(result); 
		$(".ul-select-amortize").show();	
	})
	
	//科目-自动摊销-选择科目-科目输入值变化
	var changeAjaxFlagAmortize = false;
	$("body").on("input propertychange",".ipt-subjex",function(){
		if(changeAjaxFlagAmortize){
			return;
		}		
		changeAjaxFlagAmortize = true;
		/* var param = $(this).val();		
		$.ajax({
	 	     type: 'POST',
	 	     url: '${ctx}/account/tAccount/getAccountByCodeOrName',
	 	     cache:false,  
	 	     async: false,
	 	     dataType:'json',
	 	     data: {
	 	    	 'param' :param
	 	     } ,
	 	     success: function(data){
	 	    	 var result = '';
	 	    	 var source = $("#accountTemplate").html();
	 	    	 var template = Handlebars.compile(source);
	 	    	 result= template(data);	
	 	    	$(".ul-select-amortize").html(result);
	 	    	changeAjaxFlagAmortize = false;
	 	     }
		}); */
		var param = $(this).val().trim();
		
		var filterAccountArr = new Array();
		for(var i in accountArr){
			if(accountArr[i].accountName.indexOf(param) == 0 || accountArr[i].accuntId.indexOf(param) == 0 || accountArr[i].initName.indexOf(param) == 0 || accountArr[i].initNameParent.indexOf(param)== 0){
				filterAccountArr.push(accountArr[i]);
			}
		}
		
		var result = '';
	    var source = $("#accountTemplate").html();
	    var template = Handlebars.compile(source);
	    result= template(filterAccountArr);
	    $(".ul-select-amortize").html(result);
	    changeAjaxFlagAmortize = false;
	})
	
	//科目-自动摊销-选择科目-选中
	$("body").on("click",".ul-select-amortize li",function(){
		$(clickTd).parents(".formula").find("button").show();
		var _liVal = $(this).text();
		var accountid = $(this).data("id");
		$(clickTd).prop("value",_liVal);
		$(clickTd).next().prop("value",accountid);
	})
	//在选择科目时 点击其他地方 则科目下拉隐藏
	$("body").click(function(){
		$(".ul-select").hide();
		$(".cellOn").find("input").hide();
		$(".ul-select-amortize").hide();
	})
	var baocun="";
	$(".save_mes").on("click",function() {
		baocun=$(this);
		var rate=$(this).parents(".ratestate").find(".rate");
		rate=rate[0].value;
		if(rate==null || rate==""){
			messagePop("税率不可以为空！");
			return;
		}
		$(this).parents(".main").find("div.add").each(function() {
			if($(".faccountID",this).val() == "") {
				$(this).remove();
			}
		})
		$(".main").each(function() {
			if($("div.add",this).length == 0) {
				$(".delete",this).hide();
				$("a.add",this).show();
			}else if($("div.add",this).length == 5) {
				$(".delete",this).show();
				$("a.add",this).hide();
			}else {
				$(".delete",this).show();
				$("a.add",this).show();
			}
		})
		$("select.option").on("change",function() {
			$(this).parents(".main").find("button").show();
		})
		
		var list=$(this).parents(".formula").find(".faccountID");
		var oplist=$(this).parents(".formula").find(".optionselect");
		
		var fcheckbox=$(this).parents(".ratestate").find(".fcheckbox");
		var parentaccountid=$(this).parent().find(".parentaccountid")[0].value;
		var fid=$(this).parent().find(".fid")[0].value;
		var state="1";
		
		
		if(fcheckbox[0].checked==false){
			state="0";
		}
		var result="";
		for(var i=0;i<list.length;i++){
			var accountid=list[i].value;
			var op=oplist[i].value;
			if(accountid!=null && accountid!="" && op!=null && op!=""){
				result+="{'accountid' : '" +accountid+"','op':'"+op+"'},"; 
			}
			
		}
		result="["+result.substring(0,result.length-1)+"]";
		$.ajax({
	 	     type: 'POST',
	 	     url: '${ctx}/taxbase/tTaxbase/addtaxbase',
	 	   	 cache:false,  
	 	     async: false,
	 	     dataType:'json',
	 	     data: {
	 	    	 'id':fid,
	 	    	 'rate':rate,
	 	    	 'state':state,
	 	    	 'result':result,
	 	    	 'parentaccountid':parentaccountid
	 	     }, 
	 	     success: function(data){
	 	    	 if(data!=null && data!=""){
	 	    		$(baocun).hide();
	 	    		messagePop("保存成功！");
	 	    		$(".layerS,.mask").hide();
	 	    	 }
	 	    	
	 	     },error : function() {	 
	 	    	messagePop("操作失败！");		        	
		        } 
		});
	})
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
<script id="accountTemplate" type="text/x-handlebars-template">	
{{#each this}}
	<li data-id="{{accuntId}}">{{accuntId}}&nbsp;{{parentName}}{{accountName}}</li>
{{/each}}
</script>
</body>
</html>