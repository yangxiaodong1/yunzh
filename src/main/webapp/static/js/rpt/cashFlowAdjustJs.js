
$(function() {
	// 获取参数
	var accountPeriod = $("#periodBeginHidden").val();
	var periodEnd = $("#periodEndHidden").val();
	var periodtype = $("#periodTypeHidden").val();
	tableInfo(accountPeriod, periodEnd, periodtype); // 加载table信息
	titleInfoFun(accountPeriod, periodtype);
	
	//文本框获取焦点  内容选中
	 $(":text").focus(function(){
         this.select();
     });
})

// 加载table信息
function tableInfo(beginPeriod, periodEnd, periodtype) {
	var params = 'accountPeriod=' + beginPeriod + '&periodEnd=' + periodEnd + '&periodtype=' + periodtype;
	$.ajax({
		type : 'POST',
		url : ctx + '/rpt/cashFlow/listCashFlowAdjust',
		cache : false,	  
		async : false,
		dataType : 'json',
		data : params,
		success : function(data) {
			var str = '';
			var funNumber = 1;
			var funNumberSupp = 1;
			if(eval(data.companySystem)==1){	
				str = '<thead>\
						<tr>\
							<th width="50%">项目</th>\
							<th width="10%">行次</th>\
							<th width="20%">本年累计金额</th>\
							<th width="20%">本月金额</th>\
						</tr>\
						</thead>\
						<tbody>';
				for ( var i in data.listECashFlow) {
					var result = data.listECashFlow[i];
					
					var countNameYear = "yearBalance"+i;
					var countNamePeriod = "periodBalance"+i;
					if (!result.asIsALabel) {
						funNumber = funNumber + 1;
					}
					str += '<input id="reportItem'+i+'" type="hidden" value="'+data.reportItem+'"/>';
					str += '<input id="tcashFowId'+i+'" type="hidden" value="'+data.tcashFowId+'"/>';
					str += '<input id="groupId'+i+'" type="hidden" value="'+data.groupId+'"/>';
					str += '<tr>';
					str += strToProject(result);
					str += strToLineNumber(result.lineNumber);
					str += strToNumber(result.yearBalance,countNameYear,result.asIsALabel,funNumber);
					str += strToNumber(result.periodBalance,countNamePeriod,result.asIsALabel,funNumber);
					str += '</tr>';
				}
				str+='</tbody>';
				
			}else if (eval(data.companySystem)==2) {
				str = '<thead>\
					<tr>\
						<th width="24%">项目</th>\
						<th width="4%">行次</th>\
						<th width="11%">本年累计金额</th>\
						<th width="11%">本月金额</th>\
						<th width="24%">项目</th>\
						<th width="4%">行次</th>\
						<th width="11%">本年累计金额</th>\
						<th width="11%">本月金额</th>\
					</tr>\
					</thead>\
					<tbody>';
			for ( var i in data.listECashFlow) {
				var result = data.listECashFlow[i];
				if (!result.asIsALabel) {
					funNumber = funNumber + 1;
				}
				if (!result.suppAsIsALabel) {
					funNumberSupp = funNumberSupp + 1;
				}
				funNumberSupp
				var countNameYear = "yearBalance"+i;
				var countNamePeriod = "periodBalance"+i;
				var countNameYearSupp = "yearBalanceSupp"+i;
				var countNamePeriodSupp = "periodBalanceSupp"+i;
				
				str += '<input id="reportItem'+i+'" type="hidden" value="'+data.reportItem+'"/>';
				str += '<input id="tcashFowId'+i+'" type="hidden" value="'+data.tcashFowId+'"/>';
				str += '<input id="groupId'+i+'" type="hidden" value="'+data.groupId+'"/>';
				str += '<input id="reportItemSupp'+i+'" type="hidden" value="'+data.reportItem+'"/>';
				str += '<input id="tcashFowIdSupp'+i+'" type="hidden" value="'+data.tcashFowId+'"/>';
				str += '<input id="groupIdSupp'+i+'" type="hidden" value="'+data.groupId+'"/>';
				str += '<tr>';
				str += strToProject(result);
				str += strToLineNumber(result.lineNumber);
				str += strToNumberNewRules(result.yearBalance,countNameYear,result.asIsALabel,funNumber);
				str += strToNumberNewRules(result.periodBalance,countNamePeriod,result.asIsALabel,funNumber);
				str += strToProjectSupp(result);
				str += strToLineNumber(result.suppLineNumber);
				str += strToNumberewRulesSupp(result.suppYearBalance,countNameYearSupp,result.suppAsIsALabel,funNumberSupp);
				str += strToNumberewRulesSupp(result.suppPeriodBalance,countNamePeriodSupp,result.suppAsIsALabel,funNumberSupp);
				str += '</tr>';
			}
			str+='</tbody>';
			}
			$("#myTable02").html(str);
		}
	});
}

function strToProject(data) {
	var td = "";
	if (data.projectName != undefined && data.projectName != "" && data.projectName != null) {
		if(data.titleIsOrNo){
			td ='<td class="td-tit">'+
			data.projectName
				+'</td>';
		}else{
			td='<td class="td-tip">'+
			data.projectName
				+'</td>'
		}
	}else {
		td = '<td></td>';
	}
	return td;
}

function strToProjectSupp(data) {
	var td = "";
	if (data.suppProjectName != undefined && data.suppProjectName != "" && data.suppProjectName != null) {
		if(data.suppTitleIsOrNo){
			td ='<td class="td-tit">'+
			data.suppProjectName
				+'</td>';
		}else{
			td='<td class="td-tip">'+
			data.suppProjectName
				+'</td>'
		}
	}else {
		td = '<td></td>';
	}
	return td;
}

function strToLineNumber(lineNumber) {
	var td = "";
	if (lineNumber != undefined && lineNumber != "" && lineNumber != null) {
		td ='<td>'+
			lineNumber
			+'</td>';
	}else {
		td = '<td></td>';
	}
	return td;
}
//金额处理
function strToNumber(balance,countName,asIsALabel,funNumber) {
	var attribute = "";
	if (!asIsALabel) {
		attribute = 'disabled';
	}
	var td = "";
	if (balance != undefined && balance != "" && balance != null) {
		td ='<td><input id="'+countName+'" type="text" value="'+ balance +'" onblur="textOnChange' + funNumber + '($(this))" onfocus="unformatMoney($(this))" onkeyup="keyUp($(this))"  ' + attribute + ' style="width:100%; text-align: right;"/></td>';
	}else {
		td = '<td><input id="'+countName+'" type="text" value="" onblur="textOnChange' + funNumber + ' ($(this))" onfocus="unformatMoney($(this))" onkeyup="keyUp($(this))" ' + attribute + ' style="width:100%; text-align: right;"/></td>';
	}
	return td;
}
//金额处理		--新规则
function strToNumberNewRules(balance,countName,asIsALabel,funNumber) {
	var attribute = "";
	if (!asIsALabel) {
		attribute = 'disabled';
	}
	var td = "";
	if (balance != undefined && balance != "" && balance != null) {
		td ='<td><input id="'+countName+'" type="text" value="'+ balance +'" onblur="textOnChangeNewRules' + funNumber + '($(this))" onfocus="unformatMoney($(this))" onkeyup="keyUp($(this))"  ' + attribute + ' style="width:100%; text-align: right;"/></td>';
	}else {
		td = '<td><input id="'+countName+'" type="text" value="" onblur="textOnChangeNewRules' + funNumber + ' ($(this))" onfocus="unformatMoney($(this))" onkeyup="keyUp($(this))" ' + attribute + ' style="width:100%; text-align: right;"/></td>';
	}
	return td;
}
//补充资料金额处理	--新规则
function strToNumberewRulesSupp(balance,countName,asIsALabel,funNumber) {
	var attribute = "";
	if (!asIsALabel) {
		funNumber = funNumber + 1;
		attribute = 'disabled';
	}
	var td = "";
	if (balance != undefined && balance != "" && balance != null) {
		td ='<td><input id="'+countName+'" type="text" value="'+ balance +'" onblur="textOnChangeNewRulesSupp' + funNumber + '($(this))" onfocus="unformatMoney($(this))" onkeyup="keyUp($(this))"  ' + attribute + ' style="width:100%; text-align: right;"/></td>';
	}else {
		td = '<td><input id="'+countName+'" type="text" value="" onblur="textOnChangeNewRulesSupp' + funNumber + ' ($(this))" onfocus="unformatMoney($(this))" onkeyup="keyUp($(this))" ' + attribute + ' style="width:100%; text-align: right;"/></td>';
	}
	return td;
}
function titleInfoFun(beginPeriod, periodtype) {

	var accountName = "调整现金流量表";
	var year= beginPeriod.substr(0,4);
	var month=beginPeriod.substr(4,6);
	
	if(eval(periodtype)==1){
		accountName = year + '年' + month + '期' + accountName;	
	}else{
		accountName = year + '年' + month + '季度' + accountName;
	}
	$("#cashFlowAjustTitle").html(accountName);

}
function cfadditionalPage(){
	var beginPeriod = $("#beginPeriodHidden").val();
	var periodtype = $("#periodtypeHidden").val();
	window.location.href=ctx+"/rpt/cfadditional/list?accountPeriod="+beginPeriod+"&&periodtype="+periodtype; 
}

//==========================小企业====输入金额发生变化====beign=========
//==========================================四、现金净增加额====18*004
function getNewRulesBanlance18_004(){
    // 经营活动产生的现金流量净额	||	18*001*007
    var yearBalance7 = returnUnformatMoney($("#yearBalance7"));
    var periodBalance7 =  returnUnformatMoney($("#periodBalance7"));
    //投资活动产生的现金流量净额	||	18*002*006
    var yearBalance14 = returnUnformatMoney($("#yearBalance14"));
    var periodBalance14 =  returnUnformatMoney($("#periodBalance14"));
    //筹资活动产生的现金流量净额	||	18*003*006
    var yearBalance21 =returnUnformatMoney($("#yearBalance21"));
    var periodBalance21 = returnUnformatMoney($("#periodBalance21"));
    //[18*001*007]+[18*002*006]+[18*003*006]
    var yearBalanceT = parseFloat(yearBalance7)
			        + parseFloat(yearBalance14)
			        + parseFloat(yearBalance21);
    var periodBalanceT = parseFloat(periodBalance7)
			        + parseFloat(periodBalance14)
			        + parseFloat(periodBalance21);
    //四、现金净增加额	||	18*004
    formatMoney($("#yearBalance22").val(yearBalanceT));
    formatMoney($("#periodBalance22").val(periodBalanceT));
}
//========================================== 五、期末现金余额====18*005
function getNewRulesBanlance18_005(){
    //四、现金净增加额  ||  18*004
    var yearBalance22 =returnUnformatMoney($("#yearBalance22"));
    var periodBalance22 = returnUnformatMoney($("#periodBalance22"));

    //加：期初现金余额  ||  18*004*001
    var yearBalance23 =returnUnformatMoney($("#yearBalance23"));
    var periodBalance23 = returnUnformatMoney($("#periodBalance23"));
    //[18*004]+[18*004*001]
    var yearBalanceT = parseFloat(yearBalance22)
        + parseFloat(yearBalance23);
    var periodBalanceT = parseFloat(periodBalance22)
        + parseFloat(periodBalance23);
    formatMoney($("#yearBalance24").val(yearBalanceT));
    formatMoney($("#periodBalance24").val(periodBalanceT));
}
function textOnChange2(obj){
	formatMoney(obj);
	//========================================== 经营活动产生的现金流量净额====18*001*007
	//销售产成品、商品、提供劳务收到的现金	||	18*001*001
	var yearBalance1 = returnUnformatMoney($("#yearBalance1"));
	var periodBalance1 = returnUnformatMoney($("#periodBalance1"));
	//收到其他与经营活动有关的现金	||	18*001*002
	var yearBalance2 = returnUnformatMoney($("#yearBalance2"));
	var periodBalance2 = returnUnformatMoney($("#periodBalance2"));
	//购买原材料、商品、接受劳务支付的现金	||	18*001*003
	var yearBalance3 = returnUnformatMoney($("#yearBalance3"));
	var periodBalance3 = returnUnformatMoney($("#periodBalance3"));
	//支付的职工薪酬	||	18*001*004
	var yearBalance4 = returnUnformatMoney($("#yearBalance4"));
	var periodBalance4 = returnUnformatMoney($("#periodBalance4"));
	//支付的税费	||	18*001*005
	var yearBalance5 = returnUnformatMoney($("#yearBalance5"));
	var periodBalance5 = returnUnformatMoney($("#periodBalance5"));
	//支付其他与经营活动有关的现金	||	18*001*006
	var yearBalance6 = returnUnformatMoney($("#yearBalance6"));
	var periodBalance6 = returnUnformatMoney($("#periodBalance6"));
	
	var yearBalanceT =    parseFloat(yearBalance1) 
						+ parseFloat(yearBalance2) 
						- parseFloat(yearBalance3)
						- parseFloat(yearBalance4)
						- parseFloat(yearBalance5)
						- parseFloat(yearBalance6);								
	var periodBalanceT =  parseFloat(periodBalance1) 
						+ parseFloat(periodBalance2) 
						- parseFloat(periodBalance3)
						- parseFloat(periodBalance4)
						- parseFloat(periodBalance5)
						- parseFloat(periodBalance6);
	// 经营活动产生的现金流量净额	||	18*001*007
	formatMoney($("#yearBalance7").val(yearBalanceT));
	formatMoney($("#periodBalance7").val(periodBalanceT));
	
	//==========================================四、现金净增加额====18*004
	getNewRulesBanlance18_004();
	
	//========================================== 五、期末现金余额====18_005
	getNewRulesBanlance18_005();
	
}
function textOnChange4(obj){
	formatMoney(obj);
	//收回短期投资、长期债券投资和长期股权投资收到的现金	||	18*002*001
	var yearBalance9 = returnUnformatMoney($("#yearBalance9"));
	var periodBalance9 =returnUnformatMoney($("#periodBalance9"));
	//取得投资收益收到的现金	||	18*002*002
	var yearBalance10 = returnUnformatMoney($("#yearBalance10"));
	var periodBalance10 = returnUnformatMoney($("#periodBalance10"));
	//处置固定资产、无形资产和其他非流动资产收回的现金净额	||	18*002*003
	var yearBalance11 = returnUnformatMoney($("#yearBalance11"));
	var periodBalance11 = returnUnformatMoney($("#periodBalance11"));
	//短期投资、长期债券投资和长期股权投资支付的现金	||	18*002*004
	var yearBalance12 =returnUnformatMoney($("#yearBalance12"));
	var periodBalance12 = returnUnformatMoney($("#periodBalance12"));
	//购建固定资产、无形资产和其他非流动资产支付的现金	||	18*002*005
	var yearBalance13 = returnUnformatMoney($("#yearBalance13"));
	var periodBalance13 = returnUnformatMoney($("#periodBalance13"));
	
	var yearBalanceT = parseFloat(yearBalance9) 
						+ parseFloat(yearBalance10)
						+ parseFloat(yearBalance11)
						- parseFloat(yearBalance12)
						- parseFloat(yearBalance13);
	var periodBalanceT = parseFloat(periodBalance9) 
						+ parseFloat(periodBalance10)
						+ parseFloat(periodBalance11)
						- parseFloat(periodBalance12)
						- parseFloat(periodBalance13);
	//投资活动产生的现金流量净额	||	18*002*006
	formatMoney($("#yearBalance14").val(yearBalanceT));
	formatMoney($("#periodBalance14").val(periodBalanceT));
	//==========================================四、现金净增加额====18*004
	getNewRulesBanlance18_004();
	
	//========================================== 五、期末现金余额====18_005
	getNewRulesBanlance18_005();
	
}
function textOnChange6(obj){
	formatMoney(obj);
	//取得借款收到的现金	||	18*003*001
	var yearBalance16 = returnUnformatMoney($("#yearBalance16"));
	var periodBalance16 = returnUnformatMoney($("#periodBalance16"));
	//吸收投资者投资收到的现金	||	18*003*002
	var yearBalance17 = returnUnformatMoney($("#yearBalance17"));
	var periodBalance17 = returnUnformatMoney($("#periodBalance17"));
	//偿还借款本金支付的现金	||	18*003*003
	var yearBalance18 = returnUnformatMoney($("#yearBalance18"));
	var periodBalance18 = returnUnformatMoney($("#periodBalance18"));
	//偿还借款利息支付的现金	||	18*003*004
	var yearBalance19 = returnUnformatMoney($("#yearBalance19"));
	var periodBalance19 = returnUnformatMoney($("#periodBalance19"));
	//分配利润支付的现金	||	18*003*005
	var yearBalance20 = returnUnformatMoney($("#yearBalance20"));
	var periodBalance20 =returnUnformatMoney($("#periodBalance20"));
	
	var yearBalanceT = parseFloat(yearBalance16) 
						+ parseFloat(yearBalance17) 
						- parseFloat(yearBalance18) 
						- parseFloat(yearBalance19)
						- parseFloat(yearBalance20);
	var periodBalanceT = parseFloat(periodBalance16) 
						+ parseFloat(periodBalance17) 
						- parseFloat(periodBalance18) 
						- parseFloat(periodBalance19)
						- parseFloat(periodBalance20);
	//筹资活动产生的现金流量净额	||	18*003*006
	formatMoney($("#yearBalance21").val(yearBalanceT));
	formatMoney($("#periodBalance21").val(periodBalanceT));
	//==========================================四、现金净增加额====18*004
	getNewRulesBanlance18_004();
	
	//========================================== 五、期末现金余额====18_005
	getNewRulesBanlance18_005();
}
function textOnChange8(obj){
	formatMoney(obj);
	//========================================== 五、期末现金余额====18_005
	getNewRulesBanlance18_005();
}
//==========================小企业====输入金额发生变化====end=========

//==========================新规则====输入金额发生变化====begin=========
//================================一、经营活动产生的现金流量：============

//==================================经营活动产生的现金流量净额====16*001*010
function getNewRulesBanlance16_001_010(){
	//现金流出小计	||	16*001*004
	var yearBalance4 = returnUnformatMoney($("#yearBalance4"));
	var periodBalance4 = returnUnformatMoney($("#periodBalance4"));
	//现金流出小计	||	16*001*009
	var yearBalance9 = returnUnformatMoney($("#yearBalance9"));
	var periodBalance9 = returnUnformatMoney($("#periodBalance9"));
	//[16*001*004]-[16*001*009]
	var yearBalanceT = parseFloat(yearBalance4) 
					- parseFloat(yearBalance9);
	var periodBalanceT = parseFloat(periodBalance4) 
					- parseFloat(periodBalance9);
	//经营活动产生的现金流量净额	||	16*001*010
	formatMoney($("#yearBalance10").val(yearBalanceT));
	formatMoney($("#periodBalance10").val(periodBalanceT));
}
//==================================投资活动产生的现金流量净额====16*002*010
function getNewRulesBanlance16_002_010(){
	//现金流入小计	||	16*002*005
	var yearBalance16 = returnUnformatMoney($("#yearBalance16"));
	var periodBalance16 = returnUnformatMoney($("#periodBalance16"));
	//现金流出小计	||	16*002*009
	var yearBalance20 = returnUnformatMoney($("#yearBalance20"));
	var periodBalance20 = returnUnformatMoney($("#periodBalance20"));
	//[16*002*005]-[16*002*009]
	var yearBalanceT = parseFloat(yearBalance16) 
					- parseFloat(yearBalance20);
	var periodBalanceT = parseFloat(periodBalance16) 
					- parseFloat(periodBalance20);
	//投资活动产生的现金流量净额	||	16*002*010
	formatMoney($("#yearBalance21").val(yearBalanceT));
	formatMoney($("#periodBalance21").val(periodBalanceT));
}
//==================================筹资活动产生的现金流量净额====16*003*009
function getNewRulesBanlance16_003_009(){
	//现金流入小计	||	16*003*004
	var yearBalance26 = returnUnformatMoney($("#yearBalance26"));
	var periodBalance26 = returnUnformatMoney($("#periodBalance26"));
	//现金流出小计	||	16*003*008
	var yearBalance30 = returnUnformatMoney($("#yearBalance30"));
	var periodBalance30 = returnUnformatMoney($("#periodBalance30"));
	//[16*003*004]-[16*003*008]
	var yearBalanceT = parseFloat(yearBalance26) 
					- parseFloat(yearBalance30);
	var periodBalanceT = parseFloat(periodBalance26) 
					- parseFloat(periodBalance30);
	//筹资活动产生的现金流量净额	||	16*003*009
	formatMoney($("#yearBalance31").val(yearBalanceT));
	formatMoney($("#periodBalance31").val(periodBalanceT));
}
//==================================现金及现金等价物净增加额====16*005
function getNewRulesBanlance16_005(){
	//经营活动产生的现金流量净额	||	16*001*010
	var yearBalance10 = returnUnformatMoney($("#yearBalance10"));
	var periodBalance10 = returnUnformatMoney($("#periodBalance10"));
	//投资活动产生的现金流量净额	||	16*002*010
	var yearBalance21 = returnUnformatMoney($("#yearBalance21"));
	var periodBalance21 = returnUnformatMoney($("#periodBalance21"));
	//筹资活动产生的现金流量净额	||	16*003*009
	var yearBalance31 = returnUnformatMoney($("#yearBalance31"));
	var periodBalance31 = returnUnformatMoney($("#periodBalance31"));
	//汇率变动对现金的影响	||	16*004
	var yearBalance32 = returnUnformatMoney($("#yearBalance32"));
	var periodBalance32 = returnUnformatMoney($("#periodBalance32"));
	//[16*001*010]+[16*002*010]+[16*003*009]+[16*004]
	var yearBalanceT = parseFloat(yearBalance10)
					+ parseFloat(yearBalance21)
					+ parseFloat(yearBalance31)
					+ parseFloat(yearBalance32);
	var periodBalanceT = parseFloat(periodBalance10)
					+ parseFloat(periodBalance21)
					+ parseFloat(periodBalance31)
					+ parseFloat(periodBalance32) ;
	//五、现金及现金等价物净增加额	||	16*005
	formatMoney($("#yearBalance33").val(yearBalanceT));
	formatMoney($("#periodBalance33").val(periodBalanceT));
}
//==================================加：期初现金及现金等价物余额====16*005*001
function getNewRulesBanlance16_005_001(){
	//减：现金的期初余额	||	16*010*030
	var yearBalanceSupp32 = returnUnformatMoney($("#yearBalanceSupp32"));
	var periodBalanceSupp32 = returnUnformatMoney($("#periodBalanceSupp32"));
	
	//减：现金等价物的期初余额	||	16*010*032
	var yearBalanceSupp34 = returnUnformatMoney($("#yearBalanceSupp34"));
	var periodBalanceSupp34 = returnUnformatMoney($("#periodBalanceSupp34"));
	//[16*010*030]+[16*010*032]
	var yearBalanceT = parseFloat(yearBalanceSupp32)
					+ parseFloat(yearBalanceSupp34);
	
	var periodBalanceT = parseFloat(periodBalanceSupp32)
					+ parseFloat(periodBalanceSupp34);
	//加：期初现金及现金等价物余额	||	16*005*001
	formatMoney($("#yearBalance34").val(yearBalanceT));
	formatMoney($("#periodBalance34").val(periodBalanceT));
}
//==================================六、期末现金及现金等价物余额====16*006
function getNewRulesBanlance16_006(){
	//五、现金及现金等价物净增加额	||	16*005
	var yearBalance33 = returnUnformatMoney($("#yearBalance33"));
	var periodBalance33 = returnUnformatMoney($("#periodBalance33"));
	
	//加：期初现金及现金等价物余额	||	16*005*001
	var yearBalance34 = returnUnformatMoney($("#yearBalance34"));
	var periodBalance34 = returnUnformatMoney($("#periodBalance34"));
	//[16*005]+[16*005*001]
	var yearBalanceT = parseFloat(yearBalance33)
					+ parseFloat(yearBalance34);
	
	var periodBalanceT = parseFloat(periodBalance33)
					+ parseFloat(periodBalance34);
	//六、期末现金及现金等价物余额	||	16*006
	formatMoney($("#yearBalance35").val(yearBalanceT));
	formatMoney($("#periodBalance35").val(periodBalanceT));
}

function textOnChangeNewRules2(obj){
	formatMoney(obj);
	
	//==================================现金流出小计====16*001*009
	//销售商品、提供劳务收到的现金	||	16*001*001
	var yearBalance1 = returnUnformatMoney($("#yearBalance1"));
	var periodBalance1 = returnUnformatMoney($("#periodBalance1"));
	//收到的税费返还	||	16*001*002
	var yearBalance2 = returnUnformatMoney($("#yearBalance2"));
	var periodBalance2 = returnUnformatMoney($("#periodBalance2"));
	//收到的其他与经营活动有关的现金	||	16*001*003
	var yearBalance3 = returnUnformatMoney($("#yearBalance3"));
	var periodBalance3 = returnUnformatMoney($("#periodBalance3"));
	//[16*001*001]+[16*001*002]+[16*001*003]
	var yearBalanceT = parseFloat(yearBalance1) 
						+ parseFloat(yearBalance2)
						+ parseFloat(yearBalance3);
	var periodBalanceT = parseFloat(periodBalance1) 
						+ parseFloat(periodBalance2)
						+ parseFloat(periodBalance3);
	//现金流入小计	||	16*001*004
	formatMoney($("#yearBalance4").val(yearBalanceT));
	formatMoney($("#periodBalance4").val(periodBalanceT));
	
	
	//==================================经营活动产生的现金流量净额====16*001*010
	getNewRulesBanlance16_001_010();
	//==================================现金及现金等价物净增加额====16*005
	getNewRulesBanlance16_005();
	//==================================六、期末现金及现金等价物余额====16*006
	getNewRulesBanlance16_006();
}
function textOnChangeNewRules3(obj){
	formatMoney(obj);
	//==================================现金流出小计====16*001*009
	//购买商品、接受劳务支付的现金	||	16*001*005
	var yearBalance5 = returnUnformatMoney($("#yearBalance5"));
	var periodBalance5 = returnUnformatMoney($("#periodBalance5"));
	//支付给职工以及为职工支付的现金	||	16*001*006
	var yearBalance6 = returnUnformatMoney($("#yearBalance6"));
	var periodBalance6 = returnUnformatMoney($("#periodBalance6"));
	//支付的各项税费	||	16*001*007
	var yearBalance7 = returnUnformatMoney($("#yearBalance7"));
	var periodBalance7 = returnUnformatMoney($("#periodBalance7"));
	//支付的其他与经营活动有关的现金	||	16*001*008
	var yearBalance8 = returnUnformatMoney($("#yearBalance8"));
	var periodBalance8 = returnUnformatMoney($("#periodBalance8"));
	//[16*001*005]+[16*001*006]+[16*001*007]+[16*001*008]
	var yearBalanceT = parseFloat(yearBalance5) 
					+ parseFloat(yearBalance6)
					+ parseFloat(yearBalance7)
					+ parseFloat(yearBalance8);
	var periodBalanceT = parseFloat(periodBalance5) 
					+ parseFloat(periodBalance6)
					+ parseFloat(periodBalance7)
					+ parseFloat(periodBalance8);
	//现金流出小计	||	16*001*009
	formatMoney($("#yearBalance9").val(yearBalanceT));
	formatMoney($("#periodBalance9").val(periodBalanceT));
	
	//==================================经营活动产生的现金流量净额====16*001*010
	getNewRulesBanlance16_001_010();
	//==================================现金及现金等价物净增加额====16*005
	//投资活动产生的现金流量净额	||	16*002*010
	getNewRulesBanlance16_005();
	//==================================六、期末现金及现金等价物余额====16*006
	getNewRulesBanlance16_006();
}
//=================================二、投资活动产生的现金流量
function textOnChangeNewRules6(obj){
	formatMoney(obj);
	//==================================现金流入小计====16*002*005
	//收回投资所收到的现金	||	16*002*001
	var yearBalance12 = returnUnformatMoney($("#yearBalance12"));
	var periodBalance12 = returnUnformatMoney($("#periodBalance12"));
	//取得投资收益所收到的现金	||	16*002*002
	var yearBalance13 = returnUnformatMoney($("#yearBalance13"));
	var periodBalance13 = returnUnformatMoney($("#periodBalance13"));
	//处置固定资产、无形资产和其他长期资产所收回的现金净额	||	16*002*003
	var yearBalance14 = returnUnformatMoney($("#yearBalance14"));
	var periodBalance14 = returnUnformatMoney($("#periodBalance14"));
	//收到的其他与投资活动有关的现金	||	16*002*004
	var yearBalance15 = returnUnformatMoney($("#yearBalance15"));
	var periodBalance15 = returnUnformatMoney($("#periodBalance15"));
	//[16*002*001]+[16*002*002]+[16*002*003]+[16*002*004]
	var yearBalanceT = parseFloat(yearBalance12) 
					+ parseFloat(yearBalance13)
					+ parseFloat(yearBalance14)
					+ parseFloat(yearBalance15);
	var periodBalanceT = parseFloat(periodBalance12) 
					+ parseFloat(periodBalance13)
					+ parseFloat(periodBalance14)
					+ parseFloat(periodBalance15);
	//现金流入小计	||	16*002*005
	formatMoney($("#yearBalance16").val(yearBalanceT));
	formatMoney($("#periodBalance16").val(periodBalanceT));
	//==================================投资活动产生的现金流量净额====16*002*010
	getNewRulesBanlance16_002_010();
	//==================================现金及现金等价物净增加额====16*005
	getNewRulesBanlance16_005();
	//==================================六、期末现金及现金等价物余额====16*006
	getNewRulesBanlance16_006();
}
function textOnChangeNewRules7(obj){
	//==================================现金流出小计====16*002*009
	formatMoney(obj);
	//购建固定资产、无形资产和其他长期资产所支付的现金	||	16*002*006
	var yearBalance17 = returnUnformatMoney($("#yearBalance17"));
	var periodBalance17 = returnUnformatMoney($("#periodBalance17"));
	//投资所支付的现金	||	16*002*007
	var yearBalance18 = returnUnformatMoney($("#yearBalance18"));
	var periodBalance18 = returnUnformatMoney($("#periodBalance18"));
	//支付的其他与投资活动有关的现金	||	16*002*008
	var yearBalance19 = returnUnformatMoney($("#yearBalance19"));
	var periodBalance19 = returnUnformatMoney($("#periodBalance19"));
	//[16*002*006]+[16*002*007]+[16*002*008]
	var yearBalanceT = parseFloat(yearBalance17) 
					+ parseFloat(yearBalance18)
					+ parseFloat(yearBalance19);
	var periodBalanceT = parseFloat(periodBalance17) 
					+ parseFloat(periodBalance18)
					+ parseFloat(periodBalance19);
	//现金流出小计	||	16*002*009
	formatMoney($("#yearBalance20").val(yearBalanceT));
	formatMoney($("#periodBalance20").val(periodBalanceT));
	//==================================投资活动产生的现金流量净额====16*002*010
	getNewRulesBanlance16_002_010();
	//==================================现金及现金等价物净增加额====16*005
	getNewRulesBanlance16_005();
	//==================================六、期末现金及现金等价物余额====16*006
	getNewRulesBanlance16_006();
}
//===================================三、筹资活动产生的现金流量：
function textOnChangeNewRules10(obj){
	//==================================现金流入小计====16*003*004
	formatMoney(obj);
	//吸收投资所收到的现金	||	16*003*001
	var yearBalance23 = returnUnformatMoney($("#yearBalance23"));
	var periodBalance23 = returnUnformatMoney($("#periodBalance23"));
	//借款所收到的现金	||	16*003*002
	var yearBalance24 = returnUnformatMoney($("#yearBalance24"));
	var periodBalance24 = returnUnformatMoney($("#periodBalance24"));
	//收到的其他与筹资活动有关的现金	||	16*003*003
	var yearBalance25 = returnUnformatMoney($("#yearBalance25"));
	var periodBalance25 = returnUnformatMoney($("#periodBalance25"));
	//[16*003*001]+[16*003*002]+[16*003*003]
	var yearBalanceT = parseFloat(yearBalance23) 
					+ parseFloat(yearBalance24)
					+ parseFloat(yearBalance25);
	var periodBalanceT = parseFloat(periodBalance23) 
					+ parseFloat(periodBalance24)
					+ parseFloat(periodBalance25);
	//现金流入小计	||	16*003*004
	formatMoney($("#yearBalance26").val(yearBalanceT));
	formatMoney($("#periodBalance26").val(periodBalanceT));
	//==================================筹资活动产生的现金流量净额====16*003*009
	getNewRulesBanlance16_003_009();
	//==================================现金及现金等价物净增加额====16*005
	getNewRulesBanlance16_005();
	//==================================六、期末现金及现金等价物余额====16*006
	getNewRulesBanlance16_006();
}
function textOnChangeNewRules11(obj){
	formatMoney(obj);
	//==================================现金流出小计====16*003*008
	//偿还债务所支付的现金	||	16*003*005
	var yearBalance27 = returnUnformatMoney($("#yearBalance27"));
	var periodBalance27 = returnUnformatMoney($("#periodBalance27"));
	//分配股利、利润或偿付利息所支付的现金	||	16*003*006
	var yearBalance28 = returnUnformatMoney($("#yearBalance28"));
	var periodBalance28 = returnUnformatMoney($("#periodBalance28"));
	//支付的其他与筹资活动有关的现金	||	16*003*007
	var yearBalance29 = returnUnformatMoney($("#yearBalance29"));
	var periodBalance29 = returnUnformatMoney($("#periodBalance29"));
	//[16*003*005]+[16*003*006]+[16*003*007]
	var yearBalanceT = parseFloat(yearBalance27) 
					+ parseFloat(yearBalance28)
					+ parseFloat(yearBalance29);
	var periodBalanceT = parseFloat(periodBalance27) 
					+ parseFloat(periodBalance28)
					+ parseFloat(periodBalance29);
	//现金流出小计	||	16*003*008
	formatMoney($("#yearBalance30").val(yearBalanceT));
	formatMoney($("#periodBalance30").val(periodBalanceT));
	//==================================筹资活动产生的现金流量净额====16*003*009
	getNewRulesBanlance16_003_009();
	//==================================现金及现金等价物净增加额====16*005
	getNewRulesBanlance16_005();
	//==================================六、期末现金及现金等价物余额====16*006
	getNewRulesBanlance16_006();
}
//=================================四、汇率变动对现金的影响
function textOnChangeNewRules13(obj){
	formatMoney(obj);
	//==================================现金及现金等价物净增加额====16*005
	getNewRulesBanlance16_005();
}
//==========================新规则====输入金额发生变化====end=========
//==========================新规则补充资料====输入金额发生变化==补充资料==beign=========
function textOnChangeNewRulesSupp1(obj){
	formatMoney(obj);
	// 净利润	||	16*010*002
	var yearBalanceSupp2 = returnUnformatMoney($("#yearBalanceSupp2"));
	var periodBalanceSupp2 = returnUnformatMoney($("#periodBalanceSupp2"));
	
	//加：计提的资产减值准备	||	16*010*003
	var yearBalanceSupp3 = returnUnformatMoney($("#yearBalanceSupp3"));
	var periodBalanceSupp3 = returnUnformatMoney($("#periodBalanceSupp3"));
	
	//固定资产折旧	||	16*010*004
	var yearBalanceSupp4 = returnUnformatMoney($("#yearBalanceSupp4"));
	var periodBalanceSupp4 = returnUnformatMoney($("#periodBalanceSupp4"));
	
	//无形资产摊销	||	16*010*005
	var yearBalanceSupp5 = returnUnformatMoney($("#yearBalanceSupp5"));
	var periodBalanceSupp5 = returnUnformatMoney($("#periodBalanceSupp5"));
	
	//长期待摊费用摊销	||	16*010*006
	var yearBalanceSupp6 = returnUnformatMoney($("#yearBalanceSupp6"));
	var periodBalanceSupp6 = returnUnformatMoney($("#periodBalanceSupp6"));
	
	//处置固定资产、无形资产和其他长期资产的损失(减：收益)	||	16*010*007
	var yearBalanceSupp7 = returnUnformatMoney($("#yearBalanceSupp7"));
	var periodBalanceSupp7 = returnUnformatMoney($("#periodBalanceSupp7"));
	
	//固定资产报废损失	||	16*010*008
	var yearBalanceSupp8 = returnUnformatMoney($("#yearBalanceSupp8"));
	var periodBalanceSupp8 = returnUnformatMoney($("#periodBalanceSupp8"));
	
	// 公允价值变动损失	||	16*010*009
	var yearBalanceSupp9 = returnUnformatMoney($("#yearBalanceSupp9"));
	var periodBalanceSupp9 = returnUnformatMoney($("#periodBalanceSupp9"));
	
	//财务费用（减：收入）	||	16*010*010
	var yearBalanceSupp10 = returnUnformatMoney($("#yearBalanceSupp10"));
	var periodBalanceSupp10 = returnUnformatMoney($("#periodBalanceSupp10"));
	
	//投资损失(减：收益)	||	16*010*011
	var yearBalanceSupp11 = returnUnformatMoney($("#yearBalanceSupp11"));
	var periodBalanceSupp11 = returnUnformatMoney($("#periodBalanceSupp11"));
	
	//递延所得税资产减少	||	16*010*012
	var yearBalanceSupp12 = returnUnformatMoney($("#yearBalanceSupp12"));
	var periodBalanceSupp12 = returnUnformatMoney($("#periodBalanceSupp12"));
	
	//递延所得税负债增加	||	16*010*013
	var yearBalanceSupp13 = returnUnformatMoney($("#yearBalanceSupp13"));
	var periodBalanceSupp13 = returnUnformatMoney($("#periodBalanceSupp13"));
	
	//存货的减少	||	16*010*014
	var yearBalanceSupp14 = returnUnformatMoney($("#yearBalanceSupp14"));
	var periodBalanceSupp14 = returnUnformatMoney($("#periodBalanceSupp14"));
	
	//经营性应收项目的减少	||	16*010*015
	var yearBalanceSupp15 = returnUnformatMoney($("#yearBalanceSupp15"));
	var periodBalanceSupp15 = returnUnformatMoney($("#periodBalanceSupp15"));
	
	//经营性应付项目的增加	||	16*010*016
	var yearBalanceSupp16 = returnUnformatMoney($("#yearBalanceSupp16"));
	var periodBalanceSupp16 = returnUnformatMoney($("#periodBalanceSupp16"));
	
	//其他	||	16*010*017
	var yearBalanceSupp17 = returnUnformatMoney($("#yearBalanceSupp17"));
	var periodBalanceSupp17 = returnUnformatMoney($("#periodBalanceSupp17"));
	
	var yearBalanceT = parseFloat(yearBalanceSupp2) 
						+ parseFloat(yearBalanceSupp3) 
						+ parseFloat(yearBalanceSupp4)
						+ parseFloat(yearBalanceSupp5)
						+ parseFloat(yearBalanceSupp6)
						+ parseFloat(yearBalanceSupp7)	
						+ parseFloat(yearBalanceSupp8) 
						+ parseFloat(yearBalanceSupp9)
						+ parseFloat(yearBalanceSupp10)
						+ parseFloat(yearBalanceSupp11)
						+ parseFloat(yearBalanceSupp12)
						+ parseFloat(yearBalanceSupp13) 
						+ parseFloat(yearBalanceSupp14)
						+ parseFloat(yearBalanceSupp15)
						+ parseFloat(yearBalanceSupp16)
						+ parseFloat(yearBalanceSupp17);
						
	var periodBalanceT = parseFloat(periodBalanceSupp2) 
						+ parseFloat(periodBalanceSupp3) 
						+ parseFloat(periodBalanceSupp4)
						+ parseFloat(periodBalanceSupp5)
						+ parseFloat(periodBalanceSupp6)
						+ parseFloat(periodBalanceSupp7)
						+ parseFloat(periodBalanceSupp8)
						+ parseFloat(periodBalanceSupp9)
						+ parseFloat(periodBalanceSupp10)
						+ parseFloat(periodBalanceSupp11)
						+ parseFloat(periodBalanceSupp12)
						+ parseFloat(periodBalanceSupp13) 
						+ parseFloat(periodBalanceSupp14)
						+ parseFloat(periodBalanceSupp15)
						+ parseFloat(periodBalanceSupp16)
						+ parseFloat(periodBalanceSupp17);
	
	formatMoney($("#yearBalanceSupp18").val(yearBalanceT));
	formatMoney($("#periodBalanceSupp18").val(periodBalanceT));
	
}
function textOnChangeNewRulesSupp5(obj){
	formatMoney(obj);
}
function textOnChangeNewRulesSupp9(obj){
	formatMoney(obj);
	
	//现金的期末余额
	var yearBalanceSupp31 = returnUnformatMoney($("#yearBalanceSupp31"));
	var periodBalanceSupp31 = returnUnformatMoney($("#periodBalanceSupp31"));

	//减：现金的期初余额
	var yearBalanceSupp32 = returnUnformatMoney($("#yearBalanceSupp32"));
	var periodBalanceSupp32 = returnUnformatMoney($("#periodBalanceSupp32"));
	
	//加：现金等价物的期末余额 	 
	var yearBalanceSupp33 = returnUnformatMoney($("#yearBalanceSupp33"));
	var periodBalanceSupp33 = returnUnformatMoney($("#periodBalanceSupp33"));
	
	//减：现金等价物的期初余额
	var yearBalanceSupp34 = returnUnformatMoney($("#yearBalanceSupp34"));
	var periodBalanceSupp34 = returnUnformatMoney($("#periodBalanceSupp34"));
	
	var yearBalanceT = parseFloat(yearBalanceSupp31) 
						- parseFloat(yearBalanceSupp32) 
						+ parseFloat(yearBalanceSupp33)
						- parseFloat(yearBalanceSupp34);	
	
	var periodBalanceT = parseFloat(periodBalanceSupp31) 
						- parseFloat(periodBalanceSupp32) 
						+ parseFloat(periodBalanceSupp33)
						- parseFloat(periodBalanceSupp34);
	formatMoney($("#yearBalanceSupp35").val(yearBalanceT));
	formatMoney($("#periodBalanceSupp35").val(periodBalanceT));	
	//==================================加：期初现金及现金等价物余额====16*005*001
	getNewRulesBanlance16_005_001();
	//==================================六、期末现金及现金等价物余额====16*006
	getNewRulesBanlance16_006();
}
//==========================新规则补充资料====输入金额发生变化==补充资料==end=========

// ===============================================================判断是否是数字 begin
// =====================================
function formatMoney(input){
	if(parseInt(input.val())==0){
		input.val("");
	}
	if(input.val() !=null && typeof(input.val())!="undefined" && input.val()!=0){
		input.val($.formatMoney(input.val(), 2));	
	}
	
}
function unformatMoney(input){
	if(input.val() !=null && typeof(input.val())!="undefined" && input.val()!=0){
		input.val($.unformatMoney(input.val()));
	}
}
function returnUnformatMoney(input){
	return $.unformatMoney(input.val());
}
// ===============================================================判断是否是数字 end
// =====================================

// ================================================================键盘输入数字 出发事件
// begin =============================
function keyUp(obj,event) {
	var event = event || window.event;
	obj.val(obj.val().replace(/[^\d.-]/g, "").replace(/\-{2,}/g, "").
	replace(/^\-{2,}/g, "-").
	// 只允许一个小数点
	replace(/^\./g, "").replace(/\.{2,}/g, ".").
	// 只能输入小数点后两位
	replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").
	replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));
}
// ================================================================键盘输入数字 出发事件
// end =============================

// ================================================================数据保存 传输后台
// begin =============================
function savaECashFlow(){
 	var result="";
 	var tableInfoCount = $("#tableInfoCountHidden").val();
	for(var i=1;i<=tableInfoCount;i++){
		var tcashFowId=$("#tcashFowId"+i).val();
		var reportItem=$("#reportItem"+i).val();
		var yearBalance=returnUnformatMoney($("#yearBalance"+i));
		var periodBalance=returnUnformatMoney($("#periodBalance"+i));
		var groupId=$("#groupId"+i).val();
		 if(tcashFowId != null && tcashFowId != ""&& tcashFowId!= undefined  || reportItem!=null && reportItem!="" && reportItem!= undefined || yearBalance!=null && yearBalance!="" && yearBalance!= undefined || periodBalance!=null && periodBalance!="" && periodBalance!= undefined || groupId!=null && groupId != ""&& groupId!= undefined ){
			 result += "{'tcashFowId':'" + tcashFowId
				 	+ "','reportItem':'" + reportItem
				 	+ "','yearBalance':'" + yearBalance
					+ "','periodBalance':'" + periodBalance
					+ "','groupId':'"+groupId
					+"'},";  
			
		 } 
	}
	result="["+result.substring(0,result.length-1)+"]";
	var accountPeriod = $("#periodBeginHidden").val();
	var periodtype = $("#periodTypeHidden").val();
	window.location=ctx+"/rpt/cfadditional/save?info="+result+"&&accountPeriod="+accountPeriod+"&&periodtype="+periodtype;
}
// ================================================================数据保存 传输后台
// begin =============================
