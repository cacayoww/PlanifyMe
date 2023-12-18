package com.planifyme.planifymev1.controller;

import com.planifyme.planifymev1.dto.CategoryDto;
import com.planifyme.planifymev1.dto.TaskDto;
import com.planifyme.planifymev1.model.User;
import com.planifyme.planifymev1.service.CategoryService;
import com.planifyme.planifymev1.service.TaskService;
import com.planifyme.planifymev1.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final TaskService taskService;
    private String image;

    public DashboardController(UserService userService, CategoryService categoryService, TaskService taskService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.taskService = taskService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        User user = getAuthenticatedUser();
        List<CategoryDto> categories = categoryService.findCategoriesbyUser(user);
        model.addAttribute("user",user);
        model.addAttribute("categories",categories);
        List<TaskDto> taskDtos = taskService.findTaskDtosbyUser(user);
        List<List<TaskDto>> oneWeekTask = new ArrayList<>();
        List<TaskDto> now= new ArrayList<>();
        List<TaskDto> one= new ArrayList<>();
        List<TaskDto> two= new ArrayList<>();
        List<TaskDto> three= new ArrayList<>();
        List<TaskDto> four= new ArrayList<>();
        List<TaskDto> five= new ArrayList<>();
        List<TaskDto> six= new ArrayList<>();
        for (TaskDto t: taskDtos){
            t.formatDate("E, dd MMM yyyy");
            if (t.getDueDate().equals(LocalDate.now())) {
                now.add(t);
            }else if (t.getDueDate().equals(LocalDate.now().plusDays(1))){
                one.add(t);
            }else if (t.getDueDate().equals(LocalDate.now().plusDays(2))){
                two.add(t);
            }else if (t.getDueDate().equals(LocalDate.now().plusDays(3))){
                three.add(t);
            }else if (t.getDueDate().equals(LocalDate.now().plusDays(4))){
                four.add(t);
            }else if (t.getDueDate().equals(LocalDate.now().plusDays(5))){
                five.add(t);
            }else if (t.getDueDate().equals(LocalDate.now().plusDays(6))){
                six.add(t);
            }
        }
        if(!now.isEmpty()){
            oneWeekTask.add(now);
        }
        if (!one.isEmpty()){
            oneWeekTask.add(one);
        }
        if (!two.isEmpty()){
            oneWeekTask.add(two);
        }
        if (!three.isEmpty()){
            oneWeekTask.add(three);
        }
        if (!four.isEmpty()){
            oneWeekTask.add(four);
        }
        if (!five.isEmpty()){
            oneWeekTask.add(five);
        }
        if (!six.isEmpty()){
            oneWeekTask.add(six);
        }
        int num_task = taskDtos.size();
        int num_task_completed = taskDtos.stream().filter(e->e.getStatus().equals("Completed")).toList().size();
        if (num_task==0){
            num_task=1;
        }
        model.addAttribute("progress",(num_task_completed*100/num_task));
        model.addAttribute("tasksToday",now);
        model.addAttribute("tasks7days",oneWeekTask);
        return "dashboard";
    }

    @GetMapping("/dashboard/addcategory")
    public String addcategory(Model model){
        User user = getAuthenticatedUser();
        model.addAttribute("user", user);
        model.addAttribute("selectedImage","/icon/gallery-edit.svg");
        return "add_category";
    }

    @PostMapping("/dashboard/addcategory")
    public String inputImage(@ModelAttribute("selectedImage") String selectedImage, Model model){
        User user = getAuthenticatedUser();
        model.addAttribute("user", user);
        System.out.println(selectedImage);
        image = selectedImage;
        model.addAttribute("selectedImage", selectedImage);
        return "add_category";
    }

    @PostMapping("/dashboard/addcategory/save")
    public String inputCategory(@ModelAttribute("category") CategoryDto categoryDto, Model model){
        categoryDto.setImageUrl(image);
        User user = getAuthenticatedUser();
        categoryService.saveCategory(categoryDto, user.getUsername());
        model.addAttribute("category", categoryDto);
        return "redirect:/dashboard/addcategory?success";
    }

    @GetMapping("/dashboard/category/{id}")
    public String categoryDetails(@PathVariable("id") int id, Model model) {
         prepareCategoryView(id, model);
         return "category";
    }

    @GetMapping("/dashboard/category/edit/{id}")
    public String editCategory(@PathVariable("id") int id, Model model) {
        prepareCategoryView(id, model);
        return "edit_category";
    }

    @PostMapping("/dashboard/category/edit/save/{id}")
    public String editCategoryValues(@PathVariable("id") int id, @ModelAttribute CategoryDto categoryDto) {
        categoryService.updateCategoryValues(id,categoryDto);
        return "redirect:/dashboard/category/edit/{id}?success";
    }

    @PostMapping("/dashboard/category/delete/{id}")
    public String deleteTask(@PathVariable("id") int id){
        categoryService.deleteCategory(id);
        return "redirect:/dashboard";
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.findUserByUsername(authentication.getName());
    }
    private void prepareCategoryView(int id, Model model) {
        User user = getAuthenticatedUser();
        CategoryDto categoryDto = categoryService.findById(id);
        List<TaskDto> tasks = taskService.findTasksbyCategory(id);
        List<TaskDto> upcoming = new ArrayList<>();
        List<TaskDto> today = new ArrayList<>();
        List<TaskDto> completed = new ArrayList<>();
        model.addAttribute("category", categoryDto);
        for (TaskDto task : tasks) {
            task.formatDate("E, dd MMM yyyy");
            if (task.getDueDate().isAfter(LocalDate.now())) {
                upcoming.add(task);
            } else if (task.getDueDate().isEqual(LocalDate.now())) {
                today.add(task);
            } else if (task.getDueDate().isBefore(LocalDate.now())) {
                completed.add(task);
            }
        }
        int num_task = tasks.size();
        int num_task_completed = tasks.stream().filter(e -> e.getStatus().equals("Completed")).toList().size();
        if (num_task == 0) {
            num_task = 1;
        }
        model.addAttribute("progress", (num_task_completed * 100 / num_task));
        model.addAttribute("user", user);
        model.addAttribute("upcoming_tasks", upcoming);
        model.addAttribute("today_tasks", today);
        model.addAttribute("completed_tasks", completed);
    }



}
