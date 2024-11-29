package smart.home.notificationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smart.home.notificationservice.model.email.SendEmailRequest;
import smart.home.notificationservice.model.email.SendEmailResponse;
import smart.home.notificationservice.model.email.Status;
import smart.home.notificationservice.service.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public SendEmailResponse sendEmail(@RequestBody SendEmailRequest sendEmailRequest) {
        try {
            emailService.sendEmail(sendEmailRequest);
            return SendEmailResponse.builder().status(Status.SUCCESS).build();
        } catch (Exception e) {
            return SendEmailResponse.builder().status(Status.ERROR).build();
        }
    }

}
