package com.cognizant.User_service.domain;

import com.cognizant.User_service.enums.SkillLevel;
import com.cognizant.User_service.enums.Status;
import com.cognizant.User_service.enums.TechnicalLevel;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue
    @Column(name = "userId")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false, updatable = false)
    private String email;

    @JoinColumn(name = "departmentId", referencedColumnName = "departmentId", nullable = false)
    private UUID departmentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TechnicalLevel technicalLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SkillLevel skillLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ColumnDefault(value = "false")
    private boolean deleted;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserSkills> skills = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}

