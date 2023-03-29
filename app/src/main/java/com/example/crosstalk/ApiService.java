package com.example.crosstalk;

import com.example.crosstalk.models.UserServiceModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class ApiService {

    public static final String baseUrl = "https://tan-sleepy-goose.cyclic.app/";

    public static UserServiceInterface userService = null;
    public static Retrofit retrofitInstance = null;


    /**
     * @return Retrofit instance
     */
    public static Retrofit getRetrofit() {
        if (ApiService.retrofitInstance == null) {
            Retrofit retrofit = new Retrofit
                    .Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            retrofitInstance = retrofit;
            return retrofitInstance;
        }

        return retrofitInstance;
    }


    /**
     * @returns Instance of user service interface
     */
    public static UserServiceInterface getUserService() {

        // Create post service
        if (ApiService.userService == null) {
            userService = getRetrofit().create(ApiService.UserServiceInterface.class);
            return userService;
        }

        return userService;

    }

    /** @info User service interface */
    public interface UserServiceInterface {

        @POST("/user/signIn")
        Call<UserServiceModel.ResponseModel> login(@Body UserServiceModel.LoginRequestModel loginRequest);

        @POST("/user/signUp")
        Call<UserServiceModel.ResponseModel> register(@Body UserServiceModel.RegisterRequestModel registerRequest);

    }

}
