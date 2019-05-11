package com.farzinfaghihi.thinkific.v1;

public class ResponseError {

    private final ResponseErrorDetail error;

    public ResponseError(ResponseErrorDetail error) {
        this.error = error;
    }

    // Getters

    public ResponseErrorDetail getError() {
        return error;
    }
}
