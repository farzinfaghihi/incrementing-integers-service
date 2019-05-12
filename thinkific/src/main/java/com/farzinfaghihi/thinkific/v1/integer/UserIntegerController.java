package com.farzinfaghihi.thinkific.v1.integer;

import com.farzinfaghihi.thinkific.v1.ResponseSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Controller's responsibility is to be the entry point, and leaving point for the API call.
 * The business logic for each API call will be handled within the Service
 */
@RestController
public class UserIntegerController {

    /**
     * The Autowired annotation is for using Spring's built in dependency injection,
     * so we don't have to keep passing around objects in constructors.
     */
    @Autowired
    UserIntegerService userIntegerService;


    @GetMapping(value = "/v1/current")
    public ResponseEntity getCurrentInteger() {
        UserInteger currentUserInteger = userIntegerService.getCurrentUserInteger();
        ResponseSuccess response = new ResponseSuccess(currentUserInteger);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/next")
    public ResponseEntity getNextInteger() {
        UserInteger currentUserInteger = userIntegerService.getNextUserInteger();
        ResponseSuccess response = new ResponseSuccess(currentUserInteger);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/v1/current")
    public ResponseEntity resetInteger(@RequestBody UserInteger userInteger) {
        UserInteger currentUserInteger = userIntegerService.resetUserInteger(userInteger.getValue());
        ResponseSuccess response = new ResponseSuccess(currentUserInteger);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
