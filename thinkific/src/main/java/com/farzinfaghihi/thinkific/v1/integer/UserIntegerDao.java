package com.farzinfaghihi.thinkific.v1.integer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserIntegerDao extends CrudRepository<UserInteger, Long> {

    @Query(value = "SELECT * FROM user_integer ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<UserInteger> findLatest();
}
