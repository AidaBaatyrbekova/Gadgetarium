package com.peaksoft.gadgetarium.repository;

import com.peaksoft.gadgetarium.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
