function login(){
     $.ajax({
        type: 'post', //默认get
        url: "/api/login.do",
        data: $("#form1").serialize(),//表单序列化传递参数，也会被后台spring识别
        async: true,   //是否异步（默认true：异步）
        dataType: "json",//定义服务器返回的数据类型
        success: function (result) {//data服务器返回的json字符串
            if (result.success) {
             window.location.href="/"//登陆成功，跳转到movie的index页面
            } else {
                alert(result.errorMsg)
            }

        }
    });


}