<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bag page</title>
    <link rel="stylesheet" href="/resources/css/bootstrap.css">
    <script src="/resources/js/bootstrap.min.js"></script>
</head>
<body>
<a href="/home">Go back</a>
<div class="container">
    <div class="row">
        <c:forEach var="product" items="${bag}">
            <div class="col-lg-4">
                ${product.name} ${product.price}
                    <img src="/resources/images/test.png" alt="">
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
