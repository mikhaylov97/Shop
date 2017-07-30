<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>
<a href="/home">Go home</a>
<form action="/login" method="post">
    <input type="text" name="email" id="email" placeholder="Email" required>
    <input type="password" name="password" id="password" placeholder="Password" required>
    <input type="submit" value="Войти">
</form>
<div>Don't have account yet? <a href="/signUp">Sign Up</a></div>
</body>
</html>
