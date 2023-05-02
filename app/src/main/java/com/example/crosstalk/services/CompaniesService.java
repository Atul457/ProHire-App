package com.example.crosstalk.services;

import com.example.crosstalk.fragments.companyActivity.EditCompanyFragment;
import com.example.crosstalk.fragments.companyActivity.CreateCompanyFragment;
import com.example.crosstalk.fragments.homeActivity.CompanyFragment;
import com.example.crosstalk.models.CompanyServiceModel;
import com.example.crosstalk.models.ErrorHandlingModel;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompaniesService {
    String authorizationHeader;
    CompanyFragment companyFragment;
    EditCompanyFragment editCompanyFragment;
    CreateCompanyFragment createCompanyFragment;
    ApiService.CompanyServiceInterface companyService;

    public CompaniesService(ApiService.CompanyServiceInterface companyService, Object fragment, String authorizationHeader) {
        if (fragment instanceof CompanyFragment) {
            this.companyFragment = (CompanyFragment) fragment;
        } else if (fragment instanceof EditCompanyFragment) {
            this.editCompanyFragment = (EditCompanyFragment) fragment;
        } else {
            this.createCompanyFragment = (CreateCompanyFragment) fragment;
        }

        this.companyService = companyService;
        this.authorizationHeader = authorizationHeader;
    }

    public void getMyCompanies() {

        Call<CompanyServiceModel.GetMyCompaniesResponseModel> call;

        call = companyService.getMyCompanies(authorizationHeader);

        call.enqueue(new Callback<CompanyServiceModel.GetMyCompaniesResponseModel>() {
            @Override
            public void onResponse(Call<CompanyServiceModel.GetMyCompaniesResponseModel> call, Response<CompanyServiceModel.GetMyCompaniesResponseModel> response) {
                if (response.isSuccessful()) {
                    List<CompanyServiceModel.CompanyModel> companies = response.body().getData();
                    companyFragment.updateUi(companies);
                } else {
                    try {
                        Gson gsonRef = new Gson();
                        ErrorHandlingModel error;
                        error = gsonRef.fromJson(response.errorBody().charStream(), ErrorHandlingModel.class);
                        String message = error.getMessage();
                        companyFragment.showError(message);
                    } catch (Error error) {
                        companyFragment.showError(error.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<CompanyServiceModel.GetMyCompaniesResponseModel> call, Throwable t) {
                companyFragment.showError(t.getMessage());
            }
        });
    }

    /**
     * @info Updates the company
     */
    public void updateCompany(String companyName, String companyId, String companyLocation) {

        Call<CompanyServiceModel.UpdateCompanyResponseModel> call;
        CompanyServiceModel.UpdateCompanyRequestModel request;

        request = new CompanyServiceModel.UpdateCompanyRequestModel(companyId, companyName, companyLocation);
        call = companyService.updateCompany(authorizationHeader, request);

        call.enqueue(new Callback<CompanyServiceModel.UpdateCompanyResponseModel>() {
            @Override
            public void onResponse(Call<CompanyServiceModel.UpdateCompanyResponseModel> call, Response<CompanyServiceModel.UpdateCompanyResponseModel> response) {
                if (response.isSuccessful()) {
                    editCompanyFragment.updateUi(response.body().getMessage().toString(), false);
                } else {
                    try {
                        Gson gsonRef = new Gson();
                        ErrorHandlingModel error;
                        error = gsonRef.fromJson(response.errorBody().charStream(), ErrorHandlingModel.class);
                        String message = error.getMessage();
                        editCompanyFragment.showError(message);
                    } catch (Error error) {
                        editCompanyFragment.showError(error.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<CompanyServiceModel.UpdateCompanyResponseModel> call, Throwable t) {
                editCompanyFragment.showError(t.getMessage());
            }
        });

    }

    /**
     * @info Creates the company
     */
    public void createCompany(String companyName, String companyLocation) {

        Call<CompanyServiceModel.CreateCompanyResponseModel> call;
        CompanyServiceModel.CreateCompanyRequestModel request;

        request = new CompanyServiceModel.CreateCompanyRequestModel(companyName, companyLocation);
        call = companyService.createCompany(authorizationHeader, request);

        call.enqueue(new Callback<CompanyServiceModel.CreateCompanyResponseModel>() {
            @Override
            public void onResponse(Call<CompanyServiceModel.CreateCompanyResponseModel> call, Response<CompanyServiceModel.CreateCompanyResponseModel> response) {
                if (response.isSuccessful()) {
                    createCompanyFragment.updateUi(response.body());
                } else {
                    try {
                        Gson gsonRef = new Gson();
                        ErrorHandlingModel error;
                        error = gsonRef.fromJson(response.errorBody().charStream(), ErrorHandlingModel.class);
                        String message = error.getMessage();
                        createCompanyFragment.showError(message);
                    } catch (Error error) {
                        editCompanyFragment.showError(error.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<CompanyServiceModel.CreateCompanyResponseModel> call, Throwable t) {
                createCompanyFragment.showError(t.getMessage());
            }
        });

    }

    /**
     * @info Deletes the company
     */
    public void deleteCompany(String companyId) {

        Call<CompanyServiceModel.DeleteCompanyResponseModel> call;
        CompanyServiceModel.DeleteCompanyRequestModel request;

        request = new CompanyServiceModel.DeleteCompanyRequestModel(companyId);
        call = companyService.deleteCompany(authorizationHeader, request);

        call.enqueue(new Callback<CompanyServiceModel.DeleteCompanyResponseModel>() {
            @Override
            public void onResponse(Call<CompanyServiceModel.DeleteCompanyResponseModel> call, Response<CompanyServiceModel.DeleteCompanyResponseModel> response) {
                if (response.isSuccessful()) {
                    editCompanyFragment.updateUi(response.body().getMessage(), true);
                } else {
                    try {
                        Gson gsonRef = new Gson();
                        ErrorHandlingModel error;
                        error = gsonRef.fromJson(response.errorBody().charStream(), ErrorHandlingModel.class);
                        String message = error.getMessage();
                        createCompanyFragment.showError(message);
                    } catch (Error error) {
                        editCompanyFragment.showError(error.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<CompanyServiceModel.DeleteCompanyResponseModel> call, Throwable t) {

            }
        });

    }

}
