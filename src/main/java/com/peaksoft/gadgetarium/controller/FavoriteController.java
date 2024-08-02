package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.response.FavoriteResponse;
import com.peaksoft.gadgetarium.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FavoriteController {

UserService userService;


    @PostMapping("/addFavorite/{productId}")
    public FavoriteResponse addFavorite(@PathVariable Long productId,Principal principal) {
        return userService.addFavorite(productId,principal);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearFavorites(@PathVariable Long userId) {
        userService.clearFavorites(userId);
        return ResponseEntity.noContent().build();
    }
}
