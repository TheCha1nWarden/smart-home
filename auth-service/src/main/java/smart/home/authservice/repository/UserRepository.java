package smart.home.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smart.home.authservice.model.User;

import java.util.Optional;

// Интерфейс для взаимодействия с базой данных для сущности User
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);  // Поиск пользователя по имени пользователя
}
