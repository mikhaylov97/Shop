<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                <a href="/account/settings"><i class="fa fa-cog"></i>Manage Account</a>
            </div>
        </div>
        <sec:authorize access="hasRole('ROLE_USER')">
            <div class="col-lg-8">
                <div class="history-title">Order History</div>
                <hr>
                <c:choose>
                    <c:when test="${empty orders}">
                        <div class="info-message-history">
                            You haven't placed any orders yet.
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="order" items="${orders}">
                            <div class="order col-lg-12">
                                <div class="order-info col-lg-6">
                                    <div class="order-date">
                                            ${order.date}
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
                                        <div class="header col-lg-4">
                                            Order status:
                                        </div>
                                        <div class="content col-lg-8">
                                                ${order.orderStatus}
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
                                <div class="order-items col-lg-6">
                                    <c:forEach var="product" items="${order.products}">
                                        <div class="bag-item col-lg-12">
                                            <div class="col-lg-2 image">
                                                <img src="/image/${product.product.name}" alt="item">
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
                    </c:otherwise>
                </c:choose>
            </div>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <div class="col-lg-8">
                <div class="history-title">Admin Actions</div>
                <hr>
                <div class="actions">
                    <div class="col-lg-12 action">
                        <!-- <a href=""><i class="fa fa-list-alt"></i> Manage Categories</a> -->
                        <a href=""><i class="fa fa-share-alt fa-lg"></i> Manage Categories</a>
                    </div>
                    <div class="col-lg-12 action">
                        <a href=""><i class="fa fa-check-square-o fa-lg"></i> Manage Orders</a>
                        <!-- <a href=""><i class="fa fa-edit"></i> Manage Orders</a> -->
                    </div>
                    <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
                        <div class="col-lg-12 action">
                            <a href="/admin/super/management"><i class="fa fa-group"></i> Manage Admins</a>
                        </div>
                    </sec:authorize>
                    <div class="col-lg-12 action">
                        <!-- <a href=""><i class="fa fa-plus-circle fa-lg"></i> Add Product</a> -->
                        <a href=""><i class="fa fa-plus fa-lg"></i> Add Product</a>
                    </div>
                    <div class="col-lg-12 action">
                        <!-- <a href=""><i class="fa fa-calendar"></i> Statistics</a> -->
                        <a href=""><i class="fa fa-bar-chart"></i> Statistics</a>
                    </div>
                </div>
            </div>
        </sec:authorize>
    </div>
</div>
<%@include file="footer.jsp"%>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/resources/js/bootstrap.min.js"></script>
</body>
</html>
