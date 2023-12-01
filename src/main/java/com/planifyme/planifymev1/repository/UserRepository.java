package com.planifyme.planifymev1.repository;

import com.planifyme.planifymev1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
