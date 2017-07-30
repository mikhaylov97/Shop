<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new product page</title>
</head>
<body>
<a href="/home">Go home</a>
<form action="/admin/upload" method="post">
    <input type="text" name="name" placeholder="Name" required>
    <input type="text" name="price" placeholder="Price" required>
    <%--<input type="file" name="image" required>--%>
    <select name="category" required>
        <c:forEach var="item" items="${categories}">
            <option value="${item.id}">${item.name}</option>
        </c:forEach>
    </select>
    <input type="text" name="color" placeholder="Color" required>
    <input type="text" name="model" placeholder="Model" required>
    <input type="text" name="sex" placeholder="Sex" required>
    <input type="text" name="size" placeholder="size[39]-cost_dependency[+-100]-number[8]," required>
    <input type="submit" value="Save">
</form>
</body>
</html>
