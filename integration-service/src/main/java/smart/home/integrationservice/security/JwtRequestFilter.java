package smart.home.integrationservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    // Секретный ключ для подписи токена
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Проверяем, что заголовок начинается с "Bearer " и извлекаем токен
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                // Валидация токена и получение информации из него
                Claims claims = Jwts.parser()
                        .setSigningKey(secretKey)
                        .parseClaimsJws(jwt)
                        .getBody();
                username = claims.getSubject();
            } catch (Exception e) {
                // Логируем ошибку и ставим статус 401 (неавторизован)
                logger.warn("JWT token validation failed");
            }
        }

        // Проверяем, что токен валидный и контекст аутентификации пустой
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username, null, null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // Устанавливаем аутентификацию в контекст безопасности
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Продолжаем выполнение цепочки фильтров
        chain.doFilter(request, response);
    }
}
