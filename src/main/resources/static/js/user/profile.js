/**
 * 模块JavaScript
 */
var profilePage = {
    data: {},
    init: function () {
        /**
         * 点击上传图片效果
         */
        $('.card .dimmer')
            .dimmer({
                on: 'hover'
            })
        ;
        /**
         * toastr提示消息位置
         */
        toastr.options.positionClass = 'toast-top-center';
        /**
         * 更新个人信息保存按钮触发
         */
        $('#updateAccountButton').click(function (e) {
            profilePage.updateAccount();
        });
        /**
         * 更新个人信息错误提示消息可关闭
         */
        $('#updateAccountErrorMessage,.close').on('click', function () {
            $(this).closest('#updateAccountErrorMessage').transition('fade');
            //$('#updateAccountErrorMessage').addClass('hidden');
        });
    },
    /**
     * 检查个人信息输入是否合法
     */
    checkProfile: function (phone, qq, email, description, avatarImgUrl) {
        if (phone == null || phone == ''
            || phone.replace(/(^s*)|(s*$)/g, "").length == 0) {
            $('#updateAccountErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                '                <p>' + '手机号码不能为空' + '</p>');
            $('#updateAccountErrorMessage').removeClass('hidden');
            return false;
        }
        if (qq == null || qq == ''
            || qq.replace(/(^s*)|(s*$)/g, "").length == 0) {
            $('#updateAccountErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                '                <p>' + 'QQ不能为空' + '</p>');
            $('#updateAccountErrorMessage').removeClass('hidden');
            return false;
        }
        if (email == null || email == ''
            || email.replace(/(^s*)|(s*$)/g, "").length == 0) {
            $('#updateAccountErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                '                <p>' + '邮箱地址不能为空' + '</p>');
            $('#updateAccountErrorMessage').removeClass('hidden');
            return false;
        }
        if (description == null || description == ''
            || description.replace(/(^s*)|(s*$)/g, "").length == 0) {
            $('#updateAccountErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                '                <p>' + '自我描述不能为空' + '</p>');
            $('#updateAccountErrorMessage').removeClass('hidden');
            toastr.error('自我描述不能为空');
            return false;
        }
        return true;
    },
    /**
     * 保存个人信息事件
     */
    updateAccount: function () {
        var phone = $('#myPhone').val();
        var qq = $('#myQq').val();
        var email = $('#myEmail').val();
        var description = $('#myDescription').val();
        var avatarImgUrl = $('#myAvatarImgUrl').val();
        if (avatarImgUrl == null || avatarImgUrl == ''
            || avatarImgUrl.replace(/(^s*)|(s*$)/g, "").length == 0) {
            avatarImgUrl = 'headimg_placeholder.png';
        }
        if (profilePage.checkProfile(phone, qq, email, description, avatarImgUrl)) {
            //调用后端API
            $.post(app.URL.updateAccountUrl(), {
                phone: phone,
                qq: qq,
                email: email,
                description: description,
                avatarImgUrl: avatarImgUrl
            }, function (result) {
                console.log(result);
                if (result && result['success']) {
                    toastr.success('更新成功，稍后生效！');
                    setTimeout(function () {
                        //刷新页面
                        window.location.reload();
                    }, 2000);
                } else {
                    $('#updateAccountErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                        '                <p>' + result.message + '</p>');
                    $('#updateAccountErrorMessage').removeClass('hidden');
                }
            }, "json");
        }
    },
    /**
     * ajax上传头像
     */
    uploadAvatar: function () {
        var fileName = $('#myfile').val();　　　　　　　　　　　　　　　　　　//获得文件名称
        var fileType = fileName.substr(fileName.length - 4, fileName.length);　　//截取文件类型,如(.xls)
        if (fileType == '.jpg' || fileType == '.png') {　　　　　//验证文件类型,此处验证也可使用正则
            var file = $('#myfile')[0].files[0];
            if (file.size / 1024 < 100) {
                var formData = new FormData();
                formData.append('file', file);
                $.ajax({
                    url: app.URL.uploadAvatarUrl(),　　　　　　　　　　//上传地址
                    type: 'POST',
                    cache: false,
                    data: formData,　　　　　　　　　　　　　//表单数据
                    processData: false,
                    contentType: false,
                    success: function (result) {
                        if (result && result['success']) {
                            $('#myAvatarImgUrl').val(result.data);
                            $('#avatarImgPreview').attr("src", app.URL.uploadImageUrl() + result.data);
                        } else {
                            $('#updateAccountErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                                '                <p>' + result.message + '</p>');
                            $('#updateAccountErrorMessage').removeClass('hidden');
                        }

                    }
                });
            } else {
                $('#updateAccountErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                    '                <p>请确保文件大小在 100kb 以内</p>');
                $('#updateAccountErrorMessage').removeClass('hidden');
            }

        } else {
            $('#updateAccountErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                '                <p>*上传文件类型错误,支持类型: .jpg .png</p>');
            $('#updateAccountErrorMessage').removeClass('hidden');
        }
    }
};