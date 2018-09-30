$(function() {
	// 获取当前已登陆用户并显示用户名
	$('#userName').append(getUserName() + "，"); 
	/*
	 * 正则表达式验证输入信息
	 */
	$.extend($.fn.validatebox.defaults.rules,{
        idcard : {// 验证身份证
        	validator : function(value) {
        	return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value) || /^\d{18}(\d{2}[A-Za-z0-9])?$/i.test(value);
        	},
        	message : '身份证号码格式不正确'
        },
        phoneAndMobile : {// 验证电话号码或手机号码
        	validator : function(value) {
        	return /^((\d2,3)|(\d{3}\-))?(0\d2,3|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value) || /^(13|15|18)\d{9}$/i.test(value);
        	},
        	message : '电话号码或手机号码格式不正确'
        },
        capitalAndChinese:{// 验证姓名，只能是中文或英文
        	validator:function(value,param){
        		return /^[a-zA-Z\u4e00-\u9fa5]+$/i.test(value);
        	},
        	message:'只能输入中文或英文'
        }
     });

});

var type; // 增删改查操作类型：add、edit、delete、search
var pid; // 选中行的Employee主键id

function addEmployee(){
	type = 'add';
    $('#dlg').dialog({
    	title:"New Employee",
    	buttons : [{ 
			 text : 'Save&nbsp&nbsp',
			 iconCls : 'icon-ok',
			 handler : function (){
				 saveEmployee();
			 }
		 },{
			 text : 'Cancel&nbsp',
			 iconCls : 'icon-cancel',
			 handler : function (){
				 $('#dlg').dialog('close');
			 }
		 }],
    })
    $("#idcard").textbox('enable');
	$('#dlg').dialog('open')
	$('#fm').form('clear');
	document.getElementById('sex1').checked=true;
}

function editEmployee(){
	type = 'edit';
    var row = $('#dg').datagrid('getSelected');
    if (row){
//      $('#dlg').dialog('open').dialog('center').dialog('setTitle','Edit Employee');
//      $('#fm').form('load',row);
    	pid = row.pid;
    	$('#dlg').dialog({
        	title:"Edit Employee",
        	buttons : [{ 
    			 text : 'Save&nbsp&nbsp',
    			 iconCls : 'icon-ok',
    			 handler : function (){
    				 saveEmployee();
    			 }
    		 },{
    			 text : 'Cancel&nbsp',
    			 iconCls : 'icon-cancel',
    			 handler : function (){
    				 $('#dlg').dialog('close');
    			 }
    		 }],
        });
        $('#dlg').dialog('open');
    	$('#fm').form('clear');
    	
    	// 加载选中行的Employee信息
    	$('#fm').form('load',{
    		name:row.name,
    		birthday:formatDate(row.birthday),
    		idcard:row.idcard,
    		phone:row.phone,
    		address:row.address
    	});
    	document.getElementById('sex'+row.sex).checked=true;
    	$("#idcard").textbox('disable');
    	
    }else{
    	$.messager.alert('系统提示', '请选择行！','info');
        return;
    }
}

function saveEmployee(){
	
	//获取表单信息
//	var name = $("#name").val();
//	var sex = $("input[name='sex']:checked").val();
//	var birthday = $("#birthday").val();
//	var idcard = $("#idcard").val();
//	var phone = $("#phone").val();
//	var address = $("#address").val();
	
	if(type == 'add'){
		
		$("#fm").form("submit",{
			type: "POST",
			url: "emp/addOrEdit?type=add",
			onSubmit: function(){
            	return $(this).form("validate");//表单验证
            },
	        dataType: "text",
	        success: function(data){
	        	$.messager.alert('提示信息',data,'info');
	            $('#dlg').dialog('close');
	            $("#dg").datagrid("reload");
	        },
	        error: function(){
	        	$.messager.alert('提示信息','Error!','error');
	        }
		});
		
	}else if(type == 'edit'){
		$("#fm").form("submit",{
			type: "POST",
			url: "emp/addOrEdit?type=edit&pid="+pid,
			onSubmit: function(){
            	return $(this).form("validate");//表单验证
            },
	        dataType: "text",
	        success: function(data){
	        	$.messager.alert('提示信息',data,'info');
	            $('#dlg').dialog('close');
	            $("#dg").datagrid("reload");
	        },
	        error: function(){
	        	$.messager.alert('提示信息','Error!','error');
	        }
		})
	}
}

function SearchEmployee(){
//	type = 'search';	
//	$('#dg').datagrid({
//		url: "EmployeeServlet?type=search",
//	});
	
//	在datagrid已经设置了获取Employee信息的url，这里直接刷新表格即可
	$("#dg").datagrid("reload");
}

function delEmployee(){
    var rows = $('#dg').datagrid('getChecked');
    var length = rows.length;
    var Redata;
    if(length > 0){		
        $.messager.confirm('提示信息','确认删除已勾选的员工信息?',function(r){
            if (r){
            	for(var i=0; i<length; i++){
            		$.ajax({
            	        type: "POST",
            	        url: "emp/delete",
            	        data: {"pid": rows[i].pid},
            	        dataType: "text",
            	        success: function(data){
            	        	Redata = data;
            	        	$("#dg").datagrid("reload");
            	        },
            	        error: function(){
            	        	$.messager.alert('提示信息','Error!','error');
            	        }
                	}); 
            	}
            	if(Redata=='success'){
					$.messager.alert('提示信息','删除成功','info');
				}else{
					$.messager.alert('提示信息','删除失败，失败原因【'+Redata+'】','error');
				}
            }
    	})
    }else{
    	$.messager.alert('提示信息','请勾选行!','info');
        return;
    }
}

function getUserName(){
	var strcookie = document.cookie;//获取cookie字符串
	var arrcookie = strcookie.split("; ");//分割
	//遍历匹配
	for ( var i = 0; i < arrcookie.length; i++) {
		var arr = arrcookie[i].split("=");
		if (arr[0] == "loginName"){
			return arr[1];
		}
	}
	return "";
}

/*
 * 数据显示格式化（日期、性别、证件照）
 */
function myformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
function myparser(s){
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}
function formatSex(value){
	if(value == 0){
		return '女' ;
	} else if(value == 1){
		return '男' ; 
	} else {
		return value;
	}
}
function formatDate(value){
	var date = new Date(value);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    return y + '-' +m + '-' + d;
}
function showImg(value, row, index){
	if(row.photo){
		return "<img style='width:50px;height:50px;' src='./" +row.photo+ "'/>";
	}else{
		// 若用户没有上传图片则根据性别显示默认照片
		if(row.sex){
			return "<img style='width:50px;height:50px;' src='default-img/img1.png'/>";
		}else{
			return "<img style='width:50px;height:50px;' src='default-img/img0.png'/>";
		}
	}
}