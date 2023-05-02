package com.example.crosstalk.services;

import android.content.SharedPreferences;

import com.example.crosstalk.models.CompanyServiceModel;
import com.example.crosstalk.models.JobServiceModel;
import com.example.crosstalk.models.NotificationServiceModel;
import com.example.crosstalk.models.UserServiceModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public class ApiService {

    public static final String baseUrl = "https://tan-sleepy-goose.cyclic.app/";

    public static UserServiceInterface userService = null;
    public static JobServiceInterface jobService = null;
    public static NotificationServiceInterface notificationService = null;
    public static CompanyServiceInterface companyService = null;
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
     * @returns Instance of company service interface
     */
    public static CompanyServiceInterface getCompanyService() {
        if (ApiService.companyService == null) {
            companyService = getRetrofit().create(ApiService.CompanyServiceInterface.class);
            return companyService;
        }
        return companyService;
    }


    /**
     * @returns Instance of notification service interface
     */
    public static NotificationServiceInterface getNotificationService() {
        if (ApiService.notificationService == null) {
            notificationService = getRetrofit().create(ApiService.NotificationServiceInterface.class);
            return notificationService;
        }
        return notificationService;
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

        @GET("/user/getMyProfile")
        Call<UserServiceModel.GetProfileResponseModel>
        getMyProfile(@Header("Authorization") String authorization);

        @POST("/user/signUp")
        Call<UserServiceModel.ResponseModel> register(@Body UserServiceModel.RegisterRequestModel registerRequest);

    }

    /**
     * @info Notification service interface
     */
    public interface NotificationServiceInterface {

        @GET("/notification/getMyNotifications")
        Call<NotificationServiceModel.GetNotificationsResponse>
        getMyNotifications(
                @Header("Authorization") String authorization
        );

    }

    /**
     * @info Job service interface
     */
    public interface JobServiceInterface {
        @GET("/job/getJobs")
        Call<JobServiceModel.GetJobsResponseModel>
        getJobs(
                @Header("Authorization") String authorization,
                @QueryMap Map<String, String> jobsRequest
        );

        @GET("/job/getJob/{jobId}")
        Call<JobServiceModel.GetSingleJobModel>
        getJob(
                @Header("Authorization") String authorization,
                @Path("jobId") String jobId
        );

        @GET("/job/applyForJob/{jobId}")
        Call<JobServiceModel.ApplyForJobResponse>
        applyForJob(
                @Header("Authorization") String authorization,
                @Path("jobId") String jobId
        );

        @GET("/job/getApplicantsOfJob/{jobId}")
        Call<JobServiceModel.GetApplicationsOfJobResponse>
        getApplicantsOfJob(
                @Header("Authorization") String authorization,
                @Path("jobId") String jobId
        );

        @GET("/job/getJobsOfCompany/{companyId}")
        Call<JobServiceModel.GetJobsOfCompanyResponseModel>
        getJobsOfCompany(
                @Header("Authorization") String authorization,
                @Path("companyId") String companyId
        );

        @POST("/job/createJob")
        Call<JobServiceModel.CreateJobResponseModel>
        createJob(
                @Header("Authorization") String authorization,
                @Body JobServiceModel.CreateJobRequestModel createJobBody
        );

        @POST("/job/deleteJob")
        Call<JobServiceModel.DeleteJobResponseModel>
        deleteJob(
                @Header("Authorization") String authorization,
                @Body JobServiceModel.DeleteJobRequestModel deleteJobBody
        );

        @POST("/job/updateJob")
        Call<JobServiceModel.UpdateJobResponseModel>
        updateJob(
                @Header("Authorization") String authorization,
                @Body JobServiceModel.UpdateJobRequestModel updateJobBody
        );

    }

    /**
     * @info Job service interface
     */
    public interface CompanyServiceInterface {
        @GET("/company/getMyCompanies")
        Call<CompanyServiceModel.GetMyCompaniesResponseModel>
        getMyCompanies(@Header("Authorization") String authorization);

        @POST("/company/updateCompany")
        Call<CompanyServiceModel.UpdateCompanyResponseModel>
        updateCompany(
                @Header("Authorization") String authorization,
                @Body CompanyServiceModel.UpdateCompanyRequestModel updateCompanyBody
        );

        @POST("/company/createCompany")
        Call<CompanyServiceModel.CreateCompanyResponseModel>
        createCompany(
                @Header("Authorization") String authorization,
                @Body CompanyServiceModel.CreateCompanyRequestModel createCompanyBody
        );

        @POST("/company/deleteCompany")
        Call<CompanyServiceModel.DeleteCompanyResponseModel>
        deleteCompany(
                @Header("Authorization") String authorization,
                @Body CompanyServiceModel.DeleteCompanyRequestModel deleteCompanyBody
        );

    }

}
