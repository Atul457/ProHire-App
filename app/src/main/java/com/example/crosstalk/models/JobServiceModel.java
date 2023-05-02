package com.example.crosstalk.models;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

@Generated("ByRutuja")
public class JobServiceModel {

    /**
     * @info Sets the params dynamically as per requirements
     * @returns GetJobsRequestModel type
     */
    public static class GetJobsRequestModel {

        /**
         * @info String you want to search
         */
        private String q;
        /**
         * @info Page of whose data you want to get
         */
        private Number page;
        /**
         * @info Order in which data needs to be got, this can be asc|desc
         */
        private String order;
        /**
         * @info Limit is the number of jobs you want per page
         */
        private Number limit;
        /**
         * @info This stands for lowest salary, can be used to get the jobs,
         * whose salaries are more than this
         */
        private Number ls;
        /**
         * @info This stands for lowest salary, can be used to get the jobs,
         * whose salaries is less than this
         */
        private Number hs;


        /**
         * @info This will be used to create query string that will be used by retrofit
         */
        public static Map<String, String> queryString;

        public GetJobsRequestModel(Object q, Object page, Object order, Object limit, Object ls, Object hs) {

            this.queryString = new HashMap<>();

            if (q instanceof String) {
                this.q = (String) q;
                this.queryString.put("q", (String) q);
            }

            if (page instanceof Number) {
                this.page = (Number) page;
                this.queryString.put("page", page.toString());
            }

            if (order instanceof String) {
                this.order = (String) order;
                this.queryString.put("order", (String) order);
            }

            if (limit instanceof Number) {
                this.limit = (Number) limit;
                this.queryString.put("limit", limit.toString());
            }

            if (ls instanceof Number) {
                this.ls = (Number) ls;
                this.queryString.put("ls", ls.toString());
            }

            if (hs instanceof Number) {
                this.hs = (Number) hs;
                this.queryString.put("hs", hs.toString());
            }

        }

        public Map<String, String> getQueryString() {
            return queryString;
        }

        public void setQueryString(Map<String, String> queryString) {
            GetJobsRequestModel.queryString = queryString;
        }

        public String getQ() {
            return q;
        }

        public void setQ(String q) {
            this.queryString.put("q", q);
            this.q = q;
        }

        public Number getPage() {
            return page;
        }

        public void setPage(Number page) {
            this.queryString.put("page", page.toString());
            this.page = page;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.queryString.put("order", order);
            this.order = order;
        }

        public Number getLimit() {
            return limit;
        }

        public void setLimit(Number limit) {
            this.queryString.put("limit", limit.toString());
            this.limit = limit;
        }

        public Number getLs() {
            return ls;
        }

        public void setLs(Number ls) {
            this.queryString.put("ls", ls.toString());
            this.ls = ls;
        }

        public Number getHs() {
            return hs;
        }

        public void setHs(Number hs) {
            this.queryString.put("hs", hs.toString());
            this.hs = hs;
        }
    }

    public class GetApplicationsOfJobResponse {
        private Boolean status;
        private Object message;
        private List<UserServiceModel.UserModel> data;

        public GetApplicationsOfJobResponse(Boolean status, Object message, List<UserServiceModel.UserModel> data) {
            this.status = status;
            this.message = message;
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

        public List<UserServiceModel.UserModel> getData() {
            return data;
        }

        public void setData(List<UserServiceModel.UserModel> data) {
            this.data = data;
        }
    }

    /**
     * @returns GetJobsResponseModel type
     */
    public static class GetJobsResponseModel {
        private Boolean status;
        private Object message;
        private List<JobModel> data;


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

        public List<JobModel> getData() {
            return data;
        }

        public void setData(List<JobModel> data) {
            this.data = data;
        }
    }


    /**
     * @info Model Type of job
     */
    public static class JobModel {
        private String _id;
        private String job_name;
        private Number reviews;
        private String company_name;
        private String company_location;
        private Number job_salary;
        private String job_description;
        private String created_at;
        private String updated_at;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getJob_name() {
            return job_name;
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

        public void setJob_name(String job_name) {
            this.job_name = job_name;
        }

        public Number getReviews() {
            return reviews;
        }

        public void setReviews(Number reviews) {
            this.reviews = reviews;
        }

        public Number getJob_salary() {
            return job_salary;
        }

        public void setJob_salary(Number job_salary) {
            this.job_salary = job_salary;
        }

        public String getJob_description() {
            return job_description;
        }

        public void setJob_description(String job_description) {
            this.job_description = job_description;
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
     * @info Response type model for job detail page
     */
    public static class GetSingleJobModel {

        private Boolean status;
        private Object message;
        private List<SingleJobModel> data;

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

        public List<SingleJobModel> getData() {
            return data;
        }

        public void setData(@NonNull List<SingleJobModel> data) {
            this.data = data;
        }


    }


    public static class SingleJobModel extends JobModel {

        private String user_id;
        private String user_name;
        private String user_phone;
        private String user_email;
        private Boolean is_my_job;

        public Boolean getIs_my_job() {
            return is_my_job;
        }

        public void setIs_my_job(Boolean is_my_job) {
            this.is_my_job = is_my_job;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

    }


    /**
     * @TODO Document this
     */
    public static class MyJobModel {
        private String _id;
        private String job_name;
        private Number reviews;
        private Number job_salary;
        private String job_description;
        private String created_at;
        private String updated_at;


        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getJob_name() {
            return job_name;
        }

        public void setJob_name(String job_name) {
            this.job_name = job_name;
        }

        public Number getReviews() {
            return reviews;
        }

        public void setReviews(Number reviews) {
            this.reviews = reviews;
        }

        public Number getJob_salary() {
            return job_salary;
        }

        public void setJob_salary(Number job_salary) {
            this.job_salary = job_salary;
        }

        public String getJob_description() {
            return job_description;
        }

        public void setJob_description(String job_description) {
            this.job_description = job_description;
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
     * @TODO Document this
     */
    public static class GetJobsOfCompanyResponseModel {
        private Boolean status;
        private Object message;
        private List<MyJobModel> data;

        public List<MyJobModel> getData() {
            return data;
        }

        public void setData(List<MyJobModel> data) {
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
    public static class UpdateJobRequestModel {
        private String job_id;
        private Long job_salary;
        private String job_name;
        private String job_description;


        public UpdateJobRequestModel(String job_id, Long job_salary, String job_name, String job_description) {
            this.job_id = job_id;
            this.job_salary = job_salary;
            this.job_name = job_name;
            this.job_description = job_description;
        }

        public String getJob_id() {
            return job_id;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }

        public Long getJob_salary() {
            return job_salary;
        }

        public void setJob_salary(Long job_salary) {
            this.job_salary = job_salary;
        }

        public String getJob_name() {
            return job_name;
        }

        public void setJob_name(String job_name) {
            this.job_name = job_name;
        }

        public String getJob_description() {
            return job_description;
        }

        public void setJob_description(String job_description) {
            this.job_description = job_description;
        }
    }


    public static class DeleteJobRequestModel {
        private String job_id;

        public DeleteJobRequestModel(String job_id) {
            this.job_id = job_id;
        }

        public String getJob_id() {
            return job_id;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }
    }


    /**
     * @Todo Document this
     */
    public static class UpdateJobResponseModel {
        JobServiceModel.JobModel data;
        private Boolean status;
        private Object message;

        public JobModel getData() {
            return data;
        }

        public void setData(JobModel data) {
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
    public static class DeleteJobResponseModel {
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


    /**
     * @Todo Document this
     */
    public static class CreateJobRequestModel {
        private String job_name;
        private Long job_salary;
        private String company_id;
        private String job_description;

        public CreateJobRequestModel(String job_name, Long job_salary, String company_id, String job_description) {
            this.job_name = job_name;
            this.job_salary = job_salary;
            this.company_id = company_id;
            this.job_description = job_description;
        }

        public String getJob_name() {
            return job_name;
        }

        public void setJob_name(String job_name) {
            this.job_name = job_name;
        }

        public Long getJob_salary() {
            return job_salary;
        }

        public void setJob_salary(Long job_salary) {
            this.job_salary = job_salary;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getJob_description() {
            return job_description;
        }

        public void setJob_description(String job_description) {
            this.job_description = job_description;
        }
    }


    /**
     * @TODO Document this
     */
    public static class CreateJobResponseModel {
        public Boolean status;
        public String message;
        public JobModel data;

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

        public JobModel getData() {
            return data;
        }

        public void setData(JobModel data) {
            this.data = data;
        }
    }

    /**
     * @TODO Document this
     */
    public static class ApplyForJobResponse extends CreateJobResponseModel {

    }


}
