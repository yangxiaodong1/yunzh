<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>凭证录入-在线会计-芸豆会计</title>
<link href="${pageContext.request.contextPath}/static/css/billInfo.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/handlebar/handlebars-1.0.0.beta.6.js" type="text/javascript"></script>
</head>
<body>
<div class="content clearfix">
	<div class="c-right">
		<div class="th">
			<h4 class="h4-title">腾讯控股有限公司</h4>
			<div class="timeCont">
				<a href="javascript">切换</a>
				<span>2015年10月11日  第8期</span>
			</div>
			<div class="back clearfix">
				<em class="em-out">退出</em>
				<em class="em-backw">返回工作台</em>
			</div>
		</div>
		<div class="tb">
			<div class="parta">
				<div class="head">
					<em class="refresh"></em>
					<span class="s-sort">排序：<em>
						<select multiple size=1 style="width:100%;border:none">
							<option value="1">a</option>
							<option value="2">b</option>
							<option value="3">c</option>
							<option value="4">d</option>
							<option value="5">e</option>
							<option value="6">f</option>
						</select>
					</em></span>
					<span class="checkbox-show checkbox">显示已作废<input type="checkbox" id="checkboxshowInput">
						<label for="checkboxshowInput"></label>
					</span>
					<span class="checkbox-history checkbox">查看历史票据<input type="checkbox"  id="checkboxhisInput">
						<label for="checkboxhisInput"></label>
					</span>
					<ul class="ul-each">
						<li class="li-list">列表</li>
						<li class="li-img">头像</li>
					</ul>
				</div>
				<div class="bills">
					<c:if test="${showType eq '1'}">
						<ul id="image_show" class="ul-bill clearfix">
							<c:forEach  var="billinfo" items="${result}">
								<li>
									<div class="img-bill">
										<img src="${pageContext.request.contextPath}/static/billinfo/bill.png" width="214" height="143" alt="">
									</div>
									<div class="bill-txt">
										<p class="pget">
											<input type="checkbox" id="checkboxprinput1">
											<label for="checkboxprinput1"></label>${billinfo.abstractMsg}
										</p>
										<p class="p-price">
											票价合计：<em>${billinfo.totalPrice}</em> 
											<time><fmt:formatDate value="${billinfo.signDate}" pattern="yyyy-MM-dd" /> </time>
										</p>
									</div>
								</li>
							</c:forEach>
						</ul>
					</c:if>
					<c:if test="${showType eq '2'}">
						<div class="lay images clearfix">
						<div class="changes">
							<div class="th">选择</div>
							<div class="tb">
								<div class="line">
									<em><input id="ip1" class="ipo" type="checkbox"><label class="oth" for="ip1"></label></em>
									<em><input id="ip2" class="ipo" type="checkbox"><label class="oth" for="ip2"></label></em>
									<em><input id="ip3" class="ipo" type="checkbox"><label class="oth" for="ip3"></label></em>
									<input id="ip0" class="ipfir" type="checkbox"><label class="fir" for="ip0"></label>
								</div>
								<table width="93%" class="imgs-table"  cellpadding="0" cellspacing="0">
									<thead>
									    <tr>
									      <th width="5%">影像</th>
									      <th width="10%">类型</th>
									      <th width="12%">摘要</th>
									      <th width="17%">开票单位</th>
									      <th width="10%">开票日期</th>
									      <th width="9%">金额</th>
									      <th width="9%">税额</th>
									      <th width="9%">价格合计</th>
									      <th width="15%">操作</th>
									      <th width="4%">状态</th>
									    </tr>
									 </thead>
									 <tbody>
									 	<c:forEach  var="billinfoTotal" items="${result}">
									 		<tr>
										 	  <td><img src="${ctxStatic}/billinfo/camera.png" alt=""></td>
										 	  <td></td>
										 	  <td></td>
										 	  <td></td>
										 	  <td></td>
										 	  <td>${billinfoTotal.amountCount }</td>
										 	  <td>${billinfoTotal.taxCount }</td>
										 	  <td>${billinfoTotal.totalPriceCount }</td>
										 	  <td></td>
										 	  <td></td>
										 	</tr>	
										 	<c:forEach  var="billinfo" items="${billinfoTotal.billInfoList}">
											 	<tr>
											 	  <td><img src="${ctxStatic}/billinfo/camera.png" alt=""></td>
											 	  <td>${billinfo.abstractMsg}</td>
											 	  <td>${billinfo.abstractMsg}</td>
											 	  <td>${billinfo.payName}</td>
											 	  <td><fmt:formatDate value="${billinfo.signDate}" pattern="yyyy-MM-dd" /></td>
											 	  <td>${billinfo.amount}</td>
											 	  <td>${billinfo.tax}</td>
											 	  <td>${billinfo.totalPrice}</td>
											 	  <td><div class="sp-but clearfix"><span class="biaoc">标错</span><span class="feiq">废弃</span></div></td>
											 	  <td>
											 	  	<c:choose>
											 	  		<c:when test="${billinfo.urgentState eq 0}">加急</c:when>
											 	  		<c:otherwise>正常</c:otherwise>
											 	  	</c:choose>
											 	  </td>
											 	</tr>
									 		</c:forEach>
									 	</c:forEach>
									 </tbody>
								</table>
								
							</div>
						</div>
						
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
<script id="templateImg" type="text/x-handlebars-template">	
{{#result}}
	<li>
		<div class="img-bill">
			<img src="{{{img}}}" width="214" height="143" alt="">
		</div>
		<div class="bill-txt">
			<p class="pget"><input type="checkbox" id="checkboxprinput1"><label for="checkboxprinput1"></label> {{abstractMsg}}</p>
			<p class="p-price">票价合计：<em>{{totalPrice}}</em> <time><fmt:formatDate value="${signDate}" pattern="yyyy-MM-dd" /></time></p>
		</div>
	</li>
{{/result}}
</script>

<script type="text/javascript">
	 $(document).ready(function() {
		//刷新点击
		$("body").on("click",".refresh",function(){
			$.ajax( {    
			    url:'${ctx}/sys/user/billInfosAjax',// 跳转到 action    
			    data:{    
			    },    
			    type:'post',    
			    cache:false,    
			    dataType:'json',    
			    success:function(data) {    
	 				if(data.showType == '1'){
	 					
	 					var source = $("#templateImg").html();
	 					var template = Handlebars.compile(source);
	 					var result = template(data)
	 					$("#image_show").html(result);
	 				}
			     }  
			});
		})
		
	 });
</script>
</html>