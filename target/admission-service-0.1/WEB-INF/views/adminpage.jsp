<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<t:admin title="ЕГЭ - Админ">
    <h2 style="text-align: center;">Пользователи</h2>
    <div class="row">
        <div class="col admin-form" >
            <h3>Добавление нового пользователя</h3>
            <hr>
            <form method="POST">
                <input type="hidden" name="form" value = "1">
                <div class="form-group">
                    <label>Введите имя</label>
                    <input type="text" class="form-control" placeholder="Фамилия Имя Отчество" name="name">
                </div>
                <br>
                <div class="form-group">
                    <label >Введите паспорт</label>
                    <input type="text" class="form-control"  placeholder="Паспорт" name="passport">
                </div>
                <div class="form-group">
                    <label >Введите пароль</label>
                    <input type="text" class="form-control"  placeholder="Пароль" name="password">
                </div>
                <label>Введите результаты экзаменов.(Вводить только те экзамены, которые сдал абитуриент.)</label>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Информатика</span>
                    </div>
                    <input type="text" class="form-control" name="subj-1">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" >Математика</span>
                    </div>
                    <input type="text" class="form-control"  name="subj-2">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" >Русский язык</span>
                    </div>
                    <input type="text" class="form-control"  name="subj-3">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Физика</span>
                    </div>
                    <input type="text" class="form-control"  name="subj-4">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" >Биология</span>
                    </div>
                    <input type="text" class="form-control" name="subj-5">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Химия</span>
                    </div>
                    <input type="text" class="form-control" name="subj-6">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" >Обществознание</span>
                    </div>
                    <input type="text" class="form-control" name="subj-7">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" >История</span>
                    </div>
                    <input type="text" class="form-control" name="subj-8">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" >Литература</span>
                    </div>
                    <input type="text" class="form-control" name="subj-9">
                </div>
                <button type="submit" class="btn btn-primary">Добавить</button>
            </form>
        </div>
        <div class="col admin-form">
            <h3>Удаление пользователя</h3>
            <hr>
            <form method="POST">
                <input type="hidden" name="form" value = "2">
                <div class="form-group">
                    <label >Введите паспорт пользователя, которого вы хотите удалить.</label>
                    <input type="text" class="form-control" placeholder="Паспорт" name="passport">
                </div>
                <button type="submit" class="btn btn-primary">Удалить</button>
            </form>
            <h3>База данных всех пользователей:</h3>

            <div style=" height: 100%; overflow: auto;">
                <ul class="list-group">
                    <c:forEach items="${users}" var="user">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                                ${user.fullName} | Паспорт ${user.passport}
                            <ul class="list-group">
                                <c:forEach items="${user.exams}" var="exam">
                                    <li class="list-group-item d-flex justify-content-between align-items-center">
                                            ${exam.subject}
                                        <span class="badge badge-primary badge-pill"> ${exam.score}</span>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>
                </ul>

            </div>

        </div>
    </div>

    <h2 style="text-align: center;">Университеты</h2>

    <div class="row">
        <div class="col admin-form">
            <h3>Добавление нового города</h3>
            <form method="POST">
                <input type="hidden" name="form" value = "3">
                <div class="form-group">
                    <label>Введите город</label>
                    <input type="text" class="form-control" placeholder="Город" name="city">
                </div>
                <button type="submit" class="btn btn-primary">Добавить</button>
            </form>
            <h3>Все города в базе данных:</h3>
            <div style=" height: 100%; overflow: auto;">
                <ul class="list-group">
                    <c:forEach items="${cities}" var="city">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                                ${city}
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>


        <div class="col admin-form">
            <h3>Добавление нового университета:</h3>
            <form method="POST">
                <input type="hidden" name="form" value = "4">
                <div class="form-group">
                    <label >Введите название университета</label>
                    <input type="text" class="form-control"  placeholder="Название университета" name="uni">
                </div>
                <div class="form-group">
                    <label >Введите ID города</label>
                    <input type="text" class="form-control"  placeholder="ID города" name="city">
                </div>
                <div class="form-group">
                    <label >Введите информацию об университете:</label>
                    <textarea class="form-control" rows="6" name="info"></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Добавить</button>
            </form>
            <h3>Все университеты в базе данных:</h3>
            <div style=" height: 100%; overflow: auto;">
                <ul class="list-group">
                    <c:forEach items="${unis}" var="uni">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            ID ${uni.id} | ${uni.name} | ${uni.city}
                            <div style="overflow: auto; height: 30%;">
                                    ${uni.info}
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="col admin-form">
            <h3>Добавление нового направления:</h3>
            <form method="POST">
                <input type="hidden" name="form" value = "5">
                <div class="form-group">
                    <label >Введите название направления</label>
                    <input type="text" class="form-control"  placeholder="Название направления" name="dir">
                </div>
                <div class="form-group">
                    <label >Введите ID университета</label>
                    <input type="text" class="form-control"  placeholder="ID университета" name="uni">
                </div>
                <div class="form-group">
                    <label >Введите количество бюджетных мест:</label>
                    <input type="text" class="form-control"  placeholder="Количство бюджетных мест" name="budget">
                </div>
                <div class="form-group">
                    <label >Введите информацию о направлении:</label>
                    <textarea class="form-control" rows="6" name="info"></textarea>
                </div>
                <label>Введите минимальные баллы экзаменов.(Вводить только те экзамены, которые необходимы.)</label>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Информатика</span>
                    </div>
                    <input type="text" class="form-control" name="subj-1">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" >Математика</span>
                    </div>
                    <input type="text" class="form-control"  name="subj-2">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" >Русский язык</span>
                    </div>
                    <input type="text" class="form-control"  name="subj-3">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Физика</span>
                    </div>
                    <input type="text" class="form-control"  name="subj-4">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" >Биология</span>
                    </div>
                    <input type="text" class="form-control" name="subj-5">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Химия</span>
                    </div>
                    <input type="text" class="form-control" name="subj-6">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" >Обществознание</span>
                    </div>
                    <input type="text" class="form-control" name="subj-7">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" >История</span>
                    </div>
                    <input type="text" class="form-control" name="subj-8">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" >Литература</span>
                    </div>
                    <input type="text" class="form-control" name="subj-9">
                </div>
                <button type="submit" class="btn btn-primary">Добавить</button>
            </form>
            <h3>Все направления в базе данных:</h3>
            <div style=" height: 100%; overflow: auto;">
                <ul class="list-group">
                    <c:forEach items="${dirs}" var="dir">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            ID ${dir.id} |  ${dir.university.name} ${dir.name}
                            <div style="overflow: auto; height: 30%;">
                                    ${dir.info}
                            </div>
                            <ul class="list-group">
                                <c:forEach items="${dir.examsWithMinScore}" var="exam">
                                    <li class="list-group-item d-flex justify-content-between align-items-center">
                                            ${exam.subject}
                                        <span class="badge badge-primary badge-pill"> ${exam.score}</span>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
    </body>
</t:admin>
</html>

