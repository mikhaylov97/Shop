<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders page</title>
    <link rel="stylesheet" href="/resources/css/bootstrap.css">
</head>
<body>
<a href="/home">Go home</a>
<div class="container">
    <div class="row">
        <div class="col-lg-6 col-lg-offset-3">
            <h4>Заказы</h4>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2">
            <form action="/admin/orders" method="get">
                <select name="orderStatus"  required>
                    <option value="all">All</option>
                    <c:forEach var="status" items="${types}">
                        <c:choose>
                            <c:when test="${status eq currentStatus}">
                                <option value="${status}" selected>${status}</option>
                            </c:when>
                        </c:choose>
                        <c:otherwise>
                            <option value="${status}">${status}</option>
                        </c:otherwise>
                    </c:forEach>
                </select>
                <input type="submit" value="Find">
            </form>
        </div>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="col-lg-6 col-lg-3">
            <c:forEach var="order" items="${orders}">
                ${order.orderStatus}
                <c:if test="${order.orderStatus ne 'DELIVERED'}">
                    <form action="/admin/orders" method="post">
                        <select name="status" required>
                            <c:forEach var="status" items="${types}">
                                <c:choose>
                                    <c:when test="${order.orderStatus eq status}">
                                        <option value="${status}" selected>${status}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${status}">${status}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="id" value="${order.id}">
                        <button type="submit">Set</button>
                    </form>
                </c:if>
                ${order.shippingMethod}
                ${order.payment.paymentStatus}
                ${order.payment.totalPrice}
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>
