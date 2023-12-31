package com.planifyme.planifymev1.controller;

import com.planifyme.planifymev1.dto.CategoryDto;
import com.planifyme.planifymev1.dto.ReminderDto;
import com.planifyme.planifymev1.dto.TaskDto;
import com.planifyme.planifymev1.model.Category;
import com.planifyme.planifymev1.model.Reminder;
import com.planifyme.planifymev1.model.Task;
import com.planifyme.planifymev1.model.User;
import com.planifyme.planifymev1.service.CategoryService;
import com.planifyme.planifymev1.service.ReminderService;
import com.planifyme.planifymev1.service.TaskService;
import com.planifyme.planifymev1.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
public class TaskController {

    private UserService userService;
    private CategoryService categoryService;
    private TaskService taskService;
    private ReminderService reminderService;

    @PersistenceContext
    private EntityManager entityManager;

    public TaskController(UserService userService, CategoryService categoryService, TaskService taskService, ReminderService reminderService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.taskService = taskService;
        this.reminderService =reminderService;
    }

    @GetMapping("/task")
    public String task(Model model){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        model.addAttribute("user",user);
        List<CategoryDto> categories = categoryService.findCategoriesbyUser(user);
        model.addAttribute("categories",categories);
        List<TaskDto> taskDtos = taskService.findTaskDtosbyUser(user);
        taskDtos.forEach(t->t.formatDate("E, dd MMMM yyyy"));
        taskDtos.sort(Comparator.comparing(TaskDto::getDueDate));
        model.addAttribute("tasks",taskDtos);

        return "task";
    }

    @PostMapping("/task/createTask")
    public String createTask(@ModelAttribute("task") TaskDto taskDto, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(authentication.getName());
        Task task = taskService.saveTask(taskDto, user.getUsername());
        for(int i=0;i<4;i++){
            if (taskDto.getReminder()[i] != null){
                ReminderDto reminderDto = reminderService.createReminder(user,task,i);
                reminderService.saveReminder(reminderDto,task.getIdTask());
            }
        }
        return "redirect:/task";
    }

    @PostMapping("/task/markAsCompleted/{id}")
    public String markAsCompleted(@PathVariable("id") int id, Model model){
        taskService.updateTaskStatus(id);
        return "redirect:/task";
    }
    @Transactional
    @PostMapping("task/update/{id}")
    public String updateTask(@PathVariable int id,@ModelAttribute TaskDto taskDto) {
        taskService.updateTaskValues(id, taskDto);
        Task task = entityManager.find(Task.class, id);
        entityManager.refresh(task);
        for(int i=0;i<4;i++){
            if (taskDto.getReminder()[i] != null){
                System.out.println(i);
                ReminderDto reminderDto = reminderService.createReminder(task.getUser(),task,i);
                System.out.println(task.getNama());
                reminderService.saveReminder(reminderDto,task.getIdTask());
            }
        }
        return "redirect:/task";
    }

    @PostMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable("id") int id, Model model){
        taskService.deleteTask(id);
        return "redirect:/task";
    }

}
