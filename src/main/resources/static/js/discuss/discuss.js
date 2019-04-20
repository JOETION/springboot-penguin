/**
 * 模块JavaScript
 */
var discussPage = {
    data: {
        pageNum: 0,
        pageSize: 0,
        totalPageNum: 0,
        totalPageSize: 0,
        discussVos: [],
    },
    init: function (pageNum, pageSize, totalPageNum, totalPageSize, discussVos) {
        discussPage.data.pageNum = pageNum;
        discussPage.data.pageSize = pageSize;
        discussPage.data.totalPageNum = totalPageNum;
        discussPage.data.totalPageSize = totalPageSize;
        discussPage.data.discussVos = discussVos;
        //分页初始化
        discussPage.subPageMenuInit();

    },
    firstPage: function () {
        window.location.href = app.URL.discussUrl() + '?page=1';
    },
    prevPage: function () {
        window.location.href = app.URL.discussUrl() + '?page=' + (pageNum - 1);
    },
    targetPage: function (page) {
        window.location.href = app.URL.discussUrl() + '?page=' + page;
    },
    nextPage: function () {
        window.location.href = app.URL.discussUrl() + '?page=' + (pageNum + 1);
    },
    lastPage: function () {
        window.location.href = app.URL.discussUrl() + '?page=' + discussPage.data.totalPageNum;
    },
    subPageMenuInit: function () {
        var subPageStr = '';
        if (discussPage.data.pageNum == 1) {
            subPageStr += '<a class="item disabled">首页</a>';
            subPageStr += '<a class="item disabled">上一页</a>';
        } else {
            subPageStr += '<a onclick="discussPage.firstPage()" class="item">首页</a>';
            subPageStr += '<a onclick="discussPage.prevPage()" class="item">上一页</a>';
        }
        var startPage = (discussPage.data.pageNum - 4 > 0 ? discussPage.data.pageNum - 4 : 1);
        var endPage = (startPage + 7 > discussPage.data.totalPageNum ? discussPage.data.totalPageNum : startPage + 7);
        console.log('startPage = ' + startPage);
        console.log('endPage = ' + endPage);
        console.log('pageNum = ' + discussPage.data.pageNum);
        console.log('totalPageNum = ' + discussPage.data.totalPageNum);
        for (var i = startPage; i <= endPage; i++) {
            if (i == discussPage.data.pageNum) {
                subPageStr += '<a onclick="discussPage.targetPage(' + i + ')" class="active item">' + i + '</a>';
            } else {
                subPageStr += '<a onclick="discussPage.targetPage(' + i + ')" class="item">' + i + '</a>'
            }
        }
        if (discussPage.data.pageNum == discussPage.data.totalPageNum) {
            subPageStr += '<a class="item disabled">下一页</a>';
            subPageStr += '<a class="item disabled">末页</a>';
        } else {
            subPageStr += '<a onclick="discussPage.nextPage()" class="item">下一页</a>';
            subPageStr += '<a onclick="discussPage.lastPage()" class="item">末页</a>';
        }
        $('#subPageMenu').html(subPageStr);
    },


    //参数：帖子类型
    getDiscussByType: function (obj) {
        var items = $(".ui.green.secondary.menu.type a");
        for (var i = 0; i < items.length; i++) {
            $(items[i]).removeClass("active");
        }
        $(obj).addClass("active");

        var item = $(".ui.green.secondary.pointing.menu .active.item");
        discussPage.getDiscussByLevel(item, item.attr("level"));
    },


    //参数：帖子级别
    getDiscussByLevel: function (object, level) {
        var type = $(".ui.green.secondary.menu .item.active").attr("type");

        //后太获取json数据
        $.ajax({
            url: app.URL.getPostsUrl(),
            type: "GET",
            <!-- 向后端传输的数据 -->
            data: {
                pageNum: 1,
                pageSize: 10,
                level: level,
                type: type
            },
            success: function (result) {
                if (result && result['success']) {
                    // 取得数据，更新列表
                    discussPage.data.pageNum = result.data['pageNum'];
                    discussPage.data.pageSize = result.data['pageSize'];
                    discussPage.data.totalPageNum = result.data['totalPageNum'];
                    discussPage.data.totalPageSize = result.data['totalPageSize'];
                    discussPage.data.discussVos = result.data['discussVos'];

                    //更新上下翻页
                    discussPage.subPageMenuInit();
                    //更新帖子信息
                    discussPage.updateDiscussPage(object);
                } else {
                    console.log(result.message);
                }
            },
            error: function (result) {
                console.log(result.message);
            }
        });


    },

    updateDiscussPage: function (object) {

        //激活当前菜单项
        var items = $(".ui.green.secondary.pointing.menu a");
        for (var i = 0; i < items.length; i++) {
            $(items[i]).removeClass("active");
        }
        $(object).addClass("active");

        //删除所有子元素
        var target = $(".ui.divided.items");
        target.empty();

        var discussVos = discussPage.data.discussVos;
        //遍历
        for (var i = 0; i < discussVos.length; i++) {
            var template = "<div class='item'>" + "<div class='ui avatar image mini'>" + "<img src='/upload/images/" + discussVos[i].author.avatarImgUrl + "'/>" +
                " </div>" + " <div class='content'>" + "<a href='/discuss/" + discussVos[i].post.id + "' class='header' style='font-size: 15px;'>" +
                "<p>" + discussVos[i].post.title + "</p>" + "</a>" + "<div class='extra' style='font-size: 13px;'>" + " <span class='right floated'>" +
                "<i class='talk outline icon'></i>" + "<span>" + discussVos[i].post.replyNum + "</span>" + "|" + "<i class='thumbs outline up icon'></i>" +
                "<span>17</span>" + "|" + "  <i class='unhide icon'></i>" + "<span>400</span>" + "</span>" + "<span>" + discussVos[i].author.name + "</span>" +
                " <span>" + discussPage.getDate(discussVos[i].post.createTime) + " 发表在 [" + discussPage.getDiscussTypeName(discussVos[i].post.type) + "] </span>" + "<span>" + "最后回复： " + discussPage.getDate(discussVos[i].post.lastReplyTime) + "</span>" + "</div> </div></div>";
            target.append(template);
        }
    },
    getDate: function (timestamp) {
        var date = new Date(timestamp);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        return y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d) + " " + date.toTimeString().substr(0, 8);
    },

    getDiscussTypeName: function (type) {
        switch (type) {
            case 0:
                return "笔试面经";
            case 1:
                return "我要提问";
            case 2:
                return "技术交流";
            case 3:
                return "产品运营";
            case 4:
                return "留学生";
            case 5:
                return "职业发展";
            case 6:
                return "招聘信息";
            case 7:
                return "资源分享";
            case 8:
                return "猿生活";
        }

    }

}