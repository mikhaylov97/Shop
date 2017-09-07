<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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