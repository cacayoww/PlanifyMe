package com.planifyme.planifymev1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int idUser;

    private String namaLengkap;

    private String username;

    private String password;

    private String confirmationPassword;
}
