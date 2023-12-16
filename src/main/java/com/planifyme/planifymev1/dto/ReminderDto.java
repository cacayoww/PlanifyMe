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
public class ReminderDto extends FormattedDate{
    private int idReminder;
    private String namaUser;
    private String namaTask;
    private String namaKategori;
    private String formattedDueDate;
    private String dateRemindertoNow;
    private LocalDate dateReminder;
    private LocalDate dueDate;
    private String imageUrl;

    @Override
    public void formatDate(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
        String day = "";
        if (dateReminder.equals(dueDate)){
            day = "Today, ";
        }else if (dateReminder.equals(dueDate.minusDays(1))){
            day = "Tomorrow, ";
        }else if (dateReminder.equals(dueDate.minusDays(3))){
            day = "Three Days More, ";
        }else if (dateReminder.equals(dueDate.minusWeeks(1))){
            day = "Next Week, ";
        }
        formattedDueDate = day+dueDate.format(formatter);
    }
}
