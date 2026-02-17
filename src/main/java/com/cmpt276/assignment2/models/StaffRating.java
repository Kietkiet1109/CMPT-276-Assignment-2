package com.cmpt276.assignment2.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * JPA entity representing a rating for a CS teaching staff member.
 */
@Entity
@Table(name = "staff_ratings")
public class StaffRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @NotNull(message = "Clarity score is required")
    @Min(value = 1, message = "Clarity must be at least 1")
    @Max(value = 10, message = "Clarity must be at most 10")
    private Integer clarity;

    @NotNull(message = "Niceness score is required")
    @Min(value = 1, message = "Niceness must be at least 1")
    @Max(value = 10, message = "Niceness must be at most 10")
    private Integer niceness;

    @NotNull(message = "Knowledge score is required")
    @Min(value = 1, message = "Knowledge score must be at least 1")
    @Max(value = 10, message = "Knowledge score must be at most 10")
    private Integer knowledgeableScore;

    @Size(max = 500, message = "Comment must be 500 characters or fewer")
    private String comment;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default constructor required by JPA
    public StaffRating() {
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Uses polymorphism to get the display title for this staff member's role.
     */
    public String getDisplayTitle() {
        if (roleType == null) {
            return "Unknown";
        }
        StaffMemberProfile profile = StaffMemberProfileFactory.fromRole(this.roleType);
        return profile.displayTitle();
    }

    /**
     * Computes the average of the three rating scores.
     */
    public double getOverallScore() {
        int c = (clarity != null) ? clarity : 0;
        int n = (niceness != null) ? niceness : 0;
        int k = (knowledgeableScore != null) ? knowledgeableScore : 0;
        return Math.round((c + n + k) / 3.0 * 10.0) / 10.0;
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Integer getClarity() {
        return clarity;
    }

    public void setClarity(Integer clarity) {
        this.clarity = clarity;
    }

    public Integer getNiceness() {
        return niceness;
    }

    public void setNiceness(Integer niceness) {
        this.niceness = niceness;
    }

    public Integer getKnowledgeableScore() {
        return knowledgeableScore;
    }

    public void setKnowledgeableScore(Integer knowledgeableScore) {
        this.knowledgeableScore = knowledgeableScore;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
