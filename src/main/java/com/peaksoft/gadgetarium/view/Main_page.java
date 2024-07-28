package com.peaksoft.gadgetarium.view;

import com.peaksoft.gadgetarium.model.entities.Product;

import java.util.List;

public class MainPage {
    private Product product;

    public MainPage(Product product) {
        this.product = product;
    }

    public void display() {
        System.out.println("========= Gadgetarium =========");

        System.out.println("Акция:");
        displayProducts(product.getDiscount();

        System.out.println("\nНовинки:");
        displayProducts(product.getBrandOfProduct();

        System.out.println("\nМы рекомендуем:");
        displayProducts(product.getRecommendedProducts());

        System.out.println("================================");
    }

    private void displayProducts(List<Product> products) {
        for (Product product : products) {
            System.out.printf("%s - %s (%.2f USD)%n",
                    product.getId(), product.getBrandOfProduct(), product.getPrice());
        }
    }
}

