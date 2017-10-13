document.write("<script language=javascript src='" + ctxStatic + "/js/jquery-1.11.2.min.js'></script>");
$(function() {
	var period = periodInfo(); // 加载账期信息
	tableInfo(period, period); // 加载table信息
	$("#beginPeriodHidden").attr("value", period);
	$("#endPeriodHidden").attr("value", period);
	$(".beginPeriod").val(period.substring(0, 4) + '年' + period.substring(4, 6) + '期');
	$(".endPeriod").val(period.substring(0, 4) + '年' + period.substring(4, 6) + '期');
})
// 加载账期信息
function periodInfo() {
	var period = '';
	$.ajax({
		type : 'POST',
		url : ctx + '/books/Subsidiary/getAllAccountPeriod',
		cache : false,
		async : false,
		dataType : 'json',
		data : {
			'param' : ''
		},
		success : function(data) {
			var str = '';
			period = data.defaultPeriod;
			for ( var i in data.listAccountperiod) {
				var accountPeriod = data.listAccountperiod[i];
				str += '<li class="subject" period = "' + accountPeriod + '">' + accountPeriod.substring(0, 4) + '年' + accountPeriod.substring(4, 6) + '期' + '</li>';
			}
			$(".period").html(str);
		}
	});
	return period;
}
// 加载table信息
function tableInfo(beginPeriod, endPeriod) {
	var params = 'accountPeriod=' + beginPeriod + '&periodEnd=' + endPeriod;
	$.ajax({
		type : 'POST',
		url : ctx + '/books/subjectBalance/list',
		cache : false,
		async : false,
		dataType : 'json',
		data : params,
		success : function(data) {
			var str = '';
			for ( var i in data) {
				var result = data[i];
				str += '<tr>';
				str += strToUndefined(result.accuntid,result.id,result.accLevel);
				str += strToUndefined(result.accountName,undefined,undefined);
				str += strToUndefined(result.beginDebit,undefined,undefined);
				str += strToUndefined(result.beginCredit,undefined,undefined);
				str += strToUndefined(result.debittotalamount,undefined,undefined);
				str += strToUndefined(result.credittotalamount,undefined,undefined);
				str += strToUndefined(result.ytddebittotalamount,undefined,undefined);
				str += strToUndefined(result.ytdcredittotalamount,undefined,undefined);
				str += strToUndefined(result.endDebit,undefined,undefined);
				str += strToUndefined(result.endCredit,undefined,undefined);
				str += '</tr>';
			}
			$("#tableInfo").html(str);
		}
	});
}

function strToUndefined(str1,str2,str3) {
	var td = "";
	if (str1 != undefined && str1 != "" && str1 != null) {
		if (str3 == 1) {
			td = '<td><a class="star" href="javascript:void(0)" onclick="jumpPage('+str2+')">' + str1 + '</a></td>';
		}else{
			if(str2 != undefined && str2 != "" && str2 != null){
				td = '<td Style="text-indent:8px;"><a href="javascript:void(0)" onclick="jumpPage('+str2+')">' + str1 + '</a></td>';	
			}else{
				td = '<td>'+str1+'</td>';
			}
			
		} 
	}else {
		td = '<td></td>';
	}
	return td;
}

function titleInfoFun(beginPeriod, endPeriod) {

	var accountName = "科目余额";

	if (eval(beginPeriod) == eval(endPeriod)) {
		var year = beginPeriod.substr(0, 4);
		var month = beginPeriod.substr(4, 6);
		accountName = year + '年' + month + '期' + accountName;
		$("#accountName").html(year + '年' + month + '月' + accountName);
	} else {
		var yearSt = beginPeriod.substr(0, 4);
		var monthSt = beginPeriod.substr(4, 6);

		var yearEn = endPeriod.substr(0, 4);
		var monthEn = endPeriod.substr(4, 6);
		if (eval(yearSt) == eval(yearEn)) {
			accountName = yearSt + '年' + monthSt + '期 - ' + monthEn + '期' + accountName;
		} else {
			accountName = yearSt + '年' + monthSt + '期 - ' + yearEn + '年' + monthEn + '期' + accountName;
		}
	}
	$("#accountName").html(accountName);

}
function jumpPage(valId){
	var beginPeriod = $("#beginPeriodHidden").val();
	var endPeriod = $("#endPeriodHidden").val();
	window.location.href=ctx+"/books/Subsidiary/listSubsidiaryledge?accId="+valId+"&accountPeriod="+beginPeriod+"&&periodEnd="+endPeriod; 
}
