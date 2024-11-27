package smart.home.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smart.home.authservice.model.User;

public interface TestRepo extends JpaRepository<User, Long> {
}
