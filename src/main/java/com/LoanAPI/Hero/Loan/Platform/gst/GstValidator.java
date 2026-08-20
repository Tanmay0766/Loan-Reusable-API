package com.LoanAPI.Hero.Loan.Platform.gst;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class GstValidator {

    private static final Pattern GSTIN_FORMAT_PATTERN =
            Pattern.compile("^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$");

    private static final Set<String> VALID_REGISTRATION_TYPES =
            Set.of("REGULAR", "COMPOSITION", "CASUAL", "UNREGISTERED");

    public List<String> validate(GstRequest request) {
        List<String> failureReasons = new ArrayList<>();

        if (request.getApplicationId() == null) {
            failureReasons.add("Application ID is required");
        }

        String type = request.getGstRegistrationType();
        if (isBlank(type)) {
            failureReasons.add("GST registration type is required");
        } else {
            String normalizedType = type.trim().toUpperCase();
            if (!VALID_REGISTRATION_TYPES.contains(normalizedType)) {
                failureReasons.add("GST registration type must be REGULAR, COMPOSITION, CASUAL, or UNREGISTERED");
            } else if (normalizedType.equals("UNREGISTERED")) {
                return failureReasons; // no GSTIN expected if unregistered
            }
        }

        if (isBlank(request.getLegalBusinessName())) {
            failureReasons.add("Legal business name is required");
        }

        String gstin = request.getGstin();
        if (isBlank(gstin)) {
            failureReasons.add("GSTIN is required");
            return failureReasons;
        }

        String trimmedGstin = gstin.trim().toUpperCase();

        if (!GSTIN_FORMAT_PATTERN.matcher(trimmedGstin).matches()) {
            failureReasons.add("GSTIN format is invalid (expected 15-character format: 22AAAAA0000A1Z5)");
            return failureReasons;
        }

        if (!GstinChecksum.isValid(trimmedGstin)) {
            failureReasons.add("GSTIN failed checksum validation (invalid number)");
        }

        return failureReasons;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}