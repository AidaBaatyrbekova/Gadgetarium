package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Tag(name = "The Favorites")
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FavoriteController {

    FavoriteService favoriteService;

    @Operation(summary = "User can add favorite products with productId")
    @PostMapping("/add/{productId}")
    public ProductResponse addFavorite(@PathVariable Long productId, Principal principal) {
        return favoriteService.addFavorite(productId, principal);
    }

    @Operation(summary = "The User clear all favorite products")
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearFavorites(Principal principal) {
        return favoriteService.clearFavorites(principal.getName());
    }

    @Operation(summary = "User can find all favorites")
    @GetMapping
    public List<ProductResponse> getFavorites(Principal principal) {
        return favoriteService.getFavorites(principal.getName());

    }

    @Operation(summary = "The User will be delete one by productId ")
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeFavorite(@PathVariable Long productId, Principal principal) {
        return favoriteService.removeFavorite(productId, principal.getName());
    }
}
