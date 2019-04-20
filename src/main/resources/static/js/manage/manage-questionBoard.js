/**
 * 模块化JavaScript
 **/
var manageQuestionBoardPage = {
    data: {
        pageNum: 0,
        pageSize: 0,
        totalPageNum: 0,
        totalPageSize: 0,
        content: "",
        questions: [],
    },
    init: function (pageNum, pageSize, totalPageNum, totalPageSize, content, questions) {
        manageQuestionBoardPage.data.pageNum = pageNum;
        manageQuestionBoardPage.data.pageSize = pageSize;
        manageQuestionBoardPage.data.totalPageNum = totalPageNum;
        manageQuestionBoardPage.data.totalPageSize = totalPageSize;
        manageQuestionBoardPage.data.content = content;
        manageQuestionBoardPage.data.questions = questions;
        //分页初始化
        manageQuestionBoardPage.subPageMenuInit();

        $('#content').val(content);
        /**
         * toastr提示消息位置
         */
        toastr.options.positionClass = 'toast-top-center';


        //确认添加问题到考试中
        $("#confirmAddQuestionToContest").click(function () {
            var contestId = document.getElementById("selectContest").value;
            //向后台传输数据
            manageQuestionBoardPage.addQuestionToContestAction(contestId, manageQuestionBoardPage.itemId);
        });

        //取消添加问题到考试，关闭选择窗口
        $("#cancelAddQuestionToContest").click(function () {
            $("#addQuestionToContestModal").modal('hide');
        });

        //新增题目，弹出新增窗口
        $("#addQuestionBtn").click(function () {
            //输入框初始化数据
            manageQuestionBoardPage.initAddQuestionData();
            $("#addQuestionModal").modal({
                keyboard: false,
                show: true,
                backdrop: "static"
            });
        });

        //新增题目开始-------------------------------------------------------------------

        //新增题目，取消题目增加
        $('#cancelAddQuestionBtn').click(function () {
            $("#addQuestionModal").modal('hide');
        });

        //新增题目，确定增加题目
        $('#confirmAddQuestionBtn').click(function () {
            manageQuestionBoardPage.addQuestionAction();
        });

        //编辑题目，取消题目编辑
        $('#cancelUpdateQuestionBtn').click(function () {
            $("#updateQuestionModal").modal('hide');
        });

        //编辑题目，确定保存考试
        $('#confirmUpdateQuestionBtn').click(function () {
            manageQuestionBoardPage.updateQuestionAction();
        });

        //查詢按鈕触发
        $('#queryQuestionBtn').click(function () {
            manageQuestionBoardPage.queryQuestionAction();
        });


        //修改选择文件名字来
        $("#uploadQuestionFile").change(function () {
            if (this.value != '') {
                $("#uploadFileSize").val(this.files[0].size + " 字节");
            }
        });

        //上传文件，弹出上传文件窗口
        $("#uploadQuestionBtn").click(function () {
            //初始化上传数据
            manageQuestionBoardPage.initUploadQuestionData();
            $("#uploadQuestionModal").modal({
                keyboard: false,
                show: true,
                backdrop: "static"
            });
        });

        //取消上传文件，关闭上传文件窗口
        $('#cancelUploadQuestionBtn').click(function () {
            $("#uploadQuestionModal").modal('hide');
        });


        //确认上传文件事件触发
        $('#confirmUploadQuestionBtn').click(function () {
            var value = $("#uploadFileSize").val();
            if (value != '' && value != null) {
                manageQuestionBoardPage.uploadFile();
            }
            else {
                toastr.error("请选择文件后上传");
            }
        });

    },

    //初始化上传文件数据
    initUploadQuestionData: function () {
        $('#progress').val("");
        document.getElementById("progress").style.background = "#ffffff";
        $('#uploadFileSize').val("");
        var uploadQuestionFile = $('#uploadQuestionFile');
        uploadQuestionFile.value = '';
        uploadQuestionFile.val("");

    },

    //异步上传文件
    uploadFile: function () {
        var fd = new FormData();
        var selected = $('#uploadFileSize').val();
        if (selected != null && selected != '') {
            fd.append("file", document.getElementById('uploadQuestionFile').files[0]);
            var xhr = new XMLHttpRequest();
            xhr.upload.addEventListener("progress", manageQuestionBoardPage.uploadProgress, false);
            xhr.addEventListener("load", manageQuestionBoardPage.uploadComplete, false);
            xhr.addEventListener("error", manageQuestionBoardPage.uploadFailed, false);
            xhr.addEventListener("abort", manageQuestionBoardPage.uploadCanceled, false);
            xhr.open("POST", app.URL.uploadQuestionUrl()); //修改成自己的接口
            xhr.send(fd);
        } else {
            toastr.error("请选择文件后上传");
        }

    },

    //上传文件进程
    uploadProgress: function (evt) {
        if (evt.lengthComputable) {
            var percent = Math.round(evt.loaded * 100 / evt.total);
            document.getElementById('progress').innerHTML = percent.toFixed(2) + '%';
            document.getElementById('progress').style.width = percent.toFixed(2) + '%';
            document.getElementById('progress').style.background = "#5A98CF";
        } else {
            document.getElementById('progress').innerHTML = 'unable to compute';
        }
    },

    //成功上传文件
    uploadComplete: function (evt) {
        /* 服务器端返回响应时候触发event事件*/
        toastr.success('上传成功，请耐心等待试题导入系统');
        setTimeout(function () {
            //刷新页面
            window.location.reload();
        }, 2000);
    },

    //上传文件失败
    uploadFailed: function (evt) {
        toastr.error('上传失败：' + evt.message);
    },

    //取消上传题目文件
    uploadCanceled: function (evt) {
        manageQuestionBoardPage.initUploadQuestionData();
        toastr.warning('上传取消');
    },

    //打开新增问题到考试模态框
    itemId: -1,
    openAddQuestionToContestModal: function (id) {
        $("#addQuestionToContestModal").modal({
            keyboard: false,
            show: true,
            backdrop: "static"
        });
        manageQuestionBoardPage.itemId = id;
        manageQuestionBoardPage.initAddQuestionToContestContent();
    },


    //初始化增加问题到考试
    initAddQuestionToContestContent: function () {
        document.getElementById("selectContest").value = -1;
        $("#startTime").val("");
        $("#endTime").val("");
    },



    //分页开始----------------------------------------------------------------------------------------

    firstPage: function () {
        var content = $('#content').val();
        window.location.href = app.URL.manageQuestionUrl() + '?page=1&content=' + content;
    },
    prevPage: function () {
        var content = $('#content').val();
        window.location.href = app.URL.manageQuestionUrl() + '?page=' + (pageNum - 1)
            + '&content=' + content;
    },
    targetPage: function (page) {
        var content = $('#content').val();
        window.location.href = app.URL.manageQuestionUrl() + '?page='
            + page + '&content=' + content;
    },
    nextPage: function () {
        var content = $('#content').val();
        window.location.href = app.URL.manageQuestionUrl() + '?page=' + (pageNum + 1)
            + '&content=' + content;
    },
    lastPage: function () {
        var content = $('#content').val();
        window.location.href = app.URL.manageQuestionUrl() + '?page='
            + manageQuestionBoardPage.data.totalPageNum + '&content=' + content;
    },
    subPageMenuInit: function () {
        var subPageStr = '<ul class="pagination">';
        if (manageQuestionBoardPage.data.pageNum == 1) {
            subPageStr += '<li class="disabled"><a><span>首页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>上一页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="manageQuestionBoardPage.firstPage()"><span>首页</span></a></li>';
            subPageStr += '<li><a onclick="manageQuestionBoardPage.prevPage()"><span>上一页</span></a></li>';
        }
        var startPage = (manageQuestionBoardPage.data.pageNum - 4 > 0 ? manageQuestionBoardPage.data.pageNum - 4 : 1);
        var endPage = (startPage + 7 > manageQuestionBoardPage.data.totalPageNum ? manageQuestionBoardPage.data.totalPageNum : startPage + 7);
        console.log('startPage = ' + startPage);
        console.log('endPage = ' + endPage);
        console.log('pageNum = ' + manageQuestionBoardPage.data.pageNum);
        console.log('totalPageNum = ' + manageQuestionBoardPage.data.totalPageNum);
        for (var i = startPage; i <= endPage; i++) {
            if (i == manageQuestionBoardPage.data.pageNum) {
                subPageStr += '<li class="active"><a onclick="manageQuestionBoardPage.targetPage(' + i + ')">' + i + '</a></li>';
            } else {
                subPageStr += '<li><a onclick="manageQuestionBoardPage.targetPage(' + i + ')">' + i + '</a></li>';
            }
        }
        if (manageQuestionBoardPage.data.pageNum == manageQuestionBoardPage.data.totalPageNum) {
            subPageStr += '<li class="disabled"><a><span>下一页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>末页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="manageQuestionBoardPage.nextPage()"><span>下一页</span></a></li>';
            subPageStr += '<li><a onclick="manageQuestionBoardPage.lastPage()"><span>末页</span></a></li>';
        }
        $('#subPageHead').html(subPageStr);
    },

    //分页结束-----------------------------------------------------------------------




    //初始化信息考试信息
    initAddQuestionData: function () {
        //初始化数据
        $('#questionTitle').val("");
        $('#questionContent').val("");
        $('#optionA').val("");
        $('#optionB').val("");
        $('#optionC').val("");
        $('#optionD').val("");
        $('#questionAnswer').val("");
        $('#questionParse').val("");
    },

    //检查新增考试信息
    checkAddQuestionData: function (questionTitle, questionContent, questionType,
                                    optionA, optionB, optionC, optionD,
                                    questionAnswer, questionParse) {
        //TODO::校验
        return true;

    },

    //新增考试信息
    addQuestionAction: function () {
        var questionTitle = $('#questionTitle').val();
        var questionContent = $('#questionContent').val();
        var questionType = $('#questionType').val();
        var subjectId = $('#subjectId').val();
        var optionA = $('#optionA').val();
        var optionB = $('#optionB').val();
        var optionC = $('#optionC').val();
        var optionD = $('#optionD').val();
        var questionAnswer = $('#questionAnswer').val();
        var questionParse = $('#questionParse').val();

        if (manageQuestionBoardPage.checkAddQuestionData(questionTitle, questionContent,
                questionType, optionA, optionB, optionC, optionD, questionAnswer, questionParse)) {
            $.ajax({
                url: app.URL.addQuestionUrl(),
                type: "POST",
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data: JSON.stringify({
                    title: questionTitle,
                    content: questionContent,
                    questionType: questionType,
                    optionA: optionA,
                    optionB: optionB,
                    optionC: optionC,
                    optionD: optionD,
                    answer: questionAnswer,
                    parse: questionParse,
                    subjectId: subjectId
                }),
                success: function (result) {
                    if (result && result['success']) {
                        // 验证通过 刷新页面
                        window.location.reload();
                    } else {
                        $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                            '                <p>' + result.message + '</p>');
                        $('#loginModalErrorMessage').removeClass('hidden');
                    }
                },
                error: function (result) {
                    $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                        '                <p>' + result.message + '</p>');
                    $('#loginModalErrorMessage').removeClass('hidden');
                }
            });
        }
    },

    //编辑题目模态框触发
    updateQuestionModalAction: function (index) {
        manageQuestionBoardPage.initUpdateQuestionData(index);
        $("#updateQuestionModal").modal({
            keyboard: false,
            show: true,
            backdrop: "static"
        });
    },

    //初始化编辑题目模态框
    initUpdateQuestionData: function (index) {
        //初始化数据
        $('#updateQuestionIndex').val(index);
        $('#updateQuestionTitle').val(questions[index].title);
        $('#updateQuestionContent').val(questions[index].content);
        var selectQuestionTypes = document.getElementById('updateQuestionType');
        for (var i = 0; i < selectQuestionTypes.length; i++) {
            if (selectQuestionTypes[i].value == questions[index].questionType) {
                selectQuestionTypes[i].selected = true;
            }
        }
        var selectSubjectIds = document.getElementById('updateSubjectId');
        for (var i = 0; i < selectSubjectIds.length; i++) {
            if (selectSubjectIds[i].value == questions[index].subjectId) {
                selectSubjectIds[i].selected = true;
            }
        }
        $('#updateOptionA').val(questions[index].optionA);
        $('#updateOptionB').val(questions[index].optionB);
        $('#updateOptionC').val(questions[index].optionC);
        $('#updateOptionD').val(questions[index].optionD);
        $('#updateQuestionAnswer').val(questions[index].answer);
        $('#updateQuestionParse').val(questions[index].parse);
    },

    //检查模态框数据
    checkUpdateQuestionData: function (questionTitle, questionContent, questionType,
                                       optionA, optionB, optionC, optionD,
                                       questionAnswer, questionParse) {
        //TODO::校验
        return true;

    },


    //ajax更新题目信息
    updateQuestionAction: function () {
        var index = $('#updateQuestionIndex').val();
        var questionTitle = $('#updateQuestionTitle').val();
        var questionContent = $('#updateQuestionContent').val();
        var questionType = $('#updateQuestionType').val();
        var subjectId = $('#updateSubjectId').val();
        var optionA = $('#updateOptionA').val();
        var optionB = $('#updateOptionB').val();
        var optionC = $('#updateOptionC').val();
        var optionD = $('#updateOptionD').val();
        var questionAnswer = $('#updateQuestionAnswer').val();
        var questionParse = $('#updateQuestionParse').val();

        if (manageQuestionBoardPage.checkUpdateQuestionData(questionTitle, questionContent,
                questionType, optionA, optionB, optionC, optionD, questionAnswer, questionParse)) {
            $.ajax({
                url: app.URL.updateQuestionUrl(),
                type: "POST",
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data: JSON.stringify({
                    id: questions[index].id, //此处有Bug
                    title: questionTitle,
                    content: questionContent,
                    questionType: questionType,
                    optionA: optionA,
                    optionB: optionB,
                    optionC: optionC,
                    optionD: optionD,
                    answer: questionAnswer,
                    parse: questionParse,
                    subjectId: subjectId
                }),
                success: function (result) {
                    if (result && result['success']) {
                        // 验证通过 刷新页面
                        window.location.reload();
                    } else {
                        $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                            '                <p>' + result.message + '</p>');
                        $('#loginModalErrorMessage').removeClass('hidden');
                    }
                },
                error: function (result) {
                    $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                        '                <p>' + result.message + '</p>');
                    $('#loginModalErrorMessage').removeClass('hidden');
                }
            });
        }
    },

    //ajax删除题目信息
    deleteQuestionAction: function (index) {
        $.ajax({
            url: app.URL.deleteQuestionUrl() + index,
            type: "DELETE",
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result && result['success']) {
                    // 验证通过 刷新页面
                    window.location.reload();
                } else {
                    $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                        '                <p>' + result.message + '</p>');
                    $('#loginModalErrorMessage').removeClass('hidden');
                }
            },
            error: function (result) {
                $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                    '                <p>' + result.message + '</p>');
                $('#loginModalErrorMessage').removeClass('hidden');
            }
        });
    },

    //添加题目到考试中设置时间
    setContestTime: function (value) {
        $("#startTime").val($("#timeTemp" + value).attr("title"));
        $("#endTime").val($("#timeTemp" + value).attr("value"))
    },

    //ajax添加题目到考试中
    addQuestionToContestAction: function (contestId, id) {
        $.ajax({
                url: app.URL.addQuestionToContest(),
                type: "GET",
                data: {
                    contestId: contestId,
                    id: id
                }
                ,
                success: function (result) {
                    if (result && result['success']) {
                        toastr.success("添加成功，请前往考试管理中编辑分数和难度！");
                        $("#addQuestionToContestModal").modal('hide');
                    }
                    else {
                        toastr.error("添加失败：" + result['message']);
                    }
                }
                ,
                error: function (result) {
                    toastr.error("添加失败：" + result['message']);
                }
            }
        )
        ;
    },

    //查询题目信息
    queryQuestionAction: function () {
        var content = $('#content').val();
        window.location.href = app.URL.manageQuestionUrl() + '?page=1&content=' + content;
    }

};


