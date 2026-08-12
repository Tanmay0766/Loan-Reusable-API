package com.LoanAPI.Hero.Loan.Platform.application;

import com.LoanAPI.Hero.Loan.Platform.validation.StageValidationResult;
import com.LoanAPI.Hero.Loan.Platform.validation.ValidationResultRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ValidationResultRepository validationResultRepository;

    public ApplicationController(ApplicationService applicationService,
                                 ValidationResultRepository validationResultRepository) {
        this.applicationService = applicationService;
        this.validationResultRepository = validationResultRepository;
    }

    @PostMapping
    public ResponseEntity<LoanApplication> createApplication(@RequestBody LoanApplication application) {
        LoanApplication saved = applicationService.createApplication(application);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanApplication> getApplication(@PathVariable Long id) {
        return applicationService.getApplication(id)
                .map(app -> new ResponseEntity<>(app, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<Map<String, Object>> getApplicationHistory(@PathVariable Long id) {

        return applicationService.getApplication(id)
                .map(application -> {
                    List<StageValidationResult> history =
                            validationResultRepository.findByApplicationIdOrderByValidatedAtAsc(id);

                    Map<String, Object> response = Map.of(
                            "applicationId", application.getId(),
                            "applicantName", application.getApplicantName(),
                            "currentStage", application.getCurrentStage(),
                            "history", history
                    );

                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}