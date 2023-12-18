package com.planifyme.planifymev1.service;

import com.planifyme.planifymev1.dto.UserDto;
import com.planifyme.planifymev1.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByUsername(String username);
    void updateUser(int idUser, UserDto userDto);
}
