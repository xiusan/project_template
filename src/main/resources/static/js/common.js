// 设置selectpicker的显示样式
function setSelectpicker(id, size, style) {
    $(id).selectpicker({
        style: style || 'btn-info',
        size: size || 5 // 菜单将显示项目的给定数量，即使下拉被切断。
    });
}
// 操作selectpicker
function changeSelectpicker(id, type) {
    if (type) {
        $(id).selectpicker(type);
    }
}

function setBootstrapTable(id, bootstrapTableOption) {
    $(id).bootstrapTable(
        $.extend(true,
            {url: undefined,
            queryParamsType: '',
            queryParams: function (params) { return params;},
            dataField: 'list',
            columns: [[]],
            paginationLoop: false,
            striped: true,
            pagination: true,
            sidePagination: 'server',
            pageSize: 10,
            pageList: [5, 10, 25, 50, 100],
            paginationPreText: "上一页",
            paginationNextText: "下一页",
            buttonsClass: 'select'},
            bootstrapTableOption
        )
    );
}

function increases(value, row, index) {
    return index + 1;
}

function operateBootstrapTable(id, prototype, params) {
    $(id).bootstrapTable(prototype, params);
}

function $ajax(url, callback, data, callbackParam, type, dataType, errorCallback) {
    $.ajax({
        url : url,
        type : type || 'post',
        dataType : dataType || 'json',
        data : data,
        success : function(data) {
            if (typeof callback === 'function') {
                if (callbackParam) {
                    callback(callbackParam, data);
                } else {
                    callback(data);
                }
            }
        },
        error : function(data) {
            if (typeof errorCallback === 'function') {
                errorCallback("error");
            } else {
                if (data.status == "403" && data.statusText == "Forbidden") {
                    showError(messageContent["Access.Forbidden"]);
                } else {
                    showError(messageContent["Access.Failed"]);
                }
            }
        },
        timeout: function(data){
            if (typeof errorCallback === 'function') {
                errorCallback("timeout");
            } else {
                showError(messageContent["Access.Timeout"]);
            }
        }
    });
}
//**********date************
function addDay(date, days){
	var intDays = parseInt(days, 10);
	if (!isNaN(intDays)) {
		return date.setDate(date.getDate() + intDays);
	} else {
		return date;
	}
}

function stringToDate(date, symbol){
	if(date.length <= 10){
		if (!symbol) {
			symbol = "-";
		}
		var array = date.split(symbol);
		return new Date(array[0], array[1] - 1, array[2]);
	} else {
		var array = date.split(/[^0-9]/ig);
		return new Date(array[0], array[1] - 1, array[2], array[3] || 0, array[4] || 0, array[5] || 0);
	}
}

//数据库日期转化为JSON在页面显示
function date2String(time){
	if (time) {
		var datetime = getSystemTime();
	    datetime.setTime(time);
	    var year = datetime.getFullYear();
	    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();;
	    return year + "-" + month + "-" + date;
	} else {
		return "";
	}
}

//数据库时间转化为JSON在页面显示
function timeStamp2String(time, model){
	 if (time) {
		 var datetime = getSystemTime();
		 datetime.setTime(time);
		 var year = datetime.getFullYear();
		 var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : "" + (datetime.getMonth() + 1);
		 var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : "" + datetime.getDate();
		 var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : "" + datetime.getHours();
		 var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : "" + datetime.getMinutes();
		 var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : "" + datetime.getSeconds();
		 if (model == 1) {
			 return year + month + date + hour + minute + second;
		 } else {
			 return year + "-" + month + "-" + date +" "+ hour + ":" + minute + ":" + second;
		 }
	 } else {
		 return "";
	 }
}

function time2String(time, model){
	 if (time) {
		 var datetime = getSystemTime();
		 datetime.setTime(time);
		 var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : "" + datetime.getHours();
		 var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : "" + datetime.getMinutes();
		 var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : "" + datetime.getSeconds();
		 if (model == 1) {
			 return hour + minute + second;
		 } else {
			 return hour + ":" + minute + ":" + second;
		 }
	 } else {
		 return "";
	 }
}

function formatTime(time) {
	if (time) {
		var second = parseInt(time, 10); // 秒
	    var minute  = 0; // 分
	    var hour = 0; // 时
	    if(second > 60) {
	    	minute = parseInt(second / 60, 10);
	    	second = second % 60;
	        if(minute > 60) {
	        	hour = parseInt(minute / 60, 10);
	        	minute = minute % 60;
	        	return hour + "小时" + minute + "分" + second + "秒";
	        } else {
	        	return minute + "分" + second + "秒";
	        }
	    } else {
	    	return second + "秒";
	    }
	}
}

function getSystemTime(){
	if ($('#systemTime', parent.document).val()) {
		return new Date(parseInt($('#systemTime', parent.document).val(), 10));
	} else {
		return new Date();
	}
}
//**********checkbox************
function getCheckDataList(data, ID) {
	var checkbox = getCheckbox("chk");
	var count = checkbox && checkbox.count || 0;
	var index = undefined;
	var dataList = [];
	for (var i=0; i<count; i++) {
		index = checkbox && checkbox.row_index[i] || 0;
		dataList.push(data && data[index] && data[index][ID]);
	}
	return dataList.toString();
}

function checkAll(checkboxName) {
	var checkbox = document.getElementsByName(checkboxName);
	var length = checkbox && checkbox.length || 0;
	for (i = 0; i < length; i++) {
		if (!checkbox[i].disabled) {
			checkbox[i].checked = event.srcElement.checked;
		}
	}
}

function getCheckbox(checkboxName) {
	var checkbox = {
		count: 0,
		row_index: []
	};
	var checkboxName = document.getElementsByName(checkboxName);
	var length = checkboxName && checkboxName.length || 0;
	for (i = 0; i < length; i++) {
		if (checkboxName[i] && checkboxName[i].checked == true) {
			checkbox.count += 1;
			checkbox.row_index.push(i);
		}
	}
	return checkbox;
}
//**********show message************
function showWarn(message, callback, data, option){
    var swalOption = {
            text: "警告信息",
            title: message,
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#f8bb86",
            cancelButtonText: "取消",
            confirmButtonText: "确定",
            showLoaderOnConfirm: true,
//            html: true,//文本内容text为html
//            timer: 2000,// 2秒后自动关闭
//            animation: "slide-from-top",// 弹出的方向
//            imageUrl: "",// 自定义图片
            closeOnConfirm: false
        };
    if (option) {
        $.extend(true, swalOption, option);
    }
    if (typeof callback === 'function') {
        swal(swalOption, function(){callback(data)});
    } else {
        swal(swalOption);
    }
}

function showInfo(message, callback, data){
    if (typeof callback === 'function') {
        swal({confirmButtonText: "确定", text: "提示信息", title: message, type: "info", confirmButtonColor: "#3fc3ee"}, function(){callback(data)})
    } else {
        swal({confirmButtonText: "确定", text: "提示信息", title: message, type: "info", confirmButtonColor: "#3fc3ee"})
    }
}

function showError(message, callback, data){
    if (typeof callback === 'function') {
        swal({confirmButtonText: "确定", text: "错误信息", title: message, type: "error", confirmButtonColor: "#f27474"}, function(){callback(data)})
    } else {
        swal({confirmButtonText: "确定", text: "错误信息", title: message, type: "error", confirmButtonColor: "#f27474"})
    }
}

function showSuccess(message, callback, data){
    if (typeof callback === 'function') {
        swal({confirmButtonText: "确定", text: "成功信息", title: message, type: "success", confirmButtonColor: "#4cd964"}, function(){callback(data)});
    } else {
        swal({confirmButtonText: "确定", text: "成功信息", title: message, type: "success", confirmButtonColor: "#4cd964"})
    }
}

function formReset(formID){
	document.getElementById(formID).reset();
}

function clearTable(tableID) {
	var table = document.getElementById(tableID);
	var rows = table.rows && table.rows.length;
	for (var i = 1; i <= rows; i++) {
		table.deleteRow(0);
	}
}
function formatTime(time) {
	var now = new Date();
	if (time) {
		now = new Date(time);
	}
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var day = ((now.getDate() < 10) ? "0" : "") + now.getDate();
	var hours = ((now.getHours() < 10) ? "0" : "") + now.getHours();
	var minutes = ((now.getMinutes() < 10) ? ":0" : ":") + now.getMinutes();
	var seconds = ((now.getSeconds() < 10) ? ":0" : ":") + now.getSeconds();
	var week = now.getDay();
	var weekName = [" 星期日 ", " 星期一 ", " 星期二 ", " 星期三 ", " 星期四 ", " 星期五 ", " 星期六 "];
	week = weekName[week];
	now = null;
	return year + "年" + month + "月" + day + "日" + week + hours + minutes + seconds;
}
function checkForm(id) {
    var checkFlag = true;
    $(id+ " :input").each(function(i, item) {
        if (item.required && !item.value) {
            if (item.title) {
                showInfo("【" + item.title + "】不能为空！");
            } else if (item.placeholder) {
                showInfo("【" + item.placeholder + "】不能为空！");
            } else if ($(item).siblings("label").length > 0) {
                showInfo("【" + $(item).siblings("label").text() + "】不能为空！");
            } else {
                showInfo("【" + item.id +  "】不能为空！");
            }
            checkFlag = false;
            return checkFlag;
        }
    })
    return checkFlag;
}

function logout() {
    window.location.href = "logout";
}
