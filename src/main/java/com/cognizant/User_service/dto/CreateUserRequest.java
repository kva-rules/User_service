package com.cognizant.User_service.dto;

import com.cognizant.User_service.enums.SkillLevel;
import com.cognizant.User_service.enums.TechnicalLevel;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateUserRequest {

    private String name;
    private String email;
    private UUID departmentId;
    private TechnicalLevel technicalLevel;
    private SkillLevel skillLevel;
}
