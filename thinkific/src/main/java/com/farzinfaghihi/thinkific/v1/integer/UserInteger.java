package com.farzinfaghihi.thinkific.v1.integer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserInteger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Integer value;

    private Integer startingResetValue;

    // Getters
    public long getId() {
        return id;
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

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setStartingResetValue(Integer startingResetValue) {
        this.startingResetValue = startingResetValue;
    }
}
