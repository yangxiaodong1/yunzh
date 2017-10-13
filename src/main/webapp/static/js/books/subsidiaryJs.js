/*document.write("<script language=javascript src='" + ctxStatic + "/js/jquery-1.11.2.min.js'></script>");*/
$(function() {
	var period = periodInfo(); // 加载账期信息
	var accInfo = accountInfo(period, period); // 加载科目信息
	if (accId != undefined && accId != "" && accId != null) { // 总账
																// 科目余额表跳转过来执行
		tableInfo(accId, accountPeriod, periodEnd); // 加载table信息
		$("#beginPeriodHidden").attr("value", accountPeriod);
		$("#endPeriodHidden").attr("value", periodEnd);
		$("#accIdHidden").attr("value", accId);
		$("#accNameHidden").attr("value", accName);
		$(".beginPeriod").val(accountPeriod.substring(0, 4) + '年' + accountPeriod.substring(4, 6) + '期');
		$(".endPeriod").val(periodEnd.substring(0, 4) + '年' + periodEnd.substring(4, 6) + '期');
		$(".account").val(accountId + '-' + accName);
	} else {
		tableInfo(accInfo[0], period, period); // 加载table信息
		$("#beginPeriodHidden").attr("value", period);
		$("#endPeriodHidden").attr("value", period);
		$("#accIdHidden").attr("value", accInfo[0]);
		$("#accNameHidden").attr("value", accInfo[1]);
		$(".beginPeriod").val(period.substring(0, 4) + '年' + period.substring(4, 6) + '期');
		$(".endPeriod").val(period.substring(0, 4) + '年' + period.substring(4, 6) + '期');
		if (accInfo[1] != undefined && accInfo[1] != "" && accInfo[1] != null) {
			$(".account").val(accInfo[2] + '-' + accInfo[1]);
		}else{
			$(".account").val("暂时没有科目");	
		}
	}
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
// 加载科目信息
function accountInfo(periodBegin, periodEnd) {
	var accId = '';
	var accName = '';
	var accountId = '';
	var params = 'accountPeriod=' + periodBegin + '&periodEnd=' + periodEnd;
	$.ajax({
		type : 'POST',
		url : ctx + '/books/Subsidiary/listAccount',
		cache : false,
		async : false,
		dataType : 'json',
		data : {
			'param' : ''
		},
		success : function(data) {
			if(data.length > 0){
				accId = data[0].id;
				accountId = data[0].accuntId;
				if (data[0].parentName != undefined && data[0].parentName != "" && data[0].parentName != null) {
					accName += data[0].parentName + '-';
				}
				if (data[0].accountName != undefined && data[0].accountName != "" && data[0].accountName != null) {
					accName += data[0].accountName;
				}
				var str = '';
				for ( var i in data) {
					var result = data[i];
					var accNames = '';
					if (result.parentName != undefined && result.parentName != "" && result.parentName != null) {
						accNames += '-' + data[i].parentName;
					}
					if (result.accountName != undefined && result.accountName != "" && result.accountName != null) {
						accNames += '-' + data[i].accountName;
					}
					str += '<li class="subject" accId= "' + result.id + '" accountId = "'+result.accuntId+'" parentName = "'+result.parentName+'" accountName = "'+ result.accountName +'">' + result.accuntId + accNames + '</li>';
				}
				str += '';
				$("#acc").html(str);	
			}
		}
	});
	var accInfo = new Array(2);
	accInfo[0] = accId;
	accInfo[1] = accName;
	accInfo[2] = accountId;
	return accInfo;
}
// 加载table信息
function tableInfo(accId, beginPeriod, endPeriod) {
	var params = 'accountPeriod=' + beginPeriod + '&periodEnd=' + endPeriod + '&accId=' + accId + '&ifAjax=yes';
	$.ajax({
		type : 'POST',
		url : ctx + '/books/Subsidiary/list',
		cache : false,
		async : false,
		dataType : 'json',
		data : params,
		success : function(data) {
			var str = '';
			for ( var i in data) {
				var result = data[i];
				str += '<tr>';
				str += strToUndefined(result.accountDateString,undefined);
				str += strToUndefined(result.voucherTitleName,result.voucherTitleNumber);
				str += strToUndefined(result.exp,undefined);
				str += strToUndefined(result.debit,undefined);
				str += strToUndefined(result.credit,undefined);
				str += strToUndefined(result.dc,undefined);
				str += strToUndefined(result.balance,undefined);
				str += '</tr>';
			}
			$("#tableInfo").html(str);
			tableHeight();
		}
	});
}
/*判断表格的高度*/
function tableHeight() {
	var tb_width = $(".tb").width();
	var tb_scrllbar_width = 17;
	if($(".table-account-wrapper tbody").height() <= $(window).height() - 177) {
    	/*$('#myTable02').fixedHeaderTable({ footer: false, altClass: 'odd' });*/
		$('.fht-tbody #myTable02').css("width",tb_width);
    }else {
    	$('#myTable02').fixedHeaderTable({ footer: false, altClass: 'odd' });
    	$(".fht-tbody").css({
    		"height": $(window).height() - 178
    	});
    	$('.fht-tbody #myTable02').css({
    		"width":tb_width - tb_scrllbar_width,
		    "border-collapse": "inherit"
    	});
    }
}
function strToUndefined(str1,str2) {
	var td = "";
	if (str1 != undefined && str1 != "" && str1 != null) {
		if (str2 != undefined && str2 != "" && str2 != null) {
			//td = '<td>' + str1 + PrefixInteger(str2,3) + '</td>';
			td = '<td>' + str1 + str2 + '</td>';
		}else{
			td = '<td>' + str1 + '</td>';
		} 
	}else {
		td = '<td></td>';
	}
	return td;
}

function titleInfoFun(beginPeriod, endPeriod, accountName) {

	if (typeof (accountName) == "undefined" || accountName == 0) {
		accountName = "明细账";
	} else {
		accountName = '&nbsp;' + accountName + "明细账";
	}

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
//满足3位 0 补齐
function PrefixInteger(num, length) {  
	return (Array(length).join('0') + num).slice(-length); 
}
