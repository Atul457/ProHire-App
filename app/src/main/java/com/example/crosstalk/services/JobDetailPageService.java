package com.example.crosstalk.services;

import android.util.Log;

import com.example.crosstalk.JobDetailsActivity;
import com.example.crosstalk.models.ErrorHandlingModel;
import com.example.crosstalk.models.JobServiceModel;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailPageService {

    JobDetailsActivity jobDetailsActivity;
    ApiService.JobServiceInterface jobService;

    public JobDetailPageService(ApiService.JobServiceInterface jobService, JobDetailsActivity jobDetailsActivity) {
        this.jobDetailsActivity = jobDetailsActivity;
        this.jobService = jobService;
    }

    public void getJob(String jobId) {

        Call<JobServiceModel.GetSingleJobModel> call;
        call = jobService.getJob(jobId);

        call.enqueue(new Callback<JobServiceModel.GetSingleJobModel>() {
            @Override
            public void onResponse(Call<JobServiceModel.GetSingleJobModel> call, Response<JobServiceModel.GetSingleJobModel> response) {
               String response_ = String.valueOf(response.isSuccessful());

                if (response.isSuccessful()) {
                    List<JobServiceModel.SingleJobModel> job = response.body().getData();
                    jobDetailsActivity.updateUi(job);
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
                Log.e("response_", t.getMessage());
            }
        });
    }

}
