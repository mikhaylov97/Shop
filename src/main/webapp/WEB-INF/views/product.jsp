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
    <title>Home page</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <link href="/resources/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="/resources/css/product.css">
    <link rel="stylesheet" href="/resources/css/home.css">
    <script src="/resources/js/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/product-page.js"></script>
</head>
<body>
<%@include file="header.jsp"%>
<%@include file="second-header.jsp"%>
<%@include file="options.jsp"%>
<div id="item-context">
    <div class="container">
        <h4 id="title">Product Page</h4>
        <div class="back-button">
            <c:if test="${product.category.parent.id eq 1}">
                <a href="/catalog/mens/${product.category.id}"><i class="fa fa-long-arrow-left"></i> Continue shopping</a>
            </c:if>
            <c:if test="${product.category.parent.id eq 2}">
                <a href="/catalog/womens/${product.category.id}"><i class="fa fa-long-arrow-left"></i> Continue shopping</a>
            </c:if>
        </div>
        <div class="row">
            <div class="col-lg-5 col-lg-offset-1">
                <div class="image-slider">
                    <img src="/image/${product.name}" alt="Item">
                </div>
            </div>
            <div class="col-lg-5">
                <div class="item-info">
                    <div class="item-name">
                        <h4>${product.name}</h4>
                    </div>
                    <div class="item-price">
                        <i class="fa fa-usd"></i>${product.price}
                    </div>
                    <form action="/bag/add/${product.id}" method="post">
                        <div class="item-size">
                            <h4>Choose your size:</h4>
                            <select name="sizeId" id="sizeId">
                                <c:forEach var="size" items="${product.attributes.sizes}">
                                    <c:choose>
                                        <c:when test="${size.availableNumber eq '0'}">
                                            <option value="${size.id}" disabled>${size.size} Sold out</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${size.id}">${size.size}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="item-amount">
                            <h4>Choose amount:</h4>
                            <div class="amount-button">
                                <button type="button" class="button-less" id="button-less"><i class="fa fa-minus"></i></button><input type="text" id="amount-number" name="amount" class="amount-number" value="1"><button type="button" class="button-more" id="button-more"><i class="fa fa-plus"></i></button>
                            </div>
                        </div>
                        <sec:authorize access="hasRole('ROLE_USER') or hasRole('ROLE_ANONYMOUS')">
                            <div class="bag-button">
                                <button type="submit">Add to <i class="fa fa-shopping-cart fa-lg"></i></button>
                            </div>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <div class="bag-button">
                                <a href="/admin/edit/${product.id}">Edit<i class="fa fa-pencil fa-lg"></i></a>
                            </div>
                        </sec:authorize>
                    </form>
                    <div class="item-description">
                        ${product.attributes.description}
                    </div>
                </div>
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

