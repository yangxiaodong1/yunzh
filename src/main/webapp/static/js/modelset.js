//凭证一行
var voucherTr  = '<tr class="entry-item"><td class="col-operate"><div class="add-delete"><a class="a-add" href="javascript:;"></a></div></td><td class="col-summary" data-edit="summary"><div class="cell-val summary-val"></div></td><td class="col-subject" data-edit="subject"><div class="cell-val subject-val"><p></p><p></p></div></td><td class="col-debite col-dc" data-edit="money"><div class="cell-val debit-val"></div></td><td class="col-credit col-dc" data-edit="money"><div class="cell-val credit-val"></div></td><td class="col-delete"><div class="add-delete"><a class="a-delete" href="javascript:;"></a></div></td></tr>';
//<tr class="entry-item"><td class="col-operate"><div class="add-delete"><a class="a-add" href="javascript:;"></a></div></td><td class="col-summary" data-edit="summary"><div class="cell-val summary-val"></div></td><td class="col-subject" data-edit="subject"><div class="cell-val subject-val"><p></p><p></p></div></td><td class="col-debite col-dc" data-edit="money"><div class="cell-val debit-val"></div></td><td class="col-credit col-dc" data-edit="money"><div class="cell-val credit-val"></div></td><td class="col-delete"><div class="add-delete"><a class="a-delete" href="javascript:;"></a></div></td></tr>
//信息提示窗
var messagePop = function(str){
		$(".message-pop").children("p").html(str);
		$(".message-pop").animate({bottom:"30px",right:"10px",opacity:"1"},"slow",function(){
		})
		setTimeout(function(){
			$(".message-pop").animate({bottom:"-100px",right:"-250px",opacity:"0"},"slow",function(){
			})
		},2000)
}
	
$(function(){
	//TODO 放到页面上 如果是有发票信息的时候
	countVoucherTotal();//计算 借贷方金额合计

	 billinfoFunction();// 发票处操作
	 voucherSummary();//凭证摘要
	 voucherSubject();//凭证科目
	 voucherCellVal();//凭证金额
	 voucherfunction();//凭证其它方法 
	 operationFunction();//凭证操作栏
	 templateFunction();//模板操作
	//加载模板
	getVouchetTemplate(1);
	getVouchetTemplate(2);
	getVouchetTemplate(3);
	modelsetIndex();
	
	//加载科目信息
	$.ajax({
	     type: 'POST',
	     url: ctx+'/account/tAccount/getAccountByCodeOrName',
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
	
})

//模板操作-显示模板
function modelsetIndex(){
		$(".mask").show();
		$("#tab").show();
		$(".tabCont").height(function(){
			return $mainH2;
		})
	    var $container = $('.contH');
	    $container.imagesLoaded( function () {
	        $container.masonry({
	            columnWidth: '.contlist',
	            itemSelector: '.contlist'
	        });
	    });
	    $(".mask").hide();
	}

//发票处操作
function billinfoFunction(){
	//左侧点击
	$("body").on("click",".a-left",function(){
		if($(".clearfix").find("li").length > 3 && $(".billinfo-hide-right").length > 0){	
			$(".clearfix").find("li").animate({left: '-=292px'}, 500);
			$(".billinfo-show:first").removeClass("billinfo-show").addClass("billinfo-hide-left");
			$(".billinfo-hide-right:first").removeClass("billinfo-hide-right").addClass("billinfo-show");
		}
	})
	//右侧点击
	$("body").on("click",".a-right",function(){
		if($(".clearfix").find("li").length > 3 && $(".billinfo-hide-left").length > 0){	
			$(".clearfix").find("li").animate({left: '+=292px'}, 500);
			$(".billinfo-show:last").removeClass("billinfo-show").addClass("billinfo-hide-right");
			$(".billinfo-hide-left:last").removeClass("billinfo-hide-left").addClass("billinfo-show");
		}
	})
	//删除点击
	$("body").on("click",".billinfo-delete",function(e){
			var id = $(this).data("id");
			//删除发票信息后 对于显示栏的操作
			deleteBillinfo($(".billinfo-voucher-li-"+id));
			//如果没有图片  则隐藏上方图片滚动区域
			if($(".ul-pj").find("li").length < 1){
				$(".pics").hide();
			}
			//删除凭证对应的行
			$(".billinfo-voucher-tr-"+id).remove();
			deleteAllVoucer();
	})	
}

//凭证-摘要
function voucherSummary(){
	// 摘要
	$("body").on("click","td.col-summary",function(){ 
		var summaryVal = $(this).find(".cell-val").text();
		if(summaryVal.length < 1){//取上级摘要的信息
			var preTr = $(this).parent().prev();
			if(preTr.length > 0){
				summaryVal = $(preTr).find(".col-summary").find(".cell-val").text();
			}
		}
		var inpStr = $("<input type='text'  value='"+summaryVal+"'>");
		var sumInp = $(this).find("input").length;
		if($(this).find("input").length==0){
			$(this).append(inpStr);
		}else{
			$(this).find("input").val(summaryVal);
			$(this).find("input").show();
		}
		$(this).find("input").focus().val(summaryVal);
		$(this).find("input").blur(function(){
			$(this).hide();
			var inpVal = $(this).val();
			$(this).prev(".cell-val").text(inpVal);
		});
	})
}
//凭证-科目
function voucherSubject(){
	//会计科目
	var clickTd;
	$("body").on("click","td.col-subject",function(event){
		$(".cellOn").find("input").hide();//点击出科目后 点击其他科目 前一个科目的显示问题
		clickTd = $(this);
		event.stopPropagation();
		$("td.col-subject").removeClass("cellOn");
		var inpStr = $("<input class='inp-sub' type='text'>");
		var cellVal = $(this).find(".cell-val").find("p").eq(0).text();
		if($(this).find("input").length==0){
			$(this).append(inpStr);
		}else{
			$(this).find("input").val(cellVal);
			$(this).find("input").show();
		}
		//$(this).find("input").focus();
		$(this).find("input").select();
		$(this).addClass("cellOn");
		var clickTop = $(this).find(".cell-val").offset().top;
		var clickLeft = $(this).find(".cell-val").offset().left;
		//var editTop = $(".edit").offset().top;
		var addTop = clickTop+65;	
		var addLeft = clickLeft;
		var addcss = {
					top: addTop+"px",
					left: addLeft+"px"
					};
		//$(".ul-select").css(addcss);
		$(".pull-down").css(addcss);
		/*$.ajax({
	 	     type: 'POST',
	 	     url: ctx+'/account/tAccount/getAccountByCodeOrName',
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
	 	    	 result = template(data);
	 	    	$(".ul-select").html(result);
	 	    	if($(".voucher-current").hasClass("add-voucher-account") && $(".sp-add").length < 1){
	 	    		$(".pull-down").append('<span id="add-voucher-account" class="sp-add">新增科目</span>');
	 	    	}
	 	     }
		});*/
		var result = '';
    	var source = $("#accountTemplate").html();
    	var template = Handlebars.compile(source);
    	result = template(accountArr);
    	$(".ul-select").html(result);
    	if($(".voucher-current").hasClass("add-voucher-account") && $(".sp-add").length < 1){
    		$(".pull-down").append('<span id="add-voucher-account" class="sp-add">新增科目</span>');
    	}
		$(".ul-select").show();
		$(".pull-down").show();
	
	});
	
	//科目输入值变化
	var changeAjaxFlag = false;
	$("body").on("input propertychange",".inp-sub",function(){
		if(changeAjaxFlag){
			return;
		}
		changeAjaxFlag = true;
		var param = $(this).val().trim();
		var filterAccountArr = new Array();
		for(var i in accountArr){
			if(accountArr[i].accuntId.indexOf(param) == 0){
				filterAccountArr.push(accountArr[i]);
			}
		}
		
		var result = '';
	    var source = $("#accountTemplate").html();
	    var template = Handlebars.compile(source);
	    result= template(filterAccountArr);
	    $(".ul-select").html(result);
	    if($(".voucher-current").hasClass("add-voucher-account") && $(".sp-add").length < 1){
	    	$(".pull-down").append('<span id="add-voucher-account" class="sp-add">新增科目</span>');
	    }
	    $(".pull-down").show();
	    changeAjaxFlag = false;
		/*$.ajax({
	 	     type: 'POST',
	 	     url: ctx+'/account/tAccount/getAccountByCodeOrName',
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
	 	    	$(".ul-select").html(result);
	 	    	if($(".voucher-current").hasClass("add-voucher-account") && $(".sp-add").length < 1){
	 	    		$(".pull-down").append('<span id="add-voucher-account" class="sp-add">新增科目</span>');
	 	    	}
	 	    	$(".pull-down").show();
	 	    	changeAjaxFlag = false;
	 	     }
		});*/
	})

	//选择科目 处理
	$("body").on("click",".ul-select li",function(){
		var _liVal = $(this).text();
		var ifamortize = $(this).data("ifamortize");
		$(".cellOn").find("input").hide();
		$(".cellOn").find(".cell-val").find("p").eq(0).html(_liVal);
		$(".cellOn").find(".cell-val").find("p").eq(0).data("amountid",$(this).data("id"));
		$(".cellOn").find(".cell-val").find("p").eq(0).data("id",$(this).data("accuntid"));
		$(".cellOn").find(".cell-val").find("p").eq(0).data("amountdc",$(this).data("dc"));
		$(".cellOn").find(".cell-val").find("p").eq(0).data("ifamortize",ifamortize);
		$(".ul-select").hide();	
		//根据 科目的借方方向 重置 借贷的金额 同时对下方金额进行显示  代码已有 看是否需要
		var dc = $(this).data("dc");
		var str = "余额：";
		
		var amountBalance = getLastAccountEndBalance($(this).data("id")).endbalance; //TODO  取最近一期 响应余额科目表的end_balance 
		
		
		if(dc == '-1'){
			//str += "+";
			var debitVal = $(clickTd).next().find(".cell-val").addClass("no-edit").text();
			if(debitVal.length < 1){
				debitVal = $(clickTd).next().next().find(".cell-val").text();
			}
			if(debitVal.length < 1){
				debitVal = $(clickTd).data("amount");
			}else{
			//	str = "余额：-";
			}
			if(	$(clickTd).next().find(".cell-val").text().trim().length < 1){
				$(clickTd).next().next().find(".cell-val").removeClass("no-edit").text(debitVal);
				$(clickTd).next().find(".cell-val").text('');
			}
			//陈明加的
			if(amountBalance!='0'){
				amountBalance=-1*amountBalance;
				var num = new Number(amountBalance);
				amountBalance=num.toFixed(2);
			}			
		}else if(dc == '1'){
			//str += "-";
			var creditVal = $(clickTd).next().next().find(".cell-val").addClass("no-edit").text();
			if(creditVal.length < 1){
				creditVal = $(clickTd).next().find(".cell-val").text();
			}
			if(creditVal.length < 1){
				creditVal = $(clickTd).data("amount");
			}else{
			//	str = "余额：+";
			}
			if($(clickTd).next().next().find(".cell-val").text().trim().length < 1){
				$(clickTd).next().find(".cell-val").removeClass("no-edit").text(creditVal);
				$(clickTd).next().next().find(".cell-val").text('');
			}
			/*amountBalance = "-"+amountBalance*/
		}
		//显示
		var amount = ""+$(clickTd).data("amount");
		if(amount == 'undefined' || amount.length < 1){
			amount = "000";
		}
		
		var _leftVal = str+amountBalance;
		
		//TODO 待测试
		if(!$(".voucher-current").hasClass("voucher-template-dialog")){
			if(dc == '1' && ifamortize == '1' && $(".voucher-current").find(".amortize").length < 1 && $(clickTd).next().next().find(".cell-val").text().trim().length < 1){
			//if($(clickTd).next().find(".cell-val").text().length > 0 && ifamortize == '1' && $(".voucher-current").find(".amortize").length < 1){
				//分摊按钮
				_leftVal += "<a class='amortize' href='javascript:void(0)' data-amount='"+amount+"' data-amountid='"+$(this).data("id")+"'>自动分摊</a>";
			}
		}
		$(".cellOn").find(".cell-val").find("p").eq(1).html(_leftVal);
		//计算合计
		countVoucherTotal();
	})

	//在选择科目时 点击其他地方 则科目下拉隐藏
	$("body").click(function(){
		$(".pull-down").hide();
		//$(".ul-select").hide();
		$(".cellOn").find("input").hide();
		$(".ul-select-amortize").hide();
		$(".autoAmortization").find(".cellOn-amortize").removeClass("cellOn-amortize");
	})

	//科目-自动摊销
	$("body").on("click",".amortize",function(e){
		e.stopPropagation();
		var summary = 	$(this).parent().parent().parent().prev().find(".summary-val").text();
		var amortizeAccountId = $(this).parent().prev().data("amountid");
		var idFan = $(this).parent().prev().data("id");
		idFan = idFan + "";
		if(idFan.indexOf("1601") == 0){
			summary = "计提折旧";
		} else if(idFan.indexOf("1801") == 0){
			summary = "长期待摊费用摊销";
		}else if(idFan.indexOf("1701") == 0){
			summary = "无形资产摊销";
		}

		//赋值自动摊销摘要
		$(".autoAmortization").find(".ipt-summary").val(summary);
		//赋值摊销科目
		$("#autoAmortization-amortizeAccountId").val(amortizeAccountId);
		$(".ul-select-amortize").hide();
		$(".autoAmortization").show();
		$(".mask").show();
	})
	
	//科目-自动摊销-选择科目
	$("body").on("click",".ipt-subjext",function(event){
		event.stopPropagation();
		$(".autoAmortization").find(".cellOn-amortize").removeClass("cellOn-amortize");
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
		$.ajax({
	 	     type: 'POST',
	 	     url: ctx+'/account/tAccount/getAccountByCodeOrName',
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
		});
		$(".ul-select-amortize").show();
	})
	
	//科目-自动摊销-选择科目-科目输入值变化
	var changeAjaxFlagAmortize = false;
	$("body").on("input propertychange",".ipt-subjext",function(){
		if(changeAjaxFlagAmortize){
			return;
		}
		changeAjaxFlagAmortize = true;
		var param = $(this).val();
		$.ajax({
	 	     type: 'POST',
	 	     url: ctx+'/account/tAccount/getAccountByCodeOrName',
	 	     cache:false,  
	 	     async: false,
	 	     dataType:'json',
	 	     data: {
	 	    	 'param' :param.trim()
	 	     } ,
	 	     success: function(data){
	 	    	 var result = '';
	 	    	 var source = $("#accountTemplate").html();
	 	    	 var template = Handlebars.compile(source);
	 	    	 result= template(data);
	 	    	$(".ul-select-amortize").html(result);
	 	    	changeAjaxFlagAmortize = false;
	 	     }
		});
	})
	
	//科目-自动摊销-选择科目-选中
	$("body").on("click",".ul-select-amortize li",function(){
		var _liVal = $(this).text();
		var accountid = $(this).data("id")
		$(".autoAmortization").find(".cellOn-amortize").data("accountid",accountid).val(_liVal).removeClass("cellOn-amortize");
		$(".ul-select-amortize").hide();
	})
	
	//科目-自动摊销-分摊月数和残值率值变化
	$("body").on("input propertychange",".autoAmortization .ipt-monthnum ,.autoAmortization .ipt-value",function(){
		//var month = parseInt($(".autoAmortization .ipt-monthnum").val());
		var value = parseInt($(".autoAmortization .ipt-value").val());
		if(!isNaN(value)){
			var amount = toMoneyNum($(".voucher-current .amortize").data("amount")+"");
			amount = accMul(amount,value);
			$("#scrap-value").text(accDiv(amount,100));
		}
	})
	
	//科目-自动摊销-保存
	$("body").on("click",".autoAmortization-save",function(){
		var	expName = $(".autoAmortization .ipt-summary").val(); // 名字
		var	originalValue = $(".voucher-current .amortize").data("amount"); // 原值
		var	scrapValueRate = $(".autoAmortization .ipt-value").val();  // 残值率
		var	scrapValue = $(".autoAmortization #scrap-value").text(); // 残值
		var	amortizeAgeLimit = $(".autoAmortization .ipt-monthnum").val(); // 摊销年限
		if( $(".autoAmortization .ipt-debit").val().length < 1 || $(".autoAmortization .ipt-credit").val().length < 1){
			messagePop("请选择科目");
			return false;
		}
		if(amortizeAgeLimit.length < 1){
			messagePop("请输入摊销年限");
			return false;
		}
		if(scrapValueRate.length < 1){
			messagePop("请输入残值率");
			return false;
		}
		$(".voucher-current").find(".amortize").addClass("hasSave");
		$(".autoAmortization").hide();
		$(".mask").hide();
	})
	
	//科目-自动摊销-关闭
	$("body").on("click","#autoAmortization-close,#autoAmortization-cancel",function(event){
		$(".autoAmortization").hide();
		event.stopPropagation();
		return false;
	})
	
	//科目-添加-点击
	$("body").on("click","#add-voucher-account",function(e){
		//防止事件冒泡
		$(".mask").show();
		$("#subject-select-add-name").val('');
		$("#subject-select-add").show();
		getAccountToSelect();
	})
	
	//科目-添加-保存
	$("body").on("click","#subject-select-add-save",function(e){
		var name = $("#subject-select-add-name").val();
		if(name.length < 1){
			messagePop("请输入科目名称");
			return false;
		}
		var parentId = $("#subject-select-add-select").val();// $("#subject-select-add-select").find("option:selected").val(
		var ifAmortize = $("#subject-select-add-select").find("option:selected").data("ifamortize");
		$.ajax({
		     type: 'POST',
		     url: ctx+'/account/tAccount/saveAccountInVoucher',
		     cache:false,  
		     dataType:'json',
		     data: {
		    	 "accountName":name,
		    	 "id":parentId,
		    	 "ifAmortize":ifAmortize
		     },
		     success: function(data){
		    	 if(data.suc){
		    		 $(".span-tip").text("科目保存成功").show();
		    		 $("#subject-select-add").hide();
		    		 $(".mask").hide();
		    		 //显示到凭证科目
		    		 	var id = data.id;
		    		 	var accountid = data.accountid;
		    		 	var dc = data.dc;
		    		 	var ifamortize = data.ifAmortize;
		    		 	var _liVal = data.text;
		    		 	
		    			$(".cellOn").find("input").hide();
		    			$(".cellOn").find(".cell-val").find("p").eq(0).html(_liVal);
		    			$(".cellOn").find(".cell-val").find("p").eq(0).data("amountid",id);
		    			$(".cellOn").find(".cell-val").find("p").eq(0).data("id",accountid);
		    			$(".cellOn").find(".cell-val").find("p").eq(0).data("amountdc",dc);
		    			$(".cellOn").find(".cell-val").find("p").eq(0).data("ifamortize",ifamortize);
		    			$(".ul-select").hide();	
		    			
		    			//根据 科目的借方方向 重置 借贷的金额 同时对下方金额进行显示  代码已有 看是否需要
		    			var str = "余额：";
		    			var amountBalance = 0.00;
		    			if(dc == '-1'){
		    				var debitVal = $(clickTd).next().find(".cell-val").addClass("no-edit").text();
		    				if(debitVal.length < 1){
		    					debitVal = $(clickTd).next().next().find(".cell-val").text();
		    				}
		    				if(debitVal.length < 1){
		    					debitVal = $(clickTd).data("amount");
		    				}
		    				if(	$(clickTd).next().find(".cell-val").text().trim().length < 1){
		    					$(clickTd).next().next().find(".cell-val").removeClass("no-edit").text(debitVal);
		    					$(clickTd).next().find(".cell-val").text('');
		    				}
		    			}else if(dc == '1'){
		    				var creditVal = $(clickTd).next().next().find(".cell-val").addClass("no-edit").text();
		    				if(creditVal.length < 1){
		    					creditVal = $(clickTd).next().find(".cell-val").text();
		    				}
		    				if(creditVal.length < 1){
		    					creditVal = $(clickTd).data("amount");
		    				}
		    				if($(clickTd).next().next().find(".cell-val").text().trim().length < 1){
		    					$(clickTd).next().find(".cell-val").removeClass("no-edit").text(creditVal);
		    					$(clickTd).next().next().find(".cell-val").text('');
		    				}
		    				amountBalance = "-"+amountBalance
		    			}
		    			//显示
		    			var amount ="000";
				    	var _leftVal = str+amountBalance;
		    			
		    			//TODO 待测试
		    			if(!$(".voucher-current").hasClass("voucher-template-dialog")){
		    				if(dc == '1' && ifamortize == '1' && $(".voucher-current").find(".amortize").length < 1 && $(clickTd).next().next().find(".cell-val").text().trim().length < 1){
		    					 //分摊按钮
		    					_leftVal += "<a class='amortize' href='javascript:void(0)' data-amount='"+amount+"' data-amountid='"+$(this).data("id")+"'>自动分摊</a>";
		    				}
		    			}
		    			$(".cellOn").find(".cell-val").find("p").eq(1).html(_leftVal);
		    			//计算合计
		    			countVoucherTotal();
		    			//加载科目信息
		    			$.ajax({
		    			     type: 'POST',
		    			     url: ctx+'/account/tAccount/getAccountByCodeOrName',
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
		    	 }else{
		    		 $(".span-tip").text("科目保存失败").show();
		    	 }
		    	 msgFadeOut();
		     }
		});
	})
	
	//科目-添加-取消
	$("body").on("click","#subject-select-add-cancel,#subject-select-add-close",function(){
		$(".mask").hide();
		$("#subject-select-add").hide();
	})
	
	
	//凭证保存-有自动摊销科目-没设置自动摊销-确认
	$("body").on("click","#newPop-save",function(){
		
		$("#newPop").hide();
		$(".mask").hide();
		
		 //判断当前的表单是否已提交 提交过的不允许再提交
		 if($(".voucher-current").hasClass("voucher_has_save")){
			 messagePop("当前凭证已保存");
			 return false;
		 }
		//合计金额
		 var totalAmount = $(".voucher-current").find(".debit-total").text();
		 totalAmount =  toMoneyNum(totalAmount);//小数点处理
		 var creditTotal =  $(".voucher-current").find(".credit-total").text();
		 creditTotal =  toMoneyNum(creditTotal);//小数点处理
		 if(totalAmount != creditTotal){
			 messagePop("录入借贷不平");
			 return false;
		 }
		 if(totalAmount <= 0){
			 messagePop("请录入借贷方金额");
			 return false;
		 }
		//凭证字号
		var voucherTitleNumber = $(".voucher-current").find(".ui-spinbox").val();
		//记账日期 TODO后台赋值
		var accountDate = $(".voucher-current").find(".ui-datepicker-input").val();
		//单据数
		var length = $(".voucher-current").find(".vou-right").children("input").val();		
		if(isNaN(length)){
			length = 0;
		}		
		var recieptNumber = length;
		//制单人
		var userName = $(".voucher-current").find(".make-person-name").text();
		
		var voucherExpArr = new Array()
		//凭证摘要
		var flag = false;
		var flagAccount = false;
		var noinputaccountName;
		var validTr = 0;
		$(".voucher-current").find(".entry-item").each(function(i){
			//摘要
			var exp = $(this).find(".summary-val").text();
			if(exp.length > 0){
				var voucherExp = {};
				voucherExp.exp = exp;
				noinputaccountName = $(this).find(".subject-val").find("p").eq(0);
				//var accountaaa = $(this).find(".subject-val").text();
				if($(noinputaccountName).text().length < 1 && !flagAccount){
					flagAccount = true;
				}
				voucherExp.accountName =  $(noinputaccountName).text();
				voucherExp.accountId =  $(this).find(".subject-val").find("p").eq(0).data("amountid");
				
				var debit = parseFloat($(this).find(".debit-val").text());
				
				if(debit > 0 && !isNaN(debit)){
					debit =  toMoneyNum(debit+"");//小数点处理
				}else{
					debit = 0;
				}	
				voucherExp.debit =  debit;
				var credit = parseFloat($(this).find(".credit-val").text());
				if(credit > 0 && !isNaN(credit)){
					credit =  toMoneyNum(credit+"");//小数点处理
				}else{
					credit = 0;
				}
				voucherExp.credit =  credit;
				if($(this).find(".amortize").hasClass("hasSave")){
					voucherExp.amortize =  true;
				}
				voucherExpArr.push(voucherExp);
				validTr++;
			}else{
				if(0 == i ){
					flag = true;
					return false;
				}
			}
		})
		
		if(flag){
			messagePop("第1条分录摘要不能为空！");
			return false;
		}
		
		if(flagAccount){ //摘要未填 未填处于点击状态
			$(noinputaccountName).click();
			return false;
		}
		
		if(validTr < 2){
			messagePop("请录入至少二条有效分录！");
			return false;
		}
		
		var billInfoStr='';

		$(".billinfo-delete").each(function(){ 
			billInfoStr = billInfoStr+ $(this).data("id")+","
		});
		//保存前信息验证 TODO
		var  expName;		// 名字
		var  enterAccountDate;		// 入账日期
		var  originalValue;		// 原值
		var  scrapValueRate;		// 残值率
		var  scrapValue;		// 残值
		var  amortizeAgeLimit;		// 摊销年限
		var debitAccountId;//借-科目id
		var creditAccountId;//贷-科目id
		var amortizeAccountId;//摊销科目id
		//设置自动摊销
		if($(".voucher-current").find(".amortize").hasClass("hasSave")){
			expName = $(".autoAmortization .ipt-summary").val();
			//originalValue = $(".voucher-current .amortize").data("amount");
			originalValue = toMoneyNum($(".voucher-current .amortize").data("amount")+"");
			scrapValueRate = $(".autoAmortization .ipt-value").val();
			scrapValue = $(".autoAmortization #scrap-value").text();
			amortizeAgeLimit = $(".autoAmortization .ipt-monthnum").val();
			debitAccountId = $(".autoAmortization .ipt-debit").data("accountid");
			creditAccountId = $(".autoAmortization .ipt-credit").data("accountid");
			amortizeAccountId = $("#autoAmortization-amortizeAccountId").val();
		}
		
		$.ajax({
		     type: 'POST',
		     url: ctx+'/voucher/tVoucher/saveVoucher',
		     cache:false,  
		     dataType:'json',
		     data: {
		    	 'voucherTitleNumber':voucherTitleNumber,
		    	 'accountDate' :accountDate,
		    	 'recieptNumber':recieptNumber,
		    	 'totalAmount':totalAmount,
		    	 'userName':userName,
		    	'voucherExpStr':JSON.stringify(voucherExpArr),
		    	'billinfoid':billInfoStr,
		    	'expName':expName,	
		 	    'enterAccountDate':	accountDate	,
		 	    'originalValue':originalValue,		
		 	    'scrapValueRate':scrapValueRate	,
		 	    'scrapValue':scrapValue,
		 	    'amortizeAgeLimit':	amortizeAgeLimit,
		 	    'debitAccountId':debitAccountId,
				'creditAccountId' :creditAccountId,
				'amortizeAccountId':amortizeAccountId
		     } ,
		     success: function(data){
		    	if(data.suc){
		    		//给当前凭证保存标记 和 凭证id
		    		$(".voucher-current").addClass("voucher_has_save").data("id",data.id);
		    		//保存成功后 保存按钮隐藏 新增按钮显示
		    		$(".a-save").hide();
		    		$(".add-voucher").show();
		    		$(".span-tip").text("录入凭证成功").show();
		    		//自动摊销其他内容清空
		    		$(".autoAmortization .ipt-value").val('');
		    		$(".autoAmortization #scrap-value").text('');
		    		$(".autoAmortization .ipt-monthnum").val('');
		    		$(".autoAmortization .ipt-debit").val('');
		    		$(".autoAmortization .ipt-credit").val('');
		    		/*$(".add-voucher").click();*/
		    		/*新增一个发票*/
		    		$(".add-voucher").hide();
		    		$(".a-save").show(); 
		    		$(".tb").removeClass("voucher-current").hide();
		    		$(".voucher-tab").append($("#template-voucher").html());
		    		$(".voucher-tab .tb:last").addClass("voucher-current add-voucher-account").show();
		    		var str = "";
		    		for (var i = 0;i<4;i++){
		    			str +='<tr class="entry-item "><td class="col-operate"><div class="add-delete"><a class="a-add" href="javascript:;"></a></div></td><td class="col-summary" data-edit="summary"><div class="cell-val summary-val"></div></td><td class="col-subject" data-edit="subject"><div class="cell-val subject-val"><p></p><p></p></div></td><td class="col-debite col-dc" data-edit="money"><div class="cell-val debit-val"></div></td><td class="col-credit col-dc" data-edit="money"><div class="cell-val credit-val"></div></td><td class="col-delete"><div class="add-delete"><a class="a-delete" href="javascript:;"></a></div></td></tr>';	    
		    		}
		    		$(".voucher-current").find(".tbody-voucher").html(str);
		    		//图片展览隐藏
		    		$(".pics").hide();
		    		getRememberWord();
		    		
		    	}else{
		    		//保存失败提示
		    		$(".span-tip").text("录入凭证失败").show();
		    	}
		    	msgFadeOut();
		     }

		});
	})
	
	//凭证保存-有自动摊销科目-没设置自动摊销-取消
	$("body").on("click","#newPop-cancel",function(){
		$("#newPop").hide();
		$(".mask").hide();
	})
}

//凭证-金额处理
function voucherCellVal(){
	// 借方贷方金额
	$("body").on("click",".col-dc",function(){
		/*if($(this).find(".cell-val").hasClass("no-edit")){
			return false;
		}*/
		var dcVal = $(this).find(".cell-val").text();
		
		//陈明加的
		var  dc=$(this).parents("tr").find(".col-subject").find(".cell-val").find("p").eq(0).data("amountdc");
		
		dcVal = toMoneyNum(dcVal);
		var inpStr = $("<input type='text' value='"+dcVal+"' onkeyup='clearNoNum(this)'>");
		if($(this).find("input").length==0){
			$(this).append(inpStr);
		}else{
			$(this).find("input").val(dcVal);
			$(this).find("input").show();
		}
		$(this).find("input").select();
		$(this).find("input").blur(function(){
			$(this).hide();
			var inpVal = $(this).val();
			//TODO 符号处理
			//先保留两位小数 在转换为字符串
			inpVal = roundNum(inpVal,2);
			var floatVal = inpVal;
			inpVal = toStr(inpVal+"");
			if(isNaN(inpVal)){
				$(this).prev(".cell-val").text('');
			}else{
				
				if(floatVal != "0.00"){				
					$(this).prev(".cell-val").text(inpVal);
					if(inpVal.length > 0){
						//另一侧数据清空
						if($(this).prev(".cell-val").hasClass("debit-val")){
							$(this).parent().next().find(".cell-val").text('');
						}else if($(this).prev(".cell-val").hasClass("credit-val")){
							$(this).parent().prev().find(".cell-val").text('');
						}
					}
				}
			}
			//计算合计
			countVoucherTotal();
			//TODO  计算科目下的余额
			var str = "";
			if($(this).prev(".cell-val").hasClass("debit-val")){
				if($(this).parent().prev().find("p").eq(0).text().trim().length > 0){
//					str += "-" + toMoneyNum($(this).prev(".cell-val").text());TODO
					str += $(this).parent().prev().find("p").eq(1).text();
					var values = str.split("：");
					getLastAccountEndBalance($(this).parent().prev().find("p").eq(0).data("amountid"));
					/*var val = (balance.debittotalamount * 1 + floatVal * 1) + ( balance.beginbalance * 1) - ( balance.credittotalamount * 1);*/
					//陈明加的
					if(dc == '1'){
						val =balance.endbalance*1+floatVal*1;
					}else if(dc == '-1'){
						var end=balance.endbalance;
						val =-1*end-floatVal*1
					}
					str = values[0]+"："+ roundNum(val,2);
					if(!$(".voucher-current").hasClass("voucher-template-dialog")){
						if($(this).parent().prev().find(".amortize").length > 0){
							str +="<a class='amortize' href='javascript:void(0)' data-amount='"+$(this).prev(".cell-val").text()+"' data-amountid='"+$(".voucher-current .amortize").data("id")+"'>自动分摊</a>";
						}
					}
					if($(this).parent().prev().find("p").eq(0).text().length > 0){
						$(this).parent().prev().data("amount",$(this).prev(".cell-val").text());
						if(floatVal != "0.00"){
							$(this).parent().prev().find("p").eq(1).html(str);
						}
					}
				}
			}else if($(this).prev(".cell-val").hasClass("credit-val")){
				var balanceStr =  $(this).parent().prev().prev().find("p").eq(1).text();
				var values = balanceStr.split("：");
				/*var val = (balance.debittotalamount * 1 )+ ( balance.beginbalance * 1) -( balance.credittotalamount * 1 + floatVal * 1);*/
				//陈明加的
				if(dc == '1'){
					val =balance.endbalance*1-floatVal*1;
				}else if(dc == '-1'){
					var end=balance.endbalance;
					val =-1*end + floatVal*1
				}
				var val = (balance.debittotalamount * 1 )+ ( balance.beginbalance * 1) -( balance.credittotalamount * 1 + floatVal * 1);
				balanceStr = values[0]+"："+ roundNum(val,2);
				if($(this).parent().prev().prev().find("p").eq(0).text().trim().length > 0){
//					str += "+" + toMoneyNum($(this).prev(".cell-val").text());
					if($(this).parent().prev().prev().find("p").eq(0).text().length > 0){
						$(this).parent().prev().prev().data("amount",$(this).prev(".cell-val").text());
						if(floatVal != "0.00"){
							$(this).parent().prev().prev().find("p").eq(1).html(balanceStr);
						}
					}
				}
			}
			
		});
	})
}

//凭证处的方法 
function voucherfunction(){
	//凭证-添加 点击
	$("body").on("click",".a-add",function(){
		$(this).parent().parent().parent().before(voucherTr);
	})
	//点击删除
	$("body").on("click",".a-delete",function(e){
		var id = $(this).parent().parent().parent().data("id");
		$(this).parent().parent().parent().remove();
		//对合计进行重新计算
		countVoucherTotal();
		//TODO判断是否要删除发票信息
		if(id != undefined && $(".billinfo-voucher-tr-"+id).length < 1){
			$(".billinfo-voucher-li-"+id).find(".billinfo-delete").click();
		}
	});
	//鼠标移入移出-控制"添加"、"删除" 显示
	$("body").on("mouseover",".tab-voucher tr:gt(0)",function(){
		$(this).children(".col-operate").addClass("on");
		$(this).children(".col-delete").addClass("on");
	    $(this).children(".col-option").find(".option").css("visibility","visible");
	})
	//鼠标移入移出-控制"添加"、"删除" 隐藏
	$("body").on("mouseleave",".tab-voucher tr:gt(0)",function(){
		$(this).children(".col-operate").removeClass("on");
		$(this).children(".col-delete").removeClass("on");
	    $(this).children(".col-option").find(".option").css("visibility","hidden");
	})
}

//凭证-操作栏
function operationFunction(){
	//操作栏-保存
	$("body").on("click",".a-save",function(){
		saveVoucher();
	})
	//操作栏-新增
	$("body").on("click",".add-voucher",function(){
		$(".add-voucher").hide();
		$(".a-save").show(); 
		
		$(".tb").removeClass("voucher-current").hide();
		$(".voucher-tab").append($("#template-voucher").html());
		$(".voucher-tab .tb:last").addClass("voucher-current").show();
		var str = "";
		for (var i = 0;i<4;i++){
			str +='<tr class="entry-item "><td class="col-operate"><div class="add-delete"><a class="a-add" href="javascript:;"></a></div></td><td class="col-summary" data-edit="summary"><div class="cell-val summary-val"></div></td><td class="col-subject" data-edit="subject"><div class="cell-val subject-val"><p></p><p></p></div></td><td class="col-debite col-dc" data-edit="money"><div class="cell-val debit-val"></div></td><td class="col-credit col-dc" data-edit="money"><div class="cell-val credit-val"></div></td><td class="col-delete"><div class="add-delete"><a class="a-delete" href="javascript:;"></a></div></td></tr>';	    
		}
		$(".voucher-current").find(".tbody-voucher").html(str);
		//图片展览隐藏
		$(".pics").hide();
		getRememberWord();
	})
	
	//操作栏-右侧
	$("body").on("click",".scrollright",function(){
		if($(".voucher-current").next().length > 0){
			$(".voucher-current").next().addClass("voucher-current").show().prev().removeClass("voucher-current").hide();
		}
		//控制操作栏按钮的显示
		if($(".voucher-current").hasClass("voucher_has_save")){
			$(".add-voucher").show();
			$(".a-save").hide();
		}else{
			$(".add-voucher").hide();
			$(".a-save").show();
		}
		//控制发票信息显隐
		if($(".voucher-current").hasClass("hasBillInfo")){
			$(".pics").show();
		}
	})
	
	//操作栏-左侧
	$("body").on("click",".scrollleft",function(){
		if($(".voucher-current").prev().length > 0){
			$(".voucher-current").prev().addClass("voucher-current").show().next().removeClass("voucher-current").hide();
		}
		//控制操作栏按钮的显示
		if($(".voucher-current").hasClass("voucher_has_save")){
			$(".add-voucher").show();
			$(".a-save").hide();
		}else{
			$(".add-voucher").hide();
			$(".a-save").show();
		}
		//控制发票信息显隐
		if($(".voucher-current").hasClass("hasBillInfo")){
			$(".pics").show();
		}
	})
}

//模板操作
function templateFunction(){
	
	//模板操作-保存模板
	$("body").on("click","#save-voucher-template",function(event){
		event.stopPropagation();
		var data = saveVoucherCheck();
		if(!data.suc){
			return false;
		}
		$("#save-template-input").val('');
		$("#save-template-checkbox").attr("checked",false);
		$("#save-template").show();
	})
	
	//模板操作-保存模板-保存
	$("body").on("click",".save-template-save",function(){
		var data = saveVoucherCheck();
		if(!data.suc){
			return false;
		}
		var templateName = $("#save-template-input").val();
		if(templateName.length < 1){
			messagePop("请输入模板名称");
			return false;
		}
		data.templateName = templateName;
		data.saveCredit = $("#save-template-checkbox").is(':checked') ;
		//保存模板
		$.ajax({
		     type: 'POST',
		     url: ctx+'/vouchertemplate/tVoucherTemplate/saveVoucherTemplate',
		     cache:false,  
		     dataType:'json',
		     data: data,
		     success: function(data){
		    	 $(".bill-info-close-dialog").click();
		    	 if(data.suc){
		    		 $(".span-tip").text("保存模板成功").show();
		    		 getVouchetTemplate(1);
		    	 }else{
		    		 $(".span-tip").text("保存模板失败").show();
		    	 }
		    	 msgFadeOut();
		     }
		});
		
	})
	
	//模板操作-使用模板
	$("body").on("click",".usetemplate",function(e){
		var id = $(this).parent().parent().data("id");
		window.location.href= ctx+'/vouchertemplate/tVoucherTemplate/tempateToVoucher?id='+id;
	})
	
	//模板操作-模板导入-新建
	$("body").on("click",".jiahao",function(){
		$(".mask").show();
		$("#voucher-template-id").val('');
		$(".tbvoucher").find("#new-save-template-input").val('');
		
		$(".voucher-current").addClass("to-new-voucher-template-current").removeClass("voucher-current");
		var str = "";
		var i = 0;
		for (;i<2;i++){
			str +='<tr class="entry-item "><td class="col-operate"><div class="add-delete"><a class="a-add" href="javascript:;"></a></div></td><td class="col-summary" data-edit="summary"><div class="cell-val summary-val"></div></td><td class="col-subject" data-edit="subject"><div class="cell-val subject-val"><p></p>';
			str +='<p></p>';
			str +='</div></td><td class="col-debite col-dc" data-edit="money"><div class="cell-val debit-val"></div></td><td class="col-credit col-dc" data-edit="money"><div class="cell-val credit-val"></div></td><td class="col-delete"><div class="add-delete"><a class="a-delete" href="javascript:;"></a></div></td></tr>';	    
		}
		$(".tbvoucher").find(".h6-kr").html("新建模板<i class='bill-info-close-dialog'></i>");
		$(".tbvoucher").find(".tbody-voucher").html(str);
		$(".tbvoucher").addClass("voucher-current").show();
		countVoucherTotal();
		var mar = ($(window).height() - $(".tbvoucher").height())/2;
		$(".tbvoucher").css("margin-top",mar);
	})
	
	//模板操作-模板导入-新建-保存
	$("body").on("click",".savevoucher",function(){
		var data = saveVoucherCheck();
		if(!data.suc){
			return false;
		}
		var templateName = $("#new-save-template-input").val();
		if(templateName.length < 1){
			$(".span-tip").text("请输入模板名称").show();
			return false;
		}
		data.templateName = templateName;
		data.saveCredit = true ;
		data.voucherTemplateId = $("#voucher-template-id").val();
		//保存模板
		$.ajax({
		     type: 'POST',
		     url: ctx+'/vouchertemplate/tVoucherTemplate/saveVoucherTemplate',
		     cache:false,  
		     dataType:'json',
		     data: data,
		     success: function(data){
		    	 if(data.suc){
		    		 $(".tbvoucher").removeClass("voucher-current").hide();
			    	 $(".to-new-voucher-template-current").addClass("voucher-current").removeClass("to-new-voucher-template-current");
			    	 getVouchetTemplate(1);
			    	 $(".span-tip").text("保存模板成功").show();
			    	 $(".mask").hide();
		    	 }else{
		    		 $(".span-tip").text("保存模板失败").show();
		    	 }
		    	 msgFadeOut();
		     }
		});
		
	})
	
	//模板操作-模板导入-删除
	$("body").on("click",".contH .emclose",function(e){
		$("#delete-template-id").val($(this).parent().parent().data("id"));
		//$(".mask").show();
		$(".reminder").show();	
	});
	
	//模板操作-模板导入-删除-删除确认
	$("body").on("click",".delete-template-save",function(){
		$.ajax({
		     type: 'POST',
		     url: ctx+'/vouchertemplate/tVoucherTemplate/deleteVoucherTemplate',
		     cache:false,  
		     dataType:'json',
		     data: {
		    	 "id":$("#delete-template-id").val()
		     } ,
		     success: function(data){
		    	$(".reminder").hide();	
		    	if(data.suc){	    	
		    		getVouchetTemplate(1);
		    		$(".span-tip").text("删除模板成功").show();
		    		messagePop("删除模板成功");
		    	}else{
		    		$(".span-tip").text("删除模板失败").show();
		    		messagePop("删除模板失败");
		    	}
		    	msgFadeOut();
		     }
		});
	});
	
	//模板操作-模板导入-编辑
	$("body").on("click",".contH .iwrite",function(e){
		$("#voucher-template-id").val($(this).parent().parent().data("id"));
		//获取模板信息  更新表格内容
		$.ajax({
		     type: 'POST',
		     url: ctx+'/vouchertemplateexp/tVoucherTemplateExp/getByTemplateId',
		     cache:false,  
		     dataType:'json',
		     data: {
		    	'id':$(this).parent().parent().data("id")
		     } ,
		     success: function(result){
		    	var voucherTemplate = result.voucherTemplate;
		    	var data = result.voucherTemplateExps;
		    	
		 		$(".voucher-current").addClass("to-new-voucher-template-current").removeClass("voucher-current");
		 		$(".tbvoucher").find(".h6-kr").html("编辑模板<i class='bill-info-close-dialog'></i>");
		    	var str = "";
		    	var i = 0;
		    	for( i in data){
		    		str +='<tr class="entry-item "><td class="col-operate"><div class="add-delete"><a class="a-add" href="javascript:;"></a></div></td><td class="col-summary" data-edit="summary"><div class="cell-val summary-val">'+data[i].exp+'</div></td><td class="col-subject" data-edit="subject"  data-amount="'+data[i].debitStr+data[i].creditStr+'"><div class="cell-val subject-val"><p data-amountid="'+data[i].accountId+'" data-amountdc="'+data[i].dc+'">'+data[i].accountName+'</p>';
		    		var msg ='余额:';
		    		//TODO 根据科目 类型判断是否显示自动分摊
		    		/*if(data[i].creditStr != null &&  data[i].creditStr.length > 2){
		    			msg += "+"+toMoneyNum(data[i].creditStr);
		    		}else if(data[i].debitStr != null && data[i].debitStr.length > 2){
		    			msg += "-"+toMoneyNum(data[i].debitStr);
		    		}*/
		    		msg += getLastAccountEndBalance(data[i].accountId).endbalance;
		    		/*str +='<p>'+msg+'</p>';*/
		    		//str +='<p></p>';
		    		str +='</div></td><td class="col-debite col-dc" data-edit="money"><div class="cell-val debit-val">'+data[i].debitStr+'</div></td><td class="col-credit col-dc" data-edit="money"><div class="cell-val credit-val">'+data[i].creditStr+'</div></td><td class="col-delete"><div class="add-delete"><a class="a-delete" href="javascript:;"></a></div></td></tr>';
		    	}
		    	/*if(i < 3){
		    		for (;i<3;i++){
		    			str +='<tr class="entry-item "><td class="col-operate"><div class="add-delete"><a class="a-add" href="javascript:;"></a></div></td><td class="col-summary" data-edit="summary"><div class="cell-val summary-val"></div></td><td class="col-subject" data-edit="subject"><div class="cell-val subject-val"><p></p><p></p></div></td><td class="col-debite col-dc" data-edit="money"><div class="cell-val debit-val"></div></td><td class="col-credit col-dc" data-edit="money"><div class="cell-val credit-val"></div></td><td class="col-delete"><div class="add-delete"><a class="a-delete" href="javascript:;"></a></div></td></tr>';	    
		    		}
		    	}*/
		    	$(".tbvoucher").find("#new-save-template-input").val(voucherTemplate.templateName);
		    	$(".tbvoucher").find(".tbody-voucher").html(str);
		 		$(".tbvoucher").addClass("voucher-current").show();
		 		countVoucherTotal();
		 		$(".mask").show();
		     }
		});
	});
	
}


///////////////////////////////////////////工具方法////////////////////////////////////////////////////////////
function deleteBillinfo(obj){	
	if($(".billinfo-hide-right").length > 0){
		$(".billinfo-hide-right:first").removeClass("billinfo-hide-right").addClass("billinfo-show");
		//$(obj).nextAll(".billinfo-show").animate({left: '-=292px'}, 500);
	}else if($(".billinfo-hide-left").length > 0){
		$(".billinfo-hide-left:last").removeClass("billinfo-hide-left").addClass("billinfo-show");
		//$(obj).prevAll(".billinfo-show").animate({left: '+=292px'}, 500);
		$(".clearfix").find("li").animate({left: '+=292px'}, 500);
	}
	$(obj).remove();
}

//如果凭证录入条目被删除完毕则追加一行
function deleteAllVoucer(){
	//都删除完了 则追加一行
	if($(".voucher-current").find(".entry-item").length < 1){
		$(".voucher-current").find(".tbody-voucher").append(voucherTr);
	}
};

//计算合计
function countVoucherTotal(){
	//借方
	var debitTotal = 0;
	$(".voucher-current").find(".debit-val").each(function(e){
		if(!isNaN(parseFloat($(this).text()))){
			debitTotal = debitTotal + parseFloat($(this).text());
		}
	});
	$(".voucher-current").find(".debit-total").text(debitTotal);
	//贷方
	var creditTotal = 0;
	$(".voucher-current").find(".credit-val").each(function(e){
		if(!isNaN(parseFloat($(this).text()))){
			creditTotal = creditTotal + parseFloat($(this).text());
		}
	});
	$(".voucher-current").find(".credit-total").text(creditTotal);
	//如果相等  金额大写
	if(debitTotal == creditTotal){
		var cny = numToCny(toMoneyNum(creditTotal+""));
		$(".voucher-current").find("#capAmount").text(cny);
	}
}

/**
 * 数字字符串  转换为带两位小数的数据
 * @param str
 * @returns {String}
 */
function toMoneyNum(str){
	if(str.length > 0){
		return str.substr(0,str.length-2) + "." +str.substr(str.length-2,str.length) ;
	}else{
		return "";
	}
}

//数据转换为字符串
function toStr(str){
	return str.replace(".","");
}

//小数保留处理
function roundNum(number,fractionDigits){  
    with(Math){  
    	var result = round(number*pow(10,fractionDigits))/pow(10,fractionDigits);  
        return result.toFixed(2);
    	//return  round(number*pow(10,fractionDigits))/pow(10,fractionDigits); 
    }  
}  
//只能输入两位小数
function clearNoNum(obj)  
{  
	if(obj.value == '='){		
		var inpVal = 0;
		var debitTotal = 0;
		$(".voucher-current").find(".debit-val").each(function(e){
			if(!isNaN(parseFloat($(this).text()))  && $(this) != $(obj).parent().find(".cell-val")){
				debitTotal = debitTotal + parseFloat($(this).text());
			}
		});
		var creditTotal = 0;
		$(".voucher-current").find(".credit-val").each(function(e){
			if(!isNaN(parseFloat($(this).text()))){
				creditTotal = creditTotal + parseFloat($(this).text());
			}
		});
		var val=parseFloat($(obj).parent().find(".cell-val").text());
		if(isNaN(val)){
			val=0;
		}
		if($(this).parent().hasClass("col-debite")){
			inpVal =  toMoneyNum((creditTotal - debitTotal +  val )+"");
		}else{
			inpVal = toMoneyNum((debitTotal - creditTotal + val )+"");
		}
		obj.value = inpVal;
	}
   obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
   obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是.  
   obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
   obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
   obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数  
}  

//乘法函数，用来得到精确的乘法结果
//说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
//调用：accMul(arg1,arg2)
//返回值：arg1乘以 arg2的精确结果
function accMul(arg1,arg2){
	var m=0,s1=arg1.toString(),s2=arg2.toString();
	try{m+=s1.split(".")[1].length}catch(e){}
	try{m+=s2.split(".")[1].length}catch(e){}
	return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}
//除法函数，用来得到精确的除法结果
//说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
//调用：accDiv(arg1,arg2)
//返回值：arg1除以arg2的精确结果
function accDiv(arg1,arg2){
	var t1=0,t2=0,r1,r2;
	try{t1=arg1.toString().split(".")[1].length}catch(e){}
	try{t2=arg2.toString().split(".")[1].length}catch(e){}
	with(Math){
		r1=Number(arg1.toString().replace(".",""))
		r2=Number(arg2.toString().replace(".",""))
		return (r1/r2)*pow(10,t2-t1);
	}
}
////////////////////////////////////////数据处理/////////////////////////////////////////
//信息保存
function saveVoucher(){
	 //判断当前的表单是否已提交 提交过的不允许再提交
	 if($(".voucher-current").hasClass("voucher_has_save")){
		 messagePop("当前凭证已保存");
		 return false;
	 }
	//合计金额
	 var totalAmount = $(".voucher-current").find(".debit-total").text();
	 totalAmount =  toMoneyNum(totalAmount);//小数点处理
	 var creditTotal =  $(".voucher-current").find(".credit-total").text();
	 creditTotal =  toMoneyNum(creditTotal);//小数点处理
	 if(totalAmount != creditTotal){
		 messagePop("录入借贷不平");
		 return false;
	 }
	 if(totalAmount <= 0){
		 messagePop("请录入借贷方金额");
		 return false;
	 }
	 
	 //凭证信息
	//凭证字名称
	//var voucherTitleName =   $(".voucher-current").find(".input-txt").val(); TODO 删除的字段
	 
	//凭证字号
	var voucherTitleNumber = $(".voucher-current").find(".ui-spinbox").val();
	
	//记账日期 TODO后台赋值
	var accountDate = $(".voucher-current").find(".ui-datepicker-input").val();
	
	//账期  当前的年和月 TODO 后台赋值
	//var accountPeriod = voucherDataForPage.currentYear+""+ voucherDataForPage.currentMonth; //后台传递 TODO
	
	//单据数
	//var length =$(".ul-pj").find("li").length;
	var length = $(".voucher-current").find(".vou-right").children("input").val();
	
	if(isNaN(length)){
		length = 0;
	}	
	
	var recieptNumber = length;
	//制单人
	var userName = $(".voucher-current").find(".make-person-name").text();
	
	var voucherExpArr = new Array()
	//凭证摘要
	var flag = false;
	var flagAccount = false;
	var noinputaccountName;
	var validTr = 0;
	$(".voucher-current").find(".entry-item").each(function(i){
		//摘要
		var exp = $(this).find(".summary-val").text();
		if(exp.length > 0){
			var voucherExp = {};
			voucherExp.exp = exp;
			noinputaccountName = $(this).find(".subject-val").find("p").eq(0);
			//var accountaaa = $(this).find(".subject-val").text();
			if($(noinputaccountName).text().length < 1 && !flagAccount){
				flagAccount = true;
			}
			voucherExp.accountName =  $(noinputaccountName).text();
			voucherExp.accountId =  $(this).find(".subject-val").find("p").eq(0).data("amountid");
			
			var debit = parseFloat($(this).find(".debit-val").text());
			
			if(debit > 0 && !isNaN(debit)){
				debit =  toMoneyNum(debit+"");//小数点处理
				/*var firstInt = debit.substr(0,debit.length -2); 
				var secondFloat = debit.substr(debit.length -2,debit.length);
				debit = firstInt+"."+secondFloat;*/
			}else{
				debit = 0;
			}	
			voucherExp.debit =  debit;
			var credit = parseFloat($(this).find(".credit-val").text());
			if(credit > 0 && !isNaN(credit)){
				credit =  toMoneyNum(credit+"");//小数点处理
				/*var firstInt = credit.substr(0,credit.length -2); 
				var secondFloat = credit.substr(credit.length -2,credit.length);
				credit = firstInt+"."+secondFloat;*/
			}else{
				credit = 0;
			}
			voucherExp.credit =  credit;
			if($(this).find(".amortize").hasClass("hasSave")){
				voucherExp.amortize =  true;
			}
			voucherExpArr.push(voucherExp);
			validTr++;
		}else{
			if(0 == i ){
				flag = true;
				return false;
			}
		}
	})
	
	if(flag){
		messagePop("第1条分录摘要不能为空！");
		return false;
	}
	
	if(flagAccount){ //摘要未填 未填处于点击状态
		$(noinputaccountName).click();
		return false;
	}
	
	if(validTr < 2){
		messagePop("请录入至少二条有效分录！");
		return false;
	}
	
	var billInfoStr='';

	$(".billinfo-delete").each(function(){ 
		billInfoStr = billInfoStr+ $(this).data("id")+","
	});
	//保存前信息验证 TODO
	var  expName;		// 名字
	var  enterAccountDate;		// 入账日期
	var  originalValue;		// 原值
	var  scrapValueRate;		// 残值率
	var  scrapValue;		// 残值
	var  amortizeAgeLimit;		// 摊销年限
	var debitAccountId;//借-科目id
	var creditAccountId;//贷-科目id
	var amortizeAccountId;//摊销科目id
	//设置自动摊销
	if($(".voucher-current").find(".amortize").hasClass("hasSave")){
		expName = $(".autoAmortization .ipt-summary").val();
		//originalValue = $(".voucher-current .amortize").data("amount");
		originalValue = toMoneyNum($(".voucher-current .amortize").data("amount")+"");
		scrapValueRate = $(".autoAmortization .ipt-value").val();
		scrapValue = $(".autoAmortization #scrap-value").text();
		amortizeAgeLimit = $(".autoAmortization .ipt-monthnum").val();
		debitAccountId = $(".autoAmortization .ipt-debit").data("accountid");
		creditAccountId = $(".autoAmortization .ipt-credit").data("accountid");
		amortizeAccountId = $("#autoAmortization-amortizeAccountId").val();
	}else if($(".voucher-current").find(".amortize").length > 0){
		$("#newPop").show();//确认是否不设置摊销
		$(".mask").show();
		return false;
	}
	
	$.ajax({
	     type: 'POST',
	     url: ctx+'/voucher/tVoucher/saveVoucher',
	     cache:false,  
	     dataType:'json',
	     data: {
	    	/* 'voucherTitleName' :voucherTitleName,*/
	    	 'voucherTitleNumber':voucherTitleNumber,
	    	 'accountDate' :accountDate,
	    	/* 'accountPeriod':accountPeriod,*/
	    	 'recieptNumber':recieptNumber,
	    	 'totalAmount':totalAmount,
	    	 'userName':userName,
	    	'voucherExpStr':JSON.stringify(voucherExpArr),
	    	'billinfoid':billInfoStr,
	    	'expName':expName,	
	 	    'enterAccountDate':	accountDate	,
	 	    'originalValue':originalValue,		
	 	    'scrapValueRate':scrapValueRate	,
	 	    'scrapValue':scrapValue,
	 	    'amortizeAgeLimit':	amortizeAgeLimit,
	 	    'debitAccountId':debitAccountId,
			'creditAccountId' :creditAccountId,
			'amortizeAccountId':amortizeAccountId
	     } ,
	     success: function(data){
	    	if(data.suc){
	    		//给当前凭证保存标记 和 凭证id
	    		$(".voucher-current").addClass("voucher_has_save").data("id",data.id);
	    		//保存成功后 保存按钮隐藏 新增按钮显示
	    		$(".a-save").hide();
	    		$(".add-voucher").show();
	    		$(".span-tip").text("录入凭证成功").show();
	    		//messagePop("保存成功");
	    		
	    		//自动摊销其他内容清空
	    		$(".autoAmortization .ipt-value").val('');
	    		$(".autoAmortization #scrap-value").text('');
	    		$(".autoAmortization .ipt-monthnum").val('');
	    		$(".autoAmortization .ipt-debit").val('');
	    		$(".autoAmortization .ipt-credit").val('');
	    		/*$(".add-voucher").click();*/
	    		/*新增一个发票*/
	    		$(".add-voucher").hide();
	    		$(".a-save").show(); 
	    		$(".tb").removeClass("voucher-current").hide();
	    		$(".voucher-tab").append($("#template-voucher").html());
	    		$(".voucher-tab .tb:last").addClass("voucher-current add-voucher-account").show();
	    		var str = "";
	    		for (var i = 0;i<4;i++){
	    			str +='<tr class="entry-item "><td class="col-operate"><div class="add-delete"><a class="a-add" href="javascript:;"></a></div></td><td class="col-summary" data-edit="summary"><div class="cell-val summary-val"></div></td><td class="col-subject" data-edit="subject"><div class="cell-val subject-val"><p></p><p></p></div></td><td class="col-debite col-dc" data-edit="money"><div class="cell-val debit-val"></div></td><td class="col-credit col-dc" data-edit="money"><div class="cell-val credit-val"></div></td><td class="col-delete"><div class="add-delete"><a class="a-delete" href="javascript:;"></a></div></td></tr>';	    
	    		}
	    		$(".voucher-current").find(".tbody-voucher").html(str);
	    		//图片展览隐藏
	    		$(".pics").hide();
	    		getRememberWord();
	    		
	    	}else{
	    		//保存失败提示
	    		//messagePop("保存失败");
	    		$(".span-tip").text("录入凭证失败").show();
	    	}
	    	msgFadeOut();
	     }

	});
}

function saveVoucherCheck(){
	var resultFlag = true;
	//合计金额
	 var totalAmount = $(".voucher-current").find(".debit-total").text();
	 totalAmount =  toMoneyNum(totalAmount);//小数点处理
	 var creditTotal =  $(".voucher-current").find(".credit-total").text();
	 creditTotal =  toMoneyNum(creditTotal);//小数点处理
	 if(totalAmount != creditTotal){
		 messagePop("录入借贷不平");
		 resultFlag = false;
	 }
	 if(totalAmount <= 0){
		 messagePop("请录入借贷方金额");
		 resultFlag = false;
	 }

	//凭证字号
	var voucherTitleNumber = $(".voucher-current").find(".ui-spinbox").val();
	
	//记账日期
	var accountDate = $(".voucher-current").find(".ui-datepicker-input").val();

	//单据数
	//var length =$(".ul-pj").find("li").length;
	var length = $(".voucher-current").find(".vou-right").children("input").val();
	if(isNaN(length)){
		length = 0;
	}	
	
	var recieptNumber = length;
	//制单人
	var userName = $(".voucher-current").find(".make-person-name").text();	
	var voucherExpArr = new Array()
	//凭证摘要
	var flag = false;
	var flagAccount = false;
	var noinputaccountName;
	var validTr = 0;
	$(".voucher-current").find(".entry-item").each(function(i){
		//摘要
		var exp = $(this).find(".summary-val").text();
		if(exp.length > 0){
			var voucherExp = {};
			voucherExp.exp = exp;
			noinputaccountName = $(this).find(".subject-val").find("p").eq(0);
			if($(noinputaccountName).text().length < 1 && !flagAccount){
				flagAccount = true;
			}
			voucherExp.accountName =  $(noinputaccountName).text();
			voucherExp.accountId =  $(this).find(".subject-val").find("p").eq(0).data("amountid");
			
			var debit = parseFloat($(this).find(".debit-val").text());
			
			if(debit > 0 && !isNaN(debit)){
				debit =  toMoneyNum(debit+"");//小数点处理
			}else{
				debit = 0;
			}	
			voucherExp.debit =  debit;
			var credit = parseFloat($(this).find(".credit-val").text());
			if(credit > 0 && !isNaN(credit)){
				credit =  toMoneyNum(credit+"");//小数点处理
			}else{
				credit = 0;
			}
			voucherExp.credit =  credit;
			voucherExpArr.push(voucherExp);
			validTr++;
		}else{
			if(0 == i ){
				flag = true;
				return false;
			}
		}
	})
	if(flag){
		messagePop("第1条分录摘要不能为空！");
		resultFlag =  false;
	}
	if(flagAccount){ //摘要未填 未填处于点击状态
		$(noinputaccountName).click();
		resultFlag =  false;
	}
	
	if(validTr < 2){
		messagePop("请录入至少二条有效分录！");
		resultFlag =  false;
	}
	var billInfoStr='';
	$(".billinfo-delete").each(function(){ 
		billInfoStr = billInfoStr+ $(this).data("id")+","
	});
	var data = {
		'suc':resultFlag,
    	 'voucherTitleNumber':voucherTitleNumber,
    	 'accountDate' :accountDate,
    	 'recieptNumber':recieptNumber,
    	 'totalAmount':totalAmount,
    	 'userName':userName,
    	'voucherExpStr':JSON.stringify(voucherExpArr),
    	'billinfoid':billInfoStr
     } ;
	return data;
}

//凭证字号 初始化
function getRememberWord(){
	$.ajax({
	     type: 'POST',
	     url: ctx+'/voucher/tVoucher/getMaxByAccountPeriod',
	     cache:false,  
	     async: false,
	     dataType:'json',
	     data: {
	    	 'accountPeriod' :'',
	    	 'company' :''
	     } ,
	     success: function(data){
	    	 $(".voucher-current").find(".ui-spinbox").val(data+1);
	     }
	});
}

//获取模板
function getVouchetTemplate(type){
	$.ajax({
	     type: 'POST',
	     url: ctx+'/vouchertemplate/tVoucherTemplate/modelset',
	     cache:false,  
	     async: false,
	     dataType:'json',
	     data: {
	    	 'templateTypeId' :type
	     } ,
	     success: function(data){
	    	var str = "";
	    	if(type == 1){
	    		str +='<div class="jia contlist"><div class="jiahao"></div></div>';
	    	}
	    	for(var i in data){
	    		str += '<div class="cont-txt mt15 contlist" data-id="'+data[i].id+'"><div class="th">'+data[i].templateName;
	    		if(type == 1){
	    			str +='<i class="iwrite"></i><em class="emclose">';
	    		}
	    		str +='</em></div><div class="tb clearfix"><dl class="dlcont dl-borrow"><dt>借</dt>';
	    		var debitExps = data[i].debitExps;
	    		for(var j in debitExps){
					str += '<dd><i>·</i>'+debitExps[j].accountName+'</dd>';
				}	
				str += '</dl><div class="line"></div><dl class="dlcont dl-lend"><dt>贷</dt>';
				var creditExps = data[i].creditExps;
				for(var n in creditExps){
					str += '<dd><i>·</i>'+creditExps[n].accountName+'</dd>';
				}	
				str += '</dl><span class="usetemplate">使用模板</span></div></div>';
	    	}
	    	if(type == 1){
	    		$(".contH").eq(0).html(str);
	    	}else if(type == 2){
	    		$(".contH").eq(1).html(str);
	    	}else if(type == 3){
	    		$(".contH").eq(2).html(str);
	    	}
	     }
	});
}

//提示信息渐隐
function msgFadeOut(){
	setTimeout(function(){$(".span-tip").fadeOut("slow")},3000);
}

//获取科目上一期的 endbalance
function getLastAccountEndBalance(accountid){
	//var balance
	$.ajax({
	     type: 'POST',
	     url: ctx+'/balance/tBalance/getLastAccountEndBalance',
	     cache:false,  
	     async: false,
	     dataType:'json',
	     data: {
	    	 'accountId':accountid
	     } ,
	     success: function(data){
	    	 balance = data;
	     }
	});
	return balance;
}

//获取科目 并显示到添加的下拉列表
function getAccountToSelect(){
	$.ajax({
	     type: 'POST',
	     url: ctx+'/account/tAccount/getAccountToSelect',
	     cache:false,  
	     async: false,
	     dataType:'json',
	     data: {
	    	
	     } ,
	     success: function(data){
	    	var str = "";
	    /*	for(var i in data){
	    		str += "<option value='"+data[i].id+"'>"+data[i].accountName+"</option>";
	    	}*/
	    	$(data).each(function(e){
	    		str += "<option value='"+this.id+"'  data-ifamortize='"+this.ifAmortize+"'>" +this.accuntId+"&nbsp;"+this.parentName+this.accountName+"</option>";
	    	})
	    	
	    	$("#subject-select-add").find(".inp-name").html(str);
	     }
	});
}


//人民币金额转大写程序 JavaScript版     
function numToCny(num) {  
	var fuhao = "";
	if (num.substring(0,1)=="-"){
		fuhao = "负";
		num = num.substring(1,num.length);
	}
	
	var strOutput = "";  
	var strUnit = '仟佰拾亿仟佰拾万仟佰拾元角分';  
	num += "00";  
	var intPos = num.indexOf('.');  
	if (intPos >= 0)  
		num = num.substring(0, intPos) + num.substr(intPos + 1, 2);  
		strUnit = strUnit.substr(strUnit.length - num.length);  
	for (var i=0; i < num.length; i++)  
	strOutput += '零壹贰叁肆伍陆柒捌玖'.substr(num.substr(i,1),1) + strUnit.substr(i,1);  
	return fuhao + strOutput.replace(/零角零分$/, '整').replace(/零[仟佰拾]/g, '零').replace(/零{2,}/g, '零').replace(/零([亿|万])/g, '$1').replace(/零+元/, '元').replace(/亿零{0,3}万/, '亿').replace(/^元/, "零元");  
};
