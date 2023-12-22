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
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;
    @Column(name = "nama")
    private String namaLengkap;

    private String username;
    private String password;
    public User(String namaLengkap, String username, String password) {
        this.namaLengkap = namaLengkap;
        this.username = username;
        this.password = password;

    }
}
