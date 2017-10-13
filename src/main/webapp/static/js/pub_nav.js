$(function(){
	/*$(".leftnav li").hover(function(){
		var index=$(".leftnav li").index(this)
		$(this).addClass("curr").siblings().removeClass("curr")	
		$(".side-subcon .submenu").eq(index).show().siblings().hide();
		

	})

	$(".main-con .body-con").hover(function(){
		$(".side-subcon .submenu").hide();
	})

	
	
	$('#tableframe').height(function(){
		return $(window).height();
	})*///以上是动态选项卡的效果
	$(".leftnav li").hover(function(){
		var index=$(".leftnav li").index(this)
		$(this).addClass("curr").siblings().removeClass("curr")	
		$(".side-subcon .submenu").eq(index).show().siblings().hide();
	})

	$(".main-con .body-con").hover(function(){
		$(".side-subcon .submenu").hide();
	})

	$("[famesrc]").click(function(event){
		var famesrc=$(this).attr("famesrc");
		event.preventDefault();
		$("#pane-subcon iframe").attr("src",famesrc);
		$(".side-subcon .submenu").hide();
	})
	
	$('#tableframe').height(function(){
		return $(window).height();
	})
	
})