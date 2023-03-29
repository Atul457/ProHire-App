package com.example.crosstalk.utils;

import javax.annotation.Generated;

@Generated("By Rutuja")
public class ErrorHandlingService {

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

    private Boolean status;
    private String message;

}
