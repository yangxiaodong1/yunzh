var messagePop = function(str){
	$(".message-pop").fadeIn();
	$(".message-pop").children("span").html(str);
	setTimeout(function(){
		$(".message-pop").fadeOut();
	},2000)
}