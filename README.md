# Weather

Добрый день!

Тестовое задание: написать веб приложение для отображения погоды.

Выполнил Ягудин Рустем.

Для успешного запуска нужно произвести следующие настройки:
1. Выполнить скрипт db_generate.sql создания базы MySql Server (у меня версия 5.7). Он находится в корне репозитория.
2. Настроить подключение к созданной базе данных в файле \src\main\resources\jdbc.properties.
3. Запустить сервис ActiveMQ, у меня он версии 5.7.0.
4. Настроить путь к файлу логов здесь: \src\main\resources\logback.xml
5. Сборку я выполнял на JDK8u91, запуск на tomcat8
6. В системе присутствует две роли Админ и Пользователь, их логины-пароли хранятся в базе (пароли в више хэш-суммы).

После создания базы в нее сразу кладутся 2 пользователя Админ(Administrator/test) и Пользователь(User/test).
Регистрацию пользователей я не реализовывал, можно их вставлять напрямую в базу.
Разница между ролями в том, что Админу доступен просмотр истории запросов, которая также персистится в базу.
