
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">  
	.message-pop {top:130px;}
    .help-inline {color: red;}
</style> 
	<div class="displayCss" id="dialog_mon" title="收费" style="width:840px;">
	<div class="client_inner2">
		<div class="client_form_f2">
		
		
				
		<form:form id="inputForminsert" modelAttribute="tServeInfoinsert"  method="post">
	<span style="display: none">
	<form:input id="id" path="id" />
				</span>	
					
<form:hidden class="customerId" path="customerId"/>
				<div class="form-group">
						
					<label class="labelname5 left control-label">公司名称：</label>
					<div class="labelcon6 left">
						<span id="customerName1" class='com_name7'></span>
					</div>
					<label class="labelname7 control-label">签约日期：</label>
					<div class="right line_h26 w170">

					<input name="signDate" id="signDate" type="text" readonly="readonly" class="form-control ipt_w5" style="width:112px;float:left;" value="<fmt:formatDate value="${tServiceCharge.signDate}" pattern="yyyy-MM-dd"/>"/> 
						<span class="help-inline"></span>
						<div class="clearfix"></div>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="form-group">
				
				
					

					<label class="labelname5 left control-label">服务期限：</label>
					<div class="labelcon6 left">

						<form:select path="serviceDate1"  class="form-control ipt_w5 ipt_w60">
							
							<option value="2016">2016</option>
							<option value="2017">2017</option>
							<option value="2018">2018</option>
							<option value="2019">2019</option>
							<option value="2020">2020</option>
						</form:select>
						<span>年</span>
						<form:select path="serviceDate2" class="form-control ipt_w5 ipt_w40">
							<option value="01">01</option>
							<option value="02">02</option>
							<option value="03">03</option>
							<option value="04">04</option>
							<option value="05">05</option>
							<option value="06">06</option>
							<option value="07">07</option>
							<option value="08">08</option>
							<option value="09">09</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
						</form:select>
						<span>月</span>
						<span>~</span>
						<form:select path="serviceDate3" class="form-control ipt_w5 ipt_w60">
								
							<option value="2016">2016</option>
							<option value="2017">2017</option>
							<option value="2018">2018</option>
							<option value="2019">2019</option>
							<option value="2020">2020</option>
						</form:select>
						<span>年</span>
						<form:select path="serviceDate4" class="form-control ipt_w5 ipt_w40">
							<option value="01">01</option>
							<option value="02">02</option>
							<option value="03">03</option>
							<option value="04">04</option>
							<option value="05">05</option>
							<option value="06">06</option>
							<option value="07">07</option>
							<option value="08">08</option>
							<option value="09">09</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
						</form:select>
						<span>月</span>
					</div>
					<label class="labelname7 control-label ">付款方式：</label>
					<div class="right line_h26 w170">


					<form:select style="width:136px;float:left;" path="modePayment" class="form-control ipt_w5">
						<option value="0">按月收费</option>
						<option value="1">按季收费</option>
						<option value="2">按年收费</option>
					</form:select>
						</div>
					<div class='clearfix'></div>
				</div>

				<div class="form-group">
					<label class="labelname5 left control-label">代账收费（月）：</label>
					<div class="labelcon6 left">
						<div class="line_h26 w170">

					<form:input path="loanMoney" htmlEscape="false" class="form-control ipt_w5 ipt_b80 left mar-rig5"/>元<span class="help-inline"></span>
					</div></div>

					<label class="labelname7 control-label">账本收费（年）：</label>
					<div class="right line_h26 w170">
						<div class="line_h26 w170 mar-rig10">

					<form:input path="accountbookMoney" htmlEscape="false" class="form-control ipt_w5 ipt_b80 left mar-rig5"/>元<span class="help-inline"></span>
					</div></div>
<div class='clearfix'></div>
					

				</div>
				<div class='clearfix'></div>
				<div class="form-group">
					<label class="labelname5 left control-label">应收款：</label>
					<div class="labelcon6 left">
						<div class="line_h26 w170">
						<form:input path="shouldMoney" htmlEscape="false" class="form-control ipt_w5 ipt_b80"/>&nbsp;元<span class="help-inline"></span>
					</div></div>
					<label class="labelname7 control-label">实际收款：</label>
					<div class="right line_h26 w170">
						<div class="line_h26 w170">
						<form:input path="realityMoney" htmlEscape="false" class="form-control ipt_w5 ipt_b80"/>&nbsp;元<span class="help-inline"></span>
					</div></div><div class='clearfix'></div>
				</div>

				<div class="form-group">
					<label class="labelname5 left control-label">特殊事项说明：</label>
					<div class="labelcon8 left">
					<form:textarea path="remark" htmlEscape="false" maxlength="300" class="form-control textarea_n3" rows="3" style="width:100%;"/>
					</div>
					<div class='clearfix'></div>
				</div>

				
				<div class="form-group">				
					<span class="right">
  					<input type="button" value="确定"  onclick="savetServiceCharge()" class="btn btn-default btn_w_a btn_bg_2 mar-rig10"/>
					<input type="reset"  class="btn btn-default btn_w_a btn_bg_4" value="重置"/>
					</span>					
					
					<div class='clearfix'></div>
				</div>

			</form:form>
		</div>
	</div>
	<div class="pad_l_r15">
	
	<div class="tit_sc3 font-14 border_top1"><b>收费列表</b></div>
	
	
	
	<form:form id="searchFormtServiceCharge" modelAttribute="tServiceCharge" action="${ctx}/inspection/workstatistics/tServiceCharge/" method="post">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<form:hidden id="customerId1" path="customerId"/>
				</form:form>
	<table class="table table-bordered table_list4">
		<thead>
			<tr>
				<th>签约时间</th>
				<th>合同期限</th>
				<th>月服务费</th>
				<th>应收款</th>
				<th>实际付款</th>
				<th>付款方式</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="tServiceChargeList">
		

			
		</tbody>
	</table></div>
	<div name="searchFormtServiceCharge" class="pagination">${page}</div>
	<div class="pad_10_3">
	<!--  <div class="page_cc">共2条记录
			<button class="btn_p7"></button>
			<span class="pad_l_r5">1</span>
			<button class="btn_p8"></button>
		</div>-->	
	</div>
	<div id="dialog_sure" class="dialog_sure">
		<div class="dialog">保存成功!</div>
	</div>
	<div id="dialog_delect" class="dialog_sure">
		<div class="dialog">删除成功!</div>
	</div>
	<div id="dialog_error" class="dialog_sure">
		<div class="dialog">页面君有点累，刷新页面试试</div>
	</div>
</div>


<div class="displayCss" id="delectCustomer" title="系统提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center">您确定要删除该客户吗？</div>
		<div class="hr40"></div>
		<form>
		<div class="text-center">
			<input type="button" onclick="delectCustomers()" class="btn  btn-default btn_w_a btn_bg_2 mar-rig10" value="确认" />
			<input type="button"  class="btn delectCustomerClose btn_w_a btn_bg_4" value="取消" />
			
		</div>
		</form>
	</div>
</div>
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
<!--删除收费-->
<div id="dialog_del2" class="displayCss" title="系统提示">
	<div class="min-h200">
		<div class="hr40"></div>
		<div class="font-14 text-center">您确定要“删除”该条收费信息吗？</div>
		<div class="hr40"></div>
		<form>
		<div class="text-center">
		<input id="delectids" type="hidden"/>
			<input type="button" class="btn delecttServiceCharge btn-default btn_w_a btn_bg_2 mar-rig10" value="确认"/>
			<button class="btn dialog_close6 btn_w_a btn_bg_4">取消</button>
			
		</div>
		
		</form>
	</div>
</div>


<!-- Templates -->
<p style="display:none"><textarea id="template" >
<!--
{#foreach $T.list as tServiceCharge}
			<tr>
				<td>{$T.tServiceCharge.signDate}</td>
				<td>{$T.tServiceCharge.serviceDate}</td>
				<td>{$T.tServiceCharge.loanMoney}</td>
				<td>{$T.tServiceCharge.shouldMoney}</td>
				<td>{$T.tServiceCharge.realityMoney}</td>
				<td>
				
				{#if $T.tServiceCharge.modePayment == '0'}按月收费{#elseif $T.tServiceCharge.modePayment =='1'}按季收费{#elseif $T.tServiceCharge.modePayment == '2'}按年收费 {#/if}
				</td>
				<td>{#if $T.tServiceCharge.state=='1'}已审核{#else}未审核{#/if}</td>
				<td>
				{#if $T.tServiceCharge.state=='1'} 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a title="打印收据" class="btn btn-default btn_n4 bg_p6 btn_i3 pad_10" href="#"></a>
    		    {#else} 
    				<a title="编辑" href="javascript:void(0);" onclick="ShowDetails('{$T.tServiceCharge.id}')" class="btn btn-default btn_n4 bg_p6 btn_i2 pad_10 mar-top1 mar-rig5" ></a>
    				<a title="打印收据" onclick="stampShoujv('{$T.tServiceCharge.id}')" class="btn btn-default btn_n4 bg_p6 btn_i3 pad_10" href="javascript:void(0);"></a>
    				<a title="删除" onclick="delDetails('{$T.tServiceCharge.id}')" class="btn btn-default btn_n4 bg_p6 btn_i4 pad_10" href="javascript:void(0);"></a>	
    		    {#/if}
    				</td>
			</tr>
		   {#/for}
-->
</textarea></p>
 <!-- Output elements -->	

<script src="${pageContext.request.contextPath}/static/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/jquery/jquery-jtemplates.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/jquery-validation/1.11.1/jquery.validate.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/jquery-validation/1.11.1/localization/messages_zh.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/tServiceCharge.js"></script>

