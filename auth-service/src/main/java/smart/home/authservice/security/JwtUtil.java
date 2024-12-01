package smart.home.authservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // Секретный ключ для подписи токена
    @Value("${JWT_SECRET_KEY}")
    private String secretKey;

    private final long shortExpirationTime = 900000; // Время действия короткого токена (15 минуты)
    private final long longExpirationTime = 2592000000L; // Время действия долгого токена (30 дней)

    // Генерация JWT токена
    public String generateToken(String username, String role, Boolean rememberUserFlag) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)  // Добавление роли в токен
                .setIssuedAt(new Date())  // Дата создания токена
                .setExpiration(new Date(System.currentTimeMillis() + (rememberUserFlag ? longExpirationTime : shortExpirationTime)))  // Дата истечения
                .signWith(SignatureAlgorithm.HS256, secretKey)  // Подпись токена
                .compact();
    }

    // Валидация токена и получение информации из него
    public Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
