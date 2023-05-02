package com.example.crosstalk.services;

import com.example.crosstalk.fragments.homeActivity.NotificationsFragment;
import com.example.crosstalk.models.CompanyServiceModel;
import com.example.crosstalk.models.ErrorHandlingModel;
import com.example.crosstalk.models.NotificationServiceModel;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationServices {

    public String authorizationHeader;
    NotificationsFragment notificationsFragment;
    ApiService.NotificationServiceInterface notificationService;


    public NotificationServices(ApiService.NotificationServiceInterface notificationService, String authorizationHeader, NotificationsFragment notificationsFragment) {
        this.authorizationHeader = authorizationHeader;
        this.notificationService = notificationService;
        this.notificationsFragment = notificationsFragment;
    }

    public void getNotifications(){
        Call<NotificationServiceModel.GetNotificationsResponse> call;
        call = notificationService.getMyNotifications(authorizationHeader);
        call.enqueue(new Callback<NotificationServiceModel.GetNotificationsResponse>() {
            @Override
            public void onResponse(Call<NotificationServiceModel.GetNotificationsResponse> call, Response<NotificationServiceModel.GetNotificationsResponse> response) {
                if (response.isSuccessful()) {
                    List<NotificationServiceModel.NotificationModel> notifications = response.body().getData();
                    notificationsFragment.updateUi(notifications);
                } else {
                    try {
                        Gson gsonRef = new Gson();
                        ErrorHandlingModel error;
                        error = gsonRef.fromJson(response.errorBody().charStream(), ErrorHandlingModel.class);
                        String message = error.getMessage();
                        notificationsFragment.showError(message);
                    } catch (Error error) {
                        notificationsFragment.showError(error.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationServiceModel.GetNotificationsResponse> call, Throwable t) {
                notificationsFragment.showError(t.getMessage());
            }
        });
    }
}
