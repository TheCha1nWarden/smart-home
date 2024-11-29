package smart.home.notificationservice.factory;

import org.springframework.stereotype.Component;
import smart.home.notificationservice.model.notifications.SensorNotification;

@Component
public class SensorNotificationMessageFactory {

    private static final String SENSOR_NOTIFICATION_MESSAGE_TEMPLATE = """
            Здравствуйте, %s!
            
            Мы хотим сообщить вам о текущем состоянии вашего датчика \"%s\", установленного в %s.
            
            Состояние датчика:
            
            Датчик: %s
            Местоположение: %s
            Текущие показатели: %s
            Описание: %s
            
            Рекомендуем вам проверить состояние устройства, если что-то выглядит подозрительно. Для вашего удобства, мы также предлагаем воспользоваться приложением для более подробного мониторинга и управления.
            
            Если у вас есть вопросы, пожалуйста, свяжитесь с нашей службой поддержки.
            
            С уважением,
            Команда HomeZen
            """;

    public String getSensorNotificationMessage(SensorNotification sensorNotification) {
        return String.format(SENSOR_NOTIFICATION_MESSAGE_TEMPLATE,
                sensorNotification.getUserInfo().getName() != null ? sensorNotification.getUserInfo().getName() : "-",
                sensorNotification.getSensorName() != null ? sensorNotification.getSensorName() : "-",
                sensorNotification.getLocation() != null ? sensorNotification.getLocation() : "-",
                sensorNotification.getSensorName() != null ? sensorNotification.getSensorName() : "-",
                sensorNotification.getLocation() != null ? sensorNotification.getLocation() : "-",
                sensorNotification.getSensorValue() != null ? sensorNotification.getSensorValue() : "-",
                sensorNotification.getDescription() != null ? sensorNotification.getDescription() : "-");

    }

}
