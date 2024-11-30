package smart.home.integrationservice.model;

import lombok.Data;

@Data
public class WeatherRequest {
    private String latitude;
    private String longitude;
    private String format;
}
