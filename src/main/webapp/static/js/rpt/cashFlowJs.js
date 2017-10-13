
$(function() {
	var period = periodInfo(); // 加载账期信息
	var periodtype = $("#periodtypeHidden").val();
	tableInfo(period, periodtype); // 加载table信息
	$("#beginPeriodHidden").attr("value", period);
	$(".beginPeriod").val(period.substring(0, 4) + '年' + period.substring(4, 6) + '期');
})
// 加载账期信息
function periodInfo() {
	var period = '';
	$.ajax({
		type : 'POST',
		url : ctx + '/rpt/cashFlow/getAllAccountPeriod?periodtype='+1,
		cache : false,
		async : false,
		dataType : 'json',
		data : {
			'param' : ''
		},
		success : function(data) {
			var str = '';
			period = data.defaultPeriod;
			if(eval(data.periodtype)==2){
				for ( var i in data.listAccountperiod) {
					var accountPeriod = data.listAccountperiod[i];
					str += '<li class="subject" period = "' + accountPeriod + '">' + accountPeriod.substring(0, 4) + '年' + accountPeriod.substring(4, 5) + '季度' + '</li>';
				}
			}else{
				for ( var i in data.listAccountperiod) {
					var accountPeriod = data.listAccountperiod[i];
					str += '<li class="subject" period = "' + accountPeriod + '">' + accountPeriod.substring(0, 4) + '年' + accountPeriod.substring(4, 6) + '期' + '</li>';
				}
			}
			$(".period").html(str);
		}
	});
	return period;
}
// 加载table信息
function tableInfo(beginPeriod, periodtype) {
	var params = 'accountPeriod=' + beginPeriod + '&periodEnd=' + beginPeriod + '&periodtype=' + periodtype;
	$.ajax({
		type : 'POST',
		url : ctx + '/rpt/cashFlow/list',
		cache : false,
		async : false,
		dataType : 'json',
		data : params,
		success : function(data) {
			var str = '';
			$("#companyName").html('<i></i>'+data.companyName);
			$("#companySystemHidden").val(data.companySystem);
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
					str += '<tr>';
					str += strToProject(result);
					str += strToLineNumber(result.lineNumber);
					str += strToNumber(result.yearBalance);
					str += strToNumber(result.periodBalance);
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
				str += '<tr>';
				str += strToProject(result);
				str += strToLineNumber(result.lineNumber);
				str += strToNumber(result.yearBalance);
				str += strToNumber(result.periodBalance);
				str += strToProjectSupp(result);
				str += strToLineNumber(result.suppLineNumber);
				str += strToNumber(result.suppYearBalance);
				str += strToNumber(result.suppPeriodBalance);
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

function strToNumber(balance) {
	var td = "";
	if (balance != undefined && balance != "" && balance != null) {
		td ='<td>'+
		balance
			+'</td>';
	}else {
		td = '<td></td>';
	}
	return td;
}
function titleInfoFun(beginPeriod, periodtype) {

	var accountName = "现金流量表";
	var year= beginPeriod.substr(0,4);
	var month=beginPeriod.substr(4,6);
	
	if(eval(periodtype)==1){
		accountName = year + '年' + month + '期' + accountName;	
	}else{
		accountName = year + '年' + month + '季度' + accountName;
	}
	$("#cashFlowTitle").html(accountName);

}
//跳转到现金流量附加表
function cfadditionalPage(){
	var beginPeriod = $("#beginPeriodHidden").val();
	var periodEnd = beginPeriod;
	var periodtype = $("#periodtypeHidden").val();
	window.location.href=ctx+"/rpt/cfadditional/listCfadditional?accountPeriod="+beginPeriod+'&periodEnd='+periodEnd+"&periodtype="+periodtype; 
}
