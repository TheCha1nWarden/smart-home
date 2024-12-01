# Используем базовый образ с OpenJDK 17
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл с зависимостями и проектом
COPY target/device-service-0.0.1-SNAPSHOT.jar device-service.jar

# Открываем порт, на котором работает микросервис
EXPOSE 8081

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "device-service.jar"]