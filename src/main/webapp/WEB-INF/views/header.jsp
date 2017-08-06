<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="/home" id="navbar-brand" class="navbar-brand">BLACK LI<img src="/resources/images/logo.png" alt="logo">N</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#"><i class="fa fa-search fa-lg"></i></a></li>
                <sec:authorize access="hasRole('ROLE_USER') or hasRole('ROLE_ANONYMOUS')">
                    <li><a href="/user"><i class="fa fa-user fa-lg"></i></a></li>
                    <li class="navbar-bag">
                        <a href="/bag">
                            <div>
                                <i class="fa fa-shopping-cart fa-lg"></i>
                                <span class="fa-stack">
                             <i class="fa fa-circle fa-stack-2x"></i>
                             <i class="fa-stack-1x bag-size">
                                 <c:choose>
                                     <c:when test="${not empty sessionScope.get('bag')}">
                                         ${fn:length(sessionScope.get('bag'))}
                                     </c:when>
                                     <c:otherwise>
                                         0
                                     </c:otherwise>
                                 </c:choose>
                             </i>
                          </span>
                            </div>
                        </a>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li><a href="/admin/products/add"><i class="fa fa-plus fa-lg"></i></a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')">
                    <li><a href="/logout"><i class="fa fa-sign-out fa-lg"></i></a></li>
                </sec:authorize>
                <li><a href="#"><i class="fa fa-envelope fa-lg"></i></a></li>
            </ul>
        </div>
    </div>
</div>