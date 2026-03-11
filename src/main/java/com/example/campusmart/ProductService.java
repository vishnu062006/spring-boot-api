package com.example.campusmart;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Spring Bean - manages all product business logic
 */
@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();
    private int nextId = 1;

    /**
     * Create a new product
     */
    public Product addProduct(String name, double price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        Product product = new Product(nextId++, name, price);
        products.add(product);
        System.out.println("[INFO] Product added: " + name);
        return product;
    }

    /**
     * Get all products
     */
    public List<Product> getAllProducts() {
        System.out.println("[INFO] Fetching all products");
        return products;
    }

    /**
     * Get product by ID
     */
    public Optional<Product> getProductById(int id) {
        System.out.println("[INFO] Fetching product ID: " + id);
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    /**
     * Update entire product - PUT
     */
    public Optional<Product> updateProduct(int id, String name, double price) {
        Optional<Product> found = getProductById(id);
        found.ifPresent(p -> {
            p.setName(name);
            p.setPrice(price);
            System.out.println("[INFO] Product updated: " + id);
        });
        return found;
    }

    /**
     * Update price only - PATCH
     */
    public Optional<Product> patchProduct(int id, double price) {
        Optional<Product> found = getProductById(id);
        found.ifPresent(p -> {
            p.setPrice(price);
            System.out.println("[INFO] Product price patched: " + id);
        });
        return found;
    }

    /**
     * Delete product
     */
    public boolean deleteProduct(int id) {
        boolean removed = products.removeIf(p -> p.getId().equals(id));
        if (removed) System.out.println("[INFO] Product deleted: " + id);
        else System.out.println("[WARN] Product not found: " + id);
        return removed;
    }
}