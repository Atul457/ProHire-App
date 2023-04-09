package com.example.crosstalk;

// Imports

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crosstalk.models.UserServiceModel;
import com.example.crosstalk.models.ErrorHandlingModel;
import com.example.crosstalk.services.ApiService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    // Vars
    Button submitButton;
    TextView signupText;
    ImageView si_google;
    TextView responseRef;
    TextView emailInfoRef;
    ImageView si_facebook;
    Button submitButtonRef;
    TextView login_username;
    TextView login_password;
    TextView passwordInfoRef;
    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkAlreadyLoggedIn();

        // Initializations
        si_google = findViewById(R.id.si_google);
        signupText = findViewById(R.id.signupText);
        emailInfoRef = findViewById(R.id.emailInfo);
        si_facebook = findViewById(R.id.si_facebook);
        responseRef = findViewById(R.id.responseText);
        submitButton = findViewById(R.id.submitButton);
        passwordInfoRef = findViewById(R.id.passwordInfo);
        login_password = findViewById(R.id.login_password);
        login_username = findViewById(R.id.login_username);

        submitButtonRef = submitButton;
        sharedPref = getPreferences(MODE_PRIVATE);

        // Removing header
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        // Listeners
        si_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //login to facebook

            }
        });


        /* @info Handles submit button click */
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                responseRef.setText("");
                emailInfoRef.setText("");
                passwordInfoRef.setText("");

                // Min and Max length
                int minMaxLength = 6;

                // Validations

                if (login_username.getText().toString().isEmpty()) {
                    emailInfoRef.setText(R.string.emailRequired);
                    return;
                }

                if (!login_username.getText().toString().contains("@") || !login_username.getText().toString().contains(".com")) {
                    emailInfoRef.setText(R.string.emailInvalid);
                    return;
                }

                if (login_password.getText().toString().isEmpty()) {
                    passwordInfoRef.setText(R.string.passwordRequired);
                    return;
                }

                if (login_password.getText().toString().length() != minMaxLength) {
                    passwordInfoRef.setText("password's length must be equal to " + minMaxLength);
                    return;
                }

                responseRef.setText("");
                emailInfoRef.setText("");
                passwordInfoRef.setText("");

                String email = login_username.getText().toString();
                String password = login_password.getText().toString();

                /* Login Request */

                submitButtonRef.setText(R.string.loading);

                Call<UserServiceModel.ResponseModel> loginCall;
                UserServiceModel.LoginRequestModel loginRequest;

                loginRequest = new UserServiceModel.LoginRequestModel(email, password);
                loginCall = ApiService.getUserService().login(loginRequest);

                loginCall.enqueue(new Callback<UserServiceModel.ResponseModel>() {
                    @Override
                    public void onResponse(Call<UserServiceModel.ResponseModel> call, Response<UserServiceModel.ResponseModel> response) {

                        if (response.isSuccessful()) {
                            loginUser(response.body().getData());
                        } else {
                            try {
                                ErrorHandlingModel error = (new Gson())
                                        .fromJson(response.errorBody().charStream(), ErrorHandlingModel.class);

                                String message = error.getMessage();
                                submitButtonRef.setText(R.string.signInButtonText);
                                responseRef.setText(message);

                            } catch (Error error) {
                                Log.e("error", error.toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserServiceModel.ResponseModel> call, Throwable t) {
                        String res = t.getMessage();
                        Log.e("res", res);
                    }
                });

                /* Login Request */

            }
        });


        /* @info Navigates the user to Registration activity */
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // Checks the user is already logged in or not,
    // if the user is already this navigates the user to home activity
    public void checkAlreadyLoggedIn() {

        if (sharedPref == null) sharedPref = getPreferences(MODE_PRIVATE);

        Boolean isUserLoggedIn = sharedPref.getBoolean("userLoggedIn", false);

        if (isUserLoggedIn) {
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }

    // Logs the user in by saving its data to app storage
    public void loginUser(UserServiceModel.ResponseModel.ResponseDataModel data) {

        String token;
        SharedPreferences.Editor editor;

        if (sharedPref == null) sharedPref = getPreferences(MODE_PRIVATE);

        editor = sharedPref.edit();
        token = data.getToken();

        /* Saving user details to app storage */
        editor.putString("userToken", token);
        editor.putBoolean("userLoggedIn", true);
        editor.putString("userEmail", data.getEmail());
        editor.putString("userName", data.getName());
        editor.apply();

        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }

}