var chargingStationNameList = [];
var equipmentData = {};
var bootstrapTableOption = {
    url: 'getEquipmentList',
    queryParams: function (params) {
        return {
            pageNum: params.pageNumber,// 每页要显示的数据条数
            pageSize: params.pageSize,// 每页显示数据的开始行号
            equipmentID: $('#equipmentID').val() && $('#equipmentID').val().replace("ALL", ""),
            chargingStationID: $('#chargingStationID').val(),
        }
    },
    columns: [
        {checkbox: true}, // 显示一个勾选框
        {title: '序号', align: 'center', formatter: 'increases'},
        {field: 'chargingStationName', title: '充电站'},
        {field: 'equipmentName', title: '充电桩'},
        {field: 'equipmentType', title: '桩类型'},
        {field: 'equipmentPowerType', title: '桩类号'},
        {field: 'equipmentManufacturer', title: '厂家'},
        {field: 'manufactureTime', title: '生产日期', formatter: 'date2String'},
        {field: 'protocolType', title: '协议类型'},
        {field: 'equipmentBatch', title: '批次'},
        {title: '操作', formatter:function (value, row, index) {return ["<a><i class='' onclick='showSave(" + index + ")'>修改</i></a>"].join('');}}
    ],
    responseHandler: function (data) {
        equipmentData = data.list;
        return data;
    }
};

$(document).ready(function() {
	$('title', parent.document).html($('title').text());
	$("#equipmentIDDiv").hide();
    getChargingStationName("#chargingStationID");
    setBootstrapTable('#equipmentTable', bootstrapTableOption);
});

function getChargingStationName(id) {
    changeSelectpicker(id, 'destroy');
    $(id).empty();
    if (chargingStationNameList && chargingStationNameList.length > 0) {
        setChargingStationName(id, chargingStationNameList);
    } else {
        $ajax("getChargingStationName", setChargingStationName, '', id, "get");
    }
};

function setChargingStationName(id, data) {
    var length = data.length;
    chargingStationNameList = data;
    for (var i = 0; i < length; i++) {
        $(id).append("<option value ='" + data[i].chargingStationID + "'>" + data[i].chargingStationName + "</option>");
    }
    changeSelectpicker(id, 'refresh');
    $(id).selectpicker('setStyle', 'select', 'add');
    if (length > 5) {
        setSelectpicker(id);
    }
};

function change(changeID, linkID, callback) {
    if (changeID && linkID) {
        var changeValue = $(changeID).val();
        $(linkID).empty();
        changeSelectpicker(linkID, 'destroy');
        if (changeValue) {
            $(linkID + "Div").show();
            if (callback && typeof callback === 'function') {
                callback(changeValue, linkID);
            }
        } else {
            $(linkID + "Div").hide();
        }
    }
};

function getEquipmentName(value, id) {
    $ajax("getEquipmentName?chargingStationID=" + value, setEquipmentName, '', id, "get");
};

function setEquipmentName(id, data) {
    var length = data.length;
    $(id).append("<option value='ALL'>全部桩</option>");
    for (var i = 0; i < length; i++) {
        $(id).append("<option value ='" + data[i].equipmentID + "'>" + data[i].equipmentName + "</option>");
    }
    changeSelectpicker(id, 'refresh');
    $(id).selectpicker('setStyle', 'select', 'add');
    if (length > 5) {
        setSelectpicker(id);
    }
};

function searchReset() {
    $("#equipmentID").empty();
    $("#equipmentIDDiv").hide();
    changeSelectpicker('#equipmentID', 'destroy');
    $('#chargingStationID')[0].selectedIndex = 0;
    changeSelectpicker('#chargingStationID', 'refresh');
};

function search() {
    operateBootstrapTable('#equipmentTable', 'selectPage', 1);
}
