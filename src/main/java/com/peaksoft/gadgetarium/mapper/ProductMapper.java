//package com.peaksoft.gadgetarium.mapper;
//
//import com.peaksoft.gadgetarium.model.entities.Brand;
//import com.peaksoft.gadgetarium.model.entities.Category;
//import com.peaksoft.gadgetarium.model.entities.Product;
//import com.peaksoft.gadgetarium.repository.BrandRepository;
//import com.peaksoft.gadgetarium.repository.CategoryRepository;
//import com.peaksoft.gadgetarium.request.ProductRequest;
//import com.peaksoft.gadgetarium.response.ProductResponse;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@Component
//public class ProductMapper {
//    CategoryRepository categoryRepository;
//    BrandRepository brandRepository;
//
//    public Product productMapper(ProductRequest request) {
//        Product product = new Product();
//        product.setProductName(request.getProductName());
//        product.setProductStatus(request.getProductStatus());
//        product.setMemory(request.getMemory());
//        product.setColor(request.getColor());
//        product.setOperationMemory(request.getOperationMemory());
//        product.setScreen(request.getScreen());
//        product.setOperationSystem(request.getOperationSystem());
//        product.setDateOfRelease(request.getDateOfRelease());
//        product.setSimCard(request.getSimCard());
//        product.setProcessor(request.getProcessor());
//        product.setWeight(request.getWeight());
//        product.setGuarantee(request.getGuarantee());
//        product.setPrice(request.getPrice());
//
//        Optional<Category> category = categoryRepository.findById(request.getCategoryId());
//        category.ifPresent(product::setCategory);
//
//        Optional<Brand> brand = brandRepository.findById(request.getBrandId());
//        brand.ifPresent(product::setBrandOfProduct);
//
//        return product;
//    }
//
//    public ProductResponse mapToResponse(Product product) {
//        return ProductResponse.builder()
//                .id(product.getId())
//                .productName(product.getProductName())
//                .productStatus(product.getProductStatus())
//                .categoryId(product.getId())
//                .memory(product.getMemory())
//                .color(product.getColor())
//                .operationMemory(product.getOperationMemory())
//                .screen(product.getScreen())
//                .operationSystem(product.getOperationSystem())
//                .operationSystemNum(product.getOperationSystemNum())
//                .dateOfRelease(product.getDateOfRelease())
//                .simCard(product.getSimCard())
//                .processor(product.getProcessor())
//                .weight(product.getWeight())
//                .guarantee(product.getGuarantee())
//                .rating(product.getRating())
//                .discount(product.getDiscount())
//                .price(product.getPrice())
//                .createDate(product.getCreateDate())
//                .build();
//    }
//}
