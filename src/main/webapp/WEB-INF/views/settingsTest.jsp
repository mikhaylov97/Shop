<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Settings page</title>
</head>
<body>
<a href="/home">Go home</a>
Hello ${user.name} ${user.surname}! There you can change your settings!
<form action="/user/settings/password" method="post">
    <input type="password" name="old-password" placeholder="Old password" required>
    <input type="password" name="password" placeholder="New password" required>
    <input type="submit" value="Change">
    <c:if test="${not empty msg}">
        ${msg}
    </c:if>
</form>

<form action="/user/settings" method="post">
    <input type="text" name="name" placeholder="New Name" value="${user.name}" required>
    <input type="text" name="surname" placeholder="New surname" value="${user.surname}" required>
    <input type="date" name="birthday" placeholder="Birthday" value="${user.birthday}">
    <input type="text" name="country" placeholder="Country" value="${user.address.country}">
    <input type="text" name="city" placeholder="City" value="${user.address.city}">
    <input type="text" name="street" placeholder="Street" value="${user.address.street}">
    <input type="text" name="house" placeholder="House" value="${user.address.house}">
    <input type="text" name="apartment" placeholder="Apartment" value="${user.address.apartment}">
    <input type="text" name="postcode" placeholder="Postcode" value="${user.address.postcode}">
    <input type="submit" value="Change">
    <c:if test="${not empty msg2}">
        ${msg2}
    </c:if>
</form>
</body>
</html>
