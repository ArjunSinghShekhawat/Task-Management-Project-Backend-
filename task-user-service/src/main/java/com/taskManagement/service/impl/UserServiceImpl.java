package com.taskManagement.service.impl;

import com.taskManagement.config.JwtProvider;
import com.taskManagement.models.User;
import com.taskManagement.repository.UserRepository;
import com.taskManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserProfile(String jwt) {
        String email = JwtProvider.getEmail(jwt);
        return userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("user not found with email "+email));
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
