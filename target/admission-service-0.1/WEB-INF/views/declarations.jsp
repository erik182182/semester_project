<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:user title="ЕГЭ - Заявления" active="2">
    <c:if test="${empty dirs}">
        <h2 style="text-align: center; font-weight: bold;">Вы еще не подали ни одного заявления.</h2>
    </c:if>
    <c:if test="${not empty dirs}">
        <h2 style="text-align: center; font-weight: bold;">Направления, на которые Вы подали заявления:</h2>
    </c:if>
    <c:forEach items="${dirs}" var="dir">
        <div class="card dirs" >
            <div class="card-header" style="background-color: #56b8ff; border-radius: 15px;">
                <h2 style="text-align: center; color: white;">${dir.university.name}, ${dir.name}, количество бюджетных мест: ${dir.budgetPlaces}</h2>
            </div>
            <div class="card-body">
                <ul class="list-group">
                    <c:forEach items="${dir.declarations}" var="dec" varStatus="loop">
                        <c:if test="${loop.index < dir.budgetPlaces}">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                    ${loop.index+1}. ${dec.user.fullName}
                                <span class="badge badge-primary badge-pill">${dec.sumScore}</span>
                            </li>
                        </c:if>
                        <c:if test="${loop.index >= dir.budgetPlaces}">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                    ${loop.index+1}. ${dec.user.fullName}
                                <span class="badge badge-danger badge-pill">${dec.sumScore}</span>
                            </li>
                        </c:if>

                    </c:forEach>
                </ul>
            </div>
        </div>
    </c:forEach>

    </body>
</t:user>
</html>