<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign up</title>
</head>
<body>
<a href="/home">Go home</a>
<form action="/signUp" method="post">
    <input type="text" name="email" placeholder="Email">
    <input type="text" name="name" placeholder="Name">
    <input type="text" name="surname" placeholder="Surname">
    <input type="password" name="password" placeholder="Password">
    <input type="submit" value="Sign up">
</form>
<div>Already have account? <a href="/login">Log In</a></div>
</body>
</html>
