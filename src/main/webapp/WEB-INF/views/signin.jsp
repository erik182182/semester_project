
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Вход в систему</title>

    <link rel="shortcut icon" href="http://s1.iconbird.com/ico/2013/6/281/w256h25613715677480005BookmarkOpen.png" type="image/x-icon">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/styles/style.css"/>" >
</head>
<body>
<div class="card sign">
    <div class="card-header" >
        <h1>Вход</h1>
    </div>
    <div class="card-body">
        <form method="POST">
            <div class="form-group">
                <label for="passport">Введите серию и номер паспорта</label>
                <input type="text" name="passport" class="form-control" id="passport" aria-describedby="passport-help" placeholder="Паспорт">
                <small id="passport-help" class="form-text text-muted">Пример ввода: 8013878313</small>
                <p style="color: red;" id="error-passport"></p>
            </div>
            <div class="form-group">
                <label for="password">Пароль</label>
                <input type="password" name="password" class="form-control" id="password" placeholder="Пароль">
                <p style="color: red;" id="error-password"></p>
            </div>
            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" id="remember" name="remember">
                <label class="form-check-label" for="remember">Запомнить меня</label>
            </div>
            <button type="submit" class="btn btn-primary" id="submit">Войти</button>
        </form>
    </div>
    <c:if test="${not empty message}">
        <div class="card-footer text-muted">
            <span style="color: red;">${message}</span>
        </div>
    </c:if>
</div>
</body>


<script type="text/javascript">
    document.getElementById("submit").onclick = function(){

        var regExp = /^[0-9]{10}$/;

        if(!regExp.test(passport.value) ){
            document.getElementById("error-passport").innerText = "Паспорт должен содержать 10 цифр.";
            return false;
        }
        else{
            document.getElementById("error-passport").innerText = "";
        }
        if(password.value.length == 0){
            document.getElementById("error-password").innerText = "Пароль не введен.";
            return false;
        }
        else{
            document.getElementById("error-password").innerText = "";
        }
    }
</script>
</html>