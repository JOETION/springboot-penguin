/**
 * 模块化JavaScript
 **/
var manageEditContestProblemPage = {
    data: {
        contest: null,
        questions: [],
    },
    init: function (contest, questions) {
        manageEditContestProblemPage.data.contest = contest;
        manageEditContestProblemPage.data.questions = questions;

        //新增题目，弹出新增窗口
        $("#addQuestionBtn").click(function () {
            //输入框初始化数据
            manageEditContestProblemPage.initAddQuestionData();
            $("#addQuestionModal").modal({
                keyboard: false,
                show: true,
                backdrop: "static"
            });
        });

        //新增题目，取消题目增加
        $('#cancelAddQuestionBtn').click(function () {
            $("#addQuestionModal").modal('hide');
        });

        //新增考试，确定增加考试
        $('#confirmAddQuestionBtn').click(function () {
            manageEditContestProblemPage.addContestContentAction();
        });

        //编辑题目，取消题目编辑
        $('#cancelUpdateQuestionBtn').click(function () {
            $("#updateQuestionModal").modal('hide');
        });

        //编辑题目，确定保存考试
        $('#confirmUpdateQuestionBtn').click(function () {
            var index = $('#updateQuestionIndex').val();
            var questionDifficulty = $('#updateQuestionDifficulty').val();
            var questionScore = $('#updateQuestionScore').val();
            manageEditContestProblemPage.updateContestContentAction(index, questionScore, questionDifficulty);
        });

    },
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
        $('#questionScore').val("");
    },
    checkAddQuestionData: function (questionTitle, questionContent, questionType,
                                    optionA, optionB, optionC, optionD,
                                    questionAnswer, questionParse
                                    ) {
        //TODO::校验
        return true;

    },
    addContestContentAction: function () {
        var questionTitle = $('#questionTitle').val();
        var questionContent = $('#questionContent').val();
        var questionType = $('#questionType').val();
        var optionA = $('#optionA').val();
        var optionB = $('#optionB').val();
        var optionC = $('#optionC').val();
        var optionD = $('#optionD').val();
        var questionAnswer = $('#questionAnswer').val();
        var questionParse = $('#questionParse').val();
        var questionDifficulty = $('#questionDifficulty').val();
        var questionScore = $('#questionScore').val();
        var contestId = manageEditContestProblemPage.data.contest.id;
        var subjectId = manageEditContestProblemPage.data.contest.subjectId;

        if (manageEditContestProblemPage.checkAddQuestionData(questionTitle, questionContent,
                questionType, optionA, optionB, optionC, optionD, questionAnswer, questionParse
                )) {
            $.ajax({
                url: app.URL.addContestContentUrl(),
                type: "POST",
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data: JSON.stringify({
                    question:{
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
                    },
                    contestContent:{
                        score:questionScore,
                        diffculty:questionDifficulty,
                        state:0,
                        questionId:0,
                        contestId:contestId
                    }

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
        //编辑考试，弹出编辑窗口
        //console.log(index);
        //输入框初始化数据
        manageEditContestProblemPage.initUpdateQuestionData(index);
        $("#updateQuestionModal").modal({
            keyboard: false,
            show: true,
            backdrop: "static"
        });
    },
    initUpdateQuestionData: function (index) {
        //初始化数据
        $('#updateQuestionIndex').val(index);
        $('#updateQuestionTitle').val(questions[index].question.title);
        $('#updateQuestionContent').val(questions[index].question.content);
        var selectQuestionTypes = document.getElementById('updateQuestionType');
        for (var i = 0; i < selectQuestionTypes.length; i++) {
            if (selectQuestionTypes[i].value == questions[index].question.questionType) {
                selectQuestionTypes[i].selected = true;
            }
        }
        $('#updateOptionA').val(questions[index].question.optionA);
        $('#updateOptionB').val(questions[index].question.optionB);
        $('#updateOptionC').val(questions[index].question.optionC);
        $('#updateOptionD').val(questions[index].question.optionD);
        $('#updateQuestionAnswer').val(questions[index].question.answer);
        $('#updateQuestionParse').val(questions[index].question.parse);
        var selectQuestionDifficulty = document.getElementById('updateQuestionDifficulty');
        for (var i = 0; i < selectQuestionDifficulty.length; i++) {
            if (selectQuestionDifficulty[i].value == questions[index].contestContent.diffculty) {
                selectQuestionDifficulty[i].selected = true;
            }
        }
        $('#updateQuestionScore').val(questions[index].contestContent.score);
    },
    checkUpdateQuestionData: function (questionTitle, questionContent, questionType,
                                       optionA, optionB, optionC, optionD,
                                       questionAnswer, questionParse, questionDifficulty,
                                       questionScore) {
        //TODO::校验
        return true;

    },
    //更新考试内容，但不会删除题库里的题
    updateContestContentAction: function (index, score, diffculty) {

        var index = $('#updateQuestionIndex').val();
        var questionTitle = $('#updateQuestionTitle').val();
        var questionContent = $('#updateQuestionContent').val();
        var questionType = $('#updateQuestionType').val();
        var optionA = $('#updateOptionA').val();
        var optionB = $('#updateOptionB').val();
        var optionC = $('#updateOptionC').val();
        var optionD = $('#updateOptionD').val();
        var questionAnswer = $('#updateQuestionAnswer').val();
        var questionParse = $('#updateQuestionParse').val();
        var subjectId = manageEditContestProblemPage.data.contest.subjectId;

        var id = manageEditContestProblemPage.data.contest.id;
        var questionId = manageEditContestProblemPage.data.questions[index].question.id;
        if (manageEditContestProblemPage.checkUpdateQuestionData(questionTitle, questionContent,
                questionType, optionA, optionB, optionC, optionD, questionAnswer, questionParse)) {
            $.ajax({
                url: app.URL.updateContestContentUrl() + "?id=" + id + "&questionId=" + questionId + "&score=" + score + "&diffculty=" + diffculty,
                type: "POST",
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data: JSON.stringify({
                    question: {
                        id: questionId,
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
                    },
                    contestContent:{
                        score: score,
                        diffculty: diffculty,
                        state: 0,
                        questionId: questionId,
                        contestId: id
                    }

                }),
                success: function (result) {
                    if (result && result['success']) {
                        // 验证通过 刷新页面
                        window.location.reload();
                    } else {

                    }
                },
                error: function (result) {

                }
            });
        }
    },


    //删除考试内容，但并不删除题库问题
    deleteQuestionAction: function (questionId) {
        $.ajax({
            url: app.URL.deleteContestContentUrl(),
            type: "GET",
            data: {
                contestId: manageEditContestProblemPage.data.contest.id,
                questionId: questionId

            },
            success: function (result) {
                if (result && result['success']) {
                    // 验证通过 刷新页面
                    window.location.reload();
                } else {

                }
            },
            error: function (result) {

            }
        });
    },


};