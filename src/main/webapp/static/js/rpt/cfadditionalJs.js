$(function() {

	// 获取参数
	var accountPeriod = $("#periodBeginHidden").val();
	var periodEnd = $("#periodEndHidden").val();
	var periodtype = $("#periodTypeHidden").val();
	var emptyRecal = false;
	// 加载table信息
	tableInfo(accountPeriod, periodEnd, periodtype, emptyRecal);
	titleInfoFun(accountPeriod, periodtype);
	//文本框获取焦点  内容选中
	 $(":text").focus(function(){
        this.select();
    });
})

// 加载table信息
function tableInfo(periodBegin, periodEnd, periodtype, emptyRecal) {
	var params = 'accountPeriod=' + periodBegin + '&periodEnd=' + periodEnd + '&periodtype=' + periodtype + '&emptyRecal=' + emptyRecal;
	$.ajax({
		type : 'POST',
		url : ctx + '/rpt/cfadditional/list',
		cache : false,
		async : false,
		dataType : 'json',
		data : params,
		success : function(data) {
			var str = '';
			var funNumber = 1;
			for ( var i in data.listECashFlow) {
				var result = data.listECashFlow[i];
				var num = parseInt(i) + 1;
				var countNameYear = "yearBalance" + num;
				var countNamePeriod = "periodBalance" + num;
				if (!result.asIsALabel) {
					funNumber = funNumber + 1;
				}
				str += '<input id="reportItem' + num + '" type="hidden" value="' + result.reportItem + '"/>';
				str += '<input id="tcashFowId' + num + '" type="hidden" value="' + result.tcashFowId + '"/>';
				str += '<input id="groupId' + num + '" type="hidden" value="' + result.groupId + '"/>';
				str += '<tr>';
				str += strToProject(result);
				str += strToLineNumber(result.lineNumber);
				str += strToNumber(result.periodBalance, countNamePeriod, result.asIsALabel, funNumber);
				str += strToNumber(result.yearBalance, countNameYear, result.asIsALabel, funNumber);
				str += '</tr>';
			}
			$("#tableInfoCountHidden").val(data.listECashFlow.length);
			$("#tableInfo").html(str);
		}
	});
}

function strToProject(data) {
	var td = "";
	if (data.projectName != undefined && data.projectName != "" && data.projectName != null) {
		if (data.asIsALabel) {
			// <a href="javascript:void(0)" class="dialog_link"
			// onclick="formulaPage('${eProfit.id }','${eProfit.reportItem }')">
			td = '<td class="td-tip">' + data.projectName + '</td>';
		} else {
			td = '<td class="td-tip">' + data.projectName + '</td>'
		}
	} else {
		td = '<td></td>';
	}
	return td;
}

function strToLineNumber(lineNumber) {
	var td = "";
	if (lineNumber != undefined && lineNumber != "" && lineNumber != null) {
		td = '<td>' + lineNumber + '</td>';
	} else {
		td = '<td></td>';
	}
	return td;
}

function strToNumber(balance, countName, asIsALabel, funNumber) {
	var attribute = "";
	if (!asIsALabel) {
		attribute = 'disabled';
	}
	var td = "";
	if (balance != undefined && balance != "" && balance != null) {
		td = '<td><input id="' + countName + '" type="text" value="' + balance + '" onblur="textOnChange' + funNumber + '($(this))" onfocus="unformatMoney($(this))" onkeyup="keyUp($(this))" ' + attribute + ' style="width:90%; text-align: right;"/></td>';
	} else {
		td = '<td><input id="' + countName + '" type="text" value="" onblur="textOnChange' + funNumber + '($(this))" onfocus="unformatMoney($(this))" onkeyup="keyUp($(this))" ' + attribute + ' style="width:90%; text-align: right;"/></td>';
	}
	return td;
}
function titleInfoFun(beginPeriod, periodtype) {

	var accountName = "调整辅助数据项";
	var year = beginPeriod.substr(0, 4);
	var month = beginPeriod.substr(4, 6);

	if (eval(periodtype) == 1) {
		accountName = year + '年' + month + '期' + accountName;
	} else {
		accountName = year + '年' + month + '季度' + accountName;
	}
	$("#cfadditionalTitle").html(accountName);

}

// =======================================保存现金流量附加表信息 begin
function savaECashFlow() {
	var result = "";
	var tableInfoCount = $("#tableInfoCountHidden").val();
	for ( var i = 1; i <= tableInfoCount; i++) {
		var tcashFowId = $("#tcashFowId" + i).val();
		var reportItem = $("#reportItem" + i).val();
		var yearBalance = returnUnformatMoney($("#yearBalance" + i));
		var periodBalance = returnUnformatMoney($("#periodBalance" + i));
		var groupId = $("#groupId" + i).val();
		if (tcashFowId != null && tcashFowId != "" && tcashFowId != undefined || reportItem != null && reportItem != "" && reportItem != undefined || yearBalance != null && yearBalance != "" && yearBalance != undefined || periodBalance != null && periodBalance != "" && periodBalance != undefined || groupId != null && groupId != "" && groupId != undefined) {
			result += "{'tcashFowId':'" + tcashFowId + "','reportItem':'" + reportItem + "','yearBalance':'" + yearBalance + "','periodBalance':'" + periodBalance + "','groupId':'" + groupId + "'},";

		}
	}
	result = "[" + result.substring(0, result.length - 1) + "]";
	var accountPeriod = $("#periodBeginHidden").val();
	var periodtype = $("#periodTypeHidden").val();
	window.location = ctx + "/rpt/cfadditional/save?info=" + result + "&accountPeriod=" + accountPeriod + "&periodtype=" + periodtype;
}
// =======================================保存现金流量附加表信息 end

// =======================================文本框改变事件 begin

function textOnChange1(obj) {
	formatMoney(obj);
	var yearBalance1 = returnUnformatMoney($("#yearBalance1"));
	var periodBalance1 = returnUnformatMoney($("#periodBalance1"));

	var yearBalance2 = returnUnformatMoney($("#yearBalance2"));
	var periodBalance2 = returnUnformatMoney($("#periodBalance2"));

	var yearBalance3 = returnUnformatMoney($("#yearBalance3"));
	var periodBalance3 = returnUnformatMoney($("#periodBalance3"));

	var yearBalanceT = parseFloat(yearBalance1) + parseFloat(yearBalance2) + parseFloat(yearBalance3);
	var periodBalanceT = parseFloat(periodBalance1) + parseFloat(periodBalance2) + parseFloat(periodBalance3);

	formatMoney($("#yearBalance4").val(yearBalanceT));
	formatMoney($("#periodBalance4").val(periodBalanceT));

}
function textOnChange2(obj) {
	formatMoney(obj);
	var yearBalance5 = returnUnformatMoney($("#yearBalance5"));
	var periodBalance5 = returnUnformatMoney($("#periodBalance5"));

	var yearBalance6 = returnUnformatMoney($("#yearBalance6"));
	var periodBalance6 = returnUnformatMoney($("#periodBalance6"));

	var yearBalanceT = parseFloat(yearBalance5) - parseFloat(yearBalance6);
	var periodBalanceT = parseFloat(periodBalance5) - parseFloat(periodBalance6);
	formatMoney($("#yearBalance7").val(yearBalanceT));
	formatMoney($("#periodBalance7").val(periodBalanceT));
}
function textOnChange3(obj) {
	formatMoney(obj);
	var yearBalance7 = returnUnformatMoney($("#yearBalance7"));
	var periodBalance7 = returnUnformatMoney($("#periodBalance7"));

	var yearBalance8 = returnUnformatMoney($("#yearBalance8"));
	var periodBalance8 = returnUnformatMoney($("#periodBalance8"));

	var yearBalance9 = returnUnformatMoney($("#yearBalance9"));
	var periodBalance9 = returnUnformatMoney($("#periodBalance9"));

	var yearBalance10 = returnUnformatMoney($("#yearBalance10"));
	var periodBalance10 = returnUnformatMoney($("#periodBalance10"));

	var yearBalance11 = returnUnformatMoney($("#yearBalance11"));
	var periodBalance11 = returnUnformatMoney($("#periodBalance11"));

	var yearBalanceT = parseFloat(yearBalance7) + parseFloat(yearBalance8) + parseFloat(yearBalance9) + parseFloat(yearBalance10) + parseFloat(yearBalance11);
	var periodBalanceT = parseFloat(periodBalance7) + parseFloat(periodBalance8) + parseFloat(periodBalance9) + parseFloat(periodBalance10) + parseFloat(periodBalance11);
	formatMoney($("#yearBalance12").val(yearBalanceT));
	formatMoney($("#periodBalance12").val(periodBalanceT));
}
function textOnChange4(obj) {
	formatMoney(obj);
}
// =======================================文本框改变事件 end

// =======================================按钮提交事件 begin

// 跳转到下一步页面
function nextECashFlow() {
	var accountPeriod = $("#periodBeginHidden").val();
	var periodEnd = $("#periodEndHidden").val();
	var periodtype = $("#periodTypeHidden").val();
	window.location.href = ctx + "/rpt/cashFlow/listCashFlowAdjustPage?accountPeriod=" + accountPeriod + "&periodEnd="+periodEnd+"&periodtype=" + periodtype;
}

// 清空并重算
function emptyRecal() {
	var accountPeriod = $("#periodBeginHidden").val();
	var periodEnd = $("#periodEndHidden").val();
	var periodtype = $("#periodTypeHidden").val();
	var emptyRecal = true;
	tableInfo(accountPeriod, periodEnd, periodtype, emptyRecal);
}
// =======================================按钮提交事件 end

// =======================================判断是否是数字 begin

function formatMoney(input) {
	if (parseInt(input.val()) == 0) {
		input.val("");
	}
	if (input.val() != null && typeof (input.val()) != "undefined" && input.val() != 0) {
		input.val($.formatMoney(input.val(), 2));
	}

}
function unformatMoney(input) {
	if (input.val() != null && typeof (input.val()) != "undefined" && input.val() != 0) {
		input.val($.unformatMoney(input.val()));
	}
}
function returnUnformatMoney(input) {
	return $.unformatMoney(input.val());
}
// =======================================判断是否是数字 end

// =======================================输入判断事件 begin

function keyUp(obj, event) {
	var event = event || window.event;
	obj.val(obj.val().replace(/[^\d.-]/g, "").replace(/\-{2,}/g, "").replace(/^\-{2,}/g, "-").
	// 只允许一个小数点
	replace(/^\./g, "").replace(/\.{2,}/g, ".").
	// 只能输入小数点后两位
	replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));
}
// =======================================输入判断事件 end ==========================

// ========================================调整公式事件 begin
// 暂时不用==============================
/*
 * $(function (){
 * 
 * var accountPeriod = "${accountPeriod }"; var year= accountPeriod.substr(0,4);
 * 
 * if(${periodtype }==1){ //月度表 var month=accountPeriod.substr(4,6);
 * $("#cfadditionalTitle").html(year+'年&nbsp;'+month+'月&nbsp;现金流量辅助表'); }else{
 * //季度表 var month=accountPeriod.substr(5,6);
 * $("#cfadditionalTitle").html(year+'年&nbsp;第'+month+'季度&nbsp;现金流量辅助表'); } //弹框 $(
 * "#dialog" ).dialog({ autoOpen: false, width: 840, modal: true }); });
 * //跳转到公式页面 function formulaPage(id,reportItem){ var accountPeriod=
 * ${accountPeriod }; var periodtype = ${periodtype}; $.ajax({ type:"POST",
 * url:"${ctx}/rpt/formula/listCfadditional?id="+id+"&accountPeriod="+accountPeriod+"&&periodtype="+periodtype,
 * dataType : 'json', success:function(json){
 * $("#reportitem").attr("value",reportItem); $("#id").attr("value",id);
 * $("#listEFormula").setTemplateElement("template").processTemplate(json); },
 * error:function(){alert("错误");} }); $( "#dialog" ).dialog( "open" ); } //删除
 * function deleteCfadditional(id){ $.ajax({
 * 
 * type:"POST", url:"${ctx}/rpt/formula/deleteCfadditional?id="+id, dataType :
 * 'json', success:function(json){ $('#'+id).parent().parent().remove(); },
 * error:function(){alert("错误");} }); $( "#dialog" ).dialog( "open" ); } //保存
 * function saveCfadditional(){ $("#inputFormFormual").ajaxSubmit({ type:
 * 'post', url: "${ctx}/rpt/formula/saveCfadditional" , dataType : 'json', data:
 * $("#inputForm").serialize(), success: function(json){ var reportitem =
 * $("#reportitem").attr("value"); var id = $("#id").attr("value");
 * formulaPage(id,reportitem); }, error: function(XmlHttpRequest, textStatus,
 * errorThrown){ alert("error"); }
 * 
 * }); }
 */
// ========================================调整公式事件 end
