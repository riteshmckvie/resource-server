package com.ritesh.application.service;

import java.util.List;

import com.ritesh.application.model.User;

public interface UserService {

    User save(User user);
    List<User> findAll();
}
