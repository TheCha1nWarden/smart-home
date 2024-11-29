package smart.home.notificationservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import smart.home.notificationservice.model.notifications.SensorNotification;
import smart.home.notificationservice.service.NotificationService;

@Component
public class SensorNotificationListener {

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "sensor-notifications", groupId = "sensor-notifications-group")
    public void listenSensorNotification(@Payload SensorNotification notification) {
        notificationService.sendSensorNotification(notification);
    }

}
