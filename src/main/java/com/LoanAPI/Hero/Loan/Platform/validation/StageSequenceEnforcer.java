package com.LoanAPI.Hero.Loan.Platform.validation;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StageSequenceEnforcer {

    private final ValidationResultRepository validationResultRepository;

    public StageSequenceEnforcer(ValidationResultRepository validationResultRepository) {
        this.validationResultRepository = validationResultRepository;
    }

    /**
     * Checks whether all stages before the given stage have already passed
     * for this application. GST is skipped in the check if not applicable.
     */
    public Optional<String> checkPrerequisites(Long applicationId, StageOrder stageAttempting, boolean gstApplicable) {

        if (applicationId == null) {
            return Optional.empty(); // let the stage's own validator report the missing ID
        }

        List<StageValidationResult> allResults =
                validationResultRepository.findByApplicationIdOrderByValidatedAtAsc(applicationId);

        Set<String> passedStages = allResults.stream()
                .filter(StageValidationResult::getPassed)
                .map(StageValidationResult::getStage)
                .collect(Collectors.toSet());

        int attemptingIndex = StageOrder.SEQUENCE.indexOf(stageAttempting);

        for (int i = 0; i < attemptingIndex; i++) {
            StageOrder requiredStage = StageOrder.SEQUENCE.get(i);

            if (requiredStage == StageOrder.GST && !gstApplicable) {
                continue; // GST not required for this applicant, skip
            }

            if (!passedStages.contains(requiredStage.name())) {
                return Optional.of(requiredStage.name() + " must be completed before " + stageAttempting.name());
            }
        }

        return Optional.empty();
    }
}