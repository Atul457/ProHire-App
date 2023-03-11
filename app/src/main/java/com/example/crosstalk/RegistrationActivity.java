package com.example.crosstalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrationActivity extends AppCompatActivity {

    // Vars

    Button submitButton;
    View signInText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initializations
        submitButton =  findViewById(R.id.submitButton);
        signInText = findViewById(R.id.signInText);

        // Removing header
        if(getSupportActionBar() != null) getSupportActionBar().hide();

        // Listeners
        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}