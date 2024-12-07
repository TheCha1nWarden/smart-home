package smart.home.authservice.model;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String username;
    private String password;
    private String email;
    private String role;
    private boolean rememberUserFlag;
}
