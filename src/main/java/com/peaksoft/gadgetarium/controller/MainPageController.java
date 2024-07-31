package com.peaksoft.gadgetarium.controller;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainPageController {

    private final ProductService productService;

    @GetMapping("/page")
    public ProductService.MainPage getMainPage() {
        return productService.getMainPage();
    }

    @GetMapping("/discounted")
    public List<ProductResponse> getDiscountedProducts() {
        return productService.findDiscountedProducts();
    }

    @GetMapping("/new-arrivals")
    public List<ProductResponse> getNewArrivals() {
        return productService.findNewDevices();
    }

    @GetMapping("/recommended")
    public List<ProductResponse> getRecommendedProducts() {
        return productService.findRecommendedProducts();
    }
}
