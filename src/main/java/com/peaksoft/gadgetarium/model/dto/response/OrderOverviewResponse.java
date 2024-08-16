package com.peaksoft.gadgetarium.model.dto.response;

import com.peaksoft.gadgetarium.model.enums.DeliveryType;
import com.peaksoft.gadgetarium.model.enums.PaymentType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderOverviewResponse {

    String totalSum;
    String delivery;
    PaymentType payment;
}
