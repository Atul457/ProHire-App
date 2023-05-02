package com.example.crosstalk.services;

import com.example.crosstalk.fragments.homeActivity.ProfileFragment;
import com.example.crosstalk.models.ErrorHandlingModel;
import com.example.crosstalk.models.UserServiceModel;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserServices {

    String authorizationHeader;
    ProfileFragment profileFragment;
    ApiService.UserServiceInterface userService;

    public UserServices(String authorizationHeader, ApiService.UserServiceInterface userService, ProfileFragment profileFragment) {
        this.userService = userService;
        this.profileFragment = profileFragment;
        this.authorizationHeader = authorizationHeader;
    }

    public void getMyProfile() {
        Call<UserServiceModel.GetProfileResponseModel> call;
        call = userService.getMyProfile(authorizationHeader);

        call.enqueue(new Callback<UserServiceModel.GetProfileResponseModel>() {
            @Override
            public void onResponse(Call<UserServiceModel.GetProfileResponseModel> call, Response<UserServiceModel.GetProfileResponseModel> response) {
                if (response.isSuccessful()) {
                    profileFragment.updateUi(response.body().getData());
                } else {
                    try {
                        Gson gsonRef = new Gson();
                        ErrorHandlingModel error;
                        error = gsonRef.fromJson(response.errorBody().charStream(), ErrorHandlingModel.class);
                        String message = error.getMessage();
                        profileFragment.showError(message);
                    } catch (Error error) {
                        profileFragment.showError(error.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserServiceModel.GetProfileResponseModel> call, Throwable t) {
                profileFragment.showError(t.getMessage());
            }
        });

    }

}
