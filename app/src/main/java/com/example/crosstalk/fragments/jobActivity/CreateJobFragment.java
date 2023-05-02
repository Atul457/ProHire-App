package com.example.crosstalk.fragments.jobActivity;

import android.content.Intent;
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

import com.example.crosstalk.JobActivity;
import com.example.crosstalk.R;
import com.example.crosstalk.models.CompanyServiceModel;
import com.example.crosstalk.models.JobServiceModel;
import com.example.crosstalk.services.ApiService;
import com.example.crosstalk.services.CompaniesService;
import com.example.crosstalk.services.JobServices;
import com.example.crosstalk.utils.Constants;

/**
 * @TODO Document this
 */
public class CreateJobFragment extends Fragment {

    String jobName;
    String companyId;
    String jobSalary;
    String companyName;
    TextView jobNameInfo;
    EditText jobNameView;
    String jobDescription;
    View errorToastLayout;
    TextView jobSalaryInfo;
    EditText jobSalaryView;
    Button createJobButton;
    View successToastLayout;
    JobServices jobServices;
    View customErrorToastView;
    TextView errorToastMessage;
    String authorizationHeader;
    TextView jobDescriptionInfo;
    View customSuccessToastView;
    EditText jobDescriptionView;
    SharedPreferences sharedPref;
    TextView successToastMessage;
    CreateJobFragment createJobFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Declarations
        View view;
        ApiService.JobServiceInterface jobService;

        // Initializations
        view = inflater.inflate(R.layout.fragment_create_job, container, false);

        createJobFragment = this;

        customErrorToastView = view.findViewById(R.id.customErrorToast);
        customSuccessToastView = view.findViewById(R.id.customSuccessToast);
        errorToastLayout = inflater.inflate(R.layout.error_toast_view, (ViewGroup) customErrorToastView);
        successToastLayout = inflater.inflate(R.layout.success_toast_view, (ViewGroup) customSuccessToastView);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        authorizationHeader = sharedPref.getString(Constants.AUTHORIZATION_HEADER, "");

        jobService = ApiService.getJobService();

        if (getArguments() != null) {
            companyId = getArguments().getString(Constants.COMPANY_ID);
            companyName = getArguments().getString(Constants.COMPANY_NAME);
        } else {
            showError(Constants.SOMETHING_WENT_WRONG);
            getActivity().finish();
        }

        jobNameView = view.findViewById(R.id.jobName);
        jobSalaryView = view.findViewById(R.id.jobSalary);
        jobNameInfo = view.findViewById(R.id.jobNameInfo);
        jobSalaryInfo = view.findViewById(R.id.jobSalaryInfo);
        createJobButton = view.findViewById(R.id.createJobButton);
        jobDescriptionView = view.findViewById(R.id.jobDescription);
        jobDescriptionInfo = view.findViewById(R.id.jobDescriptionInfo);

        jobServices = new JobServices(jobService, this, authorizationHeader);

        // Listeners
        createJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createJob();
            }
        });

        return view;
    }

    public void clearErrors() {
        jobNameInfo.setText("");
        jobSalaryInfo.setText("");
        jobDescriptionInfo.setText("");
    }

    public void createJob() {
        clearErrors();
        jobName = String.valueOf(jobNameView.getText());
        jobSalary = String.valueOf(jobSalaryView.getText());
        jobDescription = String.valueOf(jobDescriptionView.getText());

        if (validate()) {
            createJobButton.setText(Constants.CREATING);
            jobServices.createJob(jobName, Long.parseLong(jobSalary), jobDescription, companyId);
        } else Log.e(Constants.RESPONSE_LOG_STR, "Validation failed");
    }


    public void updateUi(JobServiceModel.CreateJobResponseModel response) {
        clearErrors();
        createJobButton.setText(Constants.CREATE);
        successToastMessage = successToastLayout.findViewById(R.id.successToastMessage);
        successToastMessage.setText(response.getMessage());
        Toast toast = new Toast(getContext());
        toast.setView(successToastLayout);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.END, 25, 35);
        toast.show();
        goBackToJobListing();
    }


    public void showError(String message) {
        Log.e(Constants.RESPONSE_LOG_STR, message);

        createJobButton.setText(Constants.CREATE);
        errorToastMessage = errorToastLayout.findViewById(R.id.errorToastMessage);
        errorToastMessage.setText(Constants.SOMETHING_WENT_WRONG);

        Toast toast = new Toast(getContext());
        toast.setView(errorToastLayout);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.END, 25, 35);
        toast.show();
    }

    public void goBackToJobListing() {
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public Boolean validate() {
        int minLength = 2;
        int jobDescriptionMinLength = 80;

        if (jobName.length() == 0) {
            jobNameInfo.setText(R.string.jobNameRequired);
            return false;
        }

        if (jobName.length() < minLength) {
            jobNameInfo.setText("job name's length must be more than " + minLength);
            return false;
        }

        if (jobSalary.length() == 0) {
            jobSalaryInfo.setText(R.string.jobSalaryRequired);
            return false;
        }

        if (isNumeric(jobSalary)) {
            long num = Long.parseLong(jobSalary);
            if (num < 100) {
                jobSalaryInfo.setText("job salary's must be more than or equal to " + jobDescriptionMinLength);
                return false;
            }
        } else {
            jobSalaryInfo.setText(R.string.jobSalaryMustBeNumber);
            return false;
        }


        if (jobDescription.length() == 0) {
            jobDescriptionInfo.setText(R.string.jobDescriptionRequired);
            return false;
        }

        if (jobDescription.length() < jobDescriptionMinLength) {
            jobDescriptionInfo.setText("job description's length must be more than " + jobDescriptionMinLength);
            return false;
        }

        return true;

    }
}