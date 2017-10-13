<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>备份与恢复-在线会计-芸豆会计</title>
	<script src="${ctxStatic}/jquery/jquery-1.11.2.js" type="text/javascript"></script>
	<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/fixedheadertable/jquery.fixedheadertable.js"></script>
	<script src="${ctxStatic}/js/table_height.js"></script>
	<link href="${ctxStatic}/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/css/master_1.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/css/cashFlowvAndProfit.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/accountAndbalance/css/dialog.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/fixedheadertable/defaultTheme.css" rel="stylesheet" media="screen" />
	<link href="${ctxStatic}/css/backupAndRecover.css" rel="stylesheet" type="text/css">
	<style type="text/css">
		.table-account-wrapper tr {font-size:12px;}
		.loading_animate{/*width:32px;height:32px;*/border-radius:5px;display:inline-block;line-height:40px;margin:0 auto;padding:20px 20px 20px 60px;background:#eeeeee url(${ctxStatic}/accountAndbalance/images/btn_loading.gif) no-repeat 20px center;}
	</style>
</head>
<body>

<div class="main-con">
	<div class="tit_channel_1">备份与恢复</div>
	<div class="t_pane_1">
		<div class="tit_d3">
			<button class="btn btn-default2 start_backup" name=''>开始备份</button>
		</div>
	</div>
	<div class="label_note">数据备份记录：</div>
	<div class="list_t1">
		<table class="table-account-wrapper" id="myTable02">
			<thead>
				<th>备份名称</th>
				<th width="20%">时间</th>
				<th width="20%">文件大小</th>
				<th width="20%">操作</th>
			</thead>
			<tbody id="tbody_add">
				<c:forEach items="${backup }" var="backup">
					<tr> 
						<td>${backup.backupName }</td>
						<td>${fn:substring(backup.backupDate, 0, 19)}</td>
						<td>${backup.fileSize }</td>
						<td>
							<a class="recover" href="javascript:;">恢复</a>
							<a class="delete" href="javascript:;" onclick="fdelete('${backup.backupNumber }',$(this))">删除</a>
							<a class="download" href="${ctx}/backupandrecover/tbackupRecover/downFile?fileName=${backup.backupName }">下载</a>
						</td>
					</tr>
				</c:forEach>			
			</tbody>
		</table>
	</div>
	<div class="label_note">
		<a class="upload" href="javascript:;">上传本地备份</a>
	</div>
</div>
<div class="mask"></div>
<!-- 恢复 -->
<div class="layer layerS recover" style="display:none;">
	<h5 class="h6-kr">系统提示<i class="i-close"></i></h5>
	<div class="chose_file">
		<img src="${ctxStatic}/images/warming.gif" alt="">
		<div class="warm_mes">
			<div>您将把账套数据恢复到备份文件所在的状态，<span class="red">此操作不可回退</span>，请谨慎操作。为保证备份数据的完整性，<span class="red">请确保账套里的其他用户已经退出系统</span>。</div>
			<div class="check">
				<input type="checkbox">
				<span>我已清楚了解将产生的后果</span>
			</div>
			<span class="red check">（请先确认并勾选“我已清楚了解将产生的后果“）</span>
		</div>
	</div>
	<div class="button buttonW clearfix">
		<button class="cancel">取消</button>
		<button class="fsave recover_sure">确定</button>
	</div>
</div>
<!-- 删除 -->
<div class="layer layerS delete_dialog" style="display:none;">
	<h5 class="h6-kr">系统提示<i class="i-close"></i></h5>
	<div class="chose_file">
		<img src="${ctxStatic}/images/warming.gif" alt="">
		<div class="warm_mes">您确定要删除该备份吗？</div>
	</div>
	<div class="button buttonW clearfix">
		<button class="cancel">取消</button>
		<button class="fsave delete_sure">确定</button>
	</div>
</div>
<!-- 开始备份 -->
<div class="layer layerS start_backup" style="display:none;">
	<h5 class="h6-kr">系统提示<i class="i-close"></i></h5>
	<div class="chose_file">
		<img src="${ctxStatic}/images/warming.gif" alt="">
		<div class="warm_mes">为保证备份数据的完整性，<strong>请确保账套里的其他用户已经退出系统</strong>。
确定执行备份？</div>
	</div>
	<div class="button buttonW clearfix">
		<button class="cancel">取消</button>
		<button class="fsave backup_sure">确定</button>
	</div>
</div>
<!-- 上传本地备份 -->
<div class="layer layerS layer_upload" style="display:none;">
	<h5 class="h6-kr">上传本地备份<i class="i-close"></i></h5>
	<div class="chose_file">
		<span>请选择本分文件：</span>
		<input type="text">
		<input type="button" value="浏览">
	</div>
	<div class="button buttonW clearfix">
		<button class="cancel">取消</button>
		<button class="fsave upload_sure">上传</button>
	</div>
</div>
<div class="message-pop"><span>保存成功！</span></div>
<div   id= "sending" style="filter:alpha(opacity=80);position:absolute; top:0px;left:0px;z-index:10; visibility:hidden; width: 100%; height:   120px; ">
<TABLE  WIDTH=100%  BORDER=0  CELLSPACING=0 CELLPADDING=0 border=0> 
        <TR> 
                
               <td> 
               <table  WIDTH=100%   height=60   BORDER=0   CELLSPACING=0  CELLPADDING=0>
                    <tr> 
                            <td align="center"><div class='loading_animate'> 正在备份, 这将花费几分钟时间, 请耐心等待... </div></td>
                    </tr> 
               </table> 
               </td> 
               
        </tr> 
</table> 
</div>
<script>
$('.fixed_table .fht-default').show();
$("#sending").css({
	'top':240
})

 //提示信息
 var messagePop = function(str){
		$(".message-pop").fadeIn();
		$(".message-pop").children("span").html(str);
		setTimeout(function(){
			$(".message-pop").fadeOut();
		},2000)
}

//删除
var fnumber;
function fdelete(number,obj){
	fnumber="";
	fnumber=number;
	obj.parents("tr").addClass("flag");
	$(".mask,div.delete_dialog").show();
}
//确定删除
$(".delete_sure").on("click",function() {
	$.ajax({
 	     type: 'POST',
 	     url: '${ctx}/backupandrecover/tbackupRecover/delete',
 	     cache:false,
 	     async: false,
 	     dataType:'json',
 	     data: {
	    	 'backupNumber':fnumber
	     },
 	     success: function(data){
 	    	messagePop("删除成功!");
 	     }
	}); 			
	$(".table-account-wrapper tr.flag").remove();
	$(".mask,div.delete_dialog").hide();
})
	$(function() {
		// 恢复
		$("a.recover").on("click",function() {
			$(".mask,div.recover").show();
		});
		
		
		/* $(".delete").on("click",function() {
	    	fnumber="";
			fnumber=$(this).attr('id');
			$(this).parents("tr").addClass("flag");
			$(".mask,div.delete_dialog").show();
		}) */
		// 上传本地备份
		$(".upload").on("click",function() {
			$(".mask,.layer_upload").show();
		});
		// 开始备份
		$("button.start_backup").on("click",function() {
			$(".mask,div.start_backup").show();
		});
		// 取消
		$(".i-close,.cancel").on("click",function() {
			$(".mask,.layer").hide();
			if($(this).parents("div.recover").find("span.check").length == 1){
				$(this).parents("div.recover").find("span.check").hide();
			}
			if($(".table-account-wrapper tr").hasClass("flag")) {
				$(".table-account-wrapper tr").removeClass("flag");
			}
		});
		$("div.recover input").on("change",function() {                
			$(this).parents("div.recover").find("span.check").hide();      
		});
		// 确定恢复
		$(".recover_sure").on("click",function() {
			if($(this).parents("div.recover").find("input")[0].checked == false) {
				$(this).parents("div.recover").find("span.check").show();
			}else {
				$(".mask,div.recover").hide();
			}
		});
		
		//下载
		/* $(".download").on("click",function() {
			number=$(this).attr('id');
			alert(number);
			$.ajax({
		 	     type: 'POST',
		 	     url: '${ctx}/backupandrecover/tbackupRecover/downFile',
		 	     cache:false,  
		 	     async: false,
		 	     dataType:'json',
		 	    data: {
			    	 'fileName':number
			     },
		 	     success: function(data){
		 	    	 
		 	     }
			}); 
		}) */
		// 确定开始备份
		$(".backup_sure").on("click",function() {
			 sending.style.visibility="visible";
			 $.ajax({
		 	     type: 'POST',
		 	     url: '${ctx}/backupandrecover/tbackupRecover/tbackuprecoveradd',
		 	     cache:false,  
		 	     async: false,
		 	     dataType:'json',
		 	     success: function(data){
		 	    	 if(data!=null){
		 	    		var date=data.backupDate;
		 	    		var addHtml="<tr>"
						+"<td>"+data.backupName+"</td>"
						+"<td>"+date+"</td>"
						+"<td>"+data.fileSize+"</td>"
						+"<td>"
						+"<a class='recover' href='javascript:;'>恢复</a>"
						+"<a class='delete' href='javascript:;' onclick='fdelete(&apos;"+data.backupNumber+"&apos;,$(this))'>删除</a>"
						+"<a href='${ctx}/backupandrecover/tbackupRecover/downFile?fileName="+data.backupName+"'>下载</a>"
						+"</td>"
						+"</tr>";
						if($("#tbody_add tr").length==0){
							$("#tbody_add").html(addHtml);
						}else{
							$("#tbody_add tr:eq(0)").before(addHtml);
						}
						$(".mask").hide();
						sending.style.visibility="hidden";
						messagePop("备份成功!");
		 	    	 }
		 	     },error : function() {	  
			 	    	$(".mask").hide();
						sending.style.visibility="hidden";
			        	messagePop("备份失败!");
			        } 
			}); 
			$("div.start_backup").hide();
		})
		// 确定上传本地备份
		$(".upload_sure").on("click",function() {
			$(".mask,.layer_upload").hide();
		})
	})
</script>
</body>
</html>