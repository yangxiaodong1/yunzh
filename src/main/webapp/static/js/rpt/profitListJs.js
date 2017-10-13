/*document.write("<script language=javascript src='" + ctxStatic + "/js/jquery-1.11.2.min.js'></script>");*/
$(function() {
	var period = periodInfo(); // 加载账期信息
	tableInfo(period, period); // 加载table信息
	$("#beginPeriodHidden").attr("value", period);
	$("#endPeriodHidden").attr("value", period);
	$(".beginPeriod").val(period.substring(0, 4) + '年' + period.substring(4, 6) + '期');
	$(".endPeriod").val(period.substring(0, 4) + '年' + period.substring(4, 6) + '期');
	
	//弹框
//	$( "#dialog" ).dialog({
//		autoOpen: false,
//		width: 840,
//		modal: true
//	});
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
		url : ctx + '/rpt/profit/list',
		cache : false,
		async : false,
		dataType : 'json',
		data : params,
		success : function(data) {
			$("#companyName").html('<i></i>'+data.companyName);
			var str = '';
			
			for ( var i in data.listEProfit) {
				var result = data.listEProfit[i];
				str += '<tr>';
				str += strProjectName(result);
				str += '<td>'+result.lineNumber+'</td>';
				str += strBalance(result.yearBalance);
				str += strBalance(result.periodBalance);
				str += '</tr>';
			}
			$("#tableInfo").html(str);
		}
	});
}

function strProjectName(result) {
	var td = "";
	if (result.asIsALabel) {
		td = '<td class="td-tit"><a href="javascript:void(0)" onclick="formulaPage('+result.id+','+result.reportItem+')">'+result.projectName+'</a></td>'
	}else {
		td = '<td class="td-tip">'+result.projectName+'</td>';
	}
	return td;
}
function strBalance(dataBalance) {
	var td = "";
	if (dataBalance != undefined && dataBalance != "" && dataBalance != null) {
		td = '<td >'+dataBalance+'</td>'
	}else {
		td = '<td></td>';
	}
	return td;
}
function titleInfoFun(beginPeriod, endPeriod) {

	var accountName = "利润表";

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
	$("#ProfitTitle").html(accountName);

}



// ===============公式编辑

//跳转到公式页面
function formulaPage(id,reportItem){
	var accountPeriod= $('#accountPeriod option:selected').html();
	var periodEnd= $('#periodEnd option:selected').html();
	 $.ajax({  
		type:"POST",
			url:"${ctx}/rpt/formula/listProfit?id="+id+"&accountPeriod="+accountPeriod+"&periodEnd="+periodEnd ,  
			dataType : 'json',
	       	success:function(json){ 
	       		$("#reportitem").attr("value",reportItem);
	       		$("#id").attr("value",id);
	    		$("#listEFormula").setTemplateElement("template").processTemplate(json);
	       },
	       error:function(){alert("错误");}
	 }); 
	 $( "#dialog" ).dialog( "open" ); 
}
//删除
function deleteProfit(id){
	 $.ajax({ 
		
		type:"POST",
			url:"${ctx}/rpt/formula/deleteBalance?id="+id,  
			dataType : 'json',
	       	success:function(json){  
	    		$('#'+id).parent().parent().remove();
	       },
	       error:function(){alert("错误");}
	 }); 
	 $( "#dialog" ).dialog( "open" ); 
}
//保存
function savaProfit(){
	 $("#inputForm").ajaxSubmit({
        type: 'post',
        url: "${ctx}/rpt/formula/saveProfit" ,
        dataType : 'json',
        data: $("#inputForm").serialize(),
        success: function(json){
        	var reportitem = $("#reportitem").attr("value");
        	var id = $("#id").attr("value");
        	formulaPage(id,reportitem);
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
        	alert("error");
        }   
        
 	}); 
	
}

