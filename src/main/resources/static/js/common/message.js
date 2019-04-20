/**
 * Created by Administrator on 2019/4/7.
 */

//设置上下文
$(function () {
    $('.ui.sidebar')
        .sidebar({
            context: $('.myMessage')
        });
});


function show() {
    $('.ui.sidebar')
        .sidebar({
            context: $('.myMessage')
        }).sidebar('setting', 'transition', 'overlay').sidebar("toggle");

    $('.ui.accordion').accordion();
}

function deleteMessage(obj, id) {

    //后台获取json数据
    $.ajax({
        url: app.URL.deleteMessageUrl(),
        type: "GET",
        <!-- 向后端传输的数据 -->
        data: {
            id: id
        },
        success: function (result) {
            if (result && result['success']) {

                //删除消息
                $(obj).parent().parent().fadeOut("slow", function () {
                    $(this).remove();
                });

            } else {
                console.log(result.message);
            }
        },
        error: function (result) {
            console.log(result.message);
        }
    });


}

function readedMessage(obj, id) {

    //改变已读标签颜色
    var readed = $(obj).attr("readed");
    if (readed == "false") {
        //后台获取json数据
        $.ajax({
            url: app.URL.updateMessageStateUrl(),
            type: "GET",
            <!-- 向后端传输的数据 -->
            data: {
                id: id
            },
            success: function (result) {
                if (result && result['success']) {

                    //更新消息
                    $(obj).css("color", "");
                    $(obj).parent().parent().children().eq(0).append("&nbsp;&nbsp;&nbsp;已读");
                    $(obj).attr("readed", "true");


                } else {
                    console.log(result.message);
                }
            },
            error: function (result) {
                console.log(result.message);
            }
        });

    } else {
        alert("更新信息失败！");
    }


}