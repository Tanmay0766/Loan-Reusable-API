package com.LoanAPI.Hero.Loan.Platform.validation;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stage_validation_result")
public class StageValidationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "application_id", nullable = false)
    private Long applicationId;

    @Column(name = "stage", nullable = false, length = 30)
    private String stage;

    @Column(name = "passed", nullable = false)
    private Boolean passed;

    @Column(name = "failure_reasons")
    private String failureReasons;

    @Column(name = "validated_at")
    private LocalDateTime validatedAt;

    @PrePersist
    protected void onCreate() {
        this.validatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public String getFailureReasons() {
        return failureReasons;
    }

    public void setFailureReasons(String failureReasons) {
        this.failureReasons = failureReasons;
    }

    public LocalDateTime getValidatedAt() {
        return validatedAt;
    }
}