package com.example.crosstalk.models;

import java.util.List;

public class NotificationServiceModel {

    public static class NotificationModel {
        String message;
        String user_id;
        String job_id;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getJob_id() {
            return job_id;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }
    }

    public static class GetNotificationsResponse {
        Boolean status;
        Object message;
        List<NotificationModel> data;

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

        public List<NotificationModel> getData() {
            return data;
        }

        public void setData(List<NotificationModel> data) {
            this.data = data;
        }
    }

}
