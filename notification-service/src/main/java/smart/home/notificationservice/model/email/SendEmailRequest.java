package smart.home.notificationservice.model.email;

import lombok.Data;

@Data
public class SendEmailRequest {
    private String recipient;
    private String subject;
    private String message;
}
