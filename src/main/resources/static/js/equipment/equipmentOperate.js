var saveFlag = undefined;
function showSave(index) {
    getChargingStationName("#saveChargingStationID");
    $("#saveChargingStationID").addClass('pop-select').selectpicker('setStyle');
    $("#saveChargingStationID").selectpicker('setStyle', 'pop-select', 'add');
    if (index != undefined) {
        saveFlag = "UPDATE";
        $(".modal-title").text("充电桩修改");
        $("#saveChargingStationID").val(equipmentData[index].chargingStationID);
        changeSelectpicker("#saveChargingStationID", 'refresh');
        $("#saveEquipmentID").attr("readonly", "readonly");
        $("#saveEquipmentID").val(equipmentData[index].equipmentID);
        $("#saveEquipmentName").val(equipmentData[index].equipmentName);
        $("#saveEquipmentType").val(equipmentData[index].equipmentType);
        $("#saveEquipmentPowerType").val(equipmentData[index].equipmentPowerType);
        $("#saveEquipmentManufacturer").val(equipmentData[index].equipmentManufacturer);
        $("#saveManufactureDateTime").val(date2String(equipmentData[index].manufactureTime));
        $("#saveProtocolType").val(equipmentData[index].protocolType);
        $("#saveEquipmentBatch").val(equipmentData[index].equipmentBatch);
    } else {
        saveFlag = "INSERT";
        $(".modal-title").text("充电桩添加");
        formReset('saveForm');
        $("#saveEquipmentID").attr("readonly", false);
    }
    $("#save").modal('show');
}
function closeSave() {
    $("#save").modal('hide');
    formReset('saveForm');
}

function save() {
    var saveChargingStationID= $('#saveChargingStationID').val();
//    if(!saveChargingStationID){
//        showInfo("请选择充电站！")
//        return;
//    }
    if (checkForm("#saveForm")) {
        if (saveFlag == "INSERT") {
            $ajax('saveEquipment', saveEquipmentCallback, ($("#saveForm").serialize() || "").replace(/save/g, ""));
        } else if (saveFlag == "UPDATE") {
            $ajax('updateEquipment', saveEquipmentCallback, ($("#saveForm").serialize() || "").replace(/save/g, ""));
        }
    }
}

function saveEquipmentCallback(data) {
    var result = data && data.result;
    var message = "";
    if (saveFlag == "INSERT") {
        message += "Insert.";
    } else if (saveFlag == "UPDATE") {
        message += "Update.";
    }
    if (result == "Success"){
        $('#equipmentTable').bootstrapTable('refresh');
        closeSave();
        showSuccess(messageContent[message + "Success"]);
    } else if (result == "ChargingStationIDEmpty") {
        showError(messageContent[message + "Failed"] + result);
    } else if (result == "EquipmentIDEmpty") {
        showError(messageContent[message + "Failed"] + result);
    } else if (result == "EquipmentIDExist") {
        showError(messageContent["Equipment.EquipmentExist"]);
    } else if (result == "EquipmentNameExist") {
        showError(messageContent["Equipment.EquipmentNameExist"]);
    } else if (result == "DuplicateError") {
        showError(messageContent[message + "Duplicate"]);
    } else if (result == "NotFound") {
        showError(messageContent[message + "NotFound"]);
    } else {
        showError(messageContent[message + "Error"]);
    }
}

function delConfirm() {
    var selectData = $('#equipmentTable').bootstrapTable('getSelections');
    if (selectData.length == 0) {
        showInfo(messageContent["Delete.Select"]);
    } else {
        showWarn(messageContent["Delete.Confirm"], del, selectData);
    }
}

function del(data) {
    var equipmentIDList = [];
    for (var i in data) {
        equipmentIDList.push(data[i].equipmentID);
    }
    $ajax('delEquipment', delEquipmentCallback, {EquipmentID: equipmentIDList.join(',')});
}

function delEquipmentCallback(data) {
    var result = data && data.result;
    if (result == "Success"){
        $('#equipmentTable').bootstrapTable('refresh');
        showSuccess(messageContent["Delete.Success"]);
    } else if (result == "EquipmentIDEmpty") {
        showError(messageContent["Delete.Failed"] + result);
    } else if (result == "NotFound") {
        showError(messageContent["Delete.NotFound"]);
    } else {
        showError(messageContent["Delete.Error"]);
    }
}
