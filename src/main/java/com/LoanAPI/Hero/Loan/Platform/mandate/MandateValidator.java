package com.LoanAPI.Hero.Loan.Platform.mandate;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class MandateValidator {

    private static final Pattern IFSC_PATTERN = Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");
    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^[0-9]{9,18}$");

    private static final Set<String> VALID_ACCOUNT_TYPES = Set.of("SAVINGS", "CURRENT");
    private static final Set<String> VALID_MANDATE_TYPES = Set.of("NACH", "EMANDATE", "ECS");
    private static final Set<String> VALID_FREQUENCIES = Set.of("MONTHLY", "QUARTERLY", "AS_PRESENTED");
    private static final Set<String> VALID_DEBIT_TYPES = Set.of("FIXED", "MAXIMUM");
    private static final Set<String> VALID_STATUSES = Set.of("PENDING", "REGISTERED", "REJECTED", "FAILED");

    public List<String> validate(MandateRequest request) {
        List<String> failureReasons = new ArrayList<>();

        if (request.getApplicationId() == null) {
            failureReasons.add("Application ID is required");
        }

        validateAccountDetails(request, failureReasons);
        validateMandateTerms(request, failureReasons);
        validateStatus(request, failureReasons);

        return failureReasons;
    }

    private void validateAccountDetails(MandateRequest request, List<String> failureReasons) {

        String accountNumber = request.getBankAccountNumber();
        String confirmAccountNumber = request.getConfirmBankAccountNumber();

        if (isBlank(accountNumber)) {
            failureReasons.add("Bank account number is required");
        } else if (!ACCOUNT_NUMBER_PATTERN.matcher(accountNumber.trim()).matches()) {
            failureReasons.add("Bank account number must be 9 to 18 digits");
        }

        if (isBlank(confirmAccountNumber)) {
            failureReasons.add("Confirm bank account number is required");
        } else if (accountNumber != null && !confirmAccountNumber.trim().equals(accountNumber.trim())) {
            failureReasons.add("Bank account number and confirmation do not match");
        }

        String ifsc = request.getIfscCode();
        if (isBlank(ifsc)) {
            failureReasons.add("IFSC code is required");
        } else if (!IFSC_PATTERN.matcher(ifsc.trim().toUpperCase()).matches()) {
            failureReasons.add("IFSC code format is invalid (expected format: AAAA0999999)");
        }

        if (isBlank(request.getBankName())) {
            failureReasons.add("Bank name is required");
        }

        if (isBlank(request.getBranchName())) {
            failureReasons.add("Branch name is required");
        }

        if (isBlank(request.getAccountHolderName())) {
            failureReasons.add("Account holder name is required");
        }

        String accountType = request.getAccountType();
        if (isBlank(accountType)) {
            failureReasons.add("Account type is required");
        } else if (!VALID_ACCOUNT_TYPES.contains(accountType.trim().toUpperCase())) {
            failureReasons.add("Account type must be SAVINGS or CURRENT");
        }
    }

    private void validateMandateTerms(MandateRequest request, List<String> failureReasons) {

        String mandateType = request.getMandateType();
        if (isBlank(mandateType)) {
            failureReasons.add("Mandate type is required");
        } else if (!VALID_MANDATE_TYPES.contains(mandateType.trim().toUpperCase())) {
            failureReasons.add("Mandate type must be NACH, EMANDATE, or ECS");
        }

        BigDecimal amountLimit = request.getMandateAmountLimit();
        if (amountLimit == null) {
            failureReasons.add("Mandate amount limit is required");
        } else if (amountLimit.compareTo(BigDecimal.ZERO) <= 0) {
            failureReasons.add("Mandate amount limit must be greater than zero");
        }

        String frequency = request.getMandateFrequency();
        if (isBlank(frequency)) {
            failureReasons.add("Mandate frequency is required");
        } else if (!VALID_FREQUENCIES.contains(frequency.trim().toUpperCase())) {
            failureReasons.add("Mandate frequency must be MONTHLY, QUARTERLY, or AS_PRESENTED");
        }

        LocalDate startDate = request.getMandateStartDate();
        LocalDate endDate = request.getMandateEndDate();

        if (startDate == null) {
            failureReasons.add("Mandate start date is required");
        } else if (startDate.isBefore(LocalDate.now())) {
            failureReasons.add("Mandate start date cannot be in the past");
        }

        if (endDate != null && startDate != null && !endDate.isAfter(startDate)) {
            failureReasons.add("Mandate end date must be after the start date");
        }

        String debitType = request.getDebitType();
        if (isBlank(debitType)) {
            failureReasons.add("Debit type is required");
        } else if (!VALID_DEBIT_TYPES.contains(debitType.trim().toUpperCase())) {
            failureReasons.add("Debit type must be FIXED or MAXIMUM");
        }
    }

    private void validateStatus(MandateRequest request, List<String> failureReasons) {

        String status = request.getMandateStatus();
        if (isBlank(status)) {
            failureReasons.add("Mandate status is required");
            return;
        }

        String normalizedStatus = status.trim().toUpperCase();
        if (!VALID_STATUSES.contains(normalizedStatus)) {
            failureReasons.add("Mandate status must be PENDING, REGISTERED, REJECTED, or FAILED");
            return;
        }

        if (normalizedStatus.equals("REGISTERED") && isBlank(request.getUmrn())) {
            failureReasons.add("UMRN is required when mandate status is REGISTERED");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}