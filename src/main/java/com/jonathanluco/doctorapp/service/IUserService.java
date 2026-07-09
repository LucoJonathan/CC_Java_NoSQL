package com.jonathanluco.doctorapp.service;

import com.jonathanluco.doctorapp.dto.UserRequest;
import com.jonathanluco.doctorapp.model.User;

import java.util.List;

public interface IUserService {
    User create(UserRequest request);
    User authenticate(String email, String password);
    List<User> findAll();
    User findByEmail(String email);
    User findById(String id);
    User update(String id, UserRequest request);
    void delete(String id);
}
