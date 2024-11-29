package smart.home.notificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import smart.home.notificationservice.model.email.SendEmailRequest;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEmail(SendEmailRequest sendEmailRequest) {
        if (sendEmailRequest.getRecipient() == null) {
            log.error("email recipient cannot be null!");
            return;
        }
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(sendEmailRequest.getRecipient());
        mailMessage.setSubject(sendEmailRequest.getSubject());
        mailMessage.setText(sendEmailRequest.getMessage());
        mailMessage.setFrom(fromEmail);

        mailSender.send(mailMessage);
    }

}
