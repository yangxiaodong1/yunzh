<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="en">
<head>
	<title>资产负债表-在线会计-芸豆会计</title>
	<meta name="decorator" content="default" />
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta name="Author" content="">
	<meta name="Keywords" content="">
	<meta name="Description" content="">
	
	<script src="${ctxStatic}/jquery/jquery.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-ui.min.js" type="text/javascript"></script>

	<script src="${ctxStatic}/jquery/jquery-jtemplates.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery.form.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery.lightbox.js" type="text/javascript"></script>
	<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/fixedheadertable/jquery.fixedheadertable.js"></script>
	
	<script src="${ctxStatic}/js/rpt/balanceListJs.js"></script>
	<script src="${ctxStatic}/js/table_height.js"></script>
	<%-- <script src="${ctxStatic}/js/loading.js"></script> --%>
	<%-- <link href="${ctxStatic}/bootstrap/css/bootstrap.min.css"	type="text/css" rel="stylesheet" /> --%>
	<link href="${ctxStatic}/css/reset.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/jquery/jquery-ui.css" rel="stylesheet" type="text/css" />
	<link href="${ctxStatic}/css/lightbox.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="${ctxStatic}/css/master_1.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/css/public_v1.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/css/dialog.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/css/cashFlowvAndProfit.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/fixedheadertable/defaultTheme.css" rel="stylesheet" media="screen" />
	<link href="${ctxStatic}/accountAndbalance/css/dialog.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/css/downlist.css" rel="stylesheet" type="text/css">

<style>

/* table.table-profit tr td:nth-child(3),
	table.table-profit tr td:nth-child(4),
	table.table-profit tr td:nth-child(7),
	table.table-profit tr td:nth-child(8) {width: 10%;}
	table.table-profit tr td:nth-child(2),
	table.table-profit tr td:nth-child(6) {width: 3%;}
	table.table-profit tr td:nth-child(1),
	table.table-profit tr td:nth-child(5) {width: 27%;}  */
table.table-profit tr td:nth-child(2),table.table-profit tr td:nth-child(6) {
	text-align: center;
	padding: 0;
}

table.table-profit tr td:nth-child(3),table.table-profit tr td:nth-child(4),table.table-profit tr td:nth-child(7),table.table-profit tr td:nth-child(8) {
	text-align: right;
	padding-right: 8px;
}

.deleteSubject {
	display: none;
	height: 200px;
}

.confirm {
	font-size: 14px;
	line-height: 30px;
	margin: 30px 0;
}

.sure {
	background: #fba22c;
	float: left;
}

.layer .buttonW {
	width: 94px;
}

.td_col2 {
	width: auto;
}
</style>


<script type="text/javascript">
	//下载
	function downloadBalance() {
		var accountPeriod = $("#beginPeriodHidden").val();
		window.open("${ctx}/rpt/balance/downloadBalance?accountPeriod=" + accountPeriod, "", "");
	}
	//打印
	function stampBalance() {
		var accountPeriod = $("#beginPeriodHidden").val();
		window.open("${ctx}/rpt/balance/stampBalance?accountPeriod=" + accountPeriod, "", "");
	}
</script>

<script type="text/javascript">
	//	项目路径
	var ctx = "${ctx}";
	var ctxStatic = "${ctxStatic}";
	//var accountPeriod = "${accountPeriod}";
	//var periodEnd = "${periodEnd}";
</script>

</head>
<body>
<input type="hidden" id="balanceInfo" value="">
	<div class="account-wrapper profit">
		<h2 class="h2-tit" id="balanceTitle"></h2>
		<div class="th clearfix">

			<div class="th-left">
				<div class="list">
					<input class="detail_subject beginPeriod" type="text" value="" readonly style="width: 100px">
					<div class="triangle"></div>
					<ul class="period" style="width: 110px">
					</ul>
				</div>
				<input id="beginPeriodHidden" type="hidden" value="" />
				<p class="ptxt" id="companyName"></p>
			</div>

			<div class="th-right clearfix">
				<a href="javascript:void(0)" onclick="stampBalance()"><span>打印</span></span></a> <a href="javascript:void(0)" onclick="downloadBalance()"><span>下载</span></span></a>
			</div>
		</div>
		<div class="tb">
			<table id="myTable02" class="table-profit table-account-wrapper" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th width="24%">资产</th>
						<th width="4%">行次</th>
						<th width="11%">期末金额</th>
						<th width="11%">年初金额</th>
						<th width="24%">负债和所有者权益</th>
						<th width="4%">行次</th>
						<th width="11%">期末金额</th>
						<th width="11%">年初金额</th>
					</tr>
				</thead>
				<tbody id="tableInfo">
					
				</tbody>
			</table>
		</div>
	</div>
	<!--编辑-->
	<%-- <div id="dialog" title="编辑公式">
		<div class='hr10'></div>
		<form:form id="inputForm" modelAttribute="tAcctreportitem" action="" method="post">
			<form:hidden path="id" />
			<form:hidden path="reportitem" />
			<div class="control-group">
				<label class="control-label">科目编号：</label>
				<div class="controls">
					<form:select path="accountid" htmlEscape="false" maxlength="6" class="input-xlarge ">
						<form:option value=""></form:option>
						<c:forEach items="${listAcc}" var="acc">
							<form:option value="${acc.accuntId }">
							${acc.accuntId }${acc.accountName }
						</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">取数规则：</label>
				<div class="controls">
					<form:select path="ftype" htmlEscape="false" maxlength="4" class="input-xlarge ">
						<form:option value="Y">余额</form:option>
						<form:option value="D">借方余额</form:option>
						<form:option value="C">贷方余额</form:option>
					</form:select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">加减运算：</label>
				<div class="controls">
					<form:select path="op" htmlEscape="false" maxlength="4" class="input-xlarge ">
						<form:option value="+">+</form:option>
						<form:option value="-">-</form:option>
					</form:select>
				</div>
			</div>
			<div class="form-actions">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="savaBalance()" /> <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)" />
			</div>
		</form:form>
		<div class='step_panel'>
			<div class='step_con step_a1'>
				<table class="table table-bordered table_vmiddle table_pad_no" style='font-size: 12px;'>
					<thead>
						<tr>
							<th><span class='pad_l_r5'>科目</span></th>
							<th><span class='pad_l_r5'>运算符号</span></th>
							<th><span class='pad_l_r5'>取数规则</span></th>
							<th><span class='pad_l_r5'>期末数</span></th>
							<th><span class='pad_l_r5'>年初数</span></th>
							<th><span class='pad_l_r5'>操作</span></th>
						</tr>
					</thead>
					<tbody id="listEFormula">
					</tbody>
				</table>
			</div>
		</div>
	</div> --%>
	<!-- Templates -->
	<p style="display: none">
		<textarea id="template">
			<!--

{#foreach $T as eformula}
<tr>
	<td><span class='pad_l_r5'>
		{$T.eformula.accuntid }{$T.eformula.accountName }
	</span></td>
	<td><span class='pad_l_r5'>
		{$T.eformula.op }	
	</span></td>
	<td><span class='pad_l_r5'>
		{$T.eformula.ftype }
	</span></td>
	<td><span class='pad_l_r5'>
		{$T.eformula.periodEnd }
	</span></td>
	<td><span class='pad_l_r5'>
		{$T.eformula.yearStart }
	</span></td>
	<td>
		<a id="{$T.eformula.acctReportItemId }" href="javascript:void(0)" onclick="deleteBalance({$T.eformula.acctReportItemId })">删除</a>
	</td>
</tr>
{#/for}
-->
</textarea>
	</p>
	<div class="mask"></div>
	<div class="layer layerS deleteSubject">
		<h5 class="h6-kr">
			系统提示<i class="i-close"></i>
		</h5>
		<div class="confirm">资产负债表不平，请检查账务处理。</div>
		<div class="buttonW clearfix">
			<button class="sure">确定</button>
			<!-- <button class="fsave cancel_delete">取消</button> -->
		</div>
	</div>
	<script>
		//日期选择
		$(function() {
			var balanceInfo = $("#balanceInfo").val();
			$("html").on("click", function() {
				$(".list ul").hide();
			})
			$(".detail_subject,.triangle").each(function() {
				$(this).on("click", function(e) {
					var e = window.event || e;
					e.stopPropagation();
					$(this).parent().find("ul").show();
					$(this).parent().siblings().find("ul").hide();
				})
			})
			$(".list:eq(0)").find("ul li").on("click", function() {
				var beginPeriod = $(this).attr("period");
				$("#beginPeriodHidden").attr("value", beginPeriod);
				tableInfo(beginPeriod,beginPeriod);
			});

			$(".list ul li").on("click", function() {
				$(this).parents(".list").find(".detail_subject").val($(this).text());
				var beginPeriod = $("#beginPeriodHidden").val();
				titleInfoFun(beginPeriod,balanceInfo);
			});
			var beginPeriod = $("#beginPeriodHidden").val();
			titleInfoFun(beginPeriod,balanceInfo);
		});
	</script>
	<script type="text/javascript">
		$(function() {
			//日期
			/* $( "#datepicker" ).datepicker({
				"dateFormat":"yy-mm-dd"
			});
			$( "#datepicker2" ).datepicker({
				"dateFormat":"yy-mm-dd"
			}); */
			/******编辑弹出框****/
			/* $( "#dialog_edit2" ).dialog({
				autoOpen: false,
				width: 470,
				height:300,
				modal: true,
				buttons: [
					{
						text: "取消",
						Class:"btn_close2",
						click: function() {
							$( this ).dialog( "close" );
						}
						
					},
					{
						text: "保存",
						Class:"btn_save2",
						click: function() {
							$( this ).dialog( "close" );
						}
					}
				]
			});	
			$( ".dialog_link" ).click(function( event ) {
				$( "#dialog_edit2" ).dialog( "open" );
				event.preventDefault();
			}); */

		})
	</script>

</body>
<script type="text/javascript">
	$(function() {
		console.log($(window).height())
		$(".fht-tbody").css({
			"height" : $(window).height() - 220
		});
	})
</script>
</html>