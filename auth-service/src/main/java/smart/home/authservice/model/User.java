package smart.home.authservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

// Аннотация @Data от Lombok автоматически генерирует геттеры, сеттеры и другие полезные методы
@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;  // Уникальное имя пользователя

    @Column(nullable = false)
    private String password;  // Хэшированный пароль

    @Column(nullable = false)
    private String role;  // Роль пользователя (например, ROLE_USER или ROLE_ADMIN)

    @Column(nullable = false)
    private String email;

    public User(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
