<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<script type="text/javascript">
    $(function() {
        $('#reviewExpertList').datagrid({
            idField: 'id',
            title: '测评专家模块',
            url: 'reviewExpertController.do?datagrid&field=id,expertName,sex,age,mobilePhone,jobTitle,workOrgName,status,createTime,updateTime,creator,',
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
                    field: 'expertName',
                    title: '专家姓名',
                    sortable: true
                },
                {
                    field: 'sex',
                    title: '性别',
                    sortable: true,
                    formatter: function(value, rec, index) {
                        if(value == 1) {
                            return "男"
                        } else if(value == 2) {
                            return "女"
                        }
                        return "--"
                    }
                },
                {
                    field: 'age',
                    title: '年龄',
                    sortable: true
                },
                {
                    field: 'mobilePhone',
                    title: '专家手机号',
                    sortable: true
                },
                {
                    field: 'jobTitle',
                    title: '职称',
                    sortable: true
                },
                {
                    field: 'workOrgName',
                    title: '工作机构名称',
                    sortable: true
                },
                {
                    field: 'status',
                    title: '状态',
                    sortable: false,
                    formatter: function(value, rec, index) {
                        if(value == 0) {
                            return "未发布"
                        } else if(value == 1) {
                            return "已发布"
                        }
                        return "--"
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
                    field: 'updateTime',
                    title: '更新时间',
                    sortable: true,
                    formatter: function(value, rec, index) {
                        return new Date().format('yyyy-MM-dd hh:mm:ss', value);
                    }
                },
                {
                    field: 'creator',
                    title: '创建人',
                    sortable: true
                },
                {
                    field: 'opt',
                    title: '操作',
                    width: 100,
                    formatter: function(value, rec, index) {
                        if (!rec.id) {
                            return '';
                        }
                        let href = "[<a href='#' onclick=delObj('reviewExpertController.do?del&id=" + rec.id + "','reviewExpertList')>删除</a>]&nbsp;&nbsp;";
                        /*href += "[<a href='#' onclick=\"add('专家日历','reviewExpertController.do?toCalendarSet&id=" + rec.id + "&expertName=" +rec.expertName+ "',900,800)\" >日历设置</a>]";*/
                        href += "[<a href='#' class='easyui-linkbutton' plain='true' icon='icon-add' onclick=\"add('专家日历','reviewExpertController.do?toCalendarSet&id=" + rec.id + "&expertName=" +rec.expertName+ "',900,800)\">日历设置</a>]";
                        /*href += "[<a href='#' onclick=\"createdetailwindow('专家日历','reviewExpertController.do?toCalendarSet&id=" + rec.id + "',800,700)\" >日历设置</a>]";*/

                        return href;
                    }
                }]],
            onLoadSuccess: function(data) {
                $("#reviewExpertList").datagrid("clearSelections");
            },
            onClickRow: function(rowIndex, rowData) {
                rowid = rowData.id;
                gridname = 'reviewExpertList';
            }
        });
        $('#reviewExpertList').datagrid('getPager').pagination({
            beforePageText: '',
            afterPageText: '/{pages}',
            displayMsg: '{from}-{to}共{total}条',
            showPageList: true,
            showRefresh: true
        });
        $('#reviewExpertList').datagrid('getPager').pagination({
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
    function reloadreviewExpertList() {
        $('#reviewExpertList').datagrid('reload');
    }
    function getreviewExpertListSelected(field) {
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
    function getreviewExpertListSelections(field) {
        var ids = [];
        var rows = $('#reviewExpertList').datagrid('getSelections');
        for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i][field]);
        }
        ids.join(',');
        return ids
    };
    function getSelectRows() {
        return $('#reviewExpertList').datagrid('getChecked');
    }
    function reviewExpertListsearch() {
        var queryParams = $('#reviewExpertList').datagrid('options').queryParams;
        $('#reviewExpertListtb').find('*').each(function() {
            queryParams[$(this).attr('name')] = $(this).val();
        });
        $('#reviewExpertList').datagrid({
            url: 'reviewExpertController.do?datagrid&field=id,expertName,sex,age,mobilePhone,jobTitle,workOrgName,createTime,updateTime,creator,',
            pageNumber: 1
        });
    }
    function dosearch(params) {
        var jsonparams = $.parseJSON(params);
        $('#reviewExpertList').datagrid({
            url: 'reviewExpertController.do?datagrid&field=id,expertName,sex,age,mobilePhone,jobTitle,workOrgName,createTime,updateTime,creator,',
            queryParams: jsonparams
        });
    }
    function reviewExpertListsearchbox(value, name) {
        var queryParams = $('#reviewExpertList').datagrid('options').queryParams;
        queryParams[name] = value;
        queryParams.searchfield = name;
        $('#reviewExpertList').datagrid('reload');
    }
    $('#reviewExpertListsearchbox').searchbox({
        searcher: function(value, name) {
            reviewExpertListsearchbox(value, name);
        },
        menu: '#reviewExpertListmm',
        prompt: '请输入查询关键字'
    });
    function EnterPress(e) {
        var e = e || window.event;
        if (e.keyCode == 13) {
            reviewExpertListsearch();
        }
    }
    function searchReset(name) {
        $("#" + name + "tb").find(":input").val("");
        reviewExpertListsearch();
    }
</script>

<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <table width="100%" id="reviewExpertList" toolbar="#reviewExpertListtb"></table>
        <div id="reviewExpertListtb" style="padding:3px; height: auto">
            <div style="height:30px;" class="datagrid-toolbar">
                <span style="float:left;">
                    <a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="pubOrSave('新增专家','reviewExpertController.do?addorupdate','reviewExpertList',700,500)">新增专家</a>
                    <a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="pubOrUpd('编辑专家','reviewExpertController.do?addorupdate','reviewExpertList',700,500)">编辑专家</a>
                    <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" onclick="detail('查看','reviewExpertController.do?addorupdate','reviewExpertList',700,500)">查看</a></span>
            </div>
        </div>
    </div>
</div>