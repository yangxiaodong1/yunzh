<!doctype html>
<html lang="en">
<head>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>个人提醒-工作台-芸豆会计</title>
<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery.ztree.core-3.5.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/gotoAccountBook.js"></script>
<link href="${pageContext.request.contextPath}/static/jquery/jquery-ui.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/accountAndbalance/css/defaultTheme.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/master_2.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/static/css/dialog.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery.ui.datepicker-zh-CN.js.js" charset="gb2312"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery-ui-timepicker-zh-CN.js" charset="gb2312"></script>
<link href="${pageContext.request.contextPath}/static/bootstrap/css/page.css" type="text/css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/static/accountAndbalance/js/jquery.fixedheadertable.js"></script>

<script>
$(function(){
	//全选/全不选
	$("#checkAll").on("click",function(){
		if(this.checked){
			$('[name=items_checkbox]:checkbox').prop("checked",true);
		}else{
			$('[name=items_checkbox]:checkbox').prop("checked",false);
		} 
	});	

}); 


</script>
<style>
/***20160131**/
textarea.textarea_n3{width:295px;}
#formsetSession{float:left;}
body{overflow:hidden;}
.user_pane_r2{position:relative;}
#newclients{position:absolute;right:0;top:20px;}
.fixed_table{width:960px;margin:0 auto;}
.pane_gzt{padding:0;width:960px;margin:0 auto;}
.pane_main5 {padding:0;width:1065px;margin:0 auto;}
table.table_line1>thead>tr>th{ border-bottom: none;  padding: 4px 0px;  text-align:center;}
table.table_line1>tbody>tr>td{padding: 4px 0px;}
.table_line1 {border-collapse: collapse;  border: 1px #ddd solid;}
.form-group .ui-datepicker-trigger{border:1px #999999 solid;border-left:none;padding:3px;margin:0;}
</style>

</head>
<body>


<div class="main-row">
	<div class="main_index3">
		<div class="pane_main5">
			<div class="t_pane_a1 radius_5">
				<div class="user_pane_r2">
					<div class="hr20"></div>
					<ul class="nav nav-tabs" role="tablist">
						<li><a href="${ctx}/workterrace/chargeToAccount/">记账</a></li>
						<li><a href="${ctx}/workterrace/reportTax/">报税</a></li>
						<li><a href="${ctx}/workterrace/wages/">工资</a></li>
						<li><a href="${ctx}/workterrace/daily/">日常</a></li>
						<li class="active"><a href="${ctx}/workterrace/tPersonalReminder/">个人提醒</a></li>
						</ul>
					<div class="tab-content main5_con radius_5">
						<div class="tab-pane active" id="tab_f5">
							<div class="hr20"></div>
							
							<div class="pane_gzt">
								<div class="search_form4">
									<span class='span_year left'>
									<form:form id="searchForm" modelAttribute="tPersonalReminder" action="${ctx}/workterrace/tPersonalReminder/list" method="post">
	
		<!-- <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/> -->
		<form:hidden path="byYear"/>
		 <input type="button" class="bg_p6 btn_sea1 left" onclick="up()"  value="左"/>
					<form:input class="form-control-static ipt_w5 left"  readonly="true" path="byYearName" htmlEscape="false"/>
					<input type="button" class="bg_p6 btn_sea2 left mar-rig3" onclick="down()" value="右"/>
					
	                                      </form:form>
										</span>
										<form id="formsetSession" name="testForm" action="${ctx}/customer/tCustomer/setSession" method="post">
										
		
										<input id="keyword_set" type="text" class="form-control ipt_w5 ipt_txt_s1 mar-rig3 left" value="请输入客户名称进入账簿">
										<input id="sessionCustomerId" name="sessionCustomerId" type="hidden" />
										<button id="enter_customer" type="submit" class="btn btn-default btn_w_a btn_bg_2 radius0 left en_customer">进入账簿</button>
										
										<div class="clearfix"></div>
									</form>
									<span class="right">
										<button onclick="insertPersonlReminder()" class="btn btn-default btn_w_a btn_bg_2 radius0 left mar-rig10">新建</button>
										<button id="shanchu" class="dialog_del2 btn btn-default btn_w_a btn_bg_4 radius0 left">删除</button>
									</span>
									<div class="clearfix"></div>
								</div>
								<div class="hr15"></div>
<div class='fixed_table'>
<c:choose>
<c:when test="${empty findListReminderService}">
<div class="no_data">
		<img src="${pageContext.request.contextPath}/static/imgs/nodata.jpg">
	</div>
</c:when>
<c:otherwise>
<table class="table table_line1" width="100%" border="1"  id='myTable01'>
		<thead>
			<tr>
				<th bgcolor='#deedfc' width='50' class="t_top1" align='center'><lable><input id="checkAll" type="checkbox"></lable></th>
				<th bgcolor='#deedfc' class="t_top1">事项</th>
				<th bgcolor='#deedfc' width='180' class="t_top1" align='center'>提醒时间</th>
				<th bgcolor='#deedfc' width='100' class="t_top1" align='center'>重要程度</th>
				<th bgcolor='#deedfc' width='100' class="t_top1" align='center'>状态</th>
				<th bgcolor='#deedfc' width='100' class="t_top1" align='center'>操作
				</th>
			</tr>
		</thead>
		
	<tbody>
	<c:forEach items="${findListReminderService}" var="tPersonalReminder">
	<tr class='bg_blue2'>
		<td align='center'><input name="items_checkbox" type="checkbox" value="${tPersonalReminder.id}"></td>
		<td>${tPersonalReminder.matters}</td>
		<td align='center'><fmt:formatDate value="${tPersonalReminder.warnTime}" pattern="yyyy-MM-dd HH:mm"/></td>
		<td align='center'>
			<c:choose>
		 						 <c:when test="${tPersonalReminder.degreeImportance=='0'}">
							      低
							    </c:when>
							     <c:when test="${tPersonalReminder.degreeImportance=='1'}">
							      中
							    </c:when>
							     <c:when test="${tPersonalReminder.degreeImportance=='2'}">
							     高
							    </c:when>
								 <c:otherwise>
							    </c:otherwise>
							</c:choose>
		</td>
		<td align='center' class="state">
		
		<c:choose>
   							 <c:when test="${tPersonalReminder.state=='1'}">
							       已完成
							    </c:when>
							     <c:when test="${tPersonalReminder.state=='0'}">
							       未完成
							    </c:when>
								 <c:otherwise>
							    </c:otherwise>
							</c:choose>
		</td>
		<td align="center">	
	
       <c:if test="${tPersonalReminder.state=='1'}">
               <a href="javascript:void(0);"  class="btn btn-default btn_n4 bg_p6 btn_i11 pad_10 mar-lft5 " title="已完成" ></a>
               
       </c:if>
       <c:if test="${tPersonalReminder.state=='0'}">
            	<a href="javascript:void(0);" onclick="updatastate()" class="show_01 btn btn-default btn_n4 bg_p6 btn_i10 pad_10"  ></a>
			<a href="javascript:void(0);" class="btn btn-default btn_n4 bg_p6 btn_i2 pad_10 dialog_edit22 mar-top1 "  ></a>
			<a href="javascript:void(0);" class="show_01 btn btn-default btn_n4 bg_p6 btn_i4 dialog_del3 pad_10"  ></a>
		
       </c:if>

		
		</td>
	</tr>
	</c:forEach>
</tbody>
</table>
</c:otherwise>
</c:choose>
	
	</div>
<!-- <div class="pagination">${page}</div> -->
							</div>

							<div class="clearfix"></div>
						</div>
					</div>

					<div class="hr10"></div>
				</div>
			</div>

			<div class="hr20"></div>
			</div>
	</div>
</div>

<!--新增客户-->
<div id="dialog_edit22" class="displayCss" title="编辑个人提醒">
	<div style='padding:15px;'>
		<div class="client_form1">
		
		<form:form id="inserttPersonalReminder" modelAttribute="tPersonalReminder" action="${ctx}/workterrace/tPersonalReminder/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-3 control-label">事项</label>
					<div class="col-sm-9">
					 	<form:textarea rows="3" path="matters" htmlEscape="false" maxlength="255" class="form-control textarea_n3"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">提醒时间</label>
					<div class="col-sm-9">
					<input id="warnTime" name="warnTime" readonly="readonly" htmlEscape="false" maxlength="64" class="form-control ipt_w4" style="float:left;width:170px;" value="<fmt:formatDate value="${tPersonalReminder.warnTime}" pattern="yyyy-MM-dd HH:mm" />"/>
				
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label">重要程度</label>
					<div class="col-sm-9">
					 	<label class="radio-inline">
						  <form:radiobutton  path="degreeImportance"  value="2"/>高
						</label>
						<label class="radio-inline">
						  <form:radiobutton  path="degreeImportance"  value="1"/>中
						</label>
						<label class="radio-inline">
						  <form:radiobutton  path="degreeImportance"  value="0" checked="checked"/>低
						</label>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-3 control-label">重要事件</label>
					<div class="col-sm-9">
					
						 <form:select class="form-control ipt_w4" path="degreeEvent">   
                   <form:option value="0">不重复</form:option>   
                   <form:option value="1">重复</form:option>   
               </form:select>   
						
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-offset-3 col-sm-9">
						<span class="right">
						<button type="submit" class="btn btn-default btn_w_a btn_bg_2 mar-rig10">确定</button>
						<!-- <button class="btn dialog_close22 btn-default btn_w_a btn_bg_4">取消</button> -->
						</span>
						<div class="right"></div>
					</div>
				</div>


			</form:form>
		</div>
	</div>
</div>


<script>
var h_1=$(window).height()-168;
$('.fixed_table').height(h_1);
var winh1=$("#myTable01").height();
if(winh1>h_1){
	$('#myTable01').fixedHeaderTable({ 
		altClass: 'odd',
		autoShow: false
	});
}
$('.fixed_table .fht-default').show();
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
/***20160131***/

function page(n,s){
	$("#pageNo").val(n);
	$("#pageSize").val(s);
	$("#searchForm").submit();
	return false;
}


function insertPersonlReminder(){
	$("#inserttPersonalReminder")[0].reset();
	$( "#dialog_edit22" ).dialog( "open" );
	event.preventDefault();
	return false;
}
function updatastate(event){
	
	var e = event || window.event;
	var id=$(e.srcElement).parent().parent().find("input:first").val();
	
	$.ajax({  
		type:"POST",
			url:"${ctx}/workterrace/tPersonalReminder/updatastate" ,
		   data: "id="+id,
	       success:function(json){ 
	    	   if(json>0){
	    		   
	    		   $(e.srcElement).parent().prev().text("已完成");
	    		   $(e.srcElement).parent().html(" <a href='javascript:void(0);'  class='btn btn-default btn_n4 bg_p6 btn_i11 pad_10 mar-lft5' href='#'></a>");
		    		  
	    	   }
	       },
	       error:function(){
}
	 }); 
}

$(function(){
	$(".table_jz2 tr").hover(function(){
		$(this).addClass("thover");
		$(this).prev("tr").addClass("top_lines");
	},function(){
		$(this).removeClass("thover");
		$(this).prev("tr").removeClass("top_lines");
	})

	$(".table_jz2 tbody tr:eq(0)").hover(function(){
		$(this).addClass("thover");
		$(".table_jz2 thead tr").addClass("top_lines");
	},function(){
		$(this).removeClass("thover");
		$(".table_jz2 thead tr").removeClass("top_lines");
	})
	//年份
	$('.span_year .btn_sea1').on('click',function(){
		var oldValue=parseInt($('.yearval').val());
		oldValue--;
		
		$('.yearval').val(oldValue);		
		
	})
	$('.span_year .btn_sea2').on('click',function(){
		var newValue=parseInt($('.yearval').val());
		newValue++;
		
		$('.yearval').val(newValue);
	})
	
	 
		 
		 
		//编辑dialog_edit22
			$( "#dialog_edit22" ).dialog({
				autoOpen: false,
				width: 470,
				modal: true
			});	
			$( ".dialog_edit22" ).click(function( event ) {
				var id= $(this).parent().parent().find("input:first").val();
				
				$.ajax({  
					type:"POST",
						url:"${ctx}/workterrace/tPersonalReminder/form" ,
						dataType : 'json',
					   data: "id="+id,
				       success:function(json){ 
				    	   
				    	   $json = json;
				    	   $('#inserttPersonalReminder').formEdit($json);

				    	   $( "#dialog_edit22" ).dialog( "open" );
							event.preventDefault();
				       },
				       error:function(){
}
				 }); 
				
			});
			
			/*日期*/
			$( "#warnTime" ).datetimepicker({
				timeFormat: "hh:mm",
				dateFormat: "yy-mm-dd",
				showOn: "button",
				buttonImage: "${pageContext.request.contextPath}/static/imgs/04.jpg",
				buttonImageOnly: true,
				buttonText: "选择日期"
			});
			$( "#dialog_selectUser" ).dialog({
				autoOpen: false,
				width: 470,
				modal: true
			});
			$( "#dialog_del2" ).dialog({
				autoOpen: false,
				width: 470,
				modal: true
			});	
			$( ".dialog_close9" ).click(function( event ) {
				$( "#dialog_selectUser" ).dialog( "close" );
				event.preventDefault();
			});
			$( ".dialog_close22" ).click(function( event ) {
				$( "#dialog_edit22" ).dialog( "close" );
				event.preventDefault();
			});
			
			$( ".dialog_del2" ).click(function( event ) {
				 var checkedNum = $("table input:checked").length;
				    if(checkedNum < 1) {
				    	$( "#dialog_selectUser" ).dialog( "open" );
						event.preventDefault();
				    return;
				    }
				   
				$( "#dialog_del2" ).dialog( "open" );
				event.preventDefault();
			});
			
			$( ".dialog_del3" ).click(function( event ) {
				$(this).parent().parent().find("input:first").prop("checked",true);
				$( "#dialog_del2" ).dialog( "open" );
				
				event.preventDefault();
			});
			
			$( ".delectUser" ).click(function( event ) {
				var ids=new Array();
				$("table input:checked").each(function(){
					ids.push($(this).val());
				});
					$.ajax({
						type:"post",
						url:" ${ctx}/workterrace/tPersonalReminder/deletes?ids="+ids.toString(),
						success:function(result){
							$("table input:checked").each(function() {
						        $(this).parent().parent().remove();
						        });
							$( "#dialog_del2" ).dialog( "close" );
							event.preventDefault();
							
						}
					});
				
			});
			$( ".dialog_close6" ).click(function( event ) {
				$( "#dialog_del2" ).dialog( "close" );
				event.preventDefault();
			});

});
function up(){
	var aa=$("#byYear").val();
	var bb=Number(aa)-1;
	$("#byYear").val(bb);
	$("#searchForm").submit();
	return false;
};
function down(){
	var aa=$("#byYear").val();
	var bb=Number(aa)+1;
	$("#byYear").val(bb);
	$("#searchForm").submit();
	return false;
};
var ctx = "${ctx}";
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
</script>
<div id="dialog_selectUser" class="displayCss" title="系统提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center">请至少选择一项要操作的提醒！</div>
		<div class="hr40"></div>
		<form>
		<div class="text-center">
			<button class="btn dialog_close9 btn-default btn_w_a btn_bg_4">确认</button>
		</div>
		</form>
	</div>
</div>

<!--删除职员-->
<div id="dialog_del2" class="displayCss" title="系统提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center">您确定要删除选中的所有个人提醒吗？</div>
		<div class="hr40"></div>
		<form>
		<div class="text-center">
			<input type="button" class="btn delectUser btn-default btn_w_a btn_bg_2 mar-rig10" value="确认" />
			<input type="button"  class="btn dialog_close6 btn_w_a btn_bg_4" value="取消" />
			
		</div>
		
		</form>
	</div>
</div>
</body>
</html>