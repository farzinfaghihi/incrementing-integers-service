package com.farzinfaghihi.thinkific.v1.integer;

import com.farzinfaghihi.thinkific.v1.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class UserInteger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // This annotation establishes the foreign key relationship to the User table
    // The other required annotation is stated in the User entity
    @ManyToOne
    // This annotation tells Spring to not return the JSON for UserInteger
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    private Integer value;

    @JsonIgnore
    // Used to keep track of the starting value incrementing began from when reset
    private Integer startingResetValue;

    // Getters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Integer getValue() {
        return value;
    }

    public Integer getStartingResetValue() {
        return startingResetValue;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setStartingResetValue(Integer startingResetValue) {
        this.startingResetValue = startingResetValue;
    }
}
