<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">

		<table id="reportList" style="width: 700px; height: 300px">
			<thead>
				<tr>
					<th field="reportId" hidden="hidden">编号</th>
					<th field="className" width="70">分类名称</th>
					<th field="reportName" width="80">维度名称</th>
					<th field="createTime" width="50">创建时间</th>
					<th field="createBy" width="40">创建人</th>
					<th field="opt" width="50">操作</th>
				</tr>
			</thead>
		</table>
		<div style="padding: 3px; height: auto;" id="reportTb" class="datagrid-toolbar">
			 <div name="searchColums">
				<span style="display:-moz-inline-box;display:inline-block;">
					<span title="分类"
					style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; ">
						分类： </span> 
						<select  id="classId" name="classId">
					   <option value="" selected="selected">--请选择--</option>
					   <c:forEach items="${classList }" var="reviewType">
					   		<option value="${reviewType.classId }">${reviewType.title }</option>
					   </c:forEach>
					</select>
					</select>
				</span>
			 </div>
			 <div class="datagrid-toolbar" style="height:30px;">
				<span style="float:left;">
					<a href="#" id='add' class="easyui-linkbutton" plain="true" icon="icon-add"
					onclick="add('添加维度','report.do?toAdd','reportList',800,500)" id="">添加维度</a></span>
				<span style="float:right">
					<a onclick="reportSearch();" iconcls="icon-search" class="easyui-linkbutton l-btn" href="#"
						id="">查询</a>
					<a onclick="searchReset();" iconcls="icon-reload"
						class="easyui-linkbutton l-btn" href="#" id="">重置</a>
				</span>
			</div>
		</div>
	<script type="text/javascript">

	    // 编辑初始化数据
		function getData(data){
			$('.datagrid-header-inner .datagrid-cell ').css("text-align","center"); 
			var rows = [];			
			var total = data.total;
			var reportname;
			for(var i=0; i<data.rows.length; i++){
				reportname = encodeURI(encodeURI(data.rows[i].reportName));
				rows.push({
					reportId: data.rows[i].reportId,
					className: data.rows[i].className,
					reportName: data.rows[i].reportName,
					createTime: data.rows[i].createTime,
					createBy: data.rows[i].createBy,
					opt: "[<a href=\"#\" onclick=\"add('维度编辑','report.do?toAdd&reportId="+data.rows[i].reportId+"&reportName="+reportname+"','reportList',800,500)\">编辑</a>]&nbsp;&nbsp;"+
				 	 "[<a href=\"#\" onclick=\"add('维度设置','report.do?toReportSet&reportId="+data.rows[i].reportId+"&reportName="+reportname+"&classId="+data.rows[i].classId+
				 			 "','reportList','100%',500)\">维度设置</a>]&nbsp;&nbsp;"+
					"[<a href=\"#\" onclick=\"delObj('report.do?del&reportId="+
							data.rows[i].reportId+"','删除')\">删除</a>]"
				});
			}
			var newData={"total":total,"rows":rows};
			$('.datagrid-cell-c1-name').css("text-align","center");
			return newData;
		}
	    // 刷新
	    function reloadTable(){
	    	$('#reportList').datagrid('reload');
	    }
	    
	    //搜索
	    function reportSearch() {
	    	var classId = $("#classId").attr("value");//得到查询条件的值
			$('#reportList').datagrid(
							{
								url : 'report.do?datagrid',
								queryParams: {//传递值
									classId: classId
								},
								pageNumber : 1
							});
		}
	    
	    //重置
	    function searchReset(name) {
			$('#classId').val("");//清空查询控件的值

			reportSearch();
		}
	    
	    function EnterPress(e) {
			var e = e || window.event;
			if (e.keyCode == 13) {
				reportSearch();
			}
		}
	
		// 设置datagrid属性
		$('#reportList').datagrid({
			title: '维度列表',
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
	        url:'report.do?datagrid',  
	        toolbar: '#reportTb',//显示查询条件部分
	        loadFilter: function(data){
	        	return getData(data);
	    	}
	     }); 
	    
	    //设置分页控件  
	    $('#reportList').datagrid('getPager').pagination({  
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
