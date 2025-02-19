package com.smarthome.app.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smarthome.app.R;
import com.smarthome.app.adapters.SensorReadingAdapter;
import com.smarthome.app.model.Sensor;
import com.smarthome.app.model.WeatherRequest;
import com.smarthome.app.model.WeatherResponse;
import com.smarthome.app.network.ApiClient;
import com.smarthome.app.network.ApiService;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {

    private static final String SHARED_PREFS_NAME = "UserData";
    private SharedPreferences sharedPreferences;

    private static final int LOCATION_REQUEST_CODE = 1000;
    private static final String LOCATION_NAME_TEMPLATE = "%s";
    private static final String DESCRIPTION_TEMPLATE = "%s";
    private static final String TEMPERATURE_TEMPLATE = "Температура: %s°C, Ощущается как: %s°C";
    private static final String DETAILS_TEMPLATE = "Давление: %s гПа, Влажность: %s%%";
    private static final String VISIBILITY_TEMPLATE = "Видимость: %s м";
    private static final String WIND_TEMPLATE = "Скорость ветра: %s м/с";

    private TextView tvWidgetLocationName, tvWidgetDescription, tvWidgetTemperature, tvWidgetDetails,
            tvWidgetWind, tvWidgetVisibility;
    private Button btnUpdateWeather;
    private RecyclerView rvSensorReadings;

    private double latitude;
    private double longitude;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // Инициализация виджетов
        tvWidgetLocationName = findViewById(R.id.tvWidgetLocationName);
        tvWidgetDescription = findViewById(R.id.tvWidgetDescription);
        tvWidgetTemperature = findViewById(R.id.tvWidgetTemperature);
        tvWidgetDetails = findViewById(R.id.tvWidgetDetails);
        tvWidgetVisibility = findViewById(R.id.tvWidgetVisibility);
        tvWidgetWind = findViewById(R.id.tvWidgetWind);
        btnUpdateWeather = findViewById(R.id.btnUpdateWeather);

        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);

        rvSensorReadings = findViewById(R.id.rvSensorReadings);
        rvSensorReadings.setLayoutManager(new LinearLayoutManager(this));

        // Добавление тени к тексту
        addShadow(tvWidgetLocationName);
        addShadow(tvWidgetDescription);
        addShadow(tvWidgetTemperature);
        addShadow(tvWidgetDetails);
        addShadow(tvWidgetWind);
        addShadow(tvWidgetVisibility);

        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        getWeatherData();
        getSensorsData();

        btnUpdateWeather.setOnClickListener((v) -> {
            getWeatherData();
            getSensorsData();
        });




    }

    private void getSensorsData() {
        apiService.getSensorsByUserId(sharedPreferences.getLong("USER_ID", 0),
                sharedPreferences.getString("TOKEN", "")).enqueue(new Callback<List<Sensor>>() {
            @Override
            public void onResponse(Call<List<Sensor>> call, Response<List<Sensor>> response) {
                if (response.isSuccessful()) {
                    rvSensorReadings.setAdapter(new SensorReadingAdapter(response.body().stream()
                            .peek(s -> s.setLabel("Значение")).collect(Collectors.toList())));
                } else {
                    Toast.makeText(WeatherActivity.this, "Не удалось получить данные о датчиках", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Sensor>> call, Throwable t) {
                Toast.makeText(WeatherActivity.this, "Не удалось получить данные о датчиках", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getWeatherData() {
        // Проверка разрешений
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        } else {
            fetchLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void fetchLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = location -> {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            fetchWeatherData();
        };

        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);
    }

    private void fetchWeatherData() {
        WeatherRequest request = new WeatherRequest(String.valueOf(latitude), String.valueOf(longitude), "json");

        String token = sharedPreferences.getString("TOKEN", "");
        Call<WeatherResponse> call = apiService.getWeather(request, token);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weather = response.body();

                    // Обновление UI
                    updateWeatherWidget(weather);

                } else {
                    Toast.makeText(WeatherActivity.this, "Не удалось получить данные о погоде", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(WeatherActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateWeatherWidget(WeatherResponse weather) {
        setTextVars(tvWidgetLocationName, LOCATION_NAME_TEMPLATE, weather.getLocationName());
        setTextVars(tvWidgetDescription, DESCRIPTION_TEMPLATE, weather.getDescription());
        setTextVars(tvWidgetTemperature, TEMPERATURE_TEMPLATE, weather.getTemperature(), weather.getFeelsLike());
        setTextVars(tvWidgetDetails, DETAILS_TEMPLATE, weather.getPressure(), weather.getHumidity());
        setTextVars(tvWidgetVisibility, VISIBILITY_TEMPLATE, weather.getVisibility());
        setTextVars(tvWidgetWind, WIND_TEMPLATE, weather.getWindSpeed());
    }

    private void addShadow(TextView textView) {
        textView.setShadowLayer(1.5f, 2.0f, 2.0f, Color.BLACK);
    }

    private void setTextVars(TextView textView, String template, Object... vars) {
        textView.setText(String.format(template, vars));
    }
}
