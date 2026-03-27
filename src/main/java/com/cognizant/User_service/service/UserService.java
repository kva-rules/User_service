package com.cognizant.User_service.service;

import com.cognizant.User_service.domain.User;
import com.cognizant.User_service.dto.CreateUserRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User createUser(CreateUserRequest createUserRequest);
}
