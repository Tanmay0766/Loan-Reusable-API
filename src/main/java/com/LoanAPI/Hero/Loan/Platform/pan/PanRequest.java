package com.LoanAPI.Hero.Loan.Platform.pan;

public class PanRequest {

    private Long applicationId;
    private String panNumber;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }
}