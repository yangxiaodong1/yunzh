/*document.write("<script language=javascript src='" + ctxStatic + "/js/jquery-1.11.2.min.js'></script>");*/
$(function() {
	var period = periodInfo(); // 加载账期信息
	var accInfo = accountInfo(period); // 加载科目信息
	tableInfo(accInfo[0], period); // 加载table信息
	$("#periodHidden").attr("value", period);
	$("#accIdHidden").attr("value", accInfo[0]);
	$("#accNameHidden").attr("value", accInfo[1]);
	$(".period").val(period+'年');
	if (accInfo[1] != undefined && accInfo[1] != "" && accInfo[1] != null) {
		$(".account").val(accInfo[2] + '-' + accInfo[1]);
	}else{
		$(".account").val("暂时没有科目");	
	}
})
// 加载账期信息
function periodInfo() {
	var period = '';
	$.ajax({
		type : 'POST',
		url : ctx + '/books/generalLedger/listHappenYear',
		cache : false,
		async : false,
		dataType : 'json',
		data : {
			'param' : ''
		},
		success : function(data) {
			var str = '';
			period = data.defaultYear;
			for ( var i in data.listYear) {
				var result = data.listYear[i];
				str += '<li class="subject" period = "' + result + '">' + result + '年 </li>';
			}
			$(".period").html(str);
		}
	});
	return period;
}
// 加载科目信息
function accountInfo(period) {
	var accId = '';
	var accName = '';
	var accountId = '';
	var params = 'period=' + period;
	$.ajax({
		type : 'POST',
		url : ctx + '/books/generalLedger/listHappenAccount',
		cache : false,
		async : false,
		dataType : 'json',
		data : params,
		success : function(data) {
			
				accId = data.defaultAccount.id;
				accName = data.defaultAccount.accountName;
				accountId = data.defaultAccount.accuntId;
				var str = '';
				for ( var i in data.listAccounts) {
					var result = data.listAccounts[i];
					str += '<li class="subject" accId= "' + result.id + '" accountId = "'+result.accuntId+'" parentName = "'+result.parentName+'" accountName = "'+ result.accountName +'">' + result.accuntId + result.accountName + '</li>';
				}
				$("#acc").html(str);
		}
	});
	var accInfo = new Array(2);
	accInfo[0] = accId;
	accInfo[1] = accName;
	accInfo[2] = accountId;
	return accInfo;
}
// 加载table信息
function tableInfo(accId, period) {
	var params = 'period=' + period  + '&accId=' + accId;
	$.ajax({
		type : 'POST',
		url : ctx + '/books/generalLedger/list',
		cache : false,
		async : false,
		dataType : 'json',
		data : params,
		success : function(data) {
			var str = '';
			for ( var i in data.listGeneralLedger) {
				var result = data.listGeneralLedger[i];
				str += '<tr>';
				console.log(result.accountPeriod);
				str += strToUndefined(result.accountPeriod);
				str += strToUndefined(result.exp);
				str += strToUndefined(result.debit);
				str += strToUndefined(result.credit);
				str += strToUndefined(result.dc);
				str += strToUndefined(result.endbalance);
				str += '</tr>';
			}
			$("#tableInfo").html(str);
			tableHeight();
		}
	});
}
/* 判断表格的高度 */
function tableHeight() {
	var tb_width = $(".tb").width();
	var tb_scrllbar_width = 17;
	if($(".table-account-wrapper tbody").height() <= $(window).height() - 177) {
    	/* $('#myTable02').fixedHeaderTable({ footer: false, altClass: 'odd' }); */
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
function strToUndefined(result) {
	var td = "";
	if (result != undefined && result != "" && result != null) {
		td = '<td>' + result + '</td>';
	}else {
		td = '<td></td>';
	}
	return td;
}

function titleInfoFun(year, accountName) {

	if (typeof (accountName) == undefined || accountName == 0) {
		accountName = "总账";
	} else {
		accountName = '&nbsp;' + accountName + "总账";
	}
	accountName = year + '年' + accountName;
		
	$("#accountName").html(accountName);

}
// 满足3位 0 补齐
function PrefixInteger(num, length) {  
	return (Array(length).join('0') + num).slice(-length); 
}
//跳转到明细账页面
function jumpPage(){
	var accountPeriod = $('#accountPeriod option:selected').html().replace(/[^0-9]+/g, '');
	periodStart = accountPeriod + "01";
	periodEnd = accountPeriod + "12";
	var id = $('#id option:selected').attr("value");
	window.location.href="${ctx}/books/Subsidiary/listSubsidiaryledge?accId="+id+"&accountPeriod="+periodStart+"&&periodEnd="+periodEnd; 
}
