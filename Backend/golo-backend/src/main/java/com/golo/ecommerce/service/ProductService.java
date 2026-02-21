package com.golo.ecommerce.service;

import com.golo.ecommerce.dto.request.ProductRequest;
import com.golo.ecommerce.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse getProductById(Long id);
    Page<ProductResponse> getAllProducts(Pageable pageable);
    Page<ProductResponse> getActiveProducts(Pageable pageable);
    Page<ProductResponse> getProductsByCategory(Long categoryId, Pageable pageable);
    Page<ProductResponse> getFeaturedProducts(Pageable pageable);
    Page<ProductResponse> searchProducts(String keyword, Pageable pageable);
    Page<ProductResponse> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    List<ProductResponse> getNewArrivals();
    List<ProductResponse> getTopRated();
    ProductResponse updateProduct(Long id, ProductRequest productRequest);
    void deleteProduct(Long id);
    void updateProductStock(Long id, Integer quantity);
}