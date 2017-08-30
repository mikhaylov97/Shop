<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:choose>
    <c:when test="${fn:length(admins) eq 0}">
        <hr>
        <div class="info-message-history">
            You haven't set any admins yet
        </div>
    </c:when>
    <c:otherwise>
        <c:forEach var="admin" items="${admins}">
            <div class="admin">
                <div class="account-info">
                    <div class="name">
                            ${admin.name} ${admin.surname}
                    </div>
                    <div class="email">
                            ${admin.email}
                    </div>
                    <div class="birthday">
                        <c:choose>
                            <c:when test="${not empty admin.birthday}">
                                ${admin.birthday}
                            </c:when>
                            <c:otherwise>
                                Birthday is not specified
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="phone">
                        <c:choose>
                            <c:when test="${not empty admin.phone}">
                                ${admin.phone}
                            </c:when>
                            <c:otherwise>
                                Phone is not specified
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <c:if test="${not empty admin.address}">
                        <div class="address">
                                ${admin.address.country}, ${admin.address.city} (${admin.address.postcode}),
                                ${admin.address.street} ${admin.address.house}, ${admin.address.apartment}
                        </div>
                    </c:if>
                </div>

                <div class="delete-button">
                    <button type="submit" onclick="deleteAdmin(${admin.id})"><i class="fa fa-times fa-lg"></i></button>
                </div>
            </div>
        </c:forEach>
    </c:otherwise>
</c:choose>
