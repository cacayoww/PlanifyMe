package com.planifyme.planifymev1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reminder")
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReminder;

    @ManyToOne
    @JoinColumn(name = "idTask", nullable = false)
    private Task task;

    @Column(nullable = false)
    private LocalDate dateReminder;
}
