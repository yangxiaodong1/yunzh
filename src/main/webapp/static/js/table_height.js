$(function() {
	if($("#myTable02 tbody").height() >= $(window).height() - 177) {
    	$('#myTable02').fixedHeaderTable({ footer: true, altClass: 'odd' });
    	$(".fht-tbody").css({
    		"height": $(window).height() - 178
    	});
    	$(".fht-table").css({
    		"border-collapse": "inherit"
    	});
    }
	if($(".tb_scroll").height() >= window.parent.$("iframe").height() - 140) {
		$(".thead_container").css("padding-right","17px");
	}
	$(".tb .tb_scroll").css({
		"height": window.parent.$("iframe").height() - 140
	})
	$(".tb .tb_scroll").css({
		"overflow":"auto"
	});
	var mar = ($(window).height() - $(".print_dialog").height())/2;
	$(".print_dialog").css("margin-top",mar);
})