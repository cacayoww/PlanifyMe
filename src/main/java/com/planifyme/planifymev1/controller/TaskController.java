package com.planifyme.planifymev1.controller;

import com.planifyme.planifymev1.dto.CategoryDto;
import com.planifyme.planifymev1.dto.TaskDto;
import com.planifyme.planifymev1.model.Category;
import com.planifyme.planifymev1.model.User;
import com.planifyme.planifymev1.service.CategoryService;
import com.planifyme.planifymev1.service.TaskService;
import com.planifyme.planifymev1.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TaskController {

    private UserService userService;
    private CategoryService categoryService;
    private TaskService taskService;

    public TaskController(UserService userService, CategoryService categoryService, TaskService taskService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.taskService = taskService;
    }

    @GetMapping("/task")
    public String task(Model model){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        model.addAttribute("user",user);
        List<Category> categories = categoryService.findCategoriesbyUser(user);
        model.addAttribute("categories",categories);

        return "task";
    }

    @PostMapping("/task/createTask")
    public String createTask(@ModelAttribute("task") TaskDto taskDto, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        taskService.saveTask(taskDto, user.getUsername());
        return "redirect:/task";
    }
}
