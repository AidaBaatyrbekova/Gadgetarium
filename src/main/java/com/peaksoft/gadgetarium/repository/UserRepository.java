package com.peaksoft.gadgetarium.repository;

import com.peaksoft.gadgetarium.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<com.peaksoft.gadgetarium.model.User, Long>{
    User findByUsername(String username);
}
