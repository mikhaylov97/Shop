<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <noscript>
        <style>html{display:none;}</style>
        <meta http-equiv="refresh" content="0.0;url=/javascript/disabled">
    </noscript>
    <title>Statistics</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <link href="/resources/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="/resources/css/home.css">
    <link rel="stylesheet" href="/resources/css/statistics.css">
    <link rel="stylesheet" href="/resources/css/jquery-ui.css">
    <script src="/resources/js/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/jquery-ui.min.js"></script>
    <script src="/resources/js/main.js"></script>
</head>
<body>
<%@include file="header.jsp"%>
<%@include file="second-header.jsp"%>
<div id="statistics" class="container">
    <h4 id="title">Shop Statistics</h4>
    <div class="back-button">
        <a href="/account"><i class="fa fa-long-arrow-left"></i> Back to my account</a>
    </div>
    <div class="row">
        <div class="col-lg-4 block">
            <div class="title">Top 10 Products</div>
            <c:forEach var="product" items="${topProducts}" varStatus="status">
                <div class="statistic-item col-lg-12">
                    <div class="top-number">#${status.index + 1}</div>
                    <div class="product col-lg-12">
                        <div class="col-lg-2 image">
                            <img src="/image/${product.id}" alt="item">
                        </div>
                        <div class="col-lg-10 product-info">
                            <div class="col-lg-8 main-info">
                                <div class="name">
                                    <a href="/catalog/${product.id}">${product.name}</a>
                                </div>
                                <div class="sales">
                                    Sales: ${product.numberOfSales}
                                </div>
                            </div>
                            <div class="col-lg-4 cost-info">
                                <i class="fa fa-usd"></i>${product.price}
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>


        <div class="col-lg-4 block">
            <div class="title">Top 10 Users</div>
            <c:forEach var="user" items="${topUsers}" varStatus="status">
                <div class="statistic-item col-lg-12">
                    <div class="top-number">#${status.index + 1}</div>
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
                    <div class="account-footer col-lg-12">
                        <div class="total-cash col-lg-7">
                            Total cash: <i class="fa fa-usd"></i>${user.totalCash}
                        </div>
                        <div class="reference col-lg-5">
                            <a href="#">Go to <i class="fa fa-user"></i></a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="col-lg-4 block">
            <div class="title">Income Statistics</div>
            <div class="statistic-item col-lg-12">
                <div class="income income-for-week col-lg-12">
                    <div class="header col-lg-8">
                        For the last week:
                    </div>
                    <div class="amount col-lg-4">
                        <i class="fa fa-usd"></i>${incomePerWeek}
                    </div>
                </div>
                <div class="income income-for-month col-lg-12">
                    <div class="header col-lg-8">
                        For the last month:
                    </div>
                    <div class="amount col-lg-4">
                        <i class="fa fa-usd"></i>${incomePerMonth}
                    </div>
                </div>
                <div class="documents col-lg-12">
                    <div class="pdf">
                        <a href="/admin/statistics/download/pdf">Download PDF</a>
                    </div>
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

