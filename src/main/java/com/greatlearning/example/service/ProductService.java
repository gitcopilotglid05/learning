package com.greatlearning.example.service;

import org.springframework.web.multipart.MultipartFile;
import com.greatlearning.example.model.Product;
import java.util.List;

public interface ProductService {
    int importProductsFromCsv(MultipartFile file);
    List<Product> getAllProducts();
}
