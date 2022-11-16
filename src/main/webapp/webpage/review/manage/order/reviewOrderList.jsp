<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<script type="text/javascript">
			$(function() {
				$('#reviewOrderList').datagrid({
					idField: 'id',
					title: '公告',
					url: 'reviewOrder.do?datagrid&field=id,userId,mobilePhone,className,orderAmount,orgAmount,orderNo,transactionId,status,payId,payId,payTime,createTime,operateTime,operator,',
					fit: true,
					loadMsg: '数据加载中...',
					pageSize: 10,
					pagination: true,
					pageList: [10, 20, 30],
					sortOrder: 'asc',
					rownumbers: true,
					singleSelect: true,
					fitColumns: true,
					showFooter: true,
					frozenColumns: [[]],
					columns: [[{
						field: 'id',
						title: '编号',
						hidden: true,
						sortable: true
					},
						{
							field: 'userId',
							title: '用户id',
							sortable: true
						},{
							field: 'mobilePhone',
							title: '交易手机号',
							sortable: true
						},
						{
							field: 'className',
							title: '量表名称',
							sortable: true
						},
						{
							field: 'orderAmount',
							title: '订单实付金额',
							sortable: true
						},
						{
							field: 'orgAmount',
							title: '原始价格',
							sortable: true
						},
						{
							field: 'orderNo',
							title: '订单号',
							sortable: true
						},
						{
							field: 'transactionId',
							title: '微信订单号',
							sortable: true
						},
						{
							field: 'status',
							title: '订单状态',
							sortable: true,
							formatter: function(value, rec, index) {
								if (value == 0) {
									return "新建"
								} else if (value == 1) {
									return "待支付"
								} else if (value == 2) {
									return "支付成功待回调"
								} else if (value == 3) {
									return "交易成功"
								} else if (value == 5) {
									return "支付失败"
								} else if (value == 7) {
									return "订单已过期"
								} else {
									return "-"
								}
							}
						},
						{
							field: 'payId',
							title: '预支付id',
							sortable: true
						},
						{
							field: 'payTime',
							title: '支付时间',
							sortable: true,
							formatter: function(value, rec, index) {
								return new Date().format('yyyy-MM-dd hh:mm:ss', value);
							}
						},
						{
							field: 'createTime',
							title: '创建时间',
							sortable: true,
							formatter: function(value, rec, index) {
								return new Date().format('yyyy-MM-dd hh:mm:ss', value);
							}
						},
						{
							field: 'operateTime',
							title: '操作时间',
							sortable: true,
							formatter: function(value, rec, index) {
								return new Date().format('yyyy-MM-dd hh:mm:ss', value);
							}
						},
						{
							field: 'operator',
							title: '操作人',
							sortable: true
						}]],
					onLoadSuccess: function(data) {
						$("#reviewOrderList").datagrid("clearSelections");
					},
					onClickRow: function(rowIndex, rowData) {
						rowid = rowData.id;
						gridname = 'reviewOrderList';
					}
				});
				$('#reviewOrderList').datagrid('getPager').pagination({
					beforePageText: '',
					afterPageText: '/{pages}',
					displayMsg: '{from}-{to}共{total}条',
					showPageList: true,
					showRefresh: true
				});
				$('#reviewOrderList').datagrid('getPager').pagination({
					onBeforeRefresh: function(pageNumber, pageSize) {
						$(this).pagination('loading');
						$(this).pagination('loaded');
					}
				});
			});
			function reloadTable() {
				try {
					$('#' + gridname).datagrid('reload');
					$('#' + gridname).treegrid('reload');
				} catch(ex) {}
			}
			function reloadreviewOrderList() {
				$('#reviewOrderList').datagrid('reload');
			}
			function getreviewOrderListSelected(field) {
				return getSelected(field);
			}
			function getSelected(field) {
				var row = $('#' + gridname).datagrid('getSelected');
				if (row != null) {
					value = row[field];
				} else {
					value = '';
				}
				return value;
			}
			function getreviewOrderListSelections(field) {
				var ids = [];
				var rows = $('#reviewOrderList').datagrid('getSelections');
				for (var i = 0; i < rows.length; i++) {
					ids.push(rows[i][field]);
				}
				ids.join(',');
				return ids
			};
			function getSelectRows() {
				return $('#reviewOrderList').datagrid('getChecked');
			}
			function reviewOrderListsearch() {
				var queryParams = $('#reviewOrderList').datagrid('options').queryParams;
				$('#reviewOrderListtb').find('*').each(function() {
					queryParams[$(this).attr('name')] = $(this).val();
				});
				$('#reviewOrderList').datagrid({
					url: 'reviewOrder.do?datagrid&field=id,userId,mobilePhone,className,orderAmount,orgAmount,orderNo,transactionId,status,payId,payId,payTime,createTime,operateTime,operator,',
					pageNumber: 1
				});
			}
			function dosearch(params) {
				//var jsonparams = $.parseJSON(params);
				var jsonparams = params;
				$('#reviewOrderList').datagrid({
					url: 'reviewOrder.do?datagrid&field=id,userId,mobilePhone,className,orderAmount,orgAmount,orderNo,transactionId,status,payId,payId,payTime,createTime,operateTime,operator,',
					queryParams: jsonparams
				});
			}
			function reviewOrderListsearchbox(value, name) {
				var queryParams = $('#reviewOrderList').datagrid('options').queryParams;
				queryParams[name] = value;
				queryParams.searchfield = name;
				$('#reviewOrderList').datagrid('reload');
			}
			$('#reviewOrderListsearchbox').searchbox({
				searcher: function(value, name) {
					reviewOrderListsearchbox(value, name);
				},
				menu: '#reviewOrderListmm',
				prompt: '请输入查询关键字'
			});
			function EnterPress(e) {
				var e = e || window.event;
				if (e.keyCode == 13) {
					reviewOrderListsearch();
				}
			}
			function searchReset(name) {
				$("#" + name + "tb").find(":input").val("");
				reviewOrderListsearch();
			}
		</script>
		<table width="100%" id="reviewOrderList" toolbar="#reviewOrderListtb">
		</table>
		<div id="reviewOrderListtb" style="padding:3px; height: auto">
			<div name="searchColums">
			</div>
			<div style="height:30px;" class="datagrid-toolbar">
				<span style="float:left;">
					<a href="#" class="easyui-linkbutton" plain="true" icon="icon-search"
					   onclick="detail('查看','reviewOrder.do?addorupdate','reviewOrderList',null,null)">
						查看
					</a>
				</span>
				<span style="float:right">
					<input id="reviewOrderListsearchbox" class="easyui-searchbox" data-options="searcher:reviewOrderListsearchbox,prompt:'请输入关键字',menu:'#reviewOrderListmm'">
					</input>
					<div id="reviewOrderListmm" style="width:120px">
						<div data-options="name:'className',iconCls:'icon-ok'  ">
							量表名称
						</div>
						<div data-options="name:'mobilePhone',iconCls:'icon-ok'  ">
							手机号
						</div>
						<div data-options="name:'orderNo',iconCls:'icon-ok'  ">
							订单号
						</div>
						<div data-options="name:'status',iconCls:'icon-ok'  ">
							订单状态
						</div>
					</div>
				</span>
			</div>
		</div>
	</div>
</div>