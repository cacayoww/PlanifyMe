package com.planifyme.planifymev1.service;

import com.planifyme.planifymev1.dto.CategoryDto;
import com.planifyme.planifymev1.model.Category;
import com.planifyme.planifymev1.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    CategoryDto findById(int id);

    void saveCategory(CategoryDto categoryDto, String username);

    List<CategoryDto> findCategoriesbyUser(User user);
}
