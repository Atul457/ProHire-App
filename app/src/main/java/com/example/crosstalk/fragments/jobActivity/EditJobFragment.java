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

import com.example.crosstalk.R;
import com.example.crosstalk.services.ApiService;
import com.example.crosstalk.services.JobServices;
import com.example.crosstalk.utils.Constants;

/**
 * @TODO Document this
 */
public class EditJobFragment extends Fragment {

    String jobId;
    String jobName;
    String jobSalary;
    TextView jobNameInfo;
    EditText jobNameView;
    String jobDescription;
    View errorToastLayout;
    TextView jobSalaryInfo;
    EditText jobSalaryView;
    Button updateJobButton;
    Button deleteJobButton;
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
    EditJobFragment editJobFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Declarations
        View view;
        ApiService.JobServiceInterface jobService;

        // Initializations
        view = inflater.inflate(R.layout.fragment_edit_job, container, false);

        editJobFragment = this;

        customErrorToastView = view.findViewById(R.id.customErrorToast);
        customSuccessToastView = view.findViewById(R.id.customSuccessToast);
        errorToastLayout = inflater.inflate(R.layout.error_toast_view, (ViewGroup) customErrorToastView);
        successToastLayout = inflater.inflate(R.layout.success_toast_view, (ViewGroup) customSuccessToastView);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        authorizationHeader = sharedPref.getString(Constants.AUTHORIZATION_HEADER, "");

        jobService = ApiService.getJobService();
        jobId = getArguments().getString(Constants.JOB_ID);
        jobName = getArguments().getString(Constants.JOB_NAME);
        jobDescription = getArguments().getString(Constants.JOB_DESCRIPTION);
        jobSalary = String.valueOf(getArguments().getLong(Constants.JOB_SALARY));

        jobNameView = view.findViewById(R.id.jobName);
        jobSalaryView = view.findViewById(R.id.jobSalary);
        jobNameInfo = view.findViewById(R.id.jobNameInfo);
        jobSalaryInfo = view.findViewById(R.id.jobSalaryInfo);
        deleteJobButton = view.findViewById(R.id.deleteJobButton);
        updateJobButton = view.findViewById(R.id.updateJobButton);
        jobDescriptionView = view.findViewById(R.id.jobDescription);
        jobDescriptionInfo = view.findViewById(R.id.jobDescriptionInfo);

        jobServices = new JobServices(jobService, this, authorizationHeader);

        if (jobId != null) {
            jobNameView.setText(jobName);
            jobSalaryView.setText(jobSalary);
            jobDescriptionView.setText(jobDescription);
        } else {
            Log.i(Constants.RESPONSE_LOG_STR, "jobId is null");
        }

        // Listeners
        updateJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateJob();
            }
        });

        deleteJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteJob();
            }
        });

        return view;
    }

    public void clearErrors() {
        jobNameInfo.setText("");
        jobSalaryInfo.setText("");
        jobDescriptionInfo.setText("");
    }

    public void updateJob() {
        clearErrors();
        jobName = String.valueOf(jobNameView.getText());
        jobSalary = String.valueOf(jobSalaryView.getText());
        jobDescription = String.valueOf(jobDescriptionView.getText());

        if (validate()) {
            updateJobButton.setText(Constants.UPDATING);
            jobServices.updateJob(jobName, jobId, Long.parseLong(jobSalary), jobDescription);
        } else Log.e(Constants.RESPONSE_LOG_STR, "Validation failed");
    }


    public void deleteJob() {
        deleteJobButton.setText(Constants.DELETING);
        jobServices.deleteJob(jobId);
    }

    public void updateUi(String response, Boolean deleted) {

        clearErrors();
        updateJobButton.setText(Constants.UPDATE);
        successToastMessage = successToastLayout.findViewById(R.id.successToastMessage);
        successToastMessage.setText(response);

        Toast toast = new Toast(getContext());
        toast.setView(successToastLayout);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.END, 25, 35);
        toast.show();

        goBackToJobListing();
    }

    public void goBackToJobListing() {
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }

    public void showError(String message) {
        Log.e(Constants.RESPONSE_LOG_STR, message);
        updateJobButton.setText(Constants.UPDATE);

        errorToastMessage = errorToastLayout.findViewById(R.id.errorToastMessage);
        errorToastMessage.setText(message);

        Toast toast = new Toast(getContext());
        toast.setView(errorToastLayout);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.END, 25, 35);
        toast.show();
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
        int minimumSalary = 100;
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
            if (num < minimumSalary) {
                jobSalaryInfo.setText("job salary's must be more than or equal to " + minimumSalary);
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