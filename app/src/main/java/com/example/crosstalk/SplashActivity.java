
package com.example.crosstalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;

import es.dmoral.toasty.Toasty;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Toasty.Config
                .getInstance()
                .setGravity(Gravity.TOP | Gravity.END, 25, 35)
                .apply();

        // Added new comment

        getSupportActionBar().hide();
        Handler h1 = new Handler();

        h1.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        }, 3000);

    }
}