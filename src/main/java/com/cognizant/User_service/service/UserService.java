package com.cognizant.User_service.service;

import com.cognizant.User_service.domain.User;
import com.cognizant.User_service.dto.CreateUserRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {
    User createUser(CreateUserRequest createUserRequest);
    User getUserById(UUID id);
    User updateUser(UUID id, CreateUserRequest updateRequest);
    List<User> getAllUsers();
}
