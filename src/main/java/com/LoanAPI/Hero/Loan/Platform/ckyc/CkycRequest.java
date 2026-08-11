package com.LoanAPI.Hero.Loan.Platform.ckyc;

public class CkycRequest {

    private Long applicationId;
    private boolean ckycFound;

    // Fields populated if CKYC lookup succeeded
    private String fetchedAddressLine1;
    private String fetchedCity;
    private String fetchedState;
    private String fetchedPincode;

    // Fields populated manually if CKYC lookup failed
    private String manualAddressLine1;
    private String manualCity;
    private String manualState;
    private String manualPincode;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public boolean isCkycFound() {
        return ckycFound;
    }

    public void setCkycFound(boolean ckycFound) {
        this.ckycFound = ckycFound;
    }

    public String getFetchedAddressLine1() {
        return fetchedAddressLine1;
    }

    public void setFetchedAddressLine1(String fetchedAddressLine1) {
        this.fetchedAddressLine1 = fetchedAddressLine1;
    }

    public String getFetchedCity() {
        return fetchedCity;
    }

    public void setFetchedCity(String fetchedCity) {
        this.fetchedCity = fetchedCity;
    }

    public String getFetchedState() {
        return fetchedState;
    }

    public void setFetchedState(String fetchedState) {
        this.fetchedState = fetchedState;
    }

    public String getFetchedPincode() {
        return fetchedPincode;
    }

    public void setFetchedPincode(String fetchedPincode) {
        this.fetchedPincode = fetchedPincode;
    }

    public String getManualAddressLine1() {
        return manualAddressLine1;
    }

    public void setManualAddressLine1(String manualAddressLine1) {
        this.manualAddressLine1 = manualAddressLine1;
    }

    public String getManualCity() {
        return manualCity;
    }

    public void setManualCity(String manualCity) {
        this.manualCity = manualCity;
    }

    public String getManualState() {
        return manualState;
    }

    public void setManualState(String manualState) {
        this.manualState = manualState;
    }

    public String getManualPincode() {
        return manualPincode;
    }

    public void setManualPincode(String manualPincode) {
        this.manualPincode = manualPincode;
    }
}