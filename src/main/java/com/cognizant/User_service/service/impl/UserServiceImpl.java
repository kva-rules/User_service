package com.cognizant.User_service.service.impl;

import com.cognizant.User_service.domain.User;
import com.cognizant.User_service.dto.CreateUserRequest;
import com.cognizant.User_service.enums.Status;
import com.cognizant.User_service.repository.UserRepository;
import com.cognizant.User_service.service.UserService;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setName(createUserRequest.getName());
        user.setEmail(createUserRequest.getEmail());
        user.setDepartmentId(createUserRequest.getDepartmentId());
        user.setTechnicalLevel(createUserRequest.getTechnicalLevel());
        user.setSkillLevel(createUserRequest.getSkillLevel());
        user.setStatus(Status.ACTIVE);
        return userRepository.save(user);
    }
}
