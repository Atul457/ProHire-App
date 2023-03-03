package com.example.crosstalk;

// Imports
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    // Vars
    TextView login_username;
    TextView login_password;
    TextView signupText;

    TextView loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(getSupportActionBar() != null) getSupportActionBar().hide();

        login_password = findViewById(R.id.login_password);
        login_username = findViewById(R.id.login_username);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Min and Max length
                int minMaxLength = 8;

                // Validations

                if (login_username.getText().toString().isEmpty()) {
                    login_username.setError(("username is required"));
                    return;
                }

                if (login_password.getText().toString().isEmpty()) {
                    login_password.setError("password is required");
                    return;
                }

                if (login_username.getText().toString().length() <= minMaxLength) {
                    login_username.setError("username length should be greater than 8");
                    return;
                }

                if (login_password.getText().toString().length() <= minMaxLength) {
                    login_password.setError("password length must be greater than 8");
                    return;
                }

                if (!login_username.getText().toString().contains("@") || !login_username.getText().toString().contains(".com")) {
                    login_username.setError("Incorrect username");
                    return;
                }

                Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            }
        });

        signupText = findViewById(R.id.signupText);
        signupText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}