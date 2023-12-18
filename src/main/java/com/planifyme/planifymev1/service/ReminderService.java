package com.planifyme.planifymev1.service;

import com.planifyme.planifymev1.dto.ReminderDto;
import com.planifyme.planifymev1.model.Task;
import com.planifyme.planifymev1.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReminderService {
    void saveReminder(ReminderDto reminderDto, int idTask);
    List<ReminderDto> findAllReminderDtos(Task task);
    ReminderDto createReminder(User user, Task task, int keterangan);
}
