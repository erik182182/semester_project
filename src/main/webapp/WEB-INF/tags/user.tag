<%@tag description="Main page" pageEncoding="UTF-8" %>

<%@attribute name="title" required="true" type="java.lang.String" %>
<%@attribute name="active" type="java.lang.Integer" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <meta charset="utf-8">
    <title>${title}</title>
    <link rel="shortcut icon" href="http://s1.iconbird.com/ico/2013/6/281/w256h25613715677480005BookmarkOpen.png" type="image/x-icon">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="<c:url value= "/styles/style.css"/>" >

    <script
            src="https://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            crossorigin="anonymous"></script>


</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand" href="<c:url value="/home"/>" >ЕГЭ</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse " id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <c:if test="${active eq 1}">
                <li class="nav-item active">
                    <a class="nav-link" href="<c:url value="/home" />" style="font-weight: bold;">Главная<span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/declarations" />" style="font-weight: bold; margin-left: 3%;">Заявления</a>
                </li>
            </c:if>
            <c:if test="${active eq 2}">
                <li class="nav-item ">
                    <a class="nav-link" href="<c:url value="/home" />" style="font-weight: bold;">Главная</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="<c:url value="/declarations" />" style="font-weight: bold; margin-left: 3%;">
                        Заявления<span class="sr-only">(current)</span></a>
                </li>
            </c:if>
        </ul>
        <c:if test="${not empty name}">
            <span style="color: #fff; margin-left: 15px; font-weight: bold">${name}</span>
        </c:if>


        <c:if test="${empty name}">
            <button type="button" class="btn btn-light" style=" margin-left: 15px;" id="sign-in-button">Войти</button>
        </c:if>
        <c:if test="${not empty name}">
            <form method="post" action="<c:url value="/logout"/>" >
                <button type="submit" class="btn btn-light" style=" margin-left: 15px;" id="log-out-button">Выйти</button>
            </form>

        </c:if>
    </div>
</nav>

<jsp:doBody />