<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <div class="item-name">
                                        <c:choose>
                                            <c:when test="${product.active}">Status: <i class="fa fa-eye"></i> </c:when>
                                            <c:otherwise>Status: <i class="fa fa-eye-slash"></i> </c:otherwise>
                                        </c:choose>
                                    </div>
                                </sec:authorize>
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