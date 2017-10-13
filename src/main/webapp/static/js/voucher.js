  
 var current_voucher_id = "";
 
 var date=new Date;
 var year= date.getFullYear(); 
 var month=date.getMonth();
 var  day = new Date(year,month,0);   
 var lastdate =  day.getDate()+1;//获取当月最后一天日期    
 month = month +1;
 
 var voucherDataForPage = {
		 defaultVoucherTitle:'记',
		 voucherTitleNumber:1, //TODO获取数据库当前的票数
		 currentYear:year,
		 currentMonth:month,
		 CurrentLastYYYYMMDD:year+"-"+month+"-"+lastdate,
		 voucherLiNum:0
 };
 
//凭证一行
var voucherTr  = '<tr class="entry-item"><td class="col-operate"><div class="add-delete"><a class="a-add" href="javascript:;"></a><a class="a-delete" href="javascript:;"></a></div></td><td class="col-summary" data-edit="summary"><div class="cell-val summary-val"></div></td><td class="col-option"><div class="option"><a class="selSummary">摘要</a></div></td><td class="col-subject" data-edit="subject"><div class="cell-val subject-val"></div></td><td class="col-option"><div class="option"><a class="selSub">科目</a></div></td><td class="col-debite col-dc" data-edit="money"><div class="cell-val debit-val"></div></td><td class="col-credit col-dc" data-edit="money"><div class="cell-val credit-val"></div></td></tr>';
		

$(function(){
	
	countVoucherTotal();//计算 借贷方金额合计
	
	var _contr = $(".ul-each li"),
		_focs = $(".switch .lay"),
		_bills = $(".ul-bill li"),
		_dels = $(".ul-bill li .a-del");
	_contr.bind("click",function(){
		var i = $(this).index();
		_focs.hide();
		$(_focs[i]).show();
	})
	// 图表删除按钮
	_bills.hover(function(){
		$(this).find(".a-del").show();
		$(this).find(".a-wrong").show();

	},function(){
		$(this).find(".a-del").hide();
		$(this).find(".a-wrong").hide();
	})


	$("body").on("click",".ul-bill li .a-del",function(){
		$(".popUp").show();
		$(".mask").show();
	})

	$("body").on("click",".butcancel",function(){
		$(".popUp").hide();
		$(".mask").hide();
	})

	$("body").on("click",".i-close",function(){
		$(".popUp").hide();
		$(".mask").hide();
	})

	//记账凭记
	$(".tab-voucher tr:gt(0)").mouseover(function(){
		$(this).children(".col-operate").addClass("on");
	    $(this).children(".col-option").find(".option").css("visibility","visible");
	})
	$(".tab-voucher tr:gt(0)").mouseleave(function(){
		$(this).children(".col-operate").removeClass("on");
	    $(this).children(".col-option").find(".option").css("visibility","hidden");
	})

	// 借方贷方金额
	$("body").on("click",".col-dc",function(){
		var inpStr = $("<input type='text'>");
		var dcVal = $(this).find(".cell-val").text();
		if($(this).find("input").length==0){
			$(this).append(inpStr);
		}else{
			$(this).find("input").val(dcVal);
			$(this).find("input").show();
		}
		$(this).find("input").focus();
		$(this).find("input").blur(function(){
			$(this).hide();
			var inpVal = $(this).val();
			$(this).prev(".cell-val").text(inpVal);
			//对合计进行重新计算
			countVoucherTotal();
		});
	})

	// 摘要
	$("body").on("click","td.col-summary",function(){ 
		var inpStr = $("<input type='text'>");
		var summaryVal = $(this).find(".cell-val").text();
		var sumInp = $(this).find("input").length;
		if($(this).find("input").length==0){
			$(this).append(inpStr);
		}else{
			$(this).find("input").val(summaryVal);
			$(this).find("input").show();
		}
		$(this).find("input").focus();
		$(this).find("input").blur(function(){
			$(this).hide();
			var inpVal = $(this).val();
			$(this).prev(".cell-val").text(inpVal);
		});
	})


//会计科目
var clickTd;
$("body").on("click","td.col-subject",function(event){
	clickTd = $(this);
	event.stopPropagation();
	$("td.col-subject").removeClass("cellOn");
	var inpStr = $("<input class='inp-sub' type='text'>");
	var cellVal = $(this).find(".cell-val").text();
	if($(this).find("input").length==0){
		$(this).append(inpStr);
	}else{
		$(this).find("input").val(cellVal);
		$(this).find("input").show();
	}

	$(this).find("input").focus();
	$(this).addClass("cellOn");
	var clickTop = $(this).find(".cell-val").offset().top;
	var clickLeft = $(this).find(".cell-val").offset().left;
	var editTop = $(".edit").offset().top;
	var addTop = clickTop+65;	
	var addLeft = clickLeft;
	var addcss = {
				top: addTop+"px",
				left: addLeft+"px"
				};
	$(".ul-select").css(addcss);
	
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
 	    	$(".ul-select").html(result);
 	     }
	});
	
	$(".ul-select").show();
})

$("body").on("click",".ul-select li",function(){
	var _liVal = $(this).text();
	$(".cellOn").find("input").hide();
	$(".cellOn").find(".cell-val").html(_liVal);
	$(".ul-select").hide();
	$(clickTd).next().find(".option").append('<a class="balance" data-id="762192203369742" data-number="1001" data-cur="[&quot;RMB&quot;]">余额</a>');
})

$("body").click(function(){
	$(".ul-select").hide();
	$(".cellOn").find("input").hide();
})

// 会计科目 余额的显示隐藏
$("body").on("input propertychange",".inp-sub",function(){
	var divstr = $(this).prev().text();
	var inpstr = $(this).val();
	if(divstr == inpstr){
		$(this).parent().next().find(".option").append('<a class="balance" data-id="762192203369742" data-number="1001" data-cur="[&quot;RMB&quot;]">余额</a>');
	}else{
		$(".balance").remove();
	}
})



// 凭证字下拉
var wordFlag = false,
	wordStrFlag = false;
$("body").on("click",".ui-combo-wrap",function(event){
	event.stopPropagation();
	var clickTop = $(this).offset().top;
	var clickLeft = $(this).offset().left;
	var editTop = $(".edit").offset().top;
	var addTop = clickTop-editTop+18;
	var addLeft = clickLeft;
	var addcss = {
				top: addTop+"px",
				left: addLeft+"px"
				};
	$(".ul-word").css(addcss);
	$(".ul-word").show();

	if(!wordFlag){
		$(this).css("border","1px solid #aaa");
		$(".ul-word").show();
		wordFlag = true;
	}else{
		$(this).css("border","1px solid #ddd");
		$(".ul-word").hide();
		wordFlag = false;
	}
})

$("body").click(function(){
	$(".ul-word").hide();
	$(".ui-combo-wrap").css("border","1px solid #ddd");
	wordFlag = false;
})

$("body").on("click",".ul-word li",function(){
	var liVal = $(this).html();
	$(".input-txt").val(liVal);
	$(".ul-word").hide();
	wordFlag = false;
})


$("body").on("click",".i-mess",function(){
	$(".message-pop").hide();
})

billinfoFunction();//发票处操作
subjectFunction();//凭证-摘要处的方法
voucherFunction();//凭证-相关操作（不包括摘要部分）



	//信息提示窗
	var messagePop = function(str){
		$(".message-pop").children("p").html(str);
		$(".message-pop").animate({bottom:"30px",right:"10px",opacity:"1"},"slow",function(){
		})
		setInterval(function(){
			$(".message-pop").animate({bottom:"-100px",right:"-250px",opacity:"0"},"slow",function(){
			})
		},2000)
	}

});

//发票处操作
function billinfoFunction(){
	//发票-点击删除
	$("body").on("click",".em-del",function(){
		var _this = $(this);
		var _id = _this.data("id");
		var _ind = _this.parent().index();
		/*$.each($(".ul-pj"),function(i,item){
			$(item).find("li").eq(_ind).remove();
		})*/
		$(".billinfo-voucher-tr-"+_id).remove();
		$(this).parent().remove();
		
		deleteAllVoucer();
	});
	
	//左侧点击
	$("body").on("click",".a-left",function(){
		/*if($(".billinfo-show").length >= 3){
			//$(".billinfo-show:first").removeClass("billinfo-show").addClass("billinfo-hide-left").fadeOut();
			$(".billinfo-show:first").removeClass("billinfo-show").addClass("billinfo-hide-left")
			.animate({left: '-=250px'}, 500,function(e){
				$(this).hide();
			});
			//$(".billinfo-show::eq(1)").animate({"left": 0}, 500);
			$(".billinfo-hide-right:first").removeClass("billinfo-hide-right").addClass("billinfo-show").fadeIn();
		}*/
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
		/*if($(".billinfo-show").length >= 3){
			$(".billinfo-show:last").removeClass("billinfo-show").addClass("billinfo-hide-right").fadeOut();
			//$(".billinfo-show::eq(1)").animate({"right": 0}, 500);
			$(".billinfo-hide-left:first").removeClass("billinfo-hide-left").addClass("billinfo-show").fadeIn();
		}*/
	})
}




// 凭证-摘要处的方法
var subjectFunction = function(){
	//会计科目 模糊查询
	var changeAjaxFlag = false;
	$("body").on('input propertychange',".tAccount-input,.inp-sub", function() {
		if(changeAjaxFlag){
			return;
		}
		changeAjaxFlag = true;
		var param = $(this).val();
		$.ajax({
	 	     type: 'POST',
	 	     url: ctx+'/account/tAccount/getAccountByCodeOrName',
	 	     cache:false,  
	 	     async: false,
	 	     dataType:'json',
	 	     data: {
	 	    	 'param' :param
	 	     } ,
	 	     success: function(data){
	 	    	changeAjaxFlag = false;
	 	    	 var result = '';
	 	    	 var source = $("#accountTemplate").html();
	 	    	 var template = Handlebars.compile(source);
	 	    	 result= template(data);
	 	    	$(".ul-select").html(result);
	 	     }
		});
	});
};

//凭证-相关操作（不包括摘要部分）
function voucherFunction(){
	
	//保存di
	$("body").on("click",".a-save",function(){
		saveVoucher();
	});
	
	
	//点击添加
	$("body").on("click",".a-add",function(e){
		$(this).parent().parent().parent().before(voucherTr);
	});
	
	//点击删除
	$("body").on("click",".a-delete",function(e){
		$(this).parent().parent().parent().remove();
		//对合计进行重新计算
		countVoucherTotal();
	});
	
	//向左翻
	$("body").on("click",".scrollleft",function(){
		var prev =  $("#voucher-ul").find(".voucher-current").prev();
		if(prev.length > 0){
			$("#voucher-ul").children(".voucher-li").removeClass("voucher-current").hide();
			$(prev).addClass("voucher-current").show();
		}else{
			//前面已经没有了
			//alert("前面已经没有了");
			messagePop("前面已经没有了");
		}
	})
	
	//向右翻
	$("body").on("click",".scrollright",function(){
		var next =  $("#voucher-ul").find(".voucher-current").next();
		if(next.length > 0){
			$("#voucher-ul").children(".voucher-li").removeClass("voucher-current").hide();
			$(next).addClass("voucher-current").show();
		}else{
			//后面已经没有了
			//alert("后面已经没有了");
			messagePop("前面已经没有了");
		}
	})
	
}


//////////////////////////////////////////////////////////各定义方法 共上放调用///////////////////////////////////////////
//如果凭证录入条目被删除完毕则追加一行
function deleteAllVoucer(){
	//都删除完了 则追加一行
	if($(".voucher-current").find(".entry-item").length < 1){
		//var str = '<tr class="entry-item"><td class="col-operate"><div class="add-delete"><a class="a-add" href="javascript:;"></a><a class="a-delete" href="javascript:;"></a></div></td><td class="col-summary" data-edit="summary"><div class="cell-val summary-val"></div></td><td class="col-option"><div class="option"><a class="selSummary">摘要</a></div></td><td class="col-subject" data-edit="subject"><div class="cell-val subject-val"></div></td><td class="col-option"><div class="option"><a class="selSub">科目</a></div></td><td class="col-debite col-dc" data-edit="money"><div class="cell-val debit-val"></div></td><td class="col-credit col-dc" data-edit="money"><div class="cell-val credit-val"></div></td></tr>';
		
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
}


//信息保存
function saveVoucher(){
	 
	 //判断当前的表单是否已提交 提交过的不允许再提交
	 if($(".voucher-current").hasClass("voucher_has_save")){
		 //alert("当前凭证已保存");
		 messagePop("当前凭证已保存");
		 return false;
	 }
	//合计金额
	 var totalAmount = $(".voucher-current").find(".debit-total").text();
	 var creditTotal =  $(".voucher-current").find(".credit-total").text();
	 if(totalAmount != creditTotal){
		// alert("录入借贷不平");
		 messagePop("录入借贷不平");
		 return false;
	 }
	 if(totalAmount <= 0){
		 //alert("请录入借贷方金额");
		 messagePop("请录入借贷方金额");
		 return false;
	 }
	 
	 //凭证信息
	//凭证字名称
	//var voucherTitleName =   $(".voucher-current").find(".input-txt").val(); TODO 删除的字段
	 
	//凭证字号
	var voucherTitleNumber = $(".voucher-current").find(".ui-spinbox").val();
	
	//记账日期
	var accountDate = $(".voucher-current").find(".ui-datepicker-input").val();
	
	//账期  当前的年和月
	var accountPeriod = voucherDataForPage.currentYear+""+ voucherDataForPage.currentMonth; //后台传递 TODO
	
	//单据数
	var length =$(".voucher-current").find(".ul-pj").find("li").length;
	
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
			noinputaccountName = $(this).find(".subject-val");
			//var accountaaa = $(this).find(".subject-val").text();
			if($(noinputaccountName).text().length < 1 && !flagAccount){
				flagAccount = true;
			}
			voucherExp.accountName =  $(noinputaccountName).text();
			voucherExp.accountId =  $(this).find(".subject-val").data("amountid");
			var debit = parseFloat($(this).find(".debit-val").text());
			if(debit.length > 0 && !isNaN(debit)){
				var firstInt = debit.substr(0,debit.length -2); 
				var secondFloat = debit.substr(debit.length -2,debit.length);
				debit = firstInt+"."+secondFloat;
			}else{
				debit = 0;
			}	
			voucherExp.debit =  debit;
			var credit = parseFloat($(this).find(".credit-val").text());
			if(credit.length > 0 && !isNaN(credit)){
				var firstInt = credit.substr(0,credit.length -2); 
				var secondFloat = credit.substr(credit.length -2,credit.length);
				credit = firstInt+"."+secondFloat;
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
	/*$('input[class="voucher_checkbox"]:checked').each(function(){ 
		billInfoStr = billInfoStr+ $(this).data("id")+","
	}); */

	$(".em-del").each(function(){ 
		billInfoStr = billInfoStr+ $(this).data("id")+","
	});
	//保存前信息验证 TODO
	
	$.ajax({
	     type: 'POST',
	     url: ctx+'/voucher/tVoucher/saveVoucher',
	     cache:false,  
	     dataType:'json',
	     data: {
	    	 'voucherTitleName' :voucherTitleName,
	    	 'voucherTitleNumber':voucherTitleNumber,
	    	 'accountDate' :accountDate,
	    	 'accountPeriod':accountPeriod,
	    	 'recieptNumber':recieptNumber,
	    	 'totalAmount':totalAmount,
	    	 'userName':userName,
	    	'voucherExpStr':JSON.stringify(voucherExpArr),
	    	'billinfoid':billInfoStr
	     } ,
	     success: function(data){
	    	if(data.suc){
	    		//给当前凭证保存标记 和 凭证id
	    		$(".voucher-current").addClass("voucher_has_save").data("id",data.id);
	    		/*$('input[class="voucher_checkbox"]:checked').each(function(){ 
	    	 		$("#billinfoi-bean-"+$(this).data("id")).remove();
	    		}); */
	    		messagePop("保存成功");
	    	}else{
	    		//保存失败提示
	    		messagePop("保存失败");
	    	}
	     }

	});
}
