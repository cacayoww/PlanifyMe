package com.planifyme.planifymev1.service.impl;


import com.planifyme.planifymev1.dto.ReminderDto;
import com.planifyme.planifymev1.dto.TaskDto;
import com.planifyme.planifymev1.model.Category;
import com.planifyme.planifymev1.model.Reminder;
import com.planifyme.planifymev1.model.Task;
import com.planifyme.planifymev1.model.User;
import com.planifyme.planifymev1.repository.ReminderRepository;
import com.planifyme.planifymev1.repository.TaskRepository;
import com.planifyme.planifymev1.service.CategoryService;
import com.planifyme.planifymev1.service.ReminderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReminderServiceImpl implements ReminderService {

    private ReminderRepository reminderRepository;
    private TaskRepository taskRepository;

    private CategoryService categoryService;

    public ReminderServiceImpl(ReminderRepository reminderRepository, TaskRepository taskRepository, CategoryService categoryService) {
        this.reminderRepository = reminderRepository;
        this.taskRepository = taskRepository;
        this.categoryService = categoryService;
    }

    @Override
    public void saveReminder(ReminderDto reminderDto, int idTask) {
        Reminder reminder = new Reminder();
        Task task = taskRepository.findByIdTask(idTask);
        reminder.setTask(task);
        reminder.setDateReminder(reminderDto.getDateReminder());
        reminderRepository.save(reminder);
    }

    @Override
    public List<ReminderDto> findAllReminders(Task task) {
        List<Reminder> reminders = reminderRepository.findAllByTask(task);

        return reminders.stream().map(this::convertModeltoDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReminderDto createReminder(User user, Task task, int keterangan) {

        ReminderDto reminderDto = new ReminderDto();
        reminderDto.setNamaUser(user.getNamaLengkap());
        reminderDto.setNamaTask(task.getNama());
        reminderDto.setNamaKategori(task.getCategory().getNama());
        reminderDto.setImageUrl(task.getCategory().getImageUrl());
        reminderDto.setDueDate(task.getDueDate());
        if (keterangan == 0){
            reminderDto.setDateReminder(task.getDueDate().minusWeeks(1));
        }else if (keterangan == 1){
            reminderDto.setDateReminder(task.getDueDate().minusDays(3));
        }else if (keterangan == 2){
            reminderDto.setDateReminder(task.getDueDate().minusDays(1));
        }else{
            reminderDto.setDateReminder(task.getDueDate());
        }
        return reminderDto;
    }

    private ReminderDto convertModeltoDto(Reminder reminder){
        ReminderDto reminderDto = new ReminderDto();
        reminderDto.setNamaUser(reminder.getTask().getUser().getNamaLengkap());
        reminderDto.setNamaTask(reminder.getTask().getNama());
        reminderDto.setNamaKategori(reminder.getTask().getCategory().getNama());
        reminderDto.setImageUrl(reminder.getTask().getCategory().getImageUrl());
        reminderDto.setDueDate(reminder.getTask().getDueDate());
        reminderDto.setDateReminder(reminder.getDateReminder());
        return reminderDto;
    }


}
