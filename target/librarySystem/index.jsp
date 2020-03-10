<%@ page contentType="text/html;charset=UTF-8"  language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>图书馆首页</title>
    <link rel="stylesheet" href="static/css/bootstrap.min.css">
    <script src="static/js/jquery-3.2.1.js"></script>
    <script src="static/js/js.cookie.js"></script>
    <style>
        #login{
            height: 50%;
            width: 28%;
            margin-left: auto;
            margin-right: auto;
            margin-top: 5%;
            display: block;
            position: center;
        }

        .form-group {
            margin-bottom: 0;
        }
        * {
            padding:0;
            margin:0;
        }

    </style>
</head>
<body background="static/img/sanjicaihua.jpg" style="background-repeat: no-repeat; background-size: 100% 100%;background-attachment: fixed">
<c:if test="test=${!empty error}">
    <script>
        alert(${error});
        window.location.href="login.html";
    </script>
</c:if>
<h2 style="text-align: center; color: white; font-family: '华文行楷'; font-size: 500%">图 书 馆</h2>

<div class="panel panel-default" id="login">
    <div class="panel-heading" style="background-color: #fff">
        <h3 class="panel-title" style="font-family: Cambria;font-style: italic;color: red" >使用学生账号或管理账号登录</h3>
    </div>
    <div class="panel-body">
        <div class="form-group">
            <label for="id">账号</label>
            <input type="text" class="form-control" id="id" placeholder="请输入账号">
        </div>
        <div class="form-group">
            <label for="passwd">密码</label>
            <input type="password" class="form-control" id="passwd" placeholder="请输入密码">
        </div>
        <div class="checkbox text-left">
            <label>
                <input type="checkbox" id="remember">记住密码
            </label>
        </div>

        <p style="text-align: right;color: red;position: absolute" id="info"></p><br/>
        <button id="loginButton"  class="btn btn-primary  btn-block">登陆
        </button>
    </div>
</div>
<script>
    // 提示信息
    $("#id").keyup(function () {
        if (isNaN($("#id").val())){
            $("#info").text("提示:账号只能为数字");
        }else {
            $("#info").text("");
        }

    })

    // 记住登录信息
    function rememberLogin(username,password,checked) {
        Cookies.set('loginStatus',{
            username:username,
            password:password,
            remember:checked

        },{expires:30,path: ''})

    }
    // 若选择记住登录信息，则进入页面时设置登录信息
</script>
</body>
</html>
