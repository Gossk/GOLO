package com.golo.ecommerce.controller;

import com.golo.ecommerce.dto.request.ProductRequest;
import com.golo.ecommerce.dto.response.ApiResponse;
import com.golo.ecommerce.dto.response.ProductResponse;
import com.golo.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse product = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Product created successfully", product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Product retrieved successfully", product));
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProductResponse> products = productService.getActiveProducts(pageable);
        return ResponseEntity.ok(new ApiResponse(true, "Products retrieved successfully", products));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponse> products = productService.getProductsByCategory(categoryId, pageable);
        return ResponseEntity.ok(new ApiResponse(true, "Products retrieved successfully", products));
    }

    @GetMapping("/featured")
    public ResponseEntity<?> getFeaturedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponse> products = productService.getFeaturedProducts(pageable);
        return ResponseEntity.ok(new ApiResponse(true, "Featured products retrieved successfully", products));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponse> products = productService.searchProducts(keyword, pageable);
        return ResponseEntity.ok(new ApiResponse(true, "Search results retrieved successfully", products));
    }

    @GetMapping("/price-range")
    public ResponseEntity<?> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponse> products = productService.getProductsByPriceRange(minPrice, maxPrice, pageable);
        return ResponseEntity.ok(new ApiResponse(true, "Products retrieved successfully", products));
    }

    @GetMapping("/new-arrivals")
    public ResponseEntity<?> getNewArrivals() {
        List<ProductResponse> products = productService.getNewArrivals();
        return ResponseEntity.ok(new ApiResponse(true, "New arrivals retrieved successfully", products));
    }

    @GetMapping("/top-rated")
    public ResponseEntity<?> getTopRated() {
        List<ProductResponse> products = productService.getTopRated();
        return ResponseEntity.ok(new ApiResponse(true, "Top rated products retrieved successfully", products));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest productRequest
    ) {
        ProductResponse product = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(new ApiResponse(true, "Product updated successfully", product));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponse(true, "Product deleted successfully"));
    }
}