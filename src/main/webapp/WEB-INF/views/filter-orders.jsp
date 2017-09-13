<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="filter-nav" class="filter-nav container">
    <a href="javascript:void(0)" class="close-button" onclick="closeFilter()"><i class="fa fa-times"></i></a>
    <div id="filter">
        <form id="filter-data">
            <h4>Orders filter</h4>
            <h5>Cost</h5>
            <div class="filter-cost">
                <div class="filter-input">
                    <h5>Date from</h5>
                    <input name="date-from" type="date">
                </div>
                <div class="filter-input">
                    <h5>Date to</h5>
                    <input name="date-to" type="date">
                </div>
            </div>
            <c:if test="${not empty paymentStatuses}">
                <div class="filter-input">
                    <h5>Payment status</h5>
                    <select name="payment-status">
                        <option value="No matter">No matter</option
                        <c:if test="${fn:length(paymentStatuses) > 0}">
                            <c:forEach var="status" items="${paymentStatuses}">
                                <option value="${status}">${status}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>
            </c:if>
            <c:if test="${not empty orderStatuses}">
                <div class="filter-input">
                    <h5>Payment status</h5>
                    <select name="order-status">
                        <option value="No matter">No matter</option
                        <c:if test="${fn:length(orderStatuses) > 0}">
                            <c:forEach var="status" items="${orderStatuses}">
                                <c:if test="${status ne 'Done'}">
                                    <option value="${status}">${status}</option>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>
            </c:if>
            <div class="filter-find">
                <button type="button" onclick="filterOrders()">Find</button>
            </div>
        </form>
    </div>
</div>
<div id="open-nav-button" onclick="openFilter()" title="Open filter">
    <i class="fa fa-caret-right fa-5x"></i>
</div>