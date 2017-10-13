$(".act_buttons .btn_opt1").on('click',function(){
	$(this).hide();
	$(".act_buttons .btn_opt2").show();
	$("#pwd").attr("style","border:1px #999999 solid")

})
var dateYear = new Date();
var myers = $("#year_span");
myers.empty();
var nowyear=dateYear.getFullYear();
var oldyear=nowyear-9;
var newyears=nowyear+1;
for(var i=oldyear;i<newyears;i++) {
	var option = $("<option>").text(i).val(i)
	myers.append(option);
}
$("[name=initperiod]").val(function(){
	var years=$('#year_span').val();
	var months=$('#month_span').val();
	return years+months;
});
$('#year_span').change(function(){
	var years=$(this).val();
	var months=$('#month_span').val()
	$("[name=initperiod]").val(function(){
		console.info(years+months);
		return years+months;
	});
})
$('#month_span').change(function(){
	var years=$('#year_span').val();
	var months=$(this).val();	
	$("[name=initperiod]").val(function(){
		console.info(years+months);
		return years+months;
	});
})

var h_1=$(window).height()-245;
$('.fixed_table').height(h_1);
var winh1=$("#myTable01").height();
if(winh1>h_1){
	$('#myTable01').fixedHeaderTable({ 
		autoShow: false
	});
	$('.fixed_table').height(h_1+1);
}
$('.fixed_table .fht-default').show();
$('.fixed_table tbody tr:last').hover(function(){
	$(this).attr("style","border-bottom:1px #3d8ae2 solid");
},function(){
	$(this).attr("style","border-bottom:1px #dddddd solid");
})