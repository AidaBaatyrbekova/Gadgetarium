package com.peaksoft.gadgetarium.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FavoriteResponse {
    Long userId;
    Long productId;
    ProductResponse productResponse;
}
