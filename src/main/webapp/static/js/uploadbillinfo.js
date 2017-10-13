///////////////////////////////////////////billinfo start//////////////////////////////////////////////////////////////
//信息提示窗
var messagePop = function(str){
		$(".message-pop").children("p").html(str);
		$(".message-pop").animate({bottom:"30px",right:"10px",opacity:"1"},"slow",function(){
		})
		setTimeout(function(){
			$(".message-pop").animate({bottom:"-100px",right:"-250px",opacity:"0"},"slow",function(){
			})
		},2000)
}

var contInState = true;
var contdeState = true;

$(function(){
	
	//页面初始化获取数据
	billinfoshow();
	imgopreation();//图片模式-操作
	commonOperation();//通用操作
	operationFun();//数据交互操作
})

function operationFun(){
	//凭证-操作-删除-确认
	$("body").on("click",".reminder-save",function(){
		billInfoDel();
	})
	
	//凭证-操作-跨期-确定
	$("body").on("click",".instrument-save",function(){
		billInfoPeriod();
	})
}

function commonOperation(){
	
	//日期改变或者是否过滤选中
	$("body").on("change","#sel_yn,#cancelFlag",function(){
		billinfoshow();
	})
	
	//弹窗关闭
	$("body").on("click",".bill-info-close-dialog,.cancel",function(){
		$(".kindlyReminder").hide();
		$(".mask").hide();
		$(".dialog").hide();
		$(".rightSlide").animate({right:"-392"});
		$(".spInteOn").addClass("spInte").removeClass("spInteOn");
		$(".spDetaOn").addClass("spDeta").removeClass("spDetaOn");
	})
}

//图片模式-操作
function imgopreation(){
	
	//图片模式-操作-作废
	$("body").on("click",".dialog-link3",function(){
		var id = $(this).parent().parent().parent().data("id");
		$("#reminder-id").val(id);
		$(".dialog").hide();
		$(".mask").show();
		$(".reminder").show();
	})

	//图片模式-操作-编辑
	$("body").on("click",".dialog-link2",function(){
		var id = $(this).parent().parent().parent().data("id");
		var imageUrl = $(this).parent().parent().parent().data("imageurl");
		$("#kindlyReminder-id").val(id);
		$("#bill-info-img",".kindlyReminder").attr("src",imageUrl);
		$(".dialog").hide();
		$(".mask").show();
		$(".kindlyReminder").show();
	})
	
	//图片模式-操作-跨期
	$("body").on("click",".dialog-link",function(){
		var id = $(this).parent().parent().parent().data("id");
		var time = $(this).parent().parent().parent().data("signdate")+"";
		var str = time.split("-")[0]+"-"+time.split("-")[1];
		$(".instrument").find(".time-from").val(str);
		$("#instrument-id").val(id);
		$(".instrument").find(".select-time").val('');
		$(".dialog").hide();
		$(".mask").show();
		$(".instrument").show();
	})
	
	//图片模式-编辑-票据作废
	$("body").on("click","#dialog-big-del",function(){
		var id = $("#kindlyReminder-id").val();
		$("#reminder-id").val(id);
		$(".dialog").hide();
		$(".mask").show();
		$(".reminder").show();
	})
	
	//图片模式-编辑-跨期
	$("body").on("click","#dialog-big-period",function(){
		var id = $("#kindlyReminder-id").val();
		var time = $(".img-show-"+id).data("signdate")+"";
		var str = time.split("-")[0]+"-"+time.split("-")[1];
		$(".instrument").find(".time-from").val(str);
		$("#instrument-id").val(id);
		$(".instrument").find(".select-time").val('');
		$(".dialog").hide();
		$(".mask").show();
		$(".instrument").show();
	})
	
	//图片模式-编辑-新增凭证
	$("body").on("click","#dialog-big-add",function(){
		var id = $("#kindlyReminder-id").val();
		$("#to-add-voucher-form-billInfos").val(id);
		$("#to-add-voucher-form-submit").submit();
	});
	
}


//发票信息显示
function billinfoshow(){
	$.ajax({
	     type: 'POST',
	     url: ctx+'/billinfo/tDealBillInfo/queryUploadBillInfo',
	     cache:false,  
	     dataType:'json',
	     data: {
	    		"uploadPeriod":$("#sel_yn").val(),
	    		"cancelFlag":$("#cancelFlag").is(':checked')
	     } ,
	     success: function(data){
	    	var str = '';
	    	var result = data.list;
	    	for(var i in result){
	    		//str += '<li class="img-show-'+result[i].id+'" data-hasSaveVoucer="'+result[i].hasSaveVoucer+'"  data-id="'+result[i].id+'" data-number="'+result[i].invoiceNumber+'"  data-signdate="'+result[i].signDate.substr(0,10)+'" data-stas="true" data-imageurl="'+result[i].imageUrl+'"><img class="img-show-big" src="'+ image_path +result[i].imageName+'" width="226" height="156" alt=""><i class="billstatus billstatus'+result[i].billStatus+'"></i>';
	    		str += '<li class="img-show-'+result[i].id+'" data-hasSaveVoucer="'+result[i].hasSaveVoucer+'"  data-id="'+result[i].id+'" data-number="'+result[i].invoiceNumber+'"  data-signdate="'+result[i].signDate+'" data-stas="true" data-imageurl="'+result[i].imageUrl+'"><img class="img-show-big" src="'+ image_path +result[i].imageName+'" width="226" height="156" alt=""><i class="billstatus billstatus'+result[i].billStatus+'"></i>';
	    		
	    		str += '<div class="text-left pad_t_b5"><span class="left">发票编号：'+ result[i].invoiceCode +'</span><span class="right tit_p3">';
	    		if(result[i].billStatus != 3){
	    			str +=  '<a href="javascript:;" class="btn btn-c1 dialog-link2">编辑</a>';
	    		}
	    		if(result[i].billStatus != 4){
	    			str +='<a href="javascript:;" class="btn btn-c2 dialog-link3">作废</a>';
	    		}
	    		if(result[i].billStatus != 6 && result[i].billStatus != 7){
	    			str +='<a href="javascript:;" class="btn btn-c3 dialog-link">跨期</a>';
	    		}
	    		str +='</span><div class="clear"></div></div></li>';
	    	}
	    	$(".ul-pj").html(str);
	    	$("#result_num").html("上传共"+data.count+"张");
	     }
	});
}

//发票-删除（作废）
function billInfoDel(){
	var id = $("#reminder-id").val();
	$.ajax( {    
	    url:ctx+'/billinfo/tBillInfo/updateBillInfoScrap',
	    data:{    
	    	"id":id
	    },    
	    type:'post',    
	    cache:false,    
	    dataType:'json',    
	    success:function(data) {  
			if(data.suc){
				$(".dialog-link3",".img-show-"+id).remove();
				$(".span-tip").text("作废反馈成功").show();
			}else{
				$(".span-tip").text("作废反馈失败").show();
			}
			 msgFadeOut();
			$(".dialog").hide();
			$(".bill-info-close-dialog").click();
	     }  
	});
}

function billInfoPeriod(){
	var endTime = $(".instrument").find(".select-time").val();
	if(endTime.length < 1){
		messagePop("请选择跨期时间");
		return false;
	}
	var id = $("#instrument-id").val();
	$.ajax({
	     type: 'POST',
	     url: ctx+'/billinfo/tBillInfo/billInfoPeriod',
	     cache:false,  
	     dataType:'json',
	     data: {
	    		"id":id,
	    		"endTime":$(".instrument").find(".select-time").val()
	     } ,
	     success: function(data){
	    	 if(data){
				$(".btn-c3",".img-show-"+id).remove();
				$(".span-tip").text("跨期反馈成功").show();
			}else{
				$(".span-tip").text("跨期反馈失败").show();
			}
	    	 msgFadeOut();
	    	$(".dialog").hide();
	    	$(".bill-info-close-dialog").click();
	     }
	});
}

////////////////////////////////////////////////billinfo end////////////////////////////////////////////////////

//提示信息渐隐
function msgFadeOut(){
	setTimeout(function(){$(".span-tip").fadeOut("slow")},3000);
}