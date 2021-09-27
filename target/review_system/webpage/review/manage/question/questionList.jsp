<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">

		<table id="questionList" style="width: 700px; height: 300px">
			<thead>
				<tr>
					<th field="questionId" hidden="hidden">编号</th>
					<th field="content" width="100">题目内容</th>
					<th field="reviewType" width="50">测评类型</th>
					<th field="createTime" width="40">创建时间</th>
					<th field="createBy" width="40">创建人</th>
					<th field="opt" width="50">操作</th>
				</tr>
			</thead>
		</table>
		<div style="padding: 3px; height: auto;" id="questionTb" class="datagrid-toolbar">
			<div name="searchColums">
				<span>
					<span title="题目内容"
					style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; ">题目内容:</span>
					<input type="text" name="content" id="content" ></input>
				</span>
				<span style="display:-moz-inline-box;display:inline-block;">
					<span title="测评类型"
					style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; ">
						测评类型： </span> 
						<input class="easyui-combobox" id="reviewType" name="reviewType"/>
				</span>
				<span style="display:-moz-inline-box;display:inline-block;">
					<span title="题目类型"
					style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; ">
						题目类型： </span> 
						<input class="easyui-combobox" id="questionType" name="questionType"/>
					</select>
				</span>
			 </div>
			 <div class="datagrid-toolbar" style="height:30px;">
				<span style="float:left;">
					<a href="#" id='add' class="easyui-linkbutton" plain="true" icon="icon-add"
					onclick="add('添加试题','review.do?toAdd','questionList')" id="">添加题目</a></span>
				<span style="float:right">
					<a onclick="questionSearch();" iconcls="icon-search" class="easyui-linkbutton l-btn" href="#"
						id="">查询</a>
					<a onclick="searchReset()" iconcls="icon-reload"
						class="easyui-linkbutton l-btn" href="#" id="">重置</a>
				</span>
			</div>
		</div>
	<script type="text/javascript">
	
		$(function(){
			$("#reviewType").combobox({
				valueField: 'id',
				textField: 'value',
				data: rTypeArr	
			});
			
			$("#questionType").combobox({
					valueField: 'id',
					textField: 'value',
					data: qTypeArr	
			});
		})
		var rTypeArr = [ {
			id : "1",
			value : "心理特征测试"
		}, {
			id : "2",
			value : "基本能力测试"
		} ];

		var qTypeArr = [ {
			id : "1",
			value : "焦虑"
		}, {
			id : "2",
			value : "抑郁"
		}, {
			id : "3",
			value : "精神病性"
		}, {
			id : "4",
			value : "人际关系"
		}, {
			id : "5",
			value : "敌对"
		}, {
			id : "6",
			value : "恐惧"
		}, {
			id : "7",
			value : "道德"
		} ];
		// 编辑初始化数据
		function getData(data) {
			$('.datagrid-header-inner .datagrid-cell ').css("text-align",
					"center");
			var rows = [];
			var total = data.total;
			var qType;

			for ( var i = 0; i < data.rows.length; i++) {
				rows.push({
							questionId : data.rows[i].questionId,
							content : data.rows[i].content,
							reviewType : "心理特征测试"

							createTime : data.rows[i].createTime,
							createBy : data.rows[i].createBy,
							opt : "[<a href=\"#\" onclick=\"createwindow('编辑','review.do?toAdd&questionId="
									+ data.rows[i].questionId
									+ "')\">编辑</a>]&nbsp;&nbsp;"
									+ "[<a href=\"#\" onclick=\"delObj('review.do?del&questionId="
									+ data.rows[i].questionId
									+ "','删除')\">删除</a>]"
						});
			}
			var newData = {
				"total" : total,
				"rows" : rows
			};
			$('.datagrid-cell-c1-name').css("text-align", "center");
			return newData;
		}
		// 刷新
		function reloadTable() {
			$('#questionList').datagrid('reload');
		}

		//搜索
		function questionSearch() {
			var content = $("#content").val();
			var reviewType = $("#reviewType").combobox("getValue");
			var questionType = $("#questionType").combobox("getValue");
			
			$('#questionList').datagrid({
				url : 'review.do?datagrid',
				queryParams : {//传递值
					content : content,
					reviewType : reviewType,
					questionType : questionType
				},
				pageNumber : 1
			});
		}

		//重置
		function searchReset(name) {
			$('#content').val("");//清空查询控件的值
			$("#reviewType").combobox("setValue","");
			$("#questionType").combobox("setValue","");
			questionSearch();
		}

		function EnterPress(e) {
			var e = e || window.event;
			if (e.keyCode == 13) {
				questionSearch();
			}
		}

		// 设置datagrid属性
		$('#questionList').datagrid({
			title : '分类列表',
			idField : 'id',
			fit : true,
			loadMsg : '数据加载中...',
			pageSize : 20,
			pagination : true,
			sortOrder : 'asc',
			rownumbers : true,
			singleSelect : true,
			fitColumns : true,
			showFooter : true,
			url : 'review.do?datagrid&classId=${classId}',
			toolbar : '#questionTb',//显示查询条件部分
			loadFilter : function(data) {
				return getData(data);
			}
		});

		//设置分页控件  
		$('#questionList').datagrid('getPager').pagination({
			pageSize : 20,
			pageList : [ 10, 20, 30 ],
			beforePageText : '',
			afterPageText : '/{pages}',
			displayMsg : '{from}-{to}共{total}条',
			showPageList : true,
			showRefresh : true,
			onBeforeRefresh : function(pageNumber, pageSize) {
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
