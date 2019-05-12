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

    /**
     * Get the UserInteger for the currently authenticated user. If none exists, create a entry,
     * with 1 as the default initial value
     * @return A UserInteger
     */
    public UserInteger getCurrentUserInteger() {
        User currentUser = userService.getAuthenticatedUser();

        // Fetch the latest row in the UserInteger table for the User
        Optional<UserInteger> currentUserInteger = userIntegerDao.findLatestForUserId(currentUser.getId());
        // If there are no entries, create a UserInteger with the value 1
        if (!currentUserInteger.isPresent()) {
            UserInteger userInteger = new UserInteger();
            // Set the authenticated user on the UserInteger object, to manage the relationship
            userInteger.setUser(currentUser);
            userInteger.setValue(1);
            userInteger.setStartingResetValue(1);
            return userIntegerDao.save(userInteger);
        }
        return currentUserInteger.get();
    }

    /**
     * Get the next UserInteger for the currently authenticated user, by incremented the current value by 1,
     * and updated the entry.
     * @return
     */
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
     * @return A UserInteger
     */
    public UserInteger resetUserInteger(Integer resetValue) {
        // Get the current UserInteger
        UserInteger currentUserInteger = getCurrentUserInteger();
        if (!currentUserInteger.getValue().equals(resetValue)) {
            UserInteger resetUserInteger = new UserInteger();
            // Set the authenticated user on the UserInteger object, to manage the relationship
            resetUserInteger.setUser(currentUserInteger.getUser());
            resetUserInteger.setValue(resetValue);
            // Save the reset value, which will not update when we increment the main value
            resetUserInteger.setStartingResetValue(resetValue);
            return userIntegerDao.save(resetUserInteger);
        }
        return currentUserInteger;
    }
}
