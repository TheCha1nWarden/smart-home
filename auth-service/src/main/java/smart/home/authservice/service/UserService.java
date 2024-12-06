package smart.home.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import smart.home.authservice.model.RegisterUserRequest;
import smart.home.authservice.model.User;
import smart.home.authservice.model.mapper.UserDto;
import smart.home.authservice.model.mapper.UserDtoMapper;
import smart.home.authservice.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDtoMapper mapper;

    // Регистрация нового пользователя
    public User registerUser(RegisterUserRequest registerUserRequest) {
        User user = new User();
        user.setUsername(registerUserRequest.getUsername());
        // Хеширование пароля перед сохранением в БД
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        user.setEmail(registerUserRequest.getEmail());
        user.setRole(registerUserRequest.getRole());
        return userRepository.save(user);
    }

    // Поиск пользователя по имени
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id).map(mapper::map);
    }

    public Optional<Long> findIdByUsername(String username) {
        return userRepository.findByUsername(username).map(User::getId);
    }
}
