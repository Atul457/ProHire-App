package com.example.crosstalk;

// Imports
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    // Vars
    TextView login_username;
    TextView login_password;
    TextView signupText;
    Button submitButton;
    ImageView si_google;
    ImageView si_facebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initializations
        login_password = findViewById(R.id.login_password);
        login_username = findViewById(R.id.login_username);
        submitButton = findViewById(R.id.submitButton);
        si_google = findViewById(R.id.si_google);
        si_facebook = findViewById(R.id.si_facebook);
        signupText = findViewById(R.id.signupText);

        // Removing header
        if(getSupportActionBar() != null) getSupportActionBar().hide();

        // Listeners
        si_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //login to facebook

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
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

        signupText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}