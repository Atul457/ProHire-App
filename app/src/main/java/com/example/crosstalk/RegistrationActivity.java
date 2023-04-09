package com.example.crosstalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.crosstalk.models.UserServiceModel;
import com.example.crosstalk.models.ErrorHandlingModel;
import com.example.crosstalk.services.ApiService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    // Vars

    TextView email;
    View signInText;
    TextView password;
    TextView userName;
    Button submitButton;
    TextView responseRef;
    TextView phoneNumber;
    TextView emailInfoRef;
    Button submitButtonRef;
    TextView userNameInfoRef;
    TextView passwordInfoRef;
    TextView phoneNumberInfoRef;
    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        checkAlreadyLoggedIn();

        // Initializations
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        userName = findViewById(R.id.userName);
        signInText = findViewById(R.id.signInText);
        emailInfoRef = findViewById(R.id.emailInfo);
        phoneNumber = findViewById(R.id.phoneNumber);
        responseRef = findViewById(R.id.responseText);
        submitButton = findViewById(R.id.submitButton);
        userNameInfoRef = findViewById(R.id.userNameInfo);
        passwordInfoRef = findViewById(R.id.passwordInfo);
        phoneNumberInfoRef = findViewById(R.id.phoneNumberInfo);

        submitButtonRef = submitButton;
        sharedPref = getPreferences(MODE_PRIVATE);

        // Removing header
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        // Listeners
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                responseRef.setText("");
                emailInfoRef.setText("");
                passwordInfoRef.setText("");
                userNameInfoRef.setText("");
                phoneNumberInfoRef.setText("");


                // Min and Max length
                int minMaxLength = 6;
                int phoneNumberMinMaxLength = 10;
                int userNameMinLength = 3;


                // Validations
                if (userName.getText().toString().isEmpty()) {
                    userNameInfoRef.setText(R.string.userNameRequired);
                    return;
                }

                if (userName.getText().toString().length() < userNameMinLength) {
                    userNameInfoRef.setText("username's minimum length must be at least " + userNameMinLength);
                    return;
                }

                if (email.getText().toString().isEmpty()) {
                    emailInfoRef.setText(R.string.emailRequired);
                    return;
                }

                if (!email.getText().toString().contains("@") || !email.getText().toString().contains(".com")) {
                    emailInfoRef.setText(R.string.emailInvalid);
                    return;
                }

                if (phoneNumber.getText().toString().isEmpty()) {
                    phoneNumberInfoRef.setText(R.string.phoneNumberRequired);
                    return;
                }

                if (phoneNumber.getText().toString().length() != phoneNumberMinMaxLength) {
                    phoneNumberInfoRef.setText("phone number's length must be equal to " + phoneNumberMinMaxLength);
                    return;
                }

                if (password.getText().toString().isEmpty()) {
                    passwordInfoRef.setText(R.string.passwordRequired);
                    return;
                }

                if (password.getText().toString().length() != minMaxLength) {
                    passwordInfoRef.setText("password's length must be equal to " + minMaxLength);
                    return;
                }

                responseRef.setText("");
                emailInfoRef.setText("");
                passwordInfoRef.setText("");
                userNameInfoRef.setText("");

                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();
                String userNameValue = userName.getText().toString();
                String phoneNumberValue = phoneNumber.getText().toString();

                /* Api request */

                submitButton.setText(R.string.loading);

                UserServiceModel.RegisterRequestModel registerRequest;
                Call<UserServiceModel.ResponseModel> registerCall;

                registerRequest = new UserServiceModel.RegisterRequestModel(userNameValue, phoneNumberValue, passwordValue, emailValue);
                registerCall = ApiService.getUserService().register(registerRequest);

                registerCall.enqueue(new Callback<UserServiceModel.ResponseModel>() {
                    @Override
                    public void onResponse(Call<UserServiceModel.ResponseModel> call, Response<UserServiceModel.ResponseModel> response) {

                        if (response.isSuccessful()) {
                            loginUser(response.body().getData());
                        } else {

                            try {

                                ErrorHandlingModel error = (new Gson())
                                        .fromJson(response.errorBody().charStream(), ErrorHandlingModel.class);

                                String message = error.getMessage();
                                submitButtonRef.setText(R.string.signUpButtonText);
                                responseRef.setText(message);

                            } catch (Error error) {
                                Log.e("error", error.toString());
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<UserServiceModel.ResponseModel> call, Throwable t) {
                        Log.e("error", t.getMessage());
                    }
                });

                /* Api request */

            }
        });

        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // Checks the user is already logged in or not,
    // if the user is already this navigates the user to home activity
    public void checkAlreadyLoggedIn() {

        if (sharedPref == null)
            sharedPref = getPreferences(MODE_PRIVATE);

        Boolean isUserLoggedIn = sharedPref.getBoolean("userLoggedIn", false);

        if (isUserLoggedIn) {
            Intent i = new Intent(RegistrationActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }

    // Logs the user in by saving its data to app storage
    public void loginUser(UserServiceModel.ResponseModel.ResponseDataModel data) {

        String token;
        SharedPreferences.Editor editor;

        if (sharedPref == null) sharedPref = getPreferences(MODE_PRIVATE);
        token = data.getToken();

        /* Saving user details to app storage */
        editor = sharedPref.edit();
        editor.putString("userToken", token);
        editor.putBoolean("userLoggedIn", true);
        editor.putString("userEmail", data.getEmail());
        editor.putString("userName", data.getName());
        editor.apply();

        Intent i = new Intent(RegistrationActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }


}