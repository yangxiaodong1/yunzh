
    <%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="Author" content="">
<meta name="Keywords" content="">
<meta name="Description" content="">
<title>自动摊销-在线会计-芸豆会计</title>
<script src="${ctxStatic}/jquery/jquery-1.11.2.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/css/master_1.css" type="text/css" rel="stylesheet" />
<style type="text/css">
	.table_list_2 tbody>tr.skin_n>td{color:#000;}
	.table_list_2 tbody>tr.skin_h>td{background:#fbfc8a;color:#000;}	
	.table_list_2 tbody>tr.skin_t>td{background:#ea2e49;color:#000;}
	.table_list_2 tbody>tr.skin_c>td{background:#d2fcfc;color:#000;}
	.main-con{padding:10px 10px 0 10px;}
	.list_pane_2{border:none;padding:0;}
	.t_pane_1 .tit_d3{margin-right:5px;}
	.table_list_2>tbody>tr>td{text-align:left;}
	.table_list_2>tbody>tr>td.tright{text-align:right;}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			
			var rows1=$(this).attr("idd");
			$("[idd='0']").each(function(){	
				$(this).addClass("skin_t");
				$(this).hover(function(){					
					$(this).removeClass("skin_t").addClass("skin_h");					
					
				},function(){
					$(this).removeClass("skin_h");			
					$(this).addClass("skin_t");
				})				
			})
			$("[idd='1']").each(function(){	
				$(this).hover(function(){
					$(this).addClass("skin_h").siblings().removeClass("skin_h");
				},function(){
					$(this).removeClass("skin_h");
				})
			})
			$("[attrclass='rows']").on('click',function(){				
				var rows=$(this).attr("id");				
				$(this).attr('class',"skin_c").siblings().removeClass("skin_c");				
												
				//$(this).removeClass("skin_t");
				$("#attr_href").attr("name",rows);				
				$("#attr_href").on('click',function(){
					rows=$(this).attr("name");
					mystart(rows);
				})		
				$("#attr_href2").attr("name",rows);				
				$("#attr_href2").on('click',function(){
					rows=$(this).attr("name");
					mylinks(rows);
				})
			})
				
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function mylinks(aid){
			urls='${ctx}/amortize/tAmortize/amortize?';
			var aid;
			window.location.href=urls+'id='+aid+'&amortize_status=0';	
		}
		function mystart(aid){
			urls='${ctx}/amortize/tAmortize/amortize?';
			var aid;
			window.location.href=urls+'id='+aid+'&amortize_status=1';	
		}

	</script>
</head>
<body>

		
		<div class="main-con">
			<div class="body-con">
				<div class="tit_channel_1">自动摊销设置</div>
				<div class="t_pane_1">
					<div class="tit_d3">
						<button class="btn btn-default2" id="attr_href" name=''>开始摊销</button><button class="btn btn-default2" id="attr_href2" name=''>停止摊销</button>
					</div>
					<div class="clearfix"></div>
					<div class="hr10"></div>
				</div>
				<div class="list_pane_2 list_pane_h">
					<div class="list_t1">
						<table class="table table-bordered table_list_2">
							<thead>
								<th>名称</th>
								<th width="11%">入账日期</th>
								<th width="11%">原值</th>
								<th width="11%">残值率</th>
								<th width="11%">残值</th>
								<th width="11%">摊销月数</th>
								<th width="11%">月折旧额</th>
								<th width="11%">累计折旧额</th>
								<th width="11%">净值</th>
							</thead>
							<tbody>
								<c:forEach items="${ta}" var="tAmortize">
			
			<tr id="${tAmortize.tVoucherExpId}" attrclass="rows" idd="${tAmortize.amortizeStatus}">
				<td>
					${tAmortize.expName}
				</td>
				<td>
					${tAmortize.enterAccountDate}
				</td>
				<td class='tright'>
					${tAmortize.originalValue}
				</td>
				<td>
					${tAmortize.scrapValueRate}
				</td>
				<td class='tright'>
					${tAmortize.scrapValue}
				</td>
				<td>
					${tAmortize.amortizeAgeLimit}
				</td>
				<td class='tright'>
					${tAmortize.monthDiscountOldPosition}
				</td>
				<td class='tright'>
					${tAmortize.totalOldPosition}
				</td>
				<td class='tright'>
					${tAmortize.netValue}
				</td>
				
			</tr>
		</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="hr10"></div>
			</div>
			<div class="clearfix"></div>
		</div>
		<div class="clearfix"></div>

</body>

</html>