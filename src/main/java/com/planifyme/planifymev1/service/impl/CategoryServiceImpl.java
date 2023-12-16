package com.planifyme.planifymev1.service.impl;

import com.planifyme.planifymev1.dto.CategoryDto;
import com.planifyme.planifymev1.model.Category;
import com.planifyme.planifymev1.model.Reminder;
import com.planifyme.planifymev1.model.Task;
import com.planifyme.planifymev1.model.User;
import com.planifyme.planifymev1.repository.CategoryRepository;
import com.planifyme.planifymev1.repository.ReminderRepository;
import com.planifyme.planifymev1.repository.TaskRepository;
import com.planifyme.planifymev1.repository.UserRepository;
import com.planifyme.planifymev1.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private UserRepository userRepository;
    private ReminderRepository reminderRepository;
    private TaskRepository taskRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository, ReminderRepository reminderRepository, TaskRepository taskRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.reminderRepository = reminderRepository;
        this.taskRepository =taskRepository;
    }

    @Override
    public CategoryDto findById(int id) {
        Category category = categoryRepository.findByIdCategory(id);
        return convertModelToDto(category);
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
    public List<CategoryDto> findCategoriesbyUser(User user) {

        List<Category> categories = categoryRepository.findAllByUser(user);
        return categories.stream().map(this::convertModelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Category updateCategoryValues(int idCategory, CategoryDto categoryDto) {
        Category category = categoryRepository.findByIdCategory(idCategory);
        category.setNama(categoryDto.getNama());
        category.setDeskripsi(categoryDto.getDeskripsi());
        category.setColor(categoryDto.getColor());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(int idCategory) {
        Category category = categoryRepository.findByIdCategory(idCategory);
        List<Task> tasks = taskRepository.findAllByCategory(category);
        for (Task task: tasks){
            reminderRepository.deleteByTask(task);
            taskRepository.deleteById(task.getIdTask());
        }
        categoryRepository.deleteById(idCategory);
    }

    private CategoryDto convertModelToDto(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setIdCategory(category.getIdCategory());
        categoryDto.setNama(category.getNama());
        categoryDto.setDeskripsi(category.getDeskripsi());
        categoryDto.setImageUrl(category.getImageUrl());
        categoryDto.setColor(category.getColor());
        return categoryDto;
    }
}
