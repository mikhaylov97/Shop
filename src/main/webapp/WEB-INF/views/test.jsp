<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test</title>
</head>
<body>
<c:forEach var="item" items="${order.products}">
    ${item.amount}
</c:forEach>
</body>
</html>
