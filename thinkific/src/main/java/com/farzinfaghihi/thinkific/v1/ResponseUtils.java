package com.farzinfaghihi.thinkific.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class ResponseUtils {

    public static ResponseEntity getResponseForOptional(Optional optional, String errorCode, String errorMessage) {
        if (optional.isPresent()) {
            Object o = optional.get();
            ResponseSuccess response = new ResponseSuccess(o);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }
        ResponseErrorDetail responseErrorDetail = new ResponseErrorDetail(errorCode, errorMessage);
        ResponseError response = new ResponseError(responseErrorDetail);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
