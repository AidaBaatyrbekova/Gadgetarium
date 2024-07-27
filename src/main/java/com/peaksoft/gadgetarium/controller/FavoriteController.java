package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.dto.request.FavoriteRequest;
import com.peaksoft.gadgetarium.model.dto.request.ProductRequest;
import com.peaksoft.gadgetarium.model.dto.response.FavoriteResponse;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FavoriteController {

UserService userService;

    @PostMapping("/save")
    public FavoriteResponse save(@RequestBody FavoriteRequest request) {
        return userService.addFavorite(request);
    }

    @PostMapping("/addFavorite")
    public ResponseEntity<FavoriteResponse> addFavorite(@RequestBody FavoriteRequest request) {
        FavoriteResponse response = userService.addFavorite(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearFavorites(@PathVariable Long userId) {
        userService.clearFavorites(userId);
        return ResponseEntity.noContent().build();
    }
}
