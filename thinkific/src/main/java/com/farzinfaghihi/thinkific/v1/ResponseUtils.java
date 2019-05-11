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
        if (errorCode.equals("404")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (errorCode.equals("400")) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else if (errorCode.equals("401")) {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } else if (errorCode.equals("403")) {
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        // Default error code case that isn't handled, map to 500 internal server error
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
