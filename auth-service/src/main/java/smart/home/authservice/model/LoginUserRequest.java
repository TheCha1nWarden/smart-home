package smart.home.authservice.model;

import lombok.Data;

@Data
public class LoginUserRequest {
    private String username;
    private String password;
    private boolean rememberUserFlag;
}
