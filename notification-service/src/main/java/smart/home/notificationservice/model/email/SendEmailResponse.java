package smart.home.notificationservice.model.email;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendEmailResponse {
    private Status status;
}
