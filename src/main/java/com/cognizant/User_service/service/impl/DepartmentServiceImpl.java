package com.cognizant.User_service.service.impl;

import com.cognizant.User_service.domain.Department;
import com.cognizant.User_service.dto.CreateDepartmentRequest;
import com.cognizant.User_service.repository.DepartmentRepository;
import com.cognizant.User_service.repository.UserRepository;
import com.cognizant.User_service.service.DepartmentService;

public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department createDepartment(CreateDepartmentRequest createDepartmentRequest) {
        Department department = new Department();
        department.setName(createDepartmentRequest.getName());
        department.setDescription(createDepartmentRequest.getDescription());
        return departmentRepository.save(department);
    }
}
