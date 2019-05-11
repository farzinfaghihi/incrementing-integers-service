package com.farzinfaghihi.thinkific.v1;

public class ResponseErrorDetail {

    private final String code;

    private final String message;

    public ResponseErrorDetail(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getters

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
