<!doctype html>
<html lang="en">
<head>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
	<title>工作量汇总表-工作统计-芸豆会计</title>
	<script src="${pageContext.request.contextPath}/static/jquery/jquery-1.11.2.js" type="text/javascript"></script>
	<link href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/zTreeStyle/zTreeStyle.css" type="text/css">
	<link href="${pageContext.request.contextPath}/static/css/master_2.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
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
									<form>
										<span class="span_year left">
										<input type="button" onclick="up()" class="bg_p6 btn_sea1 left"  value="左"/>
											<input id="byYear" readonly value="${tWorkGather.byYear}年" class="form-control-static ipt_w5 left"/>	
										<input type="button" onclick="down()" class="bg_p6 btn_sea2 left mar-rig3" value="右"/>
										</span>
										<span class="right">
										
										<input type="button" class="btn btn-default btn_w_a btn_bg_2 radius0 left mar-rig10"  onclick="gotWorkGatherinfo()"  value="详细数据列表>>"/>
													
										</span>
										<div class="clearfix"></div>
									</form>
								</div>
								<div class="hr15"></div>
								<div class="charts_lay">
										<c:choose>
				 						 <c:when test="${tWorkGather.notnull=='0'}">
										  <div class="echartable" style="width:871px; height: 400px;" id="main"></div>
									    </c:when>
									     <c:when test="${tWorkGather.notnull=='1'}">
									     <div class="no_data">
											<img src="${pageContext.request.contextPath}/static/imgs/nodata.jpg">
										</div>
									    </c:when>
										 <c:otherwise>
									    </c:otherwise>
									</c:choose>
								</div>
								<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/echarts.js"></script>
								<script type="text/javascript">  // 路径配置
								      require.config({
								          paths: {echarts: '${pageContext.request.contextPath}/static/js'}
								      });
								      require(['echarts','echarts/chart/line'],function (ec) {
								              var myChart = ec.init(document.getElementById('main')); 
								              var option = {
								            		  tooltip: {show: true},
								                      legend: {selectedMode:'single', data:['客户总数','票据总数','凭证总数'],
								                    	  selected: {'票据总数' : false,'凭证总数' : false}},
								                      xAxis : [{boundaryGap : false,type : 'category',data : ["","1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月",""]}],
								                      yAxis : [{type : 'value'}],
								                      series : [{itemStyle:{normal:{color:'#0066CC'}},"name":"客户总数","type":"line","data":[${tWorkGather.customerSum}]}, 
								                                {itemStyle:{normal:{color:'#00CC00'}},"name":"票据总数","type":"line","data":[${tWorkGather.billSum}]}, 
								                                {itemStyle:{normal:{color:'#FF6600'}},"name":"凭证总数","type":"line","data":[${tWorkGather.voucherSum}]}
								                                ]};
								                  myChart.setOption(option); 
								              }
								          );
								      var noWindowH=$(window).height()-165;
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
		 function up(){
				window.location.href="${ctx}/inspection/workstatistics/tWorkGather/list?ByYear="+${tWorkGather.byYear-1};
	        	return false;
	        }
		 function gotWorkGatherinfo(){
				window.location.href="${ctx}/inspection/workstatistics/tWorkGatherInfo/list";
	        	return false;
	        }
	        function down(){
				window.location.href="${ctx}/inspection/workstatistics/tWorkGather/list?ByYear="+${tWorkGather.byYear+1};
	        	return false;
	        }
	</script>
</body>
</html>