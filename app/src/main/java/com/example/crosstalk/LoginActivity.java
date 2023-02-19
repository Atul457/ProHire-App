package com.example.crosstalk;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView login_username;
    TextView login_password;
    TextView signupText;

    TextView loginButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        login_password = findViewById(R.id.login_password);
        login_username = findViewById(R.id.login_username);
        loginButton = findViewById(R.id.loginButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login_username.getText().toString().isEmpty())
                {
                    login_username.setError(("please enter your user name "));
                }
                else if (login_password.getText().toString().isEmpty())
                {
                    login_password.setError("please enter your user name ");
                }
                else if (login_username.getText().toString().length() <= 8)
                {
                    login_username.setError("username must be gerten than 8 ");
                }
                else if (login_password.getText().toString().length() <= 8)
                {
                    login_password.setError("password must be gerten than 8 ");
                }
                else if  (!login_username.getText().toString().contains("@") || !login_username.getText().toString().contains(".com"))
                {
                   login_username.setError("Incorrct username");
                }  else
                {
                    Toast.makeText(LoginActivity.this,"login successfully done",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        });

        signupText = findViewById(R.id.signupText);
        signupText.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}