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

    @KafkaListener(topics = "${KAFKA_TOPIC_SENSOR_NOTIFICATION}", groupId = "${KAFKA_CONSUMER_GROUP_ID}")
    public void listenSensorNotification(@Payload SensorNotification notification) {
        notificationService.sendSensorNotification(notification);
    }

}
