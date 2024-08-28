package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.model.entities.Newsletter;
import com.peaksoft.gadgetarium.repository.NewsletterRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NewsletterService {

    NewsletterRepository newsletterRepository;

    public NewsletterService(NewsletterRepository newsletterRepository) {
        this.newsletterRepository = newsletterRepository;
    }

    public Newsletter saveNewsletter(Newsletter newsletter) {
        return newsletterRepository.save(newsletter);
    }
}