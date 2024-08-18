package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.entities.Newsletter;
import com.peaksoft.gadgetarium.service.NewsletterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "The Newsletter modal")
@RequestMapping("/api/newsletter")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class NewsletterController {

    NewsletterService newsletterService;

    @Autowired
    public NewsletterController(NewsletterService newsletterService) {
        this.newsletterService = newsletterService;
    }

    @Operation(summary = "here we can create the newsletter modal")
    @PostMapping("/create")
    public String createNewsletter(@RequestBody Newsletter newsletter) {
        // Newsletter маалыматтарын базага сактоо
        Newsletter savedNewsletter = newsletterService.saveNewsletter(newsletter);
        return "Рассылка '" + savedNewsletter.getTitle() + "' успешно создана и сохранена в базе данных.";
    }
}
