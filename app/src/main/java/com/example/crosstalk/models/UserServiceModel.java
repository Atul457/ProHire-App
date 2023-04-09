package com.example.crosstalk.models;

import javax.annotation.Generated;

@Generated("ByRutuja")
public class UserServiceModel {

    /**
     * @info Login service
     * @params email-string, password string
     * @returns LoginResponse type
     * */
    public static class LoginRequestModel {

        private String email;
        private String password;

        public LoginRequestModel(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }


    /**
     * @info Login request response modal
     * */
    public static class ResponseModel {

        private Boolean status;
        private Object message;
        private ResponseDataModel data;

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

        public ResponseDataModel getData() {
            return data;
        }

        public void setData(ResponseDataModel data) {
            this.data = data;
        }


        public class ResponseDataModel{
            private String email;
            private String token;
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }

    }


    /**
     * @info register service
     * @params email-string, phone string, password string, name: string
     * @returns LoginResponse type
     * */
    public static class RegisterRequestModel{

        String name;
        String phone;
        String password;
        String email;

        public RegisterRequestModel(String name, String phone, String password, String email) {
            this.name = name;
            this.phone = phone;
            this.password = password;
            this.email = email;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

}
