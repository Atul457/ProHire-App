package com.example.crosstalk.services;

import com.example.crosstalk.models.JobServiceModel;
import com.example.crosstalk.models.UserServiceModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public class ApiService {

    public static final String baseUrl = "https://tan-sleepy-goose.cyclic.app/";

    public static UserServiceInterface userService = null;
    public static JobServiceInterface jobService = null;
    public static Retrofit retrofitInstance = null;


    /**
     * @return Retrofit instance
     */
    public static Retrofit getRetrofit() {
        if (ApiService.retrofitInstance == null) {
            Retrofit retrofit = new Retrofit
                    .Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            retrofitInstance = retrofit;
            return retrofitInstance;
        }

        return retrofitInstance;
    }


    /**
     * @returns Instance of user service interface
     */
    public static UserServiceInterface getUserService() {
        if (ApiService.userService == null) {
            userService = getRetrofit().create(ApiService.UserServiceInterface.class);
            return userService;
        }
        return userService;
    }

    /**
     * @returns Instance of job service interface
     */
    public static JobServiceInterface getJobService() {
        if (ApiService.jobService == null) {
            jobService = getRetrofit().create(ApiService.JobServiceInterface.class);
            return jobService;
        }
        return jobService;
    }


    /**
     * @info User service interface
     */
    public interface UserServiceInterface {

        @POST("/user/signIn")
        Call<UserServiceModel.ResponseModel> login(@Body UserServiceModel.LoginRequestModel loginRequest);

        @POST("/user/signUp")
        Call<UserServiceModel.ResponseModel> register(@Body UserServiceModel.RegisterRequestModel registerRequest);

    }

    /**
     * @info Job service interface
     */
    public interface JobServiceInterface {
        @GET("/job/getJobs")
        Call<JobServiceModel.GetJobsResponseModel> getJobs(@QueryMap Map<String, String> jobsRequest);

        @GET("/job/getJob/{jobId}")
        Call<JobServiceModel.GetSingleJobModel> getJob(@Path("jobId") String jobId);
    }

}
