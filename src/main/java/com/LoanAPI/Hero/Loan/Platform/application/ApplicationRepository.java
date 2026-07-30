package com.LoanAPI.Hero.Loan.Platform.application;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<LoanApplication, Long> {
}