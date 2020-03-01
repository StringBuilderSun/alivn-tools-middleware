<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>主页</title>
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/styles.css" rel="stylesheet">
    <script src="js/jquery-2.1.4.min.js"></script>
    <script src="js/bootstrap.js"></script>
</head>
<body>
<div class="htmleaf-container">
    <div class="wrapper">
        <div class="container">
            <h1>云梦丝产品营销系统</h1>
            <form class="form" id="login_form">
                <input type="text"  placeholder="用户名" name="uid">
                <input type="password" placeholder="密码" name="password">
                <button type="submit" id="login-button">登陆</button>
            </form>
        </div>

        <ul class="bg-bubbles">
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
        </ul>
    </div>
</div>
<script>
    $('#login-button').click(function (event) {
        event.preventDefault();
        $('form').fadeOut(500);
        var formData=$("#login_form").serialize();
        $.ajax({
            method:"POST",
            url:"/login",
            dataType:"json",
//            async : false,
            data:formData,
            success:function (message,status) {
                alert("登陆成功!"+status+":"+message)
            }
        });
    });
</script>


</body>
</html>
