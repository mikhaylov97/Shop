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
    <link rel="stylesheet" href="/resources/css/home.css">
    <script src="/resources/js/jquery-3.1.1.min.js"></script>
</head>
<body>
<%@include file="header.jsp"%>
<%@include file="second-header.jsp"%>
<%@include file="options.jsp"%>
<div id="main-content" class="container center-block">
    <div class="row centered">
        <c:forEach var="product" items="${catalog}">
            <div class="col-lg-3">
                <div class="item">
                    <a href="/catalog/${product.id}">
                        <div class="image">
                            <img src="/image/${product.name}" alt="item">
                        </div>
                        <div class="item-name">
                            ${product.name}
                        </div>
                        <div class="item-cost">
                            <i class="fa fa-usd"></i>${product.price}
                        </div>
                    </a>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<%@include file="footer.jsp"%>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/resources/js/bootstrap.min.js"></script>
</body>
</html>

