package com.planifyme.planifymev1.controller;

import com.planifyme.planifymev1.dto.CategoryDto;
import com.planifyme.planifymev1.dto.UserDto;
import com.planifyme.planifymev1.model.Task;
import com.planifyme.planifymev1.model.User;
import com.planifyme.planifymev1.service.CategoryService;
import com.planifyme.planifymev1.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class AuthController {

    private UserService userService;
    private CategoryService categoryService;

    public AuthController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }
    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Validated @ModelAttribute("user") UserDto userDto,
                               BindingResult result, Model model){
        User existingUser = userService.findUserByUsername(userDto.getUsername());
        if(existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()){
            result.rejectValue("username", null,
                    "There is already an account registered with the same username");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        System.out.println(userDto.getUsername());
        userService.saveUser(userDto);
        CategoryDto college = new CategoryDto("college","this is college","#a6d1ef","/images/college.png");
        CategoryDto chores = new CategoryDto("chores","this is chores","#ecaeed","/images/chores.png");
        CategoryDto parttime = new CategoryDto("part-time","this is part-time","#86ebf1","/images/part-time.png");
        CategoryDto relax = new CategoryDto("relax","this is relax","#86aaf1","/images/relax.png");

        categoryService.saveCategory(college,userDto.getUsername());
        categoryService.saveCategory(chores,userDto.getUsername());
        categoryService.saveCategory(parttime,userDto.getUsername());
        categoryService.saveCategory(relax,userDto.getUsername());
        return "redirect:/register?success";


    }

    @GetMapping("/")
    public String dashboard1(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        model.addAttribute("user",user);
        return "redirect:/dashboard";
    }

}
