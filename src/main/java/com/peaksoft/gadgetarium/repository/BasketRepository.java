package com.peaksoft.gadgetarium.repository;

import com.peaksoft.gadgetarium.model.entities.Basket;
import com.peaksoft.gadgetarium.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    Optional<Basket> findByUser(User user);
}