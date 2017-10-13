<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>记账公司管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/newcharge/tChargecompany/">记账公司列表</a></li>
		<shiro:hasPermission name="newcharge:tChargecompany:edit"><li><a href="${ctx}/newcharge/tChargecompany/form">记账公司添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tChargecompany" action="${ctx}/newcharge/tChargecompany/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>公司Id</th>
				<th>记账公司名字</th>
				<th>所在城市</th>
				<th>联系人</th>
				<th>手机号码</th>
				<th>公司人数</th>
				<th>企业经营范围</th>
				<th>0代表停止状态，1代表服务状态</th>
				<th>0代表试用客户状态，1代表正式客户状态</th>
				<th>法人代表</th>
				<th>联系电话</th>
				<th>身份证的附件类型</th>
				<th>身份证号</th>
				<th>营业执照号码</th>
				<th>营业执照号码</th>
				<th>税务登记号码</th>
				<th>税务登记号码附件</th>
				<th>组织机构代码</th>
				<th>组织机构附件</th>
				<th>对接人</th>
				<th>update_date</th>
				<th>备注</th>
				<shiro:hasPermission name="newcharge:tChargecompany:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tChargecompany">
			<tr>
				<td><a href="${ctx}/newcharge/tChargecompany/form?id=${tChargecompany.id}">
					${tChargecompany.officeid}
				</a></td>
				<td>
					${tChargecompany.chargecomname}
				</td>
				<td>
					${tChargecompany.city}
				</td>
				<td>
					${tChargecompany.contectperson}
				</td>
				<td>
					${tChargecompany.mobilenumber}
				</td>
				<td>
					${tChargecompany.companynumber}
				</td>
				<td>
					${tChargecompany.companyrunrange}
				</td>
				<td>
					${tChargecompany.servicestatus}
				</td>
				<td>
					${tChargecompany.usestatus}
				</td>
				<td>
					${tChargecompany.legalname}
				</td>
				<td>
					${tChargecompany.contectphone}
				</td>
				<td>
					${tChargecompany.appendixtype}
				</td>
				<td>
					${tChargecompany.cardnumber}
				</td>
				<td>
					${tChargecompany.runnumber}
				</td>
				<td>
					${tChargecompany.runappendix}
				</td>
				<td>
					${tChargecompany.taxrenum}
				</td>
				<td>
					${tChargecompany.taxappendx}
				</td>
				<td>
					${tChargecompany.organcode}
				</td>
				<td>
					${tChargecompany.codeappendx}
				</td>
				<td>
					${fns:getDictLabel(tChargecompany.abutment, '', '')}
				</td>
				<td>
					<fmt:formatDate value="${tChargecompany.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tChargecompany.remarks}
				</td>
				<shiro:hasPermission name="newcharge:tChargecompany:edit"><td>
    				<a href="${ctx}/newcharge/tChargecompany/form?id=${tChargecompany.id}">修改</a>
					<a href="${ctx}/newcharge/tChargecompany/delete?id=${tChargecompany.id}" onclick="return confirmx('确认要删除该记账公司吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>