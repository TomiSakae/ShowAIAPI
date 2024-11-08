package com.tomisakae.showai.service;

import com.tomisakae.showai.entity.Category;
import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);
    boolean existsByName(String name);
}
