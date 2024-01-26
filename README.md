<h1>CLEVERTEC (homework-13)</h1>

<p>CLEVERTEC homework-13 boot:</p>
<p>Task</p>
<p>Берём за основу существующее приложение и переезжаем на Spring boot 3.2.* в ветке feature/boot</p>

<p>Добавляем сущность HouseHistory (id, house_id, person_id, date, type)</p>
<ol>
<li>type [OWNER, TENANT]</li>
    <ul>
        <li>Создать свой тип данных в БД</li>
        <li>Хранить как enum в коде</li>
    </ul>
<li>При смене места жительства добавляем запись в HouseHistory [type = TENANT], с текущей датой</li>
<li>При смене владельца, добавляем запись в HouseHistory [type = OWNER], с текущей датой</li>
<li>* Реализовать через триггер в БД</li>
<li>* Если используется миграция, дописать новый changeset, а не исправлять существующие.</li>
</ol>

<p>Добавляем методы:</p>
<ol>
<li>GET для получения всех Person когда-либо проживавших в доме</li>
<li>GET для получения всех Person когда-либо владевших домом</li>
<li>GET для получения всех House где проживал Person</li>
<li>GET для получения всех House которыми когда-либо владел Person</li>
</ol>

<p>Добавляем кэш из задания по рефлексии на сервисный слой House и Person.</p>
<ol>
<li>Добавляем Integration тесты, чтобы кэш работал в многопоточной среде.</li>
<li>Делаем экзекутор на 6 потоков и параллельно вызываем сервисный слой (GET\POST\PUT\DELETE) и проверяем, что результат соответствует ожиданиям.</li>
<li>Используем H2 или *testcontainers </li>
</ol>

<h2>Что сделано:</h2>
<p>Взято за основу существующее приложение и переезжаем на Spring boot 3.2.* в ветке feature/boot</p>

<p>Добавлена сущность HouseHistory (id, house_id, person_id, date, type)</p>
<ol>
<li>type [OWNER, TENANT]</li>
    <ul>
        <li>Создан свой тип данных в БД</li>
        <li>Хранится как enum в коде</li>
    </ul>
<li>При смене места жительства добавляется запись в HouseHistory [type = TENANT], с текущей датой</li>
<li>При смене владельца, добавляется запись в HouseHistory [type = OWNER], с текущей датой</li>
<li>* Реализовано через триггер в БД</li>
<li>* Дописан новый changeset, а не исправлен существующие.</li>
</ol>

<p>Добавлены методы:</p>
<ol>
<li>GET для получения всех Person когда-либо проживавших в доме</li>
<li>GET для получения всех Person когда-либо владевших домом</li>
<li>GET для получения всех House где проживал Person</li>
<li>GET для получения всех House которыми когда-либо владел Person</li>
</ol>

<p>Добавлен кэш из задания по рефлексии на сервисный слой House и Person.</p>
<ol>
<li>Добавлены Integration тесты, чтобы кэш работал в многопоточной среде.</li>
<li>Сделан экзекутор на 6 потоков и параллельно вызыван сервисный слой (GET\POST\PUT\DELETE) и проверено, что результат соответствует ожиданиям.</li>
<li>Использован *testcontainers </li>
</ol>



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
