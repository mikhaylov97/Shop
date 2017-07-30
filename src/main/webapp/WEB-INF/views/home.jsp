<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home page</title>
    <link rel="stylesheet" href="/resources/css/bootstrap.css">
    <script src="/resources/js/jquery-3.1.1.min.js"></script>
    <link rel="stylesheet" href="/resources/css/main.css">
</head>
<body>
<sec:authorize access="hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')">
    <a href="/logout">Logout</a>
    <a href="/user/settings">Edit profile</a>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_ANONYMOUS')">
    <a href="/login">Log in</a> or <a href="/signUp">Sign Up</a>
</sec:authorize>
<a href="/bag">Go to bag</a>
<div class="container">
    <div class="row">
        <c:forEach var="product" items="${products}" varStatus="vs">
            <div class="col-lg-4">
                <div class="product">
                    <img src="/resources/images/test.png" alt="image">
                    <div class="hidden-info">
                        <p>${product.name}</p>
                        <p>${product.price}</p>
                        <a href="/addToBag/${product.id}">В корзину</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script src="/resources/js/bootstrap.min.js"></script>
</body>
</html>
