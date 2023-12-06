package com.planifyme.planifymev1.controller;

import com.planifyme.planifymev1.dto.CategoryDto;
import com.planifyme.planifymev1.dto.ReminderDto;
import com.planifyme.planifymev1.dto.TaskDto;
import com.planifyme.planifymev1.model.Category;
import com.planifyme.planifymev1.model.Task;
import com.planifyme.planifymev1.model.User;
import com.planifyme.planifymev1.service.CategoryService;
import com.planifyme.planifymev1.service.ReminderService;
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
    private ReminderService reminderService;

    public TaskController(UserService userService, CategoryService categoryService, TaskService taskService, ReminderService reminderService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.taskService = taskService;
        this.reminderService =reminderService;
    }

    @GetMapping("/task")
    public String task(Model model){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        model.addAttribute("user",user);
        List<CategoryDto> categories = categoryService.findCategoriesbyUser(user);
        model.addAttribute("categories",categories);

        return "task";
    }

    @PostMapping("/task/createTask")
    public String createTask(@ModelAttribute("task") TaskDto taskDto, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        Task task = taskService.saveTask(taskDto, user.getUsername());
        for(int i=0;i<4;i++){
            if (taskDto.getReminder()[i] != null){
                ReminderDto reminderDto = reminderService.createReminder(user,task,i);
                reminderService.saveReminder(reminderDto,task.getIdTask());
            }
        }
        return "redirect:/task";
    }
}
