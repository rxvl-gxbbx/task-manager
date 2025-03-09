# README

## Локальный запуск проекта

### Требования

- Docker и Docker Compose
- Java 21

### Шаги для запуска

1. **Запустить Docker и поднять сервисы с помощью `docker-compose`**
   ```sh
   docker-compose up -d
   ```
   Это создаст контейнер с базой данных PostgreSQL.

2. **Выполнить миграции Flyway**
   ```sh
   mvn flyway:migrate
   ```
   Этот шаг применит миграции к базе данных.

3. **Запустить Spring Boot приложение**
   ```sh
   ./mvnw spring-boot:run
   ```
   Приложение теперь запущено и работает с PostgreSQL.

### Остановка сервисов

Чтобы временно остановить базу данных, используйте:

```sh
docker-compose stop
```

