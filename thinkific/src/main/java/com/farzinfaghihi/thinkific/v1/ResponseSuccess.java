package com.farzinfaghihi.thinkific.v1;

public class ResponseSuccess {

    private final Object data;

    public ResponseSuccess(Object data) {
        this.data = data;
    }

    // Getters

    public Object getData() {
        return data;
    }
}
