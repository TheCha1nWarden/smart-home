package com.smarthome.app.network;

import com.smarthome.app.model.Device;
import com.smarthome.app.model.EmailRequest;
import com.smarthome.app.model.LoginRequest;
import com.smarthome.app.model.LoginResponse;
import com.smarthome.app.model.RegistrationRequest;
import com.smarthome.app.model.RegistrationResponse;
import com.smarthome.app.model.Sensor;
import com.smarthome.app.model.Status;
import com.smarthome.app.model.WeatherRequest;
import com.smarthome.app.model.WeatherResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("/api/weather/get")
    Call<WeatherResponse> getWeather(@Body WeatherRequest weatherRequest,
                                     @Header("Authorization") String token);

    @GET("/api/sensors/getAllByUser/{id}")
    Call<List<Sensor>> getSensorsByUserId(@Path("id") Long id,
                                          @Header("Authorization") String token);

    @GET("/api/devices/getAllByUser/{id}")
    Call<List<Device>> getDevicesByUserId(@Path("id") Long id,
                                          @Header("Authorization") String token);

    @PUT("/api/devices/updateStatus/{id}")
    Call<Device> updateDeviceStatus(@Path("id") Long id,
                                    @Body Status status,
                                    @Header("Authorization") String token);

    @POST("/api/auth/register")
    Call<RegistrationResponse> registration(@Body RegistrationRequest registrationRequest);

    @POST("/api/email/send")
    Call<Object> sendEmail(@Body EmailRequest emailRequest, @Header("Authorization") String token);
}
