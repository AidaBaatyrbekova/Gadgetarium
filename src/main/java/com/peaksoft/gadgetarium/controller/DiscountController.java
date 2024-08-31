package com.peaksoft.gadgetarium.controller;

import com.peaksoft.gadgetarium.model.entities.Discount;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.service.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@Tag(name = "The Discount-Product")
@RequiredArgsConstructor
@RequestMapping("/api/discounts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiscountController {

    DiscountService discountService;

@Operation(summary = "Метод бир продукт үчүн 15% скидканы түзүп, аны базада сактайт." +
        " Ошондой эле скидканын башталуу жана аяктаган күндөрүн кошот.")
    @PostMapping("/create")
    public Discount createDiscount(@RequestParam Long productId,
                                   @RequestParam String startDate,
                                   @RequestParam String endDate) {

        int discountRate = 15; // Fixed 15% discount
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return discountService.createDiscount(productId, discountRate, start, end);
    }

    @Operation(summary = "Ар бир продукттун баасына 15% скидканы колдонуп, жаңы баасын эсептейт.")
    @PostMapping("/apply")
    public List<Product> applyDiscountToProducts() {
        int discountRate = 15; // Fixed 15% discount for all products
        return discountService.applyDiscountToProducts(discountRate);
    }
}
