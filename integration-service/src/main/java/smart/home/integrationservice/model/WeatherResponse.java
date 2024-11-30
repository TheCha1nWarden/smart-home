package smart.home.integrationservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherResponse {
    private String description;
    private Double temperature;
    private Double feelsLike;
    private Integer pressure;
    private Integer humidity;
    private Integer visibility;
    private Integer windSpeed;
    private String locationName;
}
