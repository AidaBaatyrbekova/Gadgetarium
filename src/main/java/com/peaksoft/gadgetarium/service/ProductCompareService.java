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

    // Основной метод для сравнения продуктов по категории
    public List<ProductCompareResponse> compareProducts(Long categoryId, boolean showDifferencesOnly) {
        List<Product> products = productCompareRepository.findByCategoryId(categoryId);

        if (products == null || products.isEmpty()) {
            throw new NoSuchElementException("No products found for category ID: " + categoryId);
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
                Product p1 = products.get(i);
                Product p2 = products.get(j);

                // Если два продукта равны, удаляем их из множества
                if (areProductsEqual(p1, p2)) {
                    differentProducts.remove(p1);
                    differentProducts.remove(p2);
                }
            }
        }
        return new ArrayList<>(differentProducts);
    }

    // Метод для сравнения двух продуктов
    private boolean areProductsEqual(Product p1, Product p2) {
        return Objects.equals(p1.getBrandOfProduct(), p2.getBrandOfProduct()) &&
                Objects.equals(p1.getScreen(), p2.getScreen()) &&
                Objects.equals(p1.getColor(), p2.getColor()) &&
                Objects.equals(p1.getOperationSystem(), p2.getOperationSystem()) &&
                Objects.equals(p1.getMemory(), p2.getMemory()) &&
                p1.getWeight() == p2.getWeight() &&
                Objects.equals(p1.getSimCard(), p2.getSimCard());
    }
}