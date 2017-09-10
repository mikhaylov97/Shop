<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <noscript>
        <style>html{display:none;}</style>
        <meta http-equiv="refresh" content="0.0;url=/javascript/disabled">
    </noscript>
    <title>Catalog page</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <link href="/resources/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="/resources/css/home.css">
    <link rel="stylesheet" href="/resources/css/jquery-ui.css">
    <script src="/resources/js/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/jquery-ui.min.js"></script>
    <script src="/resources/js/main.js"></script>
</head>
<body>
<%@include file="header.jsp"%>
<%@include file="second-header.jsp"%>
<%@include file="options.jsp"%>
<%@include file="filter.jsp"%>
<div class="wrap" onclick="closeFilter()"></div>
<div id="main-content" class="container center-block">
    <div class="row centered">
        <c:choose>
            <c:when test="${fn:length(catalog) > 0}">
                <c:forEach var="product" items="${catalog}">
                    <div class="col-lg-3">
                        <div class="item">
                            <a href="/catalog/${product.id}">
                                <div class="image">
                                    <img src="/image/${product.id}" alt="item">
                                </div>
                                <div class="item-name">
                                        ${product.name}
                                </div>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <div class="item-status">
                                        <c:choose>
                                            <c:when test="${product.active}">Status: <i class="fa fa-eye"></i> </c:when>
                                            <c:otherwise>Status: <i class="fa fa-eye-slash"></i> </c:otherwise>
                                        </c:choose>
                                    </div>
                                </sec:authorize>
                                <div class="item-cost">
                                    <i class="fa fa-usd"></i>${product.price}
                                </div>
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="info-message col-lg-12">Nothing found</div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%@include file="footer.jsp"%><!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/resources/js/bootstrap.min.js"></script>
</body>
</html>

