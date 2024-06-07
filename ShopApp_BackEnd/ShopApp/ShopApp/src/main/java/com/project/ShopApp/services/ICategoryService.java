package com.project.ShopApp.services;

import com.project.ShopApp.dtos.CategoryDTO;
import com.project.ShopApp.models.Category;
import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDTO);

    Category getCategoryById(long id);

    List<Category> getAllCategories();

    Category updateCategory(long categoryId, CategoryDTO categoryDTO);

    void deleteCategory(long id);
}
