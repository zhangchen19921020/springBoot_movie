var app = new Vue({
    el: '#app',
    data: {
        movie: {},
        selectedSeats: [],//已经选中的座位
        unavailableSeats: []//已经售出的
    },
    watch: {//
        unavailableSeats: function (newValue, oldValue) {
            $.each(newValue, function (index, value) {
                $("#" + value).removeClass("available").addClass("unavailable");//改成已售出
            })

        }
    },
    methods: {

        listSaledSeats:function() {

            $.ajax({
                type: 'post', //默认get
                url: "/api/listSaledSeats.do",
                data: {showTimeId: showTimeId},
                async: true,   //是否异步（默认true：异步）
                dataType: "json",//定义服务器返回的数据类型
                success: function (data) {//data服务器返回的json字符串
                    if (data.success) {
                        // $.each(data.data, function (index, value) {
                        //     $("#" + value).removeClass("available").addClass("unavailable");//改成已售出
                        // })
                        app.unavailableSeats = data.data;
                    } else {
                        alert(data.errorMsg)
                    }

                }
            });

        },


        formatSeat: function (seat) {
            var strs = seat.split('_');
            return strs[0] + '排' + strs[1] + '座';

        },

        chooseSeat: function (event) {
            var selectedSeatDom = event.currentTarget;
            var jQuery_selectedSeatDom = $(selectedSeatDom);
            // var id = selectedSeatDom.getAttribute("id");
            var id = jQuery_selectedSeatDom.attr("id");
            // this.selectedSeats.push(id);
            // selectedSeatDom.removeClass("available").addClass("selected");

            var countTicket = $("#tickects_num");
            var total_price = $("#total_price");


            //如果座位已经售出则不执行后面的逻辑
            if (this.unavailableSeats.includes(id)) {
                return;
            }
            if (this.selectedSeats.indexOf(id) === -1) {
                jQuery_selectedSeatDom.removeClass("available").addClass("selected");
                this.selectedSeats.push(id);//向数组尾部添加
                countTicket.text(this.selectedSeats.length);
                total_price.text(this.selectedSeats.length * this.movie.price)
            } else {
                jQuery_selectedSeatDom.removeClass("selected").addClass("available");
                this.selectedSeats.splice(this.selectedSeats.indexOf(id), 1);//取消选中
                countTicket.text(this.selectedSeats.length);
                total_price.text(this.selectedSeats.length * this.movie.price)
            }
        },
        addOrder: function () {//添加订单
            var showTimeId = UrlParam.param("showTimeId");
            var movieId = UrlParam.param("movieId");
            $.ajax({
                type: 'post', //默认get
                url: "/api/addOrder.do",
                data: {
                    movieId: movieId,//需要查
                    showTimeId: showTimeId,
                    selectedSeats: app.selectedSeats
                },
                async: true,   //是否异步（默认true：异步）
                dataType: "json",//定义服务器返回的数据类型
                success: function (data) {//data服务器返回的json字符串
                    if (data.success) {
                        $("#app").html(data.data);//把支付宝表单放在html页面上
                    } else {
                        alert(data.errorCode)

                        if (data.errorCode === 600) {
                            //addOrder会被拦截到拦截器里面，如果发现未登陆就返回AjaxResult.fail(),转到login.html里面
                            window.location.href = "/login.html";
                        }

                        //重新刷新
                        //从后台获取已经售出的座位
                        app.listSaledSeats();//下单之前再获取一次已经卖掉的订单

                        //清空已选择座位
                        app.selectedSeats = [];


                    }

                }
            });
        }


    },

    mounted: function () {

        var showTimeId = UrlParam.param("showTimeId");
        var movieId = UrlParam.param("movieId");

        //从后台获取已经售出的座位
        $.ajax({
            type: 'post', //默认get
            url: "/api/listSaledSeats.do",
            data: {showTimeId: showTimeId},
            async: true,   //是否异步（默认true：异步）
            dataType: "json",//定义服务器返回的数据类型
            success: function (data) {//data服务器返回的json字符串
                if (data.success) {
                    app.unavailableSeats = data.data;
                } else {

                    alert(data.errorMsg)
                }
            }
        });


        //从后台获取当前电影
        $.ajax({
            type: 'post', //默认get
            url: "/api/getMovieInfo.do",
            data: {id: movieId},
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
