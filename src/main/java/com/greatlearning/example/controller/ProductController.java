package com.greatlearning.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.greatlearning.example.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/import-csv")
    public ResponseEntity<Integer> importProductsFromCsv(@RequestParam("file") MultipartFile file) {
        int savedCount = productService.importProductsFromCsv(file);
        return ResponseEntity.ok(savedCount);
    }
}