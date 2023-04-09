package com.example.crosstalk.services;

import com.example.crosstalk.fragments.homeActivity.HomeFragment;
import com.example.crosstalk.fragments.homeActivity.SearchFragment;
import com.example.crosstalk.models.ErrorHandlingModel;
import com.example.crosstalk.models.JobServiceModel;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomepageService {

    public ApiService.JobServiceInterface jobService;
    public HomeFragment homeFragment;
    public SearchFragment searchFragment;
    public Boolean isHomeFragment;


    public HomepageService(ApiService.JobServiceInterface jobService, Object fragment) {

        this.isHomeFragment = false;
        this.jobService = jobService;

        if (fragment instanceof HomeFragment) {
            this.isHomeFragment = true;
            this.homeFragment = (HomeFragment) fragment;
        } else if (fragment instanceof SearchFragment) {
            this.isHomeFragment = false;
            this.searchFragment = (SearchFragment) fragment;
        }
    }

    public void getJobs(Object q, Object page, Object order, Object limit, Object ls, Object hs) {

        JobServiceModel.GetJobsRequestModel gjr;
        gjr = new JobServiceModel.GetJobsRequestModel(q, page, order, limit, ls, hs);

        Call<JobServiceModel.GetJobsResponseModel> call;
        JobServiceModel.GetJobsRequestModel getJobsRequestData;
        getJobsRequestData = new JobServiceModel.GetJobsRequestModel(
                gjr.getQ(),
                gjr.getPage(),
                gjr.getOrder(),
                gjr.getLimit(),
                gjr.getLs(),
                gjr.getHs()
        );
        call = jobService.getJobs(getJobsRequestData.getQueryString());
        call.enqueue(new Callback<JobServiceModel.GetJobsResponseModel>() {
            @Override
            public void onResponse(Call<JobServiceModel.GetJobsResponseModel> call, Response<JobServiceModel.GetJobsResponseModel> response) {
                if (response.isSuccessful()) {
                    if (isHomeFragment)
                        homeFragment.updateUi(response.body().getData());
                    else
                        searchFragment.updateUi(response.body().getData());
                } else {
                    try {
                        Gson gsonRef = new Gson();
                        ErrorHandlingModel error;
                        error = gsonRef.fromJson(response.errorBody().charStream(), ErrorHandlingModel.class);
                        String message = error.getMessage();
                        if (isHomeFragment)
                            homeFragment.showError(message);
                        else
                            searchFragment.showError(message);
                    } catch (Error error) {

                        if (isHomeFragment)
                            homeFragment.showError(error.getMessage());
                        else
                            searchFragment.showError(error.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<JobServiceModel.GetJobsResponseModel> call, Throwable t) {

            }
        });

    }

}
