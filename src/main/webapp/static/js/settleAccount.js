$(function(){
	voucherCellVal();//凭证金额
	//$("body").on("click","#settleAccount",function(){
		$.ajax({
		     type: 'POST',
		     url: ctx+'/settleAccounts/settleAccounts/settleAccounts',
		     cache:false,  
		     dataType:'json',
		     data: {
		    	 
		     },
		     success: function(data){
		    	 var str = '';
		    	 //结转销售成本
		    	 if(data.carryForwardCostSales != undefined){
		    		 str += carryForwardCostSales(data.carryForwardCostSales);
		    		
		    	 }
		    	 
		    	 //计提工资
		    	 if(data.planRaiseWages != undefined){
		    		 str += planRaiseWagesFun(data.planRaiseWages);
		    	 }
		    	 
		    	 //代扣五险一金
		    	 if(data.fiveSocialInsuranceHousingFund != undefined){
		    		 str += fiveSocialInsuranceHousingFundFun(data.fiveSocialInsuranceHousingFund);
		    	 }
		    	 
		    	 //代扣个税
		    	 if(data.withholdingTax != undefined){
		    		 str += withholdingTaxFun(data.withholdingTax);
		    	 }
		    	 
		    	 //计提单位承担五险一金
		    	 if(data.unitFiveSocialInsuranceHousingFundAccount != undefined){
		    		 str += unitFiveSocialInsuranceHousingFundAccountFun(data.unitFiveSocialInsuranceHousingFundAccount);
		    	 }
		    	 
		    	 
		    	 //计提折旧 
		    	 if(data.provisionDepreciation != undefined){
		    		 str += commonSettleAccount(data.provisionDepreciation);
		    	 }
		    	 
		    	 //摊销待摊费用
		    	 if(data.amortizationExpense != undefined){
		    		 str += commonSettleAccount(data.amortizationExpense);
		    	 }
		    	 
		    	 //无形资产
		    	 if(data.immaterialAssets != undefined){
		    		 str += commonSettleAccount(data.immaterialAssets);
		    	 }
		    	 
		    	 //计提税金
		    	 if(data.taxPlanning != undefined){
			    		str += taxPlanningFun(data.taxPlanning);
			     }
		    	 //计提增值税
		    	 if(data.increaseInValue != undefined){
		    		 	str += increaseInValueFun(data.increaseInValue);
		    	 }
		    	 //结转未交增值税
		    	 if(data.carryForwardNotPayValueAddedTax != undefined){
		    		str += carryForwardNotPayValueFun(data.carryForwardNotPayValueAddedTax);
		    	 }
		    	 
		    	 //结转损益
		    	 if(data.lossGainBroughtForward != undefined){
		    		str += lossGainBroughtForwardFun(data.lossGainBroughtForward);
		    	 }
		    	 //以前年度损益
		    	 if(data.priorPeriod != undefined){
		    		str += priorPeriodFun(data.priorPeriod);
		    	 }
		    	 
		    	 
		    	 /*$(".bg").show();7		*/
		    	 $("#settle-account-ul").html(str);
		    	 $(".settle-wrapper").show();
		    	 
		    	var dataAccountid = $("#carryForwardCostSales-debit").attr("data-accountid");
			 	var dataAccountname = $("#carryForwardCostSales-debit").attr("data-accountname");
			 	var dataAccount = $("#carryForwardCostSales-debit").attr("data-account");
			 	var dataVal = $("#carryForwardCostSales-debit").attr("data-val");
		 		var dataDebitval = $("#carryForwardCostSales-debit").attr("data-debitval");
		    	if(eval(dataVal)>0){//当值大于0 时候
			 		var carryForwardCostSalesStr = '<input type="hidden" id = "carryForwardCostSalesId" class="credit exp" data-summary="'+"结转本期损益"+'" data-accountid="'+dataAccountid+'" data-accountname="'+dataAccountname+'"   data-account="'+dataAccount+'" data-val="'+dataVal+'"  data-debitval="0"   data-creditval="'+dataDebitval+'">';
			 		$("#CarryForwardProfit").after(carryForwardCostSalesStr);
			 		
			 		var datavalTotal = $("#costTotalId").attr("data-val");
			 		var dataTotal = (Number(dataVal)+Number(datavalTotal)).toFixed(2);
			 		$("#costTotalId").html(dataTotal);//修改显示的合计
			 		$("#costDPId").html("本期成本费用合计:"+dataTotal);
			 		$("#costCPId").html("本期成本费用合计:"+dataTotal);
			 		$("#CarryForwardProfit").data("val",dataTotal);	//修改本年利润
					$("#CarryForwardProfit").data("debitval",dataTotal);
		    	}
		     }
		});
//	})
	
	commonOperation();//通用操作

})

//结转销售成本
function carryForwardCostSales(data){
	/*return  '<li>'+'<h6>结转销售成本</h6>'
	+'<p class="p-num">'+data.inventoryBalanceValue+'</p>'
	+'<div class="p-txt"><i><div class="tc"><em></em>'
	+'<p>本期主营业收入：'+data.primeOperatingRevenuePercentageValue+'</p>'
	+'<p>应缴城市维护建设税税率 <input type="text"> %</p><p>教育费附加税率 <input type="text"> %</p><p>地方教育费附加税率 <input type="text"> %</p></div></i>凭证已自动生成</div>'
	+'</li>';*/
	var inventoryBalanceValue =  data.inventoryBalanceValue; //库存余额
	var primeOperatingRevenuePercentageValue = data.primeOperatingRevenuePercentageValue;//主营业务收入 * 结转百分比 条件不满足
	var carryForwardPercentage = data.carryForwardPercentage;//结转比例
	var msgInfo = "请设置结转比例";
	if(eval(inventoryBalanceValue)<eval(primeOperatingRevenuePercentageValue)){
		msgInfo= "库存不足";
	}
	if(eval(carryForwardPercentage)==0){
		msgInfo= "请设置结转比例";
	}
	
	return '<li id = "carryForwardCostSales">'
	+'<input id="primeOperatingRevenueValue" type="hidden" value="'+data.primeOperatingRevenueValue+'">'
	+'<input id="carryForwardCostSales-debit" type="hidden" class="debit exp" data-summary="'+"结转主营业务成本"+'" data-accountid="'+data.primeOperatingRevenueAcc.id+'"  data-accountname="'+data.primeOperatingRevenueAcc.accountName+'"  data-account="'+data.primeOperatingRevenueAcc.accuntId+' '+ data.primeOperatingRevenueAcc.accountName+'" data-val="'+data.primeOperatingRevenuePercentageValue+'"   data-debitval="'+data.primeOperatingRevenuePercentageValue+'"   data-creditval="0">'
	+'<input id="carryForwardCostSales-credit" type="hidden" class="credit exp" data-summary="'+"结转主营业务成本"+'" data-accountid="'+data.inventoryBalanceAcc.id+'" data-accountname="'+data.inventoryBalanceAcc.accountName+'"   data-account="'+data.inventoryBalanceAcc.accuntId+' '+ data.inventoryBalanceAcc.accountName+'" data-val="'+data.primeOperatingRevenuePercentageValue+'"  data-debitval="0"   data-creditval="'+data.primeOperatingRevenuePercentageValue+'">'
	+'<h6>结转销售成本</h6><span id="msgInfos" style="font-size:12px;text-indent:8px;line-height:24px;float:left;color: red;">'+msgInfo+'</span><p id="primeOperatingRevenueValue-show" class="p-num">'+data.primeOperatingRevenuePercentageValue+'</p><div class="p-txt">'
	+'<i><div class="tc"><em></em><p>本期主营业务收入:'+data.primeOperatingRevenueValue+'</p><p>结转百分比: <input id="carryForwardCostSales-percent" type="text" value="'+data.carryForwardPercentage+'"> % <button id="carryForwardCostSales-percent-button" style="display:none;">保存</button></p><p><span>库存商品余额:'+data.inventoryBalanceValue+'</span><input id = "inventoryBalanceValue" type="hidden" value="'+data.inventoryBalanceValue+'"></input></p></div></i>'
	+'<a class="voucher-auto-create">凭证已自动生成</a></div></li>';
	
	
}

//计提工资
function planRaiseWagesFun(data){
	return '<li id = "planRaiseWagesFun";>'
	
	+'<input type="hidden" class="debit exp" data-summary="'+data.exp+'" data-accountid="'+data.manageWageAccount.id+'" data-accountname="'+data.manageWageAccount.accountName+'"   data-account="'+data.manageWageAccount.accuntId+' '+ data.manageWageAccount.accountName+'" data-val="'+data.val+'" data-debitval="'+data.val+'"   data-creditval="0">'
	+'<input type="hidden" class="credit exp" data-summary="'+data.exp+'" data-accountid="'+data.employeesWageAccount.id+'"  data-accountname="'+data.employeesWageAccount.accountName+'"  data-account="'+data.employeesWageAccount.accuntId+' '+ data.employeesWageAccount.accountName+'" data-val="'+data.val+'"  data-debitval="0"   data-creditval="'+data.val+'">'
	+'<h6>计提工资</h6><p class="p-num">'+data.val+'</p><div class="p-txt">'
	+'<a class="voucher-auto-create">凭证已自动生成</a></div></li>';
}

//代扣五险一金
function fiveSocialInsuranceHousingFundFun(data){
	var str = 	'<li id = "fiveSocialInsuranceHousingFundFun"><input type="hidden" class="debit exp" data-summary="'+data.exp+'" data-accountid="'+data.employeesWageAccount.id+'"  data-accountname="'+data.employeesWageAccount.accountName+'"  data-account="'+data.employeesWageAccount.accuntId+' '+ data.employeesWageAccount.accountName+'" data-val="'+data.val+'"   data-debitval="'+data.val+'"   data-creditval="0">';
	for(var i in data.credits){
		var credit = data.credits[i];
		str +='<input type="hidden" class="credit exp" data-summary="'+data.exp+'" data-accountid="'+credit.creditAccount.id+'" data-accountname="'+credit.creditAccount.accountName+'"   data-account="'+credit.creditAccount.accuntId+' '+ credit.creditAccount.accountName+'" data-val="'+credit.val+'"  data-debitval="0"   data-creditval="'+credit.val+'">';
	}
	str +='<h6>代扣五险一金</h6><p class="p-num">'+data.val+'</p><div class="p-txt">';
	str +='<a class="voucher-auto-create">凭证已自动生成</a></div></li>';
	return str;
}

//代扣个税
function withholdingTaxFun(data){
	return '<li id = "withholdingTaxFun">'
	+'<input type="hidden" class="debit exp" data-summary="'+data.exp+'" data-accountid="'+data.manageWageAccount.id+'"  data-accountname="'+data.manageWageAccount.accountName+'"  data-account="'+data.manageWageAccount.accuntId+' '+ data.manageWageAccount.accountName+'" data-val="'+data.val+'"   data-debitval="'+data.val+'"   data-creditval="0">'
	+'<input type="hidden" class="credit exp" data-summary="'+data.exp+'" data-accountid="'+data.individualIncomeTaxAccount.id+'" data-accountname="'+data.individualIncomeTaxAccount.accountName+'"   data-account="'+data.individualIncomeTaxAccount.accuntId+' '+ data.individualIncomeTaxAccount.accountName+'" data-val="'+data.val+'"  data-debitval="0"   data-creditval="'+data.val+'">'
	+'<h6>代扣个税</h6><p class="p-num">'+data.val+'</p><div class="p-txt">'
	+'<a class="voucher-auto-create">凭证已自动生成</a></div></li>';
}

//计提单位承担五险一金
function unitFiveSocialInsuranceHousingFundAccountFun(data){
	var str = 	'<li id ="unitFiveSocialInsuranceHousingFundAccountFun"><input type="hidden" class="debit exp" data-summary="'+data.exp+'" data-accountid="'+data.unitFiveSocialInsuranceHousingFundAccount.id+'"  data-accountname="'+data.unitFiveSocialInsuranceHousingFundAccount.accountName+'"  data-account="'+data.unitFiveSocialInsuranceHousingFundAccount.accuntId+' '+ data.unitFiveSocialInsuranceHousingFundAccount.accountName+'" data-val="'+data.val+'"   data-debitval="'+data.val+'"   data-creditval="0">';
	for(var i in data.credits){
		var credit = data.credits[i];
		str +='<input type="hidden" class="credit exp" data-summary="'+data.exp+'" data-accountid="'+credit.creditAccount.id+'" data-accountname="'+credit.creditAccount.accountName+'"   data-account="'+credit.creditAccount.accuntId+' '+ credit.creditAccount.accountName+'" data-val="'+credit.val+'"  data-debitval="0"   data-creditval="'+credit.val+'">';
	}
	str +='<h6>计提单位承担五险一金</h6><p class="p-num">'+data.val+'</p><div class="p-txt">';
	str +='<a class="voucher-auto-create">凭证已自动生成</a></div></li>';
	return str;
}

//计提折旧  摊销待摊费用 无形资产
function commonSettleAccount(result){
	
	var str = '';
	var name = result.name;
	var dataArr = result.data;
	for(var i in dataArr){
		var data = dataArr[i];
		var idInfo = "";
		switch (result.name)
		{
			case "计提折旧":
				idInfo="provisionDepreciation"+i;
				break;
			case "无形资产":
				idInfo="immaterialAssets"+i;
				break;
			case "摊销待摊费用":
				idInfo="amortizationExpense"+i;
				break;
		}
		str += '<li id="'+idInfo+'">'
				+'<input type="hidden" class="debit exp" data-summary="'+result.exp+'" data-accountid="'+data.debitAccount.id+'"  data-accountname="'+data.debitAccount.accountName+'"  data-account="'+data.debitAccount.accuntId+' '+ data.debitAccount.accountName+'" data-val="'+data.val+'"   data-debitval="'+data.val+'"   data-creditval="0">'
				+'<input type="hidden" class="credit exp" data-summary="'+result.exp+'" data-accountid="'+data.creditAccount.id+'" data-accountname="'+data.creditAccount.accountName+'"   data-account="'+data.creditAccount.accuntId+' '+ data.creditAccount.accountName+'" data-val="'+data.val+'"  data-debitval="0"   data-creditval="'+data.val+'">';
				if(name == "计提折旧"){
					str += '<div class="txtbanner sure-settle-account"><a href="#" onclick="detailedList()">清单</a></div>';
				}
				str += '<h6>'+name+'</h6><p class="p-num">'+data.val+'</p><div class="p-txt"><a class="voucher-auto-create">凭证已自动生成</a></div></li>';
	}
	return str;
	
	/*return '<li>'+'<h6>'+data.acc.accountName+'</h6>'
			+'<input type="hidden" class="debit" data-summary="'+data.acc.accountName+'" data-accountid="'+data.acc.accuntId+'" data-account="'+data.acc.accuntId+' '+ data.acc.accountName+'" data-val="'+data.val+'">'
			+'<input type="hidden" class="credit" data-summary="'+data.acc.accountName+'" data-accountid="'+data.acc.accuntId+'" data-account="'+data.acc.accuntId+' '+ data.acc.accountName+'" data-val="'+data.val+'">'
			+'<h6>'+data.acc.accountName+'</h6><p class="p-num">'+data.val+'</p><div class="p-txt"><a class="voucher-auto-create">凭证已自动生成</a></div></li>';
*/
	}
//跳转到清单页面
function detailedList(){
	var rel="link_21";
    var text="自动摊销";
    var famsrc=ctx+'/amortize/tAmortize/list';
    window.location.href=famsrc; 	   
//    $(window.parent.addTicket(rel,text,famsrc));
//    window.parent.$("iframe").last().show();
}

//计提税金
function taxPlanningFun(data){
	var	str = '<li id = "taxPlanningFun">'
		+'<input type="hidden" class="debit exp" data-summary="'+data.exp+'" data-accountid="'+data.businessTaxesAdditionalAccount.id+'"  data-accountname="'+data.businessTaxesAdditionalAccount.accountName+'"  data-account="'+data.businessTaxesAdditionalAccount.accuntId+' '+ data.businessTaxesAdditionalAccount.accountName+'" data-val="'+data.businessTaxesAdditionalVal+'"   data-debitval="'+data.businessTaxesAdditionalVal+'"   data-creditval="0">';
		for(var i in data.credit){
			var accountdata = data.credit[i];
			str +='<input type="hidden" class="credit exp" data-summary="'+data.exp+'" data-accountid="'+accountdata.account.id+'" data-accountname="'+accountdata.account.accountName+'"   data-account="'+accountdata.account.accuntId+' '+ accountdata.account.accountName+'" data-val="'+accountdata.val+'"  data-debitval="0"   data-creditval="'+accountdata.val+'">'
		}
		str +='<div class="txtbanner sure-settle-account"><a href="javascript:void(0)" onclick="taxSetting()">税项设置</a></div>'
			+'<h6>计提税金</h6><p class="p-num">'+data.businessTaxesAdditionalVal+'</p><div class="p-txt"><a class="voucher-auto-create">凭证已自动生成</a></div></li>';
	return str;
}
//计提增值税
function increaseInValueFun(data){
	return '<li id = "increaseInValueFun">'
	+'<input type="hidden" class="credit exp" data-summary="'+data.exp+'" data-accountid="'+data.primeOperatingRevenueAccount.id+'"  data-accountname="'+data.primeOperatingRevenueAccount.accountName+'"  data-account="'+data.primeOperatingRevenueAccount.accuntId+' '+ data.primeOperatingRevenueAccount.accountName+'" data-val="'+data.valDebit+'"   data-debitval="0"   data-creditval="'+data.valDebit+'">'
	+'<input type="hidden" class="credit exp" data-summary="'+data.exp+'" data-accountid="'+data.shouldPayTaxesAccount.id+'" data-accountname="'+data.shouldPayTaxesAccount.accountName+'"   data-account="'+data.shouldPayTaxesAccount.accuntId+' '+ data.shouldPayTaxesAccount.accountName+'" data-val="'+data.valCredit+'"  data-debitval="0"   data-creditval="'+data.valCredit+'">'
	+'<h6>计提增值税</h6><p class="p-num">'+data.valCredit+'</p><div class="p-txt"><a class="voucher-auto-create">凭证已自动生成</a></div></li>';
}


//税项设置
function taxSetting(){
	var rel="link_23";
    var text="税种设置";
    var famsrc=ctx+'/taxbase/tTaxbase/list';
    window.location.href=famsrc; 
//    $(window.parent.addTicket(rel,text,famsrc));
//    window.parent.$("iframe").last().show();
}

//结转未增销售成本
function carryForwardNotPayValueFun(data){
	return '<li id = "carryForwardNotPayValueFun">'
	+'<input type="hidden" class="debit exp" data-summary="'+data.exp+'" data-accountid="'+data.debitAccount.id+'"  data-accountname="'+data.debitAccount.accountName+'"  data-account="'+data.debitAccount.accuntId+' '+ data.debitAccount.accountName+'" data-val="'+data.debitVal+'"   data-debitval="'+data.debitVal+'"   data-creditval="0">'
	+'<input type="hidden" class="credit exp" data-summary="'+data.exp+'" data-accountid="'+data.creditAccount.id+'" data-accountname="'+data.creditAccount.accountName+'"   data-account="'+data.creditAccount.accuntId+' '+ data.creditAccount.accountName+'" data-val="'+data.creditVal+'"  data-debitval="0"   data-creditval="'+data.creditVal+'">'
	+'<h6>结转未增销售成本</h6><p class="p-num">'+data.debitVal+'</p><div class="p-txt"><a class="voucher-auto-create">凭证已自动生成</a></div></li>';
}

//结转损益
function lossGainBroughtForwardFun(result){
		
	var str = "";
	//借
	if(result.rofitLossAccountDebitVal != undefined && result.rofitLossAccountDebitVal.length > 0 && result.rofitLossAccountDebitVal > 0){
		var rofitLossAccountDebit = result.rofitLossAccountDebit;
		str +='<li id = "lossGainBroughtCost">'
		+'<input id ="CarryForwardProfit" type="hidden" class="debit exp" data-summary="'+"结转本期损益"+'" data-accountid="'+result.currentProfitAccount.id+'"  data-accountname="'+result.currentProfitAccount.accountName+'"  data-account="'+result.currentProfitAccount.accuntId+' '+ result.currentProfitAccount.accountName+'" data-val="'+result.rofitLossAccountDebitVal+'"   data-debitval="'+result.rofitLossAccountDebitVal+'"   data-creditval="0">';
		for(var i in rofitLossAccountDebit){
			var data = rofitLossAccountDebit[i];
			str +='<input type="hidden" class="credit exp" data-summary="'+"结转本期损益"+'" data-accountid="'+data.debitAccount.id+'" data-accountname="'+data.debitAccount.accountName+'"   data-account="'+data.debitAccount.accuntId+' '+ data.debitAccount.accountName+'" data-val="'+data.val+'"  data-debitval="0"   data-creditval="'+data.val+'">';
		}
		str +='<h6>结转本期损益(成本费用)</h6><p id= "costTotalId" class="p-num" data-val="'+result.rofitLossAccountDebitVal+'">'+result.rofitLossAccountDebitVal+'</p><div class="p-txt">'
		+'<i><div class="tc"><em></em><p>本期收入合计:'+result.rofitLossAccountCreditVal+'</p><p id = "costDPId">本期成本费用合计:'+result.rofitLossAccountDebitVal+'</p></div></i>'
		+'<a class="voucher-auto-create">凭证已自动生成</a></div></li>';
	}
	//贷
	if(result.rofitLossAccountCreditVal != undefined && result.rofitLossAccountCreditVal.length > 0 && result.rofitLossAccountCreditVal > 0){
		var currentProfitAccount = result.rofitLossAccountCredit;
		str +='<li id = "lossGainBroughtInCome">';
			for(var i in currentProfitAccount){
				var data = currentProfitAccount[i];
				console.info(data);
				str +='<input type="hidden" class="debit exp" data-summary="'+"结转本期损益"+'" data-accountid="'+data.debitAccount.id+'"  data-accountname="'+data.debitAccount.accountName+'"  data-account="'+data.debitAccount.accuntId+' '+ data.debitAccount.accountName+'" data-val="'+data.val+'"   data-debitval="'+data.val+'"   data-creditval="0">';
			}
			str +='<input type="hidden" class="credit exp" data-summary="'+"结转本期损益"+'" data-accountid="'+result.currentProfitAccount.id+'" data-accountname="'+result.currentProfitAccount.accountName+'"   data-account="'+result.currentProfitAccount.accuntId+' '+ result.currentProfitAccount.accountName+'" data-val="'+ result.rofitLossAccountCreditVal+'"  data-debitval="0"   data-creditval="'+result.rofitLossAccountCreditVal+'">'
			+'<h6>结转本期损益(收入)</h6><p class="p-num">'+result.rofitLossAccountCreditVal+'</p><div class="p-txt">'
			+'<i><div class="tc"><em></em><p>本期收入合计:'+result.rofitLossAccountCreditVal+'</p><p id = "costCPId" >本期成本费用合计:'+result.rofitLossAccountDebitVal+'</p></div></i>'
			+'<a class="voucher-auto-create">凭证已自动生成</a></div></li>';
	}	
	return str;
}
//以前年度损益
function priorPeriodFun(data){
	var str;
	if( data.iscredit){	//同为贷方
		str = '<li id = "priorPeriodFun">'
			+'<input type="hidden" class="credit exp" data-summary="'+data.exp+'" data-accountid="'+data.debitAccount.id+'"  data-accountname="'+data.debitAccount.accountName+'"  data-account="'+data.debitAccount.accuntId+' '+ data.debitAccount.accountName+'" data-val="'+data.val+'"     data-debitval="0"   data-creditval="'+data.val+'">'
			+'<input type="hidden" class="credit exp" data-summary="'+data.exp+'" data-accountid="'+data.creditAccount.id+'" data-accountname="'+data.creditAccount.accountName+'"   data-account="'+data.creditAccount.accuntId+' '+ data.creditAccount.accountName+'" data-val="'+data.creditAccount.creditTotal+'" data-debitval="'+data.creditAccount.debitTotal+'"   data-creditval="'+data.creditAccount.creditTotal+'">'
			+'<h6>结转以前年度损益</h6><p class="p-num">'+data.val+'</p><div class="p-txt"><a class="voucher-auto-create">凭证已自动生成</a></div></li>';

	}else{
		str = '<li id = "priorPeriodFun">'
		+'<input type="hidden" class="debit exp" data-summary="'+data.exp+'" data-accountid="'+data.debitAccount.id+'"  data-accountname="'+data.debitAccount.accountName+'"  data-account="'+data.debitAccount.accuntId+' '+ data.debitAccount.accountName+'" data-val="'+data.val+'"  data-debitval="'+data.val+'" data-creditval="0">'
		+'<input type="hidden" class="credit exp" data-summary="'+data.exp+'" data-accountid="'+data.creditAccount.id+'" data-accountname="'+data.creditAccount.accountName+'"   data-account="'+data.creditAccount.accuntId+' '+ data.creditAccount.accountName+'" data-val="'+data.val+'"  data-debitval="'+data.creditAccount.debitTotal+'"   data-creditval="'+data.creditAccount.creditTotal+'">'
		+'<h6>结转以前年度损益</h6><p class="p-num">'+data.val+'</p><div class="p-txt"><a class="voucher-auto-create">凭证已自动生成</a></div></li>';

	}
	return str;
}

//查看凭证展示方法
function viewVoucher(_this){
	var li = $(_this).parent().parent();
	var str = '<input id= "voucherInfoId" type="hidden"value="'+li.attr("id")+'"/>';
	
	//号
	getRememberWord();
	
	//借
	var debitTotal = 0;
	var creditTotal = 0;
	var countNum = 0;
	$(li).find(".debit").each(function(e){
		var sum = $(this).data("summary");
		var account = $(this).data("account");
		var balance = $(this).data("val");
		var val = $(this).data("val").toString().replace(".","");
		str +='<tr class="entry-item "><td class="col-operate"><div class="add-delete"><a class="a-add" href="javascript:;"></a></div></td><td class="col-summary" data-edit="summary"><div class="cell-val summary-val">'+sum+'</div></td><td class="col-subject" data-edit="subject"><div class="cell-val subject-val"><p>'+account+'</p>';
//		var msg = "余额：-"+balance;
//		str +='<p>'+msg+'</p>';
		str +='</div></td><td class="col-debite col-dc" data-edit="money"><div class="cell-val debit-val">'+val+'</div></td><td class="col-credit col-dc" data-edit="money"><div class="cell-val credit-val"></div></td><td class="col-delete"><div class="add-delete"><a class="a-delete" href="javascript:;"></a></div></td></tr>';
		countNum +=1;
		debitTotal  +=  parseFloat(balance);
	
	})
	//贷
	$(li).find(".credit").each(function(e){
		var sum = $(this).data("summary");
		var account = $(this).data("account");
		var balance = $(this).data("val");
		var val = $(this).data("val").toString().replace(".","");
		str +='<tr class="entry-item "><td class="col-operate"><div class="add-delete"><a class="a-add" href="javascript:;"></a></div></td><td class="col-summary" data-edit="summary"><div class="cell-val summary-val">'+sum+'</div></td><td class="col-subject" data-edit="subject"><div class="cell-val subject-val"><p>'+account+'</p>';
//		var msg = "余额：+"+balance;
//		str +='<p>'+msg+'</p>';
		str +='</div></td><td class="col-debite col-dc" data-edit="money"><div class="cell-val debit-val"></div></td><td class="col-credit col-dc" data-edit="money"><div class="cell-val credit-val">'+val+'</div></td><td class="col-delete"><div class="add-delete"><a class="a-delete" href="javascript:;"></a></div></td></tr>';
		countNum +=1;
		creditTotal  +=  parseFloat(balance);
	
	})
	
	//至少显示五行
	if(countNum < 5){
		for(;countNum<5;countNum++){
			str +='<tr class="entry-item "><td class="col-operate"><div class="add-delete"><a class="a-add" href="javascript:;"></a></div></td><td class="col-summary" data-edit="summary"><div class="cell-val summary-val"></div></td><td class="col-subject" data-edit="subject"><div class="cell-val subject-val"><p></p></div></td><td class="col-debite col-dc" data-edit="money"><div class="cell-val debit-val"></div></td><td class="col-credit col-dc" data-edit="money"><div class="cell-val credit-val"></div></td><td class="col-delete"><div class="add-delete"><a class="a-delete" href="javascript:;"></a></div></td></tr>';
		}
	}

	debitTotal = roundNum(debitTotal,2);
	creditTotal = roundNum(creditTotal,2);
	//合计
	$("#debit-total").html((debitTotal+"").replace(".",""));
	$("#credit-total").html((creditTotal+"").replace(".",""));
	
	$(".tbody-voucher").html(str);
	$(".bg").show();
	$(".tbvoucher").show();
	$(".tbvoucher").css({
		"height":$(window).height() - 40+"px",
		"margin-top":"20px"
	})
	$(".tbvoucher .body").css({
		"height":$(".tbvoucher").height() -50+"px"
	})
}

//通用操作
function commonOperation(){
	//关闭弹窗
	$("body").on("click",".close-dialog",function(){
		// $(".settle-wrapper").hide();	zt 修改
		
		$(".tbvoucher").hide();

	})
	
	//查看凭证
	$("body").on("click",".voucher-auto-create",function(){
		viewVoucher(this);
	})
	
	//关闭查看凭证
	$("body").on("click",".bill-info-close-dialog",function(){
		var debitTotal = $("#debit-total").html();
		var creditTotal = $("#credit-total").html();
		if(parseFloat(debitTotal) == parseFloat(creditTotal)){
			$(".bg").hide();
			$(".tbvoucher").hide();	
			synAmount();//同步金额数据
		}else{
			alert("金额不平衡");
		}
	})
	
	//确认结账
	$("body").on("click","#sure-settle-account",function(){
		$(this).removeAttr("id");
		var voucherArr = new Array();
		$("#settle-account-ul").find("li").each(function(e){
			var voucherExpArr = new Array();
			var flag = true;
			$(this).find(".exp").each(function(e){
				
				var voucherExp = {
						'exp':$(this).data("summary"),
						'accountId':$(this).data("accountid"),
						'accountName': $(this).data("account"),/*+" "+ $(this).data("accountname"),*/
						'debit':$(this).data("debitval"),
						'credit':$(this).data("creditval")
				}
				var debitvalNum =  $(this).data("debitval");
				var creditvalNum = $(this).data("creditval");
				if(Number(debitvalNum) + Number(creditvalNum) == 0){
					flag = false;
				}
				voucherExpArr.push(voucherExp);
				
			})
			if(flag){
				voucherArr.push(voucherExpArr);	
			}
		})
		console.info(voucherArr);
		$.ajax({
		     type: 'POST',
		     url: ctx+'/voucher/tVoucher/settle',
		     cache:false,  
		     dataType:'json',
		     data: {
		    	'voucherStr':JSON.stringify(voucherArr)

		     } ,
		     success: function(data){
		    	if(data.suc){
		    		messagePop("结账成功");
		    		//隐藏窗口
		    		setTimeout(function() {
		    			$(".settle-wrapper").hide();
			    		$(".bg").hide();
			    		//信息提示 TODO
	//		    		window.parent.$("iframe").last().remove();
	//		    		window.parent.$(".box span#").last().remove();
	//		    		window.parent.$("iframe").last().show();
			    		//当前页面刷新 	
			    		 parent.location.reload();
			    		},2000)
		    	}else{
		    		alert("结账失败");
		    	}
		     }
		});
	})

	//结转百分比改变
	$("body").on("input propertychange","#carryForwardCostSales-percent",function(e){
		var val = $(this).val();
		var primeOperatingRevenueValue = $("#primeOperatingRevenueValue").val();
		var inventoryBalanceValue = $("#inventoryBalanceValue").val();
		var li = $(this).parent().parent().parent().parent().parent();
		var result = accMul(primeOperatingRevenueValue,val)/100;
		var msgInfo = "";
		if(eval(inventoryBalanceValue)<eval(result)){
			msgInfo="库存不足";
		}
		$("#msgInfos").html(msgInfo);
		result	= roundNum(result,2);
		result = result +"";
		$(li).find(".debit").data("debitval",result);
		$(li).find(".credit").data("creditval",result);
		$(li).find(".debit").data("val",result);
		$(li).find(".credit").data("val",result);
		
	/*	$("#carryForwardCostSales-debit").data("debitval",result)
		$("#carryForwardCostSales-credit").data("creditval",result)*/
		//$("#primeOperatingRevenueValue").val(result);
		if(eval(result)>0){//当值大于0 时候
			if($("#carryForwardCostSalesId").length>0){
				$("#carryForwardCostSalesId").data("val",result);
				$("#carryForwardCostSalesId").data("creditval",result);
				
			}else{
				var dataAccountid = $("#carryForwardCostSales-debit").attr("data-accountid");
				var dataAccountname = $("#carryForwardCostSales-debit").attr("data-accountname");
				var dataAccount = $("#carryForwardCostSales-debit").attr("data-account");
				var dataVal = result;
				var dataDebitval = result;
				var carryForwardCostSalesStr = '<input type="hidden" id = "carryForwardCostSalesId" class="credit exp" data-summary="'+"结转本期损益"+'" data-accountid="'+dataAccountid+'" data-accountname="'+dataAccountname+'"   data-account="'+dataAccount+'" data-val="'+dataVal+'"  data-debitval="0"   data-creditval="'+dataDebitval+'">';
				$("#CarryForwardProfit").after(carryForwardCostSalesStr);
			}
		}
		if(eval(result)==0){
			if($("#carryForwardCostSalesId").length>0){
				$("#carryForwardCostSalesId").remove();
			}
		}
		

		var datavalTotal = $("#costTotalId").attr("data-val");
 		var dataTotal = (Number(result)+Number(datavalTotal)).toFixed(2);
 		$("#costTotalId").html(dataTotal);//修改显示的合计
 		$("#costDPId").html("本期成本费用合计:"+dataTotal);
 		$("#costCPId").html("本期成本费用合计:"+dataTotal);
 		
 		$("#CarryForwardProfit").data("val",dataTotal);	//修改本年利润
		$("#CarryForwardProfit").data("debitval",dataTotal);
		
		
		$("#primeOperatingRevenueValue-show").html(result);
		//显示保存按钮
		$("#carryForwardCostSales-percent-button").show();
	})
	
	//结转百分比确认
	$("body").on("click","#carryForwardCostSales-percent-button",function(){
		var val = $("#carryForwardCostSales-percent").val();
		$.ajax({
		     type: 'POST',
		     url: ctx+'/settleAccounts/settleAccounts/updateCustom',
		     cache:false,  
		     async: false,
		     dataType:'json',
		     data: {
		    	"carryForwardPercentage":val
		     } ,
		     success: function(data){
		    	 if(data.suc){
		    		 alert("更新成功");
		    		 $("#carryForwardCostSales-percent-button").hide();
		    		// location.reload();
		    	 }else{
		    		 alert("更新失败");
		    	 }
		     }
		});		
	})
	
	
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

//小数保留
function roundNum(number,fractionDigits){  
    with(Math){  
    	var result = round(number*pow(10,fractionDigits))/pow(10,fractionDigits);  
        return result.toFixed(2);
    }  
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
					//getLastAccountEndBalance($(this).parent().prev().find("p").eq(0).data("amountid"));
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
				//getLastAccountEndBalance( $(this).parent().prev().prev().find("p").eq(0).data("amountid"));
				/*var val = (balance.debittotalamount * 1 )+ ( balance.beginbalance * 1) -( balance.credittotalamount * 1 + floatVal * 1);*/
				//陈明加的
				var val = 0.00  ;
				if(dc == '1'){
					val =balance.endbalance*1-floatVal*1;
				}else if(dc == '-1'){
					var end=balance.endbalance;
					val =-1*end + floatVal*1
				}
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
//	if(debitTotal == creditTotal){
//		var cny = numToCny(toMoneyNum(creditTotal+""));
//		$(".voucher-current").find("#capAmount").text(cny);
//	}
	
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
	
	
   obj.value = obj.value.replace(/[^\d.=-]/g,"");  //清除“数字”和“.”以外的字符  
   obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是.  
   obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
   obj.value = obj.value.replace(/\-{2,}/g,"-"); //只保留第一个. 清除多余的  
   obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$","."); 
   obj.value = obj.value.replace("-","$#$").replace(/\-/g,"").replace("$#$","-");  
   obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数  
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
//同步金额数据
function synAmount(){
	var voucherInfoId = $("#voucherInfoId").val();
	var debitTotal = toMoneyNum($("#debit-total").html());
	$(".voucher-current").find(".debit-val").each(function(e){
		if(!isNaN(parseFloat($(this).text()))  && $(this) != $(obj).parent().find(".cell-val")){
			var debitTotal = toMoneyNum($(this).text());
			var ls = $("#"+voucherInfoId+"").find(".exp").eq(e).attr("data-val");
			$("#"+voucherInfoId+"").find(".exp").eq(e).attr("data-val",debitTotal);
			$("#"+voucherInfoId+"").find(".exp").eq(e).attr("data-debitval",debitTotal);
			$("#"+voucherInfoId+"").find(".exp").eq(e).data("val",debitTotal);
		}
	});
	$(".voucher-current").find(".credit-val").each(function(e){
		if(!isNaN(parseFloat($(this).text()))&& $(this) != $(obj).parent().find(".cell-val")){
			var creditTotal = toMoneyNum($(this).text());
			var ls = $("#"+voucherInfoId+"").find(".exp").eq(e).attr("data-val");
			$("#"+voucherInfoId+"").find(".exp").eq(e).attr("data-val",creditTotal);
			$("#"+voucherInfoId+"").find(".exp").eq(e).attr("data-creditval",creditTotal);
			$("#"+voucherInfoId+"").find(".exp").eq(e).data("val",creditTotal);
		}
	});
	if(parseFloat(debitTotal) == 0){
		debitTotal = $("#"+voucherInfoId+"").find(".exp").eq(2).attr("data-val");
	}
	
	$("#"+voucherInfoId+" p[class='p-num']").html(debitTotal);	
	//特殊处理结转本期损益(成本和收入)
	if(voucherInfoId == "lossGainBroughtCost" || voucherInfoId == "lossGainBroughtInCome"){
		var broughtCost = $("#lossGainBroughtCost p[class='p-num']").html();
		var broughtInCome = $("#lossGainBroughtInCome p[class='p-num']").html();
		var infoDP = "本期收入合计:"+broughtInCome;
		var infoCP = "本期成本费用合计:"+broughtCost;
		var info1 = $("#lossGainBroughtCost div[class='tc']").find("p").html(infoDP);
		var info2 = $("#lossGainBroughtCost div[class='tc']").find("p[id='costDPId']").html(infoCP);
		
		var info1 = $("#lossGainBroughtInCome div[class='tc']").find("p").html(infoDP);
		var info2 = $("#lossGainBroughtInCome div[class='tc']").find("p[id='costCPId']").html(infoCP);
	}
	//特殊处理结转销售成本
	if(voucherInfoId == "carryForwardCostSales"){
		var carryForwardCost = $("#carryForwardCostSales p[class='p-num']").html();
		var info1 = $("#carryForwardCostSales div[class='tc']").find("p").eq(2).find("span").html("库存商品余额:"+carryForwardCost);
		var info2 = $("#carryForwardCostSales div[class='tc']").find("p").eq(2).find("input[id='inventoryBalanceValue']").val(carryForwardCost);
	}
}