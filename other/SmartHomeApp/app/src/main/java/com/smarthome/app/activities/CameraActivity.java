package com.smarthome.app.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.smarthome.app.R;

public class CameraActivity extends AppCompatActivity {

    private static final String SHARED_PREFS_NAME = "UserData";
    private static final String CAMERA_URL_KEY = "CameraUrl";

    private WebView webView;
    private String cameraUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        webView = findViewById(R.id.webViewCamera);
        Button btnSetCameraUrl = findViewById(R.id.btnSetCameraUrl);

        // Загружаем сохранённую ссылку на камеру
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        cameraUrl = sharedPreferences.getString(CAMERA_URL_KEY, null);

        // Настраиваем WebView
        setupWebView();

        // Если есть сохранённая ссылка, загружаем её
        if (cameraUrl != null) {
            loadCameraUrl(cameraUrl);
        }

        // Обработка нажатия на кнопку настройки IP-камеры
        btnSetCameraUrl.setOnClickListener(v -> showSetCameraUrlDialog());
    }

    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Включаем JavaScript
        webSettings.setDomStorageEnabled(true); // Включаем DOM Storage
        webSettings.setLoadWithOverviewMode(true); // Подгоняем страницу под размер экрана
        webSettings.setUseWideViewPort(true);

        // Устанавливаем WebViewClient, чтобы загружать страницы внутри WebView
        webView.setWebViewClient(new WebViewClient());

        // Поддержка элементов мультимедиа (например, полноэкранного режима)
        webView.setWebChromeClient(new WebChromeClient());
    }

    private void loadCameraUrl(String url) {
        try {
            webView.loadUrl(url);
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка загрузки камеры", Toast.LENGTH_SHORT).show();
        }
    }

    private void showSetCameraUrlDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Настройка IP-камеры");

        // Поле ввода для URL камеры
        final android.widget.EditText input = new android.widget.EditText(this);
        input.setHint("Введите URL камеры");
        if (cameraUrl != null) {
            input.setText(cameraUrl);
        }
        builder.setView(input);

        // Кнопки диалога
        builder.setPositiveButton("Сохранить", (dialog, which) -> {
            String url = input.getText().toString().trim();
            if (!url.isEmpty()) {
                saveCameraUrl(url);
                loadCameraUrl(url);
            } else {
                Toast.makeText(this, "URL не может быть пустым", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void saveCameraUrl(String url) {
        cameraUrl = url;
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CAMERA_URL_KEY, url);
        editor.apply();
    }
}
