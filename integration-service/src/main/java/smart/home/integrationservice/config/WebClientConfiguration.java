package smart.home.integrationservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Value("${weather.api.baseurl}")
    private String baseUrl;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Bean
    public WebClient webClientWeather() {
        return webClientBuilder
                .baseUrl(baseUrl).build();
    }

}
