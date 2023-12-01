package com.planifyme.planifymev1.service;

import com.planifyme.planifymev1.dto.CategoryDto;
import com.planifyme.planifymev1.model.Category;
import com.planifyme.planifymev1.model.User;

import java.util.List;

public interface CategoryService {

    void saveCategory(CategoryDto categoryDto, String username);

    List<Category> findCategoriesbyUser(User user);
}
