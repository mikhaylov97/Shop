<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="main-content" class="container center-block">
    <div class="row centered">
        <c:choose>
            <c:when test="${fn:length(catalog) > 0}">
                <c:forEach var="product" items="${catalog}">
                    <div class="col-lg-3">
                        <div class="item">
                            <a href="/catalog/${product.id}">
                                <div class="image">
                                    <img src="/image/${product.id}" alt="item">
                                </div>
                                <div class="item-name">
                                        ${product.name}
                                </div>
                                <div class="item-cost">
                                    <i class="fa fa-usd"></i>${product.price}
                                </div>
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="info-message col-lg-12">Nothing found</div>
            </c:otherwise>
        </c:choose>
    </div>
</div>