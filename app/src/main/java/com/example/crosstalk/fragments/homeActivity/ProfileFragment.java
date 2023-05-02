package com.example.crosstalk.fragments.homeActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.crosstalk.LoginActivity;
import com.example.crosstalk.R;
import com.example.crosstalk.RegistrationActivity;
import com.example.crosstalk.models.UserServiceModel;
import com.example.crosstalk.services.ApiService;
import com.example.crosstalk.services.UserServices;
import com.example.crosstalk.utils.Constants;


public class ProfileFragment extends Fragment {

    TextView wish;
    EditText email;
    View signInText;
    Boolean fetched;
    View progressBar;
    EditText userName;
    Button submitButton;
    EditText phoneNumber;
    TextView emailInfoRef;
    Button submitButtonRef;
    TextView userNameInfoRef;
    String authorizationHeader;
    TextView createdProfilePic;
    UserServices userServices;
    TextView phoneNumberInfoRef;
    SharedPreferences sharedPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Declarations
        View view;
        String email_;
        String userId_;
        String userName_;
        String userPhoneNumber_;
        ImageView logoutButton;
        Boolean fetchUserDetails;
        UserServiceModel.UserModel userDetails;
        ApiService.UserServiceInterface userService;

        // Initializations
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        wish = view.findViewById(R.id.wish);
        email = view.findViewById(R.id.email);
        userName = view.findViewById(R.id.userName);
        progressBar = view.findViewById(R.id.progressBar);
        phoneNumber = view.findViewById(R.id.phoneNumber);
        logoutButton = view.findViewById(R.id.logoutButton);
        createdProfilePic = view.findViewById(R.id.createdProfilePic);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        userId_ = sharedPref.getString(Constants.USER_ID, null);
        email_ = sharedPref.getString(Constants.USER_EMAIL, null);
        userName_ = sharedPref.getString(Constants.USER_NAME, null);
        userPhoneNumber_ = sharedPref.getString(Constants.USER_PHONE_NUMBER, null);
        authorizationHeader = sharedPref.getString(Constants.AUTHORIZATION_HEADER, "");
        fetchUserDetails = email_ == null || userName_ == null || userPhoneNumber_ == null || userId_ == null;

        userService = ApiService.getUserService();
        userServices = new UserServices(authorizationHeader, userService, this);


        if (fetchUserDetails)
            getProfileDetails();
        else {
            userDetails = new UserServiceModel.UserModel(userId_, userName_, email_, userPhoneNumber_);
            updateUi(userDetails);
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return view;
    }

    public void logout() {
        SharedPreferences.Editor editor;
        if (sharedPref == null)
            sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sharedPref.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().finish();
        startActivity(intent);
    }

    /**
     * @TODO Document this
     */
    public void getProfileDetails() {
        fetched = true;
        progressBar.setVisibility(View.VISIBLE);
        userServices.getMyProfile();
    }

    public void updateUi(UserServiceModel.UserModel userDetails) {

        SharedPreferences.Editor editor;
        String createdUserImageText = "";
        String nameOfLoggedInUser = userDetails.getName();
        String[] nameSegments = nameOfLoggedInUser.split(" ");

        wish.setText("Hey " + nameOfLoggedInUser);
        email.setText(userDetails.getEmail());
        userName.setText(nameOfLoggedInUser);
        phoneNumber.setText(userDetails.getPhone());

        if (nameSegments.length < 2) {
            createdUserImageText = nameOfLoggedInUser.toUpperCase().substring(0, 2);
        } else {
            for (int i = 0; i < nameSegments.length; i++) {
                createdUserImageText = createdUserImageText.concat(nameSegments[i].toUpperCase().substring(0, 1));
            }
        }

        createdProfilePic.setText(createdUserImageText);
        progressBar.setVisibility(View.GONE);

        if (sharedPref == null)
            sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        editor = sharedPref.edit();
        editor.putString(Constants.USER_ID, userDetails.get_id());
        editor.putString(Constants.USER_NAME, userDetails.getName());
        editor.putString(Constants.USER_EMAIL, userDetails.getEmail());
        editor.putString(Constants.USER_PHONE_NUMBER, userDetails.getPhone());
        editor.apply();

    }

    public static String toCamelCase(String str) {
        String[] parts = str.split("\\s|_");
        StringBuilder camelCaseString = new StringBuilder();
        for (String part : parts) {
            if (part.length() > 0) {
                camelCaseString.append(part.substring(0, 1).toUpperCase());
                if (part.length() > 1) {
                    camelCaseString.append(part.substring(1).toLowerCase());
                }
            }
        }
        return camelCaseString.toString();
    }


    public void showError(String error) {
        Log.e(Constants.RESPONSE_LOG_STR, error);
    }

}