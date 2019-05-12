package com.farzinfaghihi.thinkific.v1.integer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserIntegerDao extends CrudRepository<UserInteger, Long> {

    /**
     * Spring JPA offers basic CRUD operations for free, (ex. findById, save),
     * and we can build our own operations using native SQL queries
     */

    /**
     * Find the last row in the UserInteger table, for the passed in user
     * @param userId
     * @return Optional UserInteger
     */
    @Query(value = "SELECT * FROM user_integer AS a INNER JOIN user AS b ON a.user_id = b.id " +
            "WHERE a.user_id = :userId " +
            "ORDER BY a.id DESC LIMIT 1", nativeQuery = true)
    Optional<UserInteger> findLatestForUserId(Long userId);
}
