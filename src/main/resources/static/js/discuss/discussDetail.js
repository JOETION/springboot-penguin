/**
 * 模块JavaScript
 */
var discussDetailPage = {
    data: {
        discussVo: null,
        discussDetailVos: [],
        currentAccount: null,
    },
    init: function (discussVo, discussDetailVos, currentAccount) {
        discussDetailPage.data.post = discussVo;
        discussDetailPage.data.discussDetailVos = discussDetailVos;
        discussDetailPage.data.currentAccount = currentAccount;

        $('#postContent').html(discussVo.post.htmlContent);

        toastr.options.positionClass = 'toast-top-center';

        /**
         * 回复模态框关闭按钮触发
         */
        $('#cancelReplyBtn').click(function (e) {
            $('#replyModal').modal('hide');
        });
        /**
         * 回复模态框登录按钮触发
         */
        $('#confirmReplyBtn').click(function (e) {
            discussDetailPage.addReplyAction();
        });

        /**
         * 投诉模态框关闭按钮触发
         */
        $('#cancelComplaintBtn').click(function (e) {
            $('#complaintModal').modal('hide');
        });
        /**
         * 投诉模态框登录按钮触发
         */
        $('#confirmComplaintBtn').click(function (e) {
            discussDetailPage.addComplaint();
        });

    },
    addCommentsAction: function () {

        if (discussDetailPage.data.currentAccount.state == 1) {

            //此处可以跳到小黑屋
            toastr.error("您的账户因涉嫌违规操作，已被封禁，请联系管理员");
        } else {
            var content = $('#commentContent').val();

            if (content.trim() != '') {
                var userId = discussDetailPage.data.currentAccount.id;
                var discussVo = discussDetailPage.data.post;
                $.ajax({
                    url: app.URL.addCommentUrl(),
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json;charset=UTF-8",
                    <!-- 向后端传输的数据 -->
                    data: JSON.stringify({
                        userId: userId,
                        postId: discussVo.post.id,
                        content: content,
                    }),
                    success: function (result) {
                        if (result && result['success']) {
                            // 验证通过 刷新页面
                            window.location.reload();
                        } else {
                            console.log(result.message);
                        }
                    },
                    error: function (result) {
                        console.log(result.message);
                    }
                });
            } else {
                toastr.error("请勿提交空内容！");
            }

        }

    },
    /**
     * 回复模态框显示
     */
    showReplyModal: function (index, atuserId) {
        if (discussDetailPage.data.currentAccount.state == 1) {
            toastr.error("您的账户因涉嫌违规操作，已被封禁，请联系管理员");
        } else {
            var discussDetailVos = discussDetailPage.data.discussDetailVos;
            $('#replyCommentId').val(discussDetailVos[index].comment.id);
            $('#replyAtuserId').val(atuserId);
            $('#replyModal').modal('show');
        }
    },

    /**
     * 显示投诉模态框
     */
    showComplaintModal: function (which, whichId) {
        var currentAccount = discussDetailPage.data.currentAccount;
        if (currentAccount.state == 1) {
            toastr.error("您的账户因涉嫌违规操作，已被封禁，请联系管理员");
        } else {

            $('#complaintUserId').val(currentAccount.id);
            $('#complaintWhich').val(which);
            $('#complaintWhichId').val(whichId);
            $('#complaintModal').modal('show');
        }
    },
    addReplyAction: function () {

        var content = $('#replyContent').val();
        if (content.trim() != '') {
            var userId = discussDetailPage.data.currentAccount.id;
            var discussVo = discussDetailPage.data.post;
            var commentId = $('#replyCommentId').val();
            var atuserId = $('#replyAtuserId').val();
            $.ajax({
                url: app.URL.addReplyUrl(),
                type: "POST",
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data: JSON.stringify({
                    userId: userId,
                    atuserId: atuserId,
                    postId: discussVo.post.id,
                    commentId: commentId,
                    content: content
                }),
                success: function (result) {
                    if (result && result['success']) {
                        // 验证通过 刷新页面
                        window.location.reload();
                    } else {
                        console.log(result.message);
                    }
                },
                error: function (result) {
                    console.log(result.message);
                }
            });
        }

    },
    //添加投诉
    addComplaint: function () {
        var userId = $("#complaintUserId").val();
        var which = $("#complaintWhich").val();
        var whichId = $("#complaintWhichId").val();
        var complaintType = $("#complaintType").val();
        var complaintContent = $("#complaintContent").val();

        if (complaintContent.trim() == '') {
            toastr.error("请不要提交空的投诉信息！");
        } else {
            $.ajax({
                url: app.URL.addComplaintUrl(),
                type: "POST",
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({
                    userId: userId,
                    complaintWhich: which,
                    whichId: whichId,
                    complaintType: complaintType,
                    complaintContent: complaintContent
                })
                ,
                success: function (result) {
                    if (result && result['success']) {
                        //删除成功
                        toastr.success("投诉信息提交成功");
                        window.location.reload();
                    } else {
                        toastr.error("投诉信息提交失败，原因：" + result.message);
                    }
                },
                error: function (result) {
                    toastr.error("投诉信息提交失败，原因：" + result.message);
                }
            });
        }
    },


    //删除帖子，删除帖子，评论，及回复信息
    deletePost: function (id) {
        $.ajax({
            url: app.URL.deletePostUrl(id),
            type: "DELETE",
            success: function (result) {
                if (result && result['success']) {
                    //删除成功
                    toastr.success("删除成功");
                    window.location.href = app.URL.discussUrl();
                } else {
                    toastr.error("删除失败，原因：" + result.message);
                }
            },
            error: function (result) {
                toastr.error("删除失败，原因：" + result.message);
            }
        });
    },

    //删除评论，同时会删除评论下面的回复
    deleteComment: function (id) {
        $.ajax({
            url: app.URL.deleteCommentUrl(id),
            type: "DELETE",
            success: function (result) {
                if (result && result['success']) {
                    //删除成功
                    toastr.success("删除成功");
                    window.location.reload();
                } else {
                    toastr.error("删除失败，原因：" + result.message);
                }
            },
            error: function (result) {
                toastr.error("删除失败，原因：" + result.message);
            }
        });
    },

    //删除回复
    deleteReply: function (id) {
        $.ajax({
            url: app.URL.deleteReplyUrl(id),
            type: "DELETE",
            success: function (result) {
                if (result && result['success']) {
                    //删除成功
                    toastr.success("删除成功");
                    window.location.reload();
                } else {
                    toastr.error("删除失败，原因：" + result.message);
                }
            },
            error: function (result) {
                toastr.error("删除失败，原因：" + result.message);
            }
        });
    }

};