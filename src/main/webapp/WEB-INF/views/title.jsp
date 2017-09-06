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
    <title>Home page</title>
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

<div id="tops-and-new">
    <div class="container">
        <div class="row centered">
            <div class="col-lg-5 col-lg-offset-1">
                <a href="#">
                    <div class="link-content">
                        <div class="photo">
                            <img src="/resources/images/new.jpg" alt="New">
                        </div>
                        <div class="tops-and-new-name">
                            New Arrivals
                        </div>
                    </div>
                </a>
            </div>
            <div class="col-lg-5">
                <a href="#">
                    <div class="link-content">
                        <div class="photo">
                            <img src="/resources/images/tops.gif" alt="Tops">
                        </div>
                        <div class="tops-and-new-name">
                            Tops
                        </div>
                    </div>
                </a>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp"%>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/resources/js/bootstrap.min.js"></script>
</body>
</html>

