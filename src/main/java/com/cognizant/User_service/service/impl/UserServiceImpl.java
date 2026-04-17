package com.cognizant.User_service.service.impl;

import com.cognizant.User_service.domain.User;
import com.cognizant.User_service.dto.CreateUserRequest;
import com.cognizant.User_service.enums.Status;
import com.cognizant.User_service.repository.UserRepository;
import com.cognizant.User_service.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
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

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public User updateUser(UUID id, CreateUserRequest updateRequest) {
        User user = getUserById(id);
        if (updateRequest.getName() != null) {
            user.setName(updateRequest.getName());
        }
        if (updateRequest.getEmail() != null) {
            user.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getDepartmentId() != null) {
            user.setDepartmentId(updateRequest.getDepartmentId());
        }
        if (updateRequest.getTechnicalLevel() != null) {
            user.setTechnicalLevel(updateRequest.getTechnicalLevel());
        }
        if (updateRequest.getSkillLevel() != null) {
            user.setSkillLevel(updateRequest.getSkillLevel());
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
