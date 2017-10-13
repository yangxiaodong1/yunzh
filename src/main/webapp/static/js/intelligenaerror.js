$(function(){
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
	
	//智能做账
	$("body").on("click","#intelligentAccount",function(){
		$.ajax({
		     type: 'POST',
		     url: ctx+'/billinfo/tDealBillInfo/intelligentAccount',
		     cache:false,  
		     dataType:'json',
		     data: {
		    		"uploadPeriod":$("#sel_yn").val()
		     } ,
		     success: function(data){
		    	 if(data){
		    		  window.location.href= ctx +"/billinfo/tDealBillInfo/loading?uploadPeriod="+$("#sel_yn").val();
		    	 }
		     }
		});
	})
	
	//联查票据
	$("body").on("click","#linked-notes",function(){
		var selectBill = $(".ul-pj").find(".check");
		if(selectBill.length < 1){
			alert("请选择票据")
			return false;
		}
		if($(selectBill).data("type")==1){
			alert("请选择普通票据");
		}
		var hasSaveVoucer = $(selectBill).data("hassavevoucer");
		
		if(hasSaveVoucer){
			var id = $(selectBill).data("id");
			var relateVoucher = $(selectBill).data("relatevoucher");
			relateVoucher = '116';
			var accountPeriod = $("#sel_yn").val();
			 window.location.href= ctx +"/billinfo/tDealBillInfo/linkedNotes?id="+relateVoucher +"&accountPeriod="+accountPeriod;
		}else{
			alert("没有相应的票据");
		}
	})
	
}

function commonOperation(){
	
	//日期改变或者是否过滤选中
	$("body").on("change","#sel_yn,.bill-status",function(){
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
	
	//错误票据选中
	$("#error_billinfo").on("click","li",function(){
		$(this).addClass("check").siblings().removeClass("check");
		$("#deal_billinfo").find(".check").removeClass("check");
	})
	
	//上传票据选中
	$("#deal_billinfo").on("click","li",function(){
		$(this).addClass("check").siblings().removeClass("check");
		$("#error_billinfo").find(".check").removeClass("check");
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
		$("#panImage").empty();
		var imgList = new Array(); 
		var type = $(this).parent().parent().parent().data("type");
		if(type == 0){//单个票据
			var id = $(this).parent().parent().parent().data("id");
			var imageUrl = $(this).parent().parent().parent().data("imageurl");
			var abstractMsg = $(this).parent().parent().parent().data("abstractmsg");
			var billinfoedit = {
					"content":abstractMsg,
					"src":image_path + imageUrl,
					"attrimgid":id
			};
			imgList.push(billinfoedit);
			editBillId = id;
			
			var billInfos = billinfosData.billInfos;
			for(var i in billInfos){
				if(id = billInfos[i].id){
					editBillBean = billInfos[i];
				}
			}
			
			billInfos = billinfosData.errorBillInfos;
			for(var i in billInfos){
				if(id = billInfos[i].id){
					editBillBean = billInfos[i];
				}
			}
			
		}else if(type == 1){//多个票据
			var id = $(this).parent().parent().parent().data("id");
			var idArr = id.split(",");
			if(idArr.length > 0){
				for(var i in  idArr){
					if(i == 0){
						editBillId = idArr[i] ;
					}
					for(var j in taxiMap){
						var taxi = taxiMap[j];
						if(idArr[i] = taxi.id){
							if( i == 0 ){
								editBillBean = taxi;
							}
							var billinfoedit = {
									"content":taxi.abstractMsg,
									"src":image_path +taxi.imageName,
									"attrimgid":taxi.id
							};
							imgList.push(billinfoedit);
						}
					}
				}
			}
		}
		
		$("#panImage").zyImage({
			imgList : imgList, // 数据列表
			mainImageWidth  : 600,
			mainImageHeight : 330,
			thumImageWidth  : 110,
			thumImageHeight : 110,
			changeCallback : function(index, image){  // 切换回调事件
				$(".sta_ipta:eq(0)").val(image.attrimgid);
				editBillId = image.attrimgid;
				for(var j in taxiMap){
					var taxi = taxiMap[j];
					if(editBillId = taxi.id){
						editBillBean = taxi;
					}
				}
			},
			deleteCallback : function(image){  // 删除回调事件
				console.info(image.attrimgid);
			}
		});
		$(".dialog").hide();
		$(".kindlyReminder").show();
		$(".mask").show();
	})
	
	//图片模式-操作-跨期
	$("body").on("click",".dialog-link",function(){
		var id = $(this).parent().parent().parent().data("id");
		var time = $(this).parent().parent().parent().data("signdate")+"";
		var str = time.split("-")[0]+"-"+time.split("-")[1];
		$(".instrument").find(".time-from").val(str);
		$("#instrument-id").val(id);
		$(".dialog").hide();
		$(".mask").show();
		$(".instrument").show();
	})
	
	//图片模式-编辑-票据作废
	$("body").on("click","#dialog-big-del",function(){
		$("#reminder-id").val(editBillId);
		$(".dialog").hide();
		$(".mask").show();
		$(".reminder").show();
	})
	
	//图片模式-编辑-跨期
	$("body").on("click","#dialog-big-period",function(){
		var id = editBillBean.id;
		var time = editBillBean.signDate;
		var str = time.split("-")[0]+"-"+time.split("-")[1];
		$(".instrument").find(".time-from").val(str);
		$("#instrument-id").val(id);
		$(".dialog").hide();
		$(".mask").show();
		$(".instrument").show();
	})
	
	//图片模式-编辑-新增凭证
	$("body").on("click","#dialog-big-add",function(){
		$("#to-add-voucher-form-billInfos").val(editBillId);
		$("#to-add-voucher-form-submit").submit();
	});
	
	//图片模式-操作-新增凭证
	$("body").on("click","#add-voucher",function(){
		var id = "";
		$(".ul-pj li").each(function(){
			if($(this).hasClass("check") && $(this).data("type") == 0){
				id = $(this).data("id");
			}
		});
		if(id.length < 1){
			alert("请选择票据")
			return false;
		}
		$("#to-add-voucher-form-billInfos").val(id);
		$("#to-add-voucher-form-submit").submit();
	});
}

//发票信息显示
function billinfoshow(){
	$.ajax({
	     type: 'POST',
	     url: ctx+'/billinfo/tDealBillInfo/queryDealBillInfo',
	     cache:false,  
	     dataType:'json',
	     data: {
	    		"uploadPeriod":$("#sel_yn").val(),
	    		"billStatus":$('input[name="bill-status"]:checked').val()
	     } ,
	     success: function(data){
	    	 billinfosData = data;
	    	 //问题票据
	    	 var str = '';
	    	 var result = data.errorBillInfos;
	    	 if(result.length > 0){
		    	 for(var i in result){
			    	//str += '<li class="img-show-'+result[i].id+'" data-type="0" data-hasSaveVoucer="'+result[i].hasSaveVoucer+'"  data-id="'+result[i].id+'" data-number="'+result[i].invoiceNumber+'"  data-signdate="'+result[i].signDate.substr(0,10)+'" data-stas="true" data-imageurl="'+result[i].imageName+'" data-abstractMsg="'+result[i].abstractMsg+'"  data-relateVoucher="'+result[i].relateVoucher+'"><img class="img-show-big" src="'+ image_path +result[i].imageName+'" width="226" height="156" alt=""><i class="billstatus billstatus'+result[i].billStatus+'"></i>';
			    	
		    		str += '<li class="img-show-'+result[i].id+'" data-type="0" data-hasSaveVoucer="'+result[i].hasSaveVoucer+'"  data-id="'+result[i].id+'" data-number="'+result[i].invoiceNumber+'"  data-signdate="'+result[i].signDate+'" data-stas="true" data-imageurl="'+result[i].imageName+'" data-abstractMsg="'+result[i].abstractMsg+'"  data-relateVoucher="'+result[i].relateVoucher+'"><img class="img-show-big" src="'+ image_path +result[i].imageName+'" width="226" height="156" alt=""><i class="billstatus billstatus'+result[i].billStatus+'"></i>';
			    	str += '<div class="text-left pad_t_b5"><p>'+result[i].abstractMsg+'</p><span class="left">价格合计：'+result[i].totalPrice+'</span><span class="right tit_p3">';
			    	if(result[i].billStatus != 3){
		    			str +=  '<a href="javascript:;" class="btn btn-c1 dialog-link2">编辑</a>';
		    		}
			    	if(result[i].billStatus != 4){
			    		str += '<a href="javascript:;" class="btn btn-c2 dialog-link3">作废</a>';
			    	}
			    	if(result[i].billStatus != 6 && result[i].billStatus != 7){
			    		str += '<a href="javascript:;" class="btn btn-c3 dialog-link">跨期</a>';
			    	}
			    	str +='</span><div class="clear"></div></div></li>';
			    }
		    	$("#error_billinfo").html(str);
		    	$("#error_billinfo_div").show();
	    	 }else{
	    		$("#error_billinfo_div").hide();
	    	 }
	    	 //处理票据
	    	str = '';
		    result = data.billInfos;
	    	 if(result.length > 0){
		    	for(var i in result){
		    		//str += '<li class="img-show-'+result[i].id+'" data-type="0" data-hasSaveVoucer="'+result[i].hasSaveVoucer+'"  data-id="'+result[i].id+'" data-number="'+result[i].invoiceNumber+'"  data-signdate="'+result[i].signDate.substr(0,10)+'" data-stas="true" data-imageurl="'+result[i].imageName+'" data-abstractMsg="'+result[i].abstractMsg+'" data-relateVoucher="'+result[i].relateVoucher+'"><img class="img-show-big" src="'+ image_path +result[i].imageName+'" width="226" height="156" alt=""><i class="billstatus billstatus'+result[i].billStatus+'"></i>';
		    		str += '<li class="img-show-'+result[i].id+'" data-type="0" data-hasSaveVoucer="'+result[i].hasSaveVoucer+'"  data-id="'+result[i].id+'" data-number="'+result[i].invoiceNumber+'"  data-signdate="'+result[i].signDate+'" data-stas="true" data-imageurl="'+result[i].imageName+'" data-abstractMsg="'+result[i].abstractMsg+'" data-relateVoucher="'+result[i].relateVoucher+'"><img class="img-show-big" src="'+ image_path +result[i].imageName+'" width="226" height="156" alt=""><i class="billstatus billstatus'+result[i].billStatus+'"></i>';
		    		str += '<div class="text-left pad_t_b5"><p>'+result[i].abstractMsg+'</p><span class="left">价格合计：'+result[i].totalPrice+'</span><span class="right tit_p3">';
		    		if(result[i].billStatus != 3){
		    			str +=  '<a href="javascript:;" class="btn btn-c1 dialog-link2">编辑</a>';
		    		}
		    		if(result[i].billStatus != 4){
			    		str += '<a href="javascript:;" class="btn btn-c2 dialog-link3">作废</a>';
			    	}
			    	if(result[i].billStatus != 6 && result[i].billStatus != 7){
			    		str += '<a href="javascript:;" class="btn btn-c3 dialog-link">跨期</a>';
			    	}
			    	str +='</span><div class="clear"></div></div></li>';
			    }
		    	$("#deal_billinfo").html(str);
		    	 $("#deal_billinfo_div").show();
	    	 }else{
	    		 $("#deal_billinfo_div").hide();
	    	 }
	    	 
	    	 //出租车
	    	str = '';
	    	var billinfo = data.taxiBillInfo;
	    	if(billinfo != undefined){
	    		str += '<li class="img-show-taxi" data-type="1" data-hasSaveVoucer="false"  data-id="'+billinfo.id+'" data-number="'+billinfo.invoiceNumber+'"  data-signdate="" data-stas="true" data-imageurl="'+result[i].imageName+'"><img class="img-show-big" src="'+ image_path +result[i].imageName+'" width="226" height="156" alt=""><i class="billstatus billstatus'+billinfo.billStatus+'"></i>';
	    		str += '<div class="text-left pad_t_b5"><p>出租类票据</p><span class="left">价格合计：'+billinfo.amount+'</span><span class="right tit_p3"><a href="javascript:;" class="btn btn-c1 dialog-link2">编辑</a>';
		    	str +='</span><div class="clear"></div></div></li>';
		    	$("#deal_billinfo").append(str);
	    		$("#deal_billinfo_div").show();
	    		taxiMap = data.taxiMap;
	    	}
	    	
	    	//飞机
	    	str = '';
	    	var billinfo = data.planeBillInfo;
	    	if(billinfo != undefined){
	    		str += '<li class="img-show-taxi" data-type="1" data-hasSaveVoucer="false"  data-id="'+billinfo.id+'" data-number="'+billinfo.invoiceNumber+'"  data-signdate="" data-stas="true" data-imageurl="'+result[i].imageName+'"><img class="img-show-big" src="'+ image_path +result[i].imageName+'" width="226" height="156" alt=""><i class="billstatus billstatus'+billinfo.billStatus+'"></i>';
	    		str += '<div class="text-left pad_t_b5"><p>飞机类票据</p><span class="left">价格合计：'+billinfo.amount+'</span><span class="right tit_p3"><a href="javascript:;" class="btn btn-c1 dialog-link2">编辑</a>';
		    	str +='</span><div class="clear"></div></div></li>';
		    	$("#deal_billinfo").append(str);
	    		$("#deal_billinfo_div").show();
	    		var planeMap = data.planeMap;
	    		for(var i in planeMap){
	    			taxiMap.push(planeMap[i]);
	    		}
	    	}
	    	//火车
	    	str = '';
	    	var billinfo = data.trainBillInfo;
	    	if(billinfo != undefined){
	    		str += '<li class="img-show-taxi" data-type="1" data-hasSaveVoucer="false"  data-id="'+billinfo.id+'" data-number="'+billinfo.invoiceNumber+'"  data-signdate="" data-stas="true" data-imageurl="'+result[i].imageName+'"><img class="img-show-big" src="'+ image_path +result[i].imageName+'" width="226" height="156" alt=""><i class="billstatus billstatus'+billinfo.billStatus+'"></i>';
	    		str += '<div class="text-left pad_t_b5"><p>火车类票据</p><span class="left">价格合计：'+billinfo.amount+'</span><span class="right tit_p3"><a href="javascript:;" class="btn btn-c1 dialog-link2">编辑</a>';
		    	str +='</span><div class="clear"></div></div></li>';
		    	$("#deal_billinfo").append(str);
	    		$("#deal_billinfo_div").show();
	    		var trainMap = data.trainMap;
	    		for(var i in trainMap){
	    			taxiMap.push(trainMap[i]);
	    		}
	    	}
	    	
	    	
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
	    	$(".dialog").hide();
	    	$(".bill-info-close-dialog").click();
	     }
	});
}
