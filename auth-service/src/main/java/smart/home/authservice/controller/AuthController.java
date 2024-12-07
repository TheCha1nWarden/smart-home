package smart.home.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smart.home.authservice.model.*;
import smart.home.authservice.security.JwtUtil;
import smart.home.authservice.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Регистрация нового пользователя
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@RequestBody RegisterUserRequest registerUserRequest) {
        // Создание нового пользователя
        User user = userService.registerUser(registerUserRequest);
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole(),
                registerUserRequest.isRememberUserFlag());
        return ResponseEntity.ok(new RegisterUserResponse(user.getId(), "Bearer " + token));
    }

    // Аутентификация пользователя и генерация JWT токена
    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> login(@RequestBody LoginUserRequest loginUserRequest) {
        Optional<User> userOptional = userService.findByUsername(loginUserRequest.getUsername());

        // Проверка существования пользователя и соответствия пароля
        if (userOptional.isPresent() && passwordEncoder.matches(loginUserRequest.getPassword(), userOptional.get().getPassword())) {
            // Генерация JWT токена при успешной аутентификации
            String token = jwtUtil.generateToken(loginUserRequest.getUsername(), userOptional.get().getRole(),
                    loginUserRequest.isRememberUserFlag());
            return ResponseEntity.ok(new LoginUserResponse(userOptional.get().getId(),"Bearer " + token));
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}
