package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.response.BasketSummaryResponse;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.service.BasketService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/basket")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BasketController {

    BasketService basketService;

    @PostMapping("/addProduct/{productId}")
    public ResponseEntity<String> addProductToBasket(@PathVariable Long productId, Principal principal) {
        return basketService.addProductToBasket(productId, principal);
    }

    @GetMapping("/getAllProducts")
    public BasketSummaryResponse getProductsFromBasket(Principal principal) {
        return basketService.getProductsFromBasket(principal);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<String> deleteProductFromBasket(@PathVariable Long productId, Principal principal) {
        return basketService.deleteProductFromBasket(productId, principal);
    }

    @GetMapping("/getProductById/{productId}")
    public ProductResponse getProductById(@PathVariable Long productId, Principal principal) {
        return basketService.getProductById(productId, principal);
    }
}