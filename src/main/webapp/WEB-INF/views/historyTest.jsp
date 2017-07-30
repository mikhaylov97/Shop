<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>History page</title>
    <link rel="stylesheet" href="/resources/css/bootstrap.css">
</head>
<body>
<a href="/home">Go home</a>
<div class="container">
    <c:forEach var="order" items="${orders}">
        <div class="row">
            <div class="col-lg-4">
                <img src="/resources/images/test.png" alt="">
            </div>
            <div class="col-lg-6 col-lg-offset-1">
                <div class="row">${order.orderStatus}</div>
                <div class="row">${order.shippingMethod}</div>
                <div class="row">${order.payment.paymentStatus}</div>
                <div class="row">${order.payment.paymentType}</div>
                <div class="row">${order.payment.totalPrice}</div>
                <select name="products">
                    <c:forEach var="item" items="${order.products}">
                        <option value="${item.name}">${item.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
