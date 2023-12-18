package com.planifyme.planifymev1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCategory;
    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;
    private String nama;
    private String deskripsi;
    private String color;
    private String imageUrl;
}
