<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <noscript>
        <style>html{display:none;}</style>
        <meta http-equiv="refresh" content="0.0;url=/javascript/disabled">
    </noscript>
    <title>Black Lion</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <link href="/resources/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="/resources/css/transparent-header.css">
    <link rel="stylesheet" href="/resources/css/jquery-ui.css">
    <script src="/resources/js/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/jquery-ui.min.js"></script>
    <script src="/resources/js/header.js"></script>
    <script src="/resources/js/main.js"></script>
</head>
<body>
<%@include file="header.jsp"%>
<div id="headerwrap">
    <div class="container">
        <div class="row centered">
            <div class="col-lg-8 col-lg-offset-2">
                <h1><a href="#">Desert style</a></h1>
                <h4>Fall 2017</h4>
            </div>
        </div>
    </div>
</div>

<button onclick="scrollToTop()" id="scroll-to-top-button" title="Go to top"><i class="fa fa-chevron-up fa-lg"></i></button>

<div id="tops-and-new">
    <div class="container">
        <div class="row centered">
            <div class="col-lg-5 col-lg-offset-1">
                <div class="link-content">
                    <div class="photo">
                        <img src="/resources/images/new.jpg" alt="New">
                    </div>
                    <div class="overlay">
                        <div class="text">
                            <p>Looking for men's clothes?</p>
                            <p><a href="/catalog/mens">MEN'S</a></p>
                        </div>
                    </div>
                </div>
                <div class="tops-and-new-name">
                    MEN'S
                </div>
            </div>
            <div class="col-lg-5">
                <div class="link-content">
                    <div class="photo">
                        <img src="/resources/images/tops.gif" alt="Tops">
                    </div>
                    <div class="overlay">
                        <div class="text">
                            <p>Looking for women's clothes?</p>
                            <p><a href="/catalog/womens">WOMEN'S</a></p>
                        </div>
                    </div>
                </div>
                <div class="tops-and-new-name">
                    WOMEN'S
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp"%>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/resources/js/bootstrap.min.js"></script>
</body>
</html>

