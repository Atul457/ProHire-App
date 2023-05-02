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
import com.example.crosstalk.services.ApiService;
import com.example.crosstalk.services.CompaniesService;
import com.example.crosstalk.utils.Constants;

/**
 * @info This is used to edit a company
 */
public class EditCompanyFragment extends Fragment {

    // Vars
    String companyId;
    String companyName;
    View errorToastLayout;
    String companyLocation;
    View successToastLayout;
    EditText companyNameView;
    TextView companyNameInfo;
    Button updateCompanyButton;
    String authorizationHeader;
    Button deleteCompanyButton;
    TextView errorToastMessage;
    TextView successToastMessage;
    TextView companyLocationInfo;
    EditText companyLocationView;
    CompaniesService companiesService;
    EditCompanyFragment editCompanyFragment;
    ApiService.CompanyServiceInterface companyService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Declarations
        View view;
        View customErrorToastView;
        View customSuccessToastView;
        SharedPreferences sharedPref;

        if (getArguments() == null) {
            showError(Constants.SOMETHING_WENT_WRONG);
        }

        // Initializations
        editCompanyFragment = this;

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        authorizationHeader = sharedPref.getString(Constants.AUTHORIZATION_HEADER, "");

        view = inflater.inflate(R.layout.fragment_edit_company, container, false);

        companyNameInfo = view.findViewById(R.id.companyNameInfo);
        companyLocationInfo = view.findViewById(R.id.companyLocationInfo);
        deleteCompanyButton = view.findViewById(R.id.deleteCompanyButton);
        updateCompanyButton = view.findViewById(R.id.updateCompanyButton);

        companyId = getArguments().getString(Constants.COMPANY_ID);
        companyName = getArguments().getString(Constants.COMPANY_NAME);
        companyLocation = getArguments().getString(Constants.COMPANY_LOCATION);

        companyService = ApiService.getCompanyService();

        customErrorToastView = view.findViewById(R.id.customErrorToast);
        customSuccessToastView = view.findViewById(R.id.customSuccessToast);
        errorToastLayout = inflater.inflate(R.layout.error_toast_view, (ViewGroup) customErrorToastView);
        successToastLayout = inflater.inflate(R.layout.success_toast_view, (ViewGroup) customSuccessToastView);

        companyNameView = view.findViewById(R.id.companyName);
        companyLocationView = view.findViewById(R.id.companyLocation);

        companyNameView.setText(companyName);
        companyLocationView.setText(companyLocation);

        // Listeners
        updateCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCompany();
            }
        });

        deleteCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCompany();
            }
        });


        // Inflate the layout for this fragment
        return view;

    }

    public void clearErrors() {
        companyNameInfo.setText("");
        companyLocationInfo.setText("");
    }

    public void updateCompany() {
        clearErrors();
        companyName = String.valueOf(companyNameView.getText());
        companyLocation = String.valueOf(companyLocationView.getText());

        if (validate()) {
            updateCompanyButton.setText(Constants.UPDATING);
            companiesService = new CompaniesService(companyService, editCompanyFragment, authorizationHeader);
            companiesService.updateCompany(companyName, companyId, companyLocation);
        } else Log.e(Constants.RESPONSE_LOG_STR, "Validation failed");
    }

    public void deleteCompany() {
        deleteCompanyButton.setText(Constants.DELETING);
        companiesService = new CompaniesService(companyService, editCompanyFragment, authorizationHeader);
        companiesService.deleteCompany(companyId);
    }

    public void updateUi(String response, Boolean deleted) {

        clearErrors();
        updateCompanyButton.setText(Constants.UPDATE);
        successToastMessage = successToastLayout.findViewById(R.id.successToastMessage);
        successToastMessage.setText(response);

        Toast toast = new Toast(getContext());
        toast.setView(successToastLayout);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.END, 25, 35);
        toast.show();

        if (deleted) getActivity().finish();
    }

    public void showError(String message) {
        Log.e(Constants.RESPONSE_LOG_STR, message);
        updateCompanyButton.setText(Constants.UPDATE);

        errorToastMessage = errorToastLayout.findViewById(R.id.errorToastMessage);
        errorToastMessage.setText(message);

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