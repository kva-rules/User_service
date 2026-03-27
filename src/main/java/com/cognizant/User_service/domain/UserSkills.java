package com.cognizant.User_service.domain;

import com.cognizant.User_service.enums.SkillLevel;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class UserSkills {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "skillId")
    private Skill skill;

    private int yearOfExperience;

    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel;
}
