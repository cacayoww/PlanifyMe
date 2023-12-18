package com.planifyme.planifymev1.repository;

import com.planifyme.planifymev1.model.Category;
import com.planifyme.planifymev1.model.Task;
import com.planifyme.planifymev1.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByUser(User user);
    List<Task> findAllByCategory(Category category);
    Task findByIdTask(int idTask);
    @Transactional
    void deleteByIdTask(int idTask);
    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.status = :newStatus WHERE t.idTask = :taskId")
    void updateTaskByStatus(@Param("taskId") int taskId, @Param("newStatus") Boolean newStatus);
    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.nama=:newName, t.category=:newCategory, t.dueDate=:newDueDate WHERE t.idTask=:taskId")
    void updateTaskField(@Param("taskId") int taskId, @Param("newName") String newName, @Param("newCategory") Category newCategory, @Param("newDueDate") LocalDate newDueDate);
}
