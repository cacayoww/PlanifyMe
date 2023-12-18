package com.planifyme.planifymev1.service.impl;

import com.planifyme.planifymev1.dto.UserDto;
import com.planifyme.planifymev1.model.User;
import com.planifyme.planifymev1.repository.UserRepository;
import com.planifyme.planifymev1.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setNamaLengkap(userDto.getNamaLengkap());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        System.out.println(user.getUsername());
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void updateUser(int idUser, UserDto userDto) {
        userRepository.updateUserFields(idUser, userDto.getNamaLengkap(),passwordEncoder.encode(userDto.getNewPassword()));
    }

}


