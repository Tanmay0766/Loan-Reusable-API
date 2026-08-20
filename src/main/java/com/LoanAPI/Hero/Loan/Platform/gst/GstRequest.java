package com.LoanAPI.Hero.Loan.Platform.gst;

public class GstRequest {

    private Long applicationId;
    private String gstin;
    private String legalBusinessName;
    private String gstRegistrationType; // REGULAR, COMPOSITION, CASUAL, UNREGISTERED

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getLegalBusinessName() {
        return legalBusinessName;
    }

    public void setLegalBusinessName(String legalBusinessName) {
        this.legalBusinessName = legalBusinessName;
    }

    public String getGstRegistrationType() {
        return gstRegistrationType;
    }

    public void setGstRegistrationType(String gstRegistrationType) {
        this.gstRegistrationType = gstRegistrationType;
    }
}