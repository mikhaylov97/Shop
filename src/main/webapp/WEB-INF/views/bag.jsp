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
    <link rel="stylesheet" href="/resources/css/bag.css">
    <link rel="stylesheet" href="/resources/css/home.css">
    <script src="/resources/js/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/product-page.js"></script>
</head>
<body>
<%@include file="header.jsp"%>
<%@include file="second-header.jsp"%>
<div id="bag-context">
    <h4 id="title">Your Bag</h4>
    <div class="container">
        <div class="row">
            <c:choose>
                <c:when test="${empty bag}">
                    <div class="info-context">
                        <div class="info-message">
                            You have no items in your bag
                        </div>
                        <div class="continue-button">
                            <a href="/"><i class="fa fa-long-arrow-left"></i>Continue Shopping</a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="col-lg-7">
                        <c:forEach var="product" items="${bag}">

                            <div class="item-in-bag col-lg-12">
                                <hr>
                                <form action="/bag/delete/${product.id}" method="post">
                                    <div class="col-lg-5 item-image">
                                        <img src="/image/${product.name}" alt="Item">
                                    </div>
                                    <div class="col-lg-7 item-main-info">
                                        <div class="col-lg-10">
                                            <div class="item-name">
                                                <a href="/catalog/${product.id}">${product.name}</a>
                                            </div>
                                            <div class="item-size">
                                                <select name="sizeId" id="sizeId">
                                                    <option value="${product.sizeId}">${product.size}</option>
                                                </select>
                                            </div>
                                            <div class="item-amount">
                                                <div class="amount-button">
                                                    <button  type="button" class="button-less" id="button-less"><i class="fa fa-minus"></i></button>
                                                    <input name="amount" id="amount-number" class="amount-number" value="${product.amount}">
                                                    <button type="button" id="button-more" class="button-more"><i class="fa fa-plus"></i></button>
                                                </div>
                                            </div>
                                            <div class="item-price">
                                                Price: <i class="fa fa-usd"></i>${product.totalPrice}
                                            </div>
                                        </div>
                                        <div class="col-lg-1 delete-button">
                                            <button type="submit"><i class="fa fa-times"></i></button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="col-lg-5 checkout-context">
                        <div class="bag-info">
                            <div class="bag-total-cost">
                                <h4>Subtotal: <i class="fa fa-usd"></i>${totalPrice}</h4>
                            </div>
                            <div class="row bag-discount">
                                <div class="col-lg-6">
                                    Discount
                                </div>
                                <div class="col-lg-6 result">
                                    TBD
                                </div>
                            </div>
                            <div class="row bag-shipping">
                                <div class="col-lg-6">
                                    Shipping
                                </div>
                                <div class="col-lg-6 result">
                                    TBD
                                </div>
                            </div>
                            <div class="checkout-button">
                                <a href="/user/checkout">Checkout</a>
                            </div>
                            <div class="continue-button">
                                <a href="#">Continue Shopping</a>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
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
