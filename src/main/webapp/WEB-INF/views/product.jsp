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
    <title>Product page</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <link href="/resources/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="/resources/css/product.css">
    <link rel="stylesheet" href="/resources/css/home.css">
    <link rel="stylesheet" href="/resources/css/jquery-ui.css">
    <script src="/resources/js/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/jquery-ui.min.js"></script>
    <script src="/resources/js/product-page.js"></script>
    <script src="/resources/js/main.js"></script>
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
                    <img src="/image/${product.id}" alt="Item">
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
                            <div class="edit-button">
                                <a href="/admin/edit/${product.id}">Edit<i class="fa fa-pencil fa-lg"></i></a>
                            </div>
                            <form method="post">
                                <c:choose>
                                    <c:when test="${product.active}">
                                        <div class="hide-show-button">
                                            This product now is visible for users - <button type="submit" formaction="/admin/hide/${product.id}">Hide<i class="fa fa-eye-slash fa-lg"></i></button>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${product.category.active}">
                                            <div class="hide-show-button">
                                                This product now is hidden for users - <button type="submit" formaction="/admin/show/${product.id}">Show<i class="fa fa-eye fa-lg"></i></button>
                                            </div>
                                        </c:if>
                                        <c:if test="${not product.category.active}">
                                            <div class="hide-show-button">
                                                This product now is hidden for users. Cause: <a href="/admin/categories">${product.category.name}</a> is hidden!
                                            </div>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${product.category.parent.id eq 1}">
                                    <input name="redirect" type="hidden" value="/catalog/mens/${product.category.id}"/>
                                </c:if>
                                <c:if test="${product.category.parent.id eq 2}">
                                    <input name="redirect" type="hidden" value="/catalog/womens/${product.category.id}"/>
                                </c:if>
                            </form>
                        </sec:authorize>
                    </form>
                    <div class="item-description">
                        <pre>${product.attributes.description}</pre>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="suggestions">
    <div class="container">
        <div class="row centered">
            <div class="title col-lg-12">
                <h4>Suggested</h4>
            </div>
            <div class="col-lg-10 col-lg-offset-1">
                <c:forEach var="product" items="${suggestions}">
                    <div class="col-lg-3 item">
                        <a href="/catalog/${product.id}">
                            <div class="image">
                                <img src="/image/${product.id}" alt="Suggested product">
                            </div>
                            <div class="name">
                                ${product.name}
                            </div>
                            <div class="cost">
                                <i class="fa fa-usd"></i>${product.price}
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp"%>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/resources/js/bootstrap.min.js"></script>
</body>
</html>

