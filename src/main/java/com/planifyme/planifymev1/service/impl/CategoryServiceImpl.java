package com.planifyme.planifymev1.service.impl;

import com.planifyme.planifymev1.dto.CategoryDto;
import com.planifyme.planifymev1.model.Category;
import com.planifyme.planifymev1.model.User;
import com.planifyme.planifymev1.repository.CategoryRepository;
import com.planifyme.planifymev1.repository.UserRepository;
import com.planifyme.planifymev1.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveCategory(CategoryDto categoryDto, String username) {
        Category category = new Category();
        category.setNama(categoryDto.getNama());
        category.setDeskripsi(categoryDto.getDeskripsi());
        category.setColor(categoryDto.getColor());
        User user = userRepository.findByUsername(username);
        category.setUser(user);
        category.setImageUrl(categoryDto.getImageUrl());
        categoryRepository.save(category);
    }

    @Override
    public List<Category> findCategoriesbyUser(User user) {
        return categoryRepository.findAllByUser(user);
    }
}
