package com.LoanAPI.Hero.Loan.Platform.aadhaar;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class AadhaarValidator {

    private static final Pattern AADHAAR_FORMAT_PATTERN = Pattern.compile("^[0-9]{12}$");
    private static final Pattern PINCODE_PATTERN = Pattern.compile("^[1-9][0-9]{5}$");

    public List<String> validate(AadhaarRequest request) {
        List<String> failureReasons = new ArrayList<>();

        if (request.getApplicationId() == null) {
            failureReasons.add("Application ID is required");
        }

        validateAadhaarNumber(request.getAadhaarNumber(), failureReasons);

        if (request.isAddressFound()) {
            validateAddress(
                    request.getFetchedAddressLine1(),
                    request.getFetchedCity(),
                    request.getFetchedState(),
                    request.getFetchedPincode(),
                    "fetched",
                    failureReasons
            );
        } else {
            validateAddress(
                    request.getManualAddressLine1(),
                    request.getManualCity(),
                    request.getManualState(),
                    request.getManualPincode(),
                    "manual",
                    failureReasons
            );
        }

        return failureReasons;
    }

    private void validateAadhaarNumber(String aadhaarNumber, List<String> failureReasons) {

        if (aadhaarNumber == null || aadhaarNumber.isBlank()) {
            failureReasons.add("Aadhaar number is required");
            return;
        }

        String trimmed = aadhaarNumber.trim();

        if (!AADHAAR_FORMAT_PATTERN.matcher(trimmed).matches()) {
            failureReasons.add("Aadhaar number must be exactly 12 digits");
            return;
        }

        if (trimmed.charAt(0) == '0' || trimmed.charAt(0) == '1') {
            failureReasons.add("Aadhaar number cannot start with 0 or 1");
            return;
        }

        if (!VerhoeffAlgorithm.isValid(trimmed)) {
            failureReasons.add("Aadhaar number failed checksum validation (invalid number)");
        }
    }

    private void validateAddress(String addressLine1, String city, String state, String pincode,
                                 String sourceLabel, List<String> failureReasons) {

        if (addressLine1 == null || addressLine1.isBlank()) {
            failureReasons.add("Address line 1 (" + sourceLabel + ") is required");
        }

        if (city == null || city.isBlank()) {
            failureReasons.add("City (" + sourceLabel + ") is required");
        }

        if (state == null || state.isBlank()) {
            failureReasons.add("State (" + sourceLabel + ") is required");
        }

        if (pincode == null || pincode.isBlank()) {
            failureReasons.add("Pincode (" + sourceLabel + ") is required");
        } else if (!PINCODE_PATTERN.matcher(pincode.trim()).matches()) {
            failureReasons.add("Pincode (" + sourceLabel + ") is invalid (must be 6 digits, not starting with 0)");
        }
    }
}