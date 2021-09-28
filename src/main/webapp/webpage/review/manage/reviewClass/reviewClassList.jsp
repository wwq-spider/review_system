<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">

		<table id="reviewClassList" style="width: 700px; height: 300px">
			<thead>
				<tr>
					
					<th field="classId" hidden="hidden">编号</th>
					<th field="title" width="50">题库名称</th>
					<th field="sortId" width="50">排序ID</th>
					<th field="status" width="50">状态</th>
					<th field="type" width="50">是否热门</th>
					<th field="opt" width="150">操作</th>
				</tr>
			</thead>
		</table>
		<div style="padding: 3px; height: auto;" id="reviewClassTB" class="datagrid-toolbar">
			 <div class="datagrid-toolbar" style="height:30px;">
				<span style="float:left;">
					<a href="#" id='add' class="easyui-linkbutton" plain="true" icon="icon-add"
					onclick="pubOrSave('添加分类','reviewClass.do?toAdd','reviewClassList')" id="">添加分类</a></span>
			</div>
		</div>
	<script type="text/javascript">

	    // 编辑初始化数据
		function getData(data){
			$('.datagrid-header-inner .datagrid-cell ').css("text-align","center"); 
			var rows = [];			
			var total = data.total;
			var puburl = "";
			var statusText = "";
			let typetext = "";

			for(var i=0; i<data.rows.length; i++){
				if(data.rows[i].status == 0) {
					puburl = "[<a href=\"#\" onclick=\"publish('reviewClass.do?publish&pubType=1&classId="+
							data.rows[i].classId+"','reviewClassList','发布')\">发布</a>]&nbsp;&nbsp;";
					statusText = "停用中";
				} else {
					puburl = "[<a href=\"#\" onclick=\"publish('reviewClass.do?publish&pubType=0&classId="+
							data.rows[i].classId+"','reviewClassList','停止')\">停止</a>]&nbsp;&nbsp;";
					statusText = "已发布";
				}

				if(data.rows[i].type == 1) {
					puburl += "[<a href=\"#\" onclick=\"publish('reviewClass.do?setUpHot&opt=2&classId="+
							data.rows[i].classId+"','reviewClassList','置为热门')\">置为热门</a>]&nbsp;&nbsp;";
					typetext = "否";
				} else {
					puburl += "[<a href=\"#\" onclick=\"publish('reviewClass.do?setUpHot&opt=1&classId="+
							data.rows[i].classId+"','reviewClassList','取消热门')\">取消热门</a>]&nbsp;&nbsp;";
					typetext = "是";
				}

				rows.push({
					classId: data.rows[i].classId,
					title: data.rows[i].title,
					status: statusText,
					sortId: data.rows[i].sortId,
					type: typetext,
					opt: "[<a href=\"#\" onclick=\"pubOrSave('题目设置','reviewClass.do?toAdd&classId="+data.rows[i].classId+"','reviewClassList',1000,700)\">题目设置</a>]&nbsp;&nbsp;"+
						  puburl +"[<a href=\"#\" onclick=\"delObj('reviewClass.do?del&classId="+data.rows[i].classId+"','删除')\">删除</a>]"
				});
			}
			var newData={"total":total,"rows":rows};
			$('.datagrid-cell-c1-name').css("text-align","center");
			return newData;
		}
	    // 刷新
	    function reloadTable(){
	    	$('#reviewClassList').datagrid('reload');
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
		$('#reviewClassList').datagrid({
			title: '分类列表',
	        idField: 'id',
	        fit:true,
	        loadMsg: '数据加载中...',
	        pageSize: 20,
	        pagination : true,
	        sortOrder:'asc',
	        rownumbers:true,
	        singleSelect:true,
	        fitColumns:true,
	        showFooter:true,
	        url:'reviewClass.do?datagrid',  
	        toolbar: '#reviewClassTB',//显示查询条件部分
	        loadFilter: function(data){
	        	return getData(data);
	    	}
	     }); 
	    
	    //设置分页控件  
	    $('#reviewClassList').datagrid('getPager').pagination({  
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
