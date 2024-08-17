package com.peaksoft.gadgetarium.model.dto.response;

import com.peaksoft.gadgetarium.model.enums.DeliveryType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {

    Long id;
    DeliveryType delivery;
    String firstName;
    String lastName;
    String email;
    LocalDateTime created;
    String address;
}