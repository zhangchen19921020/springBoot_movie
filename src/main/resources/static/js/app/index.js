var app = new Vue({
    el: '#app',
    data: {
        movies: []
    },
    created: function () {
        $.ajax({
            type: 'post',
            url: "/api/listMovies.do",
            async: true,
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    app.movies = data.data;
                } else {
                    alert(data.errorMsg)
                }
            }
        });
    }

});