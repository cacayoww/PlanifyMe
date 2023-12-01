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

    @Column(nullable = false, unique = true)
    private String nama;

    @Column(nullable = false)
    private String deskripsi;
}
