package com.planifyme.planifymev1.controller;

import com.planifyme.planifymev1.model.Category;
import com.planifyme.planifymev1.model.User;
import com.planifyme.planifymev1.service.CategoryService;
import com.planifyme.planifymev1.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private UserService userService;
    private CategoryService categoryService;

    public DashboardController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        List<Category> categories = categoryService.findCategoriesbyUser(user);
        model.addAttribute("user",user);
        model.addAttribute("categories",categories);

        return "dashboard";
    }


}
