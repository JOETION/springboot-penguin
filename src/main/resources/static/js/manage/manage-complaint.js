/**
 * 模块化JavaScript
 **/
var manageComplaintPage = {
    data: {
        pageNum: 0,
        pageSize: 0,
        totalPageNum: 0,
        totalPageSize: 0,
        complaints: [],
        kind: 3
    },
    init: function (pageNum, pageSize, totalPageNum, totalPageSize, complaints, kind) {
        manageComplaintPage.data.pageNum = pageNum;
        manageComplaintPage.data.pageSize = pageSize;
        manageComplaintPage.data.totalPageNum = totalPageNum;
        manageComplaintPage.data.totalPageSize = totalPageSize;
        manageComplaintPage.data.complaints = complaints;
        manageComplaintPage.data.kind = kind;
        //分页初始化
        manageComplaintPage.subPageMenuInit();

        /**
         * toastr提示消息位置
         */
        toastr.options.positionClass = 'toast-top-center';

    },
    firstPage: function () {
        window.location.href = app.URL.manageComplaintListUrl() + '?page=1&kind=' + manageComplaintPage.data.kind;
    },
    prevPage: function () {
        window.location.href = app.URL.manageComplaintListUrl() + '?page=' + (pageNum - 1) + '&kind=' + manageComplaintPage.data.kind;
    },
    targetPage: function (page) {
        window.location.href = app.URL.manageComplaintListUrl() + '?page=' + page + '&kind=' + manageComplaintPage.data.kind;
    },
    nextPage: function () {
        window.location.href = app.URL.manageComplaintListUrl() + '?page=' + (pageNum + 1) + '&kind=' + manageComplaintPage.data.kind;
    },
    lastPage: function () {
        window.location.href = app.URL.manageComplaintListUrl() + '?page=' + manageComplaintPage.data.totalPageNum + '&kind=' + manageComplaintPage.data.kind;
    },
    subPageMenuInit: function () {
        var subPageStr = '<ul class="pagination">';
        if (manageComplaintPage.data.pageNum == 1) {
            subPageStr += '<li class="disabled"><a><span>首页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>上一页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="manageComplaintPage.firstPage()"><span>首页</span></a></li>';
            subPageStr += '<li><a onclick="manageComplaintPage.prevPage()"><span>上一页</span></a></li>';
        }
        var startPage = (manageComplaintPage.data.pageNum - 4 > 0 ? manageComplaintPage.data.pageNum - 4 : 1);
        var endPage = (startPage + 7 > manageComplaintPage.data.totalPageNum ? manageComplaintPage.data.totalPageNum : startPage + 7);
        console.log('startPage = ' + startPage);
        console.log('endPage = ' + endPage);
        console.log('pageNum = ' + manageComplaintPage.data.pageNum);
        console.log('totalPageNum = ' + manageComplaintPage.data.totalPageNum);
        for (var i = startPage; i <= endPage; i++) {
            if (i == manageComplaintPage.data.pageNum) {
                subPageStr += '<li class="active"><a onclick="manageComplaintPage.targetPage(' + i + ')">' + i + '</a></li>';
            } else {
                subPageStr += '<li><a onclick="manageComplaintPage.targetPage(' + i + ')">' + i + '</a></li>';
            }
        }
        if (manageComplaintPage.data.pageNum == manageComplaintPage.data.totalPageNum) {
            subPageStr += '<li class="disabled"><a><span>下一页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>末页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="manageComplaintPage.nextPage()"><span>下一页</span></a></li>';
            subPageStr += '<li><a onclick="manageComplaintPage.lastPage()"><span>末页</span></a></li>';
        }
        $('#subPageHead').html(subPageStr);
    },

    //请求转发至外部链接，避免了请求跨域访问
    cloudCheck: function (index) {

        $("#loadingModal").modal({backdrop: 'static', keyboard: false});

        var text = manageComplaintPage.data.complaints[index].complaintContent;
        $.ajax({
                url: app.URL.cloudCheckUrl() + "?c=" + text,
                type: "GET",
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                success: function (result) {
                    if (result.status == 1) {
                        $('#loadingModal').modal('hide');
                        $("#complaintVo" + index).children().eq(1).html(result.data);
                        toastr.success("云端检测成功，敏感词汇将用红色标出！");

                    } else {
                        $('#loadingModal').modal('hide');
                        toastr.error("云端检测失败，原因：" + result.data);
                    }
                }
                ,
                error: function (result) {
                    $('#loadingModal').modal('hide');
                    toastr.error("云端检测失败，原因：" + result.data);
                }
            }
        )
    },

    //更新投诉信息
    updateComplaint: function (index, state) {

        var complaintVo = manageComplaintPage.data.complaints[index];

        $.ajax({
                url: app.URL.updateComplaintUrl(),
                type: "POST",
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({
                    which: complaintVo.complaintKind,
                    whichId: complaintVo.complaintKindId,
                    userId: complaintVo.userId,
                    atUserId: complaintVo.atUserId,
                    state: state,
                    complaintTime: complaintVo.complaintTime
                }),
                success: function (result) {
                    if (result && result['success']) {
                        toastr.success("操作成功！");
                        $("#complaintVo" + index + "CheckBtn").attr("disabled", "disabled");
                        $("#complaintVo" + index + "FailBtn").attr("disabled", "disabled");
                        $("#complaintVo" + index + "SuccessBtn").attr("disabled", "disabled");

                    } else {
                        toastr.error("操作失败，原因：" + result.message);
                    }
                }
                ,
                error: function (result) {
                    toastr.error("操作失败，原因：" + result.message);
                }
            }
        )
    }

}