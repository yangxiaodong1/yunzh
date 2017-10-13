<!doctype html>
<html lang="en">
<head>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
	<title>工作量汇总明细表-工作统计-芸豆会计</title>
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.11.2.js" type="text/javascript"></script>
	<script src="${ctxStatic}/fixedheadertable/jquery.fixedheadertable.js"></script>
	<link href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/zTreeStyle/zTreeStyle.css" type="text/css">
	<link href="${pageContext.request.contextPath}/static/css/master_2.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
	<link href="${pageContext.request.contextPath}/static/bootstrap/css/page.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/fixedheadertable/defaultTheme.css" rel="stylesheet" media="screen" />
</head>
<body >
	<div class="main_index3">
		<div class="pane_main5">
			<div class="t_pane_a1 radius_5">
				<div class="user_pane_r2">
					<div class="hr20"></div>
					<ul class="nav nav-tabs" role="tablist">
						<li class="active"><a href="${ctx}/inspection/workstatistics/tWorkGather/">工作量汇总表</a></li>
		<li><a href="${ctx}/inspection/workstatistics/tWorkInfo/">工作量明细表</a></li>
	<li><a href="${ctx}/inspection/workstatistics/tServeInfo/">服务收费报表</a></li>
	<li><a href="${ctx}/inspection/workstatistics/tServiceCharge/">收费审核</a></li>
		</ul>
					<div class="tab-content main5_con radius_5">
						<div class="tab-pane active" id="tab_f1">
							<div class="hr20"></div>
							
							<div class="pane_gzt">
								<div class="search_form4">
								
								<form:form id="searchForm" modelAttribute="tWorkGatherInfo" action="${ctx}/inspection/workstatistics/tWorkGatherInfo/list/" method="post">
		<span class="span_year left">
		<form:hidden path="byYear"/>
				<input type="button" class="bg_p6 btn_sea1 left" onclick="up()"  value="左"/>
					<form:input class="form-control-static ipt_w5 left"  readonly="true" path="byYearName" htmlEscape="false"/>
					<input type="button" class="bg_p6 btn_sea2 left mar-rig3" onclick="down()" value="右"/>
		</span>
		<%-- <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/> --%>
		
				<form:input path="customerName" class="form-control ipt_w5 ipt_txt_s1 mar-rig3 left" htmlEscape="false" maxlength="100" />
			<input id="btnSubmit" class="btn btn-default btn_w_a btn_bg_2 radius0 left" type="submit" value="查询"/>
			
				<span class="right">
						
		<input type="button" class="btn btn-default btn_w_a btn_bg_2 radius0 left mar-rig10"  onclick="gotWorkGather()"  value="切换到图表模式>>"/>
		
			</span>
			<div class="clearfix"></div>
	
	</form:form>
								
								</div>
								<div class="hr15" ></div>
								<div class="table_container">
									<table class="table table_jz2" id="myTable02">
									      <thead>
									        <tr>
									          	<th>客户名称</th>
												<th>票据总数</th>
												<th>凭证总数</th>
												<th>分录总数</th>
												<th>记账员</th>						        
									        </tr>
									      </thead>
									      <tbody>
	
									       	<c:forEach items="${tWorkGatherInfoList}" var="tWorkGatherInfo" varStatus="status">
											<tr>
											 <th scope="row" class="row_01">
									          	<label>${status.count}</label>${tWorkGatherInfo.customerName}
									       </th>
										
												<td>
													${tWorkGatherInfo.billSum}
												</td>
												<td>
													${tWorkGatherInfo.voucherSum}
												</td>
												<td>
													${tWorkGatherInfo.shuntSum}
												</td>
												<td>
													${tWorkGatherInfo.workerName}
												</td>
													</tr>
											</c:forEach>
									      </tbody>
									</table>
								</div>
								<%-- <div class="pagination">${page}</div> --%>
								<div style="height:20px;"></div>
							</div>

						</div>
					</div>
					
				</div>
			</div>
		</div>
	</div>
	<script>
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
    	return false;
    }
    function up(){
    	var aa=$("#byYear").val();
    	var bb=Number(aa)-1;
		$("#byYear").val(bb);
		$("#searchForm").submit();
    	return false;
    }
    function down(){
    	var aa=$("#byYear").val();
    	var bb=Number(aa)+1;
		$("#byYear").val(bb);
		$("#searchForm").submit();
    	return false;
    } 
    function gotWorkGather(){
		window.location="${ctx}/inspection/workstatistics/tWorkGather/list";
    	return false;
    }
$(function(){
	fixTableHeader($(window).height() - 205, $(window).height() - 168)
	$(".table_jz2 tbody tr").hover(function(){
		$(this).addClass("thover2 font_cc4");
	},function(){
		$(this).removeClass("thover2 font_cc4");
	})
})
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
</script>
</body>
</html>