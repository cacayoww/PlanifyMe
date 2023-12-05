package com.planifyme.planifymev1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private int idTask;
    private String nama;
    private String kategori;
    private LocalDate dueDate;
    private String[] reminder = new String[4];
}
