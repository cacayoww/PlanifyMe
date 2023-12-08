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
public class ReminderDto {
    private int idReminder;

    private String namaUser;

    private String namaTask;

    private String namaKategori;

    private String formattedDueDate;

    private String dateRemindertoNow;

    private LocalDate dateReminder;

    private LocalDate dueDate;

    private String imageUrl;
}
