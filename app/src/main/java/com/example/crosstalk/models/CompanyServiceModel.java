package com.example.crosstalk.models;

import java.util.List;

import javax.annotation.Generated;

/**
 * @Todo Document this
 */
@Generated("ByRutuja")
public class CompanyServiceModel {

    public static class CompanyModel {

        private String _id;
        private String company_name;
        private String company_location;
        private String created_at;
        private String updated_at;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getCompany_location() {
            return company_location;
        }

        public void setCompany_location(String company_location) {
            this.company_location = company_location;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }

    /**
     * @Todo Document this
     */
    public static class GetMyCompaniesResponseModel {
        private Boolean status;
        private Object message;
        private List<CompanyServiceModel.CompanyModel> data;

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public Object getMessage() {
            return message;
        }

        public void setMessage(Object message) {
            this.message = message;
        }

        public List<CompanyServiceModel.CompanyModel> getData() {
            return data;
        }

        public void setData(List<CompanyServiceModel.CompanyModel> data) {
            this.data = data;
        }
    }

    /**
     * @Todo Document this
     */
    public static class GetCompaniesRequestModel {
        private String userToken;

        public GetCompaniesRequestModel(String userToken) {
            this.userToken = "Bearer " + userToken;
        }

        public String getUserToken() {
            return userToken;
        }

        public void setUserToken(String userToken) {
            this.userToken = "Bearer " + userToken;
        }
    }


    /**
     * @Todo Document this
     */
    public static class UpdateCompanyRequestModel {
        private String company_id;
        private String company_name;
        private String company_location;

        public UpdateCompanyRequestModel(String company_id, String company_name, String company_location) {
            this.company_id = company_id;
            this.company_name = company_name;
            this.company_location = company_location;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getCompany_location() {
            return company_location;
        }

        public void setCompany_location(String company_location) {
            this.company_location = company_location;
        }

    }

    /**
     * @Todo Document this
     */
    public static class UpdateCompanyResponseModel {
        CompanyModel data;
        private Boolean status;
        private Object message;

        public CompanyModel getData() {
            return data;
        }

        public void setData(CompanyModel data) {
            this.data = data;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public Object getMessage() {
            return message;
        }

        public void setMessage(Object message) {
            this.message = message;
        }
    }

    /**
     * @Todo Document this
     */
    public static class CreateCompanyRequestModel {
        private String company_name;
        private String company_location;

        public CreateCompanyRequestModel(String company_name, String company_location) {
            this.company_name = company_name;
            this.company_location = company_location;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getCompany_location() {
            return company_location;
        }

        public void setCompany_location(String company_location) {
            this.company_location = company_location;
        }

    }


    /**
     * @TODO Document this
     */
    public static class CreateCompanyResponseModel {
        private Boolean status;
        private String message;
        private Object data;

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

    public static class DeleteCompanyRequestModel {
        private String company_id;

        public DeleteCompanyRequestModel(String company_id) {
            this.company_id = company_id;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }
    }

    public static class DeleteCompanyResponseModel {
        private Boolean status;
        private String message;
        private Object data;

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

}
