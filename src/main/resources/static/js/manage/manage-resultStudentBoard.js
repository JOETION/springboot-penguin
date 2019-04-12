/**
 * 模块化JavaScript
 **/
var manageResultStudentBoardPage = {
        data: {
            contest: "",
            questions: [],
            contestRankVos: [],
            answer: [],
            pageNum: 0,
            pageSize: 0,
            totalPageNum: 0,
            totalPageSize: 0,
        },
        init: function (contest, questions, contestRankVos, pageNum, pageSize, totalPageNum, totalPageSize) {
            manageResultStudentBoardPage.data.pageNum = pageNum;
            manageResultStudentBoardPage.data.pageSize = pageSize;
            manageResultStudentBoardPage.data.totalPageNum = totalPageNum;
            manageResultStudentBoardPage.data.totalPageSize = totalPageSize;
            manageResultStudentBoardPage.data.contest = contest;
            manageResultStudentBoardPage.data.questions = questions;
            manageResultStudentBoardPage.data.contestRankVos = contestRankVos;


            //分页初始化
            manageResultStudentBoardPage.subPageMenuInit();


            /**
             * toastr提示消息位置
             */
            toastr.options.positionClass = 'toast-top-center';

            //评分，取消分数编辑
            $('#cancelUpdateGradeBtn').click(function () {
                $("#updateGradeModal").modal('hide');
            });

            //评分，确定提交分数
            $('#submitUpdateGradeBtn').click(function () {
                var answer = manageResultStudentBoardPage.data.answer;
                var accountId = document.getElementById("selectContest").options[0].getAttribute("value");
                var questionLength = manageResultStudentBoardPage.data.questions.length;
                var answerNum = 0;
                for (var i in answer) {
                    if (answer[i].studentId == accountId) {
                        answerNum++;
                    }
                }
                if (questionLength != answerNum) {
                    toastr.error("请批完该生的所有主观题！")
                } else {
                    manageResultStudentBoardPage.updateGradeAction();
                }

            });


            //contestId可以从contest对象中获得
            $("#confirmUpdateGradeBtn").click(function () {
                var contestId = $('#selectContest').find("option:selected").attr("name");
                var grade = $("#grade").val();
                var gradeReason = $("#gradeReason").val();
                var studentId = document.getElementById("selectContest").options[0].getAttribute("value");
                if (grade.trim() != '' && grade != null && gradeReason.trim() != '' && gradeReason != null)
                    manageResultStudentBoardPage.saveAnswer(studentId, contestId, $("#selectContest").val(), grade, gradeReason);
            });

        },


        //编辑成绩模态框触发,index是对应考试的索引
        updateGradeModalAction: function (index) {
            //输入框初始化数据
            manageResultStudentBoardPage.initGradeModal(index);

            var contestResultDto = manageResultStudentBoardPage.data.contestRankVos[index];
            //第0号选项用于保存学生编号和考试结果索引
            var accountId = contestResultDto.account.id;
            var questions = manageResultStudentBoardPage.data.questions;
            var text;
            document.getElementById("gradeTitle").innerHTML = contestResultDto.account.name + "的主观题答题卡 (每批完一道题保存，批完所有题提交)";
            $("#selectContest").append("<option value='" + accountId + "'name='" + index + "'>请选择</option>");
            for (var i = 0; i < questions.length; i++) {
                if (manageResultStudentBoardPage.isExistAnswer(accountId, contestResultDto.grade.contestId, questions[i].id)) {
                    text = "第 " + (i + 1) + " 道题  以批阅";
                } else {
                    text = "第 " + (i + 1) + " 道题";
                }
                $("#selectContest").append("<option value='" + questions[i].id + "'name='" + contestResultDto.grade.contestId + "'>" + text + "</option>");
            }
            $("#updateGradeModal").modal({
                keyboard: false,
                show: true,
                backdrop: "static"
            });
        }
        ,

        isExistAnswer: function (studentId, contestId, questionId) {
            var isExist = false;
            var answer = manageResultStudentBoardPage.data.answer;
            for (var i in answer) {
                if (answer[i].studentId == studentId && answer[i].contestId == contestId && answer[i].questionId == questionId) {
                    isExist = true;
                    break;
                }
            }
            return isExist;
        },


        //初始化编辑成绩模态框
        initGradeModal: function (index) {
            $("#gradeTitle").val("");
            document.getElementById("selectContest").options.length = 0;
            $("#content").val("");
            $("#height").height(60);
            $("#studentAnswer").val("");
            $("#studentAnswer").height(60);
            $("#answer").val("");
            $("#answer").height(60);
            $("#grade").val("");
            $("#gradeReason").val("");
            $("#parse").val("");
            $("#parse").height(60);
        },

        //设置主观题的数据，每次数据改变，就执行一次
        setManulQuestionData: function (questionId) {
            var answer = manageResultStudentBoardPage.data.answer;
            var questions = manageResultStudentBoardPage.data.questions;
            var question;
            for (var i = 0; i < questions.length; i++) {
                if (questions[i].id == questionId) {
                    question = questions[i];
                }
            }
            $("#grade").val("");
            $("#gradeReason").val("");
            var index = document.getElementById("selectContest").options[0].getAttribute("name");
            var contestResultDto = manageResultStudentBoardPage.data.contestRankVos[index];
            var manulQuestions = contestResultDto.answerDto.answerContents;
            var accountId = contestResultDto.account.id;
            for (var i = 0; i < manulQuestions.length; i++) {
                if (manulQuestions[i].questionId == questionId) {
                    //40个字一行，60px
                    $("#content").val(question.content);
                    $("#content").height((question.content.length / 45) * 60);
                    $("#studentAnswer").val(manulQuestions[i].answer);
                    $("#studentAnswer").height((manulQuestions[i].answer.length / 45) * 60);
                    $("#answer").val(question.answer);
                    $("#answer").height((question.answer.length / 45) * 60);
                    $("#parse").val(question.parse);
                    $("#parse").height((question.parse.length / 45) * 60);
                    if (manageResultStudentBoardPage.getJsonLength(manageResultStudentBoardPage.data.answer) != 0) {
                        for (var i1 in answer) {
                            if (answer[i1].questionId == questionId && answer[i1].studentId == accountId) {
                                $("#grade").val(answer[i1].manulResult);
                                $("#gradeReason").val(answer[i1].manulReason);
                                break;
                            }
                        }

                    }
                }
            }
        }
        ,

        //保存答案,这些数据将传上服务器,包括主观题分数，分数原因
        saveAnswer: function (studentId, contestId, questionId, manualResult, manualReason) {
            var record = {"studentId": studentId, "contestId": contestId, "questionId": questionId, "manulResult": manualResult, "manulReason": manualReason};
            var answer = manageResultStudentBoardPage.data.answer;

            for (var i in answer) {
                if (answer[i].studentId == studentId && answer[i].contestId == contestId && answer[i].questionId == questionId) {
                    answer.splice(i, 1); //删除重复元素，防止用户一直点保存
                }
            }

            answer.push(record);
            var selectContest = document.getElementById("selectContest");
            var index = selectContest.selectedIndex;
            var innerHTML = selectContest.options[index].innerHTML;
            if (innerHTML.length < 7)
                selectContest.options[index].innerHTML += "   以批阅";
            toastr.success("数据保存成功！");
        },


        getJsonLength: function (json) {
            var jsonLength = 0;
            for (var i in json) {
                jsonLength++;
            }
            return jsonLength;
        },

//更新批改后的分数
        updateGradeAction: function () {

            //得到所有答题分数
            var answer = manageResultStudentBoardPage.data.answer;
            var manualResult = 0;
            var manualReasons = [];
            //上传到服务器数据
            for (var i in answer) {
                var record = {"questionId": answer[i].questionId, "questionType": 2, "manulReason": answer[i].manulReason};
                manualReasons.push(record);
            }
            //获取json数组长度
            var length = manageResultStudentBoardPage.getJsonLength(answer);
            for (var i = 0; i < length; i++) {
                if (answer != null && answer != undefined && answer != '') {
                    manualResult += parseInt(answer[i].manulResult);
                }
            }
            var index = document.getElementById("selectContest").options[0].getAttribute("name");
            var grade = manageResultStudentBoardPage.data.contestRankVos[index].grade;
            $.ajax({
                url: app.URL.finishGradeUrl(),
                type: "POST",
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data: JSON.stringify({
                    grade: {
                        manulResult: manualResult,
                        manulReason: JSON.stringify(manualReasons),
                        result: (parseInt(grade.autoResult) + parseInt(manualResult)),
                        contestId: manageResultStudentBoardPage.data.answer[0].contestId,
                        studentId: manageResultStudentBoardPage.data.answer[0].studentId
                    }
                }),
                success: function (result) {
                    if (result && result['success']) {

                        // 验证通过 刷新页面
                        toastr.success("提交成功！");
                        window.location.reload();
                    } else {
                        toastr.error("提交失败，原因：" + result.message);
                    }
                },
                error: function (result) {
                    toastr.error("提交失败，原因：" + result.message);
                }
            });
        },

//完成考试批改ajax
        finishContestAction: function (contestId) {
            $.ajax({
                url: app.URL.finishContestUrl() + contestId,
                type: "POST",
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                success: function (result) {
                    if (result && result['success']) {
                        // 验证通过 刷新页面
                        window.location.reload();
                    } else {
                        toastr.error("提交失败，原因：" + result.message);
                    }
                },
                error: function (result) {
                    toastr.error("提交失败，原因：" + result.message);
                }
            });
        },

        //table排序
        tableSort: function () {
            $("#sortTable").tablesorter({
                headers: {
                    // 列序号默认从0开始
                    1: {
                        // 第二列不可排序
                        sorter: false
                    },
                    5: {
                        sorter: false
                    }
                },
            });
        },


        //分页开始----------------------------------------------------------------------------------------

        firstPage: function () {

            //此处获得contestId的方法，后期可以借鉴一下
            var contestId = manageResultStudentBoardPage.data.contest.id;
            window.location.href = app.URL.manageResultStudentListUrl(contestId) + '?page=1';
        },
        prevPage: function () {
            var contestId = manageResultStudentBoardPage.data.contest.id;
            window.location.href = app.URL.manageResultStudentListUrl(contestId) + '?page=' + (pageNum - 1);
        },
        targetPage: function (page) {
            var contestId = manageResultStudentBoardPage.data.contest.id;
            window.location.href = app.URL.manageQuestionUrl(contestId) + '?page=' + page;
        },
        nextPage: function () {
            var contestId = manageResultStudentBoardPage.data.contest.id;
            window.location.href = app.URL.manageQuestionUrl(contestId) + '?page=' + (pageNum + 1);
        },
        lastPage: function () {
            var contestId = manageResultStudentBoardPage.data.contest.id;
            window.location.href = app.URL.manageQuestionUrl(contestId) + '?page=' + manageResultStudentBoardPage.data.totalPageNum;
        },
        subPageMenuInit: function () {
            var subPageStr = '<ul class="pagination">';
            if (manageResultStudentBoardPage.data.pageNum == 1) {
                subPageStr += '<li class="disabled"><a><span>首页</span></a></li>';
                subPageStr += '<li class="disabled"><a><span>上一页</span></a></li>';
            } else {
                subPageStr += '<li><a onclick="manageResultStudentBoardPage.firstPage()"><span>首页</span></a></li>';
                subPageStr += '<li><a onclick="manageResultStudentBoardPage.prevPage()"><span>上一页</span></a></li>';
            }
            var startPage = (manageResultStudentBoardPage.data.pageNum - 4 > 0 ? manageResultStudentBoardPage.data.pageNum - 4 : 1);
            var endPage = (startPage + 7 > manageResultStudentBoardPage.data.totalPageNum ? manageResultStudentBoardPage.data.totalPageNum : startPage + 7);
            console.log('startPage = ' + startPage);
            console.log('endPage = ' + endPage);
            console.log('pageNum = ' + manageResultStudentBoardPage.data.pageNum);
            console.log('totalPageNum = ' + manageResultStudentBoardPage.data.totalPageNum);
            for (var i = startPage; i <= endPage; i++) {
                if (i == manageResultStudentBoardPage.data.pageNum) {
                    subPageStr += '<li class="active"><a onclick="manageResultStudentBoardPage.targetPage(' + i + ')">' + i + '</a></li>';
                } else {
                    subPageStr += '<li><a onclick="manageResultStudentBoardPage.targetPage(' + i + ')">' + i + '</a></li>';
                }
            }
            if (manageResultStudentBoardPage.data.pageNum == manageResultStudentBoardPage.data.totalPageNum) {
                subPageStr += '<li class="disabled"><a><span>下一页</span></a></li>';
                subPageStr += '<li class="disabled"><a><span>末页</span></a></li>';
            } else {
                subPageStr += '<li><a onclick="manageResultStudentBoardPage.nextPage()"><span>下一页</span></a></li>';
                subPageStr += '<li><a onclick="manageResultStudentBoardPage.lastPage()"><span>末页</span></a></li>';
            }
            $('#subPageHead').html(subPageStr);
        }

        //分页结束-----------------------------------------------------------------------


    }
    ;