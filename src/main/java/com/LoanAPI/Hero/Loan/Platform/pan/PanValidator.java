package com.LoanAPI.Hero.Loan.Platform.pan;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class PanValidator {

    private static final Pattern PAN_PATTERN = Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");

    private static final Set<Character> VALID_HOLDER_TYPE_CODES = Set.of(
            'P', // Individual
            'C', // Company
            'H', // Hindu Undivided Family
            'A', // Association of Persons
            'B', // Body of Individuals
            'G', // Government
            'J', // Artificial Judicial Person
            'L', // Local Authority
            'F', // Firm
            'T'  // Trust
    );

    public List<String> validate(PanRequest request) {
        List<String> failureReasons = new ArrayList<>();

        if (request.getApplicationId() == null) {
            failureReasons.add("Application ID is required");
        }

        String pan = request.getPanNumber();

        if (pan == null || pan.isBlank()) {
            failureReasons.add("PAN number is required");
            return failureReasons;
        }

        String trimmedPan = pan.trim().toUpperCase();

        if (trimmedPan.length() != 10) {
            failureReasons.add("PAN number must be exactly 10 characters");
            return failureReasons;
        }

        if (!PAN_PATTERN.matcher(trimmedPan).matches()) {
            failureReasons.add("PAN number format is invalid (expected format: AAAAA9999A)");
            return failureReasons;
        }

        char holderTypeCode = trimmedPan.charAt(3);
        if (!VALID_HOLDER_TYPE_CODES.contains(holderTypeCode)) {
            failureReasons.add("PAN number has an invalid holder type code at position 4: '" + holderTypeCode + "'");
        }

        return failureReasons;
    }
}