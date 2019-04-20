/**
 * 模块化JavaScript
 **/
var manageNoticePage = {
    data: {
        notices: []
    },
    init: function (notices) {
        manageNoticePage.data.notices = notices;


        //弹出新增公告窗口
        $("#releaseNoticeBtn").click(function () {
            //输入框初始化数据
            manageNoticePage.initReleaseNotice();
            $("#releaseNoticeModal").modal({
                keyboard: false,
                show: true,
                backdrop: "static"
            });
        });

        /**
         * toastr提示消息位置
         */
        toastr.options.positionClass = 'toast-top-center';

        //取消新增公告
        $('#cancelReleaseNotice').click(function () {
            $("#releaseNoticeModal").modal('hide');
        });

        /**
         * 确认新增公告
         */
        $('#confirmReleaseNotice').click(function (e) {
            manageNoticePage.releaseNotice();
        });

    },

    //发布公告
    releaseNotice: function () {

        var content = $("#noticeContent").val();
        var url = $("#noticeUrl").val();
        if (content.trim() == '' || url.trim() == '') {
            toastr.error("请确保数据不为空！");
        }
        else {
            $.ajax({
                url: app.URL.addNoticeUrl(),
                type: "POST",
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data: JSON.stringify({
                    noticeContent: "公告：" + content,
                    noticeUrl: url,
                    noticeType: 0
                }),
                success: function (result) {
                    if (result && result['success']) {
                        toastr.success("发布公告成功！");
                        // 验证通过 刷新页面
                        window.location.reload();
                    } else {
                        toastr.error("发布公告失败，原因：" + result.message);
                    }
                },
                error: function (result) {
                    toastr.error("发布公告失败，原因：" + result.message);
                }
            });
        }
    },


    deleteNotice: function (index) {
        $.ajax({
            url: app.URL.deleteNoticeUrl(),
            type: "GET",
            <!-- 向后端传输的数据 -->
            data: {
                id: manageNoticePage.data.notices[index].id
            },
            success: function (result) {
                if (result && result['success']) {
                    toastr.success("删除公告成功！");
                    // 验证通过 刷新页面
                    window.location.reload();
                } else {
                    toastr.error("删除公告失败，原因：" + result.message);
                }
            },
            error: function (result) {
                toastr.error("删除公告失败，原因：" + result.message);
            }
        });
    },

//初始化发布公告数据
    initReleaseNotice: function () {
        $("#noticeContent").val("");
        $("#noticeUrl").val("");
    }
}