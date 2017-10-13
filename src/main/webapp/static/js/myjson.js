
//下拉框改变事件
$("#Period").on("change",function(){
	$("#searchForm").submit();
})

//删除方法 
//点击事件删除确定事件

$(".dialog_remove").on("click",function(){
	id=$(this).attr('id');
	$( "#dialog_remove" ).dialog( "open" );
	$(".cancel").on("click",function() {
		$( "#dialog_remove" ).dialog( "close" );
	})
})

//删除事件
$("button.suredelete").on("click",function(){
	//alert('123');
	$( "#dialog_remove" ).dialog("close");
	//alert(id);
	window.location.href = ctx+"/voucherexp/tVoucherExp/deletebytvid?tVId="+id;
	/*$.ajax({
        type: "POST",//使用post方法访问后台
        url: "/yunzh/a/voucherexp/tVoucherExp/deletebytvid",//要访问的后台地址  
        data: "tVId="+id,
        success: function(data){
        	$('.tb_scroll').empty();
        	var json=eval(data);
        	
        	$.each(json,function(n,val){
        		console.log(val.vouchertitlenumber);
        		var list=val.tvoucherList;
        		var showexp=eval(list);
        		var showinfo="";
        		$.each(showexp,function(n,valnum){
        			showinfo+="<tr>"+"<td  width='25%'>"+valnum.exp+"</td>"+"<td  width='35%'>"+valnum.accountName+"</td>"+"<td  width='20%'>"+valnum.debit+"</td>"+"<td  width='20%'>"+valnum.credit+"</td>"+"</tr>";
        		})
        		$('.tb_scroll').append(
        		"<div class='contentTable showTable'>"
        		+"<table id='contentTable' class='table' width='100%' cellpadding='0' cellspacing='0'>"
        		+"<thead>"
        		+"<tr><td class='list-head' colspan='4'>"
        		+"<div class='clearfix'>"
        		+"<span>日期:</span>"
        		+"<span>凭证字号：记_"+val.vouchertitlenumber+"</span>"
        		+"<span>记账人："+val.username+"</span>"
        		+"<div class='hoverCont'> "
        		+"<span class='s-icon image'><i></i>影像</span>"
        		+"<span id='updatebill' class='s-icon updatebill'><i></i>修改</span>"
        		+"<span id='"+val.tvoucherexptvid+"' class='s-icon dialog_remove'><i></i>删除</span>"
        		+"<span id='insertbill' class='s-icon insertbill'><i></i>插入</span>"
        		+"<span id='dydz"+val.tvoucherexptvid+"' class='s-icon dydz' onclick='dy()'><i></i>打印</span>"
        		+"<span class='s-pizhu s-icon' mouseout='test()'><i></i>批注"
        		+"<div class='pizhu'><em></em><em>0/100</em>"
        		+"<textarea style='height:90px;' placeholder='' name='' id='text"+val.tvoucherexptvid+"' cols='30' rows='10' focus='test()' blur='test()'></textarea>"
        		+"<div class='th-right clearfix btn_txt'><span id='settle${tvoucher.id}' class='pzupload'>提交</span></div></div>"
        		+"</span>"
        		+"<span id='sign"+val.tvoucherexptvid+"' class='sign' style='display:none;'></span>"
        		+"</div>"
        		+"</div>"
        		+"</td></tr>"
        		+"</thead>"
        		+"<tbody class='list-body'>"
        		+showinfo
        		+"<tfoot>"
        		+"<tr>"
        		+"<td class='list-foot' colspan='2'>合计："+val.money+"</td>"
        		+"<td class='total_text'>"+val.totalamount+"</td>"
        		+"<td class='total_text'>"+val.totalamount+"</td>"
        		+"</tr>"
        		+"</tfoot>"
        		+"</tr>"
        		+"</tbody>"
        		+"</table>"
        		+"</div>"
        		);
        	});
        	console.log($(".dialog_remove"));
        	ajaxshow();
        },
        error:function(date){
        	alert(date);
        }
  });*/
})
function ajaxshow(){
	
	//删除事件
	$(".dialog_remove").on("click",function(){
		id=$(this).attr('id');
		$( "#dialog_remove" ).dialog( "open" );
		$(".cancel").on("click",function() {
			$( "#dialog_remove" ).dialog( "close" );
		})
	});
	//批注提交按钮显示
	$('.pizhu textarea').on("focus",function(){$(this).parent().find('.btn_txt').css('visibility','visible')});
	//批注提交
	$(".pzupload").on("click",function(){
		var id=$(this).attr("id");
		var subid=$(this).attr("id").substring(6,($(this).attr("id").length));
		$.ajax({
		    type: "get",//使用post方法访问后台
	        url: ctx+"/voucher/tVoucher/updateexpComment?id="+subid.toString()+"&expcomment="+$("#text"+subid.toString()).val(),//要访问的后台地址  
	        success: function(data){
	        	$('#sign'+eval(data).id).css("display","inline-block")
	        },
	        error:function(data){
	        	alert('批注失败！');
		        }
		  })
	});
	
}
//打印全部
$('#pdf').on('click',function(){
   window.open(ctx+"/voucher/tVoucher/pdfShow?accountPeriod="+$("#Period").val()+"&fdbid="+$("#fdbid").val()+"&count="+3,"","") ;
})
//打印模板
$('#twoDiv').on("click",function(){
	window.open(ctx+"/voucher/tVoucher/pdfModelTwo?accountPeriod="+$("#Period").val()+"&fdbid="+$("#fdbid").val()+"&count="+2,"","") ;
})
$('#oneDivfour').on("click",function(){
	window.open(ctx+"/voucher/tVoucher/createTable24?accountPeriod="+$("#Period").val()+"&fdbid="+$("#fdbid").val()+"&count="+1,"","");
})

$('#threeDiv').on("click",function(){
	window.open(ctx+"/voucher/tVoucher/pageShowThreeNew?accountPeriod="+$("#Period").val()+"&fdbid="+$("#fdbid").val()+"&count="+3,"","") ;
})
$('#oneDivtwo').on("click",function(){
	window.open(ctx+"/voucher/tVoucher/createTable12New?accountPeriod="+$("#Period").val()+"&fdbid="+$("#fdbid").val()+"&count="+1,"","");
})
//点击的时候出现提交按钮
$('.pizhu textarea').on("focus",function(){$(this).parent().find('.btn_txt').css('visibility','visible')})
/*.focus(function(){
	$(this).parent().find('.btn_txt').css('visibility','visible');
})*/
//修改凭证
$(".updatebill").on("click",function(e){
	var id = $(this).data("id");
	window.location.href = ctx+"/voucher/tVoucher/modifyVoucher?id="+id+"&accountPeriod="+$("#Period").val();	 
})
//插入凭证
$(".insertbill").on("click",function(e){
	var title = $(this).data("title");
	window.location.href = ctx+"/voucher/tVoucher/insertVoucher?title="+title+"&accountPeriod="+$("#Period").val();	 
})
//无刷新批注
function puzhuajax(date){
	  //console.log(date.attr("id"));
	   $.ajax({
	    type: "get",//使用post方法访问后台
        url: ctx+"/voucher/tVoucher/updateexpComment?id="+date+"&expcomment="+$("#text"+date).val(),//要访问的后台地址  
        success: function(data){
        	$('#sign'+eval(data).id).css("display","inline-block")
        },
        error:function(data){
        	alert('批注失败！');
	        }
	  })
}
$(".pzupload").on("click",function(){
		var id=$(this).attr("id");
		var subid=$(this).attr("id").substring(6,($(this).attr("id").length));
		puzhuajax(subid.toString());
})

 /*$(".image").on("click",function(){
	   $.ajax({
	        type: "POST",//使用post方法访问后台
	        url: "${ctx}/voucherexp/tVoucherExp/periodAjax",//要访问的后台地址  
	        success: function(data){
	        	
	        	var list=eval(data);
	        	$.each(list,function(n,val){
	        		$('.tabhead').after("<table id='contentTable' class='table' width='100%' cellpadding='0' cellspacing='0'>"
	        		+"<thead>"
	        		+"<tr><td class='list-head' colspan='4'>"
	        		+"<div class='clearfix'>"
	        		+"<span>日期:</span>"
	        		+"<span>凭证字号：记_"+val.voucherTitleNumber+"</span>"
	        		+"<span>记账人："+val.userName+"</span>"
	        		+"<div class='hoverCont'> "
	        		+"<span class='s-icon image'><i></i>影像</span>"
	        		+"<span id='updatebill' class='s-icon updatebill'><i></i>修改</span>"
	        		+"<span id='"+val.tvoucherexptVId+"' class='s-icon dialog_remove'><i></i>删除</span>"
	        		+"<span id='insertbill' class='s-icon insertbill'><i></i>插入</span>"
	        		+"<span class='s-icon' onclick='pdfshow("+val.tvoucherexptVId+")'><i></i>打印</span>"
	        		+"<span class='s-pizhu s-icon' mouseout='test()'><i></i>批注"
	        		+"<div class='pizhu'><em></em><em>0/100</em>"
	        		+"<textarea style='height:90px;' placeholder='' name='' id='text"+val.tvoucherexptVId+"' cols='30' rows='10' focus='test()' blur='test()'>${tvoucher.expComment}</textarea>"
	        		+"<input id='pizhusubmit' onclick='puzhuajax("+val.tvoucherexptVId+")' type='button' value='提交' class='btn_txt'/></div>"
	        		+"</span>"
	        		+"<span id='sign"+val.tvoucherexptVId+"' class='sign' style='display:none;'></span>"
	        		+"</div>"
	        		+"</div>"
	        		+"</td></tr>"
	        		+"</thead>"
	        		+"</table>");
	        	});
	        	//alert("成功"+eval(data[0].count));
	        },
	        error:function(date){
	        	alert("失败");
	        }
	  });
   })*/

