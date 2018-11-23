<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:user title="ЕГЭ - Главная" active="1">
    <c:if test="${not empty name}">
        <c:if test="${not empty directions}">
            <h2 style="width: 65%; font-weight: bold; margin-bottom: 0px; ">Список всех направлений, на которые Вы можете пройти:</h2>
        </c:if>
        <c:if test="${empty directions}">
            <h2 style="width: 65%; font-weight: bold; margin-bottom: 0px; ">К сожалению, вы не проходите ни на одно направление.</h2>
        </c:if>
    </c:if>
    <c:if test="${empty name}">
        <h2 style="width: 65%; font-weight: bold; margin-bottom: 0px;">Список всех направлений:</h2>
    </c:if>


    <c:forEach items="${directions}" var="dir">
        <div class="container direction">
            <h3><span class="info">Университет: </span>${dir.university.name}</h3>
            <h3><span class="info">Направление обучения: </span >${dir.name}</h3>
            <hr>
            <div class="row" style="padding-top:2%; padding-bottom: 2%;">
                <div class="col-3" style="padding: 10px;">
                    <div class="nav flex-column nav-pills"  role="tablist" aria-orientation="vertical">
                        <a class="nav-link active"  data-toggle="pill" href="#v-pills-home${dir.id}" role="tab" aria-controls="v-pills-home${dir.id}" aria-selected="true">Об университете</a>
                        <a class="nav-link" data-toggle="pill" href="#v-pills-profile${dir.id}" role="tab" aria-controls="v-pills-profile${dir.id}" aria-selected="false">О направлении</a>
                        <a class="nav-link"  data-toggle="pill" href="#v-pills-messages${dir.id}" role="tab" aria-controls="v-pills-messages${dir.id}" aria-selected="false">Проходные баллы</a>

                    </div>
                </div>
                <div class="col-9">
                    <div class="tab-content">
                        <div class="tab-pane fade show active" id="v-pills-home${dir.id}" role="tabpanel" >
                            <div>
                                    ${dir.university.info}
                                <p><span class="info">Город: </span>${dir.university.city}</p>

                            </div>
                        </div>
                        <div class="tab-pane fade" id="v-pills-profile${dir.id}" role="tabpanel" ><div>
                                ${dir.info}
                            <p><span class="info">Количество бюджетных мест: </span>${dir.budgetPlaces}</p>
                        </div>
                        </div>
                        <div class="tab-pane fade" id="v-pills-messages${dir.id}" role="tabpanel">
                            <div>
                                <ul class="list-group">
                                    <c:forEach items="${dir.examsWithMinScore}" var="exams">
                                        <li class="list-group-item d-flex justify-content-between align-items-center">
                                                ${exams.subject}
                                            <span class="badge badge-primary badge-pill">${exams.score}</span>
                                        </li>
                                    </c:forEach>
                                </ul>


                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <c:if test="${not empty name}">
                <button type="button" value="${dir.university.name}, ${dir.name}" class="btn btn-success" style="margin-left: 40%; margin-bottom: 3%;" onclick="apply(${dir.id}, value);">Подать заявление</button>
            </c:if>
        </div>

    </c:forEach>

    <c:if test="${not empty directions}">
        <c:if test="${not empty name}">
            <div class="card" style="width: 18rem;" id="dir-info">
                <div class="card-header" style="background-color: aliceblue;">
                    <h2 style="color: #ff5347;">Важная информация</h2>
                </div>
                <div class="card-body" >
                    <p class="card-text"><h3><span style="color: #56b8ff;">&bull;</span> Помните, что поданное заявление <u style="color: #ff5347;">невозможно отменить.</u></h3></p>
                    <p class="card-text"><h3><span style="color: #56b8ff;">&bull;</span> Вы можете подать максимум 3 заявления.</h3></p>
                    <p class="card-text"><h3><span style="color: #56b8ff;">&bull;</span> Вы уже подали <span id="decs-size" style="color: #2673cc;">${size}</span> из  3 заявлений.</h3></p>


                    <ul class="list-group list-group-flush" id="decs">
                        <c:forEach var="dec" items="${declarations}">
                            <li class="list-group-item" style="color: #2673cc;">${dec.direction.university.name}, ${dec.direction.name}</li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </c:if>
        <c:if test="${empty name}">
            <div class="card" style="width: 18rem;" id="dir-info">
                <div class="card-body" >
                    <c:if test="${empty name}">
                        <p>Вы можете войти на сайт, чтобы посмотреть индивидуальный список направлений
                            для Вас и подать на них заявление.</p>
                    </c:if>
                </div>
            </div>
        </c:if>
    </c:if>

    <c:if test="${not empty exams}">
        <div class="exams">
            <ul class="list-group">
                <li class="list-group-item d-flex justify-content-between align-items-center" style="font-weight: bold; font-size: 2em;">
                    Ваши результаты экзаменов
                </li>
                <c:forEach items="${exams}" var="exam">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                            ${exam.subject}
                        <span class="badge badge-primary badge-pill">${exam.score}</span>
                    </li>
                </c:forEach>
            </ul>
        </div>

    </c:if>

    <div id = "dir-message"></div>


    </body>
</t:user>



<script>

    <c:if test="${empty name}">
    document.getElementById("sign-in-button").onclick = function () {
        return location.href = "<c:url value="/signIn"/>";
    }
    </c:if>


    <c:if test="${not empty name}">


    function apply(dirId, value){
        if(!confirm("Вы действительно хотите подать заявление? Это действие невозможно отменить.\n"
        + value)) return;
        $.ajax({
            type: 'post',
            url: "<c:url value="/home"/>",
            data: {
                dirId: dirId
            }
        }).done(function (data) {
            document.getElementById("dir-message").innerHTML =
                "<div class=\"alert alert-" + data.flag +  " alert-dismissible fade show\" role=\"alert\">\n" +
                data.message +
                "  <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                "    <span aria-hidden=\"true\">&times;</span>\n" +
                "  </button>\n" +
                "</div>";
            var content = "";
            for(var i = 0; i < data.declarations.length; i++){
                if(data.declarations[i] != undefined) content += "<li class=\"list-group-item\" style='color: #3872d1;'>"+data.declarations[i] +"</li>";
            }
            document.getElementById("decs").innerHTML = content;
            document.getElementById("decs-size").innerText = data.size;
        }).fail(function () {
            alert("Произошла ошибка.");
        });
    }


    </c:if>


</script>
</html>