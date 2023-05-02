package com.example.crosstalk.services;

import com.example.crosstalk.JobDetailsActivity;
import com.example.crosstalk.fragments.jobActivity.ApplicantsOfJobFragment;
import com.example.crosstalk.fragments.jobActivity.CreateJobFragment;
import com.example.crosstalk.fragments.jobActivity.EditJobFragment;
import com.example.crosstalk.fragments.jobActivity.JobsOfCompanyFragment;
import com.example.crosstalk.models.ErrorHandlingModel;
import com.example.crosstalk.models.JobServiceModel;
import com.example.crosstalk.utils.Constants;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobServices {
    String authorizationHeader;
    EditJobFragment editJobFragment;
    CreateJobFragment createJobFragment;
    JobDetailsActivity jobDetailsActivity;
    JobsOfCompanyFragment jobsOfCompanyFragment;
    ApplicantsOfJobFragment applicantsOfJobFragment;
    ApiService.JobServiceInterface jobService;

    public JobServices(ApiService.JobServiceInterface jobService, Object jobActivityOrFragment, String authorizationHeader) {
        if (jobActivityOrFragment instanceof JobDetailsActivity) {
            this.jobDetailsActivity = (JobDetailsActivity) jobActivityOrFragment;
        } else if (jobActivityOrFragment instanceof JobsOfCompanyFragment) {
            this.jobsOfCompanyFragment = (JobsOfCompanyFragment) jobActivityOrFragment;
        } else if (jobActivityOrFragment instanceof EditJobFragment) {
            this.editJobFragment = (EditJobFragment) jobActivityOrFragment;
        } else if (jobActivityOrFragment instanceof CreateJobFragment) {
            this.createJobFragment = (CreateJobFragment) jobActivityOrFragment;
        } else if (jobActivityOrFragment instanceof ApplicantsOfJobFragment) {
            this.applicantsOfJobFragment = (ApplicantsOfJobFragment) jobActivityOrFragment;
        }

        this.jobService = jobService;
        this.authorizationHeader = authorizationHeader;
    }

    public void getJob(String jobId) {

        Call<JobServiceModel.GetSingleJobModel> call;
        call = jobService.getJob(authorizationHeader, jobId);

        call.enqueue(new Callback<JobServiceModel.GetSingleJobModel>() {
            @Override
            public void onResponse(Call<JobServiceModel.GetSingleJobModel> call, Response<JobServiceModel.GetSingleJobModel> response) {

                if (response.isSuccessful()) {
                    List<JobServiceModel.SingleJobModel> job = response.body().getData();
                    if (jobDetailsActivity != null) jobDetailsActivity.updateUi(job);
                } else {
                    try {
                        Gson gsonRef = new Gson();
                        ErrorHandlingModel error;
                        error = gsonRef.fromJson(response.errorBody().charStream(), ErrorHandlingModel.class);
                        String message = error.getMessage();
                        jobDetailsActivity.showError(message);
                    } catch (Error error) {
                        jobDetailsActivity.showError(error.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JobServiceModel.GetSingleJobModel> call, Throwable t) {
                jobDetailsActivity.showError(t.getMessage());
            }
        });
    }

    public void getJobsOfCompany(String companyId) {
        Call<JobServiceModel.GetJobsOfCompanyResponseModel> call;
        call = jobService.getJobsOfCompany((String) authorizationHeader, companyId);

        call.enqueue(new Callback<JobServiceModel.GetJobsOfCompanyResponseModel>() {
            @Override
            public void onResponse(Call<JobServiceModel.GetJobsOfCompanyResponseModel> call, Response<JobServiceModel.GetJobsOfCompanyResponseModel> response) {
                if (response.isSuccessful()) {
                    List<JobServiceModel.MyJobModel> jobs = response.body().getData();
                    jobsOfCompanyFragment.updateUi(jobs);
                } else {
                    try {
                        Gson gsonRef = new Gson();
                        ErrorHandlingModel error;
                        error = gsonRef.fromJson(response.errorBody().charStream(), ErrorHandlingModel.class);
                        String message = error.getMessage();
                        jobsOfCompanyFragment.showError(message);
                    } catch (Error error) {
                        jobsOfCompanyFragment.showError(error.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JobServiceModel.GetJobsOfCompanyResponseModel> call, Throwable t) {
                jobsOfCompanyFragment.showError(t.getMessage());
            }
        });
    }

    /**
     * @info Updates the job
     */
    public void updateJob(String jobName, String jobId, Long jobSalary, String jobDescription) {

        Call<JobServiceModel.UpdateJobResponseModel> call;
        JobServiceModel.UpdateJobRequestModel request;

        request = new JobServiceModel.UpdateJobRequestModel(jobId, jobSalary, jobName, jobDescription);
        call = jobService.updateJob((String) authorizationHeader, request);

        call.enqueue(new Callback<JobServiceModel.UpdateJobResponseModel>() {
            @Override
            public void onResponse(Call<JobServiceModel.UpdateJobResponseModel> call, Response<JobServiceModel.UpdateJobResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage() == null)
                        editJobFragment.updateUi(Constants.JOB_UPDATED_SUCCESSFULLY, false);
                    else
                        editJobFragment.updateUi(response.body().getMessage().toString(), false);
                } else {
                    try {
                        Gson gsonRef = new Gson();
                        ErrorHandlingModel error;
                        error = gsonRef.fromJson(response.errorBody().charStream(), ErrorHandlingModel.class);
                        String message = error.getMessage();
                        editJobFragment.showError(message);
                    } catch (Error error) {
                        editJobFragment.showError(error.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JobServiceModel.UpdateJobResponseModel> call, Throwable t) {
                editJobFragment.showError(t.getMessage());
            }
        });

    }

    /**
     * @info Creates the job
     */
    public void createJob(String jobName, Long jobSalary, String jobDescription, String companyId) {

        JobServiceModel.CreateJobRequestModel request;
        Call<JobServiceModel.CreateJobResponseModel> call;

        request = new JobServiceModel.CreateJobRequestModel(jobName, jobSalary, companyId, jobDescription);
        call = jobService.createJob((String) authorizationHeader, request);

        call.enqueue(new Callback<JobServiceModel.CreateJobResponseModel>() {
            @Override
            public void onResponse(Call<JobServiceModel.CreateJobResponseModel> call, Response<JobServiceModel.CreateJobResponseModel> response) {
                if (response.isSuccessful()) {
                    createJobFragment.updateUi(response.body());
                } else {
                    try {
                        Gson gsonRef = new Gson();
                        ErrorHandlingModel error;
                        error = gsonRef.fromJson(response.errorBody().charStream(), ErrorHandlingModel.class);
                        String message = error.getMessage();
                        createJobFragment.showError(message);
                    } catch (Error error) {
                        createJobFragment.showError(error.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JobServiceModel.CreateJobResponseModel> call, Throwable t) {
                createJobFragment.showError(t.getMessage());
            }
        });

    }

    /**
     * @info Deletes the job
     */
    public void deleteJob(String jobId) {

        Call<JobServiceModel.DeleteJobResponseModel> call;
        JobServiceModel.DeleteJobRequestModel request;

        request = new JobServiceModel.DeleteJobRequestModel(jobId);
        call = jobService.deleteJob((String) authorizationHeader, request);

        call.enqueue(new Callback<JobServiceModel.DeleteJobResponseModel>() {
            @Override
            public void onResponse(Call<JobServiceModel.DeleteJobResponseModel> call, Response<JobServiceModel.DeleteJobResponseModel> response) {
                if (response.isSuccessful()) {
                    editJobFragment.updateUi(response.body().getMessage(), true);
                } else {
                    try {
                        Gson gsonRef = new Gson();
                        ErrorHandlingModel error;
                        error = gsonRef.fromJson(response.errorBody().charStream(), ErrorHandlingModel.class);
                        String message = error.getMessage();
                        editJobFragment.showError(message);
                    } catch (Error error) {
                        editJobFragment.showError(error.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JobServiceModel.DeleteJobResponseModel> call, Throwable t) {
                editJobFragment.showError(t.getMessage());
            }
        });

    }

    /**
     * @TODO Document this
     */
    public void applyForJob(String jobId) {

        Call<JobServiceModel.ApplyForJobResponse> call;
        call = jobService.applyForJob((String) authorizationHeader, jobId);

        call.enqueue(new Callback<JobServiceModel.ApplyForJobResponse>() {
            @Override
            public void onResponse(Call<JobServiceModel.ApplyForJobResponse> call, Response<JobServiceModel.ApplyForJobResponse> response) {
                if (response.isSuccessful()) {
                    jobDetailsActivity.appliedForJob(response.body().getMessage());
                } else {
                    try {
                        Gson gsonRef = new Gson();
                        ErrorHandlingModel error;
                        error = gsonRef.fromJson(response.errorBody().charStream(), ErrorHandlingModel.class);
                        String message = error.getMessage();
                        jobDetailsActivity.showError(message);
                    } catch (Error error) {
                        jobDetailsActivity.showError(error.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JobServiceModel.ApplyForJobResponse> call, Throwable t) {
                jobDetailsActivity.showError(t.getMessage());
            }
        });
    }

    public void getApplicantsOfJob(String jobId) {
        Call<JobServiceModel.GetApplicationsOfJobResponse> call;
        call = jobService.getApplicantsOfJob((String) authorizationHeader, jobId);

        call.enqueue(new Callback<JobServiceModel.GetApplicationsOfJobResponse>() {
            @Override
            public void onResponse(Call<JobServiceModel.GetApplicationsOfJobResponse> call, Response<JobServiceModel.GetApplicationsOfJobResponse> response) {
                if (response.isSuccessful()) {
                    applicantsOfJobFragment.updateUi(response.body().getData());
                } else {
                    try {
                        Gson gsonRef = new Gson();
                        ErrorHandlingModel error;
                        error = gsonRef.fromJson(response.errorBody().charStream(), ErrorHandlingModel.class);
                        String message = error.getMessage();
                        applicantsOfJobFragment.showError(message);
                    } catch (Error error) {
                        applicantsOfJobFragment.showError(error.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JobServiceModel.GetApplicationsOfJobResponse> call, Throwable t) {
                applicantsOfJobFragment.showError(t.getMessage());
            }
        });
    }

}
