package smart.home.integrationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smart.home.integrationservice.model.WeatherRequest;
import smart.home.integrationservice.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @PostMapping("/get")
    public Object getWeather(@RequestBody WeatherRequest weatherRequest) {
        return weatherService.getWeather(weatherRequest);
    }

}
