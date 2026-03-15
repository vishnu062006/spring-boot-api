package com.example.campusmart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Spring Bean - manages all product business logic using JPA
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Create a new product
     */
    public Product addProduct(String name, Double price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        Product product = new Product(null, name, price);
        System.out.println("[INFO] Product added: " + name);
        return productRepository.save(product);
    }

    /**
     * Get all products
     */
    public List<Product> getAllProducts() {
        System.out.println("[INFO] Fetching all products");
        return productRepository.findAll();
    }

    /**
     * Get product by ID
     */
    public Optional<Product> getProductById(int id) {
        System.out.println("[INFO] Fetching product ID: " + id);
        return productRepository.findById(id);
    }

    /**
     * Update entire product - PUT
     */
    public Optional<Product> updateProduct(int id, String name, Double price) {
        Optional<Product> found = productRepository.findById(id);
        found.ifPresent(p -> {
            p.setName(name);
            p.setPrice(price);
            productRepository.save(p);
            System.out.println("[INFO] Product updated: " + id);
        });
        return found;
    }

    /**
     * Update price only - PATCH
     */
    public Optional<Product> patchProduct(int id, Double price) {
        Optional<Product> found = productRepository.findById(id);
        found.ifPresent(p -> {
            p.setPrice(price);
            productRepository.save(p);
            System.out.println("[INFO] Product price patched: " + id);
        });
        return found;
    }

    /**
     * Delete product
     */
    public boolean deleteProduct(int id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            System.out.println("[INFO] Product deleted: " + id);
            return true;
        }
        System.out.println("[WARN] Product not found: " + id);
        return false;
    }
}