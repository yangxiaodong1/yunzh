function gotoPage(url){
	window.location.href=url;
}
//资金利润
function listRpt(param){
	var year = $(".year").text();
	var mouth = $(".mouth").text();
	param = year + mouth;
	url=ctx+"/weChat/WTableInfo/listRpt?accountPeriod="+param;
	ajaxGoPage(url);

}
//缴税
function listTaxation(param){
	var year = $(".year").text();
	var mouth = $(".mouth").text();
	param = year + mouth;
	url=ctx+"/weChat/WTableInfo/listTaxation?accountPeriod="+param;
	ajaxGoPage(url);

}
//公司社保
function listWageSocialSecurity(param){
	var year = $(".year").text();
	var mouth = $(".mouth").text();
	param = year + mouth;
	url=ctx+"/weChat/WTableInfo/listWageSocialSecurity?accountPeriod="+param;
	ajaxGoPage(url);

}
function ajaxGoPage(url){
	$.ajax({	
	     type: 'POST',
	     url: url,
	     dataType:'json',
	     success: function(data){
	    	 if(data.loginstate){
		    	 if(data.pageName == 'capitalProfitList'){	//资产负债
		    		var str = capitalProfitListFun(data);
		    		$("#aRept").addClass("active");
		    		$("#aTaxation").removeClass("active");
		    		$("#aWage").removeClass("active");
		    	 }
		    	 if(data.pageName == 'payTaxesList'){	//缴税
		    		var str = payTaxesListFun(data);
		    		$("#aRept").removeClass("active");
		    		$("#aTaxation").addClass("active");
		    		$("#aWage").removeClass("active");
		    	 }
		    	 if(data.pageName == 'salarySocialSecurityList'){	//工资社保
		    		var str = salarySocialSecurityListFun(data);
		    		$("#aRept").removeClass("active");
		    		$("#aTaxation").removeClass("active");
		    		$("#aWage").addClass("active");
		    	 }
		    	 $("#tableInfo").html(str);
	    	 }else{
	    		 window.location.href = '/weChat/loginInterface.jsp';
	    	 }
	    	 
	     },
	     error:function(){alert("错误");}
	});
}


//资产负债
function capitalProfitListFun(data){	
	var str='<div class="charge_table"><div class="table_head">资金负债表</div><div class="table_body">';
	var numOne = 1;
	var numTwo = 1;
	for(var i in data.rpt){
		var result = data.rpt[i];
		if(eval(numOne)%2 != 0){
			str+='<div class="first_line line"><div class="first_td"><div>'+result.rptNameL+'</div><div class="number">'+result.rptMoneyL+'</div></div><div class="second_td"><div>'+result.rptNameR+'</div><div class="number">'+result.rptMoneyR+'</div></div></div>';
		}else{
			str+='<div class="second_line line"><div class="first_td"><div>'+result.rptNameL+'</div><div class="number">'+result.rptMoneyL+'</div></div><div class="second_td"><div>'+result.rptNameR+'</div><div class="number">'+result.rptMoneyR+'</div></div></div>';
		}		
		numOne = eval(numOne)+1;
	}
	str+='</div></div><div class="space"></div><div class="space"></div><div class="charge_table"><div class="table_head">利润表</div><div class="table_body">';	
	for(var i in data.Profit){
		var result = data.Profit[i];
		if(eval(numTwo)%2 != 0){
			str+='<div class="first_line line"><div class="first_td"><div>'+result.rptNameL+'</div><div class="number">'+result.rptMoneyL+'</div></div><div class="second_td"><div>'+result.rptNameR+'</div><div class="number">'+result.rptMoneyR+'</div></div></div>';		
		}else{
			str+='<div class="second_line line"><div class="first_td"><div>'+result.rptNameL+'</div><div class="number">'+result.rptMoneyL+'</div></div><div class="second_td"><div>'+result.rptNameR+'</div><div class="number">'+result.rptMoneyR+'</div></div></div>';
		}
		
		numTwo = eval(numTwo)+1;
	}
	str+='</div></div>';
	return str;
}
//缴税
function payTaxesListFun(data){
	var numOne = 1;
	var str='<div class="charge_table"><div class="table_head">缴税明细表</div><div class="remark">每月15号为缴纳截止日</div><div class="table_body charge"><div class="line line_01 first_line"><div class="first_td center">税种</div><div class="center">缴纳金额</div></div>';
	for(var i in data.Taxation){
		var result = data.Taxation[i];
		if(eval(numOne)%2 != 0){
			str+='<div class="line first_line"><div class="first_td center">'+result.rptNameL+'</div><div class="center">'+result.rptMoneyL+'</div></div>';	
		}else{
			str+='<div class="line first_line"><div class="first_td center">'+result.rptNameL+'</div><div class="center">'+result.rptMoneyL+'</div></div>';
		}
		numOne = eval(numOne)+1;
	}
	str+='</div></div>';
	return str;
}
//工资社保
function salarySocialSecurityListFun(data){
	var numOne = 1;
	var numTwo = 1;
	var str='<div class="charge_table"><div class="table_head">工资费用表</div><div class="table_body">';
	for(var i in data.Salary){
		var result = data.Salary[i];
		if(eval(numOne)%2 != 0){
			str+='<div class="first_line line"><div class="first_td"><div>'+result.rptNameL+'</div><div class="number">'+result.rptMoneyL+'</div></div><div class="second_td"><div>'+result.rptNameR+'</div><div class="number">'+result.rptMoneyR+'</div></div></div>';
		}else{
			str+='<div class="second_line line"><div class="first_td"><div>'+result.rptNameL+'</div><div class="number">'+result.rptMoneyL+'</div></div><div class="second_td"><div>'+result.rptNameR+'</div><div class="number">'+result.rptMoneyR+'</div></div></div>';	
		}
		numOne = eval(numOne)+1;
	}
	str+='</div></div><div class="space"></div><div class="space"></div><div class="charge_table"><div class="table_head">社保费用表</div><div class="table_body">';
	for(var i in data.SocialSecurity){
		var result = data.SocialSecurity[i];
		if(eval(numTwo)%2 != 0){
			str+='<div class="first_line line"><div class="first_td"><div>'+result.rptNameL+'</div><div class="number">'+result.rptMoneyL+'</div></div><div class="second_td"><div>'+result.rptNameR+'</div><div class="number">'+result.rptMoneyR+'</div></div></div>';
		}else{
			str+='<div class="second_line line"><div class="first_td"><div>'+result.rptNameL+'</div><div class="number">'+result.rptMoneyL+'</div></div><div class="second_td"><div>'+result.rptNameR+'</div><div class="number">'+result.rptMoneyR+'</div></div></div>';	
		}
		numTwo = eval(numTwo)+1;
	}
	str+='</div></div>';
	return str;
}

