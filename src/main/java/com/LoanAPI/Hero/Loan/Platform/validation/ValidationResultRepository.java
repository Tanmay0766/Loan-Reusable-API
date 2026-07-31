package com.LoanAPI.Hero.Loan.Platform.validation;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ValidationResultRepository extends JpaRepository<StageValidationResult, Long> {

    List<StageValidationResult> findByApplicationIdOrderByValidatedAtAsc(Long applicationId);
}