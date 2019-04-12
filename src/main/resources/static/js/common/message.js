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
        })
    $('.ui.sidebar')
        .sidebar('toggle');
    $('.ui.accordion').accordion();
}

function deleteMessage(obj) {
    //数据库删除逻辑
    //删除消息
    $(obj).parent().parent().fadeOut("slow", function () {
        $(this).remove();
    });

}

function readedMessage(obj) {
    //数据库已读逻辑

    //改变已读标签颜色
    var readed = $(obj).attr("readed");
    if (readed == "false") {
        $(obj).css("color", "");
        $(obj).parent().parent().children().eq(0).append("&nbsp;&nbsp;&nbsp;已读");
        $(obj).attr("readed", "true");
    }
}