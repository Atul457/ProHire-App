package com.example.crosstalk;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crosstalk.models.JobServiceModel;
import com.example.crosstalk.services.ApiService;
import com.example.crosstalk.services.JobDetailPageService;

import java.util.List;

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
    ImageView goBackButton;
    TextView jobDescription;
    TextView companyLocation;
    JobDetailPageService jobDetailPageService;
    ApiService.JobServiceInterface jobService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
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
        companyLocation = findViewById(R.id.companyLocation);
        jobDescription = findViewById(R.id.jobDescription);

        intent = getIntent();

        // Remove loader and show detail view
        progressBar.setVisibility(View.VISIBLE);
        jobDetailsView.setVisibility(View.VISIBLE);

        // Getting job id
        jobId = intent.getStringExtra("jobId");

        if (jobId != null) {
            jobService = ApiService.getJobService();
            jobDetailPageService = new JobDetailPageService(jobService, this);
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
    }

    public void updateUi(List<JobServiceModel.SingleJobModel> jobList) {

        progressBar.setVisibility(View.GONE);
        JobServiceModel.SingleJobModel job = jobList.get(0);

        Log.e("job", String.valueOf(job));

        jobName.setText(job.getJob_name().trim());
        userName.setText(job.getUser_name().trim());
        userEmail.setText(job.getUser_email().trim());
        userPhone.setText(job.getUser_phone().trim());
        companyName.setText(job.getCompany_name().trim());
        jobDescription.setText(job.getJob_description().trim());
        salary.setText(job.getJob_salary().toString().trim() + " per month");
        companyLocation.setText(job.getCompany_location().trim());

    }

    public void showError(String error) {
        progressBar.setVisibility(View.GONE);
        Log.w("error", error);
    }

}