package com.planifyme.planifymev1.controller;

import com.planifyme.planifymev1.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskController {

    private UserService userService;

    public TaskController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/task")
    public String task(Model model){


//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findUserByUsername(authentication.getName());
//        model.addAttribute("user",user);


        return "task";
    }
}
