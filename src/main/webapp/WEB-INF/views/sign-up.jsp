<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home page</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <link href="/resources/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="/resources/css/sign-up.css">
    <link rel="stylesheet" href="/resources/css/transparent-header.css">
    <script src="/resources/js/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/main.js"></script>
</head>
<body>
<%@include file="header.jsp"%>
<div id="headerwrap">
    <div class="container">
        <div class="row centered">
            <div class="col-lg-8 col-lg-offset-2 login-content">
                <h4>Create account</h4>
                <form action="/signUp" method="post" class="login-form">
                    <div class="row">
                        <div class="col-lg-8 col-lg-offset-2">
                            <input type="text" name="name" id="name" placeholder="First name" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-8 col-lg-offset-2">
                            <input type="text" name="surname" id="surname" placeholder="Last name" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-8 col-lg-offset-2">
                            <input type="email" name="email" id="email" placeholder="Email" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-8 col-lg-offset-2">
                            <input type="password" name="password" id="password" placeholder="Password" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-8 col-lg-offset-2">
                            <input  class="submit-button" type="submit" value="Sign In">
                        </div>
                    </div>
                </form>
                <div class="sign-up">Already have an account? <a href="/login">Log In</a></div>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp"%>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/resources/js/bootstrap.min.js"></script>
</body>
</html>
