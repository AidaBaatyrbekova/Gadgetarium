package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.exception.ExceptionMessage;
import com.peaksoft.gadgetarium.exception.NotFoundException;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.model.entities.User;
import com.peaksoft.gadgetarium.repository.ProductRepository;
import com.peaksoft.gadgetarium.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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

    // Получить пользователя из контекста безопасности
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            throw new NotFoundException("User not authenticated");
        }
        String email = userDetails.getUsername();


        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found for email: {}", email);
                    return new NotFoundException(ExceptionMessage.USER_NOT_FOUND);
                });
    }

    // Добавить продукт в список сравнения
    public void addProductToComparison(Long productId) {
        User user = getCurrentUser();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND));
        user.getComparedProducts().add(product);
        userRepository.save(user);
    }

    // Удалить продукт из списка сравнения по id
    public void removeProductFromComparison(Long productId) {
        User user = getCurrentUser();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.PRODUCT_NOT_FOUND));

        user.getComparedProducts().remove(product);
        userRepository.save(user);
    }

    // Очистить список сравнения
    public void clearComparisonList() {
        User user = getCurrentUser();
        user.getComparedProducts().clear();
        userRepository.save(user);
    }

    // Сравнить продукты по категории
    public String compareProductsByCategory(Long categoryId, boolean showDifferences) {
        User user = getCurrentUser();
        List<Product> products = filterProductsByCategory(user.getComparedProducts(), categoryId);
        if (products.size() < 2) {
            return "Not enough products to compare in the selected category.";
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
        return result.toString();
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