package com.planifyme.planifymev1.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int idUser;
    @NotEmpty(message = "Nama Lengkap must not be empty")
    private String namaLengkap;
    @NotEmpty(message = "Username must not be empty")
    private String username;
    @NotEmpty(message = "Password must not be empty")
    private String password;
    @NotEmpty(message = "Confirmation Password must not be empty")
    private String confirmationPassword;
    @NotEmpty(message = "Old Password must not be empty")
    private String oldPassword;
    @NotEmpty(message = "New Password must not be empty")
    private String newPassword;
}
