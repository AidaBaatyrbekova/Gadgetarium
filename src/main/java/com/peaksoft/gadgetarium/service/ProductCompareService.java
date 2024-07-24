package com.peaksoft.gadgetarium.service;

import com.peaksoft.gadgetarium.mapper.ProductCompareMapper;
import com.peaksoft.gadgetarium.model.dto.request.ProductCompareRequest;
import com.peaksoft.gadgetarium.model.dto.response.ProductCompareResponse;
import com.peaksoft.gadgetarium.model.entities.Product;
import com.peaksoft.gadgetarium.repository.ProductCompareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCompareService {

     ProductCompareRepository productCompareRepository;
     ProductCompareMapper productCompareMapper;

    public List<ProductCompareResponse> compareProducts(ProductCompareRequest request) {
        // Получаем все продукты
        List<Product> products = productCompareRepository.findAll();

        // Применяем фильтрацию в зависимости от наличия параметров запроса
        products = products.stream()
                .filter(product -> filterByCategory(product, request.getCategoryId()))
                .filter(product -> filterByBrand(product, request.getBrandId()))
                .filter(product -> filterByOperationSystem(product, request.getOperationSystem()))
                .filter(product -> filterByProductName(product, request.getProductName()))
                .filter(product -> filterByScreen(product, request.getScreen()))
                .filter(product -> filterByWeight(product, request.getWeight()))
                .filter(product -> filterByPrice(product, request.getPrice()))
                .filter(product -> filterBySimCard(product, request.getSimCard()))
                .collect(Collectors.toList());

        // Возвращаем отфильтрованные продукты в виде ответов
        return products.stream()
                .map(productCompareMapper::toProductCompareResponse)
                .collect(Collectors.toList());
    }

    // Фильтрация по категории
    private boolean filterByCategory(Product product, Long categoryId) {
        return categoryId == null || (product.getCategory() != null && product.getCategory().getId().equals(categoryId));
    }

    // Фильтрация по бренду
    private boolean filterByBrand(Product product, Long brandId) {
        return brandId == null || (product.getBrandOfProduct() != null && product.getBrandOfProduct().getId().equals(brandId));
    }

    // Фильтрация по операционной системе
    private boolean filterByOperationSystem(Product product, String operationSystem) {
        return operationSystem == null || (product.getOperationSystem() != null && product.getOperationSystem().name().equals(operationSystem));
    }

    // Фильтрация по названию продукта
    private boolean filterByProductName(Product product, String productName) {
        return productName == null || (product.getProductName() != null && product.getProductName().equals(productName));
    }

    // Фильтрация по экрану
    private boolean filterByScreen(Product product, String screen) {
        return screen == null || (product.getScreen() != null && product.getScreen().equals(screen));
    }

    // Фильтрация по весу
    private boolean filterByWeight(Product product, Integer weight) {
        return weight == null || product.getWeight() == weight;
    }

    // Фильтрация по цене
    private boolean filterByPrice(Product product, Integer price) {
        return price == null || product.getPrice() == price;
    }

    // Фильтрация по SIM-карте
    private boolean filterBySimCard(Product product, String simCard) {
        return simCard == null || (product.getSimCard() != null && product.getSimCard().equals(simCard));
    }
}
