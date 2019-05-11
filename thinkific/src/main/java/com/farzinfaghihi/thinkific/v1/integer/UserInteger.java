package com.farzinfaghihi.thinkific.v1.integer;

import com.farzinfaghihi.thinkific.v1.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class UserInteger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    private Integer value;

    @JsonIgnore
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
