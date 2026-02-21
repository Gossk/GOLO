package com.golo.ecommerce.service;

import com.golo.ecommerce.dto.request.CategoryRequest;
import com.golo.ecommerce.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategories();
    List<CategoryResponse> getActiveCategories();
    List<CategoryResponse> getRootCategories();
    List<CategoryResponse> getSubCategories(Long parentId);
    CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest);
    void deleteCategory(Long id);
}