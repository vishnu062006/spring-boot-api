package com.example.campusmart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Spring generates save(), findAll(), findById(), deleteById() automatically
}