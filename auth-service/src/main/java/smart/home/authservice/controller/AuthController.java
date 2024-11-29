package smart.home.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import smart.home.authservice.model.User;
import smart.home.authservice.security.JwtUtil;
import smart.home.authservice.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Регистрация нового пользователя
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String username,
                                      @RequestParam String password,
                                      @RequestParam String role) {
        // Создание нового пользователя
        User user = userService.registerUser(username, password, role);
        return ResponseEntity.ok("User registered successfully: " + user.getUsername());
    }

    // Аутентификация пользователя и генерация JWT токена
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam Boolean rememberUserFlag) {
        Optional<User> userOptional = userService.findByUsername(username);

        // Проверка существования пользователя и соответствия пароля
        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            // Генерация JWT токена при успешной аутентификации
            String token = jwtUtil.generateToken(username, userOptional.get().getRole(), rememberUserFlag);
            return ResponseEntity.ok("Bearer " + token);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
