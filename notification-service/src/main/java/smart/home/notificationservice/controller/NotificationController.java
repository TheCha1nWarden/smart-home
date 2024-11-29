package smart.home.notificationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smart.home.notificationservice.model.email.SendEmailResponse;
import smart.home.notificationservice.model.email.Status;
import smart.home.notificationservice.model.notifications.SensorNotification;
import smart.home.notificationservice.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/sendSensorNotification")
    public SendEmailResponse sendSensorNotification(@RequestBody SensorNotification sensorNotification) {
        try {
            if (sensorNotification.getUserInfo() == null || sensorNotification.getUserInfo().getEmail() == null) {
                throw new IllegalArgumentException("User data cannot be null!");
            }
            notificationService.sendSensorNotification(sensorNotification);
            return SendEmailResponse.builder().status(Status.SUCCESS).build();
        } catch (Exception e) {
            return SendEmailResponse.builder().status(Status.ERROR).build();
        }
    }
}
