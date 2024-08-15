package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.service.MainPageService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MainPageController {

    MainPageService mainPageService;

    @GetMapping("/page")
    public List<ProductResponse> getMainPage() {
        return mainPageService.getMainPage();
    }
    @GetMapping("/discounted")
    public List<ProductResponse> getDiscountedProducts() {
        return mainPageService.findDiscountedProducts();
    }

    @GetMapping("/new-arrivals")
    public List<ProductResponse> getNewArrivals() {
        return mainPageService.findNewDevices();
    }

    @GetMapping("/recommended")
    public List<ProductResponse> getRecommendedProducts() {
        return mainPageService.findRecommendedProducts();
    }
}
