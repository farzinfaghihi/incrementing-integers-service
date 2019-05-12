package com.farzinfaghihi.thinkific.v1.user;

import com.farzinfaghihi.thinkific.v1.integer.UserInteger;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    // This annotation establishes the relationship to the UserInteger table
    // The other required annotation is stated in the UserInteger entity
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserInteger> userIntegers;

    // Getters

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
