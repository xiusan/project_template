$(document).ready(function() {
    $("#userForm").validate({
        submitHandler:function(form) {
            $ajax("updatePassword", updateCallback, {userID: $("#userID").val(), password: $.md5($("#password").val()), oldPassword: $.md5($("#oldPassword").val())});
        }
    });
    $ajax("getUser", setUser, '', '', "get");
});

function setUser(data) {
    var user = data.user;
    if (user) {
        $("#userID").val(user.userID);
        $("#userName").val(user.userName);
    } else {
        showError(messageContent["User.NotFound"]);
    }
}

function updateCallback(data) {
    var result = data.result;
    if (result == "Success") {
        $("#oldPassword").val("");
        $("#password").val("");
        $("#confirmPassword").val("");
        $("#userForm .form-group").removeClass("has-success").removeClass("has-error");
        showSuccess(messageContent["Password.SaveSuccess"], logout);
    } else if (result == "UserIDEmpty") {
        showError(messageContent["User.UserIDEmpty"]);
    } else if (result == "OldPasswordEmpty") {
        showError(messageContent["Password.OldEmpty"]);
    } else if (result == "PasswordEmpty") {
        showError(messageContent["Password.Empty"]);
    } else if (result == "PasswordError") {
        showError(messageContent["Password.OldError"]);
    } else if (result == "NotFound") {
        showError(messageContent["User.NotFound"]);
    } else {
        showError(messageContent["Password.SaveError"]);
    }
}
