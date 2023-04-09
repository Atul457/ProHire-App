package com.example.crosstalk.models;

import javax.annotation.Generated;

@Generated("ByRutuja")
public class CompanyServiceModel {

    public static class CompanyModel{

        private String _id;
        private String company_name;
        private Number company_location;
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

        public Number getCompany_location() {
            return company_location;
        }

        public void setCompany_location(Number company_location) {
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

}
