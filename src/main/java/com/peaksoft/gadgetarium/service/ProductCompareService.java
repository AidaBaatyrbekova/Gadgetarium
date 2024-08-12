package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.exception.ExceptionMessage;
import com.peaksoft.gadgetarium.exception.NotFoundException;
import com.peaksoft.gadgetarium.model.dto.response.ProductResponse;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.model.entities.User;
import com.peaksoft.gadgetarium.repository.ProductRepository;
import com.peaksoft.gadgetarium.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductCompareService {

    ProductRepository productRepository;
    UserRepository userRepository;

    // Получить пользователя из контекста безопасности через Principal
    private User getCurrentUser(Principal principal) {
        if (principal == null) {
            throw new NotFoundException("User not authenticated");
        }
        String email = principal.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found for email: {}", email);
                    return new NotFoundException(ExceptionMessage.USER_NOT_FOUND);
                });
    }

    // Добавить продукт в список сравнения
    public ResponseEntity<String> addProductToComparison(Long productId, Principal principal) {
        User user = getCurrentUser(principal);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND));

        if (user.getComparedProducts().contains(product)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Product is already in the compare list");
        }

        user.getComparedProducts().add(product);
        userRepository.save(user);

        // Получаем имя категории продукта
        String categoryName = product.getSubCategory().getCategoryOfSubCategory().getElectronicType();

        return ResponseEntity.ok("Product added to compare successfully. Category: " + categoryName);
    }

    // Удалить продукт из списка сравнения по id
    public ResponseEntity<String> removeProductFromComparison(Long productId, Principal principal) {
        User user = getCurrentUser(principal);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND));

        if (user.getComparedProducts().remove(product)) {
            userRepository.save(user);
            return ResponseEntity.ok("Product removed from compare successfully");
        } else {
            return ResponseEntity.ok("Product not found in the compare list");
        }
    }

    // Очистить список сравнения
    public ResponseEntity<String> clearComparisonList(Principal principal) {
        User user = getCurrentUser(principal);
        if (user.getComparedProducts().isEmpty()) {
            return ResponseEntity.ok("Compare list is already empty");
        }
        user.getComparedProducts().clear();
        userRepository.save(user);

        return ResponseEntity.ok("Compare list cleared successfully");
    }

    // Получить все продукты из списка сравнения
    public ResponseEntity<List<ProductResponse>> getAllProductsInComparison(Principal principal) {
        User user = getCurrentUser(principal);
        List<ProductResponse> comparedProducts = user.getComparedProducts().stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(comparedProducts);
    }

    // Метод для конвертации Product в ProductResponse
    private ProductResponse convertToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productStatus(product.getProductStatus())
                .operationMemory(product.getOperationMemory())
                .operationSystem(product.getOperationSystem())
                .subCategory(product.getSubCategory())
                .brand(product.getBrand())
                .createDate(product.getCreateDate())
                .memory(product.getMemory())
                .color(product.getColor())
                .operationSystemNum(product.getOperationSystemNum())
                .dateOfRelease(product.getDateOfRelease())
                .processor(product.getProcessor())
                .guarantee(product.getGuarantee())
                .screen(product.getScreen())
                .simCard(product.getSimCard())
                .rating(product.getRating())
                .discount(product.getDiscount())
                .weight(product.getWeight())
                .price(product.getPrice())
                .build();
    }


    // Сравнить продукты по категории
    public ResponseEntity<String> compareProductsByCategory(Long categoryId, boolean showDifferences, Principal principal) {
        User user = getCurrentUser(principal);
        List<Product> products;

        // Если categoryId равен null, сравниваем все продукты
        if (categoryId == null) {
            products = user.getComparedProducts();
        } else {
            products = filterProductsByCategory(user.getComparedProducts(), categoryId);
        }

        if (products.size() < 2) {
            return ResponseEntity.ok("Not enough products to compare.");
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < products.size(); i++) {
            for (int j = i + 1; j < products.size(); j++) {
                Product p1 = products.get(i);
                Product p2 = products.get(j);
                Map<String, String[]> properties = getProductProperties(p1, p2);

                if (showDifferences) {
                    String comparisonResult = getDifferences(properties);
                    if (!comparisonResult.isEmpty()) {
                        result.append("Comparison between Product ").append(p1.getId())
                                .append(" and Product ").append(p2.getId()).append(":\n")
                                .append(comparisonResult).append("\n");
                    }
                } else {
                    result.append("Comparison between Product ").append(p1.getId())
                            .append(" and Product ").append(p2.getId())
                            .append(": \nThe products are compared.\n");
                }
            }
        }
        return ResponseEntity.ok(result.toString());
    }

    // Фильтровать продукты по категории
    private List<Product> filterProductsByCategory(List<Product> products, Long categoryId) {
        return products.stream()
                .filter(product -> product.getSubCategory().getCategoryOfSubCategory().getId().equals(categoryId))
                .collect(Collectors.toList());
    }

    // Получить различия между двумя продуктами
    private String getDifferences(Map<String, String[]> properties) {
        StringBuilder differences = new StringBuilder();
        properties.forEach((key, value) -> {
            if (!value[0].equals(value[1])) {
                differences.append(key).append(": ").append(value[0]).append(" vs ").append(value[1]).append("\n");
            }
        });
        return differences.toString();
    }

    // Получить свойства двух продуктов для сравнения
    private Map<String, String[]> getProductProperties(Product p1, Product p2) {
        return Map.of(
                "Brand", new String[]{p1.getBrand().getBrandName(), p2.getBrand().getBrandName()},
                "Screen", new String[]{p1.getScreen(), p2.getScreen()},
                "Color", new String[]{p1.getColor().name(), p2.getColor().name()},
                "Operation System", new String[]{p1.getOperationSystem().name(), p2.getOperationSystem().name()},
                "Memory", new String[]{p1.getMemory().name(), p2.getMemory().name()},
                "Weight", new String[]{String.valueOf(p1.getWeight()), String.valueOf(p2.getWeight())},
                "Sim Card", new String[]{p1.getSimCard(), p2.getSimCard()}
        );
    }
}