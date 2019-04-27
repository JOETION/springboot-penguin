/**
 * 模块JavaScript
 */
var managePasswordPage = {
    init: function () {


        //新增考试，弹出新增窗口
        $("#modifyPassword").click(function () {
            //输入框初始化数据
            managePasswordPage.initPassword();
            $("#updatePasswordModal").modal({
                keyboard: false,
                show: true,
                backdrop: "static"
            });
        });
        /**
         * toastr提示消息位置
         */
        toastr.options.positionClass = 'toast-top-center';

        $("#updatePasswordErrorMessage").click(function () {
            $(this).hide(500);
        });


        //新增考试，取消考试增加
        $('#cancelUpdatePassword').click(function () {
            $("#updatePasswordModal").modal('hide');
        });

        /**
         * 更新密码提交按钮触发
         */
        $('#updatePasswordButton').click(function (e) {
            managePasswordPage.updatePassword();
        });
    },
    initPassword: function () {
        //初始化数据
        $('#oldPassword').val("");
        $('#newPassword').val("");
        $('#confirmNewPassword').val("");
        $('#updatePasswordErrorMessage').hide();
        $('#updatePasswordErrorMessage').html("");
    },

    /**
     * 检查密码输入是否合法
     */
    checkPassword: function (oldPassword, newPassword, confirmNewPassword) {
        if (oldPassword == null || oldPassword == ''
            || oldPassword.replace(/(^s*)|(s*$)/g, "").length == 0) {
            $('#updatePasswordErrorMessage').html('<div class="header">错误提示</div>\n' +
                '                <p>' + '原密码不能为空' + '</p>');
            $('#updatePasswordErrorMessage').show(500);
            return false;
        }
        if (newPassword == null || newPassword == ''
            || newPassword.replace(/(^s*)|(s*$)/g, "").length == 0) {
            $('#updatePasswordErrorMessage').html('<div class="header">错误提示</div>\n' +
                '                <p>' + '新密码不能为空' + '</p>');
            $('#updatePasswordErrorMessage').show(500);
            return false;
        }
        if (confirmNewPassword == null || confirmNewPassword == ''
            || confirmNewPassword.replace(/(^s*)|(s*$)/g, "").length == 0) {
            $('#updatePasswordErrorMessage').html('<div class="header">错误提示</div>\n' +
                '                <p>' + '确认密码不能为空' + '</p>');
            $('#updatePasswordErrorMessage').show(500);
            return false;
        }
        if (newPassword != confirmNewPassword) {
            $('#updatePasswordErrorMessage').html('<div class="header">错误提示</div>\n' +
                '                <p>' + '新密码和确认密码不一致' + '</p>');
            $('#updatePasswordErrorMessage').show(500);
            return false;
        }
        return true;
    },
    /**
     * 更新密码事件触发
     */
    updatePassword: function () {
        var oldPassword = $.md5($('#oldPassword').val()+app.data.md5Salt);
        var newPassword = $.md5($('#newPassword').val()+app.data.md5Salt);
        var confirmPassword = $.md5($('#confirmNewPassword').val()+app.data.md5Salt);
        if (managePasswordPage.checkPassword(oldPassword, newPassword, confirmPassword)) {
            //调用后端API
            $.post(app.URL.updatePasswordUrl(), {
                oldPassword: oldPassword,
                newPassword: newPassword,
                confirmNewPassword: confirmPassword
            }, function (result) {
                console.log(result);
                if (result && result['success']) {
                    toastr.success('更新成功');
                    setTimeout(function () {
                        //刷新页面
                        window.location.reload();
                    }, 2000);
                } else {
                    $('#updatePasswordErrorMessage').html('<div class="header">错误提示</div>\n' +
                        '                <p>' + result.message + '</p>');
                    $('#updatePasswordErrorMessage').show(500);
                }
            }, "json");
        }
    },
};