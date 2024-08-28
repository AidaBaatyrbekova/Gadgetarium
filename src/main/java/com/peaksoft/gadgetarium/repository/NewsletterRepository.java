package com.peaksoft.gadgetarium.repository;

import com.peaksoft.gadgetarium.model.entities.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterRepository extends JpaRepository<Newsletter,Long> {
}
