package com.greatlearning.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.greatlearning.example.model.Product;
import com.greatlearning.example.repository.ProductRepository;
import com.greatlearning.example.validator.ProductValidator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private ProductRepository productRepository;

    private final ProductValidator validator = new ProductValidator();

    @Override
    public int importProductsFromCsv(MultipartFile file) {
        int savedCount = 0;
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) { // skip header
                    firstLine = false;
                    continue;
                }
                String[] fields = line.split(",");
                if (fields.length < 4) {
                    logger.warn("Skipping line due to insufficient fields: {}", line);
                    continue;
                }
                Product p = new Product();
                try {
                    p.setName(fields[0].trim());
                    p.setPrice(new BigDecimal(fields[1].trim()));
                    p.setSku(fields[2].trim());
                    p.setCreatedAt(LocalDateTime.parse(fields[3].trim()));
                } catch (Exception parseEx) {
                    logger.error("Error parsing product fields: {}", line, parseEx);
                    continue;
                }
                if (validator.isValid(p)) {
                    productRepository.save(p);
                    savedCount++;
                    logger.info("Saved product: {}", p.getName());
                } else {
                    logger.warn("Validation failed for product: {}", p.getName());
                }
            }
        } catch (Exception e) {
            logger.error("Error importing products from CSV", e);
        }
        logger.info("Total products saved: {}", savedCount);
        return savedCount;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
