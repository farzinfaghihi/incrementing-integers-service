package com.farzinfaghihi.thinkific.v1;

import java.util.List;

public class ResponseError {

    private final List<ResponseErrorDetail> errors;

    public ResponseError(List<ResponseErrorDetail> errors) {
        this.errors = errors;
    }

    // Getters

    public List<ResponseErrorDetail> getErrors() {
        return errors;
    }
}
