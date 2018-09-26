var app = new Vue({
    el: '#app',
    data: {
        movie: {}
    },
    created: function () {
        //获取页面传值通过getURLParam.js
        var id = UrlParam.param("id");
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