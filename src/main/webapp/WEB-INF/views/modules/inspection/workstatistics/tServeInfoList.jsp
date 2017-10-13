<!doctype html>
<html lang="en">
<head>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
	<title>服务收费报表-工作统计-芸豆会计</title>
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.11.2.js" type="text/javascript"></script>
	<link href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/zTreeStyle/zTreeStyle.css" type="text/css">
	<link href="${pageContext.request.contextPath}/static/css/master_2.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
	<link href="${pageContext.request.contextPath}/static/bootstrap/css/page.css" type="text/css" rel="stylesheet" />
	
</head>
<body>
	<div class="main_index3">
		<div class="pane_main5">
			<div class="t_pane_a1 radius_5">
				<div class="user_pane_r2">
					<div class="hr20"></div>
					<ul class="nav nav-tabs">
							<li><a href="${ctx}/inspection/workstatistics/tWorkGather/">工作量汇总表列表</a></li>
							<li><a href="${ctx}/inspection/workstatistics/tWorkInfo/">工作量明细表</a></li>
							<li class="active"><a href="${ctx}/inspection/workstatistics/tServeInfo/">服务收费报表</a></li>
							<li><a href="${ctx}/inspection/workstatistics/tServiceCharge/">收费审核</a></li>
					</ul>
					<div class="tab-content main5_con radius_5">
						<div class="tab-pane active" id="tab_f1">
							<div class="hr20"></div>
							<div class="pane_gzt">
								<div class="search_form4">
									<form:form id="searchForm" modelAttribute="tServeInfo" action="${ctx}/inspection/workstatistics/tServeInfo/list/" method="post">
										<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
										<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
										<span class="span_year left">
											<form:hidden path="byYear"/>
											<input type="button" class="bg_p6 btn_sea1 left" onclick="up()"  value="左"/>
											<form:input class="form-control-static ipt_w5 left"  readonly="true" path="byYearName" htmlEscape="false"/>
											<input type="button" class="bg_p6 btn_sea2 left mar-rig3" onclick="down()" value="右"/>
										</span>
										<form:input path="workerName" htmlEscape="false" maxlength="100" class="form-control ipt_w5 ipt_txt_s1 mar-rig3 left"/>
										<input id="btnSubmit" class="btn btn-default btn_w_a btn_bg_2 radius0 left" type="submit" value="查询"/>
										<span class="right">
											<form:select class="select" path="officeId">
												<form:option  value=" " label="全部"/>
												<form:options items="${listSysOffice}" itemLabel="name" itemValue="id" htmlEscape="false"/>
											</form:select>
										</span>
										<div class="clearfix"></div>
									</form:form>
								</div>
								<div class="hr15"></div>
								 <div class="charts_lay">
										<c:choose>
				 						 <c:when test="${tServeInfo.notnull=='0'}">
											 <div id="main" style="height:400px; width:871px;"></div>
									    </c:when>
									     <c:when test="${tServeInfo.notnull=='1'}">
										<div class="no_data" style="height:400px;">
											<img src="${pageContext.request.contextPath}/static/imgs/nodata.jpg">
										</div>
									    </c:when>
										 <c:otherwise>
									    </c:otherwise>
									</c:choose>
								 </div>
								<div style="height:20px;"></div>
								<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/echarts.js"></script>

	  <script type="text/javascript">
        // 路径配置
        require.config({
            paths: {
                echarts: '${pageContext.request.contextPath}/static/js'
            }
        });
        
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('main')); 
                
                var option = {              
                	animation:false,
                    tooltip: {show: true},
                    dataZoom : {
            	        show : true,
            	        realtime : true,
            	        start : 0,
            	        end : ${end}
            	    },
                    xAxis : [
                             {type : 'category',min:9,data : [
											<c:forEach items="${listTServeInfo}" var="tServeInfo">
											'${tServeInfo.workerName}',
											</c:forEach>
                                         ] 
                             
                             }
                                         
                    ],
                    yAxis : [ {type : 'value',max : ${findListMax}}],
                    series : [{type:'bar', barWidth : 30, barGap :80,data:[
											<c:forEach items="${listTServeInfo}" var="tServeInfo">
											'${tServeInfo.chargeSum}',
											</c:forEach>
											],
                            itemStyle:{normal:{color:'red',areaStyle: {type: 'default'}}}}]
                };
        
                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
        );
        
        var noWindowH=$(window).height()-185;
		$(".charts_lay").css({
			"height":noWindowH,
			"overflow-x":"hidden",
			"overflow-y":"auto"
		})

        
    </script>
								<div class="hr15"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
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
        
        
	</script>
</body>
</html>