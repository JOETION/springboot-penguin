/**
 * 模块JavaScript
 */
var editDiscussPage = {
        data: {
            E: null,
            editor: null,
            post: null
        },
        init: function (post) {
            editDiscussPage.data.post = post;

            $('#postDiscussSubmitButton').click(function (e) {
                editDiscussPage.updateDiscuss();
            });
            editDiscussPage.data.E = window.wangEditor;
            editDiscussPage.data.editor = new editDiscussPage.data.E('#editor'); // 或者 var editor = new E( document.getElementById('editor') )
            editDiscussPage.data.editor.create();
            editDiscussPage.data.editor.txt.html(post.htmlContent);
        },
        updateDiscuss: function () {
            var editor = editDiscussPage.data.editor;
            var title = $('#postTitle').val();
            var type = $("#type").val();
            $.ajax({
                url: app.URL.updatePostUrl(),
                type: "POST",
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data: JSON.stringify({
                        id: editDiscussPage.data.post.id,
                        title: title,
                        type: type,
                        htmlContent: editor.txt.html(),
                        textContent: editor.txt.text(),
                    }
                ),
                success: function (result) {
                    if (result && result['success']) {
                        window.location.href = document.referrer;//返回上一页并刷新
                    } else {
                        console.log(result.message);
                    }
                }
                ,
                error: function (result) {
                    console.log(result.message);
                }
            })
            ;
        },
    }
    ;