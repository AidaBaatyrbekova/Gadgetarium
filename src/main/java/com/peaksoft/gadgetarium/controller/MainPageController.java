package com.peaksoft.gadgetarium.controller;
import com.peaksoft.gadgetarium.service.ProductService;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

    @RestController
    @RequestMapping("/api/main")
    public class MainPageController {

        private final ProductService productService;

        @Autowired
        public MainPageController(ProductService productService) {
            this.productService = productService;
        }

        @GetMapping("/discounted")
        public List<ProductResponse> getDiscountedProducts() {
            return productService.findDiscountedProducts();
        }

        @GetMapping("/new-arrivals")
        public List<ProductResponse> getNewArrivals() {
            return productService.findNewArrivals();
        }

        @GetMapping("/recommended")
        public List<ProductResponse> getRecommendedProducts() {
            return productService.findRecommendedProducts();
        }
    }

