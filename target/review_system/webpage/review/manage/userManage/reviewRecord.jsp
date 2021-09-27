<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<table id="recordList" style="width: 700px; height: 300px">
			<thead>
				<tr>
					
					<th field="resultId" hidden="hidden">记录ID</th>
					<th field="title" width="15">题库名称</th>
					<th field="totalGrade" width="5">总得分</th>
					<th field="resultExplain" width="80">结果描述</th>
					<th field="createTime" width="15">测评时间</th>
					<th field="opt" width="10">操作</th>
				</tr>
			</thead>
		</table>
	<script type="text/javascript">

	    // 编辑初始化数据
		function getData(data){
			$('.datagrid-header-inner .datagrid-cell ').css("text-align","center"); 
			var rows = [];			
			var total = data.total;
			var puburl = "";
			var statusText = "";

			for(var i=0; i<data.rows.length; i++){
				rows.push({
					resultId: data.rows[i].resultId,
					title: data.rows[i].title,
					totalGrade: data.rows[i].totalGrade,
					resultExplain: data.rows[i].resultExplain,
					createTime: data.rows[i].createTime,
					opt: "[<a href=\"#\" onclick=\"delObj('reviewUser.do?delRecord&resultId="+data.rows[i].resultId+"','删除')\">删除</a>]"
				});
			}
			var newData={"total":total,"rows":rows};
			$('.datagrid-cell-c1-name').css("text-align","center");
			return newData;
		}
	    // 刷新
	    function reloadTable(){
	    	$('#recordList').datagrid('reload');
	    }
	    
	    //重置
	    function searchReset(name) {
			$('#status').val("");//清空查询控件的值

			keywordSearch();
		}
	    
	    function EnterPress(e) {
			var e = e || window.event;
			if (e.keyCode == 13) {
				keywordSearch();
			}
		}
	
		// 设置datagrid属性
		$('#recordList').datagrid({
			title: '记录列表',
	        idField: 'resultId',
	        fit:true,
	        loadMsg: '数据加载中...',
	        pageSize: 20,
	        pagination : true,
	        sortOrder:'asc',
	        rownumbers:true,
	        singleSelect:true,
	        fitColumns:true,
	        showFooter:true,
	        url:'reviewUser.do?recordDatagrid&userId=${userId}',  
	        loadFilter: function(data){
	        	return getData(data);
	    	}
	     }); 
	    
	    //设置分页控件  
	    $('#recordList').datagrid('getPager').pagination({  
	        pageSize: 20,  
	        pageList: [10,20,30],  
	        beforePageText: '',  
	        afterPageText: '/{pages}',
	        displayMsg: '{from}-{to}共{total}条',
	        showPageList:true,
	        showRefresh:true,
	        onBeforeRefresh:function(pageNumber, pageSize){
	            $(this).pagination('loading');
	            $(this).pagination('loaded');
	        }
	    });
	  
	</script>
</div>
	<style>
		table tr td div {
			text-align: center !important;
		}
	</style>
	</div>
</div>
