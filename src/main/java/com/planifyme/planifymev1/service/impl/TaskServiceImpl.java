package com.planifyme.planifymev1.service.impl;

import com.planifyme.planifymev1.dto.ReminderDto;
import com.planifyme.planifymev1.dto.TaskDto;
import com.planifyme.planifymev1.model.Category;
import com.planifyme.planifymev1.model.Reminder;
import com.planifyme.planifymev1.model.Task;
import com.planifyme.planifymev1.model.User;
import com.planifyme.planifymev1.repository.CategoryRepository;
import com.planifyme.planifymev1.repository.ReminderRepository;
import com.planifyme.planifymev1.repository.TaskRepository;
import com.planifyme.planifymev1.repository.UserRepository;
import com.planifyme.planifymev1.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private ReminderRepository reminderRepository;

    public TaskServiceImpl(TaskRepository taskRepository,
                           UserRepository userRepository,
                           CategoryRepository categoryRepository,
                           ReminderRepository reminderRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.reminderRepository =reminderRepository;
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
    public Task updateTaskStatus(int taskId) {
        Task task = taskRepository.findByIdTask(taskId);
        task.setStatus(!task.isStatus());
        taskRepository.save(task);
        System.out.println(task.isStatus());
        return task;
    }

    @Override
    public void deleteTask(int idTask) {
        Task task = taskRepository.findByIdTask(idTask);
        reminderRepository.deleteByTask(task);
        taskRepository.deleteByIdTask(idTask);
    }

    @Override
    public Task updateTaskValues(int taskId, TaskDto newTask) {
        Task task = taskRepository.findByIdTask(taskId);
        if (!task.getNama().equals(newTask.getNama())){
            task.setNama(newTask.getNama());
        }
        LocalDate[] dateReminders = new LocalDate[4];
        if (newTask.getReminder()[0] != null){
            dateReminders[0] = (newTask.getDueDate().minusWeeks(1));
        }else if (newTask.getReminder()[1] != null){
            dateReminders[1] = (newTask.getDueDate().minusDays(3));
        }else if (newTask.getReminder()[2] != null){
            dateReminders[2] = (newTask.getDueDate().minusDays(1));
        }else if (newTask.getReminder()[3] != null){
            dateReminders[3] = (newTask.getDueDate());
        }
        List<Reminder> reminders = reminderRepository.findAllByTask(task);
        for (Reminder r: reminders){
            if (r.getDateReminder() != dateReminders[0] &&
                    r.getDateReminder() != dateReminders[1] &&
                    r.getDateReminder() != dateReminders[2] &&
                    r.getDateReminder() != dateReminders[3]){
                reminderRepository.deleteByTask(task);
                break;
            }
        }
        if (!task.getDueDate().equals(newTask.getDueDate())){
            reminderRepository.deleteByTask(task);
            task.setDueDate(newTask.getDueDate());
        }
        if (!task.getCategory().getNama().equals(newTask.getKategori())){
            Category category = categoryRepository.findByNamaAndUser(newTask.getKategori(),task.getUser());
            task.setCategory(category);
        }
        Task savedTask = taskRepository.save(task);
        return savedTask;
    }


    private TaskDto convertModeltoDto(Task task){
        TaskDto taskDto = new TaskDto();
        taskDto.setIdTask(task.getIdTask());
        taskDto.setNama(task.getNama());
        taskDto.setKategori(task.getCategory().getNama());
        taskDto.setDueDate(task.getDueDate());
        List<Reminder> reminders = reminderRepository.findAllByTask(task);
        String[] remindersString = new String[4];
        for (Reminder reminder: reminders){
            if (reminder.getDateReminder().equals(task.getDueDate().minusWeeks(1))){
                remindersString[0] = "a week before";
            }
            if (reminder.getDateReminder().equals(task.getDueDate().minusDays(3))){
                remindersString[1] = "three days before";
            }
            if (reminder.getDateReminder().equals(task.getDueDate().minusDays(1))){
                remindersString[2] = "a day before";
            }
            if (reminder.getDateReminder().equals(task.getDueDate())){
                remindersString[3] = "on the day";
            }
        }
        taskDto.setReminder(remindersString);
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
