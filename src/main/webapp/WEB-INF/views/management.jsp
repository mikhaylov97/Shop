<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <noscript>
        <style>html{display:none;}</style>
        <meta http-equiv="refresh" content="0.0;url=/javascript/disabled">
    </noscript>
    <title>Manage orders page</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <link href="/resources/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="/resources/css/home.css">
    <link rel="stylesheet" href="/resources/css/management.css">
    <link rel="stylesheet" href="/resources/css/jquery-ui.css">
    <script src="/resources/js/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/jquery-ui.min.js"></script>
    <script src="/resources/js/main.js"></script>
</head>
<body>
<%@include file="header.jsp"%>
<%@include file="second-header.jsp"%>
<div id="account-overview" class="container">
    <h4 id="greeting">Order Management</h4>
    <div class="back-button">
        <a href="/account"><i class="fa fa-long-arrow-left"></i> Back to my account</a>
    </div>
    <%--<div class="logout-button"><a href="/logout"><i class="fa fa-sign-out"></i>Log Out</a></div>--%>
    <div class="row main-context">
        <div class="col-lg-10 col-lg-offset-1">
            <div class="history-title">Active orders</div>
            <hr>
            <!-- <div class="info-message-history">
              You haven't placed any orders yet.
            </div> -->

            <div class="order col-lg-12">
                <c:forEach var="order" items="${ordersActive}">
                    <form action="/admin/orders/save/${order.id}" method="post">
                        <div class="order-info col-lg-5">
                            <div class="order-date">
                                    ${order.date}
                            </div>
                            <div class="user-name col-lg-12">
                                <a href="/admin/orders/user/${order.user.id}"><i class="fa fa-chevron-circle-right"></i>${order.user.name} ${order.user.surname}</a>
                            </div>
                            <div class="user-email col-lg-12">
                                    ${order.user.email}
                            </div>
                            <div class="order-address">
                                <div class="header col-lg-12">
                                    Shipping address:
                                </div>
                                <div class="content col-lg-12">
                                        ${order.address}
                                </div>
                            </div>
                            <div class="order-payment">
                                <div class="header col-lg-6">
                                    Payment type:
                                </div>
                                <div class="content col-lg-6">
                                        ${order.payment.paymentType}
                                </div>
                            </div>
                            <div class="payment-status">
                                <div class="header col-lg-6">
                                    Payment status:
                                </div>
                                <div class="content col-lg-6">
                                    <select name="payment-status">
                                        <c:forEach var="status" items="${paymentStatuses}">
                                            <c:choose>
                                                <c:when test="${status eq order.payment.paymentStatus}">
                                                    <option value="${status}" selected>${status}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${status}">${status}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="order-status">
                                <div class="header col-lg-6">
                                    Order status:
                                </div>
                                <div class="content col-lg-6">
                                    <select name="order-status">
                                        <c:forEach var="status" items="${orderStatuses}">
                                            <c:if test="${status ne 'Done'}">
                                                <c:choose>
                                                    <c:when test="${status eq order.orderStatus}">
                                                        <option value="${status}" selected>${status}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${status}">${status}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="order-ship-cost">
                                <div class="header col-lg-6">
                                    Shipping cost:
                                </div>
                                <div class="content col-lg-6">
                                    <i class="fa fa-usd"></i>${order.payment.shippingPrice}
                                </div>
                            </div>
                            <div class="order-total-cost">
                                <div class="header col-lg-6">
                                    Total cost:
                                </div>
                                <div class="content col-lg-6">
                                    <i class="fa fa-usd"></i>${order.payment.totalPrice}
                                </div>
                            </div>
                        </div>
                        <div class="order-items col-lg-7">
                            <c:forEach var="product" items="${order.products}">
                                <div class="bag-item col-lg-12">
                                    <div class="col-lg-2 image">
                                        <img src="/image/${product.product.id}" alt="item">
                                    </div>
                                    <div class="col-lg-10 bag-item-info">
                                        <div class="col-lg-6 main-info">
                                            <div class="name">
                                                <a href="/catalog/${product.product.id}">${product.product.name}</a>
                                            </div>
                                            <div class="size">
                                                Size: ${product.size.size}
                                            </div>
                                            <div class="amount">
                                                Amount: ${product.amount}
                                            </div>
                                        </div>
                                        <div class="col-lg-6 cost-info">
                                            <fmt:parseNumber var="price" type="number" value="${product.product.price}"/>
                                            <fmt:parseNumber var="amount" type="number" value="${product.amount}"/>
                                            <i class="fa fa-usd"></i>${price * amount}
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="save-button col-lg-12">
                            <button type="submit">Save changes</button>
                        </div>
                    </form>
                </c:forEach>
            </div>
        </div>
        <div class="col-lg-10 col-lg-offset-1">
            <div class="history-title">Orders History</div>
            <hr>
            <!-- <div class="info-message-history">
              You haven't placed any orders yet.
            </div> -->
            <c:forEach var="order" items="${ordersDone}">
                <div class="order col-lg-12">
                    <div class="order-info col-lg-5">
                        <div class="order-date">
                                ${order.date}
                        </div>
                        <div class="user-name col-lg-12">
                            <a href="/admin/orders/user/${order.user.id}"><i class="fa fa-chevron-circle-right"></i>${order.user.name} ${order.user.surname}</a>
                        </div>
                        <div class="user-email col-lg-12">
                                ${order.user.email}
                        </div>
                        <div class="order-address">
                            <div class="header col-lg-12">
                                Shipping address:
                            </div>
                            <div class="content col-lg-12">
                                    ${order.address}
                            </div>
                        </div>
                        <div class="order-payment">
                            <div class="header col-lg-6">
                                Payment type:
                            </div>
                            <div class="content col-lg-6">
                                    ${order.payment.paymentType}
                            </div>
                        </div>
                        <div class="order-status">
                            <div class="header col-lg-6">
                                Order status:
                            </div>
                            <div class="content col-lg-6">
                                Done
                            </div>
                        </div>
                        <div class="order-ship-cost">
                            <div class="header col-lg-6">
                                Shipping cost:
                            </div>
                            <div class="content col-lg-6">
                                <i class="fa fa-usd"></i>${order.payment.shippingPrice}
                            </div>
                        </div>
                        <div class="order-total-cost">
                            <div class="header col-lg-6">
                                Total cost:
                            </div>
                            <div class="content col-lg-6">
                                <i class="fa fa-usd"></i>${order.payment.totalPrice}
                            </div>
                        </div>
                    </div>
                    <div class="order-items col-lg-7">
                        <c:forEach var="product" items="${order.products}">
                            <div class="bag-item col-lg-12">
                                <div class="col-lg-2 image">
                                    <img src="/image/${product.product.id}" alt="item">
                                </div>
                                <div class="col-lg-10 bag-item-info">
                                    <div class="col-lg-6 main-info">
                                        <div class="name">
                                            <a href="/catalog/${product.product.id}">${product.product.name}</a>
                                        </div>
                                        <div class="size">
                                            Size: ${product.size.size}
                                        </div>
                                        <div class="amount">
                                            Amount: ${product.amount}
                                        </div>
                                    </div>
                                    <div class="col-lg-6 cost-info">
                                        <fmt:parseNumber var="price" type="number" value="${product.product.price}"/>
                                        <fmt:parseNumber var="amount" type="number" value="${product.amount}"/>
                                        <i class="fa fa-usd"></i>${price * amount}
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<%@include file="footer.jsp"%>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/resources/js/bootstrap.min.js"></script>
</body>
</html>

