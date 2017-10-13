<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta charset="utf-8" />
<title>财务初始余额表-在线会计-芸豆会计</title>
<meta name="keywords" content="关键字,关键字,关键字" />
<meta name="description" content="描述。……" />
<meta name="Author" content="author_bj designer_bj">
<link href="${ctxStatic}/accountAndbalance/css/reset.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/accountAndbalance/css/financialbalance.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/accountAndbalance/css/setCom.css" rel="stylesheet" type="text/css">
<link href="${ctxStatic}/accountAndbalance/css/dialog.css" rel="stylesheet" type="text/css">
<!-- 自动摊销 -->
<link href="${pageContext.request.contextPath}/static/css/autoAmortization.css" rel="stylesheet" type="text/css">


<script type="text/javascript" src="${ctxStatic}/accountAndbalance/js/jquery-1.11.2.min.js"></script>
<script  src="${ctxStatic}/jquery-formatMoney/jQuery.formatMoney.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/handlebar/handlebars-1.0.0.beta.6.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src='${pageContext.request.contextPath}/static/js/imagesLoaded.js'></script>
<link href="${pageContext.request.contextPath}/static/accountAndbalance/css/defaultTheme.css" rel="stylesheet" type="text/css">
<script src='${pageContext.request.contextPath}/static/accountAndbalance/js/jquery.fixedheadertable.js'></script>
<style>
.tp_panel *{margin:0;padding:0;font-family:Microsoft Yahei;}
.mar_auto{margin:0 auto;}
.tp_panel{width:254px;height:320px;position:relative;}
.pz{width:113px;min-height:33px;background:url(${ctxStatic}/accountAndbalance/images/images/tp_1.png) no-repeat center bottom;position:absolute;bottom:70px;z-index:2;padding-bottom:31px;}
.pz1{left:0;}
.pz2{right:0;}
.dz{width:190px;height:32px;background:url(${ctxStatic}/accountAndbalance/images/images/tp_2.png) no-repeat  center bottom;position:absolute;bottom:0;left:32px;z-index:2;}
.zj{width:156px;height:39px;background:url(${ctxStatic}/accountAndbalance/images/images/tp_6.png) no-repeat;position:absolute;bottom:32px;left:50px;z-index:1;}
.pointer{width:30px;height:68px;background:url(${ctxStatic}/accountAndbalance/images/images/tp_5.png) no-repeat;position:absolute;bottom:10px;left:114px;z-index:3;}
.pz .fm{width:108px;height:200px;background-repeat:no-repeat;background-size:cover;margin:0 auto;text-align:center;position:relative;}
.pz .fm div{position:absolute;bottom:0px;width:100%;text-align:center;word-wrap:break-word;line-height:1.5;color:#333;
display: box; 
display: -webkit-box; 
display: -moz-box; 
	-webkit-box-pack:center; 
	-moz-box-pack:center; 
	-webkit-box-align:center; 
	-moz-box-align:center; 
}
.pz .pzname{position:absolute;width:100%;height:33px;line-height:33px;color:#fff;bottom:0;text-align:center;}


.autoAmortization {
margin-top:-166px
}
.autoAmortization{height:400px;}
.ul-select-amortize{position: absolute;background: #fff;z-index: 11;width: 204px;height: 200px;overflow-y:auto;}
.ul-select-amortize li{width: 100%;height: 28px;line-height: 28px;text-align: left;float: left;text-indent: 2px;cursor: pointer;}
.ul-select-amortize li:hover{background: #ddd}
/*
.message-pop{width: 250px;height: 100px;opacity: 0;padding-top: 20px;position: fixed;bottom: -130px;right: -300px;border: 1px solid #aaa;font-family: "Microsoft Yahei";background: #fff;z-index:99;}
.message-pop i.i-mess{width: 18px;height: 18px;cursor: pointer;background: url(${ctxStatic}/images/close.png) top center no-repeat;position: absolute;top: 0;right: 2px;}
.message-pop h4{text-align: center;}
.message-pop p{padding-top: 30px;text-align: center;}
*/
.loading_animate{/*width:32px;height:32px;*/border-radius:5px;display:inline-block;line-height:40px;margin:0 auto;padding:20px 20px 20px 60px;background:#eeeeee url(${ctxStatic}/accountAndbalance/images/btn_loading.gif) no-repeat 20px center;}
*{font-size:12px;}

/**20160118**/
.tabpane-main{width:978px;margin:0 auto;position:relative;}
.account-wrapper{min-width:978px;width:978px;position:absolute;font-size:12px;padding:0;}
.account-wrapper *{font-size:12px;}
.table-account-wrapper tbody tr:nth-child(2n){background:#f3faff;}
.table-account-wrapper tbody tr:hover{background:#cff6fb;}
body{background:#eff1f3;}
.fixed_table{position:relative;width:960px;}
.fht-table-wrapper .fht-fixed-body .fht-tbody, .fht-table-wrapper .fht-tbody{overflow-x:hidden;}
.table-account-wrapper tbody tr td:first-child{padding-left:30px;text-align:left;}
.table-account-wrapper tbody tr td:nth-child(2){padding-left:16px;text-align:left;}
.account-wrapper .tb{min-width:978px;width:978px;box-sizing:border-box;overflow:hidden;}
.layerS,.layerB{z-index:102;}
.confirm{padding:0 10px;text-align:left;}
.account-wrapper .th-right{padding-top:15px;}

/*修改*/
.deleteSubject {display: none;height: 200px;}
.confirm {font-size: 14px;line-height: 30px;margin: 30px 0;}
.sure {background: #fba22c;float: left;}
.mask{z-index:1;}
.autoAmortization{z-index:3;}

/**20160201**/
.fht-table, .fht-table thead, .fht-table tfoot, .fht-table tbody, .fht-table tr, .fht-table th, .fht-table td {
  margin: 0;
  padding: 0;
  font-size: 100%;
  font: inherit;
  vertical-align: middle;
}
.table-financialbalance tbody tr td:last-child input{position:inherit;top:0;}
.i-new{position:inherit;float:right;}
#sending{filter:alpha(opacity=80);position:absolute; top:0px;left:0px;z-index:10;  width: 100%; height:   120px;visibility:hidden;}
.table-financialbalance tbody tr td:last-child input{text-align:right;padding-right:3px;}
.table-financialbalance tbody tr td input.ss1{text-align:center;}
</style>

</head>
<div class="mask"></div>
<div class="message-pop"><span>保存成功！</span></div>
<body style="overflow:hidden;">
<div   id= "sending" style="visibility:hidden;">
<TABLE  WIDTH=100%  BORDER=0  CELLSPACING=0 CELLPADDING=0 border=0> 
        <TR> 
                
               <td> 
               <table  WIDTH=100%   height=60   BORDER=0   CELLSPACING=0  CELLPADDING=0>
                    <tr> 
                            <td align="center"><div class='loading_animate'> 正在处理数据,   请稍候... </div></td>
                    </tr> 
               </table> 
               </td> 
               
        </tr> 
</table> 
</div>
	<form id="frominfo" action="${ctx}/balance/tBalance/save" method="post">
		<input type="hidden" id="info" name="info"/>
		<input type="hidden" id="nTop" name="nTop"/>
		<input type="hidden" id="nTop1" name="nTop1"/>
		<input type="hidden" id="selectnum" name="selectnum"/>
	</form>
	
	<div class='tabpane-main'>
	<div class="account-wrapper financialbalance" id="tab">
		<h2 class="h2-tit">财务初始余额</h2>
		<div class="th clearfix">
			<div class="th-left switch">
				<a href="javascript:;" ><i></i>资产</a>
				<a href="javascript:;"><i></i>负债 </a>
				<c:if test="${sessionCustomer.system==2}">
				<a href="javascript:;"><i></i>共同</a>
				</c:if>
				<a href="javascript:;"><i></i>权益</a>
				<a href="javascript:;"><i></i>成本</a>
				<a href="javascript:;"><i></i>损益</a>
			</div>
			<div class="th-right clearfix">
				<span class="trialbal">试算平衡</span>			
				<c:choose>
					<c:when test="${dq==applyPattern }">
						<!-- <span class="leadin">导入</span> -->
						<span onclick="baocun($(this))">保存</span>
					</c:when>
					<c:otherwise>
						<span onclick="derived()">导出</span>
					</c:otherwise>
				</c:choose>							
			</div>
		</div>
		<div class="tb">
			
			<div class='tab-cont'>
				<div class='fixed_table'>
					<table class="table-financialbalance table-account-wrapper" id='myTable01' cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th width="30%">科目编号</th>
								<th width="30%">科目名称</th>
								<th width="20%">方向</th>
								<th width="20%">期初余额</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${listBalan}" var="tbalance" varStatus="status">
								<c:if test="${tbalance.groupId==1 }">
									<tr id="td${tbalance.accuntId}" class="tr${tbalance.parentId.id}">
										<td class="td-new" Style="text-indent:${tbalance.level-1}em">
											<c:choose>
												<c:when test="${tbalance.level==1}">
													<i class="star">${tbalance.accuntId}</i>
													<c:if test="${tbalance.level!=4}">
													<i class="i-new" onclick="addaccout(${tbalance.id},${tbalance.detail},'${tbalance.accountName}')"></i>
													</c:if>	
												</c:when>
												<c:otherwise>
													${tbalance.accuntId}
													<c:if test="${tbalance.level!=4}">
													<i class="i-new" onclick="addaccout(${tbalance.id},${tbalance.detail},'${tbalance.accountName}')"></i>
													</c:if>	
												</c:otherwise>
											</c:choose>							
										
										</td>
										<td Style="text-indent:${tbalance.level-1}em">${tbalance.accountName}</td>
										<td>
											<c:if test="${tbalance.dc==1}">借</c:if>
											<c:if test="${tbalance.dc==-1}">贷</c:if>
											<input class="accountId" type="hidden" name="accountId" value="${tbalance.id}"/>
											<input class="detaile"   type="hidden" value="${tbalance.detail}"/>
											<input class="ids"  	  type="hidden" name="id" value="${tbalance.id1}"/>
						 					<input class="fdc"       type="hidden" name="fdc" value="${tbalance.dc}"/>
											<input class="fgroupId"       type="hidden"  value="${tbalance.groupId}"/>
											<input class="fifAmortize"       type="hidden"  value="${tbalance.ifAmortize}"/>
											<input class="faccountName"       type="hidden"  value="${tbalance.accountName}"/>
										</td>
										<td>
											<c:choose>
												<c:when test="${dq==applyPattern }">
													<c:choose>
													<c:when test="${tbalance.detail==1}">
														<input class="end" id="end${tbalance.id}" type="text" name="beginbalance" value="${tbalance.beginbalance}" onkeyup="keyUp($(this),${tbalance.ifAmortize},'${tbalance.id}','${tbalance.accuntId}')" onblur="moveOut($(this))" onfocus="unformatMoney($(this))"/>
																								
													</c:when>
													<c:otherwise>
														<input style="border:none;background:transparent;" class="end" id="end${tbalance.id}" type="text" name="beginbalance" value="${tbalance.beginbalance}" readonly="readonly"/>
													</c:otherwise>
													</c:choose>		
												</c:when>
												<c:otherwise>													
													<input style="border:none;background:transparent;" class="end" id="end${tbalance.id}" type="text" name="beginbalance" value="${tbalance.beginbalance}" readonly="readonly"/>
												</c:otherwise>
											</c:choose>																			
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			
			
				<div class='tab-cont'>
					<div class='fixed_table'>
					<table class="table-financialbalance table-account-wrapper" id='myTable02' cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th width="30%">科目编号</th>
								<th width="30%">科目名称</th>
								<th width="20%">方向</th>
								<th width="20%">期初余额</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${listBalan}" var="tbalance" varStatus="status">
								<c:if test="${tbalance.groupId==2 }">
									<tr id="td${tbalance.accuntId}" class="tr${tbalance.parentId.id} td${tbalance.accuntId}">
										<td class="td-new" Style="text-indent:${tbalance.level-1}em">
											<c:choose>
												<c:when test="${tbalance.level==1}">
													<i class="star">${tbalance.accuntId}</i>
													<c:if test="${tbalance.level!=4}">
													<i class="i-new" onclick="addaccout(${tbalance.id},${tbalance.detail},'${tbalance.accountName}')"></i>
													</c:if>	
												</c:when>
												<c:otherwise>
													${tbalance.accuntId}
													<c:if test="${tbalance.level!=4}">
													<i class="i-new" onclick="addaccout(${tbalance.id},${tbalance.detail},'${tbalance.accountName}')"></i>
													</c:if>	
												</c:otherwise>
											</c:choose>							
										
										</td>
										<td Style="text-indent:${tbalance.level-1}em">${tbalance.accountName}</td>
										<td>
											<c:if test="${tbalance.dc==1}">借</c:if>
											<c:if test="${tbalance.dc==-1}">贷</c:if>
											<input class="accountId" type="hidden" name="accountId" value="${tbalance.id}"/>
											<input class="detaile"   type="hidden" value="${tbalance.detail}"/>
											<input class="ids"  	  type="hidden" name="id" value="${tbalance.id1}"/>
						 					<input class="fdc"       type="hidden" name="fdc" value="${tbalance.dc}"/>
											<input class="fgroupId"       type="hidden" name="fdc" value="${tbalance.groupId}"/>
											<input class="fifAmortize"       type="hidden" name="ifAmortize" value="${tbalance.ifAmortize}"/>
											<input class="faccountName"       type="hidden" name="ifAmortize" value="${tbalance.accountName}"/>
										</td>
										<td>
											<c:choose>
												<c:when test="${dq==applyPattern }">
													<c:choose>
													<c:when test="${tbalance.detail==1}">
														<input  class="end" id="end${tbalance.id}" type="text" name="beginbalance" value="${tbalance.beginbalance}" onkeyup="keyUp($(this),${tbalance.ifAmortize},'${tbalance.id}','${tbalance.accuntId}')" onblur="moveOut($(this))" onfocus="unformatMoney($(this))"/>
													</c:when>
													<c:otherwise>
														<input style="border:none;background:transparent;" class="end" id="end${tbalance.id}" type="text" name="beginbalance" value="${tbalance.beginbalance}" readonly="readonly"/>
													</c:otherwise>
													</c:choose>		
												</c:when>
												<c:otherwise>
													<input style="border:none;background:transparent;" class="end" id="end${tbalance.id}" type="text" name="beginbalance" value="${tbalance.beginbalance}" readonly="readonly"/>
												</c:otherwise>
											</c:choose>											
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			
			<c:if test="${sessionCustomer.system==2}">
			<div class='tab-cont'>
					<div class='fixed_table'>
					<table class="table-financialbalance table-account-wrapper" id='myTable03' cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th width="30%">科目编号</th>
								<th width="30%">科目名称</th>
								<th width="20%">方向</th>
								<th width="20%">期初余额</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${listBalan}" var="tbalance" varStatus="status">
								<c:if test="${tbalance.groupId==7 }">
									<tr id="td${tbalance.accuntId}" class="tr${tbalance.parentId.id} td${tbalance.accuntId}">
										<td class="td-new" Style="text-indent:${tbalance.level-1}em">
											<c:choose>
												<c:when test="${tbalance.level==1}">
													<i class="star">${tbalance.accuntId}</i>
													<c:if test="${tbalance.level!=4}">
													<i class="i-new" onclick="addaccout(${tbalance.id},${tbalance.detail},'${tbalance.accountName}')"></i>
													</c:if>	
												</c:when>
												<c:otherwise>
													${tbalance.accuntId}
													<c:if test="${tbalance.level!=4}">
													<i class="i-new" onclick="addaccout(${tbalance.id},${tbalance.detail},'${tbalance.accountName}')"></i>
													</c:if>	
												</c:otherwise>
											</c:choose>							
										
										</td>
										<td Style="text-indent:${tbalance.level-1}em">${tbalance.accountName}</td>
										<td>
											<c:if test="${tbalance.dc==1}">借</c:if>
											<c:if test="${tbalance.dc==-1}">贷</c:if>
											<input class="accountId" type="hidden" name="accountId" value="${tbalance.id}"/>
											<input class="detaile"   type="hidden" value="${tbalance.detail}"/>
											<input class="ids"  	  type="hidden" name="id" value="${tbalance.id1}"/>
						 					<input class="fdc"       type="hidden" name="fdc" value="${tbalance.dc}"/>
											<input class="fgroupId"       type="hidden" name="fdc" value="${tbalance.groupId}"/>
											<input class="fifAmortize"       type="hidden" name="ifAmortize" value="${tbalance.ifAmortize}"/>
											<input class="faccountName"       type="hidden" name="ifAmortize" value="${tbalance.accountName}"/>
										</td>
										<td>
											<c:choose>
												<c:when test="${dq==applyPattern }">
													<c:choose>
													<c:when test="${tbalance.detail==1}">
														<input class="end" id="end${tbalance.id}" type="text" name="beginbalance" value="${tbalance.beginbalance}" onkeyup="keyUp($(this),${tbalance.ifAmortize},'${tbalance.id}','${tbalance.accuntId}')" onblur="moveOut($(this))" onfocus="unformatMoney($(this))"/>
													</c:when>
													<c:otherwise>
														<input style="border:none;background:transparent;" class="end" id="end${tbalance.id}" type="text" name="beginbalance" value="${tbalance.beginbalance}" readonly="readonly"/>
													</c:otherwise>
													</c:choose>		
												</c:when>
												<c:otherwise>
													<input style="border:none;background:transparent;" class="end" id="end${tbalance.id}" type="text" name="beginbalance" value="${tbalance.beginbalance}" readonly="readonly"/>
												</c:otherwise>
											</c:choose>										
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			</c:if>
			
				<div class='tab-cont'>
					<div class='fixed_table'>
					<table class="table-financialbalance table-account-wrapper" id='myTable04' cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th width="30%">科目编号</th>
								<th width="30%">科目名称</th>
								<th width="20%">方向</th>
								<th width="20%">期初余额</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${listBalan}" var="tbalance" varStatus="status">
								<c:if test="${tbalance.groupId==3 }">
									<tr id="td${tbalance.accuntId}" class="tr${tbalance.parentId.id} td${tbalance.accuntId}">
										<td class="td-new" Style="text-indent:${tbalance.level-1}em">
											<c:choose>
												<c:when test="${tbalance.level==1}">
													<i class="star">${tbalance.accuntId}</i>
													<c:if test="${tbalance.level!=4}">
													<i class="i-new" onclick="addaccout(${tbalance.id},${tbalance.detail},'${tbalance.accountName}')"></i>
													</c:if>	
												</c:when>
												<c:otherwise>
													${tbalance.accuntId}
													<c:if test="${tbalance.level!=4}">
													<i class="i-new" onclick="addaccout(${tbalance.id},${tbalance.detail},'${tbalance.accountName}')"></i>
													</c:if>	
												</c:otherwise>
											</c:choose>							
										
										</td>
										<td Style="text-indent:${tbalance.level-1}em">${tbalance.accountName}</td>
										<td>
											<c:if test="${tbalance.dc==1}">借</c:if>
											<c:if test="${tbalance.dc==-1}">贷</c:if>
											<input class="accountId" type="hidden" name="accountId" value="${tbalance.id}"/>
											<input class="detaile"   type="hidden" value="${tbalance.detail}"/>
											<input class="ids"  	  type="hidden" name="id" value="${tbalance.id1}"/>
						 					<input class="fdc"       type="hidden" name="fdc" value="${tbalance.dc}"/>
											<input class="fgroupId"       type="hidden" name="fdc" value="${tbalance.groupId}"/>
											<input class="fifAmortize"       type="hidden" name="ifAmortize" value="${tbalance.ifAmortize}"/>
											<input class="faccountName"       type="hidden" name="ifAmortize" value="${tbalance.accountName}"/>
										</td>
										<td>
											<c:choose>
												<c:when test="${dq==applyPattern }">
													<c:choose>
													<c:when test="${tbalance.detail==1}">
														<input class="end" id="end${tbalance.id}" type="text" name="beginbalance" value="${tbalance.beginbalance}" onkeyup="keyUp($(this),${tbalance.ifAmortize},'${tbalance.id}','${tbalance.accuntId}')" onblur="moveOut($(this))" onfocus="unformatMoney($(this))"/>
													</c:when>
													<c:otherwise>
														<input style="border:none;background:transparent;" class="end" id="end${tbalance.id}" type="text" name="beginbalance" value="${tbalance.beginbalance}" readonly="readonly"/>
													</c:otherwise>
													</c:choose>		
												</c:when>
												<c:otherwise>
													<input style="border:none;background:transparent;" class="end" id="end${tbalance.id}" type="text" name="beginbalance" value="${tbalance.beginbalance}" readonly="readonly"/>
												</c:otherwise>
											</c:choose>										
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			
			
				<div class='tab-cont'>
					<div class='fixed_table'>
					<table class="table-financialbalance table-account-wrapper" id='myTable05' cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th width="30%">科目编号</th>
								<th width="30%">科目名称</th>
								<th width="20%">方向</th>
								<th width="20%">期初余额</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${listBalan}" var="tbalance" varStatus="status">
								<c:if test="${tbalance.groupId==4 }">
									<tr id="td${tbalance.accuntId}" class="tr${tbalance.parentId.id} td${tbalance.accuntId}">
										<td class="td-new" Style="text-indent:${tbalance.level-1}em">
											<c:choose>
												<c:when test="${tbalance.level==1}">
													<i class="star">${tbalance.accuntId}</i>
													<c:if test="${tbalance.level!=4}">
													<i class="i-new" onclick="addaccout(${tbalance.id},${tbalance.detail},'${tbalance.accountName}')"></i>
													</c:if>	
												</c:when>
												<c:otherwise>
													${tbalance.accuntId}
													<c:if test="${tbalance.level!=4}">
													<i class="i-new" onclick="addaccout(${tbalance.id},${tbalance.detail},'${tbalance.accountName}')"></i>
													</c:if>	
												</c:otherwise>
											</c:choose>							
										
										</td>
										<td Style="text-indent:${tbalance.level-1}em">${tbalance.accountName}</td>
										<td>
											<c:if test="${tbalance.dc==1}">借</c:if>
											<c:if test="${tbalance.dc==-1}">贷</c:if>
											<input class="accountId" type="hidden" name="accountId" value="${tbalance.id}"/>
											<input class="detaile"   type="hidden" value="${tbalance.detail}"/>
											<input class="ids"  	  type="hidden" name="id" value="${tbalance.id1}"/>
						 					<input class="fdc"       type="hidden" name="fdc" value="${tbalance.dc}"/>
											<input class="fgroupId"       type="hidden" name="fdc" value="${tbalance.groupId}"/>
											<input class="fifAmortize"       type="hidden" name="ifAmortize" value="${tbalance.ifAmortize}"/>
											<input class="faccountName"       type="hidden" name="ifAmortize" value="${tbalance.accountName}"/>
										</td>
										<td>
											<c:choose>
												<c:when test="${dq==applyPattern }">
													<c:choose>
													<c:when test="${tbalance.detail==1}">
														<input class="end" id="end${tbalance.id}" type="text" name="beginbalance" value="${tbalance.beginbalance}" onkeyup="keyUp($(this),${tbalance.ifAmortize},'${tbalance.id}','${tbalance.accuntId}')" onblur="moveOut($(this))" onfocus="unformatMoney($(this))"/>
													</c:when>
													<c:otherwise>
														<input style="border:none;background:transparent;" class="end" id="end${tbalance.id}" type="text" name="beginbalance" value="${tbalance.beginbalance}" readonly="readonly"/>
													</c:otherwise>
													</c:choose>		
												</c:when>
												<c:otherwise>
													<input style="border:none;background:transparent;" class="end" id="end${tbalance.id}" type="text" name="beginbalance" value="${tbalance.beginbalance}" readonly="readonly"/>
												</c:otherwise>
											</c:choose>											
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			
			
				<div class='tab-cont'>
					<div class='fixed_table'>
					<table class="table-financialbalance table-account-wrapper" id='myTable06' cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th width="30%">科目编号</th>
								<th width="30%">科目名称</th>
								<th width="20%">方向</th>
								<th width="20%">实际损益发生额</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${listBalan}" var="tbalance" varStatus="status">
								<c:if test="${tbalance.groupId==5 }">
									<tr id="td${tbalance.accuntId}" class="tr${tbalance.parentId.id} td${tbalance.accuntId}">
										<td class="td-new" Style="text-indent:${tbalance.level-1}em">
											<c:choose>
												<c:when test="${tbalance.level==1}">
													<i class="star">${tbalance.accuntId}</i>
													<c:if test="${tbalance.level!=4}">
													<i class="i-new" onclick="addaccout(${tbalance.id},${tbalance.detail},'${tbalance.accountName}')"></i>
													</c:if>	
												</c:when>
												<c:otherwise>
													${tbalance.accuntId}
													<c:if test="${tbalance.level!=4}">
													<i class="i-new" onclick="addaccout(${tbalance.id},${tbalance.detail},'${tbalance.accountName}')"></i>
													</c:if>	
												</c:otherwise>
											</c:choose>							
										
										</td>
										<td Style="text-indent:${tbalance.level-1}em">${tbalance.accountName}</td>
										<td>
											<c:if test="${tbalance.dc==1}">借</c:if>
											<c:if test="${tbalance.dc==-1}">贷</c:if>
											<input class="accountId" type="hidden" name="accountId" value="${tbalance.id}"/>
											<input class="detaile"   type="hidden" value="${tbalance.detail}"/>
											<input class="ids"  	  type="hidden" name="id" value="${tbalance.id1}"/>
						 					<input class="fdc"       type="hidden" name="fdc" value="${tbalance.dc}"/>
											<input class="fgroupId"       type="hidden" name="fdc" value="${tbalance.groupId}"/>
											<input class="fifAmortize"       type="hidden" name="ifAmortize" value="${tbalance.ifAmortize}"/>
											<input class="faccountName"       type="hidden" name="ifAmortize" value="${tbalance.accountName}"/>
										</td>
										<td>
											<c:choose>
												<c:when test="${dq==applyPattern }">
													<c:choose>
													<c:when test="${tbalance.detail==1}">
														<input class="end" id="end${tbalance.id}" type="text" name="beginbalance" value="${tbalance.ytdamountfor}" onkeyup="keyUp($(this),${tbalance.ifAmortize},'${tbalance.id}','${tbalance.accuntId}')" onblur="moveOut($(this))" onfocus="unformatMoney($(this))"/>
													</c:when>
													<c:otherwise>
														<input style="border:none;background:transparent;" class="end" id="end${tbalance.id}" type="text" name="beginbalance" value="${tbalance.ytdamountfor}" readonly="readonly"/>
													</c:otherwise>
													</c:choose>		
												</c:when>
												<c:otherwise>
													<input style="border:none;background:transparent;" class="end" id="end${tbalance.id}" type="text" name="beginbalance" value="${tbalance.ytdamountfor}" readonly="readonly"/>
												</c:otherwise>
											</c:choose>										
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>					
					</table>
				</div>
			</div>
		</div>
		
		<!-- 导入数据弹窗 -->
		<div class="layer layerB inportData">
			<h6 class="h6-kr">导入数据<i class="i-close"></i></h6>
			<div class="cont clearfix">
				<div class="lay laya">
					<h4>模板下载</h4>
					<p>请下载统一的模板，并按照相应的模式</p>
					<p>填写您的业务数据并导入文件！</p>
					<a class="a-btnl btn" href="javascript:;">下载</a>
				</div>
				<div class="lay layb">
					<h4>导入文件</h4>
					<p>请选择你要导入的文件</p>
					<p class="clearfix"><input class="ipt-file" type="text"><input type="text" class="ipt-btn" value="选择"></p>
					<a class="a-btnl btn" href="javascript:;">导入</a>
				</div>
			</div>
			<div class="progress">
				<div class="rate"></div>
			</div>
		</div>
		<!-- 试算平衡弹窗 -->
		<div class="layer layerB trialBalance">
			<h6 class="h6-kr"><em class="em-ri">试算平衡</em><i class="i-close"></i></h6>
			<div class="cont">
				<p class="p-tit">期初余额</p>
				<div class="picture">
			
					<div class='pz pz1'>		
						<div class='fm'  attrnum='0'><div></div></div>
						<div class='pzname'>借</div>
					</div>
					<div class='pz pz2'>
						<div class='fm'  attrnum='0'><div></div></div>
						<div class='pzname'>贷</div>
					</div>
					<div class='pointer'></div>
					<div class='zj'></div>
					<div class='dz'></div>
					
				</div>				
			</div>
			<p>资产负载表<a class="a-bal" href="javascript:;"></a></p>
		</div>
		<!-- 票据跨期弹层 -->
		<div class="layer layerS newSubject" style="display:none">
			<h5 class="h6-kr">新增科目<i class="i-close"></i></h5>
			<div class="NewSubject-ptxt">
				<p>上级科目编码:<span id="accountid"></span></p>
				<p>上级科目名称:<span id="accountname"></span></p>
			</div>
			<ul class="ul-NewSubject clearfix">
				<li class="clearfix lifir"><label for=""></label><em>科目级次:4-2-2-2</em></li>
				<li class="clearfix"><label for="">编码长度</label><input type="text" id="accountidfor" class="inp-length" value="" readonly="readonly"></li>
				<li class="clearfix"><label for="">科目名称</label><span class="sp-time"></span><input id="accountnamefor" type="text" class="inp-name" ></li>
			</ul>
			<div class="button buttonW clearfix">
				<input type="hidden" id="fparentid"/>
				<input type="hidden" id="selectnum"/>
				<input type="hidden" id="endvalue"/>
				<button class="cancel">取消</button><button class="fsave accountSave">保存</button>
			</div>
		</div>
<!-- 设置自动摊销 -->
<div class="autoAmortization dialog" style="display:none;">
	<h6 class="h6-kr">设置自动摊销<i id="autoAmortization-close" class="bill-info-close-dialog"></i></h6>
	<table class="tab-autoAmortization" cellspacing="0" cellpadding="0">
		<tbody>
			<tr>
				<td width="173">摘要</td>
				<td width="249">会计科目</td>
				<td width="93">分摊月数</td>
				<td width="87">残值率</td>
				<td width="120">残值</td>
			</tr>
			<tr>
				<td><input class="ipt-summary" type="text" readonly="readonly"></td>
				<td>借<input class="ipt-subjext ipt-debit" type="text"></td>
				<td><input class="ipt-monthnum" type="text">月</td>
				<td><input class="ipt-value" type="text"></td>
				<td id="scrap-value">0</td>
			</tr>
			<tr>
				<td><input class="ipt-summary" type="text" readonly="readonly"></td>
				<td>贷<input class="ipt-subjext ipt-credit" type="text"></td>
				<td>
					<input type="hidden" id="beginNum"/>
					<input type="hidden" id="faccountid"/>
					<input type="hidden" id="creditAmortizeId"/>
					<input type="hidden" id="debitAmortizeId"/>
					<input type="hidden" id="creditAccountId"/>
					<input type="hidden" id="debitAccountId"/>
				</td>
				<td style="visibility: hidden;"></td>
				<td style="visibility: hidden;"></td>
			</tr>
		</tbody>
	</table>
	<div class="buttonW clearfix">
		<button id="autoAmortization-cancel" class="cancel">取消</button><button class="save autoAmortization-save">保存</button>
	</div>
</div>
<!-- 修改 -->
		<div class="layer layerS deleteSubject">
			<h5 class="h6-kr">系统提示<i class="i-close"></i></h5>
			<div class="confirm"></div>
			<div class="buttonW clearfix">
				<button class="sure">确定</button>
				<button class="fsave cancel_delete">取消</button>
			</div>
		</div>
<!--
<div class="message-pop">
	<i class="i-mess"></i>
	<h4>信息提示</h4>
	<p>保存成功！！！</p>
</div>
-->

<!-- 科目-->
<ul class="ul-select-amortize" style="display:none">
				
</ul>
	</div>
	</div>
<!--页面脚本区S-->
<!--所有页面用到的js脚本都必须放到此位置，包括外链js和内嵌js-->
<script>
$(".tb").height(function(){
	return parseInt($(window).height()-120);	
})

//科目信息
var accountArr;

var h_1=$(".tb").height();
$('.fixed_table').height(h_1);

var winh1=$("#myTable01").height();
if(winh1>h_1){
	$('#myTable01').fixedHeaderTable({ 
		altClass: 'odd',
		autoShow: false
	});
}
var winh2=$("#myTable02").height();
if(winh2>h_1){
	$('#myTable02').fixedHeaderTable({ 
		altClass: 'odd',
		autoShow: false
	});
}
var winh3=$("#myTable03").height();
if(winh3>h_1){
	$('#myTable03').fixedHeaderTable({ 
		altClass: 'odd',
		autoShow: false
	});
}
var winh4=$("#myTable04").height();
if(winh4>h_1){
	$('#myTable04').fixedHeaderTable({ 
		altClass: 'odd',
		autoShow: false
	});
}
var winh5=$("#myTable05").height();
if(winh5>h_1){
	$('#myTable05').fixedHeaderTable({ 
		altClass: 'odd',
		autoShow: false
	});
}
var winh6=$("#myTable06").height();
if(winh6>h_1){
	$('#myTable06').fixedHeaderTable({ 
		altClass: 'odd',
		autoShow: false
	});
}
$('.fixed_table .fht-default').show();
$("#sending").css({
	'top':240
})


//修改
$(".cancel_delete").bind("click",function(){
	$(".deleteSubject").hide();
	$(".mask").hide();
})


jQuery.extend({
    sw: function(id, event, tabActiveClass) {
        var _this = $("#" + id),
            _tabs = _this.find(".switch a"),
            _contents = _this.find(".tab-cont"),
            showOne = function(i){
        		$("#selectnum").prop("value",i);
                _tabs.removeClass(tabActiveClass);
                _tabs.eq(i).addClass(tabActiveClass);
                _contents.hide();
                _contents.eq(i).show();
            };
        showOne(${selectnum});
        _tabs.bind(event, function(e){
            e.preventDefault();
            var _curr_tab = $(this),
                _idx = _curr_tab.index();
            showOne(_idx);
        })
    }
});


$(function($){
    $.sw("tab", "click", "on");
})

$(function(){
	$(".leadin").bind("click",function(){
		$(".mask").show();
		$(".inportData").show();
	})
	$(".i-close").bind("click",function(){
		$(".mask").hide();
		$(".inportData").hide();
	})
	//天枰
	$(".trialbal").bind("click",function(){
		var jie=0;
		var dai=0;
		for(var i=0;i<${fn:length(listBalan)};i++){
			var detaile=$(".detaile:eq("+i+")").val();
			var fgroupId=$(".fgroupId:eq("+i+")").val();
			fgroupId=(""+fgroupId+"").substring(0,1);
			var fdc=$(".fdc:eq("+i+")").val();	
			var end=$(".end:eq("+i+")").val();
			if(end!=""){
				if(end!=undefined){
					var begin=$.unformatMoney(end);				
					if(detaile==1){						
						if(fgroupId==1 || fgroupId==2 || fgroupId==3 || fgroupId==7){							
							if(fdc==1){
								jie=accMul(jie,1)+accMul(begin,1);								
							}else if(fdc==-1){
								dai=accMul(dai,1)+accMul(begin,1);
							}
						}
					}		
				}
				
			}
						
									
		}	
		if(typeof(jie)!="undefined"){
			jie=$.formatMoney(jie, 2);
		}
		if(typeof(dai)!="undefined"){			
			dai=$.formatMoney(dai, 2);
		}
		$(".pz1 .fm").attr('attrnum',jie);
		$(".pz2 .fm").attr('attrnum',dai);		
		if(jie==dai){
			$(".a-bal").html("平衡");
		}else{
			$(".a-bal").html("不平衡");
		} 
		var vala=$('.pz1 .fm').attr('attrnum');
		var valb=$('.pz2 .fm').attr('attrnum');

		var pz1=parseFloat(vala);
		var pz2=parseFloat(valb);
		
		/*************/
		$('.pz1 .fm div').text(function(){
			return vala;
		});
		$('.pz2 .fm div').text(function(){
			return valb;
		});

		
		if(pz1>pz2){
			$('.pointer').css({
				'-webkit-transform': 'rotate(-30deg)',
				'transform': 'rotate(-30deg)'
			})
			$('.zj').css({
				'-webkit-transform': 'rotate(-10deg)',
				'transform': 'rotate(-10deg)'
			})
			$('.pz1').css({
				'bottom': 55
			})
		}else if(pz1<pz2){
			$('.pointer').css({
				'-webkit-transform': 'rotate(30deg)',
				'transform': 'rotate(30deg)'
			})
			$('.zj').css({
				'-webkit-transform': 'rotate(10deg)',
				'transform': 'rotate(10deg)'
			})
			$('.pz2').css({
				'bottom': 55
			})
		}else{
			$('.pointer').css({
				'-webkit-transform': 'rotate(0deg)',
				'transform': 'rotate(0deg)'
			})
			$('.zj').css({
				'-webkit-transform': 'rotate(0deg)',
				'transform': 'rotate(0deg)'
			})
			$('.pz1').css({
				'bottom': 70
			})
			$('.pz2').css({
				'bottom': 70
			})
		}
		$(".mask").show();
		$(".trialBalance").show();
	})
	
	$(".i-close").bind("click",function(){
		$(".mask").hide();
		$(".trialBalance").hide();
	})
	
/* 	$(".i-new").bind("click",function(){
		$(".mask").show();
		$(".newSubject").show();
	}) */
	//添加科目取消的时候刷新页面
	var isclick=0;
	$(".newSubject .i-close").bind("click",function(){		
		$(".mask").hide();
		$(".newSubject").hide();
		/* var timer = setTimeout(function() {
			var nTop = $(window).scrollTop();
			var nTop1=$(".tb").scrollTop();			
			var selectnum=$("#selectnum").val();			
			if(isclick==1){
				window.location="${ctx}/account/tAccount/balan?nTop="+nTop+"&nTop1="+nTop1+"&selectnum="+selectnum;
			}	
		},0);	 */		
		
	})
	//添加科目取消的时候刷新页面
	$(".cancel").bind("click",function(){
		$(".mask").hide();
		$(".newSubject").hide();
		/* var timer = setTimeout(function() {
			var nTop = $(window).scrollTop();
			var nTop1=$(".tb").scrollTop();
			var selectnum=$("#selectnum").val();			
			if(isclick==1){
				window.location="${ctx}/account/tAccount/balan?nTop="+nTop+"&nTop1="+nTop1+"&selectnum="+selectnum;
			}	
		},0);	 */
		
	})
	//添加科目
	$(".accountSave").bind("click",function(){
		var accountidfor=$("#accountidfor").val();		
		var accountname=$("#accountnamefor").val().trim();
		if(accountname.length>64){
			messagePop("科目名称太长,只能输入64个!");
			return;
		}
		var fparentid=$("#fparentid").val();	
		var endvalue=$("#endvalue").val();
		var accountid=$("#accountid").html();
		if(accountname!=""){
			$.ajax({  
		        type : "GET",  
		        contentType:'application/x-www-form-urlencoded; charset=UTF-8',
		        url : "${ctx}/account/tAccount/add",  
		        data : "id="+fparentid+"&accountid="+accountidfor+"&accountName="+encodeURI(encodeURI(accountname)),  
		        dataType: "json",  
		        success : function(msg) { 
		        	if(msg!=null){	
		        		var inew="";		  
		        		names="&apos;"+msg.accountName+"&apos;";		        			
		        		if(msg.level!=4){
		        			inew="<i class='i-new' onclick='addaccout("+msg.id+","+msg.detail+","+names+")'></i>";
		        		}		
		        		var dd="";
		        		if(typeof(msg.idFor)!="undefined"){
		        			dd=""+msg.idFor+"";
		        		}
		        
		        		var ffdc="借";
		        		if("-1"==msg.dc){
		        			ffdc="贷";
		        		}		        		
		        		var fendvalue="";
		        		if($(".tr"+fparentid+":last").val()==undefined){
		        			fendvalue=endvalue;
						}		   
		        		var group=msg.groupId;
		        		group=group.substr(0, 1);
		        		var levelem=msg.level*1-1;		        	
		        		var addHtml="<tr id='td"+msg.accuntId+"'class='tr"+msg.parentId.id+"'>"
						+"<td class='td-new' Style='text-indent:"+levelem+"em'>"+msg.accuntId+""													
						+""+inew+"</td>"
						+"<td Style='text-indent:"+levelem+"em'>"+msg.accountName+"</td>"
						+"<td>"
						+""+ffdc+""
						+"<input class='accountId' type='hidden' name='accountId' value='"+msg.id+"'/>"
						+"<input class='detaile'   type='hidden' value='"+msg.detail+"'/>"
						+"<input class='ids' 	  type='hidden' name='id' value='"+dd+"'/>"
						+"<input class='fdc'       type='hidden' name='fdc' value='"+msg.dc+"'/>"
						+"<input class='fgroupId'       type='hidden' name='fdc' value='"+group+"'/>"
						+"<input class='fifAmortize'       type='hidden' name='ifAmortize' value='"+msg.ifAmortize+"'/>"
						+"<input class='faccountName'       type='hidden' name='ifAmortize' value='"+msg.accountName+"'/>"
						+"</td>"
						+"<td>"
						+"<input class='end' id='end"+msg.id+"' type='text' name='beginbalance' value='"+fendvalue+"' onkeyup='keyUp($(this),"+msg.ifAmortize+","+msg.id+","+msg.accuntId+")' onblur='moveOut($(this))' onfocus='unformatMoney($(this))'/>"
						+"</td>"								
						+"</tr>";	
						if($("tr[id^=td"+accountid+"]:last").val()!=undefined){
							$("tr[id^=td"+accountid+"]:last").after(addHtml);
						}
					        		
						$("#td"+accountid).find(".end").prop("readonly","readonly");
						$("#td"+accountid).find(".end").prop("onkeyup","");
						$("#td"+accountid).find(".end").prop("onblur","");
						$("#td"+accountid).find(".end").prop("onfocus","");
						$("#td"+accountid).find(".end").attr("style","border:none;background:transparent;");
						if(${dq!=applyPattern}){
							$("#td"+msg.accuntId).find(".end").prop("readonly","readonly");
							$("#td"+msg.accuntId).find(".end").prop("onkeyup","");
							$("#td"+msg.accuntId).find(".end").prop("onblur","");
							$("#td"+msg.accuntId).find(".end").prop("onfocus","");		
							$("#td"+msg.accuntId).find(".end").attr("style","border:none;background:transparent;");
						}
						$("#td"+accountid).find(".detaile").prop("value","0");	
						if(${dq==applyPattern }){
							if(typeof(msg.ifAmortize)!="undefined"){						
			        			if(msg.ifAmortize=="1"){
			        				if(fparentid!=""){			        			
				        				$(".hide"+fparentid).remove();
				        				if(endvalue!=""){			
				        					if($(".tr"+fparentid).length==1){
				        						$("#end"+msg.id).after("<span class='hide"+msg.id+"'  Style='position: absolute;display:inline-block;left: 5px;line-height:20px;'><a onclick='amortizeclick("+msg.id+","+msg.accuntId+")'>自动摊销</a></span>");
				    						}			        					
				        				}			        				
			        				}		        				
			        			}
			        		}
						}						
		        		isclick=1;
		        		messagePop("添加成功!");
		        		/* $.ajax({  
	    			        type : "GET",  
	    			        url : "${ctx}/account/tAccount/parentInfo",  
	    			        data : "id="+fparentid,  
	    			        dataType: "json",  
	    			        success : function(msg) { 
	    			        	if(msg!=null){
	    			        		$("#accountid").html(msg.accuntId);
	    			        		$("#accountname").html(msg.accountName);	        			        		
	    			        		$("#fparentid").prop("value",fparentid);
	    			        		$("#accountidfor").prop("value",msg.accuntIdfor);
	    			        		$("#accountnamefor").prop("value",null);
	    			        		$(".mask").show();
	    			        		$(".newSubject").show();
	    			        	}
	    			        },error : function() {	        			        
	    			        	alert("失败");
	    			        } 
	    			    });  */
		        	}else{
		        		messagePop("添加失败!");
		        	}
		        },error : function() {	        			        
		        	messagePop("失败");
		        } 
		    }); 
		}		
		$(".mask").hide();
		$(".newSubject").hide(); 
	}) 
})

//添加的方法
function account(id){
	$.ajax({  
        type : "GET",  
        url : "${ctx}/account/tAccount/parentInfo",  
        data : "id="+id,  
        dataType: "json",  
        success : function(msg) { 
        	if(msg!=null){
        		$("#accountid").html(msg.accuntId);
        		$("#accountname").html(msg.accountName);	        			        		
        		$("#fparentid").prop("value",id);
        		$("#accountidfor").prop("value",msg.accuntIdfor);
        		$(".mask").show();
        		$(".newSubject").show();
        	}
        },error : function() {	        			        
        	alert("失败");
        } 
    }); 
}



//添加科目前的显示
var did;
function addaccout(id,detail,accountName){
	$("#accountnamefor").prop("value",null);
	did=id;
 	if(${dq!=applyPattern }){
		$("#end"+id).removeAttr("readonly");
	} 
	var obj=$("#end"+id).val();
	if(obj!=undefined){
		$("#endvalue").prop("value",obj);
	}	
	if(${dq!=applyPattern }){
		$("#end"+id).attr("readonly","readonly");
	}  
	if(detail==1){
		$.ajax({  
	        type : "GET",  
	        url : "${ctx}/balance/tBalance/parentdetail",  
	        data : "id="+id,  
	        dataType: "text",  
	        success : function(msg) {	      		
	        	if(msg>0){
	        		$(".deleteSubject").show();
	        		$(".mask").show();
	        		$(".confirm").text("您正在为科目 '' "+accountName+" '' 增加第一个下级科目， 系统将把该科目的数据全部转移到新增的下级科目中， 该流程不可逆，您是否要继续？？");
	        		/*  if(confirm("您正在为科目 '' "+accountName+" '' 增加第一个下级科目， 系统将把该科目的数据全部转移到新增的下级科目中， 该流程不可逆，您是否要继续？？")){
	        			 account(id);
	        		 }	 */        		
	        	}else{
	        		account(id);
	        	}
	        },error : function() {	        	
	        	alert("失败");
	        } 
	    }); 
	}else{
		account(id);
	}	
	
}

//取消继续添加科目
$(".i-close").bind("click",function(){
	$(".deleteSubject").hide();
	$(".mask").hide();
})

//如果数据库有值提醒
$(".sure").on("click",function(event){	
	account(did);	
$(".deleteSubject").hide();
$(".mask").hide();
});

//批量保存
function baocun(obj){	
	var arg = arguments;
	if(!arg.callee.open){
		arg.callee.open = true;
	
			var timer = setTimeout(function() {
				var jie=0;
				var dai=0;
				//判断是否平衡
				for(var i=0;i<${fn:length(listBalan)};i++){
					var detaile=$(".detaile:eq("+i+")").val();
					var fgroupId=$(".fgroupId:eq("+i+")").val();
					fgroupId=(""+fgroupId+"").substring(0,1);
					var fdc=$(".fdc:eq("+i+")").val();	
					var end=$(".end:eq("+i+")").val();
					var begin="";
					if(end!=""){
						if(end!=undefined){			
							if(detaile==1){
								if(fgroupId==1 || fgroupId==2 || fgroupId==3 || fgroupId==7 ){
									begin=$.unformatMoney(end);
									
									if(fdc==1){
										jie=jie*1+begin*1;
									}else if(fdc==-1){
										dai=dai*1+begin*1;
									}
								}
							}
						}
					}
												
				}		
				if(typeof(jie)!="undefined"){
					jie=$.formatMoney(jie, 2);
				}
				if(typeof(dai)!="undefined"){			
					dai=$.formatMoney(dai, 2);
				}
				if(jie!=dai){	
					arg.callee.open = false;
					messagePop("亲，财务初始余额借与贷不平衡，请核对后再保存！");
				}
					var nTop = $(window).scrollTop();
					var nTop1=$(".fht-tbody").scrollTop();
					var selectnum=$("#selectnum").val();			
					var result="";
					for(var i=0;i<=${fn:length(listBalan)};i++){
						var detaile=$(".detaile:eq("+i+")").val();
						var accountId=$(".accountId:eq("+i+")").val();						
						var fifAmortize=$(".fifAmortize:eq("+i+")").val();
						var faccountName=$(".faccountName:eq("+i+")").val();
						var end=$(".end:eq("+i+")").val();
						var beginbalance="";
						if(end!="" && end!=undefined){						
							beginbalance=$.unformatMoney(end);//去掉逗号
						}
						if(detaile!=0){
							
							//判断是否设置了摊销						
							if(fifAmortize==1){						
								if(beginbalance!="" && beginbalance!=null){
									var isSuccess=0;
									$.ajax({  
								        type : "GET",  
								        url : "${ctx}/balance/tBalance/infoamortize",  
								        data : "id="+accountId,
								        async: false,
								        dataType: "json",  
								        success : function(msg) { 
								        	if(msg!=null){ 
								        		if(msg[0]['originalValue']!=beginbalance){
								        			isSuccess=1;							        			
								        		}
								        	}
								        },error : function() {      	
								        	isSuccess=2;							        	
								        } 
								    });			
								
									if(isSuccess==1){
										messagePop("亲，'' "+faccountName+" ''设置的摊销值和上次不一样，请保存摊销！");
										arg.callee.open = false;
										return;
									}
									if(isSuccess==2){
										messagePop("亲，你还没有给'' "+faccountName+" ''设置自动摊销呢！");
										arg.callee.open = false;
										return;
									}
								}
								
							}
						}
					
						var ids=$(".ids:eq("+i+")").val();
						var fdc=$(".fdc:eq("+i+")").val();
						var fgroupId=$(".fgroupId:eq("+i+")").val();
						if(detaile!=0){
							if(ids!=null && ids!="" || beginbalance!=null && beginbalance!=""){
								result+="{'accountId' : '" +accountId+"','id1':'"+ids+"','beginbalance':'"+beginbalance+"','fgroupId':'"+fgroupId+"','fdc':'"+fdc+"'},"; 
							}
						}								
					}
					result="["+result.substring(0,result.length-1)+"]";
					$("#info").prop("value",result);
					$("#nTop").prop("value",nTop);
					$("#nTop1").prop("value",nTop1);
					$("#selectnum").prop("value",selectnum);
					$("#frominfo").submit();
					sending.style.visibility="visible";
					/* window.location="${ctx}/balance/tBalance/save?info="+result+"&nTop="+nTop+"&nTop1="+nTop1+"&selectnum="+selectnum; */
						
				
				
			},0);
			
		}
		
	}

/* function panduan(obj){
		var objVal =obj.val();
		var reg = /^(([+-]?[0-9]|([+-]?[0-9][0-9]{0,9}))((\.[0-9]*)?))$/;
		if(!reg.test(objVal)){
			obj.prop("value",null);
		}else{
			var num = new Number(objVal);
			obj.prop("value",num.toFixed(2));
		} 
	} */
	

/* $("body").on("click",".bill-info-close-dialog,.cancel",function(){
	$(".dialog").hide();
	$(".mask").hide();
}) */
	//文本框录入数据是的判断
	function keyUp(obj,ifAmortize,id,accountid,event) {
		var event = event || window.event;		
		if(ifAmortize==1){
			if(obj.val() != "") {	
				if($(".hide"+id+"").val()!="underfind"){
					$(".hide"+id+"").remove();
				}
				obj.after("<span class='hide"+id+"'  Style='position: absolute;display:inline-block;left: 5px;line-height:20px;'><a onclick='amortizeclick("+id+","+accountid+")'>自动摊销</a></span>");
			}else {
				$(".hide"+id+"").remove();				
			}
		}		
		
		obj.val(obj.val().replace(/[^\d.-]/g, "").replace(/\-{2,}/g, "").
		replace(/^\-{2,}/g, "-").
		//只允许一个小数点 
		replace(/^\./g, "").replace(/\.{2,}/g, ".").
		//只能输入小数点后两位
		replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").
		replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));
	}

	//离开文本框的时候
	function moveOut(obj){		
		obj.removeClass("ss1");
		//最后一位是小数点的话，移除
		obj.val((obj.val().replace(/\.$/g, "")));
		var objVal =obj.val();
		if(objVal.indexOf(".")>0){
			if(objVal.length>13){
				obj.prop("value",null);
			}
		}else{
			if(objVal.length>=10){
				obj.prop("value",null);
			}
		}
		var reg = /^(([+-]?[0-9]|([+-]?[0-9][0-9]{0,9}))((\.[0-9]*)?))$/;
		if(!reg.test(objVal)){
			obj.prop("value",null);
		}else{
			if(objVal==0){
				obj.prop("value",null);
			}else{
				var num = new Number(objVal);
				obj.prop("value",num.toFixed(2));
			}			
		} 
		
		if(obj.val()!=""){
			obj.val($.formatMoney(obj.val(), 2));
		}	
		
	};
function unformatMoney(obj){
	obj.addClass("ss1");	
	if(obj.val()!=""){
			obj.val($.unformatMoney(obj.val()));
		}	
	}
	//导出
function derived(){
	window.location="${ctx}/account/tAccount/downloadBalance";
}
//当页面加载的时候显示
$(function() {	
	var timer = setTimeout(function() {
		$(".fht-tbody").scrollTop(${nTop1});
		if('${message}'!=''){
			messagePop('${message}');
		}
	},0)   
})


/*  function tanxiao(){			
	$(".dialog").show();
	$(".mask").show();
}  
 */
 //提示信息
 var messagePop = function(str){
		$(".message-pop").fadeIn();
		$(".message-pop").children("span").html(str);
		setTimeout(function(){
			$(".message-pop").fadeOut();
		},2000)
}
//科目-自动摊销
function amortizeclick(id,accountid){	
	/* var end=$(bofer).prev().length; */
	/* $("#sending").css('visibility', 'visible').attr('ishidden',''); */
	$(".autoAmortization .ipt-value").val('');
	$(".autoAmortization #scrap-value").text('');
	$(".autoAmortization .ipt-monthnum").val('');
	$(".autoAmortization .ipt-debit").val('');
	$(".autoAmortization .ipt-credit").val('');
	$("#creditAccountId").val('');
	$("#debitAccountId").val('');
	$("#faccountid").val('');
	$.ajax({  
        type : "GET",  
        url : "${ctx}/balance/tBalance/infoamortize",  
        data : "id="+id,  
        dataType: "json",  
        success : function(msg) { 
        	if(msg!=null){ 
        		if(msg[0]['dc']==1){
        			$(".autoAmortization .ipt-debit").prop("value",msg[0]['accuntId']+" "+msg[0]['accountName']);
        			$("#debitAmortizeId").prop("value",msg[0]['id']);
        			$("#debitAccountId").prop("value",msg[0]['accountId']);
        		}else{
        			$(".autoAmortization .ipt-credit").prop("value",msg[0]['accuntId']+" "+msg[0]['accountName']);
        			$("#creditAmortizeId").prop("value",msg[0]['id']);
        			$("#creditAccountId").prop("value",msg[0]['accountId']);        			
        		}       
        		if(msg[1]['dc']==1){
        			$(".autoAmortization .ipt-debit").prop("value",msg[1]['accuntId']+" "+msg[1]['accountName']);
        			$("#debitAmortizeId").prop("value",msg[1]['id']);
        			$("#debitAccountId").prop("value",msg[1]['accountId']);        	
        		}else{
        			$(".autoAmortization .ipt-credit").prop("value",msg[1]['accuntId']+" "+msg[1]['accountName']);
        			$("#creditAmortizeId").prop("value",msg[1]['id']);
        			$("#creditAccountId").prop("value",msg[1]['accountId']);
        		}         		
        		
        		$(".autoAmortization .ipt-monthnum").prop("value",msg[0]['amortizeAgeLimit']);
				$(".autoAmortization .ipt-value").prop("value",msg[0]['scrapValueRate']);
			
				var value = msg[0]['scrapValueRate'];
				/* var end=$(".end:eq("+count+")").val(); */
				var end=$("#end"+id).val();
				if(typeof(end)!="undefined"){					
					end=$.unformatMoney(end);
				}
				var num = new Number(end);
				var amount = toMoneyNum(num.toFixed(2)); 
					
				amount = accMul(amount,value);
				$(".autoAmortization #scrap-value").text(accDiv(amount,100));
				
				
				/* $(".autoAmortization #scrap-value").text(msg[0]['scrapValue']); */
        		/* $.each(msg,function(i,result){         				
        				
        			}); */ 
        	}
        },error : function() {	
        	$("#debitAmortizeId").prop("value","");
        	$("#creditAmortizeId").prop("value","");        	
        } 
    }); 
	
	var summary = "";
	if((""+accountid+"").length>4){
		summary=(""+accountid+"").substring(0,4);
	}else{
		summary=accountid;		
	}
	if(summary==1601){
		summary="计提折旧";
	}
	if(summary==1701){
		summary="无形资产摊销";
	}
	if(summary==1801){
		summary="长期待摊费用摊销";
	}
	var end=$("#end"+id).val();
	if(typeof(end)!="undefined"){
		$("#beginNum").attr("value",$.unformatMoney(end));
	}	
	$("#faccountid").attr("value",id);
	//赋值自动摊销摘要
	$(".autoAmortization").find(".ipt-summary").val(summary);
	$(".ul-select-amortize").hide();
	
	$(".autoAmortization").show();
	$(".mask").show();
	//加载科目信息
	$.ajax({
	     type: 'POST',
	     url: '${ctx}/account/tAccount/getAccountByCodeOrName',
	     cache:false,  
	     async: false,
	     dataType:'json',
	     data: {
	    	 'param' :''
	     } ,
	     success: function(data){
	    	 accountArr = data;  	 
	     }
	}); 
	
}

//加载摊销
function delaydiv(){
	$("#sending").css('visibility', 'hidden').attr('ishidden','1');
	$(".autoAmortization").show();
	$(".mask").show();
}
//科目-自动摊销-选择科目
	$(".ipt-subjext").on("click",function(event){	
		event.stopPropagation();
		$(".autoAmortization").find(".cellOn-amortize").removeClass("cellOn-amortize");
		$(this).find("input").focus();
		$(this).addClass("cellOn-amortize");
		var clickTop = $(this).offset().top;
		var clickLeft = $(this).offset().left;
		var addTop = clickTop+34;	
		var addLeft = clickLeft;
		var addcss = {
					top: addTop+"px",
					left: 304+"px"
					};
		$(".ul-select-amortize").css(addcss);		
		/* $.ajax({
	 	     type: 'POST',
	 	     url: '${ctx}/account/tAccount/getAccountByCodeOrName',
	 	     cache:false,  
	 	     async: false,
	 	     dataType:'json',
	 	     data: {
	 	    	 'param' :''
	 	     } ,
	 	     success: function(data){
	 	    	 
	 	    	 var result = '';	 	    	
	 	    	 var source = $("#accountTemplate").html();	 	    	
	 	    	 var template = Handlebars.compile(source);	 	    	
	 	    	 result= template(data);	 	    	
	 	    	$(".ul-select-amortize").html(result);
	 	     }
		}); */
		var result = '';
    	var source = $("#accountTemplate").html();
    	var template = Handlebars.compile(source);
    	result = template(accountArr);
    	$(".ul-select-amortize").html(result); 
		$(".ul-select-amortize").show();		
	})
	
	//科目-自动摊销-选择科目-科目输入值变化
	var changeAjaxFlagAmortize = false;
	$("body").on("input propertychange",".ipt-subjext",function(){
		if(changeAjaxFlagAmortize){
			return;
		}
		changeAjaxFlagAmortize = true;
		var param = $(this).val();		
		/* $.ajax({
	 	     type: 'POST',
	 	     url: '${ctx}/account/tAccount/getAccountByCodeOrName',
	 	     cache:false,  
	 	     async: false,
	 	     dataType:'json',
	 	     data: {
	 	    	 'param' :param
	 	     } ,
	 	     success: function(data){
	 	    	 var result = '';
	 	    	 var source = $("#accountTemplate").html();
	 	    	 var template = Handlebars.compile(source);
	 	    	 result= template(data);	 	    	
	 	    	$(".ul-select-amortize").html(result);
	 	    	changeAjaxFlagAmortize = false;
	 	     }
		}); */
		var param = $(this).val().trim();
		
		var filterAccountArr = new Array();
		for(var i in accountArr){
			if(accountArr[i].accountName.indexOf(param) == 0 || accountArr[i].accuntId.indexOf(param) == 0 || accountArr[i].initName.indexOf(param) == 0 || accountArr[i].initNameParent.indexOf(param)== 0){
				filterAccountArr.push(accountArr[i]);
			}
		}
		
		var result = '';
	    var source = $("#accountTemplate").html();
	    var template = Handlebars.compile(source);
	    result= template(filterAccountArr);
	    $(".ul-select-amortize").html(result);
	    changeAjaxFlagAmortize = false;
	})
	//科目-自动摊销-选择科目-选中
	$("body").on("click",".ul-select-amortize li",function(){
		var _liVal = $(this).text();
		var accountid = $(this).data("id")
		$(".autoAmortization").find(".cellOn-amortize").data("accountid",accountid).val(_liVal).removeClass("cellOn-amortize");
		$(".ul-select-amortize").hide();
	})
	
	/**
 * 数字字符串  转换为带两位小数的数据
 * @param str
 * @returns {String}
 */
function toMoneyNum(str){
	if(str.length > 0){
		return str.substr(0,str.length-2) + "." +str.substr(str.length-2,str.length) ;
	}else{
		return "";
	}
}

//乘法函数，用来得到精确的乘法结果
//说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
//调用：accMul(arg1,arg2)
//返回值：arg1乘以 arg2的精确结果
function accMul(arg1,arg2){
	var m=0,s1=arg1.toString(),s2=arg2.toString();
	try{m+=s1.split(".")[1].length}catch(e){}
	try{m+=s2.split(".")[1].length}catch(e){}
	return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}
//除法函数，用来得到精确的除法结果
//说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
//调用：accDiv(arg1,arg2)
//返回值：arg1除以arg2的精确结果
function accDiv(arg1,arg2){
	var t1=0,t2=0,r1,r2;
	try{t1=arg1.toString().split(".")[1].length}catch(e){}
	try{t2=arg2.toString().split(".")[1].length}catch(e){}
	with(Math){
		r1=Number(arg1.toString().replace(".",""))
		r2=Number(arg2.toString().replace(".",""))
		return (r1/r2)*pow(10,t2-t1);
	}
}
	//科目-自动摊销-分摊月数和残值率值变化
	$("body").on("input propertychange",".autoAmortization .ipt-monthnum ,.autoAmortization .ipt-value",function(){
		//var month = parseInt($(".autoAmortization .ipt-monthnum").val());
		var value = parseInt($(".autoAmortization .ipt-value").val());
		if(!isNaN(value)){			
			var num = new Number($("#beginNum").val());
			var amount = toMoneyNum(num.toFixed(2)); 
			
			amount = accMul(amount,value);
			$("#scrap-value").text(accDiv(amount,100));
		}
	})
	
	//科目-自动摊销-保存
	$("body").on("click",".autoAmortization-save",function(){
		
		var amortizeAccountId=$("#faccountid").val();
		var  fcreditAccountId=$(".autoAmortization .ipt-credit").data("accountid");
		var  fdebitAccountId=$(".autoAmortization .ipt-debit").data("accountid");
		var debitAccountId="";
		var creditAccountId="";
		if(fcreditAccountId==null || fcreditAccountId==""){
			debitAccountId = $("#debitAccountId").val();
			creditAccountId = $("#creditAccountId").val();
		}else{
			debitAccountId=fdebitAccountId;
			creditAccountId=fcreditAccountId;
		}
		
		var	expName = $(".autoAmortization .ipt-summary").val(); // 名字
		var	originalValue = $("#beginNum").val(); // 原值
		var	scrapValueRate = $(".autoAmortization .ipt-value").val();  // 残值率
		var	scrapValue = $(".autoAmortization #scrap-value").text(); // 残值
		var	amortizeAgeLimit = $(".autoAmortization .ipt-monthnum").val(); // 摊销年限
		var creditAmortizeId= $("#creditAmortizeId").val();//贷方的id
		var debitAmortizeId=$("#debitAmortizeId").val();//借方的id
			
		if( $(".autoAmortization .ipt-debit").val().length < 1 || $(".autoAmortization .ipt-credit").val().length < 1){
			messagePop("请选择科目");
			return false;
		}
		
		if(amortizeAgeLimit.length < 1){
			messagePop("请输入摊销年限");
			return false;
		}
		
		if(scrapValueRate.length < 1){
			messagePop("请输入残值率");
			return false;
		}		
		
		$(".voucher-current").find(".amortize").addClass("hasSave");		
		
		
		$.ajax({
		     type: 'POST',
		     url: "${ctx}/balance/tBalance/saveVoucher",
		     cache:false,  
		     dataType:'json',
		     data: {
		    	'debitAmortizeId':debitAmortizeId,
		    	'creditAmortizeId':creditAmortizeId,
		    	'amortizeAccountId':amortizeAccountId,
		    	'expName':expName,	
		 	    'originalValue':originalValue,		
		 	    'scrapValueRate':scrapValueRate	,
		 	    'scrapValue':scrapValue,
		 	    'amortizeAgeLimit':	amortizeAgeLimit,
		 	    'debitAccountId':debitAccountId,
				'creditAccountId' :creditAccountId
		     } ,
		     success: function(data){
		    	if(data.suc){
		    		messagePop("摊销设置成功！");
		    		$(".autoAmortization .ipt-value").val('');
		    		$(".autoAmortization #scrap-value").text('');
		    		$(".autoAmortization .ipt-monthnum").val('');
		    		$(".autoAmortization .ipt-debit").val('');
		    		$(".autoAmortization .ipt-credit").val('');	  
		    		$("#creditAccountId").val('');
		    		$("#debitAccountId").val('');
		    		$("#faccountid").val('');
		    		
		    	}else{
		    		messagePop("摊销设置失败！");
		    	}		    	
		     }

		});
		
		$(".autoAmortization").hide();
		$(".mask").hide();
	})	
	//科目-自动摊销-关闭
	$("body").on("click","#autoAmortization-close,#autoAmortization-cancel",function(event){
		$(".autoAmortization").hide();
		$(".mask").hide();
		event.stopPropagation();
		return false;
	})
	//在选择科目时 点击其他地方 则科目下拉隐藏
	$("body").click(function(){
		$(".pull-down").hide();
		//$(".ul-select").hide();
		$(".cellOn").find("input").hide();
		$(".ul-select-amortize").hide();
		$(".autoAmortization").find(".cellOn-amortize").removeClass("cellOn-amortize");
	})



</script>
<!--页面脚本区E-->
<script id="accountTemplate" type="text/x-handlebars-template">	
{{#each this}}
	<li data-id="{{id}}" data-dc="{{dc}}" data-ifamortize="{{ifAmortize}}">{{accuntId}}&nbsp;{{parentName}}{{accountName}}</li>
{{/each}}
</script>	
</body>
</html>
