package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.request.BasketRequest;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.service.BasketService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/basket")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BasketController {

    BasketService basketService;

    @PostMapping("/addProduct")
    public ResponseEntity<String> addProductToBasket(@RequestBody BasketRequest request) {
        return basketService.addProductToBasket(request);
    }

    @GetMapping("/getProduct")
    public List<Product> getProductFromBasket(@RequestBody BasketRequest request) {
        return basketService.getProductsFromBasket(request);
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<String> deleteProductFromBasket(@RequestBody BasketRequest request) {
        return basketService.deleteProductFromBasket(request);
    }
}
