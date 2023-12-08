package com.planifyme.planifymev1.service.impl;

import com.planifyme.planifymev1.dto.TaskDto;
import com.planifyme.planifymev1.model.Category;
import com.planifyme.planifymev1.model.Task;
import com.planifyme.planifymev1.model.User;
import com.planifyme.planifymev1.repository.CategoryRepository;
import com.planifyme.planifymev1.repository.TaskRepository;
import com.planifyme.planifymev1.repository.UserRepository;
import com.planifyme.planifymev1.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Task saveTask(TaskDto taskDto, String username) {
        Task task = new Task();
        task.setNama(taskDto.getNama());
        task.setDueDate(taskDto.getDueDate());
        User user = userRepository.findByUsername(username);
        task.setUser(user);
        Category category = categoryRepository.findByNamaAndUser(taskDto.getKategori(),user);
        task.setCategory(category);
        task.setStatus(false);
        Task savedTask = taskRepository.save(task);
        return savedTask;
    }

    @Override
    public List<Task> findTasksbyUser(User user) {
        return taskRepository.findAllByUser(user);
    }

    @Override
    public List<TaskDto> findTaskDtosbyUser(User user) {
        List<Task> tasks = taskRepository.findAllByUser(user);
        return tasks.stream().map(this::convertModeltoDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> findTasksbyCategory(int idCategory) {
        Category category = categoryRepository.findByIdCategory(idCategory);
        List<Task> tasks = taskRepository.findAllByCategory(category);
        return tasks.stream().map(this::convertModeltoDto).collect(Collectors.toList());
    }

    @Override
    public Task updateTaskStatus(int taskId, boolean newStatus) {
        Task task = taskRepository.findByIdTask(taskId);
        task.setStatus(!task.isStatus());
        taskRepository.save(task);
        System.out.println(task.isStatus());
        return task;
    }

    private TaskDto convertModeltoDto(Task task){
        TaskDto taskDto = new TaskDto();
        taskDto.setIdTask(task.getIdTask());
        taskDto.setNama(task.getNama());
        taskDto.setKategori(task.getCategory().getNama());
        taskDto.setDueDate(task.getDueDate());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, dd MMM yyyy", Locale.ENGLISH);
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("E, dd MMMM yyyy", Locale.ENGLISH);
        taskDto.setFormattedDueDate(task.getDueDate().format(formatter));
        taskDto.setFormattedDueDate2(task.getDueDate().format(formatter2));
        if (!task.isStatus() && LocalDate.now().isAfter(task.getDueDate())){
            taskDto.setStatus("Passed Due");
            taskDto.setWarnaStatus("#F01515");
        }else if (!task.isStatus() && (LocalDate.now().isBefore(task.getDueDate()) || LocalDate.now().isEqual(task.getDueDate()))) {
            taskDto.setStatus("Ongoing");
            taskDto.setWarnaStatus("#4E10B2");
        }else{
            taskDto.setStatus("Completed");
            taskDto.setWarnaStatus("#10B278");
        }
        taskDto.setWarnaKategori(task.getCategory().getColor());
        return taskDto;
    }
}
