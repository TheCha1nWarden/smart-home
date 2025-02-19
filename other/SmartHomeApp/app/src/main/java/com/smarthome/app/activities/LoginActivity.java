package com.smarthome.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.smarthome.app.R;
import com.smarthome.app.model.EmailRequestFactory;
import com.smarthome.app.model.LoginRequest;
import com.smarthome.app.model.LoginResponse;
import com.smarthome.app.model.RegistrationRequest;
import com.smarthome.app.model.RegistrationResponse;
import com.smarthome.app.network.ApiClient;
import com.smarthome.app.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String SHARED_PREFS_NAME = "UserData";
    private static final String KEY_REMEMBER_ME = "RememberMe";

    private SharedPreferences sharedPreferences;

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;

    private CheckBox cbRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        boolean rememberMe = sharedPreferences.getBoolean(KEY_REMEMBER_ME, false);
        String token = sharedPreferences.getString("TOKEN", null);
        long userId = sharedPreferences.getLong("USER_ID", 0);

        if (rememberMe && token != null && userId != 0) {
            Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        cbRememberMe = findViewById(R.id.checkboxRememberMe);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                LoginRequest loginRequest = new LoginRequest(username, password);
                Call<LoginResponse> call = apiService.login(loginRequest);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            String token = response.body().getToken();
                            Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                            // Переход на главное меню
                            Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("TOKEN", token);
                            editor.putLong("USER_ID", response.body().getUserId());
                            editor.putBoolean(KEY_REMEMBER_ME, cbRememberMe.isChecked());
                            editor.apply();

                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.e("LoginActivity", "Error: " + t.getMessage());
                        Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Введите свою почту");


                final android.widget.EditText input = new android.widget.EditText(LoginActivity.this);
                builder.setView(input);

                // Кнопки диалога
                builder.setPositiveButton("Регистрация", (dialog, which) -> {
                    String email = input.getText().toString().trim();
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();
                    if (!email.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                        apiService.registration(new RegistrationRequest(username, password, email, "USER"))
                                .enqueue(new Callback<RegistrationResponse>() {
                                    @Override
                                    public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                                        if (response.isSuccessful()) {
                                            apiService.sendEmail(EmailRequestFactory.create(email, username),
                                                    response.body().getToken()).enqueue(new Callback<>() {
                                                @Override
                                                public void onResponse(Call<Object> call, Response<Object> response) {
                                                }

                                                @Override
                                                public void onFailure(Call<Object> call, Throwable t) {
                                                }
                                            });
                                            Toast.makeText(LoginActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                            // Переход на главное меню
                                            Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);

                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("TOKEN", response.body().getToken());
                                            editor.putLong("USER_ID", response.body().getUserId());                            editor.putBoolean(KEY_REMEMBER_ME, cbRememberMe.isChecked());
                                            editor.putBoolean(KEY_REMEMBER_ME, cbRememberMe.isChecked());
                                            editor.apply();

                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                                        Toast.makeText(LoginActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(LoginActivity.this, "email|username|password не могут быть пустыми", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());
                builder.show();
            }
        });
    }
}
