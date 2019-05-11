package com.farzinfaghihi.thinkific.v1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public Optional<User> getUserById(Long id) {
        return userDao.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public Optional<User> createUser(User user) {
        if (!userDao.existsByEmail(user.getEmail())) {
            return Optional.of(userDao.save(user));
        }
        return Optional.empty();
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
