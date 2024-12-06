package smart.home.authservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    // Конфигурация для шифрования паролей
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Конфигурация безопасности HTTP
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Используем Customizer для настройки CSRF
        http.csrf().disable() // Хранение CSRF токена в cookies
                .authorizeRequests(auth -> auth
                        .requestMatchers("/api/auth/*").permitAll()  // Разрешение доступа к /api/auth/** для всех
                        .anyRequest().authenticated()  // Требование аутентификации для всех остальных запросов
                );
        // Добавляем фильтр перед стандартным фильтром аутентификации
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
