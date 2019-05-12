package com.farzinfaghihi.thinkific.v1.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<User, Long> {

    /**
     * Spring JPA offers basic CRUD operations for free (ex. findById, save),
     * and using a specific method name format, we can use additional queries
     */

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
