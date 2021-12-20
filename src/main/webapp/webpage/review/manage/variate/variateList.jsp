<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">

		<table id="variateList" style="width: 700px; height: 300px">
			<thead>
				<tr>
					<th field="variateId" hidden="hidden">编号</th>
					<th field="sortNum" hidden="hidden">序号</th>
					<th field="classId" hidden="hidden">序号</th>
					<th field="className" width="70">分类名称</th>
					<th field="variateName" width="50">因子名称</th>
					<th field="createTime" width="50">创建时间</th>
					<th field="createBy" width="40">创建人</th>
					<th field="opt" width="100">操作</th>
				</tr>
			</thead>
		</table>
		<div style="padding: 3px; height: auto;" id="variateTb" class="datagrid-toolbar">
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
					onclick="add('添加因子','variate.do?toAdd','variateList', 800, 500)" id="">添加因子</a></span>
				<span style="float:right">
					<a onclick="variateSearch()" iconcls="icon-search" class="easyui-linkbutton l-btn" href="#"
						id="">查询</a>
					<a onclick="searchReset()" iconcls="icon-reload"
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
			var variatename;	
			for(var i=0; i<data.rows.length; i++){
				variatename = encodeURI(encodeURI(data.rows[i].variateName));
				rows.push({
					variateId: data.rows[i].variateId,
					classId: data.rows[i].classId,
					className: data.rows[i].className,
					sortNum: data.rows[i].sortNum,
					variateName: data.rows[i].variateName,
					createTime: data.rows[i].createTime,
					createBy: data.rows[i].createBy,
					opt: "[<a href=\"#\" onclick=\"add('计分设置','variate.do?toScore&variateId="+data.rows[i].variateId+"&variateName="+variatename+"&classId="+data.rows[i].classId+"','variateList','100%',500)\">计分设置</a>]&nbsp;&nbsp;"+
						 "[<a href=\"#\" onclick=\"add('因子编辑','variate.do?toAdd&variateId="+data.rows[i].variateId+"','variateList',800,500)\">编辑</a>]&nbsp;&nbsp;[<a href=\"#\" onclick=\"delObj('variate.do?del&variateId="+
							data.rows[i].variateId+"','删除')\">删除</a>]&nbsp;&nbsp;[<a href=\"#\" onclick=\"moveUp('"+data.rows[i].variateId+"');\">上移</a>]"+
						 "[<a href=\"#\" onclick=\"moveDown('"+data.rows[i].variateId+"');\">下移</a>]"
				});
			}
			var newData={"total":total,"rows":rows};
			$('.datagrid-cell-c1-name').css("text-align","center");
			return newData;
		}
	    
	    
		//上移
		function moveUp(variateId) {
			$("#variateList").datagrid("selectRecord",variateId);
			var row = $("#variateList").datagrid("getSelected");
	    	var index = $("#variateList").datagrid("getRowIndex",row);
	    	//$("#variateList").datagrid("clearSelections");
	    	var rowDown1 = $("#variateList").datagrid("getData").rows[index];
			var rowUp1 = $("#variateList").datagrid("getData").rows[index-1];
			if(rowDown1.classId == rowUp1.classId) {
				if(index > 0) {
					/* alert(index);
					var rowDown1 = $("#variateList").datagrid("getData").rows[index];
					var rowUp1 = $("#variateList").datagrid("getData").rows[index-1];
					
					var idIndex1 = rowDown1.variateId+"_"+rowDown1.sortNum+","+rowUp1.variateId+"_"+rowUp1.sortNum;
					alert(idIndex1); */
					mysort(index, 'up', 'variateList');
					var rowDown = $("#variateList").datagrid("getData").rows[index];
					var rowUp = $("#variateList").datagrid("getData").rows[index-1];
					
					var idIndex = rowDown.variateId+"_"+rowUp.sortNum+","+rowUp.variateId+"_"+rowDown.sortNum;
					//alert(idIndex);
					$.post("variate.do?sortVariate",{"sortNums":idIndex}, function(data) {
						$("#variateList").datagrid("reload");
					});
				}
			} else {
				$.messager.alert("提示","所属分类不同，不能上下移动","info");
				return;
			}
			
		}
		
		//下移
		function moveDown(variateId) {
			$("#variateList").datagrid("selectRecord",variateId);
			var row = $("#variateList").datagrid("getSelected");
	    	var index = $("#variateList").datagrid("getRowIndex",row);
	    	
	    	var rowDown1 = $("#variateList").datagrid("getData").rows[index + 1];
			var rowUp1 = $("#variateList").datagrid("getData").rows[index];
			//alert(rowDown1.classId +"---"+ rowUp1.classId);
			if(rowDown1.classId == rowUp1.classId) {
		    	//$("#variateList").datagrid("clearSelections");
		    	mysort(index, 'down', 'variateList');
				var rows = $("#variateList").datagrid("getRows");
				if(rows.length - 1 > index) {
					var rowDown = $("#variateList").datagrid("getData").rows[index + 1];
					var rowUp = $("#variateList").datagrid("getData").rows[index];
					
					var idIndex = rowDown.variateId+"_"+rowUp.sortNum+","+rowUp.variateId+"_"+rowDown.sortNum;
					$.post("variate.do?sortVariate",{"sortNums":idIndex}, function(data) {
						$("#variateList").datagrid("reload");
					});
				}
			} else {
				$.messager.alert("提示","所属分类不同，不能上下移动","info");
				return;
			}			
		}
		
		//上移下移排序
		function mysort(index, type, gridname) {
		    if ("up" == type) {
		        if (index != 0) {
		            var toup = $('#' + gridname).datagrid('getData').rows[index];
		            var todown = $('#' + gridname).datagrid('getData').rows[index - 1];
		            $('#' + gridname).datagrid('getData').rows[index] = todown;
		            $('#' + gridname).datagrid('getData').rows[index - 1] = toup;
		            $('#' + gridname).datagrid('refreshRow', index);
		            $('#' + gridname).datagrid('refreshRow', index - 1);
		            //$('#' + gridname).datagrid('selectRow', index - 1);
		        }
		    } else if ("down" == type) {
		        var rows = $('#' + gridname).datagrid('getRows').length;
		        if (index != rows - 1) {
		            var todown = $('#' + gridname).datagrid('getData').rows[index];
		            var toup = $('#' + gridname).datagrid('getData').rows[index + 1];
		            $('#' + gridname).datagrid('getData').rows[index + 1] = todown;
		            $('#' + gridname).datagrid('getData').rows[index] = toup;
		            $('#' + gridname).datagrid('refreshRow', index);
		            $('#' + gridname).datagrid('refreshRow', index + 1);
		            //$('#' + gridname).datagrid('selectRow', index + 1);
		        }
		    }
		 
		}
	    
	    
	    // 刷新
	    function reloadTable(){
	    	$('#variateList').datagrid('reload');
	    }
	    
	    //搜索
	    function variateSearch() {
	    	var classId = $("#classId").attr("value");//得到查询条件的值
			$('#variateList').datagrid(
							{
								url : 'variate.do?datagrid',
								queryParams: {//传递值
									classId: classId
								},
								pageNumber : 1
							});
		}
	    
	    //重置
	    function searchReset(name) {
			$('#classId').val("");//清空查询控件的值

			variateSearch();
		}
	    
	    function EnterPress(e) {
			var e = e || window.event;
			if (e.keyCode == 13) {
				variateSearch();
			}
		}
	
		// 设置datagrid属性
		$('#variateList').datagrid({
			title: '因子列表',
	        idField: 'variateId',
	        fit:true,
	        loadMsg: '数据加载中...',
	        pageSize: 20,
	        pagination : true,
	        sortOrder:'asc',
	        rownumbers:true,
	        singleSelect:true,
	        fitColumns:true,
	        showFooter:true,
	        url:'variate.do?datagrid',  
	        toolbar: '#variateTb',//显示查询条件部分
	        loadFilter: function(data){
	        	return getData(data);
	    	}
	     }); 
	    
	    //设置分页控件  
	    $('#variateList').datagrid('getPager').pagination({  
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
