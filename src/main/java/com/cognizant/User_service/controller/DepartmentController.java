package com.cognizant.User_service.controller;

import com.cognizant.User_service.domain.Department;
import com.cognizant.User_service.dto.CreateDepartmentRequest;
import com.cognizant.User_service.repository.DepartmentRepository;
import com.cognizant.User_service.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    private DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public Department createDepartment(@RequestBody CreateDepartmentRequest createDepartmentRequest) {
        return departmentService.createDepartment(createDepartmentRequest);
    }
}
