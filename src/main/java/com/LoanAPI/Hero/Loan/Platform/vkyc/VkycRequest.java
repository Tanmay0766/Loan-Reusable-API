package com.LoanAPI.Hero.Loan.Platform.vkyc;

import java.time.LocalDateTime;

public class VkycRequest {

    private Long applicationId;

    private String vkycStatus; // PENDING, COMPLETED, FAILED, REJECTED
    private String sessionReferenceId;
    private String agentId;
    private String agentName;
    private LocalDateTime verificationDateTime;
    private String verificationMode; // LIVE_VIDEO_CALL, RECORDED
    private String applicantLocationCity;
    private String deviceUsed; // MOBILE, WEB
    private String livenessCheckResult; // PASS, FAIL
    private String documentShown; // PAN, AADHAAR, etc.
    private String remarks;
    private String rejectionReason;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getVkycStatus() {
        return vkycStatus;
    }

    public void setVkycStatus(String vkycStatus) {
        this.vkycStatus = vkycStatus;
    }

    public String getSessionReferenceId() {
        return sessionReferenceId;
    }

    public void setSessionReferenceId(String sessionReferenceId) {
        this.sessionReferenceId = sessionReferenceId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public LocalDateTime getVerificationDateTime() {
        return verificationDateTime;
    }

    public void setVerificationDateTime(LocalDateTime verificationDateTime) {
        this.verificationDateTime = verificationDateTime;
    }

    public String getVerificationMode() {
        return verificationMode;
    }

    public void setVerificationMode(String verificationMode) {
        this.verificationMode = verificationMode;
    }

    public String getApplicantLocationCity() {
        return applicantLocationCity;
    }

    public void setApplicantLocationCity(String applicantLocationCity) {
        this.applicantLocationCity = applicantLocationCity;
    }

    public String getDeviceUsed() {
        return deviceUsed;
    }

    public void setDeviceUsed(String deviceUsed) {
        this.deviceUsed = deviceUsed;
    }

    public String getLivenessCheckResult() {
        return livenessCheckResult;
    }

    public void setLivenessCheckResult(String livenessCheckResult) {
        this.livenessCheckResult = livenessCheckResult;
    }

    public String getDocumentShown() {
        return documentShown;
    }

    public void setDocumentShown(String documentShown) {
        this.documentShown = documentShown;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}