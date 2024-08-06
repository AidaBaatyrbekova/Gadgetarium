package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.response.FavoriteResponse;
import com.peaksoft.gadgetarium.service.FavoriteService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FavoriteController {

    FavoriteService favoriteService;


    @PostMapping("/addFavorite/{productId}")
    public FavoriteResponse addFavorite(@PathVariable Long productId, Principal principal) {
        return favoriteService.addFavorite(productId, principal);
    }

    @DeleteMapping("/clearFavorites")
    public ResponseEntity<String> clearFavorites(Principal principal) {
       return favoriteService.clearFavorites(principal.getName());
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<FavoriteResponse>> getFavorites(Principal principal) {
        List<FavoriteResponse> favorites = favoriteService.getFavorites(principal.getName());
        return ResponseEntity.ok(favorites);
    }

    @DeleteMapping("/removeFavorite/{productId}")
    public ResponseEntity<String> removeFavorite(@PathVariable Long productId, Principal principal) {
       return favoriteService.removeFavorite(productId, principal.getName());
    }
}
