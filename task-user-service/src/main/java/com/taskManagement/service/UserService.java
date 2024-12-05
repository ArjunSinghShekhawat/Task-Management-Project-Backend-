package com.taskManagement.service;

import com.taskManagement.models.User;

import java.util.List;

public interface UserService {
    User getUserProfile(String jwt);
    List<User>getAllUser();
}
