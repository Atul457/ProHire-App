package com.example.crosstalk.fragments.companyActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crosstalk.R;
import com.example.crosstalk.models.CompanyServiceModel;
import com.example.crosstalk.services.ApiService;
import com.example.crosstalk.services.CompaniesService;
import com.example.crosstalk.utils.Constants;

/**
 * @info This fragment is for creating a company
 */
public class CreateCompanyFragment extends Fragment {

    // Vars
    String companyName;
    View errorToastLayout;
    String companyLocation;
    View successToastLayout;
    EditText companyNameView;
    TextView companyNameInfo;
    TextView errorToastMessage;
    String authorizationHeader;
    Button createCompanyButton;
    TextView companyLocationInfo;
    TextView successToastMessage;
    EditText companyLocationView;
    CompaniesService companiesService;
    CreateCompanyFragment createCompanyFragment;
    ApiService.CompanyServiceInterface companyService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Declarations
        View view;
        View customErrorToastView;
        View customSuccessToastView;
        SharedPreferences sharedPref;

        // Initializations
        view = inflater.inflate(R.layout.fragment_create_company, container, false);


        companyNameView = view.findViewById(R.id.companyName);
        companyNameInfo = view.findViewById(R.id.companyNameInfo);
        companyLocationView = view.findViewById(R.id.companyLocation);
        createCompanyButton = view.findViewById(R.id.createCompanyButton);
        companyLocationInfo = view.findViewById(R.id.companyLocationInfo);

        customErrorToastView = view.findViewById(R.id.customErrorToast);
        customSuccessToastView = view.findViewById(R.id.customSuccessToast);

        errorToastLayout = inflater.inflate(R.layout.error_toast_view, (ViewGroup) customErrorToastView);
        successToastLayout = inflater.inflate(R.layout.success_toast_view, (ViewGroup) customSuccessToastView);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        authorizationHeader = sharedPref.getString(Constants.AUTHORIZATION_HEADER, "");

        createCompanyFragment = this;
        companyService = ApiService.getCompanyService();

        // Listeners

        createCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCompany();
            }
        });


        return view;
    }

    public void clearErrors() {
        companyNameInfo.setText("");
        companyLocationInfo.setText("");
    }

    public void createCompany() {
        clearErrors();
        companyName = String.valueOf(companyNameView.getText());
        companyLocation = String.valueOf(companyLocationView.getText());

        if (validate()) {
            createCompanyButton.setText(Constants.CREATING);
            companiesService = new CompaniesService(companyService, createCompanyFragment, authorizationHeader);
            companiesService.createCompany(companyName, companyLocation);
        } else Log.e(Constants.RESPONSE_LOG_STR, "Validation failed");
    }

    public void updateUi(CompanyServiceModel.CreateCompanyResponseModel response) {
        clearErrors();
        createCompanyButton.setText(Constants.UPDATE);
        successToastMessage = successToastLayout.findViewById(R.id.successToastMessage);
        successToastMessage.setText(response.getMessage());
        Toast toast = new Toast(getContext());
        toast.setView(successToastLayout);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.END, 25, 35);
        toast.show();
        getActivity().finish();
    }


    public void showError(String message) {
        Log.e(Constants.RESPONSE_LOG_STR, message);

        createCompanyButton.setText(Constants.CREATE);
        errorToastMessage = errorToastLayout.findViewById(R.id.errorToastMessage);
        errorToastMessage.setText(Constants.SOMETHING_WENT_WRONG);

        Toast toast = new Toast(getContext());
        toast.setView(errorToastLayout);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.END, 25, 35);
        toast.show();
    }

    public Boolean validate() {
        int minLength = 2;

        if (companyName.length() == 0) {
            companyNameInfo.setText(R.string.companyNameRequired);
            return false;
        }

        if (companyName.length() < 2) {
            companyNameInfo.setText("company name's length must be more than " + minLength);
            return false;
        }

        if (companyLocation.length() == 0) {
            companyLocationInfo.setText(R.string.companyLocationRequired);
            return false;
        }

        if (companyLocation.length() < 2) {
            companyLocationInfo.setText("company location's length must be more than " + minLength);
            return false;
        }

        return true;

    }


}