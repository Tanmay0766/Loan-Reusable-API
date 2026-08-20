package com.LoanAPI.Hero.Loan.Platform.pan;

import com.LoanAPI.Hero.Loan.Platform.application.ApplicationRepository;
import com.LoanAPI.Hero.Loan.Platform.application.LoanApplication;
import com.LoanAPI.Hero.Loan.Platform.validation.StageOrder;
import com.LoanAPI.Hero.Loan.Platform.validation.StageSequenceEnforcer;
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
@RequestMapping("/validate/pan")
public class PanController {

    private static final String STAGE_NAME = "PAN";

    private final PanValidator panValidator;
    private final ValidationResultRepository validationResultRepository;
    private final ApplicationRepository applicationRepository;
    private final StageSequenceEnforcer stageSequenceEnforcer;

    public PanController(PanValidator panValidator,
                         ValidationResultRepository validationResultRepository,
                         ApplicationRepository applicationRepository,
                         StageSequenceEnforcer stageSequenceEnforcer) {
        this.panValidator = panValidator;
        this.validationResultRepository = validationResultRepository;
        this.applicationRepository = applicationRepository;
        this.stageSequenceEnforcer = stageSequenceEnforcer;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> validatePan(@RequestBody PanRequest request) {

        Optional<String> prerequisiteError = stageSequenceEnforcer.checkPrerequisites(
                request.getApplicationId(), StageOrder.PAN, false);

        if (prerequisiteError.isPresent()) {
            Map<String, Object> response = Map.of(
                    "passed", false,
                    "failureReasons", List.of(prerequisiteError.get())
            );
            return new ResponseEntity<>(response, HttpStatus.PRECONDITION_FAILED);
        }

        List<String> failureReasons = panValidator.validate(request);
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