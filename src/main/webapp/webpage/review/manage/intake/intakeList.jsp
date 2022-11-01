<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <table id="intakeList" style="width: 700px; height: 300px">
      <thead>
      <tr>
        <th field="id" hidden="hidden">接线ID</th>
        <th field="projectName" width="80">公司名称</th>
        <th field="projectDesc" width="200">组织机构</th>
        <th field="creator" width="50">员工工号</th>
        <th field="createTime" width="50">创建时间</th>
        <th field="updateTime" width="50">更新时间</th>
        <th field="opt" width="100">操作</th>
      </tr>
      </thead>
    </table>
    <div style="padding: 3px; height: auto;" id="intakeTB" class="datagrid-toolbar">
      <div class="datagrid-toolbar" style="height:30px;">
				<span style="float:left;">
					<a href="#" id='add' class="easyui-linkbutton" plain="true" icon="icon-add"
                       onclick="add('接线录入','intakeController.do?addIntake','intakeList', 850, 650)" id="">接线录入</a></span>
      </div>
    </div>
    <script type="text/javascript">

      // 编辑初始化数据
      function getData(data){
        $('.datagrid-header-inner .datagrid-cell ').css("text-align","center");
        let rows = [];
        let total = data.total;

        for(let i=0; i<data.rows.length; i++){
          let edit = "[<a href=\"#\" onclick=\"createwindow('编辑', 'reviewProject.do?toAdd&projectId="+
                  data.rows[i].id+"', 800, 750)\">编辑</a>]";
          rows.push({
            id: data.rows[i].id,
            projectName: data.rows[i].projectName,
            projectDesc: data.rows[i].projectDesc,
            createTime: data.rows[i].createTime,
            updateTime: data.rows[i].updateTime,
            creator: data.rows[i].creator,
            opt: edit+"&nbsp;&nbsp;[<a href=\"#\" onclick=\"delObj('reviewProject.do?del&projectId="+data.rows[i].id+"','删除')\">删除</a>]"
          });
        }
        var newData={"total":total,"rows":rows};
        $('.datagrid-cell-c1-name').css("text-align","center");
        return newData;
      }
      //刷新
      function reloadTable(){
        $('#intakeList').datagrid('reload');
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
      $('#intakeList').datagrid({
        title: '录入列表',
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
        url:'intakeController.do?queryIntakeRecord',
        toolbar: '#intakeTB',//显示查询条件部分
        loadFilter: function(data){
          return getData(data);
        }
      });

      //设置分页控件
      $('#intakeList').datagrid('getPager').pagination({
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
