package com.LoanAPI.Hero.Loan.Platform.workdata;

import java.math.BigDecimal;
import java.time.LocalDate;

public class WorkDataRequest {

    private Long applicationId;
    private String employmentType; // SALARIED or SELF_EMPLOYED

    // Common fields
    private BigDecimal monthlyIncome;
    private BigDecimal otherMonthlyIncome;
    private String salaryAccountBank;
    private String officeAddressLine1;
    private String officeCity;
    private String officeState;
    private String officePincode;
    private String workPhoneNumber;

    // Salaried-specific fields
    private String employerName;
    private String jobDesignation;
    private Integer yearsWithEmployer;
    private Integer totalWorkExperience;
    private LocalDate dateOfJoining;
    private String employerTypeIndustry;
    private String salaryCreditFrequency; // MONTHLY, WEEKLY, BIWEEKLY
    private String natureOfEmployment;    // PERMANENT, CONTRACT, TEMPORARY

    // Self-employed-specific fields
    private String businessName;
    private String businessType;
    private BigDecimal annualTurnover;
    private Integer businessVintage;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public BigDecimal getOtherMonthlyIncome() {
        return otherMonthlyIncome;
    }

    public void setOtherMonthlyIncome(BigDecimal otherMonthlyIncome) {
        this.otherMonthlyIncome = otherMonthlyIncome;
    }

    public String getSalaryAccountBank() {
        return salaryAccountBank;
    }

    public void setSalaryAccountBank(String salaryAccountBank) {
        this.salaryAccountBank = salaryAccountBank;
    }

    public String getOfficeAddressLine1() {
        return officeAddressLine1;
    }

    public void setOfficeAddressLine1(String officeAddressLine1) {
        this.officeAddressLine1 = officeAddressLine1;
    }

    public String getOfficeCity() {
        return officeCity;
    }

    public void setOfficeCity(String officeCity) {
        this.officeCity = officeCity;
    }

    public String getOfficeState() {
        return officeState;
    }

    public void setOfficeState(String officeState) {
        this.officeState = officeState;
    }

    public String getOfficePincode() {
        return officePincode;
    }

    public void setOfficePincode(String officePincode) {
        this.officePincode = officePincode;
    }

    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getJobDesignation() {
        return jobDesignation;
    }

    public void setJobDesignation(String jobDesignation) {
        this.jobDesignation = jobDesignation;
    }

    public Integer getYearsWithEmployer() {
        return yearsWithEmployer;
    }

    public void setYearsWithEmployer(Integer yearsWithEmployer) {
        this.yearsWithEmployer = yearsWithEmployer;
    }

    public Integer getTotalWorkExperience() {
        return totalWorkExperience;
    }

    public void setTotalWorkExperience(Integer totalWorkExperience) {
        this.totalWorkExperience = totalWorkExperience;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getEmployerTypeIndustry() {
        return employerTypeIndustry;
    }

    public void setEmployerTypeIndustry(String employerTypeIndustry) {
        this.employerTypeIndustry = employerTypeIndustry;
    }

    public String getSalaryCreditFrequency() {
        return salaryCreditFrequency;
    }

    public void setSalaryCreditFrequency(String salaryCreditFrequency) {
        this.salaryCreditFrequency = salaryCreditFrequency;
    }

    public String getNatureOfEmployment() {
        return natureOfEmployment;
    }

    public void setNatureOfEmployment(String natureOfEmployment) {
        this.natureOfEmployment = natureOfEmployment;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public BigDecimal getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(BigDecimal annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public Integer getBusinessVintage() {
        return businessVintage;
    }

    public void setBusinessVintage(Integer businessVintage) {
        this.businessVintage = businessVintage;
    }
}