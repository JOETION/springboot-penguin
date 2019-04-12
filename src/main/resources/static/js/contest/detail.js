/**
 * 模块JavaScript
 */
var contestDetailPage = {
    data: {
        contest: null,
        contestDetailVos: [],
        currentQuestionIndex: 0,
        startTime:new Date().getTime()
    },
    init: function (contest, contestDetailVos) {
        contestDetailPage.data.contest = contest;
        contestDetailPage.data.contestDetailVos = contestDetailVos;

        //TODO::考试时间倒计时
        $("#contestTimeCountdown").countdown(new Date(contestDetailPage.data.contest.endTime), function (event) {
            // 事件格式
            var format = event.strftime("%D:%H:%M:%S");
            //console.log(format);
            $("#contestTimeCountdown").html(format);
        }).on('finish.countdown', function () {
            contestDetailPage.finishContestAction();
        });

        if (contestDetailPage.data.contestDetailVos[0].question.questionType == 0) {
            $('#currentQuetionTitle').html('(单选)' + contestDetailPage.data.contestDetailVos[0].question.content + '(' + contestDetailPage.data.contestDetailVos[0].contestContent.score + '分)');
            var selectOptionStr = '<div class="grouped fields">\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="A"/>\n' +
                '        <label>A.&nbsp;&nbsp;' + contestDetailPage.data.contestDetailVos[0].question.optionA + '</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="B"/>\n' +
                '        <label>B.&nbsp;&nbsp;' + contestDetailPage.data.contestDetailVos[0].question.optionB + '</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="C"/>\n' +
                '        <label>C.&nbsp;&nbsp;' + contestDetailPage.data.contestDetailVos[0].question.optionC + '</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="D"/>\n' +
                '        <label>D.&nbsp;&nbsp;' + contestDetailPage.data.contestDetailVos[0].question.optionD + '</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '  </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);
        } else if (contestDetailPage.data.contestDetailVos[0].question.questionType == 1) {
            $('#currentQuetionTitle').html('(多选)' + contestDetailPage.data.contestDetailVos[0].question.content + '(' + contestDetailPage.data.contestDetailVos[0].contestContent.score + '分)');
            var selectOptionStr = '<div class="grouped fields">\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="checkbox" name="questionAnswer" value="A"/>\n' +
                '        <label>A.&nbsp;&nbsp;' + contestDetailPage.data.contestDetailVos[0].question.optionA + '</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="checkbox" name="questionAnswer" value="B"/>\n' +
                '        <label>B.&nbsp;&nbsp;' + contestDetailPage.data.contestDetailVos[0].question.optionB + '</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="checkbox" name="questionAnswer" value="C"/>\n' +
                '        <label>C.&nbsp;&nbsp;' + contestDetailPage.data.contestDetailVos[0].question.optionC + '</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="checkbox" name="questionAnswer" value="D"/>\n' +
                '        <label>D.&nbsp;&nbsp;' + contestDetailPage.data.contestDetailVos[0].question.optionD + '</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '  </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);
        } else {
            $('#currentQuetionTitle').html('(问答)' + contestDetailPage.data.contestDetailVos[0].question.content + '(' + contestDetailPage.data.contestDetailVos[0].contestContent.score + '分)');
            var selectOptionStr = '<div class="field">\n' +
                '                        <textarea  id="questionAnswer" rows="20"></textarea>\n' +
                '                    </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);
        }
        var currentQuestionButtonStr = '';
        for (var i = 0; i < contestDetailVos.length; i++) {
            var buttonStr = '';
            if (contestDetailPage.data.currentQuestionIndex == i) buttonStr = '<button class="mini ui positive button" style="margin-top: 10px;margin-left: 5px;">' + (i + 1) + '</button>';
            else buttonStr = '<button class="mini ui button" onclick="contestDetailPage.targetQuestionAction(' + i + ')" style="margin-top: 10px;margin-left: 5px;">' + (i + 1) + '</button>';
            currentQuestionButtonStr += buttonStr;
        }
        $('#currentQuestionButton').html(currentQuestionButtonStr);
    },
    targetQuestionAction: function (index) {
        var preIndex = contestDetailPage.data.currentQuestionIndex;
        contestDetailPage.data.currentQuestionIndex = index;

        //记录答案
        if (contestDetailPage.data.contestDetailVos[preIndex].question.questionType == 0) {
            contestDetailPage.data.contestDetailVos[preIndex].question.answer = '';
            $.each($("input[name='questionAnswer']:checked"), function () {
                contestDetailPage.data.contestDetailVos[preIndex].question.answer += $(this).val();
            });
        } else if (contestDetailPage.data.contestDetailVos[preIndex].question.questionType == 1) {
            contestDetailPage.data.contestDetailVos[preIndex].question.answer = '';
            $.each($("input[name='questionAnswer']:checked"), function () {
                contestDetailPage.data.contestDetailVos[preIndex].question.answer += $(this).val();
            });
        } else {
            contestDetailPage.data.contestDetailVos[preIndex].question.answer = $("#questionAnswer").val();
        }

        if (contestDetailPage.data.contestDetailVos[index].question.questionType == 0) {
            $('#currentQuetionTitle').html('(单选)' + contestDetailPage.data.contestDetailVos[index].question.content + '(' + contestDetailPage.data.contestDetailVos[index].contestContent.score + '分)');
            var selectOptionStr = '<div class="grouped fields">\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="A"/>\n' +
                '        <label>A.&nbsp;&nbsp;' + contestDetailPage.data.contestDetailVos[index].question.optionA + '</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="B"/>\n' +
                '        <label>B.&nbsp;&nbsp;' + contestDetailPage.data.contestDetailVos[index].question.optionB + '</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="C"/>\n' +
                '        <label>C.&nbsp;&nbsp;' + contestDetailPage.data.contestDetailVos[index].question.optionC + '</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="radio" name="questionAnswer" value="D"/>\n' +
                '        <label>D.&nbsp;&nbsp;' + contestDetailPage.data.contestDetailVos[index].question.optionD + '</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '  </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);

            //显示之前作答区的答案
            if (contestDetailPage.data.contestDetailVos[index].question.answer != '') {
                $.each($("input[name='questionAnswer']"), function () {
                    if (contestDetailPage.data.contestDetailVos[index].question.answer.indexOf($(this).val()) != -1) {
                        $(this).attr("checked", "checked");
                    }
                });
            }
        } else if (contestDetailPage.data.contestDetailVos[index].question.questionType == 1) {
            $('#currentQuetionTitle').html('(多选)' + contestDetailPage.data.contestDetailVos[index].question.content + '(' + contestDetailPage.data.contestDetailVos[index].contestContent.score + '分)');
            var selectOptionStr = '<div class="grouped fields">\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="checkbox" name="questionAnswer" value="A"/>\n' +
                '        <label>A.&nbsp;&nbsp;' + contestDetailPage.data.contestDetailVos[index].question.optionA + '</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="checkbox" name="questionAnswer" value="B"/>\n' +
                '        <label>B.&nbsp;&nbsp;' + contestDetailPage.data.contestDetailVos[index].question.optionB + '</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="checkbox" name="questionAnswer" value="C"/>\n' +
                '        <label>C.&nbsp;&nbsp;' + contestDetailPage.data.contestDetailVos[index].question.optionC + '</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '    <div class="field">\n' +
                '      <div class="ui toggle checkbox">\n' +
                '        <input type="checkbox" name="questionAnswer" value="D"/>\n' +
                '        <label>D.&nbsp;&nbsp;' + contestDetailPage.data.contestDetailVos[index].question.optionD + '</label>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '  </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);

            //显示之前作答区的答案
            if (contestDetailPage.data.contestDetailVos[index].question.answer != '') {
                $.each($("input[name='questionAnswer']"), function () {
                    if (contestDetailPage.data.contestDetailVos[index].question.answer.indexOf($(this).val()) != -1) {
                        $(this).attr("checked", "checked");
                    }
                });
            }
        } else {
            $('#currentQuetionTitle').html('(问答)' + contestDetailPage.data.contestDetailVos[index].question.content + '(' + contestDetailPage.data.contestDetailVos[index].contestContent.score + '分)');
            var selectOptionStr = '<div class="field">\n' +
                '                        <textarea  id="questionAnswer" name="questionAnswer" rows="20"></textarea>\n' +
                '                    </div>';
            $('#currentQuestionAnswer').html(selectOptionStr);

            //显示之前作答区的答案
            if (contestDetailPage.data.contestDetailVos[index].question.answer != '') {
                $('#questionAnswer').val(contestDetailPage.data.contestDetailVos[index].question.answer);
            }
        }


        var currentQuestionButtonStr = '';
        for (var i = 0; i < contestDetailPage.data.contestDetailVos.length; i++) {
            var buttonStr = '';
            if (contestDetailPage.data.currentQuestionIndex == i) {
                buttonStr = '<button class="mini ui positive button" style="margin-top: 10px;margin-left: 5px;">' + (i + 1) + '</button>';
            } else if (contestDetailPage.data.contestDetailVos[i].question.answer != '') {
                buttonStr = '<button class="mini ui green basic button" onclick="contestDetailPage.targetQuestionAction(' + i + ')" style="margin-top: 10px;margin-left: 5px;">' + (i + 1) + '</button>';
            } else {
                buttonStr = '<button class="mini ui button" onclick="contestDetailPage.targetQuestionAction(' + i + ')" style="margin-top: 10px;margin-left: 5px;">' + (i + 1) + '</button>';
            }
            currentQuestionButtonStr += buttonStr;
        }
        $('#currentQuestionButton').html(currentQuestionButtonStr);
    },
    //交卷事假触发
    finishContestAction: function () {
        var index = contestDetailPage.data.currentQuestionIndex;
        //记录答案
        if (contestDetailPage.data.contestDetailVos[index].question.questionType == 0) {
            contestDetailPage.data.contestDetailVos[index].question.answer = '';
            $.each($("input[name='questionAnswer']:checked"), function () {
                contestDetailPage.data.contestDetailVos[index].question.answer += $(this).val();
            });
        } else if (contestDetailPage.data.contestDetailVos[index].question.questionType == 1) {
            contestDetailPage.data.contestDetailVos[index].question.answer = '';
            $.each($("input[name='questionAnswer']:checked"), function () {
                //console.log($(this).val());
                contestDetailPage.data.contestDetailVos[index].question.answer += $(this).val();
            });
        } else {
            //console.log($("#questionAnswer").val());
            contestDetailPage.data.contestDetailVos[index].question.answer = $("#questionAnswer").val();
        }
        //TODO::交卷
        contestDetailPage.submittingContestAction();
    },


    //答案模板
    generateAnswerModel: function () {
        var obj = new Object();
        obj.answerJson = [];
        var questionId;
        var questionType;
        var answer;
        for (var i = 0; i < contestDetailPage.data.contestDetailVos.length; i++) {
            var temp = contestDetailPage.data.contestDetailVos[i].question;
            questionId = temp.id;
            questionType = temp.questionType;
            answer = temp.answer;
            obj.answerJson.push({ "questionType": questionType,"questionId": questionId,"answer": answer});
        }
        return obj;
    },


    //正在交卷
    submittingContestAction: function (studentId) {
        $('#waitSubmitModal').modal({
            /**
             * 必须点击相关按钮才能关闭
             */
            closable: false,
            /**
             * 模糊背景
             */
            blurring: true,
        }).modal('show');

        //向后端API发送答题卡
        $.ajax({
            url: app.URL.submitGradeUrl(),
            type: "POST",
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            <!-- 向后端传输的数据 -->
            data: JSON.stringify({
                contestId: contestDetailPage.data.contest.id,
                studentId: studentId,
                answerJson: JSON.stringify(contestDetailPage.generateAnswerModel().answerJson),
                startTime: contestDetailPage.data.startTime,
                finishTime: new Date().getTime(),
                state: 0

            }),
            success: function (result) {
                if (result && result['success']) {
                } else {
                    //TODO::发送答题卡出错处理
                    console.log(result.message);
                }
            },
            error: function (result) {
                //TODO::发送答题卡出错处理
                console.log(result.message);
            }
        });

        setTimeout(function () {
            $("#waitSubmitModal").modal("hide");
            window.location.href = app.URL.contestIndexUrl();
        }, 5000);
    },

};