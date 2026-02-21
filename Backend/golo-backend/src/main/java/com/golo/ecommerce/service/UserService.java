package com.golo.ecommerce.service;

import com.golo.ecommerce.dto.request.RegisterRequest;
import com.golo.ecommerce.entity.User;

import java.util.List;

public interface UserService {
    User registerUser(RegisterRequest registerRequest);
    User getUserById(Long id);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    User getCurrentUser();
}