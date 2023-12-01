package com.planifyme.planifymev1.controller;

import com.planifyme.planifymev1.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingController {

    private UserService userService;

    public SettingController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/settings")
    public String settings(Model model){


//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findUserByUsername(authentication.getName());
//        model.addAttribute("user",user);


        return "settings";
    }

    @GetMapping("settings/editProfile")
    public String editProfile(Model model){


//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findUserByUsername(authentication.getName());
//        model.addAttribute("user",user);


        return "edit_profile";
    }
}
