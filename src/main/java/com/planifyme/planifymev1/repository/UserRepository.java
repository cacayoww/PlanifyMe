package com.planifyme.planifymev1.repository;

import com.planifyme.planifymev1.model.Category;
import com.planifyme.planifymev1.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.namaLengkap=:newName, u.password=:newPassword WHERE u.idUser=:userId")
    void updateUserFields(@Param("userId") int userId, @Param("newName") String newName, @Param("newPassword") String newPassword);
}
