package com.planifyme.planifymev1.controller;

import com.planifyme.planifymev1.dto.CategoryDto;
import com.planifyme.planifymev1.dto.UserDto;
import com.planifyme.planifymev1.model.Category;
import com.planifyme.planifymev1.model.User;
import com.planifyme.planifymev1.service.CategoryService;
import com.planifyme.planifymev1.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DashboardController {

    private UserService userService;
    private CategoryService categoryService;
    private String image;

    public DashboardController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
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

}
