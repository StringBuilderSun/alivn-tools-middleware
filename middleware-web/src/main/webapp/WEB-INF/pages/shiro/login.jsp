<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shiro登陆</title>
</head>
<body>
<form method="post"  action="shiro/user/checkLogin">
    <label>用户名:</label><input type="text" name="userName"/><br>
    <label>密码:</label><input type="password" name="password"/><br>
    <input type="submit" value="登陆"/>
</form>
</body>
</html>
