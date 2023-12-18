package com.planifyme.planifymev1.service;

import com.planifyme.planifymev1.dto.TaskDto;
import com.planifyme.planifymev1.model.Task;
import com.planifyme.planifymev1.model.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    Task saveTask(TaskDto taskDto, String username);
    List<Task> findTasksbyUser(User user);
    List<TaskDto> findTaskDtosbyUser(User user);
    List<TaskDto> findTasksbyCategory(int idCategory);
    void updateTaskStatus(int taskId);
    void deleteTask(int idTask);
    void updateTaskValues(int taskId, TaskDto newTask);

}
