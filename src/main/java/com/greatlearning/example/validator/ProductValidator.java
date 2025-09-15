package com.greatlearning.example.validator;

import com.greatlearning.example.model.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductValidator {
    public boolean isValid(Product p) {
        return validate(p).isEmpty();
    }

    public List<String> validate(Product p) {
        List<String> errors = new ArrayList<>();
        if (p == null) {
            errors.add("Product must not be null");
            return errors;
        }
        if (p.getName() == null || p.getName().trim().isEmpty()) {
            errors.add("Product name must not be empty");
        }
        if (p.getPrice() == null || p.getPrice().signum() <= 0) {
            errors.add("Product price must be positive");
        }
        if (p.getSku() == null || p.getSku().trim().isEmpty()) {
            errors.add("Product SKU must not be empty");
        }
        if (p.getCreatedAt() == null) {
            errors.add("Product createdAt must not be null");
        }
        return errors;
    }
}
