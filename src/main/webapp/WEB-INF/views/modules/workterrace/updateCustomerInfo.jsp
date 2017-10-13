
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<div class="displayCss" id="dialog_add" title="客户信息">
	<div class="dialog_client">
		<div class="t_pane_a1 radius_5">
			<ul class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active"><a href="#tab_g1" aria-controls="tab_g1" role="tab" data-toggle="tab">基本资料</a></li>
				<li role="presentation"><a href="#tab_g2" aria-controls="tab_g2" role="tab" data-toggle="tab">联系方式</a></li>
				<li role="presentation"><a href="#tab_g3" aria-controls="tab_g3" role="tab" data-toggle="tab">税务资料</a></li>
				<li role="presentation"><a href="#tab_g4" aria-controls="tab_g4" role="tab" data-toggle="tab" class="fj">附件</a></li>
				<li role="presentation"><a href="#tab_g5" aria-controls="tab_g5" role="tab" data-toggle="tab" class="khcz">客户查账</a></li>
			</ul>
			<form:form id="inputForm" modelAttribute="tCustomer" method="post" class="form-horizontal">
			<form:hidden id="id" path="id" class="yc"/>
			
			<div class="tab-content border_n3">
				<div role="tabpanel" class="tab-pane active" id="tab_g1">
					<!--基本资料-->
					<div class="client_inner2">
						<div class="client_form1">
						<input type="hidden" id="array" name="array">
								<div class="form-group">
									<label class="col-sm-2 control-label">公司名称</label>
									
									<div class="col-sm-4">
									<form:input id="customer" path="customerName" htmlEscape="false" maxlength="100" class="form-control ipt_w4"/>
									<span class="help-inline"><font color="red">*</font> </span>
									</div>

									<label class="col-sm-2 control-label">营业执照号</label>
									<div class="col-sm-4">
										<form:input path="businessLicenseNumber" htmlEscape="false" maxlength="20" class="form-control ipt_w4"/>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label">启用期间</label>
									<div class="col-sm-4" id="datediv">
										<input type='hidden' name='initperiod'/>
									    <input type='hidden' value='' id="currentPeriod"/>
										<input type='hidden' value='' id="initPeriod"/>
										<select class='form-control ipt_wauto' style='margin-right:8px;' id='year_span'>
										</select>
										<select class='form-control ipt_wauto' id='month_span'>
										</select>
									<span class="help-inline"><font color="red">*</font> </span>
									</div>

									<label class="col-sm-2 control-label">组织机构代码号</label>
									<div class="col-sm-4">
										<form:input path="organizationCode" htmlEscape="false" maxlength="20" class="form-control ipt_w4"/>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label">备注</label>
									<div class="col-sm-10">
										<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="form-control textarea_n3"/>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label">会计制度</label>
									<div class="col-sm-10" id="systemdiv">
                                       <input type="hidden" value='' id="systemid">
									<form:select path="system" htmlEscape="false" class="form-control ipt_w4"> 
								 		<form:option value="1">2013小企业会计准则</form:option>
								 		<form:option value="2">2007企业会计准则</form:option>
					             	</form:select><em>*</em>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label">记账人</label>
									<div class="col-sm-10">
										<%-- <form:input id="supervisor" path="supervisor" htmlEscape="false" maxlength="100" class="form-control ipt_w4"/>
										<span class="help-inline"><font color="red">*</font> </span> --%>
										<select id="supervisor" name="supervisor" class="form-control ipt_w4">
											<c:forEach items="${userlist}" var="user">
											<c:if test="${user.name==username}">
												<option value="${user.id}" selected="true">${user.name}</option>
											</c:if>
											<c:if test="${user.name!=username}">
											<option value="${user.id}" >${user.name}</option>
											</c:if>
											</c:forEach>											
										</select>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<span class="right">
										
										<!--  <input id="btnCancel" class="btn btn-default btn_w_a btn_bg_4 mar-rig10" type="button" value="取 消"/>-->
									 
										<button id="creat_per" type="button" class="btn btn-default btn_w_a btn_bg_2 ">确定</button>
										</span>
										<div class="right"></div>
									</div>
								</div>
						
						</div>
					</div>
				</div>
				<!--联系方式-->
				<div role="tabpanel" class="tab-pane" id="tab_g2">
					<div class="client_inner2">
						<div class="client_form2">
						
								<div class="form-group">
									<label class="col-sm-3 control-label">联系人</label>
									<div class="col-sm-9">
										<form:input path="contactPerson" htmlEscape="false" maxlength="20" class="form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">手机号码</label>
									<div class="col-sm-9">
									<form:input path="mobilePhone" htmlEscape="false" maxlength="20" class="form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">E-mail</label>
									<div class="col-sm-9">
										<form:input path="eMail" htmlEscape="false" maxlength="50" class="form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">QQ号</label>
									<div class="col-sm-9">
										<form:input path="qq" htmlEscape="false" maxlength="20" class="form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">详细地址</label>
									<div class="col-sm-9">
										<form:input path="detailedAddress" htmlEscape="false" maxlength="100" class="form-control ipt_w5"/>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-3 col-sm-9">
										<span class="right">
										
										<!--  <input id="btnCancel1" class="btn btn-default btn_w_a btn_bg_4 mar-rig10" type="button" value="取 消"/>&nbsp;-->
										
										<button id="creat_per1" type="button" class="btn btn-default btn_w_a btn_bg_2 ">确定</button>
										</span>
										<div class="right"></div>
									</div>
								</div>
						</div>
					</div>
				</div>
				<div role="tabpanel" class="tab-pane" id="tab_g3">
					<!--税务资料-->
					<div class="client_inner2">
						<div class="client_form3">
						
								<div class="form-group">
									<label class="col-sm-3 control-label">增值税性质</label>
									<div class="col-sm-9">
									 	<label class="radio-inline">
									 		<form:radiobutton path="valueAddedTax" value="0" checked="true"/>小型规模纳税人
										</label>
										<label class="radio-inline">
											<form:radiobutton path="valueAddedTax" value="1" />一般纳税人
										</label>										
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">国家税务登记证号</label>
									<div class="col-sm-9">
										<form:input path="stateTaxRegistrationNumber" htmlEscape="false" maxlength="20" class="form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">专管员</label>
									<div class="col-sm-9">
										<form:input path="statetaxregistrationadmin" htmlEscape="false" maxlength="20" class="form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">地税税务登记证号</label>
									<div class="col-sm-9">
										<form:input path="localTaxRegistrationNumber" htmlEscape="false" maxlength="20" class="form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">专管员</label>
									<div class="col-sm-9">
										<form:input path="localtaxregistrationadmin" htmlEscape="false" maxlength="20" class="form-control ipt_w4"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">证书到期时间</label>
									<div class="col-sm-9">
									 <!--	<input name="certificatesmature" type="text" class="form-control ipt_w4" id="datepicker2" />-->
									
									 <input name="certificatesmatureNew" type="text" class="form-control ipt_w4" id="datepicker2" /> 
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-3 col-sm-9">
										<span class="right">


										<!--  <input id="btnCancel2" class="btn btn-default btn_w_a btn_bg_4 mar-rig10" type="button" value="取 消"/>-->

											 
											<button id="creat_per2" type="button" class="btn btn-default btn_w_a btn_bg_2 ">确定</button>&nbsp;
											
										</span>
										<div class="right"></div>
									</div>
								</div>
						</div>
					</div>
				</div>
				</form:form>
				<div role="tabpanel" class="tab-pane" id="tab_g4">
					<!--附件-->
					<div class="client_inner2">
						<div class="client_form4">
							<form id="upload" enctype="multipart/form-data" method="post">
								<div class="form-group">
									<label class="col-sm-3 control-label">附件名称</label>
									<div class="col-sm-9">
									 	<input id="appendix" name="appendix" type="text" class="form-control ipt_w6" placeholder="" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">附件</label>
									<div class="col-sm-9">
									 	<input id="filetype" type="file" name="files" class="mar-top5" placeholder="" />
									 	<!--  
									 	<input type="file" name="files1" class="mar-top5" placeholder="" />
									 	-->
									 	<div class="mar-top5">支持txt xls doc gif pdf等文件格式，大小不超过500K</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-3 col-sm-9">
										<span class="right">
										<button id="uploadbutton" type="button" class="btn btn_w_a btn_bg_2 btn_up4 mar-rig10">上传</button>
										<button type="reset" class="btn btn_w_a btn_bg_4">重置</button>
										</span>
										<div class="right"></div>
									</div>
								</div>
							</form>
						</div>
					</div>
					<div class="tit_sc3 font-14 border_top1"><b>附件列表</b></div>
					<div class="min-h40">
						<table id="uptable" class="table table-bordered table_list4">
							<thead>
								<tr>
								<th>名称</th>
								<th>上传人</th>
								<th>上传时间</th>
								<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<!-- 动态添加的标签 -->
							</tbody>
						</table>
					</div>
					<div class="pad_10_3">
						<div class="page_cc">共2条记录
							<button class="btn_p7"></button>
							<span class="pad_l_r5">1</span>
							<button class="btn_p8"></button>
						</div>
					</div>
				</div>
				<div role="tabpanel" class="tab-pane" id="tab_g5">
					<!--客户查账-->
					<div class="client_inner2">
						<div class="client_form5 mar_auto">
							<div class="left w250"><img src="${ctxStatic}/images/gzpt/img_31.jpg" /></div>
							<div class="right w320">
								<div class="font-24">企业查账账号</div>
								<div class="hr20"></div>
								<div class="">微信扫描左侧二维码，关注即可实时查账</div>
								<div class="hr20"></div>
								<span id="khspan">
								<form class="form-horizontal" action="${ctx}/workterrace/chargeToAccount/updatepwd" method="post" id="wechatform">
								<input type="hidden" id="idi" name="fdbid"/>
								  <div class="form-group wxusername">
								    <label class="col-sm-3 control-label text-left">用户名</label>
								    <div class="col-sm-5">
								    <input type="hidden" id="username" name="username"/>
								      <p class="form-control-static" id="usernamew">cuihuohan</p>
								    </div>
								  </div>
								  <div class="form-group wxpassword">
								    <label class="col-sm-3 control-label text-left">密　码</label>
								    <div class="col-sm-5">
								      <input type="text" class="form-control ipt_w5" placeholder="" id="pwd" name="password"/>
								    </div>
								    <div class="col-sm-3">
								    	<div class='act_buttons'>
								    		<button type="button" class="btn btn-default btn_w_a btn_bg_2 btn_opt1">重置密码</button>
								    		<button type="button" class="btn btn-default btn_w_a btn_bg_2 subbutton btn_opt2" style='display:none;'>保存</button>
								    	</div>
								    </div>
								  </div>
								</form>	
								</span>							
							</div>
							<div class="clearfix"></div>
							<div class="hr20"></div>
						</div>
					</div>
					<!-- 新添加的确定 -->
					<div class="form-group" style="margin:0 20px 20px;">
						<div class="col-sm-offset-3 col-sm-9">
							<span class="right">
							<!--  <input id="btnCancel2" class="btn btn-default btn_w_a btn_bg_4 mar-rig10" type="button" value="取 消"/>-->
								<button id="creat_per3" type="button" class="btn btn-default btn_w_a btn_bg_2 ">确定</button>&nbsp;
								
							</span>
							<div class="right"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
</div>

<script>
var ycid;
var dialog, form;
dialog = $( "#dialog_add" ).dialog({
	autoOpen: false,
	width: 840,
	modal: true
});	 

$("#creat_per,#creat_per2,#creat_per1,#creat_per3").click(function() {
		//alert(ycid);
		var cusnameval=$("#customer").val();
		var idthis=$(".yc").val();
		if($("#customer").val()!="" && $("#supervisor").val()!=""){
			if("undefined" != typeof ycid){
				$("#inputForm").ajaxSubmit({
			           type: 'post',
			           url: "${ctx}/customer/tCustomer/update" ,
			           data: $("#inputForm").serialize(),
			           success: function(data){
			           	if(data=="1"){
			           		messagePop("修改成功！");           		
			               //	window.location="${pageContext.request.contextPath}/a/workterrace/chargeToAccount";
			               //alert(idthis);
			               $("#"+idthis).html(cusnameval);
			           		$( "#dialog_add" ).dialog("close");
			           	}
			               $( "#inputForm").resetForm();
			           },
			           error: function(XmlHttpRequest, textStatus, errorThrown){
			        	   messagePop( "error");
			           }                
			       })
			       $( "#inputForm").resetForm();
			}else{
				$("#inputForm").ajaxSubmit({
		            type: 'post',
		            url: "${ctx}/customer/tCustomer/save",
		            data: $("#inputForm").serialize(),
		            success: function(data){
		            	if(data.result=="1"){
		            		messagePop("保存成功！");	
		            		var activeeq=$(".active:eq(0)  a").text();
		            		var tCustomer=data.tCustomer;
		            		switch(activeeq){ 
		            		  
		            	    case "记账":    
		            	    	saveIsOk(tCustomer);
		            	    	 
		            	      break; 
		            	      
		            	    case "报税": 
		            	    	saveIsOk(tCustomer);

		            	      break; 
		            	    case "工资": 
		            	    	saveIsOk(tCustomer);
		            	      break; 
		            	    case "日常": 
		            	    	var item=""; 	 
		            	    	item+="<tr><td width=\"30\" class=\"mynum\"><span> "+($("#myTable01 tbody tr").length)+"</span></td>"; 
		            	    	item+="<td title=\""+tCustomer.customerName+"\" class=\"row_01\">"; 
		            	    	item+="<span id="+tCustomer.id+">"+(tCustomer.customerName.length>13 ? tCustomer.customerName.substring(0, 13)+"...":tCustomer.customerName) +"</span>"; 
		            	    	item+="<span class=\"btn_h4 right\">"; 
		            	    	item+="<input id=\"customerName\" type=\"hidden\" value=\""+tCustomer.customerName+"\"/>"; 
		            	    	item+="<a title=\"编辑\" href=\"javascript:void(0);\" class=\"dialog_add btn btn-default btn_n4 bg_p6 btn_i2 pad_10\"></a>"; 
		            	    	item+="<a title=\"移除\" href=\"javascript:void(0);\" onclick=\"delectCustomer("+tCustomer.id+")\" class=\"btn btn-default btn_n4 bg_p6 btn_i4 pad_10\"></a>"; 
		            	    	item+="<a title=\"收费\" href=\"javascript:void(0);\" class=\"dialog_mon btn btn-default btn_n4 bg_p6 btn_i7 pad_10\"></a>"; 
		            	    	item+="<input id=\"tCustomerid\" type=\"hidden\" value=\""+tCustomer.id+"\"/>"; 
		            	    	item+="</span></td>"; 
		            	    	item+="<td colspan=\"12\" class='td_bg3'><div class=\"line_table4\">"; 
		            	    	item+="<div class=\"nums_width\" attrstart='01' attrend='06'>"; 
		            	    	item+="<div class=\"lines_t5 pad_3 block_c1 radius_5 dialog_edit2  \"></div>"; 
		            	    	item+="<span class=\"glyphicon glyphicon-ok-circle sta_c01 none\"></span>"; 
		            	    	item+="</div><div class=\"nums_width\" attrstart='07' attrend='09'>"; 
		            	    	item+="<div class=\"lines_t5 pad_3 block_c2 radius_5 dialog_edit2  \" ></div>"; 
		            	    	item+="<span class=\"glyphicon glyphicon-ok-circle sta_c02 none\"></span>"; 
		            	    	item+="</div><div class=\"nums_width\" attrstart='01' attrend='05'>"; 
					          	item+="<div class=\"lines_t5 pad_3 block_c3 radius_5 dialog_edit2  \" ></div>"; 
					          	item+="<span class=\"glyphicon glyphicon-ok-circle sta_c03 none\"></span>"; 
					          	item+="</div></div></td></tr>"; 
		            	    	$("#myTable01 tbody").append(item);
		            	    	$(".nums_width").each(function(){
		            				var attrstart=$(this).attr('attrstart');
		            				var attrend=$(this).attr('attrend');
		            				var widthLine=attrend-attrstart+1;
		            				var marginLeft=attrstart-1;
		            				$(this).css({
		            					'width':widthLine*55,
		            					'margin-left':marginLeft*55
		            				})
		            			})
		            			
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
								});	
		            	      break; 
		            	    } 
		            		
		            		
		            		
		                	//window.location="${pageContext.request.contextPath}/a/workterrace/chargeToAccount";
		            	
		            	}
		            	$( "#dialog_add" ).dialog("close");
		                $( "#inputForm").resetForm();
		            	$( "#upload").resetForm();
		            },
		            error: function(XmlHttpRequest, textStatus, errorThrown){
		            	messagePop( "error");
		            }                
		        })
		        $( "#inputForm").resetForm();
				$( "#upload").resetForm();
			}
			
       }else{
    	   messagePop("请填写必须要输入的信息！");
       }
});
	
$(".subbutton").click(function() {
	//alert(1)
	$("#wechatform").ajaxSubmit({
       type: 'post',
       url: "${ctx}/workterrace/chargeToAccount/updatepwd" ,
       data: $("#wechatform").serialize(),
       success: function(data){
       	if(data=="1"){
       		messagePop("重置密码成功！");
       		$(".act_buttons .btn_opt2").hide();
       		$(".act_buttons .btn_opt1").show();
       		$("#pwd").attr("style","border:0");
           //	window.location="${pageContext.request.contextPath}/a/workterrace/chargeToAccount";
       	}
          // $( "#inputForm").resetForm();
       },
       error: function(XmlHttpRequest, textStatus, errorThrown){
    	   messagePop( "error");
       }                
   })
   
});
	
	
$( "#btnCancel,#btnCancel1,#btnCancel2" ).on('click',function( event ) {
	dialog.dialog( "close" );
	//event.preventDefault();
});

$( ".datepicker" ).datepicker({
	  monthNamesShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],  // 区域化月名为中文  
	  prevText: '上月',         // 前选按钮提示  
	  nextText: '下月',         // 后选按钮提示  
	           changeYear: true,          // 年下拉菜单  
	           changeMonth: true,             // 月下拉菜单  
	           showButtonPanel: true,         // 显示按钮面板  
	           showMonthAfterYear: true,  // 月份显示在年后面  
	           currentText: "本月",         // 当前日期按钮提示文字  
	           closeText: "确认",           // 关闭按钮提示文字  
	           dateFormat: "yy-mm",       // 日期格式  
	  onClose: function(dateText, inst) {// 关闭事件  
	      var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();  
	      var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();  
	      $(this).datepicker('setDate', new Date(year, month, 1));  
	  }  
});

form = dialog.find( "form" ).on( "submit", function( event ) {
//event.preventDefault();
//addUser();
});
/*日期*/
$( "#datepicker1" ).datepicker({
	dateFormat:'yy-mm-dd'
});
$( "#datepicker2" ).datepicker({
	dateFormat:'yy-mm-dd'
});
//移除客户
$( "#dialog_remove" ).dialog({
	autoOpen: false,
	width: 470,
	modal: true
});	
$( ".dialog_remove" ).click(function( event ) {
	$( "#dialog_remove" ).dialog( "open" );
	event.preventDefault();
});


</script>
<script>
$(function(){
	$("#uploadbuttonn").on("click",function(){
			if($("#appendix").val()=="" && $("#filetype").val()==""){
				messagePop("请输入必填信息");
			}else{
			$("#upload").ajaxSubmit({
				type:'post',
				url:"${ctx}/customer/tCustomer/fileUpload2",
				success:function(data){
					var list=eval(data);
					$.each(list,function(n,val){
						$("table#uptable>tbody").append("<tr>"+
						"<td>"+val.appendixName+"</td>"+
						"<td>"+val.upPeople+"</td>"+"<td>2015-12-07</td>"+
						"<td>"+
						"<a class='btn btn-default btn_n4 bg_p6 btn_i6 pad_10' href='${ctx}/customer/tCustomer/downFile?fileName="+val.appendixTypeName+"'></a>"+
						"<a id='"+val.id+"' class='btn btn-default btn_n4 bg_p6 btn_i4 pad_10' onclick='buttondelete("+val.id+")'></a>"+
						"</td>"+
						"</tr>"
						)
						
					});
				},
				error:function(XmlHttpRequest, textStatus, errorThrown){
					messagePop("error");
				}
			});
			}
		});
		
	})
	
	
	$(function(){
		//-------开始
		$("#uploadbutton").on("click",function(){
		//var appendixname=$("#appendix").val();
		
		if($("#appendix").val()=="" || $("#filetype").val()==""){
			messagePop("请输入必填上传信息或选中文件");
		}else{
		$("#upload").ajaxSubmit({
			type:'post',
			url:"${ctx}/customer/tCustomer/fileUpload2",
			//data:"appendixName="+appendixname,
			success:function(data){
				var arrays=$("#array").val();
				if(arrays==''){
					$("#array").val(data.id);
				}else{
					$("#array").val($("#array").val()+","+data.id);
				}
				
				$("table#uptable>tbody").append("<tr>"+
						"<td>"+data.appendixName+"</td>"+
						"<td>"+data.upPeople+"</td>"+"<td>"+data.createDate+"</td>"+
						"<td>"+
						"<a class='btn btn-default btn_n4 bg_p6 btn_i6 pad_10' href='${ctx}/customer/tCustomer/downFile?fileName="+data.appendixTypeName+"'></a>"+
						"<a id='"+data.id+"' class='btn btn-default btn_n4 bg_p6 btn_i4 pad_10' onclick='buttondelete("+data.id+")'></a>"+
						"</td>"+
						"</tr>"
				)
			},
			error:function(XmlHttpRequest, textStatus, errorThrown){
				messagePop("error");
			}
		});
		$( "#upload").resetForm();
		}
	});
		//-------结束
	})
/**当ID值是的时候可以调用***/
function buttondelete(id){
	$.ajax({
        type: "POST",//使用post方法访问后台
        url: "${ctx}/customer/tCustomer/deleteById",//要访问的后台地址  
        data: "id="+id,
        success: function(data){
        	messagePop("删除成功");
        	$('#'+id).parent().parent().remove();
        }
})
}	

function saveIsOk(tCustomer){
var item=""; 	 
item+="<tr><td width=\"30\" class=\"mynum\"><span> "+($("#myTable01 tbody tr").length+1)+"</span></td>"; 
item+="<td title=\""+tCustomer.customerName+"\" class=\"row_01\">"; 
item+="<span id="+tCustomer.id+">"+(tCustomer.customerName.length>13 ? tCustomer.customerName.substring(0, 13)+"...":tCustomer.customerName) +"</span>"; 
item+="<span class=\"btn_h4 right\">"; 
item+="<input id=\"customerName\" type=\"hidden\" value=\""+tCustomer.customerName+"\"/>"; 
item+="<a title=\"编辑\" href=\"javascript:void(0);\" class=\"dialog_add btn btn-default btn_n4 bg_p6 btn_i2 pad_10\"></a>"; 
item+="<a title=\"移除\" href=\"javascript:void(0);\" onclick=\"delectCustomer("+tCustomer.id+")\" class=\"btn btn-default btn_n4 bg_p6 btn_i4 pad_10\"></a>"; 
item+="<a title=\"收费\" href=\"javascript:void(0);\" class=\"dialog_mon btn btn-default btn_n4 bg_p6 btn_i7 pad_10\"></a>"; 
item+="<input id=\"tCustomerid\" type=\"hidden\" value=\""+tCustomer.id+"\"/>"; 
item+="</span></td>"; 
item+="<td class=\"cursorPointer\"><span></span></td>";
item+="<td class=\"cursorPointer\"><span></span></td>";
item+="<td class=\"cursorPointer\"><span></span></td>";
item+="<td class=\"cursorPointer\"><span></span></td>";
item+="<td class=\"cursorPointer\"><span></span></td>";
item+="<td class=\"cursorPointer\"><span></span></td>";
item+="<td class=\"cursorPointer\"><span></span></td>";
item+="<td class=\"cursorPointer\"><span></span></td>";
item+="<td class=\"cursorPointer\"><span></span></td>";
item+="<td class=\"cursorPointer\"><span></span></td>";
item+="<td class=\"cursorPointer\"><span></span></td>";
item+="<td class=\"cursorPointer\"><span></span></td>";
$("#myTable01 tbody").append(item);

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
});
}
</script>
<script>

$(".act_buttons .btn_opt1").on('click',function(){
	$(this).hide();
	$(".act_buttons .btn_opt2").show();
	$("#pwd").attr("style","border:1px #999999 solid")

})
/*
var dateYear = new Date();
var myers = $("#year_span");
myers.empty();
var nowyear=dateYear.getFullYear();
var oldyear=nowyear-5;
var newyears=nowyear+5;
var currentPeriod=$("#currentPeriod").val();//获取当前账期
alert(currentPeriod+"得到了幺")
var initPeriod=$("#initPeriod").val();//获取起始账期
var yearpreiod=initPeriod.substring(0,4);
var monthperiod=initPeriod.substring(4,6);
for(var i=oldyear;i<newyears;i++) {
	var option = $("<option>").text(i).val(i)
	myers.append(option);
}
var nowmonth=dateYear.getMonth()+1;
var months = $("#month_span");
months.empty();
for(var j=1;j<13;j++) {	
	//alert(typeof j);
	if(j==nowmonth){
		if(j<10){
			var option = $("<option selected>").text('0'+j).val('0'+j);
		}else{
			var option = $("<option selected>").text(j).val(j);
		}
		
	}else{
		if(j<10){
			var option = $("<option>").text('0'+j).val('0'+j);
		}else{
			var option = $("<option>").text(j).val(j);
		}
		
	}
	months.append(option);
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

*/
newDate();
/**20160130**/
$(function(){
	$(".khcz").on("click",function(){//点击的时候填充微信企业账号
		var id=$(".yc").val();
		//alert(id);
		if(id.length==0){
			$("#khspan").html("这是新加客户，请到工作台设置企业查找账号");
		}else{
		$.ajax({
	        type: "POST",//使用post方法访问后台
	        url: "${ctx}/workterrace/chargeToAccount/fillcount",//要访问的后台地址  
	        data: "id="+id,
	        success: function(wechet){
	        	var username=wechet.userName;var password=wechet.plainTextPassword;
	        	//alert(username+"###"+password);
	        	$("#idi").val(id);
	        	$("#username").val(username);//给隐藏的用户名赋值
	        	$("#usernamew").html(username);//给用户名
	        	$("#pwd").val(password);
	        }
	})}
	});
	//--
	//点击附件列表附件
	$(".fj").on("click",function(){
		var idthis=$(".yc").val().toString();
		$( "#upload").resetForm();
		$("table#uptable>tbody").empty();
		//ajax
		$.ajax({
        type: "POST",
        url: "${ctx}/customer/tAppendix/findBytcuid",
        data:"id="+idthis,
        success: function(data){
        	var list=eval(data);
			$.each(list,function(n,val){
				$("table#uptable>tbody").append("<tr>"+
				"<td>"+val.appendixName+"</td>"+
				"<td>"+val.upPeople+"</td>"+"<td>"+val.createDate+"</td>"+
				"<td>"+
				"<a class='btn btn-default btn_n4 bg_p6 btn_i6 pad_10' href='${ctx}/customer/tCustomer/downFile?fileName="+val.appendixTypeName+"'></a>"+
				"<a id='"+val.id+"' class='btn btn-default btn_n4 bg_p6 btn_i4 pad_10' onclick='buttondelete("+val.id+")'></a>"+
				"</td>"+
				"</tr>"
				)
			}) }
        })
		//ajax结束
		
		
		
		
	});
	//---
	
})

function messagePop(str){//用于显示提示信息
	$(".message-pop").fadeIn();
	$(".message-pop").children("span").html(str);
	setTimeout(function(){
		$(".message-pop").fadeOut();
	},2000)
}
</script>