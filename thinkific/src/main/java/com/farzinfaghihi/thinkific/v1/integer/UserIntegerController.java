package com.farzinfaghihi.thinkific.v1.integer;

import com.farzinfaghihi.thinkific.v1.ResponseSuccess;
import com.farzinfaghihi.thinkific.v1.ResponseUtils;
import com.farzinfaghihi.thinkific.v1.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserIntegerController {

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
