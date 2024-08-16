package com.peaksoft.gadgetarium.model.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderFinishResponse {

    String message;
    String applicationNumber;
    String status;
    String localDate;
    String email;

    public OrderFinishResponse(String errorMessage, String applicationNumber, String status, String localDate, String email) {
        this.message = errorMessage;
        this.applicationNumber = applicationNumber;
        this.status = status;
        this.localDate = localDate;
        this.email = email;
    }
}
