package com.LoanAPI.Hero.Loan.Platform.vkyc;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class VkycValidator {

    private static final Set<String> VALID_STATUSES = Set.of("PENDING", "COMPLETED", "FAILED", "REJECTED");
    private static final Set<String> VALID_MODES = Set.of("LIVE_VIDEO_CALL", "RECORDED");
    private static final Set<String> VALID_DEVICES = Set.of("MOBILE", "WEB");
    private static final Set<String> VALID_LIVENESS_RESULTS = Set.of("PASS", "FAIL");
    private static final Set<String> VALID_DOCUMENTS = Set.of("PAN", "AADHAAR", "PASSPORT", "VOTER_ID", "DRIVING_LICENSE");

    public List<String> validate(VkycRequest request) {
        List<String> failureReasons = new ArrayList<>();

        if (request.getApplicationId() == null) {
            failureReasons.add("Application ID is required");
        }

        String status = request.getVkycStatus();
        if (isBlank(status)) {
            failureReasons.add("V-KYC status is required");
            return failureReasons;
        }

        String normalizedStatus = status.trim().toUpperCase();
        if (!VALID_STATUSES.contains(normalizedStatus)) {
            failureReasons.add("V-KYC status must be PENDING, COMPLETED, FAILED, or REJECTED");
            return failureReasons;
        }

        if (normalizedStatus.equals("PENDING")) {
            // Nothing further to check — session hasn't happened yet
            return failureReasons;
        }

        if (normalizedStatus.equals("REJECTED")) {
            validateRejected(request, failureReasons);
            return failureReasons;
        }

        // COMPLETED or FAILED both require a fully recorded session
        validateCompletedOrFailedSession(request, normalizedStatus, failureReasons);

        return failureReasons;
    }

    private void validateRejected(VkycRequest request, List<String> failureReasons) {
        if (isBlank(request.getRejectionReason())) {
            failureReasons.add("Rejection reason is required when V-KYC status is REJECTED");
        }
        if (isBlank(request.getAgentId())) {
            failureReasons.add("Agent ID is required when V-KYC status is REJECTED");
        }
    }

    private void validateCompletedOrFailedSession(VkycRequest request, String status, List<String> failureReasons) {

        if (isBlank(request.getSessionReferenceId())) {
            failureReasons.add("Session reference ID is required for a " + status + " V-KYC session");
        }

        if (isBlank(request.getAgentId())) {
            failureReasons.add("Agent ID is required for a " + status + " V-KYC session");
        }

        if (isBlank(request.getAgentName())) {
            failureReasons.add("Agent name is required for a " + status + " V-KYC session");
        }

        LocalDateTime verificationTime = request.getVerificationDateTime();
        if (verificationTime == null) {
            failureReasons.add("Verification date/time is required for a " + status + " V-KYC session");
        } else if (verificationTime.isAfter(LocalDateTime.now())) {
            failureReasons.add("Verification date/time cannot be in the future");
        }

        String mode = request.getVerificationMode();
        if (isBlank(mode)) {
            failureReasons.add("Verification mode is required for a " + status + " V-KYC session");
        } else if (!VALID_MODES.contains(mode.trim().toUpperCase())) {
            failureReasons.add("Verification mode must be LIVE_VIDEO_CALL or RECORDED");
        }

        String device = request.getDeviceUsed();
        if (isBlank(device)) {
            failureReasons.add("Device used is required for a " + status + " V-KYC session");
        } else if (!VALID_DEVICES.contains(device.trim().toUpperCase())) {
            failureReasons.add("Device used must be MOBILE or WEB");
        }

        String document = request.getDocumentShown();
        if (isBlank(document)) {
            failureReasons.add("Document shown is required for a " + status + " V-KYC session");
        } else if (!VALID_DOCUMENTS.contains(document.trim().toUpperCase())) {
            failureReasons.add("Document shown must be one of PAN, AADHAAR, PASSPORT, VOTER_ID, DRIVING_LICENSE");
        }

        String liveness = request.getLivenessCheckResult();
        if (isBlank(liveness)) {
            failureReasons.add("Liveness check result is required for a " + status + " V-KYC session");
        } else if (!VALID_LIVENESS_RESULTS.contains(liveness.trim().toUpperCase())) {
            failureReasons.add("Liveness check result must be PASS or FAIL");
        }

        // Cross-field logic: a session marked COMPLETED shouldn't have a FAILED liveness check
        if (status.equals("COMPLETED") && "FAIL".equalsIgnoreCase(liveness)) {
            failureReasons.add("V-KYC cannot be marked COMPLETED when liveness check result is FAIL");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}