<h1>CLEVERTEC (homework-14)</h1>

<p>CLEVERTEC homework-14 boot test:</p>
<p>Task</p>
<p>
Берём за основу существующее приложение и покрываем его тестами на 80%, 
используя средства Spring для тестирования:</p>
<ol>
<li>testcontainers </li>
<li>mockMvc</li>
<li>*wireMock</li>
</ol>

<p>* Добавляем swagger</p>

<h2>Что сделано:</h2>
<p>
Взято за основу существующее приложение и покрыто тестами на 80%, 
используя средства Spring для тестирования:</p>
<ol>
<li>testcontainers</li>
<li>mockMvc</li>
<li>*wireMock (не использовался в отсутствии сторонних сервисов)</li>
</ol>

<p>* Добавлен swagger</p>


<h3>Как запускать:</h3>
<ol>
<li>Билдим проект: .\gradlew clean build</li>
<li>Запускаем postgresql в docker: docker-compose up -d</li>
<li>Запускаем приложение в tomcat</li>
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

<p>
3. HTTP PATCH method (RFC 6902) for Address Controller 
</p>
<p>PATCH:</p>
<p>http://localhost:8080/addresses/8ad957d6-9a44-4a6a-8789-0e3638bcb46c</p>
<p>
[{ "op": "replace", "path": "/city", "value": "Rome" }]
</p>
