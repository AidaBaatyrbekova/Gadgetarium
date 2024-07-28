package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.service.ProductService;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/main")
public class MainPage {

    private final ProductService productService;

    @Autowired
    public MainPage(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public String display(Model model) {
        List<ProductResponse> discountedProducts = productService.findDiscountedProducts();
        List<ProductResponse> newArrivals = productService.findNewArrivals();
        List<ProductResponse> recommendedProducts = productService.findRecommendedProducts();

        model.addAttribute("discountedProducts", discountedProducts);
        model.addAttribute("newArrivals", newArrivals);
        model.addAttribute("recommendedProducts", recommendedProducts);
        return "mainPage";
    }
}

