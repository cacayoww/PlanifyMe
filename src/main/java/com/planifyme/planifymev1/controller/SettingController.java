package com.planifyme.planifymev1.controller;

import com.planifyme.planifymev1.dto.UserDto;
import com.planifyme.planifymev1.model.User;
import com.planifyme.planifymev1.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SettingController {

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    public SettingController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/settings")
    public String settings(Model model){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        model.addAttribute("user",user);
        return "settings";
    }

    @GetMapping("settings/editProfile")
    public String editProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        model.addAttribute("OldUser",user);
        UserDto userNew = new UserDto();
        userNew.setUsername(user.getUsername());
        userNew.setNamaLengkap(user.getNamaLengkap());
        userNew.setPassword(user.getPassword());
        model.addAttribute("NewUser", userNew);
        return "edit_profile";
    }

    @PostMapping("/settings/editProfile/save")
    public String editUserProfile(@Validated @ModelAttribute("NewUser") UserDto userDto,
                                  BindingResult result, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        System.out.println("New Password:"+userDto.getNewPassword());
        System.out.println("Confirm Password:"+userDto.getConfirmationPassword());
        if (userDto.getOldPassword()!=null) {
            if (!passwordEncoder.matches(userDto.getOldPassword(),user.getPassword())) {
                result.rejectValue("oldPassword", null,
                        "The password does not match with old password");
            }
        }

        if (userDto.getNewPassword()!=null && userDto.getConfirmationPassword()!=null) {
            if (!userDto.getNewPassword().equals(userDto.getConfirmationPassword())) {
                result.rejectValue("confirmationPassword", null,
                        "The confirmation password does not match with New Password");
            }
        }

        if (userDto.getNewPassword().isEmpty() && userDto.getConfirmationPassword().isEmpty()){
            userDto.setNewPassword(userDto.getOldPassword());
            userDto.setConfirmationPassword(userDto.getOldPassword());
            for (ObjectError error : result.getAllErrors()) {
                if (!error.getDefaultMessage().equals("Password must not be empty") &&
                    !error.getDefaultMessage().equals("Confirmation Password must not be empty") &&
                    !error.getDefaultMessage().equals("New Password must not be empty")){
                    model.addAttribute("NewUser", userDto);
                    return "edit_profile";
                }
                System.out.println("Error: " + error.getDefaultMessage());
            }
        }else{
            for (ObjectError error : result.getAllErrors()) {
                if (!error.getDefaultMessage().equals("Password must not be empty")){
                    model.addAttribute("NewUser", userDto);
                    return "edit_profile";
                }
                System.out.println("Error: " + error.getDefaultMessage());
            }
        }

        userService.updateUser(user.getIdUser(),userDto);
        return "redirect:/settings/editProfile?success";
    }
}
