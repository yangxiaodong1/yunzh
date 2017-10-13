///////////////////////////////////////////billinfo start//////////////////////////////////////////////////////////////
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

var contInState = true;
var contdeState = true;

$(function(){
	
	/*//页面初始化获取数据
	billinfoshow();*/
	
	//切换
	var butState = false;
	$("body").on("click","#showType",function(){
		if(butState){
			$(".bills").hide();
			$(".imgs").show();
			$(this).html("切换为列表模式").data("showtype",1);
			butState = false;
		}else{
			$(".bills").show();
			$(".imgs").hide();
			$(this).html("切换为大图模式").data("showtype",2);
			butState = true;
		}
		billinfoshow();
	})

	var wordFlag = false,
		wordStrFlag = false;
	$("body").on("click",".ui-combo-wrap",function(event){
		event.stopPropagation();
		var clickTop = $(this).offset().top;
		var clickLeft = $(this).offset().left;
		var opTop = clickTop+22;
		var addLeft = clickLeft;
		var addcss = {
					top: opTop+"px",
					left: addLeft+"px"
					};
		$(".ul-word").css(addcss);
		$(".ul-word").show();

		if(!wordFlag){
			$(this).css("border","1px solid #bfbfbf");
			$(".ul-word").show();
			wordFlag = true;
		}else{
			$(this).css("border","1px solid #bfbfbf");
			$(".ul-word").hide();
			wordFlag = false;
		}
	})
	
	$("body").on("click",".ul-word li",function(){
		var liVal = $(this).html();
		var billstatus = $(this).data("billstatus");
		$(".input-txt").val(liVal).data("billstatus",billstatus);
		$(".ul-word").hide();
		wordFlag = false;
		billinfoshow();
	})
	
	//已审验选中
	$(".ul-word li").eq(1).click();
	
	
	//新增凭证
	$("body").on("click","#add-voucher",function(){
		var str = "";
		var showType = $("#showType").data("showtype");
		var hasSaveVoucer = false;
		if(showType == 2){//列表模式
			$(".list_checkbox_children:checked").each(function(e){
				str += $(this).parent().parent().parent().parent().data("id") + ",";
				var falg = $(this).parent().parent().parent().parent().data("hassavevoucer") ;
				if($(this).parent().parent().parent().parent().data("hassavevoucer")){
					hasSaveVoucer = true;
				}
			})
		}else if(showType == 1){ //图片模式
			$(".img_checkbox_children:checked").each(function(e){
				str += $(this).parent().parent().data("id") + ",";
				var falg = $(this).parent().parent().data("hassavevoucer") ;
				if($(this).parent().parent().data("hassavevoucer")){
					hasSaveVoucer = true;
				}
			})
		}
		
		if(hasSaveVoucer){
			messagePop("选择发票中存在已有录入凭证");
			return false;
		}
		if(str.length > 0){
			str = str.substring(0,str.length-1);
		}
		$("#to-add-voucher-form-billInfos").val(str);
		$("#to-add-voucher-form-submit").submit();
	})
	
	listoperation();//列表模式-操作
	imgopreation();//图片模式-操作
	dialogbigFunciton();//查看大图-操作
})

//列表模式-操作
function listoperation(){
	//列表模式-操作-查看大图
	$("body").on("click",".list-show-big",function(e){
		billinfoshowbig($(this).parent().parent().parent().data("id"));
	})
	
	//列表模式-操作-删除(作废)
	$("body").on("click",".list-show-del",function(){
		var id = $(this).parent().parent().parent().data("id");
		var number = $(this).parent().parent().parent().data("number");
		$("#reminder-id").val(id);
		$(".dialog").hide();
		$(".reminder").find(".reminder-msg").html('图片编号"'+number+'"');
		$(".mask").show();
		$(".reminder").show();
	})
	
	//列表模式-操作-删除-确认
	$("body").on("click",".reminder-save",function(){
		billInfoDel();
	})

	//列表模式-操作-标错
	$("body").on("click",".list-show-error",function(){
		var id = $(this).parent().parent().parent().data("id");
		var number = $(this).parent().parent().parent().data("number");
		$("#wrong-id").val(id);
		$(".dialog").hide();
		$(".mask").show();
		$(".wrong").show();
	})
	
	//列表模式-操作-标错-确认
	$("body").on("click",".wrong-save",function(){
		billInfoWrong();
	})
	
	//列表模式-操作-跨期
	$("body").on("click",".list-show-period",function(){
		var id = $(this).parent().parent().parent().data("id");
		var number = $(this).parent().parent().parent().data("number");
		var time = $(this).parent().parent().parent().find(".signdate").text();
		var str = time.split("-")[0]+"-"+time.split("-")[1];
		$(".instrument").find(".time-from").val(str);
		$("#instrument-id").val(id);
		$(".dialog").hide();
		$(".mask").show();
		$(".instrument").show();
	})
	
	//列表模式-操作-跨期-确定
	$("body").on("click",".instrument-save",function(){
		billInfoPeriod();
	})
	
	//列表模式 全选
	$("body").on("click",".list_checkbox_parent",function(e){
		var index = $(this).data("index");
		if($(this).is(':checked')){
			$(".list_checkbox_children"+index).each(function(e){
				if(!$(this).is(':checked')){
					$(this).next().click();
				}
			}) 
		}else{
			$(".list_checkbox_children"+index).each(function(e){
				if($(this).is(':checked')){
					$(this).next().click();
				}
			})
		}
	});
	
	//列表模式 单选
	 $("body").on("click",".list_checkbox_children",function(e){
		var index = $(this).data("parentcheckbox");
		if($(this).is(':checked')){
			var flag = false;
			var length = 0;
			$(".list_checkbox_children"+index).each(function(e){
				if(!$(this).is(':checked')){
					flag = true;
				}else{
					length++;
				}
			}) 
			if(flag){
				if($(".list_checkbox_parent"+index).is(':checked')){
					$(".list_checkbox_parent"+index).next().click();
				}
			}else{
				if(!$(".list_checkbox_parent"+index).is(':checked') &&length == $(".list_checkbox_children"+index).length){
						$(".list_checkbox_parent"+index).next().click();
				}
			}
		}else{
			if($(".list_checkbox_parent"+index).is(':checked')){
				$(".list_checkbox_parent"+index).next().click();
			}
		}
	}); 
	
	//关闭
	$("body").on("click",".bill-info-close-dialog,.cancel",function(){
		$(".kindlyReminder").hide();
		$(".mask").hide();
		$(".dialog").hide();
		msgFadeOut();//提示信息渐隐
		$(".rightSlide").animate({right:"-392"});
		$(".spInteOn").addClass("spInte").removeClass("spInteOn");
		$(".spDetaOn").addClass("spDeta").removeClass("spDetaOn");
		contInState = true;
		contdeState = true;
	})
}
//图片模式-操作
function imgopreation(){
	//查看大图
	$("body").on("click",".img-show-big",function(e){
		billinfoshowbig($(this).parent().data("id"));
	})
	
	//图片模式-操作-删除(作废)
	$("body").on("click",".dialog-link3",function(){
		var id = $(this).parent().parent().data("id");
		var number = $(this).parent().parent().data("number");
		$("#reminder-id").val(id);
		$(".dialog").hide();
		$(".reminder").find(".reminder-msg").html('图片编号"'+number+'"');
		$(".mask").show();
		$(".reminder").show();
	})

	//图片模式-操作-标错
	$("body").on("click",".dialog-link2",function(){
		var id = $(this).parent().parent().data("id");
		var number = $(this).parent().parent().data("number");
		$("#wrong-id").val(id);
		$(".dialog").hide();
		$(".mask").show();
		$(".wrong").show();
	})
	
	//图片模式-操作-跨期
	$("body").on("click",".dialog-link",function(){
		var id = $(this).parent().parent().data("id");
		var number = $(this).parent().parent().data("number");
		var time = $(this).parent().parent().data("signdate")+"";
		var str = time.split("-")[0]+"-"+time.split("-")[1];
		$(".instrument").find(".time-from").val(str);
		$("#instrument-id").val(id);
		$(".dialog").hide();
		$(".mask").show();
		$(".instrument").show();
	})
	
	
	//图片模式-复选框
	$("body").on("click",".img_checkbox_children",function(e){
		if($(this).is(':checked')){
			$(this).parent().parent().addClass("check");
		}else{
			$(this).parent().parent().removeClass("check");
		}
	})
}

//大图模式-操作
function dialogbigFunciton(){
	//查看大图-删除
	$("body").on("click","#dialog-big-del",function(){
		var id = $("#dillog-big-bill-info-id").val();
		var number =  $("#dillog-big-bill-info-number").val();
		$("#reminder-id").val(id);
		$(".dialog").hide();
		$(".reminder").find(".reminder-msg").html('图片编号"'+number+'"');
		$(".mask").show();
		$(".reminder").show();
	})
	
	//查看大图-标错
	$("body").on("click","#dialog-big-wrong",function(){
		var id = $("#dillog-big-bill-info-id").val();
		$("#wrong-id").val(id);
		$(".dialog").hide();
		$(".mask").show();
		$(".wrong").show();
	})
	
	//查看大图-跨期
	$("body").on("click","#dialog-big-period",function(){
		var id = $("#dillog-big-bill-info-id").val();
		$("#wrong-id").val(id);
		$(".dialog").hide();
		$(".mask").hide();
		$(".mask").show();
		$(".instrument").show();
	})
	
	//查看大图-选择智能模板
	$("body").on("click",".pbmain",function(e){
		if($("#dillog-big-bill-info-hasSave").val() == 'true'){
			messagePop("发票已录入凭证");
			return false;
		}
		
		//智能模板收起
		$(".sp-IntelligentAccount").click();
		//关闭其他弹窗
		$(".bill-info-close-dialog").click();
		
		$(".mask").show();
		//字号
		getRememberWord();
		
		var id = $("#dillog-big-bill-info-id").val();
		var intelligentid = $(this).data("id");
		$.ajax({
		     type: 'POST',
		     url: ctx+'/billinfo/tBillInfo/getBillInfoWithIntelligent',
		     cache:false,  
		     dataType:'json',
		     data: {
		    		"id":id,
		    		"intelligentid":intelligentid
		     },
		     success: function(data){
		    	var length = 1;
		    	if(data == undefined){
		    		length = 0;
		    	}
		    	var str = '';
		    	for(var i = 0;i<length;i++){
		 			str +='<tr class="entry-item "><td class="col-operate"><div class="add-delete"><a class="a-add" href="javascript:;"></a></div></td><td class="col-summary" data-edit="summary"><div class="cell-val summary-val">'+data.abstractMsg+'</div></td><td class="col-subject" data-edit="subject"  data-amount="'+data.amount+'"><div class="cell-val subject-val"><p data-amountid="'+data.debitAccount.accuntId+'" data-amountdc="'+data.debitAccount.accountName+'" data-ifamortize="'+data.debitAccount.ifAmortize+'">'+data.debitAccount.accuntId+'&nbsp;'+data.debitAccount.accountName+'</p>';
		 			//TODO 根据科目 类型判断是否显示自动分摊
		 			//余额方向为根据科目 最近一期科目余额表的endbalance TODO1
		 			var msg = "余额:";
		    		/*if(data.amount.length > 2){
		    			msg += "+"+toMoneyNum(data.amount);
		    		}*/
		    		msg += getLastAccountEndBalance(data.debitAccount.accuntId).endbalance;
		 			str +='<p>'+msg+'</p></div></td><td class="col-debite col-dc" data-edit="money"><div class="cell-val debit-val">'+data.amount+'</div></td><td class="col-credit col-dc" data-edit="money"><div class="cell-val credit-val"></div></td><td class="col-delete"><div class="add-delete"><a class="a-delete" href="javascript:;"></a></div></td></tr>';	    
		 			str +='<tr class="entry-item "><td class="col-operate"><div class="add-delete"><a class="a-add" href="javascript:;"></a></div></td><td class="col-summary" data-edit="summary"><div class="cell-val summary-val">'+data.abstractMsg+'</div></td><td class="col-subject" data-edit="subject"  data-amount="'+data.amount+'"><div class="cell-val subject-val"><p data-amountid="'+data.creditAccount.accuntId+'" data-amountdc="'+data.creditAccount.accountName+'"  data-ifamortize="'+data.debitAccount.ifAmortize+'">'+data.creditAccount.accuntId+'&nbsp;'+data.creditAccount.accountName+'</p>';
		 			//余额方向为根据科目 最近一期科目余额表的endbalance TODO1
		 			/*
		 			if(data.amount.length > 2){
		 				msg = "余额";
		    			msg += "-"+toMoneyNum(data.amount);
		    		}*/
		 			msg = "余额:";
		 			msg += getLastAccountEndBalance(data.creditAccount.accuntId).endbalance;
		 			str +='<p>'+msg+'</p></div></td><td class="col-debite col-dc" data-edit="money"><div class="cell-val debit-val"></div></td><td class="col-credit col-dc" data-edit="money"><div class="cell-val credit-val">'+data.amount+'</div></td><td class="col-delete"><div class="add-delete"><a class="a-delete" href="javascript:;"></a></div></td></tr>';	    
		    	}
		    	for(;i < 1;i++){
		    		str +='<tr class="entry-item "><td class="col-operate"><div class="add-delete"><a class="a-add" href="javascript:;"></a></div></td><td class="col-summary" data-edit="summary"><div class="cell-val summary-val"></div></td><td class="col-subject" data-edit="subject"><div class="cell-val subject-val"><p></p><p></p></div></td><td class="col-debite col-dc" data-edit="money"><div class="cell-val debit-val"></div></td><td class="col-credit col-dc" data-edit="money"><div class="cell-val credit-val"></div></td><td class="col-delete"><div class="add-delete"><a class="a-delete" href="javascript:;"></a></div></td></tr>';	    
		    		str +='<tr class="entry-item "><td class="col-operate"><div class="add-delete"><a class="a-add" href="javascript:;"></a></div></td><td class="col-summary" data-edit="summary"><div class="cell-val summary-val"></div></td><td class="col-subject" data-edit="subject"><div class="cell-val subject-val"><p></p><p></p></div></td><td class="col-debite col-dc" data-edit="money"><div class="cell-val debit-val"></div></td><td class="col-credit col-dc" data-edit="money"><div class="cell-val credit-val"></div></td><td class="col-delete"><div class="add-delete"><a class="a-delete" href="javascript:;"></a></div></td></tr>';	    
		    	}
		 		$(".voucher-current").find(".tbody-voucher").html(str);
		 		//金额合计
		 		countVoucherTotal();	 		
		 		$(".mask").show();
		 		$(".tbvoucher").show();		 		
		     }
		});
	})
	
	
	
	//查看大图-选择智能模板-保存凭证
	$("body").on("click",".savevoucher-list",function(){
		var data = saveVoucherCheck();
		if(!data.suc){
			return false;
		}
		//设置自动摊销
		if($(".voucher-current").find(".amortize").hasClass("hasSave")){
			data.expName = $(".autoAmortization .ipt-summary").val();
			data.originalValue = $(".voucher-current .amortize").data("amount");
			data.scrapValueRate = $(".autoAmortization .ipt-value").val();
			data.scrapValue = $(".autoAmortization #scrap-value").text();
			data.amortizeAgeLimit = $(".autoAmortization .ipt-monthnum").val();
			data.debitAccountId = $(".autoAmortization .ipt-debit").data("accountid");
			data.creditAccountId = $(".autoAmortization .ipt-credit").data("accountid");
		}
		//记账日期
		var accountDate = $(".voucher-current").find(".ui-datepicker-input").val();
		data.accountDate = accountDate;
		
		//制单人
		var userName = $(".voucher-current").find(".make-person-name").text();
		data.userName = userName;
		
		//保存模板
		$.ajax({
		     type: 'POST',
		     url: ctx+'/voucher/tVoucher/saveVoucher',
		     cache:false,  
		     dataType:'json',
		     data: data,
		     success: function(data){
		    	if(data.suc){
		    		$(".span-tip").text("录入凭证成功").show();
		    		
		    		//自动摊销其他内容清空
		    		$(".autoAmortization .ipt-value").val('');
		    		$(".autoAmortization #scrap-value").text('');
		    		$(".autoAmortization .ipt-monthnum").val('');
		    		$(".autoAmortization .ipt-debit").val('');
		    		$(".autoAmortization .ipt-credit").val('');
		    	}else{
		    		$(".span-tip").text("录入凭证失败").show();
		    	}
		    	$(".bill-info-close-dialog").click();
		    	//列表初始化
		    	billinfoshow();
		     }
		});
		
	})
}

//发票信息显示
function billinfoshow(){
	$.ajax({
	     type: 'POST',
	     url: ctx+'/billinfo/tBillInfo/billInfosAjax',
	     cache:false,  
	     dataType:'json',
	     data: {
	    		"showType":$("#showType").data("showtype"),
	    		"billStatus":$("#billstatus").data("billstatus")
	     } ,
	     success: function(data){
	    	if(data.showType == 1){
	    		var str = "";
	    		var result = data.result;
	    		for(var i in result){
	    			str += '<li class="img-show-'+result[i].id+'" data-hasSaveVoucer="'+result[i].hasSaveVoucer+'"  data-id="'+result[i].id+'" data-number="'+result[i].invoiceNumber+'"  data-signdate="'+result[i].signDate.substr(0,10)+'"><img class="img-show-big" src="'+result[i].imageUrl+'" width="226" height="156" alt=""><i class="billstatus billstatus'+result[i].billStatus+'"></i><p>'+result[i].abstractMsg+'</p><p class="p-price">价格合计：'+result[i].totalPrice+'</p>	<span class="sp-choose"><input type="checkbox" class="img_checkbox_children">选中</span><div class="tit_p2">';
	    			if(result[i].billStatus != 5){
	    				str +='<a href="javascript:;" class="btn btn-c1 dialog-link3">删除</a>';
	    			}
	    			if(result[i].billStatus != 6 && result[i].billStatus != 7){
	    				str +='<a href="javascript:;" class="btn btn-c2 dialog-link2">标错</a>';
	    			}
	    			if(result[i].billStatus != 4){
	    				str +='<a href="javascript:;" class="btn btn-c3 dialog-link">跨期</a>';
	    			}
	    			str +='<div class="bg"></div></div></li>';
	    		}
	    		$(".ul-pj").html(str);
	    		$(".bills").hide();
	    		$(".imgs").show();
	    		$winH=$(window).height();
				$mainH2=$winH-185;
				$(".imgs").height(function(){
					return $mainH2;
				})
	    	}else if(data.showType == 2){
	    		var str = "";
	    		var result = data.result;
	    		for(var i in result){
	    			str += '<tr class="total'+i+'"  data-i="'+i+'"><td class="none-line no-bg"><div class="choose-fir"><input id="ip'+i+'"  class="list_checkbox_parent  list_checkbox_parent'+i+'" type="checkbox" data-index="'+i+'"><label for="ip'+i+'"></label></div></td><td></td><td></td><td></td><td></td><td>'+result[i].amountCount+'</td><td>'+result[i].taxCount+'</td><td>'+result[i].totalPriceCount+'</td><td></td><td><div class="sp-but clearfix"></div></td></tr>';
	    			var billInfoList = result[i].billInfoList;
	    			for(var j in billInfoList){
	    				str +='<tr id="bill-info-list-'+billInfoList[j].id+'" class="child'+i+'" data-hasSaveVoucer="'+billInfoList[j].hasSaveVoucer+'" data-i="'+i+'" data-id="'+billInfoList[j].id+'" data-number="'+billInfoList[j].invoiceNumber+'" ><td class="none-line no-bg"><div class="choose"><em><input id="ip'+i+j+'" class="list_checkbox_children list_checkbox_children'+i+'" data-parentcheckbox="'+i+'" type="checkbox"><label for="ip'+i+j+'"></label></em></div></td><td>'+billInfoList[j].tBIdName+'</td><td>'+billInfoList[j].abstractMsg+'</td><td>'+billInfoList[j].payName+'</td><td class="signdate">'+billInfoList[j].signDate.substr(0,10)+'</td><td>'+billInfoList[j].amount+'</td><td>'+billInfoList[j].tax+'</td><td>'+billInfoList[j].totalPrice+'</td><td>'+billInfoList[j].statusName+'</td><td><div class="sp-but clearfix"><i class="list-show-big"></i>';
	    				if(billInfoList[j].billStatus != 5){
	    					str +='<i class="list-show-error"></i>';
	    				}
	    				if(billInfoList[j].billStatus != 6 && billInfoList[j].billStatus != 7){
	    					str +='<i class="list-show-period"></i>';
	    				}
	    				if(billInfoList[j].billStatus != 4){
	    					str+='<i class="list-show-del"></i>';
	    				}
	    				str +='</div></td></tr>';
	    			}
	    		}
	    		$("#bills-tbody").html(str);
	    		$(".imgs").hide();
	    		$(".bills").show();
	    		$winH=$(window).height();
				$mainH2=$winH-185;
				$(".bills").height(function(){
					return $mainH2;
				})
	    	}
	     }
	});
}

//发票信息查看大图
function billinfoshowbig(id){
	$.ajax({
	     type: 'POST',
	     url: ctx+'/billinfo/tBillInfo/getBillInfo',
	     cache:false,  
	     dataType:'json',
	     data: {
	    		"id":id
	     },
	     success: function(data){
	    	 $("#dillog-big-bill-info-id").val(data.id);
	    	 $("#dillog-big-bill-info-number").val(data.invoiceNumber);
	    	 $("#dillog-big-bill-info-hasSave").val(data.hasSaveVoucer);
	    	 $("#dillog-big-bill-info-type").val(data.tBId);
	    	 $("#bill-info-img").attr("src",data.imageUrl);
	    	 $("#bill-info-abstractMsg").text(data.abstractMsg);
	    	 $("#bill-info-tBIdName").text(data.tBIdName);
	    	 $("#bill-info-recieveName").text(data.recieveName);
	    	 $("#bill-info-statusName").text(data.statusName);
	    	 $("#bill-info-payName").text(data.payName);
	    	 $("#bill-info-amount").text(data.amount);
	    	 $("#bill-info-tax").text(data.tax);
	    	 $("#bill-info-totalPrice").text(data.totalPrice);
	    	 $(".mask").show();
	    	 $(".kindlyReminder").show();
	     }
	});
}

//发票-删除（作废）
function billInfoDel(){
	$.ajax( {    
	    url:ctx+'/billinfo/tBillInfo/updateBillInfoScrap',
	    data:{    
	    	"id":$("#reminder-id").val()
	    },    
	    type:'post',    
	    cache:false,    
	    dataType:'json',    
	    success:function(data) {    
	    	//TODO 操作成功后处理
			if(data.suc){
				var i = $("#bill-info-list-"+$("#reminder-id").val()).data("i");
				$("#bill-info-list-"+$("#reminder-id").val()).remove();
				billInfoListGroupDelAll($(".total"+i));		
				$(".img-show-"+$("#reminder-id").val()).remove();
				$(".span-tip").text("作废反馈成功").show();
			}else{
				$(".span-tip").text("作废反馈失败").show();
			}
			$(".dialog").hide();
			$(".bill-info-close-dialog").click();
	     }  
	});
}
//发票-标错
function billInfoWrong(){
	var errorReason = ""
	if($("input[name='wrong']:checked").hasClass("wrong-input")){
		errorReason = $("input[name='wrong']:checked").next().val();
	}else{
		errorReason = $("input[name='wrong']:checked").parent().text();
	}
	if(errorReason.length < 1){
		messagePop("请选择标错原因");
		return false;
	}
	
	$.ajax( {    
	    url:ctx+'/billinfo/tBillInfo/updateBillInfoError',
	    data:{    
	    	"id":$("#wrong-id").val(),
	    	"errorReason":errorReason
	    },    
	    type:'post',    
	    cache:false,    
	    dataType:'json',    
	    success:function(data) {    
	    	//TODO
	    	if(data.suc){
				var i = $("#bill-info-list-"+$("#reminder-id").val()).data("i");
				$("#bill-info-list-"+$("#reminder-id").val()).remove();
				billInfoListGroupDelAll($(".total"+i));
				$(".img-show-"+$("#wrong-id").val()).remove();
				$(".span-tip").text("标错反馈成功").show();
			}else{
				$(".span-tip").text("标错反馈失败").show();
			}
	    	$(".dialog").hide();
	    	$(".bill-info-close-dialog").click();
	     }  
	});
}

function billInfoPeriod(){
	var endTime = $(".instrument").find(".select-time").val();
	if(endTime.length < 1){
		messagePop("请选择跨期时间");
		return false;
	}
	$.ajax({
	     type: 'POST',
	     url: ctx+'/billinfo/tBillInfo/billInfoPeriod',
	     cache:false,  
	     dataType:'json',
	     data: {
	    		"id":$("#instrument-id").val(),
	    		"endTime":$(".instrument").find(".select-time").val()
	     } ,
	     success: function(data){
	    	 if(data.suc){
				var i = $("#bill-info-list-"+$("#reminder-id").val()).data("i");
				$("#bill-info-list-"+$("#reminder-id").val()).remove();
				billInfoListGroupDelAll($(".total"+i));
				$(".img-show-"+$("#instrument-id").val()).remove();
				$(".span-tip").text("跨期反馈成功").show();
			}else{
				$(".span-tip").text("跨期反馈失败").show();
			}
	    	$(".dialog").hide();
	    	$(".bill-info-close-dialog").click();
	     }
	});
}

////////////////////////////////////////////////billinfo end////////////////////////////////////////////////////

////////////////////////////////////////////////dialog 查看大图  start////////////////////////////////////////////////////
$(function(){
	$("body").on("click",".sp-IntelligentAccount",function(){
		if(contInState){
			contInState = false;
			contdeState = true;
			$.ajax({
			     type: 'POST',
			     url: ctx+'/billinfo/tBillInfo/findIntelligentTemplate',
			     cache:false,  
			     dataType:'json',
			     data: {
			    		"company":'',//TODO
			    		"billType": $("#dillog-big-bill-info-type").val()
			     },
			     success: function(result){
			    	 var billType = result.billType;
			    	 var data = result.intelligentTemplates;
			    	 var str = '';
			    	 var count = 0;
			    	 if(data.length > 0){	 
				    	 //主要做法
				    	 $(".pamain").find(".pborrow").text('<i>借</i>'+data[0].debitAccountName);
				    	 $(".pamain").find(".plend").text('<i>贷</i>'+data[0].creditAccountName);
				    	 $(".processing-quantity").text(data.size)
				    	 str +='<div class="parta"><h6 class="h6tit">本公司之前的做法主要是</h6><div class="pamain"><p class="pborrow"><i>借</i>'+data[0].debitAccountName+'</p><p class="plend"><i>贷</i>'+data[0].creditAccountName+'</p></div></div>'; 
				    	 str += '<div class="partb"><h6 class="h6tit">本公司之前处理过<i id="t_intelligent_template_count">'+data.length+'</i>张';
				    	 str += billType.billTypeName+'</h6>';
				    	 for(var i in data){
				    		 str += '<div class="pbmain" data-id="'+data[i].id+'"><span class="spnum">'+data[i].count+'<i>张</i></span><p class="pborrow"><i>借</i>'+data[i].debitAccountName+'</p><p class="plend"><i>贷</i>'+data[i].creditAccountName+'</p></div>';
				    		 count += data[i].count;
				    	 }
				    	 str += '</div>';
			    	 }
			    	 $(".cont-IntelligentAccount").html(str);
			    	 $("#t_intelligent_template_count").text(count);
			     }
			});
			$(".rightSlide").animate({right:"0"}).addClass("bor27bc9e");
			$(this).removeClass("spInte").addClass("spInteOn").css("border-right","1px solid #fff");
			$(".sp-detailedInformation").removeClass("spDetaOn").addClass("spDeta").css("border-right","1px solid #27bc9e");
			$(".cont-detailedInformation").hide();
			$(".cont-IntelligentAccount").show();
		}else{
			contInState = true;
			$(".rightSlide").animate({right:"-392"});
			$(this).removeClass("spInteOn").addClass("spInte").css("border-right","1px solid #fff");
			$(".sp-detailedInformation").removeClass("spDetaOn").addClass("spDeta").css("border-right","1px solid #fff");
		}
	})
	$("body").on("click",".sp-detailedInformation",function(){
		if(contdeState){
			contdeState = false;
			contInState = true;
			$(".rightSlide").animate({right:"0"}).removeClass("bor27bc9e");
			$(this).removeClass("spDeta").addClass("spDetaOn").css("border-right","1px solid #fff");
			$(".sp-IntelligentAccount").removeClass("spInteOn").addClass("spInte").css("border-right","1px solid #3d8ae2");
			$(".cont-IntelligentAccount").hide();
			$(".cont-detailedInformation").show();
		}else{
			contdeState = true;
			$(".rightSlide").animate({right:"-392"});
			$(this).removeClass("spDetaOn").addClass("spDeta").css("border-right","1px solid #fff");
			$(".sp-IntelligentAccount").removeClass("spInteOn").addClass("spInte").css("border-right","1px solid #fff");
		}
	})
})
////////////////////////////////////////////////dialog 查看大图   end////////////////////////////////////////////////////

function billInfoListGroupDelAll(obj){
	var i = $(obj).data("i");
	if($(".child"+i).length < 1){
		$(obj).remove();
	}
}
//提示信息渐隐
function msgFadeOut(){
	setTimeout(function(){$(".span-tip").fadeOut("slow")},3000);
}