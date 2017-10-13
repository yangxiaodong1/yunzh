//---------------修改企业接受状态----------------
function updateSate(userbeid){
		 $.ajax({
	         type:"POST",
	         url:"/yunzh/a/cususer/tCusUser/updateAcceptState",
	         data:"userbeid="+userbeid,
	         success:function(data){
	        	 if(data=="1"){
	        		 $("#span"+userbeid).replaceWith("<span class='btn btn-default right btn_w4 btn_bg_3'>已接收</span>");
	        		 
	        	 }    
	         }
	   });
}
$(function(){
	$('.accept').on("click",function(){
		var id=$(this).attr("id").substring(4,($(this).attr("id").length));
		updateSate(id.toString());
	});
})