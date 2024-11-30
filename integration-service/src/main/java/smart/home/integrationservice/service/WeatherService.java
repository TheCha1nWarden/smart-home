package smart.home.integrationservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import smart.home.integrationservice.model.WeatherRequest;
import smart.home.integrationservice.model.WeatherResponse;

import java.util.Optional;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.path}")
    private String apiWeatherPath;

    @Value("${weather.api.lang}")
    private String apiWeatherLang;

    @Value("${weather.api.units}")
    private String apiWeatherUnits;

    @Autowired
    private WebClient webClient;

    public Optional<WeatherResponse> getWeather(WeatherRequest weatherRequest) {
        try {
            JsonNode weatherJson = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(apiWeatherPath)
                            .queryParam("lat", weatherRequest.getLatitude())
                            .queryParam("lon", weatherRequest.getLongitude())
                            .queryParam("appid", apiKey)
                            .queryParam("lang", apiWeatherLang)
                            .queryParam("units", apiWeatherUnits)
                            .queryParam("mode", weatherRequest.getFormat())
                            .build())
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();
            return Optional.of(parseWeatherResponse(weatherJson));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private WeatherResponse parseWeatherResponse(JsonNode weatherJson) {
        JsonNode description = weatherJson.findPath("description");
        JsonNode temp = weatherJson.findPath("temp");
        JsonNode feelsLike = weatherJson.findPath("feels_like");
        JsonNode pressure = weatherJson.findPath("pressure");
        JsonNode humidity = weatherJson.findPath("humidity");
        JsonNode visibility = weatherJson.findPath("visibility");
        JsonNode windSpeed = weatherJson.findPath("speed");
        JsonNode nameLocation = weatherJson.findPath("name");

        return WeatherResponse.builder()
                .description(!description.isMissingNode() ? description.asText() : null)
                .temperature(!temp.isMissingNode() ? temp.asDouble() : null)
                .feelsLike(!feelsLike.isMissingNode() ? feelsLike.asDouble() : null)
                .pressure(!pressure.isMissingNode() ? pressure.asInt() : null)
                .humidity(!humidity.isMissingNode() ? humidity.asInt() : null)
                .visibility(!visibility.isMissingNode() ? visibility.asInt() : null)
                .windSpeed(!windSpeed.isMissingNode() ? windSpeed.asInt() : null)
                .locationName(!nameLocation.isMissingNode() ? nameLocation.asText() : null)
                .build();
    }

}
