<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error page</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <link href="/resources/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="/resources/css/javascript-disabled.css">
</head>
<body>
<%@include file="header.jsp"%>
<div id="wrap">
    <div class="container">
        <div class="row centered">
            <div class="col-lg-7 col-lg-offset-1 error-content">
                <h3>Ooops!</h3>
                <h4>It looks like someone forgot to enable javascript.</h4>
                <h5>Unfortunately, our service works only with it.</h5>
                <h6>But don't worry, we'll help you. <a href="http://www.enable-javascript.com" target="_blank">Try here</a></h6>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>

