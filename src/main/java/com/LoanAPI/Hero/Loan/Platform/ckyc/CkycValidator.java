package com.LoanAPI.Hero.Loan.Platform.ckyc;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class CkycValidator {

    private static final Pattern PINCODE_PATTERN = Pattern.compile("^[1-9][0-9]{5}$");

    public List<String> validate(CkycRequest request) {
        List<String> failureReasons = new ArrayList<>();

        if (request.getApplicationId() == null) {
            failureReasons.add("Application ID is required");
        }

        if (request.isCkycFound()) {
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
