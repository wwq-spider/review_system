<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true" id="tabs">
    <div region="center" style="padding: 1px">
        <input type="hidden" id="btn_sub" class="btn_sub"/>
        <table id="infoVerificatList" url="intakeController.do?getClientInfo">
            <thead>
                <tr>
                    <th field="id" hidden="hidden" width="10px"> id</th>
                    <th field="companyName" width="50px"> 公司名称</th>
                    <th field="employeeName" width="30px"> 员工姓名</th>
                    <th field="sex" width="10px"> 员工性别</th>
                </tr>
            </thead>
        </table>
    </div>
</div>
</div>
<script type="text/javascript">
    // 编辑初始化数据
    function getData(data){
        $('.datagrid-header-inner .datagrid-cell ').css("text-align","center");
        let rows = [];
        let total = data.total;
        if(data.rows){
            for(let i=0; i<data.rows.length; i++){
                rows.push({
                    id: data.rows[i].id,
                    companyName: data.rows[i].companyName,
                    employeeName: data.rows[i].employeeName,
                    sex: data.rows[i].sex
                });
            }
        }
        var newData={"total":total,"rows":rows};
        $('.datagrid-cell-c1-name').css("text-align","center");
        return newData;
    }

    // 设置datagrid属性
    $('#infoVerificatList').datagrid({
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
        url:'intakeController.do?getClientInfo&employeePhone='+"${employeePhone}" +'&employeeName=' + "${employeeName}",
        loadFilter: function(data){
            return getData(data);
        },
        onLoadSuccess: function(data) {
            $("#infoVerificatList").datagrid("clearSelections");
        },
        onClickRow: function(rowIndex, rowData) {
            rowid = rowData.id;
            gridname = 'infoVerificatList';
        }
    });

    //设置分页控件
    $('#infoVerificatList').datagrid('getPager').pagination({
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
    $("#btn_sub").click(function() {
        var rowsData = $('#infoVerificatList').datagrid('getSelections');
        if (!rowsData || rowsData.length == 0) {
            tip('请选择一条记录');
            return;
        }
        if (rowsData.length > 1) {
            tip('请选择一条记录');
            return;
        }
        var id = rowsData[0].id;
        var companyName = rowsData[0].companyName;
        var employeeName = rowsData[0].employeeName;
        var sex = rowsData[0].sex;
        frameElement.api.close();
        var url = 'intakeController.do?addIntake&id='+id + "&companyName=" + companyName + "&employeeName=" + employeeName + "&sex=" +sex;
        add('接线录入',url,'intakeList', 850, 650);
    });
</script>
<style>
    table tr td div {
        text-align: center !important;
    }
</style>
