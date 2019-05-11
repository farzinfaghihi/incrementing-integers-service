package com.farzinfaghihi.thinkific.v1.integer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserIntegerService {

    @Autowired
    UserIntegerDao userIntegerDao;

    public UserInteger getCurrentUserInteger() {
        // Fetch the latest row in the UserInteger table
        Optional<UserInteger> currentUserInteger = userIntegerDao.findLatest();
        // If there are no entries, create a UserInteger with the value 1
        if (currentUserInteger.isEmpty()) {
            UserInteger userInteger = new UserInteger();
            userInteger.setValue(1);
            userInteger.setStartingResetValue(1);
            return userIntegerDao.save(userInteger);
        }
        return currentUserInteger.get();
    }

    public UserInteger getNextUserInteger() {
        // Get the current UserInteger
        UserInteger currentUserInteger = getCurrentUserInteger();
        // Update the integer value and save
        currentUserInteger.setValue(currentUserInteger.getValue() + 1);
        return userIntegerDao.save(currentUserInteger);
    }

    /**
     * Reset the current integer by making a new entry in the UserInteger table with the passed in value.
     * This will now be the latest entry grabbed by the getCurrentUserInteger method
     *
     * @param resetValue Integer to create entry for in the UserInteger table
     * @return UserInteger object
     */
    public UserInteger resetUserInteger(Integer resetValue) {
        // Get the current UserInteger
        UserInteger currentUserInteger = getCurrentUserInteger();
        if (!currentUserInteger.getValue().equals(resetValue)) {
            UserInteger resetUserInteger = new UserInteger();
            resetUserInteger.setValue(resetValue);
            resetUserInteger.setStartingResetValue(resetValue);
            return userIntegerDao.save(resetUserInteger);
        }
        return currentUserInteger;
    }
}
