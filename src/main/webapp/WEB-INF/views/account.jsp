<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home page</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <link href="/resources/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="/resources/css/home.css">
    <link rel="stylesheet" href="/resources/css/settings.css">
    <script src="/resources/js/jquery-3.1.1.min.js"></script>
</head>
<body>
<%@include file="header.jsp"%>
<%@include file="second-header.jsp"%>
<div id="account-overview" class="container">
    <h4 id="greeting">Welcome, ${user.name}</h4>
    <h5 id="title">Account Overview</h5>
    <div class="logout-button"><a href="/logout"><i class="fa fa-sign-out"></i>Log Out</a></div>
    <div class="row main-context">
        <div class="col-lg-4">
            <div class="address-title">Default Address</div>
            <hr>
            <div class="account-info">
                <div class="name">
                    ${user.name} ${user.surname}
                </div>
                <div class="email">
                    ${user.email}
                </div>
                <div class="birthday">
                    ${user.birthday}
                </div>
                <div class="phone">
                    ${user.phone}
                </div>
            </div>
            <c:choose>
                <c:when test="${user.address == null}">
                    <div class="info-message-account">
                        You have not saved a default address
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="address">
                        ${user.address.country}, ${user.address.city} (${user.address.postcode}),
                                ${user.address.street} ${user.address.house}, ${user.address.apartment}
                    </div>
                </c:otherwise>
            </c:choose>
            <div class="manage-address-button">
                <a href="/user/settings"><i class="fa fa-cog"></i>Manage Account</a>
            </div>
        </div>
        <div class="col-lg-8">
            <div class="history-title">Order History</div>
            <hr>
            <div class="info-message-history">
                You haven't placed any orders yet.
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
