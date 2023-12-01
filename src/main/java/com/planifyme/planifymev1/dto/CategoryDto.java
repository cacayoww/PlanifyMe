package com.planifyme.planifymev1.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private int idCategory;
    private String nama;
    private String deskripsi;
    private String color;
    private String imageUrl;

    public CategoryDto(String nama, String deskripsi, String color, String imageUrl) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.color = color;
        this.imageUrl = imageUrl;
    }
}
