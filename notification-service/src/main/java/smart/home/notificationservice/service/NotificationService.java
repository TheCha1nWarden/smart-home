package smart.home.notificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import smart.home.notificationservice.factory.SensorNotificationMessageFactory;
import smart.home.notificationservice.model.notifications.SensorNotification;

@Service
@Slf4j
public class NotificationService {

    private static final String NOTIFICATION_SUBJECT = "Оповещение: Состояние вашего датчика %s";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SensorNotificationMessageFactory sensorNotificationMessageFactory;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendSensorNotification(SensorNotification sensorNotification) {
        if (sensorNotification.getUserInfo() == null || sensorNotification.getUserInfo().getEmail() == null) {
            log.error("User data cannot be null! notification from sensorId: %s skipped!", sensorNotification.getSensorId());
            return;
        }
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(sensorNotification.getUserInfo().getEmail());
        mailMessage.setSubject(String.format(NOTIFICATION_SUBJECT, sensorNotification.getSensorName()));
        mailMessage.setText(sensorNotificationMessageFactory.getSensorNotificationMessage(sensorNotification));
        mailMessage.setFrom(fromEmail);

        mailSender.send(mailMessage);
    }


}
