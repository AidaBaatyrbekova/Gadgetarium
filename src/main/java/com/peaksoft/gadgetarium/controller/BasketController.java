package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.response.BasketSummaryResponse;
import com.peaksoft.gadgetarium.model.dto.response.ProductSummaryResponse;
import com.peaksoft.gadgetarium.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Tag(name = "The Basket")
@RequiredArgsConstructor
@RequestMapping("/api/basket")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BasketController {

    BasketService basketService;

    @Operation(summary = "add new product")
    @PostMapping("/addProduct/{productId}")
    public ProductSummaryResponse addProductToBasket(@PathVariable Long productId, Principal principal) {
        return basketService.addProductToBasket(productId, principal);
    }

    @Operation(summary = "get all chosen product")
    @GetMapping("/getAllProducts")
    public BasketSummaryResponse getProductsFromBasket(Principal principal) {
        return basketService.getProductsFromBasket(principal);
    }

    @Operation(summary = "delete product by Id")
    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<String> deleteProductFromBasket(@PathVariable Long productId, Principal principal) {
        return basketService.deleteProductFromBasket(productId, principal);
    }
@Operation(summary = "get product by Id")
    @GetMapping("/getProductById/{productId}")
    public ProductSummaryResponse getProductById(@PathVariable Long productId, Principal principal) {
        return basketService.getProductById(productId, principal);
    }
}