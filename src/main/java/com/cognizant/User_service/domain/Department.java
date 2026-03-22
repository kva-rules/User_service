package com.cognizant.User_service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Transient;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Department {

    @Id
    @GeneratedValue
    @Column(name = "departmentId")
    @Transient
    private UUID id;

    @Column(name = "departmentName", unique = true, nullable = false)
    private String name;

    private String description;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
