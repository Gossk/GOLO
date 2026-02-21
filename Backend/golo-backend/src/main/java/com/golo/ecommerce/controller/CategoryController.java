package com.golo.ecommerce.controller;

import com.golo.ecommerce.dto.request.CategoryRequest;
import com.golo.ecommerce.dto.response.ApiResponse;
import com.golo.ecommerce.dto.response.CategoryResponse;
import com.golo.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse category = categoryService.createCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Category created successfully", category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        CategoryResponse category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Category retrieved successfully", category));
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getActiveCategories();
        return ResponseEntity.ok(new ApiResponse(true, "Categories retrieved successfully", categories));
    }

    @GetMapping("/root")
    public ResponseEntity<?> getRootCategories() {
        List<CategoryResponse> categories = categoryService.getRootCategories();
        return ResponseEntity.ok(new ApiResponse(true, "Root categories retrieved successfully", categories));
    }

    @GetMapping("/{parentId}/subcategories")
    public ResponseEntity<?> getSubCategories(@PathVariable Long parentId) {
        List<CategoryResponse> categories = categoryService.getSubCategories(parentId);
        return ResponseEntity.ok(new ApiResponse(true, "Subcategories retrieved successfully", categories));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest categoryRequest
    ) {
        CategoryResponse category = categoryService.updateCategory(id, categoryRequest);
        return ResponseEntity.ok(new ApiResponse(true, "Category updated successfully", category));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse(true, "Category deleted successfully"));
    }
}