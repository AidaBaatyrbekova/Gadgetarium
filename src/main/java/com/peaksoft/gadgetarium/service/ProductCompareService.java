package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.mapper.ProductCompareMapper;
import com.peaksoft.gadgetarium.model.dto.response.ProductCompareResponse;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.repository.ProductCompareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCompareService {

    final ProductCompareRepository productCompareRepository;
    final ProductCompareMapper productCompareMapper;

    // Хранилище продуктов для сравнения по категориям
    private final Map<Long, List<Product>> productComparisonStore = new HashMap<>();

    // Метод для добавления товара в список сравнения по категории
    public void addProductForComparison(Long categoryId, Product product) {
        product.setCategoryId(categoryId);
        productComparisonStore.computeIfAbsent(categoryId, k -> new ArrayList<>()).add(product);
    }

    // Метод для очистки списка товаров для сравнения по категории
    public void clearComparisonList(Long categoryId) {
        productComparisonStore.remove(categoryId);
    }

    // Основной метод для сравнения продуктов по категории
    public List<ProductCompareResponse> compareProducts(Long categoryId, boolean showDifferencesOnly) {
        List<Product> products = productComparisonStore.get(categoryId);

        if (products == null || products.isEmpty()) {
            throw new NoSuchElementException("Нет товаров для сравнения в категории с ID: " + categoryId);
        }

        // Фильтрация только различающихся продуктов
        if (showDifferencesOnly) {
            products = filterDifferences(products);
        }

        // Возвращаем список продуктов после фильтрации
        return products.stream()
                .map(productCompareMapper::toProductCompareResponse)
                .collect(Collectors.toList());
    }

    // Метод для фильтрации только различающихся продуктов
    private List<Product> filterDifferences(List<Product> products) {
        Set<Product> differentProducts = new HashSet<>(products);

        // Двойной цикл для сравнения всех возможных пар продуктов
        for (int i = 0; i < products.size(); i++) {
            for (int j = i + 1; j < products.size(); j++) {
                Product product1 = products.get(i);
                Product product2 = products.get(j);

                // Если два продукта равны, удаляем их из множества
                if (productsEqual(product1, product2)) {
                    differentProducts.remove(product1);
                    differentProducts.remove(product2);
                }
            }
        }
        return new ArrayList<>(differentProducts);
    }

    // Метод для сравнения двух продуктов
    private boolean productsEqual(Product p1, Product p2) {
        return Objects.equals(p1.getBrandOfProduct(), p2.getBrandOfProduct()) &&
                Objects.equals(p1.getScreen(), p2.getScreen()) &&
                Objects.equals(p1.getColor(), p2.getColor()) &&
                Objects.equals(p1.getOperationSystem(), p2.getOperationSystem()) &&
                Objects.equals(p1.getMemory(), p2.getMemory()) &&
                p1.getWeight() == p2.getWeight() &&
                Objects.equals(p1.getSimCard(), p2.getSimCard());
    }
}
