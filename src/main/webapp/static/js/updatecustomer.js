
$(function(){
	//是否删除
	$( "#dialog_del2" ).dialog({
		autoOpen: false,
		width: 470,
		modal: true
	});	
	
	//收费
	$( "#dialog_mon" ).dialog({
		autoOpen: false,
		width: 840,
		modal: true
	});	
	//修改客户
	$( "#dialog_add" ).dialog({
		autoOpen: false,
		width: 840,
		modal: true
	});
	
	
})
  
//点击客户时候触发
	$(".dialog_add").on("click",function(){
		  var id=$(this).siblings("input:last").val();
		  alert(id);
		
		  insertPut(id);
		$( "#dialog_add" ).dialog( "open" );
		event.preventDefault();
		
	});
    //给输入框中赋值
	function insertPut(id){
		$.ajax({  
			type:"POST",
				url:ctx+"/customer/tCustomer/forms", 
			  data: "id="+id,
		       success:function(json){  
		    	  // alert(5455555);
		    	   $json = json;
		    	   $('#inputForm').formEdit($json);
		       },
		       error:function(){showd("dialog_error"); }
		      
		 });
		 };  
		
function showd(dialog){
		var arg = arguments;
		var sure = $("#"+dialog+"");
		if(!arg.callee.open) {
			arg.callee.open = true;
			sure.css("display","block");
			sure.animate({
				top:"10px",
			},500,function() {
				setTimeout(function() {
					sure.css({
						top:0,
						display:"none"
					});
					arg.callee.open = false;
				},1000);
			});
		}
}
	$( ".dialog_mon" ).click(function( event ) {
		$("#inputForminsert #customerId").val($(this).siblings("input:last").val());
		$("#searchFormtServiceCharge #customerId1").val($(this).siblings("input:last").val());
		$("#dialog_mon #customerName1").val($(this).siblings("input:first").val());
		$("#searchFormtServiceCharge").ajaxSubmit({
		        type: 'post',
		        url: ctx+"/inspection/workstatistics/tServiceCharge/listpage" ,
		        dataType : 'json',
		        data: $("#searchFormtServiceCharge").serialize(),
		        success: function(data){
		           $("#tServiceChargeList").setTemplateElement("template").processTemplate(data);
		          $("#dialog_mon .pagination").html(data.html);
		          $("#dialog_mon #pageNo").val(data.pageNo);
		          $("#dialog_mon #pageSize").val(data.pageSize);
		        },
		        error: function(XmlHttpRequest, textStatus, errorThrown){
		        	showd("dialog_error");
		        }                
			 });
		$( "#dialog_mon" ).dialog( "open" );
		event.preventDefault();
		
	});

	$( ".delecttServiceCharge" ).click(function( event ) {
		var id= $("#dialog_del2 #delectids").val();
		$.ajax({  
			type:"POST",
				url:ctx+"/inspection/workstatistics/tServiceCharge/delects",  
				dataType : 'json',
			   data: "id="+id,
		       success:function(data){  
		    	   
		    	   
		    	   showd("dialog_delect");
		    	   $("#tServiceChargeList").setTemplateElement("template").processTemplate(data);
			          $("#dialog_mon .pagination").html(data.html);
			          $("#dialog_mon #pageNo").val(data.pageNo);
			          $("#dialog_mon #pageSize").val(data.pageSize);
		       },
		       error:function(){showd("dialog_error");}
		 });  
		$( "#dialog_del2" ).dialog( "close" );
		event.preventDefault();
	});
	$( ".dialog_close6" ).click(function( event ) {
		$( "#dialog_del2" ).dialog( "close" );
		event.preventDefault();
	});



			
function savetServiceCharge(){
	
	 $("#inputForminsert").ajaxSubmit({
	        type: 'post',
	        url: ctx+"/inspection/workstatistics/tServiceCharge/saves" ,
	        dataType : 'json',
	        data: $("#inputForminsert").serialize(),
	        success: function(data){
	    		showd("dialog_sure");
	        	
	        	 $("#tServiceChargeList").setTemplateElement("template").processTemplate(data);
	        	
		          $("#dialog_mon .pagination").html(data.html);
		          $("#dialog_mon #pageNo").val(data.pageNo);
		          $("#dialog_mon #pageSize").val(data.pageSize);
	        },
	        error: function(XmlHttpRequest, textStatus, errorThrown){
	        	showd("dialog_error");
	        }   
	        
		 });
	
	 return false;
}


	 function ShowDetails(id){
		 
			$.ajax({  
				type:"POST",
					url:ctx+"/inspection/workstatistics/tServiceCharge/forms",  
					dataType : 'json',
				   data: "id="+id,
			       success:function(json){  
			    	   
			    	   $json = json;
			    	   $('#inputForminsert').formEdit($json);
			       },
			       error:function(){showd("dialog_error");}
			 });  
		};
		 function delDetails(id){
			 $("#dialog_del2 #delectids").val(id);
			 $( "#dialog_del2" ).dialog( "open" );
				event.preventDefault();
			};
 function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		 $("#searchFormtServiceCharge").ajaxSubmit({
		        type: 'post',
		        url: ctx+"/inspection/workstatistics/tServiceCharge/listpage" ,
		        dataType : 'json',
		        data: $("#searchForm").serialize(),
		        success: function(data){
		        	 $("#tServiceChargeList").setTemplateElement("template").processTemplate(data);
			          $("#dialog_mon .pagination").html(data.html);
			          $("#dialog_mon #pageNo").val(data.pageNo);
			          $("#dialog_mon #pageSize").val(data.pageSize);
		        },
		        error: function(XmlHttpRequest, textStatus, errorThrown){
		            alert( "error");
		        }                
			 });
		return false;
	}

 $.fn.formEdit = function(data){
     return this.each(function(){
         var input, name;
         if(data == null){this.reset(); return; }
         for(var i = 0; i < this.length; i++){  
             input = this.elements[i];
             name = (input.type == "checkbox")? input.name.replace(/(.+)\[\]$/, "$1") : input.name;
             if(data[name] == undefined) continue;
             switch(input.type){
                 case "checkbox":
                     if(data[name] == ""){
                         input.checked = false;
                     }else{

                         if(data[name].indexOf(input.value) > -1){
                             input.checked = true;
                         }else{
                             input.checked = false;
                         }
                     }
                 break;
                 case "radio":
                     if(data[name] == ""){
                         input.checked = false;
                     }else if(input.value == data[name]){
                         input.checked = true;
                     }
                 break;
                 case "button": break;
                 default: input.value = data[name];
             }
         }
     });
 };