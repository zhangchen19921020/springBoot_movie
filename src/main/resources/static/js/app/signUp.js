var app = new Vue({
    el: '#app',
    data: {
        movie: {}
    },
    methods: {
        sendCode: function () {

            $("#btn_sendcode").attr('disabled', 'disabled').text('发送中');

            var loginName = $("#loginName").val();
            if (!loginName) {
                alert("请输入邮箱")
                return;
            }
            $.ajax({
                type: 'post',
                url: "/api/sendCode.do",
                data: {email: loginName},
                asnyc: true,
                dataType: "json",
                success: function (result) {
                    if (result.success) {
                        alert("发送成功");
                        app.codeCont(10);
                    } else {
                        alert(data.errorMsg)
                    }
                }
            });
        },
        codeCont: function (c) {

            if (c === 0) {
                $("#btn_sendcode").removeAttr('disabled').html("发送验证码");
                return;
            }

            $("#btn_sendcode").html(c--);
            setTimeout(function () {
                app.codeCont(c)  //递归每隔1秒执行一次自己
            }, 1000);

        },
        signup: function () {
            $.ajax({
                type: 'post', //默认get
                url: "/api/signup.do",
                data: $("#form1").serialize(),
                async: true,   //是否异步（默认true：异步）
                dataType: "json",//定义服务器返回的数据类型
                success: function (data) {//data服务器返回的json字符串
                    if (data.success) {
                        alert("注册成功");
                        window.location.href="/"//登陆成功，跳转到index页面
                    } else {
                        alert(data.errorMsg)
                    }
                }
            })
        }
    },
    created: function () {

        $.ajax({
            type: 'post', //默认get
            url: "/api/getMovieInfo.do",
            data: {id: id},
            async: true,   //是否异步（默认true：异步）
            dataType: "json",//定义服务器返回的数据类型
            success: function (data) {//data服务器返回的json字符串
                if (data.success) {
                    app.movie = data.data;
                } else {
                    alert(data.errorMsg)
                }

            }
        });
    }
});