package com.planifyme.planifymev1.repository;

import com.planifyme.planifymev1.dto.TaskDto;
import com.planifyme.planifymev1.model.Task;
import com.planifyme.planifymev1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findAllByUser(User user);
}
