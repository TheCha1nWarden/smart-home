package com.smarthome.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.smarthome.app.R;

public class MainMenuActivity extends AppCompatActivity {

    private static final String SHARED_PREFS_NAME = "UserData";
    private static final String KEY_REMEMBER_ME = "RememberMe";

    private SharedPreferences sharedPreferences;

    private Button btnDeviceService;
    private Button btnIntegrationService;

    private Button btnCamera;

    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);

        btnDeviceService = findViewById(R.id.btnDeviceService);
        btnIntegrationService = findViewById(R.id.btnIntegrationService);
        btnCamera = findViewById(R.id.btnCamera);
        btnBack = findViewById(R.id.btnBack);

        // Обработчик нажатия для перехода на экран Device Service
        btnDeviceService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, DevicesActivity.class);
                startActivity(intent);
            }
        });

        // Обработчик нажатия для перехода на экран Integration Service
        btnIntegrationService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, WeatherActivity.class);
                startActivity(intent);
            }
        });

        // Обработчик нажатия для перехода на экран с камерой
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(KEY_REMEMBER_ME, false);
                editor.apply();
                Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
