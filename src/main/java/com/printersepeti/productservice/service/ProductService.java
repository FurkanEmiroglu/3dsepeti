package com.printersepeti.productservice.service;

import com.printersepeti.productservice.dto.ProductRequest;
import com.printersepeti.productservice.exception.DuplicateProductException;
import com.printersepeti.productservice.model.Product;
import com.printersepeti.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductRequest request) throws RuntimeException {
        if (request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }

        if (request.getName().isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty");
        }

        if (request.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Description must not be empty");
        }

        // check duplicate product by productName
        if (productRepository.findByName(request.getName()).isPresent()) {
            throw new DuplicateProductException("Product with name " + request.getName() + " already exists");
        }

        try {
            Product product = Product.builder()
                    .businessID(request.getBusinessID())
                    .name(request.getName())
                    .description(request.getDescription())
                    .price(request.getPrice())
                    .build();

            productRepository.save(product);

        } catch (Exception e) {
            throw new RuntimeException("Failed to save product", e);
        }
    }

    public void deleteProduct(Long id) throws RuntimeException {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete product", e);
        }
    }
}
