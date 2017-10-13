<!doctype html>
<html lang="en">
<head>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>工作台-工资数据表</title>
<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.11.2.js" type="text/javascript"></script>
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
</style>
</head>
<body>
<ul class="nav nav-tabs">
		
		<li><a href="${ctx}/workterrace/tCustomerSalary/form">工资添加</a></li>
	</ul>
<div class="main-row">
	
	<div class="main_index3">
		<div class="pane_main5">
			<div class="t_pane_a1 radius_5">
				<div class="user_pane_data2">
					<div class="hr20"></div>	
					<form:form id="searchForm" modelAttribute="tCustomerSalary" action="${ctx}/workterrace/tCustomerSalary/" method="post" class="breadcrumb form-search">
		<form:input path="salaryPeriod" readonly="readonly"/>
	</form:form>
					<ul class="nav nav-tabs" role="tablist">
						<li class="active"><a href="#tab_g1">工资数据表</a></li>
						<li class="date">
							<span>2015</span>年
						</li>
						<li class="radio">
							<input type="radio" name="month" id=""><span>1月</span>
							<input type="radio" name="month" id=""><span>2月</span>
							<input type="radio" name="month" id=""><span>3月</span>
							<input type="radio" name="month" id=""><span>4月</span>
							<input type="radio" name="month" id=""><span>5月</span>
							<input type="radio" name="month" id=""><span>6月</span>
							<input type="radio" name="month" id=""><span>7月</span>
							<input type="radio" name="month" id=""><span>8月</span>
							<input type="radio" name="month" id=""><span>9月</span>
							<input type="radio" name="month" id=""><span>10月</span>
							<input type="radio" name="month" id=""><span>11月</span>
							<input type="radio" name="month" id=""><span>12月</span>
						</li>
						<li class="save">
							<span class="right">
								<button class="btn btn-default btn_w_a btn_bg_2 radius0 left mar-rig10">保存</button>
							</span>
						</li>
					</ul>
					<div class="tab-content main5_con radius_5">
						<div class="tab-pane active" id="tab_g1">
							<table class="easyui-datagrid" style="min-height:250px"
								data-options="singleSelect:true,collapsible:true,url:'datatable.json',method:'get'">
								<thead>
									<tr>
										<th data-options="field:'names',width:80,align:'center'">序号</th>
											<th data-options="field:'typeid',width:80,align:'center'">客户id</th>
										<th data-options="field:'typeid',width:80,align:'center'">工资账期</th>
										<th data-options="field:'typeid',width:80,align:'center'">工资保存账期</th>
										<th data-options="field:'typeid',width:80,align:'center'">序号1</th>
										<th data-options="field:'typeid',width:80,align:'center'">姓名</th>
										<th data-options="field:'numid',width:100,align:'center'">身份证件类型</th>
										<th data-options="field:'city',width:60,align:'center'">身份证件号码</th>
										<th data-options="field:'code2',width:60,align:'center'">所得项目</th>
										<th data-options="field:'project2',width:60,align:'center'">所得期间</th>
										<th data-options="field:'attr1',width:80,align:'center'">收入额</th>			
										<th data-options="field:'attr2',width:80,align:'center'">免税所得</th>
										<th data-options="field:'attr3',width:80,align:'center'">基本养老保险费</th>
										<th data-options="field:'attr4',width:80,align:'center'">基本医疗保险费</th>
										<th data-options="field:'attr5',width:60,align:'center'">失业保险费</th>
										<th data-options="field:'attr6',width:60,align:'center'">住宿公积金</th>
										<th data-options="field:'attr7',width:60,align:'center'">财产原值</th>
										<th data-options="field:'attr8',width:60,align:'center'">允许扣除的税费</th>
										<th data-options="field:'attr9',width:60,align:'center'">其他</th>
										<th data-options="field:'attr10',width:50,align:'center'">合计</th>
										<th data-options="field:'attr11',width:50,align:'center'">减除费用</th>
										<th data-options="field:'attr12',width:50,align:'center'">准予扣除的捐赠额</th>
										<th data-options="field:'attr13',width:50,align:'center'">养老保险</th>
										<th data-options="field:'attr14',width:50,align:'center'">医疗保险</th>
										<th data-options="field:'attr14',width:50,align:'center'">失业保险</th>
										<th data-options="field:'attr14',width:50,align:'center'">工伤保险</th>
										<th data-options="field:'attr14',width:50,align:'center'">生育保险</th>
										<th data-options="field:'attr14',width:50,align:'center'">住房公积金</th>
														<th data-options="field:'attr14',width:50,align:'center'">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${TCustomerSalaryList}" var="tCustomerSalary" varStatus="status">
			<tr>
			<td>
					${status.count}
				</td>
			
				<td>
					${tCustomerSalary.customerId}
				</td>
				<td>
					${tCustomerSalary.salaryPeriod}
				</td>
				<td>
					<fmt:formatDate value="${tCustomerSalary.salaryDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tCustomerSalary.orderNumber}
				</td>
				<td>
					${tCustomerSalary.employeeName}
				</td>
				<td>
					${tCustomerSalary.identityType}
				</td>
				<td>
					${tCustomerSalary.idNumber}
				</td>
				<td>
					${tCustomerSalary.incomeProject}
				</td>
				<td>
					${tCustomerSalary.incomePeriod}
				</td>
				<td>
					${tCustomerSalary.incomeMoney}
				</td>
				<td>
					${tCustomerSalary.taxFreeIncome}
				</td>
				<td>
					${tCustomerSalary.endowmentInsurance}
				</td>
				<td>
					${tCustomerSalary.medicalInsurance}
				</td>
				<td>
					${tCustomerSalary.unemploymentInsurance}
				</td>
				<td>
					${tCustomerSalary.houseFund}
				</td>
				<td>
					${tCustomerSalary.originalValue}
				</td>
				<td>
					${tCustomerSalary.allowableDeductions}
				</td>
				<td>
					${tCustomerSalary.otherDeduction}
				</td>
				<td>
					${tCustomerSalary.totalDeduction}
				</td>
				<td>
					${tCustomerSalary.deductionExpenses}
				</td>
				<td>
					${tCustomerSalary.deductibleDonationAmount}
				</td>
				<td>
					${tCustomerSalary.unitEndowmentInsurance}
				</td>
				<td>
					${tCustomerSalary.unitMedicalInsurance}
				</td>
				<td>
					${tCustomerSalary.unitUnemploymentInsurance}
				</td>
				<td>
					${tCustomerSalary.unitInjuryInsurance}
				</td>
				<td>
					${tCustomerSalary.unitBirthInsurance}
				</td>
				<td>
					${tCustomerSalary.unitHouseFund}
				</td>
				<td>
    				<a href="${ctx}/workterrace/tCustomerSalary/form?id=${tCustomerSalary.id}">修改</a>
					<a href="${ctx}/workterrace/tCustomerSalary/delete?id=${tCustomerSalary.id}" onclick="return confirmx('确认要删除该工资吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
									<tr>
										<td><input type="text"></td>
										<td><input type="text"></td>
										<td><input type="text"></td>
										<td><input type="text"></td>
										<td><input type="text"></td>
									<td><input type="text"></td>
										<td><input type="text"></td>
										<td><input type="text"></td>
										<td><input type="text"></td>
										<td><input type="text"></td>
									<td><input type="text"></td>
										<td><input type="text"></td>
										<td><input type="text"></td>
										<td><input type="text"></td>
										<td><input type="text"></td>
									<td><input type="text"></td>
										<td><input type="text"></td>
										<td><input type="text"></td>
										<td><input type="text"></td>
										<td><input type="text"></td>
									<td><input type="text"></td>
										<td><input type="text"></td>
										<td><input type="text"></td>
										<td><input type="text"></td>
										<td><input type="text"></td>
									<td><input type="text"></td>
										<td><input type="text"></td>
										<td><input type="text"></td>
										<td><input type="text"></td>
									
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="hr10"></div>
				</div>
			</div>

			<div class="hr20"></div>
			<div class="footer_s3 text-center">版权所属 北京芸智慧财务有限公司    技术支持 麟腾传媒</div>


		</div>
	</div>
</div>
<script>

</script>
</body>
</html>