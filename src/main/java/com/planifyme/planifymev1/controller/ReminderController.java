package com.planifyme.planifymev1.controller;

import com.planifyme.planifymev1.dto.ReminderDto;
import com.planifyme.planifymev1.model.Reminder;
import com.planifyme.planifymev1.model.Task;
import com.planifyme.planifymev1.model.User;
import com.planifyme.planifymev1.service.ReminderService;
import com.planifyme.planifymev1.service.TaskService;
import com.planifyme.planifymev1.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class ReminderController {
    private UserService userService;
    private TaskService taskService;

    private ReminderService reminderService;

    public ReminderController(UserService userService, TaskService taskService, ReminderService reminderService) {
        this.userService = userService;
        this.taskService = taskService;
        this.reminderService =reminderService;
    }

    @GetMapping("/reminder")
    public String reminder(Model model){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        model.addAttribute("user",user);
        LocalDate datenow = LocalDate.now();
        List<Task> tasks = taskService.findTasksbyUser(user);
        List<ReminderDto> taskReminders;
        List<ReminderDto> reminders = new ArrayList<>();
        for (Task task: tasks){
            taskReminders = reminderService.findAllReminders(task);
            for (ReminderDto reminder: taskReminders) {
                if (reminder.getDateReminder().isBefore(datenow) || reminder.getDateReminder().isEqual(datenow)) {
                    reminders.add(reminder);
                }
            }
        }
        reminders.sort(Comparator.comparing(ReminderDto::getDateReminder));

        model.addAttribute("reminders", reminders);
        return "reminder";
    }

}
