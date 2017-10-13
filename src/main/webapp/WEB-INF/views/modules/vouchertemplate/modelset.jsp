<%@ page contentType="text/html;charset=UTF-8"%>
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
<title>凭证模板-在线会计-芸豆会计</title>
<link href="${ctxStatic}/css/reset.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/main.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/sangetanceng.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/js/modelset.js" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/css/modelset.css" rel="stylesheet" type="text/css">

<link href="${ctxStatic}/css/newSubjectTwo.css" rel="stylesheet" type="text/css">

<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/js/modelset.js" type="text/javascript"></script>
<script src="${ctxStatic}/handlebar/handlebars-1.0.0.beta.6.js" type="text/javascript"></script>
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src='${ctxStatic}/js/imagesLoaded.js'></script>
<script type="text/javascript" src="${ctxStatic}/js/masonry.pkgd.min.js"></script>

<link href="${ctxStatic}/css/autoAmortization.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
	//	项目路径
	var ctx = "${ctx}";
	var balance;
</script>
<style>
.item {
	height: 254px;
	overflow: hidden;
}

.t_pane_a1 .item .captions .list_t5 {
	height: 227px;
	overflow: hidden;
}

.tit_channel_1 {
	margin-top: 10px;
}

.t_pane_a1 .item .captions .list_t5 ul {
	min-height: 40px;
}

.vou-left {
	width: 320px;
}

.voucher-top input {
	width: 200px;
}

.message-pop {
	display: none;
	position: fixed;
	top: 40px;
	width: 100%;
	text-align: center;
	z-index: 2000;
	opacity: 1;
}

.message-pop span {
	background: #4f9de8;
	padding: 5px 75px;
	color: #fff;
}

.h2-tit {
	width: 100%;
	text-align: center;
	font: 24px/50px "Microsoft Yahei";
	color: #1b1b1b;
}
.savevoucher {
	position:static;
	float:right;
	margin:0 30px 20px 0;
}
</style>
</head>
<body style="background: #fff;">
	<div class="message-pop">
		<span>保存成功！</span>
	</div>
	<h2 class="h2-tit">新增凭证</h2>
	<!-- 选择模板 -->
	<div id="tab">
		<input type="hidden" id="operate-template-id">
		<div class="content">
			<div class="switch clearfix">
				<a href="javascript:;"><i></i>日常凭证模板</a><a href="javascript:;"><i></i>计提转结模板</a>
			</div>
			<div class="tabCont">
				<div class="cont conta clearfix">
					<div class="contH"></div>
				</div>
				<div class="cont contb clearfix">
					<div class="contH"></div>
				</div>
				<div class="cont contc clearfix">
					<div class="contH"></div>
				</div>
			</div>
		</div>
	</div>

	<div id="save-template" class="layer template dialog" style="display: none;">
		<h5 class="h6-kr">
			保存模板<i class="bill-info-close-dialog"></i>
		</h5>
		<ul class="ul-template clearfix">
			<li class="clearfix"><label for="">模板名称</label><input id="save-template-input" type="text"></li>
			<li class="clearfix"><label for="">保存金额</label><input id="save-template-checkbox" type="checkbox"></li>
		</ul>
		<div class="buttonW clearfix">
			<button class="cancel">取消</button>
			<button class="save save-template-save">保存</button>
		</div>
	</div>


	<div class="tbvoucher dialog  voucher-template-dialog" style="display: none;">
		<input type="hidden" id="voucher-template-id">
		<h5 class="h6-kr">
			新建模板<i class="bill-info-close-dialog"></i>
		</h5>
		<div class="body">
			<div class="voucher-top clearfix">
				<div class="vou-left">
					<label for="" class="lab-vou">模板名称</label><input type="text" id="new-save-template-input">
				</div>
			</div>
			<table class="tab-voucher" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th class="col-operate"></th>
						<th class="col-summary">摘要</th>
						<th class="col-subject">会计科目</th>
						<th class="col-money col-debite"><strong class="tit">借方金额</strong>
							<div class="money-unit">
								<span>亿</span> <span>千</span> <span>百</span> <span>十</span> <span>万</span> <span>千</span> <span>百</span> <span>十</span> <span>元</span> <span>角</span> <span class="last">分</span>
							</div></th>
						<th class="col-money col-credit"><strong class="tit">贷方金额</strong>
							<div class="money-unit">
								<span>亿</span> <span>千</span> <span>百</span> <span>十</span> <span>万</span> <span>千</span> <span>百</span> <span>十</span> <span>元</span> <span>角</span> <span class="last">分</span>
							</div></th>
						<th class="col-delete"></th>
					</tr>
				</thead>
				<tbody class="tbody-voucher">
				
				</tbody>
				<tfoot>
					<tr>
						<td class="col-operate"></td>
						<td colspan="2" class="col-total">合计：<span id="capAmount"></span></td>
						<td class="col-debite col-debite1">
							<div class="cell-val debit-total" id="debit-total"></div>
						</td>
						<td class="col-credit col-credit1">
							<div class="cell-val credit-total" id="credit-total"></div>
						</td>
						<td class="col-delete"></td>
					</tr>
				</tfoot>
			</table>
			<p class="make-person">
				&nbsp;
				<!--制单人：<em>${user.name}</em> -->
			</p>
			<div class="savevoucher">保存模板</div>
			<div style="clear:both;"></div>
		</div>
	</div>

	<!-- 温馨提示 -->
	<div class="autoAmortization dialog" style="display: none;">
		<input type="hidden" id="autoAmortization-amortizeAccountId">
		<h6 class="h6-kr">
			设置自动摊销<i id="autoAmortization-close" class="bill-info-close-dialog"></i>
		</h6>
		<table class="tab-autoAmortization" cellspacing="0" cellpadding="0">
			<tbody>
				<tr>
					<td width="173">摘要</td>
					<td width="249">会计科目</td>
					<td width="93">分摊月数</td>
					<td width="87">残值率</td>
					<td width="120">残值</td>
				</tr>
				<tr>
					<td><input class="ipt-summary" type="text" readonly="readonly"></td>
					<td>借<input class="ipt-subjext ipt-debit" type="text"></td>
					<td><input class="ipt-monthnum" type="text">月</td>
					<td><input class="ipt-value" type="text"></td>
					<td id="scrap-value">0</td>
				</tr>
				<tr>
					<td><input class="ipt-summary" type="text" readonly="readonly"></td>
					<td>贷<input class="ipt-subjext ipt-credit" type="text"></td>
					<td></td>
					<td style="visibility: hidden;"></td>
					<td style="visibility: hidden;"></td>
				</tr>
			</tbody>
		</table>
		<div class="buttonW clearfix">
			<button id="autoAmortization-cancel" class="cancel">取消</button>
			<button class="save autoAmortization-save">保存</button>
		</div>
	</div>

	<!-- 科目-->
	<ul class="ul-select-amortize" style="display: none">

	</ul>

	<div class="pull-down" style="display: none">
		<ul class="ul-select">

		</ul>
		<!-- <span class="sp-add">新增科目</span> -->
	</div>

	<div class="mask"></div>

	<!-- 温馨提示-->
	<div class="layer reminder dialog" style="display: none;">
		<input id="delete-template-id" type="hidden">
		<h5 class="h6-kr">
			温馨提示<i class="bill-info-close-dialog"></i>
		</h5>
		<ul class="ul-kindlyReminder clearfix">
			<li class="clearfix">是否删除凭证模板？</li>
			<li class="txt-col clearfix">删除后将无法恢复!</li>
		</ul>
		<div class="buttonW clearfix">
			<button class="cancel">取消</button>
			<button class="save delete-template-save">删除</button>
		</div>
	</div>


	<!-- 票据跨期弹层 -->
	<div id="subject-select-add" class="layer newSubjectTwo" style="display: none">
		<h5 class="h6-kr">
			新增科目<i id="subject-select-add-close" class="i-close"></i>
		</h5>
		<ul class="ul-newSubjectTwo clearfix">
			<li class="clearfix"><label for="">科目名称</label><input type="text" id="subject-select-add-name" class="inp-length" value=""></li>
			<li class="clearfix"><label for="">上级科目</label> <select id="subject-select-add-select" type="text" class="inp-name" value="招商银行">

			</select></li>
		</ul>
		<div class="button buttonW clearfix">
			<button id="subject-select-add-cancel" class="cancel">取消</button>
			<button id="subject-select-add-save" class="save">保存</button>
		</div>
	</div>


	<!-- 是否设置设置摊销 -->
	<div id="newPop" class="layer newPop" style="display: none">
		<h5 class="h6-kr">
			摊销科目<i class="i-close"></i>
		</h5>
		<p class="prompt">你是否确定不设置摊销或折旧方法</p>
		<div class="button buttonW clearfix">
			<button id="newPop-save" class="cancel">确定</button>
			<button id="newPop-cancel" class="save">取消</button>
		</div>
	</div>

	<!--页面脚本区S-->
	<!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->
	<script>
		//提示信息
		var messagePop = function(str) {
			$(".message-pop").fadeIn();
			$(".message-pop").children("span").html(str);
			setTimeout(function() {
				$(".message-pop").fadeOut();
			}, 2000)
		}

		$(function() {
			var _contr = $(".ul-each li"), _focs = $(".switch .lay"), _bills = $(".ul-bill li"), _dels = $(".ul-bill li .a-del");
			_contr.bind("click", function() {
				var i = $(this).index();
				_focs.hide();
				$(_focs[i]).show();
			})
			// 图表删除按钮
			_bills.hover(function() {
				$(this).find(".a-del").show();
				$(this).find(".a-wrong").show();

			}, function() {
				$(this).find(".a-del").hide();
				$(this).find(".a-wrong").hide();
			})

			$("body").on("click", ".ul-bill li .a-del", function() {
				$(".popUp").show();
				$(".mask").show();
			})

			$("body").on("click", ".butcancel", function() {
				$(".popUp").hide();
				$(".mask").hide();
			})

			$("body").on("click", ".i-close", function() {
				$(".popUp").hide();
				$(".mask").hide();
			})
			//查看大图-关闭
			$("body").on("click", ".bill-info-close-dialog,.cancel", function() {
				$(".dialog").hide();
				$(".mask").hide();
				if ($(".to-new-voucher-template-current").length > 0) {
					$(".to-new-voucher-template-current").addClass("voucher-current").removeClass("to-new-voucher-template-current");
				}
			})

			// 会计科目 余额的显示隐藏
			$("body").on("input propertychange", ".inp-sub", function() {
				var divstr = $(this).prev().text();
				var inpstr = $(this).val();
				if (divstr == inpstr) {
					$(this).parent().next().find(".option").append('<a class="balance" data-id="762192203369742" data-number="1001" data-cur="[&quot;RMB&quot;]">余额</a>');
				} else {
					$(".balance").remove();
				}
			})

			var $container = $('.contH');
			$container.imagesLoaded(function() {
				$container.masonry({
					columnWidth : '.contlist',
					itemSelector : '.contlist'
				});
			});
		})

		var $mainH2 = 395;

		jQuery.extend({
			sw : function(id, event, tabActiveClass) {
				var _this = $("#" + id), _tabs = _this.find(".switch a"), _contents = _this.find(".cont"), showOne = function(i) {
					$(".tabCont").height(function() {
						return $mainH2;
					})

					var $container = $('.contH');
					$container.imagesLoaded(function() {
						$container.masonry({
							columnWidth : '.contlist',
							itemSelector : '.contlist'
						});
					});

					_tabs.removeClass(tabActiveClass);
					_tabs.eq(i).addClass(tabActiveClass);
					_contents.hide();
					_contents.eq(i).show();

				};
				showOne(0);
				_tabs.bind(event, function(e) {
					e.preventDefault();
					var _curr_tab = $(this), _idx = _curr_tab.index();
					showOne(_idx);
				})
			}
		});

		$(function($) {
			$.sw("tab", "mouseover", "on");
		})
	</script>
	<!--页面脚本区E-->
	<script id="accountTemplate" type="text/x-handlebars-template">	
{{#each this}}
	<li data-id="{{id}}" data-dc="{{dc}}" data-ifamortize="{{ifAmortize}}" data-accuntid="{{accuntId}}">{{accuntId}}&nbsp;{{parentName}}{{accountName}}</li>
{{/each}}
</script>
</body>
</html>