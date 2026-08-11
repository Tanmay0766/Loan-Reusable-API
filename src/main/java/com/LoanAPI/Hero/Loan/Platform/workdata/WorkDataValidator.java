package com.LoanAPI.Hero.Loan.Platform.workdata;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class WorkDataValidator {

    private static final BigDecimal MIN_MONTHLY_INCOME = new BigDecimal("15000");
    private static final Set<String> VALID_EMPLOYMENT_TYPES = Set.of("SALARIED", "SELF_EMPLOYED");
    private static final Set<String> VALID_SALARY_FREQUENCIES = Set.of("MONTHLY", "WEEKLY", "BIWEEKLY");
    private static final Set<String> VALID_EMPLOYMENT_NATURE = Set.of("PERMANENT", "CONTRACT", "TEMPORARY");
    private static final Pattern PINCODE_PATTERN = Pattern.compile("^[1-9][0-9]{5}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[6-9][0-9]{9}$");

    public List<String> validate(WorkDataRequest request) {
        List<String> failureReasons = new ArrayList<>();

        if (request.getApplicationId() == null) {
            failureReasons.add("Application ID is required");
        }

        String employmentType = request.getEmploymentType();
        if (employmentType == null || employmentType.isBlank()) {
            failureReasons.add("Employment type is required");
            return failureReasons;
        }

        String normalizedType = employmentType.trim().toUpperCase();
        if (!VALID_EMPLOYMENT_TYPES.contains(normalizedType)) {
            failureReasons.add("Employment type must be SALARIED or SELF_EMPLOYED");
            return failureReasons;
        }

        validateCommonFields(request, failureReasons);

        if (normalizedType.equals("SALARIED")) {
            validateSalariedFields(request, failureReasons);
        } else {
            validateSelfEmployedFields(request, failureReasons);
        }

        return failureReasons;
    }

    private void validateCommonFields(WorkDataRequest request, List<String> failureReasons) {

        if (request.getMonthlyIncome() == null) {
            failureReasons.add("Monthly income is required");
        } else if (request.getMonthlyIncome().compareTo(BigDecimal.ZERO) <= 0) {
            failureReasons.add("Monthly income must be greater than zero");
        } else if (request.getMonthlyIncome().compareTo(MIN_MONTHLY_INCOME) < 0) {
            failureReasons.add("Monthly income must be at least ₹" + MIN_MONTHLY_INCOME);
        }

        if (request.getOtherMonthlyIncome() != null
                && request.getOtherMonthlyIncome().compareTo(BigDecimal.ZERO) < 0) {
            failureReasons.add("Other monthly income cannot be negative");
        }

        if (isBlank(request.getSalaryAccountBank())) {
            failureReasons.add("Salary/income account bank is required");
        }

        if (isBlank(request.getOfficeAddressLine1())) {
            failureReasons.add("Office address is required");
        }

        if (isBlank(request.getOfficeCity())) {
            failureReasons.add("Office city is required");
        }

        if (isBlank(request.getOfficeState())) {
            failureReasons.add("Office state is required");
        }

        if (isBlank(request.getOfficePincode())) {
            failureReasons.add("Office pincode is required");
        } else if (!PINCODE_PATTERN.matcher(request.getOfficePincode().trim()).matches()) {
            failureReasons.add("Office pincode is invalid (must be 6 digits, not starting with 0)");
        }

        if (isBlank(request.getWorkPhoneNumber())) {
            failureReasons.add("Work phone number is required");
        } else if (!PHONE_PATTERN.matcher(request.getWorkPhoneNumber().trim()).matches()) {
            failureReasons.add("Work phone number is invalid (must be a 10-digit number starting with 6-9)");
        }
    }

    private void validateSalariedFields(WorkDataRequest request, List<String> failureReasons) {

        if (isBlank(request.getEmployerName())) {
            failureReasons.add("Employer name is required for salaried applicants");
        }

        if (isBlank(request.getJobDesignation())) {
            failureReasons.add("Job designation is required for salaried applicants");
        }

        Integer yearsWithEmployer = request.getYearsWithEmployer();
        if (yearsWithEmployer == null) {
            failureReasons.add("Years with current employer is required for salaried applicants");
        } else if (yearsWithEmployer < 0) {
            failureReasons.add("Years with current employer cannot be negative");
        }

        Integer totalExperience = request.getTotalWorkExperience();
        if (totalExperience == null) {
            failureReasons.add("Total work experience is required for salaried applicants");
        } else if (totalExperience < 0) {
            failureReasons.add("Total work experience cannot be negative");
        } else if (yearsWithEmployer != null && totalExperience < yearsWithEmployer) {
            failureReasons.add("Total work experience cannot be less than years with current employer");
        }

        if (request.getDateOfJoining() == null) {
            failureReasons.add("Date of joining is required for salaried applicants");
        } else if (request.getDateOfJoining().isAfter(LocalDate.now())) {
            failureReasons.add("Date of joining cannot be in the future");
        }

        if (isBlank(request.getEmployerTypeIndustry())) {
            failureReasons.add("Employer type/industry is required for salaried applicants");
        }

        String frequency = request.getSalaryCreditFrequency();
        if (isBlank(frequency)) {
            failureReasons.add("Salary credit frequency is required for salaried applicants");
        } else if (!VALID_SALARY_FREQUENCIES.contains(frequency.trim().toUpperCase())) {
            failureReasons.add("Salary credit frequency must be MONTHLY, WEEKLY, or BIWEEKLY");
        }

        String nature = request.getNatureOfEmployment();
        if (isBlank(nature)) {
            failureReasons.add("Nature of employment is required for salaried applicants");
        } else if (!VALID_EMPLOYMENT_NATURE.contains(nature.trim().toUpperCase())) {
            failureReasons.add("Nature of employment must be PERMANENT, CONTRACT, or TEMPORARY");
        }
    }

    private void validateSelfEmployedFields(WorkDataRequest request, List<String> failureReasons) {

        if (isBlank(request.getBusinessName())) {
            failureReasons.add("Business name is required for self-employed applicants");
        }

        if (isBlank(request.getBusinessType())) {
            failureReasons.add("Business type is required for self-employed applicants");
        }

        if (request.getAnnualTurnover() == null) {
            failureReasons.add("Annual turnover is required for self-employed applicants");
        } else if (request.getAnnualTurnover().compareTo(BigDecimal.ZERO) <= 0) {
            failureReasons.add("Annual turnover must be greater than zero");
        }

        if (request.getBusinessVintage() == null) {
            failureReasons.add("Business vintage is required for self-employed applicants");
        } else if (request.getBusinessVintage() < 0) {
            failureReasons.add("Business vintage cannot be negative");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}