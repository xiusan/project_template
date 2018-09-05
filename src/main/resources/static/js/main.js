var serverDate = undefined;
var menuInterval = undefined;
$(document).ready(function() {
    getMenuData();
    menuInterval = setInterval(getMenuData, 60000);
    $ajax("getMenuData", setMenuData, '', '', "get");
    setTime();
    setInterval(setTime, 1000);
});

function getMenuData() {
    $ajax("getMenuData", setMenuData, '', '', "get");
}

function setMenuData(data) {
    if (data) {
        clearInterval(menuInterval);
        $("#userName").text(data && data.userName || "未知");
        serverDate = new Date(data.systemDate).getTime();
    } else {
        $("#userName").text("异常");
    }
}

function setTime() {
	$("#time").text(formatTime(serverDate));
	if (serverDate) {
		serverDate = serverDate + 1000;
	}
	$("#systemTime").val(serverDate);
}

function logoutConfirm() {
    showWarn(messageContent["Logout.Confirm"], logout);
}
