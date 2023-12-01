package com.planifyme.planifymev1.controller;

import com.planifyme.planifymev1.dto.UserDto;
import com.planifyme.planifymev1.model.Task;
import com.planifyme.planifymev1.model.User;
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

    public AuthController(UserService userService) {
        this.userService = userService;
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
        // create model object to store form data
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
