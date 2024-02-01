package com.example.cursv;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    Handler handler;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = SplashActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        handler = new Handler();
        handler.postDelayed(() -> {
            int humanId = Preference.getIntIdHuman("humanId", SplashActivity.this);
            int petId = Preference.getIntIdHuman("idPet1", SplashActivity.this);
            Intent intent;
            if(petId != 0 && humanId != 0){
                intent = new Intent(context, HomeActivity.class);
            }else{
                intent = new Intent(context, MainActivity.class);
            }
            startActivity(intent);
            finish();
        }, 2000);
    }
}