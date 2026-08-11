package com.LoanAPI.Hero.Loan.Platform.workdata;

import com.LoanAPI.Hero.Loan.Platform.application.ApplicationRepository;
import com.LoanAPI.Hero.Loan.Platform.application.LoanApplication;
import com.LoanAPI.Hero.Loan.Platform.validation.StageValidationResult;
import com.LoanAPI.Hero.Loan.Platform.validation.ValidationResultRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/validate/workdata")
public class WorkDataController {

    private static final String STAGE_NAME = "WORK_DATA";

    private final WorkDataValidator workDataValidator;
    private final ValidationResultRepository validationResultRepository;
    private final ApplicationRepository applicationRepository;

    public WorkDataController(WorkDataValidator workDataValidator,
                              ValidationResultRepository validationResultRepository,
                              ApplicationRepository applicationRepository) {
        this.workDataValidator = workDataValidator;
        this.validationResultRepository = validationResultRepository;
        this.applicationRepository = applicationRepository;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> validateWorkData(@RequestBody WorkDataRequest request) {

        List<String> failureReasons = workDataValidator.validate(request);
        boolean passed = failureReasons.isEmpty();

        StageValidationResult result = new StageValidationResult();
        result.setApplicationId(request.getApplicationId());
        result.setStage(STAGE_NAME);
        result.setPassed(passed);
        result.setFailureReasons(passed ? null : String.join("; ", failureReasons));

        validationResultRepository.save(result);

        if (passed && request.getApplicationId() != null) {
            Optional<LoanApplication> applicationOpt = applicationRepository.findById(request.getApplicationId());
            applicationOpt.ifPresent(application -> {
                application.setCurrentStage(STAGE_NAME);
                applicationRepository.save(application);
            });
        }

        Map<String, Object> response = Map.of(
                "passed", passed,
                "failureReasons", failureReasons
        );

        return new ResponseEntity<>(response, passed ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}