package com.planifyme.planifymev1.repository;

import com.planifyme.planifymev1.model.Category;
import com.planifyme.planifymev1.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByUser(User user);
    Category findByNamaAndUser(String kategori, User user);
    Category findByIdCategory(int id);
    @Transactional
    @Modifying
    @Query("UPDATE Category c SET c.nama = :newName, c.deskripsi = :newDescription, c.color = :newColor WHERE c.idCategory = :categoryId")
    void updateCategoryFields(@Param("categoryId") int categoryId, @Param("newName") String newName, @Param("newDescription") String newDescription, @Param("newColor") String newColor);
}
