package com.farzinfaghihi.thinkific.v1.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.farzinfaghihi.thinkific.v1.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping(value = "/v1/users")
    public ResponseEntity createUser(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Optional<User> createdUser = userService.createUser(user);
        if (createdUser.isPresent()) {
            String tokenSubject = createdUser.get().getId().toString();
            // Create a JWT token for the apiKey to be used in Bearer Authorization
            // An expiration is not set as per the requirements, but can easily be added with the withExpiresAt method in the JWT library
            String apiKey = JWT.create()
                    .withSubject(tokenSubject)
                    .sign(Algorithm.HMAC512("blueberry".getBytes()));
            AuthenticationResponse authenticationResponse = new AuthenticationResponse(apiKey);
            ResponseSuccess response = new ResponseSuccess(authenticationResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ResponseErrorDetail responseErrorDetail = new ResponseErrorDetail("400", "A user exists with this email address.");
        List<ResponseErrorDetail> errors = new ArrayList<>();
        errors.add(responseErrorDetail);
        ResponseError response = new ResponseError(errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
