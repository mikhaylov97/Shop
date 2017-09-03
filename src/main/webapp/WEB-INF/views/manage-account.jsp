<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="/resources/css/manage-account.css">
    <script src="/resources/js/jquery-3.1.1.min.js"></script>
</head>
<body>
<%@include file="header.jsp"%>
<%@include file="second-header.jsp"%>
<div id="manage-address" class="container">
    <h4 id="title">Manage Account</h4>
    <div class="back-button">
        <a href="/account"><i class="fa fa-long-arrow-left"></i> Back to my account</a>
    </div>
    <div class="row main-context">
        <div class="col-lg-8">
            <form class="account-info" action="/account/settings" method="POST">
                <div class="col-lg-6">
                    <div class="address-title">Manage your address</div>
                    <hr>
                    <!-- <form class="account-info" action="#" method="POST"> -->
                    <div class="Country">
                        <div class="placeholder">
                            Country
                        </div>
                        <div class="input-field">
                            <input type="text" name="country" value="${user.address.country}">
                        </div>
                    </div>
                    <div class="City">
                        <div class="placeholder">
                            City
                        </div>
                        <div class="input-field">
                            <input type="text" name="city" value="${user.address.city}">
                        </div>
                    </div>
                    <div class="Street">
                        <div class="placeholder">
                            Street
                        </div>
                        <div class="input-field">
                            <input type="text" name="street" value="${user.address.street}">
                        </div>
                    </div>
                    <div class="House">
                        <div class="placeholder">
                            House
                        </div>
                        <div class="input-field">
                            <input type="text" name="house" value="${user.address.house}">
                        </div>
                    </div>
                    <div class="Apartment">
                        <div class="placeholder">
                            Apartment
                        </div>
                        <div class="input-field">
                            <input type="text" name="apartment" value="${user.address.apartment}">
                        </div>
                    </div>
                    <div class="Postcode">
                        <div class="placeholder">
                            Postcode
                        </div>
                        <div class="input-field">
                            <input type="text" name="postcode" value="${user.address.postcode}">
                        </div>
                    </div>
                </div>
                <div class="col-lg-6">
                    <div class="address-title">Manage your account</div>
                    <hr>
                    <div class="name">
                        <div class="placeholder">
                            First Name
                        </div>
                        <div class="input-field">
                            <input type="text" name="name" value="${user.name}" required>
                        </div>
                    </div>
                    <div class="surname">
                        <div class="placeholder">
                            Last Name
                        </div>
                        <div class="input-field">
                            <input type="text" name="surname" value="${user.surname}" required>
                        </div>
                    </div>
                    <div class="birthday">
                        <div class="placeholder">
                            Birthday
                        </div>
                        <div class="input-field">
                            <input type="date" name="birthday" value="${user.birthday}">
                        </div>
                    </div>
                    <div class="phone">
                        <div class="placeholder">
                            Phone
                        </div>
                        <div class="input-field">
                            <input type="phone" name="phone" value="${user.phone}">
                        </div>
                    </div>
                </div>
                <div class="col-lg-12 message success-message">
                    <c:if test="${status2 eq 'success'}">
                        ${msg2}
                    </c:if>
                </div>
                <div class="col-lg-12 message error-message">
                    <c:if test="${status2 eq 'error'}">
                        ${msg2}
                    </c:if>
                </div>
                <div class="col-lg-12">
                    <div class="manage-address-button">
                        <button type="submit">Save</button>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-lg-4">
            <form class="account-info" action="/account/settings/password" method="POST">
                <div class="address-title">Manage your password</div>
                <hr>
                <div class="old-password">
                    <div class="placeholder">
                        Old Password
                    </div>
                    <div class="input-field">
                        <input type="password" name="old-password" required>
                    </div>
                </div>
                <div class="new-password">
                    <div class="placeholder">
                        New Password
                    </div>
                    <div class="input-field">
                        <input type="password" name="new-password" required>
                    </div>
                </div>
                <div class="message success-message">
                    <c:if test="${status eq 'success'}">
                        ${msg}
                    </c:if>
                </div>
                <div class="message error-message">
                    <c:if test="${status eq 'error'}">
                        ${msg}
                    </c:if>
                </div>
                <div class="manage-address-button">
                    <button type="submit">Change</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%@include file="footer.jsp"%>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/resourcesjs/bootstrap.min.js"></script>
</body>
</html>
