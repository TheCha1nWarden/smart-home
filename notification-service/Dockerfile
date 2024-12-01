# Используем базовый образ с OpenJDK 17
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл с зависимостями и проектом
COPY target/notification-service-0.0.1-SNAPSHOT.jar notification-service.jar

# Открываем порт, на котором работает микросервис
EXPOSE 8083

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "notification-service.jar"]