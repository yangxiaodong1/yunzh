<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">

<link href="${ctxStatic}/accountAndbalance/css/dialog.css" rel="stylesheet" type="text/css">

<title>首页</title>


<style>
	.box {
		height: 40px;
		background: #eff1f3;
		margin-bottom: 0;
		border-bottom: 1px #9bb2b6 solid;
	}
	iframe {
		width: 100%;
	}
	.label_min {
		padding: 0 0 0 10px;
		background: url(${ctxStatic}/images/jerichotab.gif) left center no-repeat;
		display: inline-block;
		height: 40px;
		line-height: 40px;
		vertical-align: middle;
		cursor: pointer;
		border-radius: 10px 10px 0 0;
		border: 1px #9bb2b6 solid;
		margin-right: 4px;
	}
	.label_min a {
		display: inline-block;
		width: 11px;
		height: 11px;
		background: url(${ctxStatic}/images/jerichoclose.gif) 0 0 no-repeat;
		margin: 0 10px 0 15px;
	}
	.label_min a:hover {
		background-position: 0 -22px;
	}
	.index {
		padding: 0 10px;
		margin: 0 0 0 4px;
	}
	.now {
		background: #f7f7f9;
		border-bottom: 1px solid #f7f7f9;
	}
	.now a {
		background-position: 0 -11px;
	}
	.downlist {
		height: 100%;
		width: 50px;
		background: #fff;
	}

	/*chengming提示信息弹窗*/
	.deleteSubject {display: none;height: 200px;z-index:101;}
	.confirm {font-size: 14px;line-height: 30px;margin: 30px 0;text-align:center;}
	.sure {background: #fba22c;float: left;}
	.deleteSubject .h6-kr {margin:0;padding:0;}
	.mask {z-index:100;}
	.warning_dialog {height: auto;}
	.warning_mes {margin: 20px 20px 0;text-align: left;overflow: hidden;line-height: 24px;}
	.warning_mes .title {color:red;font-size: 14px;}
	.warning_mes .note {color: red;display: none;}
	.warning_mes img {float: left;margin-left: 20px;}
	.warning_mes .warning {width: 340px	;float: right;margin-bottom: 20px;}
	.warning_mes ul {padding-left: 16px;}
	.warning_mes ul li {list-style-type: disc;}
	.warning_dialog .clear_both {margin-bottom: 20px;}
	.help-inline {color: red;}
</style>






<script src="${ctxStatic}/jquery/jquery-1.11.2.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />

<link href="${ctxStatic}/css/sidebar.css" type="text/css" rel="stylesheet" />


<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery.form.js"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctxStatic}/jquery/jquery.ztree.core-3.5.js"></script>
<link rel="stylesheet" href="${ctxStatic}/zTreeStyle/zTreeStyle.css" type="text/css">

<link href="${pageContext.request.contextPath}/static/css/master_2.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/bootstrap/css/page.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_1.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog2.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/bootstrap/js/bootstrap-hover-dropdown.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/jquery/jquery-jtemplates.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/jquery-validation/1.11.1/jquery.validate.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/jquery-validation/1.11.1/localization/messages_zh.js" type="text/javascript"></script>
<style>
	.top_action .drop_user .dropdown span#dropdownMenu2 {
		color:#000;
	}
	.top_action .drop_user {
		padding:0;
	}
.dropsub{position:relative;}
.dropsub .glyphicon{color:#4f9de8;position:absolute;right:14px;top:43px;}
.dropsub.curr .glyphicon{color:#fff;}	
</style>
</head>

<body>

<form id="frominfo" type="hidden" action="${pageContext.request.contextPath}/a/sys/user/voucherIndexx" method="post">
		<input type="hidden" id="hrefid" name="hrefid"/>
</form>

<div class="message-pop"><span>保存成功！</span></div>
<div class="main-row">
	
	<div class="logo_c left"></div>
	<div class="top_action right">
		
		<div class="hr10"></div>
		<div class="link_home"> <a href="${pageContext.request.contextPath}/a/yunzhmainsypt" class="btn btn-default">工作台</a> </div>
		<div class="drop_company">
			<div class="btn-group">
				<div class="tit_c2 left dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-delay="100" data-close-others="false"><em id="customernames">${customername}</em>
					<input type="hidden"  id="${customerId}" class="inId"/>
					<span class="caret"></span>
				</div>				
				<ul class="dropdown-menu pull-right" >
					 <c:forEach items="${listcustomer}" var="lc">
					 <li role="presentation"><a role="menuitem" tabindex="-1" href="${pageContext.request.contextPath}/a/sys/user/voucherIndexx?hrefid=${lc.id }">${lc.customerName}</a>  
					<%--<li role="presentation"><a role="menuitem" tabindex="-1" class="hrefid" id="${lc.id }">${lc.customerName}</a> --%>
					<input type="hidden" id="${lc.id}"/>
					</li>					
					</c:forEach>
				</ul>
			</div>
		</div>	
		<div class="link-top left"><a href="javascript:void(0);" class="btn btn_edit dialog_add">账簿编辑</a><a href="javascript:void(0);" class="followUplist btn btn_follow">跟进记录</a>|</div>
		<div class="drop_user left">
			<div class="face" id='facename'>
			<c:if test="${currentUser.photo!=null&&currentUser.photo!=''}">
				 <img src="${ctx}/sys/user/imageGet?fileName=${currentUser.photo}" width="88" height="88" class="radius_imgs" />
				</c:if>
				<c:if test="${currentUser.photo==null||currentUser.photo==''}">
				<img src="${ctxStatic}/images/img-1.png" width="33" height="33" /> 
				</c:if>
			</div>
			
			 <div class="dropdown"> <span id="dropdownMenu2" data-toggle="dropdown" data-hover="dropdown" data-delay="100" data-close-others="false">
			 <c:choose >
   							 <c:when test="${fn:length(currentUser.name)>3}">
							       ${fn:substring(currentUser.name, 0, 3)}...
							    </c:when>
								 <c:otherwise>
								 ${currentUser.name}
							    </c:otherwise>
							</c:choose>
			 </span>
				<ul class="dropdown-menu pull-right">
					<li role="presentation"><a role="menuitem" tabindex="-1" href="${pageContext.request.contextPath}/a/logout">退出</a></li>
				</ul>
			</div>
			
		</div>
		<div class="hr12"></div>
	</div>
	<div class="clearfix"></div>
	<div class="line_t1"></div>
	<div class="main-frame">
		<div class="main-side">
			<div class="sidebar">
				<div class="hr40"></div>
				<ul class="leftnav remove-style">
				
					<!-- <li class="a2"><a href="" famesrc="${pageContext.request.contextPath}/a/sys/user/voucherIndex">会计首页</a></li> -->
                    <li class="a1 onlythis this"><a href="" rel="link_20" famesrc="${pageContext.request.contextPath}/a/voucher/tVoucher/enterVoucher">新增凭证</a></li>
					<%-- <li class="a2 onlythis"><a href="" rel="link_02" famesrc="${pageContext.request.contextPath}/a/billinfo/tBillInfo/billInfolist">票　据</a></li>
					 --%>
					<li class="a2 dropsub"><a rel="link_04">票     据 <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></a></li>
					<li class="a3 onlythis"><a href="" rel="link_03" famesrc="${pageContext.request.contextPath}/a/voucherexp/tVoucherExp/periodShow">查凭证</a></li>
					
					<li class="a4 dropsub"><a rel="link_04">账　簿 <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></a></li>
					<li class="a5 dropsub"><a rel="link_05">报　表 <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></a></li>
					<li class="a6 dropsub"><a rel="link_06">设　置 <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></a></li>
					
				</ul>
				<div class="side-subcon">
					<div class="submenu submenu2">
						<ul>
							<li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span><a rel="link_07" href="#" famesrc="${pageContext.request.contextPath}/a/billinfo/tDealBillInfo/uploadBillInfo">上传的票据</a></li>
							<li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span><a rel="link_08" href="#" famesrc="${pageContext.request.contextPath}/a/billinfo/tDealBillInfo/dealBilliInfo">处理的票据</a></li>
						</ul>
					</div>
					<div class="submenu submenu4">
						<ul>
							<li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span><a rel="link_07" href="#" famesrc="${pageContext.request.contextPath}/a/books/Subsidiary/listSubsidiaryledge">明细账</a></li>
							<li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span><a rel="link_08" href="#" famesrc="${pageContext.request.contextPath}/a/books/generalLedger/listGeneralLedger">总账</a></li>
							<li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span><a  rel="link_09" href="#" famesrc="${pageContext.request.contextPath}/a/books/subjectBalance/listSubjectBalance">科目余额表</a></li>
						    <!-- <li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span><a href="#" famesrc="">凭证汇总表</a></li> -->
						</ul>
					</div>
					<div class="submenu submenu5">
						<ul>
							<li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span><a rel="link_10" href="#" famesrc="${pageContext.request.contextPath}/a/rpt/balance/listBalance">资产负债表</a></li>
							<li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span><a rel="link_11" href="#" famesrc="${pageContext.request.contextPath}/a/rpt/profit/listProfit">利润表</a></li>
							<li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span><a rel="link_12" href="#" famesrc="${pageContext.request.contextPath}/a/rpt/cashFlow/listCashFlow">现金流量表</a></li>
						</ul>
					</div>
					<div class="submenu submenu6">
						<ul>
							<li style="color:#333;"><b>基础设置</b></li>
							<li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span><a rel="link_13" href="#" famesrc="${pageContext.request.contextPath}/a/account/tAccount/balan">财务初始余额</a></li>
							<!-- <li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span><a href="#" famesrc="${pageContext.request.contextPath}/a">凭证字</a></li> -->
							<li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span><a rel="link_14" href="#" famesrc="${pageContext.request.contextPath}/a/account/tAccount">科目</a></li>
							<li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span><a rel="link_23" href="#" famesrc="${pageContext.request.contextPath}/a/taxbase/tTaxbase/list">税种设置</a></li>	
							
												
							
							
							<li style="color:#333;"><b>高级设置</b></li>
							
							<li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span><a rel="link_17" href="#" famesrc="${pageContext.request.contextPath}/a/backupandrecover/tbackupRecover/list">备份与恢复</a></li>
							<li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span><a rel="link_18" id="againinputbasedata" href="#">重新初始化</a></li>
							<li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span><a rel="link_19" href="#" famesrc="${pageContext.request.contextPath}/a/vouchertemplate/tVoucherTemplate/modelsetPage">凭证模版</a></li>
							<li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span><a rel="link_21" href="#" famesrc="${pageContext.request.contextPath}/a/amortize/tAmortize/list">自动摊销</a></li>
							<li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span><a rel="link_22" href="#" famesrc="${pageContext.request.contextPath}/a/sys/log/userLog">操作日志</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="main-con left">
			<div class="body-con" id="pane-subcon">
			<!--    <div class="box">
					<span class="label_min index now" id="link_01" >首&nbsp;&nbsp;页</span>
				</div>--> 
				<iframe name="frame" id="id_iframe" frameborder=0 scrolling=auto src="${pageContext.request.contextPath}/a/sys/user/voucherIndex"></iframe>
			</div>
			<div class="clearfix"></div>
		</div>
		<div class="clearfix"></div>
	</div>

	<!-- 修改 -->
	<div class="mask"></div>
		<!-- 修改 -->
		<div class="layer layerS deleteSubject warning_dialog">
			<h5 class="h6-kr">系统提示<i class="i-close"></i></h5>
			<div class="warning_mes">
				<img src="${ctxStatic}/images/warning.gif" alt="">
				<div class="warning">
					<div class="title">重新初始化系统将会清空你录入的所有数据，请慎重！</div>
					<ul>
						<li>系统将删除您新增的所有科目</li>
						<li>系统将删除您录入的所有凭证</li>
						<li>系统将删除您录入的所有初始化数据</li>
					</ul>
					<input type="checkbox">
					<label for="">我已经清楚了解将产生的后果</label>
					<p class="note">（请先确认并勾选“我已清楚了解将产生的后果"）</p>	
				</div>
			</div>
			<div class="buttonW clearfix clear_both">
				<button class="sure">确定</button>
				<button class="fsave cancel_delete">取消</button>
			</div>
		</div>

<!-- 新增客户 -->
<%@include file="updateCustomerInfoNew.jsp"%>
<!-- 结束客户 -->
</div>
<script type="text/javascript">
$(function(){
    //iframe加载完成后执行,父窗口调用子窗口的元素
    $("#id_iframe").load(function(){
        var $frm = $(this);
        var $contents = $frm.contents();
       var title= $contents.find("title").text();
        $("title").text(title);
    });
});

</script>
<script type="text/javascript">
//子窗口调用父窗口修改动态修改图片
function updatefilename(filename){
	var filename=filename;
	var img="<img src='${ctx}/sys/user/imageGet?fileName="+filename+"' width='88' height='88' class='radius_imgs' />";
    //$("#facename").html(<img src="${ctx}/sys/user/imageGet?fileName="+filename width="88" height="88" class="radius_imgs" />);
   $("#facename").html(img);
	
}
</script>
<script type="text/javascript">
//重新初始化—提示信息—chenming
$("#againinputbasedata").bind("click",function(event){
	$(".deleteSubject").show();
	$(".mask").show();
	$(".confirm").text("你确定要删除要初始化吗？");
})
//重新初始化—叉叉—chenming
$(".i-close").bind("click",function(){
	$(".deleteSubject").hide();
	$(".mask").hide();
})
//重新初始化—取消按钮—chenming
//修改
$(".cancel_delete").bind("click",function(){
	$(this).parents(".warning_dialog").find("p.note").hide();      
	$(".deleteSubject").hide();
	$(".mask").hide();
})
$(".warning_dialog input").on("change",function() {                
	$(this).parents(".warning_dialog").find("p.note").hide();      
})                                                                 
//重新初始化—确定—向后台提交—chenming
$(".sure").on("click",function(event){	
	if($(this).parents(".warning_dialog").find("input")[0].checked == true){if($(this).parents(".warning_dialog").find("input")[0].checked == true)
		window.location.href="${pageContext.request.contextPath}/a/sys/user/againinputbasedata";
		$(".deleteSubject").hide();
		$(".mask").hide();
	}
	if($(this).parents(".warning_dialog").find("input")[0].checked == false) {
		$(this).parents(".warning_dialog").find("p.note").show();
	}
});


//鼠标离开下拉菜单隐藏20160118
$('.dropdown-toggle').dropdownHover();
$(function(){
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
		
		var id=$(".inId").attr("id");
		//alert(id);
		insertPutt(id);
		
		$( "#dialog_add" ).dialog( "open" );
		//dialog.dialog( "open" );
		event.preventDefault();
	});	
	//给输入框中赋值
	function insertPutt(id){
		//alert(id);
		$.ajax({  
			type:"POST",
			//url:"${ctxStatic}/customer/tCustomer/forms",
			url:"${pageContext.request.contextPath}/a/customer/tCustomer/forms",
			//${pageContext.request.contextPath}/a/
			//url:ctx+"/customer/tCustomer/forms",
				// contentType:"application/json; charset=utf-8",//不能用这个
				dataType : "json",
				data: "id="+id,
		       success:function(json){  
		    	  // alert(666);
		    	   $json = json;
		    	   //-------
		    	   var systemint=$json.system;
		    	   var systemintstring="";
		    	   if(systemint==1){
		    		   systemintstring="2013小企业会计准则";
		    	   }else if(systemint==2){
		    		   systemintstring="2007企业会计准则";
		    	   }
		    	   $("#systemid").val(systemintstring);
		    	   
		    	   //-------
		    	  $('#inputFormNew').formEdit($json);
		    	  $("#currentPeriod").val(json.currentperiod);
		    	   $("#initPeriod").val(json.initperiod);
		    	   date();
		       },
		      error:function(){showd("dialog_error"); }
		      //error:function(){alert(8888);},
		 });
		 };
		 
 //插件函数
		 $.fn.formEdit = function(data){
		     return this.each(function(){
		         var input, name;
		         if(data == null){this.reset(); return; }
		         for(var i = 0; i < this.length; i++){  
		             input = this.elements[i];
		             name = (input.type == "checkbox")? input.name.replace(/(.+)\[\]$/, "$1") : input.name;
		             if(data[name] == undefined) continue;
		             switch(input.type){
		                 case "checkbox":
		                     if(data[name] == ""){
		                         input.checked = false;
		                     }else{

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
		 //----------
	$( "#btnCancel,#btnCancel1,#btnCancel2" ).on('click',function( event ) {
		dialog.dialog( "close" );
		//event.preventDefault();
	});
	
	$("#creat_per,#creat_per2,#creat_per1").click(function() {
		if($("#customerName").val()!="" && $("#supervisor").val()!=""){
		$("#inputFormNew").ajaxSubmit({
            type: 'post',
            url: "${ctx}/customer/tCustomer/update" ,
            data: $("#inputFormNew").serialize(),
            success: function(data){
            	if(data=="1"){
            		messagePop("保存成功！");
                	//window.location="${pageContext.request.contextPath}/a/sys/user/voucherIndexx";
                	$( "#dialog_add" ).dialog("close");
            	}
                $( "#inputForm").resetForm();
            },
            error: function(XmlHttpRequest, textStatus, errorThrown){
                 //alert( "error");
            }                
        })
        $( "#inputFormNew").resetForm();
     	$( "#upload").resetForm();
        }else{
        	messagePop("请填写必须要输入的信息！");
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
	$("#uploadbuttonn").on("click",function(){
			if($("#appendix").val()=="" && $("#filetype").val()==""){
				messagePop("请输入必填信息");
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
	
	//开始
	$(function(){
		//-------开始
		$("#uploadbutton").on("click",function(){
		//var appendixname=$("#appendix").val();
		if($("#appendix").val()=="" || $("#filetype").val()==""){
			messagePop("请输入必填上传信息");
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
				alert("error");
			}
		});
		$( "#upload").resetForm();
		}
		
	});
		//-------结束
	})
	//结束
function messagePop(str,top){
	console.log(arguments.length)
		$(".message-pop").fadeIn();
		$(".message-pop").children("span").html(str);
		arguments.length == "2" ? $(".message-pop").css({"top":top}):$(".message-pop").css({"top":"80px"});
		setTimeout(function(){
			$(".message-pop").fadeOut();
		},2000)
}
/**当ID值是的时候可以调用***/
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
}	

//陈明加的
$(".hrefid").click(function(){
	var hrefid=this.id;
	$("#hrefid").attr("value",hrefid);
	$("#frominfo").submit();
})

</script>


<!-- 上面是客户js -->
<!-- 
<script type="text/javascript">
	var winH=$(window).height();
	var mainH = winH - 123;
	$("iframe").css({"height":mainH});
	//此方法用于被父窗口和子窗口调用生新的iframe
  function addTicket(rel,text,famesrc) {
	var label = "<span class='label_min now' id='"+rel+"'>"+text+"<a href='#'' class='remove'></a></span>";
	var add = "<iframe id='"+rel+"_con' frameborder=0 scrolling=auto src='"+famesrc+"'></iframe>";
	if($("#"+rel).length > 0) {
		$("#pane-subcon iframe").hide();
		$("#"+rel+"_con").show();
		$("#"+rel).siblings().removeClass("now").end().addClass("now");
		return;
	}
	$(".label_min").removeClass("now");
	
	$(".box").append(label);
	$("#pane-subcon").append(add);
	var frame = $("iframe");
	frame.hide();
	$("iframe").last().show();
	$("iframe").css({"height":mainH});
}
   
   
   
   
   
$(function() {
	var winH=$(window).height();
	var mainH = winH - 123;
	$("iframe").css({"height":mainH});

	$("[famesrc]").click(function(event){
		var famesrc=$(this).attr("famesrc");
		event.preventDefault();
		var text = $(this).text();
		var rel = $(this).attr("rel");
		//addTicket(rel,text,famesrc,false);
		
	})
	$(".box").on("click",function(e) {
		var e = e ||window.event;
		if(e.target.nodeName == "A") {
			var tab = $(e.target).parent().attr("id");
			if($(e.target).parent().hasClass("now")) {
				var tab_next = $(e.target).parent().next().attr("id");
				if(typeof tab_next == "undefined") {
					$(e.target).parent().prev().addClass("now");
					var tab_pre = $(e.target).parent().prev().attr("id");
					$("iframe").hide();
					$("#"+tab_pre+"_con").show();
				}else {
					$(e.target).parent().next().addClass("now");
					$("iframe").hide();
					$("#"+tab_next+"_con").show();
				}
			}
			$(e.target).parent().remove();
			$("#"+tab+"_con").remove();
		}
		if(e.target.nodeName == "SPAN") {
			var tab = $(e.target).attr("id");
			$("iframe").hide();
			$("#"+tab+"_con").show();
			$(e.target).siblings().removeClass("now").end().addClass("now");
		}
	})

	
})
</script>
  -->
  
  <script type="text/javascript">
	
	var winH=$(window).height();
	var mainH =parseInt(winH-82);
	$("iframe").css({"height":mainH});
	
	$(".leftnav li.onlythis").click(function(){
		var index=$(".leftnav li.onlythis").index(this)
		$(this).addClass("curr").siblings().removeClass("curr")	
		$(".side-subcon .submenu").hide();
	})
	$(".leftnav li.onlythis").on('mouseover',function(){
		$(".side-subcon .submenu").hide();
	})

	$(".leftnav li.dropsub").hover(function(){
		var index=$(".leftnav li.dropsub").index(this);
		$(this).addClass("curr");
		$(".side-subcon .submenu:eq("+index+")").show().siblings().hide();
		$(".side-subcon .submenu:eq("+index+")").on("mouseover",function(){
			$(".leftnav li.dropsub").eq(index).addClass("curr");
		})
	},function(){		
		var has=$(this).hasClass('curr click');
		if(has==true){
			$(this).addClass("curr").siblings().removeClass("curr")	;
		}else{
			$(this).removeClass("curr");
		}
	})
	$(".side-subcon .submenu").on('click',function(){
		ind=$(this).index();
		$(".leftnav li.dropsub").eq(ind).addClass("curr click").siblings().removeClass("curr click");
	})

	$(".side-subcon .submenu").on('mouseout',function(){
		ind=$(this).index();
		var has=$(".leftnav li.dropsub").eq(ind).hasClass('curr click');
		if(has==true){
			$(".leftnav li.dropsub").eq(ind).addClass("curr").siblings().removeClass("curr");
		}else{
			$(".leftnav li.dropsub").eq(ind).removeClass("curr");
		}
	})

	$(".main-con .body-con").hover(function(){
		$(".side-subcon .submenu").hide();
	})

	$("[famesrc]").click(function(event){
		var famesrc=$(this).attr("famesrc");
		event.preventDefault();
		$("#pane-subcon iframe").attr("src",famesrc);
		$(".side-subcon .submenu").hide();
	})
	
	$('#tableframe').height(function(){
		return $(window).height();
	})
</script>

<script>
$(function(){
	//是否删除
	$( "#dialog_del3" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});	
	$( "#upTime" ).datepicker({
		dateFormat:'yy-mm-dd',
		showOn: "button",
		buttonImage: "${pageContext.request.contextPath}/static/imgs/04.jpg",
		buttonImageOnly: true,
		buttonText: "选择日期"
	});

	$("#InsertFollowUp #upTime").on("change",function() {
		$('#InsertFollowUp').find(":checkbox").attr("checked",false); 
		 $('#InsertFollowUp').find(":checkbox").removeAttr("disabled"); 
			var byYears=$("#InsertFollowUp #upTime").val();
			if(byYears.length<7){
				return;
			}
			bymonth=byYears.substring(5, 7);
			if(bymonth>6){
				$('#InsertFollowUp #upContent1').attr("disabled",true);
			}
			if(bymonth<7||bymonth>9){
				$('#InsertFollowUp #upContent2').attr("disabled",true);
			}
			if(bymonth>5){
				$('#InsertFollowUp #upContent3').attr("disabled",true);
			}
			byYears=byYears.substring(0, 4);
			var customerIds=$("#InsertFollowUp #customerId3").val();
			$.ajax({  
				type:"GET",
					url:"${ctx}/workterrace/followUp/findUpContent" ,
				   data: {byYear:byYears,customerId:customerIds},
			       success:function(json){ 
			    	 // alert(json);
			    	  var strs= new Array(); //定义一数组 
			    	  strs=json.split(","); //字符分割 
			    	  for (i=0;i<strs.length ;i++ ) 
			    	  { 
			    		  switch (strs[i])
			    		  {
			    		  case "信息公示已完成":
			    			  $('#InsertFollowUp #upContent1').attr("disabled",true);
			    		    continue;
			    		    break;
			    		  case "残疾人保证金缴纳":
			    			  $('#InsertFollowUp #upContent2').attr("disabled",true);
			    		    continue;
			    		    break;
			    		  case "企业所得税汇算缴纳":
			    			  $('#InsertFollowUp #upContent3').attr("disabled",true);
			    			continue;
			    		 	break;
			    		  } 
			    	  } 
			       },
			       error:function(){showd("dialog_error1");}
			 });  
			
	})
	
	$(".followUplist").on("click",function() {
		
		var customername=$("#customernames").text();
		var customerId=$("#customernames").next().attr("id");
		$("#searchFormfollowUp #customerId2").val(customerId);
		$("#InsertFollowUp #customerId3").val(customerId);

		$("#InsertFollowUp #customerName2").text(customername);

		$("#searchFormfollowUp").ajaxSubmit({
		        type: 'post',
		        url: "${ctx}/workterrace/followUp/followUplist" ,
		        dataType : 'json',
		        data: $("#searchFormfollowUp").serialize(),
		        success: function(data){
		           $("#followUpList").setTemplateElement("templateFollowUpForm").processTemplate(data);
		          $("#dialog_edit2 .pagination").html(data.html);
		          $("#dialog_edit2 #pageNo").val(data.pageNo);
		          $("#dialog_edit2 #pageSize").val(data.pageSize);
		          
		          findUpContent();
		          var byYears=$("#InsertFollowUp #upTime").val();
		         
					if(byYears.length<7){
						return;
					}
					bymonth=byYears.substring(5, 7);
					
					if(bymonth>6){
						$('#InsertFollowUp #upContent1').attr("disabled",true);
					}
					if(bymonth<7||bymonth>9){
						$('#InsertFollowUp #upContent2').attr("disabled",true);
					}
					if(bymonth>5){
						$('#InsertFollowUp #upContent3').attr("disabled",true);
					}
		          $( "#dialog_edit2" ).dialog( "open" );
		  		event.preventDefault();
		        },
		        error: function(XmlHttpRequest, textStatus, errorThrown){
		        	showd("dialog_error1");
		        }                
		 });
 		/* $( "#dialog_edit2" ).dialog( "open" );  */
		/* event.preventDefault(); */
	})
	  
	//新增跟进记录
	$( "#dialog_edit2" ).dialog({
		autoOpen: false,
		width: 840,
		modal: true
	});	
	/*$( ".dialog_edit2" ).click(function( event ) {
		
	});*/
	// 确定弹窗
	$( ".dialog_sure" ).click(function( event ) {
		var arg = arguments;
		var sure = $("#dialog_sure");
		if(!arg.callee.open) {
			arg.callee.open = true;
			sure.css("display","block");
			sure.animate({
				top:"10px",
			},500,function() {
				setTimeout(function() {
					sure.css({
						top:0,
						display:"none"
					});
					arg.callee.open = false;
				},1000);
			});
		}
		event.preventDefault();
	});

	$( ".delFollowUp" ).click(function( event ) {
		var id= $("#dialog_del3 #delectids").val();
		
		$.ajax({  
			type:"POST",
			 url: "${ctx}/workterrace/followUp/delect" ,
				dataType : 'json',
			   data: "id="+id,
		       success:function(data){  
		    	   /* showd("dialog_delect1"); */
		    	   messagePop("删除成功！","140px");
		    	   $("#followUpList").setTemplateElement("templateFollowUpForm").processTemplate(data);
			          $("#dialog_edit2 .pagination").html(data.html);
			          $("#dialog_edit2 #pageNo").val(data.pageNo);
			          $("#dialog_edit2 #pageSize").val(data.pageSize);
			          
			          findUpContent();
			          
			      	$( "#dialog_del3" ).dialog( "close" );
					event.preventDefault();
		       },
		       error:function(){showd("dialog_error1");}
		 });  
	
		return;
	});
	$( ".dialog_closefollowUp" ).click(function( event ) {
		$( "#dialog_del3" ).dialog( "close" );
		event.preventDefault();
	});
	//复选变单选
	$(":checkbox").click(function(){ 
	
			$(this).parent().siblings().find(":checkbox").attr("checked",false); 
			$(this).attr("checked",true); 
			$("#remarks1").val($(this).val());
			
	}); 
	
	
})
function InsertFollowUp(){
	if(!validateInsertFollowUpForm()){
		return;
	}
	$("#InsertFollowUp").ajaxSubmit({
        type: 'post',
        url: "${ctx}/workterrace/followUp/save" ,
        dataType : 'json',
        data: $("#InsertFollowUp").serialize(),
        success: function(data){
    		/* showd("dialog_sure1"); */
    		messagePop("保存成功！","140px");
        	
    		$("#followUpList").setTemplateElement("templateFollowUpForm").processTemplate(data);
	          $("#dialog_edit2 .pagination").html(data.html);
	          $("#dialog_edit2 #pageNo").val(data.pageNo);
	          $("#dialog_edit2 #pageSize").val(data.pageSize);
	          resutf();
	          findUpContent();
	          
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
        	showd("dialog_error1");
        }   
        
	 });
	
 return false;
}
function formfollowUp(id){
	
	$.ajax({  
		type:"POST",
			url:"${ctx}/workterrace/followUp/form" ,
			dataType : 'json',
		   data: "id="+id,
	       success:function(json){ 
	    	   $json = json;
	    	   $('#InsertFollowUp').formEdit($json);
	    	   
	       },
	       error:function(){showd("dialog_error1");}
	 });  
	 
}
function delfollowUp(id){
	 $("#dialog_del3 #delectids").val(id);
	 $( "#dialog_del3" ).dialog( "open" );
		event.preventDefault();
}

function findUpContent(){
	$('#InsertFollowUp').find(":checkbox").attr("checked",false); 
	 $('#InsertFollowUp').find(":checkbox").removeAttr("disabled"); 
	var byYears=$("#InsertFollowUp #upTime").val();
	
	if(byYears.length<7){
		return;
	}
	bymonth=byYears.substring(5, 7);
	if(bymonth>6){
		$('#InsertFollowUp #upContent1').attr("disabled",true);
	}
	if(bymonth<7||bymonth>9){
		$('#InsertFollowUp #upContent2').attr("disabled",true);
	}
	if(bymonth>5){
		$('#InsertFollowUp #upContent3').attr("disabled",true);
	}
	if(byYears.length<4){
		return;
	}
	byYears=byYears.substring(0, 4);
	var customerIds=$("#InsertFollowUp #customerId3").val();
	$.ajax({  
		type:"GET",
			url:"${ctx}/workterrace/followUp/findUpContent" ,
		   data: {byYear:byYears,customerId:customerIds},
	       success:function(json){ 
	    	 // alert(json);
	    	  var strs= new Array(); //定义一数组 
	    	  strs=json.split(","); //字符分割 
	    	  
	    	  $("#InsertFollowUp .checkbox-inline .label").remove();
	    	  for (i=0;i<strs.length ;i++ ) 
	    	  { 
	    		  switch (strs[i])
	    		  {
	    		  case "信息公示已完成":
	    			  $('#InsertFollowUp #upContent1').attr("disabled",true);
	    			  if($('#InsertFollowUp #upContent1').parent().find("span").length<2){
	    			 		 $('#InsertFollowUp #upContent1').parent().append("<span class=\"label block_c1\">已完成</span>");
	    			 		$(".checkbox-inline .label").show();
	    			  }
	    		    continue;
	    		    break;
	    		  case "残疾人保证金缴纳":
	    			  $('#InsertFollowUp #upContent2').attr("disabled",true);
	    			  if($('#InsertFollowUp #upContent2').parent().find("span").length<2){
	    			 		 $('#InsertFollowUp #upContent2').parent().append("<span class=\"label block_c2\">已完成</span>");
	    			 		$(".checkbox-inline .label").show();
	    			  }
	    		    continue;
	    		    break;
	    		  case "企业所得税汇算缴纳":
	    			  $('#InsertFollowUp #upContent3').attr("disabled",true);
	    			  if($('#InsertFollowUp #upContent3').parent().find("span").length<2){
	    			 		 $('#InsertFollowUp #upContent3').parent().append("<span class=\"label block_c3\">已完成</span>");
	    			 		$(".checkbox-inline .label").show();
	    			  }
	    			continue;
	    		 	break;
	    		  } 
	    	  } 
	       },
	       error:function(){showd("dialog_error1");}
	 });  
	 
	
}
function resutf(){
	$('#InsertFollowUp #id').val("");
	$('#InsertFollowUp #phone').val("");
	 $('#InsertFollowUp #linkman').val("");
	 $('#InsertFollowUp #phone').val("");
	 $('#InsertFollowUp #remarks1').val("");
	 $('#InsertFollowUp').find(":checkbox").attr("checked",false); 
    }
function page(n,s){
	
	
	    	$("#searchFormfollowUp #pageNo").val(n);
			$("#searchFormfollowUp #pageSize").val(s);
			$("#searchFormfollowUp").ajaxSubmit({
		        type: 'post',
		        url: "$(ctx)/workterrace/followUp/followUplist" ,
		        dataType : 'json',
		        data: $("#searchFormfollowUp").serialize(),
		        success: function(data){
		           $("#followUpList").setTemplateElement("templateFollowUpForm").processTemplate(data);
		          $("#dialog_edit2 .pagination").html(data.html);
		          $("#dialog_edit2 #pageNo").val(data.pageNo);
		          $("#dialog_edit2 #pageSize").val(data.pageSize);
		          $( "#dialog_edit2" ).dialog( "open" );
		  		event.preventDefault();
		        },
		        error: function(XmlHttpRequest, textStatus, errorThrown){
		        	showd("dialog_error");
		        }                
			 });
	    } 
	
function showd(dialog){
	var arg = arguments;
	var sure = $("#"+dialog+"");
	if(!arg.callee.open) {
		arg.callee.open = true;
		sure.css("display","block");
		sure.animate({
			top:"10px",
		},500,function() {
			setTimeout(function() {
				sure.css({
					top:0,
					display:"none"
				});
				arg.callee.open = false;
			},1000);
		});
	}
}
function validateInsertFollowUpForm() {  
	return $("#InsertFollowUp").validate({		
	 errorPlacement: function(error, element) {
		  if ( element.is(":radio") )
		    error.appendTo( element.parent().next().next() );
		  else if ( element.is(":checkbox") )
		    error.appendTo ( element.parent().parent().next() );
		  else
			  if(element.attr("name")=="upTime")
			  {
				  error.appendTo( element.next().next() );
			  }else{
				  error.appendTo( element.next() );
			  }
		},
        rules: {  
        	upTime: {  required: true},  
        	linkman: {  required: true},  
        	phone: {  required: true},  
        	remarks1: {  required: true}
  } 
  }).form();     
}
</script>

<!--删除跟进-->
<div id="dialog_del3" title="系统提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center">您确定要“删除”该条跟进信息吗？</div>
		<div class="hr40"></div>

		<div class="text-center">
		<input id="delectids" type="hidden"/>
			<button  class="btn delFollowUp btn-default btn_w_a btn_bg_2 mar-rig10">确认</button>
			<button class="btn dialog_closefollowUp btn_w_a btn_bg_4">取消</button>
			
		</div>
		
		
	</div>
</div>
<!--新增跟进记录-->
<div id="dialog_edit2" title="新增跟进记录">
	<div class="client_inner2">
		<div class="client_form_a1">
		
			<form:form id="InsertFollowUp" modelAttribute="followUp" action="${ctx}/workterrace/followUp/save" method="post" style="width:100%">
		
		<span style="display: none">
	<form:input id="id" path="id" />
				</span>	
	
	<form:hidden id="customerId3" path="customerId"/>
			
				<div class="form-group">
					<label class="labelname5 left control-label">公司名称：</label>
					
					<div class="labelcon9 left">
						<span id="customerName2" class="com_name7"></span>
					</div>
					<label class="labelname5 control-label left">时间：</label>
					<div class="left line_h26 w170">
						<input style="width:145px;float:left;" id="upTime" name="upTime" type="text" readonly="readonly" class="form-control ipt_w4" 
					value="<fmt:formatDate value="${followUp.upTime}" pattern="yyyy-MM-dd"/>"/><span  class="help-inline"></span>
					 	<div class="clearfix"></div>
					</div>
					<div class="clearfix"></div>
				</div>
				
				<div class="form-group">

					<label class="labelname5 left control-label">联系人：</label>
					<div class="labelcon9 left">
					 	<form:input path="linkman" htmlEscape="false" class="form-control ipt_w4"/><span  class="help-inline"></span>
					</div>

					<label class="labelname5 control-label left">联系号码：</label>
					<div class="left line_h26 w170">
						<form:input path="phone" htmlEscape="false" class="form-control ipt_w5"/><span  class="help-inline"></span>
					</div>
					<div class="clearfix"></div>
				</div>
				
				<div class="form-group">
					<label class="labelname5 left control-label">跟进内容：</label>
					<div class="labelcon8 left">
					 	<form:textarea path="remarks1" htmlEscape="false" maxlength="300" class="form-control textarea_n3" rows="3"/><span  class="help-inline"></span>
					 	<div class='hr10'></div>
					 	<label class="checkbox-inline">
						   <form:checkbox path="upContent" htmlEscape="false" value="信息公示已完成" /><span>信息公示</span>
						</label>
						<label class="checkbox-inline">
						 <form:checkbox path="upContent" htmlEscape="false" value="残疾人保证金缴纳" /><span>残疾人保证金缴纳</span>
						</label>
						<label class="checkbox-inline">
						    <form:checkbox path="upContent" htmlEscape="false" value="企业所得税汇算缴纳" /><span>企业所得税汇算缴纳  </span>
						</label>
					</div>
					<div class="clearfix"></div>
				</div>
				
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<span class="right">
						<input type="button" value="确定" onclick="InsertFollowUp()" class="btn btn-default btn_w_a btn_bg_2 mar-rig10"/>
						<input type="button" onclick="resutf()" class="btn btn-default btn_w_a btn_bg_4" value="重置"/>
						
						</span>
						<div class="right"></div>
					</div>
					<div class="clearfix"></div>
				</div>
			</form:form>
		</div>
	</div>
	
	<div class="pad_l_r15">
		<div class="tit_sc3 font-14 border_top1"><b>跟进列表</b></div>
		
			<form:form id="searchFormfollowUp" modelAttribute="followUp" action="${ctx}/inspection/workterrace/followUp/list" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<form:hidden id="customerId2" path="customerId"/>
					</form:form>
		<table class="table table-bordered table_list4">
			<thead>
				<tr>
					<th>跟进时间</th>
					<th>跟进内容</th>
					<th>联系人</th>
					<th>跟进人</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="followUpList">
			</tbody>
		</table><div name="searchFormfollowUp" class="pagination">${page}</div>
		<!--  <div class="pad_10_3">
			<div class="page_cc">共2条记录
				<button class="btn_p7"></button>
				<span class="pad_l_r5">1</span>
				<button class="btn_p8"></button>
			</div>
		</div>-->
	</div>
	
	<div id="dialog_sure1" class="dialog_sure">
		<div class="dialog">保存成功!</div>
	</div>
	<div id="dialog_delect1" class="dialog_sure">
		<div class="dialog">删除成功!</div>
	</div>
	<div id="dialog_error1" class="dialog_sure">
		<div class="dialog">页面君有点累，刷新页面试试</div>
	</div>
</div>
<!-- Templates -->
<p style="display:none"><textarea id="templateFollowUpForm" ><!--

{#foreach $T.list as followUp}
<tr>
				<td>{$T.followUp.upTime}</td>
				<td>{$T.followUp.remarks1}</td>
				<td>{$T.followUp.linkman}</td>
				<td>{$T.followUp.userName}</td>
				<td>
					<a title="编辑" onclick="formfollowUp('{$T.followUp.id}')" href="javascript:void(0);" class="followEdit btn btn-default btn_n4 bg_p6 btn_i2 pad_10 mar-top1 "></a>
					<a title="删除" onclick="delfollowUp('{$T.followUp.id}')" href="javascript:void(0);" class="followEdit btn btn-default btn_n4 bg_p6 btn_i4 pad_10" ></a>
			</tr>
		   {#/for}
-->
</textarea></p>
 <!-- Output elements -->	
</body>
<style>
.followEdit{
	padding: 10px;
}
	.dialog_sure {
		width: 100%;
		height: 30px;
		display: none;
		position: absolute;
		top: 0;
		left: 0;
	}
	.dialog_sure .dialog {
		width: 200px;
		height: 30px;
		background: #d8e1e8;
		margin: 0 auto;
		line-height: 30px;
		text-align: center;
	}
</style>
</html>