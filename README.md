<h1>CLEVERTEC (homework-12)</h1>

<p>CLEVERTEC homework-12 hibernate:</p>
<p>Создать Web приложение учёта домов и жильцов</p>
<p>Описание:</p>
<ol>
<li>2 сущности: House, Person</li>
<li>Система должна предоставлять REST API для выполнения следующих операций:</li>
    <ul>
        <li>CRUD для House. В GET запросах не выводить информацию о Person</li>
        <li>CRUD для Person. В GET запросах не выводить информацию о House</li>
        <li>Для GET операций использовать pagination (default size: 15).</li>
    </ul>
</ol>

<p>House:</p>
<ol>
<li>У House обязаны быть поля id, uuid, area, country, city, street, number, create_date</li>
<li>House может иметь множество жильцов (0-n).</li>
<li>У House может быть множество владельцев (0-n).</li>
<li>create_date устанавливается один раз при создании.</li>
</ol>

<p>Person</p>
<ol>
<li>У Person обязаны быть id, uuid, name, surname, sex, passport_series, passport_number, create_date, update_date</li>
<li>Person обязан жить только в одном доме и не может быть бездомным.</li>
<li>Person не обязан владеть хоть одним домом и может владеть множеством домов.</li>
<li>Сочетание passport_series и passport_number уникально.</li>
<li>sex должен быть [Male, Female].</li>
<li>Все связи обеспечить через id.</li>
<li>Не возвращать id пользователям сервисов, для этого предназначено поле uuid</li>
<li>create_date устанавливается один раз при создании.</li>
<li>
update_date устанавливается при создании и изменяется каждый раз, 
когда меняется информация о Person. При этом, если запрос не изменяет 
информации, поле не должно обновиться.
</li>
</ol>

<p>Примечание:</p>
<ol>
<li>Ограничения и нормализацию сделать на своё усмотрение.</li>
<li>Поля представлены для хранения в базе данных. В коде могут отличаться.</li>
</ol>

<p>Обязательно:</p>
<ol>
<li>GET для всех Person проживающих в House</li>
<li>GET для всех House, владельцем которых является Person</li>
<li>Конфигурационный файл: application.yml</li>
<li>Скрипты для создания таблиц должны лежать в classpath:db/</li>
<li>Добавить 5 домов и 10 жильцов (Один дом без жильцов, и как минимум в 1 доме больше 1 владельца)</li>
</ol>

<p>Дополнительно:</p>
<ol>
<li>*Добавить миграцию</li>
<li>*Полнотекстовый поиск (любое текстовое поле) для House</li>
<li>*Полнотекстовый поиск (любое текстовое поле) для Person</li>
<li>*PATCH для Person и House</li>
</ol>

<p>Application requirements</p>

<ol>
<li>JDK version: 17 – use Streams, java.time.*, etc. where it is possible.</li>
<li>Application packages root: ru.clevertec.ecl.</li>
<li>Any widely-used connection pool could be used.</li>
<li>Spring JDBC Template should be used for data access.</li>
<li>Use transactions where it’s necessary.</li>
<li>Java Code Convention is mandatory (exception: margin size – 120 chars).</li>
<li>Build tool: Gradle, latest version.</li>
<li>Web server: Apache Tomcat.</li>
<li>Application container: Spring IoC. Spring Framework, the latest version.</li>
<li>Database: PostgreSQL, latest version.</li>
<li>Testing: JUnit 5.+, Mockito.</li>
<li>Service layer should be covered with unit tests not less than 80%.</li>
<li>
Repository layer should be tested using integration tests with 
an in-memory embedded database or testcontainers.
</li>
<li>As a mapper use Mapstruct.</li>
<li>Use lombok.</li>
</ol>

<p>General requirements</p>

<ol>
<li>Code should be clean and should not contain any “developer-purpose” constructions.</li>
<li>App should be designed and written with respect to OOD and SOLID principles.</li>
<li>Code should contain valuable comments where appropriate.</li>
<li>Public APIs should be documented (Javadoc).</li>
<li>Clear layered structure should be used with responsibilities of each application layer defined.</li>
<li>JSON should be used as a format of client-server communication messages.</li>
<li>
Convenient error/exception handling mechanism should be implemented: all errors should be meaningful on backend side. Example: handle 404 error:
HTTP Status: 404
response body    
{
“errorMessage”: “Requested resource not found (uuid = f4fe3df1-22cd-49ce-a54d-86f55a7f372e)”,
 “errorCode”: 40401
 }

where *errorCode” is your custom code (it can be based on http status and requested resource - person or house)
</li>
</ol>

<p>
Application restrictions
It is forbidden to use:
</p>
<ol>
<li>Spring Boot.</li>
<li>Spring Data Repositories.</li>
</ol>

<h2>Что сделано:</h2>
<p>Создано Web приложение учёта домов и жильцов</p>
<p>Описание:</p>
<ol>
<li>2 сущности: House, Person</li>
<li>Система предоставляет REST API для выполнения следующих операций:</li>
    <ul>
        <li>CRUD для House. В GET запросах не выводится информация о Person</li>
        <li>CRUD для Person. В GET запросах не выводиться информация о House</li>
        <li>Для GET операций использован pagination (default size: 15).</li>
    </ul>
</ol>

<p>House:</p>
<ol>
<li>У House есть поля id, uuid, area, country, city, street, number, create_date</li>
<li>House может иметь множество жильцов (0-n).</li>
<li>У House может быть множество владельцев (0-n).</li>
<li>create_date устанавливается один раз при создании.</li>
</ol>

<p>Person</p>
<ol>
<li>У Person есть id, uuid, name, surname, sex, passport_series, passport_number, create_date, update_date</li>
<li>Person живет только в одном доме и не может быть бездомным.</li>
<li>Person не обязан владеть хоть одним домом и может владеть множеством домов.</li>
<li>Сочетание passport_series и passport_number уникально.</li>
<li>sex только [Male, Female].</li>
<li>Все связи через id.</li>
<li>Не возвращаются id пользователям сервисов, для этого предназначено поле uuid</li>
<li>create_date устанавливается один раз при создании.</li>
<li>
update_date устанавливается при создании и изменяется каждый раз, 
когда меняется информация о Person Passport. При этом, если запрос не изменяет 
информации, поле не обновиться.
</li>
</ol>

<p>Примечание:</p>
<ol>
<li>Ограничения и нормализация сделана.</li>
</ol>

<p>Обязательно сделано:</p>
<ol>
<li>GET для всех Person проживающих в House</li>
<li>GET для всех House, владельцем которых является Person</li>
<li>Конфигурационный файл: application.yml</li>
<li>Скрипты для создания таблиц лежат в classpath:db/</li>
<li>Добавлены записи в таблицы (Один дом без жильцов, и как минимум в 1 доме больше 1 владельца)</li>
</ol>

<p>Дополнительно:</p>
<ol>
<li>*Добавлена миграция</li>
</ol>

<p>Application requirements</p>

<ol>
<li>JDK version: 17 – use Streams, java.time.*, etc. where it is possible.</li>
<li>Application packages root: ru.clevertec.ecl.</li>
<li>Hikari connection pool used.</li>
<li>Spring JDBC Template used for data access (Only demonstration in PersonsHousesPossessingDAO).</li>
<li>Using transactions where it’s necessary.</li>
<li>Java Code Convention using (exception: margin size – 120 chars).</li>
<li>Build tool: Gradle, latest version.</li>
<li>Web server: Apache Tomcat.</li>
<li>Application container: Spring IoC. Spring Framework, the latest version.</li>
<li>Database: PostgreSQL, latest version.</li>
<li>As a mapper using Mapstruct.</li>
<li>Using lombok.</li>
</ol>

<p>General requirements</p>

<ol>
<li>Code mostly clean and not contain any “developer-purpose” constructions.</li>
<li>App is designed and written with respect to OOD and SOLID principles.</li>
<li>Code contains valuable comments where appropriate.</li>
<li>Public APIs documented (Javadoc).</li>
<li>Clear layered structure is used with responsibilities of each application layer defined.</li>
<li>JSON is using as a format of client-server communication messages.</li>
<li>
Convenient error/exception handling mechanism should is implemented: all errors are meaningful on backend side. Example: handle 404 error:


response body    
{
“statusCode”: statusCode,
“timestamp”: timestamp,
"message": "message"
}
</li>
</ol>

<p>
Application restrictions
It isn't using:
</p>
<ol>
<li>Spring Boot.</li>
<li>Spring Data Repositories.</li>
</ol>



<h3>Как запускать:</h3>
<ol>
<li>Билдим проект: .\gradlew clean build</li>
<li>Запускаем postgresql в docker: docker-compose up -d</li>
<li>Запускаем liquibase и добавляем записи в таблицы: .\gradlew update</li>
</ol>

<p>Проверяем работу приложения, используем postman.</p>
<p><b>1. Для Person controller (CRUD-операции):</b></p>

<p>GET:</p>
<li>http://localhost:8080/persons/285b3607-22be-47b0-8bbc-f1f20ee0c17b</li>
<li>http://localhost:8080/persons/285b3607-22be-47b0-8bbc-f1f20ee0c17b/houses</li>
<li>http://localhost:8080/persons/285b3607-22be-47b0-8bbc-f1f20ee0c17b/houses?page=1&page_size=1</li>
<li>http://localhost:8080/persons</li>
<li>http://localhost:8080/persons?page=1&page_size=3</li>



<p>POST:</p>
<p>http://localhost:8080/persons</p>
<p>
{
    "name": "Willy",
    "surname": "Martinos",
    "sex": "male",
    "livingHouseUUID": "1b9000e6-ded1-4946-be78-2e8fbd85673e",
    "passportUUID": "41c235cd-b491-41be-8dcc-c04a1ec9eec9"
}
</p>

<p>PUT:</p>
<p>http://localhost:8080/persons</p>
<p>
{
    "uuid": "9fa06975-ab66-4ac8-913e-38c0de2fbe5d",
    "name": "Williama",
    "surname": "Martin",
    "sex": "female",
    "livingHouseUUID": "1b9000e6-ded1-4946-be78-2e8fbd85673e",
    "passportUUID": "41c235cd-b491-41be-8dcc-c04a1ec9eec9"
}
</p>

<p>
DELETE:
</p>
<p>
{
    "uuid": "9fa06975-ab66-4ac8-913e-38c0de2fbe5d"
}
</p>

<p>
2. Аналогично для House Controller.
</p>
