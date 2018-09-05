$(document).ready(function() {
    $("#userID").focus();
    $('#password').bind('keypress', function (event) {
        if (event.keyCode == "13") {
            toLogin();
        }
    });
});
function toLogin() {
    $("#message").text("");
    var userID = $("#userID").val();
    if (!userID) {
        $("#message").text("请输入用户名");
        $("#userID").focus();
        return;
    }
    var password = $("#password").val();
    if (!password) {
        $("#message").text("请输入密码");
        $("#password").focus();
        return;
    }
    $("#login").text('正在登录...');
    $("#login").attr('disabled', true);
    $("#userID").attr('readonly', true);
    $("#password").attr('readonly', true);
    $.ajax({
        url : 'loginCheck',
        type : 'post',
        dataType : 'json',
        data : {userID: userID, password: $.md5(password)},
        success : function(data) {
            var result = data.result;
            if (result == "Success") {
                window.location.href = data.url || "main";
            } else if (result == "EmptyError") {
                resetLogin();
                $("#message").text("登录失败，用户名或密码为空！");
            } else if (result == "Error") {
                resetLogin();
                $("#message").text("登录异常，请重新登录或联系管理员！");
            } else {
                resetLogin();
                $("#message").text("登录失败，用户名或密码错误！");
            }
        },
        error : function(data) {
            resetLogin();
            $("#message").text("登录异常，请重新登录或联系管理员！");
        },
        timeout: function(data){
            resetLogin();
            $("#message").text("登录超时，请重新登录！");
        }
    });
};

function resetLogin() {
    $("#login").text('登录');
    $("#login").attr('disabled', false);
    $("#userID").val("");
    $("#userID").attr('readonly', false);
    $("#password").val("");
    $("#password").attr('readonly', false);
}