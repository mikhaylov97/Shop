<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <noscript>
        <style>html{display:none;}</style>
        <meta http-equiv="refresh" content="0.0;url=/javascript/disabled">
    </noscript>
    <title>Checkout page</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <link href="/resources/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="/resources/css/checkout.css">
    <link rel="stylesheet" href="/resources/css/home.css">
    <link rel="stylesheet" href="/resources/css/jquery-ui.css">
    <script src="/resources/js/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/jquery-ui.min.js"></script>
    <script src="/resources/js/checkout-page.js"></script>
    <script src="/resources/js/main.js"></script>
</head>
<body>
<%@include file="header.jsp"%>
<%@include file="second-header.jsp"%>
<div id="bag-context">
    <h4 id="title">Checkout</h4>
    <div class="container">
        <div class="row">
            <c:choose>
            <c:when test="${not empty successMsg}">
                <div class="info-context">
                    <div class="info-message">
                        ${successMsg}
                    </div>
                    <div class="continue-button">
                        <a href="/catalog/mens"><i class="fa fa-long-arrow-left"></i>Continue Shopping</a>
                    </div>
                </div>
            </c:when>
            <c:when test="${empty sessionScope.get('bag')}">
                <div class="info-context">
                    <div class="info-message">
                        You have no items in your bag
                    </div>
                    <div class="continue-button">
                        <a href="/home"><i class="fa fa-long-arrow-left"></i>Continue Shopping</a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
            <div class="col-lg-7">
                <div class="payment">
                    <hr>
                    <div class="col-lg-12 payment-main-info">
                        <div class="payment-by-cash col-lg-12">
                            <form action="/user/checkout/cash" method="post">
                                <div class="col-lg-12">
                                    <div class="payment-type">
                                        <a href="#" class="active cash">Cash</a>
                                        <a href="#" class="card">Card</a>
                                    </div>
                                    <div class="shipping-address col-lg-12">
                                        <h4>Shipping Address</h4>
                                        <%--<div class="name-and-lastname">--%>
                                            <%--<input type="text" name="name" placeholder="First Name" value="${user.name}" required>--%>
                                            <%--<input type="text" name="surname" placeholder="Last Name" value="${user.surname}" required>--%>
                                        <%--</div>--%>
                                        <div class="country-and-code">
                                            <input type="text" name="country" placeholder="Country" value="${user.address.country}" required>
                                            <input type="text" name="postcode" placeholder="Postcode" value="${user.address.postcode}" required>
                                        </div>
                                        <div class="city-and-house">
                                            <input type="text" name="city" placeholder="City" value="${user.address.city}" required>
                                            <input type="text" name="house" placeholder="House" value="${user.address.house}" required>
                                        </div>
                                        <div class="street-and-apartment">
                                            <input type="text" name="street" placeholder="Street" value="${user.address.street}" required>
                                            <input type="text" name="apartment" placeholder="Apartment" value="${user.address.apartment}" required>
                                        </div>
                                        <div class="phone">
                                            <input type="text" name="phone" placeholder="Phone" value="${user.phone}" required>
                                        </div>
                                    </div>
                                    <div class="shipping-method col-lg-12">
                                        <h4>Shipping Method</h4>
                                        <div class="method">
                                            <label for="method-1">
                                                <input type="radio" id="method-1" name="shipping-method" class="radio cash-radio" value="22" required checked>
                                                <span class="radio-custom"></span>
                                                <div class="method-name col-lg-10">First-Class Package International Service</div>
                                                <div class="method-price col-lg-2"><i class="fa fa-usd"></i><div id="price1">22</div></div>
                                            </label>
                                        </div>
                                        <div class="method">
                                            <label for="method-2">
                                                <input type="radio" id="method-2" name="shipping-method" class="radio cash-radio" value="53" required>
                                                <span class="radio-custom"></span>
                                                <div class="method-name col-lg-10">Priority Mail International</div>
                                                <div class="method-price col-lg-2"><i class="fa fa-usd"></i><div id="price2">53</div></div>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="pay-button col-lg-12">
                                        <button type="submit">Pay</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <!--  -->
                        <div class="payment-by-card col-lg-12 hidden">
                            <form action="/user/checkout/card" method="post">
                                <div class="col-lg-12">
                                    <div class="payment-type">
                                        <a href="#" class="active cash">Cash</a>
                                        <a href="#" class="card">Card</a>
                                    </div>
                                    <div class="shipping-address col-lg-12">
                                        <h4>Shipping Address</h4>
                                        <%--<div class="name-and-lastname">--%>
                                            <%--<input type="text" name="name" placeholder="First Name" value="${user.name}" required>--%>
                                            <%--<input type="text" name="surname" placeholder="Last Name" value="${user.surname}" required>--%>
                                        <%--</div>--%>
                                        <div class="country-and-code">
                                            <input type="text" name="country" placeholder="Country" value="${user.address.country}" required>
                                            <input type="text" name="postcode" placeholder="Postcode" value="${user.address.postcode}" required>
                                        </div>
                                        <div class="city-and-house">
                                            <input type="text" name="city" placeholder="City" value="${user.address.city}" required>
                                            <input type="text" name="house" placeholder="House" value="${user.address.house}" required>
                                        </div>
                                        <div class="street-and-apartment">
                                            <input type="text" name="street" placeholder="Street" value="${user.address.street}" required>
                                            <input type="text" name="apartment" placeholder="Apartment" value="${user.address.apartment}" required>
                                        </div>
                                        <div class="phone">
                                            <input type="text" name="phone" placeholder="Phone" value="${user.phone}" required>
                                        </div>
                                    </div>
                                    <div class="shipping-method col-lg-12">
                                        <h4>Shipping Method</h4>
                                        <div class="method">
                                            <label for="method-3">
                                                <input type="radio" id="method-3" name="shipping-method" class="radio card-radio" value="22" required checked>
                                                <span class="radio-custom"></span>
                                                <div class="method-name col-lg-10">First-Class Package International Service</div>
                                                <div class="method-price col-lg-2"><i class="fa fa-usd"></i><div id="price3">22</div></div>
                                            </label>
                                        </div>
                                        <div class="method">
                                            <label for="method-4">
                                                <input type="radio" id="method-4" name="shipping-method" class="radio card-radio" value="53" required>
                                                <span class="radio-custom"></span>
                                                <div class="method-name col-lg-10">Priority Mail International</div>
                                                <div class="method-price col-lg-2"><i class="fa fa-usd"></i><div id="price4">53</div></div>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="card-info col-lg-12">
                                        <h4>Card information</h4>
                                        <div class="card-prototype col-lg-8 col-lg-offset-2">
                                            <h4>Master Card</h4>
                                            <div class="user-name col-lg-6">
                                                <input type="text" name="user-full-name" placeholder="First Name and Last Name" required>
                                            </div>
                                            <div class="card-date col-lg-3">
                                                <input type="text" name="month-year" placeholder="MM/YY" required>
                                            </div>
                                            <div class="card-cvv col-lg-3">
                                                <input type="text" name="cvv" placeholder="CVV" required>
                                            </div>
                                            <div class="card-number col-lg-12">
                                                <input type="text" name="card-number" placeholder="Card number" required>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="pay-button col-lg-12">
                                        <button type="submit">Pay</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-5 bag-context">
                <div class="row bag-items col-lg-12">
                    <c:forEach var="product" items="${sessionScope.get('bag')}">
                        <div class="bag-item col-lg-12">
                            <div class="col-lg-2 image">
                                <img src="/image/${product.id}" alt="item">
                            </div>
                            <div class="col-lg-10 bag-item-info">
                                <div class="col-lg-8 main-info">
                                    <div class="name">
                                        <a href="/catalog/${product.id}">${product.name}</a>
                                    </div>
                                    <div class="size">
                                        Size: ${product.size}
                                    </div>
                                    <div class="amount">
                                        Amount: ${product.amount}
                                    </div>
                                </div>
                                <div class="col-lg-4 cost-info">
                                    <i class="fa fa-usd"></i>${product.totalPrice}
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <div class="discount-code col-lg-12">
                        <input type="text" placeholder="Discount code">
                    </div>
                    <div class="bag-info col-lg-12">
                        <div class="row bag-subtotal">
                            <div class="col-lg-6">
                                Subtotal
                            </div>
                            <div class="col-lg-6 result">
                                <i class="fa fa-usd"></i><div class="bag-total-price">${bagTotalPrice}</div>
                            </div>
                        </div>
                        <div class="row bag-discount">
                            <div class="col-lg-6">
                                Discount
                            </div>
                            <div class="col-lg-6 result">
                                None
                            </div>
                        </div>
                        <div class="row bag-shipping">
                            <div class="col-lg-6">
                                Shipping
                            </div>
                            <div class="col-lg-6 result">
                                <i class="fa fa-usd"></i><div class="shipping-price">27</div>
                            </div>
                        </div>
                        <div class="row bag-total">
                            <div class="col-lg-6">
                                Total
                            </div>
                            <div class="col-lg-6 result">
                                <i class="fa fa-usd"></i><div class="total-price">105</div>
                            </div>
                        </div>
                        <div class="continue-button">
                            <a href="/catalog/mens">Continue Shopping</a>
                        </div>
                    </div>
                    <hr>
                </div>
            </div>
        </div>
    </div>
</div>
</c:otherwise>
</c:choose>
<%@include file="footer.jsp"%>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/resources/js/bootstrap.min.js"></script>
</body>
</html>

