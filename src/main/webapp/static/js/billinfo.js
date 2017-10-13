 
/////////////////////////////////////////////自定义变量
//对发票单个操作时记录发票的id
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
var voucherTr = '<tr class="entry-item"><td class="col-operate"><div class="add-delete"><a class="a-add" href="javascript:;"></a><a class="a-delete" href="javascript:;"></a></div></td><td class="col-summary" data-edit="summary"><div class="cell-val summary-val"></div></td><td class="col-option" width="30"><div class="option"><a class="selSummary">摘要</a></div></td><td class="col-subject" data-edit="subject"><div class="cell-val subject-val"></div></td><td class="col-option"><div class="option"><a class="selSub">科目</a></div></td><td class="col-debite" data-edit="money"><div class="cell-val cellv debit-val"></div></td><td class="col-credit" data-edit="money"><div class="cell-val cellv credit-val"></div></td></tr>';

 
///////////////////////////////////////////通用方法 
 /**
  ** 加法函数，用来得到精确的加法结果
  ** 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
  ** 调用：accAdd(arg1,arg2)
  ** 返回值：arg1加上arg2的精确结果
  **/
function accAdd(arg1, arg2) {
     var r1, r2, m, c;
     try {
        r1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
        r2 = 0;
    }
    c = Math.abs(r1 - r2);
    m = Math.pow(10, Math.max(r1, r2));
    if (c > 0) {
        var cm = Math.pow(10, c);
        if (r1 > r2) {
            arg1 = Number(arg1.toString().replace(".", ""));
            arg2 = Number(arg2.toString().replace(".", "")) * cm;
        } else {
            arg1 = Number(arg1.toString().replace(".", "")) * cm;
            arg2 = Number(arg2.toString().replace(".", ""));
        }
    } else {
        arg1 = Number(arg1.toString().replace(".", ""));
        arg2 = Number(arg2.toString().replace(".", ""));
    }
    return (arg1 + arg2) / m;
}


/**
 ** 减法函数，用来得到精确的减法结果
 ** 说明：javascript的减法结果会有误差，在两个浮点数相减的时候会比较明显。这个函数返回较为精确的减法结果。
 ** 调用：accSub(arg1,arg2)
 ** 返回值：arg1加上arg2的精确结果
 **/
function accSub(arg1, arg2) {
    var r1, r2, m, n;
    try {
        r1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2)); //last modify by deeka //动态控制精度长度
    n = (r1 >= r2) ? r1 : r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

//////////////////////////////////////////////自定义方法
 //上方列表渲染
 function getListShow(){
	 	
	 	//统计金额清0
	 	$("#amount_total").text(0);
		$("#tax_total").text(0);
		$("#total_price_total").text(0);
	 
	 
	 	var billStatus = 2;   //2、已审验      
	 	if($("#checkboxshowInput").is(':checked')){
	 		billStatus = 4;//    4、作废 
	 	}
	 	if($("#checkboxhisInput").is(':checked')){
	 		billStatus = 3;//    3、已做账历史票据
	 	}
	 	
	 	//公司和多少期 TODO
		$.ajax( {    
		    url:ctx+'/sys/user/billInfosAjax', 
		    data:{    
		    	"showType":$(".ul-each").find(".select").data("showtype"),
		    	"orderBy":$("#list_order_by").val(),
		    	"billStatus":billStatus
		    },    
		    type:'post',    
		    cache:false,  
		    async: false,
		    dataType:'json',    
		    success:function(data) {    
		    	var result;
				if(data.showType == '1'){
					var source = $("#templateImg").html();
					var template = Handlebars.compile(source);
					result= template(data);
				}else if(data.showType == '2'){
					var source = $("#templateList").html();
					var template = Handlebars.compile(source);
					result = template(data);
				}
				$(".switch").html(result);
		     }  
		});
	}

 

//上方列表 操作栏
function voucherOperation(){

	//刷新点击
	$("body").on("click",".refresh",function(){
		getListShow();
	});
	
	//上方列表 点击排序
	$("#list_order_by").change(function(){
		getListShow();
	});
	
	//显示方式点击
	$("body").on("click",".billinfo-show-type",function(){
		$(".billinfo-show-type").removeClass("select");
		$(".billinfo-show-type").removeClass("ctron");
		$(this).addClass("ctron");
		$(this).addClass("select");
		getListShow();
	});

	//显示作废
	$("body").on("click","#checkboxshowInput",function(){
		if($("#checkboxhisInput").is(':checked')){
			$("#checkboxhisInput").attr("checked", false);
		}
		getListShow();
	});
	
	//显示历史数据
	$("body").on("click","#checkboxhisInput",function(){
		if($("#checkboxshowInput").is(':checked')){
			$("#checkboxshowInput").attr("checked", false);
		}
		getListShow();
	});
	
}
 
//上方列表 列表区域 操作按钮
function listOperation(){
	//标错点击
	$("body").on("click",".voucher_sign_error",function(e){
		$("#sign-error-reason").val('');
		current_voucher_id = $(this).data("id");
		$(".error-dialog").show();
		$(".mask").show();
	})

	//确认标错点击
	$("body").on("click",".voucher-sign-error-sure",function(){
		var reason = $("#sign-error-reason").val();
		if(reason.length < 1){
			//alert("请填写原因");
			messagePop("请填写原因");
			return false;
		}
		$.ajax( {    
		    url:ctx+'/billinfo/tBillInfo/updateBillInfoError',// 跳转到 action    
		    data:{    
		    	"id":current_voucher_id,
		    	"errorReason":reason
		    },    
		    type:'post',    
		    cache:false,    
		    dataType:'json',    
		    success:function(data) {    
				if(data.suc){
					//alert("标错成功!");
					messagePop("标错成功!");
					//弹窗关闭TODO
					$(".error-dialog").hide();
					//数据移除
					$("#billinfoi-bean-"+current_voucher_id).remove();
				}else{
					//alert("标错失败，"+data.msg);
					messagePop("标错失败，"+data.msg);
				}
				$(".pop").hide();
				$(".mask").hide();
		     }  
		});
	});
	
	//废弃点击
	$("body").on("click",".voucher_scrap",function(){
		$("#scrap-reason").val('');
		current_voucher_id = $(this).data("id");
		$(".scrap-dialog").show();
		$(".mask").show();
	})
	
	//废弃确定
	$("body").on("click",".voucher-scrap-sure",function(){
		
		var reason = $("#scrap-reason").val();
		if(reason.length < 1){
			//alert("请填写原因");
			messagePop("请填写原因");
			return false;
		}
		
		$.ajax( {    
		    url:ctx+'/billinfo/tBillInfo/updateBillInfoScrap',// 跳转到 action    
		    data:{    
		    	"id":current_voucher_id,
		    	"scrapReason":reason
		    },    
		    type:'post',    
		    cache:false,    
		    dataType:'json',    
		    success:function(data) {    
				if(data.suc){
					//alert("废弃成功!");
					messagePop("废弃成功!");
					//数据移除
					$("#billinfoi-bean-"+current_voucher_id).remove();
					//弹窗关闭TODO
					$(".scrap-hide").hide();
				}else{
					//alert("废弃失败，"+data.msg);
					messagePop("废弃失败，"+data.msg);
				}
				$(".pop").hide();
				$(".mask").hide();
		     }  
		});
	})
	
}
 
//上方列表 复选操作
function voucherSelect(){
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
	
	
	//复选框选择 计算
	$("body").on("click",".voucher_checkbox",function(e){
			var  amount = 0;
			if (!isNaN($(this).data("amount")) && $(this).data("amount").length > 0) {
				  amount = parseFloat($(this).data("amount"));
			}
			var  tax = 0;
			if (!isNaN($(this).data("tax")) && $(this).data("tax").length > 0) {
				  tax =  parseFloat($(this).data("tax"));
			}
			var  totalprice = 0;
			if (!isNaN($(this).data("totalprice")) && $(this).data("totalprice").length > 0) {
				  totalprice =  parseFloat($(this).data("totalprice"));
			}
			var amountTotal = parseFloat($("#amount_total").text());
			var taxTotal = parseFloat($("#tax_total").text());
			var totalPriceTotal = parseFloat($("#total_price_total").text());
			
			if($(this).is(':checked')){
				$("#amount_total").text(accAdd(amountTotal,amount));
				$("#tax_total").text(accAdd(taxTotal,tax));
				$("#total_price_total").text(accAdd(totalPriceTotal,totalprice));
			}else{
				$("#amount_total").text(accSub(amountTotal,amount));
				$("#tax_total").text(accSub(taxTotal,tax));
				$("#total_price_total").text(accSub(totalPriceTotal,totalprice));
			}
	});
	
}


/////////////////////////////////////////////////////数据处理的有关函数  一下是凭证相关
//上方选择数据分析到 凭证表格
function dataTovoucher(){
	//点击打开编辑按钮
	$("body").on("click",".a-edit",function(){
		//打开新建 关闭不做操作
		if(!$(".edit").hasClass("editOn")){
			voucherStrCreate();
			$(".mask").show();
		}else{
			$(".mask").hide();
		}
	});
	
	//凭证保存
	$("body").on("click",".a-save",function(){
		saveVoucher();
	})
	
}

//凭证操作
function voucherFunction(){
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
	
	//点击增加
	$("body").on("click",".btn-up",function(e){
		var value = $(this).parent().prev().val();
		value = parseInt(value)+1;
		$(this).parent().prev().val(value);
	});
	
	//点击减少
	$("body").on("click",".btn-down",function(e){
		var value = $(this).parent().prev().val();
		value = parseInt(value)-1;
		if(value < 1){
			return false;
		}
		$(this).parent().prev().val(value);
	});
	
	
	//会计科目
	var changeAjaxFlag = false;
	$("body").on('input propertychange',".tAccount-input", function() {
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
	
}
 
function voucherTemplate(){
	//点击生成模板
	$("body").on("click","#new-voucher-template",function(){
		$("#newVoucherTemplate-name").val('');
		$("#voucher-template-create-dialog").show();
		$(".mask-edit").show();
	})
	
	//生成模板提交
	$("body").on("click","#newVoucherTemplate-save",function(){
		var templateTypeId = $("#newVoucherTemplate-type").val();
		var templateName = $("#newVoucherTemplate-name").val();
		var voucherExpArr = new Array()
		
		if(templateName.length < 1){
			//alert("模板名称为必填");
			messagePop("模板名称为必填");
			return false;
		}
		
		//合计金额
		 var totalAmount = $(".voucher-current").find(".debit-total").text();
		 var creditTotal =  $(".voucher-current").find(".credit-total").text();
		 if(totalAmount != creditTotal){
			 //alert("录入借贷不平");
			 messagePop("录入借贷不平");
			 return false;
		 }
		 if(totalAmount <= 0){
			 //alert("请录入借贷方金额");
			 messagePop("请录入借贷方金额");
			 return false;
		 }
		
		 var flag = false;
		 var validTr = 0;
		 var flagAccount = false;
		 var noinputaccountName;
	 	//凭证摘要
	 	$(".voucher-current").find(".entry-item").each(function(i){
	 	
	 		//摘要
	 		var exp = $(this).find(".summary-val").text();
	 		if(exp.length > 0){
	 			var voucherExp = {};
	 			voucherExp.exp = exp;
	 			noinputaccountName = $(this).find(".subject-val");
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
	 		//alert("第1条分录摘要不能为空！");
	 		messagePop("第1条分录摘要不能为空！");
	 		return false;
	 	}
	 	
	 	if(flagAccount){
	 		$(noinputaccountName).click();
	 		return false;
	 	}
	 	
	 	if(validTr < 2){
	 		//alert("请录入至少二条有效分录！");
	 		messagePop("请录入至少二条有效分录！");
	 		return false;
	 	}
	 	
	 	$.ajax({
	 	     type: 'POST',
	 	     url: ctx+'/vouchertemplate/tVoucherTemplate/saveVoucherTemplate',
	 	     cache:false,  
	 	     dataType:'json',
	 	     data: {
	 	    	 'templateTypeId' :templateTypeId,
	 	    	 'templateName':templateName,
	 	    	'voucherTemExp':JSON.stringify(voucherExpArr)
	 	     } ,
	 	     success: function(data){
	 	    	if(data.suc){ //TOOD
	 	    		//给当前凭证保存标记 和 凭证id
//	 	    		alert("保存成功");
	 	    		messagePop("保存成功");
	 	    	}else{
	 	    		//保存失败提示
	 	    		//alert("保存失败");
	 	    		messagePop("保存失败");
	 	    	}
	 	    	$(".poptemp").hide();
 	    		$(".mask-edit").hide();
	 	     }

	 	});
		
	})
	
	//模板导入
	$("body").on("click","#voucher-template-import",function(){
		$("#voucher-template-import-dialog").show();
		$(".mask-edit").show();
		getVoucherTemplateByType();
	})
	
	//模板导入类型改变
	$("body").on("change","#voucher-template-import-type",function(){
		//根据模板类型获取模板
		getVoucherTemplateByType();
	})
	
	
	//模板导入
	$("body").on("click","#voucher-template-import-save",function(){
		var id = $("#voucher-template-import-select").val();
		getVoucherTemplateExpByTID(id);
		$(".poptemp").hide();
 		$(".mask-edit").hide();
	})
	
	//模板弹窗关闭
	$("body").on("click",".butcancel,.creat-close",function(){
		$(".poptemp").hide();
		$(".mask-edit").hide();
	})
}

function newOrDeleteVoucher(){
	
	//新建凭证
	$("body").on("click","#new-voucher-option",function(){
		//新建不保留当前选择发票 --- 至选择发票为非选中
		$('input[class="voucher_checkbox"]:checked').each(function(){ 
			$(this).next().click();
		}); 
		voucherStrCreate();
	})
	
	//删除凭证
	$("body").on("click","#delete-voucher",function(){
		if($("#voucher-ul").find(".voucher-current").hasClass("voucher_has_save")){
			var id = $("#voucher-ul").find(".voucher-current").data("id");
			$.ajax({
		 	     type: 'POST',
		 	     url: ctx+'/voucher/tVoucher/deleteVoucher',
		 	     cache:false,  
		 	     dataType:'json',
		 	     data: {
		 	    	 "id":id
		 	     } ,
		 	     success: function(data){
		 	    	 if(data.suc){
		 	    		//alert("删除成功!");
		 	    		messagePop("删除成功!");
		 	    		//删除后当前页面的单据张数减少  TOOD是否要做
		 	    		voucherDataForPage.voucherLiNum = voucherDataForPage.voucherLiNum - 1;
		 	    		
		 	    		var current = $("#voucher-ul").find(".voucher-current");
						if($(current).prev().length < 1){
							//alert("前面已经没有了");
							messagePop("前面已经没有了");
							$(current).remove();
							return false;
						}else{
							$(current).hide();
							$(current).prev().addClass("voucher-current").show();
							$(current).remove();
						}
		 	    	 }else{
		 	    		//alert("删除失败!");
		 	    		messagePop("删除失败!");
		 	    	 }
		 	     }

		 	});
		}else{
			//当前凭证不可删除
			//alert("当前凭证不可删除");
			
			var current = $("#voucher-ul").find(".voucher-current");
			if($(current).prev().length < 1){
				//alert("前面已经没有了");
				messagePop("前面已经没有了");
				$(current).remove();
				return false;
			}else{
				$(current).hide();
				$(current).prev().addClass("voucher-current").show();
				$(current).remove();
			}
			//删除后当前页面的单据张数减少  TOOD是否要做
		    voucherDataForPage.voucherLiNum = voucherDataForPage.voucherLiNum - 1;
		    		
		}
	})

	//向左翻
	$("body").on("click",".i-left",function(){
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
	$("body").on("click",".i-right",function(){
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

////////////////////////////////////////////////////////////事件绑定
 $(document).ready(function() {

	 voucherOperation();//上方列表操作栏
		voucherSelect();//上方列表复选操作
		listOperation();//上方列表 列表操作按钮
		dataTovoucher();//上方选中点击凭证编辑
		voucherFunction();//录入凭证方法
		voucherTemplate();//凭证模板有关操作
		newOrDeleteVoucher();//新建或删除凭证  左右翻页

		_focs = $(".switch .lay"),
	
	$("body").on("mouseover mouseout",".ul-bill li",function(){
		 if(event.type == "mouseover"){
			 $(this).find(".a-del").show();
			 $(this).find(".a-wrong").show();
		 }else if(event.type == "mouseout"){
			$(this).find(".a-del").hide();
			$(this).find(".a-wrong").hide();
		}
	});

	//标错
	$("body").on("click","#sign-error-reson-cancel,#scrap-cancel",function(){
		$(".popUp").hide();
		$(".mask").hide();
	})

	$("body").on("click",".i-close",function(){
		$(".popUp").hide();
		$(".mask").hide();
	})

	$("body").on("mouseover",".tab-voucher tr:gt(0)",function(){
		$(this).children(".col-operate").addClass("on");
	    $(this).children(".col-option").find(".option").css("visibility","visible");
	})

	
	$("body").on("mouseleave",".tab-voucher tr:gt(0)",function(){
		$(this).children(".col-operate").removeClass("on");
	    $(this).children(".col-option").find(".option").css("visibility","hidden");
	})

	// 借方贷方金额
	$("body").on("click",".col-dc",function(){
		var inpStr = $("<input type='text'>");
		var dcVal = $(this).find(".cell-val").text();
		if(dcVal.length > 0){
			inpStr = $("<input type='text' value='"+dcVal+"'>");
			var firstInt = dcVal.substr(0,dcVal.length -2); 
			var secondFloat = dcVal.substr(dcVal.length -2,dcVal.length);
			dcVal = firstInt+"."+secondFloat;
			$(this).find(".cell-val").text('');
		}
		
		if($(this).find("input").length==0){
			$(this).append(inpStr);
		}else{
			$(this).find("input").val(dcVal);
			$(this).find("input").show();
		}
		$(this).find("input").focus();
		$(this).find("input").blur(function(){
			$(this).hide();
			var inpVal = parseFloat($(this).val()).toFixed(2);
			if(!isNaN(inpVal)){
				$(this).prev(".cell-val").text(inpVal.replace(".",""));
			}
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

	//凭证会计科目
	$("body").on("click","td.col-subject",function(event){
		event.stopPropagation();
		$("td.col-subject").removeClass("cellOn");
		var inpStr = $("<input type='text' class='tAccount-input'>");
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
		var addTop = clickTop-editTop+37;
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
		var id = $(this).data("id");
		$(".cellOn").find("input").hide();
		var amount = $(".cellOn").find(".cell-val").text(_liVal).data("amountid",id).data("amount");
		if(isNaN(amount)){
			amount = 0;
		}
		var dc = $(this).data("dc");
		if(dc == '借'){
			$(".cellOn").next().next().find(".debit-val").text(amount).click();
			$(".cellOn").next().next().next().find(".credit-val").text('');
		}else if(dc == '贷'){
			$(".cellOn").next().next().find(".debit-val").text('');
			$(".cellOn").next().next().next().find(".credit-val").text(amount).click();
		}
		$(".ul-select").hide();
	})

	$("body").click(function(){
		$(".ul-select").hide();
		$(".cellOn").find("input").hide();
	})

	// 凭证字下拉
	var wordFlag = false,
		wordStrFlag = false;
	$("body").on("click",".ui-combo-wrap",function(event){
		event.stopPropagation();
		var clickTop = $(this).offset().top;
		var clickLeft = $(this).offset().left;
		var editTop = $(".edit").offset().top;
		//var editLeft = $(this).offset().left;
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

	
	// 隐藏编辑  显示编辑
	var winHeight = $(window).height();
	$(".edit").css("top",winHeight-27+"px");
	var editheight = $(".edit").height();
	
	$("body").on("click",".a-edit",function(){
		if(!$(".edit").hasClass("editOn")){
			$(".edit").css("top",winHeight-editheight+"px");
			$(".edit").addClass("editOn");
		}else{
			$(".edit").css("top",winHeight-27+"px");
			$(".edit").removeClass("editOn");
		}
	})
		
	//点击影像
	$("body").on("click",".images .tobig",function(){
		var src = $(this).children("img").data("src");
		$("#tobig").children("img").attr("src",src);
		$("#tobig").show();
		$(".mask").show();
	})

	$("body").on("click",".tobig-i",function(){
		$("#tobig").hide();
		$(".mask").hide();
	})

	
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
	//messagePop();

	$("body").on("click",".i-mess",function(){
		$(".message-pop").hide();
	})
	
});

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


 //保存信息后 删除当前凭证录入的current TODO

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
 	var voucherTitleName =   $(".voucher-current").find(".input-txt").val();
 	//凭证字号
 	var voucherTitleNumber = $(".voucher-current").find(".ui-spinbox").val();
 	//记账日期
 	var accountDate = $(".voucher-current").find(".ui-datepicker-input").val();
 	//账期  当前的年和月
 	var accountPeriod = voucherDataForPage.currentYear+""+ voucherDataForPage.currentMonth;
 	//单据数
 	//var recieptNumber =  $(".voucher-current").find(".attach-wrap").val();
 //	var recieptNumber =  $(".voucher-current").find(".ui-input").val();
 	var length = $('input[class="voucher_checkbox"]:checked').length;
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
 		//alert("第1条分录摘要不能为空！");
 		messagePop("第1条分录摘要不能为空！");
 		return false;
 	}
 	
 	if(flagAccount){
 		$(noinputaccountName).click();
 		return false;
 	}
 	
 	if(validTr < 2){
 		//alert("请录入至少二条有效分录！");
 		messagePop("请录入至少二条有效分录！");
 		return false;
 	}
 	
 	
 	var billInfoStr='';
 	$('input[class="voucher_checkbox"]:checked').each(function(){ 
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
 	    		$('input[class="voucher_checkbox"]:checked').each(function(){ 
 	    	 		$("#billinfoi-bean-"+$(this).data("id")).remove();
 	    		}); 
 	    		//alert("保存成功");
 	    		messagePop("保存成功");
 	    	}else{
 	    		//保存失败提示
 	    		//alert("保存失败");
 	    		messagePop("保存失败");
 	    	}
 	     }

 	});
 }

 function voucherStrCreate(){
	 
	$(".voucher-li").removeClass("voucher-current").hide();
	 
	 voucherDataForPage.voucherLiNum = voucherDataForPage.voucherLiNum +1;
		var voucherData = {
				defaultVoucherTitle:voucherDataForPage.defaultVoucherTitle,
				voucherTitleNumber:voucherDataForPage.voucherTitleNumber,
				currentYear:voucherDataForPage.currentYear,
				currentMonth:voucherDataForPage.currentMonth,
				CurrentLastYYYYMMDD:voucherDataForPage.CurrentLastYYYYMMDD,
				voucherLiNum:voucherDataForPage.voucherLiNum,
				billinfo:[]
		};
		var chk_value =[]; 
		$('input[class="voucher_checkbox"]:checked').each(function(){ 
			var billInfo = {
				'amount': $(this).data("amount"),
				 'abstractmsg':$(this).data("abstractmsg")
			};
			chk_value.push(billInfo); 
		}); 
		var arrLength = chk_value.length;
		if(arrLength < 2){
			for(var i = 0 ; i < 2 - arrLength; i++){
				var billInfo = {};
				chk_value.push(billInfo);
			}
		}
		voucherData.billinfo = chk_value;
		var voucherDataArr = [voucherData];

		var source = $("#templateBillInfoToVoucher").html();
	
		var template = Handlebars.compile(source);
		
		var result = template(voucherDataArr);
	 
		$("#voucher-ul").append(result);
 }
 
 //根据模板类型获取模板
 function getVoucherTemplateByType(){
	 $.ajax( {    
		    url:ctx+'/vouchertemplate/tVoucherTemplate/findAllListByType', 
		    data:{    
		    	"templateTypeId":$("#voucher-template-import-type").val()
		    },    
		    type:'post',    
		    cache:false,  
		    async: false,
		    dataType:'json',    
		    success:function(data) {    
		    	var str = "";
		    	for(var i in data){
		    		str += '<option value="'+data[i].id+'">'+data[i].templateName+'</option>'
		    	}
		    	$("#voucher-template-import-select").html(str);
		     }  
		});
 }
 
 //根据模板id获取模板摘要
 function getVoucherTemplateExpByTID(id){
	 $.ajax( {    
		    url:ctx+'/vouchertemplateexp/tVoucherTemplateExp/getByTemplateId', 
		    data:{    
		    	"id":id
		    },    
		    type:'post',    
		    cache:false,  
		    async: false,
		    dataType:'json',    
		    success:function(data) {    
		    	voucherDataForPage.voucherLiNum = voucherDataForPage.voucherLiNum +1;
				var voucherData = {
						defaultVoucherTitle:voucherDataForPage.defaultVoucherTitle,
						voucherTitleNumber:voucherDataForPage.voucherTitleNumber,
						currentYear:voucherDataForPage.currentYear,
						currentMonth:voucherDataForPage.currentMonth,
						CurrentLastYYYYMMDD:voucherDataForPage.CurrentLastYYYYMMDD,
						voucherLiNum:voucherDataForPage.voucherLiNum,
						billinfo:[]
				};
				var chk_value =[]; 
				for(var i in data){
					var billInfo = {
							'accountId': data[i].accountId,
							'accountName': data[i].accountName,
							 'exp': data[i].exp,
							 'debit':data[i].debit,
							 'credit':data[i].credit
						}; 
						chk_value.push(billInfo); 
				}
				var arrLength = chk_value.length;
				if(arrLength < 4){
					for(var i = 0 ; i < 4 - arrLength; i++){
						var billInfo = {};
						chk_value.push(billInfo);
					}
				}
				voucherData.billinfo = chk_value;
				var voucherDataArr = [voucherData];
				
				$(".voucher-li").removeClass("voucher-current").hide();
				
				var source = $("#templateBillInfoToVoucherImport").html();
				
				var template = Handlebars.compile(source);
				
				var result = template(voucherDataArr);

				$("#voucher-ul").append(result);
				
				$("#voucher-template-import-dialog").hide();
				
				countVoucherTotal();
		     }  
		});
 }
