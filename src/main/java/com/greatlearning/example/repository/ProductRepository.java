package com.greatlearning.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.greatlearning.example.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom query methods can be added here
}
