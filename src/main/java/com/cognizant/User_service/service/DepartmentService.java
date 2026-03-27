package com.cognizant.User_service.service;

import com.cognizant.User_service.domain.Department;
import com.cognizant.User_service.dto.CreateDepartmentRequest;
import org.springframework.stereotype.Service;

@Service
public interface DepartmentService {
    Department createDepartment(CreateDepartmentRequest createDepartmentRequest);
}
