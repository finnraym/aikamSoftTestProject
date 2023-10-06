# Тестовое задание на позицию Java-разработчика в Aikamsoft.
## Технологический стек.
1. Java 8
2. Maven
3. Postgresql
4. Gson

## Запуск приложения.
1. Восстановить бэкап базы данных по файлу bd.backup, находящегося в корне проекта, с помощью утилиты pg_restore. Подробнее - https://www.postgresql.org/docs/current/app-pgrestore.html
2. Собрать проект при помощи Maven - mvn clean package
3. Запустить jar-файл (запускать jar-with-dependencies) c аргументами командной строки. Пример - java -jar target/aikamTestProject-1.0-SNAPSHOT-jar-with-dependencies.jar search correctSearchInput.json output.json

Входные файлы для тестирования находятся в корне проекта: correctSearchInput.json, correctStatInput.json, incorrectSearchInput.json, incorrectStatInput.json, output.json

### Контакты для связи
- Почта: vpegorov95@gmail.com
- TG: @egorov_vp