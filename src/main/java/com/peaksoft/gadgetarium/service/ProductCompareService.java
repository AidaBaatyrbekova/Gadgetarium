package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.model.entities.Category;
import com.peaksoft.gadgetarium.model.entities.ComparisonList;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.repository.CategoryRepository;
import com.peaksoft.gadgetarium.repository.ComparisonListRepository;
import com.peaksoft.gadgetarium.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductCompareService {

    ProductRepository productRepository;
    ComparisonListRepository comparisonListRepository;
    CategoryRepository categoryRepository;

    // Создание нового списка сравнения
    public ComparisonList createComparisonList(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            throw new IllegalArgumentException("Category not found");
        }

        ComparisonList comparisonList = new ComparisonList();
        comparisonList.setCategory(category);
        return comparisonListRepository.save(comparisonList);
    }

    // Добавление товара в список сравнения
    public void addProductToComparisonList(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId).orElse(null);
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (product != null && category != null) {
            ComparisonList comparisonList = comparisonListRepository.findByCategory(category).stream()
                    .findFirst()
                    .orElseGet(() -> createComparisonList(categoryId));

            comparisonList.getProducts().add(product);
            comparisonListRepository.save(comparisonList);
        }
    }

    // Удаление товара из списка сравнения
    public void removeProductFromComparisonList(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId).orElse(null);
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (product != null && category != null) {
            ComparisonList comparisonList = comparisonListRepository.findByCategory(category).stream()
                    .findFirst()
                    .orElse(null);

            if (comparisonList != null) {
                comparisonList.getProducts().remove(product);
                comparisonListRepository.save(comparisonList);
            }
        }
    }

    // Очистка всего списка сравнения по категории
    public void clearComparisonList(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null) {
            ComparisonList comparisonList = comparisonListRepository.findByCategory(category).stream()
                    .findFirst()
                    .orElse(null);

            if (comparisonList != null) {
                comparisonList.getProducts().clear();
                comparisonListRepository.save(comparisonList);
            }
        }
    }

    // Сравнение товаров в списке по категории
    public String compareProducts(Long categoryId, boolean showDifferences) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            return "Category not found.";
        }

        ComparisonList comparisonList = comparisonListRepository.findByCategory(category).stream()
                .findFirst()
                .orElse(null);

        if (comparisonList == null || comparisonList.getProducts().size() < 2) {
            return "Not enough products to compare.";
        }

        List<Product> products = comparisonList.getProducts().stream().collect(Collectors.toList());
        Product p1 = products.get(0);
        Product p2 = products.get(1);

        // Определяем атрибуты для сравнения
        Map<String, String[]> properties = Map.of(
                "Brand", new String[]{p1.getBrand().toString(), p2.getBrand().toString()},
                "Screen", new String[]{p1.getScreen(), p2.getScreen()},
                "Color", new String[]{p1.getColor().toString(), p2.getColor().toString()},
                "Operation System", new String[]{p1.getOperationSystem().toString(), p2.getOperationSystem().toString()},
                "Memory", new String[]{p1.getMemory().toString(), p2.getMemory().toString()},
                "Weight", new String[]{String.valueOf(p1.getWeight()), String.valueOf(p2.getWeight())},
                "Sim Card", new String[]{p1.getSimCard(), p2.getSimCard()}
        );

        StringBuilder differences = new StringBuilder();

        if (showDifferences) {
            properties.forEach((key, value) -> {
                if (!value[0].equals(value[1])) {
                    differences.append(key).append(": ").append(value[0]).append(" vs ").append(value[1]).append("\n");
                }
            });
        } else {
            differences.append("Comparison done, but differences are not displayed.");
        }

        return differences.toString();
    }
}
