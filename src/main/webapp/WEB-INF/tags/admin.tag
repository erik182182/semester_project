<%@tag description="Main page" pageEncoding="UTF-8" %>

<%@attribute name="title" required="true" type="java.lang.String" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="utf-8">
    <title>${title}</title>
    <link rel="shortcut icon" href="http://s1.iconbird.com/ico/2013/6/281/w256h25613715677480005BookmarkOpen.png" type="image/x-icon">


    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value= "/styles/style.css"/>" >
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand" href= <c:url value="/home"/> >ЕГЭ</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <c:if test="${not empty message}">
                <li>
                    <h2>${message}</h2>
                </li>
            </c:if>
        </ul>
        <span style="color: #fff; margin-left: 15px; font-weight: bold">${name}</span>
        <form method="post" action="<c:url value="/logout"/>" >
            <button type="submit" class="btn btn-light" style=" margin-left: 15px;" id="log-out-button">Выйти</button>
        </form>

    </div>
</nav>

<jsp:doBody />