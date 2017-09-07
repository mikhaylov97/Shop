<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="filter" class="container">
    <div class="row">
        <div class="col-lg-10 col-lg-offset-1 filter">
            <div class="filter-title">
                Filter products:
            </div>
            <form id="filter-data" method="post">
                <div class="filter-cost">
                    <input type="number" maxlength="5"
                           placeholder="Cost to"
                           name="cost"
                           onkeydown="checkNumeric(event)"
                           onkeyup="checkNumeric(event)"
                           oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength)">
                </div>
                <div class="filter-size">
                    <select name="size" id="size">
                        <option value="No matter" selected>No matter</option>
                        <c:forEach var="size" items="${sizes}">
                            <option value="${size}">${size}</option>
                        </c:forEach>
                        <%--<option value="Small">Small</option>--%>
                        <%--<option value="Medium">Medium</option>--%>
                        <%--<option value="Large">Large</option>--%>
                        <%--<option value="XLarge">XLarge</option>--%>
                    </select>
                </div>
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
                <%--<input type="hidden" value="${activeOptionId}">--%>
            </form>
            <div class="filter-find">
                <button type="button" onclick="filterProducts()">Find</button>
            </div>
        </div>
    </div>
</div>
