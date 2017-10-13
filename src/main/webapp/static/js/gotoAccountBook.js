$(function() {
	  var cache = {};
    $("#keyword_set").autocomplete(
    {
        source: function(request, response) {
            var term = request.term;
            if (term in cache) {
                data = cache[term];
                response($.map(data, function(item) {
                    return {value: item.id,label:item.customerName  }
                }));
            } else {
                $.ajax({
                    url: ctx+'/customer/tCustomer/getName',
                    dataType: "json",
                    data: {
                        top: 10,
                        key: term
                    },
                    success: function(data) {
                        if (data.length) {
                            cache[term] = data;
                            response($.map(data, function(item) {
                                return {value: item.id,label:item.customerName }
                            }));
                        }
                    }
                });
            }
        },
        select: function(event, ui) {
        	$( "#sessionCustomerId" ).val( ui.item.value );
        	event.preventDefault();
        },
        focus: function( event, ui ) {
        	$( "#keyword_set" ).val( ui.item.label );
        	
            event.preventDefault();
          },
        minLength: 1,
        autoFocus: false,
        delay: 500,
        minChars :0,
        autoFill:true
    });
    
    
    
     //设置提交事件，调用父类中的窗口中的方法
 	   $("#formsetSession").submit(function(e){
 		  var sessionCustomerId=$("#sessionCustomerId").val();
 		   if(sessionCustomerId==""||typeof(sessionCustomerId)=="undefined"){
 			   
 		   }else{
 			  e.preventDefault();//阻止表单提交，直接给父类窗口的方法提交
 	 		   $(window.parent.enterCustomer(sessionCustomerId));
 		   }
 	   });
    
}); 
      
/***20160131***/
function messagePop(str){
        $(".message-pop").fadeIn();
        $(".message-pop").children("span").html(str);
        setTimeout(function(){
            $(".message-pop").fadeOut();
        },2000)
}
var inputEl = $('#keyword_set'),
defVal = inputEl.val();
inputEl.css({"color":"#E3E3E3"});
inputEl.bind({
    focus: function() {
     var _this = $(this);
     _this.css({"color":"#000"});
     if (_this.val() == defVal) {
      _this.val('');
     }
    },
    blur: function() {
     var _this = $(this);    
     if (_this.val() == '') {
      _this.val(defVal);
      _this.css({"color":"#E3E3E3"});
     }
    }
});	