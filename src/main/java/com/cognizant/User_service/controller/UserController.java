package com.cognizant.User_service.controller;

import com.cognizant.User_service.domain.User;
import com.cognizant.User_service.dto.CreateUserRequest;
import com.cognizant.User_service.enums.Status;
import com.cognizant.User_service.repository.UserRepository;
import com.cognizant.User_service.service.UserService;
import com.library.common.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;

    private UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUser(createUserRequest);
        return ResponseEntity.ok(ApiResponse.<User>builder()
                .success(true)
                .message("User created successfully")
                .data(user)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable UUID id) {
        log.info("GET /api/users/{}", id);
        User user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.<User>builder()
                .success(true)
                .data(user)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @PathVariable UUID id,
            @RequestBody CreateUserRequest updateRequest) {
        log.info("PUT /api/users/{}", id);
        User user = userService.updateUser(id, updateRequest);
        return ResponseEntity.ok(ApiResponse.<User>builder()
                .success(true)
                .message("User updated successfully")
                .data(user)
                .build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        log.info("GET /api/users - list all users (admin only)");
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.<List<User>>builder()
                .success(true)
                .data(users)
                .build());
    }
}
