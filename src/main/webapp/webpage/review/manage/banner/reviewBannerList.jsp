<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <script type="text/javascript">
            $(function() {
                $('#reviewBannerList').datagrid({
                    idField: 'id',
                    title: '轮播图',
                    url: 'reviewBanner.do?datagrid&field=id,title,imgUrl,targetUrl,status,createTime,operateTime,operator,',
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
                            field: 'title',
                            title: '标题',
                            width: 15,
                            sortable: true
                        },
                        {
                            field: 'imgUrl',
                            title: '图片路径',
                            width: 30,
                            sortable: true
                        },
                        {
                            field: 'targetUrl',
                            title: '目标跳转路径',
                            width: 20,
                            sortable: true
                        },
                        {
                            field: 'status',
                            title: '状态',
                            sortable: true,
                            width: 10,
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
                            field: 'createTime',
                            title: '创建时间',
                            sortable: true,
                            width: 10,
                            formatter: function(value, rec, index) {
                                return new Date().format('yyyy-MM-dd hh:mm:ss', value);
                            }
                        },
                        {
                            field: 'operateTime',
                            title: '操作时间',
                            sortable: true,
                            width: 10,
                            formatter: function(value, rec, index) {
                                return new Date().format('yyyy-MM-dd hh:mm:ss', value);
                            }
                        },
                        {
                            field: 'operator',
                            title: '操作人',
                            width: 10,
                            sortable: true
                        },
                        {
                            field: 'opt',
                            title: '操作',
                            width: 15,
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
                                var href = "[<a href=\"#\" onclick=\"publish('reviewBanner.do?publish&pubType=" + pubType + "&id="
                                    + rec.id +"','reviewBannerList','发布')\">" + optText + "</a>]&nbsp;&nbsp;";
                                href += "[<a href='#' onclick=delObj('reviewBanner.do?del&id=" + rec.id + "','reviewBannerList')>删除</a>]";
                                return href;
                            }
                        }]],
                    onLoadSuccess: function(data) {
                        $("#reviewBannerList").datagrid("clearSelections");
                    },
                    onClickRow: function(rowIndex, rowData) {
                        rowid = rowData.id;
                        gridname = 'reviewBannerList';
                    }
                });
                $('#reviewBannerList').datagrid('getPager').pagination({
                    beforePageText: '',
                    afterPageText: '/{pages}',
                    displayMsg: '{from}-{to}共{total}条',
                    showPageList: true,
                    showRefresh: true
                });
                $('#reviewBannerList').datagrid('getPager').pagination({
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
            function reloadreviewBannerList() {
                $('#reviewBannerList').datagrid('reload');
            }
            function getreviewBannerListSelected(field) {
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
            function getreviewBannerListSelections(field) {
                var ids = [];
                var rows = $('#reviewBannerList').datagrid('getSelections');
                for (var i = 0; i < rows.length; i++) {
                    ids.push(rows[i][field]);
                }
                ids.join(',');
                return ids
            };
            function getSelectRows() {
                return $('#reviewBannerList').datagrid('getChecked');
            }
            function reviewBannerListsearch() {
                var queryParams = $('#reviewBannerList').datagrid('options').queryParams;
                $('#reviewBannerListtb').find('*').each(function() {
                    queryParams[$(this).attr('name')] = $(this).val();
                });
                $('#reviewBannerList').datagrid({
                    url: 'reviewBanner.do?datagrid&field=id,title,imgUrl,targetUrl,status,createTime,operateTime,operator,',
                    pageNumber: 1
                });
            }
            function dosearch(params) {
                var jsonparams = $.parseJSON(params);
                $('#reviewBannerList').datagrid({
                    url: 'reviewBanner.do?datagrid&field=id,title,imgUrl,targetUrl,status,createTime,operateTime,operator,',
                    queryParams: jsonparams
                });
            }
            function reviewBannerListsearchbox(value, name) {
                var queryParams = $('#reviewBannerList').datagrid('options').queryParams;
                queryParams[name] = value;
                queryParams.searchfield = name;
                $('#reviewBannerList').datagrid('reload');
            }
            $('#reviewBannerListsearchbox').searchbox({
                searcher: function(value, name) {
                    reviewBannerListsearchbox(value, name);
                },
                menu: '#reviewBannerListmm',
                prompt: '请输入查询关键字'
            });
            function EnterPress(e) {
                var e = e || window.event;
                if (e.keyCode == 13) {
                    reviewBannerListsearch();
                }
            }
            function searchReset(name) {
                $("#" + name + "tb").find(":input").val("");
                reviewBannerListsearch();
            }
        </script>
        <table width="100%" id="reviewBannerList" toolbar="#reviewBannerListtb">
        </table>
        <div id="reviewBannerListtb" style="padding:3px; height: auto">
            <div style="height:30px;" class="datagrid-toolbar">
            <span style="float:left;">
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="add('录入','reviewBanner.do?addorupdate','reviewBannerList',null,null)">
                    录入
                </a>
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="update('编辑','reviewBanner.do?addorupdate','reviewBannerList',null,null)">
                    编辑
                </a>
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search"
                   onclick="detail('查看','reviewBanner.do?addorupdate','reviewBannerList',null,null)">
                    查看
                </a>
            </span>
            </div>
        </div>
    </div>
</div>