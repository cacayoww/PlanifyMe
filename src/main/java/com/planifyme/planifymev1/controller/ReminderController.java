package com.planifyme.planifymev1.controller;

import com.planifyme.planifymev1.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReminderController {
    private UserService userService;

    public ReminderController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/reminder")
    public String reminder(Model model){


//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findUserByUsername(authentication.getName());
//        model.addAttribute("user",user);


        return "reminder";
    }

}
