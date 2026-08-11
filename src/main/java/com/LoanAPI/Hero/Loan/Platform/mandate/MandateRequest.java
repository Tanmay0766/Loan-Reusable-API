package com.LoanAPI.Hero.Loan.Platform.mandate;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MandateRequest {

    private Long applicationId;

    private String bankAccountNumber;
    private String confirmBankAccountNumber;
    private String ifscCode;
    private String bankName;
    private String branchName;
    private String accountHolderName;
    private String accountType; // SAVINGS or CURRENT

    private String mandateType;       // NACH, EMANDATE, ECS
    private BigDecimal mandateAmountLimit;
    private String mandateFrequency;  // MONTHLY, QUARTERLY, AS_PRESENTED
    private LocalDate mandateStartDate;
    private LocalDate mandateEndDate;
    private String debitType;         // FIXED or MAXIMUM

    private String umrn; // Unique Mandate Reference Number, present only if registered
    private String mandateStatus; // PENDING, REGISTERED, REJECTED, FAILED

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getConfirmBankAccountNumber() {
        return confirmBankAccountNumber;
    }

    public void setConfirmBankAccountNumber(String confirmBankAccountNumber) {
        this.confirmBankAccountNumber = confirmBankAccountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getMandateType() {
        return mandateType;
    }

    public void setMandateType(String mandateType) {
        this.mandateType = mandateType;
    }

    public BigDecimal getMandateAmountLimit() {
        return mandateAmountLimit;
    }

    public void setMandateAmountLimit(BigDecimal mandateAmountLimit) {
        this.mandateAmountLimit = mandateAmountLimit;
    }

    public String getMandateFrequency() {
        return mandateFrequency;
    }

    public void setMandateFrequency(String mandateFrequency) {
        this.mandateFrequency = mandateFrequency;
    }

    public LocalDate getMandateStartDate() {
        return mandateStartDate;
    }

    public void setMandateStartDate(LocalDate mandateStartDate) {
        this.mandateStartDate = mandateStartDate;
    }

    public LocalDate getMandateEndDate() {
        return mandateEndDate;
    }

    public void setMandateEndDate(LocalDate mandateEndDate) {
        this.mandateEndDate = mandateEndDate;
    }

    public String getDebitType() {
        return debitType;
    }

    public void setDebitType(String debitType) {
        this.debitType = debitType;
    }

    public String getUmrn() {
        return umrn;
    }

    public void setUmrn(String umrn) {
        this.umrn = umrn;
    }

    public String getMandateStatus() {
        return mandateStatus;
    }

    public void setMandateStatus(String mandateStatus) {
        this.mandateStatus = mandateStatus;
    }
}