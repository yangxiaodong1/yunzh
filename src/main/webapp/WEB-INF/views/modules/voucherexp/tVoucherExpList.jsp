<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta charset="utf-8" />
<title>查凭证-在线会计-芸豆会计</title>
<meta name="keywords" content="关键字,关键字,关键字" />
<meta name="description" content="描述。……" />
<meta name="Author" content="author_bj designer_bj">
<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctxStatic}/taxbase/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<script src="${ctxStatic}/js/table_height.js"></script> 
<link href="${ctxStatic}/css/master_2.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/reset.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/accountSettle.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/checkCredentials.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/print_dialog.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/dialog.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/master_2.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/certificate_table.css" rel="stylesheet" type="text/css">
<%-- <link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" type="text/css"> --%>
<link href="${pageContext.request.contextPath}/static/css/sangetanceng.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/static/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery.form.js"></script>
<meta name="Author" content="author_bj designer_bj">
<%-- <script src="${pageContext.request.contextPath}/static/js/settleAccount.js" type="text/javascript"></script> --%>
<script type="text/javascript">
 //	项目路径
 var ctx = "${ctx}";
</script>
<style>
*{font-size:12px;}
.btn_txt{padding:0 5px;float:right;margin-right:60px;visibility:hidden;}
.checkVoucher .btn_txt {float:right;visibility:hidden;width: 100px;margin-right: 35px;}
.checkVoucher .btn_txt span {width: 46px;text-indent: 20px;}
.ui-dialog .ui-dialog-title {
	width: auto;
	}
.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {
	background: url(images/ico/21.jpg) no-repeat center;
}
#dialog_remove {
	display:none;
}
.message-pop{display:none;position:fixed;top:40px;width:100%;text-align:center;z-index:1000;}
.message-pop span{background:#4f9de8;padding:5px 75px;color:#fff;}
.hoverCont .shade {position: absolute;top: 0;left: 75px;height: 30px;width: 226px;background: #e7f3ff;opacity: 0.6;filteer:alpha(opacity=60);z-index: 2;display:none;}
.total_text {text-align:right;padding-right: 8px;}
#contentTable .hoverCont span.pzupload {text-indent:20px;}
</style>
<script type="text/javascript">
var ctxStatic = "${ctxStatic}";
</script>
</head>
<body>
	<div class="checkVoucher">
		<div class="message-pop">
			<span></span>
		</div>
		<h2 class="h2-tit">
		
		</h2>
		<div class="th clearfix">
			<div class="th-left">
				<form:form id="searchForm" modelAttribute="tVoucher" action="${ctx}/voucherexp/tVoucherExp/periodShow" method="post" class="breadcrumb form-search">
					<form:hidden id="fdbid" path="fdbid"/>
					<form:select path="accountPeriod" htmlEscape="false" id="Period" class="seltime">
				 		<c:forEach items="${periodList}" var="period">				 				
									<form:option value="${period}">
									${fn:substring(period, 0, 4)}年
									${fn:substring(period, 4, 6)}期
									</form:option>							
						</c:forEach> 
	             	</form:select>&nbsp;&nbsp;
				</form:form>					
			</div>
			<div class="th-right clearfix">
				<span id="settleAccount">结账</span>
				<span class="print_all">打印全部</span>
			</div>
		</div>
		<div class="tb">
			<div class="thead_container">
				<table class="tabhead" width="100%" cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<th width="25%">摘要</th>
							<th width="35%">科目</th>
							<th width="20%">借方金额</th>
							<th width="20%">贷方金额</th>
						</tr>
					</thead>
				</table>
			</div>
			
			<div class="tb_scroll">
			<c:forEach items="${tvoucherList}" var="tvoucher">
				<c:if test="${tvoucher.count>0}">
					<div class="contentTable showTable">
						<table id="contentTable" class="table" width="100%" cellpadding="0" cellspacing="0">
							<thead>
							<tr>
							<td class="list-head" colspan="4">
							<div class="clearfix">
								<span>日期:<fmt:formatDate value="${tvoucher.accountDate}" pattern="yyyy-MM-dd"/></span>
								<span>凭证字号：记-${tvoucher.voucherTitleNumber}</span>
								<span>记账人：${tvoucher.userName}</span>
								<div class="hoverCont"> 
										<span class="s-icon image"><i></i>影像</span>
										<span class="s-icon updatebill" data-id="${tvoucher.id}"><i></i>修改</span>
										<span id="${tvoucher.id}" class="s-icon dialog_remove" onclick=""><i></i>删除</span>
										<span class="s-icon insertbill" data-id="${tvoucher.id}" data-title="${tvoucher.voucherTitleNumber}"><i></i>插入</span>
										<span class="s-icon" onclick="pdfshow('${tvoucher.id}')"><i></i>打印</span>
										<span class="s-pizhu s-icon" mouseout="test()"><i></i>批注
											<div class="pizhu"><em></em><em>0/100</em>
											<c:if test="${tvoucher.commenResult=='1'}">
											<textarea style='height:90px;' placeholder="" name="" id="text${tvoucher.id}" cols="30" rows="10" focus="test()" blur="test()">${tvoucher.expComment}</textarea>
											</c:if>
											<c:if test="${tvoucher.commenResult=='0'}">
											<textarea style='height:90px;' placeholder="请输入相关内容" name="" id="text${tvoucher.id}" cols="30" rows="10" focus="test()" blur="test()"></textarea>
											</c:if>
											<div class="th-right clearfix btn_txt">
												<span id="settle${tvoucher.id}" class="pzupload">提交</span>
											</div>
											
											<!--  <input id="pizhusubmit" onclick="puzhuajax('${tvoucher.id}')" type="button" value='提交' class='btn_txt'/>-->
											</div> 
										</span>
										<div class="shade"></div>
								</div>
								<c:if test="${tvoucher.commenResult=='0'}">
									<span id="sign${tvoucher.id}" class="sign" style="display:none;"></span>
								</c:if>
								<c:if test="${tvoucher.commenResult=='1'}">
									<span id="sign${tvoucher.id}" class="sign"></span>
								</c:if>
							</div>
							</td>
							</tr>
							</thead>
							<tbody class="list-body">
								<c:forEach items="${tvoucherexpList}" var="tvoucherexp">
									<c:if test="${tvoucherexp.TVId==tvoucher.id}">
										<tr>
											<td  width="25%">${tvoucherexp.exp}</td>
											<td  width="35%">${tvoucherexp.accountName}</td>
											<td  width="20%">${tvoucherexp.showdebit}</td>
											<td  width="20%">${tvoucherexp.showcredit}</td>
										</tr>
									</c:if>
								</c:forEach>
								<tfoot>
									<tr>
										<td class="list-foot" colspan="2">合计：${tvoucher.money}</td>
										<td class="total_text">${tvoucher.totalAmountshow}</td>
										<td class="total_text">${tvoucher.totalAmountshow}</td>
									</tr>
								</tfoot>
							</tbody>
						</table>
					</div>
				</c:if>
			</c:forEach>
			</div>
		</div>
	</div>
	
	
	<!-- 结账 -->
	<div class="bg" style="background:#000;opacity:0.5;width:100%;height:100%;position:absolute;top:0;left:0;display:none;"></div>
	<div class="settle-wrapper" style="display:none;">
		<h6 class="h6-kr">结账<i class="close-dialog"></i></h6>
		<div class="tb">
			<!-- <div class="th clearfix">
				<div class="th-left">
					<span class="s-money"><i class="i-money">101库存现金</i><i class="i-icon"></i></span>				
				</div>
			</div> -->
			<div class="cont">
				<div class="txtbanner">
					<p>凭证都已经自动生成，请确认无误后再结账！</p>
					<button id="sure-settle-account">确认结账</button>
				</div>
				<div class="model">
					<ul id="settle-account-ul" class="clearfix">
						<!-- <li>
							<h6>结转销售成本<i class="btn-bill">清单</i></h6>
							<p class="p-num">9.99</p>
							<div class="p-txt">
								<i>
									<div class="tc">
										<em></em>
										<p>本期应缴增值税：0.00</p>
										<p>应缴城市维护建设税税率 <input type="text"> %</p>
										<p>教育费附加税率 <input type="text"> %</p>
										<p>地方教育费附加税率 <input type="text"> %</p>
									</div>
								</i>凭证已自动生成
							</div>
						</li>
						-->
					</ul>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- 查看凭证 -->
	<div class="tbvoucher dialog voucher-current" style="display:none;z-index:999;">
		<h5 class="h6-kr">录入凭证<i class="bill-info-close-dialog"></i></h5>
				<div class="body">
					<div class="voucher-top clearfix">
				       <div class="vou-left">
					      	记字第<input class="ui-spinbox" type="text"  value="" readonly="readonly">号
					   </div>
					   <div class="date-wrap">
			            	日期 <input type="text" class="ui-datepicker-input ui-input ui-datepicker-input Wdate" id="vch-date" value="${date}" onFocus="WdatePicker({minDate:'%y-{%M-1}-01',maxDate:'%y-{%M-1}-%ld'})" readonly="readonly">
					   </div>
					   <div class="vou-right">
					      	附单据<input type="text"  value="0" readonly="readonly">张
					   </div>
			    	</div> 
					<table class="tab-voucher min-height-s"  cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th class="col-operate"></th>
								<th class="col-summary">摘要</th>
								<th class="col-subject">会计科目</th>
								<th class="col-money col-debite"> 
									<strong class="tit">借方金额</strong>
						            <div class="money-unit"> 
						            	<span>亿</span> 
						            	<span>千</span> 
						            	<span>百</span> 
						            	<span>十</span> 
						            	<span>万</span> 
						            	<span>千</span> 
						            	<span>百</span> 
						            	<span>十</span> 
						            	<span>元</span> 
						            	<span>角</span> 
						            	<span class="last">分</span> 
						            </div>
          						</th>
								<th class="col-money col-credit">
									<strong class="tit">贷方金额</strong>
						            <div class="money-unit"> 
						            	<span>亿</span> 
						            	<span>千</span> 
						            	<span>百</span> 
						            	<span>十</span> 
						            	<span>万</span> 
						            	<span>千</span> 
						            	<span>百</span> 
						            	<span>十</span> 
						            	<span>元</span> 
						            	<span>角</span> 
						            	<span class="last">分</span> 
						            </div>
								</th>
								<th class="col-delete"></th>
							</tr>
						</thead>
						<tbody class="tbody-voucher">
							
						</tbody>
						<tfoot>
					      	<td class="col-operate"></td>
					        <td colspan="2" class="col-total">合计：<span id="capAmount"></span></td>
					        <td class="col-debite col-debite1">
					        	<div class="cell-val debit-total" id="debit-total"></div>
					        </td>
					        <td class="col-credit col-credit1">
					        	<div class="cell-val credit-total" id="credit-total"></div>
					        </td>
					        <td class="col-delete"></td>
					    </tfoot>
					</table>
					<p class="make-person">制单人：<em>${user.name}</em></p>
					<!-- <div class="savevoucher-list">保存凭证</div> -->
				</div>
			</div>
			
			<!-- 打印全部 -->
			<div class="bg" style="background:#000;opacity:0.5;width:100%;height:100%;position:absolute;top:0;left:0;display:none;"></div>
	 		<div class="print_dialog">
				<h5 class="h6-kr">请选择打印模板<i class="bill-info-close-dialog"></i></h5>
				<div class="template">
					<div class="title">选择模版后，点击打印</div>
					<div class="template_chose" id="twoDiv">
						<div class="container">
							<div class="checked">
								<div class="checked_bg"></div>
							</div>
							<div class="Serial_number">
								<span>1</span>
							</div>
							<table>
								<tr>
									<th colspan="4">记账凭证</th>
								</tr>
								<tr>
									<td>摘要</td>
									<td>科目</td>
									<td>借方</td>
									<td>贷方</td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</table>
							<table>
								<tr>
									<th colspan="4">记账凭证</th>
								</tr>
								<tr>
									<td>摘要</td>
									<td>科目</td>
									<td>借方</td>
									<td>贷方</td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</table>
						</div>
						<div class="note">AA两版</div>
					</div>
					<div class="template_chose" id="threeDiv">
						<div class="container">
							<div class="checked">
								<div class="checked_bg"></div>
							</div>
							<div class="Serial_number">
								<span>2</span>
							</div>
							<table class="tb_dialog">
								<tr>
									<th colspan="4">记账凭证</th>
								</tr>
								<tr>
									<td>摘要</td>
									<td>科目</td>
									<td>借方</td>
									<td>贷方</td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</table>
							<table class="tb_dialog">
								<tr>
									<th colspan="4">记账凭证</th>
								</tr>
								<tr>
									<td>摘要</td>
									<td>科目</td>
									<td>借方</td>
									<td>贷方</td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</table>
							<table class="tb_dialog">
								<tr>
									<th colspan="4">记账凭证</th>
								</tr>
								<tr>
									<td>摘要</td>
									<td>科目</td>
									<td>借方</td>
									<td>贷方</td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</table>
						</div>
						<div class="note">AA三版</div>
					</div>
					<div class="template_chose" id="oneDivfour">
						<div class="container" style="height:186px;">
							<div class="checked">
								<div class="checked_bg"></div>
							</div>
							<div class="Serial_number">
								<span>3</span>
							</div>
							<table>
								<tr>
									<th colspan="4">记账凭证</th>
								</tr>
								<tr>
									<td>摘要</td>
									<td>科目</td>
									<td>借方</td>
									<td>贷方</td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</table>
						</div>
						<div class="note">发票版 14*24cm</div>
					</div>
					<div class="template_chose" id="oneDivtwo">
						<div class="container" style="height:120px;">
							<div class="checked">
								<div class="checked_bg"></div>
							</div>
							<div class="Serial_number">
								<span>4</span>
							</div>
							<table class="tb_dialog">
								<tr>
									<th colspan="4">记账凭证</th>
								</tr>
								<tr>
									<td>摘要</td>
									<td>科目</td>
									<td>借方</td>
									<td>贷方</td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</table>
						</div>
						<div class="note">发票版 12*21cm</div>
					</div>
					<div class="clear"></div>
					<div class="checkVoucher print_help">
						<!-- <div class="th-right clearfix">
							<span class="print_all">模板3,4打印帮助</span>
						</div> -->
					</div>
				</div>
			</div>
<!--删除提示-->
<div id="dialog_remove" title="系统提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center">您确定要“删除”该凭证吗？</div>
		<div class="hr40"></div>
		
		<div class="text-center">
			<button class="btn btn-default btn_w_a btn_bg_4 cancel mar-rig10">取消</button>
			<button class="btn btn-default btn_w_a btn_bg_2 suredelete">确认</button>
		</div>

	</div>
</div>		

<!--页面脚本区S-->
<!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->
<script>
$(function(){
	//修改凭证
	$(".updatebill").on("click",function(e){
		var id = $(this).data("id");
		window.location.href = "${ctx}/voucher/tVoucher/modifyVoucher?id="+id+"&accountPeriod="+$("#Period").val();	 
	})
	
	//插入凭证
	$(".insertbill").on("click",function(e){
		var title = $(this).data("title");
		window.location.href = "${ctx}/voucher/tVoucher/insertVoucher?title="+title+"&accountPeriod="+$("#Period").val();	 
	})
	
	
})
	$(".print_all").on("click",function() {
		$(".bg").show();
		$(".print_dialog").show();
	})
	
	$(".bill-info-close-dialog").on("click",function() {
		$(".bg").hide();
		$(".print_dialog").hide();
	})

	$(".template_chose").each(function() {
		$(this).hover(function() {
			$(".checked",this).show();
		},function() {
			$(".checked",this).hide();
		})
	})
	$( "#dialog_remove" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});	
</script>
<!--页面脚本区E-->
<script type="text/javascript">
	   //全局变量
	   var id;
	   //页面加载
	   $(function(){
		   //加载结账函数
		   jzclick();
		   //首次加载
		   var curr='${Currentperiod}';
		   var dq='${tVoucherfirst.accountPeriod}';
		   if($("#Period").val()!=dq.toString()){
			   $(".hoverCont .shade").show();
		   }
		   
		   if($("#Period").val()!=curr.toString()){
			   var last='${selectTvoucher}';
			   if($("#Period").val()==last.toString()){
				   	$("#settleAccount").replaceWith("<span id='settleAccountcontrary'>反结账</span>");
				  	$(".dialog_remove").replaceWith("<span class='s-icon forbid'><i></i>删除</span>");
					$(".insertbill").replaceWith("<span class='s-icon forbid'><i></i>插入</span>");
					$(".updatebill").replaceWith("<span class='s-icon forbid'><i></i>修改</span>");
				}else{
					$("#settleAccount").hide();
				}
		   }
		   $("#settleAccountcontrary").click(function(){
			  /* var last='${selectTvoucher}';
			   //alert(last.toString()+'     '+$("#Period").val());
			   //alert(last.toString()+'     '+$("#Period").val());
			   if($("#Period").val()!=last.toString())
			   		messagePop('不在反结账规定的期限');
			   else{*/
				   
				   $.ajax({
				        type: "POST",//使用post方法访问后台
				        url: "${ctx}/voucherexp/tVoucherExp/tvoucherFan",//要访问的后台地址  
				        data: "fdbid="+$("#fdbid").val(),
				        success: function(data){
				        	if(data=="1"){
				        		alert("反结账成功");
				        		$("#settleAccountcontrary").replaceWith("<span id='settleAccount'>结账</span>");
				        		jzclick();
				        	}
				        }
				  })
				
			/*}*/})
			
			
	   })
	 	//
	 	function jzclick(){
		   $("#settleAccount").on("click",function() {
				var rel="link_24";
			    var text="结账";
			    var famsrc="${ctx}/settleAccounts/settleAccounts/gotocheckout";
				window.location.href = famsrc;   
			})
	   }
	   
	   //单个打印
	  function pdfshow(date){
				window.open("${ctx}/voucher/tVoucher/pageShowOne?accountPeriod="+$("#Period").val()+"&fdbid="+$("#fdbid").val()+"&id="+date+"&count="+1,"","") ;
	 	}
	   //删除方法 
	  //点击事件删除确定事件
	  $(".dialog_remove").click(function( event ) {
		  	id=$(this).attr('id');
			$( "#dialog_remove" ).dialog( "open" );
			$(".cancel").on("click",function() {
				$( "#dialog_remove" ).dialog( "close" );
			})
		}
	  );
	  //首次页面显示
	   $(document).ready(function(){
		   var show=${tVoucherfirst.accountPeriod};
		   if($("#Period").val()==show.toString())
		   		showhead(show);
		   else
			   showhead($("#Period").val());
		   /*var showstring=show.toString();
		   var year=showstring.substring(0,4)+"年";
		   var math=showstring.substring(4,6)+"期凭证";
		   $('.h2-tit').text(year+math);*/
	   })
	  var messagePop = function(str){
			$(".message-pop").fadeIn();
			$(".message-pop").children("span").html(str);
			setTimeout(function(){
				$(".message-pop").fadeOut();
			},2000)
		}
	  function showhead(show){
		  //var show=${tVoucherfirst.accountPeriod};
		   var showstring=show.toString();
		   var year=showstring.substring(0,4)+"年";
		   var math=showstring.substring(4,6)+"期凭证";
		   $('.h2-tit').text(year+math);
	  }
	   
	</script>
	
<script src="${ctxStatic}/js/myjson.js" type="text/javascript"></script> 

</body>
</html>