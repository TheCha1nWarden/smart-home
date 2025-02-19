package com.smarthome.app.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smarthome.app.R;
import com.smarthome.app.adapters.DeviceAdapter;
import com.smarthome.app.model.Device;
import com.smarthome.app.model.Status;
import com.smarthome.app.network.ApiClient;
import com.smarthome.app.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DevicesActivity extends AppCompatActivity {

    private static final String SHARED_PREFS_NAME = "UserData";
    private SharedPreferences sharedPreferences;

    private RecyclerView rvDevices;
    private DeviceAdapter deviceAdapter;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        rvDevices = findViewById(R.id.rvDevices);
        rvDevices.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);

        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        getDevicesData();

    }

    private void getDevicesData() {
        apiService.getDevicesByUserId(sharedPreferences.getLong("USER_ID", 0),
                sharedPreferences.getString("TOKEN", "")).enqueue(new Callback<List<Device>>() {
            @Override
            public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                if (response.isSuccessful()) {
                    // Настройка адаптера
                    deviceAdapter = new DeviceAdapter(response.body(), (deviceId, isOn) -> {
                        toggleDeviceState(deviceId, isOn);
                    });
                    rvDevices.setAdapter(deviceAdapter);
                } else {
                    Toast.makeText(DevicesActivity.this, "Не удалось получить список устройств", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Device>> call, Throwable t) {
                Toast.makeText(DevicesActivity.this, "Не удалось получить список устройств", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleDeviceState(Long deviceId, boolean isOn) {
        apiService.updateDeviceStatus(deviceId, isOn ? Status.ON : Status.OFF,
                sharedPreferences.getString("TOKEN", "")).enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                if (response.isSuccessful()) {
                    String state = isOn ? "включено" : "выключено";
                    Toast.makeText(DevicesActivity.this, "Устройство " + response.body().getName() + " теперь " + state, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DevicesActivity.this, "Не удалось  обновить статус устройства", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Device> call, Throwable t) {
                Toast.makeText(DevicesActivity.this, "Не удалось  обновить статус устройства", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
