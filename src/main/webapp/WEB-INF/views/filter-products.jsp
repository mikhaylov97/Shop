<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="filter-nav" class="filter-nav container">
    <a href="javascript:void(0)" class="close-button" onclick="closeFilter()"><i class="fa fa-times"></i></a>
    <div id="filter">
        <form id="filter-data">
            <h4>Products filter</h4>
            <h5>Cost</h5>
            <div class="filter-cost">
                <div class="filter-input">
                    <h5>Lower bound</h5>
                    <input type="text" placeholder="Cost from, $" maxlength="6"
                           name="cost-from"
                           onkeydown="checkNumeric(event)"
                           onkeyup="checkNumeric(event)"
                           oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength)">
                </div>
                <div class="filter-input">
                    <h5>Upper bound</h5>
                    <input type="text" placeholder="Cost to, $" maxlength="6"
                           name="cost-to"
                           onkeydown="checkNumeric(event)"
                           onkeyup="checkNumeric(event)"
                           oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength)">
                </div>
            </div>
            <div class="filter-input">
                <h5>Required size</h5>
                <select name="size">
                    <option value="No matter">No matter</option
                    <c:if test="${fn:length(sizes) > 0}">
                        <c:forEach var="size" items="${sizes}">
                            <option value="${size}">${size}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
            <sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')">
                <div class="filter-input">
                    <h5>Product status</h5>
                    <select name="status">
                        <option value="No matter">No matter</option>
                        <option value="hidden">Hidden</option>
                        <option value="visible">Visible</option>
                    </select>
                </div>
            </sec:authorize>
            <c:choose>
                <c:when test="${not empty activeOptionId}">
                    <input type="hidden" name="category" value="${activeOptionId}">
                </c:when>
                <c:when test="${not empty isWomensActive}">
                    <input type="hidden" name="category" value="2">
                </c:when>
                <c:when test="${not empty isMensActive}">
                    <input type="hidden" name="category" value="1">
                </c:when>
            </c:choose>
            <div class="filter-find">
                <button type="button" onclick="filterProducts()">Find</button>
            </div>
        </form>
    </div>
</div>
<div id="open-nav-button" onclick="openFilter()" title="Open filter">
    <i class="fa fa-caret-right fa-5x"></i>
</div>