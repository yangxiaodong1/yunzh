<!doctype html>
<html lang="en">
<head>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
	<title>工作量明细表-工作统计-芸豆会计</title>
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.11.2.js" type="text/javascript"></script>
	<script src="${ctxStatic}/fixedheadertable/jquery.fixedheadertable.js"></script>
	<link href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/zTreeStyle/zTreeStyle.css" type="text/css">
	<link href="${pageContext.request.contextPath}/static/css/master_2.css" type="text/css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/static/bootstrap/css/page.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
	<link href="${ctxStatic}/fixedheadertable/defaultTheme.css" rel="stylesheet" media="screen" />
</head>
<body>
	<div class="main_index3">
		<div class="pane_main5">
			<div class="t_pane_a1 radius_5">
				<div class="user_pane_r2">
					<div class="hr20"></div>
					<ul class="nav nav-tabs" role="tablist">
							<li><a href="${ctx}/inspection/workstatistics/tWorkGather/">工作量汇总表</a></li>
							<li class="active"><a href="${ctx}/inspection/workstatistics/tWorkInfo/">工作量明细表</a></li>
							<li><a href="${ctx}/inspection/workstatistics/tServeInfo/">服务收费报表</a></li>
							<li><a href="${ctx}/inspection/workstatistics/tServiceCharge/">收费审核</a></li>
					</ul>
					<div class="tab-content main5_con radius_5">
						<div class="tab-pane active" id="tab_f1">
							<div class="hr20"></div>
							<div class="pane_gzt">
								<div class="search_form4">

										<form:form id="searchForm" modelAttribute="tWorkInfo" action="${ctx}/inspection/workstatistics/tWorkInfo/list/" method="post">
											<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
											<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
											<span class="span_year left">
												<form:hidden path="byYear"/>
												<input class="bg_p6 btn_sea1 left" type="button" onclick="up()"  value="左"/>
												<form:input class="form-control-static ipt_w5 left"  readonly="true" path="byYearName" htmlEscape="false"/>
												<input type="button" class="bg_p6 btn_sea2 left mar-rig3" onclick="down()" value="右"/>
											</span>
		
											<form:input path="workerName" htmlEscape="false" maxlength="100" class="form-control ipt_w5 ipt_txt_s1 mar-rig3 left"/>
											<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
											<span class="right">
												<form:select path="officeId" class="input-medium select">
													<form:option class="select" value=" " label="全部"/>
													<form:options items="${listSysOffice}" itemLabel="name" itemValue="id" htmlEscape="false"/>
												</form:select>
											</span>
				
							<div class="clearfix"></div>
								</form:form>
									
									
								</div>
								<div class="hr15"></div>
								<div class="table_container">
									<table class="table table_jz2" id="myTable02">
								      <thead>
								        <tr>
								          <th>记账员</th>
								          <th>票据总数</th>
								          <th>凭证总数</th>
								          <th>分数总数</th>						        
								        </tr>
								      </thead>
								      <tbody>
								       <c:forEach items="${tWorkInfoList}" var="tWorkInfo" varStatus="status">
										<tr>
											<th scope="row" class="row_01">
									          <label>${status.count}</label>${tWorkInfo.workerName}
									      	</th>
											<td>
												${tWorkInfo.billSum}
											</td>
											<td>
												${tWorkInfo.voucherSum}
											</td>
											 <td>
												${tWorkInfo.customerSum}
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
$(function(){
	fixTableHeader($(window).height() - 212, $(window).height() - 173);
	$(".table_jz2 tbody tr").hover(function(){
		$(this).addClass("thover2 font_cc4");
	},function(){
		$(this).removeClass("thover2 font_cc4");
	})
})
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