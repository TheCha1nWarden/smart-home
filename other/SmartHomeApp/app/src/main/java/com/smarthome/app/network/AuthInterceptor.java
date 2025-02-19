package com.smarthome.app.network;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.smarthome.app.activities.LoginActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private static final String SHARED_PREFS_NAME = "UserData";
    private static final String KEY_REMEMBER_ME = "RememberMe";

    private SharedPreferences sharedPreferences;

    private static final String TAG = "AuthInterceptor";
    private final Context context;

    public AuthInterceptor(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        // Выполняем запрос
        Response response = chain.proceed(request);

        // Проверяем статус ответа
        if (response.code() == 403) {
            Log.e(TAG, "Получен статус 403. Перенаправление на LoginActivity.");

            sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_REMEMBER_ME, false);
            editor.apply();

            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(context, "Сессия закончена, повторите вход!", Toast.LENGTH_SHORT).show();
            });

            // Перенаправляем пользователя на LoginActivity
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }

        return response;
    }
}

