package com.planifyme.planifymev1.service.impl;


import com.planifyme.planifymev1.dto.ReminderDto;
import com.planifyme.planifymev1.model.Reminder;
import com.planifyme.planifymev1.model.Task;
import com.planifyme.planifymev1.model.User;
import com.planifyme.planifymev1.repository.ReminderRepository;
import com.planifyme.planifymev1.repository.TaskRepository;
import com.planifyme.planifymev1.service.ReminderService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReminderServiceImpl implements ReminderService {

    private final ReminderRepository reminderRepository;
    private final TaskRepository taskRepository;



    public ReminderServiceImpl(ReminderRepository reminderRepository, TaskRepository taskRepository) {
        this.reminderRepository = reminderRepository;
        this.taskRepository = taskRepository;
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
    public List<ReminderDto> findAllReminderDtos(Task task) {
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
            System.out.println("masuk 0");
        }else if (keterangan == 1){
            reminderDto.setDateReminder(task.getDueDate().minusDays(3));
            System.out.println("masuk 1");
        }else if (keterangan == 2){
            reminderDto.setDateReminder(task.getDueDate().minusDays(1));
            System.out.println("masuk 2");
        }else{
            reminderDto.setDateReminder(task.getDueDate());
            System.out.println(task.getDueDate());
            System.out.println("masuk 3");
        }
        System.out.println(reminderDto.getDateReminder());
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
        long gapInDays = Math.abs(reminder.getDateReminder().toEpochDay() - LocalDate.now().toEpochDay());
        if (gapInDays == 0){
            reminderDto.setDateRemindertoNow("Today");
        }else if (gapInDays == 1){
            reminderDto.setDateRemindertoNow("a day ago");
        }else if (gapInDays < 7){
            reminderDto.setDateRemindertoNow(gapInDays+" days ago");
        }else if (gapInDays < 14){
            reminderDto.setDateRemindertoNow("a week ago");
        } else {
            int gap = (int) (gapInDays/7);
            reminderDto.setDateRemindertoNow(gap+" weeks ago");
        }

        return reminderDto;
    }


}
