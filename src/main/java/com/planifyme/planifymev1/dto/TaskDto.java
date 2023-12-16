package com.planifyme.planifymev1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto extends FormattedDate{
    private int idTask;
    private String nama;
    private String kategori;
    private LocalDate dueDate;
    private String formattedDueDate;
    private String[] reminder = new String[4];
    private String status;
    private String warnaKategori;
    private String warnaStatus;

    @Override
    public void formatDate(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
        formattedDueDate = dueDate.format(formatter);
    }
}
