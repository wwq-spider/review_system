<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <script type="text/javascript">
            $(function() {
                $('#reviewNoticeList').datagrid({
                    idField: 'id',
                    title: '公告',
                    url: 'reviewNotice.do?datagrid&field=id,noticeName,status,operator,createTime,updateTime,',
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
                            field: 'noticeName',
                            title: '公告名称',
                            width: 50,
                            sortable: true
                        },
                        {
                            field: 'status',
                            title: '公告状态',
                            width: 20,
                            sortable: true,
                            formatter: function(value, rec, index) {
                                if (value == 0) {
                                    return "未发布"
                                } else if (value == 1) {
                                    return "已发布"
                                } else {
                                    return "-"
                                }
                            }
                        },
                        {
                            field: 'operator',
                            title: '操作人',
                            width: 30,
                            sortable: true
                        },
                        {
                            field: 'createTime',
                            title: '创建时间',
                            width: 30,
                            sortable: true,
                            formatter: function(value, rec, index) {
                                return new Date().format('yyyy-MM-dd hh:mm:ss', value);
                            }
                        },
                        {
                            field: 'updateTime',
                            title: '更新时间',
                            width: 30,
                            sortable: true,
                            formatter: function(value, rec, index) {
                                return new Date().format('yyyy-MM-dd hh:mm:ss', value);
                            }
                        },
                        {
                            field: 'opt',
                            title: '操作',
                            width: 60,
                            formatter: function(value, rec, index) {
                                if (!rec.id) {
                                    return '';
                                }
                                let optText = ""
								let pubType = "0"
                                if(rec.status == 1) {
									optText = "下线"
									pubType = "0"
								} else {
									optText = "发布"
									pubType = "1"
								}
                                var href = "[<a href=\"#\" onclick=\"publish('reviewNotice.do?publish&pubType=" + pubType + "&id="
										+ rec.id +"','reviewNoticeList','发布')\">" + optText + "</a>]&nbsp;&nbsp;";
                                href += "[<a href='#' onclick=delObj('reviewNotice.do?del&id="
										+ rec.id + "','reviewNoticeList')>删除</a>]";
                                return href;
                            }
                        }]],
                    onLoadSuccess: function(data) {
                        $("#reviewNoticeList").datagrid("clearSelections");
                    },
                    onClickRow: function(rowIndex, rowData) {
                        rowid = rowData.id;
                        gridname = 'reviewNoticeList';
                    }
                });
                $('#reviewNoticeList').datagrid('getPager').pagination({
                    beforePageText: '',
                    afterPageText: '/{pages}',
                    displayMsg: '{from}-{to}共{total}条',
                    showPageList: true,
                    showRefresh: true
                });
                $('#reviewNoticeList').datagrid('getPager').pagination({
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
            function reloadreviewNoticeList() {
                $('#reviewNoticeList').datagrid('reload');
            }
            function getreviewNoticeListSelected(field) {
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
            function getreviewNoticeListSelections(field) {
                var ids = [];
                var rows = $('#reviewNoticeList').datagrid('getSelections');
                for (var i = 0; i < rows.length; i++) {
                    ids.push(rows[i][field]);
                }
                ids.join(',');
                return ids
            };
            function getSelectRows() {
                return $('#reviewNoticeList').datagrid('getChecked');
            }
            function reviewNoticeListsearch() {
                var queryParams = $('#reviewNoticeList').datagrid('options').queryParams;
                $('#reviewNoticeListtb').find('*').each(function() {
                    queryParams[$(this).attr('name')] = $(this).val();
                });
                $('#reviewNoticeList').datagrid({
                    url: 'reviewNotice.do?datagrid&field=id,noticeName,status,operator,createTime,updateTime,',
                    pageNumber: 1
                });
            }
            function dosearch(params) {
                var jsonparams = $.parseJSON(params);
                $('#reviewNoticeList').datagrid({
                    url: 'reviewNotice.do?datagrid&field=id,noticeName,status,operator,createTime,updateTime,',
                    queryParams: jsonparams
                });
            }
            function reviewNoticeListsearchbox(value, name) {
                var queryParams = $('#reviewNoticeList').datagrid('options').queryParams;
                queryParams[name] = value;
                queryParams.searchfield = name;
                $('#reviewNoticeList').datagrid('reload');
            }
            $('#reviewNoticeListsearchbox').searchbox({
                searcher: function(value, name) {
                    reviewNoticeListsearchbox(value, name);
                },
                menu: '#reviewNoticeListmm',
                prompt: '请输入查询关键字'
            });
            function EnterPress(e) {
                var e = e || window.event;
                if (e.keyCode == 13) {
                    reviewNoticeListsearch();
                }
            }
            function searchReset(name) {
                $("#" + name + "tb").find(":input").val("");
                reviewNoticeListsearch();
            }
        </script>
        <table width="100%" id="reviewNoticeList" toolbar="#reviewNoticeListtb">
        </table>
        <div id="reviewNoticeListtb" style="padding:3px; height: auto">
            <div style="height:30px;" class="datagrid-toolbar">
				<span style="float:left;">
					<a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="add('录入','reviewNotice.do?addorupdate','reviewNoticeList',null,null)">
						录入
					</a>
					<a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="update('编辑','reviewNotice.do?addorupdate','reviewNoticeList',null,null)">
						编辑
					</a>
					<a href="#" class="easyui-linkbutton" plain="true" icon="icon-search"
                       onclick="detail('查看','reviewNotice.do?addorupdate','reviewNoticeList',null,null)">
						查看
					</a>
				</span>
            </div>
        </div>
    </div>
</div>