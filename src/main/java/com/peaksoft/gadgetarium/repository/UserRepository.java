package com.peaksoft.gadgetarium.repository;


import com.peaksoft.gadgetarium.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Репозиторий для доступа к данным пользователей.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select user from User user where user.email=:email")
    Optional<User> findByEmail(@Param("email") String email);
}
