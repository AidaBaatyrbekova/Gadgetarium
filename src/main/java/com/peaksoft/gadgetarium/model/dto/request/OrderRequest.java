package com.peaksoft.gadgetarium.model.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {

    String name;
    String firstName;
    String email;
    String phoneNumber;
    String address;
}
