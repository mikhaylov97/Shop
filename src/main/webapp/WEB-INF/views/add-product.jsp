<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home page</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <link href="/resources/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/css/main.css">
    <link rel="stylesheet" href="/resources/css/add-product.css">
    <link rel="stylesheet" href="/resources/css/home.css">
    <script src="/resources/js/jquery-3.1.1.min.js"></script>
    <script src="/resources/js/add-product.js"></script>
</head>
<body>
<%@include file="header.jsp"%>
<%@include file="second-header.jsp"%>
<div id="item-context">
    <div class="container">
        <div class="row">
            <form:form action="/admin/products/add/upload" method="post" enctype="multipart/form-data">
                <div class="col-lg-5 col-lg-offset-1">
                    <div class="image-slider">
                        <img id="upload-image" src="/resources/images/item2.jpg" alt="Item">
                    </div>
                    <div class="file-upload">
                        <input type="file" onchange="readURL(this);" accept="image/jpeg, image/png, image/gif" name="image">
                    </div>
                    <c:if test="${not empty errorMessage}">
                        <div class="error-message">${errorMessage}</div>
                    </c:if>
                </div>
                <div class="col-lg-5">
                    <div class="item-info">
                        <div class="item-name">
                            <!-- <h4>Ben-Day Tee</h4> -->
                            <c:choose>
                                <c:when test="${not empty oldName}">
                                    <input type="text" name="name" placeholder="Name" value="${oldName}" required>
                                </c:when>
                                <c:otherwise>
                                    <input type="text" name="name" placeholder="Name" required>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="item-price">
                            <c:choose>
                                <c:when test="${not empty oldPrice}">
                                    <i class="fa fa-usd"></i><input type="text" name="price" placeholder="Price" value="${oldPrice}" required>
                                </c:when>
                                <c:otherwise>
                                    <i class="fa fa-usd"></i><input type="text" name="price" placeholder="Price" required>
                                </c:otherwise>
                            </c:choose>
                            <%--<i class="fa fa-usd"></i><input type="text" name="price" placeholder="Price" required>--%>
                        </div>
                        <div class="item-size">
                            <h4>Add some sizes:</h4>
                            <c:if test="${not empty oldSizes}">
                                <c:forEach var="size" items="${oldSizes}" varStatus="status">
                                    <div class="col-lg-12 size-line">
                                        <input type="text" name="sizes[${status.count-1}].size" value="${size.size}" placeholder="Size" required/>
                                        <input type="text" name="sizes[${status.count-1}].availableNumber" value="${size.availableNumber}" placeholder="Amount" required/>
                                        <a href="#" class="remove-field"><i class="fa fa-times"></i></a>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                        <div class="add-size-button">
                            <button id="add-size-button" type="button"><i class="fa fa-plus"></i></button>
                        </div>
                        <div class="item-category">
                            <h4>Choose the category:</h4>
                            <select name="category">
                                <c:forEach var="category" items="${options}">
                                    <c:choose>
                                        <c:when test="${category.id eq oldCategory}">
                                            <option value="${category.id}" selected>${category.parent.name}/${category.name}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${category.id}">${category.parent.name}/${category.name}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="item-description">
                            <h4>Add some description:</h4>
                            <c:choose>
                                <c:when test="${not empty oldDescription}">
                                    <textarea name="description" cols="50" rows="7" placeholder="Description" required>${oldDescription}</textarea>
                                </c:when>
                                <c:otherwise>
                                    <textarea name="description" cols="50" rows="7" placeholder="Description" required></textarea>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="col-lg-12 button-save">
                    <button type="input">Save</button>
                </div>
            </form:form>
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
