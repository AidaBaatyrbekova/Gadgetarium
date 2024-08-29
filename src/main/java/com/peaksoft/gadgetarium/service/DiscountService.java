package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.model.entities.Discount;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.repository.DiscountRepository;
import com.peaksoft.gadgetarium.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiscountService {

    ProductRepository productRepository;
    DiscountRepository discountRepository;


    public Discount createDiscount(Long productId, int discountRate, LocalDate startDate, LocalDate endDate) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Discount discount = new Discount();
        discount.setDiscountRate(discountRate);
        discount.setStartDate(startDate);
        discount.setEndDate(endDate);
        discount.setProduct(product);

        discountRepository.save(discount);

        // Скидка пайызын эске алуу менен продукттун баасын жаңыртат
        int newPrice = product.getPrice() - (product.getPrice() * discountRate / 100);
        product.setPrice(newPrice);
        productRepository.save(product);
        return discount;
    }

    public List<Product> applyDiscountToProducts(int discountRate) {
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            int newPrice = product.getPrice() - (product.getPrice() * discountRate / 100);
            product.setPrice(newPrice);
            productRepository.save(product);
        }
        return products;
    }
}