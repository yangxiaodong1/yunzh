
function fixTableHeader(baseHeight, containerheight) {
		$(".table_container").css({
			"height":containerheight
		})
		if($("#myTable02 tbody").height() >= baseHeight) {
	    	$('#myTable02').fixedHeaderTable({ footer: true, altClass: 'odd' });
	    	$(".fht-table-wrapper .fht-tbody").css({
	    		"height": baseHeight,
	    		"overflow-y":"auto",
	    		"overflow-x":"inherit"
	    	});
	    }
	}
function fixTableHead(height, box, siderbar) {
	$(".table_containerbox").css({
		"height":$(window).height() - box
	})
	$(".sidebar_user").css({
		"height":$(window).height() - siderbar
	})
	if(height >= $(window).height() - 215) {
	   	var tableWidth = $(".table_containerbox").width();
	    var scale = (1 - 17/tableWidth)*100 + "%";
	    $(".table_first").css({"width":scale});
	}else {
	 	$(".table_first").css({"width":"100%"});
	 	if(height == 0) {
	 		$(".table_containerbox").css("border","none");
	 	}
	}
}