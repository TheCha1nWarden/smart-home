# Используем базовый образ с OpenJDK 17
FROM openjdk:17

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл с зависимостями и проектом
COPY target/simple-service-0.0.1-SNAPSHOT.jar simple-service.jar

# Открываем порт, на котором работает микросервис
EXPOSE 8080

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "simple-service.jar"]