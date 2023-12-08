package com.planifyme.planifymev1.repository;

import com.planifyme.planifymev1.model.Category;
import com.planifyme.planifymev1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findAllByUser(User user);

    Category findByNamaAndUser(String kategori, User user);

    Category findByIdCategory(int id);
}
