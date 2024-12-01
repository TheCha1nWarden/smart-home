# Используем базовый образ с OpenJDK 17
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл с зависимостями и проектом
COPY target/integration-service-0.0.1-SNAPSHOT.jar integration-service.jar

# Открываем порт, на котором работает микросервис
EXPOSE 8084

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "integration-service.jar"]