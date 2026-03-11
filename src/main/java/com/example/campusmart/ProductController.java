package com.example.campusmart;

import com.example.campusmart.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller - handles all HTTP requests for products
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private com.example.campusmart.ProductService productService;

    /**
     * GET /api/products - get all products
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * GET /api/products/{id} - get one product
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/products - create product
     */
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        try {
            Product created = productService.addProduct(product.getName(), product.getPrice());
            return ResponseEntity.status(201).body(created);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.out.println("[ERROR] Unexpected: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT /api/products/{id} - update whole product
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
        return productService.updateProduct(id, product.getName(), product.getPrice())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * PATCH /api/products/{id} - update price only
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Product> patchProduct(@PathVariable int id, @RequestBody Map<String, Double> body) {
        double price = body.get("price");
        return productService.patchProduct(id, price)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/products/{id} - delete product
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}