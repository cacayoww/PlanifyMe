package com.planifyme.planifymev1.controller;

import com.planifyme.planifymev1.dto.CategoryDto;
import com.planifyme.planifymev1.dto.TaskDto;
import com.planifyme.planifymev1.dto.UserDto;
import com.planifyme.planifymev1.model.Category;
import com.planifyme.planifymev1.model.User;
import com.planifyme.planifymev1.service.CategoryService;
import com.planifyme.planifymev1.service.TaskService;
import com.planifyme.planifymev1.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {

    private UserService userService;
    private CategoryService categoryService;
    private TaskService taskService;
    private String image;

    public DashboardController(UserService userService, CategoryService categoryService, TaskService taskService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.taskService = taskService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        List<CategoryDto> categories = categoryService.findCategoriesbyUser(user);
        model.addAttribute("user",user);
        model.addAttribute("categories",categories);
        return "dashboard";
    }

    @GetMapping("/dashboard/addcategory")
    public String addcategory(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        model.addAttribute("user", user);
        model.addAttribute("selectedImage","/icon/gallery-edit.svg");
        return "add_category";
    }

    @PostMapping("/dashboard/addcategory")
    public String inputImage(@ModelAttribute("selectedImage") String selectedImage, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        model.addAttribute("user", user);
        System.out.println(selectedImage);
        image = selectedImage;
        model.addAttribute("selectedImage", selectedImage);
        return "add_category";
    }

    @PostMapping("/dashboard/addcategory/save")
    public String inputCategory(@ModelAttribute("category") CategoryDto categoryDto, Model model){
        categoryDto.setImageUrl(image);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        categoryService.saveCategory(categoryDto, user.getUsername());
        model.addAttribute("category", categoryDto);
        return "redirect:/dashboard/addcategory?success";
    }

    @GetMapping("/dashboard/category/{id}")
    public String categoryDetails(@PathVariable("id") int id, Model model){
        CategoryDto categoryDto = categoryService.findById(id);
        List<TaskDto> tasks = taskService.findTasksbyCategory(id);
        List<TaskDto> upcoming = new ArrayList<>();
        List<TaskDto> today = new ArrayList<>();
        List<TaskDto> completed = new ArrayList<>();
        model.addAttribute("category",categoryDto);
        for(TaskDto task: tasks){
            if (task.getDueDate().isAfter(LocalDate.now())){
                upcoming.add(task);
            }else if (task.getDueDate().isEqual(LocalDate.now())) {
                today.add(task);
            }else if (task.getDueDate().isBefore(LocalDate.now())){
                completed.add(task);
            }
        }
        model.addAttribute("upcoming_tasks",upcoming);
        model.addAttribute("today_tasks",today);
        model.addAttribute("completed_tasks",completed);
        return "category";
    }


}
