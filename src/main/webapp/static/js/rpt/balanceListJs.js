/*document.write("<script language=javascript src='" + ctxStatic + "/js/jquery-1.11.2.min.js'></script>");*/
$(function() {
	var period = periodInfo(); // 加载账期信息
	tableInfo(period, period); // 加载table信息
	$("#beginPeriodHidden").attr("value", period);
	$(".beginPeriod").val(period.substring(0, 4) + '年' + period.substring(4, 6) + '期');
	
	//弹框
	$( "#dialog" ).dialog({
		autoOpen: false,
		width: 840,
		modal: true
	});
	$(".sure,.i-close").on("click",function(event){
		$(".deleteSubject").hide();
		$(".mask").hide();
	});
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
	var balanceInfo = "";
	$.ajax({
		type : 'POST',
		url : ctx + '/rpt/balance/list',
		cache : false,
		async : false,
		dataType : 'json',
		data : params,
		success : function(data) {
			var str = '';
			$("#companyName").html('<i></i>'+data.companyName);
			$("#balanceInfo").val(data.balanceInfo);
			for ( var i in data.listEBalance) {
				var result = data.listEBalance[i];
				str += '<tr>';
				str += asStrToUndefined(result);
				str += liStrToUndefined(result);
				str += '</tr>';
			}
			$("#tableInfo").html(str);
		}
	});
}
//	资产类
function asStrToUndefined(result) {
	var td = "";
	if (result.assets != undefined && result.assets != "" && result.assets != null) {
		if (result.asIsALabel) {
			td += strToProjectNameLink(result.asId,result.asReportItem,result.assets);
		} else {
			td +=strToProjectName(result.assets);
		}
		td += strToLineNumber(result.asLineNumber);
		td += strToNumber(result.asPeriodEnd);
		td += strToNumber(result.asYearStart);
		
	} else {
		td = '<td></td>\
			  <td></td>\
			  <td></td>\
			  <td></td>';
	}
	return td;
}
//负债类
function liStrToUndefined(result) {
	var td = "";
	if (result.liabilities != undefined && result.liabilities != "" && result.liabilities != null) {
		if (result.liIsALabel) {
			td += strToProjectNameLink(result.liId,result.liReportItem,result.liabilities);
		} else {
			td +=strToProjectName(result.liabilities);
		}
		td += strToLineNumber(result.liLineNumber);
		td += strToNumber(result.liPeriodEnd);
		td += strToNumber(result.liYearStart);
		
	} else {
		td = '<td></td>\
			  <td></td>\
			  <td></td>\
			  <td></td>';
	}
	return td;
}
//项目名称显示处理
function strToProjectName(projectName){
	var td = '';
	if (projectName != undefined && projectName != "" && projectName != null){
		td = '<td><div class="td_col2">'+projectName+'</div></td>';
	}else{
		td ='<td></td>';
	}
	return td ;
}
//项目名称显示处理--超链接
function strToProjectNameLink(projectId,projectReportItem,projectName){
	var td = '';
	if (projectName != undefined && projectName != "" && projectName != null){
		td = '<td><div class="td_col2">\
			<a href="javascript:void(0)" class="dialog_link" onclick="formulaPage(' + projectId + ','+ projectReportItem + ')"> '+projectName+'\
			<button class="btn btn_e3 dialog_edit2"></button>\
			</a>\
		 </div></td>';
	}else{
		td ='<td></td>';
	}
	return td ;
}

function strToNumber(balance){
	var td = '';
	if (balance != undefined && balance != "" && balance != null){
		td = '<td>'+balance+'</td>';
	}else{
		td ='<td></td>';
	}
	return td ;
}
//行次处理
function strToLineNumber(lineNumber){
	var td = '';
	if (lineNumber != undefined && lineNumber != "" && lineNumber != null){
		td = '<td>'+lineNumber+'</td>';
	}else{
		td ='<td></td>';
	}
	return td ;
}

function titleInfoFun(beginPeriod,balanceInfo) {

	var accountName = "资产负债表";
	var year = beginPeriod.substr(0, 4);
	var month = beginPeriod.substr(4, 6);
	accountName = year + '年' + month + '期' + accountName;
	$("#balanceTitle").html(accountName);
	
	/* 加载系统提示 */
	if(balanceInfo == "true"){				//资产不平衡
//		   alert('资产负债表不平，请检查账务处理。');   
		$(".deleteSubject").show();
		$(".mask").show(); 
	}
}

//跳转到公式页面
function formulaPage(id,reportItem){
	var accountPeriod= $('#accountPeriod option:selected').html().replace(/[^0-9]+/g, '');
	$.ajax({  
		type:"POST",
			url:"${ctx}/rpt/formula/listBalance?id="+id+"&accountPeriod="+accountPeriod,  
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
function deleteBalance(id){
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
function savaBalance(){
	$("#inputForm").ajaxSubmit({
        type: 'post',
        url: "${ctx}/rpt/formula/saveBalance" ,
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

