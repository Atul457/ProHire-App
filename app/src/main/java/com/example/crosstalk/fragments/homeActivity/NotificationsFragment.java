package com.example.crosstalk.fragments.homeActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crosstalk.R;
import com.example.crosstalk.adapters.HomeActivityRecyclerAdapter;
import com.example.crosstalk.adapters.NotificationsRecyclerAdapter;
import com.example.crosstalk.models.JobServiceModel;
import com.example.crosstalk.models.NotificationServiceModel;
import com.example.crosstalk.services.ApiService;
import com.example.crosstalk.services.HomepageService;
import com.example.crosstalk.services.NotificationServices;
import com.example.crosstalk.utils.Constants;

import java.util.List;


public class NotificationsFragment extends Fragment {

    View progressBar;
    String authorizationHeader;
    SharedPreferences sharedPref;
    View resultsNotFoundContainer;
    FragmentManager fragmentManager;
    NotificationServices notificationServices;
    NotificationsFragment notificationsFragment;
    NotificationsRecyclerAdapter notificationsRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Declarations
        View view;
        RecyclerView recyclerView;
        ApiService.NotificationServiceInterface notificationService;

        // Initializations
        view = inflater.inflate(R.layout.fragment_notification, container, false);

        notificationsFragment = this;
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.notificationsList);
        resultsNotFoundContainer = view.findViewById(R.id.resultsNotFoundContainer);

        notificationsRecyclerAdapter = new NotificationsRecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        recyclerView.setAdapter(notificationsRecyclerAdapter);

        notificationService = ApiService.getNotificationService();

        authorizationHeader = sharedPref.getString(Constants.AUTHORIZATION_HEADER, "");
        notificationServices = new NotificationServices(notificationService, authorizationHeader, this);

        progressBar.setVisibility(View.VISIBLE);
        resultsNotFoundContainer.setVisibility(View.INVISIBLE);

        notificationServices.getNotifications();

        return view;
    }

    public void updateUi(List<NotificationServiceModel.NotificationModel> notifications) {
        if (notifications.size() == 0)
            resultsNotFoundContainer.setVisibility(View.VISIBLE);
        else
            resultsNotFoundContainer.setVisibility(View.GONE);

        notificationsRecyclerAdapter.setNotifications(notifications, fragmentManager);
        progressBar.setVisibility(View.GONE);
    }

    public void showError(String error) {
        Log.w("error", error);
    }
}