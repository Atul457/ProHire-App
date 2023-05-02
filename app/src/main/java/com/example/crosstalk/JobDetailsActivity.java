package com.example.crosstalk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crosstalk.models.JobServiceModel;
import com.example.crosstalk.services.ApiService;
import com.example.crosstalk.services.JobServices;
import com.example.crosstalk.utils.Constants;

import java.util.List;
import java.util.zip.Inflater;

public class JobDetailsActivity extends AppCompatActivity {

    String jobId;
    Intent intent;
    TextView salary;
    TextView jobName;
    TextView userName;
    View progressBar;
    TextView userEmail;
    TextView userPhone;
    View jobDetailsView;
    TextView companyName;
    View errorToastLayout;
    ImageView goBackButton;
    TextView jobDescription;
    View successToastLayout;
    TextView applyJobButton;
    TextView companyLocation;
    TextView errorToastMessage;
    TextView successToastMessage;
    JobServices jobDetailPageService;
    CardView applyJobButtonContainer;
    ApiService.JobServiceInterface jobService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        View customErrorToastView;
        String authorizationHeader;
        View customSuccessToastView;
        SharedPreferences sharedPref;
        setContentView(R.layout.activity_job_details);

        // Removing header
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        // Initializations
        salary = findViewById(R.id.salary);
        jobName = findViewById(R.id.jobName);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPhone = findViewById(R.id.userPhone);
        companyName = findViewById(R.id.companyName);
        progressBar = findViewById(R.id.progressBar);
        goBackButton = findViewById(R.id.goBackButton);
        jobDetailsView = findViewById(R.id.jobDetailsView);
        jobDescription = findViewById(R.id.jobDescription);
        companyLocation = findViewById(R.id.companyLocation);
        applyJobButton = findViewById(R.id.applyForJobButton);
        customErrorToastView = findViewById(R.id.customErrorToast);
        customSuccessToastView = findViewById(R.id.customSuccessToast);
        applyJobButtonContainer = findViewById(R.id.applyJobButtonContainer);

        LayoutInflater inflater = LayoutInflater.from(this);
        errorToastLayout = inflater.inflate(R.layout.error_toast_view, (ViewGroup) customErrorToastView);
        successToastLayout = inflater.inflate(R.layout.success_toast_view, (ViewGroup) customSuccessToastView);

        intent = getIntent();

        // Remove loader and show detail view
        progressBar.setVisibility(View.VISIBLE);
        jobDetailsView.setVisibility(View.INVISIBLE);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        authorizationHeader = sharedPref.getString(Constants.AUTHORIZATION_HEADER, "");
        Log.w(Constants.RESPONSE_LOG_STR, authorizationHeader);

        // Getting job id
        jobId = intent.getStringExtra("jobId");

        if (jobId != null) {
            jobService = ApiService.getJobService();
            jobDetailPageService = new JobServices(jobService, this, authorizationHeader);
            jobDetailPageService.getJob(jobId);
        } else {
            this.showError("Something went wrong");
        }

        // Listeners
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobDetailsActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        applyJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyForJob();
                applyJobButton.setText(Constants.APPLYING);
            }
        });
    }

    public void applyForJob() {
        jobDetailPageService.applyForJob(jobId);
    }

    public void appliedForJob(String message) {
        applyJobButton.setText("Apply");
        successToastMessage = successToastLayout.findViewById(R.id.successToastMessage);
        successToastMessage.setText(message);
        Toast toast = new Toast(getApplicationContext());
        toast.setView(successToastLayout);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.END, 25, 35);
        toast.show();
        finish();
        Log.w(Constants.RESPONSE_LOG_STR, "Applied for job");
    }

    public void updateUi(List<JobServiceModel.SingleJobModel> jobList) {

        progressBar.setVisibility(View.GONE);
        jobDetailsView.setVisibility(View.VISIBLE);
        JobServiceModel.SingleJobModel job = jobList.get(0);

        Boolean isMyJob = job.getIs_my_job();

        Log.e("job", String.valueOf(job));

        jobName.setText(job.getJob_name().trim());
        userName.setText(job.getUser_name().trim());
        userEmail.setText(job.getUser_email().trim());
        userPhone.setText(job.getUser_phone().trim());
        companyName.setText(job.getCompany_name().trim());
        jobDescription.setText(job.getJob_description().trim());
        salary.setText(job.getJob_salary().toString().trim() + " per month");
        companyLocation.setText(job.getCompany_location().trim());

        if (isMyJob)
            applyJobButtonContainer.setVisibility(View.GONE);

    }

    public void showError(String error) {

        errorToastMessage = errorToastLayout.findViewById(R.id.errorToastMessage);
        errorToastMessage.setText(error);
        Toast toast = new Toast(getApplicationContext());
        toast.setView(errorToastLayout);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.END, 25, 35);
        toast.show();
        progressBar.setVisibility(View.GONE);
        Log.w("error", error);

    }

}