<!doctype html>
<html lang="en">
<head>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>工资数据表-工作台-芸豆会计</title>
<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery.ztree.core-3.5.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/zTreeStyle/zTreeStyle.css" type="text/css">
<link href="${pageContext.request.contextPath}/static/css/master_2.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/easyui/default/easyui.css">

<style>
.t_pane_a1 .nav-tabs{border-bottom:none;}
.t_pane_a1 .nav-tabs>li.active a{color:#5f81a9;border-top:1px solid #5f81a9;}
.panel-header, .panel-body{border-color:#5f81a9;}

.tab-content td input {border: none;}
.nav-tabs > li.save {float: right;padding-top: 5px;}
.nav-tabs > li.date {font-size: 16px;line-height: 36px;padding:0 0 0 20px;}
.nav-tabs > li.radio {padding: 0;margin: 0 0 0 20px;line-height: 38px;}
.nav-tabs > li.radio span {font-size: 16px;line-height: 24px;}
.nav-tabs > li.radio input {position: static;margin: 0 5px 0 10px;}
.tab-content .datagrid-cell {min-width: 100px;}
#tab_g1 .datagrid-cell-c1-attr8 {width: auto;}
.message-pop{display:none;position:fixed;top:40px;width:100%;text-align:center;z-index:1000;}
.message-pop span{background:#4f9de8;padding:5px 75px;color:#fff;}
.pane_main5 {width:100%;}
</style>
<script type="text/javascript">
</script>

</head>
<body>
<div class="message-pop"><span>保存成功！</span></div>
<div class="main-row">
	<div class="main_index3">
		<div class="pane_main5">
			<div class="t_pane_a1 radius_5">
				<div class="user_pane_data2">
					
					<ul class="nav nav-tabs" role="tablist">
						<li class="active"><a href="#tab_g1">工资数据表</a></li>
						<li class="date">
			<span>${tCustomerSalary.salaryPeriod}</span>期

						</li>
						
						<li class="save">
							<span class="right">
								<button onclick="getExcelByTemplate()" class="btn btn-default btn_w_a btn_bg_2 radius0 left mar-rig10">下载</button>
							</span>
							<span class="right">
								<button onclick="accept()" class="btn btn-default btn_w_a btn_bg_2 radius0 left mar-rig10">保存</button>
							</span>
						</li>
					</ul>
					<div class="tab-content main5_con radius_5">
						<div class="tab-pane active" id="tab_g1">
							<table id="dg" class="easyui-datagrid" style="min-height:500px"data-options="singleSelect:true,collapsible:true,url:'${ctx}/workterrace/tCustomerSalary/tCustomerSalaryList?salaryPeriod=${tCustomerSalary.salaryPeriod}&customerId=${tCustomerSalary.customerId}',method:'get',onDblClickRow:onDblClickRow,onBeginEdit:onBeginEdit,onAfterEdit:onAfterEdit,onRowContextMenu"><thead>
									<tr><th rowspan="2" data-options="field:'ordernum',width:60,align:'left'">序号*</th>
										<th rowspan="2" data-options="field:'employeeName',width:80,align:'left',editor:{type:'textbox',options:{required:true,missingMessage:'姓名不能为空'}}">姓名*</th>
									    <th rowspan="2" data-options="field:'identityTypeId',width:100,editor:{type:'combobox',options:{valueField:'identityTypeId',textField:'identityTypeName',data:dataIdentityType,required:true,missingMessage:'身份证件类型不能为空'}}">身份证件类型*</th>
										<th rowspan="2" data-options="field:'idNumber',width:150,align:'left',editor:{type:'textbox',options:{required:true,missingMessage:'身份证件号码不能为空'}}">身份证件号码*</th>
 										<th rowspan="2" data-options="field:'incomeProject',width:150,editor:{type:'combobox',options:{valueField:'incomeProjectId',textField:'incomeProjectName',data:dataIncomeProject,required:true,missingMessage:'所得项目不能为空'}}">所得项目*</th>
										<th rowspan="2" data-options="field:'incomePeriod',width:60,align:'center'" editor="text">所得期间</th>
										<th rowspan="2" data-options="field:'incomeMoney',width:80,align:'right',editor:{type:'numberbox',options:{precision:2,required:true,missingMessage:'收入额'}}" >收入额*</th>			
										<th rowspan="2" data-options="field:'taxFreeIncome',width:80,align:'right',editor:{type:'numberbox',options:{precision:2}}">免税所得</th>
										<th colspan="8" >税前扣除项目</th>
										<th rowspan="2" data-options="field:'deductionExpenses',width:50,align:'right',editor:{type:'numberbox',options:{precision:2,required:true,missingMessage:'减除费用不能为空'}}">减除费用*</th>
										<th rowspan="2" data-options="field:'deductibleDonationAmount',width:50,align:'right',editor:{type:'numberbox',options:{precision:2}}">准予扣除的捐赠额</th>
										<th rowspan="2" data-options="field:'shouldPayIncome',width:50,align:'right',editor:{type:'numberbox',options:{precision:2}}">应纳税所得额#</th>
										<th rowspan="2" data-options="field:'taxRate',width:50,align:'right',editor:{type:'numberbox',options:{precision:2,required:true,missingMessage:'税率%不能为空'}}">税率%*#</th>
										<th rowspan="2" data-options="field:'quickDeduction',width:50,align:'right',editor:{type:'numberbox',options:{precision:2,missingMessage:'速算扣除数不能为空'}}">速算扣除数*#</th>
										<th rowspan="2" data-options="field:'shouldPay',width:50,align:'right',editor:{type:'numberbox',options:{precision:2}}">应纳税额#</th>
										<th rowspan="2" data-options="field:'taxRelief',width:50,align:'right',editor:{type:'numberbox',options:{precision:2}}">减免税额</th>
										<th rowspan="2" data-options="field:'shouldBucklePay',width:50,align:'right',editor:{type:'numberbox',options:{precision:2}}">应扣缴税额#</th>
										<th rowspan="2" data-options="field:'hasBeenWithholdingTax',width:50,align:'right',editor:{type:'numberbox',options:{precision:2,missingMessage:'已扣缴税额不能为空'}}">已扣缴税额*</th>
										<th rowspan="2" data-options="field:'shouldRepairPay',width:50,align:'right',editor:{type:'numberbox',options:{precision:2}}">应补（退）税额#</th>
										<th rowspan="2" data-options="field:'unitEndowmentInsurance',width:50,align:'right',editor:{type:'numberbox',options:{precision:2}}">养老保险</th>
										<th rowspan="2" data-options="field:'unitMedicalInsurance',width:50,align:'right',editor:{type:'numberbox',options:{precision:2}}">医疗保险</th>
										<th rowspan="2" data-options="field:'unitUnemploymentInsurance',width:50,align:'right',editor:{type:'numberbox',options:{precision:2}}">失业保险</th>
										<th rowspan="2" data-options="field:'unitInjuryInsurance',width:50,align:'right',editor:{type:'numberbox',options:{precision:2}}">工伤保险</th>
										<th rowspan="2" data-options="field:'unitBirthInsurance',width:50,align:'right',editor:{type:'numberbox',options:{precision:2}}">生育保险</th>
										<th rowspan="2" data-options="field:'unitHouseFund',width:50,align:'right',editor:{type:'numberbox',options:{precision:2}}">住房公积金</th>
										<th rowspan="2" data-options="field:'remarks1',width:50,align:'right'" editor="text">备注</th>
									<!--<th rowspan="2" data-options="field:'salaryPeriod',width:50,align:'center',hidden:'true'" editor="text">当前账期</th>
										<th rowspan="2" data-options="field:'customerId',width:50,align:'center',hidden:'true'" editor="text">公司id</th>
										<th rowspan="2" data-options="field:'isNewRecord',width:50,align:'center',hidden:'true'" editor="text">公司id</th> -->
										</tr><tr>
										<th data-options="field:'endowmentInsurance',width:80,align:'right',editor:{type:'numberbox',options:{precision:2}}">基本养老保险费</th>
										<th data-options="field:'medicalInsurance',width:80,align:'right',editor:{type:'numberbox',options:{precision:2}}">基本医疗保险费</th>
										<th data-options="field:'unemploymentInsurance',width:60,align:'right',editor:{type:'numberbox',options:{precision:2}}">失业保险费</th>
										<th data-options="field:'houseFund',width:60,align:'right',editor:{type:'numberbox',options:{precision:2}}">住房公积金</th>
										<th data-options="field:'originalValue',width:60,align:'right',editor:{type:'numberbox',options:{precision:2}}">财产原值</th>
										<th data-options="field:'allowableDeductions',width:60,align:'right',editor:{type:'numberbox',options:{precision:2}}">允许扣除的税费</th>
										<th data-options="field:'otherDeduction',width:60,align:'right',editor:{type:'numberbox',options:{precision:2}}">其他</th>
										<th data-options="field:'totalDeduction',width:50,align:'right',editor:{type:'numberbox',options:{precision:2}}">合计#</th>
										</tr>
								</thead>
							</table>
						</div>
					</div>
					<div class="hr10"></div>
				</div>
			</div>
			<div class="hr20"></div>
			
		</div>
	</div>
</div>
						<div class="hr20"></div><div id="dialog_sure" class="dialog_sure">
		<div class="dialog">保存成功!</div>
	</div>
	<div id="dialog_v" class="dialog_sure">
		<div class="dialog">请填写完整信息!</div>
	</div>

<script type="text/javascript">

/*window.onkeydown= function(e){
	  e = e || window.event;
	  if(e.keyCode == 17&&e.keyCode ==83){
		  alert("hello");
	  }
	  alert(e.keyCode);
	  
	}*/
	
/*
$.extend($.fn.datagrid.methods, {
			editCell: function(jq,param){
				return jq.each(function(){
					var opts = $(this).datagrid('options');
					var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
					for(var i=0; i<fields.length; i++){
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor1 = col.editor;
						if (fields[i] != param.field){
							col.editor = null;
						}
					}
					$(this).datagrid('beginEdit', param.index);
					for(var i=0; i<fields.length; i++){
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor = col.editor1;
					}
				});
			}
		});*/
	
	
		
		
		
	
		
		
	var editIndex = undefined;

	function endEditing(){
		if (editIndex == undefined){return true}
		if ($('#dg').datagrid('validateRow', editIndex)){
			
			
		/*	var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'identityTypeId'});
			var identityTypeName = $(ed.target).combobox('getText');
			$('#dg').datagrid('getRows')[editIndex]['identityTypeName'] = identityTypeName;*/
			
			$('#dg').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			
			return false;
		}
	}
	function onDblClickRow(rowIndex){
		if(${isok}=='0'){
			return;
		}
		var ed = $(this).datagrid('selectRow', rowIndex);
		// console.log($(ed));
		// console.log(index)
		$(ed).focus();
		if (endEditing()){
				
				$(this).datagrid('beginEdit', rowIndex);
				var cellEdit = $('#dg').datagrid('getEditors', rowIndex);
                var array = [1,3,14,17,18,19,20,22,24];
                for(var i=0;i<array.length;i++){
                    var $input = cellEdit[array[i]].target; // 得到文本框对象
                   $input.next().find("input").prop('readonly',true); // 设值只读
                }
				var allData= $('#dg').datagrid('getRows').length;
				editIndex = rowIndex;
				if(rowIndex+1==allData){
					$('#dg').datagrid('appendRow',{ordernum:rowIndex+2,salaryPeriod:"${tCustomerSalary.salaryPeriod}",customerId:"${tCustomerSalary.customerId}",isNewRecord:"true"});
					
				}
			
		}
	}
	
	var idValue=undefined;
	function onRowContextMenu(e, rowIndex, rowData) {         //右击事件
		if(${isok}=='0'){
			return;
		}
          e.preventDefault();
            var selected=$("#dg").datagrid('getRows'); //获取所有行集合对象
              idValue = selected[rowIndex].id;
             $(this).datagrid('selectRecord', idValue);  //通过获取到的id的值做参数选中一行
            $('#menu').menu('show', {
                left:e.pageX,
                top:e.pageY
            });     
        editIndex=rowIndex;
    }
	
	function menudelects(){
		if (idValue == undefined||editIndex == undefined){return}
		
		$.ajax({
			type:"post",
			url:" ${ctx}/workterrace/tCustomerSalary/delete?id="+idValue,
			success:function(result){
				$('#dg').datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
				editIndex = undefined;
				$('#dg').datagrid('acceptChanges');
				messagePop("删除成功");
			}
		});
		
		
	}
	
	
	function onBeginEdit(rowIndex){

		var editors = $('#dg').datagrid('getEditors', rowIndex);

		//9-16
		var n7 = $(editors[7].target);
		var n8 = $(editors[8].target);
		var n9 = $(editors[9].target);
		var n10 = $(editors[10].target);
		var n11 = $(editors[11].target);
		var n12 = $(editors[12].target);
		var n13 = $(editors[13].target);
		var n14 = $(editors[14].target);
		n7.add(n8).add(n9).add(n10).add(n11).add(n12).add(n13).numberbox({
			onChange:function(){
				var cost =(n7.numberbox('getValue')==""? 0 : parseFloat(n7.numberbox('getValue')))+
						(n8.numberbox('getValue')==""? 0 : parseFloat(n8.numberbox('getValue')))+
						(n9.numberbox('getValue')==""? 0 : parseFloat(n9.numberbox('getValue')))+
						(n10.numberbox('getValue')==""? 0 : parseFloat(n10.numberbox('getValue')))+
						(n11.numberbox('getValue')==""? 0 : parseFloat(n11.numberbox('getValue')))+
						(n12.numberbox('getValue')==""? 0 : parseFloat(n12.numberbox('getValue')))+
						(n13.numberbox('getValue')==""? 0 : parseFloat(n13.numberbox('getValue')));
				if(cost<0){
					cost=0;
				}
				n14.numberbox('setValue',cost);
			}
		});
		//第17列＝第5列－第6列－第14列－第15列－第16列
		var n17 = $(editors[17].target);
		var n5 = $(editors[5].target);
		var n6 = $(editors[6].target);
		var n15 = $(editors[15].target);
		var n16 = $(editors[16].target);
		var n18 = $(editors[18].target);
		var n19 = $(editors[19].target);
		n5.add(n6).add(n14).add(n15).add(n16).numberbox({
			onChange:function(){
				var cost = n5.numberbox('getValue')-n6.numberbox('getValue')-n14.numberbox('getValue')-n15.numberbox('getValue')-n16.numberbox('getValue');
				if(cost<0){
					cost=0;
				}
				n17.numberbox('setValue',cost);
				switch(true)
				{
				case cost<=1500:
					n18.numberbox('setValue',3);
					n19.numberbox('setValue',0);
					 break;
				case cost<=4500:
					n18.numberbox('setValue',10);
					n19.numberbox('setValue',105);
					 break;
				case cost<=9000:
					n18.numberbox('setValue',20);
					n19.numberbox('setValue',555);
					 break;
				case cost<=35000:
					n18.numberbox('setValue',25);
					n19.numberbox('setValue',1005);
					 break;
				case cost<=55000:
					n18.numberbox('setValue',30);
					n19.numberbox('setValue',2755);
					 break;
				case cost<=80000:
					n18.numberbox('setValue',35);
					n19.numberbox('setValue',5505);
					 break;
				default:
					n18.numberbox('setValue',45);
					n19.numberbox('setValue',13505);
				}
			}
		});
		//第20列＝第17列×第18列－第19列
		var n20 = $(editors[20].target);
		
		n17.add(n18).add(n19).numberbox({
			onChange:function(){
				var cost = n17.numberbox('getValue')*n18.numberbox('getValue')/100-n19.numberbox('getValue');
				if(cost<0){
					cost=0;
				}
				n20.numberbox('setValue',cost);
			}
		});
		//第22列＝第20列－第21列
		var n22 = $(editors[22].target);
		var n21 = $(editors[21].target);
		n20.add(n21).numberbox({
			onChange:function(){
				var cost = n20.numberbox('getValue')-n21.numberbox('getValue');
				if(cost<0){
					cost=0;
				}
				n22.numberbox('setValue',cost);
			}
		});
		//第24列＝第22列－第23列
		var n24 = $(editors[24].target);
		var n23 = $(editors[23].target);
		n22.add(n23).numberbox({
			onChange:function(){
				var cost = n22.numberbox('getValue')-n23.numberbox('getValue');
				if(cost<0){
					cost=0;
				}
				n24.numberbox('setValue',cost);
			}
		});

	}
	function onAfterEdit(){
		if(${isok}=='0'){
			return;
		}
		save(1);
		$('#dg').datagrid('acceptChanges');

	}
	
	
	//绑定datagrid
	/*function bindGridEvent(rowIndex)
    {
        try
        {
            var objGrid = $("#resourcePre");        
            var priceEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'price'}); 
            var realNumEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'realNum'}); 
            var totMoneyEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'totMoney'});      
       
            $(priceEdt.target).bind("blur",function(){
            	calcMoney(rowIndex);
            });
            
            $(realNumEdt.target).bind("blur",function(){
            	calcMoney(rowIndex);
            });
        }
        catch(e)
        {
            $.messager.alert('系统提示', "系统错误："+e);
        }
    }
    
    //计算
    function calcMoney(rowIndex)
    {
       var objGrid = $("#resourcePre");        
       var priceEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'price'}); 
       var realNumEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'realNum'}); 
       var totMoneyEdt = objGrid.datagrid('getEditor', {index:rowIndex,field:'totMoney'}); 
        
       var priceValue = $(priceEdt.target).val();
       var realNumValue = $(realNumEdt.target).val();
       var totMoneyValue = priceValue * realNumValue;
       //$(totMoneyEdt.target).numberbox("setValue",totMoneyValue); 
       $(totMoneyEdt.target).val(totMoneyValue);
    }


	//需要在增加、修改、单击双击事件中传递rowIndex参数
	onClickRow : function(rowIndex) {
				
	}*/
	function accept(){
		if(${isok}=='0'){
			return;
		}
		$('#dg').datagrid('endEdit', editIndex);
		if (endEditing()){
			save(0);
		}
		
	}
	
	function save(i){
		var allData= $('#dg').datagrid('getChanges');
		var effectRow = new Object();
        effectRow["allData"] = JSON.stringify(allData);
       // alert(effectRow["allData"]);
        if(effectRow["allData"]=="[]"&&i>0){
        	return;
        }
        	
        $.ajax({ 
            type:"post", 
            url:"${ctx}/workterrace/tCustomerSalary/savejson", 
            dataType:"json",      
            contentType:"application/json",               
            data:effectRow["allData"], 
            success:function(data){ 
            	messagePop("保存成功")
            	$('#dg').datagrid('acceptChanges');
            	/*if(i<1){
            		editIndex = undefined;
            	$('#dg').datagrid('reload');
            	
            	}*/
            } 
         }); 
	}
	

	
	function getExcelByTemplate(){
		window.location.href="${ctx}/workterrace/tCustomerSalary/GetExcelByTemplate?salaryPeriod=${tCustomerSalary.salaryPeriod}&customerId=${tCustomerSalary.customerId}";
	}

	var dataIdentityType=[{"identityTypeId":"身份证","identityTypeName":"身份证"},{"identityTypeId":"护照","identityTypeName":"护照"},{"identityTypeId":"军官证","identityTypeName":"军官证"},{"identityTypeId":"士兵证","identityTypeName":"士兵证"},{"identityTypeId":"港澳居民来往内地通行证","identityTypeName":"港澳居民来往内地通行证"},{"identityTypeId":"台湾居民来往大陆通行证","identityTypeName":"台湾居民来往大陆通行证"},{"identityTypeId":"其他","identityTypeName":"其他"}];
	var dataIncomeProject=[{"incomeProjectId":"0101-工资、薪金所得－普通月工资","incomeProjectName":"0101-工资、薪金所得－普通月工资"},{"incomeProjectId":"0103-工资、薪金所得－全年一次性奖金","incomeProjectName":"0103-工资、薪金所得－全年一次性奖金"},{"incomeProjectId":"0104-工资、薪金所得—无住所个人数月奖金","incomeProjectName":"0104-工资、薪金所得—无住所个人数月奖金"},{"incomeProjectId":"0105-工资、薪金所得－补发工资","incomeProjectName":"0105-工资、薪金所得－补发工资"},{"incomeProjectId":"0107-工资、薪金所得－内退一次性补偿","incomeProjectName":"0107-工资、薪金所得－内退一次性补偿"},{"incomeProjectId":"0108-工资、薪金所得－解除合同一次性补偿","incomeProjectName":"0108-工资、薪金所得－解除合同一次性补偿"},{"incomeProjectId":"0109-工资、薪金所得－期权奖励","incomeProjectName":"0109-工资、薪金所得－期权奖励"},{"incomeProjectId":"0110-工资、薪金所得－企业年金","incomeProjectName":"0110-工资、薪金所得－企业年金"},{"incomeProjectId":"0111-工资、薪金所得－提前退休一次性补贴","incomeProjectName":"0111-工资、薪金所得－提前退休一次性补贴"},{"incomeProjectId":"0112-工资、薪金所得－其他","incomeProjectName":"0112-工资、薪金所得－其他"},{"incomeProjectId":"0113-工资、薪金所得－出租车定额","incomeProjectName":"0113-工资、薪金所得－出租车定额"},{"incomeProjectId":"0400-劳务报酬所得","incomeProjectName":"0400-劳务报酬所得"},{"incomeProjectId":"0500-稿酬所得","incomeProjectName":"0500-稿酬所得"},{"incomeProjectId":"0600-特许权使用费所得","incomeProjectName":"0600-特许权使用费所得"},{"incomeProjectId":"0700-利息、股息、红利所得","incomeProjectName":"0700-利息、股息、红利所得"},{"incomeProjectId":"0801-财产租赁所得-房屋租赁所得","incomeProjectName":"0801-财产租赁所得-房屋租赁所得"},{"incomeProjectId":"0802-财产租赁所得-其他","incomeProjectName":"0802-财产租赁所得-其他"},{"incomeProjectId":"0901-财产转让所得-股票转让所得","incomeProjectName":"0901-财产转让所得-股票转让所得"},{"incomeProjectId":"0902-财产转让所得-个人房屋转让所得","incomeProjectName":"0902-财产转让所得-个人房屋转让所得"},{"incomeProjectId":"0999-财产转让所得－其他","incomeProjectName":"0999-财产转让所得－其他"},{"incomeProjectId":"1000-偶然所得","incomeProjectName":"1000-偶然所得"},{"incomeProjectId":"9900-其他所得","incomeProjectName":"9900-其他所得"}];


	var messagePop = function(str){
		console.log(2)
		$(".message-pop").fadeIn();
		$(".message-pop").children("span").html(str);
		setTimeout(function(){
			$(".message-pop").fadeOut();
		},2000)
	}
</script>
<div id="menu" class="easyui-menu" style="width:150px;">
<div id="menudelect" onclick="menudelects()">删除</div>
</div>
</body>
<style>
	.dialog_sure {
		width: 100%;
		height: 30px;
		display: none;
		position: absolute;
		top: 0;
		left: 0;
	}
	.dialog_sure .dialog {
		width: 200px;
		height: 30px;
		background: #d8e1e8;
		margin: 0 auto;
		line-height: 30px;
		text-align: center;
	}
</style>
</html>
