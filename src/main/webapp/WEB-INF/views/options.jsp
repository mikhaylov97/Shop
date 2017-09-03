<div class="row centered">
    <div class="col-lg-10 col-lg-offset-1 secondary-categories">
        <c:forEach var="ref" items="${options}">
            <c:choose>
                <c:when test="${ref.id eq activeOptionId}">
                    <c:choose>
                        <c:when test="${ref.parent.id eq 1}">
                            <a class="active" href="/catalog/mens/${ref.id}">${ref.name}</a>
                        </c:when>
                        <c:when test="${ref.parent.id eq 2}">
                            <a class="active" href="/catalog/womens/${ref.id}">${ref.name}</a>
                        </c:when>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${ref.parent.id eq 1}">
                            <a href="/catalog/mens/${ref.id}">${ref.name}</a>
                        </c:when>
                        <c:when test="${ref.parent.id eq 2}">
                            <a href="/catalog/womens/${ref.id}">${ref.name}</a>
                        </c:when>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>
</div>