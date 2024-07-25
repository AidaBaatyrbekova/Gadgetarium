package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.mapper.ProductCompareMapper;
import com.peaksoft.gadgetarium.model.dto.response.ProductCompareResponse;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.repository.ProductCompareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCompareService {

    final ProductCompareRepository productCompareRepository;
    final ProductCompareMapper productCompareMapper;

    public List<ProductCompareResponse> compareProducts(Long categoryId, boolean showDifferencesOnly) {
        List<Product> products = productCompareRepository.findByCategoryId(categoryId);

        if (products.isEmpty()) {
            return List.of(); // Возвращаем пустой список, если нет продуктов
        }

        if (showDifferencesOnly) {
            products = filterDifferences(products);
        }

        // Возвращаем список продуктов после фильтрации
        return products.stream()
                .map(productCompareMapper::toProductCompareResponse)
                .collect(Collectors.toList());
    }

    private List<Product> filterDifferences(List<Product> products) {
        if (products.isEmpty()) {
            return products;
        }

        // Выбираем первый продукт как эталон
        Product referenceProduct = products.get(0);

        // Фильтруем продукты, оставляя только те, которые отличаются от эталонного продукта
        return products.stream()
                .filter(product -> !areProductsEqual(product, referenceProduct))
                .collect(Collectors.toList());
    }

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
