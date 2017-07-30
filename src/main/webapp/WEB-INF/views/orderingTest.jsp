<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ordering page</title>
    <link rel="stylesheet" href="/resources/css/bootstrap.css">
</head>
<body>
<a href="/bag">Go to bag</a>
<div class="container">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4">
            Items in your order
        </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-2">
            Total cost: ${totalPrice}
        </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <c:forEach var="item" items="${bag}">
            <div class="col-lg-4">
                <img src="/resources/images/test.png" alt="">
                Price: ${item.price}
                <form  method="post">
                    <input hidden name="source" value="user/ordering">
                    <button formaction="/bag/delete/${item.id}">Delete</button>
                </form>
            </div>
        </c:forEach>
        <div class="col-lg-2 col-lg-offset-5">
            <form action="/user/ordering" method="post">
                <select name="type" required>
                    <c:forEach var="type" items="${paymentTypes}">
                        <option value="${type}">${type}</option>
                    </c:forEach>
                </select>
                <button type="submit">Order</button>
            </form>
        </div>
    </div>

</div>
</body>
</html>
