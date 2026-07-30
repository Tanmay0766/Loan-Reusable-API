package com.LoanAPI.Hero.Loan.Platform.application;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public LoanApplication createApplication(LoanApplication application) {
        return applicationRepository.save(application);
    }

    public Optional<LoanApplication> getApplication(Long id) {
        return applicationRepository.findById(id);
    }
}