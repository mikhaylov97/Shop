<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <noscript>
        <style>html{display:none;}</style>
        <meta http-equiv="refresh" content="0.0;url=/javascript/disabled">
    </noscript>
    <title>Home page</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <link href="/resources/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="/resources/css/home.css">
    <link rel="stylesheet" href="/resources/css/manage-admins.css">
    <link rel="stylesheet" href="/resources/css/jquery-ui.css">
    <script src="/resources/js/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/jquery-ui.min.js"></script>
    <script src="/resources/js/manage-admins.js"></script>
    <script src="/resources/js/main.js"></script>
</head>
<body>
<%@include file="header.jsp"%>
<%@include file="second-header.jsp"%>
<div id="manage-address" class="container">
    <h4 id="title">Manage Admins</h4>
    <div class="back-button">
        <a href="/account"><i class="fa fa-long-arrow-left"></i> Back to my account</a>
    </div>
    <div class="row">
        <div class="col-lg-5 col-lg-offset-1 admins">
            <div class="address-title">List of Admins</div>
            <%@include file="admins-list.jsp"%>
        </div>

        <div class="col-lg-5">
            <form class="account-info" action="/admin/super/management/add" method="POST">
                <div class="address-title">Add Admin</div>
                <hr>
                <div class="name">
                    <div class="placeholder">
                        First Name
                    </div>
                    <div class="input-field">
                        <input type="text" name="name"  autocomplete="off" <c:if test="${not empty name}">value="${name}"</c:if> required>
                    </div>
                </div>
                <div class="surname">
                    <div class="placeholder">
                        Last Name
                    </div>
                    <div class="input-field">
                        <input type="text" name="surname"  autocomplete="off" <c:if test="${not empty surname}">value="${surname}"</c:if> required>
                    </div>
                </div>
                <div class="email">
                    <div class="placeholder">
                        Email
                    </div>
                    <div class="input-field">
                        <input type="email" name="email"  autocomplete="off" <c:if test="${not empty email}">value="${email}"</c:if> required>
                    </div>
                </div>
                <div class="password">
                    <div class="placeholder">
                        Password
                    </div>
                    <div class="input-field">
                        <input type="password" name="password" required>
                    </div>
                </div>
                <div class="message success-message hidden">
                </div>
                <div class="message error-message hidden">
                </div>
                <div class="manage-address-button">
                    <button type="button" onclick="addNewAdmin()">Add</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%@include file="footer.jsp"%>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/resources/js/bootstrap.min.js"></script>
</body>
</html>

