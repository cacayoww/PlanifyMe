package com.planifyme.planifymev1.service;

import com.planifyme.planifymev1.dto.UserDto;
import com.planifyme.planifymev1.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByUsername(String username);
}
