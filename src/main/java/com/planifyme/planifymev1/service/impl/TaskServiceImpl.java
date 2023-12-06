package com.planifyme.planifymev1.service.impl;

import com.planifyme.planifymev1.dto.TaskDto;
import com.planifyme.planifymev1.model.Category;
import com.planifyme.planifymev1.model.Task;
import com.planifyme.planifymev1.model.User;
import com.planifyme.planifymev1.repository.CategoryRepository;
import com.planifyme.planifymev1.repository.TaskRepository;
import com.planifyme.planifymev1.repository.UserRepository;
import com.planifyme.planifymev1.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Task saveTask(TaskDto taskDto, String username) {
        Task task = new Task();
        task.setNama(taskDto.getNama());
        task.setDueDate(taskDto.getDueDate());
        User user = userRepository.findByUsername(username);
        task.setUser(user);
        Category category = categoryRepository.findByNama(taskDto.getKategori());
        task.setCategory(category);
        Task savedTask = taskRepository.save(task);
        return savedTask;
    }

    @Override
    public List<Task> findTasksbyUser(User user) {
        return taskRepository.findAllByUser(user);
    }
}
