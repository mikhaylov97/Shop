<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="col-lg-10 col-lg-offset-1">
    <div class="history-title">Orders History</div>
    <hr>
    <c:if test="${fn:length(orders) == 0}">
        <div class="info-message-history">
            You haven't any archived orders yet.
        </div>
    </c:if>
    <c:forEach var="order" items="${orders}">
        <div class="order col-lg-12">
            <div class="order-info col-lg-5">
                <div class="order-date">
                        ${order.date}
                </div>
                <div class="user-name col-lg-12">
                    <a href="/admin/orders/user/${order.user.id}"><i class="fa fa-chevron-circle-right"></i>${order.user.name} ${order.user.surname}</a>
                </div>
                <div class="user-email col-lg-12">
                        ${order.user.email}
                </div>
                <div class="order-address">
                    <div class="header col-lg-12">
                        Shipping address:
                    </div>
                    <div class="content col-lg-12">
                            ${order.address}
                    </div>
                </div>
                <div class="order-payment">
                    <div class="header col-lg-6">
                        Payment type:
                    </div>
                    <div class="content col-lg-6">
                            ${order.payment.paymentType}
                    </div>
                </div>
                <div class="order-status">
                    <div class="header col-lg-6">
                        Order status:
                    </div>
                    <div class="content col-lg-6">
                        Done
                    </div>
                </div>
                <div class="order-ship-cost">
                    <div class="header col-lg-6">
                        Shipping cost:
                    </div>
                    <div class="content col-lg-6">
                        <i class="fa fa-usd"></i>${order.payment.shippingPrice}
                    </div>
                </div>
                <div class="order-total-cost">
                    <div class="header col-lg-6">
                        Total cost:
                    </div>
                    <div class="content col-lg-6">
                        <i class="fa fa-usd"></i>${order.payment.totalPrice}
                    </div>
                </div>
            </div>
            <div class="order-items col-lg-7">
                <c:forEach var="product" items="${order.products}">
                    <div class="bag-item col-lg-12">
                        <div class="col-lg-2 image">
                            <img src="/image/${product.product.id}" alt="item">
                        </div>
                        <div class="col-lg-10 bag-item-info">
                            <div class="col-lg-6 main-info">
                                <div class="name">
                                    <a href="/catalog/${product.product.id}">${product.product.name}</a>
                                </div>
                                <div class="size">
                                    Size: ${product.size.size}
                                </div>
                                <div class="amount">
                                    Amount: ${product.amount}
                                </div>
                            </div>
                            <div class="col-lg-6 cost-info">
                                <fmt:parseNumber var="price" type="number" value="${product.product.price}"/>
                                <fmt:parseNumber var="amount" type="number" value="${product.amount}"/>
                                <i class="fa fa-usd"></i>${price * amount}
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:forEach>
</div>
