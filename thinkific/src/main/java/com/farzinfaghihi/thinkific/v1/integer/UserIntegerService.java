package com.farzinfaghihi.thinkific.v1.integer;

import com.farzinfaghihi.thinkific.v1.user.User;
import com.farzinfaghihi.thinkific.v1.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserIntegerService {

    @Autowired
    UserIntegerDao userIntegerDao;

    @Autowired
    UserService userService;

    public UserInteger getCurrentUserInteger() {
        User currentUser = userService.getAuthenticatedUser();

        // Fetch the latest row in the UserInteger table for the User
        Optional<UserInteger> currentUserInteger = userIntegerDao.findLatestForUserId(currentUser.getId());
        // If there are no entries, create a UserInteger with the value 1
        if (currentUserInteger.isEmpty()) {
            UserInteger userInteger = new UserInteger();
            userInteger.setUser(currentUser);
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
            resetUserInteger.setUser(currentUserInteger.getUser());
            resetUserInteger.setValue(resetValue);
            resetUserInteger.setStartingResetValue(resetValue);
            return userIntegerDao.save(resetUserInteger);
        }
        return currentUserInteger;
    }
}
