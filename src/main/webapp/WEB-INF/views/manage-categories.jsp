<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <noscript>
        <style>html{display:none;}</style>
        <meta http-equiv="refresh" content="0.0;url=/javascript/disabled">
    </noscript>
    <title>Manage categories page</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <link href="/resources/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="/resources/css/home.css">
    <link rel="stylesheet" href="/resources/css/manage-categories.css">
    <link rel="stylesheet" href="/resources/css/jquery-ui.css">
    <script src="/resources/js/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/jquery-ui.min.js"></script>
    <script src="/resources/js/main.js"></script>
    <script src="/resources/js/manage-categories.js"></script>
</head>
<body>
<%@include file="header.jsp"%>
<%@include file="second-header.jsp"%>
<div id="manage-address" class="container">
    <h4 id="title">Manage Categories</h4>
    <div class="back-button">
        <a href="/account"><i class="fa fa-long-arrow-left"></i> Back to my account</a>
    </div>
    <div class="row">
        <div class="col-lg-5 col-lg-offset-1">
            <form class="category-info" action="#" method="POST">
                <div class="title">List of Categories</div>
                <hr>
                <div class="checklist">
                    <div id="mens" class="root-category active">
                        <a href="#">MEN'S</a>
                    </div>
                    <div id="womens" class="root-category">
                        <a href="#">WOMEN'S</a>
                    </div>
                </div>
                <div class="current-categories">
                    <c:forEach var="category" items="${categories}">
                        <div class="category">
                            <div class="block">
                                <div class="info">
                                    <c:choose>
                                        <c:when test="${category.parent.id eq 1}">
                                            Category: <a href="/catalog/mens/${category.id}">${category.name}</a>
                                        </c:when>
                                        <c:when test="${category.parent.id eq 2}">
                                            Category: <a href="/catalog/womens/${category.id}">${category.name}</a>
                                        </c:when>
                                    </c:choose>
                                </div>
                                <div class="info">
                                    <c:choose>
                                        <c:when test="${category.parent.id eq 1}">
                                            Root Category: MEN'S
                                        </c:when>
                                        <c:when test="${category.parent.id eq 2}">
                                            Root Category: WOMEN'S
                                        </c:when>
                                    </c:choose>
                                </div>
                                <div class="info">
                                    Number of products: ${category.numberOfProducts}
                                </div>
                                <div class="info">
                                    <c:choose>
                                        <c:when test="${category.active}">
                                            Status: Active
                                        </c:when>
                                        <c:otherwise>
                                            Status: Hidden
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>

                            <div class="hide-show-button">
                                <c:choose>
                                    <c:when test="${category.active}">
                                        <a href="#" onclick="showHideCategory(event, '/admin/categories/hide/${category.id}')">Hide <i class="fa fa-eye-slash"></i></a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="#" onclick="showHideCategory(event, '/admin/categories/show/${category.id}')">Show <i class="fa fa-eye"></i></a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <%--<div class="category">--%>
                <%--<div class="block">--%>
                <%--<div class="info">--%>
                <%--Category: <a href="">Accessories</a>--%>
                <%--</div>--%>
                <%--<div class="info">--%>
                <%--Root Category: WOMEN'S--%>
                <%--</div>--%>
                <%--<div class="info">--%>
                <%--Number of products: 12--%>
                <%--</div>--%>
                <%--<div class="info">--%>
                <%--Status: Hidden--%>
                <%--</div>--%>
                <%--</div>--%>

                <%--<div class="delete-button">--%>
                <%--<a href="">Show <i class="fa fa-eye"></i></a>--%>
                <%--<a href="">Hide <i class="fa fa-eye-slash"></i></a>--%>
                <%--</div>--%>
                <%--</div>--%>
            </form>
        </div>

        <div class="col-lg-5">
            <form id="new-category-form" class="category-info" action="#" method="POST">
                <div class="title">Add Category</div>
                <hr>
                <div class="field">
                    <div class="placeholder">
                        Root Category
                    </div>
                    <div class="input-field">
                        <select name="parent" id="category-select" required>
                            <option value="1">MEN'S</option>
                            <option value="2">WOMEN'S</option>
                        </select>
                    </div>
                </div>
                <div class="field">
                    <div class="placeholder">
                        Category Name
                    </div>
                    <div class="input-field">
                        <input id="category-name" type="text" name="name" required autocomplete="off">
                    </div>
                </div>
                <div class="message success-message hidden">
                    New category was successfully added
                </div>
                <div class="message error-message hidden">
                    Such category already exists
                </div>
                <div class="manage-address-button">
                    <button type="button" id="add-button">Add</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%@include file="footer.jsp"%>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/resources/js/bootstrap.min.js"></script>
</body>
</html>

